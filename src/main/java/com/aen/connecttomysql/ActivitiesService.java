package com.aen.connecttomysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
}
