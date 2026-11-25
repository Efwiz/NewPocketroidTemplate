package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.List;
import net.bytebuddy.asm.Advice;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.FList;
import org.jetbrains.kotlin.com.intellij.util.text.NameUtilCore;

/* JADX INFO: loaded from: classes3.dex */
class TypoTolerantMatcher extends MinusculeMatcher {
    private static final char[][] keyboard = {new char[]{'q', 'w', 'e', Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL, Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL, 'y', 'u', 'i', 'o', Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL}, new char[]{'a', Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL, Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, 'f', 'g', 'h', 'j', 'k', 'l'}, new char[]{'z', 'x', 'c', 'v', 'b', 'n', Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL}};
    private final boolean[] isLowerCase;
    private final boolean[] isUpperCase;
    private final boolean[] isWordSeparator;
    private final String myHardSeparators;
    private final boolean myHasDots;
    private final boolean myHasHumps;
    private final boolean myHasSeparators;
    private final char[] myMeaningfulCharacters;
    private final int myMinNameLength;
    private final NameUtil.MatchingCaseSensitivity myOptions;
    private final char[] myPattern;
    private final char[] toLowerCase;
    private final char[] toUpperCase;

    private interface Error {
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "options";
                break;
            case 2:
                objArr[0] = "hardSeparators";
                break;
            case 3:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                objArr[0] = "name";
                break;
            case 4:
                objArr[0] = "ranges";
                break;
            case 5:
                objArr[0] = "range";
                break;
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/TypoTolerantMatcher";
                break;
            case 12:
                objArr[0] = "fragments";
                break;
            default:
                objArr[0] = "pattern";
                break;
        }
        if (i == 6 || i == 7) {
            objArr[1] = "prependRange";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/TypoTolerantMatcher";
        }
        switch (i) {
            case 3:
                objArr[2] = "nextWord";
                break;
            case 4:
            case 5:
                objArr[2] = "prependRange";
                break;
            case 6:
            case 7:
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "matchingDegree";
                break;
            case 11:
            case 12:
                objArr[2] = "isStartMatch";
                break;
            case 13:
                objArr[2] = "matches";
                break;
            case 14:
                objArr[2] = "matchingFragments";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isLowerAscii(char c) {
        return 'a' <= c && c <= 'z';
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isUpperAscii(char c) {
        return 'A' <= c && c <= 'Z';
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static char toLowerAscii(char c) {
        return (c < 'A' || c > 'Z') ? c : (char) (c + StringUtil.SPACE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static char toUpperAscii(char c) {
        return (c < 'a' || c > 'z') ? c : (char) (c - ' ');
    }

    TypoTolerantMatcher(String str, NameUtil.MatchingCaseSensitivity matchingCaseSensitivity, String str2) {
        boolean z = false;
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (matchingCaseSensitivity == null) {
            $$$reportNull$$$0(1);
        }
        if (str2 == null) {
            $$$reportNull$$$0(2);
        }
        this.myOptions = matchingCaseSensitivity;
        char[] charArray = Strings.trimEnd(str, "* ").toCharArray();
        this.myPattern = charArray;
        this.myHardSeparators = str2;
        this.isLowerCase = new boolean[charArray.length];
        this.isUpperCase = new boolean[charArray.length];
        this.isWordSeparator = new boolean[charArray.length];
        this.toUpperCase = new char[charArray.length];
        this.toLowerCase = new char[charArray.length];
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            char[] cArr = this.myPattern;
            if (i >= cArr.length) {
                break;
            }
            char c = cArr[i];
            this.isLowerCase[i] = Character.isLowerCase(c);
            this.isUpperCase[i] = Character.isUpperCase(c);
            this.isWordSeparator[i] = isWordSeparator(c);
            this.toUpperCase[i] = Strings.toUpperCase(c);
            this.toLowerCase[i] = Strings.toLowerCase(c);
            if (!isWildcard(i)) {
                sb.append(this.toLowerCase[i]);
                sb.append(this.toUpperCase[i]);
            }
            i++;
        }
        int i2 = 0;
        while (isWildcard(i2)) {
            i2++;
        }
        if (hasFlag(i2 + 1, this.isUpperCase) && hasFlag(i2, this.isLowerCase)) {
            z = true;
        }
        this.myHasHumps = z;
        this.myHasSeparators = hasFlag(i2, this.isWordSeparator);
        this.myHasDots = hasDots(i2);
        char[] charArray2 = sb.toString().toCharArray();
        this.myMeaningfulCharacters = charArray2;
        this.myMinNameLength = charArray2.length / 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isWordSeparator(char c) {
        return Character.isWhitespace(c) || c == '_' || c == '-' || c == ':' || c == '+' || c == '.';
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int nextWord(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return (i >= str.length() || !Character.isDigit(str.charAt(i))) ? NameUtilCore.nextWord(str, i) : i + 1;
    }

    private boolean hasFlag(int i, boolean[] zArr) {
        while (i < this.myPattern.length) {
            if (zArr[i]) {
                return true;
            }
            i++;
        }
        return false;
    }

    private boolean hasDots(int i) {
        while (true) {
            char[] cArr = this.myPattern;
            if (i >= cArr.length) {
                return false;
            }
            if (cArr[i] == '.') {
                return true;
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FList<TextRange> prependRange(FList<TextRange> fList, Range range) {
        if (fList == null) {
            $$$reportNull$$$0(4);
        }
        if (range == null) {
            $$$reportNull$$$0(5);
        }
        Range range2 = (Range) fList.getHead();
        if (range2 != null && range2.getStartOffset() == range.getEndOffset()) {
            FList<TextRange> fListPrepend = fList.getTail().prepend(new Range(range.getStartOffset(), range2.getEndOffset(), range.getErrorCount() + range2.getErrorCount()));
            if (fListPrepend == null) {
                $$$reportNull$$$0(6);
            }
            return fListPrepend;
        }
        FList<TextRange> fListPrepend2 = fList.prepend(range);
        if (fListPrepend2 == null) {
            $$$reportNull$$$0(7);
        }
        return fListPrepend2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public int matchingDegree(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return matchingDegree(str, false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public int matchingDegree(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        return matchingDegree(str, z, matchingFragments(str));
    }

    /* JADX WARN: Code duplicated, block: B:69:0x013f  */
    /* JADX WARN: Code duplicated, block: B:71:0x0142  */
    /* JADX WARN: Code duplicated, block: B:72:0x0145  */
    /* JADX WARN: Code duplicated, block: B:75:0x0154  */
    /* JADX WARN: Code duplicated, block: B:76:0x0156  */
    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public int matchingDegree(String str, boolean z, FList<? extends TextRange> fList) {
        int i;
        int i2;
        int i3;
        int i4;
        boolean z2;
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (fList == null) {
            return Integer.MIN_VALUE;
        }
        boolean z3 = false;
        if (fList.isEmpty()) {
            return 0;
        }
        TextRange head = fList.getHead();
        int i5 = head.getStartOffset() == 0 ? 1 : 0;
        boolean z4 = i5 != 0 && z;
        Iterator<? extends TextRange> it = fList.iterator();
        int i6 = -1;
        int i7 = 0;
        int i8 = 0;
        int iNextWord = 0;
        boolean z5 = false;
        int iPow = 0;
        while (it.hasNext()) {
            TextRange next = it.next();
            int startOffset = next.getStartOffset();
            int iEvaluateCaseMatching = i7;
            while (startOffset < next.getEndOffset()) {
                boolean z6 = (startOffset != next.getStartOffset() || head == next) ? z3 : true;
                int i9 = i8;
                boolean z7 = z3;
                while (iNextWord <= startOffset) {
                    if (iNextWord == startOffset) {
                        z7 = true;
                    } else if (z6) {
                        i9++;
                    }
                    iNextWord = nextWord(str, iNextWord);
                }
                char cCharAt = str.charAt(startOffset);
                char[] cArr = this.myPattern;
                int iIndexOf = Strings.indexOf(cArr, cCharAt, i6 + 1, cArr.length, z3);
                if (iIndexOf < 0) {
                    i6 = iIndexOf;
                    i8 = i9;
                    break;
                }
                if (z7) {
                    z2 = (cCharAt == this.myPattern[iIndexOf] && this.isUpperCase[iIndexOf]) ? true : z3;
                } else {
                    z2 = z5;
                }
                iEvaluateCaseMatching += evaluateCaseMatching(z4, iIndexOf, z2, startOffset, z6, z7, cCharAt);
                startOffset++;
                i6 = iIndexOf;
                iPow = iPow;
                next = next;
                i8 = i9;
                z5 = z2;
                iNextWord = iNextWord;
            }
            iPow = (int) (((double) iPow) + (Math.pow((((double) ((Range) next).getErrorCount()) * 1.0d) / ((double) next.getLength()), 2.0d) * 2000.0d));
            head = head;
            i7 = iEvaluateCaseMatching;
            z3 = false;
        }
        int i10 = iPow;
        int startOffset2 = head.getStartOffset();
        boolean z8 = Strings.indexOfAny(str, this.myHardSeparators, 0, startOffset2) >= 0;
        if (startOffset2 != 0) {
            if (NameUtilCore.isWordStart(str, startOffset2)) {
                i = 1;
                if (!NameUtilCore.isWordStart(str, startOffset2 - 1)) {
                }
                if (fList.get(fList.size() - i).getEndOffset() != str.length()) {
                    i = 0;
                }
                if (i2 != 0) {
                    i3 = 1000;
                } else {
                    i3 = 0;
                }
                int size = (((i3 + i7) - fList.size()) + ((-i8) * 10)) - i10;
                if (z8) {
                    i4 = 0;
                } else {
                    i4 = 2;
                }
                return size + i4 + i5 + i;
            }
            i = 1;
            i2 = 0;
            if (fList.get(fList.size() - i).getEndOffset() != str.length()) {
                i = 0;
            }
            if (i2 != 0) {
                i3 = 1000;
            } else {
                i3 = 0;
            }
            int size2 = (((i3 + i7) - fList.size()) + ((-i8) * 10)) - i10;
            if (z8) {
                i4 = 0;
            } else {
                i4 = 2;
            }
            return size2 + i4 + i5 + i;
        }
        i = 1;
        i2 = i;
        if (fList.get(fList.size() - i).getEndOffset() != str.length()) {
            i = 0;
        }
        if (i2 != 0) {
            i3 = 1000;
        } else {
            i3 = 0;
        }
        int size3 = (((i3 + i7) - fList.size()) + ((-i8) * 10)) - i10;
        if (z8) {
            i4 = 0;
        } else {
            i4 = 2;
        }
        return size3 + i4 + i5 + i;
    }

    private int evaluateCaseMatching(boolean z, int i, boolean z2, int i2, boolean z3, boolean z4, char c) {
        if (z3 && z4 && this.isLowerCase[i]) {
            return -10;
        }
        if (c != this.myPattern[i]) {
            if (z4) {
                return -1;
            }
            return (this.isLowerCase[i] && z2) ? -1 : 0;
        }
        if (this.isUpperCase[i]) {
            return 50;
        }
        if (i2 == 0 && z) {
            return 150;
        }
        return z4 ? 1 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public boolean isStartMatch(String str) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        FList<TextRange> fListMatchingFragments = matchingFragments(str);
        return fListMatchingFragments != null && isStartMatch(fListMatchingFragments);
    }

    public static boolean isStartMatch(Iterable<? extends TextRange> iterable) {
        if (iterable == null) {
            $$$reportNull$$$0(12);
        }
        Iterator<? extends TextRange> it = iterable.iterator();
        return !it.hasNext() || it.next().getStartOffset() == 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher, org.jetbrains.kotlin.com.intellij.psi.codeStyle.NameUtil.Matcher
    public boolean matches(String str) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        return matchingFragments(str) != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public String getPattern() {
        return new String(this.myPattern);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public FList<TextRange> matchingFragments(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        FList<TextRange> fListMatchingFragments = new Session(this, str, false).matchingFragments();
        return fListMatchingFragments != null ? fListMatchingFragments : new Session(this, str, true).matchingFragments();
    }

    private class Session {
        private final boolean isAsciiName;
        private final boolean myAllowTypos;
        private final String myName;
        private final boolean myTypoAware;
        final /* synthetic */ TypoTolerantMatcher this$0;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                    objArr[0] = "errorState";
                    break;
                case 15:
                    objArr[0] = "baseErrorState";
                    break;
                case 16:
                    objArr[0] = "fragment";
                    break;
                default:
                    objArr[0] = "name";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/TypoTolerantMatcher$Session";
            switch (i) {
                case 1:
                    objArr[2] = "charAt";
                    break;
                case 2:
                    objArr[2] = "toLowerCase";
                    break;
                case 3:
                    objArr[2] = "toUpperCase";
                    break;
                case 4:
                    objArr[2] = "isLowerCase";
                    break;
                case 5:
                    objArr[2] = "isUpperCase";
                    break;
                case 6:
                    objArr[2] = "isWordSeparator";
                    break;
                case 7:
                    objArr[2] = "patternLength";
                    break;
                case 8:
                    objArr[2] = "matchWildcards";
                    break;
                case 9:
                    objArr[2] = "isTrailingSpacePattern";
                    break;
                case 10:
                    objArr[2] = "matchSkippingWords";
                    break;
                case 11:
                    objArr[2] = "findNextPatternCharOccurrence";
                    break;
                case 12:
                    objArr[2] = "seemsLikeFragmentStart";
                    break;
                case 13:
                    objArr[2] = "charEquals";
                    break;
                case 14:
                    objArr[2] = "matchFragment";
                    break;
                case 15:
                    objArr[2] = "maxMatchingFragment";
                    break;
                case 16:
                    objArr[2] = "matchInsideFragment";
                    break;
                case 17:
                    objArr[2] = "isMiddleMatch";
                    break;
                case 18:
                    objArr[2] = "findLongestMatchingPrefix";
                    break;
                case 19:
                    objArr[2] = "improveCamelHumps";
                    break;
                case 20:
                    objArr[2] = "isUppercasePatternVsLowercaseNameChar";
                    break;
                case 21:
                    objArr[2] = "findUppercaseMatchFurther";
                    break;
                case 22:
                    objArr[2] = "isFirstCharMatching";
                    break;
                case 23:
                    objArr[2] = "isPatternChar";
                    break;
                case 24:
                    objArr[2] = "indexOfWordStart";
                    break;
                case 25:
                    objArr[2] = "indexOfIgnoreCase";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        Session(TypoTolerantMatcher typoTolerantMatcher, String str, boolean z) {
            boolean z2;
            boolean z3 = false;
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            this.this$0 = typoTolerantMatcher;
            this.myName = str;
            int length = str.length();
            int i = 0;
            while (true) {
                if (i >= length) {
                    z2 = true;
                    break;
                } else {
                    if (str.charAt(i) >= 128) {
                        z2 = false;
                        break;
                    }
                    i++;
                }
            }
            this.isAsciiName = z2;
            this.myTypoAware = z;
            if (z && z2) {
                z3 = true;
            }
            this.myAllowTypos = z3;
        }

        private char charAt(int i, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(1);
            }
            return errorState.affects(i) ? errorState.getChar(this.this$0.myPattern, i) : this.this$0.myPattern[i];
        }

        private char toLowerCase(int i, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(2);
            }
            return errorState.affects(i) ? TypoTolerantMatcher.toLowerAscii(errorState.getChar(this.this$0.myPattern, i)) : this.this$0.toLowerCase[i];
        }

        private char toUpperCase(int i, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(3);
            }
            return errorState.affects(i) ? TypoTolerantMatcher.toUpperAscii(errorState.getChar(this.this$0.myPattern, i)) : this.this$0.toUpperCase[i];
        }

        private boolean isLowerCase(int i, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(4);
            }
            return errorState.affects(i) ? TypoTolerantMatcher.isLowerAscii(errorState.getChar(this.this$0.myPattern, i)) : this.this$0.isLowerCase[i];
        }

        private boolean isUpperCase(int i, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(5);
            }
            return errorState.affects(i) ? TypoTolerantMatcher.isUpperAscii(errorState.getChar(this.this$0.myPattern, i)) : this.this$0.isUpperCase[i];
        }

        private boolean isWordSeparator(int i, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(6);
            }
            return errorState.affects(i) ? TypoTolerantMatcher.isWordSeparator(errorState.getChar(this.this$0.myPattern, i)) : this.this$0.isWordSeparator[i];
        }

        private int patternLength(ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(7);
            }
            return errorState.length(this.this$0.myPattern);
        }

        public FList<TextRange> matchingFragments() {
            if (this.myName.length() < this.this$0.myMinNameLength) {
                return null;
            }
            boolean z = this.myTypoAware;
            if (z && !this.isAsciiName) {
                return null;
            }
            if (!z) {
                int length = this.myName.length();
                int i = 0;
                for (int i2 = 0; i2 < length; i2++) {
                    char cCharAt = this.myName.charAt(i2);
                    if (i < this.this$0.myMeaningfulCharacters.length && (cCharAt == this.this$0.myMeaningfulCharacters[i] || cCharAt == this.this$0.myMeaningfulCharacters[i + 1])) {
                        i += 2;
                    }
                }
                if (i < this.this$0.myMinNameLength * 2) {
                    return null;
                }
            }
            return matchWildcards(0, 0, new ErrorState());
        }

        private FList<TextRange> matchWildcards(int i, int i2, ErrorState errorState) {
            int i3;
            if (errorState == null) {
                $$$reportNull$$$0(8);
            }
            if (i2 < 0) {
                return null;
            }
            if (!this.this$0.isWildcard(i)) {
                if (i == patternLength(errorState)) {
                    return FList.emptyList();
                }
                return matchFragment(i, i2, errorState);
            }
            while (true) {
                i3 = i + 1;
                if (!this.this$0.isWildcard(i3)) {
                    break;
                }
                i = i3;
            }
            if (i3 == patternLength(errorState)) {
                if (isTrailingSpacePattern(errorState) && i2 != this.myName.length() && (i3 < 2 || !isUpperCaseOrDigit(charAt(i - 1, errorState)))) {
                    int iIndexOf = this.myName.indexOf(32, i2);
                    if (iIndexOf >= 0) {
                        return FList.emptyList().prepend(new Range(iIndexOf, iIndexOf + 1, 0));
                    }
                    return null;
                }
                return FList.emptyList();
            }
            FList<TextRange> fListMatchFragment = matchFragment(i3, i2, errorState);
            return fListMatchFragment != null ? fListMatchFragment : matchSkippingWords(i3, i2, true, errorState);
        }

        private boolean isTrailingSpacePattern(ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(9);
            }
            return isPatternChar(patternLength(errorState) - 1, StringUtil.SPACE, errorState);
        }

        private boolean isUpperCaseOrDigit(char c) {
            return Character.isUpperCase(c) || Character.isDigit(c);
        }

        private FList<TextRange> matchSkippingWords(int i, int i2, boolean z, ErrorState errorState) {
            int length;
            if (errorState == null) {
                $$$reportNull$$$0(10);
            }
            int i3 = 0;
            boolean z2 = (isPatternChar(i + (-1), '*', errorState) || isWordSeparator(i, errorState)) ? false : true;
            int iFindNextPatternCharOccurrence = i2;
            while (true) {
                iFindNextPatternCharOccurrence = findNextPatternCharOccurrence(iFindNextPatternCharOccurrence, i, z, z2, errorState);
                if (iFindNextPatternCharOccurrence < 0) {
                    return null;
                }
                Fragment fragmentMaxMatchingFragment = seemsLikeFragmentStart(i, iFindNextPatternCharOccurrence, errorState) ? maxMatchingFragment(i, iFindNextPatternCharOccurrence, errorState) : null;
                if (fragmentMaxMatchingFragment != null && ((length = fragmentMaxMatchingFragment.getLength()) > i3 || (iFindNextPatternCharOccurrence + length == this.myName.length() && isTrailingSpacePattern(errorState)))) {
                    if (!isMiddleMatch(i, iFindNextPatternCharOccurrence, errorState)) {
                        i3 = length;
                    }
                    FList<TextRange> fListMatchInsideFragment = matchInsideFragment(i, iFindNextPatternCharOccurrence, fragmentMaxMatchingFragment);
                    if (fListMatchInsideFragment != null) {
                        return fListMatchInsideFragment;
                    }
                }
            }
        }

        private int findNextPatternCharOccurrence(int i, int i2, boolean z, boolean z2, ErrorState errorState) {
            int iIndexOfIgnoreCase;
            if (errorState == null) {
                $$$reportNull$$$0(11);
            }
            if (z2) {
                iIndexOfIgnoreCase = indexOfWordStart(i2, i, errorState);
            } else {
                iIndexOfIgnoreCase = indexOfIgnoreCase(i + 1, i2, errorState);
            }
            if (!z && !this.this$0.myHasSeparators && !this.this$0.myHasHumps && Strings.containsAnyChar(this.myName, this.this$0.myHardSeparators, i, iIndexOfIgnoreCase)) {
                return -1;
            }
            if (z || !this.this$0.myHasDots || isPatternChar(i2 - 1, '.', errorState) || !Strings.contains(this.myName, i, iIndexOfIgnoreCase, '.')) {
                return iIndexOfIgnoreCase;
            }
            return -1;
        }

        private boolean seemsLikeFragmentStart(int i, int i2, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(12);
            }
            return !isUpperCase(i, errorState) || Character.isUpperCase(this.myName.charAt(i2)) || NameUtilCore.isWordStart(this.myName, i2) || !(this.this$0.myHasHumps || this.this$0.myOptions == NameUtil.MatchingCaseSensitivity.ALL);
        }

        private boolean charEquals(int i, int i2, boolean z, boolean z2, ErrorState errorState) {
            char cCharAt;
            int i3;
            if (errorState == null) {
                $$$reportNull$$$0(13);
            }
            char cCharAt2 = charAt(i, errorState);
            char cCharAt3 = this.myName.charAt(i2);
            if (cCharAt2 == cCharAt3 || (z && (toLowerCase(i, errorState) == cCharAt3 || toUpperCase(i, errorState) == cCharAt3))) {
                return true;
            }
            if (!this.myAllowTypos || !z2 || errorState.countErrors(0, i) > 0 || errorState.getError(i - 1) == SwapError.instance) {
                return false;
            }
            char cLeftMiss = TypoTolerantMatcher.leftMiss(cCharAt2);
            if (cLeftMiss == 0 || (cLeftMiss != cCharAt3 && (!z || (TypoTolerantMatcher.toLowerAscii(cLeftMiss) != cCharAt3 && TypoTolerantMatcher.toUpperAscii(cLeftMiss) != cCharAt3)))) {
                char cRightMiss = TypoTolerantMatcher.rightMiss(cCharAt2);
                if (cRightMiss != 0 && (cRightMiss == cCharAt3 || (z && (TypoTolerantMatcher.toLowerAscii(cRightMiss) == cCharAt3 || TypoTolerantMatcher.toUpperAscii(cRightMiss) == cCharAt3)))) {
                    errorState.addError(i, new TypoError(cRightMiss));
                    return true;
                }
                int i4 = i + 1;
                if (patternLength(errorState) > i4 && this.myName.length() > (i3 = i2 + 1)) {
                    char cCharAt4 = this.myName.charAt(i3);
                    char cCharAt5 = charAt(i4, errorState);
                    if ((cCharAt2 == cCharAt4 || (z && (toLowerCase(i, errorState) == cCharAt4 || toUpperCase(i, errorState) == cCharAt4))) && (cCharAt5 == cCharAt3 || (z && (toLowerCase(i4, errorState) == cCharAt3 || toUpperCase(i4, errorState) == cCharAt3)))) {
                        errorState.addError(i, SwapError.instance);
                        return true;
                    }
                }
                int i5 = i2 + 1;
                if (this.myName.length() > i5 && (cCharAt2 == (cCharAt = this.myName.charAt(i5)) || (z && (toLowerCase(i, errorState) == cCharAt || toUpperCase(i, errorState) == cCharAt)))) {
                    errorState.addError(i, new MissError(cCharAt3));
                    return true;
                }
                return false;
            }
            errorState.addError(i, new TypoError(cLeftMiss));
            return true;
        }

        private FList<TextRange> matchFragment(int i, int i2, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(14);
            }
            Fragment fragmentMaxMatchingFragment = maxMatchingFragment(i, i2, errorState);
            if (fragmentMaxMatchingFragment == null) {
                return null;
            }
            return matchInsideFragment(i, i2, fragmentMaxMatchingFragment);
        }

        private Fragment maxMatchingFragment(int i, int i2, ErrorState errorState) {
            int i3;
            if (errorState == null) {
                $$$reportNull$$$0(15);
            }
            ErrorState errorStateDeriveFrom = errorState.deriveFrom(i);
            if (!isFirstCharMatching(i2, i, errorStateDeriveFrom)) {
                return null;
            }
            boolean z = this.this$0.myOptions != NameUtil.MatchingCaseSensitivity.ALL;
            int i4 = 1;
            while (true) {
                int i5 = i2 + i4;
                if (i5 >= this.myName.length() || (i3 = i + i4) >= patternLength(errorStateDeriveFrom)) {
                    break;
                }
                if (!charEquals(i3, i5, z, true, errorStateDeriveFrom)) {
                    if (!Character.isDigit(charAt(i3, errorStateDeriveFrom)) || !Character.isDigit(charAt(i3 - 1, errorStateDeriveFrom))) {
                        break;
                        break;
                    }
                    return null;
                }
                i4++;
            }
            return new Fragment(i4, errorStateDeriveFrom);
        }

        private FList<TextRange> matchInsideFragment(int i, int i2, Fragment fragment) {
            if (fragment == null) {
                $$$reportNull$$$0(16);
            }
            int i3 = isMiddleMatch(i, i2, fragment.getErrorState()) ? 3 : 1;
            FList<TextRange> fListImproveCamelHumps = improveCamelHumps(i, i2, fragment.getLength(), i3, fragment.getErrorState());
            if (fListImproveCamelHumps != null) {
                return fListImproveCamelHumps;
            }
            return findLongestMatchingPrefix(i, i2, fragment.getLength(), i3, fragment.getErrorState());
        }

        private boolean isMiddleMatch(int i, int i2, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(17);
            }
            return isPatternChar(i + (-1), '*', errorState) && !this.this$0.isWildcard(i + 1) && Character.isLetterOrDigit(this.myName.charAt(i2)) && !NameUtilCore.isWordStart(this.myName, i2);
        }

        private FList<TextRange> findLongestMatchingPrefix(int i, int i2, int i3, int i4, ErrorState errorState) {
            FList<TextRange> fListMatchSkippingWords;
            if (errorState == null) {
                $$$reportNull$$$0(18);
            }
            int i5 = i + i3;
            if (i5 >= patternLength(errorState)) {
                int iCountErrors = errorState.countErrors(i, i5);
                if (iCountErrors == i3) {
                    return null;
                }
                return FList.emptyList().prepend(new Range(i2, i3 + i2, iCountErrors));
            }
            while (true) {
                if (i3 < i4 && !this.this$0.isWildcard(i + i3)) {
                    return null;
                }
                int i6 = i + i3;
                ErrorState errorStateDeriveFrom = errorState.deriveFrom(i6);
                if (this.this$0.isWildcard(i6)) {
                    fListMatchSkippingWords = matchWildcards(i6, i2 + i3, errorStateDeriveFrom);
                } else {
                    fListMatchSkippingWords = matchSkippingWords(i6, i2 + i3, false, errorStateDeriveFrom);
                }
                if (fListMatchSkippingWords != null) {
                    int iCountErrors2 = errorState.countErrors(i, i6);
                    if (iCountErrors2 == i3) {
                        return null;
                    }
                    return TypoTolerantMatcher.prependRange(fListMatchSkippingWords, new Range(i2, i3 + i2, iCountErrors2));
                }
                i3--;
            }
        }

        private FList<TextRange> improveCamelHumps(int i, int i2, int i3, int i4, ErrorState errorState) {
            FList<TextRange> fListFindUppercaseMatchFurther;
            if (errorState == null) {
                $$$reportNull$$$0(19);
            }
            while (i4 < i3) {
                int i5 = i + i4;
                int i6 = i2 + i4;
                if (isUppercasePatternVsLowercaseNameChar(i5, i6, errorState) && (fListFindUppercaseMatchFurther = findUppercaseMatchFurther(i5, i6, errorState.deriveFrom(i5))) != null) {
                    int iCountErrors = errorState.countErrors(i, i5);
                    if (iCountErrors == i4) {
                        return null;
                    }
                    return TypoTolerantMatcher.prependRange(fListFindUppercaseMatchFurther, new Range(i2, i6, iCountErrors));
                }
                i4++;
            }
            return null;
        }

        private boolean isUppercasePatternVsLowercaseNameChar(int i, int i2, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(20);
            }
            return isUpperCase(i, errorState) && !charEquals(i, i2, false, false, errorState);
        }

        private FList<TextRange> findUppercaseMatchFurther(int i, int i2, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(21);
            }
            return matchWildcards(i, indexOfWordStart(i, i2, errorState), errorState.deriveFrom(i));
        }

        private boolean isFirstCharMatching(int i, int i2, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(22);
            }
            if (i >= this.myName.length()) {
                return false;
            }
            if (!charEquals(i2, i, this.this$0.myOptions != NameUtil.MatchingCaseSensitivity.ALL, true, errorState)) {
                return false;
            }
            char cCharAt = charAt(i2, errorState);
            return (this.this$0.myOptions == NameUtil.MatchingCaseSensitivity.FIRST_LETTER && (i2 == 0 || (i2 == 1 && this.this$0.isWildcard(0))) && hasCase(cCharAt) && Character.isUpperCase(cCharAt) != Character.isUpperCase(this.myName.charAt(0))) ? false : true;
        }

        private boolean hasCase(char c) {
            return Character.isUpperCase(c) || Character.isLowerCase(c);
        }

        private boolean isPatternChar(int i, char c, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(23);
            }
            return i >= 0 && i < patternLength(errorState) && charAt(i, errorState) == c;
        }

        private int indexOfWordStart(int i, int i2, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(24);
            }
            if (i2 >= this.myName.length() || (this.this$0.myHasHumps && isLowerCase(i, errorState) && (i <= 0 || !isWordSeparator(i - 1, errorState)))) {
                return -1;
            }
            do {
                i2 = TypoTolerantMatcher.nextWord(this.myName, i2);
                if (i2 >= this.myName.length()) {
                    return -1;
                }
            } while (!charEquals(i, i2, true, true, errorState));
            return i2;
        }

