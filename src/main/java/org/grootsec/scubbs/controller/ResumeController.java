package org.grootsec.scubbs.controller;

import org.grootsec.scubbs.entity.CookieInfo;
import org.grootsec.scubbs.entity.MyObject;
import org.grootsec.scubbs.entity.ResumeInfo;
import org.grootsec.scubbs.service.AddDataService;
import org.grootsec.scubbs.service.CookieService;
import org.grootsec.scubbs.service.GetDataService;
import org.grootsec.scubbs.service.UpdateService;
import org.grootsec.scubbs.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Path("/")
@Component
public class ResumeController {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private GetDataService getDataService;

    @Autowired
    private AddDataService addDataService;

    @Autowired
    private UpdateService updateService;

    @POST
    @Path("/api/v1/addResume")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addResume(@FormParam("brief") String brief, @FormParam("content") String content, @CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map<String, String> map = new HashMap<>();
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            ResumeInfo resumeInfo = new ResumeInfo();
            resumeInfo.setStudentnumber(cookieInfo.getStudentnumber());
            resumeInfo.setBrief(brief);
            resumeInfo.setContent(content);
            resumeInfo.setTime(DateUtils.getDate());
            if (addDataService.addResume(resumeInfo)) {
                map.put("code", "0");
            } else {
                map.put("code", "1");
                map.put("errmsg", "Failed to create resume.");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @POST
    @Path("/api/v1/setResume")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setResume(@FormParam("brief") String brief, @CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map<String, String> map = new HashMap<>();
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            ResumeInfo resumeInfo = new ResumeInfo();
            resumeInfo.setStudentnumber(cookieInfo.getStudentnumber());
            resumeInfo.setBrief(brief);
            resumeInfo.setTime(DateUtils.getDate());
            if (updateService.updateResume(resumeInfo)) {
                map.put("code", "0");
            } else {
                map.put("code", "1");
                map.put("errmsg", "Failed to update resume.");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @POST
    @Path("/api/v1/setContent")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setContent(@FormParam("content") String content, @CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map<String, String> map = new HashMap<>();
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            ResumeInfo resumeInfo = new ResumeInfo();
            resumeInfo.setStudentnumber(cookieInfo.getStudentnumber());
            resumeInfo.setContent(content);
            resumeInfo.setTime(DateUtils.getDate());
            if (updateService.updateContent(resumeInfo)) {
                map.put("code", "0");
            } else {
                map.put("code", "1");
                map.put("errmsg", "Failed to update content..");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @GET
    @Path("/api/v1/getResume")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResume(@CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map<String, String> map = new HashMap<>();
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            ResumeInfo resumeInfo = getDataService.getResume(cookieInfo.getStudentnumber());
            map.put("code", "0");
            map.put("studentnumber", resumeInfo.getStudentnumber());
            map.put("brief", resumeInfo.getBrief());
            map.put("content", resumeInfo.getContent());
            map.put("time", resumeInfo.getTime());
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

}
