package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0684j2;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1678bF extends AbstractC1598aF {
    private C1678bF(ConcurrentHashMap concurrentHashMap) {
        super(concurrentHashMap);
    }

    @Override // com.android.tools.r8.internal.AbstractC1598aF
    public /* bridge */ /* synthetic */ YE a(Object obj) {
        return super.a(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1598aF
    final AbstractC0717o2 b(Object obj) {
        return ((C0684j2) obj).J0();
    }

    @Override // com.android.tools.r8.internal.AbstractC1598aF
    final Object a(com.android.tools.r8.graph.o5 o5Var) {
        return o5Var.getReference();
    }

    public static C1678bF b() {
        return new C1678bF(new ConcurrentHashMap());
    }

    public final YE b(com.android.tools.r8.graph.o5 o5Var) {
        return a(o5Var.getReference());
    }
}
