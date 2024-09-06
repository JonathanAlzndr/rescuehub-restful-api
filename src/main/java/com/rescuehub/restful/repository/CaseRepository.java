package com.rescuehub.restful.repository;

import com.rescuehub.restful.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Case, Integer> {

}
