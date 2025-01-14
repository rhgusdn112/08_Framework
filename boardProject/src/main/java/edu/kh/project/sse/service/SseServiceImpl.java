package edu.kh.project.sse.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.sse.dto.Notification;
import edu.kh.project.sse.mapper.SseMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SseServiceImpl implements SseService{
	
	private final SseMapper mapper;
	
	@Override
	public Map<String, Object> insertNotification(Notification notification) {
		
		// 매개변수 notification에 저장된 값
		// -> type, url, content, pkNo, sendMemberNo
		
		// 결과 저장용 map
		Map<String, Object> map = null;
		
		// 알림 삽입
		int result = mapper.insertNotification(notification);
		
		if(result > 0) { // 알림 삽입 성공시
		  // 알림을 받아야 하는 회원의 번호 + 안읽은 알람 개수 조회
		  map = mapper.selectReceiveMember(notification.getNotificationNo());
			
		}
		
		
		return map;
	}
	
	@Override
	public List<Notification> selectNotificationList(int memberNo) {
		return mapper.selectNotificationList(memberNo);
	}
	
	// 현재 로그인 한 회원의 알림 중 읽지 않은 알림 개수 조회
	@Override
	public int notReadCheck(int memberNo) {
		return mapper.notReadCheck(memberNo);
	}
	
	// 알림 삭제
	@Override
	public void deleteNotification(int notificationNo) {
		mapper.deleteNotification(notificationNo);
	}
	
	// 알림 읽음 여부 변경(N->Y)
	@Override
	public void updateNotification(int notificationNo) {
		mapper.updateNotification(notificationNo);
	}
}
