package com.spring.security.auth.service.impl;

import com.spring.security.auth.entity.PrincipleGroup;
import com.spring.security.auth.entity.UserEntity;
import com.spring.security.auth.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userDetail = userDetailRepository.findByUsername(username);
        if (userDetail.isPresent()) {
            UserEntity user = userDetail.get();
            if (user.getUsername().equals(username)) {
                return User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(getAuthorities(userDetail.get())).build();
//                return new User(
//                        userDetail.get().getUsername(),
//                        userDetail.get().getPassword(),
//                        getAuthorities(userDetail.get()));
            } else {
                throw new UsernameNotFoundException("User info not correct: " + username);
            }
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Collection<GrantedAuthority> getAuthorities(UserEntity user) {
        Set<PrincipleGroup> userGroups = user.getUserGroups();
        Collection<GrantedAuthority> authorities = new ArrayList<>(userGroups.size());
        for (PrincipleGroup userGroup : userGroups) {
            authorities.add(new SimpleGrantedAuthority(userGroup.getCode().toUpperCase()));
        }
        return authorities;
    }


}