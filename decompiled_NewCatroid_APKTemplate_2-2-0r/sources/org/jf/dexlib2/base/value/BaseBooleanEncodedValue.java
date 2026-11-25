package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.BooleanEncodedValue;
import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseBooleanEncodedValue implements BooleanEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 31;
    }

    @Override // org.jf.dexlib2.iface.value.BooleanEncodedValue
    public int hashCode() {
        return getValue() ? 1 : 0;
    }

    @Override // org.jf.dexlib2.iface.value.BooleanEncodedValue
    public boolean equals(Object obj) {
        return (obj instanceof BooleanEncodedValue) && getValue() == ((BooleanEncodedValue) obj).getValue();
    }

    @Override // org.jf.dexlib2.iface.value.BooleanEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : Boolean.compare(getValue(), ((BooleanEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
