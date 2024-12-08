package com.rescuehub.restful.service;

import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.LoginUserRequest;
import com.rescuehub.restful.model.TokenResponse;
import com.rescuehub.restful.repository.UserRepository;
import com.rescuehub.restful.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginUserRequest request) {

        validationService.validate(request);

        User user = userRepository.findById(request.getNik()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "NIK or password wrong")
        );

        if(BCrypt.checkpw(request.getPassword(), user.getPassword())){
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30Days());
            userRepository.save(user);

            return TokenResponse.builder()
                    .namaPengguna(user.getName())
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .build();

        } else {
          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "NIK or password wrong");
        }
    }

    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }

    private Long next30Days() {
        return System.currentTimeMillis() + (1000 * 16 * 24 * 30);
    }
}
