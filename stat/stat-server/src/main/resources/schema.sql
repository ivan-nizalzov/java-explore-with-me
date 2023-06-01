drop table if exists ENDPOINT_HIT;

create table if not exists ENDPOINT_HIT (
    id              BIGINT generated by default as identity,
    app             VARCHAR(256),
    uri             VARCHAR(512),
    ip              VARCHAR(64),
    created         TIMESTAMP WITHOUT TIME ZONE,
    constraint ENDPOINT_HIT_PK primary key (id)
);

