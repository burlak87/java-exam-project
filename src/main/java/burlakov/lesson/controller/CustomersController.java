package burlakov.lesson.controller;

import burlakov.lesson.entity.Customers;
import burlakov.lesson.pojo.CustomersBody;
import burlakov.lesson.repo.CustomersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomersController {
    private final CustomersRepository repository;

    public CustomersController(CustomersRepository repository) {
        this.repository = repository;
    }

    @GetMapping("fetch-customers")
    public ResponseEntity<Iterable<Customers>> getCustomers() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("add-customers")
    public ResponseEntity<Customers> add(@RequestBody CustomersBody customersBody) {
        Customers customers = new Customers();
        customers.setName(customersBody.getName());
        customers.setEmail(customersBody.getEmail());
        customers.setPhone(customersBody.getPhone());
        customers.setAddress(customersBody.getAddress());
        repository.save(customers);
        return ResponseEntity.ok(customers);
    }
}
