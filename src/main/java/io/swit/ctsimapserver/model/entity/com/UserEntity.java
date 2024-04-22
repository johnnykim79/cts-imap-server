package io.swit.ctsimapserver.model.entity.com;

import io.swit.ctsimapserver.enums.UserState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;


@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "com_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx", updatable = false, unique = true, nullable = false)
    private Long userIdx;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nick_name", nullable = true)
    private String nickName;

    @Column(name = "phone_no", nullable = true, length = 20)
    private String phoneNo;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name="status")
    private Integer status;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<UserRoleEntity> roles = new HashSet<>();

    public static UserEntity registerUser(
            String email,
            String username,
            String password,
            Set<RoleEntity> roles
    ) {
        UserEntity user = new UserEntity();
        user.email = email;
        user.username = username;
        user.password = password;
        user.status = UserState.ACTIVE.getValue();

        for (RoleEntity role: roles) {
            UserRoleEntity userRole = UserRoleEntity.createUserRoles(user, role);
            user.getRoles().add(userRole);
        }
        return user;
    }
}
