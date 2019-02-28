package org.grootsec.scubbs.controller;

import org.grootsec.scubbs.entity.CookieInfo;
import org.grootsec.scubbs.entity.CriticalInfo;
import org.grootsec.scubbs.entity.MyObject;
import org.grootsec.scubbs.mapper.CookieMapper;
import org.grootsec.scubbs.mapper.ObjectMapper;
import org.grootsec.scubbs.service.AddDataService;
import org.grootsec.scubbs.service.CookieService;
import org.grootsec.scubbs.service.GetDataService;
import org.grootsec.scubbs.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/")
@Component
public class ObjectController {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private GetDataService getDataService;

    @Autowired
    private AddDataService addDataService;

    @Autowired
    private UpdateService updateService;


    @POST
    @Path("/api/v1/addObject")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addObject(@FormParam("name") String name,
                              @FormParam("icon") String icon, @FormParam("description") String description,
                              @FormParam("role") String role, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            if (addDataService.addObject(cookieInfo.getStudentnumber(), name, description, icon, role)) {
                map.put("code", "0");
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
    @Path("/api/v1/updateObject")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateObject(@FormParam("id") String id, @FormParam("name") String name,
                                 @FormParam("description") String description, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            if (updateService.updateObject(id, name, description)) {
                map.put("code", "0");
            } else {
                map.put("code", "1");
                map.put("errmsg", "Failed to update");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }


    @POST
    @Path("/api/v1/updateIcon")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateIcon(@FormParam("id") String id, @FormParam("icon") String icon, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            if (updateService.updateIcon(id, icon)) {
                map.put("code", "0");
            } else {
                map.put("code", "1");
                map.put("errmsg", "Failed to update");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @GET
    @Path("/api/v1/getObject")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObject(@CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map map = new HashMap();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            List<MyObject> list;
            list = getDataService.getAllObject("team");
            List<MyObject> list1;
            list1 = getDataService.getAllObject("student");
            map.put("teamInfo", list);
            map.put("studentInfo", list1);
            map.put("code", "0");
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }


}
