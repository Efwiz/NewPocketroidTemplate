package org.jf.dexlib2.iface.value;

import org.jf.dexlib2.iface.reference.MethodReference;

/* JADX INFO: loaded from: classes6.dex */
public interface MethodEncodedValue extends EncodedValue {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.lang.Comparable
    int compareTo(EncodedValue encodedValue);

    boolean equals(Object obj);

    MethodReference getValue();

    int hashCode();
}
