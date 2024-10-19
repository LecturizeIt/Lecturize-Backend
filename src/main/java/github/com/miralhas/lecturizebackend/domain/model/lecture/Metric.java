package github.com.miralhas.lecturizebackend.domain.model.lecture;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity(name = "metrics")
@RequiredArgsConstructor
public class Metric {

    @Id
    @Column(name = "lecture_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Lecture lecture;

    private int timesVisited;
    private int timesShared;

    public void timesVisitedPlusOne() {
        ++timesVisited;
    }

    public void timesSharedPlusOne() {
        ++timesShared;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Metric metric = (Metric) o;
        return getId() != null && Objects.equals(getId(), metric.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
