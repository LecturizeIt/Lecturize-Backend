package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.config.swagger.interfaces.SwaggerTestResource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/ip")
@RequiredArgsConstructor
public class TestResource implements SwaggerTestResource {

    @Override
    @GetMapping
    public String lecturizeIt() throws UnknownHostException {
        return InetAddress.getLocalHost().toString();
    }

}
