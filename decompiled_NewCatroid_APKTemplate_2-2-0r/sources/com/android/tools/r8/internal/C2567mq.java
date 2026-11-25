package com.android.tools.r8.internal;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2567mq extends I {
    int b;
    int c;
    boolean d;
    boolean e;
    protected transient InterfaceC2842qK f;
    protected transient InterfaceC1357Sr g;
    protected transient InterfaceC2703oS h;
    final /* synthetic */ C2807pq i;

    public C2567mq(C2807pq c2807pq, int i, boolean z, int i2, boolean z2) {
        this.i = c2807pq;
        if (!z && !z2 && c2807pq.c(i, i2) > 0) {
            throw new IllegalArgumentException("Start key (" + i + ") is larger than end key (" + i2 + Constant.AFTER_QUTO);
        }
        this.b = i;
        this.d = z;
        this.c = i2;
        this.e = z2;
        this.a = c2807pq.a;
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
        return new C2567mq(this.i, i, false, this.c, this.e);
    }

    @Override // com.android.tools.r8.internal.I, com.android.tools.r8.internal.InterfaceC0992Fq
    public final InterfaceC2842qK c() {
        if (this.f == null) {
            this.f = new C2027fq(this);
        }
        return this.f;
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        C2334jq c2334jq = new C2334jq(this);
        while (c2334jq.hasNext()) {
            c2334jq.a();
            c2334jq.remove();
        }
    }

    @Override // java.util.SortedMap
    public final /* bridge */ /* synthetic */ Comparator comparator() {
        comparator();
        return null;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        C2334jq c2334jq = new C2334jq(this);
        while (c2334jq.hasNext()) {
            if (c2334jq.a().b == obj) {
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
        C1718bq c1718bqD;
        return (!d(i) || (c1718bqD = this.i.d(i)) == null) ? this.a : c1718bqD.b;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        C1718bq c1718bq = this.i.d;
        return !(k() != null);
    }

    public final C1718bq k() {
        C1718bq c1718bqE;
        C2807pq c2807pq = this.i;
        if (c2807pq.b == null) {
            return null;
        }
        if (this.d) {
            c1718bqE = c2807pq.d;
        } else {
            c1718bqE = c2807pq.e(this.b);
            if (this.i.c(c1718bqE.a, this.b) < 0) {
                C1718bq c1718bq = c1718bqE.d;
                if ((c1718bqE.e & Integer.MIN_VALUE) == 0) {
                    c1718bqE = c1718bq;
                    while ((c1718bqE.e & 1073741824) == 0) {
                        c1718bqE = c1718bqE.c;
                    }
                } else {
                    c1718bqE = c1718bq;
                }
            }
        }
        if (c1718bqE == null || (!this.e && this.i.c(c1718bqE.a, this.c) >= 0)) {
            return null;
        }
        return c1718bqE;
    }

    @Override // com.android.tools.r8.internal.I, com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        if (this.g == null) {
            this.g = new C2182hq(this, 0);
        }
        return this.g;
    }

    public final C1718bq l() {
        C1718bq c1718bqE;
        C2807pq c2807pq = this.i;
        if (c2807pq.b == null) {
            return null;
        }
        if (this.e) {
            c1718bqE = c2807pq.e;
        } else {
            c1718bqE = c2807pq.e(this.c);
            if (this.i.c(c1718bqE.a, this.c) >= 0) {
                c1718bqE = c1718bqE.d();
            }
        }
        if (c1718bqE == null || (!this.d && this.i.c(c1718bqE.a, this.b) < 0)) {
            return null;
        }
        return c1718bqE;
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
        C2334jq c2334jq = new C2334jq(this);
        int i = 0;
        while (c2334jq.hasNext()) {
            i++;
            c2334jq.a();
        }
        return i;
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC2703oS values() {
        if (this.h == null) {
            this.h = new C2112gq(this);
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
        C1718bq c1718bqL = l();
        if (c1718bqL != null) {
            return c1718bqL.a;
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr c(int i) {
        if (!this.e && this.i.c(i, this.c) >= 0) {
            return this;
        }
        return new C2567mq(this.i, this.b, this.d, i, false);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr a(int i, int i2) {
        boolean z = this.e;
        if (z && this.d) {
            return new C2567mq(this.i, i, false, i2, false);
        }
        if (!z && this.i.c(i2, this.c) >= 0) {
            i2 = this.c;
        }
        int i3 = i2;
        if (!this.d && this.i.c(i, this.b) <= 0) {
            i = this.b;
        }
        int i4 = i;
        return (this.e || this.d || i4 != this.b || i3 != this.c) ? new C2567mq(this.i, i4, false, i3, false) : this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final int a() {
        C1718bq c1718bqK = k();
        if (c1718bqK != null) {
            return c1718bqK.a;
        }
        throw new NoSuchElementException();
    }
}
