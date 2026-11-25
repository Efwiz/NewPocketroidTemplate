package com.android.tools.r8.utils;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class H1 {
    public static final H1 a = new H1("DexIndexed", 0);
    public static final H1 b = new H1("DexFilePerClassFile", 1);
    public static final H1 c = new H1("ClassFile", 2);

    private H1(String str, int i) {
        super(str, i);
    }

    public final boolean a() {
        return this == c;
    }

    public final boolean b() {
        return this != c;
    }
}
