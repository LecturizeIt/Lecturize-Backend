package github.com.miralhas.lecturizebackend.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class LectureDTO {
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
    private List<String> tags;
    private Integer maximumCapacity;
    private UserSummaryDTO organizer;
}
