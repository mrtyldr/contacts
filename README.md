# contacts
contacts is a backend project for a simple phone book application.
To run this app on your local computer clone the project and run docker-compose.yml file, app will be running on localhost:8080.
To run the tests you should change the value of the url field in the application-test.yml file to jdbc:postgresql://localhost:5433/testdb
and integration-db container should be running.
swagger api documentation is reachable on https://basic-contacts.herokuapp.com/swagger-ui/index.html#/
if you are running the application on your local computer it should also be reachable on localhost:8080/swagger-ui/index.html#/
