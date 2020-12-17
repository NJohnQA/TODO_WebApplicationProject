drop table if exists `shop` CASCADE ;
drop table if exists `item` CASCADE ;

create table if not exists shop(id bigint PRIMARY KEY AUTO_INCREMENT, category varchar(255) not null, store_name varchar(255) not null);
create table if not exists item(id bigint PRIMARY KEY AUTO_INCREMENT, category varchar(255) not null, name varchar(255) not null, quantity integer not null check (quantity<=100 AND quantity>=1), shop_id bigint);