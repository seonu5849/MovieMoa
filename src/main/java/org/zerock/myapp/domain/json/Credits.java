package org.zerock.myapp.domain.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Credits {
    @JsonProperty("id")
    private Long movieId; // 영화번호
    private List<Cast> cast;
    private List<Crew> crew;

}
