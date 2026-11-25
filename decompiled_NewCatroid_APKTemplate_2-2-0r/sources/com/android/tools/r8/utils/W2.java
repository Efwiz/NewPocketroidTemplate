package com.android.tools.r8.utils;

import com.android.tools.r8.internal.V5;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class W2 {
    public static byte[] a(InputStream inputStream) throws IOException {
        byte[] bArrA = V5.a(inputStream);
        inputStream.close();
        return bArrA;
    }
}
