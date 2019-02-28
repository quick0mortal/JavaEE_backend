package org.grootsec.scubbs.controller;

import org.apache.ibatis.annotations.Param;
import org.grootsec.scubbs.entity.*;
import org.grootsec.scubbs.service.AddDataService;
import org.grootsec.scubbs.service.CookieService;
import org.grootsec.scubbs.service.GetDataService;
import org.grootsec.scubbs.service.LoginService;
import org.grootsec.scubbs.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Path("/")
@Component
public class StudentController {

    @GET
    @Path("test1")
    public Response createCookies() {
        NewCookie cookie1 = new NewCookie("myStrCookie", "cookieStrVal");
        NewCookie cookie2 = new NewCookie("myDateCookie", "2017-03-28");
        NewCookie cookie3 = new NewCookie("myIntCookie", "100");
        Response.ResponseBuilder rb = Response.ok("myStrCookie, "
                + "myDateCookie and myIntCookie sent to the browser");
        Response response = rb.cookie(cookie1, cookie2, cookie3)
                .build();
        return response;
    }

    @GET

    @Produces(MediaType.APPLICATION_JSON)

    @Path("/hello")

    public Map<String, Object> hello() {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("code", "1");

        map.put("codeMsg", "success");

        return map;

    }

    @Autowired
    private LoginService loginService;

    @Autowired
    private GetDataService getDataService;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private AddDataService addDataService;

    @POST
    @Path("/api/v1/login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("zjh") String zjh, @FormParam("mm") String mm, @CookieParam("myStrCookie") String strCookie) {
        Student student = new Student();
        student.setStudentNumber(zjh);
        student.setPassword(mm);
        StudentInfo studentInfo;
        MyObject myObject;
        Map<String, String> map = new HashMap();
        Response response;
        if ((loginService.localLogin(student) || loginService.remoteLogin(student))) {
            myObject = getDataService.getObjectById(zjh);
            if (myObject == null) {
                studentInfo = getDataService.getStudentInfoById(zjh);
                String name = studentInfo.getStudentName();
                addDataService.addObject(zjh, name+StringUtils.getRandom(), "", "", "student");
            }
            map = getDataService.getStudentInfo(student.getStudentNumber());
            try {
                List<ShortMessageInfo> list = getDataService.getAllShortMessagesBySendid(zjh);
                if (list == null) {
                    map.put("messageCount", "0");
                    map.put("beCriticalCount", "0");
                    map.put("beMarkCount", "0");
                } else {
                    MarkInfo markInfo = new MarkInfo();
                    markInfo.setType("good");
                    int beCriticalCount = 0;
                    int beMarkCount = 0;
                    for (ShortMessageInfo shortMessageInfo : list) {
                        List<CriticalInfo> criticalInfos = getDataService.getCriticals(shortMessageInfo.getMessageid());


                        //                markInfo.setMessageid(shortMessageInfo.getMessageid());

                        String messageId = "T" + shortMessageInfo.getMessageid();
                        markInfo.setMessageid(messageId);


                        List<MarkInfo> markInfos = getDataService.getMarkCount(markInfo);
                        if (criticalInfos == null) {
                            beCriticalCount += 0;
                        } else {
                            beCriticalCount += criticalInfos.size();
                        }
                        if (markInfos == null) {
                            beMarkCount += 0;
                        } else {
                            beMarkCount += markInfos.size();
                        }
                    }
                    map.put("messageCount", String.valueOf(list.size()));
                    map.put("beCriticalCount", String.valueOf(beCriticalCount));
                    map.put("beMarkCount", String.valueOf(beMarkCount));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            NewCookie newCookie = cookieService.generateCookie(student.getStudentNumber());
            Response.ResponseBuilder responseBuilder = Response.ok(map);
            response = responseBuilder.cookie(newCookie)
                    .build();
        } else {
            map.put("code", "1");
            map.put("errmsg", "error");
            Response.ResponseBuilder responseBuilder = Response.ok(map);
            response = responseBuilder.build();
        }

        return response;
    }

    @GET
    @Path("/api/v1/logout")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        if (cookieInfo != null) {
            map.put("code", "0");
            cookieService.deleteCookie(cookieInfo);
        } else {
            map.put("code", "1");
        }
        NewCookie newCookie = new NewCookie("myStrCookie", "", "/", "", "", 0, false);
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.cookie(newCookie)
                .build();
        return response;
    }

    @GET
    @Path("/api/v1/fetchInfo")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchInfo(@CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map<String, String> map;
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo != null) {
            String studentnumber = cookieInfo.getStudentnumber();
            map = getDataService.getStudentInfo(studentnumber);
            try {
                List<ShortMessageInfo> list = getDataService.getAllShortMessagesBySendid(cookieInfo.getStudentnumber());
                if (list == null) {
                    map.put("messageCount", "0");
                    map.put("beCriticalCount", "0");
                    map.put("beMarkCount", "0");
                } else {
                    MarkInfo markInfo = new MarkInfo();
                    markInfo.setType("good");
                    int beCriticalCount = 0;
                    int beMarkCount = 0;
                    for (ShortMessageInfo shortMessageInfo : list) {
                        List<CriticalInfo> criticalInfos = getDataService.getCriticals(shortMessageInfo.getMessageid());

                        System.out.println("皮一下很开心是不是");
                        //                markInfo.setMessageid(shortMessageInfo.getMessageid());

                        String messageId = "T" + shortMessageInfo.getMessageid();
                        markInfo.setMessageid(messageId);

                        List<MarkInfo> markInfos = getDataService.getMarkCount(markInfo);
                        if (criticalInfos == null) {
                            beCriticalCount += 0;
                        } else {
                            beCriticalCount += criticalInfos.size();
                        }
                        if (markInfos == null) {
                            beMarkCount += 0;
                        } else {
                            beMarkCount += markInfos.size();
                        }
                    }
                    map.put("messageCount", String.valueOf(list.size()));
                    map.put("beCriticalCount", String.valueOf(beCriticalCount));
                    map.put("beMarkCount", String.valueOf(beMarkCount));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            map = new HashMap<>();
            map.put("code", "1");
            map.put("errmsg", "cookie错误");
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();
        return response;
    }

    @GET
    @Path("/api/v1/getuserinfo")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getuserinfo(@QueryParam("id") String id, @CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map map = new HashMap();
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo != null) {
            map = getDataService.getNews(id);
            map.put("code", "0");

        } else {
            map.put("code", "1");
            map.put("errmsg", "cookie错误");
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();
        return response;
    }

    @GET
    @Path("/api/v1/getAllNameAndNumber")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNameAndNumber(@CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map map;
        List list;
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo != null) {
            list = getDataService.getAllNameAndNumber();
        } else {
            list = new LinkedList();
        }
        Response.ResponseBuilder responseBuilder = Response.ok(list);
        response = responseBuilder.build();
        return response;
    }



}
