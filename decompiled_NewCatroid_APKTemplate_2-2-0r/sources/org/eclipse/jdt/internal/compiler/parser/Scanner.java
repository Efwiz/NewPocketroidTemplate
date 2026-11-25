package org.eclipse.jdt.internal.compiler.parser;

import com.beust.jcommander.Parameters;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.reflect.Array;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes14.dex */
public class Scanner implements TerminalTokens {
    public static final String BINARY_LITERAL_NOT_BELOW_17 = "Binary_Literal_Not_Below_17";
    public static final int BracketKinds = 3;
    public static final int COMMENT_ARRAYS_SIZE = 30;
    public static final int CurlyBracket = 2;
    public static final String END_OF_SOURCE = "End_Of_Source";
    public static final int HIGH_SURROGATE_MAX_VALUE = 56319;
    public static final int HIGH_SURROGATE_MIN_VALUE = 55296;
    public static final char[] IDENTITY_COMPARISON_TAG;
    public static final String ILLEGAL_HEXA_LITERAL = "Illegal_Hexa_Literal";
    public static final String INVALID_BINARY = "Invalid_Binary_Literal";
    public static final String INVALID_CHARACTER_CONSTANT = "Invalid_Character_Constant";
    public static final String INVALID_CHAR_IN_STRING = "Invalid_Char_In_String";
    public static final String INVALID_DIGIT = "Invalid_Digit";
    public static final String INVALID_ESCAPE = "Invalid_Escape";
    public static final String INVALID_FLOAT = "Invalid_Float_Literal";
    public static final String INVALID_HEXA = "Invalid_Hexa_Literal";
    public static final String INVALID_HIGH_SURROGATE = "Invalid_High_Surrogate";
    public static final String INVALID_INPUT = "Invalid_Input";
    public static final String INVALID_LOW_SURROGATE = "Invalid_Low_Surrogate";
    public static final String INVALID_OCTAL = "Invalid_Octal_Literal";
    public static final String INVALID_UNDERSCORE = "Invalid_Underscore";
    public static final String INVALID_UNICODE_ESCAPE = "Invalid_Unicode_Escape";
    static final int InternalTableSize = 6;
    public static final int LOW_SURROGATE_MAX_VALUE = 57343;
    public static final int LOW_SURROGATE_MIN_VALUE = 56320;
    public static final String NULL_SOURCE_STRING = "Null_Source_String";
    public static final int OptimizedLength = 7;
    public static final int RoundBracket = 0;
    public static final int SquareBracket = 1;
    public static final char TAG_POSTFIX = '$';
    public static final int TAG_POSTFIX_LENGTH = 1;
    public static final char[] TAG_PREFIX;
    public static final int TAG_PREFIX_LENGTH;
    static final int TableSize = 30;
    public static final String UNDERSCORES_IN_LITERALS_NOT_BELOW_17 = "Underscores_In_Literals_Not_Below_17";
    public static final String UNTERMINATED_COMMENT = "Unterminated_Comment";
    public static final String UNTERMINATED_STRING = "Unterminated_String";
    ConflictedParser activeParser;
    public final char[][][][] charArray_length;
    public boolean checkNonExternalizedStringLiterals;
    public boolean checkUninternedIdentityComparison;
    public int commentPtr;
    public int[] commentStarts;
    public int[] commentStops;
    public int[] commentTagStarts;
    public long complianceLevel;
    private boolean consumingEllipsisAnnotations;
    public boolean containsAssertKeyword;
    public char currentCharacter;
    public int currentPosition;
    public boolean diet;
    public int eofPosition;
    public int foundTaskCount;
    public char[][] foundTaskMessages;
    public int[][] foundTaskPositions;
    public char[][] foundTaskPriorities;
    public char[][] foundTaskTags;
    public int initialPosition;
    public boolean insideRecovery;
    public boolean isTaskCaseSensitive;
    protected int lastCommentLinePosition;
    protected int lastPosition;
    public int[] lineEnds;
    public int linePtr;
    int[] lookBack;
    int newEntry2;
    int newEntry3;
    int newEntry4;
    int newEntry5;
    int newEntry6;
    int nextToken;
    private NLSTag[] nlsTags;
    protected int nlsTagsPtr;
    public boolean recordLineSeparator;
    public boolean returnOnlyGreater;
    public boolean scanningFloatLiteral;
    public boolean skipComments;
    public char[] source;
    public long sourceLevel;
    public int startPosition;
    public char[][] taskPriorities;
    public char[][] taskTags;
    public boolean tokenizeComments;
    public boolean tokenizeWhiteSpace;
    public boolean unicodeAsBackSlash;
    public boolean useAssertAsAnIndentifier;
    public boolean useEnumAsAnIndentifier;
    public boolean[] validIdentityComparisonLines;
    private VanguardParser vanguardParser;
    private VanguardScanner vanguardScanner;
    public boolean wasAcr;
    public char[] withoutUnicodeBuffer;
    public int withoutUnicodePtr;
    private static final int[] EMPTY_LINE_ENDS = Util.EMPTY_INT_ARRAY;
    static final char[] charArray_a = {'a'};
    static final char[] charArray_b = {'b'};
    static final char[] charArray_c = {'c'};
    static final char[] charArray_d = {Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL};
    static final char[] charArray_e = {'e'};
    static final char[] charArray_f = {'f'};
    static final char[] charArray_g = {'g'};
    static final char[] charArray_h = {'h'};
    static final char[] charArray_i = {'i'};
    static final char[] charArray_j = {'j'};
    static final char[] charArray_k = {'k'};
    static final char[] charArray_l = {'l'};
    static final char[] charArray_m = {Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL};
    static final char[] charArray_n = {'n'};
    static final char[] charArray_o = {'o'};
    static final char[] charArray_p = {Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL};
    static final char[] charArray_q = {'q'};
    static final char[] charArray_r = {Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL};
    static final char[] charArray_s = {Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL};
    static final char[] charArray_t = {Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL};
    static final char[] charArray_u = {'u'};
    static final char[] charArray_v = {'v'};
    static final char[] charArray_w = {'w'};
    static final char[] charArray_x = {'x'};
    static final char[] charArray_y = {'y'};
    static final char[] charArray_z = {'z'};
    static final char[] initCharArray = new char[6];

    public static boolean isIdentifier(int i) {
        return i == 22;
    }

    public static boolean isKeyword(int i) {
        if (i == 17 || i == 114 || i == 67 || i == 68 || i == 111 || i == 112) {
            return true;
        }
        switch (i) {
            case 34:
            case 35:
            case 36:
                return true;
            default:
                switch (i) {
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                        return true;
                    default:
                        switch (i) {
                            case 51:
                            case 52:
                            case 53:
                            case 54:
                            case 55:
                            case 56:
                            case 57:
                            case 58:
                            case 59:
                                return true;
                            default:
                                switch (i) {
                                    case 71:
                                    case 72:
                                    case 73:
                                    case 74:
                                    case 75:
                                    case 76:
                                    case 77:
                                    case 78:
                                    case 79:
                                    case 80:
                                    case 81:
                                    case 82:
                                        return true;
                                    default:
                                        switch (i) {
                                            case 95:
                                            case 96:
                                            case 97:
                                            case 98:
                                            case 99:
                                            case 100:
                                            case 101:
                                            case 102:
                                            case 103:
                                            case 104:
                                            case 105:
                                            case 106:
                                            case 107:
                                            case 108:
                                            case 109:
                                                return true;
                                            default:
                                                return false;
                                        }
                                }
                        }
                }
        }
    }

    public static boolean isLiteral(int i) {
        switch (i) {
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
                return true;
            default:
                return false;
        }
    }

    protected boolean isAtAssistIdentifier() {
        return false;
    }

    protected boolean isFirstTag() {
        return true;
    }

    static {
        char[] charArray = "//$NON-NLS-".toCharArray();
        TAG_PREFIX = charArray;
        TAG_PREFIX_LENGTH = charArray.length;
        IDENTITY_COMPARISON_TAG = "//$IDENTITY-COMPARISON$".toCharArray();
    }

