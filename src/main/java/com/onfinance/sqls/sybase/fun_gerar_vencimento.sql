/* 
 * Função para gerar data de vencimento conforme dia de pagamento ou vencimento do cartão
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 13 de mar. de 2021
 */

CREATE FUNCTION "FUN_GERAR_VENCIMENTO" (vDataLancto DATE, vInicioVigencia INTEGER, vDiaPagto INTEGER, vCartao INTEGER) RETURNS DATE
BEGIN
    DECLARE vVencimento DATE;
    DECLARE vFechamentoFatura INTEGER;
    DECLARE vDiaVencimentoFatura INTEGER;

    IF(vCartao IS NULL) THEN
	
        SET vVencimento = YMD(YEAR(vDataLancto), MONTH(vDataLancto), vDiaPagto);
        SET vVencimento = MONTHS(vVencimento, vInicioVigencia);

        -- Evita que o vencimento seja menor que a data do lançamento
        IF(vVencimento < vDataLancto) THEN
                SET vVencimento = MONTHS(vVencimento, 1);
        END IF;
		
    ELSE
	
        -- Busca os dados do cartão de crédito
        SELECT C.FECHAMENTO, C.DIA_VENCTO
          INTO vFechamentoFatura, vDiaVencimentoFatura
          FROM CARTOES_CREDITO AS C
         WHERE C.ID_CARTAO = vCartao;

        SET vVencimento = YMD(YEAR(vDataLancto), MONTH(vDataLancto), vDiaVencimentoFatura);	
        SET vVencimento = MONTHS(vVencimento, vInicioVigencia);

        -- Evita que o vencimento seja menor que a data do lançamento
        IF(vVencimento < vDataLancto) THEN
                SET vVencimento = MONTHS(vVencimento, 1);
        END IF;

        -- Evita que o vencimento fique no período de fechamento da fatura
        IF(vDataLancto >= DAYS(vVencimento, - vFechamentoFatura)) THEN 
           SET vVencimento = MONTHS(vVencimento, 1);
        END IF;
		
    END IF;

    RETURN vVencimento;
END;