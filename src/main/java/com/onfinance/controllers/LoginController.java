package com.onfinance.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.onfinance.entities.UsuarioEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.dtos.EmailDto;
import com.onfinance.dtos.JwtDto;
import com.onfinance.dtos.LoginDto;
import com.onfinance.entities.PerfilUsuarioEntity;
import com.onfinance.repositories.PerfilUsuarioRepository;
import com.onfinance.repositories.RoleRepository;
import com.onfinance.repositories.UsuarioRepository;
import com.onfinance.utils.EmailServiceUtil;
import com.onfinance.utils.HashMD5;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.PropertyUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class LoginController {

    public JwtDto login(LoginDto login) throws Exception {

        JwtController jwt = new JwtController();

        try ( HibernateSession session = HibernateSession.getHibernateSession()) {

            PerfilUsuarioEntity perfil = session.get(PerfilUsuarioRepository.class)
                    .findByUsuario(login.getLogin()).orElseThrow(() -> new Exception("Usuário não localizado na base de dados!"));

            String senha = new HashMD5().hashing(login.getPassword()).toLowerCase();
            login.setPassword(senha);

            // Valida a autenticação e gera um token    
            if (isValido(perfil.getUsuario(), login)) {
                List<String> roles = session.get(RoleRepository.class).findByUsuario(perfil.getUsuario());
                JwtDto jwtModel = new JwtDto();
                jwtModel.setJwt(jwt.gerarJwt(perfil.getUsuario(), roles));
                jwtModel.setPerfil(perfil);
                return jwtModel;
            }
        }

        return null;
    }

    public void alterarSenha(String jwtToken, String senha) throws Exception {
        DecodedJWT decode = new JwtController().decodeToken(jwtToken);
        String nomeUsuario = decode.getClaim("Usuario").asString();
        UsuarioEntity usuario;

        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            usuario = session.get(UsuarioRepository.class).findByUsuario(nomeUsuario)
                    .orElseThrow(() -> new Exception("Usuário não encontrado na base de dados!"));
        }

        usuario.setSenha(senha);
        new UsuarioController().alterarSenha(usuario);
    }

    public void redefinirSenha(String email) throws Exception {
        try {
            UsuarioEntity usuario;
            EmailServiceUtil emailService = new EmailServiceUtil();
            String emailFrom = PropertyUtil.get("email.service.email.from");

            try ( HibernateSession session = HibernateSession.getHibernateSession()) {
                usuario = session.get(UsuarioRepository.class).findByEmail(email)
                        .orElseThrow(() -> new Exception("Email não encontrado na base de dados!"));
            }

            List<String> roles = List.of("");
            String token = new JwtController().gerarJwt(usuario, roles);
            String subject = "Redefinição de senha Gerenciador Financeiro";
            String bodyText = message(usuario, token);

            EmailDto emailModel = new EmailDto("Onfinance", emailFrom, email, subject, bodyText);
            String tokenEmail = (String) emailService.login().body();
            emailService.send(emailModel, tokenEmail, 5);

        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} \n {3}", new Object[]{LocalDateTime.now(), "Erro ao redefinir senha!", ex});
            throw new Exception(ex);
        }
    }

    private String message(UsuarioEntity usuario, String token) {
        return "Olá " + usuario.getNome() + ","
                .concat("\n \n")
                .concat("Alguém solicitou alteração nas suas credenciais da conta Gerenciador Financeiro. \n Se foi você, click no link abaixo para redefinir sua senha.")
                .concat("\n\n")
                .concat("Link para redefinir credenciais:")
                .concat("\n")
                .concat(String.format("%s/%s", ServicePath.ONFINANCE_FRONTEND, "#/login/redefinir-senha?token="))
                .concat(token)
                .concat("\n\n")
                .concat("Esse link irá expirar em 60 minutos.")
                .concat("\n \n")
                .concat("Se você não deseja redefinir suas credenciais, apenas ignore essa mensagem e nada será alterado.")
                .concat("\n \n")
                .concat("Atenciosamente,").concat("\n")
                .concat("Onfinance");
    }

    private boolean isValido(UsuarioEntity usuario, LoginDto login) {
        return usuario.getUsuario().equals(login.getLogin())
                && usuario.getSenha().equals(login.getPassword()) && usuario.isAtivo();
    }

}
