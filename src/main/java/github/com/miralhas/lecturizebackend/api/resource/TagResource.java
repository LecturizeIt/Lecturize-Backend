package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.api.dto.input.TagInput;
import github.com.miralhas.lecturizebackend.config.swagger.interfaces.SwaggerTagResource;
import github.com.miralhas.lecturizebackend.domain.model.lecture.CategoryTag;
import github.com.miralhas.lecturizebackend.domain.repository.CategoryTagRepository;
import github.com.miralhas.lecturizebackend.domain.service.CategoryTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagResource implements SwaggerTagResource {

    private final CategoryTagRepository categoryTagRepository;
    private final CategoryTagService categoryTagService;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryTag> getAllTags() {
        return categoryTagRepository.findAll();
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryTag getTagById(@PathVariable Long id) {
        return categoryTagService.getTagOrException(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryTag createTag(@RequestBody @Valid TagInput tagInput) {
        return categoryTagService.create(tagInput.formatTo());
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryTag updateTag(@RequestBody @Valid TagInput tagInput, @PathVariable Long id) {
        return categoryTagService.update(tagInput.formatTo(), id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        categoryTagService.delete(id);
    }

}
