package com.aueb.issues.model.entity;

import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author George Karampelas
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
@Table(name = "_user")
public class UserEntity implements UserDetails {

    @Id
    private String id;
    private String email;
    private String password;
    private String phone;
    private String firstname;
    private String lastname;
    private String gender;
    private String address;
    private HashSet<String> preferences; //Sites' id
    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private IssueType technicalTeam;
    private boolean activated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationsEntity> notifications;

    public void addPreference(String siteId){
        if (preferences==null)
            preferences=new HashSet<>();
        preferences.add(siteId);
    }

    public boolean updatePreferences(HashSet<String> siteIds){
        try{
            preferences=siteIds;
            return true;
        }catch (Exception e){
            log.error("Exception at setting preferences \n" + e.getMessage());
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

    public void addNotification(NotificationsEntity notificationsEntity){
        if(notifications==null){
            notifications=new ArrayList<>();
        }
        notifications.add(notificationsEntity);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return this.activated;
    }
}
