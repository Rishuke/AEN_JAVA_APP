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

        AvionRepository avionRepository = context.getBean(AvionRepository.class);

        Iterable<AvionEntity> avions = avionRepository.findAll();
        for (AvionEntity avion: avions) {
            System.out.println(avion.getNom());
        }
        System.out.println("\n");

        PlanificationRepository planificationRepository = context.getBean(PlanificationRepository.class);

        Iterable<PlanificationEntity> planifications = planificationRepository.findAll();
        for (PlanificationEntity planification: planifications) {
            System.out.println(planification.getDate());
        }
        System.out.println("\n");

        LocationUlmRepository locationUlmRepository  = context.getBean(LocationUlmRepository.class);

        Iterable<LocationUlmEntity> locationulms = locationUlmRepository.findAll();
        for (LocationUlmEntity locationUlm: locationulms) {
            System.out.println(locationUlm.getHeure_debut());
        }
        System.out.println("\n");

        FormationRepository formationRepository = context.getBean(FormationRepository.class);

        Iterable<FormationEntity> formations = formationRepository.findAll();
        for (FormationEntity formation: formations) {
            System.out.println(formation.getNom());
        }
        System.out.println("\n");


    }

}
