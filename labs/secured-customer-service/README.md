# Secured Customer Service

## Testing Unsecured Application in Postman

1. Retrieve all customers

```shell
curl -H "Content-Type: application/json" -X GET http://localhost:8080/api/customer/
```

![postman](imgs/unsec-get-all.png)

2. Retrieve one customer

```shell
curl -H "Content-Type: application/json" -X GET http://localhost:8080/api/customer/1
```

![postman](imgs/unsec-get-one.png)

3. Retrieve an unknown customer

```python
curl -H "Content-Type: application/json" -X GET http://localhost:8080/api/customer/45
```
![postman](imgs/unsec-get-not-found.png)

4. Create a customer

```shell
curl -H "Content-Type: application/json" -X POST \
-d '{"name": "Pheely", "age": 51, "salary": 11000}' \
http://localhost:8080/api/customer/
```

![postman](imgs/unsec-post.png)

5. Verify the created customer

```shell
curl -H "Content-Type: application/json" -X GET http://localhost:8080/api/customer/5
```

![postman](imgs/unsec-get-newly-created.png)

6. Create a duplicate customer

```shell
curl -H "Content-Type: application/json" -X POST \
-d '{"name": "Pheely", "age": 51, "salary": 11000}' \
http://localhost:8080/api/customer/
```

![postman](imgs/unsec-post-duplicated.png)

7. Update a customer

```shell
curl -H "Content-Type: application/json" -X PUT \
-d '{"name": "Pheely", "age": 51, "salary": 1}' \
http://localhost:8080/api/customer/5
```

![postman](imgs/unsec-put.png)

8. Delete a customer

```shell
curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/api/customer/5
```

![postman](imgs/unsec-delete.png)

9. Delete all customers

```shell
curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/api/customer/
```

![postman](imgs/unsec-delete-all.png)

## Secure the Restful Service Using Basic Authentication

1. WebSecurityConfigurerAdapter can be used to configure the security settings. The subclass should be annotated for Configuration and EnableWebSecurity. AuthenticationBuilder can be used to set up user credentials.

```Java
package example.customer.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 
 * @author pyang
 *
 * This class will enable basic authentication
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private static String REALM = "MY_TEST_REALM";
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("tom").password("abc123").roles("USER");
    }
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {
  
      http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/**").hasRole("ADMIN")
        .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
	//We don't need sessions to be created.
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
     
    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }
     
    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

}
```

2. BasicAuthenticationEntryPoint overrides the default system behavior when the authentication fails. In this case, we are going to write "HTTP Status 401 : Bad credentials" in HTTP response body.

```Java
package example.customer.service.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/**
 * 
 * @author pyang
 *
 * In case the Authentication fails [invalid/missing credentials], this entry point will get triggered. 
 * It is very important, because we don’t want [Spring Security default behavior] of redirecting to a login page on authentication failure 
 * of RESTful call [ We don't have a login page].
 */
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	@Override
    public void commence(final HttpServletRequest request, 
            final HttpServletResponse response, 
            final AuthenticationException authException) throws IOException, ServletException {
        //Authentication failed, send error response.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
         
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 : " + authException.getMessage());
    }
     
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("MY_TEST_REALM");
        super.afterPropertiesSet();
    }
}
```

## Test Basic Authentication Using curl

1. Retrieve all customers

```shell
curl -H "Content-Type: application/json" -H "Authorization: Basic YmlsbDphYmMxMjM=" -X GET http://localhost:8080/api/customer/
```

2. Retrieve one customer

```shell
curl -H "Content-Type: application/json" -H "Authorization: Basic YmlsbDphYmMxMjM=" -X GET http://localhost:8080/api/customer/1
```

3. Retrieve an unknown customer

```python
curl -H "Content-Type: application/json" -H "Authorization: Basic YmlsbDphYmMxMjM=" -X GET http://localhost:8080/api/customer/45
```

4. Create a customer

```shell
curl -H "Content-Type: application/json" -H "Authorization: Basic YmlsbDphYmMxMjM=" -X POST \
-d '{"name": "Pheely", "age": 51, "salary": 11000}' \
http://localhost:8080/api/customer/
```

5. Verify the created customer

```shell
curl -H "Content-Type: application/json" -H "Authorization: Basic YmlsbDphYmMxMjM=" -X GET http://localhost:8080/api/customer/5
```

6. Update a customer

```shell
curl -H "Content-Type: application/json" -H "Authorization: Basic YmlsbDphYmMxMjM=" -X PUT \
-d '{"name": "Pheely", "age": 51, "salary": 1}' \
http://localhost:8080/api/customer/5
```

7. Delete a customer

```shell
curl -H "Content-Type: application/json" -H "Authorization: Basic YmlsbDphYmMxMjM=" -X DELETE http://localhost:8080/api/customer/5
```

8. Delete all customers

```shell
curl -H "Content-Type: application/json" -H "Authorization: Basic YmlsbDphYmMxMjM=" -X DELETE http://localhost:8080/api/customer/
```
