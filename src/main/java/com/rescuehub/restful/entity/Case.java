package com.rescuehub.restful.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "cases")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int case_id;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String status;

    @Column
    private String latitude;

    @Column
    private String longitude;


}
