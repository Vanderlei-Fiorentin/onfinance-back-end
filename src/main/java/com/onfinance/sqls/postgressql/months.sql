/**
 * Author:  Vanderlei Fiorentin
 * Created: 24 de dez de 2021
 * Adiciona/Subtrai meses com base nos parâmetros recebidos
 */

CREATE FUNCTION MONTHS(vData DATE, vMeses INTEGER) 
RETURNS DATE AS $$
	DECLARE vInterval VARCHAR;
BEGIN
    vInterval := CONCAT(vMeses, ' ', 'months');
    RETURN DATE(vData + (vInterval::interval));
END; $$
LANGUAGE plpgsql;

