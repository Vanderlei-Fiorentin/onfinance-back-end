/* 
 * Trigger que atualiza os dados do lançamento contábil
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 13 de mar. de 2021
 */

CREATE TRIGGER "TGR_ATUALIZAR_LANCTO" BEFORE UPDATE ORDER 1 ON "financeiro"."LANCTOS_CONTABEIS" 
REFERENCING OLD AS OLD_LANCTO NEW AS NEW_LANCTO
FOR EACH ROW
BEGIN
    DECLARE vExisteFaturaPaga BIT;
    DECLARE vRegerarFaturas BIT;
    DECLARE vVencimento DATE;
    DECLARE vParcela INTEGER;
    DECLARE vValorParcela NUMERIC(12,2);   
	
    SET vExisteFaturaPaga = IF EXISTS(SELECT 1
                                        FROM PARCELAS_LANCTO_CONTABIL P 
                                             INNER JOIN FATURAS F ON F.ID_FATURA = P.ID_FATURA
                                             INNER JOIN PAGAMENTOS PG  ON PG.ID_FATURA = F.ID_FATURA 
                                       WHERE P.ID_LANCTO = NEW_LANCTO.ID_LANCTO
                                         AND PG.DT_PAGTO IS NOT NULL) THEN 1 ELSE 0 END IF;

    -- Valida se há alterações que impactam nas faturas
    SET vRegerarFaturas = IF OLD_LANCTO.ID_CARTAO <> NEW_LANCTO.ID_CARTAO THEN 1 ELSE 0 END IF;
    SET vRegerarFaturas = IF vRegerarFaturas = 1 OR (OLD_LANCTO.ID_CONTA <> NEW_LANCTO.ID_CONTA) THEN 1 ELSE 0 END IF;
    SET vRegerarFaturas = IF vRegerarFaturas = 1 OR (OLD_LANCTO.VALOR <> NEW_LANCTO.VALOR) THEN 1 ELSE 0 END IF;
    SET vRegerarFaturas = IF vRegerarFaturas = 1 OR (OLD_LANCTO.JUROS <> NEW_LANCTO.JUROS) THEN 1 ELSE 0 END IF;
    SET vRegerarFaturas = IF vRegerarFaturas = 1 OR (OLD_LANCTO.VALOR_ENTRADA <> NEW_LANCTO.VALOR_ENTRADA) THEN 1 ELSE 0 END IF;
    SET vRegerarFaturas = IF vRegerarFaturas = 1 OR (OLD_LANCTO.PARCELAS <> NEW_LANCTO.PARCELAS) THEN 1 ELSE 0 END IF;
    SET vRegerarFaturas = IF vRegerarFaturas = 1 OR (OLD_LANCTO.DT_LANCTO <> NEW_LANCTO.DT_LANCTO) THEN 1 ELSE 0 END IF;
    SET vRegerarFaturas = IF vRegerarFaturas = 1 OR (OLD_LANCTO.DIA_PAGTO <> NEW_LANCTO.DIA_PAGTO) THEN 1 ELSE 0 END IF;
    SET vRegerarFaturas = IF vRegerarFaturas = 1 OR (OLD_LANCTO.TIPO_PAGTO <> NEW_LANCTO.TIPO_PAGTO) THEN 1 ELSE 0 END IF;

    IF(vExisteFaturaPaga = 1 AND vRegerarFaturas = 1) THEN 
        RAISERROR 99999 "Lançamentos com parcelas pagas não podem ser alterados!";
    ELSE IF(vRegerarFaturas = 1) THEN
        SET vVencimento = NULL;
        SET vParcela = 1;
        SET vValorParcela = 0;  
        SET vValorParcela = (NEW_LANCTO.VALOR + NEW_LANCTO.JUROS - NEW_LANCTO.VALOR_ENTRADA) / NEW_LANCTO.PARCELAS;

        DELETE PARCELAS_LANCTO_CONTABIL WHERE ID_LANCTO = NEW_LANCTO.ID_LANCTO;		

        IF(NEW_LANCTO.ID_CARTAO IS NULL) THEN
            SET vVencimento = NEW_LANCTO.DT_LANCTO;
        ELSE
            SET vVencimento = FUN_GERAR_VENCIMENTO(NEW_LANCTO.DT_LANCTO, NEW_LANCTO.INICIO_VIGENCIA, NEW_LANCTO.DIA_PAGTO, NEW_LANCTO.ID_CARTAO);
        END IF;

        -- Necessário para caso o lançamento não for no cartão
        SET vVencimento = FUN_GERAR_VENCIMENTO(NEW_LANCTO.DT_LANCTO, NEW_LANCTO.INICIO_VIGENCIA, NEW_LANCTO.DIA_PAGTO, NEW_LANCTO.ID_CARTAO); 
        -- Loop para gravar as parcelas do lançamento
        WHILE vParcela <= NEW_LANCTO.PARCELAS LOOP         
            INSERT INTO PARCELAS_LANCTO_CONTABIL VALUES(NULL, NEW_LANCTO.ID_LANCTO, NULL, vVencimento, vParcela, vValorParcela);            
            SET vParcela = vParcela + 1;
            SET vVencimento = MONTHS(vVencimento, 1);
        END LOOP;
    END IF END IF;

    IF UPDATE(ID_EMPRESA) THEN
        UPDATE FATURAS F
           SET F.ID_EMPRESA = NEW_LANCTO.ID_EMPRESA
          FROM PARCELAS_LANCTO_CONTABIL P
         WHERE P.ID_FATURA = F.ID_FATURA
           AND P.ID_LANCTO = NEW_LANCTO.ID_LANCTO;
    END IF;
	
END;