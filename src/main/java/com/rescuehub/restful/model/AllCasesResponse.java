package com.rescuehub.restful.model;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllCasesResponse {
    private Integer id;
    private LocalDateTime createdAt;
    private String latitude;
    private String longitude;
    private String namaPengguna;
    private String statusKasus;
    private String nomorTeleponPengguna;
    private String deskripsi;
    private String imageUrl;
}
