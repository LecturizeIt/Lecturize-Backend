package github.com.miralhas.lecturizebackend.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureMetricsDTO {
    private Integer timesVisited;
    private Integer timesShared;
}
