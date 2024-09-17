package github.com.miralhas.lecturizebackend.api.dto_mapper;

import github.com.miralhas.lecturizebackend.api.dto.LectureImageDTO;
import github.com.miralhas.lecturizebackend.domain.model.lecture.LectureImage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureImageMapper {

    private final ModelMapper modelMapper;

    public LectureImageDTO toModel(LectureImage lectureImage) {
        return modelMapper.map(lectureImage, LectureImageDTO.class);
    }

}
