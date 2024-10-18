package edu.kh.project.common.scheduling.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface SchedulingService {

	/** DB에 기록된 모든 파일명 조회
	 * @return
	 */
	List<String> getDbFileNameList();

}
