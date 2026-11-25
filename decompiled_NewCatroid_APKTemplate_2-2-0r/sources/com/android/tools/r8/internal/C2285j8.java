package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

/* JADX INFO: renamed from: com.android.tools.r8.internal.j8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2285j8 extends AbstractC2360k8 {
    private final List a;

    public C2285j8(ArrayList arrayList) {
        this.a = arrayList;
    }

    @Override // com.android.tools.r8.internal.AbstractC2360k8
    public final void a(Collection collection, AbstractC2591n8 abstractC2591n8, ExecutorService executorService) {
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            ((InterfaceC2135h8) it.next()).a(collection, abstractC2591n8, executorService);
        }
    }
}
