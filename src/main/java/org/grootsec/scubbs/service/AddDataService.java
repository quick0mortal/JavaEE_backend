package org.grootsec.scubbs.service;

import org.grootsec.scubbs.entity.*;
import org.springframework.stereotype.Service;

@Service
public interface AddDataService {

    Boolean addTeamObject(MyObject myObject);

    Boolean addStudentObject(MyObject myObject, CookieInfo cookieInfo);

    Boolean addTeamMember(CookieInfo cookieInfo, MyObject myObject, String isadmin);

    Boolean addMessage(String studentnumber, String content, String isanonymous, String location, String type);

    Boolean addAt(String sendid, String targetid);

    Boolean addCritical(String sendid, int messageid, String content);

//    Boolean addMark(String markerid, int messageid, String type);

    Boolean addMark(String markerid, int messageid, String type, String start);

    Boolean addAtInfo(String sendid, String content);

    Boolean addObject(String id, String name, String description, String icon, String role);

    Boolean addResume(ResumeInfo resumeInfo);

}
