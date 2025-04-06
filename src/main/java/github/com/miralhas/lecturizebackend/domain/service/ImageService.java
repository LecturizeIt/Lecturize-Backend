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

import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final LectureRepository lectureRepository;
    private final ImageStorageService imageStorageService;

    public LectureImage getImageJsonOrException(String fileName) {
        return lectureRepository.findImageByFileName(fileName).orElseThrow(() -> new ImageNotFoundException(fileName));
    }

    public ResponseEntity<InputStreamResource> getImage(String fileName) {
        LectureImage lectureImage = getImageJsonOrException(fileName);
        MediaType imageMediaType = MediaType.parseMediaType(lectureImage.getContentType());
        InputStream imageInputStream = imageStorageService.retrieve(fileName);
        return ResponseEntity.ok().contentType(imageMediaType).body(new InputStreamResource(imageInputStream));
    }
}
