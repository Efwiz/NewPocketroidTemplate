package com.android.tools.r8.internal;

import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xi, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3382xi {
    public static final C3152ui a = new C3152ui(0);
    public static final C3152ui b = new C3152ui(1);
    public static final C3152ui c = new C3152ui(0);
    public static final AbstractC3305wi d;
    public static final AbstractC3305wi e;
    public static final AbstractC3305wi f;
    public static final C3152ui g;
    public static final C3152ui h;
    public static final C3152ui i;
    public static final C3152ui j;
    public static final C3152ui k;
    public static final C3152ui l;
    public static final C3152ui m;
    public static final C3152ui n;
    public static final C3152ui o;
    public static final C3152ui p;
    public static final C3152ui q;
    public static final C3152ui r;
    public static final C3152ui s;
    public static final C3152ui t;
    public static final C3152ui u;
    public static final C3152ui v;
    public static final C3152ui w;

    static {
        C3228vi c3228vi = new C3228vi(1, EnumC2988sP.values());
        d = c3228vi;
        EnumC3502zO[] enumC3502zOArrValues = EnumC3502zO.values();
        int i2 = c3228vi.b + 1;
        C3228vi c3228vi2 = new C3228vi(i2, enumC3502zOArrValues);
        e = c3228vi2;
        new C3228vi(c3228vi2.b + i2, EnumC1690bO.values());
        EnumC3432yO[] enumC3432yOArrValues = EnumC3432yO.values();
        int i3 = c3228vi2.b + i2;
        C3228vi c3228vi3 = new C3228vi(i3, enumC3432yOArrValues);
        f = c3228vi3;
        int i4 = i3 + c3228vi3.b;
        int i5 = i4 + 2;
        g = new C3152ui(i5);
        h = new C3152ui(i4);
        i = new C3152ui(i4 + 1);
        j = new C3152ui(i5);
        k = new C3152ui(i4 + 3);
        l = new C3152ui(i4 + 4);
        m = new C3152ui(i4 + 5);
        n = new C3152ui(i4 + 6);
        o = new C3152ui(i4 + 7);
        p = new C3152ui(i4 + 8);
        q = new C3152ui(1);
        r = new C3152ui(2);
        s = new C3152ui(3);
        int i6 = i2 + c3228vi2.b;
        t = new C3152ui(i6);
        u = new C3152ui(i6 + 1);
        v = new C3152ui(i6 + 2);
        w = new C3152ui(0);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0021  */
    /* JADX WARN: Code duplicated, block: B:18:0x002b  */
    private static /* synthetic */ void a(int i2) {
        Object[] objArr = new Object[3];
        if (i2 == 1) {
            objArr[0] = "modality";
        } else if (i2 == 2) {
            objArr[0] = Namer.METADATA_CLASS_KIND;
        } else if (i2 == 5) {
            objArr[0] = "modality";
        } else if (i2 == 6) {
            objArr[0] = "memberKind";
        } else if (i2 == 8) {
            objArr[0] = "modality";
        } else if (i2 == 9) {
            objArr[0] = "memberKind";
        } else if (i2 != 11) {
            objArr[0] = "visibility";
        } else {
            objArr[0] = "modality";
        }
        objArr[1] = "kotlinx/metadata/internal/metadata/deserialization/Flags";
        switch (i2) {
            case 3:
                objArr[2] = "getConstructorFlags";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "getFunctionFlags";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "getPropertyFlags";
                break;
            case 10:
            case 11:
                objArr[2] = "getAccessorFlags";
                break;
            default:
                objArr[2] = "getClassFlags";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static int a(boolean z, EnumC2988sP enumC2988sP, EnumC3502zO enumC3502zO) {
        if (enumC2988sP == null) {
            a(10);
            throw null;
        }
        if (enumC3502zO == null) {
            a(11);
            throw null;
        }
        int iA = c.a(Boolean.valueOf(z));
        C3228vi c3228vi = (C3228vi) e;
        c3228vi.getClass();
        int iA2 = iA | (enumC3502zO.a() << c3228vi.a);
        C3228vi c3228vi2 = (C3228vi) d;
        c3228vi2.getClass();
        int iA3 = iA2 | (enumC2988sP.a() << c3228vi2.a);
        C3152ui c3152ui = t;
        Boolean bool = Boolean.FALSE;
        return iA3 | c3152ui.a(bool) | u.a(bool) | v.a(bool);
    }
}
