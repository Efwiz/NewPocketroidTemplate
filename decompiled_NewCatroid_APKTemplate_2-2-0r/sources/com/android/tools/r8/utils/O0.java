package com.android.tools.r8.utils;

import com.android.tools.r8.internal.C1036Hf;
import java.nio.file.Path;
import java.nio.file.Paths;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class O0 {
    public static O0 a() {
        String property = System.getProperty("com.android.tools.r8.dumpinputtofile");
        if (property != null) {
            return b(Paths.get(property, new String[0]));
        }
        String property2 = System.getProperty("com.android.tools.r8.dumpinputtodirectory");
        return property2 != null ? a(Paths.get(property2, new String[0])) : new K0();
    }

    public static O0 b(Path path) {
        return new L0(path);
    }

    public static O0 c() {
        return new K0();
    }

    public abstract boolean a(C1036Hf c1036Hf);

    public abstract Path b();

    public abstract boolean d();

    public static O0 a(Path path) {
        return new M0(path);
    }
}
