package com.rescuehub.restful.repository;

import com.rescuehub.restful.entity.Case;
import com.rescuehub.restful.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaseRepository extends JpaRepository<Case, Integer> {
}
