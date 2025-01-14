package edu.kh.project.board.service;

import java.util.List;
import java.util.Map;

import edu.kh.project.board.dto.Board;
import edu.kh.project.board.dto.Comment;

public interface BoardService {

	/** 게시글 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);

	/** 게시글 상세 조회
	 * @param map
	 * @return board
	 */
	Board selectDetail(Map<String, Integer> map);

	/** 조회수 1 증가
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);

	/** 게시글 좋아요
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	Map<String, Object> boardLike(int boardNo, int memberNo);

	List<Map<String, String>> selectBoardTypeList();

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return commentList
	 */
	List<Comment> selectCommentList(int boardNo);

	/** 검색 목록 조회
	 * @param boardCode
	 * @param cp
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> selectSearchList(int boardCode, int cp, Map<String, Object> paramMap);

	/** 현재 게시글이 포함된 목록의 페이지로 리다이렉트
	 * @param paramMap
	 * @return
	 */
	int getCurrentPage(Map<String, Object> paramMap);
	
}
