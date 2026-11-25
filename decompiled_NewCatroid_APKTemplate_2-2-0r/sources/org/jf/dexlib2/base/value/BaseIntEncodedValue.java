package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.IntEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseIntEncodedValue implements IntEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 4;
    }

    @Override // org.jf.dexlib2.iface.value.IntEncodedValue
    public int hashCode() {
        return getValue();
    }

    @Override // org.jf.dexlib2.iface.value.IntEncodedValue
    public boolean equals(Object obj) {
        return (obj instanceof IntEncodedValue) && getValue() == ((IntEncodedValue) obj).getValue();
    }

    @Override // org.jf.dexlib2.iface.value.IntEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : Integer.compare(getValue(), ((IntEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
