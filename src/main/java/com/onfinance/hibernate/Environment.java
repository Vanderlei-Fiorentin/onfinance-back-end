
package com.onfinance.hibernate;

/**
 *
 * @author Vanderlei Fiorentin
 */
public interface Environment {
    public void setDialect(String dialect);
    public void setDriver(String driver);
    public void setUrl(String url);
    public void setUser(String user);
    public void setPassword(String password);
    public void setAutoCommit(boolean auto);
    public void setShowSql(boolean show);
    public void setFormatSql(boolean format);
    public String getDialect();
    public String getDriver();
    public String getUrl();
    public String getUser();
    public String getPassword();
    public boolean isAutoCommit();
    public boolean isShowSql();
    public boolean isFormatSql();
}
