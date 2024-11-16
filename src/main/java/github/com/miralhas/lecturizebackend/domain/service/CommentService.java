package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.domain.exception.BusinessException;
import github.com.miralhas.lecturizebackend.domain.exception.CommentNotFoundException;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Comment;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import github.com.miralhas.lecturizebackend.domain.model.lecture.enums.Status;
import github.com.miralhas.lecturizebackend.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
        validateLectureStatus(lecture);
        var commenter = authService.findUserByEmailOrException(authToken.getName());
        comment.setLecture(lecture);
        comment.setUser(commenter);
        return commentRepository.save(comment);
    }

    @Transactional
    public void delete(Long id, JwtAuthenticationToken authToken) {
        var user = authService.findUserByEmailOrException(authToken.getName());
        var comment = getCommentOrException(id);
        validateCommenter(user, comment);
        commentRepository.deleteById(comment.getId());
    }

    private void validateCommenter(User user, Comment comment) {
        if (user.isAdmin() || Objects.equals(user, comment.getUser())) return;
        throw new AccessDeniedException("Acesso negado. " +
                "Apenas o organizador ou usuários autorizados podem fazer alterações a este comentário.");
    }

    private void validateLectureStatus(Lecture lecture) {
        if (!Objects.equals(lecture.getStatus(), Status.COMPLETED)) {
            throw new BusinessException("Não é possível adicionar comentários a uma palestra que não foi concluída.");
        }
    }
}
