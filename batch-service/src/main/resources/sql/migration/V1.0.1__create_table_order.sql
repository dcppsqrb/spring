CREATE TABLE IF NOT EXISTS orders (
	id bigint NOT NULL,
	order_ref varchar(100) NOT NULL,
	amount decimal(19, 2) NULL,
	order_date timestamp NOT NULL,
	note varchar(1000) NULL,
	CONSTRAINT orders_pk PRIMARY KEY (id)
);