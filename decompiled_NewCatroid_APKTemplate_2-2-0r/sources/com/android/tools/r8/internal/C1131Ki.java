package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ki, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1131Ki extends VY {
    static final /* synthetic */ boolean r = true;
    public static final /* synthetic */ int s = 0;
    private final C0767v2 l;
    private final C0684j2 m;
    private final EnumC1953et n;
    private final boolean o;
    private final boolean p;
    private final boolean q;

    C1131Ki(C0767v2 c0767v2, C0684j2 c0684j2, C0684j2 c0684j3, C0767v2 c0767v3, C0684j2 c0684j4, EnumC1953et enumC1953et, AbstractC1915eM.c cVar, boolean z) {
        super(c0767v2, c0684j2, cVar, c0684j3);
        boolean z2 = r;
        if (!z2) {
            if ((c0767v3 == null) != (enumC1953et == EnumC1953et.e)) {
                throw new AssertionError();
            }
        }
        this.m = c0684j4;
        this.l = c0767v3;
        this.n = enumC1953et;
        this.p = z;
        this.o = false;
        this.q = false;
        if (!z2) {
            k();
        }
        int iOrdinal = enumC1953et.ordinal();
        if (iOrdinal != 0 && iOrdinal != 1 && iOrdinal != 2 && iOrdinal != 3 && iOrdinal != 4) {
            throw new Z00("Invoke type " + enumC1953et + " is not yet supported.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, List list2, C2102gl c2102gl) {
        EnumC1953et enumC1953et = this.n;
        C0684j2 c0684j2 = this.m;
        c2102gl.a(enumC1953et, c0684j2, c0684j2.h, list, list2, this.p);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(int i, C2102gl c2102gl) {
        c2102gl.a(i, this.b.e);
    }

    private void k() {
        ArrayList arrayList = new ArrayList();
        C0767v2 c0767v2 = this.a;
        if (c0767v2 != null) {
            arrayList.add(c0767v2);
        }
        arrayList.addAll(AbstractC2763pC.a(this.b.f.a));
        if (this.q) {
            arrayList.remove(arrayList.size() - 1);
        }
        ArrayList arrayList2 = new ArrayList();
        C0767v2 c0767v3 = this.l;
        if (c0767v3 != null) {
            arrayList2.add(c0767v3);
        }
        arrayList2.addAll(AbstractC2763pC.a(this.m.h.f.a));
        if (!r && arrayList.size() != arrayList2.size()) {
            throw new AssertionError();
        }
        for (int i = 0; i < arrayList.size(); i++) {
            C0767v2 c0767v4 = (C0767v2) arrayList.get(i);
            C0767v2 c0767v5 = (C0767v2) arrayList2.get(i);
            if (!r && ((!c0767v4.O0() || !c0767v5.O0()) && c0767v4 != c0767v5)) {
                throw new AssertionError();
            }
        }
        if (!r && this.b.e != this.m.h.e && !this.o) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.VY
    protected final void j() {
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        if (this.a != null) {
            arrayList.add(EnumC1812d20.OBJECT);
            arrayList2.add(Integer.valueOf(g()));
        }
        C0767v2[] c0767v2Arr = this.b.f.a;
        for (int i = 0; i < c0767v2Arr.length - com.android.tools.r8.utils.M.a(this.q); i++) {
            arrayList.add(EnumC1812d20.a(c0767v2Arr[i]));
            arrayList2.add(Integer.valueOf(h(i)));
        }
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Ki$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(arrayList, arrayList2, (C2102gl) obj);
            }
        };
        Predicate predicate = VY.i;
        a(consumer, predicate);
        if (this.b.e.Y0()) {
            a(new Y$$ExternalSyntheticLambda1(), predicate);
            return;
        }
        final int iA = a(EnumC1812d20.a(this.b.e));
        a(new Consumer() { // from class: com.android.tools.r8.internal.Ki$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C2102gl) obj).d(iA);
            }
        }, predicate);
        if (this.b.e != this.m.h.e) {
            a(new Consumer() { // from class: com.android.tools.r8.internal.Ki$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.c(iA, (C2102gl) obj);
                }
            }, predicate);
        }
        a(new Consumer() { // from class: com.android.tools.r8.internal.Ki$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C2102gl) obj).e(iA);
            }
        }, predicate);
    }
}
