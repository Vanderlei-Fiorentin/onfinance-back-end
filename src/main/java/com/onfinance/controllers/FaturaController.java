package com.onfinance.controllers;

import com.onfinance.entities.FaturaEntity;
import com.onfinance.entities.PagamentoEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.FaturaRepository;
import com.onfinance.repositories.PagamentoRepository;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class FaturaController extends Controller<Integer, FaturaEntity> {

    public FaturaController() {
        super(FaturaRepository.class);
    }

    public List<PagamentoEntity> findFaturasByFiltro(int empresa, LocalDate dataInicial, LocalDate dataFinal, String situacao,
            String tipoLancto, List<String> tiposPagto, int usuario) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(PagamentoRepository.class).findPagamentosByFiltro(dataInicial, dataFinal, situacao, tipoLancto, tiposPagto, empresa, usuario);
        }
    }

}
