package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.NullEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseNullEncodedValue implements NullEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 30;
    }

    @Override // org.jf.dexlib2.iface.value.NullEncodedValue
    public int hashCode() {
        return 0;
    }

    @Override // org.jf.dexlib2.iface.value.NullEncodedValue
    public boolean equals(Object obj) {
        return obj instanceof NullEncodedValue;
    }

    @Override // org.jf.dexlib2.iface.value.NullEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        return Integer.compare(getValueType(), encodedValue.getValueType());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
