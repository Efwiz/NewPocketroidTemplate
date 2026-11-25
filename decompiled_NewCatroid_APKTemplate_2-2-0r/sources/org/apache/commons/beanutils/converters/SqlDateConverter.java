package org.apache.commons.beanutils.converters;

import java.sql.Date;

/* JADX INFO: loaded from: classes12.dex */
public final class SqlDateConverter extends DateTimeConverter {
    public SqlDateConverter() {
    }

    public SqlDateConverter(Object obj) {
        super(obj);
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected Class<?> getDefaultType() {
        return Date.class;
    }
}
