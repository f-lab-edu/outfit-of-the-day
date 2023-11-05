package study.outfitoftheday.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.member.exception.DuplicatedMemberException;
import study.outfitoftheday.domain.member.exception.NotFoundMemberException;
import study.outfitoftheday.domain.member.repository.MemberRepository;
import study.outfitoftheday.global.config.PasswordEncoder;
import study.outfitoftheday.web.member.controller.request.MemberSignUpRequest;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	public void signUp(MemberSignUpRequest request) {
		if (memberRepository.findByLoginIdOrNicknameAndIsDeletedIsFalse(request.getLoginId(), request.getNickname())
			.isPresent()) {
			throw new DuplicatedMemberException("중복 가입된 유저입니다.");
		}

		String encryptedPassword = passwordEncoder.encode(request.getPassword());
		Member createdMember = Member.builder()
			.loginId(request.getLoginId())
			.nickname(request.getNickname())
			.password(encryptedPassword)
			.build();

		memberRepository.save(createdMember);
	}

	public boolean isDuplicatedByLoginId(String loginId) {
		return memberRepository.findByLoginIdAndIsDeletedIsFalse(loginId).isPresent();
	}

	public boolean isDuplicatedByNickname(String nickname) {
		return memberRepository.findByNicknameAndIsDeletedIsFalse(nickname).isPresent();
	}

	public void withdraw(Member member) {
		member.withdrawMember();
	}

	public boolean isDeletedByLoginId(String loginId) {
		return memberRepository.findByLoginIdAndIsDeletedIsTrue(loginId).isPresent();
	}

	public Member findByLoginId(String loginId) {
		return memberRepository.findByLoginIdAndIsDeletedIsFalse(loginId)
			.orElseThrow(() -> new NotFoundMemberException("로그인 정보가 일치하지 않습니다."));
	}
}
