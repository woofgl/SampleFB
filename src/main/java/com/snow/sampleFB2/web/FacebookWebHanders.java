package com.snow.sampleFB2.web;

import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.britesnow.snow.web.param.annotation.WebUser;
import com.google.inject.Singleton;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.User;
import com.snow.sampleFB2.utils.JSONOptions;
import org.scribe.model.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class FacebookWebHanders {

    @WebModelHandler(startsWith = "/getFriends")
    public void getFriends(@WebModel Map m, @WebUser Token token, RequestContext rc) throws IOException {
        FacebookClient client = new DefaultFacebookClient(token.getToken());
        //client.fetchConnection("me/")

    }
    @WebModelHandler(startsWith = "/connection")
    public void connection(@WebModel Map m, @WebUser Token token, @WebParam("objType") String objType, @WebParam("opts") String jsonOpts, RequestContext rc) {
        if (objType == null) {
           return;
        }
        ConnectionType type = ConnectionType.valueOf(objType);
        if (type == null) {
            return;
        }
        FacebookClient client = new DefaultFacebookClient(token.getToken());
        JSONOptions opts = new JSONOptions(jsonOpts);
        Connection objs = client.fetchConnection(type.getUrl(), type.getClazz(), Parameter.with("offset", (opts.getPageIndex()) * opts.getPageSize()),
                Parameter.with("limit", opts.getPageSize()));
        List<Object> list = new ArrayList<Object>();
        for (Object object : objs.getData()) {
                list.add(object);
        }

        m.put("result", list);

    }
    @WebModelHandler(startsWith = "/search")
    public void search(@WebModel Map m, @WebUser Token token, @WebParam("objType") String objType,@WebParam("q") String q,RequestContext rc) {
        if (objType == null) {
           return;
        }
        ConnectionType type = ConnectionType.valueOf(objType);

        FacebookClient client = new DefaultFacebookClient(token.getToken());
        Connection<JsonObject> objs = client.fetchConnection(type.getUrl(), JsonObject.class, Parameter.with("q", q),
                Parameter.with("type", objType));
        List<JsonObject> list = new ArrayList<JsonObject>();
        for (List<JsonObject> objects : objs) {
            for (JsonObject object : objects) {
                list.add(object);
            }
        }

        m.put("result", list);

    }


}
