package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;
import java.util.Date;

@Value
public class MemberVO {
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
} // end class
