FROM maven:3.8.1-openjdk-17
COPY /Users/murat/IdeaProjects/contacts/target/contacts-0.0.1-SNAPSHOT.jar contacts-1.0.0.jar
ENTRYPOINT ["java","-jar","/contacts-1.0.0.jar"]
RUN mvn clean install
CMD mvn spring-boot:run