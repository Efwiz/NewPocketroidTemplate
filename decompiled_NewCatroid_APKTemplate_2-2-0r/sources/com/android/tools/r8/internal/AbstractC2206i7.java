package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.i7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2206i7 extends B7 {
    static final /* synthetic */ boolean d = true;
    private final com.android.tools.r8.graph.Y0 b;
    private final com.android.tools.r8.graph.Y0 c;

    public AbstractC2206i7(com.android.tools.r8.graph.Y0 y0) {
        this(y0, y0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.internal.i7$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((AbstractC2206i7) obj).U();
            }
        }).c(new Function() { // from class: com.android.tools.r8.internal.i7$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC2206i7) obj).getField();
            }
        }).c(new Function() { // from class: com.android.tools.r8.internal.i7$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC2206i7) obj).T();
            }
        });
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean E() {
        return true;
    }

    public final com.android.tools.r8.graph.Y0 T() {
        return this.c;
    }

    public abstract int U();

    public abstract AbstractC2206i7 a(com.android.tools.r8.graph.Y0 y0);

    public com.android.tools.r8.graph.Y0 getField() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2206i7 h() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean x() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return U();
    }

    public AbstractC2206i7(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        this.b = y0;
        this.c = y1;
        if (!d && y0.h != y1.h) {
            throw new AssertionError();
        }
    }

    public static AbstractC2206i7 a(int i, com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        switch (i) {
            case 178:
                return new O8(y0, y1);
            case 179:
                return new P8(y0, y1);
            case 180:
                return new C3413y7(y0, y1);
            case 181:
                return new C3482z7(y0, y1);
            default:
                throw new C3405y10("Unexpected opcode " + i);
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        return aVar.a(this, b7.h(), new C2207i7$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new C2207i7$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        com.android.tools.r8.graph.Y0 y0C = abstractC1476Wj.c((AbstractC1476Wj) null, this.b);
        com.android.tools.r8.graph.Y0 y0C2 = abstractC1476Wj.c((AbstractC1476Wj) null, this.c);
        abstractC1899eF.a(U(), abstractC3649o0.d(y0C.e), abstractC3649o0.a(y0C2).toString(), abstractC3649o0.c(y0C.h).toString());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }
}
