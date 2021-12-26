package com.onfinance.controllers;

import com.onfinance.entities.CategoriaProdutoEntity;
import com.onfinance.repositories.CategoriaRepository;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class CategoriaController extends Controller<Integer, CategoriaProdutoEntity> {

    public CategoriaController() {
        super(CategoriaRepository.class);
    }

}
