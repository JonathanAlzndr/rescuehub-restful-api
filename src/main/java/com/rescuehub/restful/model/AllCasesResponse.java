package com.rescuehub.restful.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllCasesResponse {
    private Integer id;
    private Timestamp createdAt;
    private String latitude;
    private String longitude;
    private String namaPengguna;
    private String statusKasus;
    private String nomorTeleponPengguna;
}
