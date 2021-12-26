package com.onfinance.controllers;

import com.onfinance.entities.UnidadeMedidaEntity;
import com.onfinance.repositories.UnidadeMedidaRepository;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class UnidadeMedidaController extends Controller<Integer, UnidadeMedidaEntity> {

    public UnidadeMedidaController() {
        super(UnidadeMedidaRepository.class);
    }

}
