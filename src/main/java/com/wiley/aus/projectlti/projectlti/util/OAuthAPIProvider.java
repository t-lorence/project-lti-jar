package com.wiley.aus.projectlti.projectlti.util;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class OAuthAPIProvider extends DefaultApi10a{
    @Override
    public String getRequestTokenEndpoint() {
        return null;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return null;
    }

    @Override
    public String getAuthorizationUrl(Token token) {
        return null;
    }
}
