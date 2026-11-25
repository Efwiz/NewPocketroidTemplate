package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.ByteEncodedValue;
import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseByteEncodedValue implements ByteEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 0;
    }

    @Override // org.jf.dexlib2.iface.value.ByteEncodedValue
    public int hashCode() {
        return getValue();
    }

    @Override // org.jf.dexlib2.iface.value.ByteEncodedValue
    public boolean equals(Object obj) {
        return (obj instanceof ByteEncodedValue) && getValue() == ((ByteEncodedValue) obj).getValue();
    }

    @Override // org.jf.dexlib2.iface.value.ByteEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : Integer.compare(getValue(), ((ByteEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
