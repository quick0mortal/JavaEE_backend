package org.grootsec.scubbs.service.impl;

import jdk.management.resource.internal.inst.SocketRMHooks;
import org.grootsec.scubbs.controller.MarkController;
import org.grootsec.scubbs.entity.*;
import org.grootsec.scubbs.mapper.*;
import org.grootsec.scubbs.service.GetDataService;
import org.grootsec.scubbs.service.LoginService;
import org.omg.CORBA.StructMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.image.ShortInterleavedRaster;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GetDataServiceImpl implements GetDataService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private TeamAndStudentMapper teamAndStudentMapper;

    @Autowired
    private ShortMessageMapper shortMessageMapper;

    @Autowired
    private AtInfoMapper atInfoMapper;

    @Autowired
    private CriticalInfoMapper criticalInfoMapper;

    @Autowired
    private MarkInfoMapper markInfoMapper;

    @Autowired
    private TeamMemberInfoMapper teamMemberInfoMapper;

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private ResumeMapper resumeMapper;


    @Override
    public MyObject getInfo(String id) {
        MyObject myObject;
        myObject = objectMapper.getObjectById(id);
        if (myObject == null) {
            myObject = new MyObject();
            myObject.setDescription("");
            myObject.setIcon("");
            myObject.setName("");
            myObject.setRole("");
        }
        return myObject;
    }

    @Override
    public Map<String, String> getStudentInfo(String studentnumber) {
        Map<String, String> map = new HashMap<>();

        StudentInfo  studentInfo = loginService.getStudentInfo(studentnumber);
        MyObject myObject = this.getInfo(studentnumber);
        map.put("code", "0");
        map.put("no", studentnumber);
        map.put("name", studentInfo.getStudentName());
        map.put("nickname", myObject.getName());
        map.put("avatar", myObject.getIcon());
        map.put("introduction", myObject.getDescription());
        map.put("academy", studentInfo.getStudentAcademy());
        map.put("class", studentInfo.getStudentClass());
        map.put("grade", studentInfo.getStudentGrade());
        map.put("sex", studentInfo.getStudentSex());
        map.put("profession", studentInfo.getStudentProfession());


        return map;
    }

    @Override
    public StudentInfo getStudentInfoById(String studentnumber) {
        return studentInfoMapper.getStudentInfoByNumber(studentnumber);
    }

    @Override
    public TeamMemberInfo getAdmin(String studentnumber, String teamnumber) {
        return teamMemberInfoMapper.getTeamMemberInfo(studentnumber, teamnumber);
    }

    @Override
    public Map<String, String> getObjectInfoByName(String name) {
        Map<String, String> map = new HashMap<>();
        MyObject myObject = objectMapper.getObjectByName(name);
        if (myObject == null) {
            map.put("code", "1");
            map.put("errmsg", "Wrong team id.");
        }
        map.put("code", "0");
        map.put("teamId", myObject.getId());
        map.put("icon", myObject.getIcon());
        map.put("description", myObject.getDescription());
        map.put("role", myObject.getRole());
        map.put("name", myObject.getName());

        return map;
    }

    @Override
    public MyObject getObjectByName(String name){
        return objectMapper.getObjectByName(name);
    }

    @Override
    public MyObject getObjectById(String id) {
        return objectMapper.getObjectById(id);
    }

    @Override
    public ShortMessageInfo getShortMessage(int id) {
        return shortMessageMapper.getShortMessageInfo(id);
    }

    @Override
    public List<ShortMessageInfo> getAllShortMessages(String type) {
        return shortMessageMapper.getAllShortMessageInfo(type);
    }

    @Override
    public AtInfo getAtInfo(int id) {
        return atInfoMapper.getAtInfo(id);
    }


    @Override
    public CriticalInfo getCritical(int criticalid) {
        return criticalInfoMapper.getCriticalInfo(criticalid);
    }


