package org.zerock.myapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.myapp.domain.EventsVO;
import org.zerock.myapp.service.EventService;

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

    @DeleteMapping("/detailEvent")
    public String detailEventDelete() {
        log.trace("detailEventDelete() invoked.");

        return "redirect:/event/currentEvents";
    } // detailEventDelete

    @GetMapping("/eventWrite")
    public String eventWriteView() {
        log.trace("eventWriteView() invoked.");

        return "/event/eventWrite";
    } // eventWriteView

    @PostMapping("/eventWrite")
    public String eventWrite() {
        log.trace("eventWrite() invoked.");

        return "redirect:/event/detailEvent";
    } // eventWrite

    @GetMapping("/eventUpdate")
    public String eventUpdateView() {
        log.trace("eventUpdateView() invoked.");

        return "/event/eventUpdate";
    } // eventUpdateView

    @PutMapping("/eventUpdate")
    public String eventUpdate() {
        log.trace("eventUpdate() invoked.");

        return "redirect:/event/detailEvent";
    } // eventUpdate

} // end class
