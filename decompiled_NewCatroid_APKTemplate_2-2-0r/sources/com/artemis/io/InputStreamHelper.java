package com.artemis.io;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes8.dex */
public class InputStreamHelper {
    private InputStreamHelper() {
    }

    public static void reset(InputStream inputStream) throws IOException {
        inputStream.reset();
    }

    public static boolean isMarkSupported(InputStream inputStream) {
        return inputStream.markSupported();
    }
}
