package github.com.miralhas.lecturizebackend.api.dto_mapper;

import github.com.miralhas.lecturizebackend.api.dto.input.CommentInput;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Comment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentUnmapper {
    private final ModelMapper modelMapper;

    public Comment toDomainObject(CommentInput commentInput) {
        return modelMapper.map(commentInput, Comment.class);
    }
}
