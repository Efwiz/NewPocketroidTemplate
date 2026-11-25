package com.android.tools.r8.internal;

import java.util.Map;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class LD implements InterfaceC3010sj {
    public static final KD a;

    static {
        new LD() { // from class: com.android.tools.r8.internal.JD
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Map.Entry) obj).getKey();
            }
        };
        a = new KD();
    }

    private LD(String str, int i) {
        super(str, i);
    }

    /* synthetic */ LD(String str, int i, int i2) {
        this(str, i);
    }
}
