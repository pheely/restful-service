# What are RESTFUL Web Services

See <a href="http://stackoverflow.com/questions/671118/what-exactly-is-restful-programming">this StackOverflow link</a> for details.

<b>RESTful web services</b> are built to work best on the Web. Representational State Transfer (REST) is an <u>architectural style</u> that specifies <u>constraints</u>. If applied to a web service, these constraints induce desirable properties, such as performance, scalability, and modifiability, that enable services to work best on the Web. 

There are a few important constraints in the REST architectural style:
<ol>
  <li>Data and functionality are considered resources and are accessed using Uniform Resource Identifiers (URIs), typically links on the Web.</li>
  <li>The resources are acted upon by using a set of simple, well-defined operations.</li> 
  <li>The architecture is a client/server architecture.</li>
  <li>The client and the server use a stateless communication protocol, typically HTTP.</li>
  <li>Clients and servers exchange representations of resources by using a standardized interface and protocol.</li>
  <li>The service results should be Cacheable. HTTP cache, for example.</li>
  <li>Service should assume a Layered architecture. Client should not assume direct connection to server - it might be getting info from a middle layer - cache.</li>
  <li>Self-descriptive messages: Resources are decoupled from their representation so that their content can be accessed in a variety of formats, such as HTML, XML, plain text, PDF, JPEG, JSON, and others. Metadata about the resource is available and used, for example, to control caching, detect transmission errors, negotiate the appropriate representation format, and perform authentication or access control.</li>
  <li>Stateful interactions through hyperlinks: Every interaction with a resource is stateless; that is, request messages are self-contained. Stateful interactions are based on the concept of explicit state transfer. Several techniques exist to exchange state, such as URI rewriting, cookies, and hidden form fields. State can be embedded in response messages to point to valid future states of the interaction. </li>
</ol>

# Richardson Maturity Model

Richardson Maturity Model is used to identify the maturity level of a Restful Web Service. Following are the different levels and their characteristics:

Level 0 : Expose SOAP web services in REST style. Expose action based services (http://server/getPosts, http://server/deletePosts, http://server/doThis, http://server/doThat etc) using REST.

Level 1 : Expose Resources with proper URI’s (using nouns). Ex: http://server/accounts, http://server/accounts/10. However, HTTP Methods are not used.

Level 2 : Resources use proper URI’s + HTTP Methods. For example, to update an account, you do a PUT to . The create an account, you do a POST to . Uris look like posts/1/comments/5 and accounts/1/friends/1.

Level 3 : HATEOAS (Hypermedia as the engine of application state). You will tell not only about the information being requested but also about the next possible actions that the service consumer can do. When requesting information about a facebook user, a REST service can return user details along with information about how to get his recent posts, how to get his recent comments and how to retrieve his friend’s list.

# Using appropriate Request Methods

Always use HTTP Methods. Best practices with respect to each HTTP method is described below:

GET : Should not update anything. Should be idempotent (same result in multiple calls). Possible Return Codes 200 (OK) + 404 (NOT FOUND) +400 (BAD REQUEST)

POST : Should create new resource. Ideally return JSON with link to newly created resource. Same return codes as get possible. In addition : Return code 201 (CREATED) is possible.

PUT : Update a known resource. ex: update client details. Possible Return Codes : 200(OK)

DELETE : Used to delete a resource.

# Spring Rest Controller

**@RestController**: This annotation eliminates the need of annotating each method with @ResponseBody. Under the hood, @RestController is itself annotated with @ResponseBody, and can be considered as combination of @Controller and @ResponseBody.

**@RequestBody**: If a method parameter is annotated with @RequestBody, Spring will bind the incoming HTTP request body (for the URL mentioned in @RequestMapping for that method) to that parameter. While doing that, Spring will \[behind the scenes\] use HTTP Message converters to convert the HTTP request body into domain object \[deserialize request body to domain object\], based on ACCEPT or Content-Type header present in request.

**@ResponseBody**: If a method is annotated with @ResponseBody, Spring will bind the return value to outgoing HTTP response body. While doing that, Spring will \[behind the scenes\] use HTTP Message converters to convert the return value to HTTP response body \[serialize the object to response body\], based on Content-Type present in request HTTP header. As already mentioned, in Spring 4, you may stop using this annotation.

**ResponseEntity** is a real deal. It represents the entire HTTP response. Good thing about it is that you can control anything that goes into it. You can specify status code, headers, and body. It comes with several constructors to carry the information you want to sent in HTTP Response.

**@PathVariable**: This annotation indicates that a method parameter should be bound to a URI template variable \[the one in '{}'\].

Basically, @RestController , @RequestBody, ResponseEntity & @PathVariable are all you need to know to implement a REST API in Spring. Additionally, spring provides several support classes to help you implement something customized.

**MediaType**: Although we didn’t, with @RequestMapping annotation, you can additionally, specify the MediaType to be produced or consumed (using **produces** or **consumes** attributes) by that particular controller method, to further narrow down the mapping.
