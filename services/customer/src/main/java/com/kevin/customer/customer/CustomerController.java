package com.kevin.customer.customer;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService service;

  @PostMapping
  public ResponseEntity<String> createCustomer(
      @RequestBody @Valid CustomerRequest request
  ){
    return new ResponseEntity<>(service.createCustomer(request), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<Void> updateCustomer(
      @RequestBody @Valid CustomerRequest request
  ){
    service.updateCustomer(request);
    return ResponseEntity.accepted().build();
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
    return ResponseEntity.ok(service.getCustomers());
  }

  @GetMapping("/exists/{id}")
  public ResponseEntity<Boolean> existsById(@PathVariable String id) {
    return ResponseEntity.ok(service.getCustomer(id));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @DeleteMapping("/id")
  public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
    service.deleteById(id);
    return ResponseEntity.accepted().build();
  }
}
