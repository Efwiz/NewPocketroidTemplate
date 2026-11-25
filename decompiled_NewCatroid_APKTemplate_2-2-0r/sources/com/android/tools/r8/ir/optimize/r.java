package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.AbstractC2251ih;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.internal.S10;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class r extends AbstractC2251ih {
    static final /* synthetic */ boolean d = true;
    private final com.android.tools.r8.ir.regalloc.f a;
    private final CE.a b;
    private final int[] c;

    r(C2409kl c2409kl, com.android.tools.r8.ir.regalloc.f fVar) {
        this.a = fVar;
        this.b = c2409kl.k();
        int[] iArr = new int[c2409kl.l() + 1];
        this.c = iArr;
        Arrays.fill(iArr, -1);
    }

    private int b(C3332x4 c3332x4) {
        LinkedList<AbstractC2412ko> linkedListP = c3332x4.p();
        int size = linkedListP.size();
        for (int i = 0; i < linkedListP.size() && i < 5; i++) {
            AbstractC2412ko abstractC2412ko = linkedListP.get(i);
            int iA = (abstractC2412ko.b() == null || !abstractC2412ko.b().Y()) ? 0 : this.a.a(abstractC2412ko.b(), abstractC2412ko.W0());
            for (S10 s10 : abstractC2412ko.h1()) {
                iA <<= 4;
                if (s10.Y()) {
                    iA += this.a.a(s10, abstractC2412ko.W0());
                }
            }
            size = (size * 3) + iA;
        }
        return size;
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final int a(Object obj) {
        C3332x4 c3332x4 = (C3332x4) obj;
        int i = this.c[c3332x4.v()];
        if (i == -1) {
            int iB = b(c3332x4);
            this.c[c3332x4.v()] = iB;
            return iB;
        }
        if (d || i == b(c3332x4)) {
            return i;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.tools.r8.internal.AbstractC2251ih
    public final boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        LinkedList<AbstractC2412ko> linkedListP = c3332x4.p();
        LinkedList<AbstractC2412ko> linkedListP2 = c3332x5.p();
        if (linkedListP.size() != linkedListP2.size()) {
            return false;
        }
        for (int i = 0; i < linkedListP.size(); i++) {
            if (!linkedListP.get(i).a(linkedListP2.get(i), this.a, this.b)) {
                return false;
            }
        }
        if (!this.a.a(c3332x4, c3332x5) || !c3332x4.n().equals(c3332x5.n())) {
            return false;
        }
        if (!d) {
            List<C3332x4> listZ = c3332x4.z();
            List<C3332x4> listZ2 = c3332x5.z();
            if (listZ.size() == listZ2.size()) {
                for (int i2 = 0; i2 < listZ.size(); i2++) {
                    if (listZ.get(i2) == listZ2.get(i2)) {
                    }
                }
            }
            throw new AssertionError();
        }
        return true;
    }

    final void a(C3332x4 c3332x4) {
        this.c[c3332x4.v()] = -1;
    }
}
