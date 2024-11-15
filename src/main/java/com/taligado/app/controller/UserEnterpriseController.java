package com.taligado.app.controller;

import com.taligado.app.model.UserEnterprise;
import com.taligado.app.service.UserEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserEnterpriseController {

    @Autowired
    private UserEnterpriseService userEnterpriseService;

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
        modelAndView.addObject("roles", userEnterpriseService.getAllRoles());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(UserEnterprise userEnterprise, BindingResult bindingResult, @RequestParam(name = "id_role") Long idRole) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("user/register");
            mv.addObject("user", userEnterprise);
            mv.addObject("roles", userEnterpriseService.getAllRoles());
            return mv;
        } else {
            userEnterpriseService.registerNewUser(userEnterprise, idRole);
            return new ModelAndView("redirect:/login");
        }
    }

    @GetMapping("/profile/{username}")
    public ModelAndView verProfile(@PathVariable("username") String username) {
        Optional<UserEnterprise> user = userEnterpriseService.getUserByUsername(username);

        if (user.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("user/profile");
            modelAndView.addObject("user", user.get());
            return modelAndView;
        } else {
            return new ModelAndView("error");
        }
    }

    @GetMapping("/profile/edit/{id}")
    public ModelAndView editProfile(@PathVariable("id") Long id) {
        Optional<UserEnterprise> loggedInUserOptional = userEnterpriseService.getLoggedInUser();

        if (loggedInUserOptional.isPresent()) {
            UserEnterprise loggedInUser = loggedInUserOptional.get();

            if (loggedInUser.getId().equals(id)) {
                ModelAndView modelAndView = new ModelAndView("user/edit-profile");
                modelAndView.addObject("user", loggedInUser); 
                return modelAndView;
            } else {
                return new ModelAndView("error"); 
            }
        }

        return new ModelAndView("redirect:/login"); 
    }


    @PostMapping("/profile/edit/{id}")
    public ModelAndView saveProfile(@ModelAttribute UserEnterprise user, @PathVariable("id") Long id) {
        Optional<UserEnterprise> loggedInUserOptional = userEnterpriseService.getLoggedInUser();

        if (loggedInUserOptional.isPresent()) {
            UserEnterprise loggedInUser = loggedInUserOptional.get();

            if (loggedInUser.getId().equals(id)) {
                user.setId(loggedInUser.getId()); 

                if (user.getImgPerfil() != null && !user.getImgPerfil().isEmpty()) {
                    user.updateImgPerfil(user.getImgPerfil());
                }

                userEnterpriseService.updateUser(user);

                return new ModelAndView("redirect:/profile/" + loggedInUser.getUsername()); 
            } else {
                return new ModelAndView("error"); 
            }
        }

        return new ModelAndView("redirect:/login"); 
    }

    @PostMapping("/delete-account")
    public ModelAndView deleteUserAccount() {
        Optional<UserEnterprise> user = userEnterpriseService.getLoggedInUser();

        if (user.isPresent()) {

            userEnterpriseService.deleteUser(user.get().getUsername());

            return new ModelAndView("redirect:/login");
        }

        return new ModelAndView("error");
    }
}

