package com.android.tools.r8.graph;

import java.util.function.Function;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.graph.b0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EnumC0608b0 {
    public static final EnumC0608b0 a = new EnumC0608b0("FIELD", 0);
    public static final EnumC0608b0 b = new EnumC0608b0("METHOD", 1);
    public static final EnumC0608b0 c = new EnumC0608b0("TYPE", 2);
    public static final EnumC0608b0 d = new EnumC0608b0("PARAMETER", 3);

    private EnumC0608b0(String str, int i) {
        super(str, i);
    }

    public static EnumC0608b0 a(InterfaceC0694k5 interfaceC0694k5) {
        return a(interfaceC0694k5.getReference());
    }

    public static EnumC0608b0 a(AbstractC0744s2 abstractC0744s2) {
        return (EnumC0608b0) abstractC0744s2.a(new Function() { // from class: com.android.tools.r8.graph.b0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return EnumC0608b0.a((C0767v2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.graph.b0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return EnumC0608b0.a((Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.graph.b0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return EnumC0608b0.a((C0684j2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ EnumC0608b0 a(C0767v2 c0767v2) {
        return c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ EnumC0608b0 a(Y0 y0) {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ EnumC0608b0 a(C0684j2 c0684j2) {
        return b;
    }
}
