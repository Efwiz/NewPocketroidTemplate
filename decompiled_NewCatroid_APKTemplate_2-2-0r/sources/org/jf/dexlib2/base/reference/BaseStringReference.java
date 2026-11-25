package org.jf.dexlib2.base.reference;

import org.jf.dexlib2.iface.reference.StringReference;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseStringReference extends BaseReference implements StringReference {
    @Override // org.jf.dexlib2.iface.reference.StringReference
    public int hashCode() {
        return getString().hashCode();
    }

    @Override // org.jf.dexlib2.iface.reference.StringReference
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof StringReference)) {
            return false;
        }
        return getString().equals(((StringReference) obj).getString());
    }

    @Override // org.jf.dexlib2.iface.reference.StringReference, java.lang.Comparable
    public int compareTo(CharSequence charSequence) {
        return getString().compareTo(charSequence.toString());
    }

    @Override // java.lang.CharSequence
    public int length() {
        return getString().length();
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return getString().charAt(i);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return getString().subSequence(i, i2);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return getString();
    }
}
