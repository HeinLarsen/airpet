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
    ("andersheinlarsen@gmail.com", "Anders", "larsen", "password", "Campusvej", 151, "Hillerød", 3400),
    ("Tobias@Tonndorff.dk", "Tobias", "Tonndorff", "password", "Anker Engelunds Vej", 1, "Lyngby", 2800 ),
    ("Benson@gmail.com", "Benson", "Stitt", "password", "Nørrebro Bycenter", 24, "København NV", 2400),
    ("Roswald@gmail.com", "Roswald", "Luster", "password", "Kildervangsvej", 20, "Viby", 4130),
    ("Donella@live.dk", "Donella", "Dudgeon", "password", "Christian Winthers Vej", 30, "Næstved", 4700),
    ("Johnny@yahoo.com", "Johnny", "Abraham", "password", "Nykøbingvej", 85, "Stubbekøbing", 4850),
    ("JesseVillagrana@gmail.com", "Jesse", "Villagrana", "password", "Maribovej", 245, "Nakskov", 4900),
    ("ArgusSewell@yahoo.com", "Argus", "Sewell", "password", "Lundtoftevej", 20, "Svendborg", 5700),
    ("Maria@Gmail.com", "Maria", "Brandt", "password", "Græsager", 402, "Kokkedal", 2980),
    ("PeterFalktoft@gmail.com", "Peter", "Falktoft", "hergaardetgodt", "Multivej", 16, "Århus C", 8000),
    ("EsbenBjerre@live.dk", "Esben", "Bjerre", "EsbenEllerEj", "Vermundsgade", 9, "København Ø", 2100);

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
    (1, "Labrador"),
    (1, "Siberian Husky"),
    (1, "Boxer"),
    (1, "Cane Corso"),
    (2, "Maine Coon"),
    (2, "Ragdoll"),
    (2, "Siamese"),
    (2, "Burmesse"),
    (4, "Python"),
    (3, "Lionhead");

insert ignore into pets
    (name, species, breed, owner, age, description, latitude, longitude)
values
    ("Jerry", 1, 2, 1, 7, "Good boy", 55.791188, 12.161147715673906),
    ("Kevin", 1, 1, 1, 4, "Likes long walks on the beach", 55.72735988853124,  12.421350222567543),
    ("Tom", 2, 8, 2, 7, "Sleeps a lot", 56.06122627940592, 12.775782443892037),
    ("Luna", 1, 4, 2, 12, "half blind", 55.7806875689101, 12.523890643227794),
    ("Simba", 1, 4, 9, 9, "obidient", 55.91304, 12.485486),
    ("Nala", 2, 9, 4, 4, "may scratch a lille bit", 55.5487, 12.0239),
    ("Jimmy", 4, 3, 5, 20, "Just feed him rats", 55.2324811, 11.740828),
    ("Laudrup", 3, 11, 6, 2, "likes grass, hates hawks", 54.863136291503906, 12.019526481628418),
    ("Kylian", 1, 5, 7, 5, "howls all night long, a real goofball", 54.85551071166992, 11.164922714233398),
    ("Vader", 2, 7, 8, 12, "Dark like Darth Vader", 55.0720329284668, 10.585433006286621),
    ("Rihanna", 1, 6, 10, 8, "a real pop-dog", 56.14143371582031, 10.21992301940918),
    ("Britney", 1, 7, 11, 4, "quite exclusive dogbreed", 55.70567321777344, 12.553417205810547);
    
    
insert ignore into bookings
    (pet, bookee, start, end)
values
    (1, 1, "2022-10-09", "2022-10-12"),
    (3, 1, "2022-11-23", "2022-11-24"),
    (2, 1, "2022-12-01", "2022-12-15");
create database if not exists airpets;
USE airpets;
    ("Tom", 2, 8, 2, 7, "Sleeps a lot", 56.06122627940592, 12.775782443892037),
    ("Luna", 1, 4, 2, 12, "half blind", 55.7806875689101, 12.523890643227794),
    ("Simba", 1, 4, 9, 9, "obidient", 55.91304, 12.485486),
    ("Nala", 2, 9, 4, 4, "may scratch a lille bit", 55.5487, 12.0239),
    ("Jimmy", 4, 3, 5, 20, "Just feed him rats", 55.2324811, 11.740828),
    ("Laudrup", 3, 11, 6, 2, "likes grass, hates hawks", 54.863136291503906, 12.019526481628418),
    ("Kylian", 1, 5, 7, 5, "howls all night long, a real goofball", 54.85551071166992, 11.164922714233398),
    ("Vader", 2, 7, 8, 12, "Dark like Darth Vader", 55.0720329284668, 10.585433006286621);
    
insert ignore into bookings
    (pet, bookee, start, end)
values
    (1, 1, "2022-10-09", "2022-10-12"),
    (3, 1, "2022-11-23", "2022-11-24"),
    (2, 1, "2022-12-01", "2022-12-15");
create database if not exists airpets;
USE airpets;
 ```
