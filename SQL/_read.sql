SELECT books.isbn,title,genre,page_count,price,first_name,middle_name,last_name,name
    FROM (books INNER JOIN (publisher NATURAL JOIN provincial_area) ON books.publisher_id = publisher.id) 
                INNER JOIN (authors NATURAL JOIN author) ON books.isbn = authors.isbn;

SELECT books.isbn,title,genre,page_count,price,first_name,middle_name,last_name,name
    FROM (books INNER JOIN (publisher NATURAL JOIN provincial_area) ON books.publisher_id = publisher.id) 
                INNER JOIN (authors NATURAL JOIN author) ON books.isbn = authors.isbn
    WHERE books.isbn=? AND title=? AND genre=? AND page_count=? AND price=? AND first_name=? AND middle_name=?
            AND last_name=? AND name=? AND email=? AND phone_number=? AND number=? AND street=? AND city=?
            AND province=? AND postal_code=? AND country=?;

SELECT * FROM books WHERE ISBN=?;

SELECT publisher_id FROM books WHERE isbn=?;

SELECT price,publisher_percentage FROM books WHERE isbn=?;

SELECT * FROM authors ORDER BY id;
SELECT * FROM authors WHERE ID=? AND isbn=?;

SELECT * FROM adds WHERE username=? AND isbn=?;

SELECT * FROM author WHERE ID=?;

SELECT * FROM publisher ORDER BY id;
SELECT * FROM publisher WHERE ID=?;

SELECT * FROM provincial_area WHERE postal_code=?;

SELECT * FROM street_area WHERE number=? AND street=? AND postal_code=? AND country=?;

SELECT * FROM bank_account WHERE institution_number=? AND transit_number=? AND account_number=?;

SELECT * FROM owner WHERE username=?;

SELECT * FROM users WHERE username=?;

SELECT books.isbn,selects.quantity,title,genre,page_count,price,first_name,middle_name,last_name,name
    FROM ((books INNER JOIN selects ON books.isbn=selects.isbn)
            INNER JOIN (publisher NATURAL JOIN provincial_area) ON books.publisher_id = publisher.id)
            INNER JOIN (authors NATURAL JOIN author) ON books.isbn = authors.isbn
    WHERE selects.username=?;
    
SELECT * FROM selects WHERE username=?;
SELECT * FROM selects WHERE username=? AND isbn=?;

SELECT * FROM book_order ORDER BY order_number;
SELECT * FROM book_order WHERE order_number=?;

SELECT number,street,postal_code,country FROM warehouse WHERE id=?;

SELECT number,street,postal_code,country FROM shipping_service WHERE id=?;

SELECT order_number,shipping_state,current_number,current_street,provincial_area.city,provincial_area.province,current_postal_code,current_country
    FROM ((book_order NATURAL JOIN orders)
            NATURAL JOIN order_addresses)
            INNER JOIN provincial_area ON provincial_area.postal_code = current_postal_code
    WHERE book_order.username=?;

SELECT SUM(total) FROM daily_sales_stats
    WHERE order_date >= ? AND order_date <= ?;
    
SELECT genre, SUM(total) FROM daily_sales_stats
    WHERE order_date >= ? AND order_date <= ?
    GROUP BY genre;

SELECT author_id,first_name,middle_name,last_name,total_earnings
    FROM ((SELECT author_id,SUM(total) AS total_earnings
            FROM daily_sales_stats
            WHERE order_date >= ? AND order_date <= ?
            GROUP BY author_id) AS author_id_total
            INNER JOIN author ON author_id = author.id);