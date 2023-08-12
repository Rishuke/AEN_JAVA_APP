package com.aen.connecttomysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ActivitiesService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivitiesService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<ActivitiesEntity> getAllActivities() {
        List<ActivitiesEntity> activitiesList = new ArrayList<>();
        activityRepository.findAll().forEach(activitiesList::add);
        return activitiesList;
    }

    public ActivitiesEntity addActivity(ActivitiesEntity activity) {
        return activityRepository.save(activity);
    }

    public ActivitiesEntity updateActivity(ActivitiesEntity activityEntity) {
        if (activityEntity == null) {
            throw new IllegalArgumentException("Activity entity cannot be null for update operation");
        }



        // Vous pouvez ajouter des validations supplémentaires ici, si nécessaire

        return activityRepository.save(activityEntity);
    }

}
