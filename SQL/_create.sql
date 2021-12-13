INSERT INTO books(isbn, title, genre, page_count, price,publisher_percentage,quantity, threshold, publisher_id
                ,warehouse_id)
    VALUES(?,?,?,?,?,?,?,?,?,1);
    
INSERT INTO authors(id, isbn) VALUES(?,?);

INSERT INTO adds(username, isbn) VALUES(?,?);

INSERT INTO author(first_name, middle_name, last_name, bio)
    VALUES(?,?,?,?);
    
INSERT INTO publisher(name,email,phone_number,number,street,postal_code, country)
    VALUES(?,?,?,?,?,?,?);

INSERT INTO provincial_area(postal_code,city,province)
    VALUES(?,?,?);

INSERT INTO street_area(number,street,postal_code,country)
    VALUES(?,?,?,?);

INSERT INTO bank_account(institution_number,transit_number,account_number,balance,id)
    VALUES(?,?,?,0.00,?);

INSERT INTO owner(username, password) VALUES(?,?);

INSERT INTO users(username, password) VALUES(?,?);

INSERT INTO selects(quantity, username, isbn) VALUES(?,?,?);

INSERT INTO book_order(shipping_state,username,warehouse_id,shipping_service_id)
    VALUES(?,?,1,1);

INSERT INTO orders(order_number,isbn,quantity) VALUES(?,?,?);

INSERT INTO order_addresses(order_number,shipping_number,shipping_street,shipping_postal_code
            ,shipping_country,billing_number,billing_street,billing_postal_code,billing_country
            ,current_number,current_street,current_postal_code,current_country)
    VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";