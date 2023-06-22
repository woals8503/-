package com.oneline.shimpyo.domain.house;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.oneline.shimpyo.domain.BaseException;
import lombok.Getter;

import java.util.stream.Stream;

import static com.oneline.shimpyo.domain.BaseResponseStatus.IMAGE_STATUS_NONEXISTENT;


@Getter
public enum ImageStatus {
    ADD("추가"), MODIFY("수정"), DELETE("삭제");

    private final String label;

    ImageStatus(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ImageStatus findByMethod(String type) throws BaseException {
        return Stream.of(ImageStatus.values())
                .filter(c -> c.name().equals(type))
                .findFirst()
                .orElseThrow(() -> new BaseException(IMAGE_STATUS_NONEXISTENT));
    }

}
