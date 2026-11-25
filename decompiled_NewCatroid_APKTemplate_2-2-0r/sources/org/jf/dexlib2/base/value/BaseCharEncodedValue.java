package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.CharEncodedValue;
import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseCharEncodedValue implements CharEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 3;
    }

    @Override // org.jf.dexlib2.iface.value.CharEncodedValue
    public int hashCode() {
        return getValue();
    }

    @Override // org.jf.dexlib2.iface.value.CharEncodedValue
    public boolean equals(Object obj) {
        return (obj instanceof CharEncodedValue) && getValue() == ((CharEncodedValue) obj).getValue();
    }

    @Override // org.jf.dexlib2.iface.value.CharEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : Character.compare(getValue(), ((CharEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
