package study.outfitoftheday.domain.post.repository;

import static study.outfitoftheday.domain.post.entity.QPost.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import study.outfitoftheday.domain.post.entity.Post;

@Repository
public class PostQueryRepository {

	private final JPAQueryFactory queryFactory;

	public PostQueryRepository(EntityManager entityManager) {
		queryFactory = new JPAQueryFactory(entityManager);
	}

	public Optional<Post> findById(Long postId) {
		if (postId == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(
			queryFactory
				.selectFrom(post)
				.where(
					idEq(postId),
					isDeletedEqFalse()
				)
				.fetchOne()
		);
	}

	private BooleanExpression idEq(Long postId) {
		if (postId == null) {
			return null;
		}
		return post.id.eq(postId);
	}

	private BooleanExpression isDeletedEqFalse() {
		return post.isDeleted.isFalse();
	}
}
