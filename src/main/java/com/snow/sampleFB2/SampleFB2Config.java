package com.snow.sampleFB2;


import com.britesnow.snow.web.auth.AuthRequest;
import com.google.inject.AbstractModule;
import com.snow.sampleFB2.web.FacebookAuthRequest;

public class SampleFB2Config extends AbstractModule {
    @Override
    protected void configure() {
        bind(AuthRequest.class).to(FacebookAuthRequest.class);
    }
}
