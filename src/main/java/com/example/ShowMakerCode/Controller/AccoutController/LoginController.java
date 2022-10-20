package com.example.ShowMakerCode.Controller.AccoutController;

import com.example.ShowMakerCode.Constant.*;
import com.example.ShowMakerCode.Entity.Account;
import com.example.ShowMakerCode.Entity.AccountRole;
import com.example.ShowMakerCode.Entity.AccountRoleId;
import com.example.ShowMakerCode.Service.EntiryService.AccountRoleService;
import com.example.ShowMakerCode.Service.EntiryService.AccountService;
import com.example.ShowMakerCode.Service.EntiryService.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    @Autowired
    RoleService roleService;

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
    @GetMapping("register")
    public String homepage(Model model){
        model.addAttribute("accountRegister",new Account());
        return "site/account/register";
    }
    @PostMapping("register")
    public String register(Model model,@ModelAttribute("accountRegister")Account account) throws ParseException {
        AccountRole accountRole = new AccountRole();
        account.setDob(new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("dayOfBirth")));
        account.setCreateDate(new Date().toInstant());
        account.setStatus((byte) 1);
        Account accountCurrent = sessionService.get(SessionAttr.CURRENT_USER);
        account.setCreateBy(accountCurrent.getUsername());
        accountService.save(account);
        accountRole.setId(new AccountRoleId());
        accountRole.setAccount(accountService.findByUsernameandPass(account.getUsername(),account.getPassword()));
        accountRole.setRole(roleService.findByNameRole("user"));
        accountRoleService.save(accountRole);
        return "redirect:/smk/site/login";
    }

    @GetMapping("forgotpassword")
    public String getForgot(){
       return "site/account/forgotpass";
    }

    @PostMapping("forgotpassword")
    public String postForgot(@ModelAttribute("email")String email,Model model) throws MessagingException, UnsupportedEncodingException {
        Account account = accountService.findByEmail(email);
        if (account==null){
            model.addAttribute("email",email);
            return "redirect:/smk/site/forgotpassword";
        }
        MailerService.Send(account.getEmail(),"Your Pass Word : "+account.getPassword());
        return "redirect:/smk/site/login";
    }

}
