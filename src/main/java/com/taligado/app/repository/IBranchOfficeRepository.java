package com.taligado.app.repository;

import com.taligado.app.model.BranchOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBranchOfficeRepository extends JpaRepository<BranchOffice, Long> {
}
