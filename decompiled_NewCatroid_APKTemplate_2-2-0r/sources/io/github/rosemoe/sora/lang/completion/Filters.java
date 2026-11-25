package io.github.rosemoe.sora.lang.completion;

import io.github.rosemoe.sora.util.MyCharacter;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: filters.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0015\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0018\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\u001a6\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\t\u001a`\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0000\u001a8\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0011H\u0000\u001aF\u0010$\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\t2\n\b\u0002\u0010%\u001a\u0004\u0018\u00010&H\u0007\u001aB\u0010'\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\t2\b\u0010%\u001a\u0004\u0018\u00010&\u001aB\u0010(\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\t2\b\u0010%\u001a\u0004\u0018\u00010&\u001aL\u0010)\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010*\u001a\u00020\u001e2\b\u0010%\u001a\u0004\u0018\u00010&H\u0000\u001aB\u0010+\u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010#\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\t2\b\b\u0002\u0010,\u001a\u00020\u001eH\u0007\u001a\u0016\u0010-\u001a\u00020\u001e2\u0006\u0010.\u001a\u00020\u00112\u0006\u0010/\u001a\u00020\t\u001a\u001e\u00100\u001a\u00020\u001e2\u0006\u00101\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0011\u001a\u0016\u00102\u001a\u00020\u001e2\u0006\u0010.\u001a\u00020\u00112\u0006\u0010/\u001a\u00020\t\u001a\u001a\u00103\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\tH\u0000\"\u0019\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\n\n\u0002\u0010\u0005\u001a\u0004\b\u0003\u0010\u0004\"\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\n\n\u0002\u0010\u0005\u001a\u0004\b\u0007\u0010\u0004\"\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0019\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\n\n\u0002\u0010\u0005\u001a\u0004\b\r\u0010\u0004¨\u00064"}, d2 = {"arrows", "", "", "getArrows", "()[[I", "[[I", "diag", "getDiag", "maxLen", "", "maxWordMatchPosArray", "minWordMatchPosArray", "table", "getTable", "anyScore", "Lio/github/rosemoe/sora/lang/completion/FuzzyScore;", "pattern", "", "lowPattern", "patternPos", "word", "lowWord", "wordPos", "doScore", "patternLow", "patternStart", "wordLow", "wordLen", "wordStart", "newMatchStart", "", "outFirstMatchStrong", "", "fillInMaxWordMatchPos", "", "patternLen", "fuzzyScore", "options", "Lio/github/rosemoe/sora/lang/completion/FuzzyScoreOptions;", "fuzzyScoreGraceful", "fuzzyScoreGracefulAggressive", "fuzzyScoreWithPermutations", "aggressive", "isPatternInWord", "fillMinWordPosArr", "isSeparatorAtPos", "value", "index", "isUpperCaseAtPos", "pos", "isWhitespaceAtPos", "nextTypoPermutation", "editor_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class Filters {
    private static final int[][] arrows;
    private static final int[][] diag;
    private static int maxLen = 32;
    private static final int[][] table;
    private static final int[] minWordMatchPosArray = new int[32 * 2];
    private static final int[] maxWordMatchPosArray = new int[32 * 2];

    public static final FuzzyScore fuzzyScore(String pattern, String patternLow, int i, String word, String wordLow, int i2) {
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(patternLow, "patternLow");
        Intrinsics.checkNotNullParameter(word, "word");
        Intrinsics.checkNotNullParameter(wordLow, "wordLow");
        return fuzzyScore$default(pattern, patternLow, i, word, wordLow, i2, null, 64, null);
    }

    public static final boolean isPatternInWord(String patternLow, int i, int i2, String wordLow, int i3, int i4) {
        Intrinsics.checkNotNullParameter(patternLow, "patternLow");
        Intrinsics.checkNotNullParameter(wordLow, "wordLow");
        return isPatternInWord$default(patternLow, i, i2, wordLow, i3, i4, false, 64, null);
    }

    static {
        int[][] iArr = new int[32][];
        for (int i = 0; i < 32; i++) {
            iArr[i] = new int[maxLen];
        }
        diag = iArr;
        int i2 = maxLen;
        int[][] iArr2 = new int[i2][];
        for (int i3 = 0; i3 < i2; i3++) {
            iArr2[i3] = new int[maxLen];
        }
        table = iArr2;
        int i4 = maxLen;
        int[][] iArr3 = new int[i4][];
        for (int i5 = 0; i5 < i4; i5++) {
            iArr3[i5] = new int[maxLen];
        }
        arrows = iArr3;
    }

    public static final int[][] getDiag() {
        return diag;
    }

    public static final int[][] getTable() {
        return table;
    }

    public static final int[][] getArrows() {
        return arrows;
    }

    public static /* synthetic */ boolean isPatternInWord$default(String str, int i, int i2, String str2, int i3, int i4, boolean z, int i5, Object obj) {
        if ((i5 & 64) != 0) {
            z = false;
        }
        return isPatternInWord(str, i, i2, str2, i3, i4, z);
    }

    public static final boolean isPatternInWord(String patternLow, int i, int i2, String wordLow, int i3, int i4, boolean z) {
        Intrinsics.checkNotNullParameter(patternLow, "patternLow");
        Intrinsics.checkNotNullParameter(wordLow, "wordLow");
        while (i < i2 && i3 < i4) {
            if (patternLow.charAt(i) == wordLow.charAt(i3)) {
                if (z) {
                    minWordMatchPosArray[i] = i3;
                }
                i++;
            }
            i3++;
        }
        return i == i2;
    }

    public static final void fillInMaxWordMatchPos(int i, int i2, int i3, int i4, String patternLow, String wordLow) {
        Intrinsics.checkNotNullParameter(patternLow, "patternLow");
        Intrinsics.checkNotNullParameter(wordLow, "wordLow");
        int i5 = i - 1;
        for (int i6 = i2 - 1; i5 >= i3 && i6 >= i4; i6--) {
            if (patternLow.charAt(i5) == wordLow.charAt(i6)) {
                maxWordMatchPosArray[i5] = i6;
                i5--;
            }
        }
    }

    public static final boolean isUpperCaseAtPos(int i, String word, String wordLow) {
        Intrinsics.checkNotNullParameter(word, "word");
        Intrinsics.checkNotNullParameter(wordLow, "wordLow");
        return word.charAt(i) != wordLow.charAt(i);
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0044 A[FALL_THROUGH, ORIG_RETURN, RETURN] */
    public static final boolean isSeparatorAtPos(String value, int i) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (i < 0 || i >= value.length()) {
            return false;
        }
        int iCodePointAt = value.codePointAt(i);
        if (iCodePointAt != 32 && iCodePointAt != 34 && iCodePointAt != 36 && iCodePointAt != 58 && iCodePointAt != 60 && iCodePointAt != 62 && iCodePointAt != 95 && iCodePointAt != 123 && iCodePointAt != 125) {
            switch (iCodePointAt) {
                default:
                    switch (iCodePointAt) {
                        default:
                            switch (iCodePointAt) {
                                case 91:
                                case 92:
                                case 93:
                                    break;
                                default:
                                    return MyCharacter.couldBeEmoji(iCodePointAt);
                            }
                        case 45:
                        case 46:
                        case 47:
                            return true;
                    }
                case 39:
                case 40:
                case 41:
                    return true;
            }
        }
        return true;
    }

    public static final boolean isWhitespaceAtPos(String value, int i) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (i < 0 || i >= value.length()) {
            return false;
        }
        char cCharAt = value.charAt(i);
        return cCharAt == '\t' || cCharAt == ' ';
    }

    public static final FuzzyScore anyScore(String pattern, String lowPattern, int i, String word, String lowWord, int i2) {
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(lowPattern, "lowPattern");
        Intrinsics.checkNotNullParameter(word, "word");
        Intrinsics.checkNotNullParameter(lowWord, "lowWord");
        int iCoerceAtMost = RangesKt.coerceAtMost(13, pattern.length());
        while (i < iCoerceAtMost) {
            FuzzyScore fuzzyScore = fuzzyScore(pattern, lowPattern, i, word, lowWord, i2, new FuzzyScoreOptions(false, true));
            if (fuzzyScore != null) {
                return fuzzyScore;
            }
            i++;
        }
        return new FuzzyScore(0, i2, null, 4, null);
    }

    public static /* synthetic */ FuzzyScore fuzzyScore$default(String str, String str2, int i, String str3, String str4, int i2, FuzzyScoreOptions fuzzyScoreOptions, int i3, Object obj) {
        if ((i3 & 64) != 0) {
            fuzzyScoreOptions = FuzzyScoreOptions.INSTANCE.getDefault();
        }
        return fuzzyScore(str, str2, i, str3, str4, i2, fuzzyScoreOptions);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FuzzyScore fuzzyScore(String pattern, String patternLow, int i, String word, String wordLow, int i2, FuzzyScoreOptions fuzzyScoreOptions) {
        char c;
        int i3;
        boolean[] zArr;
        int iDoScore;
        int i4;
        char c2;
        int i5;
        int i6;
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(patternLow, "patternLow");
        Intrinsics.checkNotNullParameter(word, "word");
        Intrinsics.checkNotNullParameter(wordLow, "wordLow");
        int length = pattern.length();
        int length2 = maxLen;
        if (length <= length2) {
            length2 = pattern.length();
        }
        int i7 = length2;
        int length3 = word.length();
        int i8 = maxLen;
        char c3 = 1;
        int length4 = length3 > i8 + (-1) ? i8 - 1 : word.length();
        if (i >= i7 || i2 >= length4 || i7 - i > length4 - i2 || !isPatternInWord(patternLow, i, i7, wordLow, i2, length4, true)) {
            return null;
        }
        fillInMaxWordMatchPos(i7, length4, i, i2, patternLow, wordLow);
        int i9 = 0;
        boolean[] zArr2 = {false};
        int i10 = 1;
        int i11 = 1;
        int i12 = i;
        while (i12 < i7) {
            int i13 = minWordMatchPosArray[i12];
            int[] iArr = maxWordMatchPosArray;
            int i14 = iArr[i12];
            int i15 = i12 + 1;
            int i16 = i15 < i7 ? iArr[i15] : length4;
            int i17 = (i13 - i2) + c3;
            int i18 = i13;
            while (i18 < i16) {
                if (i18 <= i14) {
                    i3 = i18;
                    zArr = zArr2;
                    iDoScore = doScore(pattern, patternLow, i12, i, word, wordLow, i3, length4, i2, diag[i11 + (-1)][i17 + (-1)] == 0 ? c3 : (char) 0, zArr);
                } else {
                    i3 = i18;
                    zArr = zArr2;
                    iDoScore = Integer.MIN_VALUE;
                }
                if (iDoScore != Integer.MAX_VALUE) {
                    i4 = iDoScore + table[i11 - 1][i17 - 1];
                    c2 = c3;
                } else {
                    i4 = 0;
                    c2 = 0;
                }
                int i19 = i3;
                char c4 = i19 > i13 ? c3 : (char) 0;
                if (c4 != 0) {
                    int i20 = i17 - 1;
                    i5 = table[i11][i20] + (diag[i11][i20] > 0 ? -5 : 0);
                } else {
                    i5 = 0;
                }
                char c5 = (i19 <= i13 + 1 || diag[i11][i17 + (-1)] <= 0) ? (char) 0 : c3;
                if (c5 != 0) {
                    int i21 = i17 - 2;
                    i6 = table[i11][i21] + (diag[i11][i21] <= 0 ? 0 : -5);
                } else {
                    i6 = 0;
                }
                if (c5 != 0 && ((c4 == 0 || i6 >= i5) && (c2 == 0 || i6 >= i4))) {
                    table[i11][i17] = i6;
                    arrows[i11][i17] = Arrow.INSTANCE.getLeftLeft();
                    diag[i11][i17] = 0;
                } else if (c4 != 0 && (c2 == 0 || i5 >= i4)) {
                    table[i11][i17] = i5;
                    arrows[i11][i17] = Arrow.INSTANCE.getLeft();
                    diag[i11][i17] = 0;
                } else if (c2 != 0) {
                    table[i11][i17] = i4;
                    arrows[i11][i17] = Arrow.INSTANCE.getDiag();
                    int[][] iArr2 = diag;
                    iArr2[i11][i17] = iArr2[i11 - 1][i17 - 1] + c3;
                } else {
                    throw new IllegalStateException("not possible".toString());
                }
                i17++;
                i18 = i19 + 1;
                i13 = i13;
                c3 = c3;
                i16 = i16;
                i15 = i15;
                i14 = i14;
                i12 = i12;
                zArr2 = zArr;
                length4 = length4;
                i7 = i7;
            }
            i11++;
            i10 = i17;
            i12 = i15;
        }
        int i22 = length4;
        char c6 = c3;
        int i23 = i7;
        if (!zArr2[0] && fuzzyScoreOptions != null && !fuzzyScoreOptions.getFirstMatchCanBeWeak()) {
            return null;
        }
        int i24 = i11 - 1;
        int i25 = i10 - 1;
        FuzzyScore fuzzyScore = new FuzzyScore(table[i24][i25], i2, null, 4, null);
        int i26 = 0;
        int i27 = i24;
        while (i27 >= c6) {
            int i28 = i25;
            do {
                int i29 = arrows[i27][i28];
                if (i29 != Arrow.INSTANCE.getLeftLeft()) {
                    if (i29 != Arrow.INSTANCE.getLeft()) {
                        break;
                    }
                    i28--;
                } else {
                    i28 -= 2;
                }
            } while (i28 >= c6);
            if (i26 > c6) {
                c = c6;
                if (patternLow.charAt((i + i27) - c6) == wordLow.charAt((i2 + i25) - c6) && !isUpperCaseAtPos((i28 + i2) - c, word, wordLow) && i26 + 1 > diag[i27][i28]) {
                    i28 = i25;
                }
            } else {
                c = c6;
            }
            i26 = i28 == i25 ? i26 + 1 : c;
            if (i9 == 0) {
                i9 = i28;
            }
            i27--;
            i25 = i28 - 1;
            fuzzyScore.getMatches().add(Integer.valueOf(i25));
            c6 = c;
        }
        char c7 = c6;
        if (i22 == i23 && fuzzyScoreOptions != null && fuzzyScoreOptions.getBoostFullMatch() == c7) {
            fuzzyScore.setScore(fuzzyScore.getScore() + 2);
        }
        fuzzyScore.setScore(fuzzyScore.getScore() - (i9 - i23));
        return fuzzyScore;
    }

    /* JADX WARN: Code duplicated, block: B:47:0x009d  */
    public static final int doScore(String pattern, String patternLow, int i, int i2, String word, String wordLow, int i3, int i4, int i5, boolean z, boolean[] outFirstMatchStrong) {
        int i6;
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(patternLow, "patternLow");
        Intrinsics.checkNotNullParameter(word, "word");
        Intrinsics.checkNotNullParameter(wordLow, "wordLow");
        Intrinsics.checkNotNullParameter(outFirstMatchStrong, "outFirstMatchStrong");
        if (patternLow.charAt(i) != wordLow.charAt(i3)) {
            return Integer.MIN_VALUE;
        }
        int i7 = 7;
        if (i3 == i - i2) {
            i7 = pattern.charAt(i) != word.charAt(i3) ? 5 : 7;
            i6 = 0;
        } else {
            if (isUpperCaseAtPos(i3, word, wordLow) && (i3 == 0 || !isUpperCaseAtPos(i3 - 1, word, wordLow))) {
                if (pattern.charAt(i) != word.charAt(i3)) {
                }
                i6 = 1;
            } else if (!isSeparatorAtPos(wordLow, i3) || (i3 != 0 && isSeparatorAtPos(wordLow, i3 - 1))) {
                int i8 = i3 - 1;
                if (!isSeparatorAtPos(wordLow, i8) && !isWhitespaceAtPos(wordLow, i8)) {
                    i6 = 0;
                    i7 = 1;
                }
            } else {
                i6 = 0;
                i7 = 5;
            }
            i7 = 5;
            i6 = 1;
        }
        if (i7 > 1 && i == i2) {
            outFirstMatchStrong[0] = true;
        }
        if (i6 == 0) {
            if (isUpperCaseAtPos(i3, word, wordLow)) {
                i6 = 1;
            } else {
                int i9 = i3 - 1;
                if (isSeparatorAtPos(wordLow, i9) || isWhitespaceAtPos(wordLow, i9)) {
                    i6 = 1;
                } else {
                    i6 = 0;
                }
            }
        }
        if (i == i2) {
            if (i3 > i5) {
                i7 -= i6 != 0 ? 3 : 5;
            }
        } else if (z) {
            i7 += i6 != 0 ? 2 : 0;
        } else {
            i7 += i6 ^ 1;
        }
        if (i3 + 1 == i4) {
            return i7 - (i6 != 0 ? 3 : 5);
        }
        return i7;
    }

    public static final FuzzyScore fuzzyScoreGracefulAggressive(String pattern, String lowPattern, int i, String word, String lowWord, int i2, FuzzyScoreOptions fuzzyScoreOptions) {
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(lowPattern, "lowPattern");
        Intrinsics.checkNotNullParameter(word, "word");
        Intrinsics.checkNotNullParameter(lowWord, "lowWord");
        return fuzzyScoreWithPermutations(pattern, lowPattern, i, word, lowWord, i2, true, fuzzyScoreOptions);
    }

    public static final FuzzyScore fuzzyScoreGraceful(String pattern, String lowPattern, int i, String word, String lowWord, int i2, FuzzyScoreOptions fuzzyScoreOptions) {
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(lowPattern, "lowPattern");
        Intrinsics.checkNotNullParameter(word, "word");
        Intrinsics.checkNotNullParameter(lowWord, "lowWord");
        return fuzzyScoreWithPermutations(pattern, lowPattern, i, word, lowWord, i2, false, fuzzyScoreOptions);
    }

    public static final FuzzyScore fuzzyScoreWithPermutations(String pattern, String lowPattern, int i, String word, String lowWord, int i2, boolean z, FuzzyScoreOptions fuzzyScoreOptions) {
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(lowPattern, "lowPattern");
        Intrinsics.checkNotNullParameter(word, "word");
        Intrinsics.checkNotNullParameter(lowWord, "lowWord");
        FuzzyScore fuzzyScore = fuzzyScore(pattern, lowPattern, i, word, lowWord, i2, fuzzyScoreOptions == null ? FuzzyScoreOptions.INSTANCE.getDefault() : fuzzyScoreOptions);
        if (fuzzyScore != null && !z) {
            return fuzzyScore;
        }
        if (pattern.length() >= 3) {
            int iCoerceAtMost = RangesKt.coerceAtMost(7, pattern.length() - 1);
            for (int i3 = i + 1; i3 < iCoerceAtMost; i3++) {
                String strNextTypoPermutation = nextTypoPermutation(pattern, i3);
                if (strNextTypoPermutation != null) {
                    String lowerCase = strNextTypoPermutation.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                    FuzzyScore fuzzyScore2 = fuzzyScore(strNextTypoPermutation, lowerCase, i, word, lowWord, i2, fuzzyScoreOptions == null ? FuzzyScoreOptions.INSTANCE.getDefault() : fuzzyScoreOptions);
                    if (fuzzyScore2 != null) {
                        fuzzyScore2.setScore(fuzzyScore2.getScore() - 3);
                        if (fuzzyScore == null || fuzzyScore2.getScore() > fuzzyScore.getScore()) {
                            fuzzyScore = fuzzyScore2;
                        }
                    }
                }
            }
        }
        return fuzzyScore;
    }

    public static final String nextTypoPermutation(String pattern, int i) {
        char cCharAt;
        char cCharAt2;
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        int i2 = i + 1;
        if (i2 >= pattern.length() || (cCharAt = pattern.charAt(i)) == (cCharAt2 = pattern.charAt(i2))) {
            return null;
        }
        String strSubstring = pattern.substring(0, i);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        String strSubstring2 = pattern.substring(i + 2);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        return strSubstring + cCharAt2 + cCharAt + strSubstring2;
    }
}
