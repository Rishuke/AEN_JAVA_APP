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
}
