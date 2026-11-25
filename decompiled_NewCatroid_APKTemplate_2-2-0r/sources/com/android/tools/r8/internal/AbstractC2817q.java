package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.graph.C0612b4;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.EnumC3891f;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

/* JADX INFO: renamed from: com.android.tools.r8.internal.q, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2817q {
    static final EnumC3891f i = EnumC3891f.U;
    static final /* synthetic */ boolean j = true;
    private final C0722p1 a;
    private final com.android.tools.r8.utils.O2 b;
    final com.android.tools.r8.utils.A1 c;
    final C2215iD d;
    final Path e;
    final Collection f;
    final Path g;
    final Path h;

    AbstractC2817q(String str, String str2, String str3, String str4) {
        this(Paths.get(str, new String[0]), AbstractC1639am.c(Paths.get(str2, new String[0])), Paths.get(str3, new String[0]), Paths.get(str4, new String[0]));
    }

    private InterfaceC1206Ne a(Path path) {
        return C1233Oe.a(com.android.tools.r8.d0.a(path), this.a, this.b, false, EnumC3891f.B.d());
    }

    AbstractC2817q(Path path, Collection collection, Path path2, Path path3) throws Exception {
        C0722p1 c0722p1 = new C0722p1();
        this.a = c0722p1;
        com.android.tools.r8.utils.O2 o2 = new com.android.tools.r8.utils.O2();
        this.b = o2;
        com.android.tools.r8.utils.A1 a1C = new com.android.tools.r8.utils.A1(c0722p1, o2).q0().a(true).c();
        this.c = a1C;
        if (!j && path3 == null) {
            throw new AssertionError();
        }
        this.e = path;
        InterfaceC1206Ne interfaceC1206NeA = a(path);
        this.h = path3;
        this.d = interfaceC1206NeA.a(a(a1C, path3), com.android.tools.r8.utils.y3.b());
        this.f = collection;
        this.g = path2;
        if (!Files.isDirectory(path2, new LinkOption[0])) {
            throw new Exception("Output directory " + path2 + " is not a directory");
        }
    }

    private static C0612b4 a(com.android.tools.r8.utils.A1 a1, Path path) throws IOException {
        C0408c c0408c = new C0408c(C3945p.b().a(path).a(), a1, com.android.tools.r8.utils.y3.b());
        ExecutorService executorServiceA = C3879c3.a(a1);
        if (!j && a1.d0) {
            throw new AssertionError();
        }
        a1.d0 = true;
        C0612b4 c0612b4A = c0408c.a(executorServiceA);
        a1.d0 = false;
        return c0612b4A;
    }

    private static String a(EnumC3891f enumC3891f) {
        String str;
        if (enumC3891f == EnumC3891f.MASTER) {
            str = "third_party/android_jar/lib-master/android.jar";
        } else {
            str = String.format("third_party/android_jar/lib-v%d/android.jar", Integer.valueOf(enumC3891f.d()));
        }
        Path path = Paths.get(str, new String[0]);
        if (Files.exists(path, new LinkOption[0])) {
            return str;
        }
        throw new RuntimeException("Generate files tools should pass a valid recent android.jar as parameter if used outside of the r8 repository. Missing file: " + path);
    }

    public static void a(String[] strArr) {
        String strA;
        String strA2;
        if (strArr[0].equals("--generate-api-docs")) {
            if (strArr.length == 4 || strArr.length == 5) {
                String str = strArr[1];
                String str2 = strArr[2];
                String str3 = strArr[3];
                if (strArr.length == 5) {
                    strA2 = strArr[4];
                } else {
                    strA2 = a(i);
                }
                new C3383xj(str, str2, str3, strA2).a();
                return;
            }
        } else if (strArr.length == 3 || strArr.length == 4) {
            String str4 = strArr[0];
            String str5 = strArr[1];
            String str6 = strArr[2];
            if (strArr.length == 4) {
                strA = strArr[3];
            } else {
                strA = a(i);
            }
            new C3453yj(str4, str5, str6, strA).a();
            return;
        }
        throw new RuntimeException(com.android.tools.r8.utils.Y2.a("Invalid invocation.", "Usage: AbstractGenerateFiles [--generate-api-docs] <desugar configuration> <desugar implementation> <output directory> [<android jar path for Android " + i + " or higher>]"));
    }
}
