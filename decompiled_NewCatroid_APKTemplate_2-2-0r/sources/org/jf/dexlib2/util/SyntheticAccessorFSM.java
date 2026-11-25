package org.jf.dexlib2.util;

import java.util.List;
import org.apache.xerces.dom3.as.ASDataType;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
import org.jf.dexlib2.iface.instruction.WideLiteralInstruction;

/* JADX INFO: loaded from: classes6.dex */
public class SyntheticAccessorFSM {
    public static final int ADD = 7;
    public static final int AND = 12;
    public static final int DIV = 10;
    public static final int DOUBLE = 3;
    public static final int FLOAT = 2;
    public static final int INT = 0;
    public static final int LONG = 1;
    public static final int MUL = 9;
    public static final int NEGATIVE_ONE = -1;
    public static final int OR = 13;
    public static final int OTHER = 0;
    public static final int POSITIVE_ONE = 1;
    public static final int REM = 11;
    public static final int SHL = 15;
    public static final int SHR = 16;
    public static final int SUB = 8;
    static final int SyntheticAccessorFSM_en_main = 1;
    static final int SyntheticAccessorFSM_error = 0;
    static final int SyntheticAccessorFSM_first_final = 17;
    static final int SyntheticAccessorFSM_start = 1;
    public static final int USHR = 17;
    public static final int XOR = 14;
    private final Opcodes opcodes;
    private static final byte[] _SyntheticAccessorFSM_actions = init__SyntheticAccessorFSM_actions_0();
    private static final short[] _SyntheticAccessorFSM_key_offsets = init__SyntheticAccessorFSM_key_offsets_0();
    private static final short[] _SyntheticAccessorFSM_trans_keys = init__SyntheticAccessorFSM_trans_keys_0();
    private static final byte[] _SyntheticAccessorFSM_single_lengths = init__SyntheticAccessorFSM_single_lengths_0();
    private static final byte[] _SyntheticAccessorFSM_range_lengths = init__SyntheticAccessorFSM_range_lengths_0();
    private static final short[] _SyntheticAccessorFSM_index_offsets = init__SyntheticAccessorFSM_index_offsets_0();
    private static final byte[] _SyntheticAccessorFSM_indicies = init__SyntheticAccessorFSM_indicies_0();
    private static final byte[] _SyntheticAccessorFSM_trans_targs = init__SyntheticAccessorFSM_trans_targs_0();
    private static final byte[] _SyntheticAccessorFSM_trans_actions = init__SyntheticAccessorFSM_trans_actions_0();

    private static byte[] init__SyntheticAccessorFSM_actions_0() {
        return new byte[]{0, 1, 0, 1, 1, 1, 2, 1, 13, 1, 14, 1, 15, 1, 16, 1, 17, 1, 18, 1, 19, 1, 20, 1, 21, 1, 25, 2, 3, 7, 2, 4, 7, 2, 5, 7, 2, 6, 7, 2, 8, 12, 2, 9, 12, 2, 10, 12, 2, 11, 12, 2, 22, 23, 2, 22, 24, 2, 22, 25, 2, 22, 26, 2, 22, 27, 2, 22, 28};
    }

    private static short[] init__SyntheticAccessorFSM_key_offsets_0() {
        return new short[]{0, 0, 12, 82, 98, 102, ASDataType.HEXBINARY_DATATYPE, 166, 172, 174, 180, 184, 190, 192, 196, 198, ASDataType.NCNAME_DATATYPE, ASDataType.TOKEN_DATATYPE};
    }

