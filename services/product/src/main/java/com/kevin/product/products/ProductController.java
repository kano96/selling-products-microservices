package com.kevin.product.products;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Integer> createProduct(
      @RequestBody @Valid ProductRequest request
  ){
    return ResponseEntity.ok(service.createProduct(request));
  }

  @PostMapping("/purchase")
  public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
          @RequestBody List<PurchaseProductRequest> request
  ){
    return ResponseEntity.ok(service.purchaseProducts(request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> findById(@PathVariable Integer id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

}
