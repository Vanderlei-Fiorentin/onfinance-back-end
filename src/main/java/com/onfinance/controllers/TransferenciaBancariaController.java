package com.onfinance.controllers;

import com.onfinance.entities.TransferenciaBancariaEntity;
import com.onfinance.entities.TransferenciaBancariaID;
import com.onfinance.repositories.TransferenciaBancariaRepository;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class TransferenciaBancariaController extends Controller<TransferenciaBancariaID, TransferenciaBancariaEntity> {

    public TransferenciaBancariaController() {
        super(TransferenciaBancariaRepository.class);
    }

}
