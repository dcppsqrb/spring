ALTER TABLE orders
  ADD COLUMN IF NOT EXISTS version integer;