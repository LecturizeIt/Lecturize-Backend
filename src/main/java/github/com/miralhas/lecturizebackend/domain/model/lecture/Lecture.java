package github.com.miralhas.lecturizebackend.domain.model.lecture;

import github.com.miralhas.lecturizebackend.domain.exception.BusinessException;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import github.com.miralhas.lecturizebackend.domain.model.lecture.enums.Status;
import github.com.miralhas.lecturizebackend.domain.model.lecture.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

    @Column(nullable = false, columnDefinition = "TEXT")
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

    private Integer maximumCapacity;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(nullable = false)
    private User organizer;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "lecture_participants", joinColumns = @JoinColumn(name = "lecture_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "lecture_tags", joinColumns = @JoinColumn(name = "lecture_id"), inverseJoinColumns = @JoinColumn(name = "category_tag_id"))
    private Set<CategoryTag> tags = new HashSet<>();

    public void validateType() {
        switch (type) {
            case ONLINE -> onlineValidations();
            case PRESENTIAL -> presentialValidations();
            case HYBRID -> hybridValidations();
        }
    }

    private void hybridValidations() {
        if (!StringUtils.hasText(url) || !StringUtils.hasText(address)) {
            throw new BusinessException("Palestra do tipo [HYBRID] necessitam de um URL e Endereço. Adicione o campo 'url' e 'address' no corpo da requisição e seus respectivos valores");
        }
        validateCapacity();

    }

    private void onlineValidations() {
        if (StringUtils.hasText(address))
            throw new BusinessException("Palestra do tipo [ONLINE] não pode possuir endereço");
        if (!StringUtils.hasText(url))
            throw new BusinessException("Palestra do tipo [ONLINE] necessita de um URL. Adicione o campo 'url' no corpo da requisição e seu respectivo valor");
    }

    private void presentialValidations() {
        if (StringUtils.hasText(url)) throw new BusinessException("Palestra do tipo [PRESENTIAL] não pode possuir URL");
        if (!StringUtils.hasText(address))
            throw new BusinessException("Palestra do tipo [PRESENTIAL] necessita de um endereço. Adicione o campo 'address' no corpo da requisição e seu respectivo valor");
        validateCapacity();
    }

    private void validateCapacity() {
        if (Objects.isNull(maximumCapacity)) {
            throw new BusinessException(String.format("Palestra do tipo [%s] precisa especificar a capacidade máxima de visitantes no local de palestra." + " Adicione o campo 'maximumCapacity' no corpo da requisição e seu respectivo valor", type.getRawName()));
        }
    }

    public String getTypeFormatted() {
        return type.getDescription();
    }

    public String getStatusFormatted() {
        return status.getDescription();
    }

    public void validateDateRange() {
        var isNotAfter = endsAt.isBefore(startsAt);
        if (isNotAfter)
            throw new BusinessException("O horário do fim da palestra 'endsAt' não pode ser anterior ao horário de início 'startsAt'." + " Verifique os valores fornecidos e tente novamente.");
    }

    public void changeStatus() {
        var now = OffsetDateTime.now();
        System.out.println(status);
        if (now.isAfter(startsAt) && status == Status.SCHEDULED) {
            status = Status.IN_PROGRESS;
        }

        if (endsAt.isBefore(now) && status == Status.IN_PROGRESS) {
            status = Status.COMPLETED;
        }
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
