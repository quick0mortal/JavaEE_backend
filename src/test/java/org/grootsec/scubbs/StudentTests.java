package org.grootsec.scubbs;

import org.grootsec.scubbs.entity.Student;
import org.grootsec.scubbs.mapper.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication(scanBasePackageClasses = ScubbsApplication.class)
public class StudentTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private StudentMapper studentMapper;


    @Test
    @Rollback
    public void delete() throws Exception {
        Student student = new Student("2015141463122", "1");
        studentMapper.delete(student);
    }
}
