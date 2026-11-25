package org.apache.xerces.util;

import java.util.Arrays;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jgit.lib.FileMode;

/* JADX INFO: loaded from: classes13.dex */
public class XML11Char {
    public static final int MASK_XML11_CONTENT = 32;
    public static final int MASK_XML11_CONTENT_INTERNAL = 48;
    public static final int MASK_XML11_CONTROL = 16;
    public static final int MASK_XML11_NAME = 8;
    public static final int MASK_XML11_NAME_START = 4;
    public static final int MASK_XML11_NCNAME = 128;
    public static final int MASK_XML11_NCNAME_START = 64;
    public static final int MASK_XML11_SPACE = 2;
    public static final int MASK_XML11_VALID = 1;
    private static final byte[] XML11CHARS;

    static {
        byte[] bArr = new byte[65536];
        XML11CHARS = bArr;
        Arrays.fill(bArr, 1, 9, (byte) 17);
        bArr[9] = 35;
        bArr[10] = 3;
        Arrays.fill(bArr, 11, 13, (byte) 17);
        bArr[13] = 3;
        Arrays.fill(bArr, 14, 32, (byte) 17);
        bArr[32] = 35;
        Arrays.fill(bArr, 33, 38, (byte) 33);
        bArr[38] = 1;
        Arrays.fill(bArr, 39, 45, (byte) 33);
        Arrays.fill(bArr, 45, 47, Opcodes.OPC_ret);
        bArr[47] = 33;
        Arrays.fill(bArr, 48, 58, Opcodes.OPC_ret);
        bArr[58] = 45;
        bArr[59] = 33;
        bArr[60] = 1;
        Arrays.fill(bArr, 61, 65, (byte) 33);
        Arrays.fill(bArr, 65, 91, (byte) -19);
        Arrays.fill(bArr, 91, 93, (byte) 33);
        bArr[93] = 1;
        bArr[94] = 33;
        bArr[95] = -19;
        bArr[96] = 33;
        Arrays.fill(bArr, 97, 123, (byte) -19);
        Arrays.fill(bArr, 123, 127, (byte) 33);
        Arrays.fill(bArr, 127, 133, (byte) 17);
        bArr[133] = 35;
        Arrays.fill(bArr, 134, 160, (byte) 17);
        Arrays.fill(bArr, 160, 183, (byte) 33);
        bArr[183] = Opcodes.OPC_ret;
        Arrays.fill(bArr, 184, 192, (byte) 33);
        Arrays.fill(bArr, 192, 215, (byte) -19);
        bArr[215] = 33;
        Arrays.fill(bArr, 216, 247, (byte) -19);
        bArr[247] = 33;
        Arrays.fill(bArr, 248, 768, (byte) -19);
        Arrays.fill(bArr, 768, 880, Opcodes.OPC_ret);
        Arrays.fill(bArr, 880, 894, (byte) -19);
        bArr[894] = 33;
        Arrays.fill(bArr, 895, 8192, (byte) -19);
        Arrays.fill(bArr, 8192, 8204, (byte) 33);
        Arrays.fill(bArr, 8204, 8206, (byte) -19);
        Arrays.fill(bArr, 8206, 8232, (byte) 33);
        bArr[8232] = 35;
        Arrays.fill(bArr, 8233, 8255, (byte) 33);
        Arrays.fill(bArr, 8255, 8257, Opcodes.OPC_ret);
        Arrays.fill(bArr, 8257, 8304, (byte) 33);
        Arrays.fill(bArr, 8304, 8592, (byte) -19);
        Arrays.fill(bArr, 8592, 11264, (byte) 33);
        Arrays.fill(bArr, 11264, 12272, (byte) -19);
        Arrays.fill(bArr, 12272, 12289, (byte) 33);
        Arrays.fill(bArr, 12289, 55296, (byte) -19);
        Arrays.fill(bArr, FileMode.TYPE_GITLINK, 63744, (byte) 33);
        Arrays.fill(bArr, 63744, 64976, (byte) -19);
        Arrays.fill(bArr, 64976, 65008, (byte) 33);
        Arrays.fill(bArr, 65008, 65534, (byte) -19);
    }

    public static boolean isXML11Content(int i) {
        return (i < 65536 && (XML11CHARS[i] & 32) != 0) || (65536 <= i && i <= 1114111);
    }

