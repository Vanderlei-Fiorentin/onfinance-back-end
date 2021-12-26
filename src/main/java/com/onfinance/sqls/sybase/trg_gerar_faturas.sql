/* 
 * Trigger que gera fatura para cada parcela cadastrada no banco
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 13 de mar. de 2021
 */

CREATE TRIGGER "TRG_GERAR_FATURAS" AFTER INSERT ORDER 1 ON "financeiro"."PARCELAS_LANCTO_CONTABIL" REFERENCING NEW AS NEW_PARCELA FOR EACH ROW
BEGIN

    DECLARE vVencimento DATE;
    DECLARE vDataLancamento DATE;
    DECLARE vFatura INTEGER;
    DECLARE vPagamento INTEGER;
    DECLARE vFechamentoFatura INTEGER;
    DECLARE vDiaVencimentoFatura INTEGER;
    DECLARE vLimiteUtilizado NUMERIC(12,2);
    DECLARE vValorFatura NUMERIC(12,2);
    DECLARE vTipoPagamento CHAR(1);
    DECLARE vEmpresa INTEGER;
    DECLARE vCartao INTEGER;
    DECLARE vConta INTEGER;
	
    SET vVencimento = NEW_PARCELA.VENCIMENTO;

    SELECT L.TIPO_PAGTO, L.DT_LANCTO, L.ID_EMPRESA, L.ID_CARTAO, L.ID_CONTA
      INTO vTipoPagamento, vDataLancamento, vEmpresa, vCartao, vConta
      FROM LANCTOS_CONTABEIS L
     WHERE L.ID_LANCTO = NEW_PARCELA.ID_LANCTO;
	
    IF(vCartao IS NULL AND vConta IS NOT NULL) THEN

        SELECT COALESCE(MAX(F.ID_FATURA), 0) + 1 
          INTO vFatura
          FROM FATURAS AS F;        

        IF(vTipoPagamento IN('A','T')) THEN      
            -- Insere a fatura      
            INSERT INTO FATURAS VALUES(vFatura, vEmpresa, vVencimento, NEW_PARCELA.VALOR);
            -- Atualiza a parcela com os dados da fatura
            UPDATE PARCELAS_LANCTO_CONTABIL SET ID_FATURA = vFatura WHERE ID_PARCELA = NEW_PARCELA.ID_PARCELA;
            -- Insere o pagamento para gerar extrato
            SELECT COALESCE(MAX(ID_PAGAMENTO), 0) + 1
              INTO vPagamento
              FROM PAGAMENTOS;
            INSERT INTO PAGAMENTOS VALUES(vPagamento, vFatura, NULL, vConta, NEW_PARCELA.VALOR, 0, 0, 0, vVencimento, 'T');
        ELSE
            INSERT INTO FATURAS VALUES(vFatura, vEmpresa, vVencimento, NEW_PARCELA.VALOR);
            -- Atualiza a parcela com os dados da fatura
            UPDATE PARCELAS_LANCTO_CONTABIL SET ID_FATURA = vFatura WHERE ID_PARCELA = NEW_PARCELA.ID_PARCELA;
        END IF;

    ELSE
        -- Busca os dados do cartão de crédito
        SELECT C.FECHAMENTO, C.DIA_VENCTO, C.LIMITE_UTILIZADO, B.ID_EMPRESA
          INTO vFechamentoFatura, vDiaVencimentoFatura, vLimiteUtilizado, vEmpresa
          FROM CARTOES_CREDITO AS C
               INNER JOIN CONTAS_CORRENTE AS CC ON(CC.ID_CONTA = C.ID_CONTA)
               INNER JOIN AGENCIAS AS A ON(A.ID_AGENCIA = CC.ID_AGENCIA)
               INNER JOIN BANCOS AS B ON(B.ID_BANCO = A.ID_BANCO)
         WHERE C.ID_CARTAO = vCartao;

        -- Busca os dados da fatura, caso ela exista
        SELECT FIRST F.ID_FATURA, F.VALOR 
          INTO vFatura, vValorFatura
          FROM FATURAS AS F 
         WHERE F.VENCIMENTO = vVencimento
           AND EXISTS(SELECT 1 
                        FROM PARCELAS_LANCTO_CONTABIL P
                             INNER JOIN LANCTOS_CONTABEIS L ON L.ID_LANCTO = P.ID_LANCTO
                       WHERE P.ID_FATURA = F.ID_FATURA
                         AND L.ID_CARTAO = vCartao)
           AND NOT EXISTS(SELECT 1
                            FROM PAGAMENTOS P
                           WHERE P.ID_FATURA = F.ID_FATURA
                             AND P.DT_PAGTO IS NOT NULL)
         ORDER BY F.VENCIMENTO;

        -- Caso já exista uma fatura em aberto, atualiza o valor da fatura
        IF(vFatura > 0) THEN	
				
            UPDATE FATURAS 
               SET FATURAS.VALOR = (vValorFatura + NEW_PARCELA.VALOR) 
             WHERE FATURAS.ID_FATURA = vFatura;            
            
            UPDATE PARCELAS_LANCTO_CONTABIL 
               SET ID_FATURA = vFatura 
             WHERE ID_PARCELA = NEW_PARCELA.ID_PARCELA;

        ELSE
            -- Caso não exista uma fatura em aberto, insere nova fatura
            SELECT COALESCE(MAX(F.ID_FATURA), 0) + 1 
              INTO vFatura
              FROM FATURAS AS F;                       

            INSERT INTO FATURAS VALUES(vFatura, vEmpresa, vVencimento, NEW_PARCELA.VALOR);

            UPDATE PARCELAS_LANCTO_CONTABIL 
               SET ID_FATURA = vFatura 
             WHERE ID_PARCELA = NEW_PARCELA.ID_PARCELA;

        END IF;		

        UPDATE CARTOES_CREDITO 
           SET CARTOES_CREDITO.LIMITE_UTILIZADO = (vLimiteUtilizado + NEW_PARCELA.VALOR) 
         WHERE CARTOES_CREDITO.ID_CARTAO = vCartao;

    END IF;
    
END;