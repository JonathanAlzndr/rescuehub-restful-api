package com.rescuehub.restful.repository;

import com.rescuehub.restful.entity.CaseReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseReportRepository extends JpaRepository<CaseReport, Integer> {
}
