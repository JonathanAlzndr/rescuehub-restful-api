package com.rescuehub.restful.repository;

import com.rescuehub.restful.entity.CaseReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseReportRepository extends JpaRepository<CaseReport, Integer> {
}
