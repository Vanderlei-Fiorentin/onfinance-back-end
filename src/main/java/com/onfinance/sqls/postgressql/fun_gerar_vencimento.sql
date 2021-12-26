/**
 * Author:  Vanderlei
 * Created: 24 de dez de 2021
 * Função para gerar data de vencimento conforme dia de pagamento ou vencimento do cartão
 */

CREATE OR REPLACE FUNCTION FUN_GERAR_VENCIMENTO(vDataLancto DATE, vInicioVigencia INTEGER, vDiaPagto INTEGER, vCartao INTEGER) 
RETURNS DATE AS $$
    DECLARE vVencimento DATE;
    DECLARE vFechamentoFatura INTEGER;
    DECLARE vDiaVencimentoFatura INTEGER;
BEGIN   
    IF(vCartao IS NULL) THEN
	
        vVencimento := YMD(DATE_PART('year', vDataLancto)::integer, DATE_PART('month', vDataLancto)::integer, vDiaPagto);
        vVencimento := MONTHS(vVencimento, vInicioVigencia);

        -- Evita que o vencimento seja menor que a data do lançamento
        IF(vVencimento < vDataLancto) THEN
            vVencimento := MONTHS(vVencimento, 1);
        END IF;
		
    ELSE
	
        -- Busca os dados do cartão de crédito
        SELECT C.FECHAMENTO, C.DIA_VENCTO
          INTO vFechamentoFatura, vDiaVencimentoFatura
          FROM CARTOES_CREDITO AS C
         WHERE C.ID_CARTAO = vCartao;

        vVencimento := YMD(DATE_PART('year', vDataLancto)::integer, DATE_PART('month', vDataLancto)::integer, vDiaVencimentoFatura);	
        vVencimento := MONTHS(vVencimento, vInicioVigencia);

        -- Evita que o vencimento seja menor que a data do lançamento
        IF(vVencimento < vDataLancto) THEN
            vVencimento := MONTHS(vVencimento, 1);
        END IF;

        -- Evita que o vencimento fique no período de fechamento da fatura
        IF(vDataLancto >= DAYS(vVencimento, - vFechamentoFatura)) THEN 
           vVencimento := MONTHS(vVencimento, 1);
        END IF;
		
    END IF;

    RETURN DATE(vVencimento);
END; $$
LANGUAGE plpgsql;