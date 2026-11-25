package com.android.tools.r8.graph;

import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.w4$$ExternalSyntheticLambda0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class C0793w4$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ List f$0;

    public /* synthetic */ C0793w4$$ExternalSyntheticLambda0(List list) {
        this.f$0 = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.add((W0) obj);
    }
}
