package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageResource {

    private final ImageService imageService;

    @GetMapping(value = "/{fileName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> getLectureImage(
            @PathVariable String fileName
    ) {
        return imageService.getImage(fileName);
    }

}
