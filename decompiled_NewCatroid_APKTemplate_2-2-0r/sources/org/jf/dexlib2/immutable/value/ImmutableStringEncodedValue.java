package org.jf.dexlib2.immutable.value;

import org.jf.dexlib2.base.value.BaseStringEncodedValue;
import org.jf.dexlib2.iface.value.StringEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableStringEncodedValue extends BaseStringEncodedValue implements ImmutableEncodedValue {
    protected final String value;

    public ImmutableStringEncodedValue(String str) {
        this.value = str;
    }

    public static ImmutableStringEncodedValue of(StringEncodedValue stringEncodedValue) {
        if (stringEncodedValue instanceof ImmutableStringEncodedValue) {
            return (ImmutableStringEncodedValue) stringEncodedValue;
        }
        return new ImmutableStringEncodedValue(stringEncodedValue.getValue());
    }

    @Override // org.jf.dexlib2.iface.value.StringEncodedValue
    public String getValue() {
        return this.value;
    }
}
