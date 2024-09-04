package github.com.miralhas.lecturizebackend.api.dto.input;

import github.com.miralhas.lecturizebackend.config.validation.EnumPattern;
import github.com.miralhas.lecturizebackend.domain.model.lecture.enums.Type;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
public class LectureInput {

    @NotBlank
    private String title;

    @NotBlank
    private String lecturer;

    @NotBlank
    private String description;

    @NotNull
    @FutureOrPresent
    private OffsetDateTime startsAt;

    @NotNull
    @Future
    private OffsetDateTime endsAt;

    @NotBlank
    @EnumPattern(enumClass = Type.class)
    private String type;

    @URL
    private String url;

    private String address;

    @Valid
    private Set<TagInput> tags;
}
