package edu.kh.project.member.service;

import edu.kh.project.member.dto.Member;

public interface MemberService {
	
	/**
	 * @param memberEmail
	 * @param memberPw
	 * @returnloginMember 또는 null(email 또는 pw 불일치)
	 */
	Member login(String memberEmail, String memberPw);

}
