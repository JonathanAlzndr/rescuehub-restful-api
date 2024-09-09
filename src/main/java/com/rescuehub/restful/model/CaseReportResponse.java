package com.rescuehub.restful.model;

import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CaseReportResponse {
    private String imageUrl;
    private String description;
}
