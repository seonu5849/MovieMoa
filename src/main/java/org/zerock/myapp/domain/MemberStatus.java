package org.zerock.myapp.domain;

public enum MemberStatus {

    ACTIVITY("활동"),
    ONE_DAY_SUSPENSTION("1일정지"),
    SEVEN_DAY_SUSPENTION("7일정지"),
    THIRTY_DAY_SUSPENTION("30일정지");

    private String status;

    MemberStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
