package org.jf.dexlib2.immutable.value;

import org.jf.dexlib2.base.value.BaseIntEncodedValue;
import org.jf.dexlib2.iface.value.IntEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableIntEncodedValue extends BaseIntEncodedValue implements ImmutableEncodedValue {
    protected final int value;

    public ImmutableIntEncodedValue(int i) {
        this.value = i;
    }

    public static ImmutableIntEncodedValue of(IntEncodedValue intEncodedValue) {
        if (intEncodedValue instanceof ImmutableIntEncodedValue) {
            return (ImmutableIntEncodedValue) intEncodedValue;
        }
        return new ImmutableIntEncodedValue(intEncodedValue.getValue());
    }

    @Override // org.jf.dexlib2.iface.value.IntEncodedValue
    public int getValue() {
        return this.value;
    }
}
