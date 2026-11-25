package com.android.tools.r8.internal;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class KL {
    public static final KL b = new KL(0, "JUST_MEMBERS", "-keepclassmembers");
    public static final KL c = new KL(1, "CLASS_OR_MEMBERS", "-keep");
    public static final KL d = new KL(2, "CLASS_AND_MEMBERS", "-keepclasseswithmembers");
    private final String a;

    private KL(int i, String str, String str2) {
        super(str, i);
        this.a = str2;
    }
}
