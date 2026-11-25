package com.android.tools.r8.internal;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1329Rp extends D {
    int b;
    int c;
    boolean d;
    boolean e;
    protected transient InterfaceC2842qK f;
    protected transient InterfaceC1357Sr g;
    protected transient ZJ h;
    final /* synthetic */ C1430Up i;

    public C1329Rp(C1430Up c1430Up, int i, boolean z, int i2, boolean z2) {
        this.i = c1430Up;
        if (!z && !z2 && c1430Up.c(i, i2) > 0) {
            throw new IllegalArgumentException("Start key (" + i + ") is larger than end key (" + i2 + Constant.AFTER_QUTO);
        }
        this.b = i;
        this.d = z;
        this.c = i2;
        this.e = z2;
        this.a = c1430Up.a;
    }

    @Override // com.android.tools.r8.internal.B, com.android.tools.r8.internal.InterfaceC2730op
    public final boolean a(int i) {
        return d(i) && this.i.a(i);
    }

    @Override // com.android.tools.r8.internal.D, com.android.tools.r8.internal.InterfaceC2947rp
    public final InterfaceC2842qK b() {
        if (this.f == null) {
            this.f = new C1138Kp(this);
        }
        return this.f;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp c(int i) {
        if (!this.e && this.i.c(i, this.c) >= 0) {
            return this;
        }
        return new C1329Rp(this.i, this.b, this.d, i, false);
    }

    @Override // com.android.tools.r8.internal.AbstractC3470z, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        C1245Op c1245Op = new C1245Op(this);
        while (c1245Op.hasNext()) {
            c1245Op.a();
            c1245Op.remove();
        }
    }

    @Override // java.util.SortedMap
    public final /* bridge */ /* synthetic */ Comparator comparator() {
        comparator();
        return null;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        C1245Op c1245Op = new C1245Op(this);
        while (c1245Op.hasNext()) {
            Object obj2 = c1245Op.a().b;
            if (obj2 == null) {
                if (obj == null) {
                    return true;
                }
            } else if (obj2.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    final boolean d(int i) {
        return (this.d || this.i.c(i, this.b) >= 0) && (this.e || this.i.c(i, this.c) < 0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2730op
    public final Object get(int i) {
        C1020Gp c1020GpD;
        return (!d(i) || (c1020GpD = this.i.d(i)) == null) ? this.a : c1020GpD.b;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        C1020Gp c1020Gp = this.i.d;
        return !(k() != null);
    }

    public final C1020Gp k() {
        C1020Gp c1020GpE;
        C1430Up c1430Up = this.i;
        if (c1430Up.b == null) {
            return null;
        }
        if (this.d) {
            c1020GpE = c1430Up.d;
        } else {
            c1020GpE = c1430Up.e(this.b);
            if (this.i.c(c1020GpE.a, this.b) < 0) {
                C1020Gp c1020Gp = c1020GpE.d;
                if ((c1020GpE.e & Integer.MIN_VALUE) == 0) {
                    c1020GpE = c1020Gp;
                    while ((c1020GpE.e & 1073741824) == 0) {
                        c1020GpE = c1020GpE.c;
                    }
                } else {
                    c1020GpE = c1020Gp;
                }
            }
        }
        if (c1020GpE == null || (!this.e && this.i.c(c1020GpE.a, this.c) >= 0)) {
            return null;
        }
        return c1020GpE;
    }

    @Override // com.android.tools.r8.internal.D, java.util.Map, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        if (this.g == null) {
            this.g = new C1191Mp(this, 0);
        }
        return this.g;
    }

    public final C1020Gp l() {
        C1020Gp c1020GpE;
        C1430Up c1430Up = this.i;
        if (c1430Up.b == null) {
            return null;
        }
        if (this.e) {
            c1020GpE = c1430Up.e;
        } else {
            c1020GpE = c1430Up.e(this.c);
            if (this.i.c(c1020GpE.a, this.c) >= 0) {
                c1020GpE = c1020GpE.e();
            }
        }
        if (c1020GpE == null || (!this.d && this.i.c(c1020GpE.a, this.b) < 0)) {
            return null;
        }
        return c1020GpE;
    }

    @Override // com.android.tools.r8.internal.AbstractC3470z
    public final Object remove(int i) {
        this.i.i = false;
        if (d(i)) {
            return this.i.i ? this.i.remove(i) : this.a;
        }
        return this.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        C1245Op c1245Op = new C1245Op(this);
        int i = 0;
        while (c1245Op.hasNext()) {
            i++;
            c1245Op.a();
        }
        return i;
    }

    @Override // com.android.tools.r8.internal.B, java.util.Map
    public final ZJ values() {
        if (this.h == null) {
            this.h = new C1164Lp(this);
        }
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC3470z, com.android.tools.r8.internal.InterfaceC2730op
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

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp, java.util.SortedMap
    public final InterfaceC3391xr comparator() {
        this.i.getClass();
        return null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final int d() {
        C1020Gp c1020GpL = l();
        if (c1020GpL != null) {
            return c1020GpL.a;
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp b(int i) {
        if (!this.d && this.i.c(i, this.b) <= 0) {
            return this;
        }
        return new C1329Rp(this.i, i, false, this.c, this.e);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp a(int i, int i2) {
        boolean z = this.e;
        if (z && this.d) {
            return new C1329Rp(this.i, i, false, i2, false);
        }
        if (!z && this.i.c(i2, this.c) >= 0) {
            i2 = this.c;
        }
        int i3 = i2;
        if (!this.d && this.i.c(i, this.b) <= 0) {
            i = this.b;
        }
        int i4 = i;
        return (this.e || this.d || i4 != this.b || i3 != this.c) ? new C1329Rp(this.i, i4, false, i3, false) : this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final int a() {
        C1020Gp c1020GpK = k();
        if (c1020GpK != null) {
            return c1020GpK.a;
        }
        throw new NoSuchElementException();
    }
}
