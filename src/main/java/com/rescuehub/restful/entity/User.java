package com.rescuehub.restful.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    private String nik;

    @Column(name = "nama")
    private String name;

    @Column
    private String password;

    @Column(name = "nomor_telepon")
    private String telephoneNumber;

    @Column
    private String kecamatan;

    @Column
    private String lingkungan;

    @Column
    private String token;

    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;

    @Column
    private String kelurahan;

    // Ke table CaseReport
    @OneToMany(mappedBy = "user")
    private List<CaseReport> caseReports;

    // ke table Case
    @OneToMany(mappedBy = "user")
    private List<Case> caseList;
}
