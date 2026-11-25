package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C1944ek;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.EnumC1953et;
import net.bytebuddy.description.method.MethodDescription;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.graph.k2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EnumC0691k2 {
    private final short a;
    static final /* synthetic */ boolean l = true;
    public static final EnumC0691k2 b = new EnumC0691k2("STATIC_PUT", 0, 0);
    public static final EnumC0691k2 c = new EnumC0691k2("STATIC_GET", 1, 1);
    public static final EnumC0691k2 d = new EnumC0691k2("INSTANCE_PUT", 2, 2);
    public static final EnumC0691k2 e = new EnumC0691k2("INSTANCE_GET", 3, 3);
    public static final EnumC0691k2 f = new EnumC0691k2("INVOKE_STATIC", 4, 4);
    public static final EnumC0691k2 g = new EnumC0691k2("INVOKE_INSTANCE", 5, 5);
    public static final EnumC0691k2 h = new EnumC0691k2("INVOKE_CONSTRUCTOR", 6, 6);
    public static final EnumC0691k2 i = new EnumC0691k2("INVOKE_DIRECT", 7, 7);
    public static final EnumC0691k2 j = new EnumC0691k2("INVOKE_INTERFACE", 8, 8);
    public static final EnumC0691k2 k = new EnumC0691k2("INVOKE_SUPER", 9, 9);

    private EnumC0691k2(String str, int i2, short s) {
        super(str, i2);
        this.a = s;
    }

    public static EnumC0691k2 c(int i2) {
        EnumC0691k2 enumC0691k2;
        switch (i2) {
            case 0:
                enumC0691k2 = b;
                break;
            case 1:
                enumC0691k2 = c;
                break;
            case 2:
                enumC0691k2 = d;
                break;
            case 3:
                enumC0691k2 = e;
                break;
            case 4:
                enumC0691k2 = f;
                break;
            case 5:
                enumC0691k2 = g;
                break;
            case 6:
                enumC0691k2 = h;
                break;
            case 7:
                enumC0691k2 = i;
                break;
            case 8:
                enumC0691k2 = j;
                break;
            case 9:
                enumC0691k2 = k;
                break;
            default:
                throw new AssertionError();
        }
        if (l || enumC0691k2.a == i2) {
            return enumC0691k2;
        }
        throw new AssertionError();
    }

    public final short a() {
        return this.a;
    }

    public final boolean b() {
        return this == b || this == c || this == d || this == e;
    }

    public final boolean d() {
        return this == i;
    }

    public final boolean e() {
        return this == g;
    }

    public final boolean f() {
        return this == j;
    }

    public final boolean g() {
        return this == f;
    }

    public final boolean h() {
        return g() || e() || f() || this == k || c() || d();
    }

    public final EnumC1953et i() {
        if (!l && !h()) {
            throw new AssertionError();
        }
        switch (ordinal()) {
            case 4:
                return EnumC1953et.e;
            case 5:
                return EnumC1953et.g;
            case 6:
                return EnumC1953et.c;
            case 7:
                return EnumC1953et.c;
            case 8:
                return EnumC1953et.d;
            case 9:
                return EnumC1953et.f;
            default:
                throw new C3405y10("Conversion to invoke type with unexpected method handle: " + this);
        }
    }

    public static EnumC0691k2 a(C1944ek c1944ek, M3 m3, C0767v2 c0767v2) {
        switch (c1944ek.d()) {
            case 1:
                return e;
            case 2:
                return c;
            case 3:
                return d;
            case 4:
                return b;
            case 5:
                return g;
            case 6:
                return f;
            case 7:
                boolean z = l;
                if (!z && c1944ek.b().equals("<init>")) {
                    throw new AssertionError();
                }
                if (z || !c1944ek.b().equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                    return m3.f(c1944ek.c()) == c0767v2 ? i : k;
                }
                throw new AssertionError();
            case 8:
                return h;
            case 9:
                return j;
            default:
                throw new C3405y10("MethodHandle tag is not supported: " + c1944ek.d());
        }
    }

    public final boolean c() {
        return this == h;
    }
}
