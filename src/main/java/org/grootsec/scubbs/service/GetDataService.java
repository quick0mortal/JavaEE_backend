package org.grootsec.scubbs.service;

import org.grootsec.scubbs.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface GetDataService {

    MyObject getInfo(String id);

    Map<String, String> getStudentInfo(String studentnumber);

    StudentInfo getStudentInfoById(String studentnumber);

    TeamMemberInfo getAdmin(String studentnumber, String teamnumber);

    Map<String, String> getObjectInfoByName(String name);

    MyObject getObjectByName(String name);

    MyObject getObjectById(String id);

    ShortMessageInfo getShortMessage(int id);

    List<ShortMessageInfo> getAllShortMessages(String type);

    AtInfo getAtInfo(int id);

//    List<AtInfo> getBeAted(String targetid);

    CriticalInfo getCritical(int criticalid);

    List<CriticalInfo> getCriticals(int messageid);

    ResumeInfo getResume(String studentnumber);

    MarkInfo getMark(MarkInfo markInfo);

    List<MarkInfo> getMarks(MarkInfo markInfo);

    List<MyObject> getAllObject(String role);

    List<MyObject> getTeamInfoBySId(String studentnumber);

    MyObject getTeamInfoByTId(String id);

    TeamMemberInfo getTeamMember(String teamid, String targetid);

    List getAllNameAndNumber();

    Map getNews(String id);

    List<MarkInfo> getMarkCount(MarkInfo markInfo);

    List<ShortMessageInfo> getRecentMessage(String type);

    List<ShortMessageInfo> getShortMessagesById(int id, String type);

    List<AtInfo> getAtInfoByTarget(String targetid, String hasreaded);

    List<TeamMemberInfo> getTeamMembers(String teamnumber);

    List<ShortMessageInfo> getHotMessage();

    List<CriticalInfo> getRecentCritial();

    List<ShortMessageInfo> getAllShortMessagesBySendid(String sendid);

    MarkResponseInfo getMarkResponse(MarkInfo markInfo);
}
