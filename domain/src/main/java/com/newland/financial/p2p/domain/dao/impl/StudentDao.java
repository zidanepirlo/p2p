package com.newland.financial.p2p.domain.dao.impl;

import com.newland.financial.p2p.domain.dao.IStudentDao;
import com.newland.financial.p2p.domain.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao extends MybatisBaseDao<Student> implements IStudentDao {

    public void insert(Student student) {
        super.insertSelective(student);
    }
}
