package com.android.tools.r8.internal;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class BM implements InterfaceC3205vM {
    public static final C3351xM a = new C3351xM();
    public static final C3430yM b = new C3430yM();

    static {
        new BM() { // from class: com.android.tools.r8.internal.zM
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return obj == null;
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "Predicates.isNull()";
            }
        };
        new BM() { // from class: com.android.tools.r8.internal.AM
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return obj != null;
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "Predicates.notNull()";
            }
        };
    }

    private BM(String str, int i) {
        super(str, i);
    }

    /* synthetic */ BM(String str, int i, int i2) {
        this(str, i);
    }
}
