package org.zerock.myapp.service;

import org.zerock.myapp.domain.EventsVO;

import java.util.List;

public interface EventService {

    // 페이지 오프셋 계산: 주어진 페이지 번호에 따라 데이터베이스 조회 시작 위치를 계산
    public abstract Integer offset(Integer pageNum);

    // 이벤트 목록 조회: 페이지네이션과 진행 여부에 따른 이벤트 목록 조회
    public abstract List<EventsVO> findEventList(Integer pageNum, String  proceedingYn);

    // 이벤트 추가: 새로운 이벤트 정보를 데이터베이스에 추가
    public abstract Integer addEvent(EventsVO event);

    // 이벤트 수정: 기존 이벤트 정보를 업데이트
    public abstract Integer updateEvent(Long adminId, EventsVO event);

    // 이벤트 삭제: 특정 이벤트 정보를 데이터베이스에서 삭제
    public abstract Integer deleteEvent(Long id);

    // 이벤트 상세 조회: 특정 이벤트 ID에 해당하는 이벤트의 상세 정보 조회
    public abstract EventsVO findEventById(Long id);

} // end interface
