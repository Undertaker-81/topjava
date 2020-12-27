package ru.javawebinar.topjava.web.oauth;

/**
 * @author Dmitriy Panfilov
 * 26.12.2020
 */
public class OAuth2Source {
    private String authorizeUrl;
    private String accessTokenUrl;
    private String clientId;
    private String privateKey;
    private String clientSecret;
    private String redirectUri;
    private String code;
    private String UrlRestApiMailRu;


    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getUrlRestApiMailRu() {
        return UrlRestApiMailRu;
    }

    public void setUrlRestApiMailRu(String urlRestApiMailRu) {
        UrlRestApiMailRu = urlRestApiMailRu;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
