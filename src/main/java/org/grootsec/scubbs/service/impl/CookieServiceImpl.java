package org.grootsec.scubbs.service.impl;

import org.grootsec.scubbs.entity.CookieInfo;
import org.grootsec.scubbs.mapper.CookieMapper;
import org.grootsec.scubbs.service.CookieService;
import org.grootsec.scubbs.util.DateUtils;
import org.grootsec.scubbs.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.NewCookie;
import java.sql.Date;
import java.sql.Timestamp;

@Service
@Transactional
public class CookieServiceImpl implements CookieService {

    @Autowired
    private CookieMapper cookieMapper;

    @Override
    public CookieInfo getCookie(String cookie) {
        CookieInfo cookieInfo;
        cookieInfo = cookieMapper.getCookie(cookie);
        return cookieInfo;
    }

    @Override
    public Boolean saveCookie(String studentnumber, String cookie) {
        try {
            CookieInfo cookieInfo = new CookieInfo();
            cookieInfo.setStudentnumber(studentnumber);
            cookieInfo.setCookie(cookie);
            String sqlTime = DateUtils.getDate();
            cookieInfo.setTime(sqlTime);
            System.out.print(cookieInfo);
            cookieMapper.insert(cookieInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteCookie(CookieInfo cookieInfo) {
        try {
            cookieMapper.delete(cookieInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public NewCookie generateCookie(String studentnumber) {
        String cookie = StringUtils.getCookie();
//        System.out.println(cookie);
        while (this.getCookie(cookie) != null) {
            cookie = StringUtils.getCookie();
        }
        NewCookie newCookie = new NewCookie("myStrCookie", cookie, "/", "", "", 999999999, false);
        this.saveCookie(studentnumber, cookie);
        return newCookie;
    }


}
