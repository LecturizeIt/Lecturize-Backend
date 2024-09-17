package github.com.miralhas.lecturizebackend.api.dto.input;

import github.com.miralhas.lecturizebackend.domain.model.lecture.CategoryTag;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagInput {

    @NotBlank
    private String name;

    public CategoryTag formatTo() {
        var tag = new CategoryTag();
        tag.setName(name);
        return tag;
    }

}
