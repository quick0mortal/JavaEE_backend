package org.grootsec.scubbs.controller;

import org.grootsec.scubbs.entity.CookieInfo;
import org.grootsec.scubbs.entity.CriticalInfo;
import org.grootsec.scubbs.entity.MarkInfo;
import org.grootsec.scubbs.service.*;
import org.jvnet.hk2.config.GenerateServiceFromMethod;
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
public class MarkController {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private GetDataService getDataService;

    @Autowired
    private AddDataService addDataService;

    @Autowired
    private DeleteService deleteService;

    @Autowired
    private UpdateService updateService;
//
//    @POST
//    @Path("/api/v1/addMark")
//    @Consumes("application/x-www-form-urlencoded")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addMark(@FormParam("messageid") int messageid, @FormParam("type") String type, @CookieParam("myStrCookie") String strCookie) {
//        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
//        Response response;
//        Map<String, String> map = new HashMap<>();
//        if (cookieInfo == null) {
//            map.put("code", "1");
//            map.put("errmsg", "error");
//        } else {
//            if (getDataService.getShortMessage(messageid) == null) {
//                map.put("code", "1");
//                map.put("errmsg", "The short message is not exist");
//            } else {
//                if (addDataService.addMark(cookieInfo.getStudentnumber(), messageid, type)) {
//                    map.put("code", "0");
//                    MarkInfo markInfo = new MarkInfo();
//                    markInfo.setMessageid(messageid);
//                    List<MarkInfo> list = getDataService.getMarkCount(markInfo);
//                    map.put("count", String.valueOf(list.size()));
//                } else {
//                    map.put("code", "1");
//                    map.put("errmsg", "Failed to add mark");
//                }
//            }
//        }
//        Response.ResponseBuilder responseBuilder = Response.ok(map);
//        response = responseBuilder.build();
//
//        return response;
//    }
//
//    @POST
//    @Path("/api/v1/deleteMark")
//    @Consumes("application/x-www-form-urlencoded")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response deleteMark(@FormParam("messageid") int messageid, @FormParam("type") String type, @CookieParam("myStrCookie") String strCookie) {
//        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
//        Response response;
//        Map<String, String> map = new HashMap<>();
//        if (cookieInfo == null) {
//            map.put("code", "1");
//            map.put("errmsg", "error");
//        } else {
//            String studentnumber = cookieInfo.getStudentnumber();
//            MarkInfo markInfo = new MarkInfo();
//            markInfo.setType(type);
//            markInfo.setMessageid(messageid);
//            markInfo.setMarkerid(studentnumber);
//            MarkInfo markInfo1 = getDataService.getMark(markInfo);
//            if ((markInfo1 != null) && markInfo1.getMarkerid().equals(studentnumber)) {
//                if (deleteService.deleteMark(markInfo)){
//                    map.put("code", "0");
//                    List<MarkInfo> list = getDataService.getMarkCount(markInfo);
//                    map.put("count", String.valueOf(list.size()));
//                } else {
//                    map.put("code", "1");
//                    map.put("errmsg", "Failed to delete mark");
//                }
//            } else {
//                map.put("code", "1");
//                map.put("errmsg", "You can't delete mark");
//            }
//        }
//        Response.ResponseBuilder responseBuilder = Response.ok(map);
//        response = responseBuilder.build();
//
//        return response;
//    }


//    @POST
//    @Path("/api/v1/setMark")
//    @Consumes("application/x-www-form-urlencoded")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response setMark(@FormParam("messageid") int messageid, @FormParam("type") String type, @FormParam("option") String option, @CookieParam("myStrCookie") String strCookie) {
//        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
//        Response response;
//        Map<String, String> map = new HashMap<>();
//        if (cookieInfo == null) {
//            map.put("code", "1");
//            map.put("errmsg", "error");
//        } else {
//            String studentnumber = cookieInfo.getStudentnumber();
//            MarkInfo markInfo = new MarkInfo();
//            markInfo.setType(type);
//            markInfo.setMessageid(messageid);
//            markInfo.setMarkerid(studentnumber);
//            if (updateService.updateMark(markInfo, option)) {
//                List<MarkInfo> list = getDataService.getMarkCount(markInfo);
//                map.put("count", String.valueOf(list.size()));
//                if (type.equals("bad") && list.size() > 2) {
//                    if(deleteService.deleteMessage(messageid)) {
//                        map.put("code", "0");
//                    } else {
//                        map.put("errmsg", "delete bad message failed");
//                    }
//                }
//            } else {
//                map.put("code", "Failed to update mark");
//            }
//        }
//        Response.ResponseBuilder responseBuilder = Response.ok(map);
//        response = responseBuilder.build();
//
//        return response;
//    }

    @POST
    @Path("/api/v1/setMark")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setMark(@FormParam("messageid") String messageid, @FormParam("type") String type, @FormParam("option") String option, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map<String, String> map = new HashMap<>();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            String studentnumber = cookieInfo.getStudentnumber();
            MarkInfo markInfo = new MarkInfo();
            markInfo.setType(type);
            markInfo.setMessageid(messageid);
            markInfo.setMarkerid(studentnumber);
            if (updateService.updateMark(markInfo, option)) {
                List<MarkInfo> list = getDataService.getMarkCount(markInfo);
                map.put("count", String.valueOf(list.size()));
                if (type.equals("bad") && list.size() > 2) {
                    String tmp = messageid.substring(1);
                    int intMessageid = Integer.parseInt(tmp);
                    if(deleteService.deleteMessage(intMessageid)) {
                        map.put("code", "0");
                    } else {
                        map.put("errmsg", "delete bad message failed");
                    }
                }
            } else {
                map.put("code", "Failed to update mark");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }


    @POST
    @Path("/api/v1/getMark")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMark(@FormParam("messageid") String messageid, @FormParam("type") String type, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map map = new HashMap();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            MarkInfo markInfo = new MarkInfo();
            markInfo.setMessageid(messageid);
            markInfo.setType(type);
            List<MarkInfo> list = getDataService.getMarkCount(markInfo);
            map.put("count", String.valueOf(list.size()));
            map.put("code", "0");
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

}
