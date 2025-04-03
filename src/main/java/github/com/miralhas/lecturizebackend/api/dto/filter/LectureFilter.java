package github.com.miralhas.lecturizebackend.api.dto.filter;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class LectureFilter {
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime createdAfter;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime createdBefore;

	private String q;
	private String lecturer;
	private List<String> tags;
}


