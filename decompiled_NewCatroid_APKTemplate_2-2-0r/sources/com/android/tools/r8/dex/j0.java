package com.android.tools.r8.dex;

import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3932m1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class j0 extends h0 {
    static final /* synthetic */ boolean k = true;
    private final ExecutorService i;
    private final AbstractC3135uW j;

    j0(C0548k c0548k, Collection collection, A1 a1, ExecutorService executorService, AbstractC3135uW abstractC3135uW) {
        super(c0548k, collection, a1, abstractC3135uW);
        this.i = executorService;
        this.j = abstractC3135uW;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(r0 r0Var) {
        return r0Var.b.a.size();
    }

    @Override // com.android.tools.r8.dex.g0
    public final ArrayList a() {
        boolean z = k;
        if (!z && this.c.size() != 1) {
            throw new AssertionError();
        }
        if (!z && !((r0) this.c.get(0)).m()) {
            throw new AssertionError();
        }
        int size = this.d.size();
        a(this.d);
        if (this.d.isEmpty()) {
            return this.c;
        }
        List listEmptyList = this.c;
        boolean zM = this.f.m();
        if (this.g.p1 && !zM) {
            if (!z && this.c.size() != 1) {
                throw new AssertionError();
            }
            if (!z && ((r0) this.c.get(0)).m()) {
                throw new AssertionError();
            }
            listEmptyList = Collections.emptyList();
        }
        Map mapB = b();
        C3932m1 c3932m1 = new C3932m1(1);
        if (zM || !this.g.q1) {
            new o0(this.c, listEmptyList, this.a, this.d, this.e, this.j, c3932m1).a();
        } else {
            new P(this.f, this.c, listEmptyList, this.d, c3932m1, this.a, this.i).a();
        }
        a(mapB, this.j);
        if (z || size == this.c.stream().mapToInt(new ToIntFunction() { // from class: com.android.tools.r8.dex.j0$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return j0.b((r0) obj);
            }
        }).sum()) {
            return this.c;
        }
        throw new AssertionError();
    }
}
