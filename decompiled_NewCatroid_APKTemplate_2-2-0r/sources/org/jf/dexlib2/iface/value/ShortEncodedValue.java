package org.jf.dexlib2.iface.value;

/* JADX INFO: loaded from: classes6.dex */
public interface ShortEncodedValue extends EncodedValue {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.lang.Comparable
    int compareTo(EncodedValue encodedValue);

    boolean equals(Object obj);

    short getValue();

    int hashCode();
}
