package io.swit.ctsimapserver.model.dto.com;

import lombok.Data;

import java.util.Set;

@Data
public class UserSignupDto {

    private String username;

    private String email;

    private Set<String> role;

    private String password;
}
