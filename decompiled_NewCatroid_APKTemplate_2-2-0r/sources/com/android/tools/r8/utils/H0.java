package com.android.tools.r8.utils;

import com.android.tools.r8.internal.C3405y10;
import java.util.Optional;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public enum H0 implements com.android.tools.r8.utils.structural.s<H0> {
    V35(35, new byte[]{48, 51, 53}, 1),
    V37(37, new byte[]{48, 51, 55}, 1),
    V38(38, new byte[]{48, 51, 56}, 1),
    V39(39, new byte[]{48, 51, 57}, 1),
    V40(40, new byte[]{48, 52, 48}, 1),
    V41(41, new byte[]{48, 52, 49}, 2);

    static final /* synthetic */ boolean k = true;
    private final int a;
    private final byte[] b;
    private final int c;

    H0(int i, byte[] bArr, int i2) {
        this.a = i;
        this.b = bArr;
        this.c = i2;
    }

    public byte[] a() {
        return this.b;
    }

    public final int b() {
        return this.a;
    }

    public boolean c() {
        return this.c == 2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.s
    public final /* bridge */ /* synthetic */ int compareTo(com.android.tools.r8.utils.structural.s sVar) {
        return compareTo((Enum) sVar);
    }

    public static Optional<H0> a(char c, char c2, char c3) {
        if (c != '0') {
            return Optional.empty();
        }
        for (H0 h0 : values()) {
            if (!k && h0.a()[0] != 48) {
                throw new AssertionError();
            }
            if (h0.a()[2] == c3 && h0.a()[1] == c2) {
                return Optional.of(h0);
            }
        }
        return Optional.empty();
    }

    public static H0 c(EnumC3891f enumC3891f) {
        switch (G0.a[enumC3891f.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return V39;
            case 10:
            case 11:
                return V38;
            case 12:
            case 13:
                return V37;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
                return V35;
            default:
                throw new C3405y10("Unsupported api level " + enumC3891f);
        }
    }

    public final boolean d(EnumC3891f enumC3891f) {
        return c(enumC3891f).a >= this.a;
    }

    public static Optional<H0> c(int i) {
        switch (i) {
            case 35:
                return Optional.of(V35);
            case 36:
            default:
                return Optional.empty();
            case 37:
                return Optional.of(V37);
            case 38:
                return Optional.of(V38);
            case 39:
                return Optional.of(V39);
            case 40:
                return Optional.of(V40);
            case 41:
                return Optional.of(V41);
        }
    }
}
