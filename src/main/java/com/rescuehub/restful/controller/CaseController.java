package com.rescuehub.restful.controller;

import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.*;
import com.rescuehub.restful.service.CaseService;
import com.rescuehub.restful.service.UserService;
import jakarta.validation.constraints.Size;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@RestController
public class CaseController {

    @Autowired
    private CaseService caseService;

    @Autowired
    private UserService userService;

    private final String uploadDir = "D:/fileUpload";

    // Post a new case
    @PostMapping(
            path = "/api/cases",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> create(User user, @RequestParam("file") MultipartFile file,
                                      @RequestParam("createdAt") LocalDateTime createdAt,
                                      @RequestParam("description") String description,
                                      @RequestParam("latitude") String latitude,
                                      @RequestParam("longitude") String longitude) {

        CreateCaseRequest request = new CreateCaseRequest();
        request.setCreatedAt(createdAt);
        request.setStatus("Baru");
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        request.setDeskripsi(description);
        try {
            caseService.create(user, request, file);
            return WebResponse.<String>builder()
                    .data("Case created successfully")
                    .build();
        } catch(Exception e) {
            return WebResponse.<String>builder().data("Case created successfully").build();
        }
    }

    // Get Detail Case
    @GetMapping(
            path = "/api/cases/{caseId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CaseDetailResponse> get(User user, @PathVariable("caseId") Integer caseId) {
        val caseDetailResponse = caseService.get(user, caseId);
        return WebResponse.<CaseDetailResponse>builder().data(caseDetailResponse).build();
    }

    // Get All Case
    @GetMapping(
            path = "/api/cases",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PagingResponse<List<AllCasesResponse>> get(User user, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        val response = caseService.get(user, page, size);
        return PagingResponse.<List<AllCasesResponse>>builder()
                .data(response)
                .page(page)
                .size(size)
                .build();
    }

    @PutMapping(
            path = "api/cases/{caseId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> updateCase(User user, @RequestBody UpdateCaseStatusRequest request, @PathVariable("caseId") Integer caseId) {
        caseService.updateStatus(user, request, caseId);
        return WebResponse.<String>builder()
                .data("Case updated successfully")
                .build();
    }

    // Get case image
    @GetMapping(
            path = "/api/uploads/{filename:.+}"
    )
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Resource resource = caseService.loadFile(filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(null);
        }
    }

}
