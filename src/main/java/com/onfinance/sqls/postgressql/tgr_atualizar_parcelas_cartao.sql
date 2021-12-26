/* 
 * Trigger que atualiza o vencimento das parcelas do cartão quando alterado
 * o dia de vencimento do cartão
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 26 de dez. de 2021
 */

CREATE OR REPLACE FUNCTION ATUALIZAR_PARCELAS_CARTAO()
RETURNS TRIGGER AS $$
BEGIN    
    -- Atualizar vencimento das parcelas das faturas
    UPDATE PARCELAS_LANCTO_CONTABIL P
       SET VENCIMENTO = MONTHS(YMD(DATE_PART('year', F.VENCIMENTO)::integer, DATE_PART('month', F.VENCIMENTO)::integer, NEW.DIA_VENCTO), 1)
      FROM FATURAS F,
           LANCTOS_CONTABEIS L
     WHERE P.ID_FATURA = F.ID_FATURA
       AND P.ID_LANCTO = L.ID_LANCTO
       AND L.ID_CARTAO = NEW.ID_CARTAO
       AND F.VENCIMENTO > MONTHS(YMD(DATE_PART('year', CURRENT_DATE)::integer, DATE_PART('month', CURRENT_DATE)::integer, DATE_PART('day', F.VENCIMENTO)::integer), 1)
       AND NOT EXISTS(SELECT 1
                        FROM PAGAMENTOS PG
                       WHERE PG.ID_FATURA = F.ID_FATURA
                         AND PG.DT_PAGTO IS NOT NULL);

    RETURN NEW;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER TGR_ATUALIZAR_PARCELAS_CARTAO AFTER UPDATE ON CARTOES_CREDITO
FOR EACH ROW
WHEN (OLD.DIA_VENCTO IS DISTINCT FROM NEW.DIA_VENCTO) 
EXECUTE PROCEDURE ATUALIZAR_PARCELAS_CARTAO();