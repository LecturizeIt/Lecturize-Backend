package github.com.miralhas.lecturizebackend.domain.repository;

import github.com.miralhas.lecturizebackend.domain.model.lecture.Comment;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("from Comment c where c.lecture.id = :id")
    List<Comment> getAllLectureComments(Long id);

}