//    @Override
//    public List<CriticalInfo> getCriticals(int messageid) {
//        List<CriticalInfo> list = criticalInfoMapper.getCriticalInfos(messageid);
//        return list;
//    }

    // 加入判断匿名帖子匿名评论
    @Override
    public List<CriticalInfo> getCriticals(int messageid) {
        ShortMessageInfo shortMessageInfo = shortMessageMapper.getShortMessageInfo(messageid);
        List<CriticalInfo> list = criticalInfoMapper.getCriticalInfos(messageid);
        if (shortMessageInfo.getIsanonymous().equals("1")) {
            for (CriticalInfo criticalInfo : list) {
                criticalInfo.setSendid("");
            }
        }
        return list;
    }

    @Override
    public ResumeInfo getResume(String studentnumber) {
        ResumeInfo resumeInfo = resumeMapper.getResumeInfo(studentnumber);
        return resumeInfo;
    }

    @Override
    public MarkInfo getMark(MarkInfo markInfo) {
        return markInfoMapper.getMarkInfo(markInfo);
    }

    @Override
    public List<MarkInfo> getMarks(MarkInfo markInfo) {
        return markInfoMapper.getMarkInfos(markInfo);
    }

    @Override
    public List<MyObject> getAllObject(String role) {
        return objectMapper.getAllObject(role);
    }

    @Override
    public List<MyObject> getTeamInfoBySId(String studentnumber) {
        return objectMapper.getTeamObjectById(studentnumber);
    }

    @Override
    public MyObject getTeamInfoByTId(String id) {
        return objectMapper.getObjectById(id);
    }

    @Override
    public TeamMemberInfo getTeamMember(String teamid, String targetid) {
        return teamMemberInfoMapper.getTeamMemberInfo(teamid, targetid);
    }

    @Override
    public List getAllNameAndNumber() {
        List<StudentInfo> list = studentInfoMapper.getAllStudentInfo();
        List<Map> listresult = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
           Map<String, String> innerMap = new HashMap<>();
           innerMap.put("value", list.get(i).getStudentNumber());
           innerMap.put("label", list.get(i).getStudentName());
           listresult.add(innerMap);
        }
        return listresult;
    }
