package com.android.tools.r8.internal;

import java.util.function.Consumer;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class CN$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ CN f$0;

    public /* synthetic */ CN$$ExternalSyntheticLambda2(CN cn) {
        this.f$0 = cn;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.add((com.android.tools.r8.graph.o5) obj);
    }
}
