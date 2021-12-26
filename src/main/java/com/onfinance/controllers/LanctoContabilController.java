package com.onfinance.controllers;

import com.onfinance.hibernate.HibernateSessionFactory;
import com.onfinance.entities.DocumentoLanctoContabilEntity;
import com.onfinance.entities.EventoLanctoEntradaEntity;
import com.onfinance.dtos.LanctoContabilGeralDto;
import com.onfinance.entities.LanctoContabilEntity;
import com.onfinance.entities.ProdutoLanctoSaidaEntity;
import com.onfinance.entities.SaidaLanctoEntradaEntity;
import com.onfinance.entities.TransferenciaBancariaEntity;
import com.onfinance.entities.TransferenciaBancariaID;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.LanctoContabilRepository;
import com.onfinance.utils.FileUtil;
import com.onfinance.utils.HashMD5;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.PropertyUtil;
import com.onfinance.utils.ServicePath;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.logging.Level;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class LanctoContabilController extends Controller<Integer, LanctoContabilEntity> {

    private final LanctoContabilRepository lanctoContabilRepository;
    private final String UPLOAD_PATH;
    private final Map<String, String> TIPO_LANCTO = Map.ofEntries(entry("saidas", "S"),
            entry("entradas", "E"), entry("todas", "T"));

    public LanctoContabilController() {
        super(LanctoContabilRepository.class);
        lanctoContabilRepository = new LanctoContabilRepository();
        UPLOAD_PATH = PropertyUtil.get("com.onfinance.files");
    }

    public List<LanctoContabilEntity> findLanctosByFiltro(String tipo, String status, int firstResult, int maxResults) throws Exception {

        LogUtil.getLogger().log(Level.INFO, "{0}?tipo={1}&status={2}", new Object[]{ServicePath.LANCTOS_CONTABEIS, tipo, status});

        tipo = Objects.isNull(tipo) ? "T" : TIPO_LANCTO.get(tipo);
        status = Objects.isNull(status) ? "todas" : status;

        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            List<LanctoContabilEntity> lanctos = session.get(LanctoContabilRepository.class).findLanctosByFiltro(tipo, status, firstResult, maxResults);
            return lanctos;
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public LanctoContabilEntity findByParcela(int idParcela) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            LanctoContabilEntity lancto = session.get(LanctoContabilRepository.class).findByParcela(idParcela);
            return lancto;
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public LanctoContabilEntity findByFatura(int idFatura) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            LanctoContabilEntity lancto = session.get(LanctoContabilRepository.class).findByFatura(idFatura);
            return lancto;
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    @Override
    public void save(LanctoContabilEntity lancto) {
        int id = lanctoContabilRepository.findMaxId() + 1;
        lancto.setId(id);
        lancto.setAtivo(true);
        if (lancto.getTipoPagto().equals(LanctoContabilEntity.AVISTA) || lancto.getTipoPagto().equals(LanctoContabilEntity.TRANSFERENCIA)) {
            lancto.setParcelas(1);
            lancto.setInicioVigencia(0);
            lancto.setJuros(0);
            int diaPagamento = lancto.getDataLancto().getDayOfMonth();
            lancto.setDiaPagamento(diaPagamento);
        } else if (lancto.getTipoPagto().equals(LanctoContabilEntity.FIXO)) {
            lancto.setParcelas(1);
            lancto.setJuros(0);
            lancto.setValorEntrada(0);
        }
        lanctoContabilRepository.save(lancto);
    }

    public LanctoContabilEntity save(LanctoContabilGeralDto lanctoGeral, Optional<List<FormDataBodyPart>> formsData) throws Exception {
        // Abre a sessão
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        // Abre a transação
        session.getTransaction().begin();
        try {
            LanctoContabilEntity lancto = saveLanctoContabilGeral(lanctoGeral, formsData, session);
            session.getTransaction().commit();
            return lancto;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar o lançamento geral!", ex});
            throw new Exception(ex);
        } finally {
            session.close();
        }
    }

    public LanctoContabilEntity update(LanctoContabilGeralDto lanctoGeral, Optional<List<FormDataBodyPart>> formsData) throws Exception {
        // Abre a sessão
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        // Abre a transação
        session.getTransaction().begin();
        // Verifica se existe parcela paga 
        Query query = session.createQuery("SELECT parcela "
                + "                          FROM ParcelaLanctoContabilEntity parcela "
                + "                         WHERE parcela.lanctoContabil.id = :idLancto"
                + "                           AND COALESCE((SELECT SUM(pagamento.valor) FROM PagamentoEntity pagamento WHERE pagamento.fatura = parcela.fatura), 0) > 0");
        query.setParameter("idLancto", lanctoGeral.getLancto().getId());

        OptionalLong.of(query.getFirstResult()).ifPresent(p -> new Exception("Lançamentos com parcelas pagas não podem ser alterados!"));

        try {
            LanctoContabilEntity lancto = updateLanctoContabilGeral(lanctoGeral, formsData, session);
            session.getTransaction().commit();
            return lancto;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao alterar o lançamento geral!", ex});
            throw new Exception(ex);
        } finally {
            session.close();
        }
    }

    public LanctoContabilEntity saveLanctoContabilGeral(LanctoContabilGeralDto lanctoContabilGeral,
            Optional<List<FormDataBodyPart>> formsData, Session session) throws Exception {

        LanctoContabilEntity lanctoContabil = lanctoContabilGeral.getLancto();
        LanctoContabilEntity lanctoContabilEntrada = (LanctoContabilEntity) lanctoContabil.clone();
        List<SaidaLanctoEntradaEntity> saidas = lanctoContabilGeral.getSaidas();
        List<EventoLanctoEntradaEntity> eventos = lanctoContabilGeral.getEventos();
        List<ProdutoLanctoSaidaEntity> produtos = lanctoContabilGeral.getProdutos();
        // Seta os valores padrões
        lanctoContabil.setAtivo(true);

        if (lanctoContabil.getTipoPagto().equals(LanctoContabilEntity.AVISTA) || lanctoContabil.getTipoPagto().equals(LanctoContabilEntity.TRANSFERENCIA)) {
            lanctoContabil.setParcelas(1);
            lanctoContabil.setInicioVigencia(0);
            lanctoContabil.setJuros(0);
            int diaPagamento = lanctoContabil.getDataLancto().getDayOfMonth();
            lanctoContabil.setDiaPagamento(diaPagamento);
        } else if (lanctoContabil.getTipoPagto().equals(LanctoContabilEntity.FIXO)) {
            lanctoContabil.setParcelas(1);
            lanctoContabil.setJuros(0);
            lanctoContabil.setValorEntrada(0);
        }

        try {
            // Grava o lançamento original
            session.persist(lanctoContabil);
            // Salva as listas de lançamentos caso haja
            saveEventoLanctoEntrada(lanctoContabil, eventos, session);
            saveProdutoLanctoSaida(lanctoContabil, produtos, session);
            saveSaidaLanctoEntrada(lanctoContabil, saidas, session);
            saveDocumentos(lanctoContabil, formsData, session);
            // Salva a transferência bancária
            if (lanctoContabil.getTipoLancto().equals(LanctoContabilEntity.SAIDA) && lanctoContabil.getTipoPagto().equals(LanctoContabilEntity.TRANSFERENCIA)) {
                TransferenciaBancariaEntity transferenciaBancaria = new TransferenciaBancariaEntity();
                TransferenciaBancariaID transferenciaBancariaID = new TransferenciaBancariaID();
                LanctoContabilGeralDto lanctoGeralEntrada = new LanctoContabilGeralDto();
                // Altera o lançamento original para seta-lo como lançamento destino na transferência                    
                lanctoContabilEntrada.setTipoLancto(LanctoContabilEntity.ENTRADA);
                lanctoContabilEntrada.setConta(lanctoContabilEntrada.getContaDestino());
                lanctoContabilEntrada.setContaDestino(null);
                lanctoGeralEntrada.setLancto(lanctoContabilEntrada);
                lanctoGeralEntrada.setEventos(eventos);
                // Salva o lançamento de entrada da transferência
                LanctoContabilEntity lanctoDestino = saveLanctoContabilGeral(lanctoGeralEntrada, Optional.ofNullable(null), session);
                transferenciaBancariaID.setLanctoOrigem(lanctoContabil);
                transferenciaBancariaID.setLanctoDestino(lanctoDestino);
                transferenciaBancaria.setTransferenciaBancariaID(transferenciaBancariaID);
                session.persist(transferenciaBancaria);
            }
            return lanctoContabil;
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar os lançamentos!", ex});
            throw new Exception(ex);
        }

    }

    public LanctoContabilEntity updateLanctoContabilGeral(LanctoContabilGeralDto lanctoContabilGeral,
            Optional<List<FormDataBodyPart>> formsData, Session session) throws Exception {

        LanctoContabilEntity lanctoContabil = lanctoContabilGeral.getLancto();
        List<SaidaLanctoEntradaEntity> saidas = lanctoContabilGeral.getSaidas();
        List<EventoLanctoEntradaEntity> eventos = lanctoContabilGeral.getEventos();
        List<ProdutoLanctoSaidaEntity> produtos = lanctoContabilGeral.getProdutos();

        if (lanctoContabil.getTipoPagto().equals(LanctoContabilEntity.AVISTA) || lanctoContabil.getTipoPagto().equals(LanctoContabilEntity.TRANSFERENCIA)) {
            lanctoContabil.setParcelas(1);
            lanctoContabil.setInicioVigencia(0);
            lanctoContabil.setJuros(0);
            int diaPagamento = lanctoContabil.getDataLancto().getDayOfMonth();
            lanctoContabil.setDiaPagamento(diaPagamento);
        } else if (lanctoContabil.getTipoPagto().equals(LanctoContabilEntity.FIXO)) {
            lanctoContabil.setParcelas(1);
            lanctoContabil.setJuros(0);
            lanctoContabil.setValorEntrada(0);
        }

        try {
            // Grava o lançamento original
            session.merge(lanctoContabil);
            // Remove as listas de lançamentos caso haja
            List<EventoLanctoEntradaEntity> eventosGravados = session.createQuery("from EventoLanctoEntradaEntity e where e.eventoLanctoEntradaID.lanctoEntrada = :lancto")
                    .setParameter("lancto", lanctoContabil).list();
            eventosGravados.forEach(evento -> {
                session.remove(evento);
            });

            List<ProdutoLanctoSaidaEntity> produtosGravados = session.createQuery("from ProdutoLanctoSaidaEntity p where p.produtoLanctoSaidaID.lanctoSaida = :lancto")
                    .setParameter("lancto", lanctoContabil).list();
            produtosGravados.forEach(produto -> {
                session.remove(produto);
            });

            List<SaidaLanctoEntradaEntity> saidasGravadas = session.createQuery("from SaidaLanctoEntradaEntity s where s.saidaLanctoEntradaID.lanctoEntrada = :lancto")
                    .setParameter("lancto", lanctoContabil).list();
            saidasGravadas.forEach(saida -> {
                session.remove(saida);
            });

            // Salva os lançamentos caso haja
            saveProdutoLanctoSaida(lanctoContabil, produtos, session);
            saveSaidaLanctoEntrada(lanctoContabil, saidas, session);
            saveEventoLanctoEntrada(lanctoContabil, eventos, session);
            saveDocumentos(lanctoContabil, formsData, session);

            return lanctoContabil;
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar os lançamentos!", ex});
            throw new Exception(ex);
        }

    }

    public void saveDocumentos(LanctoContabilEntity lanctoContabil, Optional<List<FormDataBodyPart>> formsData, Session session) throws Exception {
        if (formsData.isPresent()) {
            try {
                for (FormDataBodyPart part : formsData.get()) {

                    FormDataContentDisposition file = part.getFormDataContentDisposition();

                    String extensao = file.getFileName().substring(file.getFileName().lastIndexOf("."), file.getFileName().length());
                    String fileName = file.getFileName() + LocalDateTime.now();
                    String hash = new HashMD5().hashing(fileName).concat(extensao).toLowerCase();

                    // Salva o documento localmente
                    try {
                        LogUtil.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{LocalDateTime.now(), "Salvando os documentos localmente!"});
                        InputStream fileInputStream = part.getValueAs(InputStream.class);
                        new FileUtil().save(UPLOAD_PATH, fileInputStream, hash);
                    } catch (Exception ex) {
                        LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar os documentos localmente!", ex});
                        throw new Exception(ex);
                    }

                    // Salva o documento no banco
                    DocumentoLanctoContabilEntity documento = new DocumentoLanctoContabilEntity();
                    documento.setLancto(lanctoContabil);
                    documento.setNome(hash);
                    documento.setDocumento(null);
                    documento.setUpload(false);
                    session.persist(documento);
                }
            } catch (IOException | NoSuchAlgorithmException ex) {
                LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar os documentos!", ex});
                throw new Exception(ex);
            }
        }
    }

    public void saveSaidaLanctoEntrada(LanctoContabilEntity lanctoContabil, List<SaidaLanctoEntradaEntity> saidas, Session session) throws Exception {
        if (lanctoContabil.getTipoLancto().equals(LanctoContabilEntity.ENTRADA) && saidas != null) {
            try {
                saidas.forEach(s -> {
                    s.getSaidaLanctoEntradaID().setLanctoEntrada(lanctoContabil);
                    session.persist(s);
                });
            } catch (Exception ex) {
                LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar as saídas!", ex});
                throw new Exception(ex);
            }
        }
    }

    public void saveEventoLanctoEntrada(LanctoContabilEntity lanctoContabil, List<EventoLanctoEntradaEntity> eventos, Session session) throws Exception {
        if (lanctoContabil.getTipoLancto().equals(LanctoContabilEntity.ENTRADA) && eventos != null) {
            try {
                eventos.forEach(e -> {
                    e.getEventoLanctoEntradaID().setLanctoEntrada(lanctoContabil);
                    session.persist(e);
                });
            } catch (Exception ex) {
                LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar os eventos!", ex});
                throw new Exception(ex);
            }
        }
    }

    public void saveProdutoLanctoSaida(LanctoContabilEntity lanctoContabil, List<ProdutoLanctoSaidaEntity> produtos, Session session) throws Exception {
        if (lanctoContabil.getTipoLancto().equals(LanctoContabilEntity.SAIDA) && produtos != null) {
            try {
                produtos.forEach(p -> {
                    p.getProdutoLanctoSaidaID().setLanctoSaida(lanctoContabil);
                    session.persist(p);
                });
            } catch (Exception ex) {
                LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar os produtos!", ex});
                throw new Exception(ex);
            }
        }
    }
}
