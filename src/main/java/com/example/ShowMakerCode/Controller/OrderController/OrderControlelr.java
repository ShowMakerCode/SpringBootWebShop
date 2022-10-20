package com.example.ShowMakerCode.Controller.OrderController;

import com.example.ShowMakerCode.Constant.SessionAttr;
import com.example.ShowMakerCode.Constant.SessionService;
import com.example.ShowMakerCode.Entity.*;
import com.example.ShowMakerCode.Service.EntiryService.OrderDetailService;
import com.example.ShowMakerCode.Service.EntiryService.OrderService;
import com.example.ShowMakerCode.Service.EntiryService.ProductService;
import com.example.ShowMakerCode.Service.EntiryService.ShoppingCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("smk/card")
public class OrderControlelr {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    ShoppingCardService shoppingCardService;
    @Autowired
    SessionService sessionService;
    @Autowired
    ProductService productService;
    @GetMapping("buy")
    public String Hello(){
        try {
        Collection<ShoppingCard> shoppingCards = shoppingCardService.getCardItems();
        Account account = sessionService.get(SessionAttr.CURRENT_USER);
        Order order = orderService.findByCreater(account.getUsername());
        for (int i = 0; i < shoppingCards.size(); i++) {
            ShoppingCard shoppingCard = (ShoppingCard) shoppingCards.toArray()[i];
            Product product = productService.findByIdProduct(shoppingCard.getId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setOrder(order);
            orderDetail.setQuantity(shoppingCard.getQuantity());
            orderDetail.setSize(shoppingCard.getSize());
            orderDetail.setPrice(BigDecimal.valueOf(Double.parseDouble(String.valueOf(shoppingCard.getPrice()))*shoppingCard.getQuantity()));
            //set so luong giam
            product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
            product.setQuantitySold(product.getQuantitySold()+orderDetail.getQuantity());
            productService.saveAndFlush(product);
            orderDetailService.save(orderDetail);
        }}catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/smk/card/clear";
    }
}
