package org.grootsec.scubbs.service;

import org.grootsec.scubbs.entity.CookieInfo;

import javax.ws.rs.core.NewCookie;

public interface CookieService {

    CookieInfo getCookie(String cookie);

    Boolean saveCookie(String studentnumber, String cookie);

    Boolean deleteCookie(CookieInfo cookieInfo);

    NewCookie generateCookie(String studentnumber);

}
