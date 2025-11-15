package github.com.miralhas.lecturizebackend.api.dto;

import github.com.miralhas.lecturizebackend.domain.model.lecture.CategoryTag;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class LectureDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String lecturer;
    private String description;
    private OffsetDateTime createdAt;
    private OffsetDateTime startsAt;
    private OffsetDateTime endsAt;
    private String type;
    private String status;
    private String url;
    private String address;
    private List<CategoryTag> tags;
    private Integer maximumCapacity;
    private UserSummaryDTO organizer;
    private LectureMetricsDTO metrics;
    private LectureImageDTO image;
    
}
