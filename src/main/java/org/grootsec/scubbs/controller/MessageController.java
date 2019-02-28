package org.grootsec.scubbs.controller;

import org.grootsec.scubbs.entity.*;
import org.grootsec.scubbs.service.*;
import org.grootsec.scubbs.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Path("/")
@Component
public class MessageController {


    @Autowired
    private LoginService loginService;

    @Autowired
    private GetDataService getDataService;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private AddDataService addDataService;

    @Autowired
    private DeleteService deleteService;


    @POST
    @Path("/api/v1/sendMessage")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@FormParam("content") String content, @FormParam("isanonymous") String isanonymous,
                                @FormParam("type") String type, @FormParam("location") String location,
                                @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        String processedContent = StringUtils.cleanXSS(content);
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            MyObject myObject = getDataService.getObjectById(cookieInfo.getStudentnumber());
            if (addDataService.addMessage(cookieInfo.getStudentnumber(), processedContent, isanonymous, location, type)) {
                if (addDataService.addAtInfo(cookieInfo.getStudentnumber(), processedContent)){
                    map.put("code", "0");
                } else {
                    map.put("code", "1");
                    map.put("errmsg", "Failed to add AT");
                }
            } else {
                map.put("code", "1");
                map.put("errmsg", "Failed to add message");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @POST
    @Path("/api/v1/deleteMessage")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMessage(@FormParam("id") int id, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            String studentnumber = cookieInfo.getStudentnumber();
            ShortMessageInfo shortMessageInfo = getDataService.getShortMessage(id);
            if (shortMessageInfo.getSendid().equals(studentnumber)) { // jubao
                if (deleteService.deleteMessage(id)){
                    map.put("code", "0");
                } else {
                    map.put("code", "Failed to delete message");
                }
            } else {
                map.put("code", "1");
                map.put("errmsg", "You can't delete message");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @GET
    @Path("/api/v1/getMessages")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages(@QueryParam("type") String type, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map map = new HashMap();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            List<ShortMessageInfo> list = getDataService.getAllShortMessages(type);
            map.put("code", "0");
            map.put("info", list);
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @GET
    @Path("/api/v1/getMessageById")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessageById(@QueryParam("id") int id, @QueryParam("type") String type, @CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map map = new HashMap();
        List resultlist = new LinkedList();

        CookieInfo cookieInfo = cookieService.getCookie(strCookie);


        List<ShortMessageInfo> list;
        if (id == 0) {
            list = getDataService.getRecentMessage(type);
        }
        else{
            list = getDataService.getShortMessagesById(id, type);
        }
        if (cookieInfo != null) {
            for (int i = 0; i < list.size(); i++) {
                ShortMessageInfo shortMessageInfo = list.get(i);
                if (shortMessageInfo.getIsanonymous().equals("1")) {
                    AnonymousMessage anonymousMessage = new AnonymousMessage();
                    anonymousMessage.setContent(shortMessageInfo.getContent());
                    anonymousMessage.setIsanonymous("1");
                    anonymousMessage.setTime(shortMessageInfo.getTime());
                    anonymousMessage.setMessageid(shortMessageInfo.getMessageid());
                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo = getDataService.getStudentInfoById(shortMessageInfo.getSendid());
                    anonymousMessage.setSex(studentInfo.getStudentSex());
                    MarkInfo markInfo = new MarkInfo();
//                markInfo.setMessageid(shortMessageInfo.getMessageid());

                    String messageId = "T" + shortMessageInfo.getMessageid();
                    markInfo.setMessageid(messageId);
                    markInfo.setType("good");
                    List<MarkInfo> temp = getDataService.getMarkCount(markInfo);
                    anonymousMessage.setGoodcount(String.valueOf(temp.size()));
                    markInfo.setType("bad");
                    temp = getDataService.getMarkCount(markInfo);
                    anonymousMessage.setBadcount(String.valueOf(temp.size()));
                    resultlist.add(anonymousMessage);
                } else {
                    NoAnonymousMessage noAnonymousMessage = new NoAnonymousMessage();
                    noAnonymousMessage.set(shortMessageInfo);
                    MarkInfo markInfo = new MarkInfo();

//                markInfo.setMessageid(shortMessageInfo.getMessageid());

                    String messageId = "T" + shortMessageInfo.getMessageid();
                    markInfo.setMessageid(messageId);

                    markInfo.setType("good");
                    List<MarkInfo> temp = getDataService.getMarkCount(markInfo);
                    noAnonymousMessage.setGoodcount(String.valueOf(temp.size()));
                    markInfo.setType("bad");
                    temp = getDataService.getMarkCount(markInfo);
                    noAnonymousMessage.setBadcount(String.valueOf(temp.size()));
                    MyObject myObject = getDataService.getObjectById(shortMessageInfo.getSendid());
                    noAnonymousMessage.setIcon(myObject.getIcon());
                    noAnonymousMessage.setNickname(myObject.getName());
                    resultlist.add(noAnonymousMessage);
                }
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                ShortMessageInfo shortMessageInfo = list.get(i);
                AnonymousMessage anonymousMessage = new AnonymousMessage();
                anonymousMessage.setContent(shortMessageInfo.getContent());
                anonymousMessage.setIsanonymous("1");
                anonymousMessage.setTime(shortMessageInfo.getTime());
                anonymousMessage.setMessageid(shortMessageInfo.getMessageid());
                StudentInfo studentInfo = new StudentInfo();
                studentInfo = getDataService.getStudentInfoById(shortMessageInfo.getSendid());
                anonymousMessage.setSex(studentInfo.getStudentSex());
                MarkInfo markInfo = new MarkInfo();
//                markInfo.setMessageid(shortMessageInfo.getMessageid());

                String messageId = "T" + shortMessageInfo.getMessageid();
                markInfo.setMessageid(messageId);
                markInfo.setType("good");
                List<MarkInfo> temp = getDataService.getMarkCount(markInfo);
                anonymousMessage.setGoodcount(String.valueOf(temp.size()));
                markInfo.setType("bad");
                temp = getDataService.getMarkCount(markInfo);
                anonymousMessage.setBadcount(String.valueOf(temp.size()));
                resultlist.add(anonymousMessage);
            }
        }

        map.put("code", "0");
        map.put("info", resultlist);

        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

//    @GET
//    @Path("/api/v1/getMessageById")
//    @Consumes("application/x-www-form-urlencoded")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getMessageById(@QueryParam("id") int id, @QueryParam("type") String type, @CookieParam("myStrCookie") String strCookie) {
//        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
//        Response response;
//        Map map = new HashMap();
//        List resultlist = new LinkedList();
//        if (cookieInfo == null) {
//            map.put("code", "1");
//            map.put("errmsg", "error");
//        } else {
//            List<ShortMessageInfo> list;
//            if (id == 0) {
//                list = getDataService.getRecentMessage(type);
//            }
//            else{
//                list = getDataService.getShortMessagesById(id, type);
//            }
//            for (int i = 0; i < list.size(); i++) {
//                ShortMessageInfo shortMessageInfo = list.get(i);
//                if (shortMessageInfo.getIsanonymous().equals("1")) {
//                    AnonymousMessage anonymousMessage = new AnonymousMessage();
//                    anonymousMessage.setContent(shortMessageInfo.getContent());
//                    anonymousMessage.setIsanonymous("1");
//                    anonymousMessage.setTime(shortMessageInfo.getTime());
//                    anonymousMessage.setMessageid(shortMessageInfo.getMessageid());
//                    StudentInfo studentInfo = new StudentInfo();
//                    studentInfo = getDataService.getStudentInfoById(shortMessageInfo.getSendid());
//                    anonymousMessage.setSex(studentInfo.getStudentSex());
//                    MarkInfo markInfo = new MarkInfo();
//                    markInfo.setMessageid(shortMessageInfo.getMessageid());
//                    markInfo.setType("good");
//                    List<MarkInfo> temp = getDataService.getMarkCount(markInfo);
//                    anonymousMessage.setGoodcount(String.valueOf(temp.size()));
//                    markInfo.setType("bad");
//                    temp = getDataService.getMarkCount(markInfo);
//                    anonymousMessage.setBadcount(String.valueOf(temp.size()));
//                    resultlist.add(anonymousMessage);
//                } else {
//                    NoAnonymousMessage noAnonymousMessage = new NoAnonymousMessage();
//                    noAnonymousMessage.set(shortMessageInfo);
//                    MarkInfo markInfo = new MarkInfo();
//                    markInfo.setMessageid(shortMessageInfo.getMessageid());
//                    markInfo.setType("good");
//                    List<MarkInfo> temp = getDataService.getMarkCount(markInfo);
//                    noAnonymousMessage.setGoodcount(String.valueOf(temp.size()));
//                    markInfo.setType("bad");
//                    temp = getDataService.getMarkCount(markInfo);
//                    noAnonymousMessage.setBadcount(String.valueOf(temp.size()));
//                    MyObject myObject = getDataService.getObjectById(shortMessageInfo.getSendid());
//                    noAnonymousMessage.setIcon(myObject.getIcon());
//                    noAnonymousMessage.setNickname(myObject.getName());
//                    resultlist.add(noAnonymousMessage);
//                }
//            }
//
//            map.put("code", "0");
//            map.put("info", resultlist);
//        }
//        Response.ResponseBuilder responseBuilder = Response.ok(map);
//        response = responseBuilder.build();
//
//        return response;
//    }

    @GET
    @Path("/api/v1/getHotInfo")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHotInfo(@CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map map = new HashMap();
        List resultlist = new LinkedList();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            List<ShortMessageInfo> listMessage = getDataService.getHotMessage();
            map.put("code", "0");
            map.put("info", listMessage);
        }

        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @GET
    @Path("/api/v1/getSingleMessage")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleMessage(@QueryParam("id") int id, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map map = new HashMap();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            ShortMessageInfo shortMessageInfo = getDataService.getShortMessage(id);
            map.put("code", "0");
            map.put("info", shortMessageInfo);
        }

        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @GET
    @Path("/api/v1/getRecentCritical")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecentCritical(@CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map map = new HashMap();
        List resultlist = new LinkedList();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            List<CriticalInfo> listCritical = getDataService.getRecentCritial();
            map.put("code", "0");
            map.put("info", listCritical);
        }

        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }
}

