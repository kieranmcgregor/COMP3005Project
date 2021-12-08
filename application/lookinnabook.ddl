CREATE DATABASE lookinnabook;

CREATE TABLE IF NOT EXISTS provincial_area (
    postal_code VARCHAR(6) PRIMARY KEY,
    city VARCHAR(255),
    province VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS street_area (
    number VARCHAR(20),
    street VARCHAR(255),
    postal_code VARCHAR(6) REFERENCES provincial_area(postal_code) ON DELETE CASCADE,
    country VARCHAR(255),
    PRIMARY KEY(number, street, postal_code, country)
);

CREATE TABLE IF NOT EXISTS publisher (
    ID SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(12),
    number VARCHAR(20),
    street VARCHAR(255),
    postal_code VARCHAR(6),
    country VARCHAR(255),
    FOREIGN KEY(number, street, postal_code, country)
        REFERENCES street_area(number, street, postal_code, country)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bank_account (
    institution_number NUMERIC(3,0),
    transit_number NUMERIC(5,0),
    account_number NUMERIC(12,0),
    balance NUMERIC(12,2),
    ID INTEGER REFERENCES publisher(ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS owner (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    first_name VARCHAR(255),
    middle_name VARCHAR(255),
    last_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    first_name VARCHAR(255),
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    number VARCHAR(20),
    street VARCHAR(255),
    postal_code VARCHAR(6),
    country VARCHAR(255),
    FOREIGN KEY(number, street, postal_code, country)
        REFERENCES street_area(number, street, postal_code, country)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS author (
    ID SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    bio VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS shipping_service (
    ID SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS warehouse (
    ID SERIAL PRIMARY KEY,
    number VARCHAR(20),
    street VARCHAR(255),
    postal_code VARCHAR(6),
    country VARCHAR(255),
    FOREIGN KEY(number, street, postal_code, country)
        REFERENCES street_area(number, street, postal_code, country)
        ON DELETE CASCADE
);

CREATE TABLE books (
    ISBN VARCHAR(15) PRIMARY KEY,
    title VARCHAR(255),
    genre VARCHAR(255),
    page_count INTEGER,
    price NUMERIC(12,2),
    publisher_percentage NUMERIC(2,2),
    quantity INTEGER,
    threshold INTEGER,
    publisher_id INTEGER REFERENCES publisher(ID) ON DELETE RESTRICT,
    warehouse_id INTEGER REFERENCES warehouse(ID) ON DELETE RESTRICT
);

CREATE TABLE "order" (
    order_number SERIAL PRIMARY KEY,
    shipping_state VARCHAR(255),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    username VARCHAR(255) REFERENCES users(username) ON DELETE RESTRICT,
    warehouse_id INTEGER REFERENCES warehouse(ID) ON DELETE RESTRICT,
    shipping_service_id INTEGER REFERENCES shipping_service(ID) ON DELETE RESTRICT
);

CREATE TABLE order_addresses (
    order_number INTEGER REFERENCES "order"(order_number) ON DELETE CASCADE,
    shipping_number VARCHAR(20),
    shipping_street VARCHAR(255),
    shipping_postal_code VARCHAR(6),
    shipping_country VARCHAR(255),
    billing_number VARCHAR(20),
    billing_street VARCHAR(255),
    billing_postal_code VARCHAR(6),
    billing_country VARCHAR(255),
    current_number VARCHAR(20),
    current_street VARCHAR(255),
    current_postal_code VARCHAR(6),
    current_country VARCHAR(255),
    PRIMARY KEY (order_number
                , shipping_number, shipping_street, shipping_postal_code, shipping_country
                , billing_number, billing_street, billing_postal_code, billing_country
                , current_number, current_street, current_postal_code, current_country),

    FOREIGN KEY(shipping_number, shipping_street, shipping_postal_code, shipping_country)
        REFERENCES street_area(number, street, postal_code, country)
        ON DELETE CASCADE,
    
    FOREIGN KEY(billing_number, billing_street, billing_postal_code, billing_country)
        REFERENCES street_area(number, street, postal_code, country)
        ON DELETE CASCADE,
    
    FOREIGN KEY(current_number, current_street, current_postal_code, current_country)
        REFERENCES street_area(number, street, postal_code, country)
        ON DELETE CASCADE
);

CREATE TABLE adds (
    username VARCHAR(255) REFERENCES owner(username) ON DELETE CASCADE,
    ISBN VARCHAR(15) REFERENCES books(ISBN) ON DELETE CASCADE
);

CREATE TABLE authors (
    ID INTEGER REFERENCES author(ID) ON DELETE CASCADE,
    ISBN VARCHAR(15) REFERENCES books(ISBN) ON DELETE CASCADE
);

CREATE TABLE selects (
    username VARCHAR(255) REFERENCES users(username) ON DELETE CASCADE,
    ISBN VARCHAR(15) REFERENCES books(ISBN) ON DELETE CASCADE,
    quantity INTEGER,
    PRIMARY KEY(username, ISBN)
);

CREATE TABLE orders (
    order_number INTEGER REFERENCES "order"(order_number) ON DELETE CASCADE,
    ISBN VARCHAR(15) REFERENCES books(ISBN) ON DELETE CASCADE,
    PRIMARY KEY(order_number, ISBN)
);