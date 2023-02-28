FROM maven:3.8.1-openjdk-17
WORKDIR /app
COPY . .

# TODO Enable tests back when they start to work
RUN mvn clean install -Dmaven.test.skip=false
ENTRYPOINT ["java","-jar","/app/target/contacts-0.0.1-SNAPSHOT.jar"]