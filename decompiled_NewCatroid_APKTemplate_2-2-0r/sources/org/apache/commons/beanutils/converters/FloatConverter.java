package org.apache.commons.beanutils.converters;

/* JADX INFO: loaded from: classes12.dex */
public final class FloatConverter extends NumberConverter {
    public FloatConverter() {
        super(true);
    }

    public FloatConverter(Object obj) {
        super(true, obj);
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected Class<Float> getDefaultType() {
        return Float.class;
    }
}
