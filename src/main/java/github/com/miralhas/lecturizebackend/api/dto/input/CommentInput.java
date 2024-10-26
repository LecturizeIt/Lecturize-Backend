package github.com.miralhas.lecturizebackend.api.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentInput {
    @NotBlank
    private String text;
}
