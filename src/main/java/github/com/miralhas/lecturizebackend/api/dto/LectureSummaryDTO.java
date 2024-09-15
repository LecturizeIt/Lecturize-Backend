package github.com.miralhas.lecturizebackend.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class LectureSummaryDTO {
    private Long id;
    private String title;
    private String lecturer;
    private String description;
    private OffsetDateTime createdAt;
    private OffsetDateTime startsAt;
    private OffsetDateTime endsAt;
    private String type;
    private String status;
    private List<String> tags;
}
