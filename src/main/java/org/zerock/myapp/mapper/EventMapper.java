package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.myapp.domain.EventsVO;

import java.util.List;


@Mapper
public interface EventMapper {

    // 이벤트 목록 조회: 페이지네이션과 진행 여부에 따른 이벤트 목록 조회
    public abstract List<EventsVO> findEventList(@Param("offset") Integer offset, @Param("perPage") Integer perPage, @Param("proceedingYn") String  proceedingYn);

    // 이벤트 추가: 새로운 이벤트 정보를 데이터베이스에 추가
    public abstract Integer insertEvent(@Param("event") EventsVO event);

    // 이벤트 수정: 기존 이벤트 정보를 업데이트
    public abstract Integer updateEvent(@Param("adminId")Long adminId, @Param("event") EventsVO event);

    // 이벤트 삭제: 특정 이벤트 정보를 데이터베이스에서 삭제
    public abstract Integer deleteEvent(@Param("id") Long id);

    // 이벤트 상세 조회: 특정 이벤트 ID에 해당하는 이벤트의 상세 정보 조회
    public abstract EventsVO findEventById(@Param("id") Long id);

} // end interface

// DTO 전달값 매개변수
// VO 값을 받아서 보여주기위한 것
