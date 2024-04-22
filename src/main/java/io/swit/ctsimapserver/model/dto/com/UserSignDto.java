package io.swit.ctsimapserver.model.dto.com;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserSignDto {
    private String username;
    private String password;
}
