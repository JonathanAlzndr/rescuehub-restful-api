package com.rescuehub.restful.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {

    @NotBlank
    @Size(max = 50)
    private String nik;

    @NotBlank
    @Size(max = 100)
    private String namaPengguna;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotBlank
    @Size(max = 50)
    private String nomorTelepon;

    @NotBlank
    @Size(max = 50)
    private String kecamatan;

    @NotBlank
    private String lingkungan;

    @NotBlank
    @Size(max = 50)
    private String kelurahan;

}
