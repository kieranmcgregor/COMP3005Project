-- Retrieve certain data for all books
-- Used in user search
SELECT books.isbn,title,genre,page_count,price,first_name,middle_name,last_name,name
    FROM (books INNER JOIN (publisher NATURAL JOIN provincial_area) ON books.publisher_id = publisher.id) 
                INNER JOIN (authors NATURAL JOIN author) ON books.isbn = authors.isbn;

-- Retrieve book based on user search criterai
SELECT books.isbn,title,genre,page_count,price,first_name,middle_name,last_name,name
    FROM (books INNER JOIN (publisher NATURAL JOIN provincial_area) ON books.publisher_id = publisher.id) 
                INNER JOIN (authors NATURAL JOIN author) ON books.isbn = authors.isbn
    WHERE books.isbn=? AND title=? AND genre=? AND page_count=? AND price=? AND first_name=? AND middle_name=?
            AND last_name=? AND name=? AND email=? AND phone_number=? AND number=? AND street=? AND city=?
            AND province=? AND postal_code=? AND country=?;

-- Retrieve all books of a given isbn 
SELECT * FROM books WHERE ISBN=?;

-- Retrieve book publisher id for a given isbn
SELECT publisher_id FROM books WHERE isbn=?;

-- Retrieve book price and publisher commission for a given isbn
SELECT price,publisher_percentage FROM books WHERE isbn=?;

-- Retrieve author authors book link and sort by author id
SELECT * FROM authors ORDER BY id;
-- Retrieve author authors book link for a given id and isbn
SELECT * FROM authors WHERE ID=? AND isbn=?;

-- Retrieve owner adds book link for a given username and isbn
SELECT * FROM adds WHERE username=? AND isbn=?;

-- Retrieve author for a given id
SELECT * FROM author WHERE ID=?;

-- Retrieve publishers and sort by id
SELECT * FROM publisher ORDER BY id;
-- Retrieve publisher for a given id
SELECT * FROM publisher WHERE ID=?;

-- Retrieve provincial_area for a given postal_code
SELECT * FROM provincial_area WHERE postal_code=?;

-- Retrieve street_area for a given street number, name, postal_code and country
SELECT * FROM street_area WHERE number=? AND street=? AND postal_code=? AND country=?;

-- Retrieve bank_account for a given institution_number, transit_number and account_number
SELECT * FROM bank_account WHERE institution_number=? AND transit_number=? AND account_number=?;

-- Retrieve owner with a given username
SELECT * FROM owner WHERE username=?;

-- Retrieve user with a given username
SELECT * FROM users WHERE username=?;

-- Retrieve books and associated data in users basket
SELECT books.isbn,selects.quantity,title,genre,page_count,price,first_name,middle_name,last_name,name
    FROM ((books INNER JOIN selects ON books.isbn=selects.isbn)
            INNER JOIN (publisher NATURAL JOIN provincial_area) ON books.publisher_id = publisher.id)
            INNER JOIN (authors NATURAL JOIN author) ON books.isbn = authors.isbn
    WHERE selects.username=?;

-- Retrieve user selects books link for a given user
SELECT * FROM selects WHERE username=?;
-- Retrieve user selects books link for a given user and isbn
SELECT * FROM selects WHERE username=? AND isbn=?;

-- Retrieve book_order and sort by number
SELECT * FROM book_order ORDER BY order_number;
-- Retrieve book_order for a given order number
SELECT * FROM book_order WHERE order_number=?;

-- Retrieve warehouse street info
SELECT number,street,postal_code,country FROM warehouse WHERE id=?;

-- Retrieve shipping service street info
SELECT number,street,postal_code,country FROM shipping_service WHERE id=?;

-- Retrieve all book orders for a given user
-- Used to display users order history
SELECT order_number,shipping_state,current_number,current_street,provincial_area.city,provincial_area.province,current_postal_code,current_country
    FROM ((book_order NATURAL JOIN orders)
            NATURAL JOIN order_addresses)
            INNER JOIN provincial_area ON provincial_area.postal_code = current_postal_code
    WHERE book_order.username=?;

-- Retrieve total sales stats
SELECT SUM(total) FROM daily_sales_stats
    WHERE order_date >= ? AND order_date <= ?;
    
-- Retrieve sales stats for book genre
SELECT genre, SUM(total) FROM daily_sales_stats
    WHERE order_date >= ? AND order_date <= ?
    GROUP BY genre;

-- Retrieve sales stats for author
SELECT author_id,first_name,middle_name,last_name,total_earnings
    FROM ((SELECT author_id,SUM(total) AS total_earnings
            FROM daily_sales_stats
            WHERE order_date >= ? AND order_date <= ?
            GROUP BY author_id) AS author_id_total
            INNER JOIN author ON author_id = author.id);