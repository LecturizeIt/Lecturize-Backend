package github.com.miralhas.lecturizebackend.domain.model.lecture;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@ToString
public class LectureImage {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "lecture_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Lecture lecture;

    private String fileName;
    private String description;
    private String contentType;
    private Long size;

    public Long getSolarPanelId() {
        if (lecture != null) return lecture.getId();
        return null;
    }

}
