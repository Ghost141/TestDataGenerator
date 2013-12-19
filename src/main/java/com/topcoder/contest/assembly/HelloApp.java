package com.topcoder.contest.assembly;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloApp {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
//        MarathonContests marathonContests = context.getBean(MarathonContests.class);
//        marathonContests.execute();
//        SRMStatistics srmStatistics = context.getBean(SRMStatistics.class);
//        srmStatistics.execute();
//        MarathonStatistics marathonStatistics = context.getBean(MarathonStatistics.class);
//        marathonStatistics.execute();
        StudioTopRanked studioTopRanked = context.getBean(StudioTopRanked.class);
        studioTopRanked.execute();
    }
}
