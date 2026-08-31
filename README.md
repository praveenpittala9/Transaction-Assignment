# Transaction Starter Project

This is the starter project for the Customer Transactions exercise.

## Before you start

The first thing you should do after cloning the repository is:

### Linux / macOS

```bash
./mvnw clean test
```

### Windows

```bat
mvnw.cmd clean test
```

The sample test should pass before you begin implementing the exercise.

## What is already provided

* Java 17
* Spring Boot
* Maven wrapper
* Spring Web
* Spring Data JPA
* H2 embedded database
* JUnit / Spring Boot Test
* A sample REST endpoint: `GET /api/sample`
* A sample test that loads the Spring context



## Exercise

Implement these four operations:

1. Create transaction
2. Get transaction
3. Update transaction status
4. Get all transactions for a customer



You may change the surrounding design if you believe your solution is better.

> Problem Understanding

The application manages customer transactions.

It provides REST APIs to:

1. Create a transaction.
2. Retrieve a transaction by Transaction ID.
3. Update the status of an existing transaction.
4. Retrieve all transactions belonging to a customer.

The application uses Spring Boot, Spring Data JPA, and an H2 in-memory database.

>  Assumptions

- Transaction ID uniquely identifies a transaction.
- Customer ID can be associated with multiple transactions.
- Amount must be greater than zero.
- Currency, transaction type, and transaction status must be provided.
- Transaction status can be changed to any non-blank value because no specific transition restrictions were assigned at this stage.
- The H2 database is used as an in-memory database for this exercise, so data is not intended to persist after application shutdown.

> Known Limitations

- The database is in-memory, so transaction data is lost when the application stops.
- Status transitions are not restricted to a predefined state machine.
- Error responses currently use a simple `400 Bad Request` response for `IllegalArgumentException`.

> Improvements With More Time

- Add a centralized exception handling mechanism using `@ControllerAdvice`.
- Introduce stronger validation using Bean Validation annotations where appropriate.
- Use enums for transaction types and statuses if the business requirements define fixed values.
- Add more detailed API documentation using OpenAPI/Swagger.
- Add integration tests covering the complete REST API and database interaction.
- Add persistent database support for production use.  // very important in Production 

> Transaction fields

Every transaction contains:

* Transaction ID
* Customer ID
* Amount
* Currency
* Transaction Type
* Transaction Status





**>Validation rules**



A transaction is considered valid when:



**- Transaction ID is not null or blank.**

**- Customer ID is not null or blank.**

**- Amount is not null and must be greater than zero.**

**- Currency is not null or blank.**

**- Transaction type is not null or blank.**

**- Transaction status is not null or blank.**



These validations are performed in the service layer before a transaction is saved.



Invalid input results in an `IllegalArgumentException`, and the transaction is not saved.



**>Status transition rules**



The application allows a transaction status to be changed to any non-null, non-blank value.



There are no restrictions based on the previous status.



For example:



**- `SUCCESS` → `FAILED` is allowed.**

**- `FAILED` → `SUCCESS` is allowed.**

**- `PENDING` → `SUCCESS` is allowed.**



The update operation validates the transaction ID and new status, retrieves the existing transaction, updates its status, and saves it.



If the transaction does not exist, the update is rejected with `Transaction not found`.

### Validation rules

Define what makes a transaction valid. At minimum, consider:

* Transaction ID
* Customer ID
* Amount
* Currency
* Transaction type
* Initial status

Also explain any business validation you add beyond the annotations already supplied.

## API skeleton

### Create

**> API**



**>Create Transaction**



POST /api/transactions`



Example request:



>json data 

{

&#x20; "transactionId": "TXN001",

&#x20; "customerId": "CUST001",

&#x20; "amount": 500.00,

&#x20; "currency": "INR",

&#x20; "transactionType": "PAYMENT",

&#x20; "transactionStatus": "SUCCESS"

}





### Get

We need to get the Transaction

>**GET > /api/transactions/{transactionId} //Syntax**

**> GET > /api/transactions/TXN001**





### Update status

we need to update the Transaction ;

**> PUT > /api/transactions/{transactionId}/status?status={status} //Syntax**

**> PUT /api/transactions/TXN001/status?status=FAILED**

After Update we might be look like this

{

&#x20; "transactionId": "TXN001",

&#x20; "customerId": "CUST001",

&#x20; "amount": 500.00,

&#x20; "currency": "INR",

&#x20; "transactionType": "PAYMENT",

&#x20; "transactionStatus": "FAILED"

}



### Get customer transactions

We need to get Customer Transactions 

**> GET /api/customers/{customerId}/transactions**

**Example > GET /api/customers/CUST001/transactions**



## Testing expectations

Add at least four meaningful tests.

Your tests should cover more than just application startup.

You decide exactly which tests provide the best coverage.





### **AI Assistance**



**>AI assistance was used during the development of this project for:**


AI assistance was used during the development of this project through ChatGPT.

AI was used for:

- Understanding the starter project structure and requirements.
- Guidance on implementing the controller, service, repository, and entity layers.
- Debugging compilation and runtime errors.
- Designing and reviewing validation rules.
- Designing and reviewing status transition rules.
- Creating and reviewing unit tests.
- Reviewing API behavior and README documentation.

Significant AI suggestions included adding duplicate Transaction ID validation and corresponding test coverage. These suggestions were reviewed against the project requirements before being implemented.

During development, AI-generated suggestions were reviewed and corrected where necessary rather than being accepted blindly.

The final implementation, testing, and verification were performed by the me. The application was verified by running the automated test suite and manually testing the REST APIs.



Test Run Output

The automated test suite was executed using Maven.

Tests run: 23
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS


































