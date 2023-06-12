package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.UpdateEventDto;
import ru.practicum.service.event.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Util.YYYY_MM_DD_HH_MM_SS;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventsService;

    @GetMapping
    public ResponseEntity<List<EventFullDto>> searchEventsForAdmin(
            @RequestParam(name = "users", required = false) List<Long> users,
            @RequestParam(name = "states", required = false) List<String> states,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "rangeStart", required = false) @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS)
            LocalDateTime rangeStart,
            @RequestParam(name = "rangeEnd", required = false) @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS)
            LocalDateTime rangeEnd,
            @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        log.info("Get events from users {}, states {}, categories {}, rangeStart {}, rangeEnd {}, from {}, size {}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        return ResponseEntity.ok(eventsService.searchEventsForAdminWithFiltres(users, states, categories, rangeStart, rangeEnd, from, size));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateByAdmin(@PathVariable Long eventId,
                                                      @RequestBody @Valid UpdateEventDto dto) {
        log.info("Update event by admin with id={}, dto={}", eventId, dto);
        return ResponseEntity.ok(eventsService.updateByAdmin(eventId, dto));
    }
}
