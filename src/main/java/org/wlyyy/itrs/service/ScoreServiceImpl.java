package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.dao.ScoreFlowRepository;
import org.wlyyy.itrs.dao.ScoreRuleRepository;
import org.wlyyy.itrs.dao.ScoreUserRepository;
import org.wlyyy.itrs.domain.ScoreFlow;
import org.wlyyy.itrs.domain.ScoreUser;

@Service
public class ScoreServiceImpl implements  ScoreService{

    @Autowired
    private ScoreRuleRepository scoreRuleRepository;

    @Autowired
    private ScoreFlowRepository scoreFlowRepository;

    @Autowired
    private ScoreUserRepository scoreUserRepository;

    @Override
    @Transactional
    public BaseServiceResponse<String> changeScore(String rule, Long userId, String memo) {
        if (rule == null) {
            return new BaseServiceResponse<>(true, "没有新结果，不必进行积分更新!", null, null);
        }
        Integer score = scoreRuleRepository.findScoreByRule(rule);
        if (score == null) {
            return new BaseServiceResponse<>(true, "未找到相应规则，不进行积分更新!", null, null);
        }

        // 插入积分流程变动记录表中
        ScoreFlow scoreFlow = new ScoreFlow();
        scoreFlow.setScore(score);
        scoreFlow.setUserId(userId);
        scoreFlow.setMemo(memo);
        scoreFlowRepository.insert(scoreFlow);

        // 更新用户当前积分
        // 获取用户当前积分
        ScoreUser scoreUser = scoreUserRepository.findCurrentScoreByUserId(userId);
        if (scoreUser == null) {
            // 该用户不存在于【用户当前积分表】中
            // 插入
            ScoreUser newScoreUser = new ScoreUser();
            newScoreUser.setUserId(userId);
            newScoreUser.setCurrentScore(score);
            scoreUserRepository.insert(newScoreUser);
            return new BaseServiceResponse<>(true, "新增用户到当前积分表中!", null, null);
        } else {
            // 该用户存在于【用户当前积分表】中
            // 更新当前积分
            scoreUser.setCurrentScore(scoreUser.getCurrentScore() + score);
            scoreUserRepository.updateById(scoreUser);
            return new BaseServiceResponse<>(true, "更新用户当前积分成功!", null, null);
        }
    }
}
