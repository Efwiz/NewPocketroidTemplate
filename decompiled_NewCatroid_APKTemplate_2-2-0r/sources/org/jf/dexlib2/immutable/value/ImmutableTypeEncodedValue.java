package org.jf.dexlib2.immutable.value;

import org.jf.dexlib2.base.value.BaseTypeEncodedValue;
import org.jf.dexlib2.iface.value.TypeEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableTypeEncodedValue extends BaseTypeEncodedValue implements ImmutableEncodedValue {
    protected final String value;

    public ImmutableTypeEncodedValue(String str) {
        this.value = str;
    }

    public static ImmutableTypeEncodedValue of(TypeEncodedValue typeEncodedValue) {
        if (typeEncodedValue instanceof ImmutableTypeEncodedValue) {
            return (ImmutableTypeEncodedValue) typeEncodedValue;
        }
        return new ImmutableTypeEncodedValue(typeEncodedValue.getValue());
    }

    @Override // org.jf.dexlib2.iface.value.TypeEncodedValue
    public String getValue() {
        return this.value;
    }
}
