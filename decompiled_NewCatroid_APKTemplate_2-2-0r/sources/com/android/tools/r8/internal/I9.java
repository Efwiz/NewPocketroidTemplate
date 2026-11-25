package com.android.tools.r8.internal;

import com.android.tools.r8.tracereferences.TraceReferencesConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class I9 extends E1 implements TraceReferencesConsumer.ClassAccessFlags {
    I9(com.android.tools.r8.graph.E e) {
        super(e);
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.ClassAccessFlags
    public final boolean isEnum() {
        return ((com.android.tools.r8.graph.E) this.a).V();
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.ClassAccessFlags
    public final boolean isInterface() {
        return ((com.android.tools.r8.graph.E) this.a).W();
    }
}
