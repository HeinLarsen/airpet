# airpet

to run the backend create a application.properties in the resources folder.
it should look something like this:
```` json
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost/*DBNAME*?autoReconnect=true&useSSL=false
spring.datasource.username=*USERNAME*
spring.datasource.password=*PASSWORD*
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#spring.jpa.show-sql: true
````

to run the frontend create a conf.json file with an api key from google for their google maps api.
```json
{
  "key": "value"
}
```
Start the frontend with npm run serve


wip db query for tables, views and data - does not include pictures
```sql
create database if not exists airpets;
USE airpets;

create table if not exists users (
    ID int NOT NULL AUTO_INCREMENT,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    street varchar(255),
    street_number int,
    city varchar(255),
    zip int,
    primary key (ID));

create table if not exists pets (
    ID int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    breed int,
    species int,
    owner int,
    age int,
    longitude float,
    latitude float,
    description text,
    primary key (ID)
);

create table if not exists species (
    ID int NOT null AUTO_INCREMENT,
    species varchar(255),
    primary key (ID)
);

create table if not exists breeds (
    ID int NOT null AUTO_INCREMENT,
    species int NOT null,
    breed varchar(255),
    primary key (ID)
);

create table if not exists bookings (
    ID int NOT NULL AUTO_INCREMENT,
    pet int,
    bookee int,
    start varchar(45),
    end varchar(45),
    primary key (ID)
);

create table if not exists reviews (
    ID int not null auto_increment,
    reviewer int,
    pet int,
    description text,
    rating float,
    date varchar(45),
    primary key (ID)
);

create view reviews_view as
select reviews.*, concat(users.first_name, ' ', users.last_name) as full_name
from reviews
inner join users on reviews.reviewer = users.ID;

CREATE VIEW pets_view AS
select pets.*, concat(users.first_name, ' ', users.last_name) as owner_name,
(select count(*) from reviews where reviews.pet = pets.ID) as rating_count,
(select avg(rating) from reviews where reviews.pet = pets.ID) as rating
from pets
inner join users on users.ID = pets.owner;

create view bookings_view as
select bookings.*, pets.name as pet_name, pets.description as pet_description
from bookings
inner join pets on bookings.pet = pets.ID;

USE airpets;

insert ignore into users
    (email, first_name, last_name, password, street, street_number, city, zip)
values
    ("anders@gmail.com", "Anders", "larsen", "password", "Slotsarkaderne", 225, "Hillerød", 3400),
    ("tobias@gmail.com", "Tobias", "Tonndorff", "password", "Anker Engelunds Vej", 1, "Lyngby", 2800 );

insert ignore into species
    (species)
values
    ("dog"), ("cat"),("bunny"),("snake");

insert ignore into breeds
    (species, breed)
values
    (1, "Golden Retriver"),
    (1, "Fox Terrier"),
    (1, "Daschhound"),
    (1, "Siberian Husky"),
    (2, "Maine Coon"),
    (2, "Ragdoll"),
    (2, "Siamese"),
    (2, "Burmesse");

insert ignore into pets
    (name, species, breed, owner, age, description, latitude, longitude)
values
    ("Jerry", 1, 2, 1, 7, "Good boy", 55.791188, 12.161147715673906),
    ("Kevin", 1, 1, 1, 4, "Likes long walks on the beach", 55.72735988853124,  12.421350222567543),
    ("Tom", 2, 8, 2, 7, "Sleeps a lot", 56.06122627940592, 12.775782443892037);
    
insert ignore into bookings
    (pet, bookee, start, end)
values
    (1, 1, "2022-10-09", "2022-10-12"),
    (3, 1, "2022-11-23", "2022-11-24"),
    (2, 1, "2022-12-01", "2022-12-15")
 ```
