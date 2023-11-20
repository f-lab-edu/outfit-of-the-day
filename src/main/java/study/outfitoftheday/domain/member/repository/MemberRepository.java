package study.outfitoftheday.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import study.outfitoftheday.domain.member.entity.Member;

/*
 * @Repository
 * DB에 접근하는 데이터 엑세스 계층에 지정하는 annotation
 * 내부적으로 @Component가 포함되어 있어서 컴포넌트 스캔을 통해 빈으로 등록되는 클래스를 지정하는데 사용된다.
 * */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
