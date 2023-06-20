package ru.practicum.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static ru.practicum.util.Util.YYYY_MM_DD_HH_MM_SS;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String authorName;
    @NotBlank
    @Length(max = 1000)
    private String text;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime created;
}
