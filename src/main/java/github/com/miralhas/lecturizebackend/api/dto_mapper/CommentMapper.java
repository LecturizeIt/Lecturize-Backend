package github.com.miralhas.lecturizebackend.api.dto_mapper;

import github.com.miralhas.lecturizebackend.api.dto.CommentDTO;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Comment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentMapper {

    private final ModelMapper modelMapper;

    public CommentDTO toModel(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    public List<CommentDTO> toCollectionModel(List<Comment> comments) {
        return comments.stream().map(this::toModel).toList();
    }
}
