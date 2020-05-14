package com.muyun.springboot.service;

import com.muyun.springboot.dto.UserDTO;
import com.muyun.springboot.dto.UserDetail;
import com.muyun.springboot.entity.User;
import com.muyun.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author muyun
 * @date 2020/4/14
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private static final Long ADMIN_ID = 1L;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        User user = get(ADMIN_ID);
        if (user == null) {
            user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setCreatedBy(ADMIN_ID);
            user.setUpdatedBy(ADMIN_ID);
            userRepository.save(user);
        }
    }

    public User get(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(UserDTO userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User update(UserDTO userDto) {
        User user = get(userDto.getId());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return UserDetail.fromUser(user);
    }

}
