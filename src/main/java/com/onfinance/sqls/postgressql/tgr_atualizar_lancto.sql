/* 
 * Trigger que atualiza os dados do lançamento contábil
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 26 de dez. de 2021
 */

CREATE FUNCTION ATUALIZAR_LANCTO() 
RETURNS TRIGGER AS $$
    DECLARE vExisteFaturaPaga BOOLEAN;
    DECLARE vRegerarFaturas BOOLEAN;
    DECLARE vVencimento DATE;
    DECLARE vParcela INTEGER;
    DECLARE vValorParcela NUMERIC(12,2);
BEGIN        
	
    vExisteFaturaPaga := (SELECT COUNT(*)
                            FROM PARCELAS_LANCTO_CONTABIL P 
                                 INNER JOIN FATURAS F ON F.ID_FATURA = P.ID_FATURA
                                 INNER JOIN PAGAMENTOS PG ON PG.ID_FATURA = F.ID_FATURA 
                           WHERE P.ID_LANCTO = NEW.ID_LANCTO
                             AND PG.DT_PAGTO IS NOT NULL);

    -- Valida se há alterações que impactam nas faturas
    vRegerarFaturas := (OLD.ID_CARTAO <> NEW.ID_CARTAO);
    vRegerarFaturas := (vRegerarFaturas OR (OLD.ID_CONTA <> NEW.ID_CONTA));
    vRegerarFaturas := (vRegerarFaturas OR (OLD.VALOR <> NEW.VALOR));
    vRegerarFaturas := (vRegerarFaturas OR (OLD.JUROS <> NEW.JUROS));
    vRegerarFaturas := (vRegerarFaturas OR (OLD.VALOR_ENTRADA <> NEW.VALOR_ENTRADA));
    vRegerarFaturas := (vRegerarFaturas OR (OLD.PARCELAS <> NEW.PARCELAS));
    vRegerarFaturas := (vRegerarFaturas OR (OLD.DT_LANCTO <> NEW.DT_LANCTO));
    vRegerarFaturas := (vRegerarFaturas OR (OLD.DIA_PAGTO <> NEW.DIA_PAGTO));
    vRegerarFaturas := (vRegerarFaturas OR (OLD.TIPO_PAGTO <> NEW.TIPO_PAGTO));

    IF(vExisteFaturaPaga AND vRegerarFaturas) THEN 
        RAISE EXCEPTION 'Lançamentos com parcelas pagas não podem ser alterados!';
    ELSE IF(vRegerarFaturas) THEN
        vVencimento := NULL;
        vParcela := 1;
        vValorParcela := 0;  
        vValorParcela := (NEW.VALOR + NEW.JUROS - NEW.VALOR_ENTRADA) / NEW.PARCELAS;

        DELETE FROM PARCELAS_LANCTO_CONTABIL WHERE ID_LANCTO = NEW.ID_LANCTO;		

        IF(NEW.ID_CARTAO IS NULL) THEN
            vVencimento := NEW.DT_LANCTO;
        ELSE
            vVencimento := FUN_GERAR_VENCIMENTO(NEW.DT_LANCTO, NEW.INICIO_VIGENCIA, NEW.DIA_PAGTO, NEW.ID_CARTAO);
        END IF;

        -- Necessário para caso o lançamento não for no cartão
        vVencimento := FUN_GERAR_VENCIMENTO(NEW.DT_LANCTO, NEW.INICIO_VIGENCIA, NEW.DIA_PAGTO, NEW.ID_CARTAO); 
        -- Loop para gravar as parcelas do lançamento
        WHILE vParcela <= NEW.PARCELAS LOOP         
            INSERT INTO PARCELAS_LANCTO_CONTABIL VALUES(DEFAULT, NEW.ID_LANCTO, NULL, vVencimento, vParcela, vValorParcela);            
            vParcela := vParcela + 1;
            vVencimento := MONTHS(vVencimento, 1);
        END LOOP;
    END IF; END IF;

    IF UPDATE(ID_EMPRESA) THEN
        UPDATE FATURAS F
           SET F.ID_EMPRESA = NEW.ID_EMPRESA
          FROM PARCELAS_LANCTO_CONTABIL P
         WHERE P.ID_FATURA = F.ID_FATURA
           AND P.ID_LANCTO = NEW.ID_LANCTO;
    END IF;

    RETURN NEW;
	
END; $$
LANGUAGE plpgsql;

CREATE TRIGGER TGR_ATUALIZAR_LANCTO AFTER UPDATE ON LANCTOS_CONTABEIS
FOR EACH ROW 
EXECUTE PROCEDURE ATUALIZAR_LANCTO();