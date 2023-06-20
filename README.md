# java-explore-with-me
- - - -

### Project description:
The project enables users to organize and participate in various activities.
The API consists of three paths:
1. `/admin` for moderation;
2. `/private` for authorized users;
3. `/public` for all users.

ExploreWithMe allows users to publish events, comment events, send participation requests, review event compilations prepared by moderators, and search for events.

### Tech stack:
ExploreWithMe adopts a microservices architecture, comprising a main service and a statistics service that collects statistics on event views by users. Communication between services is done via HTTP using RestTemplate from Spring Framework.
Both services follow a RESTful design and are built using Java 11, Spring Boot 2 and Maven. Each service stores its data in a separate PostgreSQL database.
Interaction with the database is facilitated through the ORM framework Hibernate. The project is containerized using Docker.

### System requirements:
* JVM (11 or above);
* PostgreSQL (14 or above);
* Docker.
- - - -
Pull request (feature_comments): https://github.com/ivan-nizalzov/java-explore-with-me/pull/5
