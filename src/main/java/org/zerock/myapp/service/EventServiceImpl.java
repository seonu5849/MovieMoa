package org.zerock.myapp.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.EventsVO;
import org.zerock.myapp.mapper.EventMapper;

import java.util.List;

@Log4j2
@RequiredArgsConstructor

@Service
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;

    private Integer perPage = 3;

    @Override
    public Integer offset(Integer pageNum) {
        return (pageNum - 1) * this.perPage;
    } // offset

    @Override
    public List<EventsVO> findEventList(Integer pageNum, String  proceedingYn) {
        log.trace("findEventList: {}, {}, {}, {}", pageNum, proceedingYn);

        Integer offset = offset(pageNum);

        return this.eventMapper.findEventList(offset, perPage, proceedingYn);
    } // findEndedEvents

    @Override
    public Integer addEvent(EventsVO event) {
        log.trace("addEvent: {}", event);

        return this.eventMapper.insertEvent(event);
    } // addEvent

    @Override
    public Integer updateEvent(Long adminId, EventsVO event) {
        log.trace("updateEvent: {}, {}", adminId, event);

        return this.eventMapper.updateEvent(adminId, event);
    } // updateEvent

    @Override
    public Integer deleteEvent(Long id) {
        log.trace("deleteEvent: {}", id);

        return eventMapper.deleteEvent(id);
    } // deleteEvent

    @Override
    public EventsVO findEventById(Long id) {
        return this.eventMapper.findEventById(id);
    } // findEventById

} // end class
