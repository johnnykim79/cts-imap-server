package io.swit.ctsimapserver.service.com;

import io.swit.ctsimapserver.data.repository.com.UserRepository;
import io.swit.ctsimapserver.exception.CustomException;
import io.swit.ctsimapserver.model.dto.com.UserSignupDto;
import io.swit.ctsimapserver.model.entity.com.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleService roleService;

    @Transactional
    public void registerUser(UserSignupDto signupDto) throws CustomException {
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new CustomException("이미 사용중인 이메일 입니다.");
        }
        UserEntity user = UserEntity.registerUser(
                signupDto.getEmail(),
                signupDto.getUsername(),
                encoder.encode(signupDto.getPassword()),
                roleService.getDefaultRole());
        userRepository.save(user);
    }
}
