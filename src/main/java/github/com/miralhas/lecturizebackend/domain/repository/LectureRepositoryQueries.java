package github.com.miralhas.lecturizebackend.domain.repository;

import github.com.miralhas.lecturizebackend.domain.model.lecture.LectureImage;

public interface LectureRepositoryQueries {

    LectureImage saveImage(LectureImage lectureImage);

    void deleteImage(LectureImage lectureImage);

}
