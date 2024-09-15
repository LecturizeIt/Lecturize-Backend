package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.domain.exception.ImageNotFoundException;
import github.com.miralhas.lecturizebackend.domain.model.lecture.LectureImage;
import github.com.miralhas.lecturizebackend.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureImageService {

	private final LectureRepository lectureRepository;
	private final ImageStorageService imageStorageService;

	public ResponseEntity<InputStreamResource> getImage(Long id, String acceptHeader)
			throws HttpMediaTypeNotAcceptableException {
		LectureImage lectureImage = getImageJsonOrException(id);

		List<MediaType> providedMediaTypes = MediaType.parseMediaTypes(acceptHeader);
		MediaType imageMediaType = MediaType.parseMediaType(lectureImage.getContentType());
		checkMediaTypeCompability(imageMediaType, providedMediaTypes);
		InputStream imageInputStream = imageStorageService.retrieve(lectureImage.getFileName());

		return ResponseEntity.ok()
				.contentType(imageMediaType)
				.body(new InputStreamResource(imageInputStream));
	}


	public LectureImage getImageJsonOrException(Long id) {
		return lectureRepository.findImageByLectureId(id)
				.orElseThrow(() -> new ImageNotFoundException(id));
	}


	@Transactional
	public LectureImage save(LectureImage lectureImage, InputStream fileInputStream) {
		String newImageName = imageStorageService.generateFileName(lectureImage.getFileName());
		String existingFileName = null;
		lectureImage.setFileName(newImageName);

		var existingImage = lectureRepository.findImageByLectureId(lectureImage.getLectureId());
		if (existingImage.isPresent()) {
			existingFileName = existingImage.get().getFileName();
			lectureRepository.deleteImage(existingImage.get());
			lectureRepository.flush();
		}

		lectureImage = lectureRepository.saveImage(lectureImage);
		lectureRepository.flush();

		var newImage = ImageStorageService.NewImage.builder()
				.fileName(lectureImage.getFileName())
				.inputStream(fileInputStream)
				.build();

		imageStorageService.replace(newImage, existingFileName);
		return lectureImage;
	}


	@Transactional
	public void delete(Long id) {
		LectureImage lectureImage = getImageJsonOrException(id);
		lectureRepository.deleteImage(lectureImage);
		imageStorageService.remove(lectureImage.getFileName());
	}


	private void checkMediaTypeCompability(MediaType imageMediaType, List<MediaType> providedMediaTypes)
			throws HttpMediaTypeNotAcceptableException {
		boolean compativel = providedMediaTypes.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(imageMediaType));
		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(List.of(imageMediaType));
		}
	}

}
