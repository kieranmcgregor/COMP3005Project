-- Update author with new data for given author id
UPDATE author SET first_name=? AND middle_name=? AND last_name=? AND bio=? WHERE ID=?;

-- Increase book quantity for given isbn
UPDATE books SET quantity = quantity + ? WHERE isbn = ?;

-- Decrease books quantity for given isbn
UPDATE books SET quantity = quantity - ? WHERE isbn = ?;

-- Increase books quantity in a users basket for given isbn
UPDATE selects SET quantity = quantity + ? WHERE username=? AND isbn = ?;

-- Update books quantity in a users basket to a given quantity for a given isbn
UPDATE selects SET quantity=? WHERE username=? AND isbn =?;

-- Increase publisher's bank balance by a given quantity
UPDATE bank_account SET balance = balance + ? WHERE id=?;