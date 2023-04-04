python mongo_cli.py --host localhost --port 27017 --database pm --collection tasks --query '<Escribe los queres que necesites>'

how to run `mongod --dbpath /usr/local/var/mongodb`

¿Que pasaría si en lugar de Jira o cualquier UI de project management, se usa GPT?
Idea básica:
1. Una base de datos (no Jira) MongoDB y un tool que permite CRUD
2. Una UI para conversación
3. Un Engine que usa GPT para generar queries (CRUD)
4. Con esa misma info se le pide a GPT contestar consultas en Lenguaje Natural.


SYSTEM
Eres un project manager / scrum master con acceso a una base de datos en *MongoDB*, para cada petición debes escribir el query necesario para obtener el contexto que necesitas para poder asistir a tu equipo.

USER
Este es el *cli tool* que tienes a tu disposición para hacer consultas a la base de datos:
```
python mongo_cli.py --host localhost --port 27017 --database pm --collection tasks --query '<Escribe los querys que necesites>'
```
El tool imprime los resultados obtenidos o "Vacio" si no hay nada.

USER
Usando el CLI Tool, añadiendo cualquier otro comando cómo `DATE` necesario para obtener la fecha. haz el commando que debe ejecutarse en la terminal para la pregunta. --query debe ser un json valido
>"Que tareas tengo que terminar hoy?" 

ASSISTANT
El comando que debe ejecutarse en la terminal es:
```
python mongo_cli.py --host localhost --port 27017 --database pm --collection tasks --query '{"due_date": {"$eq": "'$(date +%Y-%m-%d)'"}}'
```
Este comando buscará todas las tareas cuya fecha de vencimiento sea igual a la fecha actual.

USER
El query anterior devolvió: 
Vacio
Que respuesta le puedes dar al usuario con esta información?

ASSISTANT
Si el query anterior devolvió "Vacio", significa que no hay tareas que deban ser terminadas hoy. Entonces, la respuesta al usuario podría ser: 
>"No hay tareas pendientes que deban ser terminadas hoy".
USER
Usando el CLI Tool, añadiendo cualquier otro comando cómo `DATE` necesario para obtener la fecha. haz el commando que debe ejecutarse en la terminal para la pregunta. --query debe ser un json valido
"Porque?" 
ASSISTANT
El comando anterior devolvió "Vacio" porque no hay tareas en la base de datos cuya fecha de vencimiento sea igual a la fecha actual. Si desea saber por qué no hay tareas pendientes para hoy, debería verificar si hay tareas con una fecha de vencimiento anterior a la fecha actual o si todas las tareas ya se han completado. Para buscar tareas con una fecha de vencimiento anterior, el commando sería:
```
python mongo_cli.py --host localhost --port 27017 --database pm --collection tasks --query '{"due_date": {"$lt": "'$(date +%Y-%m-%d)'"}}'
```
Y para buscar tareas que ya se han completado, el commando sería:
```
python mongo_cli.py --host localhost --port 27017 --database pm --collection tasks --query '{"status": "completed"}'
```
Dependiendo de los resultados obtenidos, se podría dar una respuesta más específica al usuario.
USER
El primer query devolvió: 
Vacío
El segundo query devolvió: 
Vacío
Que respuesta le puedes dar al usuario con esta información?
ASSISTANT
Si ambos queries devolvieron "Vacío", significa que no hay tareas pendientes en la base de datos. En este caso, la respuesta al usuario podría ser: "No hay tareas pendientes en este momento".

USER
Usando el CLI Tool, añadiendo cualquier otro comando cómo `DATE` necesario para obtener la fecha. haz el commando que debe ejecutarse en la terminal para la pregunta. --query debe ser un json valido
>"Estamos haciendo el plan para el release de este mes, queremos comprometernos a entregar lo siguiente: 1. Pantalla de login. 2. pantalla registro. 3. pantalla de bienvenida." 

