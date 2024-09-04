package github.com.miralhas.lecturizebackend.api.dto;

import github.com.miralhas.lecturizebackend.domain.model.Lecture;
import github.com.miralhas.lecturizebackend.domain.model.Type;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

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
    private Lecture.Status status;
    private String url;
    private String address;
    private LectureOrganizerDTO organizer;
}
