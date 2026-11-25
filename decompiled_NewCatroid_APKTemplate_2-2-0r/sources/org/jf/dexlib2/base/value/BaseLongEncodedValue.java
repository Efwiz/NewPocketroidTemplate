package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.LongEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseLongEncodedValue implements LongEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 6;
    }

    @Override // org.jf.dexlib2.iface.value.LongEncodedValue
    public int hashCode() {
        long value = getValue();
        return (((int) value) * 31) + ((int) (value >>> 32));
    }

    @Override // org.jf.dexlib2.iface.value.LongEncodedValue
    public boolean equals(Object obj) {
        return (obj instanceof LongEncodedValue) && getValue() == ((LongEncodedValue) obj).getValue();
    }

    @Override // org.jf.dexlib2.iface.value.LongEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : Long.compare(getValue(), ((LongEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
