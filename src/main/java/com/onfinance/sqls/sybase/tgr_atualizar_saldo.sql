/* 
 * Trigger que atualiza o saldo da conta corrente quando efetuada movimentação
 * na conta corrente
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 13 de mar. de 2021
 */

CREATE TRIGGER "TGR_ATUALIZAR_SALDO" AFTER INSERT ORDER 1 ON "financeiro"."EXTRATOS_BANCARIOS" REFERENCING NEW AS NEW_EXTRATO FOR EACH ROW /* WHEN( search_condition ) */
BEGIN
    UPDATE CONTAS_CORRENTE SET CONTAS_CORRENTE.SALDO_ANTERIOR = CONTAS_CORRENTE.SALDO, CONTAS_CORRENTE.SALDO = NEW_EXTRATO.SALDO WHERE NEW_EXTRATO.ID_CONTA = CONTAS_CORRENTE.ID_CONTA;
END;