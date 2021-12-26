/**
 * Author:  Vanderlei Fiorentin
 * Created: 24 de dez de 2021
 * Adiciona/Subtrai anos com base nos parâmetros recebidos
 */

CREATE FUNCTION DAYS(vData DATE, vDias INTEGER) 
RETURNS DATE AS $$
    DECLARE vInterval VARCHAR;
BEGIN
    vInterval := CONCAT(vDias, ' ', 'days');
    RETURN DATE(vData + vInterval::interval);
END; $$
LANGUAGE plpgsql;