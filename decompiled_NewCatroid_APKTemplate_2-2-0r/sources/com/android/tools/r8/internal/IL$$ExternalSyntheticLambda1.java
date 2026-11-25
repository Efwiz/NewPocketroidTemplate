package com.android.tools.r8.internal;

import java.util.function.Supplier;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class IL$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ IL f$0;

    public /* synthetic */ IL$$ExternalSyntheticLambda1(IL il) {
        this.f$0 = il;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return Integer.valueOf(this.f$0.e());
    }
}
