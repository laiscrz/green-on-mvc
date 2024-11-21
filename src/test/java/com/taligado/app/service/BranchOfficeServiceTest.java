package com.taligado.app.service;

import com.taligado.app.model.BranchOffice;
import com.taligado.app.repository.IBranchOfficeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchOfficeServiceTest {

    @InjectMocks
    private BranchOfficeService branchOfficeService;

    @Mock
    private IBranchOfficeRepository branchOfficeRepository;

    @Test
    void testFindAllBranchOffices_Success() {
        // Arrange
        List<BranchOffice> branchOffices = new ArrayList<>();
        branchOffices.add(new BranchOffice());
        when(branchOfficeRepository.findAll()).thenReturn(branchOffices);

        // Act
        List<BranchOffice> result = branchOfficeService.findAllBranchOffices();

        // Assert
        assertEquals(1, result.size());
        verify(branchOfficeRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdBranchOffice_Success() {
        // Arrange
        BranchOffice mockBranchOffice = new BranchOffice();
        when(branchOfficeRepository.findById(anyLong())).thenReturn(Optional.of(mockBranchOffice));

        // Act
        BranchOffice result = branchOfficeService.findByIdBranchOffice(1L);

        // Assert
        assertNotNull(result); // Verificando se o resultado não é nulo
        verify(branchOfficeRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdBranchOffice_Failure() {
        // Arrange
        when(branchOfficeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        BranchOffice result = branchOfficeService.findByIdBranchOffice(1L);

        // Assert
        assertNull(result);
        verify(branchOfficeRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveBranchOffice_Success() {
        // Arrange
        BranchOffice mockBranchOffice = mock(BranchOffice.class);
        doNothing().when(mockBranchOffice).calcularConsumoEnergia();
        doNothing().when(mockBranchOffice).calcularEmissoesCarbono();

        // Act
        branchOfficeService.saveBranchOffice(mockBranchOffice);

        // Assert
        verify(mockBranchOffice, times(1)).calcularConsumoEnergia();
        verify(mockBranchOffice, times(1)).calcularEmissoesCarbono();
        verify(branchOfficeRepository, times(1)).save(mockBranchOffice);
    }


    @Test
    void testDeleteByIdBranchOffice_Success() {
        // Arrange
        BranchOffice branchOffice = new BranchOffice();
        lenient().when(branchOfficeRepository.findById(anyLong())).thenReturn(Optional.of(branchOffice));

        // Act
        branchOfficeService.deleteByIdIBranchOffice(1L);

        // Assert
        verify(branchOfficeRepository, times(1)).deleteById(1L);
    }

}
