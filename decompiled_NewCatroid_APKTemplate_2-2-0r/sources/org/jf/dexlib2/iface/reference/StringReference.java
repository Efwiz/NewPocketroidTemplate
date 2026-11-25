package org.jf.dexlib2.iface.reference;

/* JADX INFO: loaded from: classes6.dex */
public interface StringReference extends Reference, CharSequence, Comparable<CharSequence> {
    @Override // java.lang.Comparable
    int compareTo(CharSequence charSequence);

    boolean equals(Object obj);

    String getString();

    int hashCode();
}
