package com.kevin.product.products;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  public Integer createProduct(@Valid ProductRequest request) {
    return null;
  }
}
