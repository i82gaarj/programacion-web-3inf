==== Programación Web - Práctica 1 ====
Jaime García Arjona - i82gaarj@uco.es
Sofía Salas Ruiz - i82sarus@uco.es

==== Uso del programa ====
Antes de ejecutar el programa se debe crear un fichero llamado config.properties en el directorio donde se encuentran los ficheros .jar. En este fichero de configuración se introducen las siguientes parejas clave-valor:

filePath=rutaFichero.txt
intereses=tag1,tag2,tag3

'filePath' será el fichero donde se guardarán los contactos
'intereses' son los diferentes temas de interes que se asignan mediante sistema de tagging. Se les asignará un ID en función del orden en el que se hayan especificado en el fichero de configuración.

=== EJEMPLO === 

filePath=data.txt
intereses=Coches,Inmobiliaria,Deportes,Tecnología

=== EJECUCIÓN ===
java -jar EJ1.jar
java -jar EJ2.jar