package github.com.miralhas.lecturizebackend.api.dto_mapper;

import github.com.miralhas.lecturizebackend.api.dto.input.LectureInput;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class LectureUnmapper {

    private final ModelMapper modelMapper;

    public Lecture toDomainObject(LectureInput lectureInput) {
        Lecture lecture = modelMapper.map(lectureInput, Lecture.class);
        if (lecture.getTags() == null) lecture.setTags(Set.of());
        return lecture;
    }

    public void copyToDomainObject(LectureInput lectureInput, Lecture lecture) {
        lecture.setTags(null);
        if (lectureInput.getTags() == null) lectureInput.setTags(Set.of());
        modelMapper.map(lectureInput, lecture);
    }
}
