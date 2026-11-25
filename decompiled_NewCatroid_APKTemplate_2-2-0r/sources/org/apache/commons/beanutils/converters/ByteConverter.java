package org.apache.commons.beanutils.converters;

/* JADX INFO: loaded from: classes12.dex */
public final class ByteConverter extends NumberConverter {
    public ByteConverter() {
        super(false);
    }

    public ByteConverter(Object obj) {
        super(false, obj);
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected Class<Byte> getDefaultType() {
        return Byte.class;
    }
}
