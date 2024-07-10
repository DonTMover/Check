-- DDL
create table product
(
    id                bigserial
        primary key,
    description       varchar(50) not null,
    price             numeric     not null,
    quantity_in_stock integer     not null,
    wholesale_product boolean     not null
);
alter table product
    owner to postgres;

create table discount_card
(
    id     bigserial
        primary key,
    number integer  not null,
    amount smallint not null
);

alter table discount_card
    owner to casaos;

-- DML
select * from discount_card;
select * from product;