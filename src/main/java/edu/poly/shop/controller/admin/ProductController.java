package edu.poly.shop.controller.admin;

import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Product;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.model.ProductDto;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.StorageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("admin/products")
public class ProductController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    StorageService storageService;
    @ModelAttribute("categories")
    public List<CategoryDto> getCategories(){
        return categoryService.findAll().stream().map(item->{  //chuyển thành stream, rồi chuyển thành map để map mỗi phần tử nhận được thành CategoryDto
            CategoryDto dto = new CategoryDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).toList();
    }

    @GetMapping("add")
    public String add(Model model){
        ProductDto dto = new ProductDto();
        dto.setIsEdit(false);
        model.addAttribute("product",dto );
        return "admin/products/addOrEdit";
    }

    @GetMapping("edit/{productId}")
    public ModelAndView edit(ModelMap model, @PathVariable("productId") Long productId) {
        Optional<Product> opt = productService.findById(productId);
        ProductDto dto = new ProductDto();

        if (opt.isPresent()) {
            Product entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true); //thêm cái này để biết là đang là add hay edit, để mà ẩn hiện button ở views html

            model.addAttribute("product", dto);
            return new ModelAndView("admin/products/addOrEdit", model);
        }

        model.addAttribute("message", "Product is not exited");
        return new ModelAndView("forward:/admin/products", model);
    }

    @GetMapping("images/{filename:.+}") //for display images in addOrEdit.html
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("delete/{productId}")
    public ModelAndView delete(ModelMap model,
            @PathVariable("productId") Long productId)
            throws IOException {

            Optional<Product> opt = productService.findById(productId);
            if (opt.isPresent()){
                if (!StringUtils.isEmpty(opt.get().getImage())){
                    storageService.delete(opt.get().getImage());
                }
                productService.delete(opt.get());
                model.addAttribute("message", "Product is deleted!");
            }else {
                model.addAttribute("message", "Product is not found!");
            }

        return new ModelAndView("forward:/admin/products/search", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,
             @Valid @ModelAttribute("product") ProductDto dto, BindingResult result){  //@Valid,BindingResult for validate category

        if(result.hasErrors()){
            return new ModelAndView("admin/products/addOrEdit");
        }

        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);     //save to database. entity is created by Product()

        Category category = new Category();
        category.setCategoryId(dto.getCategoryId());
        entity.setCategory(category);

        if (!dto.getImageFile().isEmpty()){
            UUID uuid = UUID.randomUUID();    //UUID cho nhận dạng ký tự
            String uuString = uuid.toString();

            entity.setImage(storageService.getStorageFilename(dto.getImageFile(), uuString));
            storageService.store(dto.getImageFile(), entity.getImage());
        }

        productService.save(entity);
        model.addAttribute("message", "Product is saved!!");
        return new ModelAndView("forward:/admin/products", model);
    }

    @RequestMapping("")
    public String list(ModelMap model){
        List<Product> list = productService.findAll();
        model.addAttribute("products", list);
        return "/admin/products/search";
    }
    @GetMapping("search")
    public String search(ModelMap model,
           @RequestParam(name = "name", required = false) String name){

        List<Product> list = null;

        if (StringUtils.hasText(name)){
            list = productService.findByNameContaining(name);
        }
        else {
            list = productService.findAll();
        }
        model.addAttribute("products", list);
        return "/admin/products/search";
    }

    @GetMapping("searchpaginated")
    public String search(ModelMap model,
                         @RequestParam(name = "name", required = false) String name,
                         @RequestParam("page")Optional<Integer> page,
                         @RequestParam("size")Optional<Integer> size){

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));
        Page<Category> resultPage = null;


        if (StringUtils.hasText(name)){   /*//nếu có search thì trong màn hình kết quả search cũng có phân trang*/
            resultPage = categoryService.findByNameContaining(name, pageable);
            model.addAttribute("name", name);
        }
        else {
            resultPage = categoryService.findAll(pageable);
        }

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage-2);
            int end = Math.min(currentPage + 2, totalPages);

            //Nghĩa là : nếu page hiện tại là page 3, thì sẽ start từ page 1 và sẽ end từ page 5(3+2)

//            if (totalPages > 5) {
//                if (end == totalPages) start = end -5;
//                else if (start == 1) end = start + 5;
//                }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("categoryPage", resultPage);
        return "/admin/products/searchpaginated";
    }
}