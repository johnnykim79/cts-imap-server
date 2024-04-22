package io.swit.ctsimapserver.model.vo.com;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserVo {
    private Long userIdx;
    private String username;
    private String password;
    private String nickName;
    private String phoneNo;
    private String email;
    private String useYn;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private List<String> roles = new ArrayList<>();
    public Collection<? extends GrantedAuthority> authorities;
}
