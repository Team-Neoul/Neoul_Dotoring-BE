package com.theZ.dotoring.app.mento.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class CustomPageRequest extends PageRequest {
    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     *
     * @param page zero-based page index, must not be negative.
     * @param size the size of the page to be returned, must be greater than 0.
     * @param sort must not be {@literal null}, use {@link Sort#unsorted()} instead.
     */

    private final String nickname;

    public CustomPageRequest(int page, int size, Sort sort, String nickname) {
        super(page, size, sort);
        this.nickname = nickname;
    }
}
