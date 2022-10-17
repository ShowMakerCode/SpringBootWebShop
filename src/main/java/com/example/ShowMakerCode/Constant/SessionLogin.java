package com.example.ShowMakerCode.Constant;

import com.example.ShowMakerCode.Entity.Account;
import com.example.ShowMakerCode.Entity.AccountRole;
import com.example.ShowMakerCode.Service.EntiryService.AccountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
@Service
public class SessionLogin {
    @Autowired
    AccountRoleService accountRoleService;
    @Autowired
    SessionService sessionService;
    @Autowired
    HttpServletRequest request;

    public void userIsLogin(Account account){
        sessionService.set(SessionAttr.CURRENT_USER,account);
        AccountRole accountRole= accountRoleService.findByAccount(account);
        sessionService.set(SessionAttr.ROLE_ACCOUNT,accountRole.getRole().getName());
    }
    public void logout(){
        sessionService.remove(SessionAttr.CURRENT_USER);
        sessionService.remove(SessionAttr.ROLE_ACCOUNT);
    }
    public void initaccount(Model model){
        Account account = (Account) request.getSession().getAttribute(SessionAttr.CURRENT_USER);
        String accountRole = (String) request.getSession().getAttribute(SessionAttr.ROLE_ACCOUNT);
        model.addAttribute("UserCurrent",account);
        model.addAttribute("UserRole",accountRole);
    }
}
