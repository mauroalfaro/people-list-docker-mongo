CREATE TABLE Customer (
    Id varchar(11),
    CUSTOMER_NAME varchar(255),
    CUSTOMER_SURNAME varchar(255),
    LOYALTY_ID varchar(255),
    CUSTOMER_ADDRESS varchar(255),
    CUSTOMER_PHONE varchar(255),
    CUSTOMER_EMAIL varchar(255)
);

CREATE TABLE Employee (
    Id varchar(11),
    EMPLOYEE_NAME varchar(255),
    EMPLOYEE_SURNAME varchar(255),
    EMPLOYEE_ADDRESS varchar(255),
    EMPLOYEE_PHONE varchar(255),
    EMPLOYEE_EMAIL varchar(255),
    DATE_HIRED varchar(255),
    ACTIVE_EMPLOYEE tinyint(1)
);

CREATE TABLE Store (
    Id varchar(11),
    STORE_NAME varchar(255),
    STORE_ADDRESS varchar(255)
);