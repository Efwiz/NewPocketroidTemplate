package org.jf.dexlib2.immutable.value;

import java.util.Collection;
import java.util.List;
import org.jf.dexlib2.base.value.BaseArrayEncodedValue;
import org.jf.dexlib2.iface.value.ArrayEncodedValue;
import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableArrayEncodedValue extends BaseArrayEncodedValue implements ImmutableEncodedValue {
    protected final List<? extends ImmutableEncodedValue> value;

    public ImmutableArrayEncodedValue(Collection<? extends EncodedValue> collection) {
        this.value = ImmutableEncodedValueFactory.immutableListOf(collection);
    }

    public ImmutableArrayEncodedValue(List<ImmutableEncodedValue> list) {
        this.value = list;
    }

    public static ImmutableArrayEncodedValue of(ArrayEncodedValue arrayEncodedValue) {
        if (arrayEncodedValue instanceof ImmutableArrayEncodedValue) {
            return (ImmutableArrayEncodedValue) arrayEncodedValue;
        }
        return new ImmutableArrayEncodedValue(arrayEncodedValue.getValue());
    }

    @Override // org.jf.dexlib2.iface.value.ArrayEncodedValue
    public List<? extends ImmutableEncodedValue> getValue() {
        return this.value;
    }
}
