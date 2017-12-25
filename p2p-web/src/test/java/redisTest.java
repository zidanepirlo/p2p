import com.newland.financial.p2p.common.util.DistributedLock;
import com.newland.financial.p2p.common.util.JedisUtil;
import com.newland.financial.p2p.common.util.TimeUtil;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context.xml"})
public class redisTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private DistributedLock distributedLock;

    @Test
    public void test(){

        System.out.println(jedisUtil);
        jedisUtil.getObject().set("yuan","qing");
        String val =jedisUtil.getObject().get("yuan");
        logger.info("-----------------------------------------:"+val);

//        Date expiry = TimeUtil.getDateExpiry(50000);
//        String str = jedisUtil.getObject().setex("yuan",(int) ((expiry.getTime() - System.currentTimeMillis()) / 1000),"qing");
//        logger.info("--------------------------st={}",str);
    }

    @Test
    public void test1(){

        Date expiry = TimeUtil.getDateExpiry(5000);
        String result =  String.valueOf((int) ((expiry.getTime() - System.currentTimeMillis()) / 1000));
        logger.info(result);

    }

    @Test
    public void testDbLock(){

//        TimeUnit timeUnit =TimeUnit.HOURS;
//        logger.info(timeUnit.name());
//        logger.info(String.valueOf(timeUnit.toDays(1)));
//        logger.info(String.valueOf(timeUnit.toHours(2)));
//        logger.info(String.valueOf(timeUnit.toNanos(1)));

        String key = "yuan";
        String value = "qing";
        Date expiry = TimeUtil.getDateExpiry(50000);
        Boolean getLock = distributedLock.tryLock(key,value,100,TimeUnit.SECONDS,expiry);

        logger.info("begin get lock key={}",key);
        if (getLock){

            logger.info(" get lock key={} success",key);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                distributedLock.releaseLock(key);
            }
        }
        else {
            logger.info(" get lock key={} fail",key);
        }

    }

    @Setter
    @Getter
    class student{

        private String name;
        private String id;

        @Override
        public String toString() {
            return "student{" +
                    "id=" + id +
                    ", name=" + name +
                    '}';
        }
    }


    @Test
    public void testprint(){

        List<student> studentList = new ArrayList<>();
        student student1 = new student();
        student1.setId("1");
        student1.setName("yuan");

        student student2 = new student();
        student2.setId("2");
        student2.setName("qing");

        studentList.add(student1);
        studentList.add(student2);

        logger.info("students ,studentList={}",studentList);
    }
}
