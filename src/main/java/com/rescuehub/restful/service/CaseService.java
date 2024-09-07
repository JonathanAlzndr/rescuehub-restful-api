package com.rescuehub.restful.service;

import com.rescuehub.restful.entity.Case;
import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.CaseDetailResponse;
import com.rescuehub.restful.model.CreateCaseRequest;
import com.rescuehub.restful.repository.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional(readOnly = true)
    public CaseDetailResponse get(User user, Integer caseId) {
        Case cases = caseRepository.findById(caseId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));

        return toCaseDetailResponse(cases);
    }

    private CaseDetailResponse toCaseDetailResponse(Case cases) {
        return CaseDetailResponse.builder()
                .id(cases.getCase_id())
                .createdAt(cases.getCreatedAt())
                .latitude(cases.getLatitude())
                .longitude(cases.getLongitude())
                .statusKasus(cases.getStatus())
                .nomorTeleponPengguna(cases.getUser().getTelephoneNumber())
                .responses(cases.getCaseReportList())
                .build();
    }

}
