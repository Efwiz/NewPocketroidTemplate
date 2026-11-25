package org.jf.dexlib2.iface.reference;

/* JADX INFO: loaded from: classes6.dex */
public interface FieldReference extends Reference, Comparable<FieldReference> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.lang.Comparable
    int compareTo(FieldReference fieldReference);

    boolean equals(Object obj);

    String getDefiningClass();

    String getName();

    String getType();

    int hashCode();
}
