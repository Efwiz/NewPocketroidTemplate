package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.MethodTypeEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseMethodTypeEncodedValue implements MethodTypeEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 21;
    }

    @Override // org.jf.dexlib2.iface.value.MethodTypeEncodedValue
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override // org.jf.dexlib2.iface.value.MethodTypeEncodedValue
    public boolean equals(Object obj) {
        if (obj instanceof MethodTypeEncodedValue) {
            return getValue().equals(((MethodTypeEncodedValue) obj).getValue());
        }
        return false;
    }

    @Override // org.jf.dexlib2.iface.value.MethodTypeEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : getValue().compareTo(((MethodTypeEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
