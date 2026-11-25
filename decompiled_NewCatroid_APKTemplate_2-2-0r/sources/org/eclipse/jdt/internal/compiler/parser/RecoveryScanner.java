package org.eclipse.jdt.internal.compiler.parser;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.InvalidInputException;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveryScanner extends Scanner {
    public static final char[] FAKE_IDENTIFIER = "$missing$".toCharArray();
    private RecoveryScannerData data;
    private char[] fakeTokenSource;
    private boolean isInserted;
    private int[] pendingTokens;
    private int pendingTokensPtr;
    private boolean precededByRemoved;
    public boolean record;
    private int skipNextInsertedTokens;

    public RecoveryScanner(Scanner scanner, RecoveryScannerData recoveryScannerData) {
        super(false, scanner.tokenizeWhiteSpace, scanner.checkNonExternalizedStringLiterals, scanner.sourceLevel, scanner.complianceLevel, scanner.taskTags, scanner.taskPriorities, scanner.isTaskCaseSensitive);
        this.pendingTokensPtr = -1;
        this.fakeTokenSource = null;
        this.isInserted = true;
        this.precededByRemoved = false;
        this.skipNextInsertedTokens = -1;
        this.record = true;
        setData(recoveryScannerData);
    }

    public RecoveryScanner(boolean z, boolean z2, long j, long j2, char[][] cArr, char[][] cArr2, boolean z3, RecoveryScannerData recoveryScannerData) {
        super(false, z, z2, j, j2, cArr, cArr2, z3);
        this.pendingTokensPtr = -1;
        this.fakeTokenSource = null;
        this.isInserted = true;
        this.precededByRemoved = false;
        this.skipNextInsertedTokens = -1;
        this.record = true;
        setData(recoveryScannerData);
    }

    public void insertToken(int i, int i2, int i3) {
        insertTokens(new int[]{i}, i2, i3);
    }

    private int[] reverse(int[] iArr) {
        int length = iArr.length;
        int i = length / 2;
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = iArr[i2];
            int i4 = (length - i2) - 1;
            iArr[i2] = iArr[i4];
            iArr[i4] = i3;
        }
        return iArr;
    }

    public void insertTokens(int[] iArr, int i, int i2) {
        if (this.record) {
            if (i <= -1 || Parser.statements_recovery_filter[i] == 0) {
                this.data.insertedTokensPtr++;
                if (this.data.insertedTokens == null) {
                    this.data.insertedTokens = new int[10][];
                    this.data.insertedTokensPosition = new int[10];
                    this.data.insertedTokenUsed = new boolean[10];
                } else if (this.data.insertedTokens.length == this.data.insertedTokensPtr) {
                    int length = this.data.insertedTokens.length;
                    int[][] iArr2 = this.data.insertedTokens;
                    int i3 = length * 2;
                    int[][] iArr3 = new int[i3][];
                    this.data.insertedTokens = iArr3;
                    System.arraycopy(iArr2, 0, iArr3, 0, length);
                    int[] iArr4 = this.data.insertedTokensPosition;
                    int[] iArr5 = new int[i3];
                    this.data.insertedTokensPosition = iArr5;
                    System.arraycopy(iArr4, 0, iArr5, 0, length);
                    boolean[] zArr = this.data.insertedTokenUsed;
                    boolean[] zArr2 = new boolean[i3];
                    this.data.insertedTokenUsed = zArr2;
                    System.arraycopy(zArr, 0, zArr2, 0, length);
                }
                this.data.insertedTokens[this.data.insertedTokensPtr] = reverse(iArr);
                this.data.insertedTokensPosition[this.data.insertedTokensPtr] = i2;
                this.data.insertedTokenUsed[this.data.insertedTokensPtr] = false;
            }
        }
    }

    public void insertTokenAhead(int i, int i2) {
        if (this.record) {
            int length = this.data.insertedTokens[i2].length;
            int[] iArr = new int[length + 1];
            System.arraycopy(this.data.insertedTokens[i2], 0, iArr, 1, length);
            iArr[0] = i;
            this.data.insertedTokens[i2] = iArr;
        }
    }

    public void replaceTokens(int i, int i2, int i3) {
        replaceTokens(new int[]{i}, i2, i3);
    }

    public void replaceTokens(int[] iArr, int i, int i2) {
        if (this.record) {
            this.data.replacedTokensPtr++;
            if (this.data.replacedTokensStart == null) {
                this.data.replacedTokens = new int[10][];
                this.data.replacedTokensStart = new int[10];
                this.data.replacedTokensEnd = new int[10];
                this.data.replacedTokenUsed = new boolean[10];
            } else if (this.data.replacedTokensStart.length == this.data.replacedTokensPtr) {
                int length = this.data.replacedTokensStart.length;
                int[][] iArr2 = this.data.replacedTokens;
                int i3 = length * 2;
                int[][] iArr3 = new int[i3][];
                this.data.replacedTokens = iArr3;
                System.arraycopy(iArr2, 0, iArr3, 0, length);
                int[] iArr4 = this.data.replacedTokensStart;
                int[] iArr5 = new int[i3];
                this.data.replacedTokensStart = iArr5;
                System.arraycopy(iArr4, 0, iArr5, 0, length);
                int[] iArr6 = this.data.replacedTokensEnd;
                int[] iArr7 = new int[i3];
                this.data.replacedTokensEnd = iArr7;
                System.arraycopy(iArr6, 0, iArr7, 0, length);
                boolean[] zArr = this.data.replacedTokenUsed;
                boolean[] zArr2 = new boolean[i3];
                this.data.replacedTokenUsed = zArr2;
                System.arraycopy(zArr, 0, zArr2, 0, length);
            }
            this.data.replacedTokens[this.data.replacedTokensPtr] = reverse(iArr);
            this.data.replacedTokensStart[this.data.replacedTokensPtr] = i;
            this.data.replacedTokensEnd[this.data.replacedTokensPtr] = i2;
            this.data.replacedTokenUsed[this.data.replacedTokensPtr] = false;
        }
    }

    public void removeTokens(int i, int i2) {
        if (this.record) {
            this.data.removedTokensPtr++;
            if (this.data.removedTokensStart == null) {
                this.data.removedTokensStart = new int[10];
                this.data.removedTokensEnd = new int[10];
                this.data.removedTokenUsed = new boolean[10];
            } else if (this.data.removedTokensStart.length == this.data.removedTokensPtr) {
                int length = this.data.removedTokensStart.length;
                int[] iArr = this.data.removedTokensStart;
                int i3 = length * 2;
                int[] iArr2 = new int[i3];
                this.data.removedTokensStart = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
                int[] iArr3 = this.data.removedTokensEnd;
                int[] iArr4 = new int[i3];
                this.data.removedTokensEnd = iArr4;
                System.arraycopy(iArr3, 0, iArr4, 0, length);
                boolean[] zArr = this.data.removedTokenUsed;
                boolean[] zArr2 = new boolean[i3];
                this.data.removedTokenUsed = zArr2;
                System.arraycopy(zArr, 0, zArr2, 0, length);
            }
            this.data.removedTokensStart[this.data.removedTokensPtr] = i;
            this.data.removedTokensEnd[this.data.removedTokensPtr] = i2;
            this.data.removedTokenUsed[this.data.removedTokensPtr] = false;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.Scanner
    protected int getNextToken0() throws InvalidInputException {
        int i = this.pendingTokensPtr;
        if (i > -1) {
            int[] iArr = this.pendingTokens;
            this.pendingTokensPtr = i - 1;
            int i2 = iArr[i];
            if (i2 == 22) {
                this.fakeTokenSource = FAKE_IDENTIFIER;
            } else {
                this.fakeTokenSource = CharOperation.NO_CHAR;
            }
            return i2;
        }
        this.fakeTokenSource = null;
        this.precededByRemoved = false;
        if (this.data.insertedTokens != null) {
            for (int i3 = 0; i3 <= this.data.insertedTokensPtr; i3++) {
                if (this.data.insertedTokensPosition[i3] == this.currentPosition - 1 && i3 > this.skipNextInsertedTokens) {
                    this.data.insertedTokenUsed[i3] = true;
                    this.pendingTokens = this.data.insertedTokens[i3];
                    this.pendingTokensPtr = this.data.insertedTokens[i3].length - 1;
                    this.isInserted = true;
                    this.startPosition = this.currentPosition;
                    this.skipNextInsertedTokens = i3;
                    int[] iArr2 = this.pendingTokens;
                    int i4 = this.pendingTokensPtr;
                    this.pendingTokensPtr = i4 - 1;
                    int i5 = iArr2[i4];
                    if (i5 == 22) {
                        this.fakeTokenSource = FAKE_IDENTIFIER;
                    } else {
                        this.fakeTokenSource = CharOperation.NO_CHAR;
                    }
                    return i5;
                }
            }
            this.skipNextInsertedTokens = -1;
        }
        int i6 = this.currentPosition;
        int nextToken0 = super.getNextToken0();
        if (this.data.replacedTokens != null) {
            for (int i7 = 0; i7 <= this.data.replacedTokensPtr; i7++) {
                if (this.data.replacedTokensStart[i7] >= i6 && this.data.replacedTokensStart[i7] <= this.startPosition && this.data.replacedTokensEnd[i7] >= this.currentPosition - 1) {
                    this.data.replacedTokenUsed[i7] = true;
                    this.pendingTokens = this.data.replacedTokens[i7];
                    this.pendingTokensPtr = this.data.replacedTokens[i7].length - 1;
                    char[] cArr = FAKE_IDENTIFIER;
                    this.fakeTokenSource = cArr;
                    this.isInserted = false;
                    this.currentPosition = this.data.replacedTokensEnd[i7] + 1;
                    int[] iArr3 = this.pendingTokens;
                    int i8 = this.pendingTokensPtr;
                    this.pendingTokensPtr = i8 - 1;
                    int i9 = iArr3[i8];
                    if (i9 == 22) {
                        this.fakeTokenSource = cArr;
                    } else {
                        this.fakeTokenSource = CharOperation.NO_CHAR;
                    }
                    return i9;
                }
            }
        }
        if (this.data.removedTokensStart != null) {
            for (int i10 = 0; i10 <= this.data.removedTokensPtr; i10++) {
                if (this.data.removedTokensStart[i10] >= i6 && this.data.removedTokensStart[i10] <= this.startPosition && this.data.removedTokensEnd[i10] >= this.currentPosition - 1) {
                    this.data.removedTokenUsed[i10] = true;
                    this.currentPosition = this.data.removedTokensEnd[i10] + 1;
                    this.precededByRemoved = false;
                    return getNextToken0();
                }
            }
        }
        return nextToken0;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.Scanner
    public char[] getCurrentIdentifierSource() {
        char[] cArr = this.fakeTokenSource;
        return cArr != null ? cArr : super.getCurrentIdentifierSource();
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.Scanner
    public char[] getCurrentTokenSourceString() {
        char[] cArr = this.fakeTokenSource;
        return cArr != null ? cArr : super.getCurrentTokenSourceString();
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.Scanner
    public char[] getCurrentTokenSource() {
        char[] cArr = this.fakeTokenSource;
        return cArr != null ? cArr : super.getCurrentTokenSource();
    }

    public RecoveryScannerData getData() {
        return this.data;
    }

    public boolean isFakeToken() {
        return this.fakeTokenSource != null;
    }

    public boolean isInsertedToken() {
        return this.fakeTokenSource != null && this.isInserted;
    }

    public boolean isReplacedToken() {
        return (this.fakeTokenSource == null || this.isInserted) ? false : true;
    }

    public boolean isPrecededByRemovedToken() {
        return this.precededByRemoved;
    }

    public void setData(RecoveryScannerData recoveryScannerData) {
        if (recoveryScannerData == null) {
            this.data = new RecoveryScannerData();
        } else {
            this.data = recoveryScannerData;
        }
    }

    public void setPendingTokens(int[] iArr) {
        this.pendingTokens = iArr;
        this.pendingTokensPtr = iArr.length - 1;
    }
}
