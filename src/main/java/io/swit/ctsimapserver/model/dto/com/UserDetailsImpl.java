package io.swit.ctsimapserver.model.dto.com;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swit.ctsimapserver.model.entity.com.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final Long userIdx;

    private final String email;

    private final String username;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long userIdx, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userIdx = userIdx;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(UserEntity user) {
        List<GrantedAuthority> authories = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getUserIdx(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authories);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public long getUserIdx() {
        return userIdx;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) obj;
        return Objects.equals(userIdx, user.userIdx);
    }
}
