package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0657g;
import com.android.tools.r8.tracereferences.TraceReferencesConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class E1 implements TraceReferencesConsumer.AccessFlags {
    AbstractC0657g a;

    E1(AbstractC0657g abstractC0657g) {
        this.a = abstractC0657g;
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.AccessFlags
    public final boolean isPrivate() {
        return this.a.j();
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.AccessFlags
    public final boolean isProtected() {
        return this.a.l();
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.AccessFlags
    public final boolean isPublic() {
        return this.a.m();
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.AccessFlags
    public final boolean isStatic() {
        return this.a.o();
    }
}
