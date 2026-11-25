package com.android.tools.r8.internal;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class L20 {
    public static final L20 c;
    public static final L20 d;
    public static final L20 e;
    public static final L20 f;
    public static final L20 g;
    private final M20 a;
    private final int b;

    static {
        M20 m20 = M20.e;
        M20 m21 = M20.d;
        M20 m22 = M20.c;
        c = new L20("INT32", 4, M20.b, 0);
        d = new L20("BOOL", 7, M20.f, 0);
        new H20();
        e = new L20() { // from class: com.android.tools.r8.internal.I20
            {
                M20 m23 = M20.j;
            }

            @Override // com.android.tools.r8.internal.L20
            public final boolean c() {
                return false;
            }
        };
        f = new L20() { // from class: com.android.tools.r8.internal.J20
            {
                M20 m23 = M20.j;
            }

            @Override // com.android.tools.r8.internal.L20
            public final boolean c() {
                return false;
            }
        };
        new L20() { // from class: com.android.tools.r8.internal.K20
            {
                M20 m23 = M20.h;
            }

            @Override // com.android.tools.r8.internal.L20
            public final boolean c() {
                return false;
            }
        };
        g = new L20("ENUM", 13, M20.i, 0);
    }

    /* synthetic */ L20(String str, int i, M20 m20, int i2, int i3) {
        this(str, i, m20, i2);
    }

    public final M20 a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    public boolean c() {
        return !(this instanceof H20);
    }

    private L20(String str, int i, M20 m20, int i2) {
        super(str, i);
        this.a = m20;
        this.b = i2;
    }
}