    public Scanner() {
        this(false, false, false, ClassFileConstants.JDK1_3, null, null, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [int] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v2, types: [int] */
    /* JADX WARN: Type inference failed for: r3v4, types: [int] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r4v2, types: [int[]] */
    /* JADX WARN: Type inference failed for: r8v3, types: [int[]] */
    public Scanner(boolean z, boolean z2, boolean z3, long j, long j2, char[][] cArr, char[][] cArr2, boolean z4) {
        char[][] cArr3;
        int i;
        ?? r3;
        char[][] cArr4 = cArr2;
        ?? r4 = 0;
        this.useAssertAsAnIndentifier = false;
        this.containsAssertKeyword = false;
        this.useEnumAsAnIndentifier = false;
        this.recordLineSeparator = false;
        this.skipComments = false;
        this.tokenizeComments = false;
        this.tokenizeWhiteSpace = false;
        this.unicodeAsBackSlash = false;
        this.scanningFloatLiteral = false;
        char c = 30;
        this.commentStops = new int[30];
        this.commentStarts = new int[30];
        this.commentTagStarts = new int[30];
        this.commentPtr = -1;
        this.lastCommentLinePosition = -1;
        ConflictedParser conflictedParser = null;
        this.foundTaskTags = null;
        this.foundTaskPriorities = null;
        this.foundTaskCount = 0;
        this.taskTags = null;
        this.taskPriorities = null;
        this.isTaskCaseSensitive = true;
        this.diet = false;
        this.lineEnds = new int[250];
        this.linePtr = -1;
        this.wasAcr = false;
        this.charArray_length = (char[][][][]) Array.newInstance((Class<?>) char[].class, 7, 30, 6);
        this.nlsTags = null;
        this.returnOnlyGreater = false;
        int i2 = 0;
        while (i2 < 6) {
            while (r3 < c) {
                int i3 = 0;
                while (i3 < 6) {
                    r3 = r4;
                    this.charArray_length[i2][r3][i3] = initCharArray;
                    i3++;
                    conflictedParser = null;
                }
                r3 = r4;
                c = 30;
                r3++;
            }
            r3 = r4;
            i2++;
            r4 = 0;
        }
        this.newEntry2 = r4;
        this.newEntry3 = r4;
        this.newEntry4 = r4;
        this.newEntry5 = r4;
        this.newEntry6 = r4;
        this.insideRecovery = r4;
        ?? r5 = new int[2];
        this.lookBack = r5;
        this.activeParser = conflictedParser;
        this.eofPosition = Integer.MAX_VALUE;
        this.tokenizeComments = z;
        this.tokenizeWhiteSpace = z2;
        this.sourceLevel = j;
        this.nextToken = r4;
        r5[1] = r4;
        r5[r4] = r4;
        this.consumingEllipsisAnnotations = r4;
        this.complianceLevel = j2;
        this.checkNonExternalizedStringLiterals = z3;
        if (cArr != null) {
            int length = cArr.length;
            if (cArr4 != null) {
                int length2 = cArr4.length;
                if (length2 == length) {
                    cArr3 = cArr;
                    i = length;
                } else if (length2 > length) {
                    char[][] cArr5 = new char[length][];
                    System.arraycopy(cArr4, r4, cArr5, r4, length);
                    i = length;
                    cArr4 = cArr5;
                    cArr3 = cArr;
                } else {
                    char[][] cArr6 = new char[length2][];
                    System.arraycopy(cArr, r4, cArr6, r4, length2);
                    i = length2;
                    cArr3 = cArr6;
                }
                ?? r8 = new int[i];
                for (?? r1 = r4; r1 < i; r1++) {
                    r8[r1] = r1;
                }
                Util.reverseQuickSort(cArr3, r4, i - 1, r8);
                char[][] cArr7 = new char[i][];
                for (?? r6 = r4; r6 < i; r6++) {
                    cArr7[r6] = cArr4[r8[r6]];
                }
                this.taskPriorities = cArr7;
            } else {
                Util.reverseQuickSort(cArr, r4, length - 1);
                cArr3 = cArr;
            }
            this.taskTags = cArr3;
            this.isTaskCaseSensitive = z4;
        }
    }

    public Scanner(boolean z, boolean z2, boolean z3, long j, char[][] cArr, char[][] cArr2, boolean z4) {
        this(z, z2, z3, j, j, cArr, cArr2, z4);
    }

    public final boolean atEnd() {
        return this.eofPosition <= this.currentPosition;
    }

    /* JADX WARN: Code duplicated, block: B:107:0x0182 A[PHI: r6
      0x0182: PHI (r6v5 int) = (r6v4 int), (r6v11 int) binds: [B:97:0x016e, B:104:0x017c] A[DONT_GENERATE, DONT_INLINE]] */
    public void checkTaskTag(int i, int i2) throws InvalidInputException {
        int i3;
        int i4;
        char c;
        char c2;
        char[] cArr = this.source;
        int i5 = this.foundTaskCount;
        if (i5 <= 0 || this.foundTaskPositions[i5 - 1][0] < i) {
            char c3 = cArr[i + 1];
            int i6 = i + 2;
            while (i6 < i2 && i6 < this.eofPosition) {
                if (c3 != '@') {
                    int i7 = 0;
                    while (true) {
                        char[][] cArr2 = this.taskTags;
                        if (i7 >= cArr2.length) {
                            break;
                        }
                        char[] cArr3 = cArr2[i7];
                        int length = cArr3.length;
                        if (length != 0 && (!ScannerHelper.isJavaIdentifierStart(this.complianceLevel, cArr3[0]) || !ScannerHelper.isJavaIdentifierPart(this.complianceLevel, c3))) {
                            int i8 = 0;
                            while (true) {
                                if (i8 < length) {
                                    int i9 = i6 + i8;
                                    if (i9 >= this.eofPosition || i9 >= i2 || ((c = cArr[i9]) != (c2 = cArr3[i8]) && (this.isTaskCaseSensitive || ScannerHelper.toLowerCase(c) != ScannerHelper.toLowerCase(c2)))) {
                                        break;
                                    } else {
                                        i8++;
                                    }
                                } else {
                                    int i10 = i6 + length;
                                    if (i10 >= i2 || !ScannerHelper.isJavaIdentifierPart(this.complianceLevel, cArr[i10 - 1]) || !ScannerHelper.isJavaIdentifierPart(this.complianceLevel, cArr[i10])) {
                                        char[][] cArr4 = this.foundTaskTags;
                                        if (cArr4 == null) {
                                            this.foundTaskTags = new char[5][];
                                            this.foundTaskMessages = new char[5][];
                                            this.foundTaskPriorities = new char[5][];
                                            this.foundTaskPositions = new int[5][];
                                        } else {
                                            int i11 = this.foundTaskCount;
                                            if (i11 == cArr4.length) {
                                                char[][] cArr5 = new char[i11 * 2][];
                                                this.foundTaskTags = cArr5;
                                                System.arraycopy(cArr4, 0, cArr5, 0, i11);
                                                char[][] cArr6 = this.foundTaskMessages;
                                                int i12 = this.foundTaskCount;
                                                char[][] cArr7 = new char[i12 * 2][];
                                                this.foundTaskMessages = cArr7;
                                                System.arraycopy(cArr6, 0, cArr7, 0, i12);
                                                char[][] cArr8 = this.foundTaskPriorities;
                                                int i13 = this.foundTaskCount;
                                                char[][] cArr9 = new char[i13 * 2][];
                                                this.foundTaskPriorities = cArr9;
                                                System.arraycopy(cArr8, 0, cArr9, 0, i13);
                                                int[][] iArr = this.foundTaskPositions;
                                                int i14 = this.foundTaskCount;
                                                int[][] iArr2 = new int[i14 * 2][];
                                                this.foundTaskPositions = iArr2;
                                                System.arraycopy(iArr, 0, iArr2, 0, i14);
                                            }
                                        }
                                        char[][] cArr10 = this.taskPriorities;
                                        char[] cArr11 = (cArr10 == null || i7 >= cArr10.length) ? null : cArr10[i7];
                                        char[][] cArr12 = this.foundTaskTags;
                                        int i15 = this.foundTaskCount;
                                        cArr12[i15] = cArr3;
                                        this.foundTaskPriorities[i15] = cArr11;
                                        this.foundTaskPositions[i15] = new int[]{i6, i10 - 1};
                                        this.foundTaskMessages[i15] = CharOperation.NO_CHAR;
                                        this.foundTaskCount++;
                                        i6 += length - 1;
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        i7++;
                    }
                }
                c3 = cArr[i6];
                i6++;
            }
            int i16 = i5;
            boolean z = false;
            while (true) {
                i3 = this.foundTaskCount;
                if (i16 >= i3) {
                    break;
                }
                int[][] iArr3 = this.foundTaskPositions;
                int length2 = iArr3[i16][0] + this.foundTaskTags[i16].length;
                int i17 = i16 + 1;
                int i18 = i17 < i3 ? iArr3[i17][0] - 1 : i2 - 1;
                if (i18 < length2) {
                    i18 = length2;
                }
                int i19 = length2;
                while (true) {
                    if (i19 >= i18) {
                        i4 = -1;
                        break;
                    }
                    char c4 = cArr[i19];
                    if (c4 == '\n' || c4 == '\r') {
                        i4 = i19 - 1;
                        break;
                    }
                    i19++;
                }
                if (i4 == -1) {
                    for (int i20 = i18; i20 > length2; i20--) {
                        if (cArr[i20] == '*') {
                            i4 = i20 - 1;
                            break;
                        }
                    }
                    if (i4 != -1) {
                        i18 = i4;
                    }
                } else {
                    i18 = i4;
                }
                if (length2 == i18) {
                    z = true;
                } else {
                    while (CharOperation.isWhitespace(cArr[i18]) && length2 <= i18) {
                        i18--;
                    }
                    this.foundTaskPositions[i16][1] = i18;
                    int i21 = (i18 - length2) + 1;
                    char[] cArr13 = new char[i21];
                    System.arraycopy(cArr, length2, cArr13, 0, i21);
                    this.foundTaskMessages[i16] = cArr13;
                }
                i16 = i17;
            }
            if (z) {
                while (i5 < i3) {
                    if (this.foundTaskMessages[i5].length == 0) {
                        for (int i22 = i5 + 1; i22 < i3; i22++) {
                            char[][] cArr14 = this.foundTaskMessages;
                            char[] cArr15 = cArr14[i22];
                            if (cArr15.length != 0) {
                                cArr14[i5] = cArr15;
                                int[][] iArr4 = this.foundTaskPositions;
                                iArr4[i5][1] = iArr4[i22][1];
                                break;
                            }
                        }
                    }
                    i5++;
                }
            }
        }
    }

    public char[] getCurrentIdentifierSource() {
        int i = this.withoutUnicodePtr;
        if (i != 0) {
            char[] cArr = new char[i];
            System.arraycopy(this.withoutUnicodeBuffer, 1, cArr, 0, i);
            return cArr;
        }
        int i2 = this.currentPosition;
        int i3 = this.startPosition;
        int i4 = i2 - i3;
        if (i4 == this.eofPosition) {
            return this.source;
        }
        switch (i4) {
            case 1:
                return optimizedCurrentTokenSource1();
            case 2:
                return optimizedCurrentTokenSource2();
            case 3:
                return optimizedCurrentTokenSource3();
            case 4:
                return optimizedCurrentTokenSource4();
            case 5:
                return optimizedCurrentTokenSource5();
            case 6:
                return optimizedCurrentTokenSource6();
            default:
                char[] cArr2 = new char[i4];
                System.arraycopy(this.source, i3, cArr2, 0, i4);
                return cArr2;
        }
    }

    public int getCurrentTokenEndPosition() {
        return this.currentPosition - 1;
    }

    public char[] getCurrentTokenSource() {
        int i = this.withoutUnicodePtr;
        if (i != 0) {
            char[] cArr = new char[i];
            System.arraycopy(this.withoutUnicodeBuffer, 1, cArr, 0, i);
            return cArr;
        }
        char[] cArr2 = this.source;
        int i2 = this.startPosition;
        int i3 = this.currentPosition - i2;
        char[] cArr3 = new char[i3];
        System.arraycopy(cArr2, i2, cArr3, 0, i3);
        return cArr3;
    }

    public final String getCurrentTokenString() {
        if (this.withoutUnicodePtr != 0) {
            return new String(this.withoutUnicodeBuffer, 1, this.withoutUnicodePtr);
        }
        char[] cArr = this.source;
        int i = this.startPosition;
        return new String(cArr, i, this.currentPosition - i);
    }

    public char[] getCurrentTokenSourceString() {
        int i = this.withoutUnicodePtr;
        if (i != 0) {
            char[] cArr = new char[i - 2];
            System.arraycopy(this.withoutUnicodeBuffer, 2, cArr, 0, i - 2);
            return cArr;
        }
        char[] cArr2 = this.source;
        int i2 = this.startPosition;
        int i3 = (this.currentPosition - i2) - 2;
        char[] cArr3 = new char[i3];
        System.arraycopy(cArr2, i2 + 1, cArr3, 0, i3);
        return cArr3;
    }

    public final String getCurrentStringLiteral() {
        if (this.withoutUnicodePtr != 0) {
            return new String(this.withoutUnicodeBuffer, 2, this.withoutUnicodePtr - 2);
        }
        char[] cArr = this.source;
        int i = this.startPosition;
        return new String(cArr, i + 1, (this.currentPosition - i) - 2);
    }

    public final char[] getRawTokenSource() {
        int i = this.currentPosition;
        int i2 = this.startPosition;
        int i3 = i - i2;
        char[] cArr = new char[i3];
        System.arraycopy(this.source, i2, cArr, 0, i3);
        return cArr;
    }

    public final char[] getRawTokenSourceEnd() {
        int i = this.eofPosition;
        int i2 = this.currentPosition;
        int i3 = (i - i2) - 1;
        char[] cArr = new char[i3];
        System.arraycopy(this.source, i2, cArr, 0, i3);
        return cArr;
    }

    public int getCurrentTokenStartPosition() {
        return this.startPosition;
    }

    public final int getLineEnd(int i) {
        int[] iArr = this.lineEnds;
        if (iArr == null || this.linePtr == -1 || i > iArr.length + 1 || i <= 0) {
            return -1;
        }
        if (i == iArr.length + 1) {
            return this.eofPosition;
        }
        return iArr[i - 1];
    }

    public final int[] getLineEnds() {
        int i = this.linePtr;
        if (i == -1) {
            return EMPTY_LINE_ENDS;
        }
        int[] iArr = new int[i + 1];
        System.arraycopy(this.lineEnds, 0, iArr, 0, i + 1);
        return iArr;
    }

    public final int getLineStart(int i) {
        int[] iArr = this.lineEnds;
        if (iArr == null || this.linePtr == -1 || i > iArr.length + 1 || i <= 0) {
            return -1;
        }
        if (i == 1) {
            return this.initialPosition;
        }
        return iArr[i - 2] + 1;
    }

    public final int getNextChar() {
        try {
            char[] cArr = this.source;
            int i = this.currentPosition;
            int i2 = i + 1;
            this.currentPosition = i2;
            char c = cArr[i];
            this.currentCharacter = c;
            if (c == '\\' && cArr[i2] == 'u') {
                getNextUnicodeChar();
            } else {
                this.unicodeAsBackSlash = false;
                if (this.withoutUnicodePtr != 0) {
                    unicodeStore();
                }
            }
            return this.currentCharacter;
        } catch (IndexOutOfBoundsException | InvalidInputException unused) {
            return -1;
        }
    }

    public final int getNextCharWithBoundChecks() {
        int i = this.currentPosition;
        int i2 = this.eofPosition;
        if (i >= i2) {
            return -1;
        }
        char[] cArr = this.source;
        int i3 = i + 1;
        this.currentPosition = i3;
        char c = cArr[i];
        this.currentCharacter = c;
        if (i3 >= i2) {
            this.unicodeAsBackSlash = false;
            if (this.withoutUnicodePtr != 0) {
                unicodeStore();
            }
            return this.currentCharacter;
        }
        if (c == '\\' && cArr[i3] == 'u') {
            try {
                getNextUnicodeChar();
            } catch (InvalidInputException unused) {
                return -1;
            }
        } else {
            this.unicodeAsBackSlash = false;
            if (this.withoutUnicodePtr != 0) {
                unicodeStore();
            }
        }
        return this.currentCharacter;
    }

    public final boolean getNextChar(char c) {
        int i = this.currentPosition;
        if (i >= this.eofPosition) {
            this.unicodeAsBackSlash = false;
            return false;
        }
        try {
            char[] cArr = this.source;
            int i2 = i + 1;
            this.currentPosition = i2;
            char c2 = cArr[i];
            this.currentCharacter = c2;
            if (c2 == '\\' && cArr[i2] == 'u') {
                getNextUnicodeChar();
                if (this.currentCharacter == c) {
                    return true;
                }
                this.currentPosition = i;
                this.withoutUnicodePtr--;
                return false;
            }
            if (c2 != c) {
                this.currentPosition = i;
                return false;
            }
            this.unicodeAsBackSlash = false;
            if (this.withoutUnicodePtr != 0) {
                unicodeStore();
            }
            return true;
        } catch (IndexOutOfBoundsException unused) {
            this.unicodeAsBackSlash = false;
            this.currentPosition = i;
            return false;
        } catch (InvalidInputException unused2) {
            this.unicodeAsBackSlash = false;
            this.currentPosition = i;
            return false;
        }
    }

    public final int getNextChar(char c, char c2) {
        int i = this.currentPosition;
        if (i >= this.eofPosition) {
            return -1;
        }
        try {
            char[] cArr = this.source;
            int i2 = i + 1;
            this.currentPosition = i2;
            char c3 = cArr[i];
            this.currentCharacter = c3;
            int i3 = 0;
            if (c3 == '\\' && cArr[i2] == 'u') {
                getNextUnicodeChar();
                char c4 = this.currentCharacter;
                if (c4 == c) {
                    return 0;
                }
                if (c4 == c2) {
                    return 1;
                }
                this.currentPosition = i;
                this.withoutUnicodePtr--;
                return -1;
            }
            if (c3 != c) {
                if (c3 != c2) {
                    this.currentPosition = i;
                    return -1;
                }
                i3 = 1;
            }
            if (this.withoutUnicodePtr != 0) {
                unicodeStore();
            }
            return i3;
        } catch (IndexOutOfBoundsException unused) {
            this.currentPosition = i;
            return -1;
        } catch (InvalidInputException unused2) {
            this.currentPosition = i;
            return -1;
        }
    }

    private final void consumeDigits(int i) throws InvalidInputException {
        consumeDigits(i, false);
    }

    private final void consumeDigits(int i, boolean z) throws InvalidInputException {
        int iConsumeDigits0 = consumeDigits0(i, 1, 2, z);
        if (iConsumeDigits0 == 1) {
            if (this.sourceLevel < ClassFileConstants.JDK1_7) {
                throw new InvalidInputException(UNDERSCORES_IN_LITERALS_NOT_BELOW_17);
            }
        } else {
            if (iConsumeDigits0 != 2) {
                return;
            }
            if (this.sourceLevel < ClassFileConstants.JDK1_7) {
                throw new InvalidInputException(UNDERSCORES_IN_LITERALS_NOT_BELOW_17);
            }
            throw new InvalidInputException(INVALID_UNDERSCORE);
        }
    }

    private final int consumeDigits0(int i, int i2, int i3, boolean z) throws InvalidInputException {
        int i4;
        if (!getNextChar('_')) {
            i4 = 0;
        } else {
            if (z) {
                return i3;
            }
            while (getNextChar('_')) {
            }
            i4 = i2;
        }
        if (!getNextCharAsDigit(i)) {
            return i4 == i2 ? i3 : i4;
        }
        while (getNextCharAsDigit(i)) {
        }
        int iConsumeDigits0 = consumeDigits0(i, i2, i3, false);
        return iConsumeDigits0 == 0 ? i4 : iConsumeDigits0;
    }

    public final boolean getNextCharAsDigit() throws InvalidInputException {
        int i = this.currentPosition;
        if (i >= this.eofPosition) {
            return false;
        }
        try {
            char[] cArr = this.source;
            int i2 = i + 1;
            this.currentPosition = i2;
            char c = cArr[i];
            this.currentCharacter = c;
            if (c == '\\' && cArr[i2] == 'u') {
                getNextUnicodeChar();
                if (ScannerHelper.isDigit(this.currentCharacter)) {
                    return true;
                }
                this.currentPosition = i;
                this.withoutUnicodePtr--;
                return false;
            }
            if (!ScannerHelper.isDigit(c)) {
                this.currentPosition = i;
                return false;
            }
            if (this.withoutUnicodePtr != 0) {
                unicodeStore();
            }
            return true;
        } catch (IndexOutOfBoundsException unused) {
            this.currentPosition = i;
            return false;
        } catch (InvalidInputException unused2) {
            this.currentPosition = i;
            return false;
        }
    }

    public final boolean getNextCharAsDigit(int i) {
        int i2 = this.currentPosition;
        if (i2 >= this.eofPosition) {
            return false;
        }
        try {
            char[] cArr = this.source;
            int i3 = i2 + 1;
            this.currentPosition = i3;
            char c = cArr[i2];
            this.currentCharacter = c;
            if (c == '\\' && cArr[i3] == 'u') {
                getNextUnicodeChar();
                if (ScannerHelper.digit(this.currentCharacter, i) != -1) {
                    return true;
                }
                this.currentPosition = i2;
                this.withoutUnicodePtr--;
                return false;
            }
            if (ScannerHelper.digit(c, i) == -1) {
                this.currentPosition = i2;
                return false;
            }
            if (this.withoutUnicodePtr != 0) {
                unicodeStore();
            }
            return true;
        } catch (IndexOutOfBoundsException unused) {
            this.currentPosition = i2;
            return false;
        } catch (InvalidInputException unused2) {
            this.currentPosition = i2;
            return false;
        }
    }

    public boolean getNextCharAsJavaIdentifierPartWithBoundCheck() {
        boolean z;
        boolean zIsJavaIdentifierPart;
        int i = this.currentPosition;
        int i2 = this.eofPosition;
        if (i >= i2) {
            return false;
        }
        int i3 = this.withoutUnicodePtr;
        try {
            char[] cArr = this.source;
            int i4 = i + 1;
            this.currentPosition = i4;
            char c = cArr[i];
            this.currentCharacter = c;
            if (i4 < i2 && c == '\\' && cArr[i4] == 'u') {
                getNextUnicodeChar();
                z = true;
            } else {
                z = false;
            }
            char c2 = this.currentCharacter;
            if (c2 >= 55296 && c2 <= 56319) {
                if (this.complianceLevel < ClassFileConstants.JDK1_5) {
                    this.currentPosition = i;
                    this.withoutUnicodePtr = i3;
                    return false;
                }
                char nextCharWithBoundChecks = (char) getNextCharWithBoundChecks();
                if (nextCharWithBoundChecks >= 56320 && nextCharWithBoundChecks <= 57343) {
                    zIsJavaIdentifierPart = ScannerHelper.isJavaIdentifierPart(this.complianceLevel, c2, nextCharWithBoundChecks);
                }
                this.currentPosition = i;
                this.withoutUnicodePtr = i3;
                return false;
            }
            if (c2 >= 56320 && c2 <= 57343) {
                this.currentPosition = i;
                this.withoutUnicodePtr = i3;
                return false;
            }
            zIsJavaIdentifierPart = ScannerHelper.isJavaIdentifierPart(this.complianceLevel, c2);
            if (z) {
                if (zIsJavaIdentifierPart) {
                    return true;
                }
                this.currentPosition = i;
                this.withoutUnicodePtr = i3;
                return false;
            }
            if (!zIsJavaIdentifierPart) {
                this.currentPosition = i;
                return false;
            }
            if (this.withoutUnicodePtr != 0) {
                unicodeStore();
            }
            return true;
        } catch (InvalidInputException unused) {
            this.currentPosition = i;
            this.withoutUnicodePtr = i3;
            return false;
        }
    }

    public boolean getNextCharAsJavaIdentifierPart() {
        boolean z;
        boolean zIsJavaIdentifierPart;
        int i = this.currentPosition;
        if (i >= this.eofPosition) {
            return false;
        }
        int i2 = this.withoutUnicodePtr;
        try {
            char[] cArr = this.source;
            int i3 = i + 1;
            this.currentPosition = i3;
            char c = cArr[i];
            this.currentCharacter = c;
            if (c == '\\' && cArr[i3] == 'u') {
                getNextUnicodeChar();
                z = true;
            } else {
                z = false;
            }
            char c2 = this.currentCharacter;
            if (c2 >= 55296 && c2 <= 56319) {
                if (this.complianceLevel < ClassFileConstants.JDK1_5) {
                    this.currentPosition = i;
                    this.withoutUnicodePtr = i2;
                    return false;
                }
                char nextChar = (char) getNextChar();
                if (nextChar >= 56320 && nextChar <= 57343) {
                    zIsJavaIdentifierPart = ScannerHelper.isJavaIdentifierPart(this.complianceLevel, c2, nextChar);
                }
                this.currentPosition = i;
                this.withoutUnicodePtr = i2;
                return false;
            }
            if (c2 >= 56320 && c2 <= 57343) {
                this.currentPosition = i;
                this.withoutUnicodePtr = i2;
                return false;
            }
            zIsJavaIdentifierPart = ScannerHelper.isJavaIdentifierPart(this.complianceLevel, c2);
            if (z) {
                if (zIsJavaIdentifierPart) {
                    return true;
                }
                this.currentPosition = i;
                this.withoutUnicodePtr = i2;
                return false;
            }
            if (!zIsJavaIdentifierPart) {
                this.currentPosition = i;
                return false;
            }
            if (this.withoutUnicodePtr != 0) {
                unicodeStore();
            }
            return true;
        } catch (IndexOutOfBoundsException unused) {
            this.currentPosition = i;
            this.withoutUnicodePtr = i2;
            return false;
        } catch (InvalidInputException unused2) {
            this.currentPosition = i;
            this.withoutUnicodePtr = i2;
            return false;
        }
    }

    public int scanIdentifier() throws InvalidInputException {
        int i;
        boolean z;
        int i2;
        boolean zJumpOverUnicodeWhiteSpace;
        boolean zIsJavaIdentifierStart;
        this.withoutUnicodePtr = 0;
        int i3 = this.currentPosition;
        boolean z2 = false;
        do {
            i = this.withoutUnicodePtr;
            int i4 = this.currentPosition;
            this.startPosition = i4;
            int i5 = this.eofPosition;
            if (i4 < i5) {
                char[] cArr = this.source;
                int i6 = i4 + 1;
                this.currentPosition = i6;
                char c = cArr[i4];
                this.currentCharacter = c;
                z = i6 < i5 && c == '\\' && cArr[i6] == 'u';
                if (z) {
                    zJumpOverUnicodeWhiteSpace = jumpOverUnicodeWhiteSpace();
                    i2 = this.currentPosition - i4;
                } else {
                    i2 = i6 - i4;
                    zJumpOverUnicodeWhiteSpace = c == '\t' || c == '\n' || c == '\f' || c == '\r' || c == ' ';
                }
                if (zJumpOverUnicodeWhiteSpace) {
                    z2 = true;
                }
            } else {
                if (!this.tokenizeWhiteSpace || i3 == i4 - 1) {
                    return 60;
                }
                this.currentPosition = i4 - 1;
                this.startPosition = i3;
                return 1000;
            }
        } while (zJumpOverUnicodeWhiteSpace);
        if (z2) {
            if (this.tokenizeWhiteSpace) {
                this.currentPosition -= i2;
                this.startPosition = i3;
                if (z) {
                    this.withoutUnicodePtr = i;
                }
                return 1000;
            }
            if (z) {
                this.withoutUnicodePtr = 0;
                unicodeStore();
            } else {
                this.withoutUnicodePtr = 0;
            }
        }
        char c2 = this.currentCharacter;
        if (c2 < 128) {
            if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c2] & 64) != 0) {
                return scanIdentifierOrKeywordWithBoundCheck();
            }
            return 118;
        }
        if (c2 < 55296 || c2 > 56319) {
            if (c2 >= 56320 && c2 <= 57343) {
                if (this.complianceLevel < ClassFileConstants.JDK1_5) {
                    throw new InvalidInputException(INVALID_UNICODE_ESCAPE);
                }
                throw new InvalidInputException(INVALID_HIGH_SURROGATE);
            }
            zIsJavaIdentifierStart = ScannerHelper.isJavaIdentifierStart(this.complianceLevel, c2);
        } else {
            if (this.complianceLevel < ClassFileConstants.JDK1_5) {
                throw new InvalidInputException(INVALID_UNICODE_ESCAPE);
            }
            char nextCharWithBoundChecks = (char) getNextCharWithBoundChecks();
            if (nextCharWithBoundChecks < 56320 || nextCharWithBoundChecks > 57343) {
                throw new InvalidInputException(INVALID_LOW_SURROGATE);
            }
            zIsJavaIdentifierStart = ScannerHelper.isJavaIdentifierStart(this.complianceLevel, c2, nextCharWithBoundChecks);
        }
        if (zIsJavaIdentifierStart) {
            return scanIdentifierOrKeywordWithBoundCheck();
        }
        return 118;
    }

    public void ungetToken(int i) {
        if (this.nextToken != 0) {
            throw new ArrayIndexOutOfBoundsException("Single cell array overflow");
        }
        this.nextToken = i;
    }

    public int getNextToken() throws InvalidInputException {
        int i = this.nextToken;
        if (i != 0) {
            this.nextToken = 0;
            return i;
        }
        int nextToken0 = getNextToken0();
        if (this.activeParser == null) {
            return nextToken0;
        }
        if (nextToken0 == 24 || nextToken0 == 11 || nextToken0 == 37) {
            nextToken0 = disambiguatedToken(nextToken0);
        } else if (nextToken0 == 113) {
            this.consumingEllipsisAnnotations = false;
        }
        int[] iArr = this.lookBack;
        iArr[0] = iArr[1];
        iArr[1] = nextToken0;
        return nextToken0;
    }

    /* JADX WARN: Code duplicated, block: B:526:0x05f4 A[Catch: InvalidInputException -> 0x06d7, IndexOutOfBoundsException -> 0x0707, TryCatch #8 {IndexOutOfBoundsException -> 0x0707, InvalidInputException -> 0x06d7, blocks: (B:512:0x05c8, B:514:0x05da, B:516:0x05e0, B:522:0x05ed, B:526:0x05f4, B:533:0x0604, B:535:0x0608, B:537:0x061e, B:539:0x0624, B:544:0x064a, B:546:0x0651, B:540:0x062e, B:542:0x0632, B:543:0x0638, B:547:0x0654, B:549:0x0666, B:551:0x066c, B:552:0x0671, B:554:0x0675, B:559:0x0681, B:561:0x0687, B:563:0x0695, B:565:0x069b, B:569:0x06a5, B:572:0x06aa, B:573:0x06b1, B:575:0x06b5, B:576:0x06b8, B:577:0x06bf, B:578:0x06c0, B:580:0x06c7, B:581:0x06ce, B:579:0x06c3, B:582:0x06cf, B:583:0x06d6, B:518:0x05e5, B:520:0x05e9), top: B:634:0x05c8, outer: #2 }] */
    /* JADX WARN: Code duplicated, block: B:528:0x05fa  */
    /* JADX WARN: Code duplicated, block: B:529:0x05fc A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:533:0x0604 A[Catch: InvalidInputException -> 0x06d7, IndexOutOfBoundsException -> 0x0707, TryCatch #8 {IndexOutOfBoundsException -> 0x0707, InvalidInputException -> 0x06d7, blocks: (B:512:0x05c8, B:514:0x05da, B:516:0x05e0, B:522:0x05ed, B:526:0x05f4, B:533:0x0604, B:535:0x0608, B:537:0x061e, B:539:0x0624, B:544:0x064a, B:546:0x0651, B:540:0x062e, B:542:0x0632, B:543:0x0638, B:547:0x0654, B:549:0x0666, B:551:0x066c, B:552:0x0671, B:554:0x0675, B:559:0x0681, B:561:0x0687, B:563:0x0695, B:565:0x069b, B:569:0x06a5, B:572:0x06aa, B:573:0x06b1, B:575:0x06b5, B:576:0x06b8, B:577:0x06bf, B:578:0x06c0, B:580:0x06c7, B:581:0x06ce, B:579:0x06c3, B:582:0x06cf, B:583:0x06d6, B:518:0x05e5, B:520:0x05e9), top: B:634:0x05c8, outer: #2 }] */
    /* JADX WARN: Code duplicated, block: B:535:0x0608 A[Catch: InvalidInputException -> 0x06d7, IndexOutOfBoundsException -> 0x0707, TryCatch #8 {IndexOutOfBoundsException -> 0x0707, InvalidInputException -> 0x06d7, blocks: (B:512:0x05c8, B:514:0x05da, B:516:0x05e0, B:522:0x05ed, B:526:0x05f4, B:533:0x0604, B:535:0x0608, B:537:0x061e, B:539:0x0624, B:544:0x064a, B:546:0x0651, B:540:0x062e, B:542:0x0632, B:543:0x0638, B:547:0x0654, B:549:0x0666, B:551:0x066c, B:552:0x0671, B:554:0x0675, B:559:0x0681, B:561:0x0687, B:563:0x0695, B:565:0x069b, B:569:0x06a5, B:572:0x06aa, B:573:0x06b1, B:575:0x06b5, B:576:0x06b8, B:577:0x06bf, B:578:0x06c0, B:580:0x06c7, B:581:0x06ce, B:579:0x06c3, B:582:0x06cf, B:583:0x06d6, B:518:0x05e5, B:520:0x05e9), top: B:634:0x05c8, outer: #2 }] */
    /* JADX WARN: Code duplicated, block: B:537:0x061e A[Catch: InvalidInputException -> 0x06d7, IndexOutOfBoundsException -> 0x0707, TryCatch #8 {IndexOutOfBoundsException -> 0x0707, InvalidInputException -> 0x06d7, blocks: (B:512:0x05c8, B:514:0x05da, B:516:0x05e0, B:522:0x05ed, B:526:0x05f4, B:533:0x0604, B:535:0x0608, B:537:0x061e, B:539:0x0624, B:544:0x064a, B:546:0x0651, B:540:0x062e, B:542:0x0632, B:543:0x0638, B:547:0x0654, B:549:0x0666, B:551:0x066c, B:552:0x0671, B:554:0x0675, B:559:0x0681, B:561:0x0687, B:563:0x0695, B:565:0x069b, B:569:0x06a5, B:572:0x06aa, B:573:0x06b1, B:575:0x06b5, B:576:0x06b8, B:577:0x06bf, B:578:0x06c0, B:580:0x06c7, B:581:0x06ce, B:579:0x06c3, B:582:0x06cf, B:583:0x06d6, B:518:0x05e5, B:520:0x05e9), top: B:634:0x05c8, outer: #2 }] */
    /* JADX WARN: Code duplicated, block: B:540:0x062e A[Catch: InvalidInputException -> 0x06d7, IndexOutOfBoundsException -> 0x0707, TryCatch #8 {IndexOutOfBoundsException -> 0x0707, InvalidInputException -> 0x06d7, blocks: (B:512:0x05c8, B:514:0x05da, B:516:0x05e0, B:522:0x05ed, B:526:0x05f4, B:533:0x0604, B:535:0x0608, B:537:0x061e, B:539:0x0624, B:544:0x064a, B:546:0x0651, B:540:0x062e, B:542:0x0632, B:543:0x0638, B:547:0x0654, B:549:0x0666, B:551:0x066c, B:552:0x0671, B:554:0x0675, B:559:0x0681, B:561:0x0687, B:563:0x0695, B:565:0x069b, B:569:0x06a5, B:572:0x06aa, B:573:0x06b1, B:575:0x06b5, B:576:0x06b8, B:577:0x06bf, B:578:0x06c0, B:580:0x06c7, B:581:0x06ce, B:579:0x06c3, B:582:0x06cf, B:583:0x06d6, B:518:0x05e5, B:520:0x05e9), top: B:634:0x05c8, outer: #2 }] */
    /* JADX WARN: Code duplicated, block: B:542:0x0632 A[Catch: InvalidInputException -> 0x06d7, IndexOutOfBoundsException -> 0x0707, TryCatch #8 {IndexOutOfBoundsException -> 0x0707, InvalidInputException -> 0x06d7, blocks: (B:512:0x05c8, B:514:0x05da, B:516:0x05e0, B:522:0x05ed, B:526:0x05f4, B:533:0x0604, B:535:0x0608, B:537:0x061e, B:539:0x0624, B:544:0x064a, B:546:0x0651, B:540:0x062e, B:542:0x0632, B:543:0x0638, B:547:0x0654, B:549:0x0666, B:551:0x066c, B:552:0x0671, B:554:0x0675, B:559:0x0681, B:561:0x0687, B:563:0x0695, B:565:0x069b, B:569:0x06a5, B:572:0x06aa, B:573:0x06b1, B:575:0x06b5, B:576:0x06b8, B:577:0x06bf, B:578:0x06c0, B:580:0x06c7, B:581:0x06ce, B:579:0x06c3, B:582:0x06cf, B:583:0x06d6, B:518:0x05e5, B:520:0x05e9), top: B:634:0x05c8, outer: #2 }] */
    /* JADX WARN: Code duplicated, block: B:546:0x0651 A[Catch: InvalidInputException -> 0x06d7, IndexOutOfBoundsException -> 0x0707, TryCatch #8 {IndexOutOfBoundsException -> 0x0707, InvalidInputException -> 0x06d7, blocks: (B:512:0x05c8, B:514:0x05da, B:516:0x05e0, B:522:0x05ed, B:526:0x05f4, B:533:0x0604, B:535:0x0608, B:537:0x061e, B:539:0x0624, B:544:0x064a, B:546:0x0651, B:540:0x062e, B:542:0x0632, B:543:0x0638, B:547:0x0654, B:549:0x0666, B:551:0x066c, B:552:0x0671, B:554:0x0675, B:559:0x0681, B:561:0x0687, B:563:0x0695, B:565:0x069b, B:569:0x06a5, B:572:0x06aa, B:573:0x06b1, B:575:0x06b5, B:576:0x06b8, B:577:0x06bf, B:578:0x06c0, B:580:0x06c7, B:581:0x06ce, B:579:0x06c3, B:582:0x06cf, B:583:0x06d6, B:518:0x05e5, B:520:0x05e9), top: B:634:0x05c8, outer: #2 }] */
    /* JADX WARN: Code duplicated, block: B:549:0x0666 A[Catch: InvalidInputException -> 0x06d7, IndexOutOfBoundsException -> 0x0707, TryCatch #8 {IndexOutOfBoundsException -> 0x0707, InvalidInputException -> 0x06d7, blocks: (B:512:0x05c8, B:514:0x05da, B:516:0x05e0, B:522:0x05ed, B:526:0x05f4, B:533:0x0604, B:535:0x0608, B:537:0x061e, B:539:0x0624, B:544:0x064a, B:546:0x0651, B:540:0x062e, B:542:0x0632, B:543:0x0638, B:547:0x0654, B:549:0x0666, B:551:0x066c, B:552:0x0671, B:554:0x0675, B:559:0x0681, B:561:0x0687, B:563:0x0695, B:565:0x069b, B:569:0x06a5, B:572:0x06aa, B:573:0x06b1, B:575:0x06b5, B:576:0x06b8, B:577:0x06bf, B:578:0x06c0, B:580:0x06c7, B:581:0x06ce, B:579:0x06c3, B:582:0x06cf, B:583:0x06d6, B:518:0x05e5, B:520:0x05e9), top: B:634:0x05c8, outer: #2 }] */
    /* JADX WARN: Code duplicated, block: B:554:0x0675 A[Catch: InvalidInputException -> 0x06d7, IndexOutOfBoundsException -> 0x0707, TryCatch #8 {IndexOutOfBoundsException -> 0x0707, InvalidInputException -> 0x06d7, blocks: (B:512:0x05c8, B:514:0x05da, B:516:0x05e0, B:522:0x05ed, B:526:0x05f4, B:533:0x0604, B:535:0x0608, B:537:0x061e, B:539:0x0624, B:544:0x064a, B:546:0x0651, B:540:0x062e, B:542:0x0632, B:543:0x0638, B:547:0x0654, B:549:0x0666, B:551:0x066c, B:552:0x0671, B:554:0x0675, B:559:0x0681, B:561:0x0687, B:563:0x0695, B:565:0x069b, B:569:0x06a5, B:572:0x06aa, B:573:0x06b1, B:575:0x06b5, B:576:0x06b8, B:577:0x06bf, B:578:0x06c0, B:580:0x06c7, B:581:0x06ce, B:579:0x06c3, B:582:0x06cf, B:583:0x06d6, B:518:0x05e5, B:520:0x05e9), top: B:634:0x05c8, outer: #2 }] */
    /* JADX WARN: Code duplicated, block: B:582:0x06cf A[Catch: InvalidInputException -> 0x06d7, IndexOutOfBoundsException -> 0x0707, TryCatch #8 {IndexOutOfBoundsException -> 0x0707, InvalidInputException -> 0x06d7, blocks: (B:512:0x05c8, B:514:0x05da, B:516:0x05e0, B:522:0x05ed, B:526:0x05f4, B:533:0x0604, B:535:0x0608, B:537:0x061e, B:539:0x0624, B:544:0x064a, B:546:0x0651, B:540:0x062e, B:542:0x0632, B:543:0x0638, B:547:0x0654, B:549:0x0666, B:551:0x066c, B:552:0x0671, B:554:0x0675, B:559:0x0681, B:561:0x0687, B:563:0x0695, B:565:0x069b, B:569:0x06a5, B:572:0x06aa, B:573:0x06b1, B:575:0x06b5, B:576:0x06b8, B:577:0x06bf, B:578:0x06c0, B:580:0x06c7, B:581:0x06ce, B:579:0x06c3, B:582:0x06cf, B:583:0x06d6, B:518:0x05e5, B:520:0x05e9), top: B:634:0x05c8, outer: #2 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:521:0x05ec -> B:522:0x05ed). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:551:0x066c -> B:517:0x05e3). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    protected int getNextToken0() throws org.eclipse.jdt.core.compiler.InvalidInputException {
        /*
            Method dump skipped, instruction units count: 1924
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.parser.Scanner.getNextToken0():int");
    }

    public void getNextUnicodeChar() throws InvalidInputException {
        int i = this.currentPosition;
        int i2 = i + 1;
        this.currentPosition = i2;
        if (i2 >= this.eofPosition) {
            this.currentPosition = i;
            throw new InvalidInputException(INVALID_UNICODE_ESCAPE);
        }
        int i3 = 6;
        while (true) {
            char[] cArr = this.source;
            int i4 = this.currentPosition;
            char c = cArr[i4];
            if (c == 'u') {
                int i5 = i4 + 1;
                this.currentPosition = i5;
                if (i5 >= this.eofPosition) {
                    this.currentPosition = i4;
                    throw new InvalidInputException(INVALID_UNICODE_ESCAPE);
                }
                i3++;
            } else {
                int i6 = i4 + 4;
                int i7 = this.eofPosition;
                if (i6 > i7) {
                    this.currentPosition = i4 + (i7 - i4);
                    throw new InvalidInputException(INVALID_UNICODE_ESCAPE);
                }
                this.currentPosition = i4 + 1;
                int hexadecimalValue = ScannerHelper.getHexadecimalValue(c);
                if (hexadecimalValue <= 15 && hexadecimalValue >= 0) {
                    char[] cArr2 = this.source;
                    int i8 = this.currentPosition;
                    this.currentPosition = i8 + 1;
                    int hexadecimalValue2 = ScannerHelper.getHexadecimalValue(cArr2[i8]);
                    if (hexadecimalValue2 <= 15 && hexadecimalValue2 >= 0) {
                        char[] cArr3 = this.source;
                        int i9 = this.currentPosition;
                        this.currentPosition = i9 + 1;
                        int hexadecimalValue3 = ScannerHelper.getHexadecimalValue(cArr3[i9]);
                        if (hexadecimalValue3 <= 15 && hexadecimalValue3 >= 0) {
                            char[] cArr4 = this.source;
                            int i10 = this.currentPosition;
                            this.currentPosition = i10 + 1;
                            int hexadecimalValue4 = ScannerHelper.getHexadecimalValue(cArr4[i10]);
                            if (hexadecimalValue4 <= 15 && hexadecimalValue4 >= 0) {
                                this.currentCharacter = (char) ((((((hexadecimalValue * 16) + hexadecimalValue2) * 16) + hexadecimalValue3) * 16) + hexadecimalValue4);
                                if (this.withoutUnicodePtr == 0) {
                                    unicodeInitializeBuffer((this.currentPosition - i3) - this.startPosition);
                                }
                                unicodeStore();
                                this.unicodeAsBackSlash = this.currentCharacter == '\\';
                                return;
                            }
                        }
                    }
                }
                throw new InvalidInputException(INVALID_UNICODE_ESCAPE);
            }
        }
    }

    public NLSTag[] getNLSTags() {
        int i = this.nlsTagsPtr;
        if (i == 0) {
            return null;
        }
        NLSTag[] nLSTagArr = new NLSTag[i];
        System.arraycopy(this.nlsTags, 0, nLSTagArr, 0, i);
        this.nlsTagsPtr = 0;
        return nLSTagArr;
    }

    public boolean[] getIdentityComparisonLines() {
        boolean[] zArr = this.validIdentityComparisonLines;
        this.validIdentityComparisonLines = null;
        return zArr;
    }

    public char[] getSource() {
        return this.source;
    }

    /* JADX WARN: Code duplicated, block: B:53:0x00b3 A[Catch: InvalidInputException -> 0x0318, IndexOutOfBoundsException | InvalidInputException -> 0x036f, TRY_LEAVE, TryCatch #0 {InvalidInputException -> 0x0318, blocks: (B:29:0x0059, B:31:0x0060, B:32:0x0065, B:34:0x006d, B:39:0x0082, B:41:0x008b, B:45:0x0096, B:53:0x00b3, B:46:0x009d, B:47:0x00a4, B:51:0x00ab), top: B:282:0x0059 }] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r1v39 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v33 */
    public final void jumpOverMethodBody() {
        boolean zIsWhitespace;
        int i;
        ?? r2;
        char c;
        char c2;
        boolean zIsJavaIdentifierStart;
        boolean z = false;
        this.wasAcr = false;
        int i2 = 1;
        int i3 = 1;
        while (true) {
            try {
                this.withoutUnicodePtr = z ? 1 : 0;
                do {
                    int i4 = this.currentPosition;
                    this.startPosition = i4;
                    char[] cArr = this.source;
                    int i5 = i4 + 1;
                    this.currentPosition = i5;
                    char c3 = cArr[i4];
                    this.currentCharacter = c3;
                    if (c3 == '\\' && cArr[i5] == 'u') {
                        zIsWhitespace = jumpOverUnicodeWhiteSpace();
                    } else {
                        if (this.recordLineSeparator && (c3 == '\r' || c3 == '\n')) {
                            pushLineSeparator();
                        }
                        zIsWhitespace = CharOperation.isWhitespace(this.currentCharacter);
                    }
                } while (zIsWhitespace);
                char c4 = this.currentCharacter;
                if (c4 == '\"') {
                    try {
                        this.unicodeAsBackSlash = z;
                        char[] cArr2 = this.source;
                        int i6 = this.currentPosition;
                        int i7 = i6 + 1;
                        this.currentPosition = i7;
                        char c5 = cArr2[i6];
                        this.currentCharacter = c5;
                        if (c5 == '\\' && cArr2[i7] == 'u') {
                            getNextUnicodeChar();
                        } else if (this.withoutUnicodePtr != 0) {
                            unicodeStore();
                        }
                        while (true) {
                            char c6 = this.currentCharacter;
                            if (c6 == '\"') {
                                break;
                            }
                            int i8 = this.currentPosition;
                            if (i8 >= this.eofPosition) {
                                return;
                            }
                            if (c6 == '\r') {
                                if (this.source[i8] != '\n') {
                                    break;
                                }
                                this.currentPosition = i8 + 1;
                                break;
                            }
                            if (c6 == '\n') {
                                break;
                            }
                            if (c6 == '\\') {
                                try {
                                    if (this.unicodeAsBackSlash) {
                                        this.unicodeAsBackSlash = false;
                                        char[] cArr3 = this.source;
                                        int i9 = i8 + 1;
                                        this.currentPosition = i9;
                                        char c7 = cArr3[i8];
                                        this.currentCharacter = c7;
                                        if (c7 == '\\' && cArr3[i9] == 'u') {
                                            getNextUnicodeChar();
                                        } else if (this.withoutUnicodePtr != 0) {
                                            unicodeStore();
                                        }
                                    } else {
                                        char[] cArr4 = this.source;
                                        this.currentPosition = i8 + 1;
                                        this.currentCharacter = cArr4[i8];
                                    }
                                    scanEscapeCharacter();
                                } catch (InvalidInputException unused) {
                                }
                            }
                            this.unicodeAsBackSlash = false;
                            char[] cArr5 = this.source;
                            int i10 = this.currentPosition;
                            int i11 = i10 + 1;
                            this.currentPosition = i11;
                            char c8 = cArr5[i10];
                            this.currentCharacter = c8;
                            if (c8 == '\\' && cArr5[i11] == 'u') {
                                getNextUnicodeChar();
                            } else if (this.withoutUnicodePtr != 0) {
                                unicodeStore();
                            }
                        }
                    } catch (InvalidInputException unused2) {
                    }
                } else if (c4 == '\'') {
                    if (getNextChar('\\')) {
                        try {
                            if (this.unicodeAsBackSlash) {
                                this.unicodeAsBackSlash = false;
                                char[] cArr6 = this.source;
                                int i12 = this.currentPosition;
                                int i13 = i12 + 1;
                                this.currentPosition = i13;
                                char c9 = cArr6[i12];
                                this.currentCharacter = c9;
                                if (c9 == '\\' && cArr6[i13] == 'u') {
                                    getNextUnicodeChar();
                                } else if (this.withoutUnicodePtr != 0) {
                                    unicodeStore();
                                }
                            } else {
                                char[] cArr7 = this.source;
                                int i14 = this.currentPosition;
                                this.currentPosition = i14 + 1;
                                this.currentCharacter = cArr7[i14];
                            }
                            scanEscapeCharacter();
                        } catch (InvalidInputException unused3) {
                        }
                    } else {
                        this.unicodeAsBackSlash = false;
                        char[] cArr8 = this.source;
                        int i15 = this.currentPosition;
                        int i16 = i15 + 1;
                        this.currentPosition = i16;
                        char c10 = cArr8[i15];
                        this.currentCharacter = c10;
                        if (c10 == '\\' && cArr8[i16] == 'u') {
                            getNextUnicodeChar();
                        } else if (this.withoutUnicodePtr != 0) {
                            unicodeStore();
                        }
                    }
                    getNextChar('\'');
                } else if (c4 == '/') {
                    int nextChar = getNextChar('/', '*');
                    if (nextChar == 0) {
                        try {
                            int i17 = this.currentPosition;
                            this.lastCommentLinePosition = i17;
                            char[] cArr9 = this.source;
                            int i18 = i17 + 1;
                            this.currentPosition = i18;
                            char c11 = cArr9[i17];
                            this.currentCharacter = c11;
                            if (c11 == '\\' && cArr9[i18] == 'u') {
                                getNextUnicodeChar();
                            }
                            if (this.currentCharacter == '\\') {
                                char[] cArr10 = this.source;
                                int i19 = this.currentPosition;
                                if (cArr10[i19] == '\\') {
                                    this.currentPosition = i19 + 1;
                                }
                            }
                            int i20 = z ? 1 : 0;
                            while (true) {
                                c = this.currentCharacter;
                                if (c == '\r' || c == '\n') {
                                    break;
                                    break;
                                }
                                int i21 = this.currentPosition;
                                if (i21 >= this.eofPosition) {
                                    this.lastCommentLinePosition = i21;
                                    this.currentPosition = i21 + 1;
                                    throw new IndexOutOfBoundsException();
                                }
                                this.lastCommentLinePosition = i21;
                                char[] cArr11 = this.source;
                                int i22 = i21 + 1;
                                this.currentPosition = i22;
                                char c12 = cArr11[i21];
                                this.currentCharacter = c12;
                                if (c12 == '\\' && cArr11[i22] == 'u') {
                                    getNextUnicodeChar();
                                    i20 = i2;
                                } else {
                                    i20 = z ? 1 : 0;
                                }
                                if (this.currentCharacter == '\\') {
                                    char[] cArr12 = this.source;
                                    int i23 = this.currentPosition;
                                    if (cArr12[i23] == '\\') {
                                        this.currentPosition = i23 + 1;
                                    }
                                }
                            }
                            if (c == '\r') {
                                int i24 = this.eofPosition;
                                int i25 = this.currentPosition;
                                if (i24 > i25) {
                                    char[] cArr13 = this.source;
                                    char c13 = cArr13[i25];
                                    if (c13 == '\n') {
                                        this.currentPosition = i25 + 1;
                                        this.currentCharacter = '\n';
                                    } else if (c13 == '\\' && cArr13[i25 + 1] == 'u') {
                                        getNextUnicodeChar();
                                        i20 = i2;
                                    }
                                }
                            }
                            recordComment(1001);
                            if (this.recordLineSeparator && ((c2 = this.currentCharacter) == '\r' || c2 == '\n')) {
                                if ((this.checkNonExternalizedStringLiterals || this.checkUninternedIdentityComparison) && this.lastPosition < this.currentPosition) {
                                    parseTags();
                                }
                                if (this.recordLineSeparator) {
                                    if (i20 != 0) {
                                        pushUnicodeLineSeparator();
                                    } else {
                                        pushLineSeparator();
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException unused4) {
                            this.currentPosition -= i2;
                            recordComment(1001);
                            if ((this.checkNonExternalizedStringLiterals || this.checkUninternedIdentityComparison) && this.lastPosition < this.currentPosition) {
                                parseTags();
                            }
                            if (!this.tokenizeComments) {
                                this.currentPosition += i2;
                            }
                        }
                    } else if (nextChar > 0) {
                        try {
                            this.unicodeAsBackSlash = z;
                            char[] cArr14 = this.source;
                            int i26 = this.currentPosition;
                            int i27 = i26 + 1;
                            this.currentPosition = i27;
                            char c14 = cArr14[i26];
                            this.currentCharacter = c14;
                            if (c14 == '\\' && cArr14[i27] == 'u') {
                                getNextUnicodeChar();
                                i = i2;
                            } else {
                                if (this.withoutUnicodePtr != 0) {
                                    unicodeStore();
                                }
                                i = z ? 1 : 0;
                            }
                            char c15 = this.currentCharacter;
                            int i28 = c15 == '*' ? i2 : z ? 1 : 0;
                            ?? r14 = i28 == true ? 1 : 0;
                            if ((c15 == '\r' || c15 == '\n') && this.recordLineSeparator) {
                                if (i != 0) {
                                    pushUnicodeLineSeparator();
                                } else {
                                    pushLineSeparator();
                                }
                            }
                            int i29 = this.currentPosition;
                            char[] cArr15 = this.source;
                            int i30 = i29 + 1;
                            this.currentPosition = i30;
                            char c16 = cArr15[i29];
                            this.currentCharacter = c16;
                            if (c16 == '\\' && cArr15[i30] == 'u') {
                                getNextUnicodeChar();
                                r2 = 1;
                            } else {
                                r2 = z ? 1 : 0;
                            }
                            char c17 = this.currentCharacter;
                            if (c17 == '\\') {
                                char[] cArr16 = this.source;
                                int i31 = this.currentPosition;
                                if (cArr16[i31] == '\\') {
                                    this.currentPosition = i31 + 1;
                                }
                            }
                            if (c17 == '/') {
                                i28 = 0;
                            }
                            int i32 = 0;
                            while (true) {
                                char c18 = this.currentCharacter;
                                if (c18 != '/' || r14 == 0) {
                                    if (this.currentPosition >= this.eofPosition) {
                                        return;
                                    }
                                    if ((c18 == '\r' || c18 == '\n') && this.recordLineSeparator) {
                                        if (r2 != 0) {
                                            pushUnicodeLineSeparator();
                                        } else {
                                            pushLineSeparator();
                                        }
                                    }
                                    char c19 = this.currentCharacter;
                                    if (c19 != '*') {
                                        if (c19 != '@' || i32 != 0 || !isFirstTag()) {
                                            i29 = i32;
                                        }
                                        i32 = i29;
                                        r14 = 0;
                                    } else {
                                        r14 = 1;
                                    }
                                    i29 = this.currentPosition;
                                    char[] cArr17 = this.source;
                                    int i33 = i29 + 1;
                                    this.currentPosition = i33;
                                    char c20 = cArr17[i29];
                                    this.currentCharacter = c20;
                                    if (c20 == '\\' && cArr17[i33] == 'u') {
                                        getNextUnicodeChar();
                                        r2 = 1;
                                    } else {
                                        r2 = 0;
                                    }
                                    if (this.currentCharacter == '\\') {
                                        char[] cArr18 = this.source;
                                        int i34 = this.currentPosition;
                                        if (cArr18[i34] == '\\') {
                                            this.currentPosition = i34 + 1;
                                        }
                                    }
                                } else {
                                    recordComment(i28 != 0 ? 1003 : 1002);
                                    this.commentTagStarts[this.commentPtr] = i32;
                                    break;
                                }
                            }
                        } catch (IndexOutOfBoundsException unused5) {
                            return;
                        }
                    }
                } else if (c4 == '{') {
                    i3++;
                } else if (c4 == '}') {
                    i3--;
                    if (i3 == 0) {
                        return;
                    }
                } else if (c4 < 128) {
                    try {
                        if ((64 & ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c4]) != 0) {
                            scanIdentifierOrKeyword();
                        } else if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c4] & 4) != 0) {
                            scanNumber(z);
                        }
                    } catch (InvalidInputException unused6) {
                    }
                } else if (c4 < 55296 || c4 > 56319) {
                    if (c4 < 56320 || c4 > 57343) {
                        zIsJavaIdentifierStart = ScannerHelper.isJavaIdentifierStart(this.complianceLevel, c4);
                        if (zIsJavaIdentifierStart) {
                            scanIdentifierOrKeyword();
                        }
                    }
                } else {
                    if (this.complianceLevel < ClassFileConstants.JDK1_5) {
                        throw new InvalidInputException(INVALID_UNICODE_ESCAPE);
                    }
                    char nextChar2 = (char) getNextChar();
                    if (nextChar2 >= 56320 && nextChar2 <= 57343) {
                        zIsJavaIdentifierStart = ScannerHelper.isJavaIdentifierStart(this.complianceLevel, c4, nextChar2);
                        if (zIsJavaIdentifierStart) {
                            scanIdentifierOrKeyword();
                        }
                    }
                }
                z = false;
                i2 = 1;
            } catch (IndexOutOfBoundsException | InvalidInputException unused7) {
                return;
            }
        }
    }

    public final boolean jumpOverUnicodeWhiteSpace() throws InvalidInputException {
        this.wasAcr = false;
        getNextUnicodeChar();
        return CharOperation.isWhitespace(this.currentCharacter);
    }

    final char[] optimizedCurrentTokenSource1() {
        char c = this.source[this.startPosition];
        switch (c) {
            case 'a':
                return charArray_a;
            case 'b':
                return charArray_b;
            case 'c':
                return charArray_c;
            case 'd':
                return charArray_d;
            case 'e':
                return charArray_e;
            case 'f':
                return charArray_f;
            case 'g':
                return charArray_g;
            case 'h':
                return charArray_h;
            case 'i':
                return charArray_i;
            case 'j':
                return charArray_j;
            case 'k':
                return charArray_k;
            case 'l':
                return charArray_l;
            case 'm':
                return charArray_m;
            case 'n':
                return charArray_n;
            case 'o':
                return charArray_o;
            case 'p':
                return charArray_p;
            case 'q':
                return charArray_q;
            case 'r':
                return charArray_r;
            case 's':
                return charArray_s;
            case 't':
                return charArray_t;
            case 'u':
                return charArray_u;
            case 'v':
                return charArray_v;
            case 'w':
                return charArray_w;
            case 'x':
                return charArray_x;
            case 'y':
                return charArray_y;
            case 'z':
                return charArray_z;
            default:
                return new char[]{c};
        }
    }

    final char[] optimizedCurrentTokenSource2() {
        char[] cArr = this.source;
        int i = this.startPosition;
        char c = cArr[i];
        char c2 = cArr[i + 1];
        char[][] cArr2 = this.charArray_length[0][((c << 6) + c2) % 30];
        int i2 = this.newEntry2;
        while (true) {
            i2++;
            if (i2 < 6) {
                char[] cArr3 = cArr2[i2];
                if (c == cArr3[0] && c2 == cArr3[1]) {
                    return cArr3;
                }
            } else {
                int i3 = this.newEntry2;
                int i4 = -1;
                while (true) {
                    i4++;
                    if (i4 <= i3) {
                        char[] cArr4 = cArr2[i4];
                        if (c == cArr4[0] && c2 == cArr4[1]) {
                            return cArr4;
                        }
                    } else {
                        int i5 = i3 + 1;
                        if (i5 >= 6) {
                            i5 = 0;
                        }
                        char[] cArr5 = new char[2];
                        System.arraycopy(cArr, i, cArr5, 0, 2);
                        this.newEntry2 = i5;
                        cArr2[i5] = cArr5;
                        return cArr5;
                    }
                }
            }
        }
    }

    final char[] optimizedCurrentTokenSource3() {
        char[] cArr = this.source;
        int i = this.startPosition;
        char c = cArr[i + 1];
        char c2 = cArr[i];
        char c3 = cArr[i + 2];
        char[][] cArr2 = this.charArray_length[1][((c2 << 6) + c3) % 30];
        int i2 = this.newEntry3;
        while (true) {
            i2++;
            if (i2 < 6) {
                char[] cArr3 = cArr2[i2];
                if (c2 == cArr3[0] && c == cArr3[1] && c3 == cArr3[2]) {
                    return cArr3;
                }
            } else {
                int i3 = this.newEntry3;
                int i4 = -1;
                while (true) {
                    i4++;
                    if (i4 <= i3) {
                        char[] cArr4 = cArr2[i4];
                        if (c2 == cArr4[0] && c == cArr4[1] && c3 == cArr4[2]) {
                            return cArr4;
                        }
                    } else {
                        int i5 = i3 + 1;
                        if (i5 >= 6) {
                            i5 = 0;
                        }
                        char[] cArr5 = new char[3];
                        System.arraycopy(cArr, i, cArr5, 0, 3);
                        this.newEntry3 = i5;
                        cArr2[i5] = cArr5;
                        return cArr5;
                    }
                }
            }
        }
    }

    final char[] optimizedCurrentTokenSource4() {
        char[] cArr = this.source;
        int i = this.startPosition;
        char c = cArr[i + 1];
        char c2 = cArr[i + 3];
        char c3 = cArr[i];
        char c4 = cArr[i + 2];
        char[][] cArr2 = this.charArray_length[2][((c3 << 6) + c4) % 30];
        int i2 = this.newEntry4;
        while (true) {
            i2++;
            int i3 = 6;
            if (i2 < 6) {
                char[] cArr3 = cArr2[i2];
                if (c3 == cArr3[0] && c == cArr3[1] && c4 == cArr3[2] && c2 == cArr3[3]) {
                    return cArr3;
                }
            } else {
                int i4 = this.newEntry4;
                int i5 = -1;
                while (true) {
                    i5++;
                    if (i5 <= i4) {
                        char[] cArr4 = cArr2[i5];
                        if (c3 == cArr4[0] && c == cArr4[1] && c4 == cArr4[2] && c2 == cArr4[3]) {
                            return cArr4;
                        }
                        i3 = 6;
                    } else {
                        int i6 = i4 + 1;
                        if (i6 >= i3) {
                            i6 = 0;
                        }
                        char[] cArr5 = new char[4];
                        System.arraycopy(cArr, i, cArr5, 0, 4);
                        this.newEntry4 = i6;
                        cArr2[i6] = cArr5;
                        return cArr5;
                    }
                }
            }
        }
    }

    final char[] optimizedCurrentTokenSource5() {
        char c;
        char[] cArr = this.source;
        int i = this.startPosition;
        char c2 = cArr[i + 1];
        char c3 = cArr[i + 3];
        char c4 = cArr[i];
        char c5 = cArr[i + 2];
        char c6 = cArr[i + 4];
        char c7 = 3;
        char[][] cArr2 = this.charArray_length[3][(((c4 << '\f') + (c5 << 6)) + c6) % 30];
        int i2 = this.newEntry5;
        while (true) {
            i2++;
            int i3 = 6;
            if (i2 < 6) {
                char[] cArr3 = cArr2[i2];
                if (c4 == cArr3[0] && c2 == cArr3[1] && c5 == cArr3[2]) {
                    c = 3;
                    if (c3 == cArr3[3] && c6 == cArr3[4]) {
                        return cArr3;
                    }
                } else {
                    c = 3;
                }
                c7 = c;
            } else {
                int i4 = this.newEntry5;
                int i5 = -1;
                while (true) {
                    int i6 = i5 + 1;
                    if (i6 <= i4) {
                        char[] cArr4 = cArr2[i6];
                        if (c4 == cArr4[0] && c2 == cArr4[1] && c5 == cArr4[2] && c3 == cArr4[c7] && c6 == cArr4[4]) {
                            return cArr4;
                        }
                        i5 = i6;
                        c7 = 3;
                        i3 = 6;
                    } else {
                        int i7 = i4 + 1;
                        if (i7 >= i3) {
                            i7 = 0;
                        }
                        char[] cArr5 = new char[5];
                        System.arraycopy(cArr, i, cArr5, 0, 5);
                        this.newEntry5 = i7;
                        cArr2[i7] = cArr5;
                        return cArr5;
                    }
                }
            }
        }
    }

    final char[] optimizedCurrentTokenSource6() {
        char c;
        char[] cArr = this.source;
        int i = this.startPosition;
        char c2 = cArr[i + 1];
        char c3 = cArr[i + 3];
        char c4 = cArr[i + 5];
        char c5 = cArr[i];
        char c6 = cArr[i + 2];
        char c7 = cArr[i + 4];
        char[][] cArr2 = this.charArray_length[4][(((c5 << '\f') + (c6 << 6)) + c7) % 30];
        int i2 = this.newEntry6;
        while (true) {
            i2++;
            char c8 = 3;
            int i3 = 6;
            if (i2 < 6) {
                char[] cArr3 = cArr2[i2];
                if (c5 == cArr3[0] && c2 == cArr3[1] && c6 == cArr3[2] && c3 == cArr3[3]) {
                    c = 4;
                    if (c7 == cArr3[4] && c4 == cArr3[5]) {
                        return cArr3;
                    }
                } else {
                    c = 4;
                }
            } else {
                int i4 = this.newEntry6;
                int i5 = -1;
                while (true) {
                    int i6 = i5 + 1;
                    if (i6 <= i4) {
                        char[] cArr4 = cArr2[i6];
                        if (c5 == cArr4[0] && c2 == cArr4[1] && c6 == cArr4[2] && c3 == cArr4[c8] && c7 == cArr4[4] && c4 == cArr4[5]) {
                            return cArr4;
                        }
                        i5 = i6;
                        c8 = 3;
                        i3 = 6;
                    } else {
                        int i7 = i4 + 1;
                        if (i7 >= i3) {
                            i7 = 0;
                        }
                        char[] cArr5 = new char[i3];
                        System.arraycopy(cArr, i, cArr5, 0, i3);
                        this.newEntry6 = i7;
                        cArr2[i7] = cArr5;
                        return cArr5;
                    }
                }
            }
        }
    }

    private void parseTags() {
        char[] cArr;
        int i;
        int i2;
        int iIndexOf;
        NLSTag nLSTag;
        int i3 = this.startPosition;
        int i4 = this.linePtr;
        int i5 = i4 >= 0 ? this.lineEnds[i4] + 1 : 0;
        while (ScannerHelper.isWhitespace(this.source[i5])) {
            i5++;
        }
        if (i3 == i5) {
            return;
        }
        int i6 = this.currentPosition;
        int i7 = this.withoutUnicodePtr;
        if (i7 != 0) {
            cArr = new char[i7];
            System.arraycopy(this.withoutUnicodeBuffer, 1, cArr, 0, i7);
            i = this.withoutUnicodePtr;
            i2 = i3;
            i3 = 1;
        } else {
            cArr = this.source;
            i = i6;
            i2 = 0;
        }
        if (this.checkNonExternalizedStringLiterals && (iIndexOf = CharOperation.indexOf(TAG_PREFIX, cArr, true, i3, i)) != -1) {
            if (this.nlsTags == null) {
                this.nlsTags = new NLSTag[10];
                this.nlsTagsPtr = 0;
            }
            while (iIndexOf != -1) {
                int i8 = TAG_PREFIX_LENGTH + iIndexOf;
                int iIndexOf2 = CharOperation.indexOf('$', cArr, i8, i);
                if (iIndexOf2 != -1) {
                    int i9 = i4 + 1;
                    try {
                        nLSTag = new NLSTag(iIndexOf + i2, iIndexOf2 + i2, i9, extractInt(cArr, i8, iIndexOf2));
                    } catch (NumberFormatException unused) {
                        nLSTag = new NLSTag(iIndexOf + i2, iIndexOf2 + i2, i9, -1);
                    }
                    int i10 = this.nlsTagsPtr;
                    NLSTag[] nLSTagArr = this.nlsTags;
                    if (i10 == nLSTagArr.length) {
                        NLSTag[] nLSTagArr2 = new NLSTag[i10 + 10];
                        this.nlsTags = nLSTagArr2;
                        System.arraycopy(nLSTagArr, 0, nLSTagArr2, 0, i10);
                    }
                    NLSTag[] nLSTagArr3 = this.nlsTags;
                    int i11 = this.nlsTagsPtr;
                    this.nlsTagsPtr = i11 + 1;
                    nLSTagArr3[i11] = nLSTag;
                    i8 = iIndexOf2;
                }
                iIndexOf = CharOperation.indexOf(TAG_PREFIX, cArr, true, i8, i);
            }
        }
        if (!this.checkUninternedIdentityComparison || CharOperation.indexOf(IDENTITY_COMPARISON_TAG, cArr, true, i3, i) == -1) {
            return;
        }
        if (this.validIdentityComparisonLines == null) {
            this.validIdentityComparisonLines = new boolean[0];
        }
        int i12 = i4 + 1;
        boolean[] zArr = this.validIdentityComparisonLines;
        int length = zArr.length;
        boolean[] zArr2 = new boolean[i4 + 2];
        this.validIdentityComparisonLines = zArr2;
        System.arraycopy(zArr, 0, zArr2, 0, length);
        this.validIdentityComparisonLines[i12] = true;
    }

    private int extractInt(char[] cArr, int i, int i2) {
        int i3;
        int i4 = 0;
        while (i < i2) {
            switch (cArr[i]) {
                case '0':
                    i3 = 0;
                    break;
                case '1':
                    i3 = 1;
                    break;
                case '2':
                    i3 = 2;
                    break;
                case '3':
                    i3 = 3;
                    break;
                case '4':
                    i3 = 4;
                    break;
                case '5':
                    i3 = 5;
                    break;
                case '6':
                    i3 = 6;
                    break;
                case '7':
                    i3 = 7;
                    break;
                case '8':
                    i3 = 8;
                    break;
                case '9':
                    i3 = 9;
                    break;
                default:
                    throw new NumberFormatException();
            }
            int i5 = i4 * 10;
            if (i3 < 0) {
                throw new NumberFormatException();
            }
            i4 = i5 + i3;
            i++;
        }
        return i4;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x005b  */
    /* JADX WARN: Code duplicated, block: B:32:0x0071  */
    public final void pushLineSeparator() {
        int i;
        int i2;
        int[] iArr;
        int length;
        int i3;
        char c = this.currentCharacter;
        if (c == '\r') {
            int i4 = this.currentPosition - 1;
            int i5 = this.linePtr;
            if (i5 < 0 || this.lineEnds[i5] < i4) {
                int[] iArr2 = this.lineEnds;
                int length2 = iArr2.length;
                int i6 = i5 + 1;
                this.linePtr = i6;
                if (i6 >= length2) {
                    int[] iArr3 = new int[length2 + 250];
                    this.lineEnds = iArr3;
                    System.arraycopy(iArr2, 0, iArr3, 0, length2);
                }
                int[] iArr4 = this.lineEnds;
                int i7 = this.linePtr;
                iArr4[i7] = i4;
                try {
                    char[] cArr = this.source;
                    int i8 = this.currentPosition;
                    if (cArr[i8] == '\n') {
                        iArr4[i7] = i8;
                        this.currentPosition = i8 + 1;
                        this.wasAcr = false;
                    } else {
                        this.wasAcr = true;
                    }
                    return;
                } catch (IndexOutOfBoundsException unused) {
                    this.wasAcr = true;
                    return;
                }
            }
            return;
        }
        if (c == '\n') {
            if (this.wasAcr) {
                int[] iArr5 = this.lineEnds;
                int i9 = this.linePtr;
                int i10 = iArr5[i9];
                int i11 = this.currentPosition;
                if (i10 == i11 - 2) {
                    iArr5[i9] = i11 - 1;
                } else {
                    i = this.currentPosition - 1;
                    i2 = this.linePtr;
                    if (i2 < 0 && this.lineEnds[i2] >= i) {
                        return;
                    }
                    iArr = this.lineEnds;
                    length = iArr.length;
                    i3 = i2 + 1;
                    this.linePtr = i3;
                    if (i3 >= length) {
                        int[] iArr6 = new int[length + 250];
                        this.lineEnds = iArr6;
                        System.arraycopy(iArr, 0, iArr6, 0, length);
                    }
                    this.lineEnds[this.linePtr] = i;
                }
            } else {
                i = this.currentPosition - 1;
                i2 = this.linePtr;
                if (i2 < 0) {
                }
                iArr = this.lineEnds;
                length = iArr.length;
                i3 = i2 + 1;
                this.linePtr = i3;
                if (i3 >= length) {
                    int[] iArr7 = new int[length + 250];
                    this.lineEnds = iArr7;
                    System.arraycopy(iArr, 0, iArr7, 0, length);
                }
                this.lineEnds[this.linePtr] = i;
            }
            this.wasAcr = false;
        }
    }

    public final void pushUnicodeLineSeparator() {
        char c = this.currentCharacter;
        if (c != '\r') {
            if (c == '\n') {
                this.wasAcr = false;
            }
        } else if (this.source[this.currentPosition] == '\n') {
            this.wasAcr = false;
        } else {
            this.wasAcr = true;
        }
    }

    public void recordComment(int i) {
        int i2 = this.startPosition;
        int i3 = this.currentPosition;
        if (i == 1001) {
            i2 = -i2;
            i3 = -this.lastCommentLinePosition;
        } else if (i == 1002) {
            i3 = -i3;
        }
        int[] iArr = this.commentStops;
        int length = iArr.length;
        int i4 = this.commentPtr + 1;
        this.commentPtr = i4;
        if (i4 >= length) {
            int i5 = length + 300;
            int[] iArr2 = new int[i5];
            this.commentStops = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
            int[] iArr3 = this.commentStarts;
            int[] iArr4 = new int[i5];
            this.commentStarts = iArr4;
            System.arraycopy(iArr3, 0, iArr4, 0, length);
            int[] iArr5 = this.commentTagStarts;
            int[] iArr6 = new int[i5];
            this.commentTagStarts = iArr6;
            System.arraycopy(iArr5, 0, iArr6, 0, length);
        }
        int[] iArr7 = this.commentStops;
        int i6 = this.commentPtr;
        iArr7[i6] = i3;
        this.commentStarts[i6] = i2;
    }

    public void resetTo(int i, int i2) {
        this.diet = false;
        this.currentPosition = i;
        this.startPosition = i;
        this.initialPosition = i;
        char[] cArr = this.source;
        if (cArr != null && cArr.length < i2) {
            this.eofPosition = cArr.length;
        } else {
            if (i2 < Integer.MAX_VALUE) {
                i2++;
            }
            this.eofPosition = i2;
        }
        this.commentPtr = -1;
        this.foundTaskCount = 0;
        int[] iArr = this.lookBack;
        this.nextToken = 0;
        iArr[1] = 0;
        iArr[0] = 0;
        this.consumingEllipsisAnnotations = false;
    }

    protected final void scanEscapeCharacter() throws InvalidInputException {
        int hexadecimalValue;
        int hexadecimalValue2;
        char c = this.currentCharacter;
        if (c == '\"') {
            this.currentCharacter = '\"';
            return;
        }
        if (c == '\'') {
            this.currentCharacter = '\'';
            return;
        }
        if (c == '\\') {
            this.currentCharacter = '\\';
            return;
        }
        if (c == 'b') {
            this.currentCharacter = '\b';
            return;
        }
        if (c == 'f') {
            this.currentCharacter = '\f';
            return;
        }
        if (c == 'n') {
            this.currentCharacter = '\n';
            return;
        }
        if (c == 'r') {
            this.currentCharacter = '\r';
            return;
        }
        if (c == 't') {
            this.currentCharacter = '\t';
            return;
        }
        int hexadecimalValue3 = ScannerHelper.getHexadecimalValue(c);
        if (hexadecimalValue3 >= 0 && hexadecimalValue3 <= 7) {
            boolean z = hexadecimalValue3 > 3;
            char[] cArr = this.source;
            int i = this.currentPosition;
            this.currentPosition = i + 1;
            char c2 = cArr[i];
            this.currentCharacter = c2;
            if (ScannerHelper.isDigit(c2) && (hexadecimalValue = ScannerHelper.getHexadecimalValue(this.currentCharacter)) >= 0 && hexadecimalValue <= 7) {
                hexadecimalValue3 = (hexadecimalValue3 * 8) + hexadecimalValue;
                char[] cArr2 = this.source;
                int i2 = this.currentPosition;
                this.currentPosition = i2 + 1;
                char c3 = cArr2[i2];
                this.currentCharacter = c3;
                if (!ScannerHelper.isDigit(c3) || z || (hexadecimalValue2 = ScannerHelper.getHexadecimalValue(this.currentCharacter)) < 0 || hexadecimalValue2 > 7) {
                    this.currentPosition--;
                } else {
                    hexadecimalValue3 = (hexadecimalValue3 * 8) + hexadecimalValue2;
                }
            } else {
                this.currentPosition--;
            }
            if (hexadecimalValue3 > 255) {
                throw new InvalidInputException(INVALID_ESCAPE);
            }
            this.currentCharacter = (char) hexadecimalValue3;
            return;
        }
        throw new InvalidInputException(INVALID_ESCAPE);
    }

    public int scanIdentifierOrKeywordWithBoundCheck() {
        int i;
        char[] cArr;
        this.useAssertAsAnIndentifier = false;
        this.useEnumAsAnIndentifier = false;
        char[] cArr2 = this.source;
        int i2 = this.eofPosition;
        while (true) {
            int i3 = this.currentPosition;
            i = 1;
            if (i3 >= i2) {
                break;
            }
            char c = cArr2[i3];
            if (c < 128) {
                if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 60) != 0) {
                    if (this.withoutUnicodePtr != 0) {
                        this.currentCharacter = c;
                        unicodeStore();
                    }
                    this.currentPosition++;
                } else {
                    if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 258) != 0) {
                        this.currentCharacter = c;
                        break;
                    }
                    while (getNextCharAsJavaIdentifierPartWithBoundCheck()) {
                    }
                    break;
                }
            } else {
                while (getNextCharAsJavaIdentifierPartWithBoundCheck()) {
                }
                break;
            }
        }
        int i4 = this.withoutUnicodePtr;
        if (i4 == 0) {
            int i5 = this.currentPosition;
            int i6 = this.startPosition;
            i4 = i5 - i6;
            if (i4 == 1) {
                return 22;
            }
            cArr = this.source;
            i = i6;
        } else {
            if (i4 == 1) {
                return 22;
            }
            cArr = this.withoutUnicodeBuffer;
        }
        return internalScanIdentifierOrKeyword(i, i4, cArr);
    }

    public int scanIdentifierOrKeyword() {
        int i;
        char[] cArr;
        this.useAssertAsAnIndentifier = false;
        this.useEnumAsAnIndentifier = false;
        char[] cArr2 = this.source;
        int i2 = this.eofPosition;
        while (true) {
            int i3 = this.currentPosition;
            i = 1;
            if (i3 >= i2) {
                break;
            }
            char c = cArr2[i3];
            if (c < 128) {
                if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 60) != 0) {
                    if (this.withoutUnicodePtr != 0) {
                        this.currentCharacter = c;
                        unicodeStore();
                    }
                    this.currentPosition++;
                } else {
                    if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 258) != 0) {
                        this.currentCharacter = c;
                        break;
                    }
                    while (getNextCharAsJavaIdentifierPart()) {
                    }
                    break;
                }
            } else {
                while (getNextCharAsJavaIdentifierPart()) {
                }
                break;
            }
        }
        int i4 = this.withoutUnicodePtr;
        if (i4 == 0) {
            int i5 = this.currentPosition;
            int i6 = this.startPosition;
            i4 = i5 - i6;
            if (i4 == 1) {
                return 22;
            }
            cArr = this.source;
            i = i6;
        } else {
            if (i4 == 1) {
                return 22;
            }
            cArr = this.withoutUnicodeBuffer;
        }
        return internalScanIdentifierOrKeyword(i, i4, cArr);
    }

    private int internalScanIdentifierOrKeyword(int i, int i2, char[] cArr) {
        switch (cArr[i]) {
            case 'a':
                if (i2 != 6) {
                    return (i2 == 8 && cArr[i + 1] == 'b' && cArr[i + 2] == 's' && cArr[i + 3] == 't' && cArr[i + 4] == 'r' && cArr[i + 5] == 'a' && cArr[i + 6] == 'c' && cArr[i + 7] == 't') ? 51 : 22;
                }
                if (cArr[i + 1] == 's' && cArr[i + 2] == 's' && cArr[i + 3] == 'e' && cArr[i + 4] == 'r' && cArr[i + 5] == 't') {
                    if (this.sourceLevel >= ClassFileConstants.JDK1_4) {
                        this.containsAssertKeyword = true;
                        return 72;
                    }
                    this.useAssertAsAnIndentifier = true;
                }
                return 22;
            case 'b':
                if (i2 == 4) {
                    return (cArr[i + 1] == 'y' && cArr[i + 2] == 't' && cArr[i + 3] == 'e') ? 98 : 22;
                }
                if (i2 != 5) {
                    return (i2 == 7 && cArr[i + 1] == 'o' && cArr[i + 2] == 'o' && cArr[i + 3] == 'l' && cArr[i + 4] == 'e' && cArr[i + 5] == 'a' && cArr[i + 6] == 'n') ? 97 : 22;
                }
                return (cArr[i + 1] == 'r' && cArr[i + 2] == 'e' && cArr[i + 3] == 'a' && cArr[i + 4] == 'k') ? 73 : 22;
            case 'c':
                if (i2 == 4) {
                    char c = cArr[i + 1];
                    if (c == 'a') {
                        return (cArr[i + 2] == 's' && cArr[i + 3] == 'e') ? 99 : 22;
                    }
                    return (c == 'h' && cArr[i + 2] == 'a' && cArr[i + 3] == 'r') ? 101 : 22;
                }
                if (i2 != 5) {
                    return (i2 == 8 && cArr[i + 1] == 'o' && cArr[i + 2] == 'n' && cArr[i + 3] == 't' && cArr[i + 4] == 'i' && cArr[i + 5] == 'n' && cArr[i + 6] == 'u' && cArr[i + 7] == 'e') ? 74 : 22;
                }
                char c2 = cArr[i + 1];
                if (c2 == 'a') {
                    return (cArr[i + 2] == 't' && cArr[i + 3] == 'c' && cArr[i + 4] == 'h') ? 100 : 22;
                }
                if (c2 == 'l') {
                    return (cArr[i + 2] == 'a' && cArr[i + 3] == 's' && cArr[i + 4] == 's') ? 67 : 22;
                }
                return (c2 == 'o' && cArr[i + 2] == 'n' && cArr[i + 3] == 's' && cArr[i + 4] == 't') ? 116 : 22;
            case 'd':
                if (i2 == 2) {
                    return cArr[i + 1] == 'o' ? 76 : 22;
                }
                if (i2 != 6) {
                    return (i2 == 7 && cArr[i + 1] == 'e' && cArr[i + 2] == 'f' && cArr[i + 3] == 'a' && cArr[i + 4] == 'u' && cArr[i + 5] == 'l' && cArr[i + 6] == 't') ? 75 : 22;
                }
                return (cArr[i + 1] == 'o' && cArr[i + 2] == 'u' && cArr[i + 3] == 'b' && cArr[i + 4] == 'l' && cArr[i + 5] == 'e') ? 102 : 22;
            case 'e':
                if (i2 != 4) {
                    return (i2 == 7 && cArr[i + 1] == 'x' && cArr[i + 2] == 't' && cArr[i + 3] == 'e' && cArr[i + 4] == 'n' && cArr[i + 5] == 'd' && cArr[i + 6] == 's') ? 96 : 22;
                }
                char c3 = cArr[i + 1];
                if (c3 == 'l') {
                    return (cArr[i + 2] == 's' && cArr[i + 3] == 'e') ? 111 : 22;
                }
                if (c3 == 'n' && cArr[i + 2] == 'u' && cArr[i + 3] == 'm') {
                    if (this.sourceLevel >= ClassFileConstants.JDK1_5) {
                        return 69;
                    }
                    this.useEnumAsAnIndentifier = true;
                }
                return 22;
            case 'f':
                if (i2 == 3) {
                    return (cArr[i + 1] == 'o' && cArr[i + 2] == 'r') ? 77 : 22;
                }
                if (i2 != 5) {
                    return (i2 == 7 && cArr[i + 1] == 'i' && cArr[i + 2] == 'n' && cArr[i + 3] == 'a' && cArr[i + 4] == 'l' && cArr[i + 5] == 'l' && cArr[i + 6] == 'y') ? 109 : 22;
                }
                char c4 = cArr[i + 1];
                if (c4 == 'i') {
                    return (cArr[i + 2] == 'n' && cArr[i + 3] == 'a' && cArr[i + 4] == 'l') ? 52 : 22;
                }
                if (c4 == 'l') {
                    return (cArr[i + 2] == 'o' && cArr[i + 3] == 'a' && cArr[i + 4] == 't') ? 103 : 22;
                }
                return (c4 == 'a' && cArr[i + 2] == 'l' && cArr[i + 3] == 's' && cArr[i + 4] == 'e') ? 38 : 22;
            case 'g':
                return (i2 == 4 && cArr[i + 1] == 'o' && cArr[i + 2] == 't' && cArr[i + 3] == 'o') ? 117 : 22;
            case 'h':
            case 'j':
            case 'k':
            case 'm':
            case 'o':
            case 'q':
            case 'u':
            default:
                return 22;
            case 'i':
                if (i2 == 2) {
                    return cArr[i + 1] == 'f' ? 78 : 22;
                }
                if (i2 == 3) {
                    return (cArr[i + 1] == 'n' && cArr[i + 2] == 't') ? 105 : 22;
                }
                if (i2 == 6) {
                    return (cArr[i + 1] == 'm' && cArr[i + 2] == 'p' && cArr[i + 3] == 'o' && cArr[i + 4] == 'r' && cArr[i + 5] == 't') ? 104 : 22;
                }
                if (i2 == 9) {
                    return (cArr[i + 1] == 'n' && cArr[i + 2] == 't' && cArr[i + 3] == 'e' && cArr[i + 4] == 'r' && cArr[i + 5] == 'f' && cArr[i + 6] == 'a' && cArr[i + 7] == 'c' && cArr[i + 8] == 'e') ? 68 : 22;
                }
                if (i2 != 10) {
                    return 22;
                }
                char c5 = cArr[i + 1];
                if (c5 == 'm') {
                    return (cArr[i + 2] == 'p' && cArr[i + 3] == 'l' && cArr[i + 4] == 'e' && cArr[i + 5] == 'm' && cArr[i + 6] == 'e' && cArr[i + 7] == 'n' && cArr[i + 8] == 't' && cArr[i + 9] == 's') ? 114 : 22;
                }
                return (c5 == 'n' && cArr[i + 2] == 's' && cArr[i + 3] == 't' && cArr[i + 4] == 'a' && cArr[i + 5] == 'n' && cArr[i + 6] == 'c' && cArr[i + 7] == 'e' && cArr[i + 8] == 'o' && cArr[i + 9] == 'f') ? 17 : 22;
            case 'l':
                return (i2 == 4 && cArr[i + 1] == 'o' && cArr[i + 2] == 'n' && cArr[i + 3] == 'g') ? 106 : 22;
            case 'n':
                if (i2 == 3) {
                    return (cArr[i + 1] == 'e' && cArr[i + 2] == 'w') ? 36 : 22;
                }
                if (i2 != 4) {
                    return (i2 == 6 && cArr[i + 1] == 'a' && cArr[i + 2] == 't' && cArr[i + 3] == 'i' && cArr[i + 4] == 'v' && cArr[i + 5] == 'e') ? 53 : 22;
                }
                return (cArr[i + 1] == 'u' && cArr[i + 2] == 'l' && cArr[i + 3] == 'l') ? 39 : 22;
            case 'p':
                if (i2 == 6) {
                    return (cArr[i + 1] == 'u' && cArr[i + 2] == 'b' && cArr[i + 3] == 'l' && cArr[i + 4] == 'i' && cArr[i + 5] == 'c') ? 56 : 22;
                }
                if (i2 != 7) {
                    return (i2 == 9 && cArr[i + 1] == 'r' && cArr[i + 2] == 'o' && cArr[i + 3] == 't' && cArr[i + 4] == 'e' && cArr[i + 5] == 'c' && cArr[i + 6] == 't' && cArr[i + 7] == 'e' && cArr[i + 8] == 'd') ? 55 : 22;
                }
                char c6 = cArr[i + 1];
                if (c6 == 'a') {
                    return (cArr[i + 2] == 'c' && cArr[i + 3] == 'k' && cArr[i + 4] == 'a' && cArr[i + 5] == 'g' && cArr[i + 6] == 'e') ? 95 : 22;
                }
                return (c6 == 'r' && cArr[i + 2] == 'i' && cArr[i + 3] == 'v' && cArr[i + 4] == 'a' && cArr[i + 5] == 't' && cArr[i + 6] == 'e') ? 54 : 22;
            case 'r':
                return (i2 == 6 && cArr[i + 1] == 'e' && cArr[i + 2] == 't' && cArr[i + 3] == 'u' && cArr[i + 4] == 'r' && cArr[i + 5] == 'n') ? 79 : 22;
            case 's':
                if (i2 == 5) {
                    char c7 = cArr[i + 1];
                    if (c7 == 'h') {
                        return (cArr[i + 2] == 'o' && cArr[i + 3] == 'r' && cArr[i + 4] == 't') ? 107 : 22;
                    }
                    return (c7 == 'u' && cArr[i + 2] == 'p' && cArr[i + 3] == 'e' && cArr[i + 4] == 'r') ? 34 : 22;
                }
                if (i2 != 6) {
                    if (i2 != 8) {
                        return (i2 == 12 && cArr[i + 1] == 'y' && cArr[i + 2] == 'n' && cArr[i + 3] == 'c' && cArr[i + 4] == 'h' && cArr[i + 5] == 'r' && cArr[i + 6] == 'o' && cArr[i + 7] == 'n' && cArr[i + 8] == 'i' && cArr[i + 9] == 'z' && cArr[i + 10] == 'e' && cArr[i + 11] == 'd') ? 41 : 22;
                    }
                    return (cArr[i + 1] == 't' && cArr[i + 2] == 'r' && cArr[i + 3] == 'i' && cArr[i + 4] == 'c' && cArr[i + 5] == 't' && cArr[i + 6] == 'f' && cArr[i + 7] == 'p') ? 57 : 22;
                }
                char c8 = cArr[i + 1];
                if (c8 == 't') {
                    return (cArr[i + 2] == 'a' && cArr[i + 3] == 't' && cArr[i + 4] == 'i' && cArr[i + 5] == 'c') ? 40 : 22;
                }
                return (c8 == 'w' && cArr[i + 2] == 'i' && cArr[i + 3] == 't' && cArr[i + 4] == 'c' && cArr[i + 5] == 'h') ? 80 : 22;
            case 't':
                if (i2 == 3) {
                    return (cArr[i + 1] == 'r' && cArr[i + 2] == 'y') ? 82 : 22;
                }
                if (i2 == 4) {
                    char c9 = cArr[i + 1];
                    if (c9 == 'h') {
                        return (cArr[i + 2] == 'i' && cArr[i + 3] == 's') ? 35 : 22;
                    }
                    return (c9 == 'r' && cArr[i + 2] == 'u' && cArr[i + 3] == 'e') ? 42 : 22;
                }
                if (i2 == 5) {
                    return (cArr[i + 1] == 'h' && cArr[i + 2] == 'r' && cArr[i + 3] == 'o' && cArr[i + 4] == 'w') ? 81 : 22;
                }
                if (i2 != 6) {
                    return (i2 == 9 && cArr[i + 1] == 'r' && cArr[i + 2] == 'a' && cArr[i + 3] == 'n' && cArr[i + 4] == 's' && cArr[i + 5] == 'i' && cArr[i + 6] == 'e' && cArr[i + 7] == 'n' && cArr[i + 8] == 't') ? 58 : 22;
                }
                return (cArr[i + 1] == 'h' && cArr[i + 2] == 'r' && cArr[i + 3] == 'o' && cArr[i + 4] == 'w' && cArr[i + 5] == 's') ? 112 : 22;
            case 'v':
                if (i2 != 4) {
                    return (i2 == 8 && cArr[i + 1] == 'o' && cArr[i + 2] == 'l' && cArr[i + 3] == 'a' && cArr[i + 4] == 't' && cArr[i + 5] == 'i' && cArr[i + 6] == 'l' && cArr[i + 7] == 'e') ? 59 : 22;
                }
                return (cArr[i + 1] == 'o' && cArr[i + 2] == 'i' && cArr[i + 3] == 'd') ? 108 : 22;
            case 'w':
                return (i2 == 5 && cArr[i + 1] == 'h' && cArr[i + 2] == 'i' && cArr[i + 3] == 'l' && cArr[i + 4] == 'e') ? 71 : 22;
        }
    }

    public int scanNumber(boolean z) throws InvalidInputException {
        boolean z2;
        boolean z3 = false;
        boolean z4 = true;
        if (!z && this.currentCharacter == '0') {
            if (getNextChar('x', 'X') >= 0) {
                int i = this.currentPosition;
                consumeDigits(16, true);
                int i2 = this.currentPosition;
                if (getNextChar('l', 'L') >= 0) {
                    if (i2 != i) {
                        return 44;
                    }
                    throw new InvalidInputException(INVALID_HEXA);
                }
                if (getNextChar('.')) {
                    boolean z5 = i2 == i;
                    int i3 = this.currentPosition;
                    consumeDigits(16, true);
                    int i4 = this.currentPosition;
                    if (z5 && i4 == i3) {
                        if (this.sourceLevel < ClassFileConstants.JDK1_5) {
                            throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                        }
                        throw new InvalidInputException(INVALID_HEXA);
                    }
                    if (getNextChar(Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL, 'P') < 0) {
                        if (this.sourceLevel < ClassFileConstants.JDK1_5) {
                            throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                        }
                        throw new InvalidInputException(INVALID_HEXA);
                    }
                    this.unicodeAsBackSlash = false;
                    char[] cArr = this.source;
                    int i5 = this.currentPosition;
                    int i6 = i5 + 1;
                    this.currentPosition = i6;
                    char c = cArr[i5];
                    this.currentCharacter = c;
                    if (c == '\\' && cArr[i6] == 'u') {
                        getNextUnicodeChar();
                    } else if (this.withoutUnicodePtr != 0) {
                        unicodeStore();
                    }
                    char c2 = this.currentCharacter;
                    if (c2 == '-' || c2 == '+') {
                        this.unicodeAsBackSlash = false;
                        char[] cArr2 = this.source;
                        int i7 = this.currentPosition;
                        int i8 = i7 + 1;
                        this.currentPosition = i8;
                        char c3 = cArr2[i7];
                        this.currentCharacter = c3;
                        if (c3 == '\\' && cArr2[i8] == 'u') {
                            getNextUnicodeChar();
                        } else if (this.withoutUnicodePtr != 0) {
                            unicodeStore();
                        }
                    }
                    if (!ScannerHelper.isDigit(this.currentCharacter)) {
                        if (this.sourceLevel < ClassFileConstants.JDK1_5) {
                            throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                        }
                        if (this.currentCharacter == '_') {
                            consumeDigits(10);
                            throw new InvalidInputException(INVALID_UNDERSCORE);
                        }
                        throw new InvalidInputException(INVALID_HEXA);
                    }
                    consumeDigits(10);
                    if (getNextChar('f', Util.C_FLOAT) >= 0) {
                        if (this.sourceLevel >= ClassFileConstants.JDK1_5) {
                            return 45;
                        }
                        throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                    }
                    if (getNextChar(Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, Util.C_DOUBLE) >= 0) {
                        if (this.sourceLevel >= ClassFileConstants.JDK1_5) {
                            return 46;
                        }
                        throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                    }
                    if (getNextChar('l', 'L') >= 0) {
                        if (this.sourceLevel < ClassFileConstants.JDK1_5) {
                            throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                        }
                        throw new InvalidInputException(INVALID_HEXA);
                    }
                    if (this.sourceLevel >= ClassFileConstants.JDK1_5) {
                        return 46;
                    }
                    throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                }
                if (getNextChar(Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL, 'P') < 0) {
                    if (i2 != i) {
                        return 43;
                    }
                    throw new InvalidInputException(INVALID_HEXA);
                }
                if (i2 == i) {
                    if (this.sourceLevel < ClassFileConstants.JDK1_5) {
                        throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                    }
                    throw new InvalidInputException(INVALID_HEXA);
                }
                this.unicodeAsBackSlash = false;
                char[] cArr3 = this.source;
                int i9 = this.currentPosition;
                int i10 = i9 + 1;
                this.currentPosition = i10;
                char c4 = cArr3[i9];
                this.currentCharacter = c4;
                if (c4 == '\\' && cArr3[i10] == 'u') {
                    getNextUnicodeChar();
                } else if (this.withoutUnicodePtr != 0) {
                    unicodeStore();
                }
                char c5 = this.currentCharacter;
                if (c5 == '-' || c5 == '+') {
                    this.unicodeAsBackSlash = false;
                    char[] cArr4 = this.source;
                    int i11 = this.currentPosition;
                    int i12 = i11 + 1;
                    this.currentPosition = i12;
                    char c6 = cArr4[i11];
                    this.currentCharacter = c6;
                    if (c6 == '\\' && cArr4[i12] == 'u') {
                        getNextUnicodeChar();
                    } else if (this.withoutUnicodePtr != 0) {
                        unicodeStore();
                    }
                }
                if (!ScannerHelper.isDigit(this.currentCharacter)) {
                    if (this.sourceLevel < ClassFileConstants.JDK1_5) {
                        throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                    }
                    if (this.currentCharacter == '_') {
                        consumeDigits(10);
                        throw new InvalidInputException(INVALID_UNDERSCORE);
                    }
                    throw new InvalidInputException(INVALID_FLOAT);
                }
                consumeDigits(10);
                if (getNextChar('f', Util.C_FLOAT) >= 0) {
                    if (this.sourceLevel >= ClassFileConstants.JDK1_5) {
                        return 45;
                    }
                    throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                }
                if (getNextChar(Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, Util.C_DOUBLE) >= 0) {
                    if (this.sourceLevel >= ClassFileConstants.JDK1_5) {
                        return 46;
                    }
                    throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                }
                if (getNextChar('l', 'L') >= 0) {
                    if (this.sourceLevel < ClassFileConstants.JDK1_5) {
                        throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
                    }
                    throw new InvalidInputException(INVALID_HEXA);
                }
                if (this.sourceLevel >= ClassFileConstants.JDK1_5) {
                    return 46;
                }
                throw new InvalidInputException(ILLEGAL_HEXA_LITERAL);
            }
            if (getNextChar('b', Util.C_BYTE) >= 0) {
                int i13 = this.currentPosition;
                consumeDigits(2, true);
                if (this.currentPosition == i13) {
                    if (this.sourceLevel < ClassFileConstants.JDK1_7) {
                        throw new InvalidInputException(BINARY_LITERAL_NOT_BELOW_17);
                    }
                    throw new InvalidInputException(INVALID_BINARY);
                }
                if (getNextChar('l', 'L') >= 0) {
                    if (this.sourceLevel >= ClassFileConstants.JDK1_7) {
                        return 44;
                    }
                    throw new InvalidInputException(BINARY_LITERAL_NOT_BELOW_17);
                }
                if (this.sourceLevel >= ClassFileConstants.JDK1_7) {
                    return 43;
                }
                throw new InvalidInputException(BINARY_LITERAL_NOT_BELOW_17);
            }
            if (getNextCharAsDigit()) {
                consumeDigits(10);
                if (getNextChar('l', 'L') >= 0) {
                    return 44;
                }
                if (getNextChar('f', Util.C_FLOAT) >= 0) {
                    return 45;
                }
                if (getNextChar(Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, Util.C_DOUBLE) >= 0) {
                    return 46;
                }
                if (getNextChar('.')) {
                    consumeDigits(10);
                    z4 = false;
                }
                if (getNextChar('e', 'E') >= 0) {
                    this.unicodeAsBackSlash = false;
                    char[] cArr5 = this.source;
                    int i14 = this.currentPosition;
                    int i15 = i14 + 1;
                    this.currentPosition = i15;
                    char c7 = cArr5[i14];
                    this.currentCharacter = c7;
                    if (c7 == '\\' && cArr5[i15] == 'u') {
                        getNextUnicodeChar();
                    } else if (this.withoutUnicodePtr != 0) {
                        unicodeStore();
                    }
                    char c8 = this.currentCharacter;
                    if (c8 == '-' || c8 == '+') {
                        this.unicodeAsBackSlash = false;
                        char[] cArr6 = this.source;
                        int i16 = this.currentPosition;
                        int i17 = i16 + 1;
                        this.currentPosition = i17;
                        char c9 = cArr6[i16];
                        this.currentCharacter = c9;
                        if (c9 == '\\' && cArr6[i17] == 'u') {
                            getNextUnicodeChar();
                        } else if (this.withoutUnicodePtr != 0) {
                            unicodeStore();
                        }
                    }
                    if (!ScannerHelper.isDigit(this.currentCharacter)) {
                        if (this.currentCharacter == '_') {
                            consumeDigits(10);
                            throw new InvalidInputException(INVALID_UNDERSCORE);
                        }
                        throw new InvalidInputException(INVALID_FLOAT);
                    }
                    consumeDigits(10);
                } else {
                    z3 = z4;
                }
                if (getNextChar('f', Util.C_FLOAT) >= 0) {
                    return 45;
                }
                return (getNextChar(Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, Util.C_DOUBLE) >= 0 || !z3) ? 46 : 43;
            }
        }
        consumeDigits(10);
        if (!z && getNextChar('l', 'L') >= 0) {
            return 44;
        }
        if (z || !getNextChar('.')) {
            z2 = z;
        } else {
            consumeDigits(10, true);
            z2 = true;
        }
        if (getNextChar('e', 'E') >= 0) {
            this.unicodeAsBackSlash = false;
            char[] cArr7 = this.source;
            int i18 = this.currentPosition;
            int i19 = i18 + 1;
            this.currentPosition = i19;
            char c10 = cArr7[i18];
            this.currentCharacter = c10;
            if (c10 == '\\' && cArr7[i19] == 'u') {
                getNextUnicodeChar();
            } else if (this.withoutUnicodePtr != 0) {
                unicodeStore();
            }
            char c11 = this.currentCharacter;
            if (c11 == '-' || c11 == '+') {
                this.unicodeAsBackSlash = false;
                char[] cArr8 = this.source;
                int i20 = this.currentPosition;
                int i21 = i20 + 1;
                this.currentPosition = i21;
                char c12 = cArr8[i20];
                this.currentCharacter = c12;
                if (c12 == '\\' && cArr8[i21] == 'u') {
                    getNextUnicodeChar();
                } else if (this.withoutUnicodePtr != 0) {
                    unicodeStore();
                }
            }
            if (!ScannerHelper.isDigit(this.currentCharacter)) {
                if (this.currentCharacter == '_') {
                    consumeDigits(10);
                    throw new InvalidInputException(INVALID_UNDERSCORE);
                }
                throw new InvalidInputException(INVALID_FLOAT);
            }
            consumeDigits(10);
        } else {
            z4 = z2;
        }
        if (getNextChar(Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, Util.C_DOUBLE) >= 0) {
            return 46;
        }
        if (getNextChar('f', Util.C_FLOAT) >= 0) {
            return 45;
        }
        return z4 ? 46 : 43;
    }

    public final int getLineNumber(int i) {
        return Util.getLineNumber(i, this.lineEnds, 0, this.linePtr);
    }

    public final void setSource(char[] cArr) {
        int length;
        if (cArr == null) {
            this.source = CharOperation.NO_CHAR;
            length = 0;
        } else {
            this.source = cArr;
            length = cArr.length;
        }
        this.startPosition = -1;
        this.eofPosition = length;
        this.currentPosition = 0;
        this.initialPosition = 0;
        this.containsAssertKeyword = false;
        this.linePtr = -1;
    }

    public final void setSource(char[] cArr, CompilationResult compilationResult) {
        if (cArr == null) {
            setSource(compilationResult.compilationUnit.getContents());
        } else {
            setSource(cArr);
        }
        int[] iArr = compilationResult.lineSeparatorPositions;
        if (iArr != null) {
            this.lineEnds = iArr;
            this.linePtr = iArr.length - 1;
        }
    }

    public final void setSource(CompilationResult compilationResult) {
        setSource(null, compilationResult);
    }

    public String toString() {
        int i = this.startPosition;
        int i2 = this.eofPosition;
        if (i == i2) {
            return "EOF\n\n".concat(new String(this.source));
        }
        int i3 = this.currentPosition;
        if (i3 > i2) {
            return "behind the EOF\n\n".concat(new String(this.source));
        }
        if (i3 <= 0) {
            return "NOT started!\n\n".concat(this.source != null ? new String(this.source) : "");
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i4 = this.startPosition;
        if (i4 < 1000) {
            stringBuffer.append(this.source, 0, i4);
        } else {
            stringBuffer.append("<source beginning>\n...\n");
            int lineStart = getLineStart(Util.getLineNumber(this.startPosition - 1000, this.lineEnds, 0, this.linePtr));
            stringBuffer.append(this.source, lineStart, this.startPosition - lineStart);
        }
        stringBuffer.append("\n===============================\nStarts here -->");
        int i5 = this.currentPosition - 1;
        int i6 = this.startPosition;
        int i7 = (i5 - i6) + 1;
        if (i7 > -1) {
            stringBuffer.append(this.source, i6, i7);
        }
        if (this.nextToken != 0) {
            stringBuffer.append("<-- Ends here [in pipeline " + toStringAction(this.nextToken) + "]\n===============================\n");
        } else {
            stringBuffer.append("<-- Ends here\n===============================\n");
        }
        char[] cArr = this.source;
        int i8 = this.currentPosition;
        stringBuffer.append(cArr, i8, (this.eofPosition - (i8 - 1)) - 1);
        return stringBuffer.toString();
    }

    public String toStringAction(int i) {
        if (i == 67) {
            return "class";
        }
        if (i == 68) {
            return PsiKeyword.INTERFACE;
        }
        if (i == 70) {
            return ContainerUtils.KEY_VALUE_DELIMITER;
        }
        if (i == 71) {
            return PsiKeyword.WHILE;
        }
        if (i == 114) {
            return PsiKeyword.IMPLEMENTS;
        }
        if (i == 1000) {
            return "white_space(" + new String(getCurrentTokenSource()) + Constant.AFTER_QUTO;
        }
        switch (i) {
            case 1:
                return "++";
            case 2:
                return "--";
            case 3:
                return ".";
            case 4:
                return Marker.ANY_NON_NULL_MARKER;
            case 5:
                return Parameters.DEFAULT_OPTION_PREFIXES;
            case 6:
                return "*";
            case 7:
                return "::";
            case 8:
                return "%";
            case 9:
                return "/";
            case 10:
                return "[";
            case 11:
                return "<";
            case 12:
                return "<=";
            case 13:
                return ">=";
            case 14:
                return ">>";
            case 15:
                return ">";
            case 16:
                return ">>>";
            case 17:
                return PsiKeyword.INSTANCEOF;
            case 18:
                return "<<";
            case 19:
                return "==";
            case 20:
                return "!=";
            case 21:
                return ContainerUtils.FIELD_DELIMITER;
            case 22:
                return "Identifier(" + new String(getCurrentTokenSource()) + Constant.AFTER_QUTO;
            case 23:
                return "^";
            case 24:
                return "(";
            case 25:
                return Constant.AFTER_QUTO;
            case 26:
                return "|";
            default:
                switch (i) {
                    case 28:
                        return ";";
                    case 29:
                        return TypeDescription.Generic.OfWildcardType.SYMBOL;
                    case 30:
                        return "&&";
                    case 31:
                        return "||";
                    case 32:
                        return "}";
                    case 33:
                        return ",";
                    case 34:
                        return PsiKeyword.SUPER;
                    case 35:
                        return "this";
                    case 36:
                        return PsiKeyword.NEW;
                    default:
                        switch (i) {
                            case 38:
                                return "false";
                            case 39:
                                return "null";
                            case 40:
                                return "static";
                            case 41:
                                return "synchronized";
                            case 42:
                                return "true";
                            case 43:
                                return "Integer(" + new String(getCurrentTokenSource()) + Constant.AFTER_QUTO;
                            case 44:
                                return "Long(" + new String(getCurrentTokenSource()) + Constant.AFTER_QUTO;
                            case 45:
                                return "Float(" + new String(getCurrentTokenSource()) + Constant.AFTER_QUTO;
                            case 46:
                                return "Double(" + new String(getCurrentTokenSource()) + Constant.AFTER_QUTO;
                            case 47:
                                return "Char(" + new String(getCurrentTokenSource()) + Constant.AFTER_QUTO;
                            case 48:
                                return "String(" + new String(getCurrentTokenSource()) + Constant.AFTER_QUTO;
                            case 49:
                                return "{";
                            default:
                                switch (i) {
                                    case 51:
                                        return "abstract";
                                    case 52:
                                        return "final";
                                    case 53:
                                        return "native";
                                    case 54:
                                        return "private";
                                    case 55:
                                        return "protected";
                                    case 56:
                                        return "public";
                                    default:
                                        switch (i) {
                                            case 58:
                                                return "transient";
                                            case 59:
                                                return "volatile";
                                            case 60:
                                                return "EOF";
                                            case 61:
                                                return ":";
                                            case 62:
                                                return "!";
                                            case 63:
                                                return "~";
                                            case 64:
                                                return "]";
                                            default:
                                                switch (i) {
                                                    case 73:
                                                        return PsiKeyword.BREAK;
                                                    case 74:
                                                        return PsiKeyword.CONTINUE;
                                                    case 75:
                                                        return "default";
                                                    case 76:
                                                        return PsiKeyword.DO;
                                                    case 77:
                                                        return PsiKeyword.FOR;
                                                    case 78:
                                                        return PsiKeyword.IF;
                                                    case 79:
                                                        return "return";
                                                    case 80:
                                                        return PsiKeyword.SWITCH;
                                                    case 81:
                                                        return PsiKeyword.THROW;
                                                    case 82:
                                                        return PsiKeyword.TRY;
                                                    default:
                                                        switch (i) {
                                                            case 84:
                                                                return "+=";
                                                            case 85:
                                                                return "-=";
                                                            case 86:
                                                                return "*=";
                                                            case 87:
                                                                return "/=";
                                                            case 88:
                                                                return "&=";
                                                            case 89:
                                                                return "|=";
                                                            case 90:
                                                                return "^=";
                                                            case 91:
                                                                return "%=";
                                                            case 92:
                                                                return "<<=";
                                                            case 93:
                                                                return ">>=";
                                                            case 94:
                                                                return ">>>=";
                                                            case 95:
                                                                return "package";
                                                            case 96:
                                                                return PsiKeyword.EXTENDS;
                                                            case 97:
                                                                return "boolean";
                                                            case 98:
                                                                return "byte";
                                                            case 99:
                                                                return PsiKeyword.CASE;
                                                            case 100:
                                                                return PsiKeyword.CATCH;
                                                            case 101:
                                                                return PsiKeyword.CHAR;
                                                            case 102:
                                                                return "double";
                                                            case 103:
                                                                return "float";
                                                            case 104:
                                                                return PsiKeyword.IMPORT;
                                                            case 105:
                                                                return "int";
                                                            case 106:
                                                                return "long";
                                                            case 107:
                                                                return "short";
                                                            case 108:
                                                                return PsiKeyword.VOID;
                                                            case 109:
                                                                return PsiKeyword.FINALLY;
                                                            case 110:
                                                                return "->";
                                                            case 111:
                                                                return PsiKeyword.ELSE;
                                                            case 112:
                                                                return PsiKeyword.THROWS;
                                                            default:
                                                                return "not-a-token";
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
    }

    public void unicodeInitializeBuffer(int i) {
        this.withoutUnicodePtr = i;
        if (this.withoutUnicodeBuffer == null) {
            this.withoutUnicodeBuffer = new char[i + 11];
        }
        char[] cArr = this.withoutUnicodeBuffer;
        int length = cArr.length;
        if (i + 1 >= length) {
            char[] cArr2 = new char[i + 11];
            this.withoutUnicodeBuffer = cArr2;
            System.arraycopy(cArr, 0, cArr2, 0, length);
        }
        System.arraycopy(this.source, this.startPosition, this.withoutUnicodeBuffer, 1, i);
    }

    public void unicodeStore() {
        int i = this.withoutUnicodePtr + 1;
        this.withoutUnicodePtr = i;
        if (this.withoutUnicodeBuffer == null) {
            this.withoutUnicodeBuffer = new char[10];
        }
        char[] cArr = this.withoutUnicodeBuffer;
        int length = cArr.length;
        if (i == length) {
            char[] cArr2 = new char[length * 2];
            this.withoutUnicodeBuffer = cArr2;
            System.arraycopy(cArr, 0, cArr2, 0, length);
        }
        this.withoutUnicodeBuffer[i] = this.currentCharacter;
    }

    public void unicodeStore(char c) {
        int i = this.withoutUnicodePtr + 1;
        this.withoutUnicodePtr = i;
        if (this.withoutUnicodeBuffer == null) {
            this.withoutUnicodeBuffer = new char[10];
        }
        char[] cArr = this.withoutUnicodeBuffer;
        int length = cArr.length;
        if (i == length) {
            char[] cArr2 = new char[length * 2];
            this.withoutUnicodeBuffer = cArr2;
            System.arraycopy(cArr, 0, cArr2, 0, length);
        }
        this.withoutUnicodeBuffer[i] = c;
    }

    private static final class VanguardScanner extends Scanner {
        public VanguardScanner(long j, long j2) {
            super(false, false, false, j, j2, null, null, false);
        }

        @Override // org.eclipse.jdt.internal.compiler.parser.Scanner
        public int getNextToken() throws InvalidInputException {
            if (this.nextToken != 0) {
                int i = this.nextToken;
                this.nextToken = 0;
                return i;
            }
            int nextToken0 = getNextToken0();
            if (nextToken0 == 37 && atTypeAnnotation()) {
                nextToken0 = ((VanguardParser) this.activeParser).currentGoal == Goal.LambdaParameterListGoal ? disambiguatedToken(nextToken0) : 27;
            }
            if (nextToken0 == 60) {
                return 0;
            }
            return nextToken0;
        }
    }

    private static final class Goal {
        static Goal BlockStatementoptGoal;
        static int BlockStatementoptRule;
        static Goal IntersectionCastGoal;
        static int IntersectionCastRule;
        static Goal LambdaParameterListGoal;
        static int LambdaParameterListRule;
        static Goal ReferenceExpressionGoal;
        static int ReferenceExpressionRule;
        static Goal VarargTypeAnnotationGoal;
        static int VarargTypeAnnotationsRule;
        int first;
        int[] follow;
        int rule;

        static {
            for (int i = 1; i <= 800; i++) {
                if ("ParenthesizedLambdaParameterList".equals(Parser.f629name[Parser.non_terminal_index[Parser.lhs[i]]])) {
                    LambdaParameterListRule = i;
                } else if ("ParenthesizedCastNameAndBounds".equals(Parser.f629name[Parser.non_terminal_index[Parser.lhs[i]]])) {
                    IntersectionCastRule = i;
                } else if ("ReferenceExpressionTypeArgumentsAndTrunk".equals(Parser.f629name[Parser.non_terminal_index[Parser.lhs[i]]])) {
                    ReferenceExpressionRule = i;
                } else if ("TypeAnnotations".equals(Parser.f629name[Parser.non_terminal_index[Parser.lhs[i]]])) {
                    VarargTypeAnnotationsRule = i;
                } else if ("BlockStatementopt".equals(Parser.f629name[Parser.non_terminal_index[Parser.lhs[i]]])) {
                    BlockStatementoptRule = i;
                }
            }
            LambdaParameterListGoal = new Goal(110, new int[]{110}, LambdaParameterListRule);
            IntersectionCastGoal = new Goal(24, followSetOfCast(), IntersectionCastRule);
            VarargTypeAnnotationGoal = new Goal(37, new int[]{113}, VarargTypeAnnotationsRule);
            ReferenceExpressionGoal = new Goal(11, new int[]{7}, ReferenceExpressionRule);
            BlockStatementoptGoal = new Goal(49, new int[0], BlockStatementoptRule);
        }

        Goal(int i, int[] iArr, int i2) {
            this.first = i;
            this.follow = iArr;
            this.rule = i2;
        }

        boolean hasBeenReached(int i, int i2) {
            if (i == this.rule) {
                int length = this.follow.length;
                if (length == 0) {
                    return true;
                }
                for (int i3 = 0; i3 < length; i3++) {
                    if (this.follow[i3] == i2) {
                        return true;
                    }
                }
            }
            return false;
        }

        private static int[] followSetOfCast() {
            return new int[]{22, 36, 34, 35, 38, 42, 39, 43, 44, 45, 46, 47, 48, 62, 63, 24};
        }
    }

    private static final class VanguardParser extends Parser {
        public static final boolean FAILURE = false;
        public static final boolean SUCCESS = true;
        Goal currentGoal;

        public VanguardParser(VanguardScanner vanguardScanner) {
            this.scanner = vanguardScanner;
        }

        protected boolean parse(Goal goal) {
            this.currentGoal = goal;
            try {
                this.stateStackTop = -1;
                this.currentToken = goal.first;
                int iTAction = ParserBasicInformation.START_STATE;
                while (true) {
                    int length = this.stack.length;
                    int i = this.stateStackTop + 1;
                    this.stateStackTop = i;
                    if (i >= length) {
                        int[] iArr = this.stack;
                        int[] iArr2 = new int[length + 255];
                        this.stack = iArr2;
                        System.arraycopy(iArr, 0, iArr2, 0, length);
                    }
                    this.stack[this.stateStackTop] = iTAction;
                    iTAction = Parser.tAction(iTAction, this.currentToken);
                    if (iTAction == 16382) {
                        return false;
                    }
                    if (iTAction > 800) {
                        if (iTAction > 16382) {
                            this.unstackedAct = iTAction;
                            try {
                                this.currentToken = this.scanner.getNextToken();
                                this.unstackedAct = 16382;
                                iTAction -= 16382;
                            } catch (Throwable th) {
                                this.unstackedAct = 16382;
                                throw th;
                            }
                        } else {
                            if (iTAction >= 16381) {
                                return false;
                            }
                            this.unstackedAct = iTAction;
                            try {
                                this.currentToken = this.scanner.getNextToken();
                                this.unstackedAct = 16382;
                            } catch (Throwable th2) {
                                this.unstackedAct = 16382;
                                throw th2;
                            }
                        }
                        return false;
                    }
                    this.stateStackTop--;
                    while (!goal.hasBeenReached(iTAction, this.currentToken)) {
                        this.stateStackTop -= Parser.rhs[iTAction] - 1;
                        iTAction = Parser.ntAction(this.stack[this.stateStackTop], Parser.lhs[iTAction]);
                        if (iTAction > 800) {
                        }
                    }
                    return true;
                }
            } catch (Exception unused) {
            }
        }

        @Override // org.eclipse.jdt.internal.compiler.parser.Parser
        public String toString() {
            return "\n\n\n----------------Scanner--------------\n" + this.scanner.toString();
        }
    }

    private VanguardParser getVanguardParser() {
        if (this.vanguardParser == null) {
            this.vanguardScanner = new VanguardScanner(this.sourceLevel, this.complianceLevel);
            VanguardParser vanguardParser = new VanguardParser(this.vanguardScanner);
            this.vanguardParser = vanguardParser;
            this.vanguardScanner.setActiveParser(vanguardParser);
        }
        this.vanguardScanner.setSource(this.source);
        this.vanguardScanner.resetTo(this.startPosition, this.eofPosition - 1);
        return this.vanguardParser;
    }

    protected final boolean maybeAtLambdaOrCast() {
        int i = this.lookBack[1];
        if (i == 22 || i == 41 || i == 71 || i == 80 || i == 82 || i == 100 || i == 34 || i == 35 || i == 77 || i == 78) {
            return false;
        }
        return this.activeParser.atConflictScenario(24);
    }

    /*  JADX ERROR: UnsupportedOperationException in pass: RegionMakerVisitor
        java.lang.UnsupportedOperationException
        	at java.base/java.util.Collections$UnmodifiableCollection.add(Collections.java:1093)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker$1.leaveRegion(SwitchRegionMaker.java:419)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:91)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:31)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaksForCase(SwitchRegionMaker.java:399)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaks(SwitchRegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.leaveRegion(PostProcessRegions.java:31)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:91)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.process(PostProcessRegions.java:21)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:31)
        */
    protected final boolean maybeAtReferenceExpression() {
        /*
            r5 = this;
            int[] r0 = r5.lookBack
            r1 = 1
            r1 = r0[r1]
            r2 = 11
            if (r1 == 0) goto L57
            r3 = 22
            r4 = 0
            if (r1 == r3) goto Lf
            return r4
        Lf:
            r0 = r0[r4]
            if (r0 == r2) goto L56
            r1 = 17
            if (r0 == r1) goto L56
            r1 = 21
            if (r0 == r1) goto L56
            r1 = 28
            if (r0 == r1) goto L56
            r1 = 32
            if (r0 == r1) goto L56
            r1 = 34
            if (r0 == r1) goto L56
            r1 = 40
            if (r0 == r1) goto L56
            r1 = 96
            if (r0 == r1) goto L56
            r1 = 112(0x70, float:1.57E-43)
            if (r0 == r1) goto L56
            r1 = 114(0x72, float:1.6E-43)
            if (r0 == r1) goto L56
            r1 = 14
            if (r0 == r1) goto L56
            r1 = 15
            if (r0 == r1) goto L56
            r1 = 36
            if (r0 == r1) goto L56
            r1 = 37
            if (r0 == r1) goto L56
            r1 = 51
            if (r0 == r1) goto L56
            r1 = 52
            if (r0 == r1) goto L56
            switch(r0) {
                case 54: goto L56;
                case 55: goto L56;
                case 56: goto L56;
                default: goto L52;
            }
        L52:
            switch(r0) {
                case 67: goto L56;
                case 68: goto L56;
                case 69: goto L56;
                default: goto L55;
            }
        L55:
            goto L57
        L56:
            return r4
        L57:
            org.eclipse.jdt.internal.compiler.parser.ConflictedParser r0 = r5.activeParser
            boolean r0 = r0.atConflictScenario(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.parser.Scanner.maybeAtReferenceExpression():boolean");
    }

    private final boolean maybeAtEllipsisAnnotationsStart() {
        int i;
        return (this.consumingEllipsisAnnotations || (i = this.lookBack[1]) == 3 || i == 11 || i == 17 || i == 21 || i == 36 || i == 49 || i == 96 || i == 112 || i == 114 || i == 33 || i == 34) ? false : true;
    }

    protected final boolean atTypeAnnotation() {
        return !this.activeParser.atConflictScenario(37);
    }

    public void setActiveParser(ConflictedParser conflictedParser) {
        this.activeParser = conflictedParser;
        int[] iArr = this.lookBack;
        iArr[1] = 0;
        iArr[0] = 0;
    }

    int disambiguatedToken(int i) {
        VanguardParser vanguardParser = getVanguardParser();
        if (i == 24 && maybeAtLambdaOrCast()) {
            if (vanguardParser.parse(Goal.LambdaParameterListGoal)) {
                this.nextToken = 24;
                return 50;
            }
            this.vanguardScanner.resetTo(this.startPosition, this.eofPosition - 1);
            if (!vanguardParser.parse(Goal.IntersectionCastGoal)) {
                return i;
            }
            this.nextToken = 24;
            return 65;
        }
        if (i == 11 && maybeAtReferenceExpression()) {
            if (!vanguardParser.parse(Goal.ReferenceExpressionGoal)) {
                return i;
            }
            this.nextToken = 11;
            return 83;
        }
        if (i != 37 || !atTypeAnnotation()) {
            return i;
        }
        if (!maybeAtEllipsisAnnotationsStart() || !vanguardParser.parse(Goal.VarargTypeAnnotationGoal)) {
            return 27;
        }
        this.consumingEllipsisAnnotations = true;
        this.nextToken = 27;
        return 115;
    }

    public int fastForward(Statement statement) {
        while (true) {
            try {
                int nextToken = getNextToken();
                if (nextToken != 1 && nextToken != 2 && nextToken != 11) {
                    if (nextToken != 22) {
                        if (nextToken != 24) {
                            if (nextToken == 32) {
                                ungetToken(nextToken);
                                return 28;
                            }
                            if (nextToken != 27) {
                                if (nextToken != 28) {
                                    switch (nextToken) {
                                        case 34:
                                        case 35:
                                        case 36:
                                        case 37:
                                        case 38:
                                        case 39:
                                        case 40:
                                        case 41:
                                        case 42:
                                        case 43:
                                        case 44:
                                        case 45:
                                        case 46:
                                        case 47:
                                        case 48:
                                        case 49:
                                        case 50:
                                        case 51:
                                        case 52:
                                        case 53:
                                        case 54:
                                        case 55:
                                        case 56:
                                        case 57:
                                        case 58:
                                        case 59:
                                            break;
                                        case 60:
                                            break;
                                        default:
                                            switch (nextToken) {
                                                case 67:
                                                case 68:
                                                case 69:
                                                    break;
                                                default:
                                                    switch (nextToken) {
                                                        case 71:
                                                        case 72:
                                                        case 73:
                                                        case 74:
                                                        case 75:
                                                        case 76:
                                                        case 77:
                                                        case 78:
                                                        case 79:
                                                        case 80:
                                                        case 81:
                                                        case 82:
                                                            break;
                                                        default:
                                                            switch (nextToken) {
                                                                case 97:
                                                                case 98:
                                                                case 99:
                                                                    break;
                                                                default:
                                                                    switch (nextToken) {
                                                                        case 101:
                                                                        case 102:
                                                                        case 103:
                                                                            break;
                                                                        default:
                                                                            switch (nextToken) {
                                                                                case 105:
                                                                                case 106:
                                                                                case 107:
                                                                                case 108:
                                                                                    break;
                                                                                default:
                                                                                    continue;
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
                                }
                                return nextToken;
                            }
                        }
                    } else if (isAtAssistIdentifier()) {
                        return nextToken;
                    }
                }
                if (getVanguardParser().parse(Goal.BlockStatementoptGoal)) {
                    return nextToken;
                }
            } catch (InvalidInputException unused) {
                return 60;
            }
        }
    }
}
