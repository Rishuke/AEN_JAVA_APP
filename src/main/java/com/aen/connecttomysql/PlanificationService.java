package com.aen.connecttomysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanificationService {

    private final PlanificationRepository planificationRepository;

    @Autowired
    public PlanificationService(PlanificationRepository planificationRepository) {
        this.planificationRepository = planificationRepository;
    }

    public List<PlanificationEntity> getAllPlanifications() {
        List<PlanificationEntity> planificationsList = new ArrayList<>();
        planificationRepository.findAll().forEach(planificationsList::add);
        return planificationsList;
    }

    public PlanificationEntity addPlanification(PlanificationEntity planification) {
        return planificationRepository.save(planification);
    }

    public PlanificationEntity updatePlanification(PlanificationEntity planificationEntity) {
        if (planificationEntity == null) {
            throw new IllegalArgumentException("Planification ID cannot be null for update operation");
        }

        // Vous pouvez ajouter des validations supplémentaires ici, si nécessaire

        return planificationRepository.save(planificationEntity);
    }
}