    public static boolean isXML11InternalEntityContent(int i) {
        return (i < 65536 && (XML11CHARS[i] & 48) != 0) || (65536 <= i && i <= 1114111);
    }

    public static boolean isXML11Invalid(int i) {
        return !isXML11Valid(i);
    }

    public static boolean isXML11NCName(int i) {
        return (i < 65536 && (XML11CHARS[i] & 128) != 0) || (65536 <= i && i < 983040);
    }

    public static boolean isXML11NCNameStart(int i) {
        return (i < 65536 && (XML11CHARS[i] & 64) != 0) || (65536 <= i && i < 983040);
    }

    public static boolean isXML11Name(int i) {
        return (i < 65536 && (XML11CHARS[i] & 8) != 0) || (i >= 65536 && i < 983040);
    }

    public static boolean isXML11NameHighSurrogate(int i) {
        return 55296 <= i && i <= 56191;
    }

    public static boolean isXML11NameStart(int i) {
        return (i < 65536 && (XML11CHARS[i] & 4) != 0) || (65536 <= i && i < 983040);
    }

    public static boolean isXML11Space(int i) {
        return i < 65536 && (XML11CHARS[i] & 2) != 0;
    }

    public static boolean isXML11Valid(int i) {
        if (i >= 65536 || (XML11CHARS[i] & 1) == 0) {
            return 65536 <= i && i <= 1114111;
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0010  */
    public static boolean isXML11ValidLiteral(int i) {
        if (i >= 65536) {
            if (65536 <= i) {
            }
            return false;
        }
        byte b = XML11CHARS[i];
        if ((b & 1) == 0 || (b & 16) != 0) {
            if (65536 <= i || i > 1114111) {
                return false;
            }
        }
        return true;
    }

    public static boolean isXML11ValidNCName(String str) {
        int i;
        int length = str.length();
        if (length == 0) {
            return false;
        }
        char cCharAt = str.charAt(0);
        if (!isXML11NCNameStart(cCharAt)) {
            if (length > 1 && isXML11NameHighSurrogate(cCharAt)) {
                char cCharAt2 = str.charAt(1);
                if (XMLChar.isLowSurrogate(cCharAt2) && isXML11NCNameStart(XMLChar.supplemental(cCharAt, cCharAt2))) {
                    i = 2;
                }
            }
            return false;
        }
        i = 1;
        while (i < length) {
            char cCharAt3 = str.charAt(i);
            if (!isXML11NCName(cCharAt3)) {
                i++;
                if (i < length && isXML11NameHighSurrogate(cCharAt3)) {
                    char cCharAt4 = str.charAt(i);
                    if (!XMLChar.isLowSurrogate(cCharAt4) || !isXML11NCName(XMLChar.supplemental(cCharAt3, cCharAt4))) {
                    }
                }
                return false;
            }
            i++;
        }
        return true;
    }

    public static boolean isXML11ValidName(String str) {
        int i;
        int length = str.length();
        if (length == 0) {
            return false;
        }
        char cCharAt = str.charAt(0);
        if (!isXML11NameStart(cCharAt)) {
            if (length > 1 && isXML11NameHighSurrogate(cCharAt)) {
                char cCharAt2 = str.charAt(1);
                if (XMLChar.isLowSurrogate(cCharAt2) && isXML11NameStart(XMLChar.supplemental(cCharAt, cCharAt2))) {
                    i = 2;
                }
            }
            return false;
        }
        i = 1;
        while (i < length) {
            char cCharAt3 = str.charAt(i);
            if (!isXML11Name(cCharAt3)) {
                i++;
                if (i < length && isXML11NameHighSurrogate(cCharAt3)) {
                    char cCharAt4 = str.charAt(i);
                    if (!XMLChar.isLowSurrogate(cCharAt4) || !isXML11Name(XMLChar.supplemental(cCharAt3, cCharAt4))) {
                    }
                }
                return false;
            }
            i++;
        }
        return true;
    }

    public static boolean isXML11ValidNmtoken(String str) {
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (!isXML11Name(cCharAt)) {
                i++;
                if (i < length && isXML11NameHighSurrogate(cCharAt)) {
                    char cCharAt2 = str.charAt(i);
                    if (!XMLChar.isLowSurrogate(cCharAt2) || !isXML11Name(XMLChar.supplemental(cCharAt, cCharAt2))) {
                    }
                }
                return false;
            }
            i++;
        }
        return true;
    }
}
