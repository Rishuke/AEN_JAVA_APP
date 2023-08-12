package com.aen.connecttomysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormationService {

    private final FormationRepository formationRepository;

    @Autowired
    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    public List<FormationEntity> getAllFormations() {
        List<FormationEntity> formationsList = new ArrayList<>();
        formationRepository.findAll().forEach(formationsList::add);
        return formationsList;
    }

    public FormationEntity addFormation(FormationEntity formation) {
        return formationRepository.save(formation);
    }


    public void updateFormation(FormationEntity formationEntity) throws Exception {
        // Assuming the FormationEntity has a valid ID, and the repository has a 'save' method
        // If the formation does not exist, an exception should be raised.
        if (formationEntity == null ) {
            throw new IllegalArgumentException("Invalid Formation Entity provided");
        }

        FormationEntity existingFormation = formationRepository.findById((long) formationEntity.getId()).orElse(null);
        if (existingFormation == null) {
            throw new Exception("Formation with ID " + formationEntity.getId() + " does not exist");
        }

        // Assuming the repository's 'save' method handles both updates and inserts
        formationRepository.save(formationEntity);
    }
}
