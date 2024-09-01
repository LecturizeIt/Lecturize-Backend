package github.com.miralhas.lecturizebackend.api.dto_mapper;

import github.com.miralhas.lecturizebackend.api.dto.LectureDTO;
import github.com.miralhas.lecturizebackend.api.dto.LectureSummaryDTO;
import github.com.miralhas.lecturizebackend.domain.model.lecture.CategoryTag;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureMapper {

    private final ModelMapper modelMapper;

    public LectureDTO toModel(Lecture lecture) {
        LectureDTO lectureDTO = modelMapper.map(lecture, LectureDTO.class);
        lectureDTO.setTags(lecture.getTags().stream().map(CategoryTag::getName).toList());
        return lectureDTO;
    }

    public List<LectureDTO> toCollectionModel(List<Lecture> lectures) {
        return lectures.stream().map(this::toModel).toList();
    }

    public LectureSummaryDTO toSummaryModel(Lecture lecture) {
        return modelMapper.map(lecture, LectureSummaryDTO.class);
    }

    public List<LectureSummaryDTO> toSummaryCollectionModel(List<Lecture> lectures) {
        return lectures.stream().map(this::toSummaryModel).toList();
    }
}
