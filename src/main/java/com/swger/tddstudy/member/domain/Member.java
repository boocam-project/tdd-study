package com.swger.tddstudy.member.domain;

import com.swger.tddstudy.util.BaseEntity;
import com.swger.tddstudy.member.domain.MemberLevel;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberLevel MemberLevel;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.MemberLevel = MemberLevel.BRONZE;
    }
    public Member(){}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId())
                && Objects.equals(getUsername(), member.getUsername())
                && Objects.equals(getPassword(), member.getPassword())
                && Objects.equals(getNickname(), member.getNickname())
                && getMemberLevel() == member.getMemberLevel() && getType() == member.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(),
                getNickname(), getMemberLevel(), getType());
    }
}
