package org.luaj.vm2.parser;

import com.beust.jcommander.Parameters;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.PrintStream;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes6.dex */
public class LuaParserTokenManager implements LuaParserConstants {
    protected char curChar;
    int curLexState;
    public PrintStream debugStream;
    int defaultLexState;
    private StringBuffer image;
    protected SimpleCharStream input_stream;
    private final StringBuffer jjimage;
    private int jjimageLen;
    int jjmatchedKind;
    int jjmatchedPos;
    int jjnewStateCnt;
    int jjround;
    private final int[] jjrounds;
    private final int[] jjstateSet;
    private int lengthOfMatch;
    static final long[] jjbitVec0 = {-2, -1, -1, -1};
    static final long[] jjbitVec2 = {0, 0, -1, -1};
    static final int[] jjnextStates = {62, 63, 65, 32, 49, 50, 51, 36, 37, 38, 26, 27, 29, 22, 36, 37, 38, 46, 36, 47, 37, 38, 49, 50, 51, 59, 49, 60, 50, 51, 20, 25, 23, 24, 33, 34, 39, 40, 45, 52, 53, 58, 0, 1, 3};
    public static final String[] jjstrLiteralImages = {"", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "and", PsiKeyword.BREAK, PsiKeyword.DO, PsiKeyword.ELSE, "elseif", "end", "false", PsiKeyword.FOR, "function", PsiKeyword.GOTO, PsiKeyword.IF, "in", "local", "nil", "not", "or", "return", "repeat", "then", "true", "until", PsiKeyword.WHILE, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "::", null, null, null, "#", ";", ContainerUtils.KEY_VALUE_DELIMITER, ",", ".", ":", "(", Constant.AFTER_QUTO, "[", "]", "...", "{", "}", Marker.ANY_NON_NULL_MARKER, Parameters.DEFAULT_OPTION_PREFIXES, "*", "/", "^", "%", "..", "<", "<=", ">", ">=", "==", "~="};
    public static final String[] lexStateNames = {"DEFAULT", "IN_COMMENT", "IN_LC0", "IN_LC1", "IN_LC2", "IN_LC3", "IN_LCN", "IN_LS0", "IN_LS1", "IN_LS2", "IN_LS3", "IN_LSN"};
    public static final int[] jjnewLexState = {-1, -1, -1, -1, -1, -1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    static final long[] jjtoToken = {6926536226618998785L, 2147483618};
    static final long[] jjtoSkip = {8257598, 0};
    static final long[] jjtoSpecial = {8257536, 0};
    static final long[] jjtoMore = {268566464, 0};

    public LuaParserTokenManager(SimpleCharStream simpleCharStream) {
        this.debugStream = System.out;
        this.jjrounds = new int[66];
        this.jjstateSet = new int[132];
        StringBuffer stringBuffer = new StringBuffer();
        this.jjimage = stringBuffer;
        this.image = stringBuffer;
        this.curLexState = 0;
        this.defaultLexState = 0;
        this.input_stream = simpleCharStream;
    }

    public LuaParserTokenManager(SimpleCharStream simpleCharStream, int i) {
        this(simpleCharStream);
        SwitchTo(i);
    }

    private void ReInitRounds() {
        this.jjround = -2147483647;
        int i = 66;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            this.jjrounds[i2] = Integer.MIN_VALUE;
            i = i2;
        }
    }

    private void jjAddStates(int i, int i2) {
        while (true) {
            int[] iArr = this.jjstateSet;
            int i3 = this.jjnewStateCnt;
            this.jjnewStateCnt = i3 + 1;
            iArr[i3] = jjnextStates[i];
            int i4 = i + 1;
            if (i == i2) {
                return;
            } else {
                i = i4;
            }
        }
    }

    private static final boolean jjCanMove_0(int i, int i2, int i3, long j, long j2) {
        if (i != 0) {
            return (jjbitVec0[i2] & j) != 0;
        }
        return (jjbitVec2[i3] & j2) != 0;
    }

    private void jjCheckNAdd(int i) {
        int[] iArr = this.jjrounds;
        int i2 = iArr[i];
        int i3 = this.jjround;
        if (i2 != i3) {
            int[] iArr2 = this.jjstateSet;
            int i4 = this.jjnewStateCnt;
            this.jjnewStateCnt = i4 + 1;
            iArr2[i4] = i;
            iArr[i] = i3;
        }
    }

    private void jjCheckNAddStates(int i, int i2) {
        while (true) {
            jjCheckNAdd(jjnextStates[i]);
            int i3 = i + 1;
            if (i == i2) {
                return;
            } else {
                i = i3;
            }
        }
    }

    private void jjCheckNAddTwoStates(int i, int i2) {
        jjCheckNAdd(i);
        jjCheckNAdd(i2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:132:0x01da  */
    /* JADX WARN: Code duplicated, block: B:175:0x025b A[PHI: r6
      0x025b: PHI (r6v27 int) = 
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v29 int)
      (r6v29 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v32 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v42 int)
      (r6v26 int)
      (r6v45 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v48 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v50 int)
      (r6v51 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v52 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v53 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v26 int)
      (r6v57 int)
      (r6v26 int)
     binds: [B:10:0x0040, B:220:0x031e, B:217:0x0310, B:218:0x0312, B:214:0x0301, B:215:0x0303, B:211:0x02f3, B:212:0x02f5, B:208:0x02e5, B:209:0x02e7, B:205:0x02d1, B:206:0x02d3, B:186:0x028d, B:187:0x028f, B:183:0x027e, B:184:0x0280, B:180:0x026f, B:181:0x0271, B:177:0x0260, B:178:0x0262, B:169:0x0248, B:174:0x0251, B:166:0x0237, B:167:0x0239, B:163:0x0230, B:156:0x0220, B:153:0x0219, B:146:0x0204, B:139:0x01f1, B:135:0x01e2, B:129:0x01d4, B:124:0x01cb, B:161:0x0228, B:118:0x01bc, B:151:0x020c, B:115:0x01b3, B:108:0x01a3, B:105:0x019c, B:103:0x018c, B:99:0x0185, B:96:0x017b, B:93:0x0171, B:89:0x0161, B:90:0x0163, B:91:0x0165, B:144:0x01fc, B:85:0x014a, B:86:0x014c, B:82:0x0138, B:83:0x013a, B:79:0x0126, B:80:0x0128, B:76:0x011f, B:72:0x0110, B:69:0x0105, B:66:0x00fe, B:63:0x00f9, B:58:0x00e6, B:60:0x00ea, B:61:0x00ec, B:56:0x00df, B:54:0x00d1, B:55:0x00d3, B:51:0x00bf, B:52:0x00c1, B:48:0x00ad, B:49:0x00af, B:45:0x00a6, B:42:0x0099, B:43:0x009b, B:74:0x0116, B:39:0x008d, B:33:0x007c, B:37:0x0083, B:137:0x01e8, B:30:0x0070, B:24:0x0061, B:19:0x0058, B:113:0x01ac, B:13:0x0049] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:272:0x03ce  */
    /* JADX WARN: Code duplicated, block: B:27:0x0067  */
    /* JADX WARN: Code duplicated, block: B:291:0x042b  */
    /* JADX WARN: Code duplicated, block: B:408:0x0612 A[LOOP:3: B:383:0x05a7->B:408:0x0612, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:414:0x05ef A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:56:0x00df  */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0219, code lost:
    
        if ((r21 & 43980465111040L) != 0) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x011f, code lost:
    
        if ((r21 & 287948901175001088L) != 0) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0171, code lost:
    
        if ((r21 & (-17179869185L)) != 0) goto L87;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int jjMoveNfa_0(int r32, int r33) {
        /*
            Method dump skipped, instruction units count: 1740
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.luaj.vm2.parser.LuaParserTokenManager.jjMoveNfa_0(int, int):int");
    }

    /* JADX WARN: Code duplicated, block: B:46:0x0098  */
    private int jjMoveNfa_1(int i, int i2) {
        this.jjnewStateCnt = 4;
        this.jjstateSet[0] = i;
        int i3 = 1;
        int i4 = Integer.MAX_VALUE;
        int i5 = 1;
        int i6 = 0;
        int i7 = Integer.MAX_VALUE;
        int i8 = i2;
        while (true) {
            int i9 = this.jjround + i3;
            this.jjround = i9;
            if (i9 == i4) {
                ReInitRounds();
            }
            char c = this.curChar;
            if (c < '@') {
                long j = 1 << c;
                while (true) {
                    int[] iArr = this.jjstateSet;
                    i5--;
                    int i10 = iArr[i5];
                    if (i10 != 0) {
                        if (i10 != i3) {
                            if (i10 != 2) {
                                if (i10 != 3) {
                                    if (i10 == 4) {
                                        if ((j & (-9217)) != 0) {
                                            if (i7 > 17) {
                                                i7 = 17;
                                            }
                                            jjCheckNAddStates(42, 44);
                                        } else if ((j & 9216) != 0 && i7 > 17) {
                                            i7 = 17;
                                        }
                                        if (this.curChar == '\r') {
                                            int[] iArr2 = this.jjstateSet;
                                            int i11 = this.jjnewStateCnt;
                                            this.jjnewStateCnt = i11 + 1;
                                            iArr2[i11] = 2;
                                        }
                                    }
                                } else if (this.curChar == '\r') {
                                    int i12 = this.jjnewStateCnt;
                                    this.jjnewStateCnt = i12 + 1;
                                    iArr[i12] = 2;
                                }
                            } else if (this.curChar == '\n' && i7 > 17) {
                                i7 = 17;
                            }
                        } else if ((j & 9216) != 0 && i7 > 17) {
                            i7 = 17;
                        }
                    } else if ((j & (-9217)) != 0) {
                        jjCheckNAddStates(42, 44);
                        i7 = 17;
                    }
                    if (i5 == i6) {
                        break;
                    }
                    i3 = 1;
                }
            } else if (c < 128) {
                do {
                    i5--;
                    int i13 = this.jjstateSet[i5];
                    if (i13 == 0 || i13 == 4) {
                        jjCheckNAddStates(42, 44);
                        i7 = 17;
                    }
                } while (i5 != i6);
            } else {
                int i14 = c >> '\b';
                int i15 = c >> 14;
                long j2 = 1 << (i14 & 63);
                int i16 = (c & 255) >> 6;
                long j3 = 1 << (c & MangleConstant.Q_MARK);
                do {
                    i5--;
                    int i17 = this.jjstateSet[i5];
                    if ((i17 == 0 || i17 == 4) && jjCanMove_0(i14, i15, i16, j2, j3)) {
                        if (i7 > 17) {
                            i7 = 17;
                        }
                        jjCheckNAddStates(42, 44);
                    }
                } while (i5 != i6);
            }
            if (i7 != Integer.MAX_VALUE) {
                this.jjmatchedKind = i7;
                this.jjmatchedPos = i8;
                i7 = Integer.MAX_VALUE;
            }
            i8++;
            i5 = this.jjnewStateCnt;
            this.jjnewStateCnt = i6;
            i6 = 4 - i6;
            if (i5 == i6) {
                return i8;
            }
            try {
                this.curChar = this.input_stream.readChar();
                i4 = Integer.MAX_VALUE;
                i3 = 1;
            } catch (IOException unused) {
                return i8;
            }
        }
    }

    private int jjMoveNfa_11(int i, int i2) {
        this.jjnewStateCnt = 7;
        this.jjstateSet[0] = i;
        int i3 = 1;
        int i4 = Integer.MAX_VALUE;
        int i5 = 0;
        while (true) {
            int i6 = this.jjround + 1;
            this.jjround = i6;
            if (i6 == Integer.MAX_VALUE) {
                ReInitRounds();
            }
            char c = this.curChar;
            if (c < '@') {
                do {
                    int[] iArr = this.jjstateSet;
                    i3--;
                    int i7 = iArr[i3];
                    if (i7 == 0 || i7 == 1) {
                        if (this.curChar == '=') {
                            jjCheckNAddTwoStates(1, 2);
                        }
                    } else if (i7 != 3) {
                        if (i7 != 4) {
                            if (i7 == 5 && this.curChar == '=') {
                                int i8 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i8 + 1;
                                iArr[i8] = 4;
                            }
                        } else if (this.curChar == '=') {
                            int i9 = this.jjnewStateCnt;
                            this.jjnewStateCnt = i9 + 1;
                            iArr[i9] = 3;
                        }
                    } else if (this.curChar == '=') {
                        int i10 = this.jjnewStateCnt;
                        this.jjnewStateCnt = i10 + 1;
                        iArr[i10] = 0;
                    }
                } while (i3 != i5);
            } else if (c < 128) {
                do {
                    int[] iArr2 = this.jjstateSet;
                    i3--;
                    int i11 = iArr2[i3];
                    if (i11 != 2) {
                        if (i11 == 6 && this.curChar == ']') {
                            int i12 = this.jjnewStateCnt;
                            this.jjnewStateCnt = i12 + 1;
                            iArr2[i12] = 5;
                        }
                    } else if (this.curChar == ']' && i4 > 27) {
                        i4 = 27;
                    }
                } while (i3 != i5);
            } else {
                do {
                    i3--;
                    int i13 = this.jjstateSet[i3];
                } while (i3 != i5);
            }
            if (i4 != Integer.MAX_VALUE) {
                this.jjmatchedKind = i4;
                this.jjmatchedPos = i2;
                i4 = Integer.MAX_VALUE;
            }
            i2++;
            i3 = this.jjnewStateCnt;
            this.jjnewStateCnt = i5;
            i5 = 7 - i5;
            if (i3 == i5) {
                return i2;
            }
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException unused) {
                return i2;
            }
        }
    }

    private int jjMoveNfa_6(int i, int i2) {
        this.jjnewStateCnt = 7;
        this.jjstateSet[0] = i;
        int i3 = 1;
        int i4 = Integer.MAX_VALUE;
        int i5 = 0;
        while (true) {
            int i6 = this.jjround + 1;
            this.jjround = i6;
            if (i6 == Integer.MAX_VALUE) {
                ReInitRounds();
            }
            char c = this.curChar;
            if (c < '@') {
                do {
                    int[] iArr = this.jjstateSet;
                    i3--;
                    int i7 = iArr[i3];
                    if (i7 == 0 || i7 == 1) {
                        if (this.curChar == '=') {
                            jjCheckNAddTwoStates(1, 2);
                        }
                    } else if (i7 != 3) {
                        if (i7 != 4) {
                            if (i7 == 5 && this.curChar == '=') {
                                int i8 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i8 + 1;
                                iArr[i8] = 4;
                            }
                        } else if (this.curChar == '=') {
                            int i9 = this.jjnewStateCnt;
                            this.jjnewStateCnt = i9 + 1;
                            iArr[i9] = 3;
                        }
                    } else if (this.curChar == '=') {
                        int i10 = this.jjnewStateCnt;
                        this.jjnewStateCnt = i10 + 1;
                        iArr[i10] = 0;
                    }
                } while (i3 != i5);
            } else if (c < 128) {
                do {
                    int[] iArr2 = this.jjstateSet;
                    i3--;
                    int i11 = iArr2[i3];
                    if (i11 != 2) {
                        if (i11 == 6 && this.curChar == ']') {
                            int i12 = this.jjnewStateCnt;
                            this.jjnewStateCnt = i12 + 1;
                            iArr2[i12] = 5;
                        }
                    } else if (this.curChar == ']' && i4 > 22) {
                        i4 = 22;
                    }
                } while (i3 != i5);
            } else {
                do {
                    i3--;
                    int i13 = this.jjstateSet[i3];
                } while (i3 != i5);
            }
            if (i4 != Integer.MAX_VALUE) {
                this.jjmatchedKind = i4;
                this.jjmatchedPos = i2;
                i4 = Integer.MAX_VALUE;
            }
            i2++;
            i3 = this.jjnewStateCnt;
            this.jjnewStateCnt = i5;
            i5 = 7 - i5;
            if (i3 == i5) {
                return i2;
            }
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException unused) {
                return i2;
            }
        }
    }

    private int jjMoveStringLiteralDfa0_0() {
        char c = this.curChar;
        if (c == '#') {
            return jjStopAtPos(0, 69);
        }
        if (c == '%') {
            return jjStopAtPos(0, 87);
        }
        if (c == '[') {
            this.jjmatchedKind = 77;
            return jjMoveStringLiteralDfa1_0(30720L, 0L);
        }
        if (c == 'i') {
            return jjMoveStringLiteralDfa1_0(1649267441664L, 0L);
        }
        if (c == 'l') {
            return jjMoveStringLiteralDfa1_0(2199023255552L, 0L);
        }
        if (c == 'r') {
            return jjMoveStringLiteralDfa1_0(105553116266496L, 0L);
        }
        if (c == 'w') {
            return jjMoveStringLiteralDfa1_0(1125899906842624L, 0L);
        }
        if (c == '{') {
            return jjStopAtPos(0, 80);
        }
        if (c == ']') {
            return jjStopAtPos(0, 78);
        }
        if (c == '^') {
            return jjStopAtPos(0, 86);
        }
        if (c == 'a') {
            return jjMoveStringLiteralDfa1_0(TagBits.HasTypeVariable, 0L);
        }
        if (c == 'b') {
            return jjMoveStringLiteralDfa1_0(1073741824L, 0L);
        }
        if (c == 'n') {
            return jjMoveStringLiteralDfa1_0(13194139533312L, 0L);
        }
        if (c == 'o') {
            return jjMoveStringLiteralDfa1_0(17592186044416L, 0L);
        }
        if (c == 't') {
            return jjMoveStringLiteralDfa1_0(422212465065984L, 0L);
        }
        if (c == 'u') {
            return jjMoveStringLiteralDfa1_0(562949953421312L, 0L);
        }
        if (c == '}') {
            return jjStopAtPos(0, 81);
        }
        if (c == '~') {
            return jjMoveStringLiteralDfa1_0(0L, 1073741824L);
        }
        switch (c) {
            case '(':
                return jjStopAtPos(0, 75);
            case ')':
                return jjStopAtPos(0, 76);
            case '*':
                return jjStopAtPos(0, 84);
            case '+':
                return jjStopAtPos(0, 82);
            case ',':
                return jjStopAtPos(0, 72);
            case '-':
                this.jjmatchedKind = 83;
                return jjMoveStringLiteralDfa1_0(66496L, 0L);
            case '.':
                this.jjmatchedKind = 73;
                return jjMoveStringLiteralDfa1_0(0L, 16809984L);
            case '/':
                return jjStopAtPos(0, 85);
            default:
                switch (c) {
                    case ':':
                        this.jjmatchedKind = 74;
                        return jjMoveStringLiteralDfa1_0(0L, 2L);
                    case ';':
                        return jjStopAtPos(0, 70);
                    case '<':
                        this.jjmatchedKind = 89;
                        return jjMoveStringLiteralDfa1_0(0L, TagBits.HasUnresolvedSuperinterfaces);
                    case '=':
                        this.jjmatchedKind = 71;
                        return jjMoveStringLiteralDfa1_0(0L, TagBits.HasTypeVariable);
                    case '>':
                        this.jjmatchedKind = 91;
                        return jjMoveStringLiteralDfa1_0(0L, TagBits.HasUnresolvedMemberTypes);
                    default:
                        switch (c) {
                            case 'd':
                                return jjMoveStringLiteralDfa1_0(2147483648L, 0L);
                            case 'e':
                                return jjMoveStringLiteralDfa1_0(30064771072L, 0L);
                            case 'f':
                                return jjMoveStringLiteralDfa1_0(240518168576L, 0L);
                            case 'g':
                                return jjMoveStringLiteralDfa1_0(274877906944L, 0L);
                            default:
                                return jjMoveNfa_0(8, 0);
                        }
                }
        }
    }

    private int jjMoveStringLiteralDfa0_1() {
        return jjMoveNfa_1(4, 0);
    }

    private int jjMoveStringLiteralDfa0_10() {
        if (this.curChar != ']') {
            return 1;
        }
        return jjMoveStringLiteralDfa1_10(TagBits.HasUnresolvedSuperinterfaces);
    }

    private int jjMoveStringLiteralDfa0_11() {
        return jjMoveNfa_11(6, 0);
    }

    private int jjMoveStringLiteralDfa0_2() {
        if (this.curChar != ']') {
            return 1;
        }
        return jjMoveStringLiteralDfa1_2(262144L);
    }

    private int jjMoveStringLiteralDfa0_3() {
        if (this.curChar != ']') {
            return 1;
        }
        return jjMoveStringLiteralDfa1_3(524288L);
    }

    private int jjMoveStringLiteralDfa0_4() {
        if (this.curChar != ']') {
            return 1;
        }
        return jjMoveStringLiteralDfa1_4(1048576L);
    }

    private int jjMoveStringLiteralDfa0_5() {
        if (this.curChar != ']') {
            return 1;
        }
        return jjMoveStringLiteralDfa1_5(2097152L);
    }

    private int jjMoveStringLiteralDfa0_6() {
        return jjMoveNfa_6(6, 0);
    }

    private int jjMoveStringLiteralDfa0_7() {
        if (this.curChar != ']') {
            return 1;
        }
        return jjMoveStringLiteralDfa1_7(8388608L);
    }

    private int jjMoveStringLiteralDfa0_8() {
        if (this.curChar != ']') {
            return 1;
        }
        return jjMoveStringLiteralDfa1_8(TagBits.HasUnresolvedTypeVariables);
    }

    private int jjMoveStringLiteralDfa0_9() {
        if (this.curChar != ']') {
            return 1;
        }
        return jjMoveStringLiteralDfa1_9(TagBits.HasUnresolvedSuperclass);
    }

    private int jjMoveStringLiteralDfa1_0(long j, long j2) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == '-') {
                if ((65536 & j) != 0) {
                    this.jjmatchedKind = 16;
                    this.jjmatchedPos = 1;
                }
                return jjMoveStringLiteralDfa2_0(j, 960L, j2, 0L);
            }
            if (c == '.') {
                if ((TagBits.HasUnresolvedTypeVariables & j2) != 0) {
                    this.jjmatchedKind = 88;
                    this.jjmatchedPos = 1;
                }
                return jjMoveStringLiteralDfa2_0(j, 0L, j2, 32768L);
            }
            if (c != ':') {
                if (c == '=') {
                    if ((TagBits.HasUnresolvedSuperinterfaces & j2) != 0) {
                        return jjStopAtPos(1, 90);
                    }
                    if ((TagBits.HasUnresolvedMemberTypes & j2) != 0) {
                        return jjStopAtPos(1, 92);
                    }
                    if ((TagBits.HasTypeVariable & j2) != 0) {
                        return jjStopAtPos(1, 93);
                    }
                    return (1073741824 & j2) != 0 ? jjStopAtPos(1, 94) : jjMoveStringLiteralDfa2_0(j, 28672L, j2, 0L);
                }
                if (c != '[') {
                    if (c == 'a') {
                        return jjMoveStringLiteralDfa2_0(j, 34359738368L, j2, 0L);
                    }
                    if (c == 'l') {
                        return jjMoveStringLiteralDfa2_0(j, 12884901888L, j2, 0L);
                    }
                    if (c == 'r') {
                        return (17592186044416L & j) != 0 ? jjStartNfaWithStates_0(1, 44, 17) : jjMoveStringLiteralDfa2_0(j, 281476050452480L, j2, 0L);
                    }
                    if (c == 'u') {
                        return jjMoveStringLiteralDfa2_0(j, 137438953472L, j2, 0L);
                    }
                    if (c == 'e') {
                        return jjMoveStringLiteralDfa2_0(j, 105553116266496L, j2, 0L);
                    }
                    if (c != 'f') {
                        if (c == 'h') {
                            return jjMoveStringLiteralDfa2_0(j, 1266637395197952L, j2, 0L);
                        }
                        if (c == 'i') {
                            return jjMoveStringLiteralDfa2_0(j, 4398046511104L, j2, 0L);
                        }
                        if (c == 'n') {
                            return (1099511627776L & j) != 0 ? jjStartNfaWithStates_0(1, 40, 17) : jjMoveStringLiteralDfa2_0(j, 562967670161408L, j2, 0L);
                        }
                        if (c == 'o') {
                            return (2147483648L & j) != 0 ? jjStartNfaWithStates_0(1, 31, 17) : jjMoveStringLiteralDfa2_0(j, 11338713661440L, j2, 0L);
                        }
                    } else if ((549755813888L & j) != 0) {
                        return jjStartNfaWithStates_0(1, 39, 17);
                    }
                } else if ((2048 & j) != 0) {
                    return jjStopAtPos(1, 11);
                }
            } else if ((2 & j2) != 0) {
                return jjStopAtPos(1, 65);
            }
            return jjStartNfa_0(0, j, j2);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_0(0, j, j2);
            return 1;
        }
    }

    private int jjMoveStringLiteralDfa1_10(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 2;
            }
            return jjMoveStringLiteralDfa2_10(j, TagBits.HasUnresolvedSuperinterfaces);
        } catch (IOException unused) {
            return 1;
        }
    }

    private int jjMoveStringLiteralDfa1_2(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == ']' && (j & 262144) != 0) {
                return jjStopAtPos(1, 18);
            }
            return 2;
        } catch (IOException unused) {
            return 1;
        }
    }

    private int jjMoveStringLiteralDfa1_3(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 2;
            }
            return jjMoveStringLiteralDfa2_3(j, 524288L);
        } catch (IOException unused) {
            return 1;
        }
    }

    private int jjMoveStringLiteralDfa1_4(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 2;
            }
            return jjMoveStringLiteralDfa2_4(j, 1048576L);
        } catch (IOException unused) {
            return 1;
        }
    }

    private int jjMoveStringLiteralDfa1_5(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 2;
            }
            return jjMoveStringLiteralDfa2_5(j, 2097152L);
        } catch (IOException unused) {
            return 1;
        }
    }

    private int jjMoveStringLiteralDfa1_7(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == ']' && (j & 8388608) != 0) {
                return jjStopAtPos(1, 23);
            }
            return 2;
        } catch (IOException unused) {
            return 1;
        }
    }

    private int jjMoveStringLiteralDfa1_8(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 2;
            }
            return jjMoveStringLiteralDfa2_8(j, TagBits.HasUnresolvedTypeVariables);
        } catch (IOException unused) {
            return 1;
        }
    }

    private int jjMoveStringLiteralDfa1_9(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 2;
            }
            return jjMoveStringLiteralDfa2_9(j, TagBits.HasUnresolvedSuperclass);
        } catch (IOException unused) {
            return 1;
        }
    }

    private int jjMoveStringLiteralDfa2_0(long j, long j2, long j3, long j4) {
        long j5 = j2 & j;
        long j6 = j4 & j3;
        if ((j5 | j6) == 0) {
            return jjStartNfa_0(0, j, j3);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '.') {
                if (c == '=') {
                    return jjMoveStringLiteralDfa3_0(j5, 24576L, j6, 0L);
                }
                if (c == '[') {
                    return (4096 & j5) != 0 ? jjStopAtPos(2, 12) : jjMoveStringLiteralDfa3_0(j5, 960L, j6, 0L);
                }
                if (c == 'i') {
                    return jjMoveStringLiteralDfa3_0(j5, 1125899906842624L, j6, 0L);
                }
                if (c == 'l') {
                    return (4398046511104L & j5) != 0 ? jjStartNfaWithStates_0(2, 42, 17) : jjMoveStringLiteralDfa3_0(j5, 34359738368L, j6, 0L);
                }
                if (c == 'n') {
                    return jjMoveStringLiteralDfa3_0(j5, 137438953472L, j6, 0L);
                }
                if (c == 'p') {
                    return jjMoveStringLiteralDfa3_0(j5, 70368744177664L, j6, 0L);
                }
                switch (c) {
                    case 'c':
                        return jjMoveStringLiteralDfa3_0(j5, 2199023255552L, j6, 0L);
                    case 'd':
                        if ((TagBits.HasTypeVariable & j5) != 0) {
                            return jjStartNfaWithStates_0(2, 29, 17);
                        }
                        if ((17179869184L & j5) != 0) {
                            return jjStartNfaWithStates_0(2, 34, 17);
                        }
                        break;
                    case 'e':
                        return jjMoveStringLiteralDfa3_0(j5, 140738562097152L, j6, 0L);
                    default:
                        switch (c) {
                            case 'r':
                                if ((68719476736L & j5) != 0) {
                                    return jjStartNfaWithStates_0(2, 36, 17);
                                }
                                break;
                            case 's':
                                return jjMoveStringLiteralDfa3_0(j5, 12884901888L, j6, 0L);
                            case 't':
                                return (8796093022208L & j5) != 0 ? jjStartNfaWithStates_0(2, 43, 17) : jjMoveStringLiteralDfa3_0(j5, 598409203417088L, j6, 0L);
                            case 'u':
                                return jjMoveStringLiteralDfa3_0(j5, 281474976710656L, j6, 0L);
                        }
                        break;
                }
            } else if ((32768 & j6) != 0) {
                return jjStopAtPos(2, 79);
            }
            return jjStartNfa_0(1, j5, j6);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_0(1, j5, j6);
            return 2;
        }
    }

    private int jjMoveStringLiteralDfa2_10(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 2;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 3;
            }
            return jjMoveStringLiteralDfa3_10(j3, TagBits.HasUnresolvedSuperinterfaces);
        } catch (IOException unused) {
            return 2;
        }
    }

    private int jjMoveStringLiteralDfa2_3(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 2;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == ']' && (j3 & 524288) != 0) {
                return jjStopAtPos(2, 19);
            }
            return 3;
        } catch (IOException unused) {
            return 2;
        }
    }

    private int jjMoveStringLiteralDfa2_4(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 2;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 3;
            }
            return jjMoveStringLiteralDfa3_4(j3, 1048576L);
        } catch (IOException unused) {
            return 2;
        }
    }

    private int jjMoveStringLiteralDfa2_5(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 2;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 3;
            }
            return jjMoveStringLiteralDfa3_5(j3, 2097152L);
        } catch (IOException unused) {
            return 2;
        }
    }

    private int jjMoveStringLiteralDfa2_8(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 2;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == ']' && (j3 & TagBits.HasUnresolvedTypeVariables) != 0) {
                return jjStopAtPos(2, 24);
            }
            return 3;
        } catch (IOException unused) {
            return 2;
        }
    }

    private int jjMoveStringLiteralDfa2_9(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 2;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 3;
            }
            return jjMoveStringLiteralDfa3_9(j3, TagBits.HasUnresolvedSuperclass);
        } catch (IOException unused) {
            return 2;
        }
    }

    private int jjMoveStringLiteralDfa3_0(long j, long j2, long j3, long j4) {
        long j5 = j2 & j;
        if (((j4 & j3) | j5) == 0) {
            return jjStartNfa_0(1, j, j3);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == '=') {
                return jjMoveStringLiteralDfa4_0(j5, 17280L);
            }
            if (c != '[') {
                if (c == 'a') {
                    return jjMoveStringLiteralDfa4_0(j5, 2200096997376L);
                }
                if (c == 'c') {
                    return jjMoveStringLiteralDfa4_0(j5, 137438953472L);
                }
                if (c == 'e') {
                    if ((4294967296L & j5) != 0) {
                        this.jjmatchedKind = 32;
                        this.jjmatchedPos = 3;
                    } else if ((281474976710656L & j5) != 0) {
                        return jjStartNfaWithStates_0(3, 48, 17);
                    }
                    return jjMoveStringLiteralDfa4_0(j5, 70377334112256L);
                }
                if (c == 'i') {
                    return jjMoveStringLiteralDfa4_0(j5, 562949953421312L);
                }
                if (c == 'l') {
                    return jjMoveStringLiteralDfa4_0(j5, 1125899906842624L);
                }
                if (c == 's') {
                    return jjMoveStringLiteralDfa4_0(j5, 34359738368L);
                }
                if (c == 'u') {
                    return jjMoveStringLiteralDfa4_0(j5, 35184372088832L);
                }
                if (c != 'n') {
                    if (c == 'o' && (274877906944L & j5) != 0) {
                        return jjStartNfaWithStates_0(3, 38, 17);
                    }
                } else if ((140737488355328L & j5) != 0) {
                    return jjStartNfaWithStates_0(3, 47, 17);
                }
            } else {
                if ((64 & j5) != 0) {
                    return jjStopAtPos(3, 6);
                }
                if ((8192 & j5) != 0) {
                    return jjStopAtPos(3, 13);
                }
            }
            return jjStartNfa_0(2, j5, 0L);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_0(2, j5, 0L);
            return 3;
        }
    }

    private int jjMoveStringLiteralDfa3_10(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 3;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 4;
            }
            return jjMoveStringLiteralDfa4_10(j3, TagBits.HasUnresolvedSuperinterfaces);
        } catch (IOException unused) {
            return 3;
        }
    }

    private int jjMoveStringLiteralDfa3_4(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 3;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == ']' && (j3 & 1048576) != 0) {
                return jjStopAtPos(3, 20);
            }
            return 4;
        } catch (IOException unused) {
            return 3;
        }
    }

    private int jjMoveStringLiteralDfa3_5(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 3;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '=') {
                return 4;
            }
            return jjMoveStringLiteralDfa4_5(j3, 2097152L);
        } catch (IOException unused) {
            return 3;
        }
    }

    private int jjMoveStringLiteralDfa3_9(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 3;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == ']' && (j3 & TagBits.HasUnresolvedSuperclass) != 0) {
                return jjStopAtPos(3, 25);
            }
            return 4;
        } catch (IOException unused) {
            return 3;
        }
    }

    private int jjMoveStringLiteralDfa4_0(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_0(2, j, 0L);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == '=') {
                return jjMoveStringLiteralDfa5_0(j3, 768L);
            }
            if (c != '[') {
                if (c == 'a') {
                    return jjMoveStringLiteralDfa5_0(j3, 70368744177664L);
                }
                if (c != 'e') {
                    if (c == 'i') {
                        return jjMoveStringLiteralDfa5_0(j3, 8589934592L);
                    }
                    if (c == 'r') {
                        return jjMoveStringLiteralDfa5_0(j3, 35184372088832L);
                    }
                    if (c == 't') {
                        return jjMoveStringLiteralDfa5_0(j3, 137438953472L);
                    }
                    if (c != 'k') {
                        if (c == 'l') {
                            if ((2199023255552L & j3) != 0) {
                                return jjStartNfaWithStates_0(4, 41, 17);
                            }
                            if ((562949953421312L & j3) != 0) {
                                return jjStartNfaWithStates_0(4, 49, 17);
                            }
                        }
                    } else if ((1073741824 & j3) != 0) {
                        return jjStartNfaWithStates_0(4, 30, 17);
                    }
                } else {
                    if ((34359738368L & j3) != 0) {
                        return jjStartNfaWithStates_0(4, 35, 17);
                    }
                    if ((1125899906842624L & j3) != 0) {
                        return jjStartNfaWithStates_0(4, 50, 17);
                    }
                }
            } else {
                if ((128 & j3) != 0) {
                    return jjStopAtPos(4, 7);
                }
                if ((16384 & j3) != 0) {
                    return jjStopAtPos(4, 14);
                }
            }
            return jjStartNfa_0(3, j3, 0L);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_0(3, j3, 0L);
            return 4;
        }
    }

    private int jjMoveStringLiteralDfa4_10(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 4;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == ']' && (j3 & TagBits.HasUnresolvedSuperinterfaces) != 0) {
                return jjStopAtPos(4, 26);
            }
            return 5;
        } catch (IOException unused) {
            return 4;
        }
    }

    private int jjMoveStringLiteralDfa4_5(long j, long j2) {
        long j3 = j & j2;
        if (j3 == 0) {
            return 4;
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == ']' && (j3 & 2097152) != 0) {
                return jjStopAtPos(4, 21);
            }
            return 5;
        } catch (IOException unused) {
            return 4;
        }
    }

    private int jjMoveStringLiteralDfa5_0(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_0(3, j, 0L);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == '=') {
                return jjMoveStringLiteralDfa6_0(j3, 512L);
            }
            if (c != '[') {
                if (c != 'f') {
                    if (c == 'i') {
                        return jjMoveStringLiteralDfa6_0(j3, 137438953472L);
                    }
                    if (c != 'n') {
                        if (c == 't' && (70368744177664L & j3) != 0) {
                            return jjStartNfaWithStates_0(5, 46, 17);
                        }
                    } else if ((35184372088832L & j3) != 0) {
                        return jjStartNfaWithStates_0(5, 45, 17);
                    }
                } else if ((8589934592L & j3) != 0) {
                    return jjStartNfaWithStates_0(5, 33, 17);
                }
            } else if ((256 & j3) != 0) {
                return jjStopAtPos(5, 8);
            }
            return jjStartNfa_0(4, j3, 0L);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_0(4, j3, 0L);
            return 5;
        }
    }

    private int jjMoveStringLiteralDfa6_0(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_0(4, j, 0L);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '[') {
                if (c == 'o') {
                    return jjMoveStringLiteralDfa7_0(j3, 137438953472L);
                }
            } else if ((512 & j3) != 0) {
                return jjStopAtPos(6, 9);
            }
            return jjStartNfa_0(5, j3, 0L);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_0(5, j3, 0L);
            return 6;
        }
    }

    private int jjMoveStringLiteralDfa7_0(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_0(5, j, 0L);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            return (c == 'n' && (137438953472L & j3) != 0) ? jjStartNfaWithStates_0(7, 37, 17) : jjStartNfa_0(6, j3, 0L);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_0(6, j3, 0L);
            return 7;
        }
    }

    private int jjStartNfaWithStates_0(int i, int i2, int i3) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_0(i3, i + 1);
        } catch (IOException unused) {
            return i + 1;
        }
    }

    private final int jjStartNfa_0(int i, long j, long j2) {
        return jjMoveNfa_0(jjStopStringLiteralDfa_0(i, j, j2), i + 1);
    }

    private int jjStopAtPos(int i, int i2) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        return i + 1;
    }

    private final int jjStopStringLiteralDfa_0(int i, long j, long j2) {
        switch (i) {
            case 0:
                if ((j & 30720) != 0 || (j2 & 8192) != 0) {
                    return 14;
                }
                if ((j2 & 16810496) != 0) {
                    return 31;
                }
                if ((j & 2251799276814336L) == 0) {
                    return ((j & 66496) == 0 && (j2 & 524288) == 0) ? -1 : 7;
                }
                this.jjmatchedKind = 51;
                return 17;
            case 1:
                if ((j & 66496) != 0) {
                    return 6;
                }
                if ((j & 28672) != 0) {
                    return 13;
                }
                if ((j & 19243600969728L) != 0) {
                    return 17;
                }
                if ((j & 2232555675844608L) == 0) {
                    return -1;
                }
                if (this.jjmatchedPos != 1) {
                    this.jjmatchedKind = 51;
                    this.jjmatchedPos = 1;
                }
                return 17;
            case 2:
                if ((j & 2219275100094464L) != 0) {
                    this.jjmatchedKind = 51;
                    this.jjmatchedPos = 2;
                    return 17;
                }
                if ((j & 24576) != 0) {
                    return 12;
                }
                if ((j & 960) != 0) {
                    return 5;
                }
                return (j & 13280575750144L) != 0 ? 17 : -1;
            case 3:
                if ((j & 896) != 0) {
                    return 4;
                }
                if ((j & 1796774872219648L) == 0) {
                    if ((j & 422500227874816L) != 0) {
                        return 17;
                    }
                    return (j & 16384) != 0 ? 9 : -1;
                }
                if (this.jjmatchedPos != 3) {
                    this.jjmatchedKind = 51;
                    this.jjmatchedPos = 3;
                }
                return 17;
            case 4:
                if ((j & 105699145154560L) != 0) {
                    this.jjmatchedKind = 51;
                    this.jjmatchedPos = 4;
                    return 17;
                }
                if ((j & 768) != 0) {
                    return 3;
                }
                return (j & 1691084316999680L) != 0 ? 17 : -1;
            case 5:
                if ((j & 512) != 0) {
                    return 0;
                }
                if ((j & 105561706201088L) != 0) {
                    return 17;
                }
                if ((j & 137438953472L) == 0) {
                    return -1;
                }
                this.jjmatchedKind = 51;
                this.jjmatchedPos = 5;
                return 17;
            case 6:
                if ((j & 137438953472L) == 0) {
                    return -1;
                }
                this.jjmatchedKind = 51;
                this.jjmatchedPos = 6;
                return 17;
            default:
                return -1;
        }
    }

    public void ReInit(SimpleCharStream simpleCharStream) {
        this.jjnewStateCnt = 0;
        this.jjmatchedPos = 0;
        this.curLexState = this.defaultLexState;
        this.input_stream = simpleCharStream;
        ReInitRounds();
    }

    public void ReInit(SimpleCharStream simpleCharStream, int i) {
        ReInit(simpleCharStream);
        SwitchTo(i);
    }

    void SkipLexicalActions(Token token) {
    }

    public void SwitchTo(int i) {
        if (i >= 12 || i < 0) {
            throw new TokenMgrError(new StringBuffer("Error: Ignoring invalid lexical state : ").append(i).append(". State unchanged.").toString(), 2);
        }
        this.curLexState = i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:12:0x003a A[PHI: r5
      0x003a: PHI (r5v26 int) = 
      (r5v17 int)
      (r5v18 int)
      (r5v19 int)
      (r5v20 int)
      (r5v21 int)
      (r5v22 int)
      (r5v23 int)
      (r5v24 int)
      (r5v25 int)
      (r5v27 int)
     binds: [B:56:0x00d5, B:51:0x00c3, B:46:0x00b2, B:41:0x00a1, B:36:0x0090, B:31:0x007f, B:26:0x006e, B:21:0x005d, B:16:0x004c, B:11:0x0038] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:132:0x019d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:133:0x0131 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:134:0x017b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:135:0x0152 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:137:0x0008 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:69:0x0111  */
    /* JADX WARN: Code duplicated, block: B:71:0x0117  */
    /* JADX WARN: Code duplicated, block: B:76:0x013f  */
    /* JADX WARN: Code duplicated, block: B:78:0x0142  */
    /* JADX WARN: Code duplicated, block: B:82:0x0160  */
    /* JADX WARN: Code duplicated, block: B:85:0x0167  */
    /* JADX WARN: Code duplicated, block: B:87:0x0170  */
    /* JADX WARN: Code duplicated, block: B:91:0x017f  */
    /* JADX WARN: Code duplicated, block: B:93:0x018d  */
    public Token getNextToken() {
        int i;
        int i2;
        boolean z;
        String strGetImage;
        int i3;
        long[] jArr;
        int i4;
        int i5;
        int i6;
        Token tokenJjFillToken;
        int i7;
        String strGetImage2 = null;
        int i8 = 0;
        Token token = null;
        int iJjMoveStringLiteralDfa0_0 = 0;
        while (true) {
            try {
                this.curChar = this.input_stream.BeginToken();
                StringBuffer stringBuffer = this.jjimage;
                this.image = stringBuffer;
                stringBuffer.setLength(0);
                this.jjimageLen = 0;
                while (true) {
                    switch (this.curLexState) {
                        case 0:
                            try {
                                this.input_stream.backup(0);
                                while (true) {
                                    char c = this.curChar;
                                    if (c <= ' ' && ((1 << c) & 4294981120L) != 0) {
                                        this.curChar = this.input_stream.BeginToken();
                                    }
                                }
                                this.jjmatchedKind = Integer.MAX_VALUE;
                                this.jjmatchedPos = 0;
                                iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_0();
                                if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                    i3 = this.jjmatchedPos;
                                    if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                        this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                    }
                                    jArr = jjtoToken;
                                    i4 = this.jjmatchedKind;
                                    if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                        Token tokenJjFillToken2 = jjFillToken();
                                        tokenJjFillToken2.specialToken = token;
                                        i5 = jjnewLexState[this.jjmatchedKind];
                                        if (i5 != -1) {
                                            this.curLexState = i5;
                                        }
                                        return tokenJjFillToken2;
                                    }
                                    if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                        this.jjimageLen += this.jjmatchedPos + 1;
                                        i7 = jjnewLexState[i4];
                                        if (i7 != -1) {
                                            this.curLexState = i7;
                                        }
                                        this.jjmatchedKind = Integer.MAX_VALUE;
                                        try {
                                            this.curChar = this.input_stream.readChar();
                                            iJjMoveStringLiteralDfa0_0 = 0;
                                        } catch (IOException unused) {
                                            iJjMoveStringLiteralDfa0_0 = 0;
                                        }
                                    } else {
                                        if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                            tokenJjFillToken = jjFillToken();
                                            if (token != null) {
                                                tokenJjFillToken.specialToken = token;
                                                token.next = tokenJjFillToken;
                                            }
                                            SkipLexicalActions(tokenJjFillToken);
                                            token = tokenJjFillToken;
                                        } else {
                                            SkipLexicalActions(null);
                                        }
                                        i6 = jjnewLexState[this.jjmatchedKind];
                                        if (i6 != -1) {
                                            this.curLexState = i6;
                                        }
                                    }
                                }
                            } catch (IOException unused2) {
                                continue;
                            }
                            break;
                        case 1:
                            this.jjmatchedKind = 17;
                            this.jjmatchedPos = -1;
                            iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_1();
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken3 = jjFillToken();
                                    tokenJjFillToken3.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken3;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                        case 2:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_2();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                                this.jjmatchedKind = 28;
                            }
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken4 = jjFillToken();
                                    tokenJjFillToken4.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken4;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                        case 3:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_3();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                                this.jjmatchedKind = 28;
                            }
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken5 = jjFillToken();
                                    tokenJjFillToken5.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken5;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                        case 4:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_4();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                                this.jjmatchedKind = 28;
                            }
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken6 = jjFillToken();
                                    tokenJjFillToken6.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken6;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                        case 5:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_5();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                                this.jjmatchedKind = 28;
                            }
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken7 = jjFillToken();
                                    tokenJjFillToken7.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken7;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                        case 6:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_6();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                                this.jjmatchedKind = 28;
                            }
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken8 = jjFillToken();
                                    tokenJjFillToken8.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken8;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                        case 7:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_7();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                                this.jjmatchedKind = 28;
                            }
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken9 = jjFillToken();
                                    tokenJjFillToken9.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken9;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                        case 8:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_8();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                                this.jjmatchedKind = 28;
                            }
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken10 = jjFillToken();
                                    tokenJjFillToken10.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken10;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                        case 9:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_9();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                                this.jjmatchedKind = 28;
                            }
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken11 = jjFillToken();
                                    tokenJjFillToken11.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken11;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                        case 10:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_10();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                                this.jjmatchedKind = 28;
                            }
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken12 = jjFillToken();
                                    tokenJjFillToken12.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken12;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                        case 11:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_11();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                                this.jjmatchedKind = 28;
                            }
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken13 = jjFillToken();
                                    tokenJjFillToken13.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken13;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                        default:
                            if (this.jjmatchedKind != Integer.MAX_VALUE) {
                                i3 = this.jjmatchedPos;
                                if (i3 + 1 < iJjMoveStringLiteralDfa0_0) {
                                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - i3) - 1);
                                }
                                jArr = jjtoToken;
                                i4 = this.jjmatchedKind;
                                if ((jArr[i4 >> 6] & (1 << (i4 & 63))) != 0) {
                                    Token tokenJjFillToken14 = jjFillToken();
                                    tokenJjFillToken14.specialToken = token;
                                    i5 = jjnewLexState[this.jjmatchedKind];
                                    if (i5 != -1) {
                                        this.curLexState = i5;
                                    }
                                    return tokenJjFillToken14;
                                }
                                if ((jjtoSkip[i4 >> 6] & (1 << (i4 & 63))) == 0) {
                                    this.jjimageLen += this.jjmatchedPos + 1;
                                    i7 = jjnewLexState[i4];
                                    if (i7 != -1) {
                                        this.curLexState = i7;
                                    }
                                    this.jjmatchedKind = Integer.MAX_VALUE;
                                    this.curChar = this.input_stream.readChar();
                                    iJjMoveStringLiteralDfa0_0 = 0;
                                } else {
                                    if (((1 << (i4 & 63)) & jjtoSpecial[i4 >> 6]) != 0) {
                                        tokenJjFillToken = jjFillToken();
                                        if (token != null) {
                                            tokenJjFillToken.specialToken = token;
                                            token.next = tokenJjFillToken;
                                        }
                                        SkipLexicalActions(tokenJjFillToken);
                                        token = tokenJjFillToken;
                                    } else {
                                        SkipLexicalActions(null);
                                    }
                                    i6 = jjnewLexState[this.jjmatchedKind];
                                    if (i6 != -1) {
                                        this.curLexState = i6;
                                    }
                                }
                            }
                            break;
                    }
                }
            } catch (IOException unused3) {
                this.jjmatchedKind = 0;
                Token tokenJjFillToken15 = jjFillToken();
                tokenJjFillToken15.specialToken = token;
                return tokenJjFillToken15;
            }
        }
        int endLine = this.input_stream.getEndLine();
        int endColumn = this.input_stream.getEndColumn();
        try {
            this.input_stream.readChar();
            this.input_stream.backup(1);
            z = false;
            i2 = endLine;
            i = endColumn;
        } catch (IOException unused4) {
            strGetImage2 = iJjMoveStringLiteralDfa0_0 <= 1 ? "" : this.input_stream.GetImage();
            char c2 = this.curChar;
            if (c2 == '\n' || c2 == '\r') {
                endLine++;
            } else {
                i8 = endColumn + 1;
            }
            i = i8;
            i2 = endLine;
            z = true;
        }
        if (z) {
            strGetImage = strGetImage2;
        } else {
            this.input_stream.backup(1);
            strGetImage = iJjMoveStringLiteralDfa0_0 > 1 ? this.input_stream.GetImage() : "";
        }
        throw new TokenMgrError(z, this.curLexState, i2, i, strGetImage, this.curChar, 0);
    }

    protected Token jjFillToken() {
        String strGetImage;
        int beginLine;
        int beginColumn;
        int endLine;
        int endColumn;
        if (this.jjmatchedPos < 0) {
            StringBuffer stringBuffer = this.image;
            strGetImage = stringBuffer == null ? "" : stringBuffer.toString();
            beginLine = this.input_stream.getBeginLine();
            beginColumn = this.input_stream.getBeginColumn();
            endLine = beginLine;
            endColumn = beginColumn;
        } else {
            strGetImage = jjstrLiteralImages[this.jjmatchedKind];
            if (strGetImage == null) {
                strGetImage = this.input_stream.GetImage();
            }
            beginLine = this.input_stream.getBeginLine();
            beginColumn = this.input_stream.getBeginColumn();
            endLine = this.input_stream.getEndLine();
            endColumn = this.input_stream.getEndColumn();
        }
        Token tokenNewToken = Token.newToken(this.jjmatchedKind, strGetImage);
        tokenNewToken.beginLine = beginLine;
        tokenNewToken.endLine = endLine;
        tokenNewToken.beginColumn = beginColumn;
        tokenNewToken.endColumn = endColumn;
        return tokenNewToken;
    }

    public void setDebugStream(PrintStream printStream) {
        this.debugStream = printStream;
    }
}
