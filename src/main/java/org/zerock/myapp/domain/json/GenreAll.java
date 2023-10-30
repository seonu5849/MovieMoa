package org.zerock.myapp.domain.json;

import lombok.Data;

import java.util.List;

@Data
public class GenreAll {
    private List<GenreInfo> genres;
}
