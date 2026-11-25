package com.android.tools.r8.utils;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.internal.C2091gb;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipFile;
import org.apache.commons.lang3.SystemProperties;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.objenesis.strategy.PlatformDescription;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class X0 {
    public static final boolean a = System.getProperty(SystemProperties.JAVA_VM_NAME).equalsIgnoreCase(PlatformDescription.DALVIK);
    static final /* synthetic */ boolean b = true;

    public static boolean a(String str) {
        String strI = Y2.i(str);
        if (strI.equals("module-info.class")) {
            return false;
        }
        return strI.endsWith(".class");
    }

    public static boolean b(Path path) {
        return a(path.getFileName().toString());
    }

    public static boolean c(Path path) {
        String string = path.getFileName().toString();
        char[] cArr = Y2.a;
        String lowerCase = string.toLowerCase(Locale.ROOT);
        if (lowerCase.startsWith("classes") && lowerCase.endsWith(".dex")) {
            String strA = AbstractC0402a.a(lowerCase, 4, 7);
            if (strA.isEmpty()) {
                return true;
            }
            char cCharAt = strA.charAt(0);
            if (strA.length() == 1) {
                return '2' <= cCharAt && cCharAt <= '9';
            }
            if (cCharAt >= '1' && '9' >= cCharAt) {
                for (int i = 1; i < strA.length(); i++) {
                    char cCharAt2 = strA.charAt(i);
                    if (cCharAt2 < '0' || '9' < cCharAt2) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean d(Path path) {
        String string = path.getFileName().toString();
        char[] cArr = Y2.a;
        return string.toLowerCase(Locale.ROOT).endsWith(".dex");
    }

    public static boolean e(Path path) {
        String string = path.getFileName().toString();
        char[] cArr = Y2.a;
        return string.toLowerCase(Locale.ROOT).endsWith(".jar");
    }

    public static boolean f(Path path) {
        String string = path.getFileName().toString();
        char[] cArr = Y2.a;
        return string.toLowerCase(Locale.ROOT).endsWith(SuffixConstants.SUFFIX_STRING_java);
    }

    public static boolean g(Path path) {
        String string = path.getFileName().toString();
        char[] cArr = Y2.a;
        return string.toLowerCase(Locale.ROOT).endsWith(Constants.ZIP_EXTENSION);
    }

    public static List<String> h(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    public static String b(String str) {
        char c = File.separatorChar;
        if (c == '/') {
            return str.replace('\\', '/');
        }
        if (b || c == '\\') {
            return str.replace('/', '\\');
        }
        throw new AssertionError();
    }

    public static boolean a(Path path) {
        String string = path.getFileName().toString();
        char[] cArr = Y2.a;
        String lowerCase = string.toLowerCase(Locale.ROOT);
        return lowerCase.endsWith(".apk") || lowerCase.endsWith(".jar") || lowerCase.endsWith(Constants.ZIP_EXTENSION) || lowerCase.endsWith(".aar");
    }

    public static String a(Path path, Charset charset) throws IOException {
        return new String(Files.readAllBytes(path), charset);
    }

    public static Path a(Path path, List<String> list) throws IOException {
        Files.write(path, list, new OpenOption[0]);
        return path;
    }

    public static Path a(Path path, String... strArr) throws IOException {
        Files.write(path, Arrays.asList(strArr), new OpenOption[0]);
        return path;
    }

    public static void a(O2 o2, Path path) {
        if (path == null || g(path) || e(path)) {
            return;
        }
        if (Files.exists(path, new LinkOption[0]) && Files.isDirectory(path, new LinkOption[0])) {
            return;
        }
        o2.error(new StringDiagnostic("Invalid output: " + path + "\nOutput must be a .zip or .jar archive or an existing directory"));
    }

    public static void a(Path path, OutputStream outputStream, byte[] bArr) throws Throwable {
        a(path, outputStream, ByteDataView.of(bArr));
    }

    public static void a(Path path, OutputStream outputStream, ByteDataView byteDataView) throws Throwable {
        C2091gb c2091gbC = C2091gb.c();
        try {
            OpenOption[] openOptionArr = {StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE};
            if (path == null) {
                if (!b && outputStream == null) {
                    throw new AssertionError();
                }
            } else {
                outputStream = Files.newOutputStream(path, openOptionArr);
                c2091gbC.a(outputStream);
            }
            outputStream.write(byteDataView.getBuffer(), byteDataView.getOffset(), byteDataView.getLength());
            c2091gbC.close();
        } catch (Throwable th) {
            try {
                c2091gbC.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static ZipFile a(File file, Charset charset) {
        if (!a) {
            return new ZipFile(file, charset);
        }
        if (Charset.defaultCharset() == StandardCharsets.UTF_8) {
            return new ZipFile(file);
        }
        throw new RuntimeException("R8 can run on dex only with UTF_8 as the default charset, but the charset used is " + Charset.defaultCharset());
    }
}
