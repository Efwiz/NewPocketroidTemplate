package org.apache.xerces.impl.xpath.regex;

import java.io.PrintStream;
import java.io.Serializable;

/* JADX INFO: loaded from: classes13.dex */
final class RangeToken extends Token implements Serializable {
    private static final int MAPSIZE = 256;
    private static final long serialVersionUID = -553983121197679934L;
    boolean compacted;
    RangeToken icaseCache;
    int[] map;
    int nonMapIndex;
    int[] ranges;
    boolean sorted;

    RangeToken(int i) {
        super(i);
        this.icaseCache = null;
        this.map = null;
        setSorted(false);
    }

    static Token complementRanges(Token token) {
        if (token.type != 4 && token.type != 5) {
            throw new IllegalArgumentException("Token#complementRanges(): must be RANGE: " + token.type);
        }
        RangeToken rangeToken = (RangeToken) token;
        rangeToken.sortRanges();
        rangeToken.compactRanges();
        int[] iArr = rangeToken.ranges;
        int length = iArr.length;
        int i = length + 2;
        int i2 = 0;
        if (iArr[0] != 0) {
            length = i;
        }
        int i3 = iArr[iArr.length - 1];
        if (i3 == 1114111) {
            length -= 2;
        }
        RangeToken rangeTokenCreateRange = Token.createRange();
        int[] iArr2 = new int[length];
        rangeTokenCreateRange.ranges = iArr2;
        int[] iArr3 = rangeToken.ranges;
        if (iArr3[0] > 0) {
            iArr2[0] = 0;
            iArr2[1] = iArr3[0] - 1;
            i2 = 2;
        }
        int i4 = 1;
        while (true) {
            int[] iArr4 = rangeToken.ranges;
            if (i4 >= iArr4.length - 2) {
                break;
            }
            int[] iArr5 = rangeTokenCreateRange.ranges;
            int i5 = i2 + 1;
            iArr5[i2] = iArr4[i4] + 1;
            i2 += 2;
            iArr5[i5] = iArr4[i4 + 1] - 1;
            i4 += 2;
        }
        if (i3 != 1114111) {
            int[] iArr6 = rangeTokenCreateRange.ranges;
            iArr6[i2] = i3 + 1;
            iArr6[i2 + 1] = 1114111;
        }
        rangeTokenCreateRange.setCompacted();
        return rangeTokenCreateRange;
    }

    private void createMap() {
        int[] iArr = new int[8];
        int length = this.ranges.length;
        int i = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            iArr[i2] = 0;
        }
        while (true) {
            int[] iArr2 = this.ranges;
            if (i >= iArr2.length) {
                break;
            }
            int i3 = iArr2[i];
            int i4 = iArr2[i + 1];
            if (i3 < 256) {
                while (i3 <= i4 && i3 < 256) {
                    int i5 = i3 / 32;
                    iArr[i5] = iArr[i5] | (1 << (i3 & 31));
                    i3++;
                }
                if (i4 < 256) {
                    i += 2;
                }
            }
            length = i;
            break;
        }
        this.map = iArr;
        this.nonMapIndex = length;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x007b  */
    private static String escapeCharInCharClass(int i) {
        StringBuilder sb;
        StringBuilder sbAppend;
        String str;
        StringBuilder sb2;
        int length;
        if (i == 9) {
            return "\\t";
        }
        if (i == 10) {
            return "\\n";
        }
        if (i == 12) {
            return "\\f";
        }
        if (i == 13) {
            return "\\r";
        }
        if (i == 27) {
            return "\\e";
        }
        if (i != 44 && i != 45) {
            switch (i) {
                case 91:
                case 92:
                case 93:
                case 94:
                    sb = new StringBuilder("\\");
                    sbAppend = sb.append((char) i);
                    break;
                default:
                    if (i < 32) {
                        str = "0" + Integer.toHexString(i);
                        sb2 = new StringBuilder("\\x");
                        length = str.length() - 2;
                    } else if (i < 65536) {
                        sb = new StringBuilder("");
                        sbAppend = sb.append((char) i);
                    } else {
                        str = "0" + Integer.toHexString(i);
                        sb2 = new StringBuilder("\\v");
                        length = str.length() - 6;
                    }
                    sbAppend = sb2.append(str.substring(length, str.length()));
                    break;
            }
        } else {
            sb = new StringBuilder("\\");
            sbAppend = sb.append((char) i);
        }
        return sbAppend.toString();
    }

