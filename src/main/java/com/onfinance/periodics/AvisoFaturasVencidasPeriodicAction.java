package com.onfinance.periodics;

import com.onfinance.entities.PagamentoEntity;
import com.onfinance.entities.ParcelaLanctoContabilEntity;
import com.onfinance.entities.PerfilUsuarioEntity;
import com.onfinance.entities.PeriodicActionEntity;
import com.onfinance.entities.UsuarioEntity;
import com.onfinance.repositories.PeriodicActionRepository;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.dtos.EmailDto;
import com.onfinance.repositories.PagamentoRepository;
import com.onfinance.repositories.ParcelaLanctoContabilRepository;
import com.onfinance.repositories.PerfilUsuarioRepository;
import com.onfinance.utils.EmailServiceUtil;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.PropertyUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class AvisoFaturasVencidasPeriodicAction {

    private final String emailFrom;

    public AvisoFaturasVencidasPeriodicAction(PeriodicActionEntity periodic) {
        emailFrom = PropertyUtil.get("email.service.email.from");
    }

    public void execute() throws Exception {

        LogUtil.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{LocalDateTime.now(), "Executando AvisoFaturasVencidasPeriodicAction...."});

        try ( HibernateSession session = HibernateSession.getHibernateSession()) {

            PeriodicActionEntity periodic = session.get(PeriodicActionRepository.class).findByNome("AvisoFaturasVencidasPeriodicAction");

            if (periodic.getUltimaExecucao().toLocalDate().isBefore(LocalDate.now())) {

                LocalDate inicio = LocalDate.now().minusYears(1);
                LocalDate fim = LocalDate.now();

                List<PagamentoEntity> faturas = session.get(PagamentoRepository.class).findPagamentosByFiltro(inicio, fim, "V", "S", List.of("TD"), 0, 0);
                EmailServiceUtil emailService = new EmailServiceUtil();
                String token = (String) emailService.login().body();

                faturas.forEach((fatura) -> {
                    try {

                        ParcelaLanctoContabilEntity parcela = session.get(ParcelaLanctoContabilRepository.class).findByFatura(fatura.getFatura().getId()).get(0);
                        UsuarioEntity usuario = !Objects.isNull(parcela.getLanctoContabil().getConta()) ? parcela.getLanctoContabil().getConta().getUsuario()
                                : parcela.getLanctoContabil().getCartao().getConta().getUsuario();
                        PerfilUsuarioEntity perfil = session.get(PerfilUsuarioRepository.class).findByUsuario(usuario.getUsuario())
                                .orElseThrow(() -> new Exception("Perfil de usuário não localizado na base de dados!"));

                        if (perfil.isAvisoFaturaVencida()) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            String vencimento = fatura.getFatura().getVencimento().format(formatter);
                            String subject = "Fatura ".concat(fatura.getFatura().getEmpresa().getNome()).concat(" - Vencto ").concat(vencimento);
                            String bodyText = message(fatura);
                            EmailDto email = new EmailDto("Onfinance", emailFrom, usuario.getEmail(), subject, bodyText);                            
                            emailService.send(email, token, 5);
                        }

                    } catch (Exception ex) {
                        LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} \n {3}", new Object[]{LocalDateTime.now(), "Erro ao enviar aviso de vencimento do cliente ", fatura.getFatura().getEmpresa().getNome(), ex});
                    }
                });

                // Grava a ultima execução da periodic
                LocalDateTime ultimaExecucao = LocalDateTime.now();
                periodic.setUltimaExecucao(ultimaExecucao);

                session.openTransaction();
                session.get(PeriodicActionRepository.class).update(periodic);
                session.commit();

                LogUtil.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{LocalDateTime.now(), "Finalizado a execução da AvisoFaturasVencidasPeriodicAction...."});
            } else {
                LogUtil.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{LocalDateTime.now(), "AvisoFaturasVencidasPeriodicAction fora do período de execução...."});
            }
        }
    }

    private String message(PagamentoEntity fatura) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String vencimento = fatura.getFatura().getVencimento().format(formatter);
        long dias = ChronoUnit.DAYS.between(fatura.getFatura().getVencimento(), LocalDate.now());
        return "Prezado(a) CLIENTE,"
                .concat("\n \n")
                .concat("Informamos que a fatura " + fatura.getFatura().getEmpresa().getNome() + " com vencimento em " + vencimento + " está em atraso a " + dias + " dias.")
                .concat("\n \n")
                .concat("Valor: R$ " + fatura.getFatura().getValor() + ".")
                .concat("\n \n")
                .concat("Atenciosamente,").concat("\n")
                .concat("Gerenciador Financeiro");
    }

}
