package github.com.miralhas.lecturizebackend.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureImageDTO {

    private String fileName;
    private String description;
    private String contentType;
    private Long size;

}
