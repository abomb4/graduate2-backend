package org.wlyyy.itrs.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wlyyy.itrs.domain.User;
import org.wlyyy.itrs.request.UserQuery;
import org.wlyyy.itrs.spring.ItrsBoot;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrsBoot.class)
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository dao;

    @Test
    public void test() {
//        final User user = new User();
//        user.setDepartmentId(1L);
//        user.setEmail("xxx@yyy.zzz");
//        user.setPassword("cao123");
//        user.setRealName("真实姓名");
//        user.setSalt("sale");
//        user.setSex(1);
//        user.setUserName("abomb4");

        // dao.insert(user);

        // final Long id = user.getId();
        // assertNotNull(id);

        // System.out.println(id);

        // assertEquals(user, dao.findById(id));

//        final Page<User> byCondition = dao.findByCondition(
//                new UserRepository.UserQuery()
//                        .setRealName(user.getRealName())
//                        .setEmail(user.getEmail())
//                        .setUserName(user.getUserName())
//                ,
//                new PageRequest(0, 10)
//        );

//        final List<User>  byCondition = dao.findByCondition(
//                new UserQuery()
//                        .setRealName(user.getRealName())
//                        .setEmail(user.getEmail())
//                        // .setUserName("'%' and 1=1 and '%'='")
//                        .setUserName(user.getUserName())
//
//                ,
//                new PageRequest(0, 10)
//        );


//        System.out.println(byCondition);

        // assertEquals(1, byCondition.getTotalElements());
        
        
        User user = dao.findById(null);
        System.out.println("user" + user);

    }
}