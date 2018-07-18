package chitfund.wayzontech.chitfund.chitfund.httpHelper;

import chitfund.wayzontech.chitfund.chitfund.session.AgentSession;

public class AgentURL {

    public static final String AGENT_BASE_URL = "http://" + AgentSession.getSubdomain() + ".onlinechitfund.com/index.php/webservices/";

    public static final String AGENT_GROUP_URL = AGENT_BASE_URL + "Registerweb/group_name_info";

    public static final String AGENT_MEMBER_URL = AGENT_BASE_URL + "Registerweb/group_member_info";

    public static final String AGENT_BANK_DETAILS_URL = AGENT_BASE_URL + "Registerweb/get_bank_details";

    public static final String AGENT_MEMBER_COLLECTION_URL = AGENT_BASE_URL + "Registerweb/group_member_info";
}
