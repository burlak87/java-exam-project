package burlakov.lesson.service;

import burlakov.lesson.entity.Customers;
import burlakov.lesson.pojo.CustomersBody;
import burlakov.lesson.repo.CustomersRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomersService {
    private final CustomersRepository customersRepo;

    public CustomersService(CustomersRepository customersRepo) {
        this.customersRepo = customersRepo;
    }

    public Iterable<Customers> getAllCustomers() {
        return customersRepo.findAll();
    }

    public Customers createCustomer(CustomersBody body) {
        Customers customer = new Customers();
        customer.setName(body.getName());
        customer.setEmail(body.getEmail());
        customer.setPhone(body.getPhone());
        customer.setAddress(body.getAddress());
        return customersRepo.save(customer);
    }
}
