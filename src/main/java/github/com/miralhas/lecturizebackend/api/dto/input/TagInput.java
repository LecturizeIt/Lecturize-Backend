package github.com.miralhas.lecturizebackend.api.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagInput {
    @NotNull
    private Long id;
}
