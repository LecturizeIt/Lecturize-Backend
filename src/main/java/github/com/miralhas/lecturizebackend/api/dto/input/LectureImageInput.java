package github.com.miralhas.lecturizebackend.api.dto.input;

import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import github.com.miralhas.lecturizebackend.domain.model.lecture.LectureImage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Getter
@Setter
public class LectureImageInput {

	@NotNull
	private MultipartFile file;

	@NotBlank
	private String description;

	public LectureImage fortmatTo(Lecture lecture) {
		var image = new LectureImage();
		image.setLecture(lecture);
		image.setDescription(description);
		image.setSize(file.getSize());
		image.setFileName(file.getOriginalFilename());
		image.setContentType(file.getContentType());
		return image;
	}

	public InputStream fileInputStream() throws IOException {
		return file.getInputStream();
	}

}
