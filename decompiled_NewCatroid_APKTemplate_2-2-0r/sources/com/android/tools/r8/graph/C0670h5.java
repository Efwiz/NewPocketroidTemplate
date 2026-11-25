package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C1549Za;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.graph.h5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0670h5 implements com.android.tools.r8.utils.structural.x<C0670h5> {
    static final /* synthetic */ boolean b = true;
    private final C0767v2 a;

    public C0670h5(C0767v2 c0767v2) {
        this.a = c0767v2;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.h5$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0670h5.a(zVar);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.h5$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0670h5) obj).a;
            }
        });
    }

    public C0767v2 a() {
        return this.a;
    }

    public final void a(C1549Za c1549Za, AbstractC3649o0 abstractC3649o0) {
        if (!b && this.a == null) {
            throw new AssertionError();
        }
        c1549Za.c(abstractC3649o0.d(this.a));
    }
}
