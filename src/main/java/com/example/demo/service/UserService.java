package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public void register(UserRequestDto userRequestDto){
        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());

        //User 엔티티로 변환
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(encodedPassword);

        //저장
        userRepository.save(user);
    }

    public String login(LoginRequestDto dto){
        User user = userRepository.findByUsername(dto.getUsername());
        if (user == null){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}
