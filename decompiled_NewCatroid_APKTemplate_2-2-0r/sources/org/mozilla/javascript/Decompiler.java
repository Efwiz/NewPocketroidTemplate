package org.mozilla.javascript;

import io.netty.util.internal.StringUtil;
import okhttp3.internal.ws.WebSocketProtocol;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes4.dex */
public class Decompiler {
    public static final int CASE_GAP_PROP = 3;
    private static final int FUNCTION_END = 168;
    public static final int INDENT_GAP_PROP = 2;
    public static final int INITIAL_INDENT_PROP = 1;
    public static final int ONLY_BODY_FLAG = 1;
    public static final int TO_SOURCE_FLAG = 2;
    private static final boolean printSource = false;
    private char[] sourceBuffer = new char[128];
    private int sourceTop;

    String getEncodedSource() {
        return sourceToString(0);
    }

    int getCurrentOffset() {
        return this.sourceTop;
    }

    int markFunctionStart(int i) {
        int currentOffset = getCurrentOffset();
        if (i != 4) {
            addToken(110);
            append((char) i);
        }
        return currentOffset;
    }

    int markFunctionEnd(int i) {
        int currentOffset = getCurrentOffset();
        append((char) 168);
        return currentOffset;
    }

    void addToken(int i) {
        if (i < 0 || i > 167) {
            throw new IllegalArgumentException();
        }
        append((char) i);
    }

    void addEOL(int i) {
        if (i < 0 || i > 167) {
            throw new IllegalArgumentException();
        }
        append((char) i);
        append((char) 1);
    }

    void addName(String str) {
        addToken(39);
        appendString(str);
    }

    void addString(String str) {
        addToken(41);
        appendString(str);
    }

    void addRegexp(String str, String str2) {
        addToken(48);
        appendString("/" + str + '/' + str2);
    }

    void addNumber(double d) {
        addToken(40);
        long j = (long) d;
        if (j != d) {
            long jDoubleToLongBits = Double.doubleToLongBits(d);
            append(Util.C_DOUBLE);
            append((char) (jDoubleToLongBits >> 48));
            append((char) (jDoubleToLongBits >> 32));
            append((char) (jDoubleToLongBits >> 16));
            append((char) jDoubleToLongBits);
            return;
        }
        if (j < 0) {
            Kit.codeBug();
        }
        if (j <= WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            append(Util.C_SHORT);
            append((char) j);
            return;
        }
        append(Util.C_LONG);
        append((char) (j >> 48));
        append((char) (j >> 32));
        append((char) (j >> 16));
        append((char) j);
    }

    private void appendString(String str) {
        int length = str.length();
        int i = this.sourceTop + (length >= 32768 ? 2 : 1) + length;
        if (i > this.sourceBuffer.length) {
            increaseSourceCapacity(i);
        }
        if (length >= 32768) {
            char[] cArr = this.sourceBuffer;
            int i2 = this.sourceTop;
            cArr[i2] = (char) (32768 | (length >>> 16));
            this.sourceTop = i2 + 1;
        }
        char[] cArr2 = this.sourceBuffer;
        int i3 = this.sourceTop;
        cArr2[i3] = (char) length;
        int i4 = i3 + 1;
        this.sourceTop = i4;
        str.getChars(0, length, cArr2, i4);
        this.sourceTop = i;
    }

    private void append(char c) {
        int i = this.sourceTop;
        if (i == this.sourceBuffer.length) {
            increaseSourceCapacity(i + 1);
        }
        char[] cArr = this.sourceBuffer;
        int i2 = this.sourceTop;
        cArr[i2] = c;
        this.sourceTop = i2 + 1;
    }

    private void increaseSourceCapacity(int i) {
        if (i <= this.sourceBuffer.length) {
            Kit.codeBug();
        }
        char[] cArr = this.sourceBuffer;
        int length = cArr.length * 2;
        if (length >= i) {
            i = length;
        }
        char[] cArr2 = new char[i];
        System.arraycopy(cArr, 0, cArr2, 0, this.sourceTop);
        this.sourceBuffer = cArr2;
    }

