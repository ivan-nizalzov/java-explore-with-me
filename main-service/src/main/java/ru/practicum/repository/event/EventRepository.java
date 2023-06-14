package ru.practicum.repository.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.category.Category;
import ru.practicum.model.event.Event;
import ru.practicum.model.event.State;
import ru.practicum.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findDByInitiator(User initiator, Pageable pageable);

    List<Event> findByCategory(Category category);

    @Query("select e from Event e " +
            "where (:users is null or e.initiator.id in :users) " +
            "and (:states is null or e.state in :states) " +
            "and (:categories is null or e.category.id in :categories) " +
            "and (cast(:rangeStart as java.time.LocalDateTime) is null or e.eventDate >= :rangeStart) " +
            "and (cast(:rangeEnd as java.time.LocalDateTime) is null or e.eventDate <= :rangeEnd)")
    List<Event> getEventsWithUsersStatesCategoriesDateTime(@Param("users") List<Long> users,
                                                           @Param("states") List<State> states,
                                                           @Param("categories") List<Long> categories,
                                                           @Param("rangeStart") LocalDateTime rangeStart,
                                                           @Param("rangeEnd") LocalDateTime rangeEnd,
                                                           Pageable pageable);

    @Query("select e from Event e " +
            "where ((:text is null or upper(e.annotation) like upper(concat('%', :text, '%'))) " +
            "or (:text is null or upper(e.description) like upper(concat('%', :text, '%')))) " +
            "and (:state is null or e.state = :state) " +
            "and (:categories is null or e.category.id in :categories) " +
            "and (:paid is null or e.paid = :paid) " +
            "and (cast(:rangeStart as java.time.LocalDateTime) is null or e.eventDate >= :rangeStart) " +
            "and (cast(:rangeEnd as java.time.LocalDateTime) is null or e.eventDate <= :rangeEnd)" +
            "and (e.participantLimit > e.confirmedRequests)" +
            "order by e.eventDate desc")
    List<Event> getAvailableEventsWithFiltersDateSorted(@Param("text") String text,
                                                        @Param("state") State state,
                                                        @Param("categories") List<Long> categories,
                                                        @Param("paid") Boolean paid,
                                                        @Param("rangeStart") LocalDateTime rangeStart,
                                                        @Param("rangeEnd") LocalDateTime rangeEnd,
                                                        Pageable pageable);

    @Query("select e from Event e " +
            "where ((:text is null or upper(e.annotation) like upper(concat('%', :text, '%'))) " +
            "or (:text is null or upper(e.description) like upper(concat('%', :text, '%')))) " +
            "and (:state is null or e.state = :state) " +
            "and (:categories is null or e.category.id in :categories) " +
            "and (:paid is null or e.paid = :paid) " +
            "and (cast(:rangeStart as java.time.LocalDateTime) is null or e.eventDate >= :rangeStart) " +
            "and (cast(:rangeEnd as java.time.LocalDateTime) is null or e.eventDate <= :rangeEnd)" +
            "and (e.participantLimit > e.confirmedRequests)")
    List<Event> getAvailableEventsWithFilters(@Param("text") String text,
                                              @Param("state") State state,
                                              @Param("categories") List<Long> categories,
                                              @Param("paid") Boolean paid,
                                              @Param("rangeStart") LocalDateTime rangeStart,
                                              @Param("rangeEnd") LocalDateTime rangeEnd,
                                              Pageable pageable);

    @Query("select e from Event e " +
            "where ((:text is null or upper(e.annotation) like upper(concat('%', :text, '%'))) " +
            "or (:text is null or upper(e.description) like upper(concat('%', :text, '%')))) " +
            "and (:state is null or e.state = :state) " +
            "and (:categories is null or e.category.id in :categories) " +
            "and (:paid is null or e.paid = :paid) " +
            "and (cast(:rangeStart as java.time.LocalDateTime) is null or e.eventDate >= :rangeStart) " +
            "and (cast(:rangeEnd as java.time.LocalDateTime) is null or e.eventDate <= :rangeEnd)" +
            "order by e.eventDate desc")
    List<Event> getAllEventsWithFiltersDateSorted(@Param("text") String text,
                                                  @Param("state") State state,
                                                  @Param("categories") List<Long> categories,
                                                  @Param("paid") Boolean paid,
                                                  @Param("rangeStart") LocalDateTime rangeStart,
                                                  @Param("rangeEnd") LocalDateTime rangeEnd,
                                                  Pageable pageable);

    @Query("select e from Event e " +
            "where ((:text is null or upper(e.annotation) like upper(concat('%', :text, '%'))) " +
            "or (:text is null or upper(e.description) like upper(concat('%', :text, '%')))) " +
            "and (:state is null or e.state = :state) " +
            "and (:categories is null or e.category.id in :categories) " +
            "and (:paid is null or e.paid = :paid) " +
            "and (cast(:rangeStart as java.time.LocalDateTime) is null or e.eventDate >= :rangeStart) " +
            "and (cast(:rangeEnd as java.time.LocalDateTime) is null or e.eventDate <= :rangeEnd)")
    List<Event> getAllEventsWithFilters(@Param("text") String text,
                                        @Param("state") State state,
                                        @Param("categories") List<Long> categories,
                                        @Param("paid") Boolean paid,
                                        @Param("rangeStart") LocalDateTime rangeStart,
                                        @Param("rangeEnd") LocalDateTime rangeEnd,
                                        Pageable pageable);
}
