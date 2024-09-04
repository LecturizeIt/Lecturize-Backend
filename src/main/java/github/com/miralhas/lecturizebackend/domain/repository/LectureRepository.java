package github.com.miralhas.lecturizebackend.domain.repository;

import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import github.com.miralhas.lecturizebackend.domain.model.lecture.LectureImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryQueries {

    @Query("from LectureImage li where li.lecture.id = :id")
    Optional<LectureImage> findImageByLectureId(Long id);


}