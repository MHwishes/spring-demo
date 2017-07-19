# create DB
DROP DATABASE IF EXISTS mahong;
CREATE DATABASE mahong;

# use DB
USE mahong;

# create user_info_table
DROP TABLE IF EXISTS  mahong;
CREATE TABLE  mahong (
    id int not null primary key AUTO_INCREMENT,
    name varchar(100) not null,
    age int(6) not null
);

# insert some user information into table
INSERT INTO mahong(name, age) VALUES( 'Bulma', 1);
INSERT INTO mahong(name, age) VALUES( 'Vegeta', 2);
INSERT INTO mahong(name, age) VALUES( 'ChiChi', 4);
INSERT INTO mahong(name, age) VALUES( 'Goku', 4);

# show all user information from users
SELECT * FROM mahong.mahong;