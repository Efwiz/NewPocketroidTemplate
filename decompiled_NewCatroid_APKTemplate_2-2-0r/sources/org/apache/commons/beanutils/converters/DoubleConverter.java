package org.apache.commons.beanutils.converters;

/* JADX INFO: loaded from: classes12.dex */
public final class DoubleConverter extends NumberConverter {
    public DoubleConverter() {
        super(true);
    }

    public DoubleConverter(Object obj) {
        super(true, obj);
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected Class<Double> getDefaultType() {
        return Double.class;
    }
}
