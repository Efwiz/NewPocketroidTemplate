package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;
import org.jetbrains.kotlin.com.intellij.util.containers.FList;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayCharSequence;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.text.NameUtilCore;

/* JADX INFO: loaded from: classes3.dex */
class MinusculeMatcherImpl extends MinusculeMatcher {
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

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "options";
                break;
            case 2:
                objArr[0] = "hardSeparators";
                break;
            case 3:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                objArr[0] = "name";
                break;
            case 4:
                objArr[0] = "ranges";
                break;
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/MinusculeMatcherImpl";
                break;
            default:
                objArr[0] = "pattern";
                break;
        }
        if (i == 5 || i == 6) {
            objArr[1] = "prependRange";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/MinusculeMatcherImpl";
        }
        switch (i) {
            case 3:
                objArr[2] = "nextWord";
                break;
            case 4:
                objArr[2] = "prependRange";
                break;
            case 5:
            case 6:
                break;
            case 7:
                objArr[2] = "matchingDegree";
                break;
            case 8:
                objArr[2] = "matchingFragments";
                break;
            case 9:
                objArr[2] = "matchBySubstring";
                break;
            case 10:
                objArr[2] = "matchWildcards";
                break;
            case 11:
                objArr[2] = "matchSkippingWords";
                break;
            case 12:
                objArr[2] = "findNextPatternCharOccurrence";
                break;
            case 13:
                objArr[2] = "seemsLikeFragmentStart";
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
                objArr[2] = "isFirstCharMatching";
                break;
            case 21:
                objArr[2] = "indexOfWordStart";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private static char toLowerAscii(char c) {
        return (c < 'A' || c > 'Z') ? c : (char) (c + StringUtil.SPACE);
    }

    private static char toUpperAscii(char c) {
        return (c < 'a' || c > 'z') ? c : (char) (c - ' ');
    }

    MinusculeMatcherImpl(String str, NameUtil.MatchingCaseSensitivity matchingCaseSensitivity, String str2) {
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

    private static boolean isWordSeparator(char c) {
        return Character.isWhitespace(c) || c == '_' || c == '-' || c == ':' || c == '+' || c == '.';
    }

    private static int nextWord(String str, int i) {
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

    private static FList<TextRange> prependRange(FList<TextRange> fList, int i, int i2) {
        if (fList == null) {
            $$$reportNull$$$0(4);
        }
        TextRange head = fList.getHead();
        if (head != null && head.getStartOffset() == i + i2) {
            FList<TextRange> fListPrepend = fList.getTail().prepend(new TextRange(i, head.getEndOffset()));
            if (fListPrepend == null) {
                $$$reportNull$$$0(5);
            }
            return fListPrepend;
        }
        FList<TextRange> fListPrepend2 = fList.prepend(TextRange.from(i, i2));
        if (fListPrepend2 == null) {
            $$$reportNull$$$0(6);
        }
        return fListPrepend2;
    }

    /* JADX WARN: Code duplicated, block: B:67:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:68:0x0100  */
    /* JADX WARN: Code duplicated, block: B:70:0x0103  */
    /* JADX WARN: Code duplicated, block: B:71:0x0106  */
    /* JADX WARN: Code duplicated, block: B:75:0x0114  */
    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public int matchingDegree(String str, boolean z, FList<? extends TextRange> fList) {
        int i;
        int i2;
        int i3;
        int i4;
        boolean z2;
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        if (fList == null) {
            return Integer.MIN_VALUE;
        }
        if (fList.isEmpty()) {
            return 0;
        }
        TextRange head = fList.getHead();
        int i5 = head.getStartOffset() == 0 ? 1 : 0;
        boolean z3 = i5 != 0 && z;
        Iterator<? extends TextRange> it = fList.iterator();
        int i6 = -1;
        int i7 = 0;
        int i8 = 0;
        int iNextWord = 0;
        boolean z4 = false;
        while (it.hasNext()) {
            TextRange next = it.next();
            int iEvaluateCaseMatching = i7;
            int startOffset = next.getStartOffset();
            while (startOffset < next.getEndOffset()) {
                boolean z5 = startOffset == next.getStartOffset() && head != next;
                int i9 = i8;
                boolean z6 = false;
                while (iNextWord <= startOffset) {
                    if (iNextWord == startOffset) {
                        z6 = true;
                    } else if (z5) {
                        i9++;
                    }
                    iNextWord = nextWord(str, iNextWord);
                }
                char cCharAt = str.charAt(startOffset);
                char[] cArr = this.myPattern;
                int iIndexOf = Strings.indexOf(cArr, cCharAt, i6 + 1, cArr.length, false);
                if (iIndexOf < 0) {
                    i6 = iIndexOf;
                    i8 = i9;
                    break;
                }
                if (z6) {
                    z2 = cCharAt == this.myPattern[iIndexOf] && this.isUpperCase[iIndexOf];
                } else {
                    z2 = z4;
                }
                iEvaluateCaseMatching += evaluateCaseMatching(z3, iIndexOf, z2, startOffset, z5, z6, cCharAt);
                startOffset++;
                i6 = iIndexOf;
                i8 = i9;
                next = next;
                z4 = z2;
                iNextWord = iNextWord;
            }
            i7 = iEvaluateCaseMatching;
        }
        int startOffset2 = head.getStartOffset();
        boolean z7 = Strings.indexOfAny(str, this.myHardSeparators, 0, startOffset2) >= 0;
        if (startOffset2 != 0) {
            if (NameUtilCore.isWordStart(str, startOffset2)) {
                i = 1;
                if (!NameUtilCore.isWordStart(str, startOffset2 - 1)) {
                }
                if (fList.get(fList.size() - i).getEndOffset() == str.length()) {
                    i3 = i;
                } else {
                    i3 = 0;
                }
                if (i2 != 0) {
                    i4 = 1000;
                } else {
                    i4 = 0;
                }
                return ((i4 + i7) - fList.size()) + ((-i8) * 10) + (z7 ? 0 : 2) + i5 + i3;
            }
            i = 1;
            i2 = 0;
            if (fList.get(fList.size() - i).getEndOffset() == str.length()) {
                i3 = i;
            } else {
                i3 = 0;
            }
            if (i2 != 0) {
                i4 = 1000;
            } else {
                i4 = 0;
            }
            return ((i4 + i7) - fList.size()) + ((-i8) * 10) + (z7 ? 0 : 2) + i5 + i3;
        }
        i = 1;
        i2 = i;
        if (fList.get(fList.size() - i).getEndOffset() == str.length()) {
            i3 = i;
        } else {
            i3 = 0;
        }
        if (i2 != 0) {
            i4 = 1000;
        } else {
            i4 = 0;
        }
        return ((i4 + i7) - fList.size()) + ((-i8) * 10) + (z7 ? 0 : 2) + i5 + i3;
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
    public String getPattern() {
        return new String(this.myPattern);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public FList<TextRange> matchingFragments(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (str.length() < this.myMinNameLength) {
            return null;
        }
        if (this.myPattern.length > 100) {
            return matchBySubstring(str);
        }
        int length = str.length();
        boolean z = true;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt >= 128) {
                z = false;
            }
            char[] cArr = this.myMeaningfulCharacters;
            if (i < cArr.length && (cCharAt == cArr[i] || cCharAt == cArr[i + 1])) {
                i += 2;
            }
        }
        if (i < this.myMinNameLength * 2) {
            return null;
        }
        return matchWildcards(str, 0, 0, z);
    }

    private FList<TextRange> matchBySubstring(String str) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        boolean zIsPatternChar = isPatternChar(0, '*');
        char[] cArrFilterWildcard = filterWildcard(this.myPattern);
        if (str.length() < cArrFilterWildcard.length) {
            return null;
        }
        if (zIsPatternChar) {
            int iIndexOfIgnoreCase = Strings.indexOfIgnoreCase(str, new CharArrayCharSequence(cArrFilterWildcard, 0, cArrFilterWildcard.length), 0);
            if (iIndexOfIgnoreCase >= 0) {
                return FList.emptyList().prepend(TextRange.from(iIndexOfIgnoreCase, cArrFilterWildcard.length - 1));
            }
            return null;
        }
        if (CharArrayUtil.regionMatches(cArrFilterWildcard, 0, cArrFilterWildcard.length, str)) {
            return FList.emptyList().prepend(new TextRange(0, cArrFilterWildcard.length));
        }
        return null;
    }

    private static char[] filterWildcard(char[] cArr) {
        char[] cArr2 = new char[cArr.length];
        int i = 0;
        for (char c : cArr) {
            if (c != '*') {
                cArr2[i] = c;
                i++;
            }
        }
        return Arrays.copyOf(cArr2, i);
    }

    private FList<TextRange> matchWildcards(String str, int i, int i2, boolean z) {
        int i3;
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (i2 < 0) {
            return null;
        }
        if (!isWildcard(i)) {
            if (i == this.myPattern.length) {
                return FList.emptyList();
            }
            return matchFragment(str, i, i2, z);
        }
        while (true) {
            i3 = i + 1;
            if (!isWildcard(i3)) {
                break;
            }
            i = i3;
        }
        if (i3 == this.myPattern.length) {
            if (isTrailingSpacePattern() && i2 != str.length() && (i3 < 2 || !isUpperCaseOrDigit(this.myPattern[i - 1]))) {
                int iIndexOf = str.indexOf(32, i2);
                if (iIndexOf >= 0) {
                    return FList.emptyList().prepend(TextRange.from(iIndexOf, 1));
                }
                return null;
            }
            return FList.emptyList();
        }
        return matchSkippingWords(str, i3, findNextPatternCharOccurrence(str, i2, i3, z), true, z);
    }

    private boolean isTrailingSpacePattern() {
        return isPatternChar(this.myPattern.length - 1, StringUtil.SPACE);
    }

    private static boolean isUpperCaseOrDigit(char c) {
        return Character.isUpperCase(c) || Character.isDigit(c);
    }

    private FList<TextRange> matchSkippingWords(String str, int i, int i2, boolean z, boolean z2) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        int i3 = 0;
        while (i2 >= 0) {
            int iMaxMatchingFragment = seemsLikeFragmentStart(str, i, i2) ? maxMatchingFragment(str, i, i2) : 0;
            if (iMaxMatchingFragment > i3 || (i2 + iMaxMatchingFragment == str.length() && isTrailingSpacePattern())) {
                int i4 = !isMiddleMatch(str, i, i2) ? iMaxMatchingFragment : i3;
                FList<TextRange> fListMatchInsideFragment = matchInsideFragment(str, i, i2, z2, iMaxMatchingFragment);
                if (fListMatchInsideFragment != null) {
                    return fListMatchInsideFragment;
                }
                i3 = i4;
            }
            int i5 = i2 + 1;
            int iFindNextPatternCharOccurrence = findNextPatternCharOccurrence(str, i5, i, z2);
            i2 = z ? iFindNextPatternCharOccurrence : checkForSpecialChars(str, i5, iFindNextPatternCharOccurrence, i);
        }
        return null;
    }

    private int findNextPatternCharOccurrence(String str, int i, int i2, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (!isPatternChar(i2 - 1, '*') && !this.isWordSeparator[i2]) {
            return indexOfWordStart(str, i2, i, z);
        }
        return indexOfIgnoreCase(str, i, this.myPattern[i2], i2, z);
    }

    private int checkForSpecialChars(String str, int i, int i2, int i3) {
        if (i2 < 0) {
            return -1;
        }
        if (!this.myHasSeparators && !this.myHasHumps && Strings.containsAnyChar(str, this.myHardSeparators, i, i2)) {
            return -1;
        }
        if (this.myHasDots && !isPatternChar(i3 - 1, '.') && Strings.contains(str, i, i2, '.')) {
            return -1;
        }
        return i2;
    }

    private boolean seemsLikeFragmentStart(String str, int i, int i2) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        return !this.isUpperCase[i] || Character.isUpperCase(str.charAt(i2)) || NameUtilCore.isWordStart(str, i2) || !(this.myHasHumps || this.myOptions == NameUtil.MatchingCaseSensitivity.ALL);
    }

    private boolean charEquals(char c, int i, char c2, boolean z) {
        return c == c2 || (z && (this.toLowerCase[i] == c2 || this.toUpperCase[i] == c2));
    }

    private FList<TextRange> matchFragment(String str, int i, int i2, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        int iMaxMatchingFragment = maxMatchingFragment(str, i, i2);
        if (iMaxMatchingFragment == 0) {
            return null;
        }
        return matchInsideFragment(str, i, i2, z, iMaxMatchingFragment);
    }

    private int maxMatchingFragment(String str, int i, int i2) {
        int i3;
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        if (!isFirstCharMatching(str, i2, i)) {
            return 0;
        }
        int i4 = 1;
        boolean z = this.myOptions != NameUtil.MatchingCaseSensitivity.ALL;
        while (true) {
            int i5 = i2 + i4;
            if (i5 >= str.length() || (i3 = i + i4) >= this.myPattern.length) {
                break;
            }
            char cCharAt = str.charAt(i5);
            if (charEquals(this.myPattern[i3], i3, cCharAt, z)) {
                i4++;
            } else if (isSkippingDigitBetweenPatternDigits(i3, cCharAt)) {
                return 0;
            }
        }
        return i4;
    }

    private boolean isSkippingDigitBetweenPatternDigits(int i, char c) {
        return Character.isDigit(this.myPattern[i]) && Character.isDigit(this.myPattern[i - 1]) && Character.isDigit(c);
    }

    private FList<TextRange> matchInsideFragment(String str, int i, int i2, boolean z, int i3) {
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        int i4 = isMiddleMatch(str, i, i2) ? 3 : 1;
        FList<TextRange> fListImproveCamelHumps = improveCamelHumps(str, i, i2, z, i3, i4);
        return fListImproveCamelHumps != null ? fListImproveCamelHumps : findLongestMatchingPrefix(str, i, i2, z, i3, i4);
    }

    private boolean isMiddleMatch(String str, int i, int i2) {
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        return isPatternChar(i + (-1), '*') && !isWildcard(i + 1) && Character.isLetterOrDigit(str.charAt(i2)) && !NameUtilCore.isWordStart(str, i2);
    }

    private FList<TextRange> findLongestMatchingPrefix(String str, int i, int i2, boolean z, int i3, int i4) {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        if (i + i3 >= this.myPattern.length) {
            return FList.emptyList().prepend(TextRange.from(i2, i3));
        }
        while (true) {
            FList<TextRange> fListMatchSkippingWords = null;
            if (i3 < i4 && (i3 <= 0 || !isWildcard(i + i3))) {
                break;
            }
            int i5 = i + i3;
            if (isWildcard(i5)) {
                fListMatchSkippingWords = matchWildcards(str, i5, i2 + i3, z);
            } else {
                int i6 = i2 + i3;
                int iCheckForSpecialChars = checkForSpecialChars(str, i6, findNextPatternCharOccurrence(str, i6 + 1, i5, z), i5);
                if (iCheckForSpecialChars >= 0) {
                    fListMatchSkippingWords = matchSkippingWords(str, i5, iCheckForSpecialChars, false, z);
                }
            }
            if (fListMatchSkippingWords != null) {
                return prependRange(fListMatchSkippingWords, i2, i3);
            }
            i3--;
        }
        return null;
    }

    private FList<TextRange> improveCamelHumps(String str, int i, int i2, boolean z, int i3, int i4) {
        FList<TextRange> fListFindUppercaseMatchFurther;
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        while (i4 < i3) {
            int i5 = i + i4;
            int i6 = i2 + i4;
            if (isUppercasePatternVsLowercaseNameChar(str, i5, i6) && (fListFindUppercaseMatchFurther = findUppercaseMatchFurther(str, i5, i6, z)) != null) {
                return prependRange(fListFindUppercaseMatchFurther, i2, i4);
            }
            i4++;
        }
        return null;
    }

    private boolean isUppercasePatternVsLowercaseNameChar(String str, int i, int i2) {
        return this.isUpperCase[i] && this.myPattern[i] != str.charAt(i2);
    }

    private FList<TextRange> findUppercaseMatchFurther(String str, int i, int i2, boolean z) {
        return matchWildcards(str, i, indexOfWordStart(str, i, i2, z), z);
    }

    private boolean isFirstCharMatching(String str, int i, int i2) {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        if (i >= str.length()) {
            return false;
        }
        boolean z = this.myOptions != NameUtil.MatchingCaseSensitivity.ALL;
        char c = this.myPattern[i2];
        if (charEquals(c, i2, str.charAt(i), z)) {
            return (this.myOptions == NameUtil.MatchingCaseSensitivity.FIRST_LETTER && (i2 == 0 || (i2 == 1 && isWildcard(0))) && hasCase(c) && Character.isUpperCase(c) != Character.isUpperCase(str.charAt(0))) ? false : true;
        }
        return false;
    }

    private static boolean hasCase(char c) {
        return Character.isUpperCase(c) || Character.isLowerCase(c);
    }

    private boolean isWildcard(int i) {
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

    private boolean isPatternChar(int i, char c) {
        if (i >= 0) {
            char[] cArr = this.myPattern;
            if (i < cArr.length && cArr[i] == c) {
                return true;
            }
        }
        return false;
    }

    private int indexOfWordStart(String str, int i, int i2, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(21);
        }
        char c = this.myPattern[i];
        if (i2 >= str.length() || (this.myHasHumps && this.isLowerCase[i] && (i <= 0 || !this.isWordSeparator[i - 1]))) {
            return -1;
        }
        boolean zIsLetterOrDigit = Character.isLetterOrDigit(c);
        int i3 = i2;
        while (true) {
            int iIndexOfIgnoreCase = indexOfIgnoreCase(str, i3, c, i, z);
            if (iIndexOfIgnoreCase < 0) {
                return -1;
            }
            if (!zIsLetterOrDigit || NameUtilCore.isWordStart(str, iIndexOfIgnoreCase)) {
                return iIndexOfIgnoreCase;
            }
            i3 = iIndexOfIgnoreCase + 1;
        }
    }

    private int indexOfIgnoreCase(String str, int i, char c, int i2, boolean z) {
        if (z && Strings.isAscii(c)) {
            char c2 = this.toUpperCase[i2];
            char c3 = this.toLowerCase[i2];
            while (i < str.length()) {
                char cCharAt = str.charAt(i);
                if (cCharAt == c || toUpperAscii(cCharAt) == c2 || toLowerAscii(cCharAt) == c3) {
                    return i;
                }
                i++;
            }
            return -1;
        }
        return Strings.indexOfIgnoreCase(str, c, i);
    }

    public String toString() {
        return "MinusculeMatcherImpl{myPattern=" + new String(this.myPattern) + ", myOptions=" + this.myOptions + '}';
    }
}
