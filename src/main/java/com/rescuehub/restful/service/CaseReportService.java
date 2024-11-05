package com.rescuehub.restful.service;

import com.rescuehub.restful.entity.Case;
import com.rescuehub.restful.entity.CaseReport;
import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.CreateCaseReportRequest;
import com.rescuehub.restful.repository.CaseReportRepository;
import com.rescuehub.restful.repository.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class CaseReportService {

    @Autowired
    CaseReportRepository caseReportRepository;

    @Autowired
    CaseRepository caseRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void create(User user, CreateCaseReportRequest request, Integer id, MultipartFile file) {
        validationService.validate(request);

        if(user.getToken() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        // cari kasus
        Case caseToBeHandle =  caseRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));


        if(caseToBeHandle != null) {
            CaseReport caseReport = new CaseReport();
            caseToBeHandle.setStatus("Selesai");
            caseReport.setACase(caseToBeHandle);
            caseReport.setDescription(request.getDescription());
            caseReport.setUser(user);

            if(file != null && !file.isEmpty()) {
                String imageUrl = saveUploadFile(file);
                caseReport.setImageUrl(imageUrl);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image file is required");
            }
            caseReportRepository.save(caseReport);
        }
    }

     private String saveUploadFile(MultipartFile file) {
        try {
            String uploadDir = "D:\\fileUpload";
            File directory = new File(uploadDir);
            if(!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File uploadedFile = new File(directory, fileName);

            try (OutputStream os = new FileOutputStream(uploadedFile)) {
                os.write(file.getBytes());
            }
            return "D:/fileUpload/" + fileName;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save file", e);
        }
    }
}
