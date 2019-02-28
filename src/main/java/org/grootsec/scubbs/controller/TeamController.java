package org.grootsec.scubbs.controller;

import org.grootsec.scubbs.entity.CookieInfo;
import org.grootsec.scubbs.entity.MyObject;
import org.grootsec.scubbs.entity.TeamMemberInfo;
import org.grootsec.scubbs.service.*;
import org.grootsec.scubbs.util.StringUtils;
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
public class TeamController {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private AddDataService addDataService;

    @Autowired
    private DeleteService deleteService;

    @Autowired
    private GetDataService getDataService;

    @Autowired
    private UpdateService updateService;

    @POST
    @Path("/api/v1/createTeam")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTeam(@FormParam("name") String name, @FormParam("description") String description, @CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map<String, String> map = new HashMap<>();
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            MyObject myObject = new MyObject();
            myObject.setName(name);
            myObject.setRole("team");
            myObject.setDescription(description);
            if (addDataService.addTeamObject(myObject)) {
                MyObject myObject1 = getDataService.getObjectByName(name);
                if (addDataService.addTeamMember(cookieInfo, myObject1, "2")) {
                    map = getDataService.getObjectInfoByName(name);
                } else {
                    deleteService.deleteTeamObject(name);
                    map.put("code", "1");
                    map.put("errmsg", "Failed to add number to team.");
                }
            } else {
                map.put("code", "1");
                map.put("errmsg", "Failed to create team, please choose another team name.");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @POST
    @Path("/api/v1/applyTeam")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response applyTeam(@FormParam("teamid") String teamid, @CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map<String, String> map = new HashMap<>();
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            MyObject myObject = new MyObject();
            myObject.setId(teamid);
            if (addDataService.addTeamMember(cookieInfo, myObject, "0")) {
                map.put("code", "0");
            } else {
                map.put("code", "1");
                map.put("errmsg", "Failed to apply.");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }


    @POST
    @Path("/api/v1/deleteTeam")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeam(@FormParam("teamid") String teamid, @CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map<String, String> map = new HashMap<>();
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            String studentnumber = cookieInfo.getStudentnumber();
//            TeamMemberInfo teamMemberInfo = getDataService.getTeamMember(teamid, studentnumber);
            if (getDataService.getTeamMember(teamid, studentnumber).getIsadmin().equals("2")) {
                if(deleteService.deleteTeamAllMember(teamid) && deleteService.deleteTeamObjectById(teamid)) {
                    map.put("code", "0");
                } else {
                    map.put("code", "1");
                    map.put("errmsg", "删除失败");
                }
            } else {
                map.put("code", "1");
                map.put("errmsg", "没有权限");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @POST
    @Path("/api/v1/deleteTeamMember")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeamMember(@FormParam("teamid") String teamid, @FormParam("targetid") String targetid, @CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map<String, String> map = new HashMap<>();
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            String studentnumber = cookieInfo.getStudentnumber();
            if (getDataService.getTeamMember(teamid, studentnumber).getIsadmin().equals("2")) {
                TeamMemberInfo teamMemberInfo = getDataService.getTeamMember(teamid, targetid);
                if(deleteService.deleteTeamMember(teamMemberInfo)) {
                    map.put("code", "0");
                } else {
                    map.put("code", "1");
                    map.put("errmsg", "删除失败");
                }
            } else {
                map.put("code", "1");
                map.put("errmsg", "没有权限");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

    @POST
    @Path("/api/v1/setAdmin")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setAdmin(@FormParam("studentnumber") String studentnumber, @FormParam("teamid") String teamid, @FormParam("admin") String admin, @CookieParam("myStrCookie") String strCookie) {
        Response response;
        Map<String, String> map = new HashMap<>();
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            String studentnumberadmin = cookieInfo.getStudentnumber();
            if (getDataService.getAdmin(studentnumberadmin, teamid).equals(2)) {
                if(updateService.changeAdmin(studentnumber, teamid, admin)) {
                    map.put("code", "0");
                } else {
                    map.put("code", "1");
                    map.put("errmsg", "设置失败");
                }
            } else {
                map.put("code", "1");
                map.put("errmsg", "没有权限");
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }


    @GET
    @Path("/api/v1/getTeamInfo")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeamInfo(@QueryParam("studentnumber") String studentnumber, @CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map map = new HashMap();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            List<MyObject> list;
            list = getDataService.getTeamInfoBySId(studentnumber);
            map.put("info", list);
            map.put("code", "0");
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }


    @GET
    @Path("/api/v1/getAllTeamInfo")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTeamInfo(@CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map map = new HashMap();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            List<MyObject> list;
            list = getDataService.getAllObject("team");
            map.put("code", "0");
            map.put("info", list);
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }



    @GET
    @Path("/api/v1/getTeamInfoByNumber")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeamInfoByNumber(@QueryParam("id") String id,@CookieParam("myStrCookie") String strCookie) {
        CookieInfo cookieInfo = cookieService.getCookie(strCookie);
        Response response;
        Map map = new HashMap();
        if (cookieInfo == null) {
            map.put("code", "1");
            map.put("errmsg", "error");
        } else {
            MyObject myObject;
            myObject = getDataService.getObjectById(id);
            map.put("code", "0");
            map.put("info", myObject);
            List<TeamMemberInfo> list = getDataService.getTeamMembers(id);
            map.put("count", String.valueOf(list.size()));
            for (TeamMemberInfo teamMemberInfo : list) {
                if (teamMemberInfo.getIsadmin().equals("2")) {
                    map.put("Studentnumber", teamMemberInfo.getStudentnumber());
                    break;
                }
            }
        }
        Response.ResponseBuilder responseBuilder = Response.ok(map);
        response = responseBuilder.build();

        return response;
    }

}
