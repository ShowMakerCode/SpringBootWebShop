package com.example.ShowMakerCode.Controller.HomeController;

import com.example.ShowMakerCode.Constant.SessionAttr;
import com.example.ShowMakerCode.Constant.SessionService;
import com.example.ShowMakerCode.Entity.Account;
import com.example.ShowMakerCode.Entity.Category;
import com.example.ShowMakerCode.Entity.Product;
import com.example.ShowMakerCode.Entity.ShoppingCard;
import com.example.ShowMakerCode.Service.EntiryService.CategoryService;
import com.example.ShowMakerCode.Service.EntiryService.ProductService;
import com.example.ShowMakerCode.Service.EntiryService.ShoppingCardService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("smk")
public class HomeController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ShoppingCardService shoppingCardService;
    @Autowired
    SessionService sessionService;

    @GetMapping("homepage")
    public String homepage(Model model){
        sessionService.set("urlMainText",request.getRequestURI());
        sessionService.set("countCard",shoppingCardService.getCount());
        Account account = (Account) request.getSession().getAttribute(SessionAttr.CURRENT_USER);
        String accountRole = (String) request.getSession().getAttribute(SessionAttr.ROLE_ACCOUNT);
        model.addAttribute("UserCurrent",account);
        model.addAttribute("UserRole",accountRole);
        List<Product> productListTop10 = productService.GetAllActiveSort(PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"quantitySold")));
        try{
        int pagesize = (int) Math.ceil((double)productListTop10.size()/4);
        model.addAttribute("pagesize",pagesize);
        int pageNumber;
        if (request.getParameter("numberpage") == null){pageNumber = 1;request.setAttribute("numbernext",2);}else {
            pageNumber= Integer.parseInt(request.getParameter("numberpage"));
            request.setAttribute("numberback",pageNumber-1);
            request.setAttribute("numbernext",pageNumber+1);
        }
        if (pagesize == pageNumber){
            model.addAttribute("productListTop10", productListTop10.subList((pageNumber - 1) * 4, productListTop10.size()));

        }else {
        model.addAttribute("productListTop10", productListTop10.subList((pageNumber - 1) * 4, (pageNumber * 4)));
        }}catch (Exception e){
            return "redirect:/smk/homepage";
        }
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList",categoryList);
        List<Product> productList = productService.GetAllActive();
        int pagesizeAll = (int) Math.ceil((double)productList.size()/8);
        model.addAttribute("pagesizeAll",pagesizeAll);

        try{
        int pageNumberAll;
        if (request.getParameter("numberpageAll") == null){pageNumberAll = 1;request.setAttribute("numbernextAll",2);}
        else {
            pageNumberAll= Integer.parseInt(request.getParameter("numberpageAll"));
            request.setAttribute("numberbackAll",pageNumberAll-1);
            request.setAttribute("numbernextAll",pageNumberAll+1);
        }
        if (pagesizeAll == pageNumberAll){
            model.addAttribute("productList", productList.subList((pageNumberAll - 1) * 8, productList.size()));

        }else {
            model.addAttribute("productList", productList.subList((pageNumberAll - 1) * 8, (pageNumberAll * 8)));
        }
        }catch (Exception e){
            return "redirect:/smk/homepage";
        }

    return "site/homePage";
    }

    @GetMapping("/card")
    public String shoppingCard(Model model){
        Collection<ShoppingCard> cardItems = shoppingCardService.getCardItems();
        model.addAttribute("cardItems",cardItems);
        model.addAttribute("total",shoppingCardService.getAmount());
        model.addAttribute("noOfItems",shoppingCardService.getCount());
        return "site/shoppingcard/home";
    }

    @GetMapping("/card/add/{idAddToCard}")
    public String add(@PathVariable("idAddToCard")Long productId){
        Product product = productService.findByIdProduct(productId);
        if (product != null){
            ShoppingCard item = new ShoppingCard();
            BeanUtils.copyProperties(product,item);
            item.setQuantity(1);
            shoppingCardService.add(item);
        }

        return "redirect:"+sessionService.get("urlMainText");
    }
    @GetMapping("card/remove/{idAddToCard}")
    public String remove(@PathVariable("idAddToCard")Integer idProduct){
        sessionService.set("countCard",shoppingCardService.getCount());
        shoppingCardService.remove(idProduct);
        return "redirect:/smk/card";
    }
    @GetMapping("card/update")
    public String update(Model model, @ModelAttribute("quantity")Integer quantity,@ModelAttribute("idpro")Long productId){
        System.out.println(quantity);
        System.out.println(productId);
        shoppingCardService.update(productId,quantity);
        return "redirect:/smk.card";
    }
    @GetMapping("card/clear")
    public String clear(){
        sessionService.set("countCard",shoppingCardService.getCount());
        shoppingCardService.clear();
        return "redirect:/smk/card";
    }

    @GetMapping("homepage/category/{idCategory}")
    public String categoryList(Model model,@PathVariable("idCategory")Long idCate){
        if (idCate == null){
            return "redirect:/smk/homepage";
        }
        sessionService.set("urlMainText",request.getRequestURI());
        model.addAttribute("cateId",idCate);
        sessionService.set("countCard",shoppingCardService.getCount());
        Account account = (Account) request.getSession().getAttribute(SessionAttr.CURRENT_USER);
        String accountRole = (String) request.getSession().getAttribute(SessionAttr.ROLE_ACCOUNT);
        model.addAttribute("UserCurrent",account);
        model.addAttribute("UserRole",accountRole);
        List<Product> productListTop10 = productService.GetAllActiveSort(PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"quantitySold")));
        try{
            int pagesize = (int) Math.ceil((double)productListTop10.size()/4);
            model.addAttribute("pagesize",pagesize);
            int pageNumber;
            if (request.getParameter("numberpage") == null){pageNumber = 1;request.setAttribute("numbernext",2);}else {
                pageNumber= Integer.parseInt(request.getParameter("numberpage"));
                request.setAttribute("numberback",pageNumber-1);
                request.setAttribute("numbernext",pageNumber+1);
            }
            if (pagesize == pageNumber){
                model.addAttribute("productListTop10", productListTop10.subList((pageNumber - 1) * 4, productListTop10.size()));

            }else {
                model.addAttribute("productListTop10", productListTop10.subList((pageNumber - 1) * 4, (pageNumber * 4)));
            }}catch (Exception e){
            return "redirect:/smk/homepage";
        }
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList",categoryList);
        List<Product> productList = productService.findByCategory(idCate);
        int pagesizeAll = (int) Math.ceil((double)productList.size()/8);
        model.addAttribute("pagesizeAll",pagesizeAll);

        try{
            int pageNumberAll;
            if (request.getParameter("numberpageAll") == null){pageNumberAll = 1;request.setAttribute("numbernextAll",2);}
            else {
                pageNumberAll= Integer.parseInt(request.getParameter("numberpageAll"));
                request.setAttribute("numberbackAll",pageNumberAll-1);
                request.setAttribute("numbernextAll",pageNumberAll+1);
            }
            if (pagesizeAll == pageNumberAll){
                model.addAttribute("productList", productList.subList((pageNumberAll - 1) * 8, productList.size()));

            }else {
                model.addAttribute("productList", productList.subList((pageNumberAll - 1) * 8, (pageNumberAll * 8)));
            }
        }catch (Exception e){
            return "redirect:/smk/homepage";
        }
    return "site/HomeForCategory/productLsitCategory";
    }




}
