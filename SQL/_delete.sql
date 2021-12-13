-- Delete book of given isbn from books table
DELETE FROM books WHERE isbn=?;

-- Delete user selects book link of given user and isbn from selects table
-- Effectively, delete a book from the user's basket
DELETE FROM selects WHERE username=? AND isbn=?;