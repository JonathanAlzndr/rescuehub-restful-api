package com.rescuehub.restful.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "case_report")
@Setter
@Getter
public class CaseReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer case_report_id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private String description;

    // user_nik foreign key ke cases
    @ManyToOne
    @JoinColumn(name = "user_nik", referencedColumnName = "nik")
    private User user;

    @ManyToOne
    @JoinColumn(name = "case_id", referencedColumnName = "case_id")
    private Case aCase;

}
