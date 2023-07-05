package com.wiley.aus.projectlti.projectlti.service.impl;

import com.wiley.aus.projectlti.projectlti.model.DeepLink;
import com.wiley.aus.projectlti.projectlti.service.LtiService;
import com.wiley.aus.projectlti.projectlti.util.GenerateSignature;
import com.wiley.aus.projectlti.projectlti.util.OAuthAPIProvider;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

public class LtiServiceImpl implements LtiService {
    boolean ltiValue = false;
    boolean authenticationFlag;
 
    String projectSecret = "TESTKEY";
    
    public boolean checkInbound(String ltiProductFlag, Map<String, String[]> ltiReceiveBeanParameters, String portStripped){
        if (ltiProductFlag != null && ltiProductFlag.equals("1")){
            ltiValue = true;
        }
        if (ltiValue){
            String oauthSignature = "";
            try {
                portStripped = portStripped.replaceFirst(":[1-9]0000", "");
                oauthSignature =
                        GenerateSignature.generateSignatureBaseString(ltiReceiveBeanParameters, "KJSlJD932LKJSJL0", portStripped);
            } catch (Exception ex) {
            }
            if (!oauthSignature.equals(ltiReceiveBeanParameters.get("oauth_signature")[0])) {
                authenticationFlag = false;
            }else authenticationFlag = true;
        }
        return authenticationFlag;
    }

    public DeepLink checkOutbound(String link, String[] userName, String role, long userId, String userEmail, String projectKey, HttpServletRequest httpServletRequest){
        
        DeepLink deepLinkBean = new DeepLink();
        deepLinkBean.setCustom_destination(link);
        deepLinkBean.setLis_person_name_given(userName[0]);
        deepLinkBean.setLis_person_name_family(userName[1]);
        deepLinkBean.setRoles(role);
        deepLinkBean.setUser_id(String.valueOf(userId));
        deepLinkBean.setLis_person_contact_email_primary(userEmail);
        deepLinkBean.setContext_id("");

        String uuidString = UUID.randomUUID().toString();
        uuidString = uuidString.replace("-", "");
        String oauthNonce = uuidString;
        deepLinkBean.setOauth_nonce(oauthNonce);
        deepLinkBean.setOauth_consumer_key(projectKey);
        String oauthTimestamp = Long.toString(System.currentTimeMillis() / 1000);
        deepLinkBean.setOauth_timestamp(oauthTimestamp);

        String oauthSignature = "";

        try {
            oauthSignature = generateSignatureBaseString(deepLinkBean, httpServletRequest);
        } catch (Exception ignored) {
        }
        deepLinkBean.setOauth_signature(oauthSignature);
        
        return deepLinkBean;
    }

    private String generateSignatureBaseString(DeepLink deepLinkBean, HttpServletRequest httpServletRequest) {

        DefaultApi10a api = new OAuthAPIProvider();

        String oauthConsumerSecretKey = projectSecret;
        // Creates Request Object with HTTP Method & End Point Url
        OAuthRequest request = new OAuthRequest(Verb.POST, deepLinkBean.getLaunchURL());

        request.addBodyParameter("lti_version", deepLinkBean.getLti_version());
        request.addBodyParameter("lti_message_type", deepLinkBean.getLti_message_type());
        request.addBodyParameter("user_id", String.valueOf(deepLinkBean.getUser_id()));
        request.addBodyParameter("roles", deepLinkBean.getRoles());
        request.addBodyParameter("lis_person_name_family", deepLinkBean.getLis_person_name_family());
        request.addBodyParameter("lis_person_name_given", deepLinkBean.getLis_person_name_given());
        request.addBodyParameter("lis_person_contact_email_primary",
                deepLinkBean.getLis_person_contact_email_primary());
        request.addBodyParameter("context_id", deepLinkBean.getContext_id());
        request.addBodyParameter("custom_destination", deepLinkBean.getCustom_destination());
        request.addBodyParameter("tool_consumer_instance_guid", deepLinkBean.getTool_consumer_instance_guid());
//        request.addBodyParameter("_csrf", ((CsrfToken)httpServletRequest.getAttribute("_csrf")).getToken());
        request.addOAuthParameter(OAuthConstants.VERSION, deepLinkBean.getOauth_version());
        request.addOAuthParameter(OAuthConstants.CONSUMER_KEY, deepLinkBean.getOauth_consumer_key());
        request.addOAuthParameter(OAuthConstants.NONCE, deepLinkBean.getOauth_nonce());
        request.addOAuthParameter(OAuthConstants.SIGN_METHOD, deepLinkBean.getOauth_signature_method());
        request.addOAuthParameter(OAuthConstants.TIMESTAMP, deepLinkBean.getOauth_timestamp());

        String baseString = api.getBaseStringExtractor().extract(request);

        return api.getSignatureService().getSignature(baseString, oauthConsumerSecretKey, "");
    }

}
