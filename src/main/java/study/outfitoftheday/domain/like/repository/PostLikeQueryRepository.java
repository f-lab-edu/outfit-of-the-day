package study.outfitoftheday.domain.like.repository;

import static study.outfitoftheday.domain.like.entity.QPostLike.*;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import study.outfitoftheday.domain.post.entity.Post;

@Repository

public class PostLikeQueryRepository {
	private final JPAQueryFactory queryFactory;

	public PostLikeQueryRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	public Long findCountByPost(Post post) {
		if (post == null) {
			return 0L;
		}

		return queryFactory
			.select(postLike.count())
			.from(postLike)
			.where(
				postEq(post),
				isDeletedEqFalse()
			).fetchOne();
	}

	private BooleanExpression postEq(Post post) {
		return postLike.post.eq(post);
	}

	private BooleanExpression isDeletedEqFalse() {
		return postLike.isDeleted.isFalse();
	}
}
