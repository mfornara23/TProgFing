# coronaTickets.uy

## Introducción

Atenta a la situación de pandemia por el COVID-19, la Facultad de Ingeniería le ha propuesto a su
Equipo de Desarrollo la construcción de la la plataforma coronaTickets.uy de manera ofrecer un servicio de 
gestión social de espectáculos artísticos y culturales a través de Internet. 
Para utilizar las funcionalidades que brinda la plataforma, los usuarios deberán registrarse utilizando sus datos personales.

## Build

### Manual

**Pre condición:** Tener instalado *Apache Maven*, en caso de no tenerlo ejecutar:

`sudo apt-get update` para actualizar las dependencias y luego `sudo apt install maven` para instalar Maven (o su equivalente en otras distribuciones).

Validar la instalación corriendo:

`mvn -version`

Cumpliendo las pre condiciones y parados en la raíz de **corona-ticket-uy**:

Compilar e instalar la dependencia de la logica:

`mvn -f ./corona-ticket-uy-central/pom.xml clean install`

Crear el JAR con la interfáz de administrado y el WS:

`mvn -f ./corona-ticket-uy-workstation/pom.xml clean install`

Crear el WAR con la web de CoronaTicketsUy:

`mvn -f ./corona-ticket-web/pom.xml clean install`

El JAR y el WAR se encuentran en la carpeta *target* de los proyectos **corona-ticket-uy-workstation** y **corona-ticket-web** respectivamente.

Este proceso se puede realizar en el mismo órden desde Eclipse o IntelliJ (utilizando los comandos que estos proveen por defecto para las distintas goals de Maven), obteniendo los mismos resultados.

### Automático

Cumpliendo las mismas pre condiciones que para el build manual y  y parados en la raíz de **corona-ticket-uy** ejecutar:

`./build.sh`

Si arroja un problema de permisos de ejecucion probar con  `sudo chmod 777 build.sh` y luego volver a ejecutar `./build.sh`.

Para ejecutar **workstation.jar** parados en el directorio *corona-ticket-uy-workstation* ejecutar:

`java -jar ./target/workstation.jar`


**IMPORTANTE: Es necesario estar parados en *corona-ticket-uy-workstation* y ejecutar el comando como se lo describe anteriormente, no se recomienda mover el jar, libs o ejecutar desde otro directorio**