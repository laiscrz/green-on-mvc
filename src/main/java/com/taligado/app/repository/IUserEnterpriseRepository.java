package com.taligado.app.repository;

import com.taligado.app.model.UserEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserEnterpriseRepository extends JpaRepository<UserEnterprise, Long> {
    Optional<UserEnterprise> findByUsername(String username);
}
