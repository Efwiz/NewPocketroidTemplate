package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class AV extends AbstractC1048Hm {
    final transient Object d;
    private transient int e;

    AV(Object obj) {
        this.d = AbstractC3123uM.a(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int a(int i, Object[] objArr) {
        objArr[i] = this.d;
        return i + 1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.d.equals(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        return new C3241vt(this.d);
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm, java.util.Collection, java.util.Set
    public final int hashCode() {
        int i = this.e;
        if (i != 0) {
            return i;
        }
        int iHashCode = this.d.hashCode();
        this.e = iHashCode;
        return iHashCode;
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm
    final AbstractC1639am i() {
        return new C3509zV(this.d);
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm
    final boolean j() {
        return this.e != 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return 1;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        String string = this.d.toString();
        return new StringBuilder(String.valueOf(string).length() + 2).append('[').append(string).append(']').toString();
    }
}
