package com.android.tools.r8.internal;

import java.nio.charset.Charset;
import org.apache.commons.lang3.CharEncoding;

/* JADX INFO: renamed from: com.android.tools.r8.internal.n9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2592n9 {
    public static final Charset a;

    static {
        Charset.forName(CharEncoding.US_ASCII);
        Charset.forName("ISO-8859-1");
        a = Charset.forName("UTF-8");
        Charset.forName(CharEncoding.UTF_16BE);
        Charset.forName(CharEncoding.UTF_16LE);
        Charset.forName(CharEncoding.UTF_16);
    }
}