    private static short[] init__SyntheticAccessorFSM_trans_keys_0() {
        return new short[]{82, 88, 89, 95, 96, 102, 103, ASDataType.DATETIME_DATATYPE, 110, 114, ASDataType.GMONTH_DATATYPE, 120, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 177, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, ASDataType.NCNAME_DATATYPE, ASDataType.NORMALIZEDSTRING_DATATYPE, ASDataType.TOKEN_DATATYPE, ASDataType.LANGUAGE_DATATYPE, ASDataType.NEGATIVEINTEGER_DATATYPE, ASDataType.LONG_DATATYPE, ASDataType.INT_DATATYPE, ASDataType.POSITIVEINTEGER_DATATYPE, 15, 17, 18, 25, 129, 143, 144, 176, 178, ASDataType.NONPOSITIVEINTEGER_DATATYPE, 144, 145, 155, 156, 166, 167, 171, 172, 176, 177, 187, 188, 198, 199, ASDataType.TOKEN_DATATYPE, ASDataType.LANGUAGE_DATATYPE, 89, 95, 103, ASDataType.DATETIME_DATATYPE, 15, 17, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 177, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, ASDataType.NCNAME_DATATYPE, ASDataType.NORMALIZEDSTRING_DATATYPE, ASDataType.TOKEN_DATATYPE, ASDataType.LANGUAGE_DATATYPE, ASDataType.NEGATIVEINTEGER_DATATYPE, ASDataType.LONG_DATATYPE, 144, 176, 178, ASDataType.NONPOSITIVEINTEGER_DATATYPE, 89, 95, 103, ASDataType.DATETIME_DATATYPE, 129, 143, 15, 17, 89, 95, 103, ASDataType.DATETIME_DATATYPE, 129, 143, 89, 95, 103, ASDataType.DATETIME_DATATYPE, 89, 95, 103, ASDataType.DATETIME_DATATYPE, 129, 143, 15, 17, 89, 95, 103, ASDataType.DATETIME_DATATYPE, 15, 17, 14, 10, 12, 15, 17, 0};
    }

