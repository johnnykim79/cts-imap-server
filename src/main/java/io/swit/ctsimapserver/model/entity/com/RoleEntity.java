package io.swit.ctsimapserver.model.entity.com;

import io.swit.ctsimapserver.enums.ERole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "com_roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "name", length = 20)
    @Enumerated(EnumType.STRING)
    private ERole name;

    @Column(name = "description", length = 100)
    private String description;
}
