package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.domain.model.lecture.CategoryTag;
import github.com.miralhas.lecturizebackend.domain.repository.CategoryTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagResource {

	private final CategoryTagRepository categoryTagRepository;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<CategoryTag> getAllTags() {
		return categoryTagRepository.findAll();
	};
}
