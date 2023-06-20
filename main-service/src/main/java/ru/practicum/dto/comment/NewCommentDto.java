package ru.practicum.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class NewCommentDto {
    private Long id;
    @NotBlank
    @Length(max = 1000)
    private String text;
}
