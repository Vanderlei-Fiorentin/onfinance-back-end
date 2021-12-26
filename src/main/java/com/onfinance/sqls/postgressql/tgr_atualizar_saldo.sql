/* 
 * Trigger que atualiza o saldo da conta corrente quando efetuada movimentação
 * na conta corrente
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 26 de dez. de 2021
 */

CREATE FUNCTION ATUALIZAR_SALDO()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE CONTAS_CORRENTE
       SET SALDO_ANTERIOR = SALDO, 
           SALDO = NEW.SALDO 
     WHERE ID_CONTA = NEW.ID_CONTA;

    RETURN NEW;
END; $$
LANGUAGE plpgsql;

CREATE TRIGGER TGR_ATUALIZAR_SALDO AFTER INSERT ON EXTRATOS_BANCARIOS 
FOR EACH ROW /* WHEN( search_condition ) */
EXECUTE PROCEDURE ATUALIZAR_SALDO();