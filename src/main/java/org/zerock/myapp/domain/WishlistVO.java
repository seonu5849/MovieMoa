package org.zerock.myapp.domain;

import lombok.Value;

@Value
public class WishlistVO {
    private Long id;
    private Long memberId;
    private Long movieId;
} // end class
