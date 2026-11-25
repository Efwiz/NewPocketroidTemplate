package javax.xml.transform.stream;

import io.netty.util.internal.StringUtil;
import java.io.File;
import java.io.UnsupportedEncodingException;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems;

/* JADX INFO: loaded from: classes11.dex */
class FilePathToURI {
    private static boolean[] gNeedEscaping = new boolean[128];
    private static char[] gAfterEscaping1 = new char[128];
    private static char[] gAfterEscaping2 = new char[128];
    private static char[] gHexChs = {ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', 'A', Util.C_BYTE, Util.C_CHAR, Util.C_DOUBLE, 'E', Util.C_FLOAT};

    static {
        for (int i = 0; i <= 31; i++) {
            gNeedEscaping[i] = true;
            char[] cArr = gAfterEscaping1;
            char[] cArr2 = gHexChs;
            cArr[i] = cArr2[i >> 4];
            gAfterEscaping2[i] = cArr2[i & 15];
        }
        gNeedEscaping[127] = true;
        gAfterEscaping1[127] = '7';
        gAfterEscaping2[127] = Util.C_FLOAT;
        char[] cArr3 = {StringUtil.SPACE, '<', '>', '#', MangleConstant.PLATFORM_FUNCTION_MARKER, '\"', '{', '}', '|', '\\', Util.C_EXCEPTION_START, '~', '[', ']', '`'};
        for (int i2 = 0; i2 < 15; i2++) {
            char c = cArr3[i2];
            gNeedEscaping[c] = true;
            char[] cArr4 = gAfterEscaping1;
            char[] cArr5 = gHexChs;
            cArr4[c] = cArr5[c >> 4];
            gAfterEscaping2[c] = cArr5[c & 15];
        }
    }

    FilePathToURI() {
    }

    public static String filepath2URI(String str) {
        char c;
        char cCharAt;
        char c2;
        char upperCase;
        if (str == null) {
            return null;
        }
        String strReplace = str.replace(File.separatorChar, '/');
        int length = strReplace.length();
        StringBuffer stringBuffer = new StringBuffer(length * 3);
        stringBuffer.append(StandardFileSystems.FILE_PROTOCOL_PREFIX);
        if (length >= 2 && strReplace.charAt(1) == ':' && (upperCase = Character.toUpperCase(strReplace.charAt(0))) >= 'A' && upperCase <= 'Z') {
            stringBuffer.append('/');
        }
        int i = 0;
        while (i < length && (cCharAt = strReplace.charAt(i)) < 128) {
            if (gNeedEscaping[cCharAt]) {
                stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                stringBuffer.append(gAfterEscaping1[cCharAt]);
                c2 = gAfterEscaping2[cCharAt];
            } else {
                c2 = cCharAt;
            }
            stringBuffer.append(c2);
            i++;
        }
        if (i < length) {
            try {
                for (byte b : strReplace.substring(i).getBytes("UTF-8")) {
                    if (b < 0) {
                        int i2 = b + 256;
                        stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                        stringBuffer.append(gHexChs[i2 >> 4]);
                        c = gHexChs[i2 & 15];
                    } else if (gNeedEscaping[b]) {
                        stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                        stringBuffer.append(gAfterEscaping1[b]);
                        c = gAfterEscaping2[b];
                    } else {
                        c = (char) b;
                    }
                    stringBuffer.append(c);
                }
            } catch (UnsupportedEncodingException unused) {
                return strReplace;
            }
        }
        return stringBuffer.toString();
    }
}
