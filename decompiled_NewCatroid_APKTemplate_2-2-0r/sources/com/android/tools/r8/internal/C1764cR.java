package com.android.tools.r8.internal;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1764cR extends AbstractC2350k1 implements InterfaceC1613aR, Cloneable {
    protected final Object a;
    protected final boolean b = true;
    protected transient C2695oK c;
    protected transient C3358xS d;
    protected transient C2897r5 e;

    protected C1764cR(Object obj) {
        this.a = obj;
    }

    @Override // com.android.tools.r8.internal.XQ
    public final boolean b(Object obj) {
        if (this.a == obj) {
            return this.b;
        }
        return false;
    }

    public final Object clone() {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        return this.a == obj;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return ((Boolean) obj).booleanValue() == this.b;
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return h();
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (map.size() != 1) {
            return false;
        }
        return ((Map.Entry) map.entrySet().iterator().next()).equals(h().iterator().next());
    }

    @Override // com.android.tools.r8.internal.InterfaceC1613aR
    public final InterfaceC2529mK h() {
        if (this.c == null) {
            C2430l1 c2430l1 = new C2430l1(this.a, this.b);
            C2604nK c2604nK = AbstractC2771pK.a;
            this.c = new C2695oK(c2430l1);
        }
        return this.c;
    }

    @Override // java.util.Map
    public final int hashCode() {
        return System.identityHashCode(this.a) ^ (this.b ? XKeySym.XK_kana_MA : XKeySym.XK_kana_YU);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return false;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final /* bridge */ /* synthetic */ int size() {
        return 1;
    }

    public final String toString() {
        return "{" + this.a + "=>" + this.b + "}";
    }

    @Override // java.util.Map
    public final Collection values() {
        if (this.e == null) {
            this.e = AbstractC2968s5.a(this.b);
        }
        return this.e;
    }

    @Override // java.util.Map
    public final InterfaceC3211vS keySet() {
        if (this.d == null) {
            Object obj = this.a;
            C3289wS c3289wS = AbstractC3436yS.a;
            this.d = new C3358xS(obj);
        }
        return this.d;
    }
}
