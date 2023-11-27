package study.outfitoftheday.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.member.exception.DuplicatedMemberException;
import study.outfitoftheday.domain.member.exception.NotFoundMemberException;
import study.outfitoftheday.domain.member.repository.MemberQueryRepository;
import study.outfitoftheday.domain.member.repository.MemberRepository;
import study.outfitoftheday.global.config.PasswordEncoder;
import study.outfitoftheday.web.member.controller.request.MemberSignUpRequest;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final MemberQueryRepository memberQueryRepository;

	public void signUp(MemberSignUpRequest request) {
		if (memberQueryRepository.findByLoginIdOrNickname(request.getLoginId(), request.getNickname()).isPresent()) {
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
		return memberQueryRepository.findByLoginId(loginId).isPresent();
	}

	public boolean isDuplicatedByNickname(String nickname) {
		return memberQueryRepository.findByNickname(nickname).isPresent();
	}

	public void withdraw(Member member) {
		member.withdrawMember();
	}

	public Member findById(Long memberId) {
		return memberQueryRepository.findById(memberId)
			.orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));
	}
}
