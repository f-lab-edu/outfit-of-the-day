package study.outfitoftheday.core.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import study.outfitoftheday.core.domain.member.entity.Member;

/*
 * @Repository
 * DB에 접근하는 데이터 엑세스 계층에 지정하는 annotation
 * 내부적으로 @Component가 포함되어 있어서 컴포넌트 스캔을 통해 빈으로 등록되는 클래스를 지정하는데 사용된다.
 * */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	/*
	 * @Query
	 * Spring Data JPA에서 제공하는 annotation
	 * JPQL이나 Native Query를 사용하여 DB Query를 정의하는데 사용한다.
	 * Repository Interface의 메서드에 적용해서 특정 쿼리를 지정할 수 있게 해준다.
	 *
	 * @Param
	 * Spring Data JPA에서 제공하는 annotation
	 * 메서드의 파라미터를 쿼리 매개변수와 매핑하기 위해 사용한다.
	 * Query annotation을 사용하여 직접 JPQL 쿼리를 정의할 때 매개변수를 지정하는 용도로 주로 사용한다.
	 * */
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

	@Query("SELECT m FROM Member m WHERE m.loginId = :loginId AND m.isDeleted = true")
	Optional<Member> findDeletedMemberByLoginId(@Param("loginId") String loginId);
}