    private static byte[] init__SyntheticAccessorFSM_single_lengths_0() {
        return new byte[]{0, 0, 60, 16, 0, 0, 58, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
    }

    private static byte[] init__SyntheticAccessorFSM_range_lengths_0() {
        return new byte[]{0, 6, 5, 0, 2, 1, 2, 3, 1, 3, 2, 3, 1, 2, 1, 1, 1, 0};
    }

    private static short[] init__SyntheticAccessorFSM_index_offsets_0() {
        return new short[]{0, 0, 7, 73, 90, 93, 95, 156, 160, 162, 166, 169, 173, 175, 178, 180, 183, 185};
    }

    private static byte[] init__SyntheticAccessorFSM_indicies_0() {
        return new byte[]{0, 2, 0, 2, 3, 3, 1, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 9, 10, 11, 12, 13, 14, 15, 16, 17, 20, 21, 9, 10, 11, 22, 23, 9, 10, 11, 8, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 10, 11, 12, 13, 14, 15, 16, 17, 20, 21, 10, 11, 22, 23, 10, 11, 24, 24, 4, 5, 6, 7, 9, 1, 25, 26, 27, 28, 29, 30, 31, 32, 25, 26, 27, 28, 29, 30, 31, 32, 1, 33, 33, 1, 34, 1, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 9, 10, 11, 12, 13, 14, 15, 16, 17, 20, 21, 9, 10, 11, 22, 23, 9, 10, 11, 8, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 10, 11, 12, 13, 14, 15, 16, 17, 20, 21, 10, 11, 22, 23, 10, 11, 7, 9, 1, 35, 35, 36, 1, 37, 1, 35, 35, 38, 1, 35, 35, 1, 39, 39, 40, 1, 41, 1, 39, 39, 1, 42, 1, 44, 43, 1, 45, 1, 1, 0};
    }

    private static byte[] init__SyntheticAccessorFSM_trans_targs_0() {
        return new byte[]{2, 0, 14, 15, 17, 3, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 11, 4, 4, 4, 4, 4, 4, 4, 4, 5, 17, 8, 9, 17, 10, 12, 13, 17, 17, 16, 17, 17};
    }

    private static byte[] init__SyntheticAccessorFSM_trans_actions_0() {
        return new byte[]{0, 0, 1, 0, 51, 3, 0, 27, 39, 7, 9, 11, 13, 15, 17, 19, 21, 23, 30, 42, 33, 45, 36, 48, 5, 27, 39, 30, 42, 33, 45, 36, 48, 1, 63, 1, 0, 66, 0, 1, 0, 60, 54, 0, 25, 57};
    }

    public SyntheticAccessorFSM(Opcodes opcodes) {
        this.opcodes = opcodes;
    }

    /* JADX WARN: Code duplicated, block: B:106:0x01cb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:126:0x01c5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x010a  */
    /* JADX WARN: Code duplicated, block: B:46:0x011a  */
    /* JADX WARN: Code duplicated, block: B:50:0x0131  */
    /* JADX WARN: Code duplicated, block: B:51:0x013c  */
    /* JADX WARN: Code duplicated, block: B:57:0x0171  */
    /* JADX WARN: Code duplicated, block: B:58:0x017c  */
    /* JADX WARN: Code duplicated, block: B:59:0x017f  */
    /* JADX WARN: Code duplicated, block: B:60:0x0182  */
    /* JADX WARN: Code duplicated, block: B:61:0x0185  */
    /* JADX WARN: Code duplicated, block: B:62:0x0188  */
    /* JADX WARN: Code duplicated, block: B:63:0x018b  */
    /* JADX WARN: Code duplicated, block: B:64:0x018e  */
    /* JADX WARN: Code duplicated, block: B:65:0x0191  */
    /* JADX WARN: Code duplicated, block: B:66:0x0194  */
    /* JADX WARN: Code duplicated, block: B:67:0x0197  */
    /* JADX WARN: Code duplicated, block: B:68:0x019a  */
    /* JADX WARN: Code duplicated, block: B:69:0x019c  */
    /* JADX WARN: Code duplicated, block: B:70:0x019e  */
    /* JADX WARN: Code duplicated, block: B:71:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:72:0x01a3  */
    /* JADX WARN: Code duplicated, block: B:73:0x01ae A[FALL_THROUGH, PHI: r10 r20
      0x01ae: PHI (r10v11 int) = (r10v6 int), (r10v12 int) binds: [B:47:0x0122, B:72:0x01a3] A[DONT_GENERATE, DONT_INLINE]
      0x01ae: PHI (r20v3 long) = (r20v1 long), (r20v4 long) binds: [B:47:0x0122, B:72:0x01a3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:74:0x01b0  */
    /* JADX WARN: Code duplicated, block: B:75:0x01bb  */
    /* JADX WARN: Code duplicated, block: B:84:0x01de  */
    /* JADX WARN: Code duplicated, block: B:88:0x01da A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:90:0x0127 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:91:0x014a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:92:0x0157 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:93:0x0164 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:94:0x01d5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:95:0x01e3 A[SYNTHETIC] */
    /* JADX WARN: Failed to find 'out' block for switch in B:47:0x0122. Please report as an issue. */
    public int test(List<? extends Instruction> list) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        byte b;
        int i6;
        int i7;
        long wideLiteral;
        int i8;
        int i9;
        int i10;
        int incrementType;
        int i11;
        int i12;
        int size = list.size();
        int i13 = -1;
        int i14 = 1;
        int registerA = -1;
        int registerA2 = -1;
        long j = 0;
        byte b2 = 1;
        int i15 = 0;
        int i16 = 0;
        int i17 = -1;
        int i18 = -1;
        while (true) {
            if (i15 != 0) {
                if (i15 != i14) {
                    if (i15 == 2) {
                        i = i14;
                    }
                }
                if (b2 == 0) {
                    i14 = i;
                    i15 = 5;
                } else {
                    i16++;
                    if (i16 != size) {
                        i14 = i;
                        i15 = i14;
                    }
                }
            } else if (i16 == size) {
                i15 = 4;
            } else {
                if (b2 == 0) {
                }
                i15 = 5;
            }
            short s = _SyntheticAccessorFSM_key_offsets[b2];
            short s2 = _SyntheticAccessorFSM_index_offsets[b2];
            byte b3 = _SyntheticAccessorFSM_single_lengths[b2];
            if (b3 > 0) {
                int i19 = s + b3;
                int i20 = s;
                int i21 = i19 - 1;
                while (true) {
                    if (i21 < i20) {
                        i2 = i13;
                        i4 = i19;
                        i3 = s2 + b3;
                    } else {
                        int i22 = i20 + ((i21 - i20) >> 1);
                        i2 = i13;
                        short sShortValue = this.opcodes.getOpcodeValue(list.get(i16).getOpcode()).shortValue();
                        short[] sArr = _SyntheticAccessorFSM_trans_keys;
                        int i23 = i20;
                        if (sShortValue < sArr[i22]) {
                            i21 = i22 - 1;
                            i12 = i23 == true ? 1 : 0;
                        } else if (this.opcodes.getOpcodeValue(list.get(i16).getOpcode()).shortValue() > sArr[i22]) {
                            i12 = i22 + 1;
                        } else {
                            i11 = s2 + (i22 - s);
                        }
                        i13 = i2;
                        i20 = i12;
                    }
                    i = 1;
                    i5 = i11;
                    byte b4 = _SyntheticAccessorFSM_indicies[i5];
                    b2 = _SyntheticAccessorFSM_trans_targs[b4];
                    b = _SyntheticAccessorFSM_trans_actions[b4];
                    if (b != 0) {
                        i6 = b + 1;
                        i7 = _SyntheticAccessorFSM_actions[b];
                        registerA = registerA;
                        registerA2 = registerA2;
                        wideLiteral = j;
                        i8 = i17;
                        i18 = i18;
                        while (true) {
                            i9 = i7 - 1;
                            if (i7 > 0) {
                                i10 = i6 + 1;
                                switch (_SyntheticAccessorFSM_actions[i6]) {
                                    case 0:
                                        registerA = ((OneRegisterInstruction) list.get(i16)).getRegisterA();
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 1:
                                        wideLiteral = ((WideLiteralInstruction) list.get(i16)).getWideLiteral();
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 2:
                                        wideLiteral = ((WideLiteralInstruction) list.get(i16)).getWideLiteral();
                                        i8 = 7;
                                    case 3:
                                    case 8:
                                        i18 = 0;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 4:
                                    case 9:
                                        i18 = i;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 5:
                                    case 10:
                                        i18 = 2;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 6:
                                    case 11:
                                        i18 = 3;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 7:
                                        i8 = 7;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 12:
                                        i8 = 8;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 13:
                                        i8 = 9;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 14:
                                        i8 = 10;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 15:
                                        i8 = 11;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 16:
                                        i8 = 12;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 17:
                                        i8 = 13;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 18:
                                        i8 = 14;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 19:
                                        i8 = 15;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 20:
                                        i8 = 16;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 21:
                                        i8 = 17;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 22:
                                        registerA2 = ((OneRegisterInstruction) list.get(i16)).getRegisterA();
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 23:
                                        i16++;
                                        i17 = i8;
                                        i13 = i;
                                        i14 = i13;
                                        j = wideLiteral;
                                        break;
                                    case 24:
                                        i16++;
                                        i17 = i8;
                                        i18 = i18;
                                        registerA = registerA;
                                        registerA2 = registerA2;
                                        i14 = i;
                                        j = wideLiteral;
                                        i13 = 2;
                                        break;
                                    case 25:
                                        i16++;
                                        i17 = i8;
                                        i18 = i18;
                                        registerA = registerA;
                                        registerA2 = registerA2;
                                        i14 = i;
                                        j = wideLiteral;
                                        i13 = 0;
                                        break;
                                    case 26:
                                        incrementType = getIncrementType(i8, i18, wideLiteral, registerA, registerA2);
                                        i2 = incrementType;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 27:
                                        incrementType = getIncrementType(i8, i18, wideLiteral, registerA, registerA2);
                                        i2 = incrementType;
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                    case 28:
                                        i16++;
                                        i13 = i8;
                                        i17 = i13;
                                        i14 = i;
                                        j = wideLiteral;
                                        break;
                                    default:
                                        i7 = i9;
                                        i6 = i10;
                                        break;
                                }
                            } else {
                                i17 = i8;
                                i18 = i18;
                                registerA = registerA;
                                registerA2 = registerA2;
                                j = wideLiteral;
                            }
                            i15 = 5;
                        }
                    }
                    i13 = i2;
                    if (b2 == 0) {
                        i14 = i;
                        i15 = 5;
                    } else {
                        i16++;
                        if (i16 != size) {
                            i14 = i;
                            i15 = i14;
                        }
                    }
                }
            } else {
                i2 = i13;
                i4 = s;
                i3 = s2;
            }
            byte b5 = _SyntheticAccessorFSM_range_lengths[b2];
            i11 = i3;
            if (b5 > 0) {
                int i24 = ((b5 << 1) + i4) - 2;
                int i25 = i4;
                while (true) {
                    if (i24 < i25) {
                        i11 = i3 + b5;
                        i = 1;
                        i5 = i11;
                    } else {
                        int i26 = (((i24 - i25) >> 1) & (-2)) + i25;
                        byte b6 = b5;
                        short sShortValue2 = this.opcodes.getOpcodeValue(list.get(i16).getOpcode()).shortValue();
                        short[] sArr2 = _SyntheticAccessorFSM_trans_keys;
                        int i27 = i24;
                        if (sShortValue2 < sArr2[i26]) {
                            i24 = i26 - 2;
                        } else if (this.opcodes.getOpcodeValue(list.get(i16).getOpcode()).shortValue() > sArr2[i26 + 1]) {
                            i25 = i26 + 2;
                            i24 = i27;
                        } else {
                            i = 1;
                            i5 = i3 + ((i26 - i4) >> 1);
                        }
                        b5 = b6;
                        i25 = i25;
                    }
                }
            } else {
                i = 1;
                i5 = i11;
            }
            byte b7 = _SyntheticAccessorFSM_indicies[i5];
            b2 = _SyntheticAccessorFSM_trans_targs[b7];
            b = _SyntheticAccessorFSM_trans_actions[b7];
            if (b != 0) {
                i6 = b + 1;
                i7 = _SyntheticAccessorFSM_actions[b];
                registerA = registerA;
                registerA2 = registerA2;
                wideLiteral = j;
                i8 = i17;
                i18 = i18;
                while (true) {
                    i9 = i7 - 1;
                    if (i7 > 0) {
                        i10 = i6 + 1;
                        switch (_SyntheticAccessorFSM_actions[i6]) {
                            case 0:
                                registerA = ((OneRegisterInstruction) list.get(i16)).getRegisterA();
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 1:
                                wideLiteral = ((WideLiteralInstruction) list.get(i16)).getWideLiteral();
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 2:
                                wideLiteral = ((WideLiteralInstruction) list.get(i16)).getWideLiteral();
                                i8 = 7;
                            case 3:
                            case 8:
                                i18 = 0;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 4:
                            case 9:
                                i18 = i;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 5:
                            case 10:
                                i18 = 2;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 6:
                            case 11:
                                i18 = 3;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 7:
                                i8 = 7;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 12:
                                i8 = 8;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 13:
                                i8 = 9;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 14:
                                i8 = 10;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 15:
                                i8 = 11;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 16:
                                i8 = 12;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 17:
                                i8 = 13;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 18:
                                i8 = 14;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 19:
                                i8 = 15;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 20:
                                i8 = 16;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 21:
                                i8 = 17;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 22:
                                registerA2 = ((OneRegisterInstruction) list.get(i16)).getRegisterA();
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 23:
                                i16++;
                                i17 = i8;
                                i13 = i;
                                i14 = i13;
                                j = wideLiteral;
                                break;
                            case 24:
                                i16++;
                                i17 = i8;
                                i18 = i18;
                                registerA = registerA;
                                registerA2 = registerA2;
                                i14 = i;
                                j = wideLiteral;
                                i13 = 2;
                                break;
                            case 25:
                                i16++;
                                i17 = i8;
                                i18 = i18;
                                registerA = registerA;
                                registerA2 = registerA2;
                                i14 = i;
                                j = wideLiteral;
                                i13 = 0;
                                break;
                            case 26:
                                incrementType = getIncrementType(i8, i18, wideLiteral, registerA, registerA2);
                                i2 = incrementType;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 27:
                                incrementType = getIncrementType(i8, i18, wideLiteral, registerA, registerA2);
                                i2 = incrementType;
                                i7 = i9;
                                i6 = i10;
                                break;
                            case 28:
                                i16++;
                                i13 = i8;
                                i17 = i13;
                                i14 = i;
                                j = wideLiteral;
                                break;
                            default:
                                i7 = i9;
                                i6 = i10;
                                break;
                        }
                    } else {
                        i17 = i8;
                        i18 = i18;
                        registerA = registerA;
                        registerA2 = registerA2;
                        j = wideLiteral;
                    }
                    i15 = 5;
                }
            }
            i13 = i2;
            if (b2 == 0) {
                i14 = i;
                i15 = 5;
            } else {
                i16++;
                if (i16 != size) {
                    i14 = i;
                    i15 = i14;
                }
            }
        }
        return i13;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0012  */
    private static int getIncrementType(int i, int i2, long j, int i3, int i4) {
        boolean z;
        boolean z2 = true;
        boolean z3 = i3 == i4;
        if (i2 == 0 || i2 == 1) {
            if (j == 1) {
                z = false;
            } else {
                if (j != -1) {
                    return -1;
                }
                z = true;
            }
        } else if (i2 != 2) {
            if (i2 == 3) {
                double dLongBitsToDouble = Double.longBitsToDouble(j);
                if (dLongBitsToDouble != 1.0d) {
                    if (dLongBitsToDouble != -1.0d) {
                        return -1;
                    }
                    z = true;
                }
            }
            z = false;
        } else {
            float fIntBitsToFloat = Float.intBitsToFloat((int) j);
            if (fIntBitsToFloat == 1.0f) {
                z = false;
            } else {
                if (fIntBitsToFloat != -1.0f) {
                    return -1;
                }
                z = true;
            }
        }
        if ((i != 7 || z) && (i != 8 || !z)) {
            z2 = false;
        }
        if (z3) {
            return z2 ? 4 : 6;
        }
        return z2 ? 3 : 5;
    }
}
