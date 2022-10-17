package com.example.ShowMakerCode.Controller.AccoutController;

import com.example.ShowMakerCode.Constant.SessionAttr;
import com.example.ShowMakerCode.Constant.SessionService;
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

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("smk/site")
public class RegisterController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    AccountRoleService accountRoleService;
    @Autowired
    AccountService accountService;
    @Autowired
    RoleService roleService;
    @Autowired
    SessionService sessionService;


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
}
