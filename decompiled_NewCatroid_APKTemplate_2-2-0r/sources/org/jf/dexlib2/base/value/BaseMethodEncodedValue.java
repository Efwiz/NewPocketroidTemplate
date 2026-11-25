package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.MethodEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseMethodEncodedValue implements MethodEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 26;
    }

    @Override // org.jf.dexlib2.iface.value.MethodEncodedValue
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override // org.jf.dexlib2.iface.value.MethodEncodedValue
    public boolean equals(Object obj) {
        if (obj instanceof MethodEncodedValue) {
            return getValue().equals(((MethodEncodedValue) obj).getValue());
        }
        return false;
    }

    @Override // org.jf.dexlib2.iface.value.MethodEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : getValue().compareTo(((MethodEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
