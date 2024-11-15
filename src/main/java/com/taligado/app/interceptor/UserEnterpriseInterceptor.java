package com.taligado.app.interceptor;

import com.taligado.app.model.UserEnterprise;
import com.taligado.app.service.UserEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.lang.NonNull;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class UserEnterpriseInterceptor implements HandlerInterceptor {

    @Autowired
    private UserEnterpriseService userEnterpriseService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        Optional<UserEnterprise> user = userEnterpriseService.getLoggedInUser();

        user.ifPresent(u -> {
            request.setAttribute("user", u);
            request.setAttribute("userName", u.getNome());
        });

        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request,
                           @NonNull HttpServletResponse response,
                           @NonNull Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            if (request.getAttribute("user") != null) {
                UserEnterprise user = (UserEnterprise) request.getAttribute("user");
                modelAndView.addObject("user", user);
                modelAndView.addObject("userName", request.getAttribute("userName"));
            }
        }
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                @Nullable Exception ex) throws Exception {

    }
}

