package com.wiley.aus.projectlti.projectlti.service;

import com.wiley.aus.projectlti.projectlti.model.DeepLink;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LtiService {
    
    public boolean checkInbound(String studyOnFlag, Map<String, String[]> studyOnReceiveBeanParameters, String portStripped);
    
    public DeepLink checkOutbound(String link, String[] userName, String role, long userId, String userEmail, String projectKey, HttpServletRequest httpServletRequest);
}
