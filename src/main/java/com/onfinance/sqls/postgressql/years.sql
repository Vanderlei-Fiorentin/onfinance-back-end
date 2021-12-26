/**
 * Author:  Vanderlei Fiorentin
 * Created: 24 de dez de 2021
 * Adiciona/Subtrai anos com base nos parâmetros recebidos
 */

CREATE FUNCTION YEARS(vData DATE, vAnos INTEGER) 
RETURNS DATE AS $$
	DECLARE vInterval VARCHAR;
BEGIN
    vInterval := CONCAT(vAnos, ' ', 'years');
    RETURN DATE(vData + (vInterval::interval));
END; $$
LANGUAGE plpgsql;