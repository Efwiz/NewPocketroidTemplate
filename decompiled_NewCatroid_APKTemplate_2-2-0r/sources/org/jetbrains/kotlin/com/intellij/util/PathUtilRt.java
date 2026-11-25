package org.jetbrains.kotlin.com.intellij.util;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtilRt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: loaded from: classes3.dex */
public final class PathUtilRt {
    private static final Set<String> WINDOWS_RESERVED_NAMES = new HashSet(Arrays.asList("CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"));
    private static final Charset FS_CHARSET = fsCharset();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 3:
            case 8:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                i2 = 3;
                break;
            case 3:
            case 8:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
                objArr[0] = "path";
                break;
            case 3:
            case 8:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/PathUtilRt";
                break;
            case 6:
            case 7:
                objArr[0] = "text";
                break;
            case 9:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
            case 10:
                objArr[0] = "name";
                break;
            case 11:
                objArr[0] = "os";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/PathUtilRt";
                break;
            case 3:
                objArr[1] = "getParentPath";
                break;
            case 8:
                objArr[1] = "suggestFileName";
                break;
            default:
                objArr[1] = "getFileName";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "getEnd";
                break;
            case 2:
                objArr[2] = "getParentPath";
                break;
            case 4:
                objArr[2] = "getLastIndexOfPathSeparator";
                break;
            case 5:
                objArr[2] = "isWindowsUNCRoot";
                break;
            case 6:
            case 7:
                objArr[2] = "suggestFileName";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "isValidFileName";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                throw new IllegalArgumentException(str2);
            case 3:
            case 8:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public static String getFileName(String str) {
        if (StringUtilRt.isEmpty(str)) {
            return "";
        }
        int end = getEnd(str);
        int lastIndexOfPathSeparator = getLastIndexOfPathSeparator(str, end);
        if (isWindowsUNCRoot(str, lastIndexOfPathSeparator)) {
            lastIndexOfPathSeparator = -1;
        }
        String strSubstring = str.substring(lastIndexOfPathSeparator + 1, end);
        if (strSubstring == null) {
            $$$reportNull$$$0(0);
        }
        return strSubstring;
    }

    private static int getEnd(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        for (int length = str.length() - 1; length >= 0; length--) {
            char cCharAt = str.charAt(length);
            if (cCharAt != '/' && cCharAt != '\\') {
                return length + 1;
            }
        }
        return str.length() - 1;
    }

    private static int getLastIndexOfPathSeparator(CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        int i2 = i - 1;
        return Math.max(StringUtilRt.lastIndexOf(charSequence, '/', 0, i2), StringUtilRt.lastIndexOf(charSequence, '\\', 0, i2));
    }

    public static boolean isWindowsUNCRoot(CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(5);
        }
        return Platform.CURRENT == Platform.WINDOWS && (StringUtilRt.startsWith(charSequence, "//") || StringUtilRt.startsWith(charSequence, "\\\\")) && getLastIndexOfPathSeparator(charSequence, i) == 1;
    }

    public enum Platform {
        UNIX,
        WINDOWS;

        public static final Platform CURRENT;

        static {
            Platform platform = UNIX;
            CURRENT = SystemInfoRt.isWindows ? WINDOWS : platform;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0008, code lost:
    
        r0 = java.lang.System.getProperty("sun.jnu.encoding");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.nio.charset.Charset fsCharset() {
        /*
            boolean r0 = org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt.isWindows
            if (r0 != 0) goto L2e
            boolean r0 = org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt.isMac
            if (r0 != 0) goto L2e
            java.lang.String r0 = "sun.jnu.encoding"
            java.lang.String r0 = java.lang.System.getProperty(r0)
            if (r0 == 0) goto L2e
            java.nio.charset.Charset r0 = java.nio.charset.Charset.forName(r0)     // Catch: java.lang.Exception -> L15
            return r0
        L15:
            r1 = move-exception
            java.lang.Class<org.jetbrains.kotlin.com.intellij.util.PathUtilRt> r2 = org.jetbrains.kotlin.com.intellij.util.PathUtilRt.class
            org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt r2 = org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.getInstance(r2)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "unknown JNU charset: "
            r3.<init>(r4)
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r0 = r0.toString()
            r2.warn(r0, r1)
        L2e:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.com.intellij.util.PathUtilRt.fsCharset():java.nio.charset.Charset");
    }
}
