package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.C3332x4;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class c0$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ Set f$0;

    public /* synthetic */ c0$$ExternalSyntheticLambda2(Set set) {
        this.f$0 = set;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return this.f$0.contains((C3332x4) obj);
    }
}
