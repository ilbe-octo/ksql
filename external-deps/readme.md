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
#### ****We can't update connectors by PUT Request => Method Not Allowed ****
#### ****We can create multiple instances of the Debezium Postgres connector :****
If you deploy multiple instances of the Debezium Postgres connector, you must make sure to use distinct replication slot names.
You can specify a name when setting up the connector , problem if we depasse number of slot by default is 10

**Create connector :**

```jsx
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" 127.0.0.1:8083/connectors/ --data "@connector.json"
```
in the connector.json file I specified table.exclude.list , and we this if we create new table and add record in it , automatically a topic will be created for this table

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

CREATE TABLE USERS_TABLE (ID INTEGER PRIMARY KEY, username VARCHAR) WITH (
    kafka_topic = 'postgres.public.owner',
    value_format = 'avro'
);
CREATE TABLE USERS_TABLE_1 (ID INTEGER PRIMARY KEY, username VARCHAR) WITH (
    kafka_topic = 'users',
    value_format = 'avro'
);


CREATE TABLE users_count 
  AS SELECT ID, username 
  FROM USERS_TABLE 
  WHERE username= 'seth.ebert';
```


```jsx
CREATE TABLE campaign (id BIGINT primary key, name VARCHAR, type Varchar, owner_id BIGINT )
WITH (KAFKA_TOPIC='postgres.public.campaign',KEY_FORMAT='JSON',
partitions = 1,
VALUE_FORMAT='AVRO');

CREATE TABLE campaign_view 
  AS SELECT *
  FROM campaign;


CREATE TABLE campaign_status
  AS SELECT ca.type,count(*) as total_campaign
  FROM campaign ca group by ca.type;

select * from campaign_view cv where cv.OWNER_ID=4 ;
```

```jsx
CREATE TABLE owner (id BIGINT primary key, username VARCHAR )
WITH (KAFKA_TOPIC='postgres.public.owner',KEY_FORMAT='JSON',
partitions = 1,
VALUE_FORMAT='AVRO');

CREATE TABLE owner_view 
  AS SELECT *
  FROM owner;
```



```jsx
CREATE TABLE user_total_campaigns_with AS SELECT count(*) as total_campaigns,ow.ID FROM campaign ca  JOIN owner ow ON ca.owner_id = ow.ID GROUP BY ow.id;

```
```jsx
CREATE TABLE COMMENTS (
    ID INT PRIMARY KEY, 
    CONTENT STRING
  ) WITH (
    KAFKA_TOPIC='postgres.public.comment',
    KEY_FORMAT='KAFKA',
    VALUE_FORMAT='JSON'
  );

CREATE TABLE comments_count 
  AS SELECT ID, content 
  FROM COMMENTS
  WHERE content='content'
```



```jsx
CREATE TABLE userCampain AS SELECT us.id,count(*) FROM campaign ca INNER JOIN users us  WITHIN 1 HOURS ON us.ID = ca.OWNER_ID GROUP BY us.id EMIT CHANGES;  
```

**Query the ksqlDB stream:**

```jsx
SET 'auto.offset.reset' = 'earliest';
SELECT * FROM USERS EMIT CHANGES LIMIT 5;
```

## ****Part 02 - Migration Tool :****
Creates a new migrations project directory structure and config file.
```jsx
ksql-migrations new-project /share/ksql-migrations http://localhost:8088
```
Initializes the migrations schema metadata (ksqlDB stream and able) on the ksqlDB server.
```jsx
ksql-migrations --config-file /share/ksql-migrations/ksql-migrations.properties initialize-metadata
```
this command create  stream: MIGRATION_EVENTS and table: MIGRATION_SCHEMA_VERSIONS
```jsx
ksql-migrations --config-file /share/ksql-migrations/ksql-migrations.properties create Add_owner_table
ksql-migrations --config-file /share/ksql-migrations/ksql-migrations.properties create Add_owner_mv
```

Appliquer les migrations
```jsx
ksql-migrations --config-file /share/ksql-migrations/ksql-migrations.properties apply --next
ksql-migrations --config-file /share/ksql-migrations/ksql-migrations.properties apply --all

To obtain the same result, we could have also used the option -n, which applies the next unapplied migration file, -u 1, 
    which applies all migrations up to and including version 1 or -v 1, which applies version 1.
```
with dry-run
```jsx
ksql-migrations --config-file /share/ksql-migrations/ksql-migrations.properties apply --next --dry-run  
```

https://www.confluent.io/blog/easily-manage-database-migrations-with-evolving-schemas-in-ksqldb/?_ga=2.81400273.713791376.1653154297-1421860517.1650212037
https://github.com/jzaralim/ksqldb-migrations-action



