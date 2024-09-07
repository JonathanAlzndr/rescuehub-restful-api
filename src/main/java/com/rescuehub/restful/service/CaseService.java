package com.rescuehub.restful.service;

import com.rescuehub.restful.entity.Case;
import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.CreateCaseRequest;
import com.rescuehub.restful.repository.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
