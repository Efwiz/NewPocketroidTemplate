package org.jf.dexlib2.iface.value;

/* JADX INFO: loaded from: classes6.dex */
public interface CharEncodedValue extends EncodedValue {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.lang.Comparable
    int compareTo(EncodedValue encodedValue);

    boolean equals(Object obj);

    char getValue();

    int hashCode();
}
