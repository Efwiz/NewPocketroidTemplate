package com.android.tools.r8.internal;

import java.io.IOException;
import java.math.RoundingMode;

/* JADX INFO: renamed from: com.android.tools.r8.internal.p4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2753p4 {
    private static final AbstractC2753p4 a;

    static {
        new C2587n4("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", '=');
        a = new C2587n4("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", '=');
        new C2671o4(new C2436l4("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray()), '=');
        new C2671o4(new C2436l4("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV".toCharArray()), '=');
        new C2513m4();
    }

    AbstractC2753p4() {
    }

    public final String a(byte[] bArr) {
        int length = bArr.length;
        AbstractC3123uM.a(0, length, bArr.length);
        C2436l4 c2436l4 = ((C2671o4) this).b;
        StringBuilder sb = new StringBuilder(AbstractC1112Jr.a(length, c2436l4.f, RoundingMode.CEILING) * c2436l4.e);
        try {
            a(sb, bArr, length);
            return sb.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    abstract void a(StringBuilder sb, byte[] bArr, int i);

    public abstract AbstractC2753p4 b();

    public static AbstractC2753p4 a() {
        return a;
    }
}
