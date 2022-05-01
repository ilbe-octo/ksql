## ******Streaming ETL demo - Enriching event stream data with CDC data from MySQL******

## ****Pre-reqs:****

- Docker

## **Pre-Flight Setup**

Start the environment:

`docker-compose up`

## ****Run ksqlDB CLI:****

`docker exec -it ksqldb ksql http://0.0.0.0:8088`

<aside>
💡 **KSQL** is the streaming SQL engine that enables real-time-data-processing

</aside>

## ****Part 01 - Create Connector :****

#### ****We can't update connectors by PUT Request Method Not Allowed :****
#### ****We can create multiple instances of the Debezium Postgres connector :****
If you deploy multiple instances of the Debezium Postgres connector, you must make sure to use distinct replication slot names.
You can specify a name when setting up the connector , problem if we depasse number of slot by default is 10

**Check if the connector is created successfuly with :**

`ksql> SHOW CONNECTORS;`

<aside>
💡 The **source connector** allows us to import data from any relational database with jdbc into Apache Kafka.

</aside>

<aside>
💡 The **sink connector** delivers data from Kafka topics into other systems or any kind of database

</aside>

**Check if the topic is created too with :**

`ksql> SHOW TOPICS;`

**Create ksqlDB stream  :**

```jsx
CREATE STREAM users WITH (
    kafka_topic = 'postgres.public.owner',
    value_format = 'avro'
);
```

```jsx
CREATE STREAM campaign WITH (
    kafka_topic = 'postgres.public.campaign',
    value_format = 'avro'
);
```

**Query the ksqlDB stream:**

```jsx
SET 'auto.offset.reset' = 'earliest';
SELECT * FROM USERS EMIT CHANGES LIMIT 5;
```











