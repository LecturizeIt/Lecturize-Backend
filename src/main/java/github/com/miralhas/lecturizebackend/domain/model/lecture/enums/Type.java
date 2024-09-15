package github.com.miralhas.lecturizebackend.domain.model.lecture.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Type {
    ONLINE("Online", "ONLINE"),
    PRESENTIAL("Presencial", "PRESENTIAL"),
    HYBRID("Híbrido", "HYBRID");

    private final String description;
    private final String rawName;
}
