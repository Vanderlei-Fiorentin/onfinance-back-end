package com.onfinance.controllers;

import com.onfinance.entities.CartaoCreditoEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.CartaoRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

public class CartaoController extends Controller<Integer, CartaoCreditoEntity> {

    public CartaoController() {
        super(CartaoRepository.class);
    }

    public List<CartaoCreditoEntity> findByStatus(boolean status) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(CartaoRepository.class).findByStatus(status);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: \n {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    /*
    * Ação realizada via Trigger
     */
    public void atualizaLimite(CartaoCreditoEntity cartao, double valor) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            double limiteUtilizado = cartao.getLimiteUtilizado() + valor;
            cartao.setLimiteUtilizado(limiteUtilizado);
            session.get(CartaoRepository.class).updateNewTransaction(cartao);
        }
    }

}