//
//    @Override
//    public Map getNews(String id) {
//        MyObject myObjectStudent = objectMapper.getObjectById(id);
//        StudentInfo studentInfo = studentInfoMapper.getStudentInfoByNumber(id);
//        Map map = new HashMap();
//        map.put("name", studentInfo.getStudentName());
//        map.put("sex", studentInfo.getStudentSex());
//        map.put("class", studentInfo.getStudentClass());
//        map.put("grade", studentInfo.getStudentGrade());
//        map.put("academy", studentInfo.getStudentAcademy());
//        map.put("profession", studentInfo.getStudentProfession());
//        if (myObjectStudent == null){
//            map.put("nickname", "");
//            map.put("avatar", "");
//            map.put("introduction", "");
//        } else {
//            map.put("nickname", myObjectStudent.getName());
//            map.put("avatar", myObjectStudent.getIcon());
//            map.put("introduction", myObjectStudent.getDescription());
//        }
//        List<MarkInfo> listMark;
//        List<ShortMessageInfo> listMessage;
//        listMark = markInfoMapper.getMarkInfosByMarker(id);
//        listMessage = shortMessageMapper.getShortMessageInfos(id, "0");
//        map.put("mark", listMark);
//        map.put("message", listMessage);
//
//        return map;
//    }



    @Override
    public Map getNews(String id) {
        MyObject myObjectStudent = objectMapper.getObjectById(id);
        StudentInfo studentInfo = studentInfoMapper.getStudentInfoByNumber(id);
        List<MarkResponseInfo> list = new LinkedList<>();
        Map map = new HashMap();
        map.put("name", studentInfo.getStudentName());
        map.put("sex", studentInfo.getStudentSex());
        map.put("class", studentInfo.getStudentClass());
        map.put("grade", studentInfo.getStudentGrade());
        map.put("academy", studentInfo.getStudentAcademy());
        map.put("profession", studentInfo.getStudentProfession());
        if (myObjectStudent == null){
            map.put("nickname", "");
            map.put("avatar", "");
            map.put("introduction", "");
        } else {
            map.put("nickname", myObjectStudent.getName());
            map.put("avatar", myObjectStudent.getIcon());
            map.put("introduction", myObjectStudent.getDescription());
        }
        List<ShortMessageInfo> shortMessageInfoList = shortMessageMapper.getShortMessageInfos(id, "0");
        List<CriticalInfo> criticalInfoList = criticalInfoMapper.getNACriticalInfos(id);
        List<MarkInfo> markInfoList = markInfoMapper.getMarkInfosByMarker(id);

        for (ShortMessageInfo shortMessageInfo : shortMessageInfoList) {
            MarkResponseInfo markResponseInfo = new MarkResponseInfo();
            markResponseInfo.setId(""+shortMessageInfo.getMessageid());
            markResponseInfo.setTime(shortMessageInfo.getTime());
            markResponseInfo.setType("shortMessage");
            list.add(markResponseInfo);
        }
        for (CriticalInfo criticalInfo : criticalInfoList) {
            MarkResponseInfo markResponseInfo = new MarkResponseInfo();
            markResponseInfo.setId(""+criticalInfo.getCriticalid());
            markResponseInfo.setType("critical");
            markResponseInfo.setType(criticalInfo.getTime());
            list.add(markResponseInfo);
        }
        for (MarkInfo markInfo : markInfoList) {
            MarkResponseInfo markResponseInfo = getMarkResponse(markInfo);
            if (markResponseInfo != null) {
                list.add(markResponseInfo);
            }
        }

        map.put("array", list);


        return map;
    }

    @Override
    public List<MarkInfo> getMarkCount(MarkInfo markInfo) {
        List<MarkInfo> list = markInfoMapper.getMarkCount(markInfo);
        return list;
    }

    @Override
    public List<ShortMessageInfo> getRecentMessage(String type) {
        List<ShortMessageInfo> list = shortMessageMapper.getAllShortMessageInfo(type);
        List<ShortMessageInfo> result = new LinkedList<>();
        int index = 10;
        if (list.size() < 10) {
            index = list.size();
        }
        for (int i = 0; i < index; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    @Override
    public List<ShortMessageInfo> getShortMessagesById(int id, String type) {
        ShortMessageInfo shortMessageInfo = shortMessageMapper.getShortMessageInfo(id);
        System.out.println(id);
        System.out.println(shortMessageInfo.toString());
        List<ShortMessageInfo> list = shortMessageMapper.getAllShortMessageInfo(type);
        System.out.println(list);
        System.out.println("list.indexOf(shortMessageInfo) " + list.indexOf(shortMessageInfo));
        int index = -1;
        for (ShortMessageInfo shortMessageInfo1 : list) {
            if (id == shortMessageInfo.getMessageid()) {
                index = list.indexOf(shortMessageInfo1) + 1;
            }
        }
        int maxIndex = index + 10;
        int maxSize = list.size();
        if (index + 10 > maxSize) {
            maxIndex = maxSize;
        }
        List<ShortMessageInfo> result = new LinkedList<>();
        System.out.println("maxIndex " + maxIndex);
        System.out.println("maxSize" + maxSize);
        System.out.println("index " + index);
        for (int i = index; i < maxIndex; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    @Override
    public List<AtInfo> getAtInfoByTarget(String targetid, String hasreaded) {
        return atInfoMapper.getAtInfoByTarget(targetid, hasreaded);
    }

    @Override
    public List<TeamMemberInfo> getTeamMembers(String teamnumber) {
        return teamMemberInfoMapper.getAllTeamMemberInfoByTId(teamnumber);
    }

    @Override
    public List<ShortMessageInfo> getHotMessage() {
        List<MarkCount> listMarkAll = markInfoMapper.getGoodOrder();
        int size = listMarkAll.size();
        int range = 10;
        if (size < 10) {
            range = size;
        }
        List<MarkCount> listMark = listMarkAll.subList(0, range);
        List<ShortMessageInfo> list = new LinkedList<>();
        for (MarkCount markCount : listMark) {
            ShortMessageInfo shortMessageInfo = shortMessageMapper.getShortMessageInfo(markCount.getMessageid());
            list.add(shortMessageInfo);
        }
        return list;
    }

    @Override
    public List<CriticalInfo> getRecentCritial() {
        List<CriticalInfo> resultList = new LinkedList<>();
        List<CriticalInfo> list = criticalInfoMapper.getAllCriticalInfo();
        int index = 10;
        if (index > list.size()) {
            index = list.size();
        }
        for (int i = 0; i < index; i++) {
            resultList.add(list.get(i));
        }
        return resultList;
    }

    @Override
    public List<ShortMessageInfo> getAllShortMessagesBySendid(String sendid) {
        return shortMessageMapper.getAllShortMessagesBySendid(sendid);
    }

    @Override
    public MarkResponseInfo getMarkResponse(MarkInfo markInfo) {
        MarkResponseInfo markResponseInfo = new MarkResponseInfo();
        if (markInfo.getMessageid().startsWith("T")) {
            ShortMessageInfo shortMessageInfo = shortMessageMapper.getShortMessageInfo(markInfo.getIntMessageid());
            if (shortMessageInfo.getIsanonymous().equals("0")) {
                markResponseInfo.setId(shortMessageInfo.getMessageid()+"");
                markResponseInfo.setType(markInfo.getType());
                markResponseInfo.setTime(shortMessageInfo.getTime());
            } else {
                return null;
            }
        } else if (markInfo.getMessageid().startsWith("C")) {
            CriticalInfo criticalInfo = criticalInfoMapper.getCriticalInfo(markInfo.getIntMessageid());
            ShortMessageInfo shortMessageInfo = shortMessageMapper.getShortMessageInfo(criticalInfo.getMessageid());
            if (shortMessageInfo.getIsanonymous().equals("0")) {
                markResponseInfo.setId(criticalInfo.getCriticalid()+"");
                markResponseInfo.setType(markInfo.getType());
                markResponseInfo.setTime(criticalInfo.getTime());
            } else {
                return null;
            }
        } else {
            try {
                StudentInfo studentInfo = studentInfoMapper.getStudentInfoByNumber(markInfo.getMessageid());
                markResponseInfo.setId(studentInfo.getStudentNumber());
                markResponseInfo.setType(markInfo.getType());
                markResponseInfo.setTime("");
            } catch (Exception e) {
                return null;
            }
        }
        return markResponseInfo;
    }


}
