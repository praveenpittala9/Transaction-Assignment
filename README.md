# Transaction Starter Project

This project implements the Customer Transactions exercise using Spring Boot, Spring Data JPA, and an H2 in-memory database.

## Before You Start

The first thing to do after cloning the repository is to run the test suite and verify that the project builds successfully.

### Linux / macOS

```bash
./mvnw clean test
```

### Windows

```bat
mvnw.cmd clean test
```

The project should compile successfully and the automated tests should pass before making changes to the application.

## Technologies

- Java 17
- Spring Boot 3.5.5
- Maven Wrapper
- Spring Web
- Spring Data JPA
- H2 embedded database
- JUnit / Spring Boot Test
- Hibernate ORM

## Problem Understanding

The application manages customer transactions.

It provides REST APIs to:

1. Create a transaction.
2. Retrieve a transaction by Transaction ID.
3. Update the status of an existing transaction.
4. Retrieve all transactions belonging to a customer.

The application uses Spring Boot, Spring Data JPA, and an H2 in-memory database.

## Project Architecture

The application follows a simple layered architecture:

```text
Client / Postman
       |
       v
REST Controller
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
H2 Database
```

### Controller Layer

The controller exposes REST API endpoints and receives HTTP requests.

### Service Layer

The service layer contains the business logic, validation rules, duplicate Transaction ID checking, transaction retrieval, status updates, and customer lookup operations.

### Repository Layer

The repository uses Spring Data JPA to communicate with the database.

### Entity Layer

The `Transaction` class represents a transaction stored in the database.

## Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.example.transactionstarter
│   │       ├── TransactionStarterApplication.java
│   │       ├── repository
│   │       │   └── TransactionRepository.java
│   │       ├── sample
│   │       │   └── SampleController.java
│   │       ├── service
│   │       │   └── TransactionService.java
│   │       └── transaction
│   │           └── Transaction.java
│   │
│   └── resources
│       └── application.yml
│
└── test
    └── java
        └── com.example.transactionstarter
            ├── TransactionStarterApplicationTests.java
            ├── sample
            │   └── SampleControllerTest.java
            └── service
                └── TransactionServiceTest.java
```

## Assumptions

- Transaction ID uniquely identifies a transaction.
- Customer ID can be associated with multiple transactions.
- Amount must be greater than zero.
- Currency, transaction type, and transaction status must be provided.
- Transaction status can be changed to any non-null, non-blank value because the assignment does not define a fixed status vocabulary or transition matrix.
- The H2 database is used as an in-memory database for this exercise, so data is not intended to persist after application shutdown.

## Transaction Fields

Every transaction contains:

- Transaction ID
- Customer ID
- Amount
- Currency
- Transaction Type
- Transaction Status

## Validation Rules

A transaction is considered valid when:

- Transaction ID is not null or blank.
- Customer ID is not null or blank.
- Amount is not null and must be greater than zero.
- Currency is not null or blank.
- Transaction type is not null or blank.
- Transaction status is not null or blank.

These validations are performed in the service layer before a transaction is saved.

Invalid input results in an `IllegalArgumentException`, and the transaction is not saved.

## Duplicate Transaction ID

Transaction ID is treated as the unique identifier of a transaction.

Before saving a new transaction, the service checks whether the Transaction ID already exists.

If the ID already exists, the request is rejected with:

```text
Transaction ID already exists
```

This prevents an existing transaction from being accidentally overwritten during creation.

## Status Transition Rules

The application allows a transaction status to be changed to any non-null, non-blank value.

There are no restrictions based on the previous status because the assignment does not define a fixed status vocabulary or transition matrix.

For example:

- `SUCCESS` → `FAILED` is allowed.
- `FAILED` → `SUCCESS` is allowed.
- `PENDING` → `SUCCESS` is allowed.

The update operation:

1. Validates the transaction ID.
2. Validates the new status.
3. Retrieves the existing transaction.
4. Updates its status.
5. Saves the transaction.
6. Returns the updated transaction.

If the transaction does not exist, the update is rejected with:

```text
Transaction not found
```

## API Endpoints

### 1. Create Transaction

**Method:** `POST`

**Endpoint:** `/api/transactions`

**Example request:**

```json
{
  "transactionId": "TXN001",
  "customerId": "CUST001",
  "amount": 500.00,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "transactionStatus": "SUCCESS"
}
```

The service validates the transaction before saving it.

If the Transaction ID already exists, the request is rejected.

### 2. Get Transaction

**Method:** `GET`

**Endpoint:** `/api/transactions/{transactionId}`

**Example:**

```text
GET /api/transactions/TXN001
```

The service searches for the transaction using its Transaction ID.

If the transaction does not exist, the API returns:

```text
Transaction not found
```

### 3. Update Transaction Status

**Method:** `PUT`

**Endpoint:** `/api/transactions/{transactionId}/status?status={status}`

**Example:**

```text
PUT /api/transactions/TXN001/status?status=FAILED
```

**Example response:**

```json
{
  "transactionId": "TXN001",
  "customerId": "CUST001",
  "amount": 500.00,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "transactionStatus": "FAILED"
}
```

### 4. Get Customer Transactions

**Method:** `GET`

**Endpoint:** `/api/customers/{customerId}/transactions`

**Example:**

```text
GET /api/customers/CUST001/transactions
```

The API returns all transactions associated with the specified Customer ID.

If the customer has no transactions, an empty list is returned.

## Error Handling

The application uses `IllegalArgumentException` for invalid business input and missing transactions.

The controller contains an exception handler that converts these exceptions into a `400 Bad Request` response.

Example:

```json
{
  "error": "Transaction not found"
}
```

Another example:

```json
{
  "error": "Amount must be greater than zero"
}
```

This provides a simple and consistent error response for the current exercise.

## Database

The project uses an H2 in-memory database.

The configured database URL is:

```text
jdbc:h2:mem:transactions
```

The database schema is created when the application starts and removed when the application stops.

The H2 console is available at:

```text
/h2-console
```

The application runs on:

```text
http://localhost:8080
```

Because the database is in memory, transaction data is lost when the application is stopped or restarted.

## Testing

The project contains automated tests covering the main transaction operations, validation rules, duplicate Transaction IDs, error scenarios, and customer transaction lookup.

The test suite includes:

- Successful transaction creation.
- Validation failures.
- Duplicate Transaction ID rejection.
- Retrieval of an existing transaction.
- Handling of a non-existent transaction.
- Transaction status updates.
- Customer transaction lookup.
- Multiple validation scenarios.
- Error handling.
- Spring application context startup.

Manual REST API testing was also performed using Postman while the application was running.

### Manual API Testing

The following scenarios were manually tested using Postman:

1. Create a transaction.
2. Reject a duplicate Transaction ID.
3. Retrieve an existing transaction.
4. Handle a non-existent transaction.
5. Update transaction status.
6. Verify the updated status using GET.
7. Retrieve transactions for a customer.
8. Validate missing Transaction ID.
9. Validate zero amount.
10. Validate negative amount.
11. Validate missing Customer ID.
12. Validate missing Currency.
13. Validate missing Transaction Type.
14. Validate missing Transaction Status.
15. Validate blank status during update.
16. Validate non-existent transaction during status update.
17. Create multiple transactions for the same customer.
18. Retrieve multiple transactions for the same customer.

## Final Automated Test Result

The final automated test suite was executed using Maven:

```text
Tests run: 23
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

