package com.onfinance.controllers;

import com.onfinance.entities.DocumentoPagamentoEntity;
import com.onfinance.repositories.DocumentoPagamentoRepository;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class DocumentoPagamentoController extends Controller<Integer, DocumentoPagamentoEntity> {

    public DocumentoPagamentoController() {
        super(DocumentoPagamentoRepository.class);
    }

}
