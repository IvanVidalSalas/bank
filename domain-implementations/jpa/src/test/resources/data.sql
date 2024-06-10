
USE BANK;

INSERT INTO CUSTOMER (CUSTOMER_ID, FIRST_NAME, LAST_NAME, ADDRESS, EMAIL) VALUES
    (1, 'Carla', 'Piñeyro', 'Carrer Llobregat', 'carlapiñeyro@gmail.com'),
    (2, 'Ariel', 'Espiro', 'Carrer Girona', 'arielespiro@gmail.com'),
    (3, 'Joel', 'Margenet', 'Carrer Taradell', 'joelmargenet@gmail.com'),
    (4, 'Pau', 'Font', 'Carrer Major', 'paufont@gmail.com');


INSERT INTO ACCOUNT (ACCOUNT_ID, CUSTOMER_ID, ACCOUNT_TYPE, BALANCE) VALUES
    (3, 1, 'Savings', 5000),
    (4, 2, 'Checking', 2500);


INSERT INTO TRANSACTION (TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE) VALUES
    (9, 1, 'Deposit', 1000, '2024-01-20'),
    (10, 2, 'Withdrawal', 500, '2024-02-05');


INSERT INTO WORKER (WORKER_ID, TRANSACTION_ID, FIRST_NAME, LAST_NAME) VALUES
    (5, 9, 'Jaume', 'Suñe'),
    (6, 10, 'Marisol', 'Aibar');


INSERT INTO LOAN (CUSTOMER_ID, LOAN_DATE, RETURN_DATE) VALUES
    (1, '2024-06-15', '2024-08-15'),
    (2, '2024-07-01', '2024-09-01');

