CREATE DATABASE lookinnabook;

CREATE TABLE IF NOT EXISTS provincial_area (
    postal_code VARCHAR(6) PRIMARY KEY,
    city VARCHAR(255) NOT NULL,
    province VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS street_area (
    number VARCHAR(20) NOT NULL,
    street VARCHAR(255) NOT NULL,
    postal_code VARCHAR(6) REFERENCES provincial_area(postal_code) ON DELETE CASCADE NOT NULL,
    country VARCHAR(255) NOT NULL,
    PRIMARY KEY(number, street, postal_code, country)
);

CREATE TABLE IF NOT EXISTS publisher (
    ID SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone_number VARCHAR(15),
    number VARCHAR(20) NOT NULL,
    street VARCHAR(255) NOT NULL,
    postal_code VARCHAR(6) NOT NULL,
    country VARCHAR(255) NOT NULL,
    FOREIGN KEY(number, street, postal_code, country)
        REFERENCES street_area(number, street, postal_code, country)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bank_account (
    institution_number NUMERIC(3,0),
    transit_number NUMERIC(5,0),
    account_number NUMERIC(12,0),
    balance NUMERIC(12,2),
    PRIMARY KEY (institution_number, transit_number, account_number),
    ID INTEGER REFERENCES publisher(ID) ON DELETE CASCADE NOT NULL
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
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    bio VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS shipping_service (
    ID SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS warehouse (
    ID SERIAL PRIMARY KEY,
    number VARCHAR(20) NOT NULL,
    street VARCHAR(255) NOT NULL,
    postal_code VARCHAR(6) NOT NULL,
    country VARCHAR(255) NOT NULL,
    FOREIGN KEY(number, street, postal_code, country)
        REFERENCES street_area(number, street, postal_code, country)
        ON DELETE CASCADE
);

CREATE TABLE books (
    ISBN VARCHAR(17) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    page_count INTEGER,
    price NUMERIC(12,2) NOT NULL,
    publisher_percentage NUMERIC(2,2) NOT NULL,
    quantity INTEGER NOT NULL,
    threshold INTEGER,
    publisher_id INTEGER REFERENCES publisher(ID) ON DELETE RESTRICT NOT NULL,
    warehouse_id INTEGER REFERENCES warehouse(ID) ON DELETE RESTRICT NOT NULL
);

CREATE TABLE "order" (
    order_number SERIAL PRIMARY KEY,
    shipping_state VARCHAR(255),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    username VARCHAR(255) REFERENCES users(username) ON DELETE RESTRICT NOT NULL,
    warehouse_id INTEGER REFERENCES warehouse(ID) ON DELETE RESTRICT NOT NULL,
    shipping_service_id INTEGER REFERENCES shipping_service(ID) ON DELETE RESTRICT NOT NULL
);

CREATE TABLE order_addresses (
    order_number INTEGER REFERENCES "order"(order_number) ON DELETE CASCADE NOT NULL,
    shipping_number VARCHAR(20) NOT NULL,
    shipping_street VARCHAR(255) NOT NULL,
    shipping_postal_code VARCHAR(6) NOT NULL,
    shipping_country VARCHAR(255) NOT NULL,
    billing_number VARCHAR(20) NOT NULL,
    billing_street VARCHAR(255) NOT NULL,
    billing_postal_code VARCHAR(6) NOT NULL,
    billing_country VARCHAR(255) NOT NULL,
    current_number VARCHAR(20) NOT NULL,
    current_street VARCHAR(255) NOT NULL,
    current_postal_code VARCHAR(6) NOT NULL,
    current_country VARCHAR(255) NOT NULL,
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
    username VARCHAR(255) REFERENCES owner(username) ON DELETE CASCADE NOT NULL,
    ISBN VARCHAR(17) REFERENCES books(ISBN) ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (username, ISBN)
);

CREATE TABLE authors (
    ID INTEGER REFERENCES author(ID) ON DELETE CASCADE NOT NULL,
    ISBN VARCHAR(17) REFERENCES books(ISBN) ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (ID, ISBN)
);

CREATE TABLE selects (
    username VARCHAR(255) REFERENCES users(username) ON DELETE CASCADE NOT NULL,
    ISBN VARCHAR(17) REFERENCES books(ISBN) ON DELETE CASCADE NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY(username, ISBN)
);

CREATE TABLE orders (
    order_number INTEGER REFERENCES "order"(order_number) ON DELETE CASCADE NOT NULL,
    ISBN VARCHAR(17) REFERENCES books(ISBN) ON DELETE CASCADE NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY(order_number, ISBN)
);

INSERT INTO provincial_area(postal_code, city, province)
    VALUES ('K4B1P6', 'Ottawa', 'Ontario');

INSERT INTO street_area(number, street, postal_code, country)
    VALUES (5225, 'Boundary Rd.', 'K4B1P6', 'Canada');

INSERT INTO warehouse(number, street, postal_code, country)
    VALUES (5225, 'Boundary Rd.', 'K4B1P6', 'Canada');

INSERT INTO shipping_service(name) VALUES ('Canada Post');

CREATE OR REPLACE FUNCTION order_more_books()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL
    AS
$$
    BEGIN
        IF NEW.quantity < NEW.threshold THEN
        UPDATE books SET quantity = quantity +
            (SELECT COUNT(*) FROM "order" NATURAL JOIN orders WHERE order_date < NOW() - INTERVAL '30 days' AND books.ISBN == orders.ISBN);
        END IF;

        RETURN NEW;
    END;
$$;

CREATE TRIGGER check_books_levels
    AFTER UPDATE
    ON books
    FOR EACH STATEMENT
    EXECUTE PROCEDURE order_more_books();