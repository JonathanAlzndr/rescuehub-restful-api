package com.rescuehub.restful.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
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
    private Timestamp createdAt;

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

    @OneToMany(mappedBy = "aCase")
    private List<CaseReport> caseReportList;
}
