package edu.kh.project.chatting.service;

import java.util.List;

import edu.kh.project.chatting.dto.ChattingRoom;
import edu.kh.project.chatting.dto.Message;
import edu.kh.project.member.dto.Member;

public interface ChattingService {

	
	/** 채팅 상대 검색
	 * @param query
	 * @param memberNo
	 * @return
	 */
	List<Member> selectTarget(String query, int memberNo);

	/** 채팅방 입장(처음 채팅이면 채팅방 생성(INSERT))
	 * @param targetNo
	 * @param memberNo
	 * @return
	 */
	int chattingEnter(int targetNo, int memberNo);

	/** 로그인 한 회원이 참여한 채팅방 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<ChattingRoom> selectRoomList(int memberNo);

	/** 특정 채팅방의 메시지 모두 조회하기
	 * @param chattingNo
	 * @param memberNo
	 * @return
	 */
	List<Message> selectMessage(int chattingNo, int memberNo);

}
