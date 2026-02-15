# Mobile Services (Contact, Task, Appointment) – Java + JUnit 5

This project implements three simple in-memory backend services for a mobile application (no database, no UI):

- **Contact Service**
- **Task Service**
- **Appointment Service**

Each domain includes a model class with strict validation and a corresponding service class that manages core operations.

---

## Features

### Contact Service
- Add contacts with a **unique contact ID**
- Update contact fields (except ID)
- Delete contacts by ID
- Validate:
  - `firstName`
  - `lastName`
  - `phone`
  - `address`

### Task Service
- Add tasks with a **unique task ID**
- Update task name and description
- Delete tasks by ID
- Validate:
  - `name`
  - `description`

### Appointment Service
- Add appointments with a **unique appointment ID**
- Delete appointments by ID
- Validate:
  - `appointmentDate`
  - `description`

---

## Validation Rules

### Contact
- `contactId`: required, max 10 characters, not updatable
- `firstName`: required, max 10 characters
- `lastName`: required, max 10 characters
- `phone`: required, exactly 10 digits
- `address`: required, max 30 characters

### Task
- `taskId`: required, max 10 characters, not updatable
- `name`: required, max 20 characters
- `description`: required, max 50 characters

### Appointment
- `appointmentId`: required, max 10 characters, not updatable
- `appointmentDate`: required, must not be in the past
- `description`: required, max 50 characters

---

## Error Handling

Each domain uses small custom runtime exceptions implemented as static nested classes:

- `ValidationException`
- `DuplicateIdException`
- `NotFoundException`

These exceptions are thrown when:
- Required fields are null or invalid
- Field lengths exceed limits
- Dates are invalid
- Duplicate IDs are added
- Delete/update operations target non-existing records

---

## Testing Strategy

All services include comprehensive unit testing using **JUnit 5 (Jupiter)**.

Testing includes:
- Positive tests (valid object creation and operations)
- Negative tests (null values, invalid formats)
- Boundary tests (exact max lengths)
- Duplicate ID testing
- Exception testing for invalid operations
- Not-found behavior testing

The project consistently achieves **80%+ test coverage** (typically much higher).

---

## Tech Stack

- Java
- JUnit 5 (Jupiter)
- IntelliJ IDEA

---

## Project Structure
```
ContactService/
Contact.java
ContactService.java
ContactTest.java
ContactServiceTest.java

TaskService/
Task.java
TaskService.java
TaskTest.java
TaskServiceTest.java

AppointmentService/
Appointment.java
AppointmentService.java
AppointmentTest.java
AppointmentServiceTest.java
```

> Note: Files intentionally use the default package to match typical course autograder expectations.

---

## How to Run Tests

Open the project in IntelliJ IDEA (or Eclipse) and run:

- `ContactTest`
- `ContactServiceTest`
- `TaskTest`
- `TaskServiceTest`
- `AppointmentTest`
- `AppointmentServiceTest`
