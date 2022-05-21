CREATE TABLE owner (id BIGINT primary key, username VARCHAR )

    WITH (KAFKA_TOPIC='postgres.public.owner',KEY_FORMAT='JSON',

        partitions = 1,

        VALUE_FORMAT='AVRO');