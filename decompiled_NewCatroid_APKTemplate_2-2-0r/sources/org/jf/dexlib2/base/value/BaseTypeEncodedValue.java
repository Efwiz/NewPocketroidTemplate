package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.TypeEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseTypeEncodedValue implements TypeEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 24;
    }

    @Override // org.jf.dexlib2.iface.value.TypeEncodedValue
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override // org.jf.dexlib2.iface.value.TypeEncodedValue
    public boolean equals(Object obj) {
        if (obj instanceof TypeEncodedValue) {
            return getValue().equals(((TypeEncodedValue) obj).getValue());
        }
        return false;
    }

    @Override // org.jf.dexlib2.iface.value.TypeEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : getValue().compareTo(((TypeEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