    private final boolean isCompacted() {
        return this.compacted;
    }

    private final boolean isSorted() {
        return this.sorted;
    }

    private final void setCompacted() {
        this.compacted = true;
    }

    private final void setSorted(boolean z) {
        this.sorted = z;
        if (z) {
            return;
        }
        this.compacted = false;
    }

    @Override // org.apache.xerces.impl.xpath.regex.Token
    protected void addRange(int i, int i2) {
        this.icaseCache = null;
        if (i > i2) {
            i2 = i;
            i = i2;
        }
        int[] iArr = this.ranges;
        if (iArr == null) {
            this.ranges = new int[]{i, i2};
            setSorted(true);
            return;
        }
        int length = iArr.length;
        int i3 = length - 1;
        if (iArr[i3] + 1 == i) {
            iArr[i3] = i2;
            return;
        }
        int[] iArr2 = new int[length + 2];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        this.ranges = iArr2;
        if (iArr2[i3] >= i) {
            setSorted(false);
        }
        int[] iArr3 = this.ranges;
        iArr3[length] = i;
        iArr3[length + 1] = i2;
        if (this.sorted) {
            return;
        }
        sortRanges();
    }

    @Override // org.apache.xerces.impl.xpath.regex.Token
    protected void compactRanges() {
        int i;
        int i2;
        int[] iArr = this.ranges;
        if (iArr == null || iArr.length <= 2 || isCompacted()) {
            return;
        }
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int[] iArr2 = this.ranges;
            if (i3 >= iArr2.length) {
                if (i4 != iArr2.length) {
                    int[] iArr3 = new int[i4];
                    System.arraycopy(iArr2, 0, iArr3, 0, i4);
                    this.ranges = iArr3;
                }
                setCompacted();
                return;
            }
            if (i4 != i3) {
                int i5 = i3 + 1;
                iArr2[i4] = iArr2[i3];
                i3 += 2;
                iArr2[i4 + 1] = iArr2[i5];
            } else {
                i3 += 2;
            }
            int i6 = i4 + 1;
            int i7 = iArr2[i6];
            while (true) {
                int[] iArr4 = this.ranges;
                if (i3 >= iArr4.length || (i = i7 + 1) < (i2 = iArr4[i3])) {
                    break;
                }
                if (i == i2) {
                    i7 = iArr4[i3 + 1];
                    iArr4[i6] = i7;
                } else {
                    int i8 = i3 + 1;
                    int i9 = iArr4[i8];
                    if (i7 < i9) {
                        if (i7 >= i9) {
                            throw new RuntimeException("Token#compactRanges(): Internel Error: [" + this.ranges[i4] + "," + this.ranges[i6] + "] [" + this.ranges[i3] + "," + this.ranges[i8] + "]");
                        }
                        iArr4[i6] = i9;
                        i3 += 2;
                        i7 = i9;
                    }
                }
                i3 += 2;
            }
            i4 += 2;
        }
    }

    void dumpRanges() {
        PrintStream printStream;
        String str;
        System.err.print("RANGE: ");
        if (this.ranges == null) {
            printStream = System.err;
            str = " NULL";
        } else {
            for (int i = 0; i < this.ranges.length; i += 2) {
                System.err.print("[" + this.ranges[i] + "," + this.ranges[i + 1] + "] ");
            }
            printStream = System.err;
            str = "";
        }
        printStream.println(str);
    }

    synchronized RangeToken getCaseInsensitiveToken() {
        RangeToken rangeToken = this.icaseCache;
        if (rangeToken != null) {
            return rangeToken;
        }
        RangeToken rangeTokenCreateRange = this.type == 4 ? Token.createRange() : Token.createNRange();
        int i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.ranges;
            if (i2 >= iArr.length) {
                break;
            }
            for (int i3 = iArr[i2]; i3 <= this.ranges[i2 + 1]; i3++) {
                if (i3 > 65535) {
                    rangeTokenCreateRange.addRange(i3, i3);
                } else {
                    char upperCase = Character.toUpperCase((char) i3);
                    rangeTokenCreateRange.addRange(upperCase, upperCase);
                }
            }
            i2 += 2;
        }
        RangeToken rangeTokenCreateRange2 = this.type == 4 ? Token.createRange() : Token.createNRange();
        while (true) {
            int[] iArr2 = rangeTokenCreateRange.ranges;
            if (i >= iArr2.length) {
                rangeTokenCreateRange2.mergeRanges(rangeTokenCreateRange);
                rangeTokenCreateRange2.mergeRanges(this);
                rangeTokenCreateRange2.compactRanges();
                this.icaseCache = rangeTokenCreateRange2;
                return rangeTokenCreateRange2;
            }
            for (int i4 = iArr2[i]; i4 <= rangeTokenCreateRange.ranges[i + 1]; i4++) {
                if (i4 > 65535) {
                    rangeTokenCreateRange2.addRange(i4, i4);
                } else {
                    char lowerCase = Character.toLowerCase((char) i4);
                    rangeTokenCreateRange2.addRange(lowerCase, lowerCase);
                }
            }
            i += 2;
        }
    }

    @Override // org.apache.xerces.impl.xpath.regex.Token
    protected void intersectRanges(Token token) {
        RangeToken rangeToken = (RangeToken) token;
        if (rangeToken.ranges == null || this.ranges == null) {
            return;
        }
        this.icaseCache = null;
        sortRanges();
        compactRanges();
        rangeToken.sortRanges();
        rangeToken.compactRanges();
        int[] iArr = new int[this.ranges.length + rangeToken.ranges.length];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr2 = this.ranges;
            if (i >= iArr2.length) {
                break;
            }
            int[] iArr3 = rangeToken.ranges;
            if (i2 >= iArr3.length) {
                break;
            }
            int i4 = iArr2[i];
            int i5 = i + 1;
            int i6 = iArr2[i5];
            int i7 = iArr3[i2];
            int i8 = i2 + 1;
            int i9 = iArr3[i8];
            if (i6 >= i7) {
                if (i6 < i7 || i4 > i9) {
                    if (i9 >= i4) {
                        throw new RuntimeException("Token#intersectRanges(): Internal Error: [" + this.ranges[i] + "," + this.ranges[i5] + "] & [" + rangeToken.ranges[i2] + "," + rangeToken.ranges[i8] + "]");
                    }
                    i2 += 2;
                } else if (i7 <= i4 && i6 <= i9) {
                    int i10 = i3 + 1;
                    iArr[i3] = i4;
                    i3 += 2;
                    iArr[i10] = i6;
                } else if (i7 <= i4) {
                    int i11 = i3 + 1;
                    iArr[i3] = i4;
                    i3 += 2;
                    iArr[i11] = i9;
                    iArr2[i] = i9 + 1;
                    i2 += 2;
                } else if (i6 <= i9) {
                    int i12 = i3 + 1;
                    iArr[i3] = i7;
                    i3 += 2;
                    iArr[i12] = i6;
                } else {
                    int i13 = i3 + 1;
                    iArr[i3] = i7;
                    i3 += 2;
                    iArr[i13] = i9;
                    iArr2[i] = i9 + 1;
                }
            }
            i += 2;
        }
        while (true) {
            int[] iArr4 = this.ranges;
            if (i >= iArr4.length) {
                int[] iArr5 = new int[i3];
                this.ranges = iArr5;
                System.arraycopy(iArr, 0, iArr5, 0, i3);
                return;
            } else {
                int i14 = i3 + 1;
                int i15 = i + 1;
                iArr[i3] = iArr4[i];
                i3 += 2;
                i += 2;
                iArr[i14] = iArr4[i15];
            }
        }
    }

    @Override // org.apache.xerces.impl.xpath.regex.Token
    boolean match(int i) {
        if (this.map == null) {
            createMap();
        }
        if (this.type == 4) {
            if (i < 256) {
                return ((1 << (i & 31)) & this.map[i / 32]) != 0;
            }
            int i2 = this.nonMapIndex;
            while (true) {
                int[] iArr = this.ranges;
                if (i2 >= iArr.length) {
                    return false;
                }
                if (iArr[i2] <= i && i <= iArr[i2 + 1]) {
                    return true;
                }
                i2 += 2;
            }
        } else {
            if (i < 256) {
                return ((1 << (i & 31)) & this.map[i / 32]) == 0;
            }
            int i3 = this.nonMapIndex;
            while (true) {
                int[] iArr2 = this.ranges;
                if (i3 >= iArr2.length) {
                    return true;
                }
                if (iArr2[i3] <= i && i <= iArr2[i3 + 1]) {
                    return false;
                }
                i3 += 2;
            }
        }
    }

    @Override // org.apache.xerces.impl.xpath.regex.Token
    protected void mergeRanges(Token token) {
        RangeToken rangeToken = (RangeToken) token;
        sortRanges();
        rangeToken.sortRanges();
        if (rangeToken.ranges == null) {
            return;
        }
        this.icaseCache = null;
        setSorted(true);
        int[] iArr = this.ranges;
        int i = 0;
        if (iArr == null) {
            int[] iArr2 = new int[rangeToken.ranges.length];
            this.ranges = iArr2;
            int[] iArr3 = rangeToken.ranges;
            System.arraycopy(iArr3, 0, iArr2, 0, iArr3.length);
            return;
        }
        int[] iArr4 = new int[iArr.length + rangeToken.ranges.length];
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr5 = this.ranges;
            if (i >= iArr5.length && i2 >= rangeToken.ranges.length) {
                this.ranges = iArr4;
                return;
            }
            if (i >= iArr5.length) {
                int i4 = i3 + 1;
                int[] iArr6 = rangeToken.ranges;
                int i5 = i2 + 1;
                iArr4[i3] = iArr6[i2];
                i3 += 2;
                i2 += 2;
                iArr4[i4] = iArr6[i5];
            } else {
                int[] iArr7 = rangeToken.ranges;
                if (i2 >= iArr7.length) {
                    int i6 = i3 + 1;
                    int i7 = i + 1;
                    iArr4[i3] = iArr5[i];
                    i3 += 2;
                    i += 2;
                    iArr4[i6] = iArr5[i7];
                } else {
                    int i8 = iArr7[i2];
                    int i9 = iArr5[i];
                    if (i8 < i9 || (i8 == i9 && iArr7[i2 + 1] < iArr5[i + 1])) {
                        int i10 = i3 + 1;
                        int i11 = i2 + 1;
                        iArr4[i3] = i8;
                        i3 += 2;
                        i2 += 2;
                        iArr4[i10] = iArr7[i11];
                    } else {
                        int i12 = i3 + 1;
                        int i13 = i + 1;
                        iArr4[i3] = i9;
                        i3 += 2;
                        i += 2;
                        iArr4[i12] = iArr5[i13];
                    }
                }
            }
        }
    }

    @Override // org.apache.xerces.impl.xpath.regex.Token
    protected void sortRanges() {
        int[] iArr;
        if (isSorted() || (iArr = this.ranges) == null) {
            return;
        }
        for (int length = iArr.length - 4; length >= 0; length -= 2) {
            int i = 0;
            while (i <= length) {
                int[] iArr2 = this.ranges;
                int i2 = iArr2[i];
                int i3 = i + 2;
                int i4 = iArr2[i3];
                if (i2 > i4 || (i2 == i4 && iArr2[i + 1] > iArr2[i + 3])) {
                    iArr2[i3] = i2;
                    iArr2[i] = i4;
                    int i5 = i + 3;
                    int i6 = iArr2[i5];
                    int i7 = i + 1;
                    iArr2[i5] = iArr2[i7];
                    iArr2[i7] = i6;
                }
                i = i3;
            }
        }
        setSorted(true);
    }

    @Override // org.apache.xerces.impl.xpath.regex.Token
    protected void subtractRanges(Token token) {
        if (token.type == 5) {
            intersectRanges(token);
            return;
        }
        RangeToken rangeToken = (RangeToken) token;
        if (rangeToken.ranges == null || this.ranges == null) {
            return;
        }
        this.icaseCache = null;
        sortRanges();
        compactRanges();
        rangeToken.sortRanges();
        rangeToken.compactRanges();
        int[] iArr = new int[this.ranges.length + rangeToken.ranges.length];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr2 = this.ranges;
            if (i >= iArr2.length) {
                break;
            }
            int[] iArr3 = rangeToken.ranges;
            if (i2 >= iArr3.length) {
                break;
            }
            int i4 = iArr2[i];
            int i5 = i + 1;
            int i6 = iArr2[i5];
            int i7 = iArr3[i2];
            int i8 = i2 + 1;
            int i9 = iArr3[i8];
            if (i6 < i7) {
                int i10 = i3 + 1;
                iArr[i3] = i4;
                i3 += 2;
                i += 2;
                iArr[i10] = iArr2[i5];
            } else if (i6 < i7 || i4 > i9) {
                if (i9 >= i4) {
                    throw new RuntimeException("Token#subtractRanges(): Internal Error: [" + this.ranges[i] + "," + this.ranges[i5] + "] - [" + rangeToken.ranges[i2] + "," + rangeToken.ranges[i8] + "]");
                }
                i2 += 2;
            } else {
                if (i7 > i4 || i6 > i9) {
                    if (i7 <= i4) {
                        iArr2[i] = i9 + 1;
                    } else if (i6 <= i9) {
                        int i11 = i3 + 1;
                        iArr[i3] = i4;
                        i3 += 2;
                        iArr[i11] = i7 - 1;
                    } else {
                        int i12 = i3 + 1;
                        iArr[i3] = i4;
                        i3 += 2;
                        iArr[i12] = i7 - 1;
                        iArr2[i] = i9 + 1;
                    }
                    i2 += 2;
                }
                i += 2;
            }
        }
        while (true) {
            int[] iArr4 = this.ranges;
            if (i >= iArr4.length) {
                int[] iArr5 = new int[i3];
                this.ranges = iArr5;
                System.arraycopy(iArr, 0, iArr5, 0, i3);
                return;
            } else {
                int i13 = i3 + 1;
                int i14 = i + 1;
                iArr[i3] = iArr4[i];
                i3 += 2;
                i += 2;
                iArr[i13] = iArr4[i14];
            }
        }
    }

    @Override // org.apache.xerces.impl.xpath.regex.Token
    public String toString(int i) {
        StringBuffer stringBuffer;
        String strEscapeCharInCharClass;
        String strEscapeCharInCharClass2;
        int i2 = 0;
        if (this.type == 4) {
            if (this == Token.token_dot) {
                return ".";
            }
            if (this == Token.token_0to9) {
                return "\\d";
            }
            if (this == Token.token_wordchars) {
                return "\\w";
            }
            if (this == Token.token_spaces) {
                return "\\s";
            }
            stringBuffer = new StringBuffer("[");
            while (i2 < this.ranges.length) {
                if ((i & 1024) != 0 && i2 > 0) {
                    stringBuffer.append(',');
                }
                int[] iArr = this.ranges;
                int i3 = iArr[i2];
                int i4 = i2 + 1;
                if (i3 == iArr[i4]) {
                    strEscapeCharInCharClass2 = escapeCharInCharClass(i3);
                } else {
                    stringBuffer.append(escapeCharInCharClass(i3));
                    stringBuffer.append('-');
                    strEscapeCharInCharClass2 = escapeCharInCharClass(this.ranges[i4]);
                }
                stringBuffer.append(strEscapeCharInCharClass2);
                i2 += 2;
            }
        } else {
            if (this == Token.token_not_0to9) {
                return "\\D";
            }
            if (this == Token.token_not_wordchars) {
                return "\\W";
            }
            if (this == Token.token_not_spaces) {
                return "\\S";
            }
            stringBuffer = new StringBuffer("[^");
            while (i2 < this.ranges.length) {
                if ((i & 1024) != 0 && i2 > 0) {
                    stringBuffer.append(',');
                }
                int[] iArr2 = this.ranges;
                int i5 = iArr2[i2];
                int i6 = i2 + 1;
                if (i5 == iArr2[i6]) {
                    strEscapeCharInCharClass = escapeCharInCharClass(i5);
                } else {
                    stringBuffer.append(escapeCharInCharClass(i5));
                    stringBuffer.append('-');
                    strEscapeCharInCharClass = escapeCharInCharClass(this.ranges[i6]);
                }
                stringBuffer.append(strEscapeCharInCharClass);
                i2 += 2;
            }
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }
}
