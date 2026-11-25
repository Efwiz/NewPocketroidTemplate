package com.android.tools.r8.internal;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2028fr extends I {
    int b;
    int c;
    boolean d;
    boolean e;
    protected transient InterfaceC2842qK f;
    protected transient InterfaceC1357Sr g;
    protected transient InterfaceC2703oS h;
    final /* synthetic */ C2261ir i;

    public C2028fr(C2261ir c2261ir, int i, boolean z, int i2, boolean z2) {
        this.i = c2261ir;
        if (!z && !z2 && c2261ir.c(i, i2) > 0) {
            throw new IllegalArgumentException("Start key (" + i + ") is larger than end key (" + i2 + Constant.AFTER_QUTO);
        }
        this.b = i;
        this.d = z;
        this.c = i2;
        this.e = z2;
        this.a = c2261ir.a;
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC3087tq
    public final boolean a(int i) {
        return d(i) && this.i.a(i);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr b(int i) {
        if (!this.d && this.i.c(i, this.b) <= 0) {
            return this;
        }
        return new C2028fr(this.i, i, false, this.c, this.e);
    }

    @Override // com.android.tools.r8.internal.I, com.android.tools.r8.internal.InterfaceC0992Fq
    public final InterfaceC2842qK c() {
        if (this.f == null) {
            this.f = new C1539Yq(this);
        }
        return this.f;
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        C1796cr c1796cr = new C1796cr(this);
        while (c1796cr.hasNext()) {
            c1796cr.a();
            c1796cr.remove();
        }
    }

    @Override // java.util.SortedMap
    public final /* bridge */ /* synthetic */ Comparator comparator() {
        comparator();
        return null;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        C1796cr c1796cr = new C1796cr(this);
        while (c1796cr.hasNext()) {
            if (c1796cr.a().b == obj) {
                return true;
            }
        }
        return false;
    }

    final boolean d(int i) {
        return (this.d || this.i.c(i, this.b) >= 0) && (this.e || this.i.c(i, this.c) < 0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3087tq
    public final Object get(int i) {
        C1431Uq c1431UqD;
        return (!d(i) || (c1431UqD = this.i.d(i)) == null) ? this.a : c1431UqD.b;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        C1431Uq c1431Uq = this.i.d;
        return !(k() != null);
    }

    public final C1431Uq k() {
        C1431Uq c1431UqE;
        C2261ir c2261ir = this.i;
        if (c2261ir.b == null) {
            return null;
        }
        if (this.d) {
            c1431UqE = c2261ir.d;
        } else {
            c1431UqE = c2261ir.e(this.b);
            if (this.i.c(c1431UqE.a, this.b) < 0) {
                C1431Uq c1431Uq = c1431UqE.d;
                if ((c1431UqE.e & Integer.MIN_VALUE) == 0) {
                    c1431UqE = c1431Uq;
                    while ((c1431UqE.e & 1073741824) == 0) {
                        c1431UqE = c1431UqE.c;
                    }
                } else {
                    c1431UqE = c1431Uq;
                }
            }
        }
        if (c1431UqE == null || (!this.e && this.i.c(c1431UqE.a, this.c) >= 0)) {
            return null;
        }
        return c1431UqE;
    }

    @Override // com.android.tools.r8.internal.I, com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        if (this.g == null) {
            this.g = new C1644ar(this, 0);
        }
        return this.g;
    }

    public final C1431Uq l() {
        C1431Uq c1431UqE;
        C2261ir c2261ir = this.i;
        if (c2261ir.b == null) {
            return null;
        }
        if (this.e) {
            c1431UqE = c2261ir.e;
        } else {
            c1431UqE = c2261ir.e(this.c);
            if (this.i.c(c1431UqE.a, this.c) >= 0) {
                c1431UqE = c1431UqE.e();
            }
        }
        if (c1431UqE == null || (!this.d && this.i.c(c1431UqE.a, this.b) < 0)) {
            return null;
        }
        return c1431UqE;
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object remove(int i) {
        this.i.i = false;
        if (d(i)) {
            return this.i.i ? this.i.remove(i) : this.a;
        }
        return this.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        C1796cr c1796cr = new C1796cr(this);
        int i = 0;
        while (c1796cr.hasNext()) {
            i++;
            c1796cr.a();
        }
        return i;
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC2703oS values() {
        if (this.h == null) {
            this.h = new C1565Zq(this);
        }
        return this.h;
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object a(int i, Object obj) {
        this.i.i = false;
        if (d(i)) {
            return this.i.i ? this.a : this.i.a(i, obj);
        }
        StringBuilder sbA = AbstractC1588a5.a("Key (", i, ") out of range [");
        boolean z = this.d;
        String strValueOf = Parameters.DEFAULT_OPTION_PREFIXES;
        StringBuilder sbAppend = sbA.append(z ? Parameters.DEFAULT_OPTION_PREFIXES : String.valueOf(this.b)).append(", ");
        if (!this.e) {
            strValueOf = String.valueOf(this.c);
        }
        throw new IllegalArgumentException(sbAppend.append(strValueOf).append(Constant.AFTER_QUTO).toString());
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC3391xr comparator() {
        this.i.getClass();
        return null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final int d() {
        C1431Uq c1431UqL = l();
        if (c1431UqL != null) {
            return c1431UqL.a;
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr c(int i) {
        if (!this.e && this.i.c(i, this.c) >= 0) {
            return this;
        }
        return new C2028fr(this.i, this.b, this.d, i, false);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr a(int i, int i2) {
        boolean z = this.e;
        if (z && this.d) {
            return new C2028fr(this.i, i, false, i2, false);
        }
        if (!z && this.i.c(i2, this.c) >= 0) {
            i2 = this.c;
        }
        int i3 = i2;
        if (!this.d && this.i.c(i, this.b) <= 0) {
            i = this.b;
        }
        int i4 = i;
        return (this.e || this.d || i4 != this.b || i3 != this.c) ? new C2028fr(this.i, i4, false, i3, false) : this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final int a() {
        C1431Uq c1431UqK = k();
        if (c1431UqK != null) {
            return c1431UqK.a;
        }
        throw new NoSuchElementException();
    }
}
