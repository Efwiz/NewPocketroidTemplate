package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class Wj$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ List f$0;

    public /* synthetic */ Wj$$ExternalSyntheticLambda2(List list) {
        this.f$0 = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.add((C0684j2) obj);
    }
}
