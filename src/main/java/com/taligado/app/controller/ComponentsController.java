package com.taligado.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ComponentsController {

    @GetMapping("/control")
    public ModelAndView showControlPanel() {
        return new ModelAndView("components/control-panel");
    }

    @GetMapping("/access-denied")
    public ModelAndView accessDenied() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("components/access-denied");
        return modelAndView;
    }
}
