package com.onfinance.utils;

import com.google.api.client.util.ArrayMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import static java.util.Map.entry;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class XmlQueries {

    private String xml = "/queries/";
    private final Map<String, String> queries = new ArrayMap<>();
    private final String ATTRIBUTE_NAME = "name";
    
    public XmlQueries(String xml) {
        this.xml += xml;
        getQueries();
    }
    
    public String get(String query) {
        return this.queries.get(query);
    }
    
    private void getQueries() {

        String file = getXMLToString();
        int start;
        int end = 0;

        while ((start = file.indexOf("<query", end)) != -1) {

            end = file.indexOf("</query>", start);
            String textXml = file.substring(start + 6, end);
            Map<String, String> query = getQuery(textXml);

            queries.putAll(query);
        }
    }
    
    private Map<String, String> getQuery(String text) {
        
        String queryName = getNameQueryByAttribute(text, ATTRIBUTE_NAME);
        String queryValue = getQueryValue(text);
        
        return Map.ofEntries(entry(queryName, queryValue));
    }

    private String getNameQueryByAttribute(String text, String attribute) {
        attribute = attribute.concat("=").concat("\"");
        int start = text.indexOf(attribute) + attribute.length();
        int end = text.indexOf("\"", start);
        return text.substring(start, end);
    }
    
    private String getQueryValue(String text) {
        int start = text.indexOf(">") + 1;
        int end = text.length();
        return text.substring(start, end).trim();
    }

    private String getXMLToString() {

        InputStream inputStream = XmlQueries.class.getResourceAsStream(xml);
        StringBuilder textBuilder = new StringBuilder();

        try ( Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
            return textBuilder.toString();
        } catch (IOException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} {3}/{4}", new Object[]{LocalDateTime.now(), "Erro ao ler o arquivo", xml, ex});
        }

        return null;
    }

}
