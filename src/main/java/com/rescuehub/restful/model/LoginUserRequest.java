package com.rescuehub.restful.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest {

    @NotBlank
    @Size(max = 50)
    private String nik;

    @NotBlank
    @Size(max = 100)
    private String password;
}
