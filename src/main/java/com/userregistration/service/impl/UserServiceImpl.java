package com.userregistration.service.impl;

import com.userregistration.domain.entites.ConfirmationToken;
import com.userregistration.domain.entites.User;
import com.userregistration.domain.enums.Role;
import com.userregistration.domain.repository.ConfirmationTokenRepository;
import com.userregistration.domain.repository.UserRepository;
import com.userregistration.dto.LoginUserDTO;
import com.userregistration.dto.RecoveryJwtTokenDTO;
import com.userregistration.dto.RegisterUserDTO;
import com.userregistration.exceptions.InfoAlreadyExistsException;
import com.userregistration.service.EmailService;
import com.userregistration.service.JwtService;
import com.userregistration.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final EmailService emailService;

    @Transactional
    public void signup(RegisterUserDTO user, String siteUrl)
            throws MessagingException, UnsupportedEncodingException {
        boolean emailExists = userRepository
                .existsByEmailIgnoreCase(user.email());

        if(emailExists){
            throw new InfoAlreadyExistsException("Email já cadastrado");
        }

        User newUser = User
                .builder()
                .firstName(user.firstName())
                .lastName(user.lastName())
                .email(user.email())
                .password(passwordEncoder.encode(user.password()))
                .role(Role.fromValue(user.role()))
                .build();

        userRepository.save(newUser);

        ConfirmationToken confirmationToken = new ConfirmationToken(newUser);

        confirmationTokenRepository.save(confirmationToken);

        emailService.sendVerificationEmail(newUser, confirmationToken, siteUrl);
    }

    public RecoveryJwtTokenDTO authenticate(LoginUserDTO loginUser) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.email(), loginUser.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        User user = (User) authentication.getPrincipal();

        return RecoveryJwtTokenDTO
                .builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public ResponseEntity<?> verify(String verificationToken){
        ConfirmationToken token = confirmationTokenRepository
                .findByConfirmationToken(verificationToken);

        if(token != null){
            Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());
            user.ifPresent(value -> {
                value.setEnabled(true);
                userRepository.save(user.get());
            });
            return ResponseEntity.ok("Email verificado com sucesso!");
        }
        return ResponseEntity.badRequest().body("Erro: não foi possível verificar o e-mail");
    }


}
