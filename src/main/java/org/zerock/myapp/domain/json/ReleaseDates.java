package org.zerock.myapp.domain.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReleaseDates { // Certification 1
    @JsonProperty("id")
    private Long movieId; // 영화번호
    private List<CertificationResults> results;

}
