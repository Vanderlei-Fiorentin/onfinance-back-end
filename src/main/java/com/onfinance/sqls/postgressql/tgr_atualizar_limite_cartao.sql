/* 
 * Trigger que atualiza o limite do cartão quando efetuado o pagamento da fatura
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 26 de dez. de 2021
 */

CREATE FUNCTION ATUALIZAR_LIMITE_CARTAO() 
RETURNS TRIGGER AS $$
    DECLARE vLimiteUtilizado NUMERIC(12,2);
    DECLARE vDescricao VARCHAR(50);
    DECLARE vCartao INTEGER;
BEGIN

    SELECT L.ID_CARTAO 
      INTO vCartao
      FROM LANCTOS_CONTABEIS AS L
           INNER JOIN PARCELAS_LANCTO_CONTABIL P ON P.ID_LANCTO = L.ID_LANCTO
     WHERE P.ID_FATURA = NEW.ID_FATURA
     ORDER BY L.ID_CARTAO
	 LIMIT 1;

    IF(vCartao IS NOT NULL) THEN

        -- Busca os dados do cartão de crédito
        SELECT C.LIMITE_UTILIZADO, CONCAT('Fatura cartão final ', RIGHT(C.NUMERO, 4), ' ', B.NOME)
          INTO vLimiteUtilizado, vDescricao
          FROM CARTOES_CREDITO AS C
               INNER JOIN CONTAS_CORRENTE AS CC ON(CC.ID_CONTA = C.ID_CONTA)
               INNER JOIN AGENCIAS AS A ON(A.ID_AGENCIA = CC.ID_AGENCIA)
               INNER JOIN BANCOS AS B ON(B.ID_BANCO = A.ID_BANCO)
         WHERE C.ID_CARTAO = vCartao;	
	
        -- Libera no limite de crédito o valor pago 
        UPDATE CARTOES_CREDITO SET LIMITE_UTILIZADO = (vLimiteUtilizado - NEW.VALOR) WHERE ID_CARTAO = vCartao;    
        
    END IF;  

    RETURN NEW;
END; $$
LANGUAGE plpgsql;

CREATE TRIGGER TGR_ATUALIZAR_LIMITE_CARTAO AFTER INSERT ON PAGAMENTOS
FOR EACH ROW 
WHEN (NEW.DT_PAGTO IS NOT NULL AND NEW.ID_CARTAO IS NULL)
EXECUTE PROCEDURE ATUALIZAR_LIMITE_CARTAO();