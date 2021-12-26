/**
 * Author:  Vanderlei Fiorentin
 * Created: 24 de dez de 2021
 * Monta uma data com base nos parâmetros recebidos
 */

CREATE FUNCTION YMD(vAno INTEGER, vMes INTEGER, vDia INTEGER) 
RETURNS DATE AS $$
	DECLARE vData VARCHAR;
BEGIN
    vData := CONCAT(vAno, '-', vMes, '-', vDia);
    RETURN DATE(vData);
END; $$
LANGUAGE plpgsql;

