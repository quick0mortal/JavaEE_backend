package org.grootsec.scubbs.service;

import org.grootsec.scubbs.entity.MarkInfo;
import org.grootsec.scubbs.entity.TeamMemberInfo;

public interface DeleteService {

    Boolean deleteTeamObject(String name);

    Boolean deleteTeamObjectById(String id);

    Boolean deleteMessage(int id);

    Boolean deleteAtInfo(int id);

    Boolean deleteCritical(int criticalid);

    Boolean deleteMark(MarkInfo markInfo);

    Boolean deleteTeamMember(TeamMemberInfo teamMemberInfo);

    Boolean deleteTeamAllMember(String teamid);
}
