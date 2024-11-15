package com.taligado.app.controller;

import com.taligado.app.model.BranchOffice;
import com.taligado.app.model.enums.SegmentType;
import com.taligado.app.service.BranchOfficeService;
import com.taligado.app.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/branch-offices")
public class BranchOfficeController {

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public ModelAndView showListBranchOffices(@RequestParam(required = false) String message, @RequestParam(required = false) String error) {
        List<BranchOffice> branchOffices = branchOfficeService.findAllBranchOffices();
        ModelAndView modelAndView = new ModelAndView("branch/list");
        modelAndView.addObject("branchOffices", branchOffices);
        modelAndView.addObject("message", message);
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showBranchOfficeDetails(@PathVariable Long id) {
        BranchOffice branchOffice = branchOfficeService.findByIdBranchOffice(id);
        ModelAndView modelAndView = new ModelAndView("branch/details");
        modelAndView.addObject("branch", branchOffice);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView showCreateBranchOfficeForm() {
        ModelAndView modelAndView = new ModelAndView("branch/form");
        modelAndView.addObject("branch", new BranchOffice());
        modelAndView.addObject("devices", deviceService.findAllDevices());
        return modelAndView;
    }

    @PostMapping
    public String saveBranchOffice(@Valid @ModelAttribute BranchOffice branchOffice) {
        branchOfficeService.saveBranchOffice(branchOffice);
        return "redirect:/branch-offices";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditBranchOfficeForm(@PathVariable Long id) {
        BranchOffice branchOffice = branchOfficeService.findByIdBranchOffice(id);
        ModelAndView modelAndView = new ModelAndView("branch/form");
        modelAndView.addObject("branch", branchOffice);
        modelAndView.addObject("devices", deviceService.findAllDevices());
        return modelAndView;
    }

    @PostMapping("/{id}")
    public String updateBranchOffice(@PathVariable Long id, @ModelAttribute BranchOffice branchOffice) {
        branchOffice.setId(id);
        branchOfficeService.saveBranchOffice(branchOffice);
        return "redirect:/branch-offices";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteBranchOffice(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("branch/list");
        try {
            branchOfficeService.deleteByIdIBranchOffice(id);
            modelAndView.addObject("message", "Filial excluída com sucesso.");
        } catch (RuntimeException e) {
            modelAndView.addObject("error", e.getMessage());
        }
        modelAndView.addObject("branchOffices", branchOfficeService.findAllBranchOffices());
        return modelAndView;
    }

    @ModelAttribute("segmento")
    public SegmentType[] getSegmento(){
        return SegmentType.values();
    }
}
