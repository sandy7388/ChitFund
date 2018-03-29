package chitfund.wayzontech.chitfund.chitfund.httpHelper;

import com.android.volley.toolbox.StringRequest;

/**
 * Created by sandy on 19/3/18.
 */

public class URLs
{
    public static final String BASE_URL = "http://wayzontech.in/chitfund/index.php/webservices/";

    public static final String GUARANTOR_URL = BASE_URL + "guarantierlist";

    public static final String REGISTRATION_URL = BASE_URL + "registerweb";

    public static final String LOGIN_URL = BASE_URL + "loginweb";

    public static final String GROUP_URL = REGISTRATION_URL + "/getgrouplist";

    public static final String CHANGE_PASS_URL = LOGIN_URL + "/changepassword";

    public static final String GROUP_DETAILS = REGISTRATION_URL + "/getgrouplist";

    public static final String LAST_AUCTION = BASE_URL + "realtimeauction/getfinalauctions" ;

    public static final String JOIN_GROUP = BASE_URL + "groupinfo/joingroup";

    public static final String JOINED_GROUP = BASE_URL + "groupinfo/userwisegroupList";

    public static final String COLLECTION_REPORT = BASE_URL + " ";
}
