# Telekom Challenge Project

## How to run

### #1 Option

Go run `docker-compose up` in `/hub` folder.

```bash
$ cd /hub$ docker-compose up
```

`/hub/docker-compose.yml` file pulls application image from [Docker Hub](https://hub.docker.com/repository/docker/mbdilaver/telekom).

### #2 Option

Run command below to build Spring Boot project.

```bash
$ mvn clean package -DskipTests=true
```

Then to run both this application and `Postgres` using `docker-compose`

```bash
$ docker-compose up --build
```

## How it works

### Rest

After running application you can see REST endpoints from your browser at `http://localhost:8080/swagger-ui/index.html`. For trying out different endpoints on Swagger UI you need an authorization token.

### Security

This application uses JWTs for authentication.

To get an JWT you first need to register. Send a `POST` request to `http://localhost:8080/accounts` with a body like given below:

```
{    "number": "555111223",    "password": "12345"}
```

Then login with that user credentials. Send a `POST` request to `http://localhost:8080/login` with the given body:

```
{ "number": "555111223", "password": "12345"}
```

In the response `Headers` you will see your token at `Authorization` field.

You can use the token given below for testing.

```
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNzA3Nzc4NDExfQ.hIjYs7_VJO3bbbu5XDpP-y0dVPYgyiWXz5rVyGIhNSdaYsoe8B3-F7mYQhUl-_aZ0XBzW4M-7ScVyZ0vmzi1vQ
```

You need to add this token to your REST requests.

### WebSockets

WebSocket URL is `ws://localhost:8080/chat`. Notifications for each number are published through `/notifications/{number}` topic. For example notifications of number `555111223` will be published on `/notifications/555111223` topic.

WebSockets are used by server in two cases:

1. When a client connects to a notification topic, if there are missed calls for that topic, their call IDs are sent to client using this topic. For this purpose EventListeners are used.

2. When an approvement request for missed calls is made for a target number using REST, availability information is sent to each destination number using its topic.

### Use cases

1. To make a call you need to send a `POST` request to `http://localhost:8080/calls` with a body like below. This will create a `Call Entity` in `Call` table. Several calls from/to same number can be made.

```
{    "destinationNumber": "555111223",    "targetNumber": "555444332"}
```

2. To approve missed calls you need to send a `PUT` request to `http://localhost:8080/calls` with a body like below. This will notify callers of corresponding calls using `/notifications/{destinationNumber}` topic of each caller.

```
{    "callIdList": [1, 2, 3, 4]}
```

### Architecture

Hexagonal architecture is used for organizing project's code. In domain layer Service classes are getting requests from Controller and Listener classes using port interfaces. Controller and Listeners are implementing this interfaces in adapter classes. Service classes again uses port interfaces to make requests to/from database adapters.
