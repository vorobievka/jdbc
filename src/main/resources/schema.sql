create table if not exists customers (
    id serial primary key,
    name varchar(60) not null,
    surname varchar(60) not null,
    age smallint not null,
    phone_number varchar(10) not null
);
create table if not exists orders (
    id serial primary key,
    date date not null,
    customer_id int not null references public.customers(id),
    product_name varchar(60) not null,
    amount smallint not null
);