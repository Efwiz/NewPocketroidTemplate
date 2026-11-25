package org.jf.dexlib2.immutable.value;

import org.jf.dexlib2.base.value.BaseCharEncodedValue;
import org.jf.dexlib2.iface.value.CharEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableCharEncodedValue extends BaseCharEncodedValue implements ImmutableEncodedValue {
    protected final char value;

    public ImmutableCharEncodedValue(char c) {
        this.value = c;
    }

    public static ImmutableCharEncodedValue of(CharEncodedValue charEncodedValue) {
        if (charEncodedValue instanceof ImmutableCharEncodedValue) {
            return (ImmutableCharEncodedValue) charEncodedValue;
        }
        return new ImmutableCharEncodedValue(charEncodedValue.getValue());
    }

    @Override // org.jf.dexlib2.iface.value.CharEncodedValue
    public char getValue() {
        return this.value;
    }
}
