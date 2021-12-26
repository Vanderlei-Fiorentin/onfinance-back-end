/* 
 * Trigger que atualiza o vencimento das parcelas do cartão quando alterado
 * o dia de vencimento do cartão
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 13 de mar. de 2021
 */

CREATE TRIGGER "TGR_ATUALIZAR_PARCELAS_CARTAO" AFTER UPDATE ORDER 1 ON "financeiro"."CARTOES_CREDITO" 
REFERENCING OLD AS OLD_CARTAO NEW AS NEW_CARTAO FOR EACH ROW 
BEGIN
    
    IF(OLD_CARTAO.DIA_VENCTO <> NEW_CARTAO.DIA_VENCTO) THEN
        -- Atualizar vencimento das parcelas das faturas
        UPDATE PARCELAS_LANCTO_CONTABIL P
           SET P.VENCIMENTO = MONTHS(YMD(YEAR(F.VENCIMENTO), MONTH(F.VENCIMENTO), NEW_CARTAO.DIA_VENCTO), 1)
          FROM FATURAS F,
               LANCTOS_CONTABEIS L
         WHERE P.ID_FATURA = F.ID_FATURA
           AND P.ID_LANCTO = L.ID_LANCTO
           AND L.ID_CARTAO = NEW_CARTAO.ID_CARTAO
           AND F.VENCIMENTO > MONTHS(YMD(YEAR(TODAY()), MONTH(TODAY()), DAY(F.VENCIMENTO)), 1)
           AND NOT EXISTS(SELECT 1
                            FROM PAGAMENTOS PG
                           WHERE PG.ID_FATURA = F.ID_FATURA
                             AND PG.DT_PAGTO IS NOT NULL);
    END IF;

END;