    private String sourceToString(int i) {
        if (i < 0 || this.sourceTop < i) {
            Kit.codeBug();
        }
        return new String(this.sourceBuffer, i, this.sourceTop - i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:139:0x025f  */
    /* JADX WARN: Code duplicated, block: B:70:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:72:0x00ee  */
    /* JADX WARN: Code duplicated, block: B:73:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:75:0x00fc  */
    public static String decompile(String str, int i, UintMap uintMap) {
        byte bCharAt;
        int iPrintSourceString;
        boolean z;
        int i2;
        char c;
        int length = str.length();
        if (length == 0) {
            return "";
        }
        int i3 = uintMap.getInt(1, 0);
        if (i3 < 0) {
            throw new IllegalArgumentException();
        }
        char c2 = 4;
        int i4 = uintMap.getInt(2, 4);
        if (i4 < 0) {
            throw new IllegalArgumentException();
        }
        int i5 = uintMap.getInt(3, 2);
        if (i5 < 0) {
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder();
        boolean z2 = (i & 1) != 0;
        boolean z3 = (i & 2) != 0;
        if (str.charAt(0) == 137) {
            bCharAt = -1;
            iPrintSourceString = 1;
        } else {
            bCharAt = str.charAt(1);
            iPrintSourceString = 0;
        }
        if (!z3) {
            sb.append('\n');
            for (int i6 = 0; i6 < i3; i6++) {
                sb.append(StringUtil.SPACE);
            }
        } else if (bCharAt == 2) {
            sb.append('(');
        }
        int i7 = 0;
        boolean z4 = false;
        while (iPrintSourceString < length) {
            char cCharAt = str.charAt(iPrintSourceString);
            if (cCharAt != 1) {
                if (cCharAt == c2) {
                    sb.append("return");
                    if (83 != getNext(str, length, iPrintSourceString)) {
                        sb.append(StringUtil.SPACE);
                    }
                } else if (cCharAt == '2') {
                    sb.append("throw ");
                } else if (cCharAt == 'C') {
                    sb.append(": ");
                } else if (cCharAt == 'I') {
                    sb.append("yield ");
                } else if (cCharAt == 161) {
                    sb.append("debugger;\n");
                } else if (cCharAt != 168) {
                    if (cCharAt == '4') {
                        sb.append(" in ");
                    } else if (cCharAt == '5') {
                        sb.append(" instanceof ");
                    } else if (cCharAt == 144) {
                        sb.append("..");
                    } else if (cCharAt == 145) {
                        sb.append("::");
                    } else if (cCharAt == 147) {
                        sb.append(".(");
                    } else if (cCharAt != 148) {
                        switch (cCharAt) {
                            case '\t':
                                sb.append(" | ");
                                break;
                            case '\n':
                                sb.append(" ^ ");
                                break;
                            case 11:
                                sb.append(" & ");
                                break;
                            case '\f':
                                sb.append(" == ");
                                break;
                            case '\r':
                                sb.append(" != ");
                                break;
                            case 14:
                                sb.append(" < ");
                                break;
                            case 15:
                                sb.append(" <= ");
                                break;
                            case 16:
                                sb.append(" > ");
                                break;
                            case 17:
                                sb.append(" >= ");
                                break;
                            case 18:
                                sb.append(" << ");
                                break;
                            case 19:
                                sb.append(" >> ");
                                break;
                            case 20:
                                sb.append(" >>> ");
                                break;
                            case 21:
                                sb.append(" + ");
                                break;
                            case 22:
                                sb.append(" - ");
                                break;
                            case 23:
                                sb.append(" * ");
                                break;
                            case 24:
                                sb.append(" / ");
                                break;
                            case 25:
                                sb.append(" % ");
                                break;
                            case 26:
                                sb.append('!');
                                break;
                            case 27:
                                sb.append('~');
                                break;
                            case 28:
                                sb.append('+');
                                break;
                            case 29:
                                sb.append('-');
                                break;
                            case 30:
                                sb.append("new ");
                                break;
                            case 31:
                                sb.append("delete ");
                                break;
                            case ' ':
                                sb.append("typeof ");
                                break;
                            default:
                                switch (cCharAt) {
                                    case '\'':
                                    case '0':
                                        c = '(';
                                        iPrintSourceString = printSourceString(str, iPrintSourceString + 1, false, sb);
                                        c2 = 4;
                                        break;
                                    case '(':
                                        c = '(';
                                        iPrintSourceString = printSourceNumber(str, iPrintSourceString + 1, sb);
                                        c2 = 4;
                                        break;
                                    case ')':
                                        c = '(';
                                        iPrintSourceString = printSourceString(str, iPrintSourceString + 1, true, sb);
                                        c2 = 4;
                                        break;
                                    case '*':
                                        sb.append("null");
                                        break;
                                    case '+':
                                        sb.append("this");
                                        break;
                                    case ',':
                                        sb.append("false");
                                        break;
                                    case '-':
                                        sb.append("true");
                                        break;
                                    case '.':
                                        sb.append(" === ");
                                        break;
                                    case '/':
                                        sb.append(" !== ");
                                        break;
                                    default:
                                        switch (cCharAt) {
                                            case 'R':
                                                sb.append("try ");
                                                break;
                                            case 'S':
                                                sb.append(';');
                                                if (1 != getNext(str, length, iPrintSourceString)) {
                                                    sb.append(StringUtil.SPACE);
                                                }
                                                break;
                                            case 'T':
                                                sb.append('[');
                                                break;
                                            case 'U':
                                                sb.append(']');
                                                break;
                                            case 'V':
                                                i7++;
                                                if (1 == getNext(str, length, iPrintSourceString)) {
                                                    i3 += i4;
                                                }
                                                sb.append('{');
                                                break;
                                            case 'W':
                                                i7--;
                                                if (!z2 || i7 != 0) {
                                                    sb.append('}');
                                                    int next = getNext(str, length, iPrintSourceString);
                                                    if (next == 1) {
                                                        i3 -= i4;
                                                    } else if (next == 114 || next == 118) {
                                                        i3 -= i4;
                                                        sb.append(StringUtil.SPACE);
                                                    } else if (next == 168) {
                                                        i3 -= i4;
                                                    }
                                                }
                                                break;
                                            case 'X':
                                                sb.append('(');
                                                break;
                                            case 'Y':
                                                sb.append(')');
                                                if (86 == getNext(str, length, iPrintSourceString)) {
                                                    sb.append(StringUtil.SPACE);
                                                }
                                                break;
                                            case 'Z':
                                                sb.append(", ");
                                                break;
                                            case '[':
                                                sb.append(" = ");
                                                break;
                                            case '\\':
                                                sb.append(" |= ");
                                                break;
                                            case ']':
                                                sb.append(" ^= ");
                                                break;
                                            case '^':
                                                sb.append(" &= ");
                                                break;
                                            case '_':
                                                sb.append(" <<= ");
                                                break;
                                            case '`':
                                                sb.append(" >>= ");
                                                break;
                                            case 'a':
                                                sb.append(" >>>= ");
                                                break;
                                            case 'b':
                                                sb.append(" += ");
                                                break;
                                            case 'c':
                                                sb.append(" -= ");
                                                break;
                                            case 'd':
                                                sb.append(" *= ");
                                                break;
                                            case 'e':
                                                sb.append(" /= ");
                                                break;
                                            case 'f':
                                                sb.append(" %= ");
                                                break;
                                            case 'g':
                                                sb.append(" ? ");
                                                break;
                                            case 'h':
                                                if (1 == getNext(str, length, iPrintSourceString)) {
                                                    sb.append(':');
                                                } else {
                                                    sb.append(" : ");
                                                }
                                                break;
                                            case 'i':
                                                sb.append(" || ");
                                                break;
                                            case 'j':
                                                sb.append(" && ");
                                                break;
                                            case 'k':
                                                sb.append("++");
                                                break;
                                            case 'l':
                                                sb.append("--");
                                                break;
                                            case 'm':
                                                sb.append('.');
                                                break;
                                            case 'n':
                                                iPrintSourceString++;
                                                sb.append("function ");
                                                break;
                                            default:
                                                switch (cCharAt) {
                                                    case 'q':
                                                        sb.append("if ");
                                                        break;
                                                    case 'r':
                                                        sb.append("else ");
                                                        break;
                                                    case 's':
                                                        sb.append("switch ");
                                                        break;
                                                    case 't':
                                                        sb.append("case ");
                                                        break;
                                                    case 'u':
                                                        sb.append("default");
                                                        break;
                                                    case 'v':
                                                        sb.append("while ");
                                                        break;
                                                    case 'w':
                                                        sb.append("do ");
                                                        break;
                                                    case 'x':
                                                        sb.append("for ");
                                                        break;
                                                    case 'y':
                                                        sb.append(PsiKeyword.BREAK);
                                                        if (39 == getNext(str, length, iPrintSourceString)) {
                                                            sb.append(StringUtil.SPACE);
                                                        }
                                                        break;
                                                    case 'z':
                                                        sb.append(PsiKeyword.CONTINUE);
                                                        if (39 == getNext(str, length, iPrintSourceString)) {
                                                            sb.append(StringUtil.SPACE);
                                                        }
                                                        break;
                                                    case '{':
                                                        sb.append("var ");
                                                        break;
                                                    case '|':
                                                        sb.append("with ");
                                                        break;
                                                    case '}':
                                                        sb.append("catch ");
                                                        break;
                                                    case '~':
                                                        sb.append("finally ");
                                                        break;
                                                    case 127:
                                                        sb.append("void ");
                                                        break;
                                                    default:
                                                        switch (cCharAt) {
                                                            case 152:
                                                            case 153:
                                                                if (str.charAt(iPrintSourceString) == 152) {
                                                                    sb.append("get ");
                                                                } else if (str.charAt(iPrintSourceString) == 153) {
                                                                    sb.append("set ");
                                                                }
                                                                iPrintSourceString = printSourceString(str, iPrintSourceString + 2, false, sb) + 1;
                                                                break;
                                                            case 154:
                                                                sb.append("let ");
                                                                break;
                                                            case 155:
                                                                sb.append("const ");
                                                                break;
                                                            default:
                                                                switch (cCharAt) {
                                                                    case 164:
                                                                        if (str.charAt(iPrintSourceString) == 152) {
                                                                            sb.append("get ");
                                                                        } else if (str.charAt(iPrintSourceString) == 153) {
                                                                            sb.append("set ");
                                                                        }
                                                                        iPrintSourceString = printSourceString(str, iPrintSourceString + 2, false, sb) + 1;
                                                                        break;
                                                                    case 165:
                                                                        sb.append(" => ");
                                                                        break;
                                                                    case 166:
                                                                        sb.append("yield *");
                                                                        break;
                                                                    default:
                                                                        throw new RuntimeException("Token: " + Token.name(str.charAt(iPrintSourceString)));
                                                                }
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                    } else {
                        sb.append('@');
                    }
                }
            } else if (!z3) {
                if (z4) {
                    z = true;
                } else if (z2) {
                    sb.setLength(0);
                    i3 -= i4;
                    z4 = true;
                    z = false;
                } else {
                    z = true;
                    z4 = true;
                }
                if (z) {
                    sb.append('\n');
                }
                int i8 = iPrintSourceString + 1;
                if (i8 < length) {
                    char cCharAt2 = str.charAt(i8);
                    if (cCharAt2 == 't' || cCharAt2 == 'u') {
                        i2 = i4 - i5;
                    } else {
                        i2 = (cCharAt2 == 'W' || (cCharAt2 == '\'' && str.charAt(getSourceStringEnd(str, iPrintSourceString + 2)) == 'h')) ? i4 : 0;
                    }
                    while (i2 < i3) {
                        sb.append(StringUtil.SPACE);
                        i2++;
                    }
                }
            }
            iPrintSourceString++;
            c2 = 4;
        }
        if (z3) {
            if (bCharAt == 2) {
                sb.append(')');
            }
        } else if (!z2) {
            sb.append('\n');
        }
        return sb.toString();
    }

    private static int getNext(String str, int i, int i2) {
        int i3 = i2 + 1;
        if (i3 < i) {
            return str.charAt(i3);
        }
        return 0;
    }

    private static int getSourceStringEnd(String str, int i) {
        return printSourceString(str, i, false, null);
    }

    private static int printSourceString(String str, int i, boolean z, StringBuilder sb) {
        int iCharAt = str.charAt(i);
        int i2 = i + 1;
        if ((32768 & iCharAt) != 0) {
            iCharAt = ((iCharAt & 32767) << 16) | str.charAt(i2);
            i2 = i + 2;
        }
        if (sb != null) {
            String strSubstring = str.substring(i2, i2 + iCharAt);
            if (!z) {
                sb.append(strSubstring);
            } else {
                sb.append('\"');
                sb.append(ScriptRuntime.escapeString(strSubstring));
                sb.append('\"');
            }
        }
        return i2 + iCharAt;
    }

    private static int printSourceNumber(String str, int i, StringBuilder sb) {
        int i2;
        char cCharAt = str.charAt(i);
        int i3 = i + 1;
        double dLongBitsToDouble = 0.0d;
        if (cCharAt == 'S') {
            dLongBitsToDouble = sb != null ? str.charAt(i3) : 0.0d;
            i2 = i + 2;
        } else {
            if (cCharAt != 'J' && cCharAt != 'D') {
                throw new RuntimeException();
            }
            if (sb != null) {
                long jCharAt = (((long) str.charAt(i3)) << 48) | (((long) str.charAt(i + 2)) << 32) | (((long) str.charAt(i + 3)) << 16) | ((long) str.charAt(i + 4));
                dLongBitsToDouble = cCharAt == 'J' ? jCharAt : Double.longBitsToDouble(jCharAt);
            }
            i2 = i + 5;
        }
        if (sb != null) {
            sb.append(ScriptRuntime.numberToString(dLongBitsToDouble, 10));
        }
        return i2;
    }
}
