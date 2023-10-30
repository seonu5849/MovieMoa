package org.zerock.myapp.domain.json;

import lombok.Data;

import java.util.List;

@Data
public class CertificationResults { // Certification 2

    private String iso_3166_1;
    private List<Certifications> release_dates;

}
