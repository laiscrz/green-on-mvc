package com.taligado.app.controller;

import com.taligado.app.model.UserEnterprise;
import com.taligado.app.service.UserEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserEnterpriseController {

    @Autowired
    private UserEnterpriseService userProfileService;

    @GetMapping("/login")
    public ModelAndView showLoginView() {
        ModelAndView modelAndView = new ModelAndView("user/login");
        modelAndView.addObject("user", new UserEnterprise());

        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterView() {
        ModelAndView modelAndView = new ModelAndView("user/register");
        modelAndView.addObject("user", new UserEnterprise());
        modelAndView.addObject("roles", userProfileService.getAllRoles());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(UserEnterprise userEnterprise, BindingResult bindingResult, @RequestParam(name = "id_role") Long idRole) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("user/register");
            mv.addObject("user", userEnterprise);
            mv.addObject("roles", userProfileService.getAllRoles());
            return mv;
        } else {
            userProfileService.registerNewUser(userEnterprise, idRole);
            return new ModelAndView("redirect:/login");
        }
    }


    @PostMapping("/delete-account")
    public ModelAndView deleteUserAccount() {
        Optional<UserEnterprise> user = userProfileService.getLoggedInUser();

        if (user.isPresent()) {

            userProfileService.deleteUser(user.get().getUsername());

            return new ModelAndView("redirect:/login");
        }

        return new ModelAndView("error");
    }
}

