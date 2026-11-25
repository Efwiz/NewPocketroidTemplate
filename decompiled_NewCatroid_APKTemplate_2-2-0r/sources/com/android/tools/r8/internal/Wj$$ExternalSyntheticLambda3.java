package com.android.tools.r8.internal;

import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class Wj$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ List f$0;

    public /* synthetic */ Wj$$ExternalSyntheticLambda3(List list) {
        this.f$0 = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.add((com.android.tools.r8.graph.Y0) obj);
    }
}
