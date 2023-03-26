# Juego de carreras en consola con conexión a base de datos MySQL

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

**Tabla de contenido**

- [Requisitos](#requisitos)
- [Configuración](#configuración)
  * [Configuración de la conexión a la base de datos](#configuración-de-la-conexión-a-la-base-de-datos)
  * [Configuracion de la base de datos](#configuración-de-la-base-de-datos)
  * [Configuracion del proyecto](#configuracion-del-proyecto)
- [Uso](#uso)
- [Características del juego](#características-del-juego)
  * [Crear jugadores, carros y pistas](#crear-jugadores-carros-y-pistas)
  * [Iniciar el juego](#iniciar-el-juego)
  * [Seleccionar los primeros tres lugares](#seleccionar-los-primeros-tres-lugares)
  * [Persistir los resultados](#persistir-los-resultados)
- [Contribuir](#contribuir)
- [Licencia](#licencia)
- [Sobre mi](#sobre-mi)


## Requisitos

- [Java SE 8 o superior](https://www.java.com/en/download)
- [Java Development Kit (JDK) 11 o superior](https://www.oracle.com/java/technologies/downloads)
- [IDE IntelliJ](https://www.jetbrains.com/idea/download)
- [XAMPP Apache + MariaDB (MySQL) + PHP + Perl](https://www.apachefriends.org/es/index.html) u otro servidor con MySQL
- [MySQL Workbench](https://www.mysql.com/products/workbench/)
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

## Configuración

### Configuración de la conexión a la base de datos

Para configurar la conexión a la base de datos MySQL, sigue estos pasos:

1. Abre el archivo `XAMPP/etc/my.cnf` con un editor de texto y ubicar la
linea con `[mysqld]` en la siguiente linea agregar `skip-grant-tables`.
2. Abre XAMPP y asegúrate de que Apache y MySQL estén en ejecución.
3. Identifica la IP y puerto del servidor.

### Configuración de la base de datos

1. Abre MySQL Workbrench.
2. Agrega una nueva conexion.
   - Hostname: IP del servidor (IP local del computador).
   - Port: Puerto del servidor (3306 por default).
   - Username: Usuario configurado en el servidor (root por default).
   - Password: Contraseña configurada en el servidor (por default no tiene contraseña).
3. Crea una query e ingresar las siguientes lineas de SQL

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

### Configuracion del proyecto

1. Abre IntelliJ IDEA.
2. Clona el proyecto del [repositorio](https://github.com/KrlozMedina/GameCar.git).
3. Abre el archivo `src/main/java/org.krlozmedina/game/Game` y edita las siguientes líneas:

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

4. Ejecutar el archivo `src/main/java/org.krlozmedina/Main` e iniciara el
juego en la consola.

## Uso

Al iniciar el juego, se mostrarán los tres mejores puntajes registrados. Luego se solicitará ingresar la distancia máxima de la pista y la cantidad de jugadores que participarán. Para cada jugador, se deberá ingresar un nickname y el color del carro que utilizará en la carrera.

Posteriormente, se mostrarán los parámetros ingresados y se preguntará si se desea modificar algún dato o iniciar el juego. Al iniciar el juego, se visualizará la pista con los carros de cada jugador y el nickname del jugador que debe tirar un dado entre 1 y 6 al oprimir la tecla "Enter". El resultado obtenido se multiplicará por 100 metros para realizar el movimiento en la pista y luego será el turno del siguiente jugador.

El juego finalizará cuando tres jugadores hayan pasado la meta y se mostrarán los tres ganadores en su orden de llegada.

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