drop table if exists Users, Location, Category, Events, Compilation, Compilations_Events, Requests, Comments;

create table if not exists Users
(
    id    BIGINT generated by default as identity NOT NULL,
    name  VARCHAR(250) UNIQUE,
    email VARCHAR(254),
    constraint USERS_PK primary key (id)
);

create table if not exists Location
(
    id        BIGINT generated by default as identity NOT NULL,
    latitude  DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    constraint Location_PK primary key (id)
);

create table if not exists Category
(
    id   BIGINT generated by default as identity NOT NULL,
    name VARCHAR(50) UNIQUE,
    constraint Category_PK primary key (id)
);

create table if not exists Events
(
    id                 BIGINT generated by default as identity NOT NULL,
    annotation         VARCHAR(2000),
    category_id        BIGINT,
    confirmed_requests BIGINT,
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    description        VARCHAR(7000),
    event_date         TIMESTAMP WITHOUT TIME ZONE,
    initiator_id       BIGINT,
    location_id        BIGINT,
    paid               BOOLEAN,
    participant_limit  BIGINT,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state              VARCHAR(255),
    title              VARCHAR(120),
    views              BIGINT,
    constraint EVENTS_PK primary key (id),
    constraint EVENTS_Category_category_id_FK foreign key (category_id) references Category (id) ON DELETE CASCADE,
    constraint EVENTS_USERS_initiator_id_FK foreign key (initiator_id) references Users (id) ON DELETE CASCADE,
    constraint EVENTS_Location_location_id_FK foreign key (location_id) references Location (id) ON DELETE CASCADE
);

create table if not exists Compilation
(
    id     BIGINT generated by default as identity NOT NULL,
    pinned BOOLEAN,
    title  VARCHAR(50),
    constraint Compilation_PK primary key (id)
);

create table if not exists Compilations_Events
(
    event_id       BIGINT,
    compilation_id BIGINT,
    constraint Compilations_Events_EVENTS_event_id_FK foreign key (event_id) references Events (id) ON DELETE CASCADE,
    constraint Compilations_Events_Compilation_events_id_FK foreign key (compilation_id) references Compilation (id) ON DELETE CASCADE
);

create table if not exists Requests
(
    id           BIGINT generated by default as identity NOT NULL,
    events_id    BIGINT,
    created      TIMESTAMP WITHOUT TIME ZONE,
    requester_id BIGINT,
    status       VARCHAR(255),
    constraint Requests_PK primary key (id),
    constraint UniqueConstraint unique (events_id, requester_id),
    constraint Requests_EVENTS_events_id_FK foreign key (events_id) references Events (id) ON DELETE CASCADE,
    constraint Requests_USERS_requester_id_FK foreign key (requester_id) references Users (id) ON DELETE CASCADE
);

create table if not exists Comments
(
    id        BIGINT generated by default as identity NOT NULL,
    event_id  BIGINT,
    author_id BIGINT,
    text      VARCHAR(255),
    created   TIMESTAMP WITHOUT TIME ZONE,
    constraint COMMENTS_PK primary key (id),
    constraint COMMENTS_USERS_author_id_FK foreign key (author_id) references Users (id) ON DELETE CASCADE,
    constraint COMMENTS_EVENTS_event_id_FK foreign key (event_id) references Events (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX users_id_idx ON Users (id);
CREATE UNIQUE INDEX users_name_idx ON Users (name);
CREATE INDEX users_email_idx ON Users (email);
CREATE UNIQUE INDEX Location_id_idx ON Location (id);
CREATE UNIQUE INDEX Category_id_idx ON Category (id);
CREATE UNIQUE INDEX EVENTS_id_idx ON Events (id);
CREATE INDEX EVENTS_event_date_idx ON Events (event_date);
CREATE UNIQUE INDEX Compilation_id_idx ON Compilation (id);
CREATE INDEX Compilation_title_idx ON Compilation (title);
CREATE UNIQUE INDEX Requests_id_idx ON Requests (id);
CREATE UNIQUE INDEX COMMENTS_id_idx ON Comments (id);