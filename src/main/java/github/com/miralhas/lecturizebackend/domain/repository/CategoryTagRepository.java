package github.com.miralhas.lecturizebackend.domain.repository;

import github.com.miralhas.lecturizebackend.domain.model.lecture.CategoryTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryTagRepository extends JpaRepository<CategoryTag, Long> {
}