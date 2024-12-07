package org.codeplay.playcoolbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.codeplay.playcoolbackend.dto.AuthenResponseDto;
import org.codeplay.playcoolbackend.dto.User;
import org.codeplay.playcoolbackend.dto.UserRegisterRequestDto;
import org.codeplay.playcoolbackend.repository.UserRepository;
import org.codeplay.playcoolbackend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public AuthenResponseDto register(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {

        User user = new User();
        user.setName(userRegisterRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return AuthenResponseDto.builder().token(jwt).build();

    }

    @PostMapping("/login")
    public AuthenResponseDto login(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRegisterRequestDto.getUsername(), userRegisterRequestDto.getPassword()));

       var  user = userRepository.findUserByName(userRegisterRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);
        return AuthenResponseDto.builder().token(jwt).build();
    }

}
