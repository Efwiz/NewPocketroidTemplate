package com.android.tools.r8.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1872dv {
    public static final C2772pL a(AbstractC0958Ej abstractC0958Ej, Q20 q20) throws IOException {
        AbstractC0936Ds.c(q20, "c");
        C1648av c1648avA = q20.a();
        AbstractC0936Ds.a((Object) c1648avA, "null cannot be cast to non-null type org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable");
        int i = C1569Zu.b;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        c1648avA.a(byteArrayOutputStream);
        abstractC0958Ej.a((OutputStream) byteArrayOutputStream);
        return new C2772pL(Z4.a(byteArrayOutputStream.toByteArray()), c1648avA.a().toArray(new String[0]));
    }
}
