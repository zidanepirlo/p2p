import com.newland.financial.p2p.domain.dao.IStudentDao;
import com.newland.financial.p2p.domain.entity.Student;
import com.newland.financial.p2p.domain.entity.TCmmCity;
import com.newland.financial.p2p.domain.entity.User;
import com.newland.financial.p2p.service.IUserService;
import org.aspectj.weaver.ast.ITestVisitor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context.xml"})

public class testService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    @Autowired
    private IStudentDao studentDao;

   // @Test
    public void  testInsert(){

//        User user = new User();
//        user.setId("1111");
//        user.setName("yuan");
//        userService.addUser(user);

//       User user = userService.getUserById("1111");
//        logger.info(user.getId());
//        logger.info(user.getName());

        Student student = new Student();
        student.setId("1111");
        student.setName("yuanqing");
        student.setAvgScore(new BigDecimal("34.12"));
        student.setCreateTime(new Date());
        studentDao.insert(student);
    }

    //@Test
    public void testInserts(){
        userService.addUsersForTest();
    }


    //@Test
    public void findByCitys(){

       List<TCmmCity> list = userService.findCityByName("广西自治区");
       logger.info(String.valueOf(list.size()));
    }



}
