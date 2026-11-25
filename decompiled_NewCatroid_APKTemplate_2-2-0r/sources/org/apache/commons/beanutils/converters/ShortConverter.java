package org.apache.commons.beanutils.converters;

/* JADX INFO: loaded from: classes12.dex */
public final class ShortConverter extends NumberConverter {
    public ShortConverter() {
        super(false);
    }

    public ShortConverter(Object obj) {
        super(false, obj);
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected Class<Short> getDefaultType() {
        return Short.class;
    }
}
