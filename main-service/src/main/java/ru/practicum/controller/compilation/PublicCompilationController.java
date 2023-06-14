package ru.practicum.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.service.compilation.CompilationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationService compilationService;

    @GetMapping("/{compilationId}")
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable("compilationId") Long compilationId) {
        log.info("Get compilation with id={}", compilationId);
        return ResponseEntity.ok(compilationService.getCompilationById(compilationId));
    }

    @GetMapping
    public ResponseEntity<List<CompilationDto>> getAllCompilationsWithFilters(
            @RequestParam(name = "pinned", required = false) Boolean pinned,
            @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        log.info("Get compilations with pined {}, from {}, size {}", pinned, from, size);
        return ResponseEntity.ok(compilationService.getAllCompilationsWithFilters(pinned, from, size));
    }
}
