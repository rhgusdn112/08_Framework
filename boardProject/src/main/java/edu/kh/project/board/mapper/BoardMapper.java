package edu.kh.project.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import edu.kh.project.board.dto.Board;
import edu.kh.project.board.dto.Comment;

@Mapper
public interface BoardMapper {

	/** boardCode가 일치하는 게시글 중 삭제되지 않은 게시글 수 조회
	 * @param boardCode
	 * @return listCount
	 */
	int getListCount(int boardCode);

	/** 지정된 페이지 분량의 게시글 목록 조회
	 * @param boardCode
	 * @param rowBounds
	 * @return boardList
	 */
	List<Board> selectBoardList(int boardCode, RowBounds rowBounds);

	
	/** 게시글 상세 조회
	 * @param map
	 * @return board
	 */
	Board selectDetail(Map<String, Integer> map);

	/** 조회 수 1 증가
	 * @param boardNo
	 * @return result
	 */
	int updateReadCount(int boardNo);

	/** 좋아요 누른적있어? 검사
	 * @param boardNo
	 * @param memberNo
	 * @return result
	 */
	int checkBoardLike(@Param("boardNo")  int boardNo, 
										 @Param("memberNo") int memberNo);

	
	/** 좋아요 테이블에 삽입
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	int insertBoardLike(@Param("boardNo")  int boardNo, 
			 							  @Param("memberNo") int memberNo);

	/** 좋아요 테이블에서 삭제
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	int deleteBoardLike(@Param("boardNo")  int boardNo, 
			 							  @Param("memberNo") int memberNo);

	/** 좋아요 개수 조회
	 * @param boardNo
	 * @return count
	 */
	int getLikeCount(int boardNo);

	/** DB에서 모든 게시판 종류 조회
	 * @return
	 */
	List<Map<String, String>> selectBoardTypeList();

	
	/** 댓글 목록 조회
	 * @param boardNo
	 * @return commentList
	 */
	List<Comment> selectCommentList(int boardNo);

	
	/** 검색 조건이 맞는 게시글 수 조회
	 * @param paramMap
	 * @return count
	 */
	int getSearchCount(Map<String, Object> paramMap);

	/** 검색 목록 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return boardList
	 */
	List<Board> selectSearchList(Map<String, Object> paramMap, RowBounds rowBounds);

	/** 현재 게시글이 포함된 목록의 페이지로 리다이렉트
	 * @param paramMap
	 * @return cp
	 */
	int getCurrentPage(Map<String, Object> paramMap);
	
	
	
	
	
	
	

}
