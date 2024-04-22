package io.swit.ctsimapserver.controller.com;

import io.swit.ctsimapserver.component.JwtUtil;
import io.swit.ctsimapserver.data.repository.com.UserRepository;
import io.swit.ctsimapserver.exception.CustomException;
import io.swit.ctsimapserver.jwt.AuthTokenFilter;
import io.swit.ctsimapserver.model.dto.com.ApiResponse;
import io.swit.ctsimapserver.model.dto.com.JwtResponse;
import io.swit.ctsimapserver.model.dto.com.UserSignDto;
import io.swit.ctsimapserver.model.dto.com.UserSignupDto;
import io.swit.ctsimapserver.model.dto.com.UserDetailsImpl;
import io.swit.ctsimapserver.service.com.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthTokenFilter authenticationJwtTokenFilter;

    @PostMapping("/signin")
    public ResponseEntity<?> postOauthSignIn(@RequestBody UserSignDto signDto) {
        return ResponseEntity.ok().body(authenticateAndGenerateJWT(signDto.getUsername(), signDto.getPassword()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerAndAuthenticateUser(@RequestBody UserSignupDto signupRequest) throws CustomException {
        //사용자 등록
        userService.registerUser(signupRequest);

        JwtResponse jwtResponse = authenticateAndGenerateJWT(signupRequest.getEmail(), signupRequest.getPassword());
        ApiResponse<JwtResponse> response = ApiResponse.setApiResponse(true, "메일이 등록 되었습니다.", jwtResponse);

        return ResponseEntity.ok().body(response);
    } 

    private JwtResponse authenticateAndGenerateJWT(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roleNames = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return JwtResponse.setJwtResponse(jwt, userDetails.getUserIdx(), userDetails.getUsername(), userDetails.getEmail(), roleNames);
    }
}
