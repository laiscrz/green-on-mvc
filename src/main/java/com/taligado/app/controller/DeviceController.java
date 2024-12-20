package com.taligado.app.controller;

import com.taligado.app.model.Device;
import com.taligado.app.model.enums.DepartmentType;
import com.taligado.app.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public ModelAndView showListDevices() {

        List<Device> devices = deviceService.findAllDevices();
        ModelAndView modelAndView = new ModelAndView("device/list");
        modelAndView.addObject("devices", devices);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showDeviceDetails(@PathVariable Long id) {
        Device device = deviceService.findByIdDevice(id);
        ModelAndView modelAndView = new ModelAndView("device/details");
        modelAndView.addObject("device", device);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView showCreateDeviceForm() {
        ModelAndView modelAndView = new ModelAndView("device/form");
        modelAndView.addObject("device", new Device());
        return modelAndView;
    }

    @PostMapping
    public String saveDevice(@ModelAttribute Device device) {
        deviceService.saveDevice(device);
        return "redirect:/devices";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditDeviceForm(@PathVariable Long id) {
        Device device = deviceService.findByIdDevice(id);
        ModelAndView modelAndView = new ModelAndView("device/form");
        modelAndView.addObject("device", device);
        return modelAndView;
    }

    @PostMapping("/{id}")
    public String updateDevice(@PathVariable Long id, @ModelAttribute Device device) {
        device.setId(id);
        deviceService.saveDevice(device);
        return "redirect:/devices";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteDevice(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("device/list");
        try {
            deviceService.deleteByIdDevice(id);

            modelAndView.addObject("message", "Dispositivo excluído com sucesso.");
        } catch (RuntimeException e) {
            modelAndView.addObject("error", e.getMessage());
        }
        modelAndView.addObject("devices", deviceService.findAllDevices());
        return modelAndView;
    }

    @ModelAttribute("departamento")
    public DepartmentType[] getSetor(){
        return DepartmentType.values();
    }

}
