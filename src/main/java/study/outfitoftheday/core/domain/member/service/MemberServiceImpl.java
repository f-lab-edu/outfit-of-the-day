package study.outfitoftheday.core.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.outfitoftheday.core.domain.member.entity.Member;
import study.outfitoftheday.core.domain.member.exception.DuplicatedMemberException;
import study.outfitoftheday.core.domain.member.exception.MismatchPasswordInSignUpException;
import study.outfitoftheday.core.domain.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	@Override
	@Transactional
	public void signUp(
		String loginId,
		String nickname,
		String plainPassword,
		String passwordConfirm
	) {

		if (!plainPassword.equals(passwordConfirm)) {
			throw new MismatchPasswordInSignUpException();
		}

		if (memberRepository.findByMemberByLoginIdOrNickname(loginId, nickname).isPresent()) {
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

	@Override
	public boolean isDuplicatedMemberByLoginId(String loginId) {
		return memberRepository.findByLoginId(loginId).isPresent();
	}

	@Override
	public boolean isDuplicatedMemberByNickname(String nickname) {
		return memberRepository.findByNickname(nickname).isPresent();
	}

	@Override
	public void withdrawMember(Member member) {
		member.deleteMember();
	}
}
