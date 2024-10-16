package github.com.miralhas.lecturizebackend.domain.model.lecture.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Status {

    IN_PROGRESS("Em andamento"),
    COMPLETED("Finalziada"),
    CANCELED("Cancelada"),
    SCHEDULED("Agendada");

    private final String description;
    
}
