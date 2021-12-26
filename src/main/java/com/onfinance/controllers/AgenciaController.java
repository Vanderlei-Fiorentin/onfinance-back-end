package com.onfinance.controllers;

import com.onfinance.entities.AgenciaEntity;
import com.onfinance.repositories.AgenciaRepository;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class AgenciaController extends Controller<Integer, AgenciaEntity> {

    public AgenciaController() {
        super(AgenciaRepository.class);
    }

}
