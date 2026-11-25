package org.apache.xerces.impl.xpath.regex;

import com.beust.jcommander.Parameters;
import java.io.PrintStream;
import java.text.CharacterIterator;
import net.bytebuddy.asm.Advice;
import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes13.dex */
public final class REUtil {
    static final int CACHESIZE = 20;
    static final RegularExpression[] regexCache = new RegularExpression[20];

    private REUtil() {
    }

    static final int composeFromSurrogates(int i, int i2) {
        return ((((i - 55296) << 10) + 65536) + i2) - 56320;
    }

    static final String createOptionString(int i) {
        StringBuffer stringBuffer = new StringBuffer(9);
        if ((i & 256) != 0) {
            stringBuffer.append(Util.C_FLOAT);
        }
        if ((i & 128) != 0) {
            stringBuffer.append('H');
        }
        if ((i & 512) != 0) {
            stringBuffer.append('X');
        }
        if ((i & 2) != 0) {
            stringBuffer.append('i');
        }
        if ((i & 8) != 0) {
            stringBuffer.append(Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL);
        }
        if ((i & 4) != 0) {
            stringBuffer.append(Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL);
        }
        if ((i & 32) != 0) {
            stringBuffer.append('u');
        }
        if ((i & 64) != 0) {
            stringBuffer.append('w');
        }
        if ((i & 16) != 0) {
            stringBuffer.append('x');
        }
        if ((i & 1024) != 0) {
            stringBuffer.append(',');
        }
        return stringBuffer.toString().intern();
    }

