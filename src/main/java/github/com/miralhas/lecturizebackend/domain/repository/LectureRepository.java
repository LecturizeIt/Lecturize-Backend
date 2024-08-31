package github.com.miralhas.lecturizebackend.domain.repository;

import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}