        private int indexOfIgnoreCase(int i, int i2, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(25);
            }
            char cCharAt = charAt(i2, errorState);
            if (this.isAsciiName && Strings.isAscii(cCharAt)) {
                int iIndexIgnoringCaseAscii = indexIgnoringCaseAscii(i, i2, cCharAt);
                if (iIndexIgnoringCaseAscii != -1) {
                    return iIndexIgnoringCaseAscii;
                }
                if (this.myAllowTypos) {
                    int iIndexIgnoringCaseAscii2 = indexIgnoringCaseAscii(i, i2, TypoTolerantMatcher.leftMiss(cCharAt));
                    if (iIndexIgnoringCaseAscii2 != -1) {
                        return iIndexIgnoringCaseAscii2;
                    }
                    int iIndexIgnoringCaseAscii3 = indexIgnoringCaseAscii(i, i2, TypoTolerantMatcher.rightMiss(cCharAt));
                    if (iIndexIgnoringCaseAscii3 != -1) {
                        return iIndexIgnoringCaseAscii3;
                    }
                }
                return -1;
            }
            return Strings.indexOfIgnoreCase(this.myName, cCharAt, i);
        }

        private int indexIgnoringCaseAscii(int i, int i2, char c) {
            char upperAscii = TypoTolerantMatcher.toUpperAscii(c);
            char lowerAscii = TypoTolerantMatcher.toLowerAscii(c);
            while (i < this.myName.length()) {
                char cCharAt = this.myName.charAt(i);
                if (cCharAt == c || TypoTolerantMatcher.toUpperAscii(cCharAt) == upperAscii || TypoTolerantMatcher.toLowerAscii(cCharAt) == lowerAscii) {
                    return i;
                }
                i++;
            }
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isWildcard(int i) {
        if (i < 0) {
            return false;
        }
        char[] cArr = this.myPattern;
        if (i >= cArr.length) {
            return false;
        }
        char c = cArr[i];
        return c == ' ' || c == '*';
    }

    public String toString() {
        return "TypoTolerantMatcher{myPattern=" + new String(this.myPattern) + ", myOptions=" + this.myOptions + '}';
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ErrorState {
        private final ErrorState myBase;
        private final int myDeriveIndex;
        private List<Pair<Integer, Error>> myErrors;
        private char[] myPattern;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "error", "org/jetbrains/kotlin/com/intellij/psi/codeStyle/TypoTolerantMatcher$ErrorState", "addError"));
        }

        ErrorState(ErrorState errorState, int i) {
            this.myBase = errorState;
            this.myDeriveIndex = i;
        }

        ErrorState() {
            this(null, 0);
        }

        ErrorState deriveFrom(int i) {
            return new ErrorState(this, i);
        }

        void addError(int i, Error error) {
            if (error == null) {
                $$$reportNull$$$0(0);
            }
            if (this.myErrors == null) {
                this.myErrors = new SmartList();
            }
            Pair<Integer, Error> pairCreate = Pair.create(Integer.valueOf(i), error);
            this.myErrors.add(pairCreate);
            char[] cArr = this.myPattern;
            if (cArr != null) {
                this.myPattern = applyError(cArr, pairCreate);
            }
        }

        int countErrors(int i, int i2) {
            int i3;
            ErrorState errorState = this.myBase;
            int iCountErrors = (errorState == null || i >= (i3 = this.myDeriveIndex)) ? 0 : errorState.countErrors(i, i3);
            List<Pair<Integer, Error>> list = this.myErrors;
            if (list != null) {
                for (Pair<Integer, Error> pair : list) {
                    if (i <= pair.first.intValue() && pair.first.intValue() < i2) {
                        iCountErrors++;
                    }
                }
            }
            return iCountErrors;
        }

        private boolean processErrors(int i, int i2, Processor<? super Pair<Integer, Error>> processor) {
            int i3;
            ErrorState errorState = this.myBase;
            if (errorState != null && i < (i3 = this.myDeriveIndex) && !errorState.processErrors(i, i3, processor)) {
                return false;
            }
            List<Pair<Integer, Error>> list = this.myErrors;
            if (list == null) {
                return true;
            }
            for (Pair<Integer, Error> pair : list) {
                if (i <= pair.first.intValue() && pair.first.intValue() < i2 && !processor.process(pair)) {
                    return false;
                }
            }
            return true;
        }

        public char getChar(char[] cArr, int i) {
            if (this.myPattern == null) {
                this.myPattern = applyErrors((char[]) cArr.clone(), Integer.MAX_VALUE);
            }
            return this.myPattern[i];
        }

        private char[] applyErrors(char[] cArr, int i) {
            ErrorState errorState = this.myBase;
            if (errorState != null) {
                cArr = errorState.applyErrors(cArr, Math.min(this.myDeriveIndex, i));
            }
            List<Pair<Integer, Error>> list = this.myErrors;
            if (list != null) {
                for (Pair<Integer, Error> pair : list) {
                    if (pair.first.intValue() < i) {
                        cArr = applyError(cArr, pair);
                    }
                }
            }
            return cArr;
        }

        private static char[] applyError(char[] cArr, Pair<Integer, Error> pair) {
            if (!(pair.second instanceof TypoError)) {
                if (!(pair.second instanceof SwapError)) {
                    return pair.second instanceof MissError ? ArrayUtil.insert(cArr, pair.first.intValue(), ((MissError) pair.second).myMissedChar) : cArr;
                }
                char c = cArr[pair.first.intValue()];
                cArr[pair.first.intValue()] = cArr[pair.first.intValue() + 1];
                cArr[pair.first.intValue() + 1] = c;
                return cArr;
            }
            cArr[pair.first.intValue()] = ((TypoError) pair.second).myCorrectChar;
            return cArr;
        }

        public boolean affects(final int i) {
            return !processErrors(0, i + 1, new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.codeStyle.TypoTolerantMatcher$ErrorState$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Processor
                public final boolean process(Object obj) {
                    return TypoTolerantMatcher.ErrorState.lambda$affects$0(i, (Pair) obj);
                }
            });
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ boolean lambda$affects$0(int i, Pair pair) {
            if (((Integer) pair.first).intValue() == i) {
                return false;
            }
            if (((Integer) pair.first).intValue() == i - 1 && pair.second == SwapError.instance) {
                return false;
            }
            return ((Integer) pair.first).intValue() >= i || !(pair.second instanceof MissError);
        }

        public Error getError(int i) {
            List<Pair<Integer, Error>> list = this.myErrors;
            if (list != null) {
                for (Pair<Integer, Error> pair : list) {
                    if (pair.first.intValue() == i) {
                        return pair.second;
                    }
                }
            }
            ErrorState errorState = this.myBase;
            if (errorState == null || this.myDeriveIndex <= i) {
                return null;
            }
            return errorState.getError(i);
        }

        public int length(char[] cArr) {
            final Ref ref = new Ref(Integer.valueOf(cArr.length));
            processErrors(0, Integer.MAX_VALUE, new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.codeStyle.TypoTolerantMatcher$ErrorState$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.util.Processor
                public final boolean process(Object obj) {
                    return TypoTolerantMatcher.ErrorState.lambda$length$1(ref, (Pair) obj);
                }
            });
            return ((Integer) ref.get()).intValue();
        }

        static /* synthetic */ boolean lambda$length$1(Ref ref, Pair pair) {
            if (pair.second instanceof MissError) {
                ref.set(Integer.valueOf(((Integer) ref.get()).intValue() + 1));
            }
            return true;
        }
    }

    private static class TypoError implements Error {
        private final char myCorrectChar;

        TypoError(char c) {
            this.myCorrectChar = c;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SwapError implements Error {
        public static final SwapError instance = new SwapError();

        private SwapError() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MissError implements Error {
        private final char myMissedChar;

        MissError(char c) {
            this.myMissedChar = c;
        }
    }

    private static class Fragment {
        private final ErrorState myErrorState;
        private final int myLength;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = "errorState";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/TypoTolerantMatcher$Fragment";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/TypoTolerantMatcher$Fragment";
            } else {
                objArr[1] = "getErrorState";
            }
            if (i != 1) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        Fragment(int i, ErrorState errorState) {
            if (errorState == null) {
                $$$reportNull$$$0(0);
            }
            this.myLength = i;
            this.myErrorState = errorState;
        }

        int getLength() {
            return this.myLength;
        }

        ErrorState getErrorState() {
            ErrorState errorState = this.myErrorState;
            if (errorState == null) {
                $$$reportNull$$$0(1);
            }
            return errorState;
        }
    }

    private static class Range extends TextRange {
        private final int myErrorCount;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/codeStyle/TypoTolerantMatcher$Range", "shiftRight"));
        }

        Range(int i, int i2, int i3) {
            super(i, i2);
            this.myErrorCount = i3;
        }

        public int getErrorCount() {
            return this.myErrorCount;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.TextRange
        public Range shiftRight(int i) {
            return i == 0 ? this : new Range(getStartOffset() + i, getEndOffset() + i, getErrorCount());
        }

        public static Range from(int i, int i2) {
            return new Range(i, i2 + i, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static char leftMiss(char c) {
        boolean zIsUpperAscii = isUpperAscii(c);
        if (zIsUpperAscii) {
            c = toLowerAscii(c);
        }
        for (char[] cArr : keyboard) {
            for (int i = 0; i < cArr.length; i++) {
                if (cArr[i] == c) {
                    if (i <= 0) {
                        return (char) 0;
                    }
                    int i2 = i - 1;
                    return zIsUpperAscii ? toUpperAscii(cArr[i2]) : cArr[i2];
                }
            }
        }
        return (char) 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static char rightMiss(char c) {
        boolean zIsUpperAscii = isUpperAscii(c);
        if (zIsUpperAscii) {
            c = toLowerAscii(c);
        }
        for (char[] cArr : keyboard) {
            for (int i = 0; i < cArr.length; i++) {
                if (cArr[i] == c) {
                    int i2 = i + 1;
                    if (i2 < cArr.length) {
                        return zIsUpperAscii ? toUpperAscii(cArr[i2]) : cArr[i2];
                    }
                    return (char) 0;
                }
            }
        }
        return (char) 0;
    }
}
