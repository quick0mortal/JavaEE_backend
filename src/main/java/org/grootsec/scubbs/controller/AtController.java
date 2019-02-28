package org.grootsec.scubbs.controller;

import org.grootsec.scubbs.entity.AtInfo;
import org.grootsec.scubbs.entity.CookieInfo;
import org.grootsec.scubbs.service.*;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Path("/")
@Component
public class AtController {

    @Autowired
    private AddDataService addDataService;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private GetDataService getDataService;

    @Autowired
    private DeleteService deleteService;

    @Autowired
    private UpdateService updateService;


    @POST
    @Path("/api/v1/addAt")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAt(@FormParam("targetid") String targetid, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            if (addDataService.addAt(cookieInfo.getStudentnumber(), targetid)) {
                map.put("code", "0");
            } else {
                map.put("code", "1");
                map.put("errsmg", "Failed to add at");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @POST
    @Path("/api/v1/deleteAt")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAt(@FormParam("id") int id, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            String studentnumber = cookieInfo.getStudentnumber();
            AtInfo atInfo = getDataService.getAtInfo(id);
            if (atInfo.getSendid().equals(studentnumber)) {
                if (deleteService.deleteAtInfo(id)){
                    map.put("code", "0");
                } else {
                    map.put("code", "Failed to delete at");
                }
            } else {
                map.put("code", "1");
                map.put("errsmg", "You can't delete at");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @GET
    @Path("/api/v1/getAtNumber")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAtNumber(@CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            String studentnumber = cookieInfo.getStudentnumber();
            List<AtInfo> list;
            list = getDataService.getAtInfoByTarget(studentnumber, "0");
            map.put("count", String.valueOf(list.size()));
            map.put("code", "0");
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();
        return response;
    }

    @GET
    @Path("/api/v1/getAtInfo")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAtInfo(@CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map map = new HashMap();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            String studentnumber = cookieInfo.getStudentnumber();
            List<AtInfo> list;
            list = getDataService.getAtInfoByTarget(studentnumber, "0");
            map.put("code", "0");
            map.put("info", list);
            for (AtInfo atInfo : list) {
                atInfo.setHasreaded("1");
                updateService.updateAt(atInfo);
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();
        return response;
    }
}

