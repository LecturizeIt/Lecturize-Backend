package github.com.miralhas.lecturizebackend.api.dto;

import github.com.miralhas.lecturizebackend.domain.model.lecture.Type;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class LectureSummaryDTO {
    public Long id;
    public String title;
    public String description;
    public OffsetDateTime startsAt;
    public OffsetDateTime endsAt;
    public Type type;
}
