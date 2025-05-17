package burlakov.lesson.controller;

import burlakov.lesson.entity.Customers;
import burlakov.lesson.pojo.CustomersBody;
import burlakov.lesson.service.CustomersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomersController {
    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @GetMapping("customers/index")
    public ResponseEntity<Iterable<Customers>> getCustomers() {
        return ResponseEntity.ok(customersService.getAllCustomers());
    }

    @PostMapping("customers/store")
    public ResponseEntity<Customers> add(@RequestBody CustomersBody customersBody) {
        Customers customers = customersService.createCustomer(customersBody);
        return ResponseEntity.ok(customers);
    }
}
