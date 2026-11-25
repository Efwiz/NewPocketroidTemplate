package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0737r2;
import com.beust.jcommander.Parameters;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class XK extends AbstractC1757cL {
    static final /* synthetic */ boolean h = true;
    private final C0684j2 c;
    private final EnumC1953et d;
    private final boolean e;
    private final C0737r2 f;
    private final boolean g;

    private XK(C0684j2 c0684j2, EnumC1953et enumC1953et, boolean z, EnumC1812d20[] enumC1812d20Arr, C0737r2 c0737r2) {
        super(EnumC1686bL.f);
        boolean z2 = enumC1812d20Arr.length != c0684j2.h.f.a.length;
        this.g = z2;
        if (!h && z2 && !enumC1812d20Arr[0].a()) {
            throw new AssertionError();
        }
        this.c = c0684j2;
        this.d = enumC1953et;
        this.e = z;
        this.f = c0737r2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int a(AbstractC1757cL abstractC1757cL) {
        int iCompareTo;
        if (!(abstractC1757cL instanceof XK)) {
            return this.a.compareTo(abstractC1757cL.a);
        }
        XK xk = (XK) abstractC1757cL;
        int iCompareTo2 = this.c.compareTo(xk.c);
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        int iCompareTo3 = this.d.compareTo(xk.d);
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        int iCompare = Boolean.compare(this.e, xk.e);
        if (iCompare != 0) {
            return iCompare;
        }
        C0737r2 c0737r2 = this.f;
        if (c0737r2 != null && (iCompareTo = c0737r2.compareTo(xk.f)) != 0) {
            return iCompareTo;
        }
        if (h || equals(abstractC1757cL)) {
            return 0;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final String b() {
        return this.a.name() + Parameters.DEFAULT_OPTION_PREFIXES + this.d.name();
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final boolean c() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int d() {
        boolean z = this.g;
        return (z ? 1 : 0) + this.c.h.f.a.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final boolean equals(Object obj) {
        if (!(obj instanceof XK)) {
            return false;
        }
        XK xk = (XK) obj;
        return this.c == xk.c && this.d == xk.d && this.e == xk.e && Objects.equals(this.f, xk.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int hashCode() {
        return Objects.hashCode(this.f) + Boolean.hashCode(this.e) + this.d.hashCode() + (this.c.hashCode() * 13) + (this.a.ordinal() * 7);
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final String a() {
        return "; method: " + this.c.r0();
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final int a(C2102gl c2102gl, ZK zk, int i) {
        S10 s10A;
        EnumC1968f20 enumC1968f20A;
        ArrayList arrayList = new ArrayList(d());
        int i2 = 0;
        while (i2 < d()) {
            int i3 = i + 1;
            int iIntValue = ((Integer) zk.b.get(i)).intValue();
            if (iIntValue == -1) {
                iIntValue = zk.a.size();
            }
            if (!this.g) {
                enumC1968f20A = EnumC1968f20.a(this.c.h.f.a[i2]);
            } else if (i2 == 0) {
                enumC1968f20A = EnumC1968f20.OBJECT;
            } else {
                enumC1968f20A = EnumC1968f20.a(this.c.h.f.a[i2 - 1]);
            }
            arrayList.add(c2102gl.b(iIntValue, enumC1968f20A));
            i2++;
            i = i3;
        }
        if (this.e) {
            s10A = c2102gl.a(zk.a.size(), AbstractC3472z00.a(this.c.h.e, WH.i(), (C0702m<?>) c2102gl.o), 2);
        } else {
            s10A = null;
        }
        c2102gl.a(AbstractC1084Is.a(this.d, this.c, this.f, s10A, arrayList, false));
        return i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1757cL
    public final boolean a(AbstractC1476Wj abstractC1476Wj) {
        return abstractC1476Wj.c((AbstractC1476Wj) null, this.c) != this.c;
    }
}
