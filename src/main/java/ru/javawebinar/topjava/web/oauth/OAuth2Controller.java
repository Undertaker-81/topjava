package ru.javawebinar.topjava.web.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Dmitriy Panfilov
 * 26.12.2020
 */
@Controller
@RequestMapping("/oauth2/mailru")
public class OAuth2Controller {

    private String code;

    @Autowired
    private UserDetailsService service;

    @Autowired
    private OAuth2Source source;

    @GetMapping("/authorize")
    public String authorize() {
        return "redirect:" + source.getAuthorizeUrl()
                + "?client_id=" + source.getClientId()
                + "&response_type=code"
               + "&redirect_uri=" + source.getRedirectUri();

    }

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, RedirectAttributes attr, HttpServletRequest request) {



        return new ModelAndView("login");
    }




}
