package io.swit.ctsimapserver.service.com;

import io.swit.ctsimapserver.data.repository.com.RoleRepository;
import io.swit.ctsimapserver.enums.ERole;
import io.swit.ctsimapserver.model.entity.com.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Set<RoleEntity> getDefaultRole() {
        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(
                () -> new RuntimeException("Error: 해당하는 권한이 없습니다."));
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(userRole);
        return roles;
    }
}
