package example.customer.service.test;

import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import example.customer.service.model.Customer;

public class RestfulTestClient {
	public static final String REST_SERVICE_URI = "http://localhost:8080/api";
	  
    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    private static HttpHeaders getHeaders(){
        String plainCredentials="bill:abc123";
        String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
         
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
     
    /*
     * Send a GET request to get list of all customers.
     */
    @SuppressWarnings("unchecked")
    private static void listAllCustomers(){
        System.out.println("\nTesting listAllCustomers API-----------");
        RestTemplate restTemplate = new RestTemplate(); 
         
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI+"/customer/", HttpMethod.GET, request, List.class);
        List<LinkedHashMap<String, Object>> customersMap = (List<LinkedHashMap<String, Object>>)response.getBody();
         
        if(customersMap!=null){
            for(LinkedHashMap<String, Object> map : customersMap){
                System.out.println("Customer : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
            }
        }else{
            System.out.println("No customer exist----------");
        }
    }
      
    /*
     * Send a GET request to get a specific customer.
     */
    private static void getCustomer(){
        System.out.println("\nTesting getCustomer API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<Customer> response = restTemplate.exchange(REST_SERVICE_URI+"/customer/1", HttpMethod.GET, request, Customer.class);
        Customer customer = response.getBody();
        System.out.println(customer);
    }
      
    /*
     * Send a POST request to create a new customer.
     */
    private static void createCustomer() {
        System.out.println("\nTesting create Customer API----------");
        RestTemplate restTemplate = new RestTemplate();
        Customer customer = new Customer(0,"Sarah",51,134);
        HttpEntity<Object> request = new HttpEntity<Object>(customer, getHeaders());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/customer/", request, Customer.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
  
    /*
     * Send a PUT request to update an existing customer.
     */
    private static void updateCustomer() {
        System.out.println("\nTesting update Customer API----------");
        RestTemplate restTemplate = new RestTemplate();
        Customer customer  = new Customer(1,"Tomy",33, 70000);
        HttpEntity<Object> request = new HttpEntity<Object>(customer, getHeaders());
        ResponseEntity<Customer> response = restTemplate.exchange(REST_SERVICE_URI+"/customer/1", HttpMethod.PUT, request, Customer.class);
        System.out.println(response.getBody());
    }
  
    /*
     * Send a DELETE request to delete a specific customer.
     */
    private static void deleteCustomer() {
        System.out.println("\nTesting delete Customer API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI+"/customer/3", HttpMethod.DELETE, request, Customer.class);
    }
  
  
    /*
     * Send a DELETE request to delete all customers.
     */
    private static void deleteAllCustomers() {
        System.out.println("\nTesting all delete Customers API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI+"/customer/", HttpMethod.DELETE, request, Customer.class);
    }
  
 
    public static void main(String args[]){
         
        listAllCustomers();
 
        getCustomer();
 
        createCustomer();
        listAllCustomers();
 
        updateCustomer();
        listAllCustomers();
 
        deleteCustomer();
        listAllCustomers();
 
        deleteAllCustomers();
        listAllCustomers();
    }
}
