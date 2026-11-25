package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.ShortEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseShortEncodedValue implements ShortEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 2;
    }

    @Override // org.jf.dexlib2.iface.value.ShortEncodedValue
    public int hashCode() {
        return getValue();
    }

    @Override // org.jf.dexlib2.iface.value.ShortEncodedValue
    public boolean equals(Object obj) {
        return (obj instanceof ShortEncodedValue) && getValue() == ((ShortEncodedValue) obj).getValue();
    }

    @Override // org.jf.dexlib2.iface.value.ShortEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : Short.compare(getValue(), ((ShortEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
