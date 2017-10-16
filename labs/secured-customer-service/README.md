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
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
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
