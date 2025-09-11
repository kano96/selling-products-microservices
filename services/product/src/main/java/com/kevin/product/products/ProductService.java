package com.kevin.product.products;

import com.kevin.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository repository;
  private final ProductMapper mapper;

  public Integer createProduct(@Valid ProductRequest request) {
    var product = mapper.toProduct(request);
    return repository.save(product).getId();
  }

  public List<ProductPurchaseResponse> purchaseProducts(List<PurchaseProductRequest> request) {
    var productIds = request.stream()
            .map(PurchaseProductRequest::productId)
            .toList();
    var storedProducts = repository.findAllByIdInOrderById(productIds);
    if (productIds.size() != storedProducts.size()) {
      throw new ProductPurchaseException("One or more products does not exist");
    }

    var sortedProductsRequest = request.stream()
            .sorted(Comparator.comparing(PurchaseProductRequest::productId))
            .toList();

    var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

    for (int i = 0; i < storedProducts.size(); i++) {
      var product = storedProducts.get(i);
      var productRequest = sortedProductsRequest.get(i);

      if (product.getAvailableQuantity() < productRequest.quantity()) {
        throw new ProductPurchaseException("Insufficient stock quantity for product with id " + product.getId());
      }

      var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
      product.setAvailableQuantity(newAvailableQuantity);
      repository.save(product);
      purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
    }

    return purchasedProducts;
  }

  public ProductResponse findById(Integer id) {
    return repository.findById(id)
            .map(mapper::toProductResponse)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with the id: " + id));
  }

  public List<ProductResponse> findAll() {
    return repository.findAll()
            .stream()
            .map(mapper::toProductResponse)
            .collect(Collectors.toList());
  }
}
