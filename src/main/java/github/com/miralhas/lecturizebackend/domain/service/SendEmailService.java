package github.com.miralhas.lecturizebackend.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

public interface SendEmailService {
    void send(Message message);

    @Getter
    @Builder
    class Message {

        @Singular
        private Set<String> recipients;

        private String subject;
        private String body;

        @Singular
        private Map<String, Object> models;
    }
}
