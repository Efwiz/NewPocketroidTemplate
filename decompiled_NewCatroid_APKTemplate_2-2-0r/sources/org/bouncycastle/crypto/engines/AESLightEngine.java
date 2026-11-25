package org.bouncycastle.crypto.engines;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.gaurav.avnc.vnc.XKeySym;
import java.lang.reflect.Array;
import org.billthefarmer.mididriver.MidiConstants;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Pack;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes13.dex */
public class AESLightEngine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final int m1 = -2139062144;
    private static final int m2 = 2139062143;
    private static final int m3 = 27;
    private static final int m4 = -1061109568;
    private static final int m5 = 1061109567;
    private int C0;
    private int C1;
    private int C2;
    private int C3;
    private int ROUNDS;
    private int[][] WorkingKey = null;
    private boolean forEncryption;
    private static final byte[] S = {99, 124, 119, 123, MidiConstants.SONG_POSITION_POBYTEER, 107, 111, Opcodes.OPC_multianewarray, 48, 1, 103, 43, -2, -41, Opcodes.OPC_lookupswitch, 118, -54, Opcodes.OPC_ixor, Opcodes.OPC_jsr_w, 125, -6, 89, 71, -16, Opcodes.OPC_lreturn, -44, Opcodes.OPC_if_icmpge, Opcodes.OPC_dreturn, Opcodes.OPC_ifge, Opcodes.OPC_if_icmple, 114, -64, Opcodes.OPC_invokespecial, -3, Opcodes.OPC_i2s, 38, 54, 63, -9, -52, 52, Opcodes.OPC_if_acmpeq, -27, MidiConstants.MIDI_TIME_CODE, 113, -40, 49, 21, 4, Opcodes.OPC_ifnonnull, 35, Opcodes.OPC_monitorexit, 24, Opcodes.OPC_fcmpg, 5, Opcodes.OPC_ifne, 7, 18, -128, -30, -21, 39, Opcodes.OPC_getstatic, 117, 9, Opcodes.OPC_lxor, 44, 26, 27, 110, 90, -96, 82, 59, -42, Opcodes.OPC_putstatic, 41, -29, 47, Opcodes.OPC_iinc, 83, -47, 0, -19, 32, -4, Opcodes.OPC_return, 91, 106, -53, Opcodes.OPC_arraylength, 57, 74, 76, 88, WasmBinary.SUB_FINAL_TYPE, -48, ByteSourceJsonBootstrapper.UTF8_BOM_1, Opcodes.OPC_tableswitch, -5, 67, 77, 51, Opcodes.OPC_i2l, 69, -7, 2, 127, 80, 60, Opcodes.OPC_if_icmpeq, Opcodes.OPC_jsr, 81, Opcodes.OPC_if_icmpgt, 64, Opcodes.OPC_d2l, Opcodes.OPC_i2c, Opcodes.OPC_ifgt, 56, -11, -68, Opcodes.OPC_invokevirtual, -38, 33, 16, -1, MidiConstants.SONG_SELECT, -46, -51, 12, 19, -20, 95, Opcodes.OPC_dcmpl, 68, 23, Opcodes.OPC_wide, Opcodes.OPC_goto, 126, 61, 100, 93, 25, 115, 96, Opcodes.OPC_lor, 79, -36, 34, 42, -112, Opcodes.OPC_l2i, 70, -18, Opcodes.OPC_invokestatic, 20, WasmBinary.ARRAY_TYPE, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, Opcodes.OPC_monitorenter, -45, Opcodes.OPC_ireturn, 98, Opcodes.OPC_i2b, Opcodes.OPC_fcmpl, -28, 121, -25, Opcodes.OPC_goto_w, 55, 109, Opcodes.OPC_f2d, -43, 78, Opcodes.OPC_ret, 108, 86, -12, -22, 101, 122, Opcodes.OPC_freturn, 8, Opcodes.OPC_invokedynamic, 120, 37, 46, 28, Opcodes.OPC_if_acmpne, Opcodes.OPC_getfield, Opcodes.OPC_ifnull, -24, -35, 116, 31, 75, Opcodes.OPC_anewarray, Opcodes.OPC_f2i, Opcodes.OPC_l2d, 112, 62, Opcodes.OPC_putfield, 102, 72, 3, -10, 14, 97, 53, 87, Opcodes.OPC_invokeinterface, Opcodes.OPC_i2f, Opcodes.OPC_instanceof, 29, Opcodes.OPC_ifle, -31, -8, Opcodes.OPC_dcmpg, 17, 105, -39, Opcodes.OPC_d2i, Opcodes.OPC_lcmp, Opcodes.OPC_iflt, 30, Opcodes.OPC_i2d, -23, WasmBinary.REC_GROUP, 85, 40, WasmBinary.STRUCT_TYPE, Opcodes.OPC_f2l, Opcodes.OPC_if_icmplt, Opcodes.OPC_l2f, 13, -65, -26, 66, 104, 65, Opcodes.OPC_ifeq, 45, 15, -80, 84, -69, 22};
    private static final byte[] Si = {82, 9, 106, -43, 48, 54, Opcodes.OPC_if_acmpeq, 56, -65, 64, Opcodes.OPC_if_icmpgt, Opcodes.OPC_ifle, Opcodes.OPC_lor, MidiConstants.SONG_SELECT, -41, -5, 124, -29, 57, Opcodes.OPC_ixor, Opcodes.OPC_iflt, 47, -1, Opcodes.OPC_i2d, 52, Opcodes.OPC_d2i, 67, 68, Opcodes.OPC_wide, WasmBinary.ARRAY_TYPE, -23, -53, 84, 123, Opcodes.OPC_lcmp, 50, Opcodes.OPC_if_acmpne, Opcodes.OPC_monitorenter, 35, 61, -18, 76, Opcodes.OPC_fcmpl, 11, 66, -6, Opcodes.OPC_monitorexit, 78, 8, 46, Opcodes.OPC_if_icmplt, 102, 40, -39, 36, Opcodes.OPC_getstatic, 118, 91, Opcodes.OPC_if_icmpge, 73, 109, Opcodes.OPC_f2i, -47, 37, 114, -8, -10, 100, Opcodes.OPC_i2f, 104, Opcodes.OPC_dcmpg, 22, -44, Opcodes.OPC_if_icmple, 92, -52, 93, 101, Opcodes.OPC_invokevirtual, Opcodes.OPC_i2c, 108, 112, 72, 80, -3, -19, Opcodes.OPC_invokeinterface, -38, 94, 21, 70, 87, Opcodes.OPC_goto, Opcodes.OPC_f2d, Opcodes.OPC_ifgt, Opcodes.OPC_iinc, -112, -40, Opcodes.OPC_lookupswitch, 0, Opcodes.OPC_f2l, -68, -45, 10, -9, -28, 88, 5, Opcodes.OPC_invokestatic, Opcodes.OPC_putstatic, 69, 6, -48, 44, 30, Opcodes.OPC_d2l, -54, 63, 15, 2, Opcodes.OPC_instanceof, Opcodes.OPC_dreturn, Opcodes.OPC_anewarray, 3, 1, 19, Opcodes.OPC_l2d, 107, 58, Opcodes.OPC_i2b, 17, 65, 79, 103, -36, -22, Opcodes.OPC_dcmpl, MidiConstants.SONG_POSITION_POBYTEER, WasmBinary.SUB_FINAL_TYPE, WasmBinary.REC_GROUP, -16, Opcodes.OPC_getfield, -26, 115, Opcodes.OPC_fcmpg, Opcodes.OPC_ireturn, 116, 34, -25, Opcodes.OPC_lreturn, 53, Opcodes.OPC_i2l, -30, -7, 55, -24, 28, 117, WasmBinary.STRUCT_TYPE, 110, 71, MidiConstants.MIDI_TIME_CODE, 26, 113, 29, 41, Opcodes.OPC_multianewarray, Opcodes.OPC_l2f, 111, Opcodes.OPC_invokespecial, 98, 14, Opcodes.OPC_tableswitch, 24, Opcodes.OPC_arraylength, 27, -4, 86, 62, 75, Opcodes.OPC_ifnull, -46, 121, 32, Opcodes.OPC_ifne, -37, -64, -2, 120, -51, 90, -12, 31, -35, Opcodes.OPC_jsr, 51, Opcodes.OPC_l2i, 7, Opcodes.OPC_ifnonnull, 49, Opcodes.OPC_return, 18, 16, 89, 39, -128, -20, 95, 96, 81, 127, Opcodes.OPC_ret, 25, Opcodes.OPC_putfield, 74, 13, 45, -27, 122, Opcodes.OPC_if_icmpeq, Opcodes.OPC_i2s, Opcodes.OPC_jsr_w, Opcodes.OPC_ifge, ByteSourceJsonBootstrapper.UTF8_BOM_1, -96, -32, 59, 77, Opcodes.OPC_freturn, 42, -11, -80, Opcodes.OPC_goto_w, -21, -69, 60, Opcodes.OPC_lxor, 83, Opcodes.OPC_ifeq, 97, 23, 43, 4, 126, Opcodes.OPC_invokedynamic, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125};
    private static final int[] rcon = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, XKeySym.XK_idiaeresis, 197, 145};

    public AESLightEngine() {
    }

    private static int FFmulX(int i) {
        return (((i & m1) >>> 7) * 27) ^ ((m2 & i) << 1);
    }

    private static int FFmulX2(int i) {
        int i2 = (m5 & i) << 2;
        int i3 = i & m4;
        int i4 = i3 ^ (i3 >>> 1);
        return (i4 >>> 5) ^ (i2 ^ (i4 >>> 2));
    }

    private void decryptBlock(int[][] iArr) {
        int i = this.C0;
        int i2 = this.ROUNDS;
        int[] iArr2 = iArr[i2];
        int i3 = i ^ iArr2[0];
        int i4 = this.C1 ^ iArr2[1];
        int i5 = this.C2 ^ iArr2[2];
        int i6 = i2 - 1;
        int iInv_mcol = iArr2[3] ^ this.C3;
        while (true) {
            byte[] bArr = Si;
            int i7 = i3 & 255;
            if (i6 <= 1) {
                int iInv_mcol2 = inv_mcol((((bArr[i7] & 255) ^ ((bArr[(iInv_mcol >> 8) & 255] & 255) << 8)) ^ ((bArr[(i5 >> 16) & 255] & 255) << 16)) ^ (bArr[(i4 >> 24) & 255] << 24)) ^ iArr[i6][0];
                int iInv_mcol3 = inv_mcol((((bArr[i4 & 255] & 255) ^ ((bArr[(i3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol >> 16) & 255] & 255) << 16)) ^ (bArr[(i5 >> 24) & 255] << 24)) ^ iArr[i6][1];
                int iInv_mcol4 = inv_mcol((((bArr[i5 & 255] & 255) ^ ((bArr[(i4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i3 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol >> 24) & 255] << 24)) ^ iArr[i6][2];
                int iInv_mcol5 = inv_mcol((bArr[(i3 >> 24) & 255] << 24) ^ (((bArr[iInv_mcol & 255] & 255) ^ ((bArr[(i5 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i4 >> 16) & 255] & 255) << 16))) ^ iArr[i6][3];
                int i8 = (((bArr[iInv_mcol2 & 255] & 255) ^ ((bArr[(iInv_mcol5 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol4 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol3 >> 24) & 255] << 24);
                int[] iArr3 = iArr[0];
                this.C0 = i8 ^ iArr3[0];
                this.C1 = ((((bArr[iInv_mcol3 & 255] & 255) ^ ((bArr[(iInv_mcol2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol5 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol4 >> 24) & 255] << 24)) ^ iArr3[1];
                this.C2 = ((((bArr[iInv_mcol4 & 255] & 255) ^ ((bArr[(iInv_mcol3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol2 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol5 >> 24) & 255] << 24)) ^ iArr3[2];
                this.C3 = ((((bArr[iInv_mcol5 & 255] & 255) ^ ((bArr[(iInv_mcol4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol3 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol2 >> 24) & 255] << 24)) ^ iArr3[3];
                return;
            }
            int iInv_mcol6 = inv_mcol((((bArr[i7] & 255) ^ ((bArr[(iInv_mcol >> 8) & 255] & 255) << 8)) ^ ((bArr[(i5 >> 16) & 255] & 255) << 16)) ^ (bArr[(i4 >> 24) & 255] << 24)) ^ iArr[i6][0];
            int iInv_mcol7 = inv_mcol((((bArr[i4 & 255] & 255) ^ ((bArr[(i3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol >> 16) & 255] & 255) << 16)) ^ (bArr[(i5 >> 24) & 255] << 24)) ^ iArr[i6][1];
            int iInv_mcol8 = inv_mcol((((bArr[i5 & 255] & 255) ^ ((bArr[(i4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i3 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol >> 24) & 255] << 24)) ^ iArr[i6][2];
            int iInv_mcol9 = inv_mcol((bArr[(i3 >> 24) & 255] << 24) ^ (((bArr[iInv_mcol & 255] & 255) ^ ((bArr[(i5 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i4 >> 16) & 255] & 255) << 16)));
            int i9 = i6 - 1;
            int i10 = iInv_mcol9 ^ iArr[i6][3];
            int iInv_mcol10 = inv_mcol((((bArr[iInv_mcol6 & 255] & 255) ^ ((bArr[(i10 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol8 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol7 >> 24) & 255] << 24)) ^ iArr[i9][0];
            int iInv_mcol11 = inv_mcol((((bArr[iInv_mcol7 & 255] & 255) ^ ((bArr[(iInv_mcol6 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i10 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol8 >> 24) & 255] << 24)) ^ iArr[i9][1];
            int iInv_mcol12 = inv_mcol((((bArr[iInv_mcol8 & 255] & 255) ^ ((bArr[(iInv_mcol7 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol6 >> 16) & 255] & 255) << 16)) ^ (bArr[(i10 >> 24) & 255] << 24)) ^ iArr[i9][2];
            i6 -= 2;
            iInv_mcol = iArr[i9][3] ^ inv_mcol((((bArr[i10 & 255] & 255) ^ ((bArr[(iInv_mcol8 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iInv_mcol7 >> 16) & 255] & 255) << 16)) ^ (bArr[(iInv_mcol6 >> 24) & 255] << 24));
            i3 = iInv_mcol10;
            i4 = iInv_mcol11;
            i5 = iInv_mcol12;
        }
    }

    private void encryptBlock(int[][] iArr) {
        int i = this.C0;
        int[] iArr2 = iArr[0];
        int i2 = i ^ iArr2[0];
        int i3 = this.C1 ^ iArr2[1];
        int i4 = this.C2 ^ iArr2[2];
        int iMcol = iArr2[3] ^ this.C3;
        int i5 = 1;
        while (i5 < this.ROUNDS - 1) {
            byte[] bArr = S;
            int iMcol2 = mcol((((bArr[i2 & 255] & 255) ^ ((bArr[(i3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i4 >> 16) & 255] & 255) << 16)) ^ (bArr[(iMcol >> 24) & 255] << 24)) ^ iArr[i5][0];
            int iMcol3 = mcol((((bArr[i3 & 255] & 255) ^ ((bArr[(i4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iMcol >> 16) & 255] & 255) << 16)) ^ (bArr[(i2 >> 24) & 255] << 24)) ^ iArr[i5][1];
            int iMcol4 = mcol((((bArr[i4 & 255] & 255) ^ ((bArr[(iMcol >> 8) & 255] & 255) << 8)) ^ ((bArr[(i2 >> 16) & 255] & 255) << 16)) ^ (bArr[(i3 >> 24) & 255] << 24)) ^ iArr[i5][2];
            int iMcol5 = mcol(((((bArr[(i2 >> 8) & 255] & 255) << 8) ^ (bArr[iMcol & 255] & 255)) ^ ((bArr[(i3 >> 16) & 255] & 255) << 16)) ^ (bArr[(i4 >> 24) & 255] << 24));
            int i6 = i5 + 1;
            int i7 = iMcol5 ^ iArr[i5][3];
            int iMcol6 = mcol((((bArr[iMcol2 & 255] & 255) ^ ((bArr[(iMcol3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iMcol4 >> 16) & 255] & 255) << 16)) ^ (bArr[(i7 >> 24) & 255] << 24)) ^ iArr[i6][0];
            int iMcol7 = mcol((((bArr[iMcol3 & 255] & 255) ^ ((bArr[(iMcol4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i7 >> 16) & 255] & 255) << 16)) ^ (bArr[(iMcol2 >> 24) & 255] << 24)) ^ iArr[i6][1];
            int iMcol8 = mcol((((bArr[iMcol4 & 255] & 255) ^ ((bArr[(i7 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iMcol2 >> 16) & 255] & 255) << 16)) ^ (bArr[(iMcol3 >> 24) & 255] << 24)) ^ iArr[i6][2];
            i5 += 2;
            iMcol = iArr[i6][3] ^ mcol((((bArr[i7 & 255] & 255) ^ ((bArr[(iMcol2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iMcol3 >> 16) & 255] & 255) << 16)) ^ (bArr[(iMcol4 >> 24) & 255] << 24));
            i2 = iMcol6;
            i3 = iMcol7;
            i4 = iMcol8;
        }
        byte[] bArr2 = S;
        int iMcol9 = mcol((((bArr2[i2 & 255] & 255) ^ ((bArr2[(i3 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(i4 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iMcol >> 24) & 255] << 24)) ^ iArr[i5][0];
        int iMcol10 = mcol((((bArr2[i3 & 255] & 255) ^ ((bArr2[(i4 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iMcol >> 16) & 255] & 255) << 16)) ^ (bArr2[(i2 >> 24) & 255] << 24)) ^ iArr[i5][1];
        int iMcol11 = mcol((((bArr2[i4 & 255] & 255) ^ ((bArr2[(iMcol >> 8) & 255] & 255) << 8)) ^ ((bArr2[(i2 >> 16) & 255] & 255) << 16)) ^ (bArr2[(i3 >> 24) & 255] << 24)) ^ iArr[i5][2];
        int iMcol12 = mcol(((((bArr2[(i2 >> 8) & 255] & 255) << 8) ^ (bArr2[iMcol & 255] & 255)) ^ ((bArr2[(i3 >> 16) & 255] & 255) << 16)) ^ (bArr2[(i4 >> 24) & 255] << 24)) ^ iArr[i5][3];
        int i8 = (((bArr2[iMcol9 & 255] & 255) ^ ((bArr2[(iMcol10 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iMcol11 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iMcol12 >> 24) & 255] << 24);
        int[] iArr3 = iArr[i5 + 1];
        this.C0 = iArr3[0] ^ i8;
        this.C1 = ((((bArr2[iMcol10 & 255] & 255) ^ ((bArr2[(iMcol11 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iMcol12 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iMcol9 >> 24) & 255] << 24)) ^ iArr3[1];
        this.C2 = ((((bArr2[iMcol11 & 255] & 255) ^ ((bArr2[(iMcol12 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iMcol9 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iMcol10 >> 24) & 255] << 24)) ^ iArr3[2];
        this.C3 = ((((bArr2[iMcol12 & 255] & 255) ^ ((bArr2[(iMcol9 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iMcol10 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iMcol11 >> 24) & 255] << 24)) ^ iArr3[3];
    }

    private int[][] generateWorkingKey(byte[] bArr, boolean z) {
        int length = bArr.length;
        if (length < 16 || length > 32 || (length & 7) != 0) {
            throw new IllegalArgumentException("Key length not 128/192/256 bits.");
        }
        int i = length >> 2;
        this.ROUNDS = i + 6;
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i + 7, 4);
        if (i == 4) {
            int iLittleEndianToInt = Pack.littleEndianToInt(bArr, 0);
            iArr[0][0] = iLittleEndianToInt;
            int iLittleEndianToInt2 = Pack.littleEndianToInt(bArr, 4);
            iArr[0][1] = iLittleEndianToInt2;
            int iLittleEndianToInt3 = Pack.littleEndianToInt(bArr, 8);
            iArr[0][2] = iLittleEndianToInt3;
            int iLittleEndianToInt4 = Pack.littleEndianToInt(bArr, 12);
            iArr[0][3] = iLittleEndianToInt4;
            for (int i2 = 1; i2 <= 10; i2++) {
                iLittleEndianToInt ^= subWord(shift(iLittleEndianToInt4, 8)) ^ rcon[i2 - 1];
                int[] iArr2 = iArr[i2];
                iArr2[0] = iLittleEndianToInt;
                iLittleEndianToInt2 ^= iLittleEndianToInt;
                iArr2[1] = iLittleEndianToInt2;
                iLittleEndianToInt3 ^= iLittleEndianToInt2;
                iArr2[2] = iLittleEndianToInt3;
                iLittleEndianToInt4 ^= iLittleEndianToInt3;
                iArr2[3] = iLittleEndianToInt4;
            }
        } else if (i == 6) {
            int iLittleEndianToInt5 = Pack.littleEndianToInt(bArr, 0);
            iArr[0][0] = iLittleEndianToInt5;
            int iLittleEndianToInt6 = Pack.littleEndianToInt(bArr, 4);
            iArr[0][1] = iLittleEndianToInt6;
            int iLittleEndianToInt7 = Pack.littleEndianToInt(bArr, 8);
            iArr[0][2] = iLittleEndianToInt7;
            int iLittleEndianToInt8 = Pack.littleEndianToInt(bArr, 12);
            iArr[0][3] = iLittleEndianToInt8;
            int iLittleEndianToInt9 = Pack.littleEndianToInt(bArr, 16);
            iArr[1][0] = iLittleEndianToInt9;
            int iLittleEndianToInt10 = Pack.littleEndianToInt(bArr, 20);
            iArr[1][1] = iLittleEndianToInt10;
            int iSubWord = iLittleEndianToInt5 ^ (subWord(shift(iLittleEndianToInt10, 8)) ^ 1);
            int[] iArr3 = iArr[1];
            iArr3[2] = iSubWord;
            int i3 = iLittleEndianToInt6 ^ iSubWord;
            iArr3[3] = i3;
            int i4 = iLittleEndianToInt7 ^ i3;
            int[] iArr4 = iArr[2];
            iArr4[0] = i4;
            int i5 = iLittleEndianToInt8 ^ i4;
            iArr4[1] = i5;
            int i6 = iLittleEndianToInt9 ^ i5;
            iArr4[2] = i6;
            int i7 = iLittleEndianToInt10 ^ i6;
            iArr4[3] = i7;
            int i8 = 2;
            for (int i9 = 3; i9 < 12; i9 += 3) {
                int iSubWord2 = iSubWord ^ (subWord(shift(i7, 8)) ^ i8);
                int[] iArr5 = iArr[i9];
                iArr5[0] = iSubWord2;
                int i10 = i3 ^ iSubWord2;
                iArr5[1] = i10;
                int i11 = i4 ^ i10;
                iArr5[2] = i11;
                int i12 = i5 ^ i11;
                iArr5[3] = i12;
                int i13 = i6 ^ i12;
                int i14 = i9 + 1;
                int[] iArr6 = iArr[i14];
                iArr6[0] = i13;
                int i15 = i7 ^ i13;
                iArr6[1] = i15;
                int iSubWord3 = subWord(shift(i15, 8)) ^ (i8 << 1);
                i8 <<= 2;
                iSubWord = iSubWord2 ^ iSubWord3;
                int[] iArr7 = iArr[i14];
                iArr7[2] = iSubWord;
                i3 = i10 ^ iSubWord;
                iArr7[3] = i3;
                i4 = i11 ^ i3;
                int[] iArr8 = iArr[i9 + 2];
                iArr8[0] = i4;
                i5 = i12 ^ i4;
                iArr8[1] = i5;
                i6 = i13 ^ i5;
                iArr8[2] = i6;
                i7 = i15 ^ i6;
                iArr8[3] = i7;
            }
            int iSubWord4 = (subWord(shift(i7, 8)) ^ i8) ^ iSubWord;
            int[] iArr9 = iArr[12];
            iArr9[0] = iSubWord4;
            int i16 = iSubWord4 ^ i3;
            iArr9[1] = i16;
            int i17 = i16 ^ i4;
            iArr9[2] = i17;
            iArr9[3] = i17 ^ i5;
        } else {
            if (i != 8) {
                throw new IllegalStateException("Should never get here");
            }
            int iLittleEndianToInt11 = Pack.littleEndianToInt(bArr, 0);
            iArr[0][0] = iLittleEndianToInt11;
            int iLittleEndianToInt12 = Pack.littleEndianToInt(bArr, 4);
            iArr[0][1] = iLittleEndianToInt12;
            int iLittleEndianToInt13 = Pack.littleEndianToInt(bArr, 8);
            iArr[0][2] = iLittleEndianToInt13;
            int iLittleEndianToInt14 = Pack.littleEndianToInt(bArr, 12);
            iArr[0][3] = iLittleEndianToInt14;
            int iLittleEndianToInt15 = Pack.littleEndianToInt(bArr, 16);
            iArr[1][0] = iLittleEndianToInt15;
            int iLittleEndianToInt16 = Pack.littleEndianToInt(bArr, 20);
            iArr[1][1] = iLittleEndianToInt16;
            int iLittleEndianToInt17 = Pack.littleEndianToInt(bArr, 24);
            iArr[1][2] = iLittleEndianToInt17;
            int iLittleEndianToInt18 = Pack.littleEndianToInt(bArr, 28);
            iArr[1][3] = iLittleEndianToInt18;
            int i18 = 1;
            for (int i19 = 2; i19 < 14; i19 += 2) {
                int iSubWord5 = subWord(shift(iLittleEndianToInt18, 8)) ^ i18;
                i18 <<= 1;
                iLittleEndianToInt11 ^= iSubWord5;
                int[] iArr10 = iArr[i19];
                iArr10[0] = iLittleEndianToInt11;
                iLittleEndianToInt12 ^= iLittleEndianToInt11;
                iArr10[1] = iLittleEndianToInt12;
                iLittleEndianToInt13 ^= iLittleEndianToInt12;
                iArr10[2] = iLittleEndianToInt13;
                iLittleEndianToInt14 ^= iLittleEndianToInt13;
                iArr10[3] = iLittleEndianToInt14;
                iLittleEndianToInt15 ^= subWord(iLittleEndianToInt14);
                int[] iArr11 = iArr[i19 + 1];
                iArr11[0] = iLittleEndianToInt15;
                iLittleEndianToInt16 ^= iLittleEndianToInt15;
                iArr11[1] = iLittleEndianToInt16;
                iLittleEndianToInt17 ^= iLittleEndianToInt16;
                iArr11[2] = iLittleEndianToInt17;
                iLittleEndianToInt18 ^= iLittleEndianToInt17;
                iArr11[3] = iLittleEndianToInt18;
            }
            int iSubWord6 = (subWord(shift(iLittleEndianToInt18, 8)) ^ i18) ^ iLittleEndianToInt11;
            int[] iArr12 = iArr[14];
            iArr12[0] = iSubWord6;
            int i20 = iSubWord6 ^ iLittleEndianToInt12;
            iArr12[1] = i20;
            int i21 = i20 ^ iLittleEndianToInt13;
            iArr12[2] = i21;
            iArr12[3] = i21 ^ iLittleEndianToInt14;
        }
        if (!z) {
            for (int i22 = 1; i22 < this.ROUNDS; i22++) {
                for (int i23 = 0; i23 < 4; i23++) {
                    int[] iArr13 = iArr[i22];
                    iArr13[i23] = inv_mcol(iArr13[i23]);
                }
            }
        }
        return iArr;
    }

    private static int inv_mcol(int i) {
        int iShift = shift(i, 8) ^ i;
        int iFFmulX = i ^ FFmulX(iShift);
        int iFFmulX2 = iShift ^ FFmulX2(iFFmulX);
        return iFFmulX ^ (iFFmulX2 ^ shift(iFFmulX2, 16));
    }

    private static int mcol(int i) {
        int iShift = shift(i, 8);
        int i2 = i ^ iShift;
        return FFmulX(i2) ^ (iShift ^ shift(i2, 16));
    }

    private void packBlock(byte[] bArr, int i) {
        int i2 = this.C0;
        bArr[i] = (byte) i2;
        bArr[i + 1] = (byte) (i2 >> 8);
        bArr[i + 2] = (byte) (i2 >> 16);
        bArr[i + 3] = (byte) (i2 >> 24);
        int i3 = this.C1;
        bArr[i + 4] = (byte) i3;
        bArr[i + 5] = (byte) (i3 >> 8);
        bArr[i + 6] = (byte) (i3 >> 16);
        bArr[i + 7] = (byte) (i3 >> 24);
        int i4 = this.C2;
        bArr[i + 8] = (byte) i4;
        bArr[i + 9] = (byte) (i4 >> 8);
        bArr[i + 10] = (byte) (i4 >> 16);
        bArr[i + 11] = (byte) (i4 >> 24);
        int i5 = this.C3;
        bArr[i + 12] = (byte) i5;
        bArr[i + 13] = (byte) (i5 >> 8);
        bArr[i + 14] = (byte) (i5 >> 16);
        bArr[i + 15] = (byte) (i5 >> 24);
    }

    private static int shift(int i, int i2) {
        return (i << (-i2)) | (i >>> i2);
    }

    private static int subWord(int i) {
        byte[] bArr = S;
        return (bArr[(i >> 24) & 255] << 24) | (bArr[i & 255] & 255) | ((bArr[(i >> 8) & 255] & 255) << 8) | ((bArr[(i >> 16) & 255] & 255) << 16);
    }

    private void unpackBlock(byte[] bArr, int i) {
        int i2 = bArr[i] & 255;
        this.C0 = i2;
        int i3 = ((bArr[i + 1] & 255) << 8) | i2;
        this.C0 = i3;
        int i4 = i3 | ((bArr[i + 2] & 255) << 16);
        this.C0 = i4;
        this.C0 = i4 | (bArr[i + 3] << 24);
        int i5 = bArr[i + 4] & 255;
        this.C1 = i5;
        int i6 = ((bArr[i + 5] & 255) << 8) | i5;
        this.C1 = i6;
        int i7 = i6 | ((bArr[i + 6] & 255) << 16);
        this.C1 = i7;
        this.C1 = i7 | (bArr[i + 7] << 24);
        int i8 = bArr[i + 8] & 255;
        this.C2 = i8;
        int i9 = ((bArr[i + 9] & 255) << 8) | i8;
        this.C2 = i9;
        int i10 = i9 | ((bArr[i + 10] & 255) << 16);
        this.C2 = i10;
        this.C2 = i10 | (bArr[i + 11] << 24);
        int i11 = bArr[i + 12] & 255;
        this.C3 = i11;
        int i12 = ((bArr[i + 13] & 255) << 8) | i11;
        this.C3 = i12;
        int i13 = i12 | ((bArr[i + 14] & 255) << 16);
        this.C3 = i13;
        this.C3 = (bArr[i + 15] << 24) | i13;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "AES";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("invalid parameter passed to AES init - " + cipherParameters.getClass().getName());
        }
        this.WorkingKey = generateWorkingKey(((KeyParameter) cipherParameters).getKey(), z);
        this.forEncryption = z;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.WorkingKey == null) {
            throw new IllegalStateException("AES engine not initialised");
        }
        if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i2 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        boolean z = this.forEncryption;
        unpackBlock(bArr, i);
        int[][] iArr = this.WorkingKey;
        if (z) {
            encryptBlock(iArr);
        } else {
            decryptBlock(iArr);
        }
        packBlock(bArr2, i2);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