    public static RegularExpression createRegex(String str, String str2) throws ParseException {
        RegularExpression regularExpression;
        int options = parseOptions(str2);
        synchronized (regexCache) {
            int i = 0;
            while (true) {
                regularExpression = null;
                if (i >= 20) {
                    break;
                }
                try {
                    RegularExpression regularExpression2 = regexCache[i];
                    if (regularExpression2 == null) {
                        i = -1;
                        break;
                    }
                    if (regularExpression2.equals(str, options)) {
                        regularExpression = regularExpression2;
                        break;
                    }
                    i++;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (regularExpression == null) {
                regularExpression = new RegularExpression(str, str2);
                RegularExpression[] regularExpressionArr = regexCache;
                System.arraycopy(regularExpressionArr, 0, regularExpressionArr, 1, 19);
                regularExpressionArr[0] = regularExpression;
            } else if (i != 0) {
                RegularExpression[] regularExpressionArr2 = regexCache;
                System.arraycopy(regularExpressionArr2, 0, regularExpressionArr2, 1, i);
                regularExpressionArr2[0] = regularExpression;
            }
        }
        return regularExpression;
    }

    static final String decomposeToSurrogates(int i) {
        int i2 = i - 65536;
        return new String(new char[]{(char) ((i2 >> 10) + 55296), (char) ((i2 & 1023) + 56320)});
    }

    static void dumpString(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.print(Integer.toHexString(str.charAt(i)));
            System.out.print(" ");
        }
        System.out.println();
    }

    static final int getOptionValue(int i) {
        if (i == 44) {
            return 1024;
        }
        if (i == 70) {
            return 256;
        }
        if (i == 72) {
            return 128;
        }
        if (i == 88) {
            return 512;
        }
        if (i == 105) {
            return 2;
        }
        if (i == 109) {
            return 8;
        }
        if (i == 115) {
            return 4;
        }
        if (i == 117) {
            return 32;
        }
        if (i != 119) {
            return i != 120 ? 0 : 16;
        }
        return 64;
    }

    static final boolean isHighSurrogate(int i) {
        return (i & 64512) == 55296;
    }

    static final boolean isLowSurrogate(int i) {
        return (i & 64512) == 56320;
    }

    public static void main(String[] strArr) {
        PrintStream printStream;
        String str;
        PrintStream printStream2;
        String str2;
        PrintStream printStream3;
        String str3;
        StringBuilder sbAppend;
        String str4 = null;
        try {
            if (strArr.length == 0) {
                System.out.println("Error:Usage: java REUtil -i|-m|-s|-u|-w|-X regularExpression String");
                System.exit(0);
            }
            String string = "";
            String str5 = null;
            for (int i = 0; i < strArr.length; i++) {
                if (strArr[i].length() != 0 && strArr[i].charAt(0) == '-') {
                    if (strArr[i].equals("-i")) {
                        sbAppend = new StringBuilder().append(string).append(ASTExpr.DEFAULT_INDEX_VARIABLE_NAME);
                    } else if (strArr[i].equals("-m")) {
                        sbAppend = new StringBuilder().append(string).append("m");
                    } else if (strArr[i].equals("-s")) {
                        sbAppend = new StringBuilder().append(string).append("s");
                    } else if (strArr[i].equals("-u")) {
                        sbAppend = new StringBuilder().append(string).append("u");
                    } else if (strArr[i].equals("-w")) {
                        sbAppend = new StringBuilder().append(string).append("w");
                    } else if (strArr[i].equals("-X")) {
                        sbAppend = new StringBuilder().append(string).append(SpriteActivity.EXTRA_X_TRANSFORM);
                    } else {
                        printStream3 = System.err;
                        str3 = "Unknown option: " + strArr[i];
                        printStream3.println(str3);
                    }
                    string = sbAppend.toString();
                } else if (str4 == null) {
                    str4 = strArr[i];
                } else if (str5 == null) {
                    str5 = strArr[i];
                } else {
                    printStream3 = System.err;
                    str3 = "Unnecessary: " + strArr[i];
                    printStream3.println(str3);
                }
            }
            RegularExpression regularExpression = new RegularExpression(str4, string);
            System.out.println("RegularExpression: " + regularExpression);
            Match match = new Match();
            regularExpression.matches(str5, match);
            for (int i2 = 0; i2 < match.getNumberOfGroups(); i2++) {
                if (i2 == 0) {
                    printStream = System.out;
                    str = "Matched range for the whole pattern: ";
                } else {
                    printStream = System.out;
                    str = "[" + i2 + "]: ";
                }
                printStream.print(str);
                if (match.getBeginning(i2) < 0) {
                    printStream2 = System.out;
                    str2 = ServerAuthenticationConstants.TOKEN_CODE_INVALID;
                } else {
                    System.out.print(match.getBeginning(i2) + ", " + match.getEnd(i2) + ", ");
                    printStream2 = System.out;
                    str2 = "\"" + match.getCapturedText(i2) + "\"";
                }
                printStream2.println(str2);
            }
        } catch (ParseException e) {
            if (str4 == null) {
                e.printStackTrace();
                return;
            }
            System.err.println("org.apache.xerces.utils.regex.ParseException: " + e.getMessage());
            System.err.println("        " + str4);
            int location = e.getLocation();
            if (location >= 0) {
                System.err.print("        ");
                for (int i3 = 0; i3 < location; i3++) {
                    System.err.print(Parameters.DEFAULT_OPTION_PREFIXES);
                }
                System.err.println("^");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean matches(String str, String str2) throws ParseException {
        return createRegex(str, null).matches(str2);
    }

    public static boolean matches(String str, String str2, String str3) throws ParseException {
        return createRegex(str, str2).matches(str3);
    }

    static final int parseOptions(String str) throws ParseException {
        if (str == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            int optionValue = getOptionValue(str.charAt(i2));
            if (optionValue == 0) {
                throw new ParseException("Unknown Option: " + str.substring(i2), -1);
            }
            i |= optionValue;
        }
        return i;
    }

    public static String quoteMeta(String str) {
        int length = str.length();
        StringBuffer stringBuffer = null;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (".*+?{[()|\\^$".indexOf(cCharAt) >= 0) {
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer(((length - i) * 2) + i);
                    if (i > 0) {
                        stringBuffer.append(str.substring(0, i));
                    }
                }
                stringBuffer.append('\\');
            } else {
                if (stringBuffer != null) {
                }
            }
            stringBuffer.append(cCharAt);
        }
        return stringBuffer != null ? stringBuffer.toString() : str;
    }

    static String stripExtendedComment(String str) {
        char c;
        int i;
        char cCharAt;
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int i4 = i2 + 1;
            char cCharAt2 = str.charAt(i2);
            if (cCharAt2 == '\t' || cCharAt2 == '\n' || cCharAt2 == '\f' || cCharAt2 == '\r' || cCharAt2 == ' ') {
                if (i3 > 0) {
                    stringBuffer.append(cCharAt2);
                }
                i2 = i4;
                break;
            }
            if (cCharAt2 == '#') {
                while (true) {
                    i2 = i4;
                    if (i2 >= length) {
                        break;
                    }
                    i4 = i2 + 1;
                    char cCharAt3 = str.charAt(i2);
                    if (cCharAt3 == '\r' || cCharAt3 == '\n') {
                        i2 = i4;
                        break;
                        break;
                    }
                }
            } else {
                if (cCharAt2 != '\\' || i4 >= length) {
                    if (cCharAt2 != '[') {
                        if (i3 > 0 && cCharAt2 == ']') {
                            i3--;
                        }
                        stringBuffer.append(cCharAt2);
                        i2 = i4;
                        break;
                        break;
                    }
                    i3++;
                    stringBuffer.append(cCharAt2);
                    if (i4 < length) {
                        char cCharAt4 = str.charAt(i4);
                        if (cCharAt4 == '[' || cCharAt4 == ']') {
                            c = cCharAt4;
                        } else if (cCharAt4 == '^' && (i = i2 + 2) < length && ((cCharAt = str.charAt(i)) == '[' || cCharAt == ']')) {
                            stringBuffer.append(Util.C_EXCEPTION_START);
                            stringBuffer.append(cCharAt);
                            i2 += 3;
                        }
                    }
                    i2 = i4;
                    break;
                    break;
                }
                char cCharAt5 = str.charAt(i4);
                if (cCharAt5 != '#' && cCharAt5 != '\t' && cCharAt5 != '\n' && cCharAt5 != '\f' && cCharAt5 != '\r' && cCharAt5 != ' ') {
                    stringBuffer.append('\\');
                }
                c = cCharAt5;
                stringBuffer.append(c);
                i2 += 2;
            }
        }
        return stringBuffer.toString();
    }

    static final String substring(CharacterIterator characterIterator, int i, int i2) {
        int i3 = i2 - i;
        char[] cArr = new char[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            cArr[i4] = characterIterator.setIndex(i4 + i);
        }
        return new String(cArr);
    }
}
