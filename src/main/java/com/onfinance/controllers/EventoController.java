package com.onfinance.controllers;

import com.onfinance.entities.EventoEntity;
import com.onfinance.repositories.EventoRepository;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class EventoController extends Controller<Integer, EventoEntity> {

    public EventoController() {
        super(EventoRepository.class);
    }

}
