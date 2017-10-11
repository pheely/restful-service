package example.customer.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import example.customer.service.model.Customer;
import example.customer.service.service.CustomerService;
import example.customer.service.util.CustomerServiceErrorType;

@RestController
@RequestMapping("/api")

public class CustomerServiceController {
	public static final Logger logger = LoggerFactory.getLogger(CustomerServiceController.class);

	@Autowired
	CustomerService customerService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Customers---------------------------------------------

	@RequestMapping(value = "/customer/", method = RequestMethod.GET)
	public ResponseEntity<List<Customer>> listAllCustomers() {
		List<Customer> customers = customerService.findAllCustomers();
		if (customers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}

	// -------------------Retrieve Single Customer------------------------------------------

	@RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCustomer(@PathVariable("id") long id) {
		logger.info("Fetching Customer with id {}", id);
		Customer customer = customerService.findById(id);
		if (customer == null) {
			logger.error("Customer with id {} not found.", id);
			return new ResponseEntity(new CustomerServiceErrorType("Customer with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	// -------------------Create a Customer-------------------------------------------

	@RequestMapping(value = "/customer/", method = RequestMethod.POST)
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Customer : {}", customer);

		if (customerService.isCustomerExist(customer)) {
			logger.error("Unable to create. A Customer with name {} already exist", customer.getName());
			return new ResponseEntity(new CustomerServiceErrorType("Unable to create. A Customer with name " + 
			customer.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		customerService.saveCustomer(customer);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/customer/{id}").buildAndExpand(customer.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Customer ------------------------------------------------

	@RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
		logger.info("Updating Customer with id {}", id);

		Customer currentCustomer = customerService.findById(id);

		if (currentCustomer == null) {
			logger.error("Unable to update. Customer with id {} not found.", id);
			return new ResponseEntity(new CustomerServiceErrorType("Unable to upate. Customer with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentCustomer.setName(customer.getName());
		currentCustomer.setAge(customer.getAge());
		currentCustomer.setSalary(customer.getSalary());

		customerService.updateCustomer(currentCustomer);
		return new ResponseEntity<Customer>(currentCustomer, HttpStatus.OK);
	}

	// ------------------- Delete a Customer-----------------------------------------

	@RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Customer with id {}", id);

		Customer customer = customerService.findById(id);
		if (customer == null) {
			logger.error("Unable to delete. Customer with id {} not found.", id);
			return new ResponseEntity(new CustomerServiceErrorType("Unable to delete. Customer with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		customerService.deleteCustomerById(id);
		return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Customers-----------------------------

	@RequestMapping(value = "/customer/", method = RequestMethod.DELETE)
	public ResponseEntity<Customer> deleteAllCustomers() {
		logger.info("Deleting All Customers");

		customerService.deleteAllCustomers();
		return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
	}
}
