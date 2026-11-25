package org.jetbrains.kotlin.com.intellij.psi.util;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiLiteralUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String BIN_PREFIX = "0b";
    public static final String HEX_PREFIX = "0x";
    public static final String _2_IN_31 = Long.toString(-2147483648L).substring(1);
    public static final String _2_IN_63 = Long.toString(Long.MIN_VALUE).substring(1);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 7 || i == 15 || i == 23 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 7 || i == 15 || i == 23 || i == 3 || i == 4) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 7:
            case 15:
            case 23:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiLiteralUtil";
                break;
            case 2:
            case 29:
            case 30:
                objArr[0] = "text";
                break;
            case 5:
            case 6:
            case 8:
            case 10:
            case 12:
            case 24:
            case 25:
            case 26:
            case 28:
                objArr[0] = "s";
                break;
            case 9:
            case 11:
            case 13:
                objArr[0] = "result";
                break;
            case 14:
            case 16:
            case 17:
                objArr[0] = "str";
                break;
            case 18:
            case 19:
                objArr[0] = "expression";
                break;
            case 20:
            case 21:
                objArr[0] = "lines";
                break;
            case 22:
            case 31:
                objArr[0] = Constant.LINE;
                break;
            case 27:
                objArr[0] = "charPredicate";
                break;
            case 32:
                objArr[0] = "literal";
                break;
            default:
                objArr[0] = "charLiteral";
                break;
        }
        if (i == 1) {
            objArr[1] = "stringForCharLiteral";
        } else if (i == 7) {
            objArr[1] = "escapeTextBlockCharacters";
        } else if (i == 15) {
            objArr[1] = "escapeBackSlashesInTextBlock";
        } else if (i == 23) {
            objArr[1] = "trimTrailingWhitespaces";
        } else if (i == 3 || i == 4) {
            objArr[1] = "charLiteralForCharString";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiLiteralUtil";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 7:
            case 15:
            case 23:
                break;
            case 2:
                objArr[2] = "charLiteralForCharString";
                break;
            case 5:
            case 6:
                objArr[2] = "escapeTextBlockCharacters";
                break;
            case 8:
            case 9:
                objArr[2] = "parseQuotes";
                break;
            case 10:
            case 11:
                objArr[2] = "parseSpaces";
                break;
            case 12:
            case 13:
                objArr[2] = "parseBackSlashes";
                break;
            case 14:
                objArr[2] = "escapeBackSlashesInTextBlock";
                break;
            case 16:
                objArr[2] = "parseBackSlash";
                break;
            case 17:
                objArr[2] = "parseEscapedBackSlash";
                break;
            case 18:
                objArr[2] = "getTextBlockLines";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "getTextBlockIndent";
                break;
            case 22:
                objArr[2] = "trimTrailingWhitespaces";
                break;
            case 24:
                objArr[2] = "parseWhitespaceBackwards";
                break;
            case 25:
                objArr[2] = "countBackSlashes";
                break;
            case 26:
            case 27:
                objArr[2] = "parseUnicodeEscapeBackwards";
                break;
            case 28:
                objArr[2] = "findSlashU";
                break;
            case 29:
                objArr[2] = "mapBackStringRange";
                break;
            case 30:
                objArr[2] = "mapBackTextBlockRange";
                break;
            case 31:
                objArr[2] = "getCharEndIndex";
                break;
            case 32:
                objArr[2] = "tryConvertNumericLiteral";
                break;
            default:
                objArr[2] = "stringForCharLiteral";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 7 && i != 15 && i != 23 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static Integer parseInteger(String str) {
        try {
            if (str.startsWith(HEX_PREFIX)) {
                return Integer.valueOf((int) parseDigits(str.substring(2), 4, 32));
            }
            if (str.startsWith(BIN_PREFIX)) {
                return Integer.valueOf((int) parseDigits(str.substring(2), 1, 32));
            }
            if (StringUtil.startsWithChar(str, ExternalAnnotationProvider.NULLABLE)) {
                return Integer.valueOf((int) parseDigits(str, 3, 32));
            }
            return parseIntegerNoPrefix(str);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public static Integer parseIntegerNoPrefix(String str) {
        long j = Long.parseLong(str, 10);
        if (str.equals(_2_IN_31) || j == ((int) j)) {
            return Integer.valueOf((int) j);
        }
        return null;
    }

    public static Long parseLong(String str) {
        if (StringUtil.endsWithChar(str, 'L') || StringUtil.endsWithChar(str, 'l')) {
            str = str.substring(0, str.length() - 1);
        }
        try {
            if (str.startsWith(HEX_PREFIX)) {
                return Long.valueOf(parseDigits(str.substring(2), 4, 64));
            }
            if (str.startsWith(BIN_PREFIX)) {
                return Long.valueOf(parseDigits(str.substring(2), 1, 64));
            }
            if (StringUtil.startsWithChar(str, ExternalAnnotationProvider.NULLABLE)) {
                return Long.valueOf(parseDigits(str, 3, 64));
            }
            if (_2_IN_63.equals(str)) {
                return Long.MIN_VALUE;
            }
            return Long.valueOf(str, 10);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public static Float parseFloat(String str) {
        try {
            return Float.valueOf(str);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public static Double parseDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public static long parseDigits(String str, int i, int i2) throws NumberFormatException {
        int i3 = 1 << i;
        int length = str.length();
        if (length == 0) {
            throw new NumberFormatException(str);
        }
        long j = length == 1 ? 0L : Long.parseLong(str.substring(0, length - 1), i3);
        if ((((-1) << (i2 - i)) & j) != 0) {
            throw new NumberFormatException(str);
        }
        int iDigit = Character.digit(str.charAt(length - 1), i3);
        if (iDigit != -1) {
            return (j << i) | ((long) iDigit);
        }
        throw new NumberFormatException(str);
    }

    public static String stringForCharLiteral(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if ("'\"'".equals(str)) {
            return "\"\\\"\"";
        }
        if ("'\\''".equals(str)) {
            return "\"'\"";
        }
        String str2 = "\"" + str.substring(1, str.length() - 1) + '\"';
        if (str2 == null) {
            $$$reportNull$$$0(1);
        }
        return str2;
    }

    public static String charLiteralForCharString(String str) {
        String str2;
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        int length = str.length();
        if (length <= 1) {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            return str;
        }
        String strSubstring = str.substring(1, length - 1);
        if (FormatHelper.QUOTE.equals(strSubstring)) {
            str2 = "'\\''";
        } else if ("\\\"".equals(strSubstring)) {
            str2 = "'\"'";
        } else {
            str2 = FormatHelper.QUOTE + strSubstring + '\'';
        }
        if (str2 == null) {
            $$$reportNull$$$0(4);
        }
        return str2;
    }

    public static boolean isUnsafeLiteral(PsiLiteralExpression psiLiteralExpression) {
        IElementType tokenType = ((PsiJavaToken) psiLiteralExpression.getFirstChild()).getTokenType();
        return (tokenType == JavaTokenType.CHARACTER_LITERAL || tokenType == JavaTokenType.STRING_LITERAL || tokenType == JavaTokenType.TEXT_BLOCK_LITERAL) && psiLiteralExpression.getValue() == null;
    }

    public static String escapeTextBlockCharacters(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return escapeTextBlockCharacters(str, false, true, true);
    }

    public static String escapeTextBlockCharacters(String str, boolean z, boolean z2, boolean z3) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        int i = 0;
        while (i < length) {
            int quotes = parseQuotes(i, str, sb, z, z2);
            if (quotes == -1 && (quotes = parseSpaces(i, str, sb, z3)) == -1 && (quotes = parseBackSlashes(i, str, sb)) == -1) {
                sb.append(str.charAt(i));
                i++;
            } else {
                i = quotes;
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(7);
        }
        return string;
    }

    private static int parseQuotes(int i, String str, StringBuilder sb, boolean z, boolean z2) {
        int i2;
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (sb == null) {
            $$$reportNull$$$0(9);
        }
        if (str.charAt(i) != '\"') {
            return -1;
        }
        int i3 = i;
        int i4 = 1;
        while (true) {
            i2 = i3 + 1;
            int backSlash = i2 >= str.length() ? -1 : parseBackSlash(str, i2);
            if (backSlash == -1) {
                backSlash = i2;
            }
            if (backSlash >= str.length() || str.charAt(backSlash) != '\"') {
                break;
            }
            i4++;
            i3 = backSlash;
        }
        for (int i5 = 0; i5 < i4; i5++) {
            if ((i5 == 0 && i == 0 && z) || i5 % 3 == 2 || (i5 == i4 - 1 && i2 == str.length() && z2)) {
                sb.append("\\\"");
            } else {
                sb.append('\"');
            }
        }
        return i2;
    }

    private static int parseSpaces(int i, String str, StringBuilder sb, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (sb == null) {
            $$$reportNull$$$0(11);
        }
        if (str.charAt(i) != ' ') {
            return -1;
        }
        int i2 = 0;
        while (i < str.length() && str.charAt(i) == ' ') {
            i2++;
            i++;
        }
        if (i >= str.length() && z) {
            sb.append(StringUtil.repeat(" ", i2 - 1)).append("\\s");
            return i;
        }
        int backSlash = i >= str.length() ? -1 : parseBackSlash(str, i);
        if (backSlash != -1 && backSlash < str.length() && str.charAt(backSlash) == 'n') {
            sb.append(StringUtil.repeat(" ", i2 - 1)).append("\\s");
            return i;
        }
        sb.append(StringUtil.repeatSymbol(io.netty.util.internal.StringUtil.SPACE, i2));
        return i;
    }

    private static int parseBackSlashes(int i, String str, StringBuilder sb) {
        int backSlash;
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (sb == null) {
            $$$reportNull$$$0(13);
        }
        int backSlash2 = parseBackSlash(str, i);
        if (backSlash2 == -1) {
            return -1;
        }
        int i2 = 1;
        int i3 = i;
        int i4 = backSlash2;
        while (i4 < str.length() && (backSlash = parseBackSlash(str, i4)) != -1) {
            sb.append((CharSequence) str, i3, i4);
            i2++;
            i3 = i4;
            i4 = backSlash;
        }
        if (i4 >= str.length()) {
            sb.append((CharSequence) str, i3, str.length());
        } else if (i2 % 2 == 0) {
            sb.append((CharSequence) str, i3, i4);
        } else {
            char cCharAt = str.charAt(i4);
            if (cCharAt == 'n') {
                sb.append('\n');
            } else {
                if (cCharAt == '\"') {
                    return i4;
                }
                sb.append((CharSequence) str, i3, i4).append(cCharAt);
            }
            return i4 + 1;
        }
        return i4;
    }

    public static String escapeBackSlashesInTextBlock(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        int i = 0;
        while (i < length) {
            int backSlash = parseBackSlash(str, i);
            if (backSlash != -1) {
                sb.append("\\\\");
                i = backSlash;
            } else {
                sb.append(str.charAt(i));
                i++;
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(15);
        }
        return string;
    }

    public static int parseBackSlash(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        if (str.charAt(i) != '\\') {
            return -1;
        }
        int escapedBackSlash = parseEscapedBackSlash(str, i);
        return escapedBackSlash > 0 ? escapedBackSlash : i + 1;
    }

    private static int parseEscapedBackSlash(String str, int i) {
        int i2;
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        int i3 = i + 1;
        int length = str.length();
        if (i3 < length && str.charAt(i3) == 'u') {
            while (true) {
                i2 = i3 + 1;
                if (i2 >= length || str.charAt(i2) != 'u') {
                    break;
                }
                i3 = i2;
            }
            if (i3 + 4 >= length) {
                return -1;
            }
            int i4 = i3 + 5;
            try {
                if (Integer.parseInt(str.substring(i2, i4), 16) == 92) {
                    return i4;
                }
            } catch (NumberFormatException unused) {
            }
        }
        return -1;
    }

    public static String[] getTextBlockLines(PsiLiteralExpression psiLiteralExpression) {
        if (psiLiteralExpression == null) {
            $$$reportNull$$$0(18);
        }
        if (!psiLiteralExpression.isTextBlock()) {
            return null;
        }
        String text = psiLiteralExpression.getText();
        if (text.length() >= 7 && text.endsWith("\"\"\"")) {
            int i = 3;
            while (true) {
                int i2 = i + 1;
                char cCharAt = text.charAt(i);
                if (cCharAt != '\n') {
                    if (!Character.isWhitespace(cCharAt) || i2 == text.length()) {
                        break;
                    }
                    i = i2;
                } else {
                    return text.substring(i2, text.length() - 3).split("\n", -1);
                }
            }
        }
        return null;
    }

    public static int getTextBlockIndent(PsiLiteralExpression psiLiteralExpression) {
        if (psiLiteralExpression == null) {
            $$$reportNull$$$0(19);
        }
        String[] textBlockLines = getTextBlockLines(psiLiteralExpression);
        if (textBlockLines == null) {
            return -1;
        }
        return getTextBlockIndent(textBlockLines);
    }

    public static int getTextBlockIndent(String[] strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(20);
        }
        return getTextBlockIndent(strArr, false, false);
    }

    public static int getTextBlockIndent(String[] strArr, boolean z, boolean z2) {
        if (strArr == null) {
            $$$reportNull$$$0(21);
        }
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < strArr.length && i != 0; i2++) {
            String str = strArr[i2];
            int i3 = 0;
            while (i3 < str.length() && Character.isWhitespace(str.charAt(i3))) {
                i3++;
            }
            if (i3 != str.length() || (i2 >= strArr.length - 1 && !z2)) {
                if (i3 < i) {
                    i = i3;
                }
            } else if (!z) {
                strArr[i2] = "";
            }
        }
        return i;
    }

    public static String getStringLiteralContent(PsiLiteralExpression psiLiteralExpression) {
        String text = psiLiteralExpression.getText();
        int length = text.length();
        if (length > 1 && text.charAt(0) == '\"') {
            int i = length - 1;
            if (text.charAt(i) == '\"') {
                return text.substring(1, i);
            }
        }
        if (length > "&quot;".length() && text.startsWith("&quot;") && text.endsWith("&quot;")) {
            return text.substring("&quot;".length(), length - "&quot;".length());
        }
        return null;
    }

    public static String getTextBlockText(PsiLiteralExpression psiLiteralExpression) {
        String[] textBlockLines = getTextBlockLines(psiLiteralExpression);
        if (textBlockLines == null) {
            return null;
        }
        int textBlockIndent = getTextBlockIndent(textBlockLines);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < textBlockLines.length; i++) {
            String str = textBlockLines[i];
            if (str.length() > 0) {
                sb.append(trimTrailingWhitespaces(str.substring(textBlockIndent)));
            }
            if (i < textBlockLines.length - 1) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    private static String trimTrailingWhitespaces(String str) {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        int length = str.length();
        while (true) {
            int whitespaceBackwards = parseWhitespaceBackwards(str, length - 1);
            if (whitespaceBackwards == -1) {
                break;
            }
            length = whitespaceBackwards;
        }
        String strSubstring = str.substring(0, length);
        if (strSubstring == null) {
            $$$reportNull$$$0(23);
        }
        return strSubstring;
    }

    private static int parseWhitespaceBackwards(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(24);
        }
        if (i < 0) {
            return -1;
        }
        if (Character.isWhitespace(str.charAt(i))) {
            return i;
        }
        int unicodeEscapeBackwards = parseUnicodeEscapeBackwards(str, i, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Character.isWhitespace(((Character) obj).charValue());
            }
        });
        if (unicodeEscapeBackwards < 0) {
            return -1;
        }
        int i2 = unicodeEscapeBackwards - 1;
        if (((i2 < 0 || str.charAt(i2) != '\\') ? 1 : countBackSlashes(str, unicodeEscapeBackwards - 2) + 2) % 2 == 0) {
            return -1;
        }
        return unicodeEscapeBackwards;
    }

    private static int countBackSlashes(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        int i2 = 0;
        while (i >= 0) {
            if (str.charAt(i) != '\\') {
                i = parseUnicodeEscapeBackwards(str, i, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return PsiLiteralUtil.lambda$countBackSlashes$0((Character) obj);
                    }
                });
            }
            if (i == -1) {
                break;
            }
            i2++;
            i--;
        }
        return i2;
    }

    static /* synthetic */ boolean lambda$countBackSlashes$0(Character ch) {
        return ch.charValue() == '\\';
    }

    private static int parseUnicodeEscapeBackwards(String str, int i, Predicate<? super Character> predicate) {
        int iFindSlashU;
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        if (predicate == null) {
            $$$reportNull$$$0(27);
        }
        if (i - 5 < 0 || (iFindSlashU = findSlashU(str, i - 4)) < 0) {
            return -1;
        }
        try {
            if (predicate.test(Character.valueOf((char) Integer.parseInt(str.substring(i - 3, i + 1), 16)))) {
                return iFindSlashU;
            }
            return -1;
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    private static int findSlashU(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        if (str.charAt(i) != 'u') {
            return -1;
        }
        do {
            i--;
            if (i < 0) {
                break;
            }
        } while (str.charAt(i) == 'u');
        if (i < 0 || str.charAt(i) != '\\') {
            return -1;
        }
        return i;
    }

    public static TextRange mapBackStringRange(String str, int i, int i2) {
        if (str == null) {
            $$$reportNull$$$0(29);
        }
        if (i <= i2 && i2 >= 0 && str.length() >= 2 && str.startsWith("\"") && str.endsWith("\"")) {
            if (str.indexOf(92) == -1) {
                return new TextRange(i + 1, i2 + 1);
            }
            String strSubstring = str.substring(1, str.length() - 1);
            int i3 = 0;
            int i4 = -1;
            for (int charEndIndex = 0; charEndIndex != -1; charEndIndex = getCharEndIndex(strSubstring, charEndIndex)) {
                if (i3 == i) {
                    i4 = charEndIndex;
                }
                if (i3 != i2) {
                    i3++;
                } else {
                    return new TextRange(i4 + 1, charEndIndex + 1);
                }
            }
        }
        return null;
    }

    public static TextRange mapBackTextBlockRange(String str, int i, int i2, int i3) {
        TextBlockModel textBlockModelCreate;
        if (str == null) {
            $$$reportNull$$$0(30);
        }
        if (i > i2 || i2 < 0 || (textBlockModelCreate = TextBlockModel.create(str, i3)) == null) {
            return null;
        }
        return textBlockModelCreate.mapTextBlockRangeBack(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getCharEndIndex(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(31);
        }
        if (i >= str.length()) {
            return -1;
        }
        int i2 = i + 1;
        if (str.charAt(i) != '\\') {
            return i2;
        }
        int i3 = i + 2;
        char cCharAt = str.charAt(i2);
        if (cCharAt == 'u') {
            while (i3 < str.length() && str.charAt(i3) == 'u') {
                i3++;
            }
            return i3 + 4;
        }
        if (cCharAt >= '0' && cCharAt <= '7') {
            char cCharAt2 = i3 < str.length() ? str.charAt(i3) : (char) 0;
            if (cCharAt2 >= '0' && cCharAt2 <= '7') {
                i3 = i + 3;
                char cCharAt3 = i3 < str.length() ? str.charAt(i3) : (char) 0;
                if (cCharAt3 >= '0' && cCharAt3 <= '7' && cCharAt <= '3') {
                    return i + 4;
                }
            }
        }
        return i3;
    }

    public static String tryConvertNumericLiteral(PsiLiteralExpression psiLiteralExpression, PsiType psiType) {
        Double d;
        Long l;
        if (psiLiteralExpression == null) {
            $$$reportNull$$$0(32);
        }
        PsiType type = psiLiteralExpression.getType();
        if (PsiType.INT.equals(type)) {
            if (PsiType.LONG.equals(psiType)) {
                return psiLiteralExpression.getText() + "L";
            }
            if (PsiType.FLOAT.equals(psiType)) {
                String text = psiLiteralExpression.getText();
                if (!text.startsWith("0")) {
                    return text + "F";
                }
            }
            if (PsiType.DOUBLE.equals(psiType)) {
                String text2 = psiLiteralExpression.getText();
                if (!text2.startsWith("0")) {
                    return text2 + ".0";
                }
            }
        }
        if (PsiType.LONG.equals(type) && PsiType.INT.equals(psiType) && (l = (Long) ObjectUtils.tryCast(psiLiteralExpression.getValue(), Long.class)) != null && l.longValue() >= -2147483648L && l.longValue() <= 2147483647L) {
            String text3 = psiLiteralExpression.getText();
            if (StringUtil.endsWithIgnoreCase(text3, "L")) {
                return text3.substring(0, text3.length() - 1);
            }
        }
        if (PsiType.DOUBLE.equals(type) && PsiType.FLOAT.equals(psiType) && (d = (Double) ObjectUtils.tryCast(psiLiteralExpression.getValue(), Double.class)) != null && ((float) d.doubleValue()) == d.doubleValue()) {
            String text4 = psiLiteralExpression.getText();
            if (StringUtil.endsWithIgnoreCase(text4, "D")) {
                text4 = text4.substring(0, text4.length() - 1);
            }
            return text4 + "F";
        }
        if (!PsiType.FLOAT.equals(type) || !PsiType.DOUBLE.equals(psiType)) {
            return null;
        }
        String text5 = psiLiteralExpression.getText();
        if (!StringUtil.endsWithIgnoreCase(text5, "F")) {
            return null;
        }
        String strSubstring = text5.substring(0, text5.length() - 1);
        return !StringUtil.containsAnyChar(strSubstring, ".eEpP") ? strSubstring + ".0" : strSubstring;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TextBlockModel {
        private final int indent;
        private final String[] lines;
        private final int startPrefixLength;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 2 || i == 3) {
                objArr[0] = "text";
            } else {
                objArr[0] = Constant.LINE;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiLiteralUtil$TextBlockModel";
            if (i == 1) {
                objArr[2] = "findLineSuffixLength";
            } else if (i == 2) {
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
            } else if (i != 3) {
                objArr[2] = "findLinePrefixLength";
            } else {
                objArr[2] = "findStartPrefixLength";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private TextBlockModel(String[] strArr, int i, int i2) {
            this.lines = strArr;
            this.indent = i;
            this.startPrefixLength = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public TextRange mapTextBlockRangeBack(int i, int i2) {
            int length = this.startPrefixLength;
            int i3 = 0;
            int i4 = 0;
            int i5 = -1;
            while (true) {
                String[] strArr = this.lines;
                if (i3 >= strArr.length) {
                    return null;
                }
                String str = strArr[i3];
                int iFindLinePrefixLength = findLinePrefixLength(str, this.indent);
                String strSubstring = str.substring(iFindLinePrefixLength);
                boolean z = i3 == this.lines.length - 1;
                int iFindLineSuffixLength = findLineSuffixLength(strSubstring, z);
                String strSubstring2 = strSubstring.substring(0, strSubstring.length() - iFindLineSuffixLength);
                if (!z) {
                    strSubstring2 = strSubstring2 + '\n';
                }
                int i6 = length + iFindLinePrefixLength;
                int charEndIndex = 0;
                while (true) {
                    if (i == i4) {
                        i5 = i6 + charEndIndex;
                    }
                    if (i2 != i4) {
                        charEndIndex = PsiLiteralUtil.getCharEndIndex(strSubstring2, charEndIndex);
                        if (charEndIndex == -1) {
                            break;
                        }
                        i4++;
                        if (charEndIndex == strSubstring2.length()) {
                            i6 += iFindLineSuffixLength;
                        }
                    } else {
                        return new TextRange(i5, i6 + charEndIndex);
                    }
                }
                length = i6 + strSubstring2.length();
                i3++;
            }
        }

        private static int findLinePrefixLength(String str, int i) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            return str.chars().allMatch(new IntPredicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil$TextBlockModel$$ExternalSyntheticLambda0
                @Override // java.util.function.IntPredicate
                public final boolean test(int i2) {
                    return Character.isWhitespace(i2);
                }
            }) ? str.length() : i;
        }

        private static int findLineSuffixLength(String str, boolean z) {
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            if (z) {
                return 0;
            }
            int length = str.length() - 1;
            for (int i = length; i >= 0; i--) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return length - i;
                }
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static TextBlockModel create(String str, int i) {
            int iFindStartPrefixLength;
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            if (str.length() < 7 || !str.startsWith("\"\"\"") || !str.endsWith("\"\"\"") || (iFindStartPrefixLength = findStartPrefixLength(str)) == -1) {
                return null;
            }
            return new TextBlockModel(str.substring(iFindStartPrefixLength, str.length() - 3).split("\n", -1), i, iFindStartPrefixLength);
        }

        private static int findStartPrefixLength(String str) {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            int iIndexOf = str.indexOf("\n");
            if (iIndexOf == -1) {
                return -1;
            }
            return iIndexOf + 1;
        }
    }
}
