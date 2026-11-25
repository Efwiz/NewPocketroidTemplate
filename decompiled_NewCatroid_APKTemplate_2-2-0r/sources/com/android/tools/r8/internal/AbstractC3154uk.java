package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3154uk {
    private static final char[] a = "0123456789abcdef".toCharArray();
    public static final /* synthetic */ int b = 0;

    AbstractC3154uk() {
    }

    abstract boolean a(AbstractC3154uk abstractC3154uk);

    public final boolean equals(Object obj) {
        if (!(obj instanceof AbstractC3154uk)) {
            return false;
        }
        AbstractC3154uk abstractC3154uk = (AbstractC3154uk) obj;
        return ((C3081tk) this).c.length * 8 == ((C3081tk) abstractC3154uk).c.length * 8 && a(abstractC3154uk);
    }

    public final int hashCode() {
        byte[] bArr = ((C3081tk) this).c;
        if (bArr.length * 8 < 32) {
            int i = bArr[0] & 255;
            for (int i2 = 1; i2 < bArr.length; i2++) {
                i |= (bArr[i2] & 255) << (i2 * 8);
            }
            return i;
        }
        boolean z = bArr.length >= 4;
        int length = bArr.length;
        int i3 = AbstractC3123uM.a;
        if (z) {
            return ((bArr[3] & 255) << 24) | (bArr[0] & 255) | ((bArr[1] & 255) << 8) | ((bArr[2] & 255) << 16);
        }
        throw new IllegalStateException(AbstractC2619nY.a("HashCode#asInt() requires >= 4 bytes (it only has %s bytes).", Integer.valueOf(length)));
    }

    public final String toString() {
        byte[] bArr = ((C3081tk) this).c;
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b2 : bArr) {
            char[] cArr = a;
            sb.append(cArr[(b2 >> 4) & 15]).append(cArr[b2 & 15]);
        }
        return sb.toString();
    }
}
