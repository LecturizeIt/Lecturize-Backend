package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.domain.exception.CategoryTagNotFoundException;
import github.com.miralhas.lecturizebackend.domain.model.lecture.CategoryTag;
import github.com.miralhas.lecturizebackend.domain.repository.CategoryTagRepository;
import lombok.RequiredArgsConstructor;
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

}
