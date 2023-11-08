CREATE DATABASE TSB_DB;

USE TSB_DB;

CREATE TABLE Provedores(
id INT PRIMARY KEY AUTO_INCREMENT,
izena VARCHAR(255),
herria VARCHAR(255),
mota VARCHAR(255),
korreoa VARCHAR(255),
mugikorra VARCHAR(255),
komentarioa VARCHAR(255)
);

CREATE TABLE Usuarios(
id INT PRIMARY KEY AUTO_INCREMENT,
erabiltzailea VARCHAR(255),
email VARCHAR(255),
enpresa VARCHAR(255)
);

CREATE TABLE Compras(
id INT PRIMARY KEY AUTO_INCREMENT,
izena VARCHAR(255),
estatua VARCHAR(255),
faktura VARCHAR(255),
klientea VARCHAR(255),
enpresa VARCHAR(255),
prezio_base VARCHAR(255),
bez VARCHAR(255),
prezio_totala VARCHAR(255),
eskera_data VARCHAR(255),
baimentze_data VARCHAR(255)
);

CREATE TABLE Gastos(
id INT PRIMARY KEY AUTO_INCREMENT,
izena VARCHAR(255),
enpresa VARCHAR(255),
eroslea VARCHAR(255),
produktua VARCHAR(255),
ordaindu_modua VARCHAR(255),
estatua VARCHAR(255),
sortze_data VARCHAR(255),
ordaindu_data VARCHAR(255),
deskribapena VARCHAR(255),
ordaindu_prezioa VARCHAR(255)
);

CREATE TABLE Productos(
id INT PRIMARY KEY AUTO_INCREMENT,
izena VARCHAR(255),
kategoria VARCHAR(255),
mota VARCHAR(255),
prezioa VARCHAR(255),
pisua VARCHAR(255),
saldu_ok VARCHAR(255),
erosi_ok VARCHAR(255),
faktura_politika VARCHAR(255),
deskribapena VARCHAR(255)
);



