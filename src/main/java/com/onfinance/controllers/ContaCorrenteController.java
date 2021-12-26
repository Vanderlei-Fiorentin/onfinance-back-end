package com.onfinance.controllers;

import com.onfinance.entities.ContaEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.ContaRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

public class ContaCorrenteController extends Controller<Integer, ContaEntity> {

    public ContaCorrenteController() {
        super(ContaRepository.class);
    }

    public List<ContaEntity> findByStatus(boolean status) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(ContaRepository.class).findByStatus(status);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public void atualizaSaldo(ContaEntity conta, double valor) throws Exception {
        //Busca o saldo atualizado da conta
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            ContaEntity contaSaldoAtualizado = session.get(ContaRepository.class).findById(conta.getId());
            double saldoAnterior = contaSaldoAtualizado.getSaldo();
            double saldoAtual = contaSaldoAtualizado.getSaldo() + valor;
            contaSaldoAtualizado.setSaldoAnterior(saldoAnterior);
            contaSaldoAtualizado.setSaldo(saldoAtual);
            session.get(ContaRepository.class).updateNewTransaction(contaSaldoAtualizado);
        }
    }

}
