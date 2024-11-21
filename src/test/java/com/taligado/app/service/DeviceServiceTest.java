package com.taligado.app.service;

import com.taligado.app.model.BranchOffice;
import com.taligado.app.model.Device;
import com.taligado.app.repository.IDeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @InjectMocks
    private DeviceService deviceService;

    @Mock
    private IDeviceRepository deviceRepository;

    @Mock
    private Device device;

    @Test
    void testFindAllDevices_Success() {
        // Arrange
        List<Device> devices = List.of(new Device(), new Device());
        when(deviceRepository.findAll()).thenReturn(devices);

        // Act
        List<Device> result = deviceService.findAllDevices();

        // Assert
        assertEquals(2, result.size());
        verify(deviceRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdDevice_Success() {
        // Arrange
        Device mockDevice = new Device();
        when(deviceRepository.findById(anyLong())).thenReturn(Optional.of(mockDevice));

        // Act
        Device result = deviceService.findByIdDevice(1L);

        // Assert
        assertNotNull(result);
        verify(deviceRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdDevice_Failure() {
        // Arrange
        when(deviceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Device result = deviceService.findByIdDevice(1L);

        // Assert
        assertNull(result);
        verify(deviceRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveDevice_Success() {
        // Arrange
        Device mockDevice = new Device();

        // Act
        deviceService.saveDevice(mockDevice);

        // Assert
        verify(deviceRepository, times(1)).save(mockDevice);
    }

    @Test
    void testDeleteByIdDevice_Success() {
        // Arrange
        Long deviceId = 1L;
        Device mockDevice = new Device();
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(mockDevice));

        // Act
        deviceService.deleteByIdDevice(deviceId);

        // Assert
        verify(deviceRepository, times(1)).deleteById(deviceId);
    }

    @Test
    void testDeleteByIdDevice_Failure_DeviceAssociated() {
        // Arrange
        Long deviceId = 1L;
        Device mockDevice = new Device();
        mockDevice.setFiliais(List.of(new BranchOffice()));
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(mockDevice));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deviceService.deleteByIdDevice(deviceId);
        });
        assertEquals("O dispositivo está associado a uma ou mais filiais. Remova-o de todas as filiais antes de tentar excluí-lo.", exception.getMessage());
        verify(deviceRepository, never()).deleteById(deviceId);
    }
}
