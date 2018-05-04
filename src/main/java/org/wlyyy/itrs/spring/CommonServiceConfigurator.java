package org.wlyyy.itrs.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wlyyy.common.service.JumpedSequenceManagerImpl;
import org.wlyyy.itrs.dao.SequenceRepository;

@Configuration
public class CommonServiceConfigurator {

    @Autowired
    SequenceRepository sequenceRepository;

    @Bean
    public JumpedSequenceManagerImpl seq(@Value("${sequence.jump:100}") Long jumpStep, SequenceRepository sequenceRepository) {
        return new JumpedSequenceManagerImpl(jumpStep, sequenceRepository);
    }
}

