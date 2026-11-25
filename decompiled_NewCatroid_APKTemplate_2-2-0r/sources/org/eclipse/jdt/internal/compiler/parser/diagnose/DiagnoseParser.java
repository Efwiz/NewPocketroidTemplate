package org.eclipse.jdt.internal.compiler.parser.diagnose;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.parser.ConflictedParser;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.eclipse.jdt.internal.compiler.parser.RecoveryScanner;
import org.eclipse.jdt.internal.compiler.parser.ScannerHelper;
import org.eclipse.jdt.internal.compiler.parser.TerminalTokens;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class DiagnoseParser implements ParserBasicInformation, TerminalTokens, ConflictedParser {
    private static final int BEFORE_CODE = 2;
    private static final int BUFF_SIZE = 32;
    private static final int BUFF_UBOUND = 31;
    private static final boolean DEBUG = false;
    private static final int DELETION_CODE = 6;
    private static final int EOF_CODE = 11;
    private static final int INSERTION_CODE = 3;
    private static final int INVALID_CODE = 4;
    private static final int MAX_DISTANCE = 30;
    private static final int MERGE_CODE = 7;
    private static final int MIN_DISTANCE = 3;
    private static final int MISPLACED_CODE = 8;
    private static final int NIL = -1;
    private static final int SCOPE_CODE = 9;
    private static final int SECONDARY_CODE = 10;
    private static final int STACK_INCREMENT = 256;
    private static final int SUBSTITUTION_CODE = 5;
    private boolean DEBUG_PARSECHECK;
    int[] buffer;
    private int currentToken;
    private int errorToken;
    private int errorTokenStart;
    private LexStream lexStream;
    int[] list;
    private int[] locationStack;
    private int[] locationStartStack;
    private int[] nextStack;
    private int nextStackTop;
    private CompilerOptions options;
    private Parser parser;
    private int[] prevStack;
    private int prevStackTop;
    private RecoveryScanner recoveryScanner;
    private boolean reportProblem;
    private int[] scopeIndex;
    private int[] scopePosition;
    private int scopeStackTop;
    private int[] stack;
    private int stackLength;
    StateInfo[] statePool;
    int statePoolTop;
    int[] stateSeen;
    private int stateStackTop;
    private int[] tempStack;
    private int tempStackTop;

    private static class RepairCandidate {
        public int symbol = 0;
        public int location = 0;
    }

    private static class PrimaryRepairInfo {
        public int distance = 0;
        public int misspellIndex = 0;
        public int code = 0;
        public int bufferPosition = 0;
        public int symbol = 0;

        public PrimaryRepairInfo copy() {
            PrimaryRepairInfo primaryRepairInfo = new PrimaryRepairInfo();
            primaryRepairInfo.distance = this.distance;
            primaryRepairInfo.misspellIndex = this.misspellIndex;
            primaryRepairInfo.code = this.code;
            primaryRepairInfo.bufferPosition = this.bufferPosition;
            primaryRepairInfo.symbol = this.symbol;
            return primaryRepairInfo;
        }
    }

    static class SecondaryRepairInfo {
        public int bufferPosition;
        public int code;
        public int distance;
        public int numDeletions;
        boolean recoveryOnNextStack;
        public int stackPosition;
        public int symbol;

        SecondaryRepairInfo() {
        }
    }

    private static class StateInfo {
        int next;
        int state;

        public StateInfo(int i, int i2) {
            this.state = i;
            this.next = i2;
        }
    }

    public DiagnoseParser(Parser parser, int i, int i2, int i3, CompilerOptions compilerOptions) {
        this(parser, i, i2, i3, Util.EMPTY_INT_ARRAY, Util.EMPTY_INT_ARRAY, Util.EMPTY_INT_ARRAY, compilerOptions);
    }

    public DiagnoseParser(Parser parser, int i, int i2, int i3, int[] iArr, int[] iArr2, int[] iArr3, CompilerOptions compilerOptions) {
        this.DEBUG_PARSECHECK = false;
        this.currentToken = 0;
        this.list = new int[479];
        this.buffer = new int[32];
        this.parser = parser;
        this.options = compilerOptions;
        this.lexStream = new LexStream(32, parser.scanner, iArr, iArr2, iArr3, i, i2, i3);
        this.recoveryScanner = parser.recoveryScanner;
    }

    private ProblemReporter problemReporter() {
        return this.parser.problemReporter();
    }

    private void reallocateStacks() {
        int i = this.stackLength;
        int i2 = i + 256;
        this.stackLength = i2;
        if (i == 0) {
            this.stack = new int[i2];
            this.locationStack = new int[i2];
            this.locationStartStack = new int[i2];
            this.tempStack = new int[i2];
            this.prevStack = new int[i2];
            this.nextStack = new int[i2];
            this.scopeIndex = new int[i2];
            this.scopePosition = new int[i2];
            return;
        }
        int[] iArr = this.stack;
        int[] iArr2 = new int[i2];
        this.stack = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, i);
        int[] iArr3 = this.locationStack;
        int[] iArr4 = new int[this.stackLength];
        this.locationStack = iArr4;
        System.arraycopy(iArr3, 0, iArr4, 0, i);
        int[] iArr5 = this.locationStartStack;
        int[] iArr6 = new int[this.stackLength];
        this.locationStartStack = iArr6;
        System.arraycopy(iArr5, 0, iArr6, 0, i);
        int[] iArr7 = this.tempStack;
        int[] iArr8 = new int[this.stackLength];
        this.tempStack = iArr8;
        System.arraycopy(iArr7, 0, iArr8, 0, i);
        int[] iArr9 = this.prevStack;
        int[] iArr10 = new int[this.stackLength];
        this.prevStack = iArr10;
        System.arraycopy(iArr9, 0, iArr10, 0, i);
        int[] iArr11 = this.nextStack;
        int[] iArr12 = new int[this.stackLength];
        this.nextStack = iArr12;
        System.arraycopy(iArr11, 0, iArr12, 0, i);
        int[] iArr13 = this.scopeIndex;
        int[] iArr14 = new int[this.stackLength];
        this.scopeIndex = iArr14;
        System.arraycopy(iArr13, 0, iArr14, 0, i);
        int[] iArr15 = this.scopePosition;
        int[] iArr16 = new int[this.stackLength];
        this.scopePosition = iArr16;
        System.arraycopy(iArr15, 0, iArr16, 0, i);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x007e A[PHI: r0
      0x007e: PHI (r0v3 org.eclipse.jdt.internal.compiler.parser.RecoveryScanner) = 
      (r0v2 org.eclipse.jdt.internal.compiler.parser.RecoveryScanner)
      (r0v6 org.eclipse.jdt.internal.compiler.parser.RecoveryScanner)
      (r0v7 org.eclipse.jdt.internal.compiler.parser.RecoveryScanner)
     binds: [B:52:0x0129, B:23:0x007c, B:37:0x00aa] A[DONT_GENERATE, DONT_INLINE]] */
    public void diagnoseParse(boolean z) {
        boolean z2;
        int i;
        RecoveryScanner recoveryScanner;
        this.reportProblem = true;
        RecoveryScanner recoveryScanner2 = this.recoveryScanner;
        if (recoveryScanner2 != null) {
            z2 = recoveryScanner2.record;
            this.recoveryScanner.record = z;
        } else {
            z2 = false;
        }
        this.parser.scanner.setActiveParser(this);
        try {
            this.lexStream.reset();
            this.currentToken = this.lexStream.getToken();
            reallocateStacks();
            this.stateStackTop = 0;
            int[] iArr = this.stack;
            int iTAction = ParserBasicInformation.START_STATE;
            iArr[0] = 1580;
            int iKind = this.lexStream.kind(this.currentToken);
            int[] iArr2 = this.locationStack;
            int i2 = this.stateStackTop;
            int i3 = this.currentToken;
            iArr2[i2] = i3;
            this.locationStartStack[i2] = this.lexStream.start(i3);
            while (true) {
                int i4 = -1;
                this.prevStackTop = -1;
                this.nextStackTop = -1;
                int i5 = this.stateStackTop;
                this.tempStackTop = i5 - 1;
                for (int i6 = 0; i6 <= this.stateStackTop; i6++) {
                    this.tempStack[i6] = this.stack[i6];
                }
                iTAction = Parser.tAction(iTAction, iKind);
                while (iTAction <= 800) {
                    do {
                        int i7 = this.tempStackTop - (Parser.rhs[iTAction] - 1);
                        this.tempStackTop = i7;
                        iTAction = Parser.ntAction(this.tempStack[i7], Parser.lhs[iTAction]);
                    } while (iTAction <= 800);
                    if (this.tempStackTop + 1 >= this.stackLength) {
                        reallocateStacks();
                    }
                    int i8 = this.tempStackTop;
                    if (i5 >= i8) {
                        i5 = i8;
                    }
                    this.tempStack[i8 + 1] = iTAction;
                    iTAction = Parser.tAction(iTAction, iKind);
                }
                int i9 = i5;
                int i10 = -1;
                while (true) {
                    if (iTAction <= 16382 && iTAction >= 16381) {
                        break;
                    }
                    this.nextStackTop = this.tempStackTop + 1;
                    for (int i11 = i4 + 1; i11 <= this.nextStackTop; i11++) {
                        this.nextStack[i11] = this.tempStack[i11];
                    }
                    for (int i12 = i9 + 1; i12 <= this.nextStackTop; i12++) {
                        int[] iArr3 = this.locationStack;
                        int i13 = this.stateStackTop;
                        iArr3[i12] = iArr3[i13];
                        int[] iArr4 = this.locationStartStack;
                        iArr4[i12] = iArr4[i13];
                    }
                    if (iTAction > 16382) {
                        iTAction -= 16382;
                        do {
                            int i14 = this.nextStackTop - (Parser.rhs[iTAction] - 1);
                            this.nextStackTop = i14;
                            iTAction = Parser.ntAction(this.nextStack[i14], Parser.lhs[iTAction]);
                        } while (iTAction <= 800);
                        int i15 = this.nextStackTop;
                        if (i9 >= i15) {
                            i9 = i15;
                        }
                    }
                    if (this.nextStackTop + 1 >= this.stackLength) {
                        reallocateStacks();
                    }
                    int i16 = this.nextStackTop;
                    this.tempStackTop = i16;
                    int[] iArr5 = this.nextStack;
                    int i17 = i16 + 1;
                    this.nextStackTop = i17;
                    iArr5[i17] = iTAction;
                    int token = this.lexStream.getToken();
                    this.currentToken = token;
                    int iKind2 = this.lexStream.kind(token);
                    iTAction = Parser.tAction(iTAction, iKind2);
                    while (iTAction <= 800) {
                        do {
                            char c = Parser.lhs[iTAction];
                            int i18 = this.tempStackTop - (Parser.rhs[iTAction] - 1);
                            this.tempStackTop = i18;
                            iTAction = Parser.ntAction(i18 > i17 ? this.tempStack[i18] : this.nextStack[i18], c);
                        } while (iTAction <= 800);
                        if (this.tempStackTop + 1 >= this.stackLength) {
                            reallocateStacks();
                        }
                        int i19 = this.tempStackTop;
                        if (i17 >= i19) {
                            i17 = i19;
                        }
                        this.tempStack[i19 + 1] = iTAction;
                        iTAction = Parser.tAction(iTAction, iKind2);
                    }
                    if (iTAction != 16382) {
                        this.prevStackTop = this.stateStackTop;
                        while (true) {
                            i10++;
                            if (i10 > this.prevStackTop) {
                                break;
                            } else {
                                this.prevStack[i10] = this.stack[i10];
                            }
                        }
                        this.stateStackTop = this.nextStackTop;
                        int i20 = i9 + 1;
                        while (true) {
                            i = this.stateStackTop;
                            if (i20 > i) {
                                break;
                            }
                            this.stack[i20] = this.nextStack[i20];
                            i20++;
                        }
                        int[] iArr6 = this.locationStack;
                        int i21 = this.currentToken;
                        iArr6[i] = i21;
                        this.locationStartStack[i] = this.lexStream.start(i21);
                        i10 = i9;
                        i9 = i17;
                        iKind = iKind2;
                        i4 = i9;
                    } else {
                        i4 = i17;
                        iKind = iKind2;
                    }
                }
                if (iTAction == 16382) {
                    RepairCandidate repairCandidateErrorRecovery = errorRecovery(this.currentToken, false);
                    if (this.parser.reportOnlyOneSyntaxError) {
                        recoveryScanner = this.recoveryScanner;
                        if (recoveryScanner != null) {
                            recoveryScanner.record = z2;
                        }
                    } else {
                        if (this.parser.problemReporter().options.maxProblemsPerUnit < this.parser.compilationUnit.compilationResult.problemCount) {
                            RecoveryScanner recoveryScanner3 = this.recoveryScanner;
                            if (recoveryScanner3 == null || !recoveryScanner3.record) {
                                recoveryScanner = this.recoveryScanner;
                                if (recoveryScanner != null) {
                                    recoveryScanner.record = z2;
                                }
                            } else {
                                this.reportProblem = false;
                            }
                        }
                        iTAction = this.stack[this.stateStackTop];
                        if (repairCandidateErrorRecovery.symbol != 0) {
                            if (repairCandidateErrorRecovery.symbol > 118) {
                                int iNtAction = Parser.ntAction(iTAction, repairCandidateErrorRecovery.symbol - 118);
                                while (iNtAction <= 800) {
                                    int i22 = this.stateStackTop - (Parser.rhs[iNtAction] - 1);
                                    this.stateStackTop = i22;
                                    iNtAction = Parser.ntAction(this.stack[i22], Parser.lhs[iNtAction]);
                                }
                                int[] iArr7 = this.stack;
                                int i23 = this.stateStackTop + 1;
                                this.stateStackTop = i23;
                                iArr7[i23] = iNtAction;
                                int token2 = this.lexStream.getToken();
                                this.currentToken = token2;
                                int iKind3 = this.lexStream.kind(token2);
                                int[] iArr8 = this.locationStack;
                                int i24 = this.stateStackTop;
                                int i25 = this.currentToken;
                                iArr8[i24] = i25;
                                this.locationStartStack[i24] = this.lexStream.start(i25);
                                iTAction = iNtAction;
                                iKind = iKind3;
                            } else {
                                int i26 = repairCandidateErrorRecovery.symbol;
                                this.locationStack[this.stateStackTop] = repairCandidateErrorRecovery.location;
                                this.locationStartStack[this.stateStackTop] = this.lexStream.start(repairCandidateErrorRecovery.location);
                                iKind = i26;
                            }
                        }
                        recoveryScanner = this.recoveryScanner;
                        if (recoveryScanner != null) {
                            recoveryScanner.record = z2;
                        }
                    }
                    this.parser.scanner.setActiveParser(null);
                    return;
                }
                if (iTAction == 16381) {
                    recoveryScanner = this.recoveryScanner;
                    if (recoveryScanner != null) {
                        recoveryScanner.record = z2;
                    }
                    this.parser.scanner.setActiveParser(null);
                    return;
                }
            }
        } catch (Throwable th) {
            RecoveryScanner recoveryScanner4 = this.recoveryScanner;
            if (recoveryScanner4 != null) {
                recoveryScanner4.record = z2;
            }
            this.parser.scanner.setActiveParser(null);
            throw th;
        }
    }

    private static char[] displayEscapeCharacters(char[] cArr, int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < i; i3++) {
            stringBuffer.append(cArr[i3]);
        }
        while (i < i2) {
            Util.appendEscapedChar(stringBuffer, cArr[i], true);
            i++;
        }
        while (i2 < cArr.length) {
            stringBuffer.append(cArr[i2]);
            i2++;
        }
        return stringBuffer.toString().toCharArray();
    }

    private RepairCandidate errorRecovery(int i, boolean z) {
        this.errorToken = i;
        this.errorTokenStart = this.lexStream.start(i);
        int iPrevious = this.lexStream.previous(i);
        int iKind = this.lexStream.kind(iPrevious);
        int i2 = 0;
        if (z) {
            reportError(3, Parser.terminal_index[49], iPrevious, iPrevious);
            RepairCandidate repairCandidate = new RepairCandidate();
            repairCandidate.symbol = 49;
            repairCandidate.location = i;
            this.lexStream.reset(i);
            this.stateStackTop = this.nextStackTop;
            while (true) {
                int i3 = this.stateStackTop;
                if (i2 <= i3) {
                    this.stack[i2] = this.nextStack[i2];
                    i2++;
                } else {
                    this.locationStack[i3] = i;
                    this.locationStartStack[i3] = this.lexStream.start(i);
                    return repairCandidate;
                }
            }
        } else {
            RepairCandidate repairCandidatePrimaryPhase = primaryPhase(i);
            if (repairCandidatePrimaryPhase.symbol != 0) {
                return repairCandidatePrimaryPhase;
            }
            RepairCandidate repairCandidateSecondaryPhase = secondaryPhase(i);
            if (repairCandidateSecondaryPhase.symbol != 0) {
                return repairCandidateSecondaryPhase;
            }
            if (this.lexStream.kind(i) == 60) {
                reportError(11, Parser.terminal_index[60], iPrevious, iPrevious);
                repairCandidateSecondaryPhase.symbol = 0;
                repairCandidateSecondaryPhase.location = i;
                return repairCandidateSecondaryPhase;
            }
            do {
                int i4 = 31;
                if (this.lexStream.kind(this.buffer[31]) != 60) {
                    repairCandidateSecondaryPhase = secondaryPhase(this.buffer[29]);
                } else {
                    while (this.lexStream.kind(this.buffer[i4]) == 60) {
                        i4--;
                    }
                    reportError(6, Parser.terminal_index[iKind], i, this.buffer[i4]);
                    repairCandidateSecondaryPhase.symbol = 0;
                    repairCandidateSecondaryPhase.location = this.buffer[i4];
                    return repairCandidateSecondaryPhase;
                }
            } while (repairCandidateSecondaryPhase.symbol == 0);
            return repairCandidateSecondaryPhase;
        }
    }

    private RepairCandidate primaryPhase(int i) {
        int i2;
        PrimaryRepairInfo primaryRepairInfo = new PrimaryRepairInfo();
        RepairCandidate repairCandidate = new RepairCandidate();
        int i3 = this.nextStackTop >= 0 ? 3 : 2;
        this.buffer[i3] = i;
        for (int i4 = i3; i4 > 0; i4--) {
            int[] iArr = this.buffer;
            iArr[i4 - 1] = this.lexStream.previous(iArr[i4]);
        }
        for (int i5 = i3 + 1; i5 < 32; i5++) {
            int[] iArr2 = this.buffer;
            iArr2[i5] = this.lexStream.next(iArr2[i5 - 1]);
        }
        if (this.nextStackTop >= 0) {
            primaryRepairInfo.bufferPosition = 3;
            primaryRepairInfo = checkPrimaryDistance(this.nextStack, this.nextStackTop, primaryRepairInfo);
        }
        PrimaryRepairInfo primaryRepairInfoCopy = primaryRepairInfo.copy();
        primaryRepairInfoCopy.bufferPosition = 2;
        PrimaryRepairInfo primaryRepairInfoCheckPrimaryDistance = checkPrimaryDistance(this.stack, this.stateStackTop, primaryRepairInfoCopy);
        if (primaryRepairInfoCheckPrimaryDistance.distance > primaryRepairInfo.distance || primaryRepairInfoCheckPrimaryDistance.misspellIndex > primaryRepairInfo.misspellIndex) {
            primaryRepairInfo = primaryRepairInfoCheckPrimaryDistance;
        }
        if (this.prevStackTop >= 0) {
            PrimaryRepairInfo primaryRepairInfoCopy2 = primaryRepairInfo.copy();
            primaryRepairInfoCopy2.bufferPosition = 1;
            PrimaryRepairInfo primaryRepairInfoCheckPrimaryDistance2 = checkPrimaryDistance(this.prevStack, this.prevStackTop, primaryRepairInfoCopy2);
            if (primaryRepairInfoCheckPrimaryDistance2.distance > primaryRepairInfo.distance || primaryRepairInfoCheckPrimaryDistance2.misspellIndex > primaryRepairInfo.misspellIndex) {
                primaryRepairInfo = primaryRepairInfoCheckPrimaryDistance2;
            }
        }
        int i6 = this.nextStackTop;
        if (i6 >= 0) {
            if (secondaryCheck(this.nextStack, i6, 3, primaryRepairInfo.distance)) {
                return repairCandidate;
            }
        } else if (secondaryCheck(this.stack, this.stateStackTop, 2, primaryRepairInfo.distance)) {
            return repairCandidate;
        }
        primaryRepairInfo.distance = (primaryRepairInfo.distance - primaryRepairInfo.bufferPosition) + 1;
        if (primaryRepairInfo.code == 4 || primaryRepairInfo.code == 6 || primaryRepairInfo.code == 5 || primaryRepairInfo.code == 7) {
            primaryRepairInfo.distance--;
        }
        if (primaryRepairInfo.distance < 3) {
            return repairCandidate;
        }
        if (primaryRepairInfo.code == 3 && this.buffer[primaryRepairInfo.bufferPosition - 1] == 0) {
            primaryRepairInfo.code = 2;
        }
        int i7 = 0;
        if (primaryRepairInfo.bufferPosition == 1) {
            this.stateStackTop = this.prevStackTop;
            while (i7 <= this.stateStackTop) {
                this.stack[i7] = this.prevStack[i7];
                i7++;
            }
        } else if (this.nextStackTop >= 0 && primaryRepairInfo.bufferPosition >= 3) {
            this.stateStackTop = this.nextStackTop;
            while (true) {
                i2 = this.stateStackTop;
                if (i7 > i2) {
                    break;
                }
                this.stack[i7] = this.nextStack[i7];
                i7++;
            }
            int[] iArr3 = this.locationStack;
            int[] iArr4 = this.buffer;
            iArr3[i2] = iArr4[3];
            this.locationStartStack[i2] = this.lexStream.start(iArr4[3]);
        }
        return primaryDiagnosis(primaryRepairInfo);
    }

    private int mergeCandidate(int i, int i2) {
        char[] cArrName = this.lexStream.name(this.buffer[i2]);
        char[] cArrName2 = this.lexStream.name(this.buffer[i2 + 1]);
        int length = cArrName.length + cArrName2.length;
        char[] cArrConcat = CharOperation.concat(cArrName, cArrName2);
        for (int iAsi = Parser.asi(i); Parser.asr[iAsi] != 0; iAsi++) {
            char c = Parser.terminal_index[Parser.asr[iAsi]];
            if (length == Parser.f629name[c].length() && CharOperation.equals(cArrConcat, Parser.f629name[c].toCharArray(), false)) {
                return Parser.asr[iAsi];
            }
        }
        return 0;
    }

    private PrimaryRepairInfo checkPrimaryDistance(int[] iArr, int i, PrimaryRepairInfo primaryRepairInfo) {
        int i2;
        int iMergeCandidate;
        int check;
        PrimaryRepairInfo primaryRepairInfoScopeTrial = scopeTrial(iArr, i, primaryRepairInfo.copy());
        if (primaryRepairInfoScopeTrial.distance > primaryRepairInfo.distance) {
            primaryRepairInfo = primaryRepairInfoScopeTrial;
        }
        if (this.buffer[primaryRepairInfo.bufferPosition] != 0 && this.buffer[primaryRepairInfo.bufferPosition + 1] != 0 && (iMergeCandidate = mergeCandidate(iArr[i], primaryRepairInfo.bufferPosition)) != 0 && ((check = parseCheck(iArr, i, iMergeCandidate, primaryRepairInfo.bufferPosition + 2)) > primaryRepairInfo.distance || (check == primaryRepairInfo.distance && primaryRepairInfo.misspellIndex < 10))) {
            primaryRepairInfo.misspellIndex = 10;
            primaryRepairInfo.symbol = iMergeCandidate;
            primaryRepairInfo.distance = check;
            primaryRepairInfo.code = 7;
        }
        int check2 = parseCheck(iArr, i, this.lexStream.kind(this.buffer[primaryRepairInfo.bufferPosition + 1]), primaryRepairInfo.bufferPosition + 2);
        int i3 = (this.lexStream.kind(this.buffer[primaryRepairInfo.bufferPosition]) == 60 && this.lexStream.afterEol(this.buffer[primaryRepairInfo.bufferPosition + 1])) ? 10 : 0;
        if (check2 > primaryRepairInfo.distance || (check2 == primaryRepairInfo.distance && i3 > primaryRepairInfo.misspellIndex)) {
            primaryRepairInfo.misspellIndex = i3;
            primaryRepairInfo.code = 6;
            primaryRepairInfo.distance = check2;
        }
        int i4 = iArr[i];
        this.tempStackTop = i - 1;
        int iKind = this.lexStream.kind(this.buffer[primaryRepairInfo.bufferPosition]);
        this.lexStream.reset(this.buffer[primaryRepairInfo.bufferPosition + 1]);
        int iTAction = Parser.tAction(i4, iKind);
        int i5 = i;
        while (iTAction <= 800) {
            do {
                this.tempStackTop -= Parser.rhs[iTAction] - 1;
                char c = Parser.lhs[iTAction];
                int i6 = this.tempStackTop;
                if (i6 > i5) {
                    i2 = this.tempStack[i6];
                } else {
                    i2 = iArr[i6];
                }
                iTAction = Parser.ntAction(i2, c);
            } while (iTAction <= 800);
            int i7 = this.tempStackTop;
            if (i5 >= i7) {
                i5 = i7;
            }
            this.tempStack[i7 + 1] = iTAction;
            iTAction = Parser.tAction(iTAction, iKind);
            i4 = iTAction;
        }
        char c2 = 0;
        for (int iAsi = Parser.asi(i4); Parser.asr[iAsi] != 0; iAsi++) {
            char c3 = Parser.asr[iAsi];
            if (c3 != '<' && c3 != 'v') {
                if (c2 == 0) {
                    this.list[c3] = c3;
                } else {
                    int[] iArr2 = this.list;
                    iArr2[c3] = iArr2[c2];
                    iArr2[c2] = c3;
                }
                c2 = c3;
            }
        }
        int i8 = iArr[i];
        if (i8 != i4) {
            for (int iAsi2 = Parser.asi(i8); Parser.asr[iAsi2] != 0; iAsi2++) {
                char c4 = Parser.asr[iAsi2];
                if (c4 != '<' && c4 != 'v') {
                    int[] iArr3 = this.list;
                    if (iArr3[c4] == 0) {
                        if (c2 == 0) {
                            iArr3[c4] = c4;
                        } else {
                            iArr3[c4] = iArr3[c2];
                            iArr3[c2] = c4;
                        }
                        c2 = c4;
                    }
                }
            }
        }
        int[] iArr4 = this.list;
        int i9 = iArr4[c2];
        iArr4[c2] = 0;
        int i10 = i9;
        while (i10 != 0) {
            int i11 = (i10 == 60 && this.lexStream.afterEol(this.buffer[primaryRepairInfo.bufferPosition])) ? 10 : 0;
            int check3 = parseCheck(iArr, i, i10, primaryRepairInfo.bufferPosition);
            if (check3 > primaryRepairInfo.distance) {
                primaryRepairInfo.misspellIndex = i11;
                primaryRepairInfo.distance = check3;
                primaryRepairInfo.symbol = i10;
                primaryRepairInfo.code = 3;
            } else if (check3 == primaryRepairInfo.distance && i11 > primaryRepairInfo.misspellIndex) {
                primaryRepairInfo.misspellIndex = i11;
                primaryRepairInfo.distance = check3;
                primaryRepairInfo.symbol = i10;
                primaryRepairInfo.code = 3;
            }
            i10 = this.list[i10];
        }
        if (this.buffer[primaryRepairInfo.bufferPosition] != 0) {
            while (i9 != 0) {
                int iMisspell = (i9 == 60 && this.lexStream.afterEol(this.buffer[primaryRepairInfo.bufferPosition + 1])) ? 10 : misspell(i9, this.buffer[primaryRepairInfo.bufferPosition]);
                int check4 = parseCheck(iArr, i, i9, primaryRepairInfo.bufferPosition + 1);
                if (check4 > primaryRepairInfo.distance) {
                    primaryRepairInfo.misspellIndex = iMisspell;
                    primaryRepairInfo.distance = check4;
                    primaryRepairInfo.symbol = i9;
                    primaryRepairInfo.code = 5;
                } else if (check4 == primaryRepairInfo.distance && iMisspell > primaryRepairInfo.misspellIndex) {
                    primaryRepairInfo.misspellIndex = iMisspell;
                    primaryRepairInfo.symbol = i9;
                    primaryRepairInfo.code = 5;
                }
                int[] iArr5 = this.list;
                int i12 = iArr5[i9];
                iArr5[i9] = 0;
                i9 = i12;
            }
        }
        for (int iNasi = Parser.nasi(iArr[i]); Parser.nasr[iNasi] != 0; iNasi++) {
            int i13 = Parser.nasr[iNasi] + 'v';
            int check5 = parseCheck(iArr, i, i13, primaryRepairInfo.bufferPosition + 1);
            if (check5 > primaryRepairInfo.distance) {
                primaryRepairInfo.misspellIndex = 0;
                primaryRepairInfo.distance = check5;
                primaryRepairInfo.symbol = i13;
                primaryRepairInfo.code = 4;
            }
            int check6 = parseCheck(iArr, i, i13, primaryRepairInfo.bufferPosition);
            if (check6 > primaryRepairInfo.distance || (check6 == primaryRepairInfo.distance && primaryRepairInfo.code == 4)) {
                primaryRepairInfo.misspellIndex = 0;
                primaryRepairInfo.distance = check6;
                primaryRepairInfo.symbol = i13;
                primaryRepairInfo.code = 3;
            }
        }
        return primaryRepairInfo;
    }

    /* JADX WARN: Code duplicated, block: B:44:0x018c  */
    private RepairCandidate primaryDiagnosis(PrimaryRepairInfo primaryRepairInfo) {
        int termIndex;
        int termIndex2;
        int i;
        int i2 = this.buffer[primaryRepairInfo.bufferPosition - 1];
        int i3 = this.buffer[primaryRepairInfo.bufferPosition];
        int i4 = primaryRepairInfo.code;
        if (i4 == 2 || i4 == 3) {
            if (primaryRepairInfo.symbol > 118) {
                termIndex = getNtermIndex(this.stack[this.stateStackTop], primaryRepairInfo.symbol, primaryRepairInfo.bufferPosition);
            } else {
                termIndex = getTermIndex(this.stack, this.stateStackTop, primaryRepairInfo.symbol, primaryRepairInfo.bufferPosition);
            }
            if (primaryRepairInfo.code != 3) {
                i2 = i3;
            }
            reportError(primaryRepairInfo.code, termIndex, i2, i2);
        } else if (i4 == 4) {
            reportError(primaryRepairInfo.code, getNtermIndex(this.stack[this.stateStackTop], primaryRepairInfo.symbol, primaryRepairInfo.bufferPosition + 1), i3, i3);
        } else if (i4 == 5) {
            if (primaryRepairInfo.misspellIndex >= 6) {
                i = Parser.terminal_index[primaryRepairInfo.symbol];
            } else {
                termIndex2 = getTermIndex(this.stack, this.stateStackTop, primaryRepairInfo.symbol, primaryRepairInfo.bufferPosition + 1);
                if (termIndex2 != Parser.terminal_index[primaryRepairInfo.symbol]) {
                    i = termIndex2;
                    primaryRepairInfo.code = 4;
                    i = termIndex2;
                }
            }
            i = termIndex2;
            reportError(primaryRepairInfo.code, i, i3, i3);
        } else if (i4 == 7) {
            reportError(primaryRepairInfo.code, Parser.terminal_index[primaryRepairInfo.symbol], i3, this.lexStream.next(i3));
        } else if (i4 == 9) {
            for (int i5 = 0; i5 < this.scopeStackTop; i5++) {
                reportError(primaryRepairInfo.code, -this.scopeIndex[i5], this.locationStack[this.scopePosition[i5]], i2, Parser.non_terminal_index[Parser.scope_lhs[this.scopeIndex[i5]]]);
            }
            primaryRepairInfo.symbol = Parser.scope_lhs[this.scopeIndex[this.scopeStackTop]] + 'v';
            this.stateStackTop = this.scopePosition[this.scopeStackTop];
            int i6 = primaryRepairInfo.code;
            int[] iArr = this.scopeIndex;
            int i7 = this.scopeStackTop;
            reportError(i6, -iArr[i7], this.locationStack[this.scopePosition[i7]], i2, getNtermIndex(this.stack[this.stateStackTop], primaryRepairInfo.symbol, primaryRepairInfo.bufferPosition));
        } else {
            reportError(primaryRepairInfo.code, Parser.terminal_index[118], i3, i3);
        }
        RepairCandidate repairCandidate = new RepairCandidate();
        int i8 = primaryRepairInfo.code;
        if (i8 == 2 || i8 == 3) {
            repairCandidate.symbol = primaryRepairInfo.symbol;
            repairCandidate.location = this.buffer[primaryRepairInfo.bufferPosition];
            this.lexStream.reset(this.buffer[primaryRepairInfo.bufferPosition]);
        } else if (i8 == 4 || i8 == 5) {
            repairCandidate.symbol = primaryRepairInfo.symbol;
            repairCandidate.location = this.buffer[primaryRepairInfo.bufferPosition];
            this.lexStream.reset(this.buffer[primaryRepairInfo.bufferPosition + 1]);
        } else if (i8 == 7) {
            repairCandidate.symbol = primaryRepairInfo.symbol;
            repairCandidate.location = this.buffer[primaryRepairInfo.bufferPosition];
            this.lexStream.reset(this.buffer[primaryRepairInfo.bufferPosition + 2]);
        } else if (i8 == 9) {
            repairCandidate.symbol = primaryRepairInfo.symbol;
            repairCandidate.location = this.buffer[primaryRepairInfo.bufferPosition];
            this.lexStream.reset(this.buffer[primaryRepairInfo.bufferPosition]);
        } else {
            repairCandidate.location = this.buffer[primaryRepairInfo.bufferPosition + 1];
            repairCandidate.symbol = this.lexStream.kind(this.buffer[primaryRepairInfo.bufferPosition + 1]);
            this.lexStream.reset(this.buffer[primaryRepairInfo.bufferPosition + 2]);
        }
        return repairCandidate;
    }

    private int getTermIndex(int[] iArr, int i, int i2, int i3) {
        int iTAction;
        int i4;
        int i5;
        int i6 = iArr[i];
        this.tempStackTop = i - 1;
        this.lexStream.reset(this.buffer[i3]);
        int iTAction2 = Parser.tAction(i6, i2);
        while (iTAction2 <= 800) {
            do {
                this.tempStackTop -= Parser.rhs[iTAction2] - 1;
                char c = Parser.lhs[iTAction2];
                int i7 = this.tempStackTop;
                if (i7 > i) {
                    i5 = this.tempStack[i7];
                } else {
                    i5 = iArr[i7];
                }
                iTAction2 = Parser.ntAction(i5, c);
            } while (iTAction2 <= 800);
            int i8 = this.tempStackTop;
            if (i >= i8) {
                i = i8;
            }
            this.tempStack[i8 + 1] = iTAction2;
            iTAction2 = Parser.tAction(iTAction2, i2);
        }
        int i9 = this.tempStackTop + 1;
        this.tempStackTop = i9;
        int iKind = this.lexStream.kind(this.buffer[i3]);
        this.lexStream.reset(this.buffer[i3 + 1]);
        if (iTAction2 > 16382) {
            iTAction = iTAction2 - 16382;
        } else {
            this.tempStack[this.tempStackTop + 1] = iTAction2;
            iTAction = Parser.tAction(iTAction2, iKind);
        }
        while (iTAction <= 800) {
            do {
                int i10 = this.tempStackTop - (Parser.rhs[iTAction] - 1);
                this.tempStackTop = i10;
                if (i10 < i9) {
                    if (i2 > 118) {
                        return Parser.non_terminal_index[i2 - 118];
                    }
                    return Parser.terminal_index[i2];
                }
                char c2 = Parser.lhs[iTAction];
                int i11 = this.tempStackTop;
                if (i11 == i9) {
                    i2 = c2 + 'v';
                }
                if (i11 > i) {
                    i4 = this.tempStack[i11];
                } else {
                    i4 = iArr[i11];
                }
                iTAction = Parser.ntAction(i4, c2);
            } while (iTAction <= 800);
            this.tempStack[this.tempStackTop + 1] = iTAction;
            iTAction = Parser.tAction(iTAction, iKind);
        }
        if (i2 > 118) {
            return Parser.non_terminal_index[i2 - 118];
        }
        return Parser.terminal_index[i2];
    }

    private int getNtermIndex(int i, int i2, int i3) {
        int i4 = i2 - 118;
        int iKind = this.lexStream.kind(this.buffer[i3]);
        this.lexStream.reset(this.buffer[i3 + 1]);
        this.tempStackTop = 0;
        this.tempStack[0] = i;
        int iNtAction = Parser.ntAction(i, i4);
        if (iNtAction > 800) {
            this.tempStack[this.tempStackTop + 1] = iNtAction;
            iNtAction = Parser.tAction(iNtAction, iKind);
        }
        while (iNtAction <= 800) {
            do {
                int i5 = this.tempStackTop - (Parser.rhs[iNtAction] - 1);
                this.tempStackTop = i5;
                i4 = i4;
                if (i5 < 0) {
                    return Parser.non_terminal_index[i4];
                }
                if (i5 == 0) {
                    i4 = Parser.lhs[iNtAction];
                }
                iNtAction = Parser.ntAction(this.tempStack[this.tempStackTop], Parser.lhs[iNtAction]);
            } while (iNtAction <= 800);
            this.tempStack[this.tempStackTop + 1] = iNtAction;
            iNtAction = Parser.tAction(iNtAction, iKind);
        }
        return Parser.non_terminal_index[i4];
    }

    private int misspell(int i, int i2) {
        char[] charArray = Parser.f629name[Parser.terminal_index[i]].toCharArray();
        int length = charArray.length;
        char[] cArr = new char[length + 1];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            cArr[i4] = ScannerHelper.toLowerCase(charArray[i4]);
        }
        cArr[length] = 0;
        char[] cArrName = this.lexStream.name(i2);
        int length2 = cArrName.length;
        int i5 = length2 < 41 ? length2 : 41;
        char[] cArr2 = new char[i5 + 1];
        for (int i6 = 0; i6 < i5; i6++) {
            cArr2[i6] = ScannerHelper.toLowerCase(cArrName[i6]);
        }
        cArr2[i5] = 0;
        if (length == 1 && i5 == 1) {
            char c = cArr[0];
            if (c == ';' && cArr2[0] == ',') {
                return 3;
            }
            if (c == ',' && cArr2[0] == ';') {
                return 3;
            }
            if (c == ';' && cArr2[0] == ':') {
                return 3;
            }
            if (c == ':' && cArr2[0] == ';') {
                return 3;
            }
            if (c == '.' && cArr2[0] == ',') {
                return 3;
            }
            if (c == ',' && cArr2[0] == '.') {
                return 3;
            }
            if (c == '\'' && cArr2[0] == '\"') {
                return 3;
            }
            if (c == '\"' && cArr2[0] == '\'') {
                return 3;
            }
        }
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        while (i3 < length && i7 < i5) {
            char c2 = cArr[i3];
            char c3 = cArr2[i7];
            if (c2 == c3) {
                i9++;
                i3++;
                i7++;
                if (i8 == 0) {
                    i10++;
                }
            } else {
                int i11 = i3 + 1;
                char c4 = cArr[i11];
                if (c4 == c3 && c2 == cArr2[i7 + 1]) {
                    i9 += 2;
                    i3 += 2;
                    i7 += 2;
                } else {
                    int i12 = i7 + 1;
                    if (c4 == cArr2[i12]) {
                        i8++;
                        i7 = i12;
                        i3 = i11;
                    } else {
                        int i13 = length - i3;
                        int i14 = i5 - i7;
                        if (i13 > i14) {
                            i3 = i11;
                        } else if (i14 > i13) {
                            i7 = i12;
                        } else {
                            i7 = i12;
                            i3 = i11;
                        }
                    }
                }
                i8++;
            }
        }
        if (i3 < length || i7 < i5) {
            i8++;
        }
        if (length < i5) {
            i5 = length;
        }
        if (i8 > (i5 / 6) + 1) {
            i9 = i10;
        }
        int i15 = i9 * 10;
        if (length < length2) {
            length = length2;
        }
        return i15 / (length + i8);
    }

    private PrimaryRepairInfo scopeTrial(int[] iArr, int i, PrimaryRepairInfo primaryRepairInfo) {
        this.stateSeen = new int[this.stackLength];
        int i2 = 0;
        while (true) {
            int i3 = this.stackLength;
            if (i2 < i3) {
                this.stateSeen[i2] = -1;
                i2++;
            } else {
                this.statePoolTop = 0;
                this.statePool = new StateInfo[i3];
                scopeTrialCheck(iArr, i, primaryRepairInfo, 0);
                this.stateSeen = null;
                this.statePoolTop = 0;
                primaryRepairInfo.code = 9;
                primaryRepairInfo.misspellIndex = 10;
                return primaryRepairInfo;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:70:0x0148  */
    private void scopeTrialCheck(int[] iArr, int i, PrimaryRepairInfo primaryRepairInfo, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        if (i2 > 20) {
            return;
        }
        int i7 = iArr[i];
        int i8 = this.stateSeen[i];
        while (i8 != -1) {
            if (this.statePool[i8].state == i7) {
                return;
            } else {
                i8 = this.statePool[i8].next;
            }
        }
        int i9 = this.statePoolTop;
        int i10 = i9 + 1;
        this.statePoolTop = i10;
        StateInfo[] stateInfoArr = this.statePool;
        int i11 = 0;
        if (i10 >= stateInfoArr.length) {
            StateInfo[] stateInfoArr2 = new StateInfo[i10 * 2];
            this.statePool = stateInfoArr2;
            System.arraycopy(stateInfoArr, 0, stateInfoArr2, 0, i10);
        }
        this.statePool[i9] = new StateInfo(i7, this.stateSeen[i]);
        this.stateSeen[i] = i9;
        while (i11 < 291) {
            int i12 = iArr[i];
            this.tempStackTop = i - 1;
            byte b = Parser.scope_la[i11];
            this.lexStream.reset(this.buffer[primaryRepairInfo.bufferPosition]);
            int iTAction = Parser.tAction(i12, b);
            int i13 = i;
            while (true) {
                i3 = ParserBasicInformation.NUM_RULES;
                if (iTAction > 800) {
                    break;
                }
                int i14 = i11;
                do {
                    this.tempStackTop -= Parser.rhs[iTAction] - 1;
                    char c = Parser.lhs[iTAction];
                    int i15 = this.tempStackTop;
                    if (i15 > i13) {
                        i6 = this.tempStack[i15];
                    } else {
                        i6 = iArr[i15];
                    }
                    iTAction = Parser.ntAction(i6, c);
                } while (iTAction <= 800);
                int i16 = this.tempStackTop;
                if (i16 + 1 >= this.stackLength) {
                    return;
                }
                if (i13 >= i16) {
                    i13 = i16;
                }
                this.tempStack[i16 + 1] = iTAction;
                iTAction = Parser.tAction(iTAction, b);
                i11 = i14;
            }
            if (iTAction != 16382) {
                char c2 = Parser.scope_prefix[i11];
                int i17 = this.tempStackTop + 1;
                int i18 = c2;
                while (true) {
                    i5 = i13 + 1;
                    if (i17 < i5 || Parser.in_symbol(this.tempStack[i17]) != Parser.scope_rhs[i18]) {
                        break;
                    }
                    i17--;
                    i18++;
                }
                if (i17 == i13) {
                    i17 = i13;
                    while (i17 >= 1 && Parser.in_symbol(iArr[i17]) == Parser.scope_rhs[i18]) {
                        i18 = i18;
                        i17--;
                        i18++;
                    }
                }
                if (i13 >= i) {
                    i18 = i18;
                    i18 = i18;
                    i5 = i;
                }
                i18 = i18;
                i18 = i18;
                if (Parser.scope_rhs[i18] != 0 || i17 >= i5) {
                    i4 = i11;
                } else {
                    int i19 = Parser.scope_state_set[i11];
                    while (iArr[i17] != Parser.scope_state[i19] && Parser.scope_state[i19] != 0) {
                        i19++;
                    }
                    if (Parser.scope_state[i19] != 0) {
                        int i20 = primaryRepairInfo.distance;
                        int check = parseCheck(iArr, i17, Parser.scope_lhs[i11] + 'v', primaryRepairInfo.bufferPosition);
                        if ((check - primaryRepairInfo.bufferPosition) + 1 < 3) {
                            int iNtAction = Parser.ntAction(iArr[i17], Parser.scope_lhs[i11]);
                            int i21 = i17;
                            while (true) {
                                if (iNtAction > i3) {
                                    int i22 = i21 + 1;
                                    int i23 = iArr[i22];
                                    iArr[i22] = iNtAction;
                                    scopeTrialCheck(iArr, i22, primaryRepairInfo, i2 + 1);
                                    iArr[i22] = i23;
                                    i4 = i11;
                                } else {
                                    i4 = i11;
                                    if (Parser.rules_compliance[iNtAction] <= this.options.sourceLevel) {
                                        i21 -= Parser.rhs[iNtAction] - 1;
                                        iNtAction = Parser.ntAction(iArr[i21], Parser.lhs[iNtAction]);
                                        i11 = i4;
                                        i3 = ParserBasicInformation.NUM_RULES;
                                    }
                                }
                            }
                        } else {
                            i4 = i11;
                            if (check > primaryRepairInfo.distance) {
                                this.scopeStackTop = i2;
                                primaryRepairInfo.distance = check;
                            }
                        }
                        if (this.lexStream.kind(this.buffer[primaryRepairInfo.bufferPosition]) == 60 && primaryRepairInfo.distance == i20) {
                            this.scopeStackTop = i2;
                            primaryRepairInfo.distance = 30;
                        }
                        if (primaryRepairInfo.distance > i20) {
                            this.scopeIndex[i2] = i4;
                            this.scopePosition[i2] = i17;
                            return;
                        }
                    } else {
                        i4 = i11;
                    }
                }
            } else {
                i4 = i11;
            }
            i11 = i4 + 1;
        }
    }

    private boolean secondaryCheck(int[] iArr, int i, int i2, int i3) {
        for (int i4 = i - 1; i4 >= 0; i4--) {
            int check = parseCheck(iArr, i4, this.lexStream.kind(this.buffer[i2]), i2 + 1);
            if ((check - i2) + 1 > 3 && check > i3) {
                return true;
            }
        }
        PrimaryRepairInfo primaryRepairInfo = new PrimaryRepairInfo();
        primaryRepairInfo.bufferPosition = i2 + 1;
        primaryRepairInfo.distance = i3;
        PrimaryRepairInfo primaryRepairInfoScopeTrial = scopeTrial(iArr, i, primaryRepairInfo);
        return primaryRepairInfoScopeTrial.distance - i2 > 3 && primaryRepairInfoScopeTrial.distance > i3;
    }

    private RepairCandidate secondaryPhase(int i) {
        int i2;
        int i3;
        int i4;
        SecondaryRepairInfo secondaryRepairInfo = new SecondaryRepairInfo();
        SecondaryRepairInfo secondaryRepairInfo2 = new SecondaryRepairInfo();
        RepairCandidate repairCandidate = new RepairCandidate();
        repairCandidate.symbol = 0;
        secondaryRepairInfo.code = 0;
        secondaryRepairInfo.distance = 0;
        secondaryRepairInfo.recoveryOnNextStack = false;
        secondaryRepairInfo2.distance = 0;
        secondaryRepairInfo2.recoveryOnNextStack = false;
        if (this.nextStackTop >= 0) {
            int[] iArr = this.buffer;
            iArr[2] = i;
            iArr[1] = this.lexStream.previous(i);
            int[] iArr2 = this.buffer;
            iArr2[0] = this.lexStream.previous(iArr2[1]);
            for (int i5 = 3; i5 < 31; i5++) {
                int[] iArr3 = this.buffer;
                iArr3[i5] = this.lexStream.next(iArr3[i5 - 1]);
            }
            this.buffer[31] = this.lexStream.badtoken();
            int i6 = 29;
            while (i6 >= 1 && this.lexStream.kind(this.buffer[i6]) == 60) {
                i6--;
            }
            i3 = i6 + 1;
            int[] iArr4 = this.locationStack;
            int i7 = this.nextStackTop;
            int i8 = iArr4[i7];
            int[] iArr5 = this.locationStartStack;
            int i9 = iArr5[i7];
            int[] iArr6 = this.buffer;
            iArr4[i7] = iArr6[2];
            iArr5[i7] = this.lexStream.start(iArr6[2]);
            secondaryRepairInfo2.numDeletions = this.nextStackTop;
            i2 = 1;
            SecondaryRepairInfo secondaryRepairInfoMisplacementRecovery = misplacementRecovery(this.nextStack, this.nextStackTop, i3, secondaryRepairInfo2, true);
            if (secondaryRepairInfoMisplacementRecovery.recoveryOnNextStack) {
                secondaryRepairInfoMisplacementRecovery.distance++;
            }
            secondaryRepairInfo.numDeletions = this.nextStackTop + 31;
            SecondaryRepairInfo secondaryRepairInfoSecondaryRecovery = secondaryRecovery(this.nextStack, this.nextStackTop, i3, secondaryRepairInfo, true);
            if (secondaryRepairInfoSecondaryRecovery.recoveryOnNextStack) {
                secondaryRepairInfoSecondaryRecovery.distance++;
            }
            int[] iArr7 = this.locationStack;
            int i10 = this.nextStackTop;
            iArr7[i10] = i8;
            this.locationStartStack[i10] = i9;
            secondaryRepairInfo2 = secondaryRepairInfoMisplacementRecovery;
            secondaryRepairInfo = secondaryRepairInfoSecondaryRecovery;
        } else {
            i2 = 1;
            secondaryRepairInfo2.numDeletions = this.stateStackTop;
            secondaryRepairInfo.numDeletions = this.stateStackTop + 31;
            i3 = 0;
        }
        int[] iArr8 = this.buffer;
        iArr8[3] = i;
        iArr8[2] = this.lexStream.previous(i);
        int[] iArr9 = this.buffer;
        iArr9[i2] = this.lexStream.previous(iArr9[2]);
        int[] iArr10 = this.buffer;
        iArr10[0] = this.lexStream.previous(iArr10[i2]);
        for (int i11 = 4; i11 < 32; i11++) {
            int[] iArr11 = this.buffer;
            iArr11[i11] = this.lexStream.next(iArr11[i11 - 1]);
        }
        int i12 = 29;
        while (i12 >= i2 && this.lexStream.kind(this.buffer[i12]) == 60) {
            i12--;
        }
        int i13 = i12 + 1;
        SecondaryRepairInfo secondaryRepairInfoMisplacementRecovery2 = misplacementRecovery(this.stack, this.stateStackTop, i13, secondaryRepairInfo2, false);
        SecondaryRepairInfo secondaryRepairInfoSecondaryRecovery2 = secondaryRecovery(this.stack, this.stateStackTop, i13, secondaryRepairInfo, false);
        if (secondaryRepairInfoMisplacementRecovery2.distance > 3 && (secondaryRepairInfoMisplacementRecovery2.numDeletions <= secondaryRepairInfoSecondaryRecovery2.numDeletions || secondaryRepairInfoMisplacementRecovery2.distance - secondaryRepairInfoMisplacementRecovery2.numDeletions >= secondaryRepairInfoSecondaryRecovery2.distance - secondaryRepairInfoSecondaryRecovery2.numDeletions)) {
            secondaryRepairInfoSecondaryRecovery2.code = 8;
            secondaryRepairInfoSecondaryRecovery2.stackPosition = secondaryRepairInfoMisplacementRecovery2.stackPosition;
            secondaryRepairInfoSecondaryRecovery2.bufferPosition = 2;
            secondaryRepairInfoSecondaryRecovery2.numDeletions = secondaryRepairInfoMisplacementRecovery2.numDeletions;
            secondaryRepairInfoSecondaryRecovery2.distance = secondaryRepairInfoMisplacementRecovery2.distance;
            secondaryRepairInfoSecondaryRecovery2.recoveryOnNextStack = secondaryRepairInfoMisplacementRecovery2.recoveryOnNextStack;
        }
        if (secondaryRepairInfoSecondaryRecovery2.recoveryOnNextStack) {
            this.stateStackTop = this.nextStackTop;
            for (int i14 = 0; i14 <= this.stateStackTop; i14++) {
                this.stack[i14] = this.nextStack[i14];
            }
            int[] iArr12 = this.buffer;
            iArr12[2] = i;
            iArr12[i2] = this.lexStream.previous(i);
            int[] iArr13 = this.buffer;
            iArr13[0] = this.lexStream.previous(iArr13[i2]);
            for (int i15 = 3; i15 < 31; i15++) {
                int[] iArr14 = this.buffer;
                iArr14[i15] = this.lexStream.next(iArr14[i15 - 1]);
            }
            this.buffer[31] = this.lexStream.badtoken();
            int[] iArr15 = this.locationStack;
            int i16 = this.nextStackTop;
            int[] iArr16 = this.buffer;
            iArr15[i16] = iArr16[2];
            this.locationStartStack[i16] = this.lexStream.start(iArr16[2]);
            i4 = i3;
        } else {
            i4 = i13;
        }
        if (secondaryRepairInfoSecondaryRecovery2.code == 10 || secondaryRepairInfoSecondaryRecovery2.code == 6) {
            PrimaryRepairInfo primaryRepairInfo = new PrimaryRepairInfo();
            primaryRepairInfo.distance = 0;
            primaryRepairInfo.bufferPosition = 2;
            while (primaryRepairInfo.bufferPosition <= secondaryRepairInfoSecondaryRecovery2.bufferPosition && secondaryRepairInfoSecondaryRecovery2.code != 9) {
                primaryRepairInfo = scopeTrial(this.stack, this.stateStackTop, primaryRepairInfo);
                int i17 = (primaryRepairInfo.distance == 30 ? i4 : primaryRepairInfo.distance) - (primaryRepairInfo.bufferPosition - i2);
                if (i17 > 3 && i17 > secondaryRepairInfoSecondaryRecovery2.distance - secondaryRepairInfoSecondaryRecovery2.numDeletions) {
                    secondaryRepairInfoSecondaryRecovery2.code = 9;
                    secondaryRepairInfoSecondaryRecovery2.symbol = Parser.scope_lhs[this.scopeIndex[this.scopeStackTop]] + 'v';
                    secondaryRepairInfoSecondaryRecovery2.stackPosition = this.stateStackTop;
                    secondaryRepairInfoSecondaryRecovery2.bufferPosition = primaryRepairInfo.bufferPosition;
                }
                primaryRepairInfo.bufferPosition += i2;
            }
        }
        if (secondaryRepairInfoSecondaryRecovery2.code == 0 && this.lexStream.kind(this.buffer[i4]) == 60) {
            PrimaryRepairInfo primaryRepairInfo2 = new PrimaryRepairInfo();
            primaryRepairInfo2.bufferPosition = i4;
            primaryRepairInfo2.distance = 0;
            for (int i18 = this.stateStackTop; i18 >= 0 && secondaryRepairInfoSecondaryRecovery2.code == 0; i18--) {
                primaryRepairInfo2 = scopeTrial(this.stack, i18, primaryRepairInfo2);
                if (primaryRepairInfo2.distance > 0) {
                    secondaryRepairInfoSecondaryRecovery2.code = 9;
                    secondaryRepairInfoSecondaryRecovery2.symbol = Parser.scope_lhs[this.scopeIndex[this.scopeStackTop]] + 'v';
                    secondaryRepairInfoSecondaryRecovery2.stackPosition = i18;
                    secondaryRepairInfoSecondaryRecovery2.bufferPosition = primaryRepairInfo2.bufferPosition;
                }
            }
        }
        if (secondaryRepairInfoSecondaryRecovery2.code == 0) {
            return repairCandidate;
        }
        secondaryDiagnosis(secondaryRepairInfoSecondaryRecovery2);
        int i19 = secondaryRepairInfoSecondaryRecovery2.code;
        if (i19 == 6) {
            repairCandidate.location = this.buffer[secondaryRepairInfoSecondaryRecovery2.bufferPosition];
            repairCandidate.symbol = this.lexStream.kind(this.buffer[secondaryRepairInfoSecondaryRecovery2.bufferPosition]);
            LexStream lexStream = this.lexStream;
            lexStream.reset(lexStream.next(this.buffer[secondaryRepairInfoSecondaryRecovery2.bufferPosition]));
        } else if (i19 == 8) {
            repairCandidate.location = this.buffer[2];
            repairCandidate.symbol = this.lexStream.kind(this.buffer[2]);
            LexStream lexStream2 = this.lexStream;
            lexStream2.reset(lexStream2.next(this.buffer[2]));
        } else {
            repairCandidate.symbol = secondaryRepairInfoSecondaryRecovery2.symbol;
            repairCandidate.location = this.buffer[secondaryRepairInfoSecondaryRecovery2.bufferPosition];
            this.lexStream.reset(this.buffer[secondaryRepairInfoSecondaryRecovery2.bufferPosition]);
        }
        return repairCandidate;
    }

    private SecondaryRepairInfo misplacementRecovery(int[] iArr, int i, int i2, SecondaryRepairInfo secondaryRepairInfo, boolean z) {
        int i3 = this.buffer[2];
        int i4 = i - 1;
        int i5 = 0;
        while (i4 >= 0) {
            int i6 = this.locationStack[i4];
            if (i6 < i3) {
                i5++;
            }
            int check = parseCheck(iArr, i4, this.lexStream.kind(this.buffer[2]), 3);
            if (check == 30) {
                check = i2;
            }
            if (check > 3 && check - i5 > secondaryRepairInfo.distance - secondaryRepairInfo.numDeletions) {
                secondaryRepairInfo.stackPosition = i4;
                secondaryRepairInfo.distance = check;
                secondaryRepairInfo.numDeletions = i5;
                secondaryRepairInfo.recoveryOnNextStack = z;
            }
            i4--;
            i3 = i6;
        }
        return secondaryRepairInfo;
    }

    private SecondaryRepairInfo secondaryRecovery(int[] iArr, int i, int i2, SecondaryRepairInfo secondaryRepairInfo, boolean z) {
        int i3;
        int i4;
        int i5;
        int i6 = 2;
        int i7 = 0;
        int i8 = this.buffer[2];
        int i9 = i;
        while (i9 >= 0 && secondaryRepairInfo.numDeletions >= i7) {
            int i10 = this.locationStack[i9];
            if (i10 < i8) {
                i7++;
            }
            int i11 = i6;
            while (i11 <= i2 - 2 && secondaryRepairInfo.numDeletions >= (i3 = (i7 + i11) - 1)) {
                int i12 = i11 + 1;
                int check = parseCheck(iArr, i9, this.lexStream.kind(this.buffer[i11]), i12);
                int i13 = 30;
                if (check == 30) {
                    check = i2;
                }
                int i14 = 10;
                if ((check - i11) + 1 > 3 && (i3 < secondaryRepairInfo.numDeletions || (i5 = check - i3) > secondaryRepairInfo.distance - secondaryRepairInfo.numDeletions || (secondaryRepairInfo.code == 10 && i5 == secondaryRepairInfo.distance - secondaryRepairInfo.numDeletions))) {
                    secondaryRepairInfo.code = 6;
                    secondaryRepairInfo.distance = check;
                    secondaryRepairInfo.stackPosition = i9;
                    secondaryRepairInfo.bufferPosition = i11;
                    secondaryRepairInfo.numDeletions = i3;
                    secondaryRepairInfo.recoveryOnNextStack = z;
                }
                int iNasi = Parser.nasi(iArr[i9]);
                while (iNasi >= 0 && Parser.nasr[iNasi] != 0) {
                    int i15 = Parser.nasr[iNasi] + 'v';
                    int check2 = parseCheck(iArr, i9, i15, i11);
                    if (check2 == i13) {
                        check2 = i2;
                    }
                    if ((check2 - i11) + 1 > 3) {
                        if (i3 < secondaryRepairInfo.numDeletions) {
                            i4 = i14;
                        } else if (check2 - i3 > secondaryRepairInfo.distance - secondaryRepairInfo.numDeletions) {
                            i4 = 10;
                        } else {
                            i4 = 10;
                        }
                        secondaryRepairInfo.code = i4;
                        secondaryRepairInfo.symbol = i15;
                        secondaryRepairInfo.distance = check2;
                        secondaryRepairInfo.stackPosition = i9;
                        secondaryRepairInfo.bufferPosition = i11;
                        secondaryRepairInfo.numDeletions = i3;
                        secondaryRepairInfo.recoveryOnNextStack = z;
                    } else {
                        i4 = i14;
                    }
                    iNasi++;
                    i14 = i4;
                    i13 = 30;
                }
                i11 = i12;
            }
            i9--;
            i8 = i10;
            i6 = 2;
        }
        return secondaryRepairInfo;
    }

    private void secondaryDiagnosis(SecondaryRepairInfo secondaryRepairInfo) {
        int ntermIndex;
        if (secondaryRepairInfo.code == 9) {
            if (secondaryRepairInfo.stackPosition < this.stateStackTop) {
                reportError(6, Parser.terminal_index[118], this.locationStack[secondaryRepairInfo.stackPosition], this.buffer[1]);
            }
            for (int i = 0; i < this.scopeStackTop; i++) {
                reportError(9, -this.scopeIndex[i], this.locationStack[this.scopePosition[i]], this.buffer[1], Parser.non_terminal_index[Parser.scope_lhs[this.scopeIndex[i]]]);
            }
            secondaryRepairInfo.symbol = Parser.scope_lhs[this.scopeIndex[this.scopeStackTop]] + 'v';
            int[] iArr = this.scopePosition;
            int i2 = this.scopeStackTop;
            int i3 = iArr[i2];
            this.stateStackTop = i3;
            reportError(9, -this.scopeIndex[i2], this.locationStack[i3], this.buffer[1], getNtermIndex(this.stack[i3], secondaryRepairInfo.symbol, secondaryRepairInfo.bufferPosition));
            return;
        }
        int i4 = secondaryRepairInfo.code;
        if (secondaryRepairInfo.code == 10) {
            ntermIndex = getNtermIndex(this.stack[secondaryRepairInfo.stackPosition], secondaryRepairInfo.symbol, secondaryRepairInfo.bufferPosition);
        } else {
            ntermIndex = Parser.terminal_index[118];
        }
        reportError(i4, ntermIndex, this.locationStack[secondaryRepairInfo.stackPosition], this.buffer[secondaryRepairInfo.bufferPosition - 1]);
        this.stateStackTop = secondaryRepairInfo.stackPosition;
    }

    private int parseCheck(int[] iArr, int i, int i2, int i3) {
        int iKind;
        int i4;
        int i5;
        int i6;
        int i7 = i;
        int iTAction = iArr[i7];
        if (i2 > 118) {
            this.tempStackTop = i7;
            if (this.DEBUG_PARSECHECK) {
                System.out.println(this.tempStackTop);
            }
            iKind = this.lexStream.kind(this.buffer[i3]);
            LexStream lexStream = this.lexStream;
            lexStream.reset(lexStream.next(this.buffer[i3]));
            iTAction = Parser.ntAction(iTAction, i2 - 118);
            if (iTAction <= 800) {
                do {
                    this.tempStackTop -= Parser.rhs[iTAction] - 1;
                    if (this.DEBUG_PARSECHECK) {
                        System.out.print(this.tempStackTop);
                        System.out.print(" (");
                        System.out.print(-(Parser.rhs[iTAction] - 1));
                        System.out.print(") [max:");
                        System.out.print(i7);
                        System.out.print("]\tprocess_non_terminal\t");
                        System.out.print(iTAction);
                        System.out.print("\t");
                        System.out.print(Parser.f629name[Parser.non_terminal_index[Parser.lhs[iTAction]]]);
                        System.out.println();
                    }
                    if (Parser.rules_compliance[iTAction] > this.options.sourceLevel) {
                        return 0;
                    }
                    char c = Parser.lhs[iTAction];
                    int i8 = this.tempStackTop;
                    if (i8 > i7) {
                        i6 = this.tempStack[i8];
                    } else {
                        i6 = iArr[i8];
                    }
                    iTAction = Parser.ntAction(i6, c);
                } while (iTAction <= 800);
                int i9 = this.tempStackTop;
                if (i7 >= i9) {
                    i7 = i9;
                }
            }
            i4 = i3;
        } else {
            this.tempStackTop = i7 - 1;
            if (this.DEBUG_PARSECHECK) {
                System.out.println(this.tempStackTop);
            }
            i7 = this.tempStackTop;
            this.lexStream.reset(this.buffer[i3]);
            iKind = i2;
            i4 = i3 - 1;
        }
        while (true) {
            if (this.DEBUG_PARSECHECK) {
                System.out.print(this.tempStackTop + 1);
                System.out.print(" (+1) [max:");
                System.out.print(i7);
                System.out.print("]\tprocess_terminal    \t");
                System.out.print(iKind);
                System.out.print("\t");
                System.out.print(Parser.f629name[Parser.terminal_index[iKind]]);
                System.out.println();
            }
            int i10 = this.tempStackTop + 1;
            this.tempStackTop = i10;
            if (i10 >= this.stackLength) {
                return i4;
            }
            this.tempStack[i10] = iTAction;
            iTAction = Parser.tAction(iTAction, iKind);
            if (iTAction <= 800) {
                this.tempStackTop--;
                if (this.DEBUG_PARSECHECK) {
                    System.out.print(this.tempStackTop);
                    System.out.print(" (-1) [max:");
                    System.out.print(i7);
                    System.out.print("]\treduce");
                    System.out.println();
                }
            } else {
                if (iTAction >= 16381 && iTAction <= 16382) {
                    if (iTAction == 16381) {
                        return 30;
                    }
                    return i4;
                }
                if (i4 == 30) {
                    return i4;
                }
                i4++;
                int iKind2 = this.lexStream.kind(this.buffer[i4]);
                LexStream lexStream2 = this.lexStream;
                lexStream2.reset(lexStream2.next(this.buffer[i4]));
                if (iTAction > 16382) {
                    iTAction -= 16382;
                    if (this.DEBUG_PARSECHECK) {
                        System.out.print(this.tempStackTop);
                        System.out.print("\tshift reduce");
                        System.out.println();
                    }
                    iKind = iKind2;
                } else {
                    if (this.DEBUG_PARSECHECK) {
                        System.out.println("\tshift");
                    }
                    iKind = iKind2;
                }
            }
            do {
                this.tempStackTop -= Parser.rhs[iTAction] - 1;
                if (this.DEBUG_PARSECHECK) {
                    System.out.print(this.tempStackTop);
                    System.out.print(" (");
                    System.out.print(-(Parser.rhs[iTAction] - 1));
                    System.out.print(") [max:");
                    System.out.print(i7);
                    System.out.print("]\tprocess_non_terminal\t");
                    System.out.print(iTAction);
                    System.out.print("\t");
                    System.out.print(Parser.f629name[Parser.non_terminal_index[Parser.lhs[iTAction]]]);
                    System.out.println();
                }
                if (iTAction <= 800 && Parser.rules_compliance[iTAction] > this.options.sourceLevel) {
                    return 0;
                }
                char c2 = Parser.lhs[iTAction];
                int i11 = this.tempStackTop;
                if (i11 > i7) {
                    i5 = this.tempStack[i11];
                } else {
                    i5 = iArr[i11];
                }
                iTAction = Parser.ntAction(i5, c2);
            } while (iTAction <= 800);
            int i12 = this.tempStackTop;
            if (i7 >= i12) {
                i7 = i12;
            }
        }
    }

    private void reportError(int i, int i2, int i3, int i4) {
        reportError(i, i2, i3, i4, 0);
    }

    private void reportError(int i, int i2, int i3, int i4, int i5) {
        int i6 = i3 > i4 ? i4 : i3;
        if (i6 < i4) {
            reportSecondaryError(i, i2, i6, i4, i5);
        } else {
            reportPrimaryError(i, i2, i4, i5);
        }
    }

    private void reportPrimaryError(int i, int i2, int i3, int i4) {
        String str;
        if (i2 >= 0) {
            str = Parser.readableName[i2];
        } else {
            str = Util.EMPTY_STRING;
        }
        String str2 = str;
        int iStart = this.lexStream.start(i3);
        int iEnd = this.lexStream.end(i3);
        int iKind = this.lexStream.kind(i3);
        String str3 = Parser.f629name[Parser.terminal_index[this.lexStream.kind(i3)]];
        char[] cArrName = this.lexStream.name(i3);
        if (iKind == 48) {
            cArrName = displayEscapeCharacters(cArrName, 1, cArrName.length - 1);
        }
        char[] cArr = cArrName;
        int i5 = (this.recoveryScanner == null || i2 < 0) ? -1 : Parser.reverse_index[i2];
        switch (i) {
            case 2:
                RecoveryScanner recoveryScanner = this.recoveryScanner;
                if (recoveryScanner != null) {
                    if (i5 > -1) {
                        recoveryScanner.insertToken(i5, -1, iStart);
                    } else {
                        int[] nTermTemplate = getNTermTemplate(-i5);
                        if (nTermTemplate != null) {
                            this.recoveryScanner.insertTokens(nTermTemplate, -1, iStart);
                        }
                    }
                }
                if (this.reportProblem) {
                    problemReporter().parseErrorInsertBeforeToken(iStart, iEnd, iKind, cArr, str3, str2);
                }
                break;
            case 3:
                RecoveryScanner recoveryScanner2 = this.recoveryScanner;
                if (recoveryScanner2 != null) {
                    if (i5 > -1) {
                        recoveryScanner2.insertToken(i5, -1, iEnd);
                    } else {
                        int[] nTermTemplate2 = getNTermTemplate(-i5);
                        if (nTermTemplate2 != null) {
                            this.recoveryScanner.insertTokens(nTermTemplate2, -1, iEnd);
                        }
                    }
                }
                if (this.reportProblem) {
                    problemReporter().parseErrorInsertAfterToken(iStart, iEnd, iKind, cArr, str3, str2);
                }
                break;
            case 4:
                if (str2.length() == 0) {
                    RecoveryScanner recoveryScanner3 = this.recoveryScanner;
                    if (recoveryScanner3 != null) {
                        recoveryScanner3.removeTokens(iStart, iEnd);
                    }
                    if (this.reportProblem) {
                        problemReporter().parseErrorReplaceToken(iStart, iEnd, iKind, cArr, str3, str2);
                    }
                } else {
                    RecoveryScanner recoveryScanner4 = this.recoveryScanner;
                    if (recoveryScanner4 != null) {
                        if (i5 > -1) {
                            recoveryScanner4.replaceTokens(i5, iStart, iEnd);
                        } else {
                            int[] nTermTemplate3 = getNTermTemplate(-i5);
                            if (nTermTemplate3 != null) {
                                this.recoveryScanner.replaceTokens(nTermTemplate3, iStart, iEnd);
                            }
                        }
                    }
                    if (this.reportProblem) {
                        problemReporter().parseErrorInvalidToken(iStart, iEnd, iKind, cArr, str3, str2);
                    }
                }
                break;
            case 5:
                RecoveryScanner recoveryScanner5 = this.recoveryScanner;
                if (recoveryScanner5 != null) {
                    if (i5 > -1) {
                        recoveryScanner5.replaceTokens(i5, iStart, iEnd);
                    } else {
                        int[] nTermTemplate4 = getNTermTemplate(-i5);
                        if (nTermTemplate4 != null) {
                            this.recoveryScanner.replaceTokens(nTermTemplate4, iStart, iEnd);
                        }
                    }
                }
                if (this.reportProblem) {
                    problemReporter().parseErrorReplaceToken(iStart, iEnd, iKind, cArr, str3, str2);
                }
                break;
            case 6:
                RecoveryScanner recoveryScanner6 = this.recoveryScanner;
                if (recoveryScanner6 != null) {
                    recoveryScanner6.removeTokens(iStart, iEnd);
                }
                if (this.reportProblem) {
                    problemReporter().parseErrorDeleteToken(iStart, iEnd, iKind, cArr, str3);
                }
                break;
            case 7:
                RecoveryScanner recoveryScanner7 = this.recoveryScanner;
                if (recoveryScanner7 != null) {
                    if (i5 > -1) {
                        recoveryScanner7.replaceTokens(i5, iStart, iEnd);
                    } else {
                        int[] nTermTemplate5 = getNTermTemplate(-i5);
                        if (nTermTemplate5 != null) {
                            this.recoveryScanner.replaceTokens(nTermTemplate5, iStart, iEnd);
                        }
                    }
                }
                if (this.reportProblem) {
                    problemReporter().parseErrorMergeTokens(iStart, iEnd, str2);
                }
                break;
            case 8:
                RecoveryScanner recoveryScanner8 = this.recoveryScanner;
                if (recoveryScanner8 != null) {
                    recoveryScanner8.removeTokens(iStart, iEnd);
                }
                if (this.reportProblem) {
                    problemReporter().parseErrorMisplacedConstruct(iStart, iEnd);
                }
                break;
            case 9:
                StringBuffer stringBuffer = new StringBuffer();
                int[] iArr = this.recoveryScanner != null ? new int[Parser.scope_rhs.length - Parser.scope_suffix[-i2]] : null;
                int i6 = 0;
                int i7 = 0;
                int i8 = Parser.scope_suffix[-i2];
                while (Parser.scope_rhs[i8] != 0) {
                    stringBuffer.append(Parser.readableName[Parser.scope_rhs[i8]]);
                    int i9 = i8 + 1;
                    if (Parser.scope_rhs[i9] != 0) {
                        stringBuffer.append(StringUtil.SPACE);
                    } else {
                        i7 = Parser.reverse_index[Parser.scope_rhs[i8]];
                    }
                    if (iArr != null) {
                        int i10 = Parser.reverse_index[Parser.scope_rhs[i8]];
                        if (i10 > -1) {
                            int length = iArr.length;
                            if (i6 == length) {
                                int[] iArr2 = new int[length * 2];
                                System.arraycopy(iArr, 0, iArr2, 0, length);
                                iArr = iArr2;
                            }
                            iArr[i6] = i10;
                            i6++;
                        } else {
                            int[] nTermTemplate6 = getNTermTemplate(-i10);
                            if (nTermTemplate6 != null) {
                                int i11 = 0;
                                while (i11 < nTermTemplate6.length) {
                                    int length2 = iArr.length;
                                    if (i6 == length2) {
                                        int[] iArr3 = new int[length2 * 2];
                                        System.arraycopy(iArr, 0, iArr3, 0, length2);
                                        iArr = iArr3;
                                    }
                                    iArr[i6] = nTermTemplate6[i11];
                                    i11++;
                                    i6++;
                                }
                            } else {
                                iArr = null;
                                i6 = 0;
                            }
                        }
                    }
                    i8 = i9;
                }
                if (i6 > 0) {
                    int[] iArr4 = new int[i6];
                    System.arraycopy(iArr, 0, iArr4, 0, i6);
                    this.recoveryScanner.insertTokens(iArr4, i4 != 0 ? -Parser.reverse_index[i4] : -1, iEnd);
                }
                if (i4 != 0) {
                    if (i7 != 66 && this.reportProblem) {
                        problemReporter().parseErrorInsertToComplete(iStart, iEnd, stringBuffer.toString(), Parser.readableName[i4]);
                    }
                } else if (this.reportProblem) {
                    problemReporter().parseErrorInsertToCompleteScope(iStart, iEnd, stringBuffer.toString());
                }
                break;
            case 10:
            default:
                if (str2.length() == 0) {
                    RecoveryScanner recoveryScanner9 = this.recoveryScanner;
                    if (recoveryScanner9 != null) {
                        recoveryScanner9.removeTokens(iStart, iEnd);
                    }
                    if (this.reportProblem) {
                        problemReporter().parseErrorNoSuggestion(iStart, iEnd, iKind, cArr, str3);
                    }
                } else {
                    RecoveryScanner recoveryScanner10 = this.recoveryScanner;
                    if (recoveryScanner10 != null) {
                        if (i5 > -1) {
                            recoveryScanner10.replaceTokens(i5, iStart, iEnd);
                        } else {
                            int[] nTermTemplate7 = getNTermTemplate(-i5);
                            if (nTermTemplate7 != null) {
                                this.recoveryScanner.replaceTokens(nTermTemplate7, iStart, iEnd);
                            }
                        }
                    }
                    if (this.reportProblem) {
                        problemReporter().parseErrorReplaceToken(iStart, iEnd, iKind, cArr, str3, str2);
                    }
                }
                break;
            case 11:
                if (this.reportProblem) {
                    problemReporter().parseErrorUnexpectedEnd(iStart, iEnd);
                }
                break;
        }
    }

    private void reportSecondaryError(int i, int i2, int i3, int i4, int i5) {
        String str;
        int iStart;
        if (i2 >= 0) {
            str = Parser.readableName[i2];
        } else {
            str = Util.EMPTY_STRING;
        }
        if (!this.lexStream.isInsideStream(i3)) {
            if (i3 == this.errorToken) {
                iStart = this.errorTokenStart;
            } else {
                int i6 = -1;
                for (int i7 = 0; i7 <= this.stateStackTop; i7++) {
                    if (this.locationStack[i7] == i3) {
                        i6 = this.locationStartStack[i7];
                    }
                }
                iStart = i6;
            }
            if (iStart == -1) {
                iStart = this.lexStream.start(i4);
            }
        } else if (i3 == 0) {
            iStart = this.lexStream.start(i3 + 1);
        } else {
            iStart = this.lexStream.start(i3);
        }
        int iEnd = this.lexStream.end(i4);
        int i8 = (this.recoveryScanner == null || i2 < 0) ? -1 : Parser.reverse_index[i2];
        switch (i) {
            case 6:
                RecoveryScanner recoveryScanner = this.recoveryScanner;
                if (recoveryScanner != null) {
                    recoveryScanner.removeTokens(iStart, iEnd);
                }
                if (this.reportProblem) {
                    problemReporter().parseErrorDeleteTokens(iStart, iEnd);
                }
                break;
            case 7:
                RecoveryScanner recoveryScanner2 = this.recoveryScanner;
                if (recoveryScanner2 != null) {
                    if (i8 > -1) {
                        recoveryScanner2.replaceTokens(i8, iStart, iEnd);
                    } else {
                        int[] nTermTemplate = getNTermTemplate(-i8);
                        if (nTermTemplate != null) {
                            this.recoveryScanner.replaceTokens(nTermTemplate, iStart, iEnd);
                        }
                    }
                }
                if (this.reportProblem) {
                    problemReporter().parseErrorMergeTokens(iStart, iEnd, str);
                }
                break;
            case 8:
                RecoveryScanner recoveryScanner3 = this.recoveryScanner;
                if (recoveryScanner3 != null) {
                    recoveryScanner3.removeTokens(iStart, iEnd);
                }
                if (this.reportProblem) {
                    problemReporter().parseErrorMisplacedConstruct(iStart, iEnd);
                }
                break;
            case 9:
                int iStart2 = this.lexStream.start(i4);
                StringBuffer stringBuffer = new StringBuffer();
                int[] iArr = this.recoveryScanner != null ? new int[Parser.scope_rhs.length - Parser.scope_suffix[-i2]] : null;
                int i9 = 0;
                int i10 = 0;
                int i11 = Parser.scope_suffix[-i2];
                while (Parser.scope_rhs[i11] != 0) {
                    stringBuffer.append(Parser.readableName[Parser.scope_rhs[i11]]);
                    int i12 = i11 + 1;
                    if (Parser.scope_rhs[i12] != 0) {
                        stringBuffer.append(StringUtil.SPACE);
                    } else {
                        i10 = Parser.reverse_index[Parser.scope_rhs[i11]];
                    }
                    if (iArr != null) {
                        int i13 = Parser.reverse_index[Parser.scope_rhs[i11]];
                        if (i13 > -1) {
                            int length = iArr.length;
                            if (i9 == length) {
                                int[] iArr2 = new int[length * 2];
                                System.arraycopy(iArr, 0, iArr2, 0, length);
                                iArr = iArr2;
                            }
                            iArr[i9] = i13;
                            i9++;
                        } else {
                            int[] nTermTemplate2 = getNTermTemplate(-i13);
                            if (nTermTemplate2 != null) {
                                int i14 = 0;
                                while (i14 < nTermTemplate2.length) {
                                    int length2 = iArr.length;
                                    if (i9 == length2) {
                                        int[] iArr3 = new int[length2 * 2];
                                        System.arraycopy(iArr, 0, iArr3, 0, length2);
                                        iArr = iArr3;
                                    }
                                    iArr[i9] = nTermTemplate2[i14];
                                    i14++;
                                    i9++;
                                }
                            } else {
                                iArr = null;
                                i9 = 0;
                            }
                        }
                    }
                    i11 = i12;
                }
                if (i9 > 0) {
                    int[] iArr4 = new int[i9];
                    System.arraycopy(iArr, 0, iArr4, 0, i9);
                    this.recoveryScanner.insertTokens(iArr4, i5 != 0 ? -Parser.reverse_index[i5] : -1, iEnd);
                }
                if (i5 != 0) {
                    if (i10 != 66 && this.reportProblem) {
                        problemReporter().parseErrorInsertToComplete(iStart2, iEnd, stringBuffer.toString(), Parser.readableName[i5]);
                    }
                } else if (this.reportProblem) {
                    problemReporter().parseErrorInsertToCompletePhrase(iStart2, iEnd, stringBuffer.toString());
                }
                break;
            default:
                if (str.length() == 0) {
                    RecoveryScanner recoveryScanner4 = this.recoveryScanner;
                    if (recoveryScanner4 != null) {
                        recoveryScanner4.removeTokens(iStart, iEnd);
                    }
                    if (this.reportProblem) {
                        problemReporter().parseErrorNoSuggestionForTokens(iStart, iEnd);
                    }
                } else {
                    RecoveryScanner recoveryScanner5 = this.recoveryScanner;
                    if (recoveryScanner5 != null) {
                        if (i8 > -1) {
                            recoveryScanner5.replaceTokens(i8, iStart, iEnd);
                        } else {
                            int[] nTermTemplate3 = getNTermTemplate(-i8);
                            if (nTermTemplate3 != null) {
                                this.recoveryScanner.replaceTokens(nTermTemplate3, iStart, iEnd);
                            }
                        }
                    }
                    if (this.reportProblem) {
                        problemReporter().parseErrorReplaceTokens(iStart, iEnd, str);
                    }
                }
                break;
        }
    }

    private int[] getNTermTemplate(int i) {
        char c = Parser.recovery_templates_index[i];
        if (c <= 0) {
            return null;
        }
        int[] iArr = new int[Parser.recovery_templates.length];
        int i2 = 0;
        for (int i3 = c; Parser.recovery_templates[i3] != 0; i3++) {
            iArr[i2] = Parser.recovery_templates[i3];
            i2++;
        }
        int[] iArr2 = new int[i2];
        System.arraycopy(iArr, 0, iArr2, 0, i2);
        return iArr2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.lexStream.toString());
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.ConflictedParser
    public boolean atConflictScenario(int i) {
        if (i == 24 || i == 37) {
            return true;
        }
        return i == 11 && !this.lexStream.awaitingColonColon();
    }
}
