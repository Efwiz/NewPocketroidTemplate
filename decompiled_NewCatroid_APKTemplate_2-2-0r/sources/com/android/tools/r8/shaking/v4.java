package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC3473z1;
import com.android.tools.r8.internal.C1103Ji;
import com.android.tools.r8.internal.C1131Ki;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.internal.InterfaceC3404y1;
import com.android.tools.r8.internal.KV;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class v4 extends AbstractC3473z1 {
    static final /* synthetic */ boolean i = true;
    private C0684j2 d;
    private C0684j2 e;
    private C0684j2 f;
    private EnumC1953et g;
    private final boolean h;

    public v4(C0684j2 c0684j2, C0684j2 c0684j3, C0684j2 c0684j4, EnumC1953et enumC1953et, boolean z) {
        this.d = c0684j2;
        this.e = c0684j3;
        this.f = c0684j4;
        this.g = enumC1953et;
        this.h = z;
    }

    @Override // com.android.tools.r8.internal.AbstractC3473z1
    public final Consumer H0() {
        return new Consumer() { // from class: com.android.tools.r8.shaking.v4$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((J5) obj);
            }
        };
    }

    @Override // com.android.tools.r8.internal.AbstractC3473z1
    public final InterfaceC3404y1 I0() {
        C0684j2 c0684j2 = this.d;
        int i2 = C1131Ki.s;
        final C1103Ji c1103Ji = new C1103Ji(c0684j2);
        c1103Ji.a(c0684j2.e).a(this.e).b(this.g.d() ? null : this.d.e).b(this.f).a(this.g).a(this.h);
        return new InterfaceC3404y1() { // from class: com.android.tools.r8.shaking.v4$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3404y1
            public final KV a(o5 o5Var, AbstractC1915eM abstractC1915eM) {
                return this.f$0.a(c1103Ji, o5Var, abstractC1915eM);
            }
        };
    }

    public final void a(Function function) {
        this.d = (C0684j2) function.apply(this.d);
        this.f = (C0684j2) function.apply(this.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ KV a(C1103Ji c1103Ji, o5 o5Var, AbstractC1915eM abstractC1915eM) {
        return c1103Ji.a(((AbstractC1915eM.c.a) ((AbstractC1915eM.c.a) AbstractC1915eM.c.x().a(0).a(this.d).a(true)).a(abstractC1915eM)).a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(J5 j5) {
        if (!i && !j5.e().h()) {
            throw new AssertionError();
        }
        int iOrdinal = this.g.ordinal();
        if (iOrdinal == 0) {
            j5.a(this.f);
            return;
        }
        if (iOrdinal == 2) {
            j5.e(this.f);
        } else if (iOrdinal == 4) {
            j5.h(this.f);
        } else {
            throw new C3405y10("Unexpected invocation type: " + this.g);
        }
    }
}
