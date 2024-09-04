package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.api.dto.input.TagInput;
import github.com.miralhas.lecturizebackend.domain.exception.BusinessException;
import github.com.miralhas.lecturizebackend.domain.exception.CategoryTagNotFoundException;
import github.com.miralhas.lecturizebackend.domain.model.lecture.CategoryTag;
import github.com.miralhas.lecturizebackend.domain.repository.CategoryTagRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryTagService {

    private final CategoryTagRepository categoryTagRepository;

    public CategoryTag getTagOrException(Long id) {
        return categoryTagRepository.findById(id)
                .orElseThrow(() -> new CategoryTagNotFoundException(id));
    }


	@Transactional
	public CategoryTag create(CategoryTag categoryTag) {
		return categoryTagRepository.save(categoryTag);
	}


	@Transactional
	public CategoryTag update(CategoryTag categoryTag, Long id) {
		var tag = getTagOrException(id);
		tag.setName(categoryTag.getName());
		return categoryTagRepository.save(tag);
	}


	@Transactional
	public void delete(Long id) {
		try {
			categoryTagRepository.deleteById(id);
			categoryTagRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException("Não é possível apagar uma tag que está vinculada a uma palestra");
		}
	}

}
