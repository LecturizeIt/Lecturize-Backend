package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.api.dto.LectureImageDTO;
import github.com.miralhas.lecturizebackend.api.dto.input.LectureImageInput;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureImageMapper;
import github.com.miralhas.lecturizebackend.domain.model.lecture.LectureImage;
import github.com.miralhas.lecturizebackend.domain.service.LectureImageService;
import github.com.miralhas.lecturizebackend.domain.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectures/{id}/image")
public class LectureImageResource {

	private final LectureService lectureService;
	private final LectureImageMapper lectureImageMapper;
	private final LectureImageService lectureImageService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public LectureImageDTO getLectureImageJson(@PathVariable Long id) {
		LectureImage lectureImage = lectureImageService.getImageJsonOrException(id);
		return lectureImageMapper.toModel(lectureImage);
	}


	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<InputStreamResource> getLectureImage(
			@PathVariable Long id, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		return lectureImageService.getImage(id, acceptHeader);
	}


	@ResponseStatus(HttpStatus.OK)
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public LectureImageDTO updateLectureImage(@PathVariable Long id, @Valid LectureImageInput lectureImageInput) throws IOException {
		LectureImage lectureImage = lectureImageInput.fortmatTo(lectureService.getLectureOrException(id));
		lectureImage = lectureImageService.save(lectureImage, lectureImageInput.fileInputStream());
		return lectureImageMapper.toModel(lectureImage);
	}


	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLectureImage(@PathVariable Long id) {
		lectureImageService.delete(id);
	}
}
