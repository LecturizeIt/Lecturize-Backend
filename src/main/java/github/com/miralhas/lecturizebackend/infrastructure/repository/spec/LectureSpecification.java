package github.com.miralhas.lecturizebackend.infrastructure.repository.spec;

import github.com.miralhas.lecturizebackend.api.dto.filter.LectureFilter;
import github.com.miralhas.lecturizebackend.domain.model.lecture.CategoryTag;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;

public class LectureSpecification {
	public static Specification<Lecture> withFilter(LectureFilter filter) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();

			if (hasText(filter.getQ())) {
				predicates.add(builder.or(
				    builder.like(root.get("title"), "%" + filter.getQ() + "%"),
					builder.like(root.get("description"), "%" + filter.getQ() + "%")
				));
			}

			if (hasText(filter.getLecturer())) {
				predicates.add(builder.like(root.get("lecturer"), "%" + filter.getLecturer() + "%"));
			}

			if (hasText(filter.getUser())) {
				predicates.add(builder.equal(root.get("organizer").get("email"), filter.getUser()));
			}

			if (Objects.nonNull(filter.getTags()) && !filter.getTags().isEmpty() && Objects.nonNull(query)) {
				Join<Lecture, CategoryTag> tagsJoin = root.join("tags");
				Predicate tagPredicate = tagsJoin.get("name").in(filter.getTags());
				query.groupBy(root.get("id"));
				query.having(builder.equal(builder.countDistinct(tagsJoin.get("id")), filter.getTags().size()));
				predicates.add(tagPredicate);
			}

			return builder.and(predicates.toArray(new Predicate[]{}));
		};
	}
}
