package org.jf.dexlib2.base.value;

import org.jf.dexlib2.formatter.DexFormatter;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.FieldEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseFieldEncodedValue implements FieldEncodedValue {
    @Override // org.jf.dexlib2.iface.value.EncodedValue
    public int getValueType() {
        return 25;
    }

    @Override // org.jf.dexlib2.iface.value.FieldEncodedValue
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override // org.jf.dexlib2.iface.value.FieldEncodedValue
    public boolean equals(Object obj) {
        if (obj instanceof FieldEncodedValue) {
            return getValue().equals(((FieldEncodedValue) obj).getValue());
        }
        return false;
    }

    @Override // org.jf.dexlib2.iface.value.FieldEncodedValue, java.lang.Comparable
    public int compareTo(EncodedValue encodedValue) {
        int iCompare = Integer.compare(getValueType(), encodedValue.getValueType());
        return iCompare != 0 ? iCompare : getValue().compareTo(((FieldEncodedValue) encodedValue).getValue());
    }

    public String toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this);
    }
}
