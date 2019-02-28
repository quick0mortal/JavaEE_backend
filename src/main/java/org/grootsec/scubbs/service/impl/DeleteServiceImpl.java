package org.grootsec.scubbs.service.impl;


import org.grootsec.scubbs.entity.CriticalInfo;
import org.grootsec.scubbs.entity.MarkInfo;
import org.grootsec.scubbs.entity.TeamMemberInfo;
import org.grootsec.scubbs.mapper.*;
import org.grootsec.scubbs.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteServiceImpl implements DeleteService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeamMemberInfoMapper teamMemberInfoMapper;

    @Autowired
    private ShortMessageMapper shortMessageMapper;

    @Autowired
    private AtInfoMapper atInfoMapper;

    @Autowired
    private CriticalInfoMapper criticalInfoMapper;

    @Autowired
    private MarkInfoMapper markInfoMapper;

    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    public Boolean deleteTeamObject(String name) {
        try {
            objectMapper.deleteByName(name);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteTeamObjectById(String id) {
        try {
            objectMapper.deleteById(id);
            System.out.println();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteMessage(int id) {
        try {
            shortMessageMapper.deleteMessageById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteAtInfo(int id) {
        try {
            atInfoMapper.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteCritical(int criticalid) {
        try {
            CriticalInfo criticalInfo = criticalInfoMapper.getCriticalInfo(criticalid);
            criticalInfoMapper.delete(criticalInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteMark(MarkInfo markInfo) {
        try {
            markInfoMapper.delete(markInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            return false;
        }
    }

    @Override
    public Boolean deleteTeamAllMember(String teamid) {
        try {
            teamMemberInfoMapper.deleteAllMember(teamid);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteTeamMember(TeamMemberInfo teamMemberInfo) {
        try {
            teamMemberInfoMapper.delete(teamMemberInfo.getTeamnumber(), teamMemberInfo.getStudentnumber());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
