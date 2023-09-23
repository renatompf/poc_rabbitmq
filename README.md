# poc_rabbitmq

This is a [Spring Boot](https://spring.io/) project that implements a calculator service using [RabbitMQ](https://www.rabbitmq.com/) for message-based communication. <br> The project is split into two modules: one that accepts requests, and the other that performs calculations after reading messages from the queue.

## Modules

### Rest Module
This module is responsible for receiving calculation requests through RESTful endpoints and publishing them to a RabbitMQ queue for processing.

#### Endpoints

- **Sum**: Add two numbers.
    - Endpoint: `POST /sum`
    - Example:
      ```json
      {
          "firstNumber": 5.0,
          "secondNumber": 3.0
      }
      ```

- **Subtraction**: Subtract one number from another.
    - Endpoint: `POST /subtraction`
    - Example:
      ```json
      {
          "firstNumber": 10.0,
          "secondNumber": 4.0
      }
      ```

- **Division**: Divide one number by another.
    - Endpoint: `POST /division`
    - Example:
      ```json
      {
          "firstNumber": 12.0,
          "secondNumber": 3.0
      }
      ```

- **Multiplication**: Multiply two numbers.
    - Endpoint: `POST /multiplication`
    - Example:
      ```json
      {
          "firstNumber": 7.0,
          "secondNumber": 2.0
      }
      ```

### Calculation Module
This module listens for incoming calculation messages from the RabbitMQ queue, performs the requested calculations, and sends back the results.

## Getting Started

1. Clone the repository.
   ```shell
   git clone https://github.com/renatompf/poc_rabbitmq.git
   ```

2. Start all the containers by making.
   ```shell
   make all
   ```
3. Use the provided endpoints to perform calculations. For example, send a POST request to http://localhost:8080/sum with the request body as shown in the examples above.

