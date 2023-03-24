# GameCar

## SQL

```SQL
CREATE DATABASE GameCar;
USE GameCar;
CREATE TABLE Cars(  idCar INTEGER PRIMARY KEY,
                    color VARCHAR(10));
CREATE TABLE Players(   idPlayer INTEGER NOT NULL PRIMARY KEY,
                        idCar INTEGER NOT NULL,
                        name VARCHAR(20),
                        score INTEGER,
                        FOREIGN KEY (idCar) REFERENCES Cars(idCar));

INSERT INTO Cars VALUES (0, "BLACK");
INSERT INTO Cars VALUES (1, "RED");
INSERT INTO Cars VALUES (2, "GREEN");
INSERT INTO Cars VALUES (3, "YELLOW");
INSERT INTO Cars VALUES (4, "BLUE");
INSERT INTO Cars VALUES (5, "PURPLE");
INSERT INTO Cars VALUES (6, "CYAN");
INSERT INTO Cars VALUES (7, "GRAY");
INSERT INTO Cars VALUES (8, "WHITE");
```

![video-gamecar2](https://user-images.githubusercontent.com/78941509/227647932-328ed475-69ba-4f99-8372-153f3915aa10.gif)
