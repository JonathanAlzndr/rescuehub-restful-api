package com.rescuehub.restful.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String nik;

    private String namaPengguna;

    private String password;

    private String nomorTelepon;

    private String kecamatan;

    private String kelurahan;

    private String lingkungan;
}
