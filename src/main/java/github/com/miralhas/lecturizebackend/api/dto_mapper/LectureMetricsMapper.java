package github.com.miralhas.lecturizebackend.api.dto_mapper;

import github.com.miralhas.lecturizebackend.api.dto.LectureMetricsDTO;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Metric;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureMetricsMapper {

    private final ModelMapper modelMapper;

    public LectureMetricsDTO toModel(Metric metric) {
        return modelMapper.map(metric, LectureMetricsDTO.class);
    }
}
