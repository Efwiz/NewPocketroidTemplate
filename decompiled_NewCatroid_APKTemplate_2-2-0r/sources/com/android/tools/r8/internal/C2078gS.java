package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gS, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2078gS extends AbstractC1578a1 implements InterfaceC1614aS {
    final /* synthetic */ C2306jS a;

    private C2078gS(C2306jS c2306jS) {
        this.a = c2306jS;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        C2306jS c2306jS;
        Object obj2;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (key == null) {
            C2306jS c2306jS2 = this.a;
            return c2306jS2.d && c2306jS2.b[c2306jS2.e] == value;
        }
        Object[] objArr = this.a.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(key));
        C2306jS c2306jS3 = this.a;
        int i = iA & c2306jS3.c;
        Object obj3 = objArr[i];
        if (obj3 == null) {
            return false;
        }
        if (key == obj3) {
            return c2306jS3.b[i] == value;
        }
        do {
            c2306jS = this.a;
            i = (i + 1) & c2306jS.c;
            obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
        } while (key != obj2);
        return c2306jS.b[i] == value;
    }

    @Override // com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return new C1765cS(this.a, 0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (key == null) {
            C2306jS c2306jS = this.a;
            if (!c2306jS.d || c2306jS.b[c2306jS.e] != value) {
                return false;
            }
            C2306jS.a(c2306jS);
            return true;
        }
        Object[] objArr = this.a.a;
        int iA = AbstractC3307wk.a(System.identityHashCode(key));
        C2306jS c2306jS2 = this.a;
        int i = iA & c2306jS2.c;
        Object obj2 = objArr[i];
        if (obj2 == null) {
            return false;
        }
        if (obj2 == key) {
            if (c2306jS2.b[i] != value) {
                return false;
            }
            c2306jS2.e(i);
            return true;
        }
        while (true) {
            C2306jS c2306jS3 = this.a;
            i = (i + 1) & c2306jS3.c;
            Object obj3 = objArr[i];
            if (obj3 == null) {
                return false;
            }
            if (obj3 == key && c2306jS3.b[i] == value) {
                c2306jS3.e(i);
                return true;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.g;
    }

    /* synthetic */ C2078gS(C2306jS c2306jS, int i) {
        this(c2306jS);
    }
}