## Known Limitations

- The database is in-memory, so transaction data is lost when the application stops.
- Status transitions are not restricted to a predefined state machine.
- Error responses currently use a simple `400 Bad Request` response for `IllegalArgumentException`.
- The current implementation does not use a persistent production database.

## Improvements With More Time

- Add a centralized exception handling mechanism using `@ControllerAdvice`.
- Introduce stronger validation using Bean Validation annotations where appropriate.
- Use enums for transaction types and statuses if the business requirements define fixed values.
- Add more detailed API documentation using OpenAPI/Swagger.
- Add integration tests covering the complete REST API and database interaction.
- Add persistent database support for production use.
- Add authentication and authorization for production APIs.
- Add structured logging and monitoring.
- Add pagination for customer transaction lookup if the transaction volume becomes large.

## AI Assistance

AI assistance was used during the development of this project through ChatGPT.

AI was used for:

- Understanding the starter project structure and requirements.
- Guidance on implementing the controller, service, repository, and entity layers.
- Debugging compilation and runtime errors.
- Designing and reviewing validation rules.
- Designing and reviewing status transition rules.
- Creating and reviewing unit tests.
- Reviewing API behavior and README documentation.

A significant AI suggestion was adding duplicate Transaction ID validation and corresponding test coverage. This suggestion was reviewed against the project requirements before being implemented.

During development, AI-generated suggestions were reviewed and corrected where necessary rather than being accepted blindly.

The final implementation, testing, and verification were performed by me. The application was verified by running the automated test suite and manually testing the REST APIs.

## Student Checklist

The required Student Checklist has been completed in `STUDENT_CHECKLIST.md`.

The completed checklist covers:

- Running the starter project.
- Running the Maven test suite.
- Implementing all four required operations.
- Defining validation rules.
- Defining status transition rules.
- Adding meaningful tests.
- Adding error handling.
- Running the complete test suite.
- Disclosing AI assistance.

## Conclusion

The Customer Transactions exercise has been implemented using a layered Spring Boot architecture.

The application provides:

```text
Create Transaction
       ↓
Get Transaction
       ↓
Update Transaction Status
       ↓
Get Customer Transactions
```

The implementation includes validation, duplicate Transaction ID protection, error handling, automated tests, and manual REST API verification.

The final automated test execution completed successfully with:

```text
23 tests
0 failures
0 errors
0 skipped

BUILD SUCCESS
```
