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
        UserVO userVO = new UserVO();
        userVO.setId(this.id);
        userVO.setUsername(this.username);
        userVO.setPassword(this.password);
        userVO.setNickname(this.nickname);
        userVO.setUserLevel(this.userLevel.name());
        userVO.setType(this.type.name());
        return userVO;
    }
}
