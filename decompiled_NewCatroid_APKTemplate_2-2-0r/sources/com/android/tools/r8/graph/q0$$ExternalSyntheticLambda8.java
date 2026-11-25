package com.android.tools.r8.graph;

import com.android.tools.r8.internal.InterfaceC3205vM;
import java.util.function.Predicate;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class q0$$ExternalSyntheticLambda8 implements InterfaceC3205vM {
    public final /* synthetic */ Predicate f$0;

    public /* synthetic */ q0$$ExternalSyntheticLambda8(Predicate predicate) {
        this.f$0 = predicate;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3205vM
    public final boolean apply(Object obj) {
        return this.f$0.test((W0) obj);
    }
}
