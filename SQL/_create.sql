-- Insert new book into books table
INSERT INTO books(isbn, title, genre, page_count, price,publisher_percentage,quantity, threshold, publisher_id
                ,warehouse_id)
    VALUES(?,?,?,?,?,?,?,?,?,1);

-- Insert new author authors book link into authors table
INSERT INTO authors(id, isbn) VALUES(?,?);

-- Insert new owner adds book link into adds table
INSERT INTO adds(username, isbn) VALUES(?,?);

-- Insert new author into author table
INSERT INTO author(first_name, middle_name, last_name, bio)
    VALUES(?,?,?,?);
    
-- Insert new publisher into publisher table
INSERT INTO publisher(name,email,phone_number,number,street,postal_code, country)
    VALUES(?,?,?,?,?,?,?);

-- Insert new provincial area into provincial_area table
INSERT INTO provincial_area(postal_code,city,province)
    VALUES(?,?,?);

-- Insert new street area into street_area table
INSERT INTO street_area(number,street,postal_code,country)
    VALUES(?,?,?,?);

-- Insert new bank account into bank_account table
INSERT INTO bank_account(institution_number,transit_number,account_number,balance,id)
    VALUES(?,?,?,0.00,?);

-- Insert new owner into owner table
INSERT INTO owner(username, password) VALUES(?,?);

-- Insert new user into owner users
INSERT INTO users(username, password) VALUES(?,?);

-- Insert new user selects book (basket) into owner selects
INSERT INTO selects(quantity, username, isbn) VALUES(?,?,?);

-- Insert new order into book_order table
INSERT INTO book_order(shipping_state,username,warehouse_id,shipping_service_id)
    VALUES(?,?,1,1);

-- Insert new user orders book link into orders table
INSERT INTO orders(order_number,isbn,quantity) VALUES(?,?,?);

-- Insert new order addresses into order_addresses table
INSERT INTO order_addresses(order_number,shipping_number,shipping_street,shipping_postal_code
            ,shipping_country,billing_number,billing_street,billing_postal_code,billing_country
            ,current_number,current_street,current_postal_code,current_country)
    VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";