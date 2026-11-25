package org.jetbrains.kotlin.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes3.dex */
public class KotlinCompilerVersion {
    public static final String VERSION;
    public static final String VERSION_FILE_PATH = "/META-INF/compiler.version";

    public static String getVersion() {
        String str = VERSION;
        if (str.equals("@snapshot@")) {
            return null;
        }
        return str;
    }

    private static String loadKotlinCompilerVersion() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(KotlinCompilerVersion.class.getResourceAsStream(VERSION_FILE_PATH)));
        try {
            return bufferedReader.readLine();
        } finally {
            bufferedReader.close();
        }
    }

    static {
        try {
            VERSION = loadKotlinCompilerVersion();
        } catch (IOException unused) {
            throw new IllegalStateException("Failed to read compiler version from /META-INF/compiler.version");
        }
    }
}
