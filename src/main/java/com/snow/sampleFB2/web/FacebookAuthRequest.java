package com.snow.sampleFB2.web;

import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.auth.AuthRequest;
import com.britesnow.snow.web.auth.AuthToken;
import com.britesnow.snow.web.binding.ApplicationProperties;
import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.britesnow.snow.web.param.annotation.WebUser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static org.scribe.model.OAuthConstants.*;


@Singleton
public class FacebookAuthRequest implements AuthRequest {
    public static final String COOKIE_TOKEN = "facebook_accessToken";
    private static Logger log = LoggerFactory.getLogger(FacebookAuthRequest.class);
    private final OAuthService service;
    private final String clientId;
    private final String secret;
    private String callback;

    @Inject
    public FacebookAuthRequest(@ApplicationProperties Map appConfig) {
        this.clientId = (String) appConfig.get("facebook.client_id");
        this.secret = (String) appConfig.get("facebook.secret");
        this.callback = (String) appConfig.get("facebook.callback");
        service = new ServiceBuilder()
                .provider(FacebookApi.class)
                .apiKey(clientId)
                .apiSecret(secret)
                .callback(callback)
                .build();
    }


    @Override
    public AuthToken authRequest(RequestContext rc) {
        // Note: this is not the login logic, the login logic would be
        // @WebActionHandler that would generate the appropriate

        // Note: this is a simple stateless authentication scheme.
        // Security is medium-low, however, with little bit more logic
        // it can be as secure as statefull login while keeping it's scalability attributes

        // First, we get userId and userToken from cookie
///*        String userIdStr = rc.getCookie("userId");
        String accessToken = rc.getCookie(COOKIE_TOKEN);

        if (accessToken != null) {
            // if valid, then, we create the AuthTocken with our User object
            AuthToken<Token> authToken = new AuthToken<Token>();
            authToken.setUser(new Token(accessToken, ""));
            return authToken;

        } else {
            // otherwise, we could throw an exception, or just return null
            // In this example (and snowStarter, we just return null)
            return null;
        }
    }

    @WebModelHandler(startsWith = "/")
    public void pageIndex(@WebModel Map m,@WebUser Token token, RequestContext rc) {
        if (token != null) {
            m.put("accessToken", token.getToken());
        }

    }
    @WebModelHandler(startsWith = "/login")
    public void login(RequestContext rc) throws IOException {
        Token accessToken = rc.getUser(Token.class);
        if (accessToken == null) {
            rc.getRes().sendRedirect(service.getAuthorizationUrl(EMPTY_TOKEN));
        }

    }

    @WebModelHandler(startsWith = "/callback")
    public void callback(@WebParam("code") String code, RequestContext rc) throws IOException {
        if (code != null) {
            Verifier verifier = new Verifier(code);
            Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
            rc.setCookie(COOKIE_TOKEN, accessToken.getToken());

        }
        rc.getRes().sendRedirect(rc.getReq().getContextPath());
    }
}