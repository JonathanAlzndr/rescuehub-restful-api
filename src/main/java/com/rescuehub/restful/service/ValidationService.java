package com.rescuehub.restful.service;

import com.rescuehub.restful.model.RegisterUserRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import java.util.Set;

@Service
public class ValidationService {

    @Autowired
    private Validator validator;


    public void validate(Object request) {
        Set<ConstraintViolation<Object>> constraintViolations =  validator.validate(request);
        if(constraintViolations.size() != 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
