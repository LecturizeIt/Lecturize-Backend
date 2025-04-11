package github.com.miralhas.lecturizebackend.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String text;
    private UserSummaryDTO user;
    private OffsetDateTime createdAt;
}
