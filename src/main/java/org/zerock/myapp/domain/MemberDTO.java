package org.zerock.myapp.domain;

import lombok.Data;
import lombok.Value;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class MemberDTO {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String phoneNum;
    private Timestamp joinDate;
    private Timestamp lastLoginDate;
    private Role role;
    private String status;
    private String address;
    private Date birthday;
    private Date suspensionPeriod;
} // end class
