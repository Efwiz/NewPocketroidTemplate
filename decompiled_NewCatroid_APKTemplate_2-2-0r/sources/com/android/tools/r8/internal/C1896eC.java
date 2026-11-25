package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1896eC extends TB {
    static final /* synthetic */ boolean d = true;
    private final IdentityHashMap a;
    private final MR b;
    private final ArrayList c;

    private C1896eC() {
        this.a = new IdentityHashMap();
        this.b = new MR();
        this.c = new ArrayList();
    }

    @Override // com.android.tools.r8.internal.TB
    public final Object a(Object obj, int i) {
        S10 s10 = (S10) obj;
        if (s10.i()) {
            C2214iC c2214iC = (C2214iC) this.a.get(s10);
            if (d || c2214iC != null) {
                return c2214iC;
            }
            throw new AssertionError();
        }
        if (!C2214iC.b && i < 0) {
            throw new AssertionError();
        }
        C2214iC c2214iC2 = new C2214iC(i);
        this.a.put(s10, c2214iC2);
        return c2214iC2;
    }

    @Override // com.android.tools.r8.internal.TB
    public final boolean b() {
        return false;
    }

    @Override // com.android.tools.r8.internal.TB
    public final boolean b(Object obj, int i) {
        C2214iC c2214iC = (C2214iC) this.a.get((S10) obj);
        boolean z = d;
        if (!z && !c2214iC.a()) {
            throw new AssertionError();
        }
        if (z) {
            return true;
        }
        if (!C2214iC.b && !c2214iC.a()) {
            throw new AssertionError();
        }
        if (i == c2214iC.a) {
            return true;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.TB
    public final Object a(Object obj) {
        return (C2214iC) this.a.get((S10) obj);
    }

    @Override // com.android.tools.r8.internal.TB
    public final void a(C3332x4 c3332x4, int i) {
        if (!d && this.b.containsKey(c3332x4)) {
            throw new AssertionError();
        }
        this.b.a(i, c3332x4);
        if (c3332x4.x().isEmpty()) {
            return;
        }
        int i2 = 0;
        for (ML ml : c3332x4.x()) {
            IdentityHashMap identityHashMap = this.a;
            int i3 = i2 + 1;
            if (i < 32768) {
                boolean z = C1589a6.a;
                if (!z && !C1589a6.a(i)) {
                    throw new AssertionError();
                }
                int i4 = (i & 65535) << 16;
                if (!z && !C1589a6.a(i2)) {
                    throw new AssertionError();
                }
                int i5 = (i2 & 65535) | Integer.MIN_VALUE | i4;
                if (!C2214iC.b && i5 >= 0) {
                    throw new AssertionError();
                }
                identityHashMap.put(ml, new C2214iC(i5));
                i2 = i3;
            } else {
                throw new Z00("No support for more than 15-bit block index.");
            }
        }
        this.c.add(Integer.valueOf(i));
        this.c.add(Integer.valueOf(i2));
    }

    @Override // com.android.tools.r8.internal.TB
    public final int a(C3332x4 c3332x4) {
        if (d || this.b.containsKey(c3332x4)) {
            return this.b.a(c3332x4);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.TB
    public final AbstractC2366kC a() {
        if (this.c.isEmpty()) {
            return C2055gC.b;
        }
        int[] iArr = new int[this.c.size()];
        for (int i = 0; i < this.c.size(); i++) {
            iArr[i] = ((Integer) this.c.get(i)).intValue();
        }
        return new C2055gC(iArr);
    }
}
