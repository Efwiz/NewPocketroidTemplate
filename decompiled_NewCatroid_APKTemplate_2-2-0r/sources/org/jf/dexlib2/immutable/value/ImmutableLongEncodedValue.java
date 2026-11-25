package org.jf.dexlib2.immutable.value;

import org.jf.dexlib2.base.value.BaseLongEncodedValue;
import org.jf.dexlib2.iface.value.LongEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableLongEncodedValue extends BaseLongEncodedValue implements ImmutableEncodedValue {
    protected final long value;

    public ImmutableLongEncodedValue(long j) {
        this.value = j;
    }

    public static ImmutableLongEncodedValue of(LongEncodedValue longEncodedValue) {
        if (longEncodedValue instanceof ImmutableLongEncodedValue) {
            return (ImmutableLongEncodedValue) longEncodedValue;
        }
        return new ImmutableLongEncodedValue(longEncodedValue.getValue());
    }

    @Override // org.jf.dexlib2.iface.value.LongEncodedValue
    public long getValue() {
        return this.value;
    }
}
