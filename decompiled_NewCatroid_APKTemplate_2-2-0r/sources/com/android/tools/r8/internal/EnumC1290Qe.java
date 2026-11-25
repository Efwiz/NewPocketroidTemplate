package com.android.tools.r8.internal;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.Qe, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class EnumC1290Qe {
    public static final EnumC1290Qe a = new EnumC1290Qe("WRAPPER", 0);
    public static final EnumC1290Qe b = new EnumC1290Qe("VIVIFIED_WRAPPER", 1);

    private EnumC1290Qe(String str, int i) {
        super(str, i);
    }

    final com.android.tools.r8.synthesis.C a() {
        return this == a ? new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Qe$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.o;
            }
        } : new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Qe$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.p;
            }
        };
    }
}
