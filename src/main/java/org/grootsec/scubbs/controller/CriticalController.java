package org.grootsec.scubbs.controller;


import org.grootsec.scubbs.entity.CookieInfo;
import org.grootsec.scubbs.entity.CriticalInfo;
import org.grootsec.scubbs.service.AddDataService;
import org.grootsec.scubbs.service.CookieService;
import org.grootsec.scubbs.service.DeleteService;
import org.grootsec.scubbs.service.GetDataService;
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
public class CriticalController {

    @Autowired
    private AddDataService addDataService;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private GetDataService getDataService;

    @Autowired
    private DeleteService deleteService;

    @POST
    @Path("/api/v1/addCritical")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCritical(@FormParam("messageid") int messageid, @FormParam("content") String content, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            if (addDataService.addCritical(cookieInfo.getStudentnumber(), messageid, content)) {
                map.put("code", "0");
            } else {
                map.put("code", "1");
                map.put("errmsg", "Failed to add critical");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @POST
    @Path("/api/v1/deleteCritical")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCritical(@FormParam("criticalid") int criticalid, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            String studentnumber = cookieInfo.getStudentnumber();
            CriticalInfo criticalInfo = getDataService.getCritical(criticalid);
            if (criticalInfo == null) {
                map.put("code", "1");
                map.put("errmsg", "The critical id is not exist");
            } else if (criticalInfo.getSendid().equals(studentnumber)) {
                if (deleteService.deleteCritical(criticalid)){
                    map.put("code", "0");
                } else {
                    map.put("code", "Failed to delete critical");
                }
            } else {
                map.put("code", "1");
                map.put("errmsg", "You can't delete critical");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @GET
    @Path("/api/v1/getCritical")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCritical(@QueryParam("messageid") int messageid, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map map = new HashMap();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            List<CriticalInfo> list;
            list = getDataService.getCriticals(messageid);
            map.put("info", list);
            map.put("code", "0");
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }


}
