package com.android.tools.r8.synthesis;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface y {
    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ InterfaceC3862u a(InterfaceC3862u interfaceC3862u) {
        return interfaceC3862u;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ InterfaceC3862u a(w wVar) {
        return wVar;
    }

    static y b() {
        return new y() { // from class: com.android.tools.r8.synthesis.y$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.synthesis.y
            public final InterfaceC3862u a() {
                return y.c();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ InterfaceC3862u c() {
        return new v();
    }

    static y d() {
        final x xVar = new x();
        return new y() { // from class: com.android.tools.r8.synthesis.y$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.synthesis.y
            public final InterfaceC3862u a() {
                return y.a(xVar);
            }
        };
    }

    static y e() {
        final w wVar = new w();
        return new y() { // from class: com.android.tools.r8.synthesis.y$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.y
            public final InterfaceC3862u a() {
                return y.a(wVar);
            }
        };
    }

    InterfaceC3862u a();
}
