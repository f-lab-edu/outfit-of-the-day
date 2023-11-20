package study.outfitoftheday.domain.member.repository;

import static study.outfitoftheday.domain.member.entity.QMember.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import study.outfitoftheday.domain.member.entity.Member;

@Repository
public class MemberQueryRepository {
	private final JPAQueryFactory queryFactory;
	
	public MemberQueryRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}
	
	private BooleanExpression idEq(Long memberId) {
		if (memberId == null) {
			return null;
		}
		return member.id.eq(memberId);
	}
	
	private BooleanExpression nicknameEq(String nickname) {
		if (nickname == null) {
			return null;
		}
		return member.nickname.eq(nickname);
	}
	
	private BooleanExpression loginIdEq(String loginId) {
		
		if (loginId == null) {
			return null;
		}
		return member.loginId.eq(loginId);
		
	}
	
	private BooleanExpression isDeletedEqFalse() {
		return member.isDeleted.isFalse();
	}
	
	public Optional<Member> findByLoginId(String loginId) {
		if (loginId == null) {
			return Optional.empty();
		}
		
		return Optional.ofNullable(
			queryFactory
				.selectFrom(member)
				.where(
					loginIdEq(loginId),
					isDeletedEqFalse()
				)
				.fetchOne());
	}
	
	public Optional<Member> findByNickname(String nickname) {
		if (nickname == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(
			queryFactory
				.selectFrom(member)
				.where(
					nicknameEq(nickname),
					isDeletedEqFalse()
				)
				.fetchOne()
		);
	}
	
	public Optional<Member> findByLoginIdOrNickname(String loginId, String nickname) {
		if (loginId == null || nickname == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(
			queryFactory
				.selectFrom(member)
				.where(
					loginIdEq(loginId).or(nicknameEq(nickname)),
					isDeletedEqFalse()
				)
				.fetchOne()
		);
	}
	
	public Optional<Member> findById(Long memberId) {
		if (memberId == null) {
			return Optional.empty();
		}
		
		return Optional.ofNullable(
			queryFactory
				.selectFrom(member)
				.where(
					idEq(memberId),
					isDeletedEqFalse()
				)
				.fetchOne()
		);
	}
}
