package com.android.tools.r8.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class L3 {
    public static final L3 c;
    static final /* synthetic */ boolean d = true;
    private String a;
    private String b;

    private L3() {
    }

    public final String a() {
        String strB = b();
        String str = this.b;
        return "build " + strB + (str != null ? " from " + str : "");
    }

    public String b() {
        return c() ? "engineering" : this.a;
    }

    public final boolean c() {
        String str = this.a;
        return str == null || str.trim().isEmpty();
    }

    public final String toString() {
        return this.a + " from " + this.b;
    }

    static {
        L3 l3;
        try {
            InputStream resourceAsStream = L3.class.getClassLoader().getResourceAsStream("r8-version.properties");
            try {
                l3 = resourceAsStream == null ? new L3() : new L3(resourceAsStream);
                if (resourceAsStream != null) {
                    resourceAsStream.close();
                }
            } catch (Throwable th) {
                if (resourceAsStream != null) {
                    try {
                        resourceAsStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException unused) {
            l3 = new L3();
        }
        c = l3;
    }

    private L3(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        long j = Long.parseLong(properties.getProperty("version-file.version.code"));
        if (!d && j < 1) {
            throw new AssertionError();
        }
        this.a = properties.getProperty("version.sha");
        this.b = properties.getProperty("releaser");
    }
}
