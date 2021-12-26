/* 
 * Trigger que atualiza o valor da fatura quando removido alguma parcela
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 26 de dez. de 2021
 */

CREATE FUNCTION REMOVER_PARCELA_FATURA()
RETURNS TRIGGER AS $$
    DECLARE vCartao INTEGER;
BEGIN	
    SELECT L.ID_CARTAO
      INTO vCartao 
      FROM LANCTOS_CONTABEIS AS L 
     WHERE L.ID_LANCTO = OLD.ID_LANCTO;

    UPDATE FATURAS 
       SET VALOR = VALOR - OLD.VALOR 
     WHERE ID_FATURA = OLD.ID_FATURA;

    IF(vCartao IS NOT NULL) THEN
        UPDATE CARTOES_CREDITO 
           SET LIMITE_UTILIZADO = LIMITE_UTILIZADO - OLD.VALOR 
         WHERE ID_CARTAO = vCartao;
    END IF;

    RETURN NEW;
END; $$
LANGUAGE plpgsql;

CREATE TRIGGER TGR_REMOVER_PARCELA_FATURA AFTER DELETE ON PARCELAS_LANCTO_CONTABIL 
FOR EACH ROW /* WHEN( search_condition ) */
EXECUTE PROCEDURE REMOVER_PARCELA_FATURA();