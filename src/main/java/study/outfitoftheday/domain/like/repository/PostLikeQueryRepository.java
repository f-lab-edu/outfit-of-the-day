package study.outfitoftheday.domain.like.repository;

import static study.outfitoftheday.domain.like.entity.QPostLike.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import study.outfitoftheday.domain.like.entity.PostLike;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.post.entity.Post;

@Repository

public class PostLikeQueryRepository {
	private final JPAQueryFactory queryFactory;

	public PostLikeQueryRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	public Optional<PostLike> findByMemberAndPostIncludeDeleted(Member member, Post post) {
		if (member == null || post == null) {
			return Optional.empty();
		}

		return Optional.ofNullable(
			queryFactory
				.selectFrom(postLike)
				.where(
					memberEq(member),
					postEq(post)
				).fetchOne()
		);
	}

	private BooleanExpression postIdEq(Long postId) {
		return postLike.post.id.eq(postId);
	}

	private BooleanExpression memberEq(Member member) {
		return postLike.member.eq(member);
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
