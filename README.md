# kit-kodeopgave-v3 project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `kit-kodeopgave-v3-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/kit-kodeopgave-v3-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/kit-kodeopgave-v3-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Development prerequisites

### Run postgres

This service needs a PostgreSQL database.
See [/src/main/resources/application.properties](/src/main/resources/application.properties) for configuration details.

```bash
docker pull postgres
docker run --name kit-postgres -e POSTGRES_PASSWORD=1234 -d -p 5432:5432 postgres
docker start kit-postgres
```

Run

```bash
mvn quarkus:dev
```

## Build and create a docker image (on Windows)

Create package

```powershell
.\mvnw package -DskipTests
```

Build the docker image (remember the dot at the end.)

```powershell
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/kit-kodeopgave-v3-jvm .
```

## Using Docker compose

Create package

```powershell
.\mvnw clean package -DskipTests
```

Run

```powershell
docker-compose build
docker-compose up
```

### Interesting endpoints

* [Rest resources](http://localhost:8080/)
* [swagger-ui](http://localhost:8080/swagger-ui/)
* [health](http://localhost:8080/health)
* [metrics](http://localhost:8080/metrics)

## TODO

### Code

* Better date handling of birthdays in the Person class.
* Better Swagger documentation.
* Frontend?

### Docker stuff

* google/cadvisor
* Improved grafana dashboard
