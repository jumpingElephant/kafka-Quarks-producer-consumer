# kafka-Quarks-producer-consumer Project

This project uses Kafka, Zookeeper and Quarkus to showcase producing and consuming messages.

## Running all services

You can run Kafka and Zookeeper using:

```shell script
docker-compose up --detach
```

> **_NOTE:_**  Kafka is listening on port 9092 on localhost.

```shell script
mvn -f producer quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

```shell script
mvn -f consumer quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8081/q/dev/.

## Creating messages

You can send _GET_ requests

```http request
GET http://localhost:8080/hello
```

The _producer_ will then send a message and the _consumer_ read this message and print it to the console.

## [kcat](https://github.com/edenhill/kcat) commands

### List metadata for all topics

```shell script
docker run -it --network=kafka_default edenhill/kcat:1.7.1 -b kafka:9092 -L
```

### Consume last message with kcat and exit

```shell script
docker run -it --network=kafka_default edenhill/kcat:1.7.1 -b kafka:9092 \
  -C -t time -f '[%T] Key="%k", message payload is: \n%s\n\n' -o -1 -e
```

### Produce messages from file with kcat

```shell script
docker run -it --network=kafka_default -v $PWD:/opt/var/ edenhill/kcat:1.7.1 -b kafka:9092 \
  -P -t time -K: -l /opt/var/data.txt
```
