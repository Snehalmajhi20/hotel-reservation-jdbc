CREATE database hotel_reservation_DB;
SHOW DATABASES;
use hotel_reservation_DB;

CREATE TABLE reservation(
guest_id INT auto_increment primary key,
guest_name varchar(100) not null,
contact_number varchar(11) not null,
room_number int not null,
reservation_date timestamp default current_timestamp);

show tables;
desc reservation;
