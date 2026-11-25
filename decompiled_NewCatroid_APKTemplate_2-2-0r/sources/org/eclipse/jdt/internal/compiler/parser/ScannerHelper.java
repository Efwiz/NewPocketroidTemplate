package org.eclipse.jdt.internal.compiler.parser;

import io.netty.util.internal.StringUtil;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import kotlin.jvm.internal.CharCompanionObject;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;

/* JADX INFO: loaded from: classes14.dex */
public class ScannerHelper {
    public static final long[] Bits = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, TagBits.HasUnresolvedTypeVariables, TagBits.HasUnresolvedSuperclass, TagBits.HasUnresolvedSuperinterfaces, TagBits.HasUnresolvedEnclosingType, TagBits.HasUnresolvedMemberTypes, TagBits.HasTypeVariable, 1073741824, 2147483648L, 4294967296L, 8589934592L, 17179869184L, 34359738368L, 68719476736L, 137438953472L, 274877906944L, 549755813888L, 1099511627776L, 2199023255552L, 4398046511104L, 8796093022208L, 17592186044416L, 35184372088832L, 70368744177664L, 140737488355328L, 281474976710656L, 562949953421312L, 1125899906842624L, 2251799813685248L, 4503599627370496L, 9007199254740992L, 18014398509481984L, 36028797018963968L, 72057594037927936L, 144115188075855872L, 288230376151711744L, 576460752303423488L, 1152921504606846976L, 2305843009213693952L, 4611686018427387904L, Long.MIN_VALUE};
    public static final int C_DIGIT = 4;
    public static final int C_IDENT_PART = 8;
    public static final int C_IDENT_START = 64;
    public static final int C_JLS_SPACE = 256;
    public static final int C_LOWER_LETTER = 16;
    public static final int C_SEPARATOR = 2;
    public static final int C_SPACE = 1;
    public static final int C_SPECIAL = 128;
    public static final int C_UPPER_LETTER = 32;
    public static final int MAX_OBVIOUS = 128;
    public static final int[] OBVIOUS_IDENT_CHAR_NATURES;
    private static final int PART_INDEX = 1;
    private static final int START_INDEX = 0;
    private static long[][][] Tables;
    private static long[][][] Tables7;
    private static long[][][] Tables8;

    public static int getHexadecimalValue(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                switch (c) {
                    case 'A':
                        return 10;
                    case 'B':
                        return 11;
                    case 'C':
                        return 12;
                    case 'D':
                        return 13;
                    case 'E':
                        return 14;
                    case 'F':
                        return 15;
                    default:
                        switch (c) {
                            case 'a':
                                return 10;
                            case 'b':
                                return 11;
                            case 'c':
                                return 12;
                            case 'd':
                                return 13;
                            case 'e':
                                return 14;
                            case 'f':
                                return 15;
                            default:
                                return -1;
                        }
                }
        }
    }

    private static int toCodePoint(char c, char c2) {
        return ((c - 55296) * 1024) + (c2 - CharCompanionObject.MIN_LOW_SURROGATE) + 65536;
    }

    static {
        int[] iArr = new int[128];
        OBVIOUS_IDENT_CHAR_NATURES = iArr;
        iArr[0] = 8;
        iArr[1] = 8;
        iArr[2] = 8;
        iArr[3] = 8;
        iArr[4] = 8;
        iArr[5] = 8;
        iArr[6] = 8;
        iArr[7] = 8;
        iArr[8] = 8;
        iArr[14] = 8;
        iArr[15] = 8;
        iArr[16] = 8;
        iArr[17] = 8;
        iArr[18] = 8;
        iArr[19] = 8;
        iArr[20] = 8;
        iArr[21] = 8;
        iArr[22] = 8;
        iArr[23] = 8;
        iArr[24] = 8;
        iArr[25] = 8;
        iArr[26] = 8;
        iArr[27] = 8;
        iArr[127] = 8;
        for (int i = 48; i <= 57; i++) {
            OBVIOUS_IDENT_CHAR_NATURES[i] = 12;
        }
        for (int i2 = 97; i2 <= 122; i2++) {
            OBVIOUS_IDENT_CHAR_NATURES[i2] = 88;
        }
        for (int i3 = 65; i3 <= 90; i3++) {
            OBVIOUS_IDENT_CHAR_NATURES[i3] = 104;
        }
        int[] iArr2 = OBVIOUS_IDENT_CHAR_NATURES;
        iArr2[95] = 200;
        iArr2[36] = 200;
        iArr2[9] = 257;
        iArr2[10] = 257;
        iArr2[11] = 1;
        iArr2[12] = 257;
        iArr2[13] = 257;
        iArr2[28] = 1;
        iArr2[29] = 1;
        iArr2[30] = 1;
        iArr2[31] = 1;
        iArr2[32] = 257;
        iArr2[46] = 2;
        iArr2[58] = 2;
        iArr2[59] = 2;
        iArr2[44] = 2;
        iArr2[91] = 2;
        iArr2[93] = 2;
        iArr2[40] = 2;
        iArr2[41] = 2;
        iArr2[123] = 2;
        iArr2[125] = 2;
        iArr2[43] = 2;
        iArr2[45] = 2;
        iArr2[42] = 2;
        iArr2[47] = 2;
        iArr2[61] = 2;
        iArr2[38] = 2;
        iArr2[124] = 2;
        iArr2[63] = 2;
        iArr2[60] = 2;
        iArr2[62] = 2;
        iArr2[33] = 2;
        iArr2[37] = 2;
        iArr2[94] = 2;
        iArr2[126] = 2;
        iArr2[34] = 2;
        iArr2[39] = 2;
    }

    static void initializeTable() {
        Tables = initializeTables("unicode");
    }

    static void initializeTable17() {
        Tables7 = initializeTables("unicode6");
    }

    static void initializeTable18() {
        Tables8 = initializeTables("unicode6_2");
    }

    static long[][][] initializeTables(String str) {
        long[][][] jArr = {new long[3][], new long[4][]};
        try {
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(String.valueOf(str) + "/start0.rsc")));
            long[] jArr2 = new long[1024];
            for (int i = 0; i < 1024; i++) {
                jArr2[i] = dataInputStream.readLong();
            }
            dataInputStream.close();
            jArr[0][0] = jArr2;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            DataInputStream dataInputStream2 = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(String.valueOf(str) + "/start1.rsc")));
            long[] jArr3 = new long[1024];
            for (int i2 = 0; i2 < 1024; i2++) {
                jArr3[i2] = dataInputStream2.readLong();
            }
            dataInputStream2.close();
            jArr[0][1] = jArr3;
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        try {
            DataInputStream dataInputStream3 = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(String.valueOf(str) + "/start2.rsc")));
            long[] jArr4 = new long[1024];
            for (int i3 = 0; i3 < 1024; i3++) {
                jArr4[i3] = dataInputStream3.readLong();
            }
            dataInputStream3.close();
            jArr[0][2] = jArr4;
        } catch (FileNotFoundException e5) {
            e5.printStackTrace();
        } catch (IOException e6) {
            e6.printStackTrace();
        }
        try {
            DataInputStream dataInputStream4 = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(String.valueOf(str) + "/part0.rsc")));
            long[] jArr5 = new long[1024];
            for (int i4 = 0; i4 < 1024; i4++) {
                jArr5[i4] = dataInputStream4.readLong();
            }
            dataInputStream4.close();
            jArr[1][0] = jArr5;
        } catch (FileNotFoundException e7) {
            e7.printStackTrace();
        } catch (IOException e8) {
            e8.printStackTrace();
        }
        try {
            DataInputStream dataInputStream5 = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(String.valueOf(str) + "/part1.rsc")));
            long[] jArr6 = new long[1024];
            for (int i5 = 0; i5 < 1024; i5++) {
                jArr6[i5] = dataInputStream5.readLong();
            }
            dataInputStream5.close();
            jArr[1][1] = jArr6;
        } catch (FileNotFoundException e9) {
            e9.printStackTrace();
        } catch (IOException e10) {
            e10.printStackTrace();
        }
        try {
            DataInputStream dataInputStream6 = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(String.valueOf(str) + "/part2.rsc")));
            long[] jArr7 = new long[1024];
            for (int i6 = 0; i6 < 1024; i6++) {
                jArr7[i6] = dataInputStream6.readLong();
            }
            dataInputStream6.close();
            jArr[1][2] = jArr7;
        } catch (FileNotFoundException e11) {
            e11.printStackTrace();
        } catch (IOException e12) {
            e12.printStackTrace();
        }
        try {
            DataInputStream dataInputStream7 = new DataInputStream(new BufferedInputStream(ScannerHelper.class.getResourceAsStream(String.valueOf(str) + "/part14.rsc")));
            long[] jArr8 = new long[1024];
            for (int i7 = 0; i7 < 1024; i7++) {
                jArr8[i7] = dataInputStream7.readLong();
            }
            dataInputStream7.close();
            jArr[1][3] = jArr8;
        } catch (FileNotFoundException e13) {
            e13.printStackTrace();
        } catch (IOException e14) {
            e14.printStackTrace();
        }
        return jArr;
    }

    private static final boolean isBitSet(long[] jArr, int i) {
        try {
            return (jArr[i / 64] & Bits[i % 64]) != 0;
        } catch (NullPointerException unused) {
        }
    }

    public static boolean isJavaIdentifierPart(char c) {
        if (c < 128) {
            return (OBVIOUS_IDENT_CHAR_NATURES[c] & 8) != 0;
        }
        return Character.isJavaIdentifierPart(c);
    }

    public static boolean isJavaIdentifierPart(long j, char c) {
        if (c < 128) {
            return (OBVIOUS_IDENT_CHAR_NATURES[c] & 8) != 0;
        }
        return isJavaIdentifierPart(j, (int) c);
    }

    public static boolean isJavaIdentifierPart(long j, int i) {
        if (j <= ClassFileConstants.JDK1_6) {
            if (Tables == null) {
                initializeTable();
            }
            int i2 = (i & 2031616) >> 16;
            if (i2 == 0) {
                return isBitSet(Tables[1][0], i & 65535);
            }
            if (i2 == 1) {
                return isBitSet(Tables[1][1], i & 65535);
            }
            if (i2 == 2) {
                return isBitSet(Tables[1][2], i & 65535);
            }
            if (i2 == 14) {
                return isBitSet(Tables[1][3], i & 65535);
            }
        } else if (j <= ClassFileConstants.JDK1_7) {
            if (Tables7 == null) {
                initializeTable17();
            }
            int i3 = (i & 2031616) >> 16;
            if (i3 == 0) {
                return isBitSet(Tables7[1][0], i & 65535);
            }
            if (i3 == 1) {
                return isBitSet(Tables7[1][1], i & 65535);
            }
            if (i3 == 2) {
                return isBitSet(Tables7[1][2], i & 65535);
            }
            if (i3 == 14) {
                return isBitSet(Tables7[1][3], i & 65535);
            }
        } else {
            if (Tables8 == null) {
                initializeTable18();
            }
            int i4 = (i & 2031616) >> 16;
            if (i4 == 0) {
                return isBitSet(Tables8[1][0], i & 65535);
            }
            if (i4 == 1) {
                return isBitSet(Tables8[1][1], i & 65535);
            }
            if (i4 == 2) {
                return isBitSet(Tables8[1][2], i & 65535);
            }
            if (i4 == 14) {
                return isBitSet(Tables8[1][3], i & 65535);
            }
        }
        return false;
    }

    public static boolean isJavaIdentifierPart(long j, char c, char c2) {
        return isJavaIdentifierPart(j, toCodePoint(c, c2));
    }

    public static boolean isJavaIdentifierStart(char c) {
        if (c < 128) {
            return (OBVIOUS_IDENT_CHAR_NATURES[c] & 64) != 0;
        }
        return Character.isJavaIdentifierStart(c);
    }

    public static boolean isJavaIdentifierStart(long j, char c) {
        if (c < 128) {
            return (OBVIOUS_IDENT_CHAR_NATURES[c] & 64) != 0;
        }
        return isJavaIdentifierStart(j, (int) c);
    }

    public static boolean isJavaIdentifierStart(long j, char c, char c2) {
        return isJavaIdentifierStart(j, toCodePoint(c, c2));
    }

    public static boolean isJavaIdentifierStart(long j, int i) {
        if (j <= ClassFileConstants.JDK1_6) {
            if (Tables == null) {
                initializeTable();
            }
            int i2 = (i & 2031616) >> 16;
            if (i2 == 0) {
                return isBitSet(Tables[0][0], i & 65535);
            }
            if (i2 == 1) {
                return isBitSet(Tables[0][1], i & 65535);
            }
            if (i2 == 2) {
                return isBitSet(Tables[0][2], i & 65535);
            }
        } else if (j <= ClassFileConstants.JDK1_7) {
            if (Tables7 == null) {
                initializeTable17();
            }
            int i3 = (i & 2031616) >> 16;
            if (i3 == 0) {
                return isBitSet(Tables7[0][0], i & 65535);
            }
            if (i3 == 1) {
                return isBitSet(Tables7[0][1], i & 65535);
            }
            if (i3 == 2) {
                return isBitSet(Tables7[0][2], i & 65535);
            }
        } else {
            if (Tables8 == null) {
                initializeTable18();
            }
            int i4 = (i & 2031616) >> 16;
            if (i4 == 0) {
                return isBitSet(Tables8[0][0], i & 65535);
            }
            if (i4 == 1) {
                return isBitSet(Tables8[0][1], i & 65535);
            }
            if (i4 == 2) {
                return isBitSet(Tables8[0][2], i & 65535);
            }
        }
        return false;
    }

    public static boolean isDigit(char c) throws InvalidInputException {
        if (c < 128) {
            return (OBVIOUS_IDENT_CHAR_NATURES[c] & 4) != 0;
        }
        if (Character.isDigit(c)) {
            throw new InvalidInputException(Scanner.INVALID_DIGIT);
        }
        return false;
    }

    public static int digit(char c, int i) {
        if (c < 128) {
            if (i == 8) {
                if (c < '0' || c > '7') {
                    return -1;
                }
                return c - ExternalAnnotationProvider.NULLABLE;
            }
            if (i == 10) {
                if (c < '0' || c > '9') {
                    return -1;
                }
                return c - ExternalAnnotationProvider.NULLABLE;
            }
            if (i == 16) {
                if (c >= '0' && c <= '9') {
                    return c - ExternalAnnotationProvider.NULLABLE;
                }
                if (c >= 'A' && c <= 'F') {
                    return c - '7';
                }
                if (c < 'a' || c > 'f') {
                    return -1;
                }
                return c - 'W';
            }
        }
        return Character.digit(c, i);
    }

    public static int getNumericValue(char c) {
        if (c < 128) {
            int i = OBVIOUS_IDENT_CHAR_NATURES[c];
            if (i == 4) {
                return c - '0';
            }
            if (i == 16) {
                return c - 'W';
            }
            if (i == 32) {
                return c - '7';
            }
        }
        return Character.getNumericValue(c);
    }

    public static char toUpperCase(char c) {
        if (c < 128) {
            int i = OBVIOUS_IDENT_CHAR_NATURES[c];
            if ((i & 32) != 0) {
                return c;
            }
            if ((i & 16) != 0) {
                return (char) (c - ' ');
            }
        }
        return Character.toUpperCase(c);
    }

    public static char toLowerCase(char c) {
        if (c < 128) {
            int i = OBVIOUS_IDENT_CHAR_NATURES[c];
            if ((i & 16) != 0) {
                return c;
            }
            if ((i & 32) != 0) {
                return (char) (c + StringUtil.SPACE);
            }
        }
        return Character.toLowerCase(c);
    }

    public static boolean isLowerCase(char c) {
        if (c < 128) {
            return (OBVIOUS_IDENT_CHAR_NATURES[c] & 16) != 0;
        }
        return Character.isLowerCase(c);
    }

    public static boolean isUpperCase(char c) {
        if (c < 128) {
            return (OBVIOUS_IDENT_CHAR_NATURES[c] & 32) != 0;
        }
        return Character.isUpperCase(c);
    }

    public static boolean isWhitespace(char c) {
        if (c < 128) {
            return (OBVIOUS_IDENT_CHAR_NATURES[c] & 1) != 0;
        }
        return Character.isWhitespace(c);
    }

    public static boolean isLetter(char c) {
        if (c < 128) {
            return (OBVIOUS_IDENT_CHAR_NATURES[c] & 48) != 0;
        }
        return Character.isLetter(c);
    }

    public static boolean isLetterOrDigit(char c) {
        if (c < 128) {
            return (OBVIOUS_IDENT_CHAR_NATURES[c] & 52) != 0;
        }
        return Character.isLetterOrDigit(c);
    }
}
