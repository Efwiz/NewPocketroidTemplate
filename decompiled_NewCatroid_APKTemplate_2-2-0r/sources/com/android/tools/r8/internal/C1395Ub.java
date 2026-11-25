package com.android.tools.r8.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ub, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1395Ub {
    private final Path a;

    public C1395Ub(Path path) {
        this.a = path;
    }

    public static C1395Ub a(Path path, Path path2) throws IOException {
        com.android.tools.r8.utils.P3.a(path, path2);
        return new C1395Ub(path2);
    }

    public Path b() {
        return this.a.resolve("library.jar");
    }

    public Path c() {
        return this.a.resolve("program.jar");
    }

    public Path d() {
        return this.a.resolve("proguard.config");
    }

    public C1036Hf a() throws IOException {
        if (!Files.exists(this.a.resolve("build.properties"), new LinkOption[0])) {
            return null;
        }
        C1036Hf.a aVar = new C1036Hf.a();
        C1036Hf.b(aVar, com.android.tools.r8.utils.X0.a(this.a.resolve("build.properties"), StandardCharsets.UTF_8));
        return aVar.a();
    }

    public void a(EN en) throws IOException {
        BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(d());
        try {
            for (String line = bufferedReaderNewBufferedReader.readLine(); line != null; line = bufferedReaderNewBufferedReader.readLine()) {
                en.d(line);
            }
            bufferedReaderNewBufferedReader.close();
        } catch (Throwable th) {
            if (bufferedReaderNewBufferedReader != null) {
                try {
                    bufferedReaderNewBufferedReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
