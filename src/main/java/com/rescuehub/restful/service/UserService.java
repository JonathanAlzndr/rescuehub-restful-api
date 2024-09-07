package com.rescuehub.restful.service;

import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.RegisterUserRequest;
import com.rescuehub.restful.model.UserResponse;
import com.rescuehub.restful.repository.UserRepository;
import com.rescuehub.restful.security.BCrypt;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ValidationService validationService;


    @Transactional
    public void register(RegisterUserRequest request) {

        validationService.validate(request);

       if(userRepository.existsById(request.getNik())) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NIK already registered");
       }

       User user = new User();
       user.setNik(request.getNik());
       user.setName(request.getNamaPengguna());
       user.setTelephoneNumber(request.getNomorTelepon());
       user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
       user.setKecamatan(request.getKecamatan());
       user.setKelurahan(request.getKelurahan());
       user.setLingkungan(request.getLingkungan());

       userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public UserResponse get(User user) {
        return UserResponse.builder()
                .nik(user.getNik())
                .namaPengguna(user.getName())
                .password(user.getPassword())
                .nomorTelepon(user.getTelephoneNumber())
                .kecamatan(user.getKecamatan())
                .kelurahan(user.getKelurahan())
                .lingkungan(user.getLingkungan())
                .build();
    }

}
