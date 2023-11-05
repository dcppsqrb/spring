ALTER TABLE orders
  ADD COLUMN IF NOT EXISTS created_by varchar(255);
ALTER TABLE orders
  ADD COLUMN IF NOT EXISTS last_modified_by varchar(255);
ALTER TABLE orders
  ADD COLUMN IF NOT EXISTS created_date timestamp ;  
ALTER TABLE orders
  ADD COLUMN IF NOT EXISTS last_modified_date timestamp ;    