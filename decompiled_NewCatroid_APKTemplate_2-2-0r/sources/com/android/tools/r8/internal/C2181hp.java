package com.android.tools.r8.internal;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2181hp extends D {
    int b;
    int c;
    boolean d;
    boolean e;
    protected transient InterfaceC2842qK f;
    protected transient InterfaceC1357Sr g;
    protected transient ZJ h;
    final /* synthetic */ C2413kp i;

    public C2181hp(C2413kp c2413kp, int i, boolean z, int i2, boolean z2) {
        this.i = c2413kp;
        if (!z && !z2 && c2413kp.c(i, i2) > 0) {
            throw new IllegalArgumentException("Start key (" + i + ") is larger than end key (" + i2 + Constant.AFTER_QUTO);
        }
        this.b = i;
        this.d = z;
        this.c = i2;
        this.e = z2;
        this.a = c2413kp.a;
    }

    @Override // com.android.tools.r8.internal.B, com.android.tools.r8.internal.InterfaceC2730op
    public final boolean a(int i) {
        return d(i) && this.i.a(i);
    }

    @Override // com.android.tools.r8.internal.D, com.android.tools.r8.internal.InterfaceC2947rp
    public final InterfaceC2842qK b() {
        if (this.f == null) {
            this.f = new C1642ap(this);
        }
        return this.f;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp c(int i) {
        if (!this.e && this.i.c(i, this.c) >= 0) {
            return this;
        }
        return new C2181hp(this.i, this.b, this.d, i, false);
    }

    @Override // com.android.tools.r8.internal.AbstractC3470z, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        C1949ep c1949ep = new C1949ep(this);
        while (c1949ep.hasNext()) {
            c1949ep.a();
            c1949ep.remove();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp, java.util.SortedMap
    public final InterfaceC3391xr comparator() {
        return this.i.k;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        C1949ep c1949ep = new C1949ep(this);
        while (c1949ep.hasNext()) {
            Object obj2 = c1949ep.a().b;
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
        C1481Wo c1481WoD;
        return (!d(i) || (c1481WoD = this.i.d(i)) == null) ? this.a : c1481WoD.b;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        C1481Wo c1481Wo = this.i.d;
        return !(k() != null);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0032  */
    public final C1481Wo k() {
        C1481Wo c1481Wo;
        C1481Wo c1481Wo2;
        C2413kp c2413kp = this.i;
        C1481Wo c1481Wo3 = c2413kp.b;
        if (c1481Wo3 == null) {
            return null;
        }
        if (this.d) {
            c1481Wo = c2413kp.d;
        } else {
            int i = this.b;
            int iC = 0;
            C1481Wo c1481Wo4 = c1481Wo3;
            while (c1481Wo3 != null && (iC = c2413kp.c(i, c1481Wo3.a)) != 0) {
                if (iC < 0) {
                    if ((c1481Wo3.e & 1073741824) != 0) {
                        c1481Wo2 = null;
                    } else {
                        c1481Wo2 = c1481Wo3.c;
                    }
                } else if ((c1481Wo3.e & Integer.MIN_VALUE) != 0) {
                    c1481Wo2 = null;
                } else {
                    c1481Wo2 = c1481Wo3.d;
                }
                C1481Wo c1481Wo5 = c1481Wo2;
                c1481Wo4 = c1481Wo3;
                c1481Wo3 = c1481Wo5;
            }
            c1481Wo = iC == 0 ? c1481Wo3 : c1481Wo4;
            if (this.i.c(c1481Wo.a, this.b) < 0) {
                C1481Wo c1481Wo6 = c1481Wo.d;
                if ((c1481Wo.e & Integer.MIN_VALUE) == 0) {
                    c1481Wo = c1481Wo6;
                    while ((c1481Wo.e & 1073741824) == 0) {
                        c1481Wo = c1481Wo.c;
                    }
                } else {
                    c1481Wo = c1481Wo6;
                }
            }
        }
        if (c1481Wo == null || (!this.e && this.i.c(c1481Wo.a, this.c) >= 0)) {
            return null;
        }
        return c1481Wo;
    }

    @Override // com.android.tools.r8.internal.D, java.util.Map, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        if (this.g == null) {
            this.g = new C1794cp(this, 0);
        }
        return this.g;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0032  */
    public final C1481Wo l() {
        C1481Wo c1481WoD;
        C1481Wo c1481Wo;
        C2413kp c2413kp = this.i;
        C1481Wo c1481Wo2 = c2413kp.b;
        if (c1481Wo2 == null) {
            return null;
        }
        if (this.e) {
            c1481WoD = c2413kp.e;
        } else {
            int i = this.c;
            int iC = 0;
            C1481Wo c1481Wo3 = c1481Wo2;
            while (c1481Wo2 != null) {
                iC = c2413kp.c(i, c1481Wo2.a);
                if (iC == 0) {
                    break;
                }
                if (iC < 0) {
                    if ((c1481Wo2.e & 1073741824) != 0) {
                        c1481Wo = null;
                    } else {
                        c1481Wo = c1481Wo2.c;
                    }
                } else if ((c1481Wo2.e & Integer.MIN_VALUE) != 0) {
                    c1481Wo = null;
                } else {
                    c1481Wo = c1481Wo2.d;
                }
                C1481Wo c1481Wo4 = c1481Wo;
                c1481Wo3 = c1481Wo2;
                c1481Wo2 = c1481Wo4;
            }
            c1481WoD = iC == 0 ? c1481Wo2 : c1481Wo3;
            if (this.i.c(c1481WoD.a, this.c) >= 0) {
                c1481WoD = c1481WoD.d();
            }
        }
        if (c1481WoD == null || (!this.d && this.i.c(c1481WoD.a, this.b) < 0)) {
            return null;
        }
        return c1481WoD;
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
        C1949ep c1949ep = new C1949ep(this);
        int i = 0;
        while (c1949ep.hasNext()) {
            i++;
            c1949ep.a();
        }
        return i;
    }

    @Override // com.android.tools.r8.internal.B, java.util.Map
    public final ZJ values() {
        if (this.h == null) {
            this.h = new C1717bp(this);
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

    @Override // java.util.SortedMap
    public final Comparator comparator() {
        return this.i.k;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final int d() {
        C1481Wo c1481WoL = l();
        if (c1481WoL != null) {
            return c1481WoL.a;
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp b(int i) {
        if (!this.d && this.i.c(i, this.b) <= 0) {
            return this;
        }
        return new C2181hp(this.i, i, false, this.c, this.e);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp a(int i, int i2) {
        boolean z = this.e;
        if (z && this.d) {
            return new C2181hp(this.i, i, false, i2, false);
        }
        if (!z && this.i.c(i2, this.c) >= 0) {
            i2 = this.c;
        }
        int i3 = i2;
        if (!this.d && this.i.c(i, this.b) <= 0) {
            i = this.b;
        }
        int i4 = i;
        return (this.e || this.d || i4 != this.b || i3 != this.c) ? new C2181hp(this.i, i4, false, i3, false) : this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final int a() {
        C1481Wo c1481WoK = k();
        if (c1481WoK != null) {
            return c1481WoK.a;
        }
        throw new NoSuchElementException();
    }
}
