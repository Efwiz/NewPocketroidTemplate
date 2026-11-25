package com.android.tools.r8.internal;

import java.util.Map;
import java.util.function.BiConsumer;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class LS extends AbstractC1214Nl {
    static final LS k = new LS(null, null, AbstractC2178hm.d, 0, 0);
    private final transient C2410km[] e;
    private final transient C2410km[] f;
    final transient Map.Entry[] g;
    private final transient int h;
    private final transient int i;
    private transient AbstractC1214Nl j;

    private LS(C2410km[] c2410kmArr, C2410km[] c2410kmArr2, Map.Entry[] entryArr, int i, int i2) {
        this.e = c2410kmArr;
        this.f = c2410kmArr2;
        this.g = entryArr;
        this.h = i;
        this.i = i2;
    }

    @Override // java.util.Map
    public final void forEach(BiConsumer biConsumer) {
        int i = AbstractC3123uM.a;
        biConsumer.getClass();
        for (Map.Entry entry : this.g) {
            biConsumer.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final Object get(Object obj) {
        C2410km[] c2410kmArr = this.e;
        if (c2410kmArr != null) {
            int i = this.h;
            AbstractC2178hm abstractC2178hm = PS.h;
            if (obj != null) {
                for (C2410km c2410kmB = c2410kmArr[i & AbstractC0900Ck.a(obj.hashCode())]; c2410kmB != null; c2410kmB = c2410kmB.b()) {
                    if (obj.equals(c2410kmB.a)) {
                        return c2410kmB.b;
                    }
                }
            }
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final int hashCode() {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm k() {
        if (!isEmpty()) {
            return new C2486lm(this, AbstractC1639am.a(this.g));
        }
        int i = AbstractC1048Hm.c;
        return SS.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm l() {
        return new C2638nm(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final boolean o() {
        return false;
    }

    @Override // java.util.Map
    public final int size() {
        return this.g.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC1214Nl
    /* JADX INFO: renamed from: u */
    public final AbstractC1214Nl g() {
        if (isEmpty()) {
            return k;
        }
        AbstractC1214Nl abstractC1214Nl = this.j;
        if (abstractC1214Nl != null) {
            return abstractC1214Nl;
        }
        KS ks = new KS(this, 0);
        this.j = ks;
        return ks;
    }

    static AbstractC1214Nl a(int i, Map.Entry[] entryArr) {
        C2410km c2256im;
        int i2 = i;
        Map.Entry[] entryArr2 = entryArr;
        AbstractC3123uM.b(i2, entryArr2.length);
        int iA = AbstractC0900Ck.a(i2, 1.2d);
        int i3 = iA - 1;
        C2410km[] c2410kmArr = new C2410km[iA];
        C2410km[] c2410kmArr2 = new C2410km[iA];
        Map.Entry[] entryArr3 = i2 == entryArr2.length ? entryArr2 : new C2410km[i2];
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            Map.Entry entry = entryArr2[i4];
            Object key = entry.getKey();
            Object value = entry.getValue();
            AbstractC2625nb.a(key, value);
            int iHashCode = key.hashCode();
            int iHashCode2 = value.hashCode();
            int iA2 = AbstractC0900Ck.a(iHashCode) & i3;
            int iA3 = AbstractC0900Ck.a(iHashCode2) & i3;
            C2410km c2410km = c2410kmArr[iA2];
            AbstractC2178hm abstractC2178hm = PS.h;
            C2410km c2410kmB = c2410km;
            int i6 = 0;
            while (c2410kmB != null) {
                int i7 = i3;
                if (key.equals(c2410kmB.a)) {
                    throw AbstractC2178hm.a(ASTExpr.DEFAULT_MAP_KEY_NAME, entry, c2410kmB);
                }
                i6++;
                c2410kmB = c2410kmB.b();
                i3 = i7;
            }
            int i8 = i3;
            C2410km c2410km2 = c2410kmArr2[iA3];
            C2410km c2410kmC = c2410km2;
            int i9 = i5;
            int i10 = 0;
            while (c2410kmC != null) {
                int i11 = iHashCode;
                if (value.equals(c2410kmC.b)) {
                    throw AbstractC2178hm.a("value", entry, c2410kmC);
                }
                i10++;
                c2410kmC = c2410kmC.c();
                iHashCode = i11;
            }
            int i12 = iHashCode;
            if (i6 > 8 || i10 > 8) {
                return C0995Ft.a(i, entryArr);
            }
            if (c2410km2 == null && c2410km == null) {
                c2256im = ((entry instanceof C2410km) && ((C2410km) entry).d()) ? (C2410km) entry : new C2410km(key, value);
            } else {
                c2256im = new C2256im(key, value, c2410km, c2410km2);
            }
            c2410kmArr[iA2] = c2256im;
            c2410kmArr2[iA3] = c2256im;
            entryArr3[i4] = c2256im;
            i5 = i9 + (i12 ^ iHashCode2);
            i4++;
            i2 = i;
            entryArr2 = entryArr;
            i3 = i8;
        }
        return new LS(c2410kmArr, c2410kmArr2, entryArr3, i3, i5);
    }
}
