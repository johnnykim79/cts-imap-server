package io.swit.ctsimapserver.model.dto.com;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtResponse {
    private final String type = "Bearer";
    private String token;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public static JwtResponse setJwtResponse(String token, Long id, String username, String email, List<String> roles) {
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.token = token;
        jwtResponse.id = id;
        jwtResponse.username = username;
        jwtResponse.email = email;
        jwtResponse.roles = roles;
        return jwtResponse;
    }
}
