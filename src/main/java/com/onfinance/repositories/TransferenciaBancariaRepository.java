package com.onfinance.repositories;

import com.onfinance.entities.TransferenciaBancariaEntity;
import org.hibernate.Session;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class TransferenciaBancariaRepository extends Repository<Integer, TransferenciaBancariaEntity> {

    public TransferenciaBancariaRepository() {
        super();
    }
    
    public TransferenciaBancariaRepository(Session session) {
        super(session);
    }

}