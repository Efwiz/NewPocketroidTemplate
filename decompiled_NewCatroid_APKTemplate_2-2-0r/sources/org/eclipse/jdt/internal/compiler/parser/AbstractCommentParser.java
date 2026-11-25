package org.eclipse.jdt.internal.compiler.parser;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public abstract class AbstractCommentParser implements JavadocTagConstants {
    protected static final int ARGUMENT_RECOVERY = 2;
    protected static final int ARGUMENT_TYPE_RECOVERY = 3;
    protected static final int AST_STACK_INCREMENT = 10;
    public static final int COMPIL_PARSER = 1;
    public static final int COMPLETION_PARSER = 8;
    public static final int DOM_PARSER = 2;
    protected static final int EMPTY_ARGUMENT_RECOVERY = 4;
    public static final int FORMATTER_COMMENT_PARSER = 32;
    private static final int INHERITED_POSITIONS_ARRAY_INCREMENT = 4;
    protected static final int PARSER_KIND = 255;
    protected static final int QUALIFIED_NAME_RECOVERY = 1;
    public static final int SELECTION_PARSER = 4;
    public static final int SOURCE_PARSER = 16;
    protected static final int TEXT_PARSE = 256;
    protected static final int TEXT_VERIF = 512;
    protected int astLengthPtr;
    protected int astPtr;
    public boolean checkDocComment;
    protected long complianceLevel;
    protected boolean deprecated;
    protected int firstTagPosition;
    protected int identifierLengthPtr;
    protected int identifierPtr;
    protected int index;
    protected long[] inheritedPositions;
    protected int inheritedPositionsPtr;
    protected int inlineTagStart;
    protected int javadocEnd;
    protected int javadocStart;
    protected int javadocTextStart;
    protected int kind;
    protected int lastIdentifierEndPosition;
    private int lastLinePtr;
    protected int lineEnd;
    protected int[] lineEnds;
    private int linePtr;
    protected int memberStart;
    public boolean reportProblems;
    protected Object returnStatement;
    public char[] source;
    protected long sourceLevel;
    protected Parser sourceParser;
    protected int starPosition;
    protected int tagSourceEnd;
    protected int tagSourceStart;
    protected int textStart;
    protected int tokenPreviousPosition;
    private int currentTokenType = -1;
    public boolean setJavadocPositions = false;
    protected int javadocTextEnd = -1;
    protected boolean lineStarted = false;
    protected boolean inlineTagStarted = false;
    protected boolean abort = false;
    protected int tagValue = 0;
    protected int lastBlockTagValue = 0;
    public Scanner scanner = new Scanner(false, false, false, ClassFileConstants.JDK1_3, null, null, true);
    protected char[][] identifierStack = new char[20][];
    protected long[] identifierPositionStack = new long[20];
    protected int[] identifierLengthStack = new int[10];
    protected Object[] astStack = new Object[30];
    protected int[] astLengthStack = new int[20];

    protected abstract Object createArgumentReference(char[] cArr, int i, boolean z, Object obj, long[] jArr, long j) throws InvalidInputException;

    protected boolean createFakeReference(int i) {
        return true;
    }

    protected abstract Object createFieldReference(Object obj) throws InvalidInputException;

    protected abstract Object createMethodReference(Object obj, List list) throws InvalidInputException;

    protected Object createReturnStatement() {
        return null;
    }

    protected abstract void createTag();

    protected abstract Object createTypeReference(int i);

    protected boolean parseHtmlTag(int i, int i2) throws InvalidInputException {
        return false;
    }

    protected abstract boolean parseTag(int i) throws InvalidInputException;

    protected abstract boolean pushParamName(boolean z);

    protected abstract boolean pushSeeRef(Object obj);

    protected void pushText(int i, int i2) {
    }

    protected abstract boolean pushThrowName(Object obj);

    protected void refreshInlineTagPosition(int i) {
    }

    protected void refreshReturnStatement() {
    }

    protected Object syntaxRecoverQualifiedName(int i) throws InvalidInputException {
        return null;
    }

    protected abstract void updateDocComment();

    protected AbstractCommentParser(Parser parser) {
        this.checkDocComment = false;
        this.sourceParser = parser;
        this.reportProblems = parser != null;
        if (parser != null) {
            this.checkDocComment = this.sourceParser.options.docCommentSupport;
            long j = this.sourceParser.options.sourceLevel;
            this.sourceLevel = j;
            this.scanner.sourceLevel = j;
            this.complianceLevel = this.sourceParser.options.complianceLevel;
        }
    }

    /* JADX WARN: Code duplicated, block: B:216:0x0284 A[Catch: Exception -> 0x0357, TryCatch #5 {Exception -> 0x0357, blocks: (B:39:0x00a7, B:41:0x00ab, B:44:0x00b3, B:46:0x00b8, B:47:0x00bb, B:49:0x00bf, B:195:0x024a, B:198:0x025a, B:201:0x0261, B:205:0x0267, B:207:0x026f, B:209:0x0274, B:211:0x0278, B:212:0x027b, B:214:0x027f, B:217:0x0286, B:216:0x0284, B:76:0x0106, B:78:0x010a, B:80:0x010e, B:81:0x0111, B:83:0x0115, B:85:0x011d, B:88:0x0124, B:89:0x0127, B:91:0x012c, B:98:0x013b, B:95:0x0135, B:97:0x0139, B:100:0x0144, B:102:0x0148, B:104:0x014c, B:105:0x014f, B:109:0x0157, B:114:0x0160, B:115:0x016b, B:117:0x016f, B:120:0x0176, B:121:0x0179, B:129:0x0191, B:131:0x0195, B:132:0x0197, B:123:0x0182, B:125:0x0188, B:127:0x018d, B:128:0x018f, B:133:0x019e, B:137:0x01a5, B:139:0x01a9, B:141:0x01ad, B:143:0x01b1, B:188:0x023b, B:145:0x01b7, B:147:0x01bc, B:150:0x01c2, B:154:0x01ca, B:159:0x01d3, B:160:0x01de, B:163:0x01e5, B:166:0x01ec, B:170:0x01f6, B:173:0x01fd, B:174:0x0200, B:179:0x0211, B:180:0x021d, B:183:0x0225, B:184:0x0228, B:187:0x0237, B:175:0x0207, B:178:0x020e, B:221:0x0297, B:225:0x029f, B:227:0x02a7, B:231:0x02b2, B:239:0x02c9, B:242:0x02d1, B:245:0x02d8, B:248:0x02df, B:252:0x02e6, B:254:0x02f3, B:257:0x02fb, B:260:0x0301, B:50:0x00c4, B:53:0x00d1, B:56:0x00da, B:261:0x0304, B:263:0x030e, B:268:0x0316, B:272:0x031e, B:273:0x0329, B:275:0x032d, B:278:0x0334, B:279:0x0337), top: B:308:0x00a7 }] */
    /* JADX WARN: Code duplicated, block: B:234:0x02bc  */
    /* JADX WARN: Multi-variable type inference failed */
    protected boolean commentParse() {
        int i;
        char c;
        int i2;
        int i3;
        int currentTokenStartPosition;
        char c2;
        int i4;
        boolean z;
        int i5;
        int i6;
        int i7 = 0;
        try {
            this.astLengthPtr = -1;
            this.astPtr = -1;
            this.identifierPtr = -1;
            this.currentTokenType = -1;
            setInlineTagStarted(false);
            this.inlineTagStart = -1;
            this.lineStarted = false;
            this.returnStatement = null;
            this.inheritedPositions = null;
            this.lastBlockTagValue = 0;
            this.deprecated = false;
            this.lastLinePtr = getLineNumber(this.javadocEnd);
            this.textStart = -1;
            this.abort = false;
            int i8 = this.kind;
            int i9 = 1;
            boolean z2 = (i8 & 512) != 0;
            boolean z3 = (i8 & 2) != 0;
            char c3 = StringUtil.SPACE;
            boolean z4 = (i8 & 32) != 0;
            int lineNumber = getLineNumber(this.firstTagPosition);
            this.linePtr = lineNumber;
            int lineEnd = lineNumber == 1 ? this.javadocStart : this.scanner.getLineEnd(lineNumber - 1) + 1;
            int i10 = this.javadocStart;
            if (lineEnd < i10) {
                lineEnd = i10;
            }
            this.scanner.resetTo(lineEnd, this.javadocEnd);
            this.index = lineEnd;
            if (lineEnd == this.javadocStart) {
                readChar();
                readChar();
            }
            char c4 = '*';
            if (lineEnd == this.javadocStart) {
                c = readChar();
                while (peekChar() == '*') {
                    c = readChar();
                }
                this.javadocTextStart = this.index;
            } else {
                c = 0;
            }
            int i11 = this.linePtr;
            this.lineEnd = i11 == this.lastLinePtr ? this.javadocEnd : this.scanner.getLineEnd(i11) - 1;
            this.javadocTextEnd = this.javadocEnd - 2;
            int i12 = -1;
            int i13 = -1;
            int i14 = -1;
            int i15 = -1;
            boolean z5 = true;
            int i16 = 1;
            while (!this.abort && (currentTokenStartPosition = this.index) < this.javadocEnd) {
                try {
                    if (currentTokenStartPosition > this.lineEnd + i9) {
                        updateLineEnd();
                    }
                    if (this.currentTokenType < 0) {
                        c = readChar();
                    } else {
                        currentTokenStartPosition = this.scanner.getCurrentTokenStartPosition();
                        int i17 = this.currentTokenType;
                        if (i17 != 6) {
                            c = i17 != c3 ? this.scanner.currentCharacter : '}';
                        } else {
                            c = c4;
                        }
                        consumeToken();
                    }
                    if (c != '\t') {
                        if (c != '\n') {
                            if (c != '\f') {
                                if (c != '\r') {
                                    if (c != c3) {
                                        if (c != c4) {
                                            if (c == '/') {
                                                c2 = c4;
                                                if (c == c2) {
                                                    i4 = 1;
                                                }
                                            } else if (c != '@') {
                                                if (c == '{') {
                                                    if (z2 && this.tagValue == 3 && this.returnStatement != null) {
                                                        refreshReturnStatement();
                                                    }
                                                    if (this.inlineTagStarted) {
                                                        try {
                                                            setInlineTagStarted(false);
                                                            if (this.reportProblems) {
                                                                this.sourceParser.problemReporter().javadocUnterminatedInlineTag(this.inlineTagStart, currentTokenStartPosition < i12 ? currentTokenStartPosition : i12);
                                                            }
                                                            if (this.lineStarted && (i5 = this.textStart) != -1 && i5 < i13) {
                                                                pushText(i5, i13);
                                                            }
                                                            refreshInlineTagPosition(i13);
                                                            i13 = this.index;
                                                        } catch (Exception unused) {
                                                            i = 0;
                                                            return i;
                                                        }
                                                    } else if (peekChar() != '@') {
                                                        if (this.textStart == -1) {
                                                            this.textStart = currentTokenStartPosition;
                                                        }
                                                        i13 = this.index;
                                                    }
                                                    if (!this.lineStarted) {
                                                        this.textStart = currentTokenStartPosition;
                                                    }
                                                    this.lineStarted = true;
                                                    this.inlineTagStart = currentTokenStartPosition;
                                                } else if (c == '}') {
                                                    if (z2 && this.tagValue == 3 && this.returnStatement != null) {
                                                        refreshReturnStatement();
                                                    }
                                                    if (this.inlineTagStarted) {
                                                        int i18 = this.index - 1;
                                                        if (this.lineStarted && (i6 = this.textStart) != -1 && i6 < i18) {
                                                            pushText(i6, i18);
                                                        }
                                                        refreshInlineTagPosition(currentTokenStartPosition);
                                                        if (!z4) {
                                                            this.textStart = this.index;
                                                        }
                                                        i7 = 0;
                                                        setInlineTagStarted(false);
                                                    } else if (!this.lineStarted) {
                                                        this.textStart = currentTokenStartPosition;
                                                    }
                                                    this.lineStarted = true;
                                                    i13 = this.index;
                                                }
                                                c = c;
                                                i9 = 1;
                                                c3 = StringUtil.SPACE;
                                                c4 = '*';
                                            } else {
                                                if (!this.lineStarted || c == '{') {
                                                    if (this.inlineTagStarted) {
                                                        try {
                                                            setInlineTagStarted(false);
                                                            if (this.reportProblems) {
                                                                this.sourceParser.problemReporter().javadocUnterminatedInlineTag(this.inlineTagStart, currentTokenStartPosition < i12 ? currentTokenStartPosition : i12);
                                                            }
                                                            int i19 = this.textStart;
                                                            if (i19 != -1 && i19 < i13) {
                                                                pushText(i19, i13);
                                                            }
                                                            if (z3 || z4) {
                                                                refreshInlineTagPosition(i13);
                                                            }
                                                            z5 = false;
                                                        } catch (Exception unused2) {
                                                            i = 0;
                                                            return i;
                                                        }
                                                    }
                                                    if (c == '{') {
                                                        int i20 = this.textStart;
                                                        if (i20 != -1 && i20 < i13) {
                                                            pushText(i20, i13);
                                                        }
                                                        setInlineTagStarted(true);
                                                        i12 = this.lineEnd;
                                                    } else {
                                                        int i21 = this.textStart;
                                                        if (i21 != -1 && i21 < i15) {
                                                            pushText(i21, i15);
                                                        }
                                                    }
                                                    this.scanner.resetTo(this.index, this.javadocEnd);
                                                    this.currentTokenType = -1;
                                                    try {
                                                        if (!parseTag(currentTokenStartPosition)) {
                                                            if (z3) {
                                                                try {
                                                                    createTag();
                                                                } catch (InvalidInputException unused3) {
                                                                    z5 = false;
                                                                    consumeToken();
                                                                    z = true;
                                                                }
                                                            }
                                                            this.textStart = this.tagSourceEnd + 1;
                                                            i15 = this.lineEnd;
                                                            i13 = this.index;
                                                            z = true;
                                                            z5 = false;
                                                        }
                                                    } catch (InvalidInputException unused4) {
                                                    }
                                                    this.lineStarted = z;
                                                    c = c;
                                                    i9 = 1;
                                                    c3 = StringUtil.SPACE;
                                                    c4 = '*';
                                                } else {
                                                    int i22 = this.index;
                                                    if (z2 && this.tagValue == 3 && this.returnStatement != null) {
                                                        refreshReturnStatement();
                                                    } else if (z4 && this.textStart == -1) {
                                                        this.textStart = currentTokenStartPosition;
                                                    }
                                                    i13 = i22;
                                                }
                                                z = true;
                                                this.lineStarted = z;
                                                c = c;
                                                i9 = 1;
                                                c3 = StringUtil.SPACE;
                                                c4 = '*';
                                            }
                                            if (z4 && c == '<') {
                                                int i23 = this.index;
                                                this.scanner.resetTo(i23, this.javadocEnd);
                                                if (!ScannerHelper.isWhitespace(c)) {
                                                    i13 = currentTokenStartPosition;
                                                }
                                                if (!parseHtmlTag(currentTokenStartPosition, i13)) {
                                                    if (this.abort) {
                                                        return false;
                                                    }
                                                    this.scanner.currentPosition = i23;
                                                    this.index = i23;
                                                    if (z2) {
                                                        refreshReturnStatement();
                                                    }
                                                    if (this.lineStarted) {
                                                        this.textStart = currentTokenStartPosition;
                                                    } else {
                                                        this.textStart = currentTokenStartPosition;
                                                    }
                                                    this.lineStarted = true;
                                                    i13 = this.index;
                                                }
                                            } else {
                                                if (z2 && this.tagValue == 3 && this.returnStatement != null) {
                                                    refreshReturnStatement();
                                                }
                                                if (this.lineStarted || this.textStart == -1) {
                                                    this.textStart = currentTokenStartPosition;
                                                }
                                                this.lineStarted = true;
                                                i13 = this.index;
                                            }
                                            c = c;
                                            i9 = 1;
                                            c3 = StringUtil.SPACE;
                                            c4 = '*';
                                        } else {
                                            c2 = c4;
                                            if (c != c2) {
                                                this.starPosition = currentTokenStartPosition;
                                                if (z3 || z4) {
                                                    if (i16 != 0) {
                                                        i4 = 1;
                                                        this.lineStarted = true;
                                                        if (this.textStart == -1) {
                                                            this.textStart = currentTokenStartPosition;
                                                            int i24 = this.index;
                                                            if (i24 <= this.javadocTextEnd) {
                                                                i13 = i24;
                                                            }
                                                        }
                                                    } else {
                                                        i4 = 1;
                                                    }
                                                    if (!this.lineStarted) {
                                                        i14 = currentTokenStartPosition;
                                                        c4 = c2;
                                                        i9 = i4;
                                                        i16 = i9;
                                                    }
                                                } else {
                                                    i4 = 1;
                                                }
                                                i14 = currentTokenStartPosition;
                                            } else {
                                                i4 = 1;
                                                i14 = currentTokenStartPosition;
                                            }
                                            c3 = StringUtil.SPACE;
                                        }
                                        c4 = c2;
                                        i9 = i4;
                                        c3 = StringUtil.SPACE;
                                    }
                                }
                            }
                        }
                        char c5 = c4;
                        if (this.lineStarted) {
                            if (z4 && !ScannerHelper.isWhitespace(c)) {
                                i13 = currentTokenStartPosition;
                            }
                            int i25 = this.textStart;
                            if (i25 != -1 && i25 < i13) {
                                pushText(i25, i13);
                            }
                        }
                        this.lineStarted = false;
                        this.textStart = -1;
                        c = c;
                        c4 = c5;
                        i9 = 1;
                        c3 = StringUtil.SPACE;
                        i16 = 0;
                    }
                    c2 = c4;
                    i4 = 1;
                    if (z4) {
                        if (!ScannerHelper.isWhitespace(c)) {
                            i13 = currentTokenStartPosition;
                        }
                    } else if (this.lineStarted && z3) {
                        i13 = this.index;
                    }
                    c4 = c2;
                    i9 = i4;
                    c3 = StringUtil.SPACE;
                } catch (Exception unused5) {
                    i = 0;
                }
            }
            int i26 = this.starPosition;
            i = i26 - 1;
            this.javadocTextEnd = i;
            try {
                if (this.inlineTagStarted) {
                    if (this.reportProblems) {
                        if (i >= i12) {
                            i = i12;
                        }
                        if (this.index < this.javadocEnd) {
                            i12 = i;
                        }
                        this.sourceParser.problemReporter().javadocUnterminatedInlineTag(this.inlineTagStart, i12);
                    }
                    if (this.lineStarted && (i3 = this.textStart) != -1 && i3 < i13) {
                        pushText(i3, i13);
                    }
                    refreshInlineTagPosition(i13);
                    i = 0;
                    setInlineTagStarted(false);
                } else {
                    i = 0;
                    if (this.lineStarted && (i2 = this.textStart) != -1 && i2 <= i13 && (i2 < i26 || i26 == i14)) {
                        pushText(i2, i13);
                    }
                }
                updateDocComment();
                return z5;
            } catch (Exception unused6) {
                return i;
            }
        } catch (Exception unused7) {
            i = i7;
        }
    }

    protected void consumeToken() {
        this.currentTokenType = -1;
        updateLineEnd();
    }

    private int getIndexPosition() {
        int i = this.index;
        int i2 = this.lineEnd;
        return i > i2 ? i2 : i - 1;
    }

    private int getLineNumber(int i) {
        if (this.scanner.linePtr != -1) {
            return Util.getLineNumber(i, this.scanner.lineEnds, 0, this.scanner.linePtr);
        }
        int[] iArr = this.lineEnds;
        if (iArr == null) {
            return 1;
        }
        return Util.getLineNumber(i, iArr, 0, iArr.length - 1);
    }

    private int getTokenEndPosition() {
        int currentTokenEndPosition = this.scanner.getCurrentTokenEndPosition();
        int i = this.lineEnd;
        return currentTokenEndPosition > i ? i : this.scanner.getCurrentTokenEndPosition();
    }

    protected int getCurrentTokenType() {
        return this.currentTokenType;
    }

    /* JADX WARN: Code duplicated, block: B:108:0x003f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:109:0x0194 A[EDGE_INSN: B:109:0x0194->B:99:0x0194 BREAK  A[LOOP:0: B:3:0x001a->B:78:0x013b, LOOP_LABEL: LOOP:0: B:3:0x001a->B:78:0x013b], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:110:0x0194 A[EDGE_INSN: B:110:0x0194->B:99:0x0194 BREAK  A[LOOP:0: B:3:0x001a->B:78:0x013b], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:111:0x0194 A[EDGE_INSN: B:111:0x0194->B:99:0x0194 BREAK  A[LOOP:0: B:3:0x001a->B:78:0x013b], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:113:0x0194 A[EDGE_INSN: B:113:0x0194->B:99:0x0194 BREAK  A[LOOP:0: B:3:0x001a->B:78:0x013b], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:114:0x0194 A[EDGE_INSN: B:114:0x0194->B:99:0x0194 BREAK  A[LOOP:0: B:3:0x001a->B:78:0x013b], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:115:0x014e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:116:0x0139 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:118:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:17:0x003a  */
    /* JADX WARN: Code duplicated, block: B:24:0x004b  */
    /* JADX WARN: Code duplicated, block: B:26:0x004f  */
    /* JADX WARN: Code duplicated, block: B:29:0x0057  */
    /* JADX WARN: Code duplicated, block: B:32:0x005d  */
    /* JADX WARN: Code duplicated, block: B:34:0x0067  */
    /* JADX WARN: Code duplicated, block: B:36:0x006e  */
    /* JADX WARN: Code duplicated, block: B:38:0x0078  */
    /* JADX WARN: Code duplicated, block: B:42:0x0083  */
    /* JADX WARN: Code duplicated, block: B:44:0x0094 A[LOOP:1: B:39:0x0079->B:44:0x0094, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:45:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:47:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:48:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:51:0x00df  */
    /* JADX WARN: Code duplicated, block: B:53:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:56:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:58:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:59:0x00ee A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:61:0x010a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:62:0x010c  */
    /* JADX WARN: Code duplicated, block: B:65:0x0113  */
    /* JADX WARN: Code duplicated, block: B:66:0x0116  */
    /* JADX WARN: Code duplicated, block: B:70:0x0124  */
    /* JADX WARN: Code duplicated, block: B:71:0x0127  */
    /* JADX WARN: Code duplicated, block: B:74:0x012c  */
    /* JADX WARN: Code duplicated, block: B:78:0x013b A[LOOP:0: B:3:0x001a->B:78:0x013b, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:81:0x0152  */
    /* JADX WARN: Code duplicated, block: B:83:0x0158  */
    /* JADX WARN: Code duplicated, block: B:85:0x015d  */
    /* JADX WARN: Code duplicated, block: B:88:0x0167  */
    /* JADX WARN: Code duplicated, block: B:91:0x016d  */
    /* JADX WARN: Code duplicated, block: B:94:0x017a  */
    /* JADX WARN: Code duplicated, block: B:96:0x0188 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:97:0x0189  */
    protected Object parseArguments(Object obj) throws InvalidInputException {
        int i;
        int i2;
        int i3;
        boolean z;
        int i4;
        long currentTokenStartPosition;
        long j;
        char[] cArr;
        int i5;
        int i6;
        int token;
        char[] cArr2;
        Object objCreateArgumentReference;
        int i7;
        Object objCreateArgumentReference2;
        int currentTokenStartPosition2;
        char c = '\n';
        ArrayList arrayList = new ArrayList(10);
        int currentTokenStartPosition3 = this.scanner.getCurrentTokenStartPosition();
        long[] jArr = new long[20];
        boolean z2 = false;
        List list = null;
        int i8 = 0;
        int i9 = 0;
        char[] currentIdentifierSource = null;
        loop0: while (this.index < this.scanner.eofPosition) {
            try {
                Object qualifiedName = parseQualifiedName(z2);
                if (!this.abort) {
                    boolean z3 = i8 == 0 ? true : z2;
                    if (!z3) {
                        if (i9 % i8 != 0) {
                            break;
                        }
                        if (qualifiedName != null) {
                            i2 = i9 + 1;
                            if (readToken() == c) {
                                i4 = z2;
                                while (readToken() == c) {
                                    currentTokenStartPosition2 = this.scanner.getCurrentTokenStartPosition();
                                    consumeToken();
                                    if (readToken() == 64) {
                                        break loop0;
                                        break loop0;
                                    }
                                    consumeToken();
                                    jArr[i4] = (((long) currentTokenStartPosition2) << 32) + ((long) this.scanner.getCurrentTokenEndPosition());
                                    i4++;
                                    currentTokenStartPosition3 = currentTokenStartPosition3;
                                    c = '\n';
                                    z2 = false;
                                }
                                i3 = currentTokenStartPosition3;
                                z = z2;
                            } else {
                                i3 = currentTokenStartPosition3;
                                if (readToken() == 113) {
                                    jArr[0] = (((long) this.scanner.getCurrentTokenStartPosition()) << 32) + ((long) this.scanner.getCurrentTokenEndPosition());
                                    consumeToken();
                                    z = true;
                                    i4 = 1;
                                } else {
                                    z = false;
                                    i4 = 0;
                                }
                            }
                            if (readToken() == 22) {
                                if (currentIdentifierSource == null) {
                                    break;
                                    break;
                                }
                                currentTokenStartPosition = -1;
                                j = currentTokenStartPosition;
                                cArr = currentIdentifierSource;
                                i5 = i2;
                                if (z3) {
                                    if (i5 % i8 == i8 - 1) {
                                        break;
                                        break;
                                    }
                                } else {
                                    i8 = i5 + 1;
                                }
                                i6 = i8;
                                token = readToken();
                                if (cArr == null) {
                                    cArr2 = CharOperation.NO_CHAR;
                                } else {
                                    cArr2 = cArr;
                                }
                                if (token == 33) {
                                    if (token == 25) {
                                        break;
                                    }
                                    if (!verifySpaceOrEndComment()) {
                                        i7 = this.starPosition;
                                        if (i7 == -1) {
                                            i7 = this.lineEnd;
                                        }
                                        if (this.source[i7] == '\n') {
                                            i7--;
                                        }
                                        if (this.reportProblems) {
                                            return null;
                                        }
                                        this.sourceParser.problemReporter().javadocMalformedSeeReference(i3, i7);
                                        return null;
                                    }
                                    objCreateArgumentReference = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                                    if (this.abort) {
                                        return null;
                                    }
                                    arrayList.add(objCreateArgumentReference);
                                    consumeToken();
                                    return createMethodReference(obj, arrayList);
                                }
                                objCreateArgumentReference2 = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                                if (this.abort) {
                                    return null;
                                }
                                arrayList.add(objCreateArgumentReference2);
                                consumeToken();
                                i9 = i5 + 1;
                                currentIdentifierSource = cArr;
                                i8 = i6;
                                currentTokenStartPosition3 = i3;
                                c = '\n';
                                z2 = false;
                                list = null;
                            } else {
                                consumeToken();
                                if (z3) {
                                    if (i2 % i8 == 1) {
                                        break;
                                        break;
                                    }
                                    if (currentIdentifierSource != null) {
                                    }
                                    currentIdentifierSource = this.scanner.getCurrentIdentifierSource();
                                    currentTokenStartPosition = (((long) this.scanner.getCurrentTokenStartPosition()) << 32) + ((long) this.scanner.getCurrentTokenEndPosition());
                                    i2 = i9 + 2;
                                    j = currentTokenStartPosition;
                                    cArr = currentIdentifierSource;
                                    i5 = i2;
                                    if (z3) {
                                        if (i5 % i8 == i8 - 1) {
                                            break;
                                            break;
                                        }
                                    } else {
                                        i8 = i5 + 1;
                                    }
                                    i6 = i8;
                                    token = readToken();
                                    if (cArr == null) {
                                        cArr2 = CharOperation.NO_CHAR;
                                    } else {
                                        cArr2 = cArr;
                                    }
                                    if (token == 33) {
                                        if (token == 25) {
                                            break;
                                        }
                                        if (!verifySpaceOrEndComment()) {
                                            i7 = this.starPosition;
                                            if (i7 == -1) {
                                                i7 = this.lineEnd;
                                            }
                                            if (this.source[i7] == '\n') {
                                                i7--;
                                            }
                                            if (this.reportProblems) {
                                                return null;
                                            }
                                            this.sourceParser.problemReporter().javadocMalformedSeeReference(i3, i7);
                                            return null;
                                        }
                                        objCreateArgumentReference = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                                        if (this.abort) {
                                            return null;
                                        }
                                        arrayList.add(objCreateArgumentReference);
                                        consumeToken();
                                        return createMethodReference(obj, arrayList);
                                    }
                                    objCreateArgumentReference2 = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                                    if (this.abort) {
                                        return null;
                                    }
                                    arrayList.add(objCreateArgumentReference2);
                                    consumeToken();
                                    i9 = i5 + 1;
                                    currentIdentifierSource = cArr;
                                    i8 = i6;
                                    currentTokenStartPosition3 = i3;
                                    c = '\n';
                                    z2 = false;
                                    list = null;
                                } else {
                                    if (i2 == 1) {
                                        break;
                                        break;
                                    }
                                    if (currentIdentifierSource != null) {
                                    }
                                    currentIdentifierSource = this.scanner.getCurrentIdentifierSource();
                                    currentTokenStartPosition = (((long) this.scanner.getCurrentTokenStartPosition()) << 32) + ((long) this.scanner.getCurrentTokenEndPosition());
                                    i2 = i9 + 2;
                                    j = currentTokenStartPosition;
                                    cArr = currentIdentifierSource;
                                    i5 = i2;
                                    if (z3) {
                                        if (i5 % i8 == i8 - 1) {
                                            break;
                                            break;
                                        }
                                    } else {
                                        i8 = i5 + 1;
                                    }
                                    i6 = i8;
                                    token = readToken();
                                    if (cArr == null) {
                                        cArr2 = CharOperation.NO_CHAR;
                                    } else {
                                        cArr2 = cArr;
                                    }
                                    if (token == 33) {
                                        if (token == 25) {
                                            break;
                                        }
                                        if (!verifySpaceOrEndComment()) {
                                            i7 = this.starPosition;
                                            if (i7 == -1) {
                                                i7 = this.lineEnd;
                                            }
                                            if (this.source[i7] == '\n') {
                                                i7--;
                                            }
                                            if (this.reportProblems) {
                                                return null;
                                            }
                                            this.sourceParser.problemReporter().javadocMalformedSeeReference(i3, i7);
                                            return null;
                                        }
                                        objCreateArgumentReference = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                                        if (this.abort) {
                                            return null;
                                        }
                                        arrayList.add(objCreateArgumentReference);
                                        consumeToken();
                                        return createMethodReference(obj, arrayList);
                                    }
                                    objCreateArgumentReference2 = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                                    if (this.abort) {
                                        return null;
                                    }
                                    arrayList.add(objCreateArgumentReference2);
                                    consumeToken();
                                    i9 = i5 + 1;
                                    currentIdentifierSource = cArr;
                                    i8 = i6;
                                    currentTokenStartPosition3 = i3;
                                    c = '\n';
                                    z2 = false;
                                    list = null;
                                }
                            }
                        } else {
                            if (z3) {
                                break;
                            }
                            if (!verifySpaceOrEndComment()) {
                                i = this.starPosition;
                                if (i == -1) {
                                    i = this.lineEnd;
                                }
                                if (this.source[i] == c) {
                                    i--;
                                }
                                if (this.reportProblems) {
                                    this.sourceParser.problemReporter().javadocMalformedSeeReference(currentTokenStartPosition3, i);
                                }
                                return list;
                            }
                            this.lineStarted = true;
                            return createMethodReference(obj, list);
                        }
                    } else {
                        if (i9 != 0) {
                            break;
                        }
                        if (qualifiedName != null) {
                            if (z3 || this.currentTokenType != 25) {
                                break;
                                break;
                            }
                            if (!verifySpaceOrEndComment()) {
                                i = this.starPosition;
                                if (i == -1) {
                                    i = this.lineEnd;
                                }
                                if (this.source[i] == c) {
                                    i--;
                                }
                                if (this.reportProblems) {
                                    this.sourceParser.problemReporter().javadocMalformedSeeReference(currentTokenStartPosition3, i);
                                }
                                return list;
                            }
                            this.lineStarted = true;
                            return createMethodReference(obj, list);
                        }
                        i2 = i9 + 1;
                        if (readToken() == c) {
                            i4 = z2;
                            while (readToken() == c) {
                                currentTokenStartPosition2 = this.scanner.getCurrentTokenStartPosition();
                                consumeToken();
                                if (readToken() == 64) {
                                    break loop0;
                                }
                                consumeToken();
                                jArr[i4] = (((long) currentTokenStartPosition2) << 32) + ((long) this.scanner.getCurrentTokenEndPosition());
                                i4++;
                                currentTokenStartPosition3 = currentTokenStartPosition3;
                                c = '\n';
                                z2 = false;
                            }
                            i3 = currentTokenStartPosition3;
                            z = z2;
                        } else {
                            i3 = currentTokenStartPosition3;
                            if (readToken() == 113) {
                                jArr[0] = (((long) this.scanner.getCurrentTokenStartPosition()) << 32) + ((long) this.scanner.getCurrentTokenEndPosition());
                                consumeToken();
                                z = true;
                                i4 = 1;
                            } else {
                                z = false;
                                i4 = 0;
                            }
                        }
                        if (readToken() == 22) {
                            if (currentIdentifierSource == null) {
                                break;
                            }
                            currentTokenStartPosition = -1;
                            j = currentTokenStartPosition;
                            cArr = currentIdentifierSource;
                            i5 = i2;
                            if (z3) {
                                if (i5 % i8 == i8 - 1) {
                                    break;
                                    break;
                                }
                            } else {
                                i8 = i5 + 1;
                            }
                            i6 = i8;
                            token = readToken();
                            if (cArr == null) {
                                cArr2 = CharOperation.NO_CHAR;
                            } else {
                                cArr2 = cArr;
                            }
                            if (token == 33) {
                                if (token == 25) {
                                    break;
                                }
                                if (!verifySpaceOrEndComment()) {
                                    i7 = this.starPosition;
                                    if (i7 == -1) {
                                        i7 = this.lineEnd;
                                    }
                                    if (this.source[i7] == '\n') {
                                        i7--;
                                    }
                                    if (this.reportProblems) {
                                        return null;
                                    }
                                    this.sourceParser.problemReporter().javadocMalformedSeeReference(i3, i7);
                                    return null;
                                }
                                objCreateArgumentReference = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                                if (this.abort) {
                                    return null;
                                }
                                arrayList.add(objCreateArgumentReference);
                                consumeToken();
                                return createMethodReference(obj, arrayList);
                            }
                            objCreateArgumentReference2 = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                            if (this.abort) {
                                return null;
                            }
                            arrayList.add(objCreateArgumentReference2);
                            consumeToken();
                            i9 = i5 + 1;
                            currentIdentifierSource = cArr;
                            i8 = i6;
                            currentTokenStartPosition3 = i3;
                            c = '\n';
                            z2 = false;
                            list = null;
                        } else {
                            consumeToken();
                            if (z3) {
                                if (i2 % i8 == 1) {
                                    break;
                                }
                                if (currentIdentifierSource != null) {
                                }
                                currentIdentifierSource = this.scanner.getCurrentIdentifierSource();
                                currentTokenStartPosition = (((long) this.scanner.getCurrentTokenStartPosition()) << 32) + ((long) this.scanner.getCurrentTokenEndPosition());
                                i2 = i9 + 2;
                                j = currentTokenStartPosition;
                                cArr = currentIdentifierSource;
                                i5 = i2;
                                if (z3) {
                                    if (i5 % i8 == i8 - 1) {
                                        break;
                                        break;
                                    }
                                } else {
                                    i8 = i5 + 1;
                                }
                                i6 = i8;
                                token = readToken();
                                if (cArr == null) {
                                    cArr2 = CharOperation.NO_CHAR;
                                } else {
                                    cArr2 = cArr;
                                }
                                if (token == 33) {
                                    if (token == 25) {
                                        break;
                                    }
                                    if (!verifySpaceOrEndComment()) {
                                        i7 = this.starPosition;
                                        if (i7 == -1) {
                                            i7 = this.lineEnd;
                                        }
                                        if (this.source[i7] == '\n') {
                                            i7--;
                                        }
                                        if (this.reportProblems) {
                                            return null;
                                        }
                                        this.sourceParser.problemReporter().javadocMalformedSeeReference(i3, i7);
                                        return null;
                                    }
                                    objCreateArgumentReference = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                                    if (this.abort) {
                                        return null;
                                    }
                                    arrayList.add(objCreateArgumentReference);
                                    consumeToken();
                                    return createMethodReference(obj, arrayList);
                                }
                                objCreateArgumentReference2 = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                                if (this.abort) {
                                    return null;
                                }
                                arrayList.add(objCreateArgumentReference2);
                                consumeToken();
                                i9 = i5 + 1;
                                currentIdentifierSource = cArr;
                                i8 = i6;
                                currentTokenStartPosition3 = i3;
                                c = '\n';
                                z2 = false;
                                list = null;
                            } else {
                                if (i2 == 1) {
                                    break;
                                }
                                if (currentIdentifierSource != null && !z3) {
                                    break;
                                }
                                currentIdentifierSource = this.scanner.getCurrentIdentifierSource();
                                currentTokenStartPosition = (((long) this.scanner.getCurrentTokenStartPosition()) << 32) + ((long) this.scanner.getCurrentTokenEndPosition());
                                i2 = i9 + 2;
                                j = currentTokenStartPosition;
                                cArr = currentIdentifierSource;
                                i5 = i2;
                                if (z3) {
                                    if (i5 % i8 == i8 - 1) {
                                        break;
                                    }
                                } else {
                                    i8 = i5 + 1;
                                }
                                i6 = i8;
                                token = readToken();
                                if (cArr == null) {
                                    cArr2 = CharOperation.NO_CHAR;
                                } else {
                                    cArr2 = cArr;
                                }
                                if (token == 33) {
                                    if (token == 25) {
                                        break;
                                    }
                                    if (!verifySpaceOrEndComment()) {
                                        i7 = this.starPosition;
                                        if (i7 == -1) {
                                            i7 = this.lineEnd;
                                        }
                                        if (this.source[i7] == '\n') {
                                            i7--;
                                        }
                                        if (this.reportProblems) {
                                            return null;
                                        }
                                        this.sourceParser.problemReporter().javadocMalformedSeeReference(i3, i7);
                                        return null;
                                    }
                                    objCreateArgumentReference = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                                    if (this.abort) {
                                        return null;
                                    }
                                    arrayList.add(objCreateArgumentReference);
                                    consumeToken();
                                    return createMethodReference(obj, arrayList);
                                }
                                objCreateArgumentReference2 = createArgumentReference(cArr2, i4, z, qualifiedName, jArr, j);
                                if (this.abort) {
                                    return null;
                                }
                                arrayList.add(objCreateArgumentReference2);
                                consumeToken();
                                i9 = i5 + 1;
                                currentIdentifierSource = cArr;
                                i8 = i6;
                                currentTokenStartPosition3 = i3;
                                c = '\n';
                                z2 = false;
                                list = null;
                            }
                        }
                    }
                } else {
                    return list;
                }
            } catch (InvalidInputException unused) {
            }
        }
        throw new InvalidInputException();
    }

    /* JADX WARN: Code duplicated, block: B:75:0x0127 A[Catch: all -> 0x0145, EDGE_INSN: B:90:0x0127->B:75:0x0127 BREAK  A[LOOP:0: B:16:0x0054->B:99:?], EDGE_INSN: B:91:0x0127->B:75:0x0127 BREAK  A[LOOP:0: B:16:0x0054->B:99:?], EDGE_INSN: B:92:0x0127->B:75:0x0127 BREAK  A[LOOP:0: B:16:0x0054->B:99:?], EDGE_INSN: B:93:0x0127->B:75:0x0127 BREAK  A[LOOP:0: B:16:0x0054->B:99:?], PHI: r1
      0x0127: PHI (r1v8 int) = 
      (r1v3 int)
      (r1v3 int)
      (r1v3 int)
      (r1v3 int)
      (r1v3 int)
      (r1v4 int)
      (r1v5 int)
      (r1v5 int)
      (r1v5 int)
      (r1v5 int)
      (r1v3 int)
     binds: [B:7:0x002b, B:84:0x0127, B:10:0x003c, B:12:0x0044, B:14:0x004f, B:18:0x005a, B:90:0x0127, B:91:0x0127, B:92:0x0127, B:93:0x0127, B:5:0x001d] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TryCatch #1 {all -> 0x0145, blocks: (B:3:0x0009, B:75:0x0127, B:77:0x0135, B:79:0x0139, B:6:0x001f, B:8:0x002d, B:9:0x0030, B:11:0x003e, B:13:0x0046, B:15:0x0051, B:16:0x0054, B:19:0x005c, B:21:0x0068, B:22:0x006b, B:24:0x0073, B:26:0x0084, B:29:0x008c, B:42:0x00a9, B:44:0x00b3, B:46:0x00b9, B:48:0x00bd, B:51:0x00c4, B:52:0x00c8, B:54:0x00d6, B:56:0x00da, B:59:0x00ea, B:61:0x00f4, B:63:0x00fa, B:65:0x00fe, B:68:0x0105, B:69:0x0109, B:71:0x0117, B:73:0x011b), top: B:86:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:77:0x0135 A[Catch: all -> 0x0145, TryCatch #1 {all -> 0x0145, blocks: (B:3:0x0009, B:75:0x0127, B:77:0x0135, B:79:0x0139, B:6:0x001f, B:8:0x002d, B:9:0x0030, B:11:0x003e, B:13:0x0046, B:15:0x0051, B:16:0x0054, B:19:0x005c, B:21:0x0068, B:22:0x006b, B:24:0x0073, B:26:0x0084, B:29:0x008c, B:42:0x00a9, B:44:0x00b3, B:46:0x00b9, B:48:0x00bd, B:51:0x00c4, B:52:0x00c8, B:54:0x00d6, B:56:0x00da, B:59:0x00ea, B:61:0x00f4, B:63:0x00fa, B:65:0x00fe, B:68:0x0105, B:69:0x0109, B:71:0x0117, B:73:0x011b), top: B:86:0x0009 }] */
    protected boolean parseHref() throws InvalidInputException {
        boolean z = this.scanner.skipComments;
        this.scanner.skipComments = true;
        try {
            int currentTokenStartPosition = this.scanner.getCurrentTokenStartPosition();
            char c = readChar();
            if (c == 'a' || c == 'A') {
                this.scanner.currentPosition = this.index;
                if (readToken() == 22) {
                    consumeToken();
                    try {
                        if (CharOperation.equals(this.scanner.getCurrentIdentifierSource(), HREF_TAG, false) && readToken() == 70) {
                            consumeToken();
                            if (readToken() == 48) {
                                consumeToken();
                                while (this.index < this.javadocEnd) {
                                    while (readToken() != 15) {
                                        if (this.scanner.currentPosition < this.scanner.eofPosition && this.scanner.currentCharacter != '@' && (!this.inlineTagStarted || this.scanner.currentCharacter != '}')) {
                                            this.currentTokenType = -1;
                                        }
                                        int i = this.tokenPreviousPosition;
                                        this.index = i;
                                        this.scanner.currentPosition = i;
                                        this.currentTokenType = -1;
                                        if (this.tagValue != 10 && this.reportProblems) {
                                            this.sourceParser.problemReporter().javadocInvalidSeeHref(currentTokenStartPosition, this.lineEnd);
                                        }
                                    }
                                    consumeToken();
                                    while (readToken() != 11) {
                                        if (this.scanner.currentPosition < this.scanner.eofPosition && this.scanner.currentCharacter != '@' && (!this.inlineTagStarted || this.scanner.currentCharacter != '}')) {
                                            consumeToken();
                                        }
                                        int i2 = this.tokenPreviousPosition;
                                        this.index = i2;
                                        this.scanner.currentPosition = i2;
                                        this.currentTokenType = -1;
                                        if (this.tagValue != 10 && this.reportProblems) {
                                            this.sourceParser.problemReporter().javadocInvalidSeeHref(currentTokenStartPosition, this.lineEnd);
                                        }
                                    }
                                    consumeToken();
                                    currentTokenStartPosition = this.scanner.getCurrentTokenStartPosition();
                                    char c2 = readChar();
                                    if (c2 == '/' && ((c2 = readChar()) == 'a' || c2 == 'A')) {
                                        c2 = readChar();
                                        if (c2 == '>') {
                                            this.scanner.skipComments = z;
                                            return true;
                                        }
                                    }
                                    if (c2 == '\r' || c2 == '\n' || c2 == '\t' || c2 == ' ') {
                                        break;
                                    }
                                }
                                int i3 = this.tokenPreviousPosition;
                                this.index = i3;
                                this.scanner.currentPosition = i3;
                                this.currentTokenType = -1;
                                if (this.tagValue != 10) {
                                    this.sourceParser.problemReporter().javadocInvalidSeeHref(currentTokenStartPosition, this.lineEnd);
                                }
                            } else {
                                int i4 = this.tokenPreviousPosition;
                                this.index = i4;
                                this.scanner.currentPosition = i4;
                                this.currentTokenType = -1;
                                if (this.tagValue != 10) {
                                    this.sourceParser.problemReporter().javadocInvalidSeeHref(currentTokenStartPosition, this.lineEnd);
                                }
                            }
                        } else {
                            int i5 = this.tokenPreviousPosition;
                            this.index = i5;
                            this.scanner.currentPosition = i5;
                            this.currentTokenType = -1;
                            if (this.tagValue != 10 && this.reportProblems) {
                                this.sourceParser.problemReporter().javadocInvalidSeeHref(currentTokenStartPosition, this.lineEnd);
                            }
                        }
                    } catch (InvalidInputException unused) {
                    }
                } else {
                    int i6 = this.tokenPreviousPosition;
                    this.index = i6;
                    this.scanner.currentPosition = i6;
                    this.currentTokenType = -1;
                    if (this.tagValue != 10) {
                        this.sourceParser.problemReporter().javadocInvalidSeeHref(currentTokenStartPosition, this.lineEnd);
                    }
                }
            } else {
                int i7 = this.tokenPreviousPosition;
                this.index = i7;
                this.scanner.currentPosition = i7;
                this.currentTokenType = -1;
                if (this.tagValue != 10) {
                    this.sourceParser.problemReporter().javadocInvalidSeeHref(currentTokenStartPosition, this.lineEnd);
                }
            }
            this.scanner.skipComments = z;
            return false;
        } catch (Throwable th) {
            this.scanner.skipComments = z;
            throw th;
        }
    }

    protected boolean parseIdentifierTag(boolean z) {
        if (readTokenSafely() == 22) {
            pushIdentifier(true, false);
            return true;
        }
        if (z) {
            this.sourceParser.problemReporter().javadocMissingIdentifier(this.tagSourceStart, this.tagSourceEnd, this.sourceParser.modifiers);
        }
        return false;
    }

    protected Object parseMember(Object obj) throws InvalidInputException {
        int currentTokenStartPosition;
        this.identifierPtr = -1;
        this.identifierLengthPtr = -1;
        int currentTokenStartPosition2 = this.scanner.getCurrentTokenStartPosition();
        this.memberStart = currentTokenStartPosition2;
        if (readToken() == 22) {
            if (this.scanner.currentCharacter == '.') {
                parseQualifiedName(true);
            } else {
                consumeToken();
                pushIdentifier(true, false);
            }
            int i = this.index;
            if (readToken() == 24) {
                consumeToken();
                int currentTokenStartPosition3 = this.scanner.getCurrentTokenStartPosition();
                try {
                    return parseArguments(obj);
                } catch (InvalidInputException unused) {
                    if (this.scanner.getCurrentTokenEndPosition() < this.lineEnd) {
                        currentTokenStartPosition = this.scanner.getCurrentTokenEndPosition();
                    } else {
                        currentTokenStartPosition = this.scanner.getCurrentTokenStartPosition();
                    }
                    int i2 = this.lineEnd;
                    if (currentTokenStartPosition >= i2) {
                        currentTokenStartPosition = i2;
                    }
                    if (this.reportProblems) {
                        this.sourceParser.problemReporter().javadocInvalidSeeReferenceArgs(currentTokenStartPosition3, currentTokenStartPosition);
                    }
                    return null;
                }
            }
            this.index = i;
            this.scanner.currentPosition = i;
            this.currentTokenType = -1;
            if (!verifySpaceOrEndComment()) {
                int i3 = this.starPosition;
                if (i3 == -1) {
                    i3 = this.lineEnd;
                }
                if (this.source[i3] == '\n') {
                    i3--;
                }
                if (this.reportProblems) {
                    this.sourceParser.problemReporter().javadocMalformedSeeReference(currentTokenStartPosition2, i3);
                }
                return null;
            }
            return createFieldReference(obj);
        }
        int tokenEndPosition = getTokenEndPosition() - 1;
        if (currentTokenStartPosition2 > tokenEndPosition) {
            tokenEndPosition = currentTokenStartPosition2;
        }
        if (this.reportProblems) {
            this.sourceParser.problemReporter().javadocInvalidReference(currentTokenStartPosition2, tokenEndPosition);
        }
        int i4 = this.tokenPreviousPosition;
        this.index = i4;
        this.scanner.currentPosition = i4;
        this.currentTokenType = -1;
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:109:0x0177 A[DONT_INVERT, PHI: r7 r10 r11
      0x0177: PHI (r7v7 boolean) = (r7v6 boolean), (r7v6 boolean), (r7v13 boolean) binds: [B:51:0x00ba, B:52:0x00bc, B:108:0x0170] A[DONT_GENERATE, DONT_INLINE]
      0x0177: PHI (r10v5 int) = (r10v4 int), (r10v4 int), (r10v14 int) binds: [B:51:0x00ba, B:52:0x00bc, B:108:0x0170] A[DONT_GENERATE, DONT_INLINE]
      0x0177: PHI (r11v4 boolean) = (r11v3 boolean), (r11v3 boolean), (r11v14 boolean) binds: [B:51:0x00ba, B:52:0x00bc, B:108:0x0170] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:110:0x0179 A[Catch: all -> 0x0256, TRY_LEAVE, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:113:0x0186 A[Catch: all -> 0x0256, TRY_ENTER, TRY_LEAVE, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:119:0x019d  */
    /* JADX WARN: Code duplicated, block: B:120:0x019f A[Catch: all -> 0x0256, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:122:0x01a3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:123:0x01a5 A[Catch: all -> 0x0256, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:124:0x01a8 A[Catch: all -> 0x0256, TRY_LEAVE, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:127:0x01b4 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:136:0x01ca A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:138:0x01d6 A[Catch: all -> 0x0256, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:142:0x01ee  */
    /* JADX WARN: Code duplicated, block: B:144:0x01f1 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:147:0x01fb A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:148:0x01fd A[Catch: all -> 0x0256, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:149:0x0200 A[Catch: all -> 0x0256, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:151:0x020a A[Catch: all -> 0x0256, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:154:0x0217  */
    /* JADX WARN: Code duplicated, block: B:156:0x021a  */
    /* JADX WARN: Code duplicated, block: B:160:0x0222 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:161:0x0224 A[Catch: all -> 0x0256, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:162:0x0232  */
    /* JADX WARN: Code duplicated, block: B:165:0x0240 A[Catch: all -> 0x0256, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:167:0x024b A[Catch: all -> 0x0256, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:185:0x021e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:209:0x01bf A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:210:0x01c8 A[ADDED_TO_REGION, EDGE_INSN: B:210:0x01c8->B:135:0x01c8 BREAK  A[LOOP:3: B:174:0x01ae->B:213:0x01ae], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:212:0x01bc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:48:0x00b0 A[Catch: all -> 0x0256, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x00b3 A[Catch: all -> 0x0256, TryCatch #3 {all -> 0x0256, blocks: (B:3:0x000f, B:7:0x0019, B:9:0x0022, B:11:0x002c, B:13:0x0030, B:15:0x0041, B:16:0x0047, B:19:0x004e, B:23:0x005e, B:28:0x006e, B:29:0x0070, B:152:0x020d, B:145:0x01f3, B:148:0x01fd, B:149:0x0200, B:151:0x020a, B:158:0x021e, B:161:0x0224, B:164:0x0236, B:165:0x0240, B:167:0x024b, B:168:0x0251, B:40:0x008b, B:42:0x0096, B:53:0x00be, B:54:0x00c1, B:61:0x00cf, B:62:0x00d2, B:65:0x00dc, B:68:0x00e9, B:70:0x00ed, B:72:0x00f8, B:73:0x00fe, B:75:0x0105, B:78:0x0110, B:79:0x0119, B:80:0x011b, B:89:0x012d, B:90:0x0130, B:93:0x0139, B:98:0x0149, B:100:0x014d, B:102:0x0158, B:103:0x015e, B:105:0x0165, B:108:0x0170, B:106:0x0168, B:76:0x0108, B:110:0x0179, B:111:0x0180, B:113:0x0186, B:117:0x0199, B:120:0x019f, B:123:0x01a5, B:125:0x01ae, B:129:0x01b7, B:131:0x01bc, B:132:0x01bf, B:137:0x01cc, B:138:0x01d6, B:134:0x01c6, B:124:0x01a8, B:139:0x01df, B:43:0x0099, B:46:0x00a4, B:48:0x00b0, B:49:0x00b3), top: B:180:0x000f, inners: #0 }] */
    protected boolean parseParam() throws InvalidInputException {
        int currentTokenStartPosition;
        int currentTokenEndPosition;
        int currentTokenEndPosition2;
        int token;
        int i;
        int currentTokenStartPosition2 = this.tagSourceStart;
        int currentTokenEndPosition3 = this.tagSourceEnd;
        boolean z = this.scanner.tokenizeWhiteSpace;
        this.scanner.tokenizeWhiteSpace = true;
        try {
            boolean z2 = (this.kind & 8) != 0;
            int i2 = -1;
            if (this.scanner.currentCharacter != ' ' && !ScannerHelper.isWhitespace(this.scanner.currentCharacter)) {
                if (this.reportProblems) {
                    this.sourceParser.problemReporter().javadocInvalidTag(currentTokenStartPosition2, this.scanner.getCurrentTokenEndPosition());
                }
                if (!z2) {
                    this.scanner.currentPosition = currentTokenStartPosition2;
                    this.index = currentTokenStartPosition2;
                }
                this.currentTokenType = -1;
            } else {
                this.identifierPtr = -1;
                this.identifierLengthPtr = -1;
                boolean z3 = this.scanner.currentPosition > this.lineEnd + 1;
                boolean z4 = this.sourceLevel >= ClassFileConstants.JDK1_5;
                boolean z5 = true;
                boolean z6 = true;
                boolean z7 = false;
                int tokenAndConsume = -1;
                while (true) {
                    this.currentTokenType = i2;
                    try {
                        tokenAndConsume = readToken();
                    } catch (InvalidInputException unused) {
                        z5 = false;
                    }
                    if (tokenAndConsume == 11) {
                        if (z5 && z4) {
                            pushIdentifier(true, true);
                            currentTokenStartPosition = this.scanner.getCurrentTokenStartPosition();
                            if (z3) {
                                currentTokenEndPosition = this.lineEnd;
                            } else {
                                currentTokenEndPosition = this.scanner.getCurrentTokenEndPosition();
                            }
                            z7 = true;
                            if (z7 || !z4) {
                                if (z5) {
                                    this.currentTokenType = -1;
                                    i = this.scanner.currentPosition;
                                    try {
                                        tokenAndConsume = readTokenAndConsume();
                                    } catch (InvalidInputException unused2) {
                                    }
                                    if (tokenAndConsume == 1000) {
                                        this.scanner.resetTo(i, this.javadocEnd);
                                        this.index = i;
                                        boolean zPushParamName = pushParamName(z7);
                                        this.scanner.tokenizeWhiteSpace = z;
                                        return zPushParamName;
                                    }
                                }
                                this.currentTokenType = -1;
                                if (!z2) {
                                    if (this.reportProblems) {
                                        if (z3) {
                                            currentTokenEndPosition2 = this.lineEnd;
                                        } else {
                                            currentTokenEndPosition2 = this.scanner.getCurrentTokenEndPosition();
                                        }
                                        while (true) {
                                            try {
                                                token = readToken();
                                                if (token != 1000 || token == 60) {
                                                    break;
                                                    break;
                                                }
                                                this.currentTokenType = -1;
                                                currentTokenEndPosition2 = z3 ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                                            } catch (InvalidInputException unused3) {
                                                currentTokenEndPosition2 = this.lineEnd;
                                            }
                                        }
                                        if (!z4 && z7) {
                                            this.sourceParser.problemReporter().javadocInvalidParamTypeParameter(currentTokenStartPosition, currentTokenEndPosition2);
                                        } else {
                                            this.sourceParser.problemReporter().javadocInvalidParamTagName(currentTokenStartPosition, currentTokenEndPosition2);
                                        }
                                    }
                                    this.scanner.currentPosition = currentTokenStartPosition;
                                    this.index = currentTokenStartPosition;
                                    this.currentTokenType = -1;
                                }
                            } else {
                                while (true) {
                                    this.currentTokenType = -1;
                                    try {
                                        tokenAndConsume = readToken();
                                    } catch (InvalidInputException unused4) {
                                        z5 = false;
                                    }
                                    if (tokenAndConsume != 22) {
                                        if (tokenAndConsume != 60) {
                                            if (tokenAndConsume == 1000) {
                                                if (!z5 || this.scanner.currentPosition > this.lineEnd + 1) {
                                                }
                                            } else {
                                                currentTokenEndPosition = z3 ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                                                z5 = false;
                                            }
                                        }
                                        if (this.reportProblems) {
                                            this.sourceParser.problemReporter().javadocInvalidParamTypeParameter(currentTokenStartPosition, currentTokenEndPosition);
                                        }
                                        if (!z2) {
                                            this.scanner.currentPosition = currentTokenStartPosition;
                                            this.index = currentTokenStartPosition;
                                        }
                                        this.currentTokenType = -1;
                                    } else {
                                        currentTokenEndPosition = z3 ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                                        if (z5) {
                                            pushIdentifier(false, false);
                                            boolean z8 = z5;
                                            int i3 = -1;
                                            int token2 = tokenAndConsume;
                                            boolean z9 = z3;
                                            int currentTokenEndPosition4 = currentTokenEndPosition;
                                            boolean z10 = false;
                                            while (true) {
                                                this.currentTokenType = i3;
                                                try {
                                                    token2 = readToken();
                                                } catch (InvalidInputException unused5) {
                                                    z8 = false;
                                                }
                                                if (token2 != 15) {
                                                    if (token2 != 60) {
                                                        if (token2 != 1000) {
                                                            if (!z10) {
                                                                currentTokenEndPosition4 = z9 ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                                                            }
                                                            z8 = false;
                                                        } else {
                                                            if (this.scanner.currentPosition > this.lineEnd + 1) {
                                                                z8 = false;
                                                                z9 = true;
                                                            }
                                                            if (z8) {
                                                                z10 = true;
                                                            }
                                                        }
                                                    }
                                                    if (this.reportProblems) {
                                                        this.sourceParser.problemReporter().javadocInvalidParamTypeParameter(currentTokenStartPosition, currentTokenEndPosition4);
                                                    }
                                                    if (!z2) {
                                                        this.scanner.currentPosition = currentTokenStartPosition;
                                                        this.index = currentTokenStartPosition;
                                                    }
                                                    this.currentTokenType = -1;
                                                } else {
                                                    currentTokenEndPosition4 = z9 ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                                                    if (z8) {
                                                        pushIdentifier(false, true);
                                                        z3 = z9;
                                                        tokenAndConsume = token2;
                                                        z5 = z8;
                                                        if (z5) {
                                                            this.currentTokenType = -1;
                                                            i = this.scanner.currentPosition;
                                                            tokenAndConsume = readTokenAndConsume();
                                                            if (tokenAndConsume == 1000) {
                                                                this.scanner.resetTo(i, this.javadocEnd);
                                                                this.index = i;
                                                                boolean zPushParamName2 = pushParamName(z7);
                                                                this.scanner.tokenizeWhiteSpace = z;
                                                                return zPushParamName2;
                                                            }
                                                        }
                                                        this.currentTokenType = -1;
                                                        if (!z2) {
                                                            if (this.reportProblems) {
                                                                if (z3) {
                                                                    currentTokenEndPosition2 = this.lineEnd;
                                                                } else {
                                                                    currentTokenEndPosition2 = this.scanner.getCurrentTokenEndPosition();
                                                                }
                                                                while (true) {
                                                                    token = readToken();
                                                                    if (token != 1000) {
                                                                        break;
                                                                    }
                                                                    this.currentTokenType = -1;
                                                                    if (z3) {
                                                                    }
                                                                }
                                                                if (!z4) {
                                                                    this.sourceParser.problemReporter().javadocInvalidParamTagName(currentTokenStartPosition, currentTokenEndPosition2);
                                                                } else {
                                                                    this.sourceParser.problemReporter().javadocInvalidParamTagName(currentTokenStartPosition, currentTokenEndPosition2);
                                                                }
                                                            }
                                                            this.scanner.currentPosition = currentTokenStartPosition;
                                                            this.index = currentTokenStartPosition;
                                                            this.currentTokenType = -1;
                                                        }
                                                    }
                                                }
                                                i3 = -1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (tokenAndConsume == 18) {
                            z7 = true;
                        }
                        if (z5 && !z3) {
                            currentTokenStartPosition2 = this.scanner.getCurrentTokenStartPosition();
                        }
                        if (z3) {
                            currentTokenEndPosition3 = this.lineEnd;
                            z5 = false;
                            if (this.scanner.currentPosition > this.lineEnd + 1) {
                                z3 = true;
                            }
                            if (!z5) {
                                if (this.reportProblems) {
                                    if (z6) {
                                        this.sourceParser.problemReporter().javadocMissingParamName(currentTokenStartPosition2, currentTokenEndPosition3, this.sourceParser.modifiers);
                                    } else if (!z4 && z7) {
                                        this.sourceParser.problemReporter().javadocInvalidParamTypeParameter(currentTokenStartPosition2, currentTokenEndPosition3);
                                    } else {
                                        this.sourceParser.problemReporter().javadocInvalidParamTagName(currentTokenStartPosition2, currentTokenEndPosition3);
                                    }
                                }
                                if (!z2) {
                                    this.scanner.currentPosition = currentTokenStartPosition2;
                                    this.index = currentTokenStartPosition2;
                                }
                                this.currentTokenType = -1;
                            }
                        } else {
                            if (z3) {
                                currentTokenEndPosition3 = this.lineEnd;
                            } else {
                                currentTokenEndPosition3 = this.scanner.getCurrentTokenEndPosition();
                            }
                            z5 = false;
                            z6 = false;
                        }
                        i2 = -1;
                    } else {
                        if (tokenAndConsume == 22) {
                            if (z5) {
                                pushIdentifier(true, false);
                                currentTokenStartPosition = this.scanner.getCurrentTokenStartPosition();
                                currentTokenEndPosition = z3 ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                            } else {
                                if (z5) {
                                    pushIdentifier(true, true);
                                    currentTokenStartPosition = this.scanner.getCurrentTokenStartPosition();
                                    if (z3) {
                                        currentTokenEndPosition = this.lineEnd;
                                    } else {
                                        currentTokenEndPosition = this.scanner.getCurrentTokenEndPosition();
                                    }
                                    z7 = true;
                                }
                                if (tokenAndConsume == 18) {
                                    z7 = true;
                                }
                                if (z5) {
                                    currentTokenStartPosition2 = this.scanner.getCurrentTokenStartPosition();
                                }
                                if (z3) {
                                    if (z3) {
                                        currentTokenEndPosition3 = this.lineEnd;
                                    } else {
                                        currentTokenEndPosition3 = this.scanner.getCurrentTokenEndPosition();
                                    }
                                    z5 = false;
                                    z6 = false;
                                } else {
                                    currentTokenEndPosition3 = this.lineEnd;
                                    z5 = false;
                                    if (this.scanner.currentPosition > this.lineEnd + 1) {
                                        z3 = true;
                                    }
                                    if (!z5) {
                                    }
                                }
                                i2 = -1;
                            }
                            if (z7) {
                                if (z5) {
                                    this.currentTokenType = -1;
                                    i = this.scanner.currentPosition;
                                    tokenAndConsume = readTokenAndConsume();
                                    if (tokenAndConsume == 1000) {
                                        this.scanner.resetTo(i, this.javadocEnd);
                                        this.index = i;
                                        boolean zPushParamName3 = pushParamName(z7);
                                        this.scanner.tokenizeWhiteSpace = z;
                                        return zPushParamName3;
                                    }
                                }
                                this.currentTokenType = -1;
                                if (!z2) {
                                    if (this.reportProblems) {
                                        if (z3) {
                                            currentTokenEndPosition2 = this.lineEnd;
                                        } else {
                                            currentTokenEndPosition2 = this.scanner.getCurrentTokenEndPosition();
                                        }
                                        while (true) {
                                            token = readToken();
                                            if (token != 1000) {
                                                break;
                                                break;
                                            }
                                            this.currentTokenType = -1;
                                            if (z3) {
                                            }
                                        }
                                        if (!z4) {
                                            this.sourceParser.problemReporter().javadocInvalidParamTagName(currentTokenStartPosition, currentTokenEndPosition2);
                                        } else {
                                            this.sourceParser.problemReporter().javadocInvalidParamTagName(currentTokenStartPosition, currentTokenEndPosition2);
                                        }
                                    }
                                    this.scanner.currentPosition = currentTokenStartPosition;
                                    this.index = currentTokenStartPosition;
                                    this.currentTokenType = -1;
                                }
                            } else {
                                if (z5) {
                                    this.currentTokenType = -1;
                                    i = this.scanner.currentPosition;
                                    tokenAndConsume = readTokenAndConsume();
                                    if (tokenAndConsume == 1000) {
                                        this.scanner.resetTo(i, this.javadocEnd);
                                        this.index = i;
                                        boolean zPushParamName4 = pushParamName(z7);
                                        this.scanner.tokenizeWhiteSpace = z;
                                        return zPushParamName4;
                                    }
                                }
                                this.currentTokenType = -1;
                                if (!z2) {
                                    if (this.reportProblems) {
                                        if (z3) {
                                            currentTokenEndPosition2 = this.lineEnd;
                                        } else {
                                            currentTokenEndPosition2 = this.scanner.getCurrentTokenEndPosition();
                                        }
                                        while (true) {
                                            token = readToken();
                                            if (token != 1000) {
                                                break;
                                                break;
                                            }
                                            this.currentTokenType = -1;
                                            if (z3) {
                                            }
                                        }
                                        if (!z4) {
                                            this.sourceParser.problemReporter().javadocInvalidParamTagName(currentTokenStartPosition, currentTokenEndPosition2);
                                        } else {
                                            this.sourceParser.problemReporter().javadocInvalidParamTagName(currentTokenStartPosition, currentTokenEndPosition2);
                                        }
                                    }
                                    this.scanner.currentPosition = currentTokenStartPosition;
                                    this.index = currentTokenStartPosition;
                                    this.currentTokenType = -1;
                                }
                            }
                        } else if (tokenAndConsume != 60) {
                            if (tokenAndConsume != 1000) {
                                if (tokenAndConsume == 18) {
                                    z7 = true;
                                }
                                if (z5) {
                                    currentTokenStartPosition2 = this.scanner.getCurrentTokenStartPosition();
                                }
                                if (z3) {
                                    if (z3) {
                                        currentTokenEndPosition3 = this.lineEnd;
                                    } else {
                                        currentTokenEndPosition3 = this.scanner.getCurrentTokenEndPosition();
                                    }
                                    z5 = false;
                                    z6 = false;
                                } else {
                                    currentTokenEndPosition3 = this.lineEnd;
                                    z5 = false;
                                    if (this.scanner.currentPosition > this.lineEnd + 1) {
                                        z3 = true;
                                    }
                                    if (!z5) {
                                    }
                                }
                            } else {
                                if (this.scanner.currentPosition > this.lineEnd + 1) {
                                    z3 = true;
                                }
                                if (!z5) {
                                }
                            }
                            i2 = -1;
                        }
                        if (this.reportProblems) {
                            if (z6) {
                                this.sourceParser.problemReporter().javadocMissingParamName(currentTokenStartPosition2, currentTokenEndPosition3, this.sourceParser.modifiers);
                            } else if (!z4) {
                                this.sourceParser.problemReporter().javadocInvalidParamTagName(currentTokenStartPosition2, currentTokenEndPosition3);
                            } else {
                                this.sourceParser.problemReporter().javadocInvalidParamTagName(currentTokenStartPosition2, currentTokenEndPosition3);
                            }
                        }
                        if (!z2) {
                            this.scanner.currentPosition = currentTokenStartPosition2;
                            this.index = currentTokenStartPosition2;
                        }
                        this.currentTokenType = -1;
                    }
                }
            }
            this.scanner.tokenizeWhiteSpace = z;
            return false;
        } catch (Throwable th) {
            this.scanner.tokenizeWhiteSpace = z;
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0053 A[DONT_INVERT, FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:35:0x0055  */
    /* JADX WARN: Code duplicated, block: B:36:0x005c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:37:0x005e  */
    /* JADX WARN: Code duplicated, block: B:39:0x0062  */
    /* JADX WARN: Code duplicated, block: B:42:0x006b  */
    /* JADX WARN: Code duplicated, block: B:44:0x006f  */
    /* JADX WARN: Code duplicated, block: B:46:0x0072 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:47:0x0074  */
    /* JADX WARN: Code duplicated, block: B:49:0x0078  */
    /* JADX WARN: Code duplicated, block: B:52:0x0084  */
    /* JADX WARN: Code duplicated, block: B:54:0x0088  */
    /* JADX WARN: Code duplicated, block: B:59:0x009b  */
    /* JADX WARN: Code duplicated, block: B:64:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:79:? A[RETURN, SYNTHETIC] */
    protected Object parseQualifiedName(boolean z) throws InvalidInputException {
        int i;
        int i2;
        int i3;
        if (z) {
            this.identifierPtr = -1;
            this.identifierLengthPtr = -1;
        }
        int i4 = this.kind & 255;
        int i5 = 0;
        while (true) {
            int tokenSafely = readTokenSafely();
            if (tokenSafely != 3) {
                if (tokenSafely != 17) {
                    if (tokenSafely != 22) {
                        if (tokenSafely != 111 && tokenSafely != 114 && tokenSafely != 67 && tokenSafely != 68) {
                            switch (tokenSafely) {
                                default:
                                    switch (tokenSafely) {
                                        default:
                                            switch (tokenSafely) {
                                                default:
                                                    switch (tokenSafely) {
                                                        default:
                                                            switch (tokenSafely) {
                                                                default:
                                                                    switch (tokenSafely) {
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
                                                                            break;
                                                                        default:
                                                                            if (i5 == 0) {
                                                                                i2 = this.identifierPtr;
                                                                                if (i2 < 0) {
                                                                                    return null;
                                                                                }
                                                                                this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i2];
                                                                                return null;
                                                                            }
                                                                            if ((i5 & 1) == 0) {
                                                                                if (i4 != 2) {
                                                                                    if (i4 == 8) {
                                                                                        i = this.identifierPtr;
                                                                                        if (i >= 0) {
                                                                                            this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i];
                                                                                        }
                                                                                        return syntaxRecoverQualifiedName(-1);
                                                                                    }
                                                                                } else if (this.currentTokenType != -1) {
                                                                                    int i6 = this.tokenPreviousPosition;
                                                                                    this.index = i6;
                                                                                    this.scanner.currentPosition = i6;
                                                                                    this.currentTokenType = -1;
                                                                                }
                                                                                throw new InvalidInputException();
                                                                            }
                                                                            break;
                                                                    }
                                                                case 80:
                                                                case 81:
                                                                case 82:
                                                                    if (i5 == 0) {
                                                                        pushIdentifier(true, true);
                                                                        consumeToken();
                                                                    } else {
                                                                        if (i5 == 0) {
                                                                            i2 = this.identifierPtr;
                                                                            if (i2 < 0) {
                                                                                return null;
                                                                            }
                                                                            this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i2];
                                                                            return null;
                                                                        }
                                                                        if ((i5 & 1) == 0) {
                                                                            if (i4 != 2) {
                                                                                if (i4 == 8) {
                                                                                    i = this.identifierPtr;
                                                                                    if (i >= 0) {
                                                                                        this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i];
                                                                                    }
                                                                                    return syntaxRecoverQualifiedName(-1);
                                                                                }
                                                                            } else if (this.currentTokenType != -1) {
                                                                                int i7 = this.tokenPreviousPosition;
                                                                                this.index = i7;
                                                                                this.scanner.currentPosition = i7;
                                                                                this.currentTokenType = -1;
                                                                            }
                                                                            throw new InvalidInputException();
                                                                        }
                                                                    }
                                                                    break;
                                                            }
                                                        case 71:
                                                        case 72:
                                                        case 73:
                                                        case 74:
                                                        case 75:
                                                        case 76:
                                                        case 77:
                                                        case 78:
                                                            if (i5 == 0) {
                                                                pushIdentifier(true, true);
                                                                consumeToken();
                                                            } else {
                                                                if (i5 == 0) {
                                                                    i2 = this.identifierPtr;
                                                                    if (i2 < 0) {
                                                                        return null;
                                                                    }
                                                                    this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i2];
                                                                    return null;
                                                                }
                                                                if ((i5 & 1) == 0) {
                                                                    if (i4 != 2) {
                                                                        if (i4 == 8) {
                                                                            i = this.identifierPtr;
                                                                            if (i >= 0) {
                                                                                this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i];
                                                                            }
                                                                            return syntaxRecoverQualifiedName(-1);
                                                                        }
                                                                    } else if (this.currentTokenType != -1) {
                                                                        int i8 = this.tokenPreviousPosition;
                                                                        this.index = i8;
                                                                        this.scanner.currentPosition = i8;
                                                                        this.currentTokenType = -1;
                                                                    }
                                                                    throw new InvalidInputException();
                                                                }
                                                            }
                                                            break;
                                                    }
                                                case 51:
                                                case 52:
                                                case 53:
                                                case 54:
                                                case 55:
                                                case 56:
                                                case 57:
                                                case 58:
                                                case 59:
                                                    if (i5 == 0) {
                                                        pushIdentifier(true, true);
                                                        consumeToken();
                                                    } else {
                                                        if (i5 == 0) {
                                                            i2 = this.identifierPtr;
                                                            if (i2 < 0) {
                                                                return null;
                                                            }
                                                            this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i2];
                                                            return null;
                                                        }
                                                        if ((i5 & 1) == 0) {
                                                            if (i4 != 2) {
                                                                if (i4 == 8) {
                                                                    i = this.identifierPtr;
                                                                    if (i >= 0) {
                                                                        this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i];
                                                                    }
                                                                    return syntaxRecoverQualifiedName(-1);
                                                                }
                                                            } else if (this.currentTokenType != -1) {
                                                                int i9 = this.tokenPreviousPosition;
                                                                this.index = i9;
                                                                this.scanner.currentPosition = i9;
                                                                this.currentTokenType = -1;
                                                            }
                                                            throw new InvalidInputException();
                                                        }
                                                    }
                                                    break;
                                            }
                                        case 38:
                                        case 39:
                                        case 40:
                                        case 41:
                                        case 42:
                                            if (i5 == 0) {
                                                pushIdentifier(true, true);
                                                consumeToken();
                                            } else {
                                                if (i5 == 0) {
                                                    i2 = this.identifierPtr;
                                                    if (i2 < 0) {
                                                        return null;
                                                    }
                                                    this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i2];
                                                    return null;
                                                }
                                                if ((i5 & 1) == 0) {
                                                    if (i4 != 2) {
                                                        if (i4 == 8) {
                                                            i = this.identifierPtr;
                                                            if (i >= 0) {
                                                                this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i];
                                                            }
                                                            return syntaxRecoverQualifiedName(-1);
                                                        }
                                                    } else if (this.currentTokenType != -1) {
                                                        int i10 = this.tokenPreviousPosition;
                                                        this.index = i10;
                                                        this.scanner.currentPosition = i10;
                                                        this.currentTokenType = -1;
                                                    }
                                                    throw new InvalidInputException();
                                                }
                                            }
                                            break;
                                    }
                                case 34:
                                case 35:
                                case 36:
                                    if (i5 == 0) {
                                        pushIdentifier(true, true);
                                        consumeToken();
                                    } else {
                                        if (i5 == 0) {
                                            i2 = this.identifierPtr;
                                            if (i2 < 0) {
                                                return null;
                                            }
                                            this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i2];
                                            return null;
                                        }
                                        if ((i5 & 1) == 0) {
                                            if (i4 != 2) {
                                                if (i4 == 8) {
                                                    i = this.identifierPtr;
                                                    if (i >= 0) {
                                                        this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i];
                                                    }
                                                    return syntaxRecoverQualifiedName(-1);
                                                }
                                            } else if (this.currentTokenType != -1) {
                                                int i11 = this.tokenPreviousPosition;
                                                this.index = i11;
                                                this.scanner.currentPosition = i11;
                                                this.currentTokenType = -1;
                                            }
                                            throw new InvalidInputException();
                                        }
                                    }
                                    break;
                            }
                        }
                        if (i4 != 8 && this.currentTokenType != -1) {
                            int i12 = this.tokenPreviousPosition;
                            this.index = i12;
                            this.scanner.currentPosition = i12;
                            this.currentTokenType = -1;
                        }
                        i3 = this.identifierPtr;
                        if (i3 >= 0) {
                            this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i3];
                        }
                        return createTypeReference(tokenSafely);
                    }
                    if ((i5 & 1) == 0) {
                        pushIdentifier(i5 == 0, false);
                        consumeToken();
                    }
                    tokenSafely = -1;
                    if (i4 != 8) {
                        int i13 = this.tokenPreviousPosition;
                        this.index = i13;
                        this.scanner.currentPosition = i13;
                        this.currentTokenType = -1;
                    }
                    i3 = this.identifierPtr;
                    if (i3 >= 0) {
                        this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i3];
                    }
                    return createTypeReference(tokenSafely);
                }
                if (i5 == 0) {
                    pushIdentifier(true, true);
                    consumeToken();
                } else {
                    if (i5 == 0) {
                        i2 = this.identifierPtr;
                        if (i2 < 0) {
                            return null;
                        }
                        this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i2];
                        return null;
                    }
                    if ((i5 & 1) == 0) {
                        if (i4 != 2) {
                            if (i4 == 8) {
                                i = this.identifierPtr;
                                if (i >= 0) {
                                    this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i];
                                }
                                return syntaxRecoverQualifiedName(-1);
                            }
                        } else if (this.currentTokenType != -1) {
                            int i14 = this.tokenPreviousPosition;
                            this.index = i14;
                            this.scanner.currentPosition = i14;
                            this.currentTokenType = -1;
                        }
                        throw new InvalidInputException();
                    }
                    tokenSafely = -1;
                }
                if (i4 != 8) {
                    int i15 = this.tokenPreviousPosition;
                    this.index = i15;
                    this.scanner.currentPosition = i15;
                    this.currentTokenType = -1;
                }
                i3 = this.identifierPtr;
                if (i3 >= 0) {
                    this.lastIdentifierEndPosition = (int) this.identifierPositionStack[i3];
                }
                return createTypeReference(tokenSafely);
            }
            if ((i5 & 1) == 0) {
                throw new InvalidInputException();
            }
            consumeToken();
            i5++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:46:0x0097 A[Catch: InvalidInputException -> 0x0230, TryCatch #0 {InvalidInputException -> 0x0230, blocks: (B:4:0x0008, B:70:0x0105, B:72:0x0113, B:74:0x0119, B:76:0x011d, B:78:0x0121, B:80:0x0133, B:82:0x0139, B:83:0x0140, B:85:0x0146, B:87:0x014a, B:89:0x0156, B:101:0x017f, B:103:0x0187, B:105:0x0195, B:106:0x0197, B:108:0x019d, B:109:0x019f, B:111:0x01a3, B:113:0x01ad, B:93:0x0163, B:95:0x0169, B:97:0x016f, B:99:0x0173, B:115:0x01b2, B:117:0x01b6, B:7:0x0019, B:16:0x0031, B:18:0x003c, B:20:0x0042, B:23:0x0048, B:25:0x0051, B:27:0x0057, B:29:0x005b, B:31:0x0060, B:33:0x0063, B:36:0x006c, B:37:0x006f, B:39:0x0073, B:41:0x0077, B:43:0x007d, B:45:0x0083, B:46:0x0097, B:50:0x00ae, B:52:0x00bb, B:54:0x00bf, B:56:0x00d1, B:58:0x00d7, B:60:0x00dc, B:62:0x00e0, B:65:0x00f2, B:119:0x01cb, B:121:0x01da, B:123:0x01e1, B:125:0x01e5, B:127:0x01f7, B:129:0x01fd, B:131:0x0202, B:133:0x0206, B:134:0x0216, B:136:0x021a, B:138:0x021e), top: B:145:0x0008 }] */
    protected boolean parseReference() throws InvalidInputException {
        char c;
        int i = this.scanner.currentPosition;
        Object qualifiedName = null;
        int currentTokenStartPosition = -1;
        do {
            try {
                int i2 = 1;
                if (this.index < this.scanner.eofPosition) {
                    int i3 = this.index;
                    int tokenSafely = readTokenSafely();
                    if (tokenSafely != 11) {
                        if (tokenSafely != 22) {
                            if (tokenSafely != 48) {
                                if (tokenSafely == 118) {
                                    consumeToken();
                                    if (this.scanner.currentCharacter == '#') {
                                        Object member = parseMember(qualifiedName);
                                        if (member != null) {
                                            return pushSeeRef(member);
                                        }
                                        return false;
                                    }
                                    char[] currentIdentifierSource = this.scanner.getCurrentIdentifierSource();
                                    if (currentIdentifierSource.length > 0 && currentIdentifierSource[0] == '\"') {
                                        if (this.reportProblems) {
                                            if (this.tagValue == 6) {
                                                int length = currentIdentifierSource.length;
                                                while (i2 < length && ScannerHelper.isLetter(currentIdentifierSource[i2])) {
                                                    i2++;
                                                }
                                                if (i2 < length - 2 && currentIdentifierSource[i2] == ':' && currentIdentifierSource[i2 + 1] == '/' && currentIdentifierSource[i2 + 2] == '/') {
                                                    this.sourceParser.problemReporter().javadocInvalidSeeUrlReference(this.scanner.getCurrentTokenStartPosition(), getTokenEndPosition());
                                                } else {
                                                    this.sourceParser.problemReporter().javadocInvalidReference(this.scanner.getCurrentTokenStartPosition(), getTokenEndPosition());
                                                }
                                            } else {
                                                this.sourceParser.problemReporter().javadocInvalidReference(this.scanner.getCurrentTokenStartPosition(), getTokenEndPosition());
                                            }
                                        }
                                        return false;
                                    }
                                }
                            } else if (qualifiedName == null) {
                                consumeToken();
                                int currentTokenStartPosition2 = this.scanner.getCurrentTokenStartPosition();
                                if (this.tagValue == 10) {
                                    if (this.reportProblems) {
                                        this.sourceParser.problemReporter().javadocInvalidValueReference(currentTokenStartPosition2, getTokenEndPosition(), this.sourceParser.modifiers);
                                    }
                                    return false;
                                }
                                if (verifyEndLine(i3)) {
                                    return createFakeReference(currentTokenStartPosition2);
                                }
                                if (this.reportProblems) {
                                    this.sourceParser.problemReporter().javadocUnexpectedText(this.scanner.currentPosition, this.lineEnd);
                                }
                                return false;
                            }
                        } else if (qualifiedName == null) {
                            currentTokenStartPosition = this.scanner.getCurrentTokenStartPosition();
                            qualifiedName = parseQualifiedName(true);
                        }
                    } else if (qualifiedName == null) {
                        consumeToken();
                        int currentTokenStartPosition3 = this.scanner.getCurrentTokenStartPosition();
                        if (parseHref()) {
                            consumeToken();
                            if (this.tagValue == 10) {
                                if (this.reportProblems) {
                                    this.sourceParser.problemReporter().javadocInvalidValueReference(currentTokenStartPosition3, getIndexPosition(), this.sourceParser.modifiers);
                                }
                                return false;
                            }
                            if (verifyEndLine(i3)) {
                                return createFakeReference(currentTokenStartPosition3);
                            }
                            if (this.reportProblems) {
                                this.sourceParser.problemReporter().javadocUnexpectedText(this.scanner.currentPosition, this.lineEnd);
                            }
                        } else if (this.tagValue == 10 && this.reportProblems) {
                            this.sourceParser.problemReporter().javadocInvalidValueReference(currentTokenStartPosition3, getIndexPosition(), this.sourceParser.modifiers);
                        }
                        return false;
                    }
                }
                if (qualifiedName == null) {
                    int i4 = this.tokenPreviousPosition;
                    this.index = i4;
                    this.scanner.currentPosition = i4;
                    this.currentTokenType = -1;
                    if (this.tagValue == 10) {
                        if ((this.kind & 2) != 0) {
                            createTag();
                        }
                        return true;
                    }
                    if (this.reportProblems) {
                        this.sourceParser.problemReporter().javadocMissingReference(this.tagSourceStart, this.tagSourceEnd, this.sourceParser.modifiers);
                    }
                    return false;
                }
                int i5 = this.lastIdentifierEndPosition;
                if (i5 > this.javadocStart) {
                    int i6 = i5 + 1;
                    this.index = i6;
                    this.scanner.currentPosition = i6;
                }
                this.currentTokenType = -1;
                if (this.tagValue == 10) {
                    if (this.reportProblems) {
                        this.sourceParser.problemReporter().javadocInvalidReference(currentTokenStartPosition, this.lineEnd);
                    }
                    return false;
                }
                int i7 = this.index;
                char c2 = readChar();
                if (c2 == '(') {
                    if (this.reportProblems) {
                        ProblemReporter problemReporter = this.sourceParser.problemReporter();
                        int i8 = this.lineEnd;
                        problemReporter.javadocMissingHashCharacter(currentTokenStartPosition, i8, String.valueOf(this.source, currentTokenStartPosition, (i8 - currentTokenStartPosition) + 1));
                    }
                    return false;
                }
                if (c2 == ':' && (c = readChar()) == '/' && c == readChar() && this.reportProblems) {
                    this.sourceParser.problemReporter().javadocInvalidSeeUrlReference(currentTokenStartPosition, this.lineEnd);
                    return false;
                }
                this.index = i7;
                if (!verifySpaceOrEndComment()) {
                    int i9 = this.tokenPreviousPosition;
                    this.index = i9;
                    this.scanner.currentPosition = i9;
                    this.currentTokenType = -1;
                    int i10 = this.starPosition;
                    if (i10 == -1) {
                        i10 = this.lineEnd;
                    }
                    if (this.source[i10] == '\n') {
                        i10--;
                    }
                    if (this.reportProblems) {
                        this.sourceParser.problemReporter().javadocMalformedSeeReference(currentTokenStartPosition, i10);
                    }
                    return false;
                }
                return pushSeeRef(qualifiedName);
            } catch (InvalidInputException unused) {
                if (this.reportProblems) {
                    this.sourceParser.problemReporter().javadocInvalidReference(i, getTokenEndPosition());
                }
                int i11 = this.tokenPreviousPosition;
                this.index = i11;
                this.scanner.currentPosition = i11;
                this.currentTokenType = -1;
                return false;
            }
        } while (!this.abort);
        return false;
    }

    protected boolean parseThrows() {
        int i = this.scanner.currentPosition;
        try {
            Object qualifiedName = parseQualifiedName(true);
            if (this.abort) {
                return false;
            }
            if (qualifiedName == null) {
                if (this.reportProblems) {
                    this.sourceParser.problemReporter().javadocMissingThrowsClassName(this.tagSourceStart, this.tagSourceEnd, this.sourceParser.modifiers);
                }
                return false;
            }
            return pushThrowName(qualifiedName);
        } catch (InvalidInputException unused) {
            if (this.reportProblems) {
                this.sourceParser.problemReporter().javadocInvalidThrowsClass(i, getTokenEndPosition());
            }
        }
    }

    protected char peekChar() {
        char c;
        int hexadecimalValue;
        int i = this.index;
        char[] cArr = this.source;
        int i2 = i + 1;
        char c2 = cArr[i];
        if (c2 != '\\' || cArr[i2] != 'u') {
            return c2;
        }
        int i3 = i + 2;
        while (true) {
            c = this.source[i3];
            if (c != 'u') {
                break;
            }
            i3++;
        }
        int i4 = i3 + 1;
        int hexadecimalValue2 = ScannerHelper.getHexadecimalValue(c);
        if (hexadecimalValue2 > 15 || hexadecimalValue2 < 0) {
            return c2;
        }
        int i5 = i3 + 2;
        int hexadecimalValue3 = ScannerHelper.getHexadecimalValue(this.source[i4]);
        if (hexadecimalValue3 > 15 || hexadecimalValue3 < 0) {
            return c2;
        }
        int i6 = i3 + 3;
        int hexadecimalValue4 = ScannerHelper.getHexadecimalValue(this.source[i5]);
        return (hexadecimalValue4 > 15 || hexadecimalValue4 < 0 || (hexadecimalValue = ScannerHelper.getHexadecimalValue(this.source[i6])) > 15 || hexadecimalValue < 0) ? c2 : (char) ((((((hexadecimalValue2 * 16) + hexadecimalValue3) * 16) + hexadecimalValue4) * 16) + hexadecimalValue);
    }

    protected void pushIdentifier(boolean z, boolean z2) {
        char[][] cArr = this.identifierStack;
        int length = cArr.length;
        int i = this.identifierPtr + 1;
        this.identifierPtr = i;
        if (i >= length) {
            int i2 = length + 10;
            char[][] cArr2 = new char[i2][];
            this.identifierStack = cArr2;
            System.arraycopy(cArr, 0, cArr2, 0, length);
            long[] jArr = this.identifierPositionStack;
            long[] jArr2 = new long[i2];
            this.identifierPositionStack = jArr2;
            System.arraycopy(jArr, 0, jArr2, 0, length);
        }
        this.identifierStack[this.identifierPtr] = z2 ? this.scanner.getCurrentTokenSource() : this.scanner.getCurrentIdentifierSource();
        this.identifierPositionStack[this.identifierPtr] = (((long) this.scanner.startPosition) << 32) + ((long) (this.scanner.currentPosition - 1));
        if (z) {
            int[] iArr = this.identifierLengthStack;
            int length2 = iArr.length;
            int i3 = this.identifierLengthPtr + 1;
            this.identifierLengthPtr = i3;
            if (i3 >= length2) {
                int[] iArr2 = new int[length2 + 10];
                this.identifierLengthStack = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length2);
            }
            this.identifierLengthStack[this.identifierLengthPtr] = 1;
            return;
        }
        int[] iArr3 = this.identifierLengthStack;
        int i4 = this.identifierLengthPtr;
        iArr3[i4] = iArr3[i4] + 1;
    }

    protected void pushOnAstStack(Object obj, boolean z) {
        if (obj == null) {
            int[] iArr = this.astLengthStack;
            int length = iArr.length;
            int i = this.astLengthPtr + 1;
            this.astLengthPtr = i;
            if (i >= length) {
                int[] iArr2 = new int[length + 10];
                this.astLengthStack = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, length);
            }
            this.astLengthStack[this.astLengthPtr] = 0;
            return;
        }
        Object[] objArr = this.astStack;
        int length2 = objArr.length;
        int i2 = this.astPtr + 1;
        this.astPtr = i2;
        if (i2 >= length2) {
            Object[] objArr2 = new Object[length2 + 10];
            this.astStack = objArr2;
            System.arraycopy(objArr, 0, objArr2, 0, length2);
            this.astPtr = length2;
        }
        this.astStack[this.astPtr] = obj;
        if (z) {
            int[] iArr3 = this.astLengthStack;
            int length3 = iArr3.length;
            int i3 = this.astLengthPtr + 1;
            this.astLengthPtr = i3;
            if (i3 >= length3) {
                int[] iArr4 = new int[length3 + 10];
                this.astLengthStack = iArr4;
                System.arraycopy(iArr3, 0, iArr4, 0, length3);
            }
            this.astLengthStack[this.astLengthPtr] = 1;
            return;
        }
        int[] iArr5 = this.astLengthStack;
        int i4 = this.astLengthPtr;
        iArr5[i4] = iArr5[i4] + 1;
    }

    protected char readChar() {
        int i;
        char c;
        char[] cArr = this.source;
        int i2 = this.index;
        int i3 = i2 + 1;
        this.index = i3;
        char c2 = cArr[i2];
        if (c2 != '\\' || cArr[i3] != 'u') {
            return c2;
        }
        this.index = i2 + 2;
        while (true) {
            char[] cArr2 = this.source;
            i = this.index;
            c = cArr2[i];
            if (c != 'u') {
                break;
            }
            this.index = i + 1;
        }
        this.index = i + 1;
        int hexadecimalValue = ScannerHelper.getHexadecimalValue(c);
        if (hexadecimalValue <= 15 && hexadecimalValue >= 0) {
            char[] cArr3 = this.source;
            int i4 = this.index;
            this.index = i4 + 1;
            int hexadecimalValue2 = ScannerHelper.getHexadecimalValue(cArr3[i4]);
            if (hexadecimalValue2 <= 15 && hexadecimalValue2 >= 0) {
                char[] cArr4 = this.source;
                int i5 = this.index;
                this.index = i5 + 1;
                int hexadecimalValue3 = ScannerHelper.getHexadecimalValue(cArr4[i5]);
                if (hexadecimalValue3 <= 15 && hexadecimalValue3 >= 0) {
                    char[] cArr5 = this.source;
                    int i6 = this.index;
                    this.index = i6 + 1;
                    int hexadecimalValue4 = ScannerHelper.getHexadecimalValue(cArr5[i6]);
                    if (hexadecimalValue4 <= 15 && hexadecimalValue4 >= 0) {
                        return (char) ((((((hexadecimalValue * 16) + hexadecimalValue2) * 16) + hexadecimalValue3) * 16) + hexadecimalValue4);
                    }
                }
            }
        }
        this.index = i3;
        return c2;
    }

    protected int readToken() throws InvalidInputException {
        if (this.currentTokenType < 0) {
            this.tokenPreviousPosition = this.scanner.currentPosition;
            this.currentTokenType = this.scanner.getNextToken();
            if (this.scanner.currentPosition > this.lineEnd + 1) {
                this.lineStarted = false;
                while (this.currentTokenType == 6) {
                    this.currentTokenType = this.scanner.getNextToken();
                }
            }
            this.index = this.scanner.currentPosition;
            this.lineStarted = true;
        }
        return this.currentTokenType;
    }

    protected int readTokenAndConsume() throws InvalidInputException {
        int token = readToken();
        consumeToken();
        return token;
    }

    protected int readTokenSafely() {
        try {
            return readToken();
        } catch (InvalidInputException unused) {
            return 118;
        }
    }

    protected void recordInheritedPosition(long j) {
        long[] jArr = this.inheritedPositions;
        if (jArr == null) {
            this.inheritedPositions = new long[4];
            this.inheritedPositionsPtr = 0;
        } else {
            int i = this.inheritedPositionsPtr;
            if (i == jArr.length) {
                long[] jArr2 = new long[i + 4];
                this.inheritedPositions = jArr2;
                System.arraycopy(jArr, 0, jArr2, 0, i);
            }
        }
        long[] jArr3 = this.inheritedPositions;
        int i2 = this.inheritedPositionsPtr;
        this.inheritedPositionsPtr = i2 + 1;
        jArr3[i2] = j;
    }

    protected void setInlineTagStarted(boolean z) {
        this.inlineTagStarted = z;
    }

    public String toString() {
        char[] cArr;
        StringBuffer stringBuffer = new StringBuffer();
        int i = this.scanner.currentPosition;
        int i2 = this.index;
        if (i < i2) {
            i2 = this.scanner.currentPosition;
        }
        int i3 = this.scanner.currentPosition;
        int i4 = this.index;
        if (i3 >= i4) {
            i4 = this.scanner.currentPosition;
        }
        char[] cArr2 = this.source;
        if (i2 == cArr2.length) {
            return "EOF\n\n".concat(new String(this.source));
        }
        if (i4 > cArr2.length) {
            return "behind the EOF\n\n".concat(new String(this.source));
        }
        char[] cArr3 = new char[i2];
        System.arraycopy(cArr2, 0, cArr3, 0, i2);
        int i5 = i4 - 1;
        int i6 = (i5 - i2) + 1;
        if (i6 > -1) {
            cArr = new char[i6];
            System.arraycopy(this.source, i2, cArr, 0, i6);
        } else {
            cArr = CharOperation.NO_CHAR;
        }
        char[] cArr4 = this.source;
        char[] cArr5 = new char[cArr4.length - i5];
        System.arraycopy(cArr4, i4, cArr5, 0, (cArr4.length - i5) - 1);
        stringBuffer.append(cArr3);
        if (this.scanner.currentPosition < this.index) {
            stringBuffer.append("\n===============================\nScanner current position here -->");
        } else {
            stringBuffer.append("\n===============================\nParser index here -->");
        }
        stringBuffer.append(cArr);
        if (this.scanner.currentPosition < this.index) {
            stringBuffer.append("<-- Parser index here\n===============================\n");
        } else {
            stringBuffer.append("<-- Scanner current position here\n===============================\n");
        }
        stringBuffer.append(cArr5);
        return stringBuffer.toString();
    }

    protected void updateLineEnd() {
        while (this.index > this.lineEnd + 1) {
            int i = this.linePtr;
            if (i < this.lastLinePtr) {
                Scanner scanner = this.scanner;
                int i2 = i + 1;
                this.linePtr = i2;
                this.lineEnd = scanner.getLineEnd(i2) - 1;
            } else {
                this.lineEnd = this.javadocEnd;
                return;
            }
        }
    }

    protected boolean verifyEndLine(int i) {
        boolean z = (this.kind & 2) != 0;
        if (this.inlineTagStarted) {
            if (peekChar() != '}') {
                return false;
            }
            if (z) {
                createTag();
                pushText(i, this.index);
            }
            return true;
        }
        int i2 = this.index;
        this.starPosition = -1;
        char c = readChar();
        int i3 = i2;
        while (true) {
            if (c != '\t') {
                if (c != '\n') {
                    if (c != '\f') {
                        if (c == '\r') {
                            break;
                        }
                        if (c != ' ') {
                            if (c == '*') {
                                this.starPosition = i3;
                                i3 = this.index;
                                c = readChar();
                            } else {
                                if (c == '/' && this.starPosition >= i) {
                                    if (z) {
                                        createTag();
                                        pushText(i, this.starPosition);
                                    }
                                    return true;
                                }
                                this.index = i2;
                                return false;
                            }
                        }
                    }
                } else {
                    break;
                }
            }
            if (this.starPosition < 0) {
                i3 = this.index;
                c = readChar();
            } else {
                this.index = i2;
                return false;
            }
        }
        if (z) {
            createTag();
            pushText(i, i3);
        }
        this.index = i3;
        return true;
    }

    protected boolean verifySpaceOrEndComment() {
        this.starPosition = -1;
        int i = this.index;
        char cPeekChar = peekChar();
        if (cPeekChar == '}') {
            return this.inlineTagStarted;
        }
        if (ScannerHelper.isWhitespace(cPeekChar)) {
            return true;
        }
        int i2 = this.index;
        char c = readChar();
        while (true) {
            int i3 = this.index;
            if (i3 >= this.source.length) {
                this.index = i;
                return false;
            }
            if (c == '*') {
                this.starPosition = i2;
                c = readChar();
                i2 = i3;
            } else {
                if (c == '/' && this.starPosition >= i) {
                    return true;
                }
                this.index = i;
                return false;
            }
        }
    }
}
