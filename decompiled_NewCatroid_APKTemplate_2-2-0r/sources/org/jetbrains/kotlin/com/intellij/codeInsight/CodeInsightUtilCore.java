package org.jetbrains.kotlin.com.intellij.codeInsight;

import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.function.IntUnaryOperator;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CodeInsightUtilCore extends FileModificationService {
    private static final Logger LOG = Logger.getInstance((Class<?>) CodeInsightUtilCore.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "klass";
                break;
            case 2:
            case 5:
                objArr[0] = "language";
                break;
            case 3:
            default:
                objArr[0] = "file";
                break;
            case 6:
            case 7:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 8:
            case 10:
                objArr[0] = "chars";
                break;
            case 9:
            case 11:
                objArr[0] = "outChars";
                break;
            case 12:
                objArr[0] = "endChars";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/CodeInsightUtilCore";
        switch (i) {
            case 6:
            case 7:
                objArr[2] = "forcePsiPostprocessAndRestoreElement";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[2] = "parseStringCharacters";
                break;
            default:
                objArr[2] = "findElementInRange";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static boolean parseStringCharacters(String str, StringBuilder sb, int[] iArr) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (sb == null) {
            $$$reportNull$$$0(9);
        }
        return parseStringCharacters(str, sb, iArr, true, true, '\"', '\'');
    }

    public static boolean parseStringCharacters(String str, StringBuilder sb, int[] iArr, boolean z, boolean z2, char... cArr) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (sb == null) {
            $$$reportNull$$$0(11);
        }
        if (cArr == null) {
            $$$reportNull$$$0(12);
        }
        return new StringParser(iArr, z, z2, cArr).parse(str, sb);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class StringParser {
        private final char[] myEndChars;
        private final boolean myExitOnEscapingWrongSymbol;
        private final boolean mySlashMustBeEscaped;
        private final int[] mySourceOffsets;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                case 3:
                    objArr[0] = "chars";
                    break;
                case 2:
                case 4:
                case 6:
                case 7:
                case 8:
                case 10:
                    objArr[0] = "outChars";
                    break;
                case 5:
                case 9:
                    objArr[0] = "s";
                    break;
                default:
                    objArr[0] = "endChars";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/CodeInsightUtilCore$StringParser";
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "parse";
                    break;
                case 3:
                case 4:
                    objArr[2] = "parseEscapedSymbol";
                    break;
                case 5:
                case 6:
                    objArr[2] = "parseUnicodeEscape";
                    break;
                case 7:
                    objArr[2] = "handleUnexpectedChar";
                    break;
                case 8:
                    objArr[2] = "parseEscapedChar";
                    break;
                case 9:
                case 10:
                    objArr[2] = "parseOctalEscape";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        static /* synthetic */ int lambda$parse$0(int i) {
            return i;
        }

        private StringParser(int[] iArr, boolean z, boolean z2, char... cArr) {
            if (cArr == null) {
                $$$reportNull$$$0(0);
            }
            this.mySourceOffsets = iArr;
            this.mySlashMustBeEscaped = z;
            this.myExitOnEscapingWrongSymbol = z2;
            this.myEndChars = cArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean parse(String str, StringBuilder sb) {
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            if (sb == null) {
                $$$reportNull$$$0(2);
            }
            Logger logger = CodeInsightUtilCore.LOG;
            int[] iArr = this.mySourceOffsets;
            logger.assertTrue(iArr == null || iArr.length == str.length() + 1);
            if (str.indexOf(92) < 0) {
                sb.append(str);
                int[] iArr2 = this.mySourceOffsets;
                if (iArr2 != null) {
                    Arrays.setAll(iArr2, new IntUnaryOperator() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.CodeInsightUtilCore$StringParser$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntUnaryOperator
                        public final int applyAsInt(int i) {
                            return CodeInsightUtilCore.StringParser.lambda$parse$0(i);
                        }
                    });
                }
                return true;
            }
            int length = sb.length();
            int escapedSymbol = 0;
            while (escapedSymbol < str.length()) {
                int i = escapedSymbol + 1;
                char cCharAt = str.charAt(escapedSymbol);
                int[] iArr3 = this.mySourceOffsets;
                if (iArr3 != null) {
                    iArr3[sb.length() - length] = escapedSymbol;
                    this.mySourceOffsets[(sb.length() + 1) - length] = i;
                }
                if (cCharAt != '\\') {
                    sb.append(cCharAt);
                    escapedSymbol = i;
                } else {
                    escapedSymbol = parseEscapedSymbol(str, sb, i, length, false);
                    if (escapedSymbol == -1) {
                        return false;
                    }
                    int[] iArr4 = this.mySourceOffsets;
                    if (iArr4 != null) {
                        iArr4[sb.length() - length] = escapedSymbol;
                    }
                }
            }
            return true;
        }

        private int parseEscapedSymbol(String str, StringBuilder sb, int i, int i2, boolean z) {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            if (sb == null) {
                $$$reportNull$$$0(4);
            }
            if (i == str.length()) {
                return -1;
            }
            int i3 = i + 1;
            char cCharAt = str.charAt(i);
            if (parseEscapedChar(cCharAt, sb)) {
                return i3;
            }
            if (cCharAt == '\\') {
                if (z && i3 < str.length() && str.charAt(i3) == 'u') {
                    return parseUnicodeEscape(str, sb, i3, i2, true);
                }
                sb.append('\\');
                return i3;
            }
            if (cCharAt != 'u') {
                switch (cCharAt) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                        return parseOctalEscape(str, sb, cCharAt, i3);
                    default:
                        if (handleUnexpectedChar(sb, i, i2, cCharAt)) {
                            return i3;
                        }
                        return -1;
                }
            }
            if (z) {
                if (handleUnexpectedChar(sb, i, i2, cCharAt)) {
                    return i3;
                }
                return -1;
            }
            return parseUnicodeEscape(str, sb, i, i2, false);
        }

        private int parseUnicodeEscape(String str, StringBuilder sb, int i, int i2, boolean z) {
            int i3;
            int i4;
            if (str == null) {
                $$$reportNull$$$0(5);
            }
            if (sb == null) {
                $$$reportNull$$$0(6);
            }
            int length = str.length();
            int i5 = i - 1;
            int i6 = i;
            while (true) {
                i3 = i6 + 1;
                if (i3 >= length || str.charAt(i3) != 'u') {
                    break;
                }
                i6 = i3;
            }
            int i7 = i6 + 5;
            if (i7 > length) {
                return -1;
            }
            try {
                char cCharAt = str.charAt(i3);
                if (cCharAt != '+' && cCharAt != '-' && (i4 = Integer.parseInt(str.substring(i3, i7), 16)) != 10 && i4 != 13) {
                    char c = (char) i4;
                    if (c == '\\') {
                        if (z) {
                            sb.append('\\');
                            return i7;
                        }
                        return parseEscapedSymbol(str, sb, i7, i2, true);
                    }
                    if (z) {
                        if (parseEscapedChar(c, sb) || handleUnexpectedChar(sb, i5, i2, c)) {
                            return i7;
                        }
                        return -1;
                    }
                    sb.append(c);
                    return i7;
                }
            } catch (NumberFormatException unused) {
            }
            return -1;
        }

        private boolean handleUnexpectedChar(StringBuilder sb, int i, int i2, char c) {
            if (sb == null) {
                $$$reportNull$$$0(7);
            }
            char[] cArr = this.myEndChars;
            if (CharArrayUtil.indexOf(cArr, c, 0, cArr.length) != -1) {
                sb.append(c);
                return true;
            }
            if (this.myExitOnEscapingWrongSymbol) {
                return false;
            }
            if (!this.mySlashMustBeEscaped) {
                sb.append('\\');
                int[] iArr = this.mySourceOffsets;
                if (iArr != null) {
                    iArr[sb.length() - i2] = i;
                }
            }
            sb.append(c);
            return true;
        }

        private static boolean parseEscapedChar(char c, StringBuilder sb) {
            if (sb == null) {
                $$$reportNull$$$0(8);
            }
            if (c != '\n') {
                if (c != 'b') {
                    if (c == 'f') {
                        sb.append('\f');
                        return true;
                    }
                    if (c == 'n') {
                        sb.append('\n');
                        return true;
                    }
                    switch (c) {
                        case 'r':
                            sb.append('\r');
                            return true;
                        case 's':
                            sb.append(StringUtil.SPACE);
                            return true;
                        case 't':
                            sb.append('\t');
                            return true;
                        default:
                            return false;
                    }
                }
                sb.append('\b');
            }
            return true;
        }

        private static int parseOctalEscape(String str, StringBuilder sb, char c, int i) {
            if (str == null) {
                $$$reportNull$$$0(9);
            }
            if (sb == null) {
                $$$reportNull$$$0(10);
            }
            int i2 = c - '0';
            if (i < str.length()) {
                int i3 = i + 1;
                char cCharAt = str.charAt(i);
                if ('0' <= cCharAt && cCharAt <= '7') {
                    i2 = (i2 << 3) + (cCharAt - ExternalAnnotationProvider.NULLABLE);
                    if (c > '3' || i3 >= str.length()) {
                        i = i3;
                    } else {
                        int i4 = i + 2;
                        char cCharAt2 = str.charAt(i3);
                        if ('0' > cCharAt2 || cCharAt2 > '7') {
                            i++;
                        } else {
                            i2 = (i2 << 3) + (cCharAt2 - ExternalAnnotationProvider.NULLABLE);
                            i = i4;
                        }
                    }
                }
            }
            sb.append((char) i2);
            return i;
        }
    }
}
