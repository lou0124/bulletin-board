package personal.bulletinborad.controller.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static personal.bulletinborad.controller.AttributeNameConst.SESSION_MEMBER;

public class LoginMemberGetter {

    public static Object getLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        return session.getAttribute(SESSION_MEMBER);
    }
}
