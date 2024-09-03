package github.com.miralhas.lecturizebackend.api.dto;

import github.com.miralhas.lecturizebackend.domain.model.lecture.enums.Status;
import github.com.miralhas.lecturizebackend.domain.model.lecture.enums.Type;
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
    private OffsetDateTime startsAt;
    private OffsetDateTime endsAt;
    private Type type;
    private Status status;
    private String url;
    private String address;
    private List<String> tags;
    private LectureOrganizerDTO organizer;
}
