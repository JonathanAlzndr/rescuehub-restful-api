package com.rescuehub.restful.model;

import com.rescuehub.restful.entity.CaseReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseDetailResponse {
    private Integer id;

    private LocalDateTime createdAt;

    private String latitude;

    private String longitude;

    private String statusKasus;

    private String nomorTeleponPengguna;

    private String description;

    private List<CaseReportResponse> responses;
}
