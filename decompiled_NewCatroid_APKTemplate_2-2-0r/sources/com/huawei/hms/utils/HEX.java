package com.huawei.hms.utils;

import net.bytebuddy.asm.Advice;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes10.dex */
public final class HEX {
    public static final char[] a = {ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, 'e', 'f'};
    public static final char[] b = {ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', 'A', org.eclipse.jdt.internal.compiler.util.Util.C_BYTE, org.eclipse.jdt.internal.compiler.util.Util.C_CHAR, org.eclipse.jdt.internal.compiler.util.Util.C_DOUBLE, 'E', org.eclipse.jdt.internal.compiler.util.Util.C_FLOAT};

    public static char[] a(byte[] bArr, char[] cArr) {
        char[] cArr2 = new char[bArr.length << 1];
        int i = 0;
        for (byte b2 : bArr) {
            int i2 = i + 1;
            cArr2[i] = cArr[(b2 & 240) >>> 4];
            i += 2;
            cArr2[i2] = cArr[b2 & 15];
        }
        return cArr2;
    }

    public static char[] encodeHex(byte[] bArr) {
        return encodeHex(bArr, false);
    }

    public static String encodeHexString(byte[] bArr, boolean z) {
        return new String(encodeHex(bArr, z));
    }

    public static char[] encodeHex(byte[] bArr, boolean z) {
        return a(bArr, z ? b : a);
    }
}
