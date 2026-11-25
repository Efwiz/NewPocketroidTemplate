package com.android.tools.r8.retrace;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ProguardMapProducer {
    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ InputStream a(String str) {
        return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    }

    static ProguardMapProducer fromBytes(final byte[]... bArr) {
        return new ProguardMapProducer() { // from class: com.android.tools.r8.retrace.ProguardMapProducer$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.retrace.ProguardMapProducer
            public final InputStream get() {
                return ProguardMapProducer.a(bArr);
            }
        };
    }

    static ProguardMapProducer fromPath(Path path) {
        return new a(path);
    }

    static ProguardMapProducer fromString(final String str) {
        return new ProguardMapProducer() { // from class: com.android.tools.r8.retrace.ProguardMapProducer$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.retrace.ProguardMapProducer
            public final InputStream get() {
                return ProguardMapProducer.a(str);
            }
        };
    }

    InputStream get() throws IOException;

    default Path getPath() throws FileNotFoundException {
        return null;
    }

    default boolean isFileBacked() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static InputStream a(byte[][] bArr) {
        int length = 0;
        for (byte[] bArr2 : bArr) {
            length += bArr2.length;
        }
        byte[] bArr3 = new byte[length];
        int length2 = 0;
        for (byte[] bArr4 : bArr) {
            System.arraycopy(bArr4, 0, bArr3, length2, bArr4.length);
            length2 += bArr4.length;
        }
        return new ByteArrayInputStream(bArr3);
    }
}
