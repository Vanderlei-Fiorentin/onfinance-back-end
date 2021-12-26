/* 
 * Trigger que atualiza o vencimento das faturas quando alterado o vencimento da parcela
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 26 de dez. de 2021
 */

CREATE OR REPLACE FUNCTION ATUALIZAR_FATURAS() 
RETURNS TRIGGER AS $$
BEGIN  
    -- Atualizar vencimento das parcelas das faturas
    UPDATE FATURAS F
       SET VENCIMENTO = NEW.VENCIMENTO
     WHERE F.ID_FATURA = NEW.ID_FATURA
       AND NOT EXISTS(SELECT 1
                        FROM PAGAMENTOS PG
                       WHERE PG.ID_FATURA = F.ID_FATURA
                         AND PG.DT_PAGTO IS NOT NULL);

    RETURN NEW;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER TGR_ATUALIZAR_FATURAS AFTER UPDATE ON PARCELAS_LANCTO_CONTABIL
FOR EACH ROW 
WHEN (OLD.VENCIMENTO IS DISTINCT FROM NEW.VENCIMENTO)
EXECUTE PROCEDURE ATUALIZAR_FATURAS();