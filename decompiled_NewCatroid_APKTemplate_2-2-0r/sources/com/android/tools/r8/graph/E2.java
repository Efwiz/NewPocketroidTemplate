package com.android.tools.r8.graph;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class E2 {
    public static final E2 b = new E2("BYTE", 0, 0);
    public static final E2 c = new E2("SHORT", 1, 2);
    public static final E2 d = new E2("CHAR", 2, 3);
    public static final E2 e = new E2("INT", 3, 4);
    public static final E2 f = new E2("LONG", 4, 6);
    public static final E2 g = new E2("FLOAT", 5, 16);
    public static final E2 h = new E2("DOUBLE", 6, 17);
    public static final E2 i = new E2("METHOD_TYPE", 7, 21);
    public static final E2 j = new E2("METHOD_HANDLE", 8, 22);
    public static final E2 k = new E2("STRING", 9, 23);
    public static final E2 l = new E2("TYPE", 10, 24);
    public static final E2 m = new E2("FIELD", 11, 25);
    public static final E2 n = new E2("METHOD", 12, 26);
    public static final E2 o = new E2("ENUM", 13, 27);
    public static final E2 p = new E2("ARRAY", 14, 28);
    public static final E2 q = new E2("ANNOTATION", 15, 29);
    public static final E2 r = new E2("NULL", 16, 30);
    public static final E2 s = new E2("BOOLEAN", 17, 31);
    private final byte a;

    private E2(String str, int i2, int i3) {
        super(str, i2);
        this.a = (byte) i3;
    }

    final byte a() {
        return this.a;
    }
}
