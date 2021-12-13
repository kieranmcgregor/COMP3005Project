UPDATE author SET first_name=? AND middle_name=? AND last_name=? AND bio=? WHERE ID=?;

UPDATE books SET quantity = quantity + ? WHERE isbn = ?;

UPDATE books SET quantity = quantity - ? WHERE isbn = ?;

UPDATE selects SET quantity = quantity + ? WHERE username=? AND isbn = ?;

UPDATE selects SET quantity=? WHERE username=? AND isbn =?;

UPDATE bank_account SET balance = balance + ? WHERE id=?";