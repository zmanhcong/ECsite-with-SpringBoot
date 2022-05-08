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

        //Kiểm tra trạng thái login(bằng cách get thông tin từ sesstion được thiết lập trong cofig/adminauthen). nếu đã login thì cho vào  ,chưa login thì redirect đến trang login

        session.setAttribute("username", account.getUsername());

        Object ruri = session.getAttribute("redirect-uri");
        if (ruri != null){
            session.removeAttribute("redirect-uri");
            return new ModelAndView("redirect:" + ruri);
        }

        return new ModelAndView("forward:/admin/categories", model);
    }
}
