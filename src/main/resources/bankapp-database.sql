--delete tables if they exist
drop table accounts;
drop table users;

--(re)create tables
create table users (
	user_id     integer primary key check(user_id >= 100000 and user_id <= 999999),
	first_name  varchar(20) not null,
	last_name   varchar(30) not null,
	email       varchar(50) unique not null,
	password    varchar(60) not null    --TODO: Encrypt via API later
);

--TODO: Figure out how to pass enum to sql query using preparedStatement
--create type account_enum as enum ('CHECKING', 'SAVINGS');

create table accounts(
	user_id         integer references users(user_id) on delete cascade,
	account_type    varchar(10) not null,
	account_balance numeric not null check(account_balance >= 0)
);

--multi-insert
insert into users (user_id, first_name, last_name, email, password)
    values(898900, 'Jane', 'Doe', 'jdoe23@email.net', 'R3v@200sr'),
          (123123, 'John', 'Doe', 'jdoe2@email.net', 'R3v@2tur');

insert into accounts (user_id, account_type, account_balance)
    values (898900, 'CHECKING', 23.24),
           (123123, 'SAVINGS', 548.11),
           (123123, 'CHECKING', 260.88);
  
-- Function for new user insert
create or replace function generate_user_id()
returns trigger
language plpgsql
as $$
declare new_id integer;
begin
	select random()*1000000 into new_id;
	new.user_id := new_id;
	return new;
end; 
$$;


-- Trigger for new user insert
create or replace trigger assign_user_id 
before insert 
on users
for each row
execute function generate_user_id();

insert into users (first_name, last_name, email, password) values('Remmy', 'Doe', 'remmy2@email.net', 'R3v@200sr');

--show tables
select * from users;
select * from accounts;


