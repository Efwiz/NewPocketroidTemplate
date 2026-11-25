package com.android.tools.r8.internal;

import com.android.tools.r8.profile.art.ArtProfileProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class I3 {
    public static ArtProfileProvider a(Path path) {
        return new E3(path);
    }

    public static String a(ArtProfileProvider artProfileProvider) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);
        try {
            artProfileProvider.getArtProfile(new H3(outputStreamWriter));
            outputStreamWriter.close();
            return byteArrayOutputStream.toString();
        } catch (Throwable th) {
            try {
                outputStreamWriter.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(OutputStreamWriter outputStreamWriter, String str) {
        try {
            outputStreamWriter.write(str);
            outputStreamWriter.write(10);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
