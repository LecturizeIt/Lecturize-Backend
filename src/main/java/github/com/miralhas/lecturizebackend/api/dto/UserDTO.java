package github.com.miralhas.lecturizebackend.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String telefone;
    private List<String> roles;
}
