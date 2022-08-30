# coronaTickets.uy - Workstation

## Antes de ejecutar

Asegurarse de haber instalado dependencia del *corona-ticket-uy-central* en su repositorio maven local, para eso referirse al read me de dicho proyecto que puede encontrar [aquí](../corona-ticket-uy-central/readme.md)

## Compilar

Ejecutar `mvn clean install` lo cual genera un jar dentro de la carpeta *target* llamado *workstation.jar*.

## Para ejecutar el sistema

Una vez compilado e instalado existen dos maneras de correr la aplicación:

* `Click derecho > Run as Java Application` a la clase **Login.java** (en Eclipse)

o bien:

* Ejecutando el jar dentro de la carpeta *target* ejecutando `java -jar workstation.jar`

Cualquier de las opciones anteriores levanta la interfáz gráfica y el WebService.