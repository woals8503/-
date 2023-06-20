package com.oneline.shimpyo.domain.member;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static lombok.AccessLevel.PROTECTED;

@Builder
@AllArgsConstructor
@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "MEMBER_GRADE")
public class MemberGrade {
    @Id @GeneratedValue
    @Column(name = "member_grade_id")
    private Long id;

    @NotNull
    @Enumerated(value = STRING)
    private GradeName grade;

    @NotNull
    private int discount;

    @OneToMany(mappedBy = "memberGrade")
    private List<Member> members = new ArrayList<>();

    public MemberGrade(GradeName grade, int discount) {
        this.grade = grade;
        this.discount = discount;
    }
}
