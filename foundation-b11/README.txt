The structure is divided into the following

controller for REST 
service layer as main business logic 
repo layer for reposiotory 
model for business facing data
dao layer for data base objects



  



Tests for Account and Customer Repo and CustomerService 
Tests for CustomerService



API details 
localhost:8081/api/v1/customer -  [POST]Create Customer
localhost:8081/api/v1/customers - [GET] Get All Customers
localhost:8081/api/v1/customer/{customer-id} - [GET] to get customer by id
localhost:8081/api/v1/customer/{customer-id} - [DELETE] customer by id
localhost:8081/api/v1/customer/{customer-id}  [PUT] Update Customer  by id
localhost:8081/api/v1/customer/{customer-id}/account  [POST] add and attach account to customer
localhost:8081/api/v1/customer/{customer-id}account/{acc-id} [PUT] Update customer Account
localhost:8081/api/v1/customer/{customer-id}/account/{account-no}/balance [PATCH]Update customer account balance by account no
localhost:8081/api/v1/customer/{customer-id}/account/transfer  [PATCH] Transfer account balance 

Test
localhost:8081/api/v1/testcreateuser - GET (creates a customer with account)


Not done 
Login 
Tests fore controller 
More Tests required for Service (Exception Tests)


Run 
java -jar target\foundation-b11-0.0.1-SNAPSHOT.jar


Also added PostManCollection



