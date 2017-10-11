package example.customer.service.service;

import java.util.List;

import example.customer.service.model.Customer;

public interface CustomerService {
	Customer findById(long id);
	Customer findByName(String name);
	void saveCustomer(Customer customer);
	void updateCustomer(Customer customer);
	void deleteCustomerById(long id);
	List<Customer> findAllCustomers();
	void deleteAllCustomers();
	boolean isCustomerExist(Customer customer);
}
