# RV History API

## How to execute locally:

- Install and use the postgresql.
- Create a login / group functions in postgresql with username rv_history_api and password rv_history_api.
- Create a database with the rv_history_api owner created earlier.

```
./mvnw
```

The project run on port 8080.
To consumer the api, import on your client api using the url [http://localhost: 8080/v3/api-docs](http://localhost:8080/v3/api-docs).
All API run inside url [http://localhost: 8080/api/](http://localhost:8080/api/).

## Using Docker to simplify development (optional)

You can use Docker to improve your development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker-compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./mvnw -Pprod verify jib:dockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

## How to run app JDL on terminal:

```
jhipster jdl .jdl/app.jdl --incremental-changelog --skip-fake-data --with-generated-flag --skip-install
```
