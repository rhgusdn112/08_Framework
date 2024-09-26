package edu.kh.project.member.service;

import edu.kh.project.member.dto.Member;

public interface MemberService {
	
	/**
	 * @param memberEmail
	 * @param memberPw
	 * @returnloginMember 또는 null(email 또는 pw 불일치)
	 */
	Member login(String memberEmail, String memberPw);

	
	/** 회원 가입
	 * @param inputMember
	 * @return result
	 */
	int signUp(Member inputMember);


	/** 이메일 중복 검사(비동기)
	 * @param email
	 * @return count
	 */
	int emailCheck(String email);


	/** 닉네임 중복 검사
	 * @param nickname
	 * @return count
	 */
	int nicknameCheck(String nickname);
	
}
