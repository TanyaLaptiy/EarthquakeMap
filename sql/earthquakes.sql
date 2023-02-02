DROP DATABASE IF EXISTS earthquakes;
CREATE DATABASE earthquakes;
USE earthquakes;

CREATE TABLE userE (
    id INT(11) NOT NULL AUTO_INCREMENT,
    lastName VARCHAR(50) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50),
    login VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    sex VARCHAR(50),
    birthday DATE,


    PRIMARY KEY (id),
    KEY lastName (lastName),
    KEY firstName(firstName),
    KEY login (login),
    KEY country (country),
    UNIQUE (login)

) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE clientE (
    id INT(11) NOT NULL AUTO_INCREMENT,
    mail VARCHAR(50) NOT NULL,
    userId INT(11) NOT NULL,

    PRIMARY KEY (id),
    KEY mail(mail),
    FOREIGN KEY (userId) REFERENCES `userE` (id) ON DELETE CASCADE

) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE administratorE (
    id INT(11) NOT NULL AUTO_INCREMENT,
    `position` VARCHAR(50) NOT NULL,
    userId INT(11) NOT NULL,

    PRIMARY KEY (id),
    KEY `position` (`position`),
    FOREIGN KEY (userId) REFERENCES `userE` (id) ON DELETE CASCADE

) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `request` (
    id INT(11) NOT NULL AUTO_INCREMENT,
    clientId INT(11) NOT NULL,
    data DATE NOT NULL,
    title VARCHAR(100) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    magnitude DOUBLE NOT NULL,
    depth DOUBLE NOT NULL,
    age VARCHAR(10) NOT NULL,
    onland VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    requestData DATE NOT NULL,

    KEY data(data),
    KEY title(title),
    KEY message(message),
    KEY latitude(latitude),
    KEY longitude(longitude),
    KEY magnitude(magnitude),
    KEY depth(depth),
    KEY age(age),
    KEY onland(onland),
    KEY country(country),
    KEY status(status),
    KEY requestData(requestData),

    PRIMARY KEY (id),
    FOREIGN KEY (clientId) REFERENCES `clientE` (id) ON DELETE CASCADE

) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE earthquake (
    id INT(11) NOT NULL AUTO_INCREMENT,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    magnitude DOUBLE NOT NULL,
    age VARCHAR(10) NOT NULL,
    depth DOUBLE NOT NULL,
    onland VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    data DATE NOT NULL,



    PRIMARY KEY (id),
    KEY latitude (latitude),
    KEY longitude(longitude),
    KEY magnitude (magnitude),
    KEY age (age),
    KEY depth (depth),
    KEY onland (onland),
    KEY country(country),
    KEY data(data)


) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `sms` (
    id INT(11) NOT NULL AUTO_INCREMENT,
    clientId INT(11) NOT NULL,
    data DATE NOT NULL,
    message VARCHAR(1000) NOT NULL,

    KEY data(data),
    KEY message(message),

    PRIMARY KEY (id),
    FOREIGN KEY (clientId) REFERENCES `clientE` (id) ON DELETE CASCADE

) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `sessionE` (
    id INT(11) NOT NULL AUTO_INCREMENT,
    token VARCHAR(61) NOT NULL,
    userId INT(11) NOT NULL,

    PRIMARY KEY (id),
    KEY userId(userId),
    KEY token(token)

) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `rating` (
    id INT(11) NOT NULL AUTO_INCREMENT,
    clientId INT(11) NOT NULL,
    mark INT(11) NOT NULL,
    message VARCHAR(1000),

    PRIMARY KEY (id),
    UNIQUE (clientId)

) ENGINE=INNODB DEFAULT CHARSET=utf8;



