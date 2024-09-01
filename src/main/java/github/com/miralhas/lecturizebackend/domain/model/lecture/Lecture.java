package github.com.miralhas.lecturizebackend.domain.model.lecture;

import github.com.miralhas.lecturizebackend.domain.exception.BusinessException;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String lecturer;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime startsAt;

    @Column(nullable = false)
    private OffsetDateTime endsAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Status status = Status.SCHEDULED;

    private String address;

    private String url;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(nullable = false)
    private User organizer;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "lecture_tags",
            joinColumns = @JoinColumn(name = "lecture_id"),
            inverseJoinColumns = @JoinColumn(name = "category_tag_id")
    )
    private Set<CategoryTag> tags = new HashSet<>();

    private boolean isOnline() {
        return type.equals(Type.ONLINE);
    }

    private boolean isPresential() {
        return type.equals(Type.PRESENTIAL);
    }


    public void onlineValidations() {
        if (isPresential()) return;
        if (StringUtils.hasText(address)) throw new BusinessException("Palestra do tipo [ONLINE] não pode possuir endereço");
        if (!StringUtils.hasText(url)) throw new BusinessException("Palestra do tipo [ONLINE] necessita de um URL. Adicione o campo 'url' no corpo da requisição");
    }

    public void presentialValidations() {
        if (isOnline()) return;
        if (StringUtils.hasText(url)) throw new BusinessException("Palestra do tipo [PRESENTIAL] não pode possuir URL");
        if (!StringUtils.hasText(address)) throw new BusinessException("Palestra do tipo [PRESENTIAL] necessita de um endereço. Adicione o campo 'address' no corpo da requisição");
    }

    public void validateDateRange() {
        var isNotAfter = endsAt.isBefore(startsAt);
        if (isNotAfter) throw new BusinessException(
                "O horário do fim da palestra 'endsAt' não pode ser anterior ao horário de início 'startsAt'." +
                        " Verifique os valores fornecidos e tente novamente."
        );
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Lecture lecture = (Lecture) o;
        return getId() != null && Objects.equals(getId(), lecture.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}

