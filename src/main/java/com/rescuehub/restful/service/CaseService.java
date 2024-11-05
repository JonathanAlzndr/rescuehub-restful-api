package com.rescuehub.restful.service;

import com.rescuehub.restful.entity.Case;
import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.*;
import com.rescuehub.restful.repository.CaseRepository;
import com.rescuehub.restful.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
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
    public void create(User user, CreateCaseRequest request, MultipartFile file) {
        validationService.validate(request);
        Logger logger = Logger.getLogger(getClass().getName());
        if(user.getToken() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Case newCase = new Case();
        newCase.setCreatedAt(request.getCreatedAt());
        newCase.setLatitude(request.getLatitude());
        newCase.setLongitude(request.getLongitude());
        newCase.setUser(user);
        newCase.setDeskripsi(request.getDeskripsi());
        newCase.setStatus(request.getStatus());
        logger.info("Creating new case: " + newCase.toString());
        if(file != null && !file.isEmpty()) {
            String imageUrl = saveUploadFile(file);
            newCase.setImageUrl(imageUrl);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image file is required");
        }
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

    @Transactional(readOnly = true)
    public List<AllCasesResponse> get(User user, Integer page, Integer size) {

        if(user.getToken() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Case> listCasePage = caseRepository.findAll(pageable);
        System.out.println("Cases fetched: " + listCasePage.getContent());
        return listCasePage.getContent().stream().map(this::mapToAllCaseResponse).collect(Collectors.toList());
    }

    @Transactional
    public void updateStatus(User user,  UpdateCaseStatusRequest request, Integer caseId) {
        validationService.validate(request);
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



    private AllCasesResponse mapToAllCaseResponse(Case aCase) {
        String imageUrl = aCase.getImageUrl() != null ? aCase.getImageUrl() : "";
        System.out.println("Mapping case: " + aCase.getCase_id());
        return AllCasesResponse.builder()
                .id(aCase.getCase_id())
                .createdAt(aCase.getCreatedAt())
                .latitude(aCase.getLatitude())
                .longitude(aCase.getLongitude())
                .namaPengguna(aCase.getUser().getName())
                .statusKasus(aCase.getStatus())
                .deskripsi(aCase.getDeskripsi())
                .imageUrl(imageUrl)
                .nomorTeleponPengguna(aCase.getUser().getTelephoneNumber())
                .build();
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
                .description(cases.getDeskripsi())
                .responses(caseReportResponses)
                .build();
    }

    private String saveUploadFile(MultipartFile file) {
        try {
            String uploadDir = "D:\\fileUpload";
            File directory = new File(uploadDir);
            if(!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File uploadedFile = new File(directory, fileName);

            try (OutputStream os = new FileOutputStream(uploadedFile)) {
                os.write(file.getBytes());
            }
            return "D:/fileUpload/" + fileName;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save file", e);
        }
    }

    public Resource loadFile(String filename) {
        final Path rootLocation = Paths.get("D:/fileUpload");
        try {
            Path file = rootLocation.resolve(filename).normalize().toAbsolutePath();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not read file: " + filename, e);
        }
    }


}
