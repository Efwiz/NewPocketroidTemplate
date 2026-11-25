package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.DoubleEncodedValue;
import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseDoubleEncodedValue implements DoubleEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 17;
    }

    @Override // org.jf.dexlib2.iface.value.DoubleEncodedValue
    public int hashCode() {
        long jDoubleToRawLongBits = Double.doubleToRawLongBits(getValue());
        return (int) (jDoubleToRawLongBits ^ (jDoubleToRawLongBits >>> 32));
    }

    @Override // org.jf.dexlib2.iface.value.DoubleEncodedValue
    public boolean equals(Object obj) {
        return (obj instanceof DoubleEncodedValue) && Double.doubleToRawLongBits(getValue()) == Double.doubleToRawLongBits(((DoubleEncodedValue) obj).getValue());
    }

    @Override // org.jf.dexlib2.iface.value.DoubleEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : Double.compare(getValue(), ((DoubleEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
