package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.EnumEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseEnumEncodedValue implements EnumEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 27;
    }

    @Override // org.jf.dexlib2.iface.value.EnumEncodedValue
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override // org.jf.dexlib2.iface.value.EnumEncodedValue
    public boolean equals(Object obj) {
        if (obj instanceof EnumEncodedValue) {
            return getValue().equals(((EnumEncodedValue) obj).getValue());
        }
        return false;
    }

    @Override // org.jf.dexlib2.iface.value.EnumEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : getValue().compareTo(((EnumEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
