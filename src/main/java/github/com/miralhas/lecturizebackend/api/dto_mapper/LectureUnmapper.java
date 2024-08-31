package github.com.miralhas.lecturizebackend.api.dto_mapper;

import github.com.miralhas.lecturizebackend.api.dto.input.LectureInput;
import github.com.miralhas.lecturizebackend.domain.model.Lecture;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureUnmapper {

    private final ModelMapper modelMapper;

    public Lecture toDomainObject(LectureInput lectureInput) {
        return modelMapper.map(lectureInput, Lecture.class);
    }

    public void copyToDomainObject(LectureInput lectureInput, Lecture lecture) {
        modelMapper.map(lectureInput, lecture);
    }
}
