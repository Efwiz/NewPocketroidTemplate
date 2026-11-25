package com.android.tools.r8.internal;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class M20 {
    public static final M20 b = new M20("INT", 0, 0);
    public static final M20 c = new M20("LONG", 1, 0L);
    public static final M20 d = new M20("FLOAT", 2, Float.valueOf(0.0f));
    public static final M20 e = new M20("DOUBLE", 3, Double.valueOf(0.0d));
    public static final M20 f = new M20("BOOLEAN", 4, Boolean.FALSE);
    public static final M20 g = new M20("STRING", 5, "");
    public static final M20 h = new M20("BYTE_STRING", 6, Y5.a);
    public static final M20 i = new M20("ENUM", 7, null);
    public static final M20 j = new M20("MESSAGE", 8, null);
    private final Object a;

    private M20(String str, int i2, Object obj) {
        super(str, i2);
        this.a = obj;
    }
}
