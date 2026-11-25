package org.eclipse.jdt.internal.compiler.flow;

import java.util.Arrays;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;

/* JADX INFO: loaded from: classes14.dex */
public class UnconditionalFlowInfo extends FlowInfo {
    public static final int BitCacheSize = 64;
    public static final boolean COVERAGE_TEST_FLAG = false;
    public static int CoverageTestId = 0;
    public static final int IN = 6;
    public static final int INN = 7;
    public static final int extraLength = 8;
    public long definiteInits;
    public long[][] extra;
    public long iNBit;
    public long iNNBit;
    public int maxFieldCount;
    public long nullBit1;
    public long nullBit2;
    public long nullBit3;
    public long nullBit4;
    public long potentialInits;

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo initsWhenFalse() {
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo initsWhenTrue() {
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo unconditionalInits() {
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo unconditionalInitsWithoutSideEffect() {
        return this;
    }

    public static class AssertionFailedException extends RuntimeException {
        private static final long serialVersionUID = 1827352841030089703L;

        public AssertionFailedException(String str) {
            super(str);
        }
    }

    public static UnconditionalFlowInfo fakeInitializedFlowInfo(int i, int i2) {
        UnconditionalFlowInfo unconditionalFlowInfo = new UnconditionalFlowInfo();
        unconditionalFlowInfo.maxFieldCount = i2;
        for (int i3 = 0; i3 < i; i3++) {
            unconditionalFlowInfo.markAsDefinitelyAssigned(i3 + i2);
        }
        return unconditionalFlowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo addInitializationsFrom(FlowInfo flowInfo) {
        return addInfoFrom(flowInfo, true);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo addNullInfoFrom(FlowInfo flowInfo) {
        return addInfoFrom(flowInfo, false);
    }

    /* JADX WARN: Code duplicated, block: B:54:0x021b  */
    /* JADX WARN: Code duplicated, block: B:58:0x0221 A[LOOP:2: B:56:0x021e->B:58:0x0221, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:59:0x023a A[LOOP:1: B:55:0x021c->B:59:0x023a, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:62:0x025e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:64:0x0261  */
    /* JADX WARN: Code duplicated, block: B:67:0x0266  */
    /* JADX WARN: Code duplicated, block: B:72:0x026e  */
    /* JADX WARN: Code duplicated, block: B:75:0x0274 A[LOOP:5: B:73:0x026f->B:75:0x0274, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:76:0x0283 A[LOOP:3: B:68:0x0268->B:76:0x0283, LOOP_END] */
    private FlowInfo addInfoFrom(FlowInfo flowInfo, boolean z) {
        int length;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        UnconditionalFlowInfo unconditionalFlowInfo = this;
        if (unconditionalFlowInfo == DEAD_END || flowInfo == DEAD_END) {
            return unconditionalFlowInfo;
        }
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = flowInfo.unconditionalInits();
        if (z) {
            unconditionalFlowInfo.definiteInits |= unconditionalFlowInfoUnconditionalInits.definiteInits;
            unconditionalFlowInfo.potentialInits |= unconditionalFlowInfoUnconditionalInits.potentialInits;
        }
        boolean z2 = (unconditionalFlowInfo.tagBits & 4) != 0;
        boolean z3 = (unconditionalFlowInfoUnconditionalInits.tagBits & 4) != 0;
        if (z3) {
            if (!z2) {
                unconditionalFlowInfo.nullBit1 = unconditionalFlowInfoUnconditionalInits.nullBit1;
                unconditionalFlowInfo.nullBit2 = unconditionalFlowInfoUnconditionalInits.nullBit2;
                unconditionalFlowInfo.nullBit3 = unconditionalFlowInfoUnconditionalInits.nullBit3;
                unconditionalFlowInfo.nullBit4 = unconditionalFlowInfoUnconditionalInits.nullBit4;
                unconditionalFlowInfo.iNBit = unconditionalFlowInfoUnconditionalInits.iNBit;
                unconditionalFlowInfo.iNNBit = unconditionalFlowInfoUnconditionalInits.iNNBit;
            } else {
                long j = unconditionalFlowInfo.nullBit1;
                long j2 = unconditionalFlowInfo.nullBit2;
                long j3 = unconditionalFlowInfo.nullBit3;
                long j4 = unconditionalFlowInfo.nullBit4;
                long j5 = j & j2 & j3 & j4;
                long j6 = unconditionalFlowInfoUnconditionalInits.iNNBit;
                long j7 = unconditionalFlowInfoUnconditionalInits.iNBit;
                long j8 = j7 | j5;
                long j9 = (~j5) | j6;
                long j10 = j & j9;
                long j11 = j9 & j8 & j2;
                long j12 = j9 & j6 & j3;
                long j13 = j4 & j9;
                long j14 = j10 & (j11 | j12 | j13);
                long j15 = unconditionalFlowInfoUnconditionalInits.nullBit1;
                long j16 = j12 & j13;
                long j17 = unconditionalFlowInfoUnconditionalInits.nullBit2;
                long j18 = ~j17;
                long j19 = unconditionalFlowInfoUnconditionalInits.nullBit4;
                long j20 = ~j19;
                long j21 = ~j13;
                long j22 = ~j12;
                long j23 = ~j11;
                long j24 = j23 & j18;
                long j25 = unconditionalFlowInfoUnconditionalInits.nullBit3;
                long j26 = ~j25;
                unconditionalFlowInfo.nullBit1 = j15 | (j14 & ((j16 & j18 & j20) | ((j21 | j22) & (j24 | (j11 & j26 & j20)))));
                long j27 = (j20 | j26 | (j22 & j21)) & j17;
                long j28 = j26 & j20;
                long j29 = ~j15;
                long j30 = ~j14;
                unconditionalFlowInfo.nullBit2 = j27 | (j11 & (j28 | (j29 & (j22 | j30)) | (j14 & j17)));
                long j31 = j12 & j18;
                unconditionalFlowInfo.nullBit3 = (j25 & (((j17 | j11 | j30) & j29) | (j15 & (j19 | j18 | (j14 & j12))) | (j30 & j23 & j21))) | (j31 & j20) | (j29 & ((((j23 & j13) | j30) & j12) | (j14 & j23 & j21 & j17)));
                long j32 = j22 & j26;
                unconditionalFlowInfo.nullBit4 = (((j30 & (j32 | j24)) | (((j18 & j26) | (j11 & j12)) & j14)) & j19) | (j15 & (((j16 | (j23 & j21 & j26)) & j19) | ((((j25 | j13) & j19) | (j22 & j13 & j17 & j25)) & j11) | (j30 & (j19 | ((j13 | j11) & j17 & j25))))) | (j29 & ((j13 & (j32 | ((j12 | j23) & j18))) | (j14 & ((j31 & j19) | (j11 & j17 & (j19 | (j12 & j21 & j26)))))));
                unconditionalFlowInfo.iNBit &= j7;
                unconditionalFlowInfo.iNNBit &= j6;
            }
            unconditionalFlowInfo.tagBits |= 4;
        } else {
            unconditionalFlowInfoUnconditionalInits = unconditionalFlowInfoUnconditionalInits;
            z2 = z2;
            z3 = z3;
        }
        long[][] jArr = unconditionalFlowInfo.extra;
        if (jArr != null || unconditionalFlowInfoUnconditionalInits.extra != null) {
            char c = 7;
            char c2 = 6;
            int i6 = 2;
            int i7 = 8;
            if (jArr != null) {
                long[][] jArr2 = unconditionalFlowInfoUnconditionalInits.extra;
                if (jArr2 != null) {
                    int length2 = jArr[0].length;
                    length = jArr2[0].length;
                    if (length2 < length) {
                        for (int i8 = 0; i8 < 8; i8++) {
                            long[][] jArr3 = unconditionalFlowInfo.extra;
                            long[] jArr4 = jArr3[i8];
                            long[] jArr5 = new long[length];
                            jArr3[i8] = jArr5;
                            System.arraycopy(jArr4, 0, jArr5, 0, length2);
                        }
                        i = length2;
                    } else {
                        i = length;
                        length = 0;
                    }
                }
                if (z) {
                    i5 = 0;
                    while (i5 < i) {
                        long[][] jArr6 = unconditionalFlowInfo.extra;
                        long[] jArr7 = jArr6[0];
                        long j33 = jArr7[i5];
                        long[][] jArr8 = unconditionalFlowInfoUnconditionalInits.extra;
                        jArr7[i5] = j33 | jArr8[0][i5];
                        long[] jArr9 = jArr6[1];
                        jArr9[i5] = jArr9[i5] | jArr8[1][i5];
                        i5++;
                    }
                    while (i5 < length) {
                        long[][] jArr10 = unconditionalFlowInfo.extra;
                        long[] jArr11 = jArr10[0];
                        long[][] jArr12 = unconditionalFlowInfoUnconditionalInits.extra;
                        jArr11[i5] = jArr12[0][i5];
                        jArr10[1][i5] = jArr12[1][i5];
                        i5++;
                    }
                }
                i2 = 0;
                if (!z2) {
                    if (length >= i) {
                        i = length;
                    }
                    length = i;
                    i = 0;
                }
                if (!z3) {
                    length = 0;
                    i = 0;
                }
                while (i2 < i) {
                    long[][] jArr13 = unconditionalFlowInfo.extra;
                    long[] jArr14 = jArr13[i6];
                    long j34 = jArr14[i2];
                    long[] jArr15 = jArr13[3];
                    long j35 = jArr15[i2];
                    long[] jArr16 = jArr13[4];
                    long j36 = jArr16[i2];
                    long[] jArr17 = jArr13[5];
                    long j37 = jArr17[i2];
                    int i9 = length;
                    long j38 = j34 & j35 & j36 & j37;
                    long[][] jArr18 = unconditionalFlowInfoUnconditionalInits.extra;
                    long[] jArr19 = jArr18[c];
                    long j39 = jArr19[i2];
                    long[] jArr20 = jArr18[c2];
                    long j40 = jArr20[i2] | j38;
                    long j41 = (~j38) | j39;
                    long j42 = j34 & j41;
                    long j43 = j41 & j40 & j35;
                    long j44 = j41 & j39 & j36;
                    long j45 = j37 & j41;
                    long j46 = j42 & (j43 | j44 | j45);
                    int i10 = i;
                    UnconditionalFlowInfo unconditionalFlowInfo2 = unconditionalFlowInfoUnconditionalInits;
                    long j47 = jArr18[i6][i2];
                    long j48 = j44 & j45;
                    long j49 = jArr18[3][i2];
                    long j50 = ~j49;
                    long j51 = jArr18[5][i2];
                    long j52 = ~j51;
                    long j53 = j48 & j50 & j52;
                    long j54 = ~j45;
                    long j55 = ~j44;
                    long j56 = ~j43;
                    long j57 = j56 & j50;
                    long j58 = jArr18[4][i2];
                    long j59 = ~j58;
                    jArr14[i2] = j47 | (j46 & (j53 | ((j54 | j55) & (j57 | (j43 & j59 & j52)))));
                    long j60 = (j52 | j59 | (j55 & j54)) & j49;
                    long j61 = j59 & j52;
                    long j62 = ~j47;
                    long j63 = ~j46;
                    jArr15[i2] = j60 | (j43 & (j61 | (j62 & (j55 | j63)) | (j46 & j49)));
                    long j64 = j44 & j50;
                    jArr16[i2] = (j58 & (((j49 | j43 | j63) & j62) | (j47 & (j51 | j50 | (j46 & j44))) | (j63 & j56 & j54))) | (j64 & j52) | (j62 & ((((j56 & j45) | j63) & j44) | (j46 & j56 & j54 & j49)));
                    long j65 = j55 & j59;
                    jArr17[i2] = (j47 & (((((j58 | j45) & j51) | (j55 & j45 & j49 & j58)) & j43) | ((j48 | (j56 & j54 & j59)) & j51) | ((j51 | ((j45 | j43) & j49 & j58)) & j63))) | (j62 & ((j45 & (j65 | ((j44 | j56) & j50))) | (j46 & ((j64 & j51) | (j43 & j49 & (j51 | (j44 & j54 & j59))))))) | (((((j43 & j44) | (j50 & j59)) & j46) | ((j65 | j57) & j63)) & j51);
                    long[] jArr21 = jArr13[6];
                    jArr21[i2] = jArr21[i2] & jArr20[i2];
                    long[] jArr22 = jArr13[7];
                    jArr22[i2] = jArr22[i2] & jArr19[i2];
                    i2++;
                    c2 = 6;
                    c = 7;
                    unconditionalFlowInfoUnconditionalInits = unconditionalFlowInfo2;
                    i = i10;
                    length = i9;
                    i6 = 2;
                    i7 = 8;
                    unconditionalFlowInfo = this;
                }
                for (i3 = i2; i3 < length; i3++) {
                    for (i4 = i6; i4 < i7; i4++) {
                        unconditionalFlowInfo.extra[i4][i3] = unconditionalFlowInfoUnconditionalInits.extra[i4][i3];
                    }
                }
            } else {
                int i11 = 0;
                if (unconditionalFlowInfoUnconditionalInits.extra != null) {
                    long[][] jArr23 = new long[8][];
                    unconditionalFlowInfo.extra = jArr23;
                    long[] jArr24 = unconditionalFlowInfoUnconditionalInits.extra[0];
                    int length3 = jArr24.length;
                    long[] jArr25 = new long[length3];
                    jArr23[0] = jArr25;
                    System.arraycopy(jArr24, 0, jArr25, 0, length3);
                    long[] jArr26 = unconditionalFlowInfoUnconditionalInits.extra[1];
                    long[] jArr27 = new long[length3];
                    unconditionalFlowInfo.extra[1] = jArr27;
                    System.arraycopy(jArr26, 0, jArr27, 0, length3);
                    if (z3) {
                        for (int i12 = 2; i12 < 8; i12++) {
                            long[] jArr28 = unconditionalFlowInfoUnconditionalInits.extra[i12];
                            long[] jArr29 = new long[length3];
                            unconditionalFlowInfo.extra[i12] = jArr29;
                            System.arraycopy(jArr28, 0, jArr29, 0, length3);
                        }
                    } else {
                        int i13 = 2;
                        while (i13 < 8) {
                            unconditionalFlowInfo.extra[i13] = new long[length3];
                            i13++;
                            i11 = 0;
                        }
                        System.arraycopy(unconditionalFlowInfoUnconditionalInits.extra[6], i11, unconditionalFlowInfo.extra[6], i11, length3);
                        System.arraycopy(unconditionalFlowInfoUnconditionalInits.extra[7], i11, unconditionalFlowInfo.extra[7], i11, length3);
                    }
                }
            }
            length = 0;
            i = 0;
            if (z) {
                i5 = 0;
                while (i5 < i) {
                    long[][] jArr30 = unconditionalFlowInfo.extra;
                    long[] jArr31 = jArr30[0];
                    long j310 = jArr31[i5];
                    long[][] jArr32 = unconditionalFlowInfoUnconditionalInits.extra;
                    jArr31[i5] = j310 | jArr32[0][i5];
                    long[] jArr33 = jArr30[1];
                    jArr33[i5] = jArr33[i5] | jArr32[1][i5];
                    i5++;
                }
                while (i5 < length) {
                    long[][] jArr110 = unconditionalFlowInfo.extra;
                    long[] jArr111 = jArr110[0];
                    long[][] jArr112 = unconditionalFlowInfoUnconditionalInits.extra;
                    jArr111[i5] = jArr112[0][i5];
                    jArr110[1][i5] = jArr112[1][i5];
                    i5++;
                }
            }
            i2 = 0;
            if (!z2) {
                if (length >= i) {
                    i = length;
                }
                length = i;
                i = 0;
            }
            if (!z3) {
                length = 0;
                i = 0;
            }
            while (i2 < i) {
                long[][] jArr113 = unconditionalFlowInfo.extra;
                long[] jArr114 = jArr113[i6];
                long j311 = jArr114[i2];
                long[] jArr115 = jArr113[3];
                long j312 = jArr115[i2];
                long[] jArr116 = jArr113[4];
                long j313 = jArr116[i2];
                long[] jArr117 = jArr113[5];
                long j314 = jArr117[i2];
                int i14 = length;
                long j315 = j311 & j312 & j313 & j314;
                long[][] jArr118 = unconditionalFlowInfoUnconditionalInits.extra;
                long[] jArr119 = jArr118[c];
                long j316 = jArr119[i2];
                long[] jArr210 = jArr118[c2];
                long j410 = jArr210[i2] | j315;
                long j411 = (~j315) | j316;
                long j412 = j311 & j411;
                long j413 = j411 & j410 & j312;
                long j414 = j411 & j316 & j313;
                long j415 = j314 & j411;
                long j416 = j412 & (j413 | j414 | j415);
                int i15 = i;
                UnconditionalFlowInfo unconditionalFlowInfo3 = unconditionalFlowInfoUnconditionalInits;
                long j417 = jArr118[i6][i2];
                long j418 = j414 & j415;
                long j419 = jArr118[3][i2];
                long j510 = ~j419;
                long j511 = jArr118[5][i2];
                long j512 = ~j511;
                long j513 = j418 & j510 & j512;
                long j514 = ~j415;
                long j515 = ~j414;
                long j516 = ~j413;
                long j517 = j516 & j510;
                long j518 = jArr118[4][i2];
                long j519 = ~j518;
                jArr114[i2] = j417 | (j416 & (j513 | ((j514 | j515) & (j517 | (j413 & j519 & j512)))));
                long j66 = (j512 | j519 | (j515 & j514)) & j419;
                long j67 = j519 & j512;
                long j68 = ~j417;
                long j69 = ~j416;
                jArr115[i2] = j66 | (j413 & (j67 | (j68 & (j515 | j69)) | (j416 & j419)));
                long j610 = j414 & j510;
                jArr116[i2] = (j518 & (((j419 | j413 | j69) & j68) | (j417 & (j511 | j510 | (j416 & j414))) | (j69 & j516 & j514))) | (j610 & j512) | (j68 & ((((j516 & j415) | j69) & j414) | (j416 & j516 & j514 & j419)));
                long j611 = j515 & j519;
                jArr117[i2] = (j417 & (((((j518 | j415) & j511) | (j515 & j415 & j419 & j518)) & j413) | ((j418 | (j516 & j514 & j519)) & j511) | ((j511 | ((j415 | j413) & j419 & j518)) & j69))) | (j68 & ((j415 & (j611 | ((j414 | j516) & j510))) | (j416 & ((j610 & j511) | (j413 & j419 & (j511 | (j414 & j514 & j519))))))) | (((((j413 & j414) | (j510 & j519)) & j416) | ((j611 | j517) & j69)) & j511);
                long[] jArr211 = jArr113[6];
                jArr211[i2] = jArr211[i2] & jArr210[i2];
                long[] jArr212 = jArr113[7];
                jArr212[i2] = jArr212[i2] & jArr119[i2];
                i2++;
                c2 = 6;
                c = 7;
                unconditionalFlowInfoUnconditionalInits = unconditionalFlowInfo3;
                i = i15;
                length = i14;
                i6 = 2;
                i7 = 8;
                unconditionalFlowInfo = this;
            }
            while (i3 < length) {
                while (i4 < i7) {
                    unconditionalFlowInfo.extra[i4][i3] = unconditionalFlowInfoUnconditionalInits.extra[i4][i3];
                }
            }
        }
        return unconditionalFlowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo addPotentialInitializationsFrom(FlowInfo flowInfo) {
        if (this == DEAD_END || flowInfo == DEAD_END) {
            return this;
        }
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = flowInfo.unconditionalInits();
        this.potentialInits |= unconditionalFlowInfoUnconditionalInits.potentialInits;
        long[][] jArr = this.extra;
        int i = 0;
        if (jArr != null) {
            long[][] jArr2 = unconditionalFlowInfoUnconditionalInits.extra;
            if (jArr2 != null) {
                int length = jArr[0].length;
                int length2 = jArr2[0].length;
                if (length < length2) {
                    for (int i2 = 0; i2 < 8; i2++) {
                        long[][] jArr3 = this.extra;
                        long[] jArr4 = jArr3[i2];
                        long[] jArr5 = new long[length2];
                        jArr3[i2] = jArr5;
                        System.arraycopy(jArr4, 0, jArr5, 0, length);
                    }
                    while (i < length) {
                        long[] jArr6 = this.extra[1];
                        jArr6[i] = jArr6[i] | unconditionalFlowInfoUnconditionalInits.extra[1][i];
                        i++;
                    }
                    while (i < length2) {
                        this.extra[1][i] = unconditionalFlowInfoUnconditionalInits.extra[1][i];
                        i++;
                    }
                } else {
                    while (i < length2) {
                        long[] jArr7 = this.extra[1];
                        jArr7[i] = jArr7[i] | unconditionalFlowInfoUnconditionalInits.extra[1][i];
                        i++;
                    }
                }
            }
        } else {
            long[][] jArr8 = unconditionalFlowInfoUnconditionalInits.extra;
            if (jArr8 != null) {
                int length3 = jArr8[0].length;
                createExtraSpace(length3);
                System.arraycopy(unconditionalFlowInfoUnconditionalInits.extra[1], 0, this.extra[1], 0, length3);
            }
        }
        addPotentialNullInfoFrom(unconditionalFlowInfoUnconditionalInits);
        return this;
    }

    public UnconditionalFlowInfo addPotentialNullInfoFrom(UnconditionalFlowInfo unconditionalFlowInfo) {
        boolean z;
        UnconditionalFlowInfo unconditionalFlowInfo2;
        boolean z2;
        int i;
        int length;
        char c;
        if ((this.tagBits & 3) == 0 && (unconditionalFlowInfo.tagBits & 3) == 0 && (unconditionalFlowInfo.tagBits & 4) != 0) {
            boolean z3 = (this.tagBits & 4) != 0;
            if (z3) {
                long j = this.nullBit1;
                long j2 = this.nullBit3;
                long j3 = this.nullBit4;
                long j4 = unconditionalFlowInfo.nullBit2;
                long j5 = ~j4;
                long j6 = unconditionalFlowInfo.nullBit4;
                long j7 = ~j6;
                long j8 = j5 & j7;
                long j9 = unconditionalFlowInfo.nullBit1;
                long j10 = unconditionalFlowInfo.nullBit3;
                long j11 = j9 & j10;
                long j12 = j2 & j3 & (j8 | j11);
                long j13 = this.nullBit2;
                z = z3;
                long j14 = ~j13;
                long j15 = ~j3;
                long j16 = ~j2;
                long j17 = j15 | j16;
                long j18 = ~j10;
                long j19 = j18 & j7;
                long j20 = j9 & j4;
                this.nullBit1 = j & (j12 | (j14 & (j11 | (j17 & j5))) | (j13 & j17 & (j19 | j20)));
                long j21 = ~j9;
                long j22 = j4 & (j18 | j21);
                long j23 = ~j;
                long j24 = j22 | (j13 & (j19 | j4 | j16 | j23));
                this.nullBit2 = j24;
                long j25 = j & j14 & j15;
                long j26 = ((j8 | (j14 & j3) | j23) & j2) | (j10 & ((j21 & j4) | (j13 & (j5 | j2)) | (j23 & j5) | (j25 & j9))) | (j25 & j4);
                this.nullBit3 = j26;
                long j27 = j16 & ((j21 & j18 & j6) | (j3 & (j18 | j20)));
                long j28 = j21 & j6;
                long j29 = (j5 & ((j16 & j9 & j18) | (j14 & (j28 | (j9 & j18) | j3)))) | j27 | ((((j28 | (j15 & (j4 | j9) & j18)) & j13 & j) | (j3 & (j5 | j11))) & j2);
                this.nullBit4 = j29;
                if ((j29 | j26 | j24) != 0) {
                    unconditionalFlowInfo2 = unconditionalFlowInfo;
                } else {
                    unconditionalFlowInfo2 = unconditionalFlowInfo;
                }
            } else {
                z = z3;
                this.nullBit1 = 0L;
                unconditionalFlowInfo2 = unconditionalFlowInfo;
                long j30 = unconditionalFlowInfo2.nullBit2;
                long j31 = unconditionalFlowInfo2.nullBit3;
                long j32 = ~j31;
                long j33 = unconditionalFlowInfo2.nullBit1;
                long j34 = ~j33;
                long j35 = (j32 | j34) & j30;
                this.nullBit2 = j35;
                long j36 = ~j30;
                long j37 = j31 & (j34 | j36);
                this.nullBit3 = j37;
                long j38 = unconditionalFlowInfo2.nullBit4;
                long j39 = (j36 & ((j38 & j34) | (j32 & j33))) | (j34 & j32 & j38);
                this.nullBit4 = j39;
                z2 = (j39 | (j35 | j37)) != 0;
            }
            long[][] jArr = unconditionalFlowInfo2.extra;
            if (jArr != null) {
                int length2 = jArr[0].length;
                long[][] jArr2 = this.extra;
                if (jArr2 == null) {
                    createExtraSpace(length2);
                    length = 0;
                    i = 0;
                } else {
                    long[] jArr3 = jArr2[0];
                    if (length2 > jArr3.length) {
                        length = jArr3.length;
                        for (int i2 = 0; i2 < 8; i2++) {
                            long[][] jArr4 = this.extra;
                            long[] jArr5 = jArr4[i2];
                            long[] jArr6 = new long[length2];
                            jArr4[i2] = jArr6;
                            System.arraycopy(jArr5, 0, jArr6, 0, length);
                        }
                        if (!z) {
                            length = 0;
                        }
                        i = 0;
                    } else {
                        i = 0;
                        length = length2;
                    }
                }
                while (true) {
                    c = 2;
                    if (i >= length) {
                        break;
                    }
                    long[][] jArr7 = this.extra;
                    long[] jArr8 = jArr7[2];
                    long j40 = jArr8[i];
                    long[] jArr9 = jArr7[4];
                    long j41 = jArr9[i];
                    long[] jArr10 = jArr7[5];
                    long j42 = jArr10[i];
                    long j43 = j41 & j42;
                    long[][] jArr11 = unconditionalFlowInfo2.extra;
                    long j44 = jArr11[3][i];
                    boolean z4 = z2;
                    long j45 = ~j44;
                    int i3 = length2;
                    int i4 = length;
                    long j46 = jArr11[5][i];
                    long j47 = ~j46;
                    long j48 = j45 & j47;
                    long j49 = jArr11[2][i];
                    long j50 = jArr11[4][i];
                    long j51 = j49 & j50;
                    long[] jArr12 = jArr7[3];
                    long j52 = jArr12[i];
                    int i5 = i;
                    long j53 = ~j52;
                    long j54 = ~j42;
                    long j55 = ~j41;
                    long j56 = j54 | j55;
                    long j57 = ~j50;
                    long j58 = j47 & j57;
                    long j59 = j49 & j44;
                    jArr8[i5] = j40 & ((j43 & (j48 | j51)) | (j53 & (j51 | (j56 & j45))) | (j52 & j56 & (j58 | j59)));
                    long j60 = ~j49;
                    long j61 = j44 & (j57 | j60);
                    long j62 = ~j40;
                    jArr12[i5] = j61 | (j52 & (j58 | j44 | j55 | j62));
                    long j63 = j40 & j53 & j54;
                    jArr9[i5] = ((j48 | (j53 & j42) | j62) & j41) | (j50 & ((j60 & j44) | (j52 & (j45 | j41)) | (j62 & j45) | (j63 & j49))) | (j63 & j44);
                    long j64 = j55 & ((j60 & j57 & j46) | (j42 & (j57 | j59)));
                    long j65 = j60 & j46;
                    long j66 = (((j42 & (j45 | j51)) | ((j65 | (j54 & (j44 | j49) & j57)) & j40 & j52)) & j41) | j64 | (((j55 & j49 & j57) | (j53 & (j65 | (j49 & j57) | j42))) & j45);
                    jArr10[i5] = j66;
                    z2 = (j66 | (jArr12[i5] | jArr9[i5])) != 0 ? true : z4;
                    i = i5 + 1;
                    unconditionalFlowInfo2 = unconditionalFlowInfo;
                    length2 = i3;
                    length = i4;
                }
                while (i < length2) {
                    long[][] jArr13 = this.extra;
                    jArr13[c][i] = 0;
                    long[] jArr14 = jArr13[3];
                    long[][] jArr15 = unconditionalFlowInfo2.extra;
                    long j67 = jArr15[3][i];
                    long j68 = jArr15[4][i];
                    long j69 = ~j68;
                    long j70 = jArr15[c][i];
                    int i6 = i;
                    long j71 = ~j70;
                    jArr14[i6] = j67 & (j69 | j71);
                    long[] jArr16 = jArr13[4];
                    long j72 = ~j67;
                    jArr16[i6] = j68 & (j71 | j72);
                    long[] jArr17 = jArr13[5];
                    long j73 = jArr15[5][i6];
                    long j74 = (j71 & j69 & j73) | (((j71 & j73) | (j70 & j69)) & j72);
                    jArr17[i6] = j74;
                    if ((jArr14[i6] | jArr16[i6] | j74) != 0) {
                        z2 = true;
                    }
                    i = i6 + 1;
                    c = 2;
                }
            }
            if (z2) {
                this.tagBits |= 4;
            } else {
                this.tagBits &= 4;
            }
        }
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean cannotBeDefinitelyNullOrNonNull(LocalVariableBinding localVariableBinding) {
        int i;
        if ((this.tagBits & 4) != 0 && (localVariableBinding.type.tagBits & 2) == 0) {
            int i2 = localVariableBinding.f627id + this.maxFieldCount;
            if (i2 < 64) {
                long j = ~this.nullBit1;
                long j2 = this.nullBit2;
                long j3 = this.nullBit3;
                long j4 = this.nullBit4;
                return (((j4 & ((~j2) & (~j3))) | (j & ((j2 & j3) | j4))) & (1 << i2)) != 0;
            }
            long[][] jArr = this.extra;
            if (jArr == null || (i = (i2 / 64) - 1) >= jArr[0].length) {
                return false;
            }
            long j5 = ~jArr[2][i];
            long j6 = jArr[3][i];
            long j7 = jArr[4][i];
            long j8 = jArr[5][i];
            if ((((((j6 & j7) | j8) & j5) | ((~j7) & (~j6) & j8)) & (1 << (i2 % 64))) != 0) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean cannotBeNull(LocalVariableBinding localVariableBinding) {
        int i;
        if ((this.tagBits & 4) != 0 && (localVariableBinding.type.tagBits & 2) == 0) {
            int i2 = localVariableBinding.f627id + this.maxFieldCount;
            if (i2 < 64) {
                long j = this.nullBit1 & this.nullBit3;
                long j2 = this.nullBit2;
                return ((1 << i2) & (j & ((~j2) | (this.nullBit4 & j2)))) != 0;
            }
            long[][] jArr = this.extra;
            if (jArr == null || (i = (i2 / 64) - 1) >= jArr[0].length) {
                return false;
            }
            long j3 = jArr[2][i] & jArr[4][i];
            long j4 = jArr[3][i];
            if (((1 << (i2 % 64)) & ((jArr[5][i] & j4) | (~j4)) & j3) != 0) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean canOnlyBeNull(LocalVariableBinding localVariableBinding) {
        int i;
        if ((this.tagBits & 4) != 0 && (localVariableBinding.type.tagBits & 2) == 0) {
            int i2 = localVariableBinding.f627id + this.maxFieldCount;
            if (i2 < 64) {
                return ((1 << i2) & ((this.nullBit1 & this.nullBit2) & ((~this.nullBit3) | (~this.nullBit4)))) != 0;
            }
            long[][] jArr = this.extra;
            if (jArr == null || (i = (i2 / 64) - 1) >= jArr[0].length) {
                return false;
            }
            if (((1 << (i2 % 64)) & ((~jArr[5][i]) | (~jArr[4][i])) & jArr[2][i] & jArr[3][i]) != 0) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo copy() {
        if (this == DEAD_END) {
            return this;
        }
        UnconditionalFlowInfo unconditionalFlowInfo = new UnconditionalFlowInfo();
        unconditionalFlowInfo.definiteInits = this.definiteInits;
        unconditionalFlowInfo.potentialInits = this.potentialInits;
        boolean z = (this.tagBits & 4) != 0;
        if (z) {
            unconditionalFlowInfo.nullBit1 = this.nullBit1;
            unconditionalFlowInfo.nullBit2 = this.nullBit2;
            unconditionalFlowInfo.nullBit3 = this.nullBit3;
            unconditionalFlowInfo.nullBit4 = this.nullBit4;
        }
        unconditionalFlowInfo.iNBit = this.iNBit;
        unconditionalFlowInfo.iNNBit = this.iNNBit;
        unconditionalFlowInfo.tagBits = this.tagBits;
        unconditionalFlowInfo.maxFieldCount = this.maxFieldCount;
        if (this.extra != null) {
            long[][] jArr = new long[8][];
            unconditionalFlowInfo.extra = jArr;
            long[] jArr2 = this.extra[0];
            int length = jArr2.length;
            long[] jArr3 = new long[length];
            jArr[0] = jArr3;
            System.arraycopy(jArr2, 0, jArr3, 0, length);
            long[] jArr4 = this.extra[1];
            long[] jArr5 = new long[length];
            unconditionalFlowInfo.extra[1] = jArr5;
            System.arraycopy(jArr4, 0, jArr5, 0, length);
            int i = 2;
            if (z) {
                while (i < 8) {
                    long[] jArr6 = this.extra[i];
                    long[] jArr7 = new long[length];
                    unconditionalFlowInfo.extra[i] = jArr7;
                    System.arraycopy(jArr6, 0, jArr7, 0, length);
                    i++;
                }
            } else {
                while (i < 8) {
                    unconditionalFlowInfo.extra[i] = new long[length];
                    i++;
                }
            }
        }
        return unconditionalFlowInfo;
    }

    public UnconditionalFlowInfo discardInitializationInfo() {
        if (this == DEAD_END) {
            return this;
        }
        this.potentialInits = 0L;
        this.definiteInits = 0L;
        long[][] jArr = this.extra;
        if (jArr != null) {
            int length = jArr[0].length;
            for (int i = 0; i < length; i++) {
                long[][] jArr2 = this.extra;
                long[] jArr3 = jArr2[0];
                jArr2[1][i] = 0;
                jArr3[i] = 0;
            }
        }
        return this;
    }

    public UnconditionalFlowInfo discardNonFieldInitializations() {
        int length;
        int i;
        int i2;
        int i3 = this.maxFieldCount;
        if (i3 < 64) {
            long j = (1 << i3) - 1;
            this.definiteInits &= j;
            this.potentialInits &= j;
            this.nullBit1 &= j;
            this.nullBit2 &= j;
            this.nullBit3 &= j;
            this.nullBit4 &= j;
            this.iNBit &= j;
            this.iNNBit = j & this.iNNBit;
        }
        long[][] jArr = this.extra;
        if (jArr == null || (i2 = (i = i3 / 64) - 1) >= (length = jArr[0].length)) {
            return this;
        }
        if (i2 >= 0) {
            long j2 = (1 << (i3 % 64)) - 1;
            for (int i4 = 0; i4 < 8; i4++) {
                long[] jArr2 = this.extra[i4];
                jArr2[i2] = jArr2[i2] & j2;
            }
        }
        for (i = i3 / 64; i < length; i++) {
            for (int i5 = 0; i5 < 8; i5++) {
                this.extra[i5][i] = 0;
            }
        }
        return this;
    }

    private final boolean isDefinitelyAssigned(int i) {
        if (i < 64) {
            return ((1 << i) & this.definiteInits) != 0;
        }
        long[][] jArr = this.extra;
        if (jArr == null) {
            return false;
        }
        int i2 = (i / 64) - 1;
        long[] jArr2 = jArr[0];
        if (i2 >= jArr2.length) {
            return false;
        }
        return ((1 << (i % 64)) & jArr2[i2]) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isDefinitelyAssigned(FieldBinding fieldBinding) {
        if ((this.tagBits & 1) != 0) {
            return true;
        }
        return isDefinitelyAssigned(fieldBinding.f627id);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isDefinitelyAssigned(LocalVariableBinding localVariableBinding) {
        if ((this.tagBits & 1) == 0 || (localVariableBinding.declaration.bits & 1073741824) == 0) {
            return isDefinitelyAssigned(localVariableBinding.f627id + this.maxFieldCount);
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isDefinitelyNonNull(LocalVariableBinding localVariableBinding) {
        if ((this.tagBits & 3) != 0 || (this.tagBits & 4) == 0) {
            return false;
        }
        if ((localVariableBinding.type.tagBits & 2) != 0 || localVariableBinding.constant() != Constant.NotAConstant) {
            return true;
        }
        int i = localVariableBinding.f627id + this.maxFieldCount;
        if (i < 64) {
            return (((this.nullBit1 & this.nullBit3) & ((~this.nullBit2) | this.nullBit4)) & (1 << i)) != 0;
        }
        long[][] jArr = this.extra;
        if (jArr == null) {
            return false;
        }
        int i2 = (i / 64) - 1;
        long[] jArr2 = jArr[2];
        if (i2 >= jArr2.length) {
            return false;
        }
        return ((1 << (i % 64)) & (((~jArr[3][i2]) | jArr[5][i2]) & (jArr2[i2] & jArr[4][i2]))) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isDefinitelyNull(LocalVariableBinding localVariableBinding) {
        if ((this.tagBits & 3) != 0 || (this.tagBits & 4) == 0 || (localVariableBinding.type.tagBits & 2) != 0) {
            return false;
        }
        int i = localVariableBinding.f627id + this.maxFieldCount;
        if (i < 64) {
            return ((1 << i) & ((this.nullBit1 & this.nullBit2) & ((~this.nullBit3) | (~this.nullBit4)))) != 0;
        }
        long[][] jArr = this.extra;
        if (jArr == null) {
            return false;
        }
        int i2 = (i / 64) - 1;
        long[] jArr2 = jArr[2];
        if (i2 >= jArr2.length) {
            return false;
        }
        return ((((~jArr[4][i2]) | (~jArr[5][i2])) & (jArr2[i2] & jArr[3][i2])) & (1 << (i % 64))) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isDefinitelyUnknown(LocalVariableBinding localVariableBinding) {
        if ((this.tagBits & 3) == 0 && (this.tagBits & 4) != 0) {
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                return ((1 << i) & (((this.nullBit1 & this.nullBit4) & (~this.nullBit2)) & (~this.nullBit3))) != 0;
            }
            long[][] jArr = this.extra;
            if (jArr == null) {
                return false;
            }
            int i2 = (i / 64) - 1;
            long[] jArr2 = jArr[2];
            if (i2 >= jArr2.length) {
                return false;
            }
            if (((1 << (i % 64)) & (~jArr[3][i2]) & jArr2[i2] & jArr[5][i2] & (~jArr[4][i2])) != 0) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean hasNullInfoFor(LocalVariableBinding localVariableBinding) {
        if ((this.tagBits & 3) == 0 && (this.tagBits & 4) != 0) {
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                return ((1 << i) & (((this.nullBit1 | this.nullBit2) | this.nullBit3) | this.nullBit4)) != 0;
            }
            long[][] jArr = this.extra;
            if (jArr == null) {
                return false;
            }
            int i2 = (i / 64) - 1;
            long[] jArr2 = jArr[2];
            if (i2 >= jArr2.length) {
                return false;
            }
            if (((1 << (i % 64)) & (jArr[5][i2] | jArr2[i2] | jArr[3][i2] | jArr[4][i2])) != 0) {
                return true;
            }
        }
        return false;
    }

    private final boolean isPotentiallyAssigned(int i) {
        int i2;
        if (i < 64) {
            return ((1 << i) & this.potentialInits) != 0;
        }
        long[][] jArr = this.extra;
        if (jArr != null && (i2 = (i / 64) - 1) < jArr[0].length) {
            return ((1 << (i % 64)) & jArr[1][i2]) != 0;
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isPotentiallyAssigned(FieldBinding fieldBinding) {
        return isPotentiallyAssigned(fieldBinding.f627id);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isPotentiallyAssigned(LocalVariableBinding localVariableBinding) {
        if (localVariableBinding.constant() != Constant.NotAConstant) {
            return true;
        }
        return isPotentiallyAssigned(localVariableBinding.f627id + this.maxFieldCount);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isPotentiallyNonNull(LocalVariableBinding localVariableBinding) {
        if ((this.tagBits & 4) != 0 && (localVariableBinding.type.tagBits & 2) == 0) {
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                return ((1 << i) & (this.nullBit3 & ((~this.nullBit1) | (~this.nullBit2)))) != 0;
            }
            long[][] jArr = this.extra;
            if (jArr == null) {
                return false;
            }
            int i2 = (i / 64) - 1;
            long[] jArr2 = jArr[2];
            if (i2 >= jArr2.length) {
                return false;
            }
            if ((((~jArr2[i2]) | (~jArr[3][i2])) & jArr[4][i2] & (1 << (i % 64))) != 0) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isPotentiallyNull(LocalVariableBinding localVariableBinding) {
        if ((this.tagBits & 4) != 0 && (localVariableBinding.type.tagBits & 2) == 0) {
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                return ((1 << i) & (this.nullBit2 & ((~this.nullBit1) | (~this.nullBit3)))) != 0;
            }
            long[][] jArr = this.extra;
            if (jArr == null) {
                return false;
            }
            int i2 = (i / 64) - 1;
            long[] jArr2 = jArr[2];
            if (i2 >= jArr2.length) {
                return false;
            }
            if (((1 << (i % 64)) & ((~jArr2[i2]) | (~jArr[4][i2])) & jArr[3][i2]) != 0) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isPotentiallyUnknown(LocalVariableBinding localVariableBinding) {
        if ((this.tagBits & 3) != 0 || (this.tagBits & 4) == 0) {
            return false;
        }
        int i = localVariableBinding.f627id + this.maxFieldCount;
        if (i < 64) {
            long j = this.nullBit4;
            return (((((~this.nullBit3) & (~this.nullBit2)) | (~this.nullBit1)) & j) & (1 << i)) != 0;
        }
        long[][] jArr = this.extra;
        if (jArr == null) {
            return false;
        }
        int i2 = (i / 64) - 1;
        long[] jArr2 = jArr[2];
        if (i2 >= jArr2.length) {
            return false;
        }
        return (((((~jArr[3][i2]) & (~jArr[4][i2])) | (~jArr2[i2])) & jArr[5][i2]) & (1 << (i % 64))) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isProtectedNonNull(LocalVariableBinding localVariableBinding) {
        int i;
        if ((this.tagBits & 4) != 0 && (localVariableBinding.type.tagBits & 2) == 0) {
            int i2 = localVariableBinding.f627id + this.maxFieldCount;
            if (i2 < 64) {
                return ((1 << i2) & ((this.nullBit1 & this.nullBit3) & this.nullBit4)) != 0;
            }
            long[][] jArr = this.extra;
            if (jArr == null || (i = (i2 / 64) - 1) >= jArr[0].length) {
                return false;
            }
            if (((1 << (i2 % 64)) & jArr[5][i] & jArr[2][i] & jArr[4][i]) != 0) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public final boolean isProtectedNull(LocalVariableBinding localVariableBinding) {
        int i;
        if ((this.tagBits & 4) != 0 && (localVariableBinding.type.tagBits & 2) == 0) {
            int i2 = localVariableBinding.f627id + this.maxFieldCount;
            if (i2 < 64) {
                return ((1 << i2) & ((this.nullBit1 & this.nullBit2) & (this.nullBit3 ^ this.nullBit4))) != 0;
            }
            long[][] jArr = this.extra;
            if (jArr == null || (i = (i2 / 64) - 1) >= jArr[0].length) {
                return false;
            }
            if (((1 << (i2 % 64)) & (jArr[5][i] ^ jArr[4][i]) & jArr[2][i] & jArr[3][i]) != 0) {
                return true;
            }
        }
        return false;
    }

    protected static boolean isTrue(boolean z, String str) {
        if (z) {
            return z;
        }
        throw new AssertionFailedException("assertion failed: " + str);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsComparedEqualToNonNull(LocalVariableBinding localVariableBinding) {
        if (this != DEAD_END) {
            this.tagBits |= 4;
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                long j = 1 << i;
                long j2 = this.nullBit1;
                long j3 = j & j2;
                long j4 = this.nullBit2;
                long j5 = (~j4) & j3;
                long j6 = this.nullBit3;
                long j7 = j5 & (~j6);
                long j8 = this.nullBit4;
                if ((j7 & j8) != 0) {
                    this.nullBit4 = j8 & (~j);
                } else if ((j5 & j6) == 0) {
                    this.nullBit4 = j8 | j;
                    if (j3 == 0) {
                        if ((j & j4 & (j6 ^ j8)) != 0) {
                            this.nullBit2 = (~j) & j4;
                        } else if (((j8 | j4 | j6) & j) == 0) {
                            this.nullBit2 = j4 | j;
                        }
                    }
                }
                this.nullBit1 = j2 | j;
                this.nullBit3 = j6 | j;
                this.iNBit = (~j) & this.iNBit;
                return;
            }
            int i2 = i / 64;
            int i3 = i2 - 1;
            long[][] jArr = this.extra;
            if (jArr == null) {
                createExtraSpace(i2);
            } else {
                int length = jArr[0].length;
                if (i3 >= length) {
                    for (int i4 = 0; i4 < 8; i4++) {
                        long[][] jArr2 = this.extra;
                        long[] jArr3 = jArr2[i4];
                        long[] jArr4 = new long[i2];
                        jArr2[i4] = jArr4;
                        System.arraycopy(jArr3, 0, jArr4, 0, length);
                    }
                }
            }
            long j9 = 1 << (i % 64);
            long[][] jArr5 = this.extra;
            long[] jArr6 = jArr5[2];
            long j10 = jArr6[i3] & j9;
            long[] jArr7 = jArr5[3];
            long j11 = jArr7[i3];
            long j12 = (~j11) & j10;
            long[] jArr8 = jArr5[4];
            long j13 = jArr8[i3];
            long j14 = (~j13) & j12;
            long[] jArr9 = jArr5[5];
            long j15 = jArr9[i3];
            if ((j14 & j15) != 0) {
                jArr9[i3] = j15 & (~j9);
            } else if ((j12 & j13) == 0) {
                jArr9[i3] = j15 | j9;
                if (j10 == 0) {
                    if ((j9 & j11 & (j13 ^ j15)) != 0) {
                        jArr7[i3] = jArr7[i3] & (~j9);
                    } else if (((j11 | j13 | j15) & j9) == 0) {
                        jArr7[i3] = jArr7[i3] | j9;
                    }
                }
            }
            jArr6[i3] = jArr6[i3] | j9;
            jArr8[i3] = jArr8[i3] | j9;
            long[] jArr10 = jArr5[6];
            jArr10[i3] = (~j9) & jArr10[i3];
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsComparedEqualToNull(LocalVariableBinding localVariableBinding) {
        if (this != DEAD_END) {
            this.tagBits |= 4;
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                long j = 1 << i;
                long j2 = this.nullBit1;
                if ((j & j2) != 0) {
                    long j3 = (~this.nullBit2) | this.nullBit3;
                    long j4 = this.nullBit4;
                    if (((j3 | (~j4)) & j) != 0) {
                        this.nullBit4 = (~j) & j4;
                    }
                } else {
                    long j5 = this.nullBit4;
                    if ((j & j5) != 0) {
                        this.nullBit3 &= ~j;
                    } else if ((this.nullBit2 & j) != 0) {
                        this.nullBit3 &= ~j;
                        this.nullBit4 = j5 | j;
                    } else {
                        this.nullBit3 |= j;
                    }
                }
                this.nullBit1 = j2 | j;
                this.nullBit2 |= j;
                this.iNNBit = (~j) & this.iNNBit;
                return;
            }
            int i2 = i / 64;
            int i3 = i2 - 1;
            long j6 = 1 << (i % 64);
            long[][] jArr = this.extra;
            if (jArr == null) {
                createExtraSpace(i2);
            } else {
                int length = jArr[0].length;
                if (i3 >= length) {
                    for (int i4 = 0; i4 < 8; i4++) {
                        long[][] jArr2 = this.extra;
                        long[] jArr3 = jArr2[i4];
                        long[] jArr4 = new long[i2];
                        jArr2[i4] = jArr4;
                        System.arraycopy(jArr3, 0, jArr4, 0, length);
                    }
                }
            }
            long[][] jArr5 = this.extra;
            long[] jArr6 = jArr5[2];
            if ((jArr6[i3] & j6) != 0) {
                long j7 = (~jArr5[3][i3]) | jArr5[4][i3];
                long[] jArr7 = jArr5[5];
                long j8 = jArr7[i3];
                if ((((~j8) | j7) & j6) != 0) {
                    jArr7[i3] = (~j6) & j8;
                }
            } else {
                long[] jArr8 = jArr5[5];
                if ((jArr8[i3] & j6) != 0) {
                    long[] jArr9 = jArr5[4];
                    jArr9[i3] = jArr9[i3] & (~j6);
                } else if ((j6 & jArr5[3][i3]) != 0) {
                    long[] jArr10 = jArr5[4];
                    jArr10[i3] = jArr10[i3] & (~j6);
                    jArr8[i3] = jArr8[i3] | j6;
                } else {
                    long[] jArr11 = jArr5[4];
                    jArr11[i3] = jArr11[i3] | j6;
                }
            }
            jArr6[i3] = jArr6[i3] | j6;
            long[] jArr12 = jArr5[3];
            jArr12[i3] = jArr12[i3] | j6;
            long[] jArr13 = jArr5[7];
            jArr13[i3] = (~j6) & jArr13[i3];
        }
    }

    private final void markAsDefinitelyAssigned(int i) {
        if (this != DEAD_END) {
            if (i < 64) {
                long j = 1 << i;
                this.definiteInits |= j;
                this.potentialInits = j | this.potentialInits;
                return;
            }
            int i2 = i / 64;
            int i3 = i2 - 1;
            long[][] jArr = this.extra;
            if (jArr == null) {
                createExtraSpace(i2);
            } else {
                int length = jArr[0].length;
                if (i3 >= length) {
                    for (int i4 = 0; i4 < 8; i4++) {
                        long[][] jArr2 = this.extra;
                        long[] jArr3 = jArr2[i4];
                        long[] jArr4 = new long[i2];
                        jArr2[i4] = jArr4;
                        System.arraycopy(jArr3, 0, jArr4, 0, length);
                    }
                }
            }
            long[][] jArr5 = this.extra;
            long[] jArr6 = jArr5[0];
            long j2 = 1 << (i % 64);
            jArr6[i3] = jArr6[i3] | j2;
            long[] jArr7 = jArr5[1];
            jArr7[i3] = j2 | jArr7[i3];
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsDefinitelyAssigned(FieldBinding fieldBinding) {
        if (this != DEAD_END) {
            markAsDefinitelyAssigned(fieldBinding.f627id);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsDefinitelyAssigned(LocalVariableBinding localVariableBinding) {
        if (this != DEAD_END) {
            markAsDefinitelyAssigned(localVariableBinding.f627id + this.maxFieldCount);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsDefinitelyNonNull(LocalVariableBinding localVariableBinding) {
        if (this != DEAD_END) {
            this.tagBits |= 4;
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                long j = 1 << i;
                this.nullBit1 |= j;
                this.nullBit3 |= j;
                long j2 = this.nullBit2;
                long j3 = ~j;
                this.nullBit2 = j2 & j3;
                this.nullBit4 &= j3;
                this.iNBit &= j3;
                this.iNNBit &= j3;
                return;
            }
            int i2 = i / 64;
            int i3 = i2 - 1;
            long[][] jArr = this.extra;
            if (jArr == null) {
                createExtraSpace(i2);
            } else {
                int length = jArr[0].length;
                if (i3 >= length) {
                    for (int i4 = 0; i4 < 8; i4++) {
                        long[][] jArr2 = this.extra;
                        long[] jArr3 = jArr2[i4];
                        long[] jArr4 = new long[i2];
                        jArr2[i4] = jArr4;
                        System.arraycopy(jArr3, 0, jArr4, 0, length);
                    }
                }
            }
            long[][] jArr5 = this.extra;
            long[] jArr6 = jArr5[2];
            long j4 = 1 << (i % 64);
            jArr6[i3] = jArr6[i3] | j4;
            long[] jArr7 = jArr5[4];
            jArr7[i3] = jArr7[i3] | j4;
            long[] jArr8 = jArr5[3];
            long j5 = jArr8[i3];
            long j6 = ~j4;
            jArr8[i3] = j5 & j6;
            long[] jArr9 = jArr5[5];
            jArr9[i3] = jArr9[i3] & j6;
            long[] jArr10 = jArr5[6];
            jArr10[i3] = jArr10[i3] & j6;
            long[] jArr11 = jArr5[7];
            jArr11[i3] = jArr11[i3] & j6;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsDefinitelyNull(LocalVariableBinding localVariableBinding) {
        if (this != DEAD_END) {
            this.tagBits |= 4;
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                long j = 1 << i;
                this.nullBit1 |= j;
                this.nullBit2 |= j;
                long j2 = this.nullBit3;
                long j3 = ~j;
                this.nullBit3 = j2 & j3;
                this.nullBit4 &= j3;
                this.iNBit &= j3;
                this.iNNBit &= j3;
                return;
            }
            int i2 = i / 64;
            int i3 = i2 - 1;
            long[][] jArr = this.extra;
            if (jArr == null) {
                createExtraSpace(i2);
            } else {
                int length = jArr[0].length;
                if (i3 >= length) {
                    for (int i4 = 0; i4 < 8; i4++) {
                        long[][] jArr2 = this.extra;
                        long[] jArr3 = jArr2[i4];
                        long[] jArr4 = new long[i2];
                        jArr2[i4] = jArr4;
                        System.arraycopy(jArr3, 0, jArr4, 0, length);
                    }
                }
            }
            long[][] jArr5 = this.extra;
            long[] jArr6 = jArr5[2];
            long j4 = 1 << (i % 64);
            jArr6[i3] = jArr6[i3] | j4;
            long[] jArr7 = jArr5[3];
            jArr7[i3] = jArr7[i3] | j4;
            long[] jArr8 = jArr5[4];
            long j5 = jArr8[i3];
            long j6 = ~j4;
            jArr8[i3] = j5 & j6;
            long[] jArr9 = jArr5[5];
            jArr9[i3] = jArr9[i3] & j6;
            long[] jArr10 = jArr5[6];
            jArr10[i3] = jArr10[i3] & j6;
            long[] jArr11 = jArr5[7];
            jArr11[i3] = jArr11[i3] & j6;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsDefinitelyUnknown(LocalVariableBinding localVariableBinding) {
        if (this != DEAD_END) {
            this.tagBits |= 4;
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                long j = 1 << i;
                this.nullBit1 |= j;
                this.nullBit4 |= j;
                long j2 = this.nullBit2;
                long j3 = ~j;
                this.nullBit2 = j2 & j3;
                this.nullBit3 &= j3;
                this.iNBit &= j3;
                this.iNNBit &= j3;
                return;
            }
            int i2 = i / 64;
            int i3 = i2 - 1;
            long[][] jArr = this.extra;
            if (jArr == null) {
                createExtraSpace(i2);
            } else {
                int length = jArr[0].length;
                if (i3 >= length) {
                    for (int i4 = 0; i4 < 8; i4++) {
                        long[][] jArr2 = this.extra;
                        long[] jArr3 = jArr2[i4];
                        long[] jArr4 = new long[i2];
                        jArr2[i4] = jArr4;
                        System.arraycopy(jArr3, 0, jArr4, 0, length);
                    }
                }
            }
            long[][] jArr5 = this.extra;
            long[] jArr6 = jArr5[2];
            long j4 = 1 << (i % 64);
            jArr6[i3] = jArr6[i3] | j4;
            long[] jArr7 = jArr5[5];
            jArr7[i3] = jArr7[i3] | j4;
            long[] jArr8 = jArr5[3];
            long j5 = jArr8[i3];
            long j6 = ~j4;
            jArr8[i3] = j5 & j6;
            long[] jArr9 = jArr5[4];
            jArr9[i3] = jArr9[i3] & j6;
            long[] jArr10 = jArr5[6];
            jArr10[i3] = jArr10[i3] & j6;
            long[] jArr11 = jArr5[7];
            jArr11[i3] = jArr11[i3] & j6;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void resetNullInfo(LocalVariableBinding localVariableBinding) {
        if (this != DEAD_END) {
            this.tagBits |= 4;
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                long j = this.nullBit1;
                long j2 = ~(1 << i);
                this.nullBit1 = j & j2;
                this.nullBit2 &= j2;
                this.nullBit3 &= j2;
                this.nullBit4 &= j2;
                this.iNBit &= j2;
                this.iNNBit &= j2;
                return;
            }
            int i2 = (i / 64) - 1;
            long[][] jArr = this.extra;
            if (jArr != null) {
                long[] jArr2 = jArr[2];
                if (i2 >= jArr2.length) {
                    return;
                }
                long j3 = jArr2[i2];
                long j4 = ~(1 << (i % 64));
                jArr2[i2] = j3 & j4;
                long[] jArr3 = jArr[3];
                jArr3[i2] = jArr3[i2] & j4;
                long[] jArr4 = jArr[4];
                jArr4[i2] = jArr4[i2] & j4;
                long[] jArr5 = jArr[5];
                jArr5[i2] = jArr5[i2] & j4;
                long[] jArr6 = jArr[6];
                jArr6[i2] = jArr6[i2] & j4;
                long[] jArr7 = jArr[7];
                jArr7[i2] = jArr7[i2] & j4;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markPotentiallyUnknownBit(LocalVariableBinding localVariableBinding) {
        if (this != DEAD_END) {
            this.tagBits |= 4;
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                long j = 1 << i;
                isTrue((this.nullBit1 & j) == 0, "Adding 'unknown' mark in unexpected state");
                this.nullBit4 |= j;
                return;
            }
            int i2 = i / 64;
            int i3 = i2 - 1;
            long[][] jArr = this.extra;
            if (jArr == null) {
                createExtraSpace(i2);
            } else {
                int length = jArr[0].length;
                if (i3 >= length) {
                    for (int i4 = 0; i4 < 8; i4++) {
                        long[][] jArr2 = this.extra;
                        long[] jArr3 = jArr2[i4];
                        long[] jArr4 = new long[i2];
                        jArr2[i4] = jArr4;
                        System.arraycopy(jArr3, 0, jArr4, 0, length);
                    }
                }
            }
            long j2 = 1 << (i % 64);
            isTrue((this.extra[2][i3] & j2) == 0, "Adding 'unknown' mark in unexpected state");
            long[] jArr5 = this.extra[5];
            jArr5[i3] = jArr5[i3] | j2;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markPotentiallyNullBit(LocalVariableBinding localVariableBinding) {
        if (this != DEAD_END) {
            this.tagBits |= 4;
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                long j = 1 << i;
                isTrue((this.nullBit1 & j) == 0, "Adding 'potentially null' mark in unexpected state");
                this.nullBit2 |= j;
                return;
            }
            int i2 = i / 64;
            int i3 = i2 - 1;
            long[][] jArr = this.extra;
            if (jArr == null) {
                createExtraSpace(i2);
            } else {
                int length = jArr[0].length;
                if (i3 >= length) {
                    for (int i4 = 0; i4 < 8; i4++) {
                        long[][] jArr2 = this.extra;
                        long[] jArr3 = jArr2[i4];
                        long[] jArr4 = new long[i2];
                        jArr2[i4] = jArr4;
                        System.arraycopy(jArr3, 0, jArr4, 0, length);
                    }
                }
            }
            long j2 = 1 << (i % 64);
            long[][] jArr5 = this.extra;
            long[] jArr6 = jArr5[3];
            jArr6[i3] = jArr6[i3] | j2;
            isTrue((j2 & jArr5[2][i3]) == 0, "Adding 'potentially null' mark in unexpected state");
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markPotentiallyNonNullBit(LocalVariableBinding localVariableBinding) {
        if (this != DEAD_END) {
            this.tagBits |= 4;
            int i = localVariableBinding.f627id + this.maxFieldCount;
            if (i < 64) {
                long j = 1 << i;
                isTrue((this.nullBit1 & j) == 0, "Adding 'potentially non-null' mark in unexpected state");
                this.nullBit3 = j | this.nullBit3;
                return;
            }
            int i2 = i / 64;
            int i3 = i2 - 1;
            long[][] jArr = this.extra;
            if (jArr == null) {
                createExtraSpace(i2);
            } else {
                int length = jArr[0].length;
                if (i3 >= length) {
                    for (int i4 = 0; i4 < 8; i4++) {
                        long[][] jArr2 = this.extra;
                        long[] jArr3 = jArr2[i4];
                        long[] jArr4 = new long[i2];
                        jArr2[i4] = jArr4;
                        System.arraycopy(jArr3, 0, jArr4, 0, length);
                    }
                }
            }
            long j2 = 1 << (i % 64);
            isTrue((this.extra[2][i3] & j2) == 0, "Adding 'potentially non-null' mark in unexpected state");
            long[] jArr5 = this.extra[4];
            jArr5[i3] = j2 | jArr5[i3];
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo mergedWith(UnconditionalFlowInfo unconditionalFlowInfo) {
        UnconditionalFlowInfo unconditionalFlowInfo2;
        UnconditionalFlowInfo unconditionalFlowInfo3;
        UnconditionalFlowInfo unconditionalFlowInfo4;
        int length;
        int length2;
        int i;
        int i2;
        int i3;
        char c;
        if ((unconditionalFlowInfo.tagBits & 1) != 0 && this != DEAD_END) {
            return this;
        }
        if ((this.tagBits & 1) != 0) {
            return (UnconditionalFlowInfo) unconditionalFlowInfo.copy();
        }
        this.definiteInits &= unconditionalFlowInfo.definiteInits;
        this.potentialInits |= unconditionalFlowInfo.potentialInits;
        boolean z = (this.tagBits & 4) != 0;
        boolean z2 = (unconditionalFlowInfo.tagBits & 4) != 0;
        if ((unconditionalFlowInfo.tagBits & 2) != 0) {
            unconditionalFlowInfo2 = this;
            unconditionalFlowInfo4 = unconditionalFlowInfo;
            z = z;
            z2 = false;
        } else if ((this.tagBits & 2) != 0) {
            this.nullBit1 = unconditionalFlowInfo.nullBit1;
            this.nullBit2 = unconditionalFlowInfo.nullBit2;
            this.nullBit3 = unconditionalFlowInfo.nullBit3;
            this.nullBit4 = unconditionalFlowInfo.nullBit4;
            this.iNBit = unconditionalFlowInfo.iNBit;
            this.iNNBit = unconditionalFlowInfo.iNNBit;
            this.tagBits = unconditionalFlowInfo.tagBits;
            unconditionalFlowInfo2 = this;
            unconditionalFlowInfo4 = unconditionalFlowInfo;
            z = z2;
            z = false;
        } else {
            if (z) {
                if (z2) {
                    long j = this.nullBit1;
                    long j2 = unconditionalFlowInfo.nullBit1;
                    long j3 = j & j2;
                    long j4 = this.nullBit2;
                    long j5 = unconditionalFlowInfo.nullBit2;
                    long j6 = this.nullBit3;
                    long j7 = this.nullBit4;
                    long j8 = j6 & j7;
                    long j9 = unconditionalFlowInfo.nullBit3;
                    long j10 = unconditionalFlowInfo.nullBit4;
                    long j11 = j9 & j10;
                    long j12 = (~(j8 ^ j11)) & j5;
                    long j13 = ~j5;
                    long j14 = ~j4;
                    long j15 = ~j6;
                    unconditionalFlowInfo2 = this;
                    unconditionalFlowInfo2.nullBit1 = j3 & (((j12 | (j8 & j13)) & j4) | (j14 & ((j5 & j9 & j10) | (j13 & (j15 ^ j9)))));
                    long j16 = ~j9;
                    long j17 = ~j2;
                    long j18 = ~j;
                    long j19 = j6 & (j7 | j18);
                    long j20 = ~j10;
                    long j21 = j5 & (j16 | j17 | (j19 & j20));
                    long j22 = ~j7;
                    unconditionalFlowInfo2.nullBit2 = j21 | (j4 & (j5 | (j22 & j9 & (j10 | j17)) | j15 | j18));
                    long j23 = j2 & j13;
                    unconditionalFlowInfo2.nullBit3 = (j6 & ((j & j14) | j18 | (j9 & (j22 ^ j10)))) | (j9 & (j17 | j23));
                    long j24 = j18 & j7;
                    long j25 = j2 & j5;
                    long j26 = (j15 & ((j17 & j16 & j10) | (j2 & ((j13 & j16) | (j7 & j5 & j20))) | (j24 & (j16 | j25)))) | (j8 & (j11 | j23 | (j18 & j4)));
                    long j27 = j17 & j10;
                    unconditionalFlowInfo2.nullBit4 = (j & ((j4 & ((j6 & j22 & j2) | j27) & j16) | (j15 & ((j16 & j10) | (j25 & j9 & j20) | (j14 & (j16 | j13)))) | (j14 & j9 & j10))) | j26 | (j14 & (j27 | (j2 & j16) | j24) & j13) | (j17 & j5 & j9 & j10);
                } else {
                    unconditionalFlowInfo2 = this;
                    long j28 = unconditionalFlowInfo2.nullBit1;
                    unconditionalFlowInfo2.nullBit1 = 0L;
                    long j29 = unconditionalFlowInfo2.nullBit2;
                    long j30 = unconditionalFlowInfo2.nullBit3;
                    long j31 = ~j30;
                    long j32 = ~j28;
                    long j33 = j31 | j32;
                    unconditionalFlowInfo2.nullBit2 = j29 & j33;
                    long j34 = ~j29;
                    long j35 = unconditionalFlowInfo2.nullBit4;
                    unconditionalFlowInfo2.nullBit3 = ((~j35) & j28 & j34) | (j30 & ((j34 & j35) | j32));
                    unconditionalFlowInfo2.nullBit4 = ((j33 | j34) & j32 & j35) | (j34 & j28 & j33);
                }
                unconditionalFlowInfo3 = unconditionalFlowInfo;
                unconditionalFlowInfo2.iNBit |= unconditionalFlowInfo3.iNBit;
                unconditionalFlowInfo2.iNNBit |= unconditionalFlowInfo3.iNNBit;
            } else {
                unconditionalFlowInfo2 = this;
                z = z;
                z2 = z2;
                unconditionalFlowInfo3 = unconditionalFlowInfo;
                if (z2) {
                    unconditionalFlowInfo2.nullBit1 = 0L;
                    long j36 = unconditionalFlowInfo3.nullBit2;
                    long j37 = unconditionalFlowInfo3.nullBit3;
                    long j38 = ~j37;
                    long j39 = unconditionalFlowInfo3.nullBit1;
                    long j40 = ~j39;
                    long j41 = j38 | j40;
                    long j42 = j36 & j41;
                    unconditionalFlowInfo2.nullBit2 = j42;
                    long j43 = ~j36;
                    long j44 = unconditionalFlowInfo3.nullBit4;
                    long j45 = (j39 & j43 & (~j44)) | (j37 & ((j43 & j44) | j40));
                    unconditionalFlowInfo2.nullBit3 = j45;
                    long j46 = (j43 & j41 & j39) | ((j41 | j43) & j40 & j44);
                    unconditionalFlowInfo2.nullBit4 = j46;
                    unconditionalFlowInfo4 = unconditionalFlowInfo;
                    unconditionalFlowInfo2.iNBit |= unconditionalFlowInfo4.iNBit;
                    unconditionalFlowInfo2.iNNBit |= unconditionalFlowInfo4.iNNBit;
                    boolean z3 = (j42 == 0 && j45 == 0 && j46 == 0) ? false : true;
                    z2 = z2;
                    z = z;
                    z = z3;
                }
            }
            unconditionalFlowInfo4 = unconditionalFlowInfo3;
            z2 = z2;
            z = z;
        }
        long[][] jArr = unconditionalFlowInfo2.extra;
        if (jArr != null || unconditionalFlowInfo4.extra != null) {
            char c2 = 7;
            char c3 = 6;
            if (jArr != null) {
                long[][] jArr2 = unconditionalFlowInfo4.extra;
                if (jArr2 != null) {
                    int length3 = jArr[0].length;
                    int length4 = jArr2[0].length;
                    if (length3 < length4) {
                        for (int i4 = 0; i4 < 8; i4++) {
                            long[][] jArr3 = unconditionalFlowInfo2.extra;
                            long[] jArr4 = jArr3[i4];
                            long[] jArr5 = new long[length4];
                            jArr3[i4] = jArr5;
                            System.arraycopy(jArr4, 0, jArr5, 0, length3);
                        }
                        length2 = 0;
                        i = length3;
                        length = length4;
                    } else {
                        length2 = length3;
                        length = 0;
                        i = length4;
                    }
                } else {
                    i = 0;
                    length2 = jArr[0].length;
                    length = 0;
                }
            } else {
                int i5 = 0;
                long[][] jArr6 = unconditionalFlowInfo4.extra;
                if (jArr6 != null) {
                    length = jArr6[0].length;
                    unconditionalFlowInfo2.extra = new long[8][];
                    int i6 = 0;
                    while (i6 < 8) {
                        unconditionalFlowInfo2.extra[i6] = new long[length];
                        i6++;
                        i5 = 0;
                    }
                    System.arraycopy(unconditionalFlowInfo4.extra[1], i5, unconditionalFlowInfo2.extra[1], i5, length);
                    System.arraycopy(unconditionalFlowInfo4.extra[6], i5, unconditionalFlowInfo2.extra[6], i5, length);
                    System.arraycopy(unconditionalFlowInfo4.extra[7], i5, unconditionalFlowInfo2.extra[7], i5, length);
                } else {
                    length = 0;
                }
                length2 = 0;
                i = 0;
            }
            int i7 = 0;
            while (i7 < i) {
                UnconditionalFlowInfo unconditionalFlowInfo5 = unconditionalFlowInfo4;
                UnconditionalFlowInfo unconditionalFlowInfo6 = unconditionalFlowInfo2;
                char c4 = c2;
                long[][] jArr7 = unconditionalFlowInfo6.extra;
                long[] jArr8 = jArr7[0];
                long j47 = jArr8[i7];
                long[][] jArr9 = unconditionalFlowInfo5.extra;
                jArr8[i7] = j47 & jArr9[0][i7];
                long[] jArr10 = jArr7[1];
                jArr10[i7] = jArr10[i7] | jArr9[1][i7];
                i7++;
                c2 = c4;
                unconditionalFlowInfo2 = unconditionalFlowInfo6;
                unconditionalFlowInfo4 = unconditionalFlowInfo5;
                c3 = 6;
            }
            while (i7 < length) {
                UnconditionalFlowInfo unconditionalFlowInfo7 = unconditionalFlowInfo2;
                unconditionalFlowInfo7.extra[1][i7] = unconditionalFlowInfo.extra[1][i7];
                i7++;
                unconditionalFlowInfo4 = unconditionalFlowInfo;
                c3 = c3;
                c2 = c2;
                unconditionalFlowInfo2 = unconditionalFlowInfo7;
            }
            while (i7 < length2) {
                UnconditionalFlowInfo unconditionalFlowInfo8 = unconditionalFlowInfo2;
                unconditionalFlowInfo8.extra[0][i7] = 0;
                i7++;
                unconditionalFlowInfo4 = unconditionalFlowInfo;
                c3 = c3;
                c2 = c2;
                unconditionalFlowInfo2 = unconditionalFlowInfo8;
            }
            if (z2) {
                i2 = length;
                i3 = i;
            } else {
                if (length2 >= i) {
                    i = length2;
                }
                length2 = i;
                i3 = 0;
                i2 = 0;
            }
            int i8 = !z ? 0 : length2;
            int i9 = 0;
            while (true) {
                c = 3;
                if (i9 >= i3) {
                    break;
                }
                long[][] jArr11 = unconditionalFlowInfo2.extra;
                long[] jArr12 = jArr11[2];
                long j48 = jArr12[i9];
                long[][] jArr13 = unconditionalFlowInfo4.extra;
                long j49 = jArr13[2][i9];
                long[] jArr14 = jArr11[3];
                int i10 = i3;
                int i11 = i2;
                long j50 = jArr14[i9];
                long j51 = jArr13[3][i9];
                long[] jArr15 = jArr11[4];
                long j52 = jArr15[i9];
                long[] jArr16 = jArr11[5];
                long j53 = jArr16[i9];
                long j54 = j52 & j53;
                long j55 = jArr13[4][i9];
                int i12 = i8;
                long j56 = jArr13[5][i9];
                long j57 = j55 & j56;
                int i13 = i9;
                long j58 = (~(j54 ^ j57)) & j51;
                long j59 = ~j51;
                long j60 = (j58 | (j54 & j59)) & j50;
                long j61 = ~j50;
                long j62 = ~j52;
                jArr12[i13] = j48 & j49 & (j60 | (j61 & ((j51 & j55 & j56) | (j59 & (j62 ^ j55)))));
                long j63 = ~j55;
                long j64 = ~j49;
                long j65 = ~j48;
                long j66 = j52 & (j53 | j65);
                long j67 = ~j56;
                long j68 = j51 & (j63 | j64 | (j66 & j67));
                long j69 = ~j53;
                jArr14[i13] = j68 | (j50 & (j51 | (j69 & j55 & (j56 | j64)) | j62 | j65));
                long j70 = j49 & j59;
                jArr15[i13] = (j52 & (j65 | (j48 & j61) | (j55 & (j69 ^ j56)))) | (j55 & (j64 | j70));
                long j71 = (j64 & j63 & j56) | (j49 & ((j59 & j63) | (j53 & j51 & j67)));
                long j72 = j53 & j65;
                long j73 = j49 & j51;
                long j74 = (j62 & (j71 | (j72 & (j63 | j73)))) | (j54 & (j57 | j70 | (j65 & j50)));
                long j75 = j64 & j56;
                jArr16[i13] = j74 | (j61 & (j75 | (j49 & j63) | j72) & j59) | (j48 & ((j50 & (j75 | (j52 & j69 & j49)) & j63) | (j62 & ((j63 & j56) | (j73 & j55 & j67) | (j61 & (j63 | j59)))) | (j61 & j55 & j56))) | (j64 & j51 & j55 & j56);
                long[] jArr17 = jArr11[6];
                jArr17[i13] = jArr17[i13] | jArr13[6][i13];
                long[] jArr18 = jArr11[7];
                jArr18[i13] = jArr18[i13] | jArr13[7][i13];
                z = (!z && jArr14[i13] == 0 && jArr15[i13] == 0 && jArr16[i13] == 0) ? false : true;
                i9 = i13 + 1;
                unconditionalFlowInfo4 = unconditionalFlowInfo;
                i2 = i11;
                i3 = i10;
                i8 = i12;
                unconditionalFlowInfo2 = this;
            }
            while (i9 < i2) {
                long[][] jArr19 = unconditionalFlowInfo2.extra;
                jArr19[2][i9] = 0;
                long[] jArr20 = jArr19[3];
                long[][] jArr21 = unconditionalFlowInfo4.extra;
                long j76 = jArr21[3][i9];
                long j77 = jArr21[4][i9];
                long j78 = ~j77;
                long j79 = jArr21[2][i9];
                long j80 = ~j79;
                long j81 = j78 | j80;
                jArr20[i9] = j76 & j81;
                long[] jArr22 = jArr19[4];
                long j82 = ~j76;
                long j83 = jArr21[5][i9];
                jArr22[i9] = (j77 & ((j82 & j83) | j80)) | (j79 & j82 & (~j83));
                long[] jArr23 = jArr19[5];
                jArr23[i9] = ((j81 | j82) & j80 & j83) | (j79 & j81 & j82);
                long[] jArr24 = jArr19[6];
                jArr24[i9] = jArr24[i9] | jArr21[6][i9];
                long[] jArr25 = jArr19[7];
                jArr25[i9] = jArr25[i9] | jArr21[7][i9];
                z = (!z && jArr20[i9] == 0 && jArr22[i9] == 0 && jArr23[i9] == 0) ? false : true;
                i9++;
                unconditionalFlowInfo2 = this;
                c = 3;
            }
            while (i9 < i8) {
                long[][] jArr26 = unconditionalFlowInfo2.extra;
                long[] jArr27 = jArr26[2];
                long j84 = jArr27[i9];
                jArr27[i9] = 0;
                long[] jArr28 = jArr26[c];
                long j85 = jArr28[i9];
                long[] jArr29 = jArr26[4];
                long j86 = jArr29[i9];
                long j87 = ~j86;
                long j88 = ~j84;
                long j89 = j87 | j88;
                jArr28[i9] = j85 & j89;
                long j90 = ~j85;
                long[] jArr30 = jArr26[5];
                long j91 = jArr30[i9];
                jArr29[i9] = (j84 & j90 & (~j91)) | (j86 & ((j90 & j91) | j88));
                long j92 = (j84 & j89 & j90) | (j91 & j88 & (j89 | j90));
                jArr30[i9] = j92;
                z = (!z && jArr28[i9] == 0 && jArr29[i9] == 0 && j92 == 0) ? false : true;
                i9++;
                unconditionalFlowInfo2 = this;
                c = 3;
            }
        }
        if (z) {
            unconditionalFlowInfo2.tagBits |= 4;
        } else {
            unconditionalFlowInfo2.tagBits &= -5;
        }
        return unconditionalFlowInfo2;
    }

    static int numberOfEnclosingFields(ReferenceBinding referenceBinding) {
        int iFieldCount = 0;
        for (ReferenceBinding referenceBindingEnclosingType = referenceBinding.enclosingType(); referenceBindingEnclosingType != null; referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType()) {
            iFieldCount += referenceBindingEnclosingType.fieldCount();
        }
        return iFieldCount;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo nullInfoLessUnconditionalCopy() {
        if (this == DEAD_END) {
            return this;
        }
        UnconditionalFlowInfo unconditionalFlowInfo = new UnconditionalFlowInfo();
        unconditionalFlowInfo.definiteInits = this.definiteInits;
        unconditionalFlowInfo.potentialInits = this.potentialInits;
        unconditionalFlowInfo.iNBit = -1L;
        unconditionalFlowInfo.iNNBit = -1L;
        unconditionalFlowInfo.tagBits = this.tagBits & (-5);
        unconditionalFlowInfo.tagBits |= 64;
        unconditionalFlowInfo.maxFieldCount = this.maxFieldCount;
        if (this.extra != null) {
            long[][] jArr = new long[8][];
            unconditionalFlowInfo.extra = jArr;
            long[] jArr2 = this.extra[0];
            int length = jArr2.length;
            long[] jArr3 = new long[length];
            jArr[0] = jArr3;
            System.arraycopy(jArr2, 0, jArr3, 0, length);
            long[] jArr4 = this.extra[1];
            long[] jArr5 = new long[length];
            unconditionalFlowInfo.extra[1] = jArr5;
            System.arraycopy(jArr4, 0, jArr5, 0, length);
            for (int i = 2; i < 8; i++) {
                unconditionalFlowInfo.extra[i] = new long[length];
            }
            Arrays.fill(unconditionalFlowInfo.extra[6], -1L);
            Arrays.fill(unconditionalFlowInfo.extra[7], -1L);
        }
        return unconditionalFlowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo safeInitsWhenTrue() {
        return copy();
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo setReachMode(int i) {
        if (this == DEAD_END) {
            return this;
        }
        if (i == 0) {
            this.tagBits &= -4;
        } else if (i == 2) {
            this.tagBits |= 2;
        } else {
            if ((this.tagBits & 3) == 0) {
                this.potentialInits = 0L;
                long[][] jArr = this.extra;
                if (jArr != null) {
                    int length = jArr[0].length;
                    for (int i2 = 0; i2 < length; i2++) {
                        this.extra[1][i2] = 0;
                    }
                }
            }
            this.tagBits = i | this.tagBits;
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public String toString() {
        if (this == DEAD_END) {
            return "FlowInfo.DEAD_END";
        }
        char c = 4;
        String str = "], reachable:";
        char c2 = 0;
        if ((this.tagBits & 4) != 0) {
            String str2 = ", incoming: ";
            if (this.extra == null) {
                return "FlowInfo<def: " + this.definiteInits + ", pot: " + this.potentialInits + ", reachable:" + ((this.tagBits & 3) == 0) + ", null: " + this.nullBit1 + this.nullBit2 + this.nullBit3 + this.nullBit4 + ", incoming: " + this.iNBit + this.iNNBit + ">";
            }
            String str3 = "FlowInfo<def:[" + this.definiteInits;
            String str4 = "], pot:[" + this.potentialInits;
            String str5 = ", null:[" + this.nullBit1 + this.nullBit2 + this.nullBit3 + this.nullBit4;
            long[] jArr = this.extra[0];
            int length = jArr.length > 3 ? 3 : jArr.length;
            int i = 0;
            while (i < length) {
                String str6 = str2;
                str3 = String.valueOf(str3) + "," + this.extra[c2][i];
                str4 = String.valueOf(str4) + "," + this.extra[1][i];
                str5 = String.valueOf(str5) + "," + this.extra[2][i] + this.extra[3][i] + this.extra[c][i] + this.extra[5][i] + str6 + this.extra[6][i] + this.extra[7];
                i++;
                str2 = str6;
                str = str;
                c = 4;
                c2 = 0;
            }
            if (length < this.extra[c2].length) {
                str3 = String.valueOf(str3) + ",...";
                str4 = String.valueOf(str4) + ",...";
                str5 = String.valueOf(str5) + ",...";
            }
            return String.valueOf(str3) + str4 + str + ((this.tagBits & 3) != 0 ? c2 : 1) + str5 + "]>";
        }
        if (this.extra == null) {
            return "FlowInfo<def: " + this.definiteInits + ", pot: " + this.potentialInits + ", reachable:" + ((this.tagBits & 3) == 0) + ", no null info>";
        }
        String str7 = "FlowInfo<def:[" + this.definiteInits;
        String str8 = "], pot:[" + this.potentialInits;
        char c3 = 0;
        long[] jArr2 = this.extra[0];
        int length2 = jArr2.length > 3 ? 3 : jArr2.length;
        int i2 = 0;
        while (i2 < length2) {
            str7 = String.valueOf(str7) + "," + this.extra[0][i2];
            str8 = String.valueOf(str8) + "," + this.extra[1][i2];
            i2++;
            c3 = 0;
        }
        if (length2 < this.extra[c3].length) {
            str7 = String.valueOf(str7) + ",...";
            str8 = String.valueOf(str8) + ",...";
        }
        return String.valueOf(str7) + str8 + "], reachable:" + ((this.tagBits & 3) == 0) + ", no null info>";
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo unconditionalCopy() {
        return (UnconditionalFlowInfo) copy();
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo unconditionalFieldLessCopy() {
        UnconditionalFlowInfo unconditionalFlowInfo = new UnconditionalFlowInfo();
        unconditionalFlowInfo.tagBits = this.tagBits;
        unconditionalFlowInfo.maxFieldCount = this.maxFieldCount;
        int i = this.maxFieldCount;
        if (i < 64) {
            long j = this.definiteInits;
            long j2 = ~((1 << i) - 1);
            unconditionalFlowInfo.definiteInits = j & j2;
            unconditionalFlowInfo.potentialInits = this.potentialInits & j2;
            unconditionalFlowInfo.nullBit1 = this.nullBit1 & j2;
            unconditionalFlowInfo.nullBit2 = this.nullBit2 & j2;
            unconditionalFlowInfo.nullBit3 = this.nullBit3 & j2;
            unconditionalFlowInfo.nullBit4 = this.nullBit4 & j2;
            unconditionalFlowInfo.iNBit = this.iNBit & j2;
            unconditionalFlowInfo.iNNBit = this.iNNBit & j2;
        }
        long[][] jArr = this.extra;
        if (jArr == null) {
            return unconditionalFlowInfo;
        }
        int i2 = i / 64;
        int i3 = i2 - 1;
        int length = jArr[0].length;
        if (i3 >= length) {
            return unconditionalFlowInfo;
        }
        unconditionalFlowInfo.extra = new long[8][];
        if (i2 < length) {
            int i4 = length - i2;
            for (int i5 = 0; i5 < 8; i5++) {
                long[] jArr2 = this.extra[i5];
                long[] jArr3 = new long[length];
                unconditionalFlowInfo.extra[i5] = jArr3;
                System.arraycopy(jArr2, i2, jArr3, i2, i4);
            }
        } else if (i3 >= 0) {
            unconditionalFlowInfo.createExtraSpace(length);
        }
        if (i3 >= 0) {
            long j3 = ~((1 << (i % 64)) - 1);
            for (int i6 = 0; i6 < 8; i6++) {
                unconditionalFlowInfo.extra[i6][i3] = this.extra[i6][i3] & j3;
            }
        }
        return unconditionalFlowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void resetAssignmentInfo(LocalVariableBinding localVariableBinding) {
        resetAssignmentInfo(localVariableBinding.f627id + this.maxFieldCount);
    }

    public void resetAssignmentInfo(int i) {
        if (this != DEAD_END) {
            if (i < 64) {
                long j = this.definiteInits;
                long j2 = ~(1 << i);
                this.definiteInits = j & j2;
                this.potentialInits = j2 & this.potentialInits;
                return;
            }
            int i2 = (i / 64) - 1;
            long[][] jArr = this.extra;
            if (jArr != null) {
                long[] jArr2 = jArr[0];
                if (i2 >= jArr2.length) {
                    return;
                }
                long j3 = jArr2[i2];
                long j4 = ~(1 << (i % 64));
                jArr2[i2] = j3 & j4;
                long[] jArr3 = jArr[1];
                jArr3[i2] = j4 & jArr3[i2];
            }
        }
    }

    private void createExtraSpace(int i) {
        this.extra = new long[8][];
        for (int i2 = 0; i2 < 8; i2++) {
            this.extra[i2] = new long[i];
        }
        if ((this.tagBits & 64) != 0) {
            Arrays.fill(this.extra[6], -1L);
            Arrays.fill(this.extra[7], -1L);
        }
    }
}
