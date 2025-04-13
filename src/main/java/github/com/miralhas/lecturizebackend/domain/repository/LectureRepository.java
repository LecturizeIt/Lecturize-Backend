package github.com.miralhas.lecturizebackend.domain.repository;

import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import github.com.miralhas.lecturizebackend.domain.model.lecture.LectureImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long>,
        LectureRepositoryQueries, JpaSpecificationExecutor<Lecture>
{

    @Query("from LectureImage li where li.lecture.id = :id")
    Optional<LectureImage> findImageByLectureId(Long id);

    @Query("from LectureImage li where li.fileName = :fileName")
    Optional<LectureImage> findImageByFileName(String fileName);

    @Query("from Lecture l JOIN l.participants lp where lp.id = :userId")
    List<Lecture> findLecturesByParticipantId(Long userId);

    @Query("from Lecture l where l.status = 'SCHEDULED' or l.status = 'IN_PROGRESS'")
    List<Lecture> findAllLecturesScheduling();

}
