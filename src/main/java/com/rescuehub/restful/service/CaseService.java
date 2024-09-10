package com.rescuehub.restful.service;

import com.rescuehub.restful.entity.Case;
import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.*;
import com.rescuehub.restful.repository.CaseRepository;
import com.rescuehub.restful.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaseService {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private UserRepository userRepository;

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
        if(user.getToken() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Case cases = caseRepository.findById(caseId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));

        return toCaseDetailResponse(cases);
    }

    @Transactional
    public List<AllCasesResponse> get(User user) {

        if(user.getToken() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        val listCase = caseRepository.findAll();
        return listCase.stream().map(cases -> new AllCasesResponse(
                cases.getCase_id(), cases.getCreatedAt(),
                cases.getLatitude(), cases.getLongitude(),
                cases.getUser().getName(), cases.getStatus(),
                cases.getUser().getTelephoneNumber()))
                .toList();
    }

    @Transactional
    public void updateStatus(User user,  UpdateCaseStatusRequest request, Integer caseId) {

        if(user.getToken() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Case caseToBeUpdate = caseRepository.findById(caseId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));

        if(caseToBeUpdate != null) {
            caseToBeUpdate.setStatus(request.getStatus());
            caseRepository.save(caseToBeUpdate);
        }
    }

    private CaseDetailResponse toCaseDetailResponse(Case cases) {
        List<CaseReportResponse> caseReportResponses = cases.getCaseReportList().stream()
                .map(report -> new CaseReportResponse(report.getImageUrl(), report.getDescription()))
                .collect(Collectors.toList());
        return CaseDetailResponse.builder()
                .id(cases.getCase_id())
                .createdAt(cases.getCreatedAt())
                .latitude(cases.getLatitude())
                .longitude(cases.getLongitude())
                .statusKasus(cases.getStatus())
                .nomorTeleponPengguna(cases.getUser().getTelephoneNumber())
                .responses(caseReportResponses)
                .build();
    }

}
