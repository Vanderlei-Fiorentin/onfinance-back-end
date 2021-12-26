/* 
 * Trigger que gera as parcelas de um lançamento contábil
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 26 de dez. de 2021
 */

CREATE OR REPLACE FUNCTION GERAR_PARCELA()
RETURNS TRIGGER AS $$
    DECLARE vVencimento DATE;
    DECLARE vParcela INTEGER;
    DECLARE vValorParcela NUMERIC(12,2);
BEGIN
    vVencimento := NULL;
    vParcela := 1;
    vValorParcela := 0;  
    vValorParcela := (NEW.VALOR + NEW.JUROS - NEW.VALOR_ENTRADA) / NEW.PARCELAS;

    IF(NEW.ID_CARTAO IS NULL) THEN
        vVencimento := NEW.DT_LANCTO;
    ELSE
        vVencimento := FUN_GERAR_VENCIMENTO(NEW.DT_LANCTO, NEW.INICIO_VIGENCIA, NEW.DIA_PAGTO, NEW.ID_CARTAO);
    END IF;

    IF(NEW.TIPO_PAGTO IN('A','T')) THEN        
        INSERT INTO PARCELAS_LANCTO_CONTABIL VALUES(DEFAULT, NEW.ID_LANCTO, NULL, vVencimento, 1, vValorParcela);
    ELSE  
        -- Necessário para caso o lançamento não for no cartão
        vVencimento := FUN_GERAR_VENCIMENTO(NEW.DT_LANCTO, NEW.INICIO_VIGENCIA, NEW.DIA_PAGTO, NEW.ID_CARTAO); 
        -- Loop para gravar as parcelas do lançamento
        WHILE vParcela <= NEW.PARCELAS LOOP         
            INSERT INTO PARCELAS_LANCTO_CONTABIL VALUES(DEFAULT, NEW.ID_LANCTO, NULL, vVencimento, vParcela, vValorParcela);            
            vParcela := vParcela + 1;
            vVencimento := MONTHS(vVencimento, 1);
        END LOOP;
    END IF;

    RETURN NEW;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER TRG_GERAR_PARCELAS AFTER INSERT ON LANCTOS_CONTABEIS 
FOR EACH ROW
EXECUTE PROCEDURE GERAR_PARCELA();