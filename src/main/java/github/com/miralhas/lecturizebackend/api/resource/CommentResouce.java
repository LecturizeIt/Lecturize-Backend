package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.api.dto.CommentDTO;
import github.com.miralhas.lecturizebackend.api.dto.input.CommentInput;
import github.com.miralhas.lecturizebackend.api.dto_mapper.CommentMapper;
import github.com.miralhas.lecturizebackend.api.dto_mapper.CommentUnmapper;
import github.com.miralhas.lecturizebackend.domain.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures/{lectureId}/comments")
@RequiredArgsConstructor
public class CommentResouce {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final CommentUnmapper commentUnmapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getAllLectureComments(@PathVariable Long lectureId) {
        return commentMapper.toCollectionModel(commentService.getAllLectureComments(lectureId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(@PathVariable Long lectureId, @RequestBody @Valid CommentInput commentInput, JwtAuthenticationToken authToken) {
        var comment = commentUnmapper.toDomainObject(commentInput);
        return commentMapper.toModel(commentService.create(lectureId, comment, authToken));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id, JwtAuthenticationToken authToken, @PathVariable Long lectureId) {
        commentService.delete(id, authToken);
    }
}
