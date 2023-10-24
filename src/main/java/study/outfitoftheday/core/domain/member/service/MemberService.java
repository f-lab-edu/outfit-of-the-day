package study.outfitoftheday.core.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.outfitoftheday.common.config.PasswordEncoder;
import study.outfitoftheday.core.domain.member.entity.Member;
import study.outfitoftheday.core.domain.member.exception.DuplicatedMemberException;
import study.outfitoftheday.core.domain.member.exception.MismatchPasswordInSignUpException;
import study.outfitoftheday.core.domain.member.exception.NotFoundMemberException;
import study.outfitoftheday.core.domain.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	public void signUp(String loginId, String nickname, String plainPassword, String passwordConfirm) {
		if (!plainPassword.equals(passwordConfirm)) {
			throw new MismatchPasswordInSignUpException();
		}

		if (memberRepository.findByLoginIdOrNicknameAndIsDeletedIsFalse(loginId, nickname).isPresent()) {
			throw new DuplicatedMemberException();
		}

		String encryptedPassword = passwordEncoder.encode(plainPassword);
		Member createdMember = Member.builder()
			.loginId(loginId)
			.nickname(nickname)
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
