package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import java.util.Collection;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.se, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3005se extends C3224ve {
    final /* synthetic */ C3075te b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3005se(C3075te c3075te) {
        super();
        this.b = c3075te;
    }

    @Override // com.android.tools.r8.internal.C3224ve
    public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        InterfaceC2864qe interfaceC2864qe = this.b.b;
        if (interfaceC2864qe == null) {
            return null;
        }
        return interfaceC2864qe.a(interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
    }

    @Override // com.android.tools.r8.internal.C3224ve
    public final boolean b() {
        return this.b.b != null;
    }

    @Override // com.android.tools.r8.internal.C3224ve
    public final void d() {
        this.b.a.forEach(new Consumer() { // from class: com.android.tools.r8.internal.se$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((InterfaceC3147ue) obj).a();
            }
        });
    }
}
