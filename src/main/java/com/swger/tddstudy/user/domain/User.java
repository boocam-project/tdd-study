package com.swger.tddstudy.user.domain;

import com.swger.tddstudy.util.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Entity
@Table(name = "Users")
public class User extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "username", nullable = false, length = 15)
    private String username;

    @NonNull
    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @NonNull
    @Column(name = "nickname", nullable = false, length = 15)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserLevel userLevel;

    @Enumerated(EnumType.STRING)
    private UserType type;

    public UserVO toUserVO() {
        UserVO userVO = UserVO.builder().id(this.id).username(this.username).password(this.password).nickname(this.nickname).userLevel(this.userLevel.name()).type(this.type.name()).build();
        return userVO;
    }

    public void levelUp() {
        this.userLevel = this.userLevel.levelUp();
    }
}
