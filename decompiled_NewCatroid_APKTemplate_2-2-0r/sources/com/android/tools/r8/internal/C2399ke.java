package com.android.tools.r8.internal;

import com.android.tools.r8.InputDependencyGraphConsumer;
import com.android.tools.r8.origin.Origin;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ke, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2399ke implements InputDependencyGraphConsumer {
    private final Path a;
    private final String b;
    private final HashSet c = new HashSet();

    public C2399ke(String str, Path path) {
        this.a = path;
        this.b = str;
    }

    @Override // com.android.tools.r8.InputDependencyGraphConsumer
    public final void accept(Origin origin, Path path) {
        this.c.add(path);
    }

    @Override // com.android.tools.r8.InputDependencyGraphConsumer
    public final void finished() {
        ArrayList<Path> arrayList = new ArrayList(this.c);
        arrayList.sort(new Comparator() { // from class: com.android.tools.r8.internal.ke$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((Path) obj).compareTo((Path) obj2);
            }
        });
        try {
            BufferedWriter bufferedWriterNewBufferedWriter = Files.newBufferedWriter(Paths.get(this.b, new String[0]), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            try {
                bufferedWriterNewBufferedWriter.write(this.a.toString().replace(" ", "\\ "));
                bufferedWriterNewBufferedWriter.write(":");
                for (Path path : arrayList) {
                    bufferedWriterNewBufferedWriter.write(" ");
                    bufferedWriterNewBufferedWriter.write(path.toString().replace(" ", "\\ "));
                }
                bufferedWriterNewBufferedWriter.write("\n");
                bufferedWriterNewBufferedWriter.close();
            } catch (Throwable th) {
                if (bufferedWriterNewBufferedWriter != null) {
                    try {
                        bufferedWriterNewBufferedWriter.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
