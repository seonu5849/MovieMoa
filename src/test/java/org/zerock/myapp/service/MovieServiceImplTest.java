package org.zerock.myapp.service;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieServiceImplTest {

    @Setter(onMethod_ = @Autowired)
    private MovieService movieService;

    @Test
    void testAddSearchHistory() {

        Long memberId = Long.valueOf(2);
        Long movieId = Long.valueOf(676);

        Integer affectedRows = this.movieService.addSearchHistory(memberId, movieId);
        assertThat(affectedRows).isEqualTo(1);

    }
}