package study.outfitoftheday.core.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import study.outfitoftheday.core.domain.member.entity.Member;

/*
 * Bean에 Repository를 등록한다.
 * 따로 Repository Annotation을 생략해도 되지만 명시적으로 등록
 *
 * */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("SELECT m FROM Member m WHERE m.loginId = :loginId AND m.isDeleted = false")
	Optional<Member> findByLoginId(@Param("loginId") String loginId);

	@Query("SELECT m FROM Member m WHERE m.nickname = :nickname AND m.isDeleted = false")
	Optional<Member> findByNickname(@Param("nickname") String nickname);

	@Query("SELECT m FROM Member m WHERE  m.loginId = :loginId AND m.nickname = :nickname AND m.isDeleted = false")
	Optional<Member> findByMemberByLoginIdOrNickname(@Param("loginId") String loginId,
		@Param("nickname") String nickname);

	@Override
	@Query("SELECT m FROM Member m WHERE m.id = :memberId AND m.isDeleted = false")
	Optional<Member> findById(@Param("memberId") Long memberId);
}
