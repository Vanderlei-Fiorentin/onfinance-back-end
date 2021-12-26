package com.onfinance.controllers;

import com.onfinance.entities.PermissaoEntity;
import com.onfinance.repositories.PermissaoRepository;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class PermissaoController extends Controller<Integer, PermissaoEntity> {

    public PermissaoController() {
        super(PermissaoRepository.class);
    }

}
