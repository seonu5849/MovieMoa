package org.zerock.myapp.domain.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Cast {
    private Long crewInfoId;
    private Long id;
    private String name;
    @JsonProperty("character")
    private String characterName;
    @JsonProperty("profile_path")
    private String profilePath;

}
