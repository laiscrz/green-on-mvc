package com.taligado.app.repository;

import com.taligado.app.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeviceRepository extends JpaRepository<Device, Long> {
}
