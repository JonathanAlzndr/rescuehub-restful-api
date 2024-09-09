package com.rescuehub.restful.service;

import com.rescuehub.restful.entity.Case;
import com.rescuehub.restful.entity.CaseReport;
import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.CaseDetailResponse;
import com.rescuehub.restful.model.CreateCaseReportRequest;
import com.rescuehub.restful.model.WebResponse;
import com.rescuehub.restful.repository.CaseReportRepository;
import com.rescuehub.restful.repository.CaseRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CaseReportService {

    @Autowired
    CaseReportRepository caseReportRepository;

    @Autowired
    CaseRepository caseRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void create(User user, CreateCaseReportRequest request, Integer id) {
        validationService.validate(request);

        // cari kasus
        Case caseToBeHandle =  caseRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));

        // jika kasus ada maka set caseToBeHandle ke CaseReport
        if(caseToBeHandle != null) {
            CaseReport caseReport = new CaseReport();
            caseToBeHandle.setStatus("Ditanggapi");
            caseReport.setACase(caseToBeHandle);
            caseReport.setDescription(request.getDescription());
            caseReport.setUser(user);
            caseReport.setImageUrl(request.getImageUrl());
            caseReportRepository.save(caseReport);
        }
    }
}
