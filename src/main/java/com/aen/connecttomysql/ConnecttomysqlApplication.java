package com.aen.connecttomysql;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConnecttomysqlApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(com.aen.connecttomysql.ConnecttomysqlApplication.class, args);

        MemberRepository memberRepository = context.getBean(MemberRepository.class);

        Iterable<MembersEntity> members = memberRepository.findAll();
        for (MembersEntity member : members) {
            System.out.println(member.getPrenom());
        }
        System.out.println("\n");

        ActivityRepository activityRepository = context.getBean(ActivityRepository.class);

        Iterable<ActivitiesEntity> activities = activityRepository.findAll();
        for (ActivitiesEntity activity : activities) {
            System.out.println(activity.getNom_activite());
        }
        System.out.println("\n");

        UlmRepository ulmRepository = context.getBean(UlmRepository.class);

        Iterable<UlmEntity> ulms = ulmRepository.findAll();
        for (UlmEntity ulm : ulms) {
            System.out.println(ulm.getNom());
        }
        System.out.println("\n");
    }

}
