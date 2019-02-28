package org.grootsec.scubbs.service.impl;

import org.grootsec.scubbs.entity.*;
import org.grootsec.scubbs.mapper.*;
import org.grootsec.scubbs.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private TeamMemberInfoMapper teamMemberInfoMapper;

    @Autowired
    private ObjectMapper objectMapper;

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
    public Boolean changeAdmin(String studentnumber, String teamid, String admin) {
        TeamMemberInfo teamMemberInfo = new TeamMemberInfo();
        teamMemberInfo.setTeamnumber(teamid);
        teamMemberInfo.setStudentnumber(studentnumber);
        teamMemberInfo.setIsadmin(admin);
        try {
            teamMemberInfoMapper.update(teamMemberInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateObject(String id, String name, String description) {
        MyObject myObject = new MyObject();
        myObject.setName(name);
        myObject.setId(id);
        myObject.setDescription(description);
        try {
            objectMapper.updateName(myObject);
            objectMapper.updateDes(myObject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateIcon(String id, String icon) {
        MyObject myObject = new MyObject();
        myObject.setId(id);
        myObject.setIcon(icon);
        try {
            System.out.println("Update myObject.");
            System.out.println();
            objectMapper.updateIcon(myObject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateMark(MarkInfo markInfo, String option) {
        try {
            if (option.equals("1")) {
                markInfoMapper.insert(markInfo);
                return true;
            } else if (option.equals("0")) {
                markInfoMapper.delete(markInfo);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateAt(AtInfo atInfo) {
        try {
            atInfoMapper.updateRead(atInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Boolean updateResume(ResumeInfo resumeInfo) {
        try {
            resumeMapper.updateBrief(resumeInfo);
            return true;
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateContent(ResumeInfo resumeInfo) {
        try {
            resumeMapper.updateContent(resumeInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
