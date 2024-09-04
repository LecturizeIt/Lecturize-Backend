package github.com.miralhas.lecturizebackend.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Type {
    ONLINE("Online"),
    PRESENTIAL("Presential");

    private final String description;
}
