package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0767v2;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.dex.k$$ExternalSyntheticLambda21, reason: case insensitive filesystem */
/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class C0563k$$ExternalSyntheticLambda21 implements Consumer {
    public final /* synthetic */ List f$0;

    public /* synthetic */ C0563k$$ExternalSyntheticLambda21(List list) {
        this.f$0 = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.add((C0767v2) obj);
    }
}
