package org.grootsec.scubbs.service.impl;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import org.grootsec.scubbs.entity.*;
import org.grootsec.scubbs.mapper.*;
import org.grootsec.scubbs.service.AddDataService;
import org.grootsec.scubbs.util.DateUtils;
import org.grootsec.scubbs.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class AddDataServiceImpl implements AddDataService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeamMemberInfoMapper teamMemberInfoMapper;

    @Autowired
    private ShortMessageMapper shortMessageMapper;

    @Autowired
    private AtInfoMapper atInfoMapper;

    @Autowired
    private CriticalInfoMapper criticalInfoMapper;

    @Autowired
    private MarkInfoMapper markInfoMapper;

    @Autowired
    private ResumeMapper resumeMapper;

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Override
    public Boolean addTeamObject(MyObject myObject) {
        myObject.setIcon("");
        myObject.setRole("team");
        String teamId = StringUtils.getTeamId();
        if (objectMapper.getObjectById(teamId) != null) {
            teamId = StringUtils.getTeamId();
        }
        myObject.setId(teamId);
        try {
            objectMapper.insert(myObject);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean addStudentObject(MyObject myObject, CookieInfo cookieInfo) {
        myObject.setIcon("");
        myObject.setId(cookieInfo.getStudentnumber());
        myObject.setRole("student");
        try {
            objectMapper.insert(myObject);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public Boolean addTeamMember(CookieInfo cookieInfo, MyObject myObject, String isadmin) {
        String teamid = myObject.getId();
        TeamMemberInfo teamMemberInfo = new TeamMemberInfo();
        teamMemberInfo.setIsadmin(isadmin);
        teamMemberInfo.setStudentnumber(cookieInfo.getStudentnumber());
        teamMemberInfo.setJoindate(DateUtils.getDate());
        teamMemberInfo.setTeamnumber(teamid);
        try {
            System.out.println("mapper insert before");
            teamMemberInfoMapper.insert(teamMemberInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean addMessage(String studentnumber, String content, String isanonymous, String location, String type) {
        MyObject myObject = objectMapper.getObjectById(studentnumber);
        ShortMessageInfo shortMessageInfo = new ShortMessageInfo();
        shortMessageInfo.setContent(content);
        shortMessageInfo.setIsanonymous(isanonymous);
        shortMessageInfo.setLocaltion(location);
        shortMessageInfo.setType(type);
        shortMessageInfo.setTime(DateUtils.getDate());
        shortMessageInfo.setSendid(studentnumber);
        try {
            System.out.println("开始插入");
            shortMessageMapper.insert(shortMessageInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean addAt(String sendid, String targetid) {
        AtInfo atInfo = new AtInfo();
        atInfo.setHasreaded("0");
        atInfo.setSendid(sendid);
        atInfo.setTargetid(targetid);
        atInfo.setTime(DateUtils.getDate());
        try {
            atInfoMapper.insert(atInfo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean addCritical(String sendid, int messageid, String content) {
        CriticalInfo criticalInfo = new CriticalInfo();
        criticalInfo.setContent(content);
        criticalInfo.setMessageid(messageid);
        criticalInfo.setTime(DateUtils.getDate());
        criticalInfo.setSendid(sendid);
        try {
            criticalInfoMapper.insert(criticalInfo);
            return true;
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
            return false;
        }
    }

//    @Override
//    public Boolean addMark(String markerid, int messageid, String type) {
//        MarkInfo markInfo = new MarkInfo();
//        markInfo.setMarkerid(markerid);
//        markInfo.setMessageid(messageid);
//        markInfo.setType(type);
//        try {
//            markInfoMapper.insert(markInfo);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    @Override
    public Boolean addMark(String markerid, int messageid, String type, String start) {
        MarkInfo markInfo = new MarkInfo();
        markInfo.setMarkerid(markerid);

//        markInfo.setMessageid(messageid);
        String messageId = start + messageid;
        markInfo.setMessageid(messageId);
        markInfo.setType(type);
        try {
            markInfoMapper.insert(markInfo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public Boolean addAtInfo(String sendid, String content) {
        Boolean flag = true;
        Pattern pattern = Pattern.compile("@T?\\d+ ");
        Matcher matcher= pattern.matcher(content);
        AtInfo atInfo = new AtInfo();
        atInfo.setTime(DateUtils.getDate());
        atInfo.setSendid(sendid);
        atInfo.setHasreaded("0");
        while(matcher.find()) {
            String s = matcher.group();
            System.out.println(matcher.group());
            String sub = s.substring(1, s.length()-1);
            atInfo.setTargetid(sub);
            MyObject myObject = objectMapper.getObjectById(sub);
            try {
                atInfoMapper.insert(atInfo);
            } catch (Exception e) {
                flag = false;
                System.out.println(matcher.group());
                e.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public Boolean addObject(String id, String name, String description, String icon, String role) {
        MyObject myObject = new MyObject();
        myObject.setId(id);
        myObject.setRole(role);
        myObject.setIcon(icon);
        myObject.setDescription(description);
        myObject.setName(name);
        try {
            System.out.println("开始加入object");
            objectMapper.insert(myObject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean addResume(ResumeInfo resumeInfo) {
        try {
            resumeMapper.insert(resumeInfo);
            return true;
        } catch (Exception e) {
            System.out.println("简历投递失败");
            e.printStackTrace();
            return false;
        }
    }
}
