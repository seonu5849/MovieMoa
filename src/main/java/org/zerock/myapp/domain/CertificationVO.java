package org.zerock.myapp.domain;

import lombok.Value;

@Value
public class CertificationVO {
    private Long id;
    private String iso31661;
    private String certification;
    private Long movieId;
} // end class
