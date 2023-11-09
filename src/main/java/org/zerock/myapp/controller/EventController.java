package org.zerock.myapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.myapp.domain.EventsVO;
import org.zerock.myapp.service.EventService;

import java.util.HashMap;
import java.util.List;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/event/*")
@Controller
public class EventController {

    private final EventService eventService;

    // "pageNum" 요청 매개변수를 통해 현재 페이지 번호를 받고, "proceedingYn"을 통해 진행중인 이벤트 여부를 받음
    @GetMapping("/currentEvents")
    public String currentEventView(
            @RequestParam(value="pageNum", required = false, defaultValue="1") Integer pageNum,
            @RequestParam(value="proceedingYn", required = false, defaultValue="Y") String proceedingYn,
            Model model) {
        log.trace("currentEventView({}, {}) invoked.", pageNum, proceedingYn);

        // 현재 페이지 번호와 진행 여부에 따라 이벤트 목록을 조회
        List<EventsVO> eventList = this.eventService.findEventList(pageNum, proceedingYn);
        // 전체 페이지 수 계산
        Integer totalPages = this.eventService.findEventList(-1, proceedingYn).size() / 4 + 1;

        model.addAttribute("eventList", eventList);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("proceedingYn", proceedingYn);
        model.addAttribute("totalPages", totalPages);

        return "/event/currentEvents";
    } // currentEventView

    @GetMapping("/endedEvents")
    public String endedEvents() {
        log.trace("endedEvents() invoked.");

        return "/event/endedEvents";
    } // endedEvents

    // "id" 경로 변수를 통해 조회할 이벤트의 ID를 받음
    @GetMapping("/detailEvent/{id}")
    public String detailEventView(@PathVariable Long id, Model model) {
        log.trace("detailEventView({}) invoked.", id);

        // 이벤트 ID를 사용하여 해당 이벤트 정보를 가져오는 서비스 호출
        EventsVO event = eventService.findEventById(id);

        model.addAttribute("event", event);

        return "/event/detailEvent";
    } // detailEventView

    @DeleteMapping("/detailEvent/{id}")
    public ResponseEntity<?> detailEventDelete(@PathVariable Long id) {
        log.trace("detailEventDelete({}) invoked.", id);

        Integer deleteEvent = this.eventService.deleteEvent(id);
        log.info("\t+ deleteEvent: {}", deleteEvent);

        // AJAX 요청에 JSON 형식으로 응답을 반환합니다.
        if (deleteEvent > 0) {
            return ResponseEntity.ok().body(new HashMap<String, Object>() {{
                put("redirectUrl", "/event/currentEvents");
            }});
        } else {
            // 삭제가 실패하면 적절한 오류 메시지와 함께 상태 코드를 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<String, Object>() {{
                put("errorMessage", "Event could not be deleted.");
            }});
        }
    } // detailEventDelete

} // end class