package github.com.miralhas.lecturizebackend.api.dto_mapper;

import github.com.miralhas.lecturizebackend.api.dto.LectureDTO;
import github.com.miralhas.lecturizebackend.api.dto.LectureSummaryDTO;
import github.com.miralhas.lecturizebackend.domain.model.lecture.CategoryTag;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import github.com.miralhas.lecturizebackend.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LectureMapper {

    private final ModelMapper modelMapper;
    private final LectureImageMapper lectureImageMapper;
    private final LectureRepository lectureRepository;

    public LectureDTO toModel(Lecture lecture) {
		return modelMapper.map(lecture, LectureDTO.class);
    }

    public List<LectureDTO> toCollectionModel(List<Lecture> lectures) {
        return lectures.stream().map(this::toModel).toList();
    }

    public LectureSummaryDTO toSummaryModel(Lecture lecture) {
        LectureSummaryDTO lectureSummaryDTO = modelMapper.map(lecture, LectureSummaryDTO.class);
        lectureSummaryDTO.setTags(getFormattedTags(lecture));
//        var image = lectureRepository.findImageByLectureId(lecture.getId()).orElse(null);
//        if (Objects.nonNull(image)) {
//            lectureSummaryDTO.setImage(lectureImageMapper.toModel(image));
//            return lectureSummaryDTO;
//        }
//        lectureSummaryDTO.setImage(null);
        return lectureSummaryDTO;
    }

    public List<LectureSummaryDTO> toSummaryCollectionModel(List<Lecture> lectures) {
        return lectures.stream().map(this::toSummaryModel).toList();
    }

    private List<String> getFormattedTags(Lecture lecture) {
        return lecture.getTags().stream().map(CategoryTag::getName).toList();
    }
    
}
