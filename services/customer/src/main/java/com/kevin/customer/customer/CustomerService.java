package com.kevin.customer.customer;

import static java.lang.String.format;

import com.kevin.customer.exception.CostumerNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;
  private final CustomerMapper mapper;

  public String createCustomer(@Valid CustomerRequest request) {
    var customer = repository.save(mapper.toCustomer(request));
    return customer.getId();
  }

  public void updateCustomer(CustomerRequest request) {
    var customer = repository.findById(request.id()).orElseThrow(() -> new CostumerNotFoundException(
        format("Costumer with id %s not found", request.id())
    ));
    mergerCustomer(customer, request);
    repository.save(customer);
  }

  private void mergerCustomer(Customer customer, CustomerRequest request) {
    if(StringUtils.isNotBlank(request.firstname())){
      customer.setFirstname(request.firstname());
    }

    if(StringUtils.isNotBlank(request.lastname())){
      customer.setLastname(request.lastname());
    }

    if(StringUtils.isNotBlank(request.email())){
      customer.setEmail(request.email());
    }

    if(request.address() != null){
      customer.setAddress(request.address());
    }
  }

  public List<CustomerResponse> getCustomers() {
    return repository.findAll()
        .stream()
        .map(mapper::fromCustomer)
        .collect(Collectors.toList());
  }

  public Boolean getCustomer(String id) {
    return repository.findById(id).isPresent();
  }

  public CustomerResponse findById(String id) {
    return repository.findById(id)
        .map(mapper::fromCustomer)
        .orElseThrow(() -> new CostumerNotFoundException(
        format("Costumer with id %s not found", id)
    ));
  }

  public void deleteById(String id) {
    repository.deleteById(id);
  }
}
