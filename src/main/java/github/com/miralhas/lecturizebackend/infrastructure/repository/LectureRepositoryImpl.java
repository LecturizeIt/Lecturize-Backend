package github.com.miralhas.lecturizebackend.infrastructure.repository;

import github.com.miralhas.lecturizebackend.domain.model.lecture.LectureImage;
import github.com.miralhas.lecturizebackend.domain.repository.LectureRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepositoryQueries {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional
    public LectureImage saveImage(LectureImage lectureImage) {
        return entityManager.merge(lectureImage);
    }

    @Override
    @Transactional
    public void deleteImage(LectureImage lectureImage) {
        entityManager.remove(lectureImage);
    }

}
