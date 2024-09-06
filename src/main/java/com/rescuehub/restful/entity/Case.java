package com.rescuehub.restful.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column
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

    @OneToMany(mappedBy = "aCase")
    private List<CaseReport> caseReportList;
}
