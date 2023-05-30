package com.oneline.shimpyo.domain.wish;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WishList implements Serializable {
    @Column(name = "member_id")
    private long member_id;

    @Column(name = "house_id")
    private long house_id;
}
