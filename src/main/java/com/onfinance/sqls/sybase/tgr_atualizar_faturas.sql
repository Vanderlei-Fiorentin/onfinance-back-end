/* 
 * Trigger que atualiza o vencimento das faturas quando alterado o vencimento da parcela
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 13 de mar. de 2021
 */

CREATE TRIGGER "TGR_ATUALIZAR_FATURAS" AFTER UPDATE ORDER 1 ON "financeiro"."PARCELAS_LANCTO_CONTABIL" 
REFERENCING OLD AS OLD_PARCELA NEW AS NEW_PARCELA FOR EACH ROW 
BEGIN
    
    IF(OLD_PARCELA.VENCIMENTO <> NEW_PARCELA.VENCIMENTO) THEN
        -- Atualizar vencimento das parcelas das faturas
        UPDATE FATURAS F
           SET F.VENCIMENTO = NEW_PARCELA.VENCIMENTO
         WHERE F.ID_FATURA = NEW_PARCELA.ID_FATURA
           AND NOT EXISTS(SELECT 1
                            FROM PAGAMENTOS PG
                           WHERE PG.ID_FATURA = F.ID_FATURA
                             AND PG.DT_PAGTO IS NOT NULL);
    END IF;

END;