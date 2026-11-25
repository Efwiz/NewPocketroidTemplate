package com.android.tools.r8.ir.regalloc;

import com.android.tools.r8.internal.C3405y10;
import java.util.Arrays;
import java.util.BitSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class k extends j {
    static final /* synthetic */ boolean g = true;
    private final int a;
    private int[] b = new int[16];
    private final BitSet c;
    private final BitSet d;
    private final BitSet e;
    private final BitSet f;

    public k(int i) {
        this.a = i;
        for (int i2 = 0; i2 < 16; i2++) {
            this.b[i2] = Integer.MAX_VALUE;
        }
        this.c = new BitSet(i);
        this.d = new BitSet(i);
        this.e = new BitSet(i);
        this.f = new BitSet(i);
    }

    @Override // com.android.tools.r8.ir.regalloc.j
    public final boolean a(int i, i iVar) {
        if (!g && this.f.get(i)) {
            throw new AssertionError();
        }
        int iOrdinal = iVar.ordinal();
        if (iOrdinal == 0) {
            return this.d.get(i);
        }
        if (iOrdinal == 1) {
            return this.c.get(i);
        }
        if (iOrdinal == 2) {
            return (this.d.get(i) || this.c.get(i) || this.e.get(i)) ? false : true;
        }
        if (iOrdinal == 3) {
            return true;
        }
        throw new C3405y10("Unexpected register position type: " + iVar);
    }

    @Override // com.android.tools.r8.ir.regalloc.j
    public final boolean b(int i) {
        return this.f.get(i);
    }

    public final void c(int i) {
        this.f.set(i);
    }

    public final void a(int i, int i2, c cVar) {
        int[] iArr = this.b;
        if (i >= iArr.length) {
            int i3 = i + 1;
            int length = iArr.length;
            while (length < i3) {
                length *= 2;
            }
            int iMin = Math.min(length, this.a);
            int[] iArr2 = this.b;
            this.b = Arrays.copyOf(iArr2, iMin);
            for (int length2 = iArr2.length; length2 < iMin; length2++) {
                this.b[length2] = Integer.MAX_VALUE;
            }
        }
        this.b[i] = i2;
        this.c.set(i, cVar.v());
        this.d.set(i, cVar.G());
        this.e.set(i, cVar.x());
    }

    @Override // com.android.tools.r8.ir.regalloc.j
    public final int a(int i) {
        boolean z = g;
        if (!z && this.f.get(i)) {
            throw new AssertionError();
        }
        int[] iArr = this.b;
        if (i < iArr.length) {
            return iArr[i];
        }
        if (z || i < this.a) {
            return Integer.MAX_VALUE;
        }
        throw new AssertionError();
    }

    public final int a() {
        return this.a;
    }
}
