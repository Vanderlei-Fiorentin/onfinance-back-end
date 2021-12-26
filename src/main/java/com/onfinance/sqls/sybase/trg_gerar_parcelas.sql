/* 
 * Trigger que gera as parcelas de um lançamento contábil
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 13 de mar. de 2021
 */

CREATE TRIGGER "TRG_GERAR_PARCELAS" AFTER INSERT ORDER 1 ON LANCTOS_CONTABEIS REFERENCING NEW AS NEW_LANCTO FOR EACH ROW
BEGIN

    DECLARE vVencimento DATE;
    DECLARE vParcela INTEGER;
    DECLARE vValorParcela NUMERIC(12,2);

    SET vVencimento = NULL;
    SET vParcela = 1;
    SET vValorParcela = 0;  
    SET vValorParcela = (NEW_LANCTO.VALOR + NEW_LANCTO.JUROS - NEW_LANCTO.VALOR_ENTRADA) / NEW_LANCTO.PARCELAS;

    IF(NEW_LANCTO.ID_CARTAO IS NULL) THEN
        SET vVencimento = NEW_LANCTO.DT_LANCTO;
    ELSE
        SET vVencimento = FUN_GERAR_VENCIMENTO(NEW_LANCTO.DT_LANCTO, NEW_LANCTO.INICIO_VIGENCIA, NEW_LANCTO.DIA_PAGTO, NEW_LANCTO.ID_CARTAO);
    END IF;

    IF(NEW_LANCTO.TIPO_PAGTO IN('A','T')) THEN        
        INSERT INTO PARCELAS_LANCTO_CONTABIL VALUES(NULL, NEW_LANCTO.ID_LANCTO, NULL, vVencimento, 1, vValorParcela);
    ELSE  
        -- Necessário para caso o lançamento não for no cartão
        SET vVencimento = FUN_GERAR_VENCIMENTO(NEW_LANCTO.DT_LANCTO, NEW_LANCTO.INICIO_VIGENCIA, NEW_LANCTO.DIA_PAGTO, NEW_LANCTO.ID_CARTAO); 
        -- Loop para gravar as parcelas do lançamento
        WHILE vParcela <= NEW_LANCTO.PARCELAS LOOP         
            INSERT INTO PARCELAS_LANCTO_CONTABIL VALUES(NULL, NEW_LANCTO.ID_LANCTO, NULL, vVencimento, vParcela, vValorParcela);            
            SET vParcela = vParcela + 1;
            SET vVencimento = MONTHS(vVencimento, 1);
        END LOOP;
    END IF;
END;