package com.onfinance.controllers;

import com.onfinance.UserSession;
import com.onfinance.entities.DocumentoPagamentoEntity;
import com.onfinance.entities.LanctoContabilEntity;
import com.onfinance.entities.PagamentoEntity;
import com.onfinance.entities.UsuarioEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.dtos.ParcelamentoDto;
import com.onfinance.repositories.DocumentoPagamentoRepository;
import com.onfinance.repositories.LanctoContabilRepository;
import com.onfinance.repositories.PagamentoRepository;
import com.onfinance.utils.FileUtil;
import com.onfinance.utils.HashMD5;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.PropertyUtil;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class PagamentoController extends Controller<Integer, PagamentoEntity> {

    private final String uploadPath;

    public PagamentoController() {
        super(PagamentoRepository.class);
        uploadPath = PropertyUtil.get("com.onfinance.files");
    }

    public PagamentoEntity findByFatura(int idFatura) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(PagamentoRepository.class).findByFatura(idFatura);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public void save(PagamentoEntity pagamento, ParcelamentoDto parcelamento, Optional<List<FormDataBodyPart>> formsData) throws Exception {
        UserSession userSession = UserSession.getCurrentInstance();
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            session.openTransaction();
            session.get(PagamentoRepository.class).save(pagamento);
            saveDocumentos(pagamento, formsData, session);
            if (pagamento.getCartao() != null && pagamento.getConta() == null) {
                saveLancamentoCartaoCredito(pagamento, userSession.getUsuario(), session);
            }
            if (pagamento.getTipoPagamento() == 'M') {
                saveLancamentoPagamentoMinimo(pagamento, parcelamento, userSession.getUsuario(), session);
            }
            session.commit();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar o pagamento!", ex});
            throw new Exception(ex);
        }
    }

    public void saveLancamentoCartaoCredito(PagamentoEntity pagamento, UsuarioEntity usuario, HibernateSession session) throws Exception {
        try {
            double valor = pagamento.getValor() + pagamento.getJuros() + pagamento.getMulta();
            LanctoContabilEntity lancto = new LanctoContabilEntity();
            lancto.setId(0);
            lancto.setDescricao("Gerado devido pagamento realizado com cartão de crédito");
            lancto.setUsuario(usuario);
            lancto.setAtivo(true);
            lancto.setEmpresa(pagamento.getFatura().getEmpresa());
            lancto.setDataLancto(pagamento.getDataPagamento());
            lancto.setTipoLancto("S");
            lancto.setTipoPagto("A");
            lancto.setConta(null);
            lancto.setCartao(pagamento.getCartao());
            lancto.setInicioVigencia(0);
            lancto.setValor(valor);
            lancto.setJuros(0.0);
            lancto.setValorEntrada(0.0);
            lancto.setParcelas(1);
            lancto.setDiaPagamento(pagamento.getDataPagamento().getDayOfMonth());
            session.get(LanctoContabilRepository.class).save(lancto);
            session.flush();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar o lançamento contábil!", ex});
            throw new Exception(ex);
        }
    }

    public void saveLancamentoPagamentoMinimo(PagamentoEntity pagamento, ParcelamentoDto parcelamento, UsuarioEntity usuario, HibernateSession session) throws Exception {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            double valor = pagamento.getFatura().getValor() - pagamento.getValor();
            double valorTotal = valor + parcelamento.getJuros() + parcelamento.getMulta();
            String descricao = "Valor à pagar da fatura de " + pagamento.getFatura().getVencimento().format(formatter);
            String tipoPagto = (parcelamento.getParcelas() > 1) ? "P" : "A";
            LanctoContabilEntity lanctoOriginal = session.get(LanctoContabilRepository.class).findByFatura(pagamento.getFatura().getId());
            LanctoContabilEntity lancto = new LanctoContabilEntity();
            lancto.setId(0);
            lancto.setDescricao(descricao);
            lancto.setUsuario(usuario);
            lancto.setAtivo(true);
            lancto.setEmpresa(pagamento.getFatura().getEmpresa());
            lancto.setDataLancto(pagamento.getDataPagamento());
            lancto.setTipoLancto("S");
            lancto.setTipoPagto(tipoPagto);
            lancto.setConta(null);
            lancto.setCartao(lanctoOriginal.getCartao());
            lancto.setInicioVigencia(parcelamento.getVigencia());
            lancto.setValor(valorTotal);
            lancto.setJuros(0.0);
            lancto.setValorEntrada(0.0);
            lancto.setParcelas(parcelamento.getParcelas());
            lancto.setDiaPagamento(pagamento.getDataPagamento().getDayOfMonth());
            session.get(LanctoContabilRepository.class).save(lancto);
            session.flush();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar o lançamento contábil!", ex});
            throw new Exception(ex);
        }
    }

    public void saveDocumentos(PagamentoEntity pagamento, Optional<List<FormDataBodyPart>> formsData, HibernateSession session) throws Exception {
        if (formsData.isPresent()) {
            try {
                for (FormDataBodyPart part : formsData.get()) {
                    // Salva os documentos
                    FormDataContentDisposition file = part.getFormDataContentDisposition();
                    String extensao = file.getFileName().substring(file.getFileName().lastIndexOf("."), file.getFileName().length());
                    String fileName = file.getFileName() + String.valueOf(pagamento.getId());
                    String hash = new HashMD5().hashing(fileName).concat(extensao).toLowerCase();

                    // Salva o documento localmente
                    try {
                        LogUtil.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{LocalDateTime.now(), "Salvando os documentos localmente!"});
                        InputStream fileInputStream = part.getValueAs(InputStream.class);
                        new FileUtil().save(uploadPath, fileInputStream, hash);
                    } catch (Exception ex) {
                        LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar os documentos localmente!", ex});
                        throw new Exception(ex);
                    }

                    // Salva o documento no banco
                    DocumentoPagamentoEntity documento = new DocumentoPagamentoEntity();
                    documento.setPagamento(pagamento);
                    documento.setNome(hash);
                    documento.setDocumento(null);
                    documento.setUpload(false);
                    session.get(DocumentoPagamentoRepository.class).save(documento);
                }
            } catch (IOException | NoSuchAlgorithmException ex) {
                LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar os documentos!", ex});
                throw new Exception(ex);
            }
        }
    }

}
