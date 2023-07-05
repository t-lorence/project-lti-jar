package com.wiley.aus.projectlti.projectlti.util;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;

import java.net.URLDecoder;
import java.util.Map;

public class GenerateSignature {
    private static final String CONTEXT_TITLE = "context_title";

    private GenerateSignature() {
    }

    public static String generateSignatureBaseString(Map<String, String[]> receiveBean, String consumerSecret, String requestURL) {
        DefaultApi10a api = new OAuthAPIProvider();

        String oauthConsumeSecretKey = consumerSecret;

        // Creates Request Object with HTTP Method & End Point Url
        OAuthRequest oauthRequest = new OAuthRequest(Verb.POST, requestURL);

        // Adds parameters to the request body
        oauthRequest.addBodyParameter("lti_version", receiveBean.get("lti_version")[0]);
        oauthRequest.addBodyParameter("lti_message_type", receiveBean.get("lti_message_type")[0]);
        oauthRequest.addBodyParameter("resource_link_id", receiveBean.get("resource_link_id")[0]);
        oauthRequest.addBodyParameter("user_id", String.valueOf(receiveBean.get("user_id")[0]));
        oauthRequest.addBodyParameter("roles", receiveBean.get("roles")[0]);
        oauthRequest.addBodyParameter("lis_person_name_family",
                URLDecoder.decode(receiveBean.get("lis_person_name_family")[0]));
        oauthRequest.addBodyParameter("lis_person_name_given",
                URLDecoder.decode(receiveBean.get("lis_person_name_given")[0]));
        oauthRequest.addBodyParameter("lis_person_contact_email_primary",
                receiveBean.get("lis_person_contact_email_primary")[0]);
        oauthRequest.addBodyParameter("context_id", receiveBean.get("context_id")[0]);
        oauthRequest.addBodyParameter(CONTEXT_TITLE, receiveBean.get("context_title")[0]);
        oauthRequest.addBodyParameter("tool_consumer_instance_guid", receiveBean.get("tool_consumer_instance_guid")[0]);
        oauthRequest.addBodyParameter("tool_consumer_instance_name", receiveBean.get("tool_consumer_instance_name")[0]);
        oauthRequest.addBodyParameter("launch_presentation_document_target",
                receiveBean.get("launch_presentation_document_target")[0]);
        oauthRequest.addBodyParameter("launch_presentation_return_url",
                receiveBean.get("launch_presentation_return_url")[0]);
        oauthRequest.addBodyParameter("custom_jacplus_url",
                receiveBean.get("custom_jacplus_url")[0]);
        oauthRequest.addOAuthParameter("oauth_consumer_key", receiveBean.get("oauth_consumer_key")[0]);
        oauthRequest.addOAuthParameter("oauth_nonce", receiveBean.get("oauth_nonce")[0]);
        oauthRequest.addOAuthParameter("oauth_signature_method", receiveBean.get("oauth_signature_method")[0]);
        oauthRequest.addOAuthParameter("oauth_timestamp", receiveBean.get("oauth_timestamp")[0]);
        oauthRequest.addOAuthParameter("oauth_version", receiveBean.get("oauth_version")[0]);

        String baseString = api.getBaseStringExtractor().extract(oauthRequest);

        // Passing an empty token, as the 2-legged OAuth doesn't require it
        return api.getSignatureService().getSignature(baseString, oauthConsumeSecretKey, "");
    }
}
