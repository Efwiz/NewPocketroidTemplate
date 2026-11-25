package org.jf.dexlib2.immutable.value;

import org.jf.dexlib2.base.value.BaseShortEncodedValue;
import org.jf.dexlib2.iface.value.ShortEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableShortEncodedValue extends BaseShortEncodedValue implements ImmutableEncodedValue {
    protected final short value;

    public ImmutableShortEncodedValue(short s) {
        this.value = s;
    }

    public static ImmutableShortEncodedValue of(ShortEncodedValue shortEncodedValue) {
        if (shortEncodedValue instanceof ImmutableShortEncodedValue) {
            return (ImmutableShortEncodedValue) shortEncodedValue;
        }
        return new ImmutableShortEncodedValue(shortEncodedValue.getValue());
    }

    @Override // org.jf.dexlib2.iface.value.ShortEncodedValue
    public short getValue() {
        return this.value;
    }
}
