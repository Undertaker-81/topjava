package ru.javawebinar.topjava.web.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
import ru.javawebinar.topjava.to.MailRuTo;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Dmitriy Panfilov
 * 26.12.2020
 */
@Controller
@RequestMapping("/oauth2/mailru")
public class OAuth2Controller {
    private final ObjectMapper objectMapper = new ObjectMapper();

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
    public String authenticate(@RequestParam String code, RedirectAttributes attr, HttpServletRequest request) throws JsonProcessingException, NoSuchAlgorithmException {

        MailRuTo mailRuTo = getMailRuUser(getAccessToken(code)) ;

        return "meals";
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        //https://api.mail.ru/docs/guides/oauth/sites/
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(source.getAccessTokenUrl())
                .queryParam("client_id", source.getClientId())
                .queryParam("client_secret", source.getClientSecret())
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", code)
                .queryParam("redirect_uri", source.getRedirectUri());
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(headers);

      //  ResponseEntity<JsonNode> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.POST, stringHttpEntity, JsonNode.class);
        ResponseEntity<String> response = restTemplate.postForEntity(builder.build().toUri(), stringHttpEntity, String.class);
        String jsonString = response.getBody();
        JsonNode root = objectMapper.readTree(jsonString);

        return root.findValue("access_token").asText();
    }
    private MailRuTo getMailRuUser(String accessToken) throws NoSuchAlgorithmException, JsonProcessingException {
        //https://api.mail.ru/docs/guides/restapi/
        RestTemplate restTemplate = new RestTemplate();
       // https://stackoverflow.com/questions/415953/how-can-i-generate-an-md5-hash

        String params =  "app_id=" + source.getClientId() + "method=users.getInfo"
                          + "secure=1"
                         + "session_key=" + accessToken + source.getClientSecret();

        String hash = DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(params.getBytes(StandardCharsets.UTF_8)));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(source.getUrlRestApiMailRu())
                .queryParam("method", "users.getInfo")
                .queryParam("app_id", source.getClientId())
                .queryParam("secure", "1")
                .queryParam("session_key", accessToken)
                .queryParam("sig", hash.toLowerCase());//LOW CASE!!!!!

        String st = builder.build().encode().toUri().toString();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(builder.build().encode().toUri(), String.class);
        String jsonString = responseEntity.getBody();
        JsonNode root = objectMapper.readTree(jsonString);

        String login = root.findValue("nick").asText();
        String name = root.findValue("first_name").asText();
        if (name.equals("null")) {
            name = login;
        }
        String email = root.findValue("email").asText();
        if (email.equals("null")) {
            throw new NotFoundException("No email found in Mail.ru account");
        }
        return new MailRuTo(login, name, email);
    }

}




