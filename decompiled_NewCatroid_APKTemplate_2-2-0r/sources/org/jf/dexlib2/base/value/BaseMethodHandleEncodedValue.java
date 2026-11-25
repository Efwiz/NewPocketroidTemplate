package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.MethodHandleEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseMethodHandleEncodedValue implements MethodHandleEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 22;
    }

    @Override // org.jf.dexlib2.iface.value.MethodHandleEncodedValue
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override // org.jf.dexlib2.iface.value.MethodHandleEncodedValue
    public boolean equals(Object obj) {
        if (obj instanceof MethodHandleEncodedValue) {
            return getValue().equals(((MethodHandleEncodedValue) obj).getValue());
        }
        return false;
    }

    @Override // org.jf.dexlib2.iface.value.MethodHandleEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : getValue().compareTo(((MethodHandleEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
