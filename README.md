# Telekom Challenge Project

## How to run

### #1 Option

Go run `docker-compose up` in `/hub` folder.

```bash
$ cd /hub
$ docker-compose up
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
{
    "number": "5551112233",
    "password": "12345"
}
```

Then login with that user credentials. Send a `POST` request to `http://localhost:8080/login` with the given body:

```
{
 "number": "5551112233",
 "password": "12345"
}
```

In the response `Headers` you will see your token at `Authorization` field.

You can use the token given below for testing.

```
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNzA3Nzc4NDExfQ.hIjYs7_VJO3bbbu5XDpP-y0dVPYgyiWXz5rVyGIhNSdaYsoe8B3-F7mYQhUl-_aZ0XBzW4M-7ScVyZ0vmzi1vQ
```

You need to add this token to your REST requests.

### WebSockets

WebSocket URL is `ws://localhost:8080/chat`. Notifications for each number are published through `/notifications/{number}` topic. For example notifications of number `5551112233` will be published on `/notifications/5551112233` topic.

WebSockets are used by server in two cases:

1. When a client connects to a notification topic, if there are missed calls for that topic, they are sent to client using this topic. For this purpose EventListeners are used.

2. 

### Use cases

1. To make a call you need to send a `POST` request to `http://localhost:8080/calls` with a body like given below. This will create a `Call Entity` in `Call` table. Several calls from/to same number can  be made.

```
{
    "destinationNumber": "5551112233",
    "targetNumber": "5554443322"
}
```















