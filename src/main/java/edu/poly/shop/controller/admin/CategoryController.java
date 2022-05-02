package edu.poly.shop.controller.admin;

import edu.poly.shop.domain.Category;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("add")
    public String add(Model model){
        model.addAttribute("category", new CategoryDto());
        System.out.println("add ok");
        return "admin/categories/addOrEdit";
    }

    @GetMapping("edit/{categoryId}")
    public String edit(){
        return "admin/categories/addOrEdit";
    }

    @GetMapping("delete/{categoryId}")
    public String delete(){
        return  "redirect:/admin/categories";  //return ve list
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, CategoryDto dto){
        Category entity = new Category();
        BeanUtils.copyProperties(dto, entity);
        categoryService.save(entity);
        model.addAttribute("message", "Category is saved!!(message from controller)");
        System.out.println("add ok");
        return new ModelAndView("forward:/admin/categories", model);
    }

    @RequestMapping("")
    public String list(ModelMap model){
        List<Category> list = categoryService.findAll();
        model.addAttribute("categories", list);
        return "admin/categories/list";
    }
    @GetMapping("search")
    public String search(){
        return "admin/categories/search";
    }
}
