package edu.poly.shop.HomeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class home2 {
    @GetMapping("")
    public String home1() {
        return "admin/categories/search";
    }
}
