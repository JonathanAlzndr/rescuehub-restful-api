package com.rescuehub.restful.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCaseRequest {

    @NotNull
    private LocalDateTime createdAt;

    @NotBlank
    @Size(max = 50)
    private String status;

    @NotBlank
    @Size(max = 50)
    private String latitude;

    @NotBlank
    @Size(max = 50)
    private String longitude;

    @NotBlank
    @Size(max = 500)
    private String deskripsi;

}
