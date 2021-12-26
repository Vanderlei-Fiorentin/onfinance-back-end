package com.onfinance.controllers;

import com.onfinance.entities.ParcelaLanctoContabilEntity;
import com.onfinance.dtos.RenegociacaoLanctoContabilDto;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.ParcelaLanctoContabilRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class RenegociarLanctoController {

    public void renegociar(RenegociacaoLanctoContabilDto renegociacao) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {

            List<ParcelaLanctoContabilEntity> parcelas = renegociacao.getParcelas();
            double valotTotal = (renegociacao.getMulta() + renegociacao.getJuros()) - (renegociacao.getValorEntrada() + renegociacao.getDesconto());

            for (ParcelaLanctoContabilEntity p : parcelas) {
                valotTotal += p.getValor();
            }

            session.openTransaction();

            if (renegociacao.getTipoPagto().equals("A")) {
                pagamentoAVista(renegociacao, valotTotal, session);
            } else if (renegociacao.getTipoPagto().equals("P")) {
                pagamentoParcelado(renegociacao, valotTotal, session);
            }

            removeParcelas(parcelas, session);
            session.commit();

        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} \n {3}", new Object[]{LocalDateTime.now(), "Erro ao efetuar a renegociação!", ex});
        }
    }

    public void pagamentoAVista(RenegociacaoLanctoContabilDto renegociacao, double valotTotal, HibernateSession session) throws Exception {
        try {
            ParcelaLanctoContabilEntity parcela = new ParcelaLanctoContabilEntity();
            parcela.setLanctoContabil(renegociacao.getParcelas().get(0).getLanctoContabil());
            parcela.setValor(valotTotal);
            parcela.setParcela(1);
            parcela.setVencimento(renegociacao.getVencimento());
            session.get(ParcelaLanctoContabilRepository.class).save(parcela);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} \n {3}", new Object[]{LocalDateTime.now(), "Erro ao gerar parcela da renegociação!", ex});
            throw new Exception(ex);
        }
    }

    public void pagamentoParcelado(RenegociacaoLanctoContabilDto renegociacao, double valotTotal, HibernateSession session) throws Exception {
        double valorParcelas = valotTotal / renegociacao.getQtdParcelas();
        LocalDate vencimento = gerarVencimento(renegociacao);
        try {
            for (int noParcela = 1; noParcela <= renegociacao.getQtdParcelas(); noParcela++) {
                ParcelaLanctoContabilEntity parcela = new ParcelaLanctoContabilEntity();
                parcela.setLanctoContabil(renegociacao.getParcelas().get(0).getLanctoContabil());
                parcela.setValor(valorParcelas);
                parcela.setParcela(noParcela);
                parcela.setVencimento(vencimento);
                session.get(ParcelaLanctoContabilRepository.class).save(parcela);
                vencimento = vencimento.plusMonths(1);
            }
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} \n {3}", new Object[]{LocalDateTime.now(), "Erro ao gerar parcelas da renegociação!", ex});
            throw new Exception(ex);
        }
    }

    public LocalDate gerarVencimento(RenegociacaoLanctoContabilDto renegociacao) {
        LocalDate vencimento = renegociacao.getDataRenegociacao().withDayOfMonth(renegociacao.getDiaPagamento()).plusMonths(renegociacao.getInicioVigencia());
        return (vencimento.isBefore(renegociacao.getDataRenegociacao())) ? vencimento.plusMonths(1) : vencimento;
    }

    public void removeParcelas(List<ParcelaLanctoContabilEntity> parcelas, HibernateSession session) throws Exception {
        try {
            session.get(ParcelaLanctoContabilRepository.class).deleteAll(parcelas);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} \n {3}", new Object[]{LocalDateTime.now(), "Erro ao remover as parcelas!", ex});
            throw new Exception(ex);
        }
    }
}