ASSISTANT
Para obtener las tareas relacionadas con el plan de lanzamiento de este mes que incluya las pantallas de login, registro y bienvenida, el commando que debe ejecutarse en la terminal es:
```
python mongo_cli.py --host localhost --port 27017 --database pm --collection tasks --query '{"name": {"$in": ["Pantalla de login", "Pantalla registro", "Pantalla de bienvenida"]}, "release": "Este mes"}'
```
Este query buscará todas las tareas cuyo nombre sea "Pantalla de login", "Pantalla registro" o "Pantalla de bienvenida" y que pertenezcan al release "Este mes".

USER
El query anterior devolvió: 
Vacío
Que respuesta le puedes dar al usuario con esta información?

ASSISTANT
Si el query anterior devolvió "Vacío", significa que no se encontraron tareas relacionadas con el plan de lanzamiento de este mes que incluya las pantallas de login, registro y bienvenida. En este caso, la respuesta al usuario podría ser: 
>"No se encontraron tareas relacionadas con el plan de lanzamiento de este mes que incluya las pantallas de login, registro y bienvenida".

USER
Usando el CLI Tool, añadiendo cualquier otro comando cómo `DATE` necesario para obtener la fecha. el mes de hoy es `Marzo`. --query debe ser un json valido. haz el commando que debe ejecutarse en la terminal para la pregunta:
>"Puedes crearlas por favor" 
ASSISTANT
Para crear las tareas relacionadas con el plan de lanzamiento de este mes que incluye las pantallas de login, registro y bienvenida, el commando que debe ejecutarse en la terminal es:
```
python mongo_cli.py --host localhost --port 27017 --database pm --collection tasks --insert '[{"name": "Pantalla de login", "release": "Marzo"}, {"name": "Pantalla registro", "release": "Marzo"}, {"name": "Pantalla de bienvenida", "release": "Marzo"}]'
```
Este query insertará las tres nuevas tareas en la base de datos con el nombre y release correspondiente.
USER
El query anterior devolvió: 
Documentos insertados correctamente
Que respuesta le puedes dar al usuario con esta información?

ASSISTANT
Si el query anterior devolvió "Documentos insertados correctamente", significa que las tareas relacionadas con el plan de lanzamiento de este mes que incluyen las pantallas de login, registro y bienvenida se han creado correctamente en la base de datos. En este caso, la respuesta al usuario podría ser: 
>"Se han creado correctamente las tareas relacionadas con el plan de lanzamiento de este mes que incluyen las pantallas de login, registro y bienvenida".

USER
Usando el CLI Tool, sabiendo que la fecha de hoy es `Tue Mar 28 19:33:27 CST 2023` . --query debe ser un json valido. haz el commando que debe ejecutarse en la terminal para la pregunta:
>"La tarea mas prioritaria consideramos que es el registro, porque si no ningun usuario va a poder acceder " 
ASSISTANT
Para actualizar la tarea "Pantalla registro" y establecerla como la tarea más prioritaria, el commando que debe ejecutarse en la terminal es:
```
python mongo_cli.py --host localhost --port 27017 --database pm --collection tasks --update '{"name": "Pantalla registro", "release": "Marzo"}' '{"$set": {"priority": 1}}'
```
Este query buscará la tarea con el nombre "Pantalla registro" y release "Marzo" y establecerá su prioridad en 1.





Add message
Mode
ChatBeta
Model
gpt-3.5-turbo
Temperature
0.7
Maximum length
256
Top P
1
Frequency penalty
0
Presence penalty
0

python mongo_cli.py --host localhost --port 27017 --database pm --collection tasks --insert '[{"name": "Lista de tareas", "release": "Abril"}, {"name": "Ordenar lista", "release": "Abril"}, {"name": "Arrastrar elementos", "release": "Abril"}]


Example:

1. Que tareas tengo que terminar hoy?
2. No hay tareas pendientes que deban ser terminadas hoy
3. Estamos haciendo el plan para el release de este mes, queremos comprometernos a entregar lo siguiente: 1. Pantalla de login. 2. pantalla registro. 3. pantalla de bienvenida.
4. Se han creado correctamente las tareas relacionadas con el plan de lanzamiento de este mes que incluyen las pantallas de login, registro y bienvenida