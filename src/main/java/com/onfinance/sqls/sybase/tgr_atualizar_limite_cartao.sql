/* 
 * Trigger que atualiza o limite do cartão quando efetuado o pagamento da fatura
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 13 de mar. de 2021
 */

CREATE TRIGGER "TGR_ATUALIZAR_LIMITE_CARTAO" AFTER INSERT ORDER 2 ON "financeiro"."PAGAMENTOS"  
REFERENCING NEW AS NEW_PAGTO
FOR EACH ROW WHEN(NEW_PAGTO.DT_PAGTO IS NOT NULL AND NEW_PAGTO.ID_CARTAO IS NULL) 
BEGIN
	
    DECLARE vLimiteUtilizado NUMERIC(12,2);
    DECLARE vValorFatura NUMERIC(12,2);
    DECLARE vDescricao VARCHAR(50);
    DECLARE vCartao INTEGER;

    SELECT FIRST L.ID_CARTAO 
      INTO vCartao
      FROM LANCTOS_CONTABEIS L
           INNER JOIN PARCELAS_LANCTO_CONTABIL P ON P.ID_LANCTO = L.ID_LANCTO
     WHERE P.ID_FATURA = NEW_PAGTO.ID_FATURA
     ORDER BY L.ID_CARTAO;

    IF(vCartao IS NOT NULL) THEN

        -- Busca os dados do cartão de crédito
        SELECT C.LIMITE_UTILIZADO, STRING('Fatura cartão final ', RIGHT(C.NUMERO, 4), ' ', B.NOME)
          INTO vLimiteUtilizado, vDescricao
          FROM CARTOES_CREDITO AS C
               INNER JOIN CONTAS_CORRENTE AS CC ON(CC.ID_CONTA = C.ID_CONTA)
               INNER JOIN AGENCIAS AS A ON(A.ID_AGENCIA = CC.ID_AGENCIA)
               INNER JOIN BANCOS AS B ON(B.ID_BANCO = A.ID_BANCO)
         WHERE C.ID_CARTAO = vCartao;	
	
        SET vValorFatura = (NEW_PAGTO.VALOR + NEW_PAGTO.JUROS + NEW_PAGTO.MULTA) - NEW_PAGTO.DESCONTO;
        -- Libera no limite de crédito o valor pago 
        UPDATE CARTOES_CREDITO SET CARTOES_CREDITO.LIMITE_UTILIZADO = (vLimiteUtilizado - vValorFatura) WHERE CARTOES_CREDITO.ID_CARTAO = vCartao;    
        
    END IF;    
END;