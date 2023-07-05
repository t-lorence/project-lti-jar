package com.wiley.aus.projectlti.projectlti.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeepLink {

    String lti_version = "LTI-1p0";
    String lti_message_type = "basic lti launch request";

    // OAuth Parameters
    String oauth_version = "1.0";
    String oauth_consumer_key;
    String oauth_nonce;
    String oauth_signature;
    String oauth_signature_method = "HMAC-SHA1";
    String oauth_timestamp;
    String tool_consumer_instance_guid = "jacplus.com.au";

    // User Parameters
    String user_id;
    String roles;
    String lis_person_name_given;
    String lis_person_name_family;
    String lis_person_contact_email_primary;

    // Context Parameters
    String context_id;

    // Custom Parameters
    String custom_destination;

    // JacPLUS EndPoint
    String launchURL = "https://www.jacplus.com.au/servlet/lti-receive";

    public DeepLink() {
        //Use empty constructor
    }
}


