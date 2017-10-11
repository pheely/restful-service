package example.customer.service.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import example.customer.service.model.Customer;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Customer> customers;
	
	static{
		customers= populateDummyCustomers();
	}

	public List<Customer> findAllCustomers() {
		return customers;
	}
	
	public Customer findById(long id) {
		for(Customer customer : customers){
			if(customer.getId() == id){
				return customer;
			}
		}
		return null;
	}
	
	public Customer findByName(String name) {
		for(Customer customer : customers){
			if(customer.getName().equalsIgnoreCase(name)){
				return customer;
			}
		}
		return null;
	}
	
	public void saveCustomer(Customer customer) {
		customer.setId(counter.incrementAndGet());
		customers.add(customer);
	}

	public void updateCustomer(Customer customer) {
		int index = customers.indexOf(customer);
		customers.set(index, customer);
	}

	public void deleteCustomerById(long id) {
		for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext(); ) {
		    Customer customer = iterator.next();
		    if (customer.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isCustomerExist(Customer customer) {
		return findByName(customer.getName())!=null;
	}
	
	public void deleteAllCustomers(){
		customers.clear();
	}

	private static List<Customer> populateDummyCustomers(){
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(counter.incrementAndGet(),"Sam",30, 70000));
		customers.add(new Customer(counter.incrementAndGet(),"Tom",40, 50000));
		customers.add(new Customer(counter.incrementAndGet(),"Jerome",45, 30000));
		customers.add(new Customer(counter.incrementAndGet(),"Silvia",50, 40000));
		return customers;
	}
}
