package github.com.miralhas.lecturizebackend.domain.model.lecture;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryTagRepository extends JpaRepository<CategoryTag, Long> {
}