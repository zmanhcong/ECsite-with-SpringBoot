package edu.poly.shop.controller.admin;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.model.AccountDto;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("add")
    public String add(Model model){
        model.addAttribute("account", new AccountDto());
        return "admin/accounts/addOrEdit";
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,
            @Valid @ModelAttribute("account") AccountDto dto, BindingResult result){  //validate cho account

        if(result.hasErrors()){
            return new ModelAndView("admin/accounts/addOrEdit");
        }

        Account entity = new Account();
        BeanUtils.copyProperties(dto, entity);
        accountService.save(entity);
        model.addAttribute("message", "account is saved!!");
        return new ModelAndView("forward:/admin/accounts", model);
    }

        @RequestMapping("")
    public String list(ModelMap model){
        List<Account> list = accountService.findAll();
        model.addAttribute("accounts", list);
        return "/admin/accounts/search";
    }

        @GetMapping("search")
    public String search(ModelMap model,
           @RequestParam(name = "name", required = false) String username){

        List<Account> list = null;

        if (StringUtils.hasText(username)){
            list = accountService.findByUsernameContaining(username);
        }
        else {
            list = accountService.findAll();
        }
        model.addAttribute("accounts", list);
        return "/admin/accounts/search";
    }

    @GetMapping("edit/{userId}")
    public ModelAndView edit(ModelMap model, @PathVariable("userId") Long userId) {
        Optional<Account> opt = accountService.findById(userId);
        AccountDto dto = new AccountDto();

        if (opt.isPresent()) {     //get acount. nếu acount đã tồn tại, nghĩa là edit acount thì mã hóa password, nếu acount chưa tồn tại ( tạo mới thì cũng mã hóa password)
            Account entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);                         //thêm cái này để biết là đang là add hay edit, để mà ẩn hiện button ở views html

            dto.setPassword("");                          // Dont show password
            model.addAttribute("account", dto);
            return new ModelAndView("admin/accounts/addOrEdit", model);
        }else {
            model.addAttribute("message", "account is not exited");
        }
        return new ModelAndView("forward:/admin/accounts", model);
    }

    @GetMapping("delete/{userId}")
    public ModelAndView delete(ModelMap model,
            @PathVariable("userId") Long userId){
            accountService.deleteById(userId);
            model.addAttribute("message", "account is deleted!");
        return  new ModelAndView("forward:/admin/accounts/search", model);
    }

//
//    @GetMapping("searchpaginated")
//    public String search(ModelMap model,
//                         @RequestParam(name = "name", required = false) String name,
//                         @RequestParam("page")Optional<Integer> page,
//                         @RequestParam("size")Optional<Integer> size){
//
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(5);
//
//        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));
//        Page<account> resultPage = null;
//
//
//        if (StringUtils.hasText(name)){   /*//nếu có search thì trong màn hình kết quả search cũng có phân trang*/
//            resultPage = accountService.findByNameContaining(name, pageable);
//            model.addAttribute("name", name);
//        }
//        else {
//            resultPage = accountService.findAll(pageable);
//        }
//
//        int totalPages = resultPage.getTotalPages();
//        if (totalPages > 0) {
//            int start = Math.max(1, currentPage-2);
//            int end = Math.min(currentPage + 2, totalPages);
//
//            //Nghĩa là : nếu page hiện tại là page 3, thì sẽ start từ page 1 và sẽ end từ page 5(3+2)
//
////            if (totalPages > 5) {
////                if (end == totalPages) start = end -5;
////                else if (start == 1) end = start + 5;
////                }
//            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//        model.addAttribute("accountPage", resultPage);
//        return "/admin/accounts/searchpaginated";
//    }
}
