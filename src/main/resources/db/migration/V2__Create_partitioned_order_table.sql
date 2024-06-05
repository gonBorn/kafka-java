CREATE TABLE orders
(
    order_id    varchar PRIMARY KEY,
    customer_id integer,
    order_date  varchar
);
--     PARTITION BY RANGE (order_date);

-- CREATE TABLE orders_2020 PARTITION OF orders
--     FOR VALUES FROM
-- (
--     '2020-01-01'
-- ) TO
-- (
--     '2021-01-01'
-- );
--
-- CREATE TABLE orders_2021 PARTITION OF orders
--     FOR VALUES FROM
-- (
--     '2021-01-01'
-- ) TO
-- (
--     '2022-01-01'
-- );