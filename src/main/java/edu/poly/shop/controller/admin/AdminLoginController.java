package edu.poly.shop.controller.admin;

import edu.poly.shop.domain.Account;
import edu.poly.shop.model.AdminLoginDto;
import edu.poly.shop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AdminLoginController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private HttpSession session;

    @GetMapping("alogin")
    public String login(ModelMap model){
        model.addAttribute("account", new AdminLoginDto());
        return "/admin/accounts/login";
    }

    @PostMapping("alogin")
    public ModelAndView login(ModelMap model,
                              @Valid @ModelAttribute("account") AdminLoginDto dto,
                              BindingResult result){
        if (result.hasErrors()){
            return new ModelAndView("/admin/accounts/login",model);
        }

            Account account = accountService.login(dto.getUsername(), dto.getPassword());
        if (account == null){
            model.addAttribute("message", "Invalid username or password");
            return new ModelAndView("/admin/accounts/login", model);
        }

        // Set the 'username' attribute in the session object to the authenticated account's username
        session.setAttribute("username", account.getUsername());
        // Get the 'redirect-uri' attribute from the session object
        Object ruri = session.getAttribute("redirect-uri");
        if (ruri != null) {
            // Remove the old 'redirect-uri' attribute from the session object, and set new redirect-uri in interceptor.java
            session.removeAttribute("redirect-uri");

            // Create a new ModelAndView object that redirects to the saved 'redirect-uri', this uri was saved in interceptor.java
            return new ModelAndView("redirect:" + ruri);
        }

        // If the 'redirect-uri' attribute is null, create a new ModelAndView object that forwards to the '/admin/categories' page(that mean user access to aloginPage first.)
        return new ModelAndView("forward:/admin/categories", model);
    }
}
