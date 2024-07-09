drop table users;
create table users (
	user_id integer primary key check(user_id >= 100000 and user_id <= 999999),
	first_name varchar(20) not null,
	last_name varchar(30) not null,
	email varchar(50) not null,
	password varchar(60) not null
);

drop table accounts;
create table accounts(
	user_id integer references users(user_id),
	account_type varchar(20) not null, --TODO: change to type enum
	account_balance numeric not null check(account_balance >= 0)
);

insert into users (user_id, first_name, last_name, email, password)
	values(123123, 'John', 'Doe', 'jdoe2@email.net', 'R3v@2tur');

insert into accounts (user_id, account_type, account_balance)
    values (123123, 'CHECKING', 23.24);

select * from users;
select * from accounts;