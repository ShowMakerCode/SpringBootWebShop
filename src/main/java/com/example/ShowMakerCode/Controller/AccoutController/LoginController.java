package com.example.ShowMakerCode.Controller.AccoutController;

import com.example.ShowMakerCode.Constant.AlertService;
import com.example.ShowMakerCode.Constant.SessionLogin;
import com.example.ShowMakerCode.Constant.SessionService;
import com.example.ShowMakerCode.Entity.Account;
import com.example.ShowMakerCode.Service.EntiryService.AccountRoleService;
import com.example.ShowMakerCode.Service.EntiryService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("smk/site")
public class LoginController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    AccountService accountService;
    @Autowired
    SessionLogin sessionLogin;
    @Autowired
    AccountRoleService accountRoleService;
    @Autowired
    AlertService alearService;
    @Autowired
    SessionService sessionService;

    @GetMapping("login")
    public String home(Model model){
        return "site/account/login";
    }


    @PostMapping("login")
    public String login(Model model){
       Account account =  accountService.findByUsernameandPass(request.getParameter("username"),request.getParameter("password"));
       if (account == null){
           return "redirect:/smk/site/login";
       }else {
           sessionLogin.userIsLogin(account);
       }
       return "redirect:/smk/homepage";
    }

    @GetMapping("logout")
    public String logout(){
        sessionLogin.logout();
        return "redirect:/smk/site/login";
    }

}
