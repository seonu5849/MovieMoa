package org.zerock.myapp.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.myapp.domain.MemberStatus;
import org.zerock.myapp.domain.MemberVO;
import org.zerock.myapp.domain.Role;
import org.zerock.myapp.service.AdminService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Log4j2
@RequiredArgsConstructor

@Component
public class MemberSuspensionScheduler {

    private final AdminService adminService;

    @Scheduled(fixedDelay = 5000)
    public void checkMemberSuspension(){
        log.trace("checkMemberSuspension() invoked.");

        // 현재 날짜를 가져옵니다.
        LocalDate currentDay = LocalDate.now();

        // 회원의 suspensionPeriod를 확인하고 처리합니다.
        List<MemberVO> members = this.adminService.findAllMember();

        for(MemberVO member : members){
            Date suspensionPeriod = member.getSuspensionPeriod();
            log.info("\t+ sc.member : {} ", member);

            if(suspensionPeriod != null) {
                Instant instant = suspensionPeriod.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDate suspensionDate = instant.atZone(zoneId).toLocalDate();

                if (member.getRole() == Role.ROLE_LOCKED) {
                    if (suspensionDate.isBefore(currentDay)) {
                        adminService.editMemberStatus(member.getId(), MemberStatus.ACTIVITY.getStatus());
                    } // if
                } // if
            } // if
        } // for
    } // checkMemberSuspension

} // end class
