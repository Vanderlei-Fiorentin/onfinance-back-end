package com.onfinance.hibernate;

import com.onfinance.utils.PropertyUtil;

/**
 *
 * @author Vanderlei Fiorentin
 */
public final class EnvironmentProd implements Environment {

    public String dialect;
    public String driver;
    public String url;
    public String user;
    public String password;
    public boolean autoCommit;
    public boolean showSql;
    public boolean formatSql;

    public EnvironmentProd() {
        setDialect(PropertyUtil.get("prod.hibernate.dialect"));
        setDriver(PropertyUtil.get("prod.hibernate.connection.driver"));
        setUrl(PropertyUtil.get("prod.hibernate.connection.url"));
        setUser(PropertyUtil.get("prod.hibernate.connection.username"));
        setPassword(PropertyUtil.get("prod.hibernate.connection.password"));
        setAutoCommit(Boolean.valueOf(PropertyUtil.get("prod.hibernate.connection.autocommit")));
        setShowSql(Boolean.valueOf(PropertyUtil.get("prod.show_sql")));
        setFormatSql(Boolean.valueOf(PropertyUtil.get("prod.format_sql")));
    }

    @Override
    public String getDialect() {
        return dialect;
    }

    @Override
    public final void setDialect(String dialect) {
        this.dialect = dialect;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public final void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public final void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public final void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public final void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isShowSql() {
        return showSql;
    }

    @Override
    public final void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    @Override
    public boolean isFormatSql() {
        return formatSql;
    }

    @Override
    public final void setFormatSql(boolean formatSql) {
        this.formatSql = formatSql;
    }

    @Override
    public void setAutoCommit(boolean auto) {
        this.autoCommit = auto;
    }

    @Override
    public boolean isAutoCommit() {
        return autoCommit;
    }

}
