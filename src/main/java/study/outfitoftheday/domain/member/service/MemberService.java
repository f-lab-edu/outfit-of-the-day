package study.outfitoftheday.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.member.exception.DuplicatedMemberException;
import study.outfitoftheday.domain.member.exception.MismatchPasswordInSignUpException;
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
		if (!request.getPassword().equals(request.getPasswordConfirm())) {
			throw new MismatchPasswordInSignUpException();
		}

		if (memberRepository.findByLoginIdOrNicknameAndIsDeletedIsFalse(request.getLoginId(), request.getNickname())
			.isPresent()) {
			throw new DuplicatedMemberException();
		}

		String encryptedPassword = passwordEncoder.encode(request.getPassword());
		Member createdMember = Member.builder()
			.loginId(request.getLoginId())
			.nickname(request.getNickname())
			.password(encryptedPassword)
			.build();

		memberRepository.save(createdMember);
	}

	public boolean isDuplicatedMemberByLoginId(String loginId) {
		return memberRepository.findByLoginIdAndIsDeletedIsFalse(loginId).isPresent();
	}

	public boolean isDuplicatedMemberByNickname(String nickname) {
		return memberRepository.findByNicknameAndIsDeletedIsFalse(nickname).isPresent();
	}

	public void withdrawMember(Member member) {
		member.withdrawMember();
	}

	public boolean isDeletedMemberByLoginId(String loginId) {
		return memberRepository.findByLoginIdAndIsDeletedIsTrue(loginId).isPresent();
	}

	public Member findMemberByLoginId(String loginId) {
		return memberRepository.findByLoginIdAndIsDeletedIsFalse(loginId).orElseThrow(NotFoundMemberException::new);
	}
}
