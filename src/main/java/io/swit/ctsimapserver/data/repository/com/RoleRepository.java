package io.swit.ctsimapserver.data.repository.com;

import io.swit.ctsimapserver.enums.ERole;
import io.swit.ctsimapserver.model.entity.com.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(ERole name);
}
