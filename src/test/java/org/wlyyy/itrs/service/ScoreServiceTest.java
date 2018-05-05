package org.wlyyy.itrs.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wlyyy.itrs.spring.ItrsBoot;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItrsBoot.class)
// @Transactional
public class ScoreServiceTest {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScoreRuleService scoreRuleService;

    @Test
    public void test() {

//        ScoreRule scoreRule = new ScoreRule();
//        scoreRule.setRule("二面通过");
//        scoreRule.setScore(10);
//        scoreRuleService.insertScoreRule(scoreRule);


        // String result = "二面通过";
        String result = null;
        Long userId = 4l;
        String memo = "您推荐的xxx已二面通过";
        scoreService.changeScore(result, userId, memo);
    }
}
