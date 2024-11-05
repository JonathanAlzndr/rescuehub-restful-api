package com.rescuehub.restful.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cases")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer case_id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column
    private String status;

    @Column
    private String latitude;

    @Column
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "user_nik", // foreign key
    referencedColumnName = "nik") // primary
    private User user;

    @Column
    private String deskripsi;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "aCase")
    private List<CaseReport> caseReportList;
}
