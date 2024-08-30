package github.com.miralhas.lecturizebackend.api.dto_mapper;

import github.com.miralhas.lecturizebackend.api.dto.LectureDTO;
import github.com.miralhas.lecturizebackend.domain.model.Lecture;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureMapper {

    private final ModelMapper modelMapper;

    public LectureDTO toModel(Lecture lecture) {
        return modelMapper.map(lecture, LectureDTO.class);
    }

    public List<LectureDTO> toCollectionModel(List<Lecture> lectures) {
        return lectures.stream().map(this::toModel).toList();
    }
}
