package com.taligado.app.service;

import com.taligado.app.model.BranchOffice;
import com.taligado.app.repository.IBranchOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchOfficeService {
    @Autowired
    private IBranchOfficeRepository branchOfficeRepository;

    // Retorna todos
    public List<BranchOffice> findAllBranchOffices() {
        return branchOfficeRepository.findAll();
    }

    // Busca pelo ID
    public BranchOffice findByIdBranchOffice(Long id) {
        return branchOfficeRepository.findById(id).orElse(null);
    }

    // Salva ou atualiza
    public void saveBranchOffice(BranchOffice branchOffice) {
        branchOffice.calcularConsumoEnergia();
        branchOffice.calcularEmissoesCarbono();

        branchOfficeRepository.save(branchOffice);
    }

    // Remove pelo ID
    public void deleteByIdIBranchOffice(Long id) {
        branchOfficeRepository.deleteById(id);
    }
}
