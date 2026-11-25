package org.bouncycastle.crypto.engines;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import org.billthefarmer.mididriver.MidiConstants;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes13.dex */
public final class TwofishEngine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final int GF256_FDBK = 361;
    private static final int GF256_FDBK_2 = 180;
    private static final int GF256_FDBK_4 = 90;
    private static final int INPUT_WHITEN = 0;
    private static final int MAX_KEY_BITS = 256;
    private static final int MAX_ROUNDS = 16;
    private static final int OUTPUT_WHITEN = 4;
    private static final byte[][] P = {new byte[]{Opcodes.OPC_ret, 103, Opcodes.OPC_putstatic, -24, 4, -3, Opcodes.OPC_if_icmpgt, 118, Opcodes.OPC_ifne, Opcodes.OPC_i2c, -128, 120, -28, -35, -47, 56, 13, Opcodes.OPC_ifnull, 53, Opcodes.OPC_dcmpg, 24, -9, -20, 108, 67, 117, 55, 38, -6, 19, Opcodes.OPC_lcmp, 72, MidiConstants.SONG_POSITION_POBYTEER, -48, Opcodes.OPC_f2i, 48, Opcodes.OPC_iinc, 84, WasmBinary.STRUCT_TYPE, 35, 25, 91, 61, 89, MidiConstants.SONG_SELECT, Opcodes.OPC_freturn, Opcodes.OPC_if_icmpge, Opcodes.OPC_ixor, 99, 1, Opcodes.OPC_lxor, 46, -39, 81, Opcodes.OPC_iflt, 124, Opcodes.OPC_if_acmpne, -21, Opcodes.OPC_if_acmpeq, Opcodes.OPC_arraylength, 22, 12, -29, 97, -64, Opcodes.OPC_f2l, 58, -11, 115, 44, 37, 11, -69, 78, Opcodes.OPC_l2f, 107, 83, 106, Opcodes.OPC_getfield, MidiConstants.MIDI_TIME_CODE, -31, -26, Opcodes.OPC_anewarray, 69, -30, -12, Opcodes.OPC_invokevirtual, 102, -52, Opcodes.OPC_fcmpl, 3, 86, -44, 28, 30, -41, -5, Opcodes.OPC_monitorexit, Opcodes.OPC_d2i, Opcodes.OPC_putfield, -23, WasmBinary.SUB_FINAL_TYPE, -65, Opcodes.OPC_invokedynamic, -22, 119, 57, Opcodes.OPC_dreturn, 51, Opcodes.OPC_jsr_w, 98, 113, Opcodes.OPC_lor, 121, 9, Opcodes.OPC_lreturn, 36, -51, -7, -40, -27, Opcodes.OPC_multianewarray, Opcodes.OPC_invokeinterface, 77, 68, 8, Opcodes.OPC_i2f, -25, Opcodes.OPC_if_icmplt, 29, Opcodes.OPC_tableswitch, -19, 6, 112, Opcodes.OPC_getstatic, -46, 65, 123, -96, 17, 49, Opcodes.OPC_monitorenter, 39, -112, 32, -10, 96, -1, Opcodes.OPC_fcmpg, 92, Opcodes.OPC_return, Opcodes.OPC_lookupswitch, Opcodes.OPC_ifle, Opcodes.OPC_ifge, 82, 27, 95, Opcodes.OPC_i2s, 10, ByteSourceJsonBootstrapper.UTF8_BOM_1, Opcodes.OPC_i2b, Opcodes.OPC_i2l, 73, -18, 45, 79, Opcodes.OPC_d2l, 59, 71, Opcodes.OPC_i2d, 109, 70, -42, 62, 105, 100, 42, WasmBinary.REC_GROUP, -53, 47, -4, Opcodes.OPC_dcmpl, 5, 122, Opcodes.OPC_ireturn, 127, -43, 26, 75, 14, Opcodes.OPC_goto, 90, 40, 20, 63, 41, Opcodes.OPC_l2i, 60, 76, 2, Opcodes.OPC_invokestatic, -38, -80, 23, 85, 31, Opcodes.OPC_l2d, 125, 87, Opcodes.OPC_ifnonnull, Opcodes.OPC_f2d, 116, Opcodes.OPC_invokespecial, Opcodes.OPC_wide, Opcodes.OPC_if_icmpeq, 114, 126, 21, 34, 18, 88, 7, Opcodes.OPC_ifeq, 52, 110, 80, WasmBinary.ARRAY_TYPE, 104, 101, -68, -37, -8, Opcodes.OPC_goto_w, Opcodes.OPC_jsr, 43, 64, -36, -2, 50, Opcodes.OPC_if_icmple, -54, 16, 33, -16, -45, 93, 15, 0, 111, Opcodes.OPC_ifgt, 54, 66, 74, 94, Opcodes.OPC_instanceof, -32}, new byte[]{117, MidiConstants.SONG_SELECT, Opcodes.OPC_ifnull, -12, -37, 123, -5, Opcodes.OPC_goto_w, 74, -45, -26, 107, 69, 125, -24, 75, -42, 50, -40, -3, 55, 113, MidiConstants.MIDI_TIME_CODE, -31, 48, 15, -8, 27, Opcodes.OPC_i2d, -6, 6, 63, 94, Opcodes.OPC_invokedynamic, Opcodes.OPC_freturn, 91, Opcodes.OPC_l2d, 0, -68, Opcodes.OPC_ifgt, 109, Opcodes.OPC_instanceof, Opcodes.OPC_return, 14, -128, 93, -46, -43, -96, Opcodes.OPC_iinc, 7, 20, Opcodes.OPC_putfield, -112, 44, Opcodes.OPC_if_icmpgt, Opcodes.OPC_getstatic, 115, 76, 84, Opcodes.OPC_i2c, 116, 54, 81, 56, -80, Opcodes.OPC_anewarray, 90, -4, 96, 98, Opcodes.OPC_fcmpg, 108, 66, -9, 16, 124, 40, 39, Opcodes.OPC_f2l, 19, Opcodes.OPC_fcmpl, Opcodes.OPC_ifge, Opcodes.OPC_ifnonnull, 36, 70, 59, 112, -54, -29, Opcodes.OPC_i2l, -53, 17, -48, Opcodes.OPC_i2s, Opcodes.OPC_invokestatic, Opcodes.OPC_if_acmpne, Opcodes.OPC_lxor, 32, -1, Opcodes.OPC_if_icmpeq, 119, Opcodes.OPC_monitorexit, -52, 3, 111, 8, -65, 64, -25, 43, -30, 121, 12, Opcodes.OPC_tableswitch, Opcodes.OPC_ixor, 65, 58, -22, Opcodes.OPC_invokeinterface, -28, Opcodes.OPC_ifne, Opcodes.OPC_if_icmple, Opcodes.OPC_dcmpl, 126, -38, 122, 23, 102, Opcodes.OPC_lcmp, Opcodes.OPC_if_icmplt, 29, 61, -16, WasmBinary.ARRAY_TYPE, Opcodes.OPC_putstatic, 11, 114, Opcodes.OPC_goto, 28, ByteSourceJsonBootstrapper.UTF8_BOM_1, -47, 83, 62, Opcodes.OPC_d2l, 51, 38, 95, -20, 118, 42, 73, Opcodes.OPC_lor, Opcodes.OPC_l2i, -18, 33, Opcodes.OPC_wide, 26, -21, -39, Opcodes.OPC_multianewarray, 57, Opcodes.OPC_ifeq, -51, Opcodes.OPC_lreturn, 49, Opcodes.OPC_f2i, 1, 24, 35, -35, 31, 78, 45, -7, 72, 79, MidiConstants.SONG_POSITION_POBYTEER, 101, Opcodes.OPC_d2i, 120, 92, 88, 25, Opcodes.OPC_f2d, -27, Opcodes.OPC_dcmpg, 87, 103, 127, 5, 100, Opcodes.OPC_dreturn, 99, Opcodes.OPC_invokevirtual, -2, -11, Opcodes.OPC_invokespecial, 60, Opcodes.OPC_if_acmpeq, WasmBinary.REC_GROUP, -23, 104, 68, -32, 77, 67, 105, 41, 46, Opcodes.OPC_ireturn, 21, 89, Opcodes.OPC_jsr, 10, Opcodes.OPC_ifle, 110, 71, WasmBinary.STRUCT_TYPE, 52, 53, 106, WasmBinary.SUB_FINAL_TYPE, -36, 34, Opcodes.OPC_jsr_w, -64, Opcodes.OPC_iflt, Opcodes.OPC_l2f, -44, -19, Opcodes.OPC_lookupswitch, 18, Opcodes.OPC_if_icmpge, 13, 82, -69, 2, 47, Opcodes.OPC_ret, -41, 97, 30, Opcodes.OPC_getfield, 80, 4, -10, Opcodes.OPC_monitorenter, 22, 37, Opcodes.OPC_i2f, 86, 85, 9, Opcodes.OPC_arraylength, Opcodes.OPC_i2b}};
    private static final int P_00 = 1;
    private static final int P_01 = 0;
    private static final int P_02 = 0;
    private static final int P_03 = 1;
    private static final int P_04 = 1;
    private static final int P_10 = 0;
    private static final int P_11 = 0;
    private static final int P_12 = 1;
    private static final int P_13 = 1;
    private static final int P_14 = 0;
    private static final int P_20 = 1;
    private static final int P_21 = 1;
    private static final int P_22 = 0;
    private static final int P_23 = 0;
    private static final int P_24 = 0;
    private static final int P_30 = 0;
    private static final int P_31 = 1;
    private static final int P_32 = 1;
    private static final int P_33 = 0;
    private static final int P_34 = 1;
    private static final int ROUNDS = 16;
    private static final int ROUND_SUBKEYS = 8;
    private static final int RS_GF_FDBK = 333;
    private static final int SK_BUMP = 16843009;
    private static final int SK_ROTL = 9;
    private static final int SK_STEP = 33686018;
    private static final int TOTAL_SUBKEYS = 40;
    private int[] gSBox;
    private int[] gSubKeys;
    private boolean encrypting = false;
    private int[] gMDS0 = new int[256];
    private int[] gMDS1 = new int[256];
    private int[] gMDS2 = new int[256];
    private int[] gMDS3 = new int[256];
    private int k64Cnt = 0;
    private byte[] workingKey = null;

    public TwofishEngine() {
        for (int i = 0; i < 256; i++) {
            byte[][] bArr = P;
            int i2 = bArr[0][i] & 255;
            int iMx_X = Mx_X(i2) & 255;
            int iMx_Y = Mx_Y(i2) & 255;
            int i3 = bArr[1][i] & 255;
            int[] iArr = {i2, i3};
            int[] iArr2 = {iMx_X, Mx_X(i3) & 255};
            int[] iArr3 = {iMx_Y, Mx_Y(i3) & 255};
            int[] iArr4 = this.gMDS0;
            int i4 = iArr[1] | (iArr2[1] << 8);
            int i5 = iArr3[1];
            iArr4[i] = i4 | (i5 << 16) | (i5 << 24);
            int[] iArr5 = this.gMDS1;
            int i6 = iArr3[0];
            iArr5[i] = i6 | (i6 << 8) | (iArr2[0] << 16) | (iArr[0] << 24);
            int[] iArr6 = this.gMDS2;
            int i7 = iArr2[1];
            int i8 = iArr3[1];
            iArr6[i] = (iArr[1] << 16) | i7 | (i8 << 8) | (i8 << 24);
            int[] iArr7 = this.gMDS3;
            int i9 = iArr2[0];
            iArr7[i] = (iArr3[0] << 16) | (iArr[0] << 8) | i9 | (i9 << 24);
        }
    }

    private void Bits32ToBytes(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        bArr[i2 + 1] = (byte) (i >> 8);
        bArr[i2 + 2] = (byte) (i >> 16);
        bArr[i2 + 3] = (byte) (i >> 24);
    }

    private int BytesTo32Bits(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    private int F32(int i, int[] iArr) {
        int i2;
        int i3;
        int iB0 = b0(i);
        int iB1 = b1(i);
        int iB2 = b2(i);
        int iB3 = b3(i);
        int i4 = iArr[0];
        int i5 = iArr[1];
        int i6 = iArr[2];
        int i7 = iArr[3];
        int i8 = this.k64Cnt & 3;
        if (i8 != 0) {
            if (i8 != 1) {
                if (i8 != 2) {
                    if (i8 != 3) {
                        return 0;
                    }
                }
                int[] iArr2 = this.gMDS0;
                byte[][] bArr = P;
                byte[] bArr2 = bArr[0];
                i2 = (iArr2[(bArr2[(bArr2[iB0] & 255) ^ b0(i5)] & 255) ^ b0(i4)] ^ this.gMDS1[(bArr[0][(bArr[1][iB1] & 255) ^ b1(i5)] & 255) ^ b1(i4)]) ^ this.gMDS2[(bArr[1][(bArr[0][iB2] & 255) ^ b2(i5)] & 255) ^ b2(i4)];
                int[] iArr3 = this.gMDS3;
                byte[] bArr3 = bArr[1];
                i3 = iArr3[(bArr3[(bArr3[iB3] & 255) ^ b3(i5)] & 255) ^ b3(i4)];
            } else {
                int[] iArr4 = this.gMDS0;
                byte[][] bArr4 = P;
                i2 = (iArr4[(bArr4[0][iB0] & 255) ^ b0(i4)] ^ this.gMDS1[(bArr4[0][iB1] & 255) ^ b1(i4)]) ^ this.gMDS2[(bArr4[1][iB2] & 255) ^ b2(i4)];
                i3 = this.gMDS3[(bArr4[1][iB3] & 255) ^ b3(i4)];
            }
            return i2 ^ i3;
        }
        byte[][] bArr5 = P;
        iB0 = (bArr5[1][iB0] & 255) ^ b0(i7);
        iB1 = (bArr5[0][iB1] & 255) ^ b1(i7);
        iB2 = (bArr5[0][iB2] & 255) ^ b2(i7);
        iB3 = (bArr5[1][iB3] & 255) ^ b3(i7);
        byte[][] bArr6 = P;
        iB0 = (bArr6[1][iB0] & 255) ^ b0(i6);
        iB1 = (bArr6[1][iB1] & 255) ^ b1(i6);
        iB2 = (bArr6[0][iB2] & 255) ^ b2(i6);
        iB3 = (bArr6[0][iB3] & 255) ^ b3(i6);
        int[] iArr5 = this.gMDS0;
        byte[][] bArr7 = P;
        byte[] bArr8 = bArr7[0];
        i2 = (iArr5[(bArr8[(bArr8[iB0] & 255) ^ b0(i5)] & 255) ^ b0(i4)] ^ this.gMDS1[(bArr7[0][(bArr7[1][iB1] & 255) ^ b1(i5)] & 255) ^ b1(i4)]) ^ this.gMDS2[(bArr7[1][(bArr7[0][iB2] & 255) ^ b2(i5)] & 255) ^ b2(i4)];
        int[] iArr6 = this.gMDS3;
        byte[] bArr9 = bArr7[1];
        i3 = iArr6[(bArr9[(bArr9[iB3] & 255) ^ b3(i5)] & 255) ^ b3(i4)];
        return i2 ^ i3;
    }

    private int Fe32_0(int i) {
        int[] iArr = this.gSBox;
        return iArr[(((i >>> 24) & 255) * 2) + 513] ^ ((iArr[(i & 255) * 2] ^ iArr[(((i >>> 8) & 255) * 2) + 1]) ^ iArr[(((i >>> 16) & 255) * 2) + 512]);
    }

    private int Fe32_3(int i) {
        int[] iArr = this.gSBox;
        return iArr[(((i >>> 16) & 255) * 2) + 513] ^ ((iArr[((i >>> 24) & 255) * 2] ^ iArr[((i & 255) * 2) + 1]) ^ iArr[(((i >>> 8) & 255) * 2) + 512]);
    }

    private int LFSR1(int i) {
        return ((i & 1) != 0 ? 180 : 0) ^ (i >> 1);
    }

    private int LFSR2(int i) {
        return ((i >> 2) ^ ((i & 2) != 0 ? 180 : 0)) ^ ((i & 1) != 0 ? 90 : 0);
    }

    private int Mx_X(int i) {
        return i ^ LFSR2(i);
    }

    private int Mx_Y(int i) {
        return LFSR2(i) ^ (LFSR1(i) ^ i);
    }

    private int RS_MDS_Encode(int i, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            i2 = RS_rem(i2);
        }
        int iRS_rem = i ^ i2;
        for (int i4 = 0; i4 < 4; i4++) {
            iRS_rem = RS_rem(iRS_rem);
        }
        return iRS_rem;
    }

    private int RS_rem(int i) {
        int i2 = i >>> 24;
        int i3 = i2 & 255;
        int i4 = ((i3 << 1) ^ ((i2 & 128) != 0 ? RS_GF_FDBK : 0)) & 255;
        int i5 = ((i3 >>> 1) ^ ((i2 & 1) != 0 ? 166 : 0)) ^ i4;
        return ((((i << 8) ^ (i5 << 24)) ^ (i4 << 16)) ^ (i5 << 8)) ^ i3;
    }

    private int b0(int i) {
        return i & 255;
    }

    private int b1(int i) {
        return (i >>> 8) & 255;
    }

    private int b2(int i) {
        return (i >>> 16) & 255;
    }

    private int b3(int i) {
        return (i >>> 24) & 255;
    }

    private void decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int iBytesTo32Bits = BytesTo32Bits(bArr, i) ^ this.gSubKeys[4];
        int iBytesTo32Bits2 = BytesTo32Bits(bArr, i + 4) ^ this.gSubKeys[5];
        int iBytesTo32Bits3 = BytesTo32Bits(bArr, i + 8) ^ this.gSubKeys[6];
        int iBytesTo32Bits4 = BytesTo32Bits(bArr, i + 12) ^ this.gSubKeys[7];
        int i3 = 39;
        for (int i4 = 0; i4 < 16; i4 += 2) {
            int iFe32_0 = Fe32_0(iBytesTo32Bits);
            int iFe32_3 = Fe32_3(iBytesTo32Bits2);
            int[] iArr = this.gSubKeys;
            int i5 = iBytesTo32Bits4 ^ (((iFe32_3 * 2) + iFe32_0) + iArr[i3]);
            iBytesTo32Bits3 = ((iBytesTo32Bits3 >>> 31) | (iBytesTo32Bits3 << 1)) ^ ((iFe32_0 + iFe32_3) + iArr[i3 - 1]);
            iBytesTo32Bits4 = (i5 << 31) | (i5 >>> 1);
            int iFe32_1 = Fe32_0(iBytesTo32Bits3);
            int iFe32_4 = Fe32_3(iBytesTo32Bits4);
            int[] iArr2 = this.gSubKeys;
            int i6 = i3 - 3;
            int i7 = iBytesTo32Bits2 ^ (((iFe32_4 * 2) + iFe32_1) + iArr2[i3 - 2]);
            i3 -= 4;
            iBytesTo32Bits = ((iBytesTo32Bits >>> 31) | (iBytesTo32Bits << 1)) ^ ((iFe32_1 + iFe32_4) + iArr2[i6]);
            iBytesTo32Bits2 = (i7 << 31) | (i7 >>> 1);
        }
        Bits32ToBytes(this.gSubKeys[0] ^ iBytesTo32Bits3, bArr2, i2);
        Bits32ToBytes(iBytesTo32Bits4 ^ this.gSubKeys[1], bArr2, i2 + 4);
        Bits32ToBytes(this.gSubKeys[2] ^ iBytesTo32Bits, bArr2, i2 + 8);
        Bits32ToBytes(this.gSubKeys[3] ^ iBytesTo32Bits2, bArr2, i2 + 12);
    }

    private void encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int iBytesTo32Bits = BytesTo32Bits(bArr, i) ^ this.gSubKeys[0];
        int iBytesTo32Bits2 = BytesTo32Bits(bArr, i + 4) ^ this.gSubKeys[1];
        int iBytesTo32Bits3 = BytesTo32Bits(bArr, i + 8) ^ this.gSubKeys[2];
        int iBytesTo32Bits4 = BytesTo32Bits(bArr, i + 12) ^ this.gSubKeys[3];
        int i3 = 8;
        for (int i4 = 0; i4 < 16; i4 += 2) {
            int iFe32_0 = Fe32_0(iBytesTo32Bits);
            int iFe32_3 = Fe32_3(iBytesTo32Bits2);
            int[] iArr = this.gSubKeys;
            int i5 = iBytesTo32Bits3 ^ ((iFe32_0 + iFe32_3) + iArr[i3]);
            iBytesTo32Bits3 = (i5 << 31) | (i5 >>> 1);
            iBytesTo32Bits4 = ((iBytesTo32Bits4 >>> 31) | (iBytesTo32Bits4 << 1)) ^ ((iFe32_0 + (iFe32_3 * 2)) + iArr[i3 + 1]);
            int iFe32_1 = Fe32_0(iBytesTo32Bits3);
            int iFe32_4 = Fe32_3(iBytesTo32Bits4);
            int[] iArr2 = this.gSubKeys;
            int i6 = i3 + 3;
            int i7 = iBytesTo32Bits ^ ((iFe32_1 + iFe32_4) + iArr2[i3 + 2]);
            iBytesTo32Bits = (i7 << 31) | (i7 >>> 1);
            i3 += 4;
            iBytesTo32Bits2 = ((iBytesTo32Bits2 >>> 31) | (iBytesTo32Bits2 << 1)) ^ ((iFe32_1 + (iFe32_4 * 2)) + iArr2[i6]);
        }
        Bits32ToBytes(this.gSubKeys[4] ^ iBytesTo32Bits3, bArr2, i2);
        Bits32ToBytes(iBytesTo32Bits4 ^ this.gSubKeys[5], bArr2, i2 + 4);
        Bits32ToBytes(this.gSubKeys[6] ^ iBytesTo32Bits, bArr2, i2 + 8);
        Bits32ToBytes(this.gSubKeys[7] ^ iBytesTo32Bits2, bArr2, i2 + 12);
    }

    private void setKey(byte[] bArr) {
        int iB0;
        int iB1;
        int iB2;
        int iB3;
        int iB4;
        int iB5;
        int iB6;
        int iB7;
        int[] iArr = new int[4];
        int[] iArr2 = new int[4];
        int[] iArr3 = new int[4];
        this.gSubKeys = new int[40];
        int i = this.k64Cnt;
        if (i < 1) {
            throw new IllegalArgumentException("Key size less than 64 bits");
        }
        if (i > 4) {
            throw new IllegalArgumentException("Key size larger than 256 bits");
        }
        for (int i2 = 0; i2 < this.k64Cnt; i2++) {
            int i3 = i2 * 8;
            iArr[i2] = BytesTo32Bits(bArr, i3);
            int iBytesTo32Bits = BytesTo32Bits(bArr, i3 + 4);
            iArr2[i2] = iBytesTo32Bits;
            iArr3[(this.k64Cnt - 1) - i2] = RS_MDS_Encode(iArr[i2], iBytesTo32Bits);
        }
        for (int i4 = 0; i4 < 20; i4++) {
            int i5 = SK_STEP * i4;
            int iF32 = F32(i5, iArr);
            int iF33 = F32(i5 + 16843009, iArr2);
            int i6 = (iF33 >>> 24) | (iF33 << 8);
            int i7 = iF32 + i6;
            int[] iArr4 = this.gSubKeys;
            int i8 = i4 * 2;
            iArr4[i8] = i7;
            int i9 = i7 + i6;
            iArr4[i8 + 1] = (i9 << 9) | (i9 >>> 23);
        }
        int i10 = iArr3[0];
        int i11 = iArr3[1];
        int i12 = 2;
        int i13 = iArr3[2];
        int i14 = iArr3[3];
        this.gSBox = new int[1024];
        int i15 = 0;
        while (i15 < 256) {
            int i16 = this.k64Cnt & 3;
            if (i16 != 0) {
                if (i16 == 1) {
                    int[] iArr5 = this.gSBox;
                    int i17 = i15 * 2;
                    int[] iArr6 = this.gMDS0;
                    byte[][] bArr2 = P;
                    iArr5[i17] = iArr6[(bArr2[0][i15] & 255) ^ b0(i10)];
                    this.gSBox[i17 + 1] = this.gMDS1[(bArr2[0][i15] & 255) ^ b1(i10)];
                    this.gSBox[i17 + 512] = this.gMDS2[(bArr2[1][i15] & 255) ^ b2(i10)];
                    this.gSBox[i17 + 513] = this.gMDS3[(bArr2[1][i15] & 255) ^ b3(i10)];
                } else if (i16 == i12) {
                    iB7 = i15;
                    iB6 = iB7;
                    iB5 = iB6;
                    iB4 = iB5;
                    int[] iArr7 = this.gSBox;
                    int i18 = i15 * 2;
                    int[] iArr8 = this.gMDS0;
                    byte[][] bArr3 = P;
                    byte[] bArr4 = bArr3[0];
                    iArr7[i18] = iArr8[(bArr4[(bArr4[iB6] & 255) ^ b0(i11)] & 255) ^ b0(i10)];
                    this.gSBox[i18 + 1] = this.gMDS1[(bArr3[0][(bArr3[1][iB5] & 255) ^ b1(i11)] & 255) ^ b1(i10)];
                    this.gSBox[i18 + 512] = this.gMDS2[(bArr3[1][(bArr3[0][iB4] & 255) ^ b2(i11)] & 255) ^ b2(i10)];
                    int[] iArr9 = this.gMDS3;
                    byte[] bArr5 = bArr3[1];
                    this.gSBox[i18 + 513] = iArr9[(bArr5[(bArr5[iB7] & 255) ^ b3(i11)] & 255) ^ b3(i10)];
                } else if (i16 == 3) {
                    iB3 = i15;
                    iB0 = iB3;
                    iB1 = iB0;
                    iB2 = iB1;
                }
                i15++;
                i12 = 2;
            } else {
                byte[][] bArr6 = P;
                iB0 = (bArr6[1][i15] & 255) ^ b0(i14);
                iB1 = (bArr6[0][i15] & 255) ^ b1(i14);
                iB2 = (bArr6[0][i15] & 255) ^ b2(i14);
                iB3 = (bArr6[1][i15] & 255) ^ b3(i14);
            }
            byte[][] bArr7 = P;
            iB6 = (bArr7[1][iB0] & 255) ^ b0(i13);
            iB5 = (bArr7[1][iB1] & 255) ^ b1(i13);
            iB4 = (bArr7[0][iB2] & 255) ^ b2(i13);
            iB7 = (bArr7[0][iB3] & 255) ^ b3(i13);
            int[] iArr10 = this.gSBox;
            int i19 = i15 * 2;
            int[] iArr11 = this.gMDS0;
            byte[][] bArr8 = P;
            byte[] bArr9 = bArr8[0];
            iArr10[i19] = iArr11[(bArr9[(bArr9[iB6] & 255) ^ b0(i11)] & 255) ^ b0(i10)];
            this.gSBox[i19 + 1] = this.gMDS1[(bArr8[0][(bArr8[1][iB5] & 255) ^ b1(i11)] & 255) ^ b1(i10)];
            this.gSBox[i19 + 512] = this.gMDS2[(bArr8[1][(bArr8[0][iB4] & 255) ^ b2(i11)] & 255) ^ b2(i10)];
            int[] iArr12 = this.gMDS3;
            byte[] bArr10 = bArr8[1];
            this.gSBox[i19 + 513] = iArr12[(bArr10[(bArr10[iB7] & 255) ^ b3(i11)] & 255) ^ b3(i10)];
            i15++;
            i12 = 2;
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "Twofish";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("invalid parameter passed to Twofish init - " + cipherParameters.getClass().getName());
        }
        this.encrypting = z;
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        this.workingKey = key;
        this.k64Cnt = key.length / 8;
        setKey(key);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey == null) {
            throw new IllegalStateException("Twofish not initialised");
        }
        if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i2 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        if (this.encrypting) {
            encryptBlock(bArr, i, bArr2, i2);
            return 16;
        }
        decryptBlock(bArr, i, bArr2, i2);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        byte[] bArr = this.workingKey;
        if (bArr != null) {
            setKey(bArr);
        }
    }
}
