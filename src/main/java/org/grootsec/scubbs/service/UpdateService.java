package org.grootsec.scubbs.service;

import org.grootsec.scubbs.entity.AtInfo;
import org.grootsec.scubbs.entity.MarkInfo;
import org.grootsec.scubbs.entity.ResumeInfo;

public interface UpdateService {

    Boolean changeAdmin(String studentnumber, String teamid, String admin);

    Boolean updateObject(String id, String  name, String  description);

    Boolean updateIcon(String id, String icon);

    Boolean updateMark(MarkInfo markInfo, String option);

    Boolean updateAt(AtInfo atInfo);

    Boolean updateResume(ResumeInfo resumeInfo);

    Boolean updateContent(ResumeInfo resumeInfo);
}
