package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.domain.exception.CommentNotFoundException;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Comment;
import github.com.miralhas.lecturizebackend.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final AuthService authService;
    private final LectureService lectureService;
    private final CommentRepository commentRepository;

    public Comment getCommentOrException(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    public List<Comment> getAllLectureComments(Long lectureId) {
        var lecture = lectureService.getLectureOrException(lectureId);
        return commentRepository.getAllLectureComments(lecture.getId());
    }

    @Transactional
    public Comment create(Long lectureId, Comment comment, JwtAuthenticationToken authToken) {
        var lecture = lectureService.getLectureOrException(lectureId);
        var commenter = authService.findUserByEmailOrException(authToken.getName());
        comment.setLecture(lecture);
        comment.setUser(commenter);
        return commentRepository.save(comment);
    }
}
