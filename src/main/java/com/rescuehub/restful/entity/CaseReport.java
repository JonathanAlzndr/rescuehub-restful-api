package com.rescuehub.restful.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "case_report")
@Getter
@Setter
public class CaseReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int case_report_id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private String description;

    // user_nik foreign key ke cases
    @ManyToOne
    @JoinColumn(name = "user_nik", referencedColumnName = "nik")
    private User user;

}
