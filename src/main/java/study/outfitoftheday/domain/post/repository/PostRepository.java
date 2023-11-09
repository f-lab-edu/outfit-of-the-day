package study.outfitoftheday.domain.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findByIdAndIsDeletedIsFalse(Long postId);

	List<Post> findAllByMemberAndIsDeletedIsFalse(@Param("member") Member member);
}
