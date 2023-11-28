package study.outfitoftheday.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import study.outfitoftheday.domain.like.entity.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
