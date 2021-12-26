package com.onfinance.controllers;

import com.onfinance.entities.EmpresaEntity;
import com.onfinance.repositories.EmpresaRepository;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class EmpresaController extends Controller<Integer, EmpresaEntity> {

    public EmpresaController() {
        super(EmpresaRepository.class);
    }

}
