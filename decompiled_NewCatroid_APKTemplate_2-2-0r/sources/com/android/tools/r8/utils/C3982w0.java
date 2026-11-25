package com.android.tools.r8.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/* JADX INFO: renamed from: com.android.tools.r8.utils.w0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3982w0 implements InterfaceC3996z0 {
    private int a = 0;
    final /* synthetic */ Path b;

    C3982w0(Path path) {
        this.b = path;
    }

    public final InterfaceC3986x0 a() {
        Path path = this.b;
        int i = this.a;
        this.a = i + 1;
        Path pathResolve = path.resolve(i + ".log");
        if (Files.exists(pathResolve, new LinkOption[0])) {
            System.out.println("Checking against determinism log: " + pathResolve);
            return new C3990y0(Files.newBufferedReader(pathResolve, StandardCharsets.UTF_8));
        }
        System.out.println("Writing determinism log: " + pathResolve);
        return new A0(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathResolve.toFile()), StandardCharsets.UTF_8)));
    }
}
