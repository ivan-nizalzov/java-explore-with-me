package ru.practicum.service.compilation;

import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto dto);

    void deleteCompilationById(Long compId);

    CompilationDto updateCompilation(Long compId, UpdateCompilationRequest dto);

    CompilationDto getCompilationById(Long compId);

    List<CompilationDto> getAllCompilationsWithFilters(Boolean pinned, Integer from, Integer size);

}
