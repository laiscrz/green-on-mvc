package com.taligado.app.service;


import com.taligado.app.model.Device;
import com.taligado.app.repository.IDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private IDeviceRepository deviceRepository;

    // Retorna todos
    public List<Device> findAllDevices() {
        return deviceRepository.findAll();
    }

    // Busca uma pelo ID
    public Device findByIdDevice(Long id) {
        return deviceRepository.findById(id).orElse(null);
    }

    // Cria ou atualiza
    public void saveDevice(Device device) {
        deviceRepository.save(device);
    }

    // Remove pelo ID
    public void deleteByIdDevice(Long id) {
        Device device = findByIdDevice(id);
        if (device != null && (device.getFiliais() != null && !device.getFiliais().isEmpty())) {
            throw new RuntimeException("O dispositivo está associado a uma ou mais filiais. Remova-o de todas as filiais antes de tentar excluí-lo.");
        }
        deviceRepository.deleteById(id);
    }

}
