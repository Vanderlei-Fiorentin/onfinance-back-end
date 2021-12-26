package com.onfinance.controllers;

import com.onfinance.entities.ProdutoEntity;
import com.onfinance.repositories.ProdutoRepository;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class ProdutoController extends Controller<Integer, ProdutoEntity> {

    public ProdutoController() {
        super(ProdutoRepository.class);
    }

}
