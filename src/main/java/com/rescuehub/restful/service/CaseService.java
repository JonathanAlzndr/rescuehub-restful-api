package com.rescuehub.restful.service;

import com.rescuehub.restful.entity.Case;
import com.rescuehub.restful.entity.CaseReport;
import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.CreateCaseRequest;
import com.rescuehub.restful.repository.CaseRepository;
import com.rescuehub.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class CaseService {

    @Autowired
    CaseRepository caseRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void create(User user, CreateCaseRequest request) {
        validationService.validate(request);

        Case newCase = new Case();
        newCase.setCreatedAt(request.getCreatedAt());
        newCase.setLatitude(request.getLatitude());
        newCase.setLongitude(request.getLongitude());
        newCase.setUser(user);
        newCase.setStatus(request.getStatus());

        caseRepository.save(newCase);
    }
}
