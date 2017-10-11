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
