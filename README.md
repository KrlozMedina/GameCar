# Juego de carros en consola con conexión a base de datos MySQL

![](https://img.shields.io/github/stars/KrlozMedina/GameCar)
![](https://img.shields.io/github/forks/KrlozMedina/GameCar)
![](https://img.shields.io/github/tag/KrlozMedina/GameCar)
![](https://img.shields.io/github/release/KrlozMedina/GameCar)
![](https://img.shields.io/github/issues/KrlozMedina/GameCar)

Este es un juego de carros simple que se ejecuta en la consola y utiliza una
conexión a una base de datos MySQL para persistir los datos de los jugadores,
carros y pistas. El objetivo del juego es tirar un dado para mover un carro
por una pista y llegar a la meta antes que los demas jugadores para ubicarse
en  los primeros tres lugares y se persisten los resultados en la base de
datos.

![video-gamecar2](https://user-images.githubusercontent.com/78941509/227647932-328ed475-69ba-4f99-8372-153f3915aa10.gif)

## Requisitos

Para utilizar este juego, necesitarás tener Java y MySQL instalados en tu
computadora. Si aún no los tienes instalados, puedes descargar la última
versión de Java desde el sitio web oficial de Java (https://www.java.com/es/download/)
y un servidor con MySQL, recomiendo XAMPP por su facil instalacion y previa
configuracion, puede descargar la ultima version en el sitio oficial (https://www.apachefriends.org/es/index.html).

## Configuración de la base de datos

Antes de ejecutar el juego, necesitarás configurar la conexión a la base de
datos. Abre el archivo `src/main/java/org/game/Game` y edita
las siguientes líneas:

```java
// Server data -----------------------------------------
public static final String URL_SERVER = "IP server";
public static final String PORT_SERVER = "Port server";
public static final String USERNAME_SERVER = "Username";
public static final String PASSWORD_SERVER = "Password";
//------------------------------------------------------
```

Reemplaza `IP server`, `Port server`, `Username` y `Password` con la
información correspondiente para tu servidor.

Las tablas de la base de datos se creará con las siguientes lineas de codigo:

```sql
CREATE DATABASE GameCar;
USE GameCar;
CREATE TABLE Cars(  idCar INTEGER PRIMARY KEY,
                    color VARCHAR(10));
CREATE TABLE Rails( idRail INTEGER PRIMARY KEY,
                    idPlayer INTEGER NOT NULL,
                    position INTEGER,
                    goal INTEGER,
                    FOREIGN KEY (idPlayer) REFERENCES Players(idPlayer));
CREATE TABLE Players(   idPlayer INTEGER NOT NULL PRIMARY KEY,
                        idCar INTEGER NOT NULL,
                        name VARCHAR(20),
                        score INTEGER,
                        FOREIGN KEY (idCar) REFERENCES Cars(idCar));

INSERT INTO Cars VALUES (1, "Red");
INSERT INTO Cars VALUES (2, "Green");
INSERT INTO Cars VALUES (3, "Yellow");
INSERT INTO Cars VALUES (4, "Blue");
INSERT INTO Cars VALUES (5, "Purple");
INSERT INTO Cars VALUES (6, "Cyan");
INSERT INTO Cars VALUES (7, "Gray");
INSERT INTO Cars VALUES (8, "White");
INSERT INTO Cars VALUES (9, "Black");
```

## Uso

Para ejecutar el juego, abre una consola en el directorio raíz del proyecto
y escribe:

```bash
javac src/main/java/org/krlozmedina/Main.java
java -cp src/main/java com.krlozmedina.Main
```

El juego se iniciará en la consola y podrás jugarlo ingresando la distancia
de la pista, la cantidad de jugadores y asigando un nikname y color por
jugador.

## Características del juego

### Crear jugadores, carros y pistas

El juego permite crear jugadores, carros y pistas. Un jugador puede ser un
conductor y un conductor debe tener un carro asociado y un carro debe estar
asociado a una pista.

### Iniciar el juego

Con un identificador se puede iniciar el juego, se tiene la lista de carros
en donde se puede iterar y avanzar según la posición de la pista, esto se
hace de forma aleatoria.

### Seleccionar los primeros tres lugares

Se pueden seleccionar el primer, segundo y tercer lugar en la medida que
los carros llegan a la meta (final del recorrido). Se crea un objeto podio
con los nombres de los conductores y se persiste en la base de datos.

### Persistir los resultados

Los resultados se persisten en la base de datos con los nombres de los
conductores y se agrega un contador de las veces que han ganado.

## Contribuir

Si deseas contribuir a este proyecto, puedes hacer un fork del repositorio
y enviar tus cambios a través de un pull request. Asegúrate de seguir las
mejores prácticas de programación y de agregar pruebas para cualquier nueva
funcionalidad que agregues.

## Licencia

Este proyecto está bajo la licencia MIT. Puedes leer más sobre la licencia
en el archivo LICENSE.md del proyecto.

¡Gracias por utilizar este juego! Si tienes algún problema o sugerencia, no
dudes en crear un issue en el repositorio.

## Sobre mi

`Visitar mi portafolio web`: <https://krlozmedina.com>