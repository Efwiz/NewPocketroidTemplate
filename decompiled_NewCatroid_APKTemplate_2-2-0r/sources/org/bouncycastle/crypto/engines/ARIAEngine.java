package org.bouncycastle.crypto.engines;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import java.lang.reflect.Array;
import org.billthefarmer.mididriver.MidiConstants;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes13.dex */
public class ARIAEngine implements BlockCipher {
    protected static final int BLOCK_SIZE = 16;
    private static final byte[][] C = {Hex.decode("517cc1b727220a94fe13abe8fa9a6ee0"), Hex.decode("6db14acc9e21c820ff28b1d5ef5de2b0"), Hex.decode("db92371d2126e9700324977504e8c90e")};
    private static final byte[] SB1_sbox = {99, 124, 119, 123, MidiConstants.SONG_POSITION_POBYTEER, 107, 111, Opcodes.OPC_multianewarray, 48, 1, 103, 43, -2, -41, Opcodes.OPC_lookupswitch, 118, -54, Opcodes.OPC_ixor, Opcodes.OPC_jsr_w, 125, -6, 89, 71, -16, Opcodes.OPC_lreturn, -44, Opcodes.OPC_if_icmpge, Opcodes.OPC_dreturn, Opcodes.OPC_ifge, Opcodes.OPC_if_icmple, 114, -64, Opcodes.OPC_invokespecial, -3, Opcodes.OPC_i2s, 38, 54, 63, -9, -52, 52, Opcodes.OPC_if_acmpeq, -27, MidiConstants.MIDI_TIME_CODE, 113, -40, 49, 21, 4, Opcodes.OPC_ifnonnull, 35, Opcodes.OPC_monitorexit, 24, Opcodes.OPC_fcmpg, 5, Opcodes.OPC_ifne, 7, 18, -128, -30, -21, 39, Opcodes.OPC_getstatic, 117, 9, Opcodes.OPC_lxor, 44, 26, 27, 110, 90, -96, 82, 59, -42, Opcodes.OPC_putstatic, 41, -29, 47, Opcodes.OPC_iinc, 83, -47, 0, -19, 32, -4, Opcodes.OPC_return, 91, 106, -53, Opcodes.OPC_arraylength, 57, 74, 76, 88, WasmBinary.SUB_FINAL_TYPE, -48, ByteSourceJsonBootstrapper.UTF8_BOM_1, Opcodes.OPC_tableswitch, -5, 67, 77, 51, Opcodes.OPC_i2l, 69, -7, 2, 127, 80, 60, Opcodes.OPC_if_icmpeq, Opcodes.OPC_jsr, 81, Opcodes.OPC_if_icmpgt, 64, Opcodes.OPC_d2l, Opcodes.OPC_i2c, Opcodes.OPC_ifgt, 56, -11, -68, Opcodes.OPC_invokevirtual, -38, 33, 16, -1, MidiConstants.SONG_SELECT, -46, -51, 12, 19, -20, 95, Opcodes.OPC_dcmpl, 68, 23, Opcodes.OPC_wide, Opcodes.OPC_goto, 126, 61, 100, 93, 25, 115, 96, Opcodes.OPC_lor, 79, -36, 34, 42, -112, Opcodes.OPC_l2i, 70, -18, Opcodes.OPC_invokestatic, 20, WasmBinary.ARRAY_TYPE, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, Opcodes.OPC_monitorenter, -45, Opcodes.OPC_ireturn, 98, Opcodes.OPC_i2b, Opcodes.OPC_fcmpl, -28, 121, -25, Opcodes.OPC_goto_w, 55, 109, Opcodes.OPC_f2d, -43, 78, Opcodes.OPC_ret, 108, 86, -12, -22, 101, 122, Opcodes.OPC_freturn, 8, Opcodes.OPC_invokedynamic, 120, 37, 46, 28, Opcodes.OPC_if_acmpne, Opcodes.OPC_getfield, Opcodes.OPC_ifnull, -24, -35, 116, 31, 75, Opcodes.OPC_anewarray, Opcodes.OPC_f2i, Opcodes.OPC_l2d, 112, 62, Opcodes.OPC_putfield, 102, 72, 3, -10, 14, 97, 53, 87, Opcodes.OPC_invokeinterface, Opcodes.OPC_i2f, Opcodes.OPC_instanceof, 29, Opcodes.OPC_ifle, -31, -8, Opcodes.OPC_dcmpg, 17, 105, -39, Opcodes.OPC_d2i, Opcodes.OPC_lcmp, Opcodes.OPC_iflt, 30, Opcodes.OPC_i2d, -23, WasmBinary.REC_GROUP, 85, 40, WasmBinary.STRUCT_TYPE, Opcodes.OPC_f2l, Opcodes.OPC_if_icmplt, Opcodes.OPC_l2f, 13, -65, -26, 66, 104, 65, Opcodes.OPC_ifeq, 45, 15, -80, 84, -69, 22};
    private static final byte[] SB2_sbox = {-30, 78, 84, -4, Opcodes.OPC_lcmp, Opcodes.OPC_monitorenter, 74, -52, 98, 13, 106, 70, 60, 77, Opcodes.OPC_f2i, -47, 94, -6, 100, -53, Opcodes.OPC_getfield, Opcodes.OPC_dcmpl, Opcodes.OPC_arraylength, 43, -68, 119, 46, 3, -45, 25, 89, Opcodes.OPC_instanceof, 29, 6, 65, 107, 85, -16, Opcodes.OPC_ifeq, 105, -22, Opcodes.OPC_ifge, 24, Opcodes.OPC_freturn, 99, WasmBinary.STRUCT_TYPE, -25, -69, 0, 115, 102, -5, Opcodes.OPC_fcmpg, 76, Opcodes.OPC_i2l, -28, 58, 9, 69, Opcodes.OPC_tableswitch, 15, -18, 16, -21, 45, 127, -12, 41, Opcodes.OPC_ireturn, WasmBinary.SUB_FINAL_TYPE, Opcodes.OPC_lreturn, Opcodes.OPC_i2b, Opcodes.OPC_f2d, 120, Opcodes.OPC_goto_w, Opcodes.OPC_fcmpl, -7, 47, WasmBinary.REC_GROUP, -51, 8, 122, Opcodes.OPC_l2i, 56, 92, Opcodes.OPC_lxor, 42, 40, 71, -37, Opcodes.OPC_invokestatic, Opcodes.OPC_ifnonnull, Opcodes.OPC_i2s, Opcodes.OPC_if_icmple, 18, 83, -1, Opcodes.OPC_i2d, 14, 49, 54, 33, 88, 72, 1, Opcodes.OPC_d2i, 55, 116, 50, -54, -23, Opcodes.OPC_return, Opcodes.OPC_invokespecial, Opcodes.OPC_lookupswitch, 12, -41, Opcodes.OPC_wide, 86, 66, 38, 7, Opcodes.OPC_dcmpg, 96, -39, Opcodes.OPC_invokevirtual, Opcodes.OPC_invokeinterface, 17, 64, -20, 32, Opcodes.OPC_f2l, Opcodes.OPC_anewarray, -96, Opcodes.OPC_jsr_w, Opcodes.OPC_iinc, 4, 73, 35, MidiConstants.MIDI_TIME_CODE, 79, 80, 31, 19, -36, -40, -64, Opcodes.OPC_ifle, 87, -29, Opcodes.OPC_monitorexit, 123, 101, 59, 2, Opcodes.OPC_d2l, 62, -24, 37, Opcodes.OPC_i2c, -27, 21, -35, -3, 23, Opcodes.OPC_ret, -65, -44, Opcodes.OPC_ifne, 126, Opcodes.OPC_multianewarray, 57, 103, -2, 118, Opcodes.OPC_ifgt, 67, Opcodes.OPC_goto, -31, -48, -11, 104, MidiConstants.SONG_POSITION_POBYTEER, 27, 52, 112, 5, Opcodes.OPC_if_icmpgt, Opcodes.OPC_l2d, -43, 121, Opcodes.OPC_i2f, Opcodes.OPC_jsr, 48, Opcodes.OPC_ifnull, 81, 75, 30, Opcodes.OPC_if_acmpne, 39, -10, 53, -46, 110, 36, 22, Opcodes.OPC_ixor, 95, -38, -26, 117, Opcodes.OPC_if_icmpge, ByteSourceJsonBootstrapper.UTF8_BOM_1, 44, Opcodes.OPC_getstatic, 28, Opcodes.OPC_if_icmpeq, 93, 111, -128, 10, 114, 68, Opcodes.OPC_iflt, 108, -112, 11, 91, 51, 125, 90, 82, MidiConstants.SONG_SELECT, 97, Opcodes.OPC_if_icmplt, -9, -80, -42, 63, 124, 109, -19, 20, -32, Opcodes.OPC_if_acmpeq, 61, 34, Opcodes.OPC_putstatic, -8, Opcodes.OPC_l2f, WasmBinary.ARRAY_TYPE, 113, 26, Opcodes.OPC_dreturn, Opcodes.OPC_invokedynamic, Opcodes.OPC_putfield, Opcodes.OPC_lor};
    private static final byte[] SB3_sbox = {82, 9, 106, -43, 48, 54, Opcodes.OPC_if_acmpeq, 56, -65, 64, Opcodes.OPC_if_icmpgt, Opcodes.OPC_ifle, Opcodes.OPC_lor, MidiConstants.SONG_SELECT, -41, -5, 124, -29, 57, Opcodes.OPC_ixor, Opcodes.OPC_iflt, 47, -1, Opcodes.OPC_i2d, 52, Opcodes.OPC_d2i, 67, 68, Opcodes.OPC_wide, WasmBinary.ARRAY_TYPE, -23, -53, 84, 123, Opcodes.OPC_lcmp, 50, Opcodes.OPC_if_acmpne, Opcodes.OPC_monitorenter, 35, 61, -18, 76, Opcodes.OPC_fcmpl, 11, 66, -6, Opcodes.OPC_monitorexit, 78, 8, 46, Opcodes.OPC_if_icmplt, 102, 40, -39, 36, Opcodes.OPC_getstatic, 118, 91, Opcodes.OPC_if_icmpge, 73, 109, Opcodes.OPC_f2i, -47, 37, 114, -8, -10, 100, Opcodes.OPC_i2f, 104, Opcodes.OPC_dcmpg, 22, -44, Opcodes.OPC_if_icmple, 92, -52, 93, 101, Opcodes.OPC_invokevirtual, Opcodes.OPC_i2c, 108, 112, 72, 80, -3, -19, Opcodes.OPC_invokeinterface, -38, 94, 21, 70, 87, Opcodes.OPC_goto, Opcodes.OPC_f2d, Opcodes.OPC_ifgt, Opcodes.OPC_iinc, -112, -40, Opcodes.OPC_lookupswitch, 0, Opcodes.OPC_f2l, -68, -45, 10, -9, -28, 88, 5, Opcodes.OPC_invokestatic, Opcodes.OPC_putstatic, 69, 6, -48, 44, 30, Opcodes.OPC_d2l, -54, 63, 15, 2, Opcodes.OPC_instanceof, Opcodes.OPC_dreturn, Opcodes.OPC_anewarray, 3, 1, 19, Opcodes.OPC_l2d, 107, 58, Opcodes.OPC_i2b, 17, 65, 79, 103, -36, -22, Opcodes.OPC_dcmpl, MidiConstants.SONG_POSITION_POBYTEER, WasmBinary.SUB_FINAL_TYPE, WasmBinary.REC_GROUP, -16, Opcodes.OPC_getfield, -26, 115, Opcodes.OPC_fcmpg, Opcodes.OPC_ireturn, 116, 34, -25, Opcodes.OPC_lreturn, 53, Opcodes.OPC_i2l, -30, -7, 55, -24, 28, 117, WasmBinary.STRUCT_TYPE, 110, 71, MidiConstants.MIDI_TIME_CODE, 26, 113, 29, 41, Opcodes.OPC_multianewarray, Opcodes.OPC_l2f, 111, Opcodes.OPC_invokespecial, 98, 14, Opcodes.OPC_tableswitch, 24, Opcodes.OPC_arraylength, 27, -4, 86, 62, 75, Opcodes.OPC_ifnull, -46, 121, 32, Opcodes.OPC_ifne, -37, -64, -2, 120, -51, 90, -12, 31, -35, Opcodes.OPC_jsr, 51, Opcodes.OPC_l2i, 7, Opcodes.OPC_ifnonnull, 49, Opcodes.OPC_return, 18, 16, 89, 39, -128, -20, 95, 96, 81, 127, Opcodes.OPC_ret, 25, Opcodes.OPC_putfield, 74, 13, 45, -27, 122, Opcodes.OPC_if_icmpeq, Opcodes.OPC_i2s, Opcodes.OPC_jsr_w, Opcodes.OPC_ifge, ByteSourceJsonBootstrapper.UTF8_BOM_1, -96, -32, 59, 77, Opcodes.OPC_freturn, 42, -11, -80, Opcodes.OPC_goto_w, -21, -69, 60, Opcodes.OPC_lxor, 83, Opcodes.OPC_ifeq, 97, 23, 43, 4, 126, Opcodes.OPC_invokedynamic, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125};
    private static final byte[] SB4_sbox = {48, 104, Opcodes.OPC_ifeq, 27, Opcodes.OPC_i2d, Opcodes.OPC_invokeinterface, 33, 120, 80, 57, -37, -31, 114, 9, 98, 60, 62, 126, 94, Opcodes.OPC_d2i, MidiConstants.MIDI_TIME_CODE, -96, -52, Opcodes.OPC_if_icmpgt, 42, 29, -5, Opcodes.OPC_invokevirtual, -42, 32, Opcodes.OPC_wide, Opcodes.OPC_f2d, Opcodes.OPC_lor, 101, -11, Opcodes.OPC_l2f, -53, Opcodes.OPC_ifgt, 119, Opcodes.OPC_ifnull, 87, 67, 86, 23, -44, 64, 26, 77, -64, 99, 108, -29, Opcodes.OPC_invokespecial, Opcodes.OPC_goto_w, 100, 106, 83, Opcodes.OPC_tableswitch, 56, Opcodes.OPC_dcmpg, 12, -12, Opcodes.OPC_iflt, -19, 127, 34, 118, Opcodes.OPC_dreturn, -35, 58, 11, 88, 103, Opcodes.OPC_l2i, 6, Opcodes.OPC_monitorexit, 53, 13, 1, Opcodes.OPC_f2i, Opcodes.OPC_f2l, Opcodes.OPC_monitorenter, -26, 95, 2, 36, 117, Opcodes.OPC_i2s, 102, 30, -27, -30, 84, -40, 16, WasmBinary.REC_GROUP, 122, -24, 8, 44, 18, Opcodes.OPC_dcmpl, 50, Opcodes.OPC_lookupswitch, Opcodes.OPC_getfield, 39, 10, 35, WasmBinary.STRUCT_TYPE, ByteSourceJsonBootstrapper.UTF8_BOM_1, -54, -39, Opcodes.OPC_invokestatic, -6, -36, 49, 107, -47, Opcodes.OPC_lreturn, 25, 73, Opcodes.OPC_anewarray, 81, Opcodes.OPC_fcmpg, -18, -28, Opcodes.OPC_jsr, 65, -38, -1, -51, 85, Opcodes.OPC_i2f, 54, Opcodes.OPC_arraylength, 97, 82, -8, -69, 14, Opcodes.OPC_ixor, 72, 105, Opcodes.OPC_ifne, -32, 71, Opcodes.OPC_ifle, 92, 4, 75, 52, 21, 121, 38, Opcodes.OPC_goto, WasmBinary.ARRAY_TYPE, 41, Opcodes.OPC_freturn, Opcodes.OPC_i2c, -41, Opcodes.OPC_iinc, -23, -46, Opcodes.OPC_invokedynamic, 93, MidiConstants.SONG_SELECT, Opcodes.OPC_multianewarray, -80, -65, Opcodes.OPC_if_icmple, 59, 113, 68, 70, 43, -4, -21, 111, -43, -10, 20, -2, 124, 112, 90, 125, -3, 47, 24, Opcodes.OPC_lxor, 22, Opcodes.OPC_if_acmpeq, Opcodes.OPC_i2b, 31, 5, Opcodes.OPC_fcmpl, 116, Opcodes.OPC_ret, Opcodes.OPC_instanceof, 91, 74, Opcodes.OPC_i2l, 109, 19, 7, 79, 78, 69, Opcodes.OPC_getstatic, 15, Opcodes.OPC_jsr_w, 28, Opcodes.OPC_if_acmpne, -68, -20, 115, -112, 123, WasmBinary.SUB_FINAL_TYPE, 89, Opcodes.OPC_d2l, Opcodes.OPC_if_icmplt, -7, 45, MidiConstants.SONG_POSITION_POBYTEER, Opcodes.OPC_return, 0, Opcodes.OPC_lcmp, 55, Opcodes.OPC_if_icmpeq, -48, 46, Opcodes.OPC_ifge, 110, 40, 63, -128, -16, 61, -45, 37, Opcodes.OPC_l2d, Opcodes.OPC_putfield, -25, 66, Opcodes.OPC_putstatic, Opcodes.OPC_ifnonnull, -22, -9, 76, 17, 51, 3, Opcodes.OPC_if_icmpge, Opcodes.OPC_ireturn, 96};
    private byte[][] roundKeys;

    protected static void A(byte[] bArr) {
        byte b = bArr[0];
        byte b2 = bArr[1];
        byte b3 = bArr[2];
        byte b4 = bArr[3];
        byte b5 = bArr[4];
        byte b6 = bArr[5];
        byte b7 = bArr[6];
        byte b8 = bArr[7];
        byte b9 = bArr[8];
        byte b10 = bArr[9];
        byte b11 = bArr[10];
        byte b12 = bArr[11];
        byte b13 = bArr[12];
        byte b14 = bArr[13];
        byte b15 = bArr[14];
        byte b16 = bArr[15];
        bArr[0] = (byte) ((((((b4 ^ b5) ^ b7) ^ b9) ^ b10) ^ b14) ^ b15);
        bArr[1] = (byte) ((((((b3 ^ b6) ^ b8) ^ b9) ^ b10) ^ b13) ^ b16);
        bArr[2] = (byte) ((((((b2 ^ b5) ^ b7) ^ b11) ^ b12) ^ b13) ^ b16);
        bArr[3] = (byte) ((((((b ^ b6) ^ b8) ^ b11) ^ b12) ^ b14) ^ b15);
        int i = b ^ b3;
        bArr[4] = (byte) (((((i ^ b6) ^ b9) ^ b12) ^ b15) ^ b16);
        int i2 = b2 ^ b4;
        bArr[5] = (byte) (((((i2 ^ b5) ^ b10) ^ b11) ^ b15) ^ b16);
        bArr[6] = (byte) (((((i ^ b8) ^ b10) ^ b11) ^ b13) ^ b14);
        bArr[7] = (byte) (((((i2 ^ b7) ^ b9) ^ b12) ^ b13) ^ b14);
        int i3 = b ^ b2;
        bArr[8] = (byte) (((((i3 ^ b5) ^ b8) ^ b11) ^ b14) ^ b16);
        bArr[9] = (byte) (((((i3 ^ b6) ^ b7) ^ b12) ^ b13) ^ b15);
        int i4 = b3 ^ b4;
        bArr[10] = (byte) (((((i4 ^ b6) ^ b7) ^ b9) ^ b14) ^ b16);
        bArr[11] = (byte) (((((i4 ^ b5) ^ b8) ^ b10) ^ b13) ^ b15);
        int i5 = b2 ^ b3;
        bArr[12] = (byte) (((((i5 ^ b7) ^ b8) ^ b10) ^ b12) ^ b13);
        int i6 = b ^ b4;
        bArr[13] = (byte) (((((i6 ^ b7) ^ b8) ^ b9) ^ b11) ^ b14);
        bArr[14] = (byte) (((((i6 ^ b5) ^ b6) ^ b10) ^ b12) ^ b15);
        bArr[15] = (byte) (((((i5 ^ b5) ^ b6) ^ b9) ^ b11) ^ b16);
    }

    protected static void FE(byte[] bArr, byte[] bArr2) {
        xor(bArr, bArr2);
        SL2(bArr);
        A(bArr);
    }

    protected static void FO(byte[] bArr, byte[] bArr2) {
        xor(bArr, bArr2);
        SL1(bArr);
        A(bArr);
    }

    protected static byte SB1(byte b) {
        return SB1_sbox[b & 255];
    }

    protected static byte SB2(byte b) {
        return SB2_sbox[b & 255];
    }

    protected static byte SB3(byte b) {
        return SB3_sbox[b & 255];
    }

    protected static byte SB4(byte b) {
        return SB4_sbox[b & 255];
    }

    protected static void SL1(byte[] bArr) {
        bArr[0] = SB1(bArr[0]);
        bArr[1] = SB2(bArr[1]);
        bArr[2] = SB3(bArr[2]);
        bArr[3] = SB4(bArr[3]);
        bArr[4] = SB1(bArr[4]);
        bArr[5] = SB2(bArr[5]);
        bArr[6] = SB3(bArr[6]);
        bArr[7] = SB4(bArr[7]);
        bArr[8] = SB1(bArr[8]);
        bArr[9] = SB2(bArr[9]);
        bArr[10] = SB3(bArr[10]);
        bArr[11] = SB4(bArr[11]);
        bArr[12] = SB1(bArr[12]);
        bArr[13] = SB2(bArr[13]);
        bArr[14] = SB3(bArr[14]);
        bArr[15] = SB4(bArr[15]);
    }

    protected static void SL2(byte[] bArr) {
        bArr[0] = SB3(bArr[0]);
        bArr[1] = SB4(bArr[1]);
        bArr[2] = SB1(bArr[2]);
        bArr[3] = SB2(bArr[3]);
        bArr[4] = SB3(bArr[4]);
        bArr[5] = SB4(bArr[5]);
        bArr[6] = SB1(bArr[6]);
        bArr[7] = SB2(bArr[7]);
        bArr[8] = SB3(bArr[8]);
        bArr[9] = SB4(bArr[9]);
        bArr[10] = SB1(bArr[10]);
        bArr[11] = SB2(bArr[11]);
        bArr[12] = SB3(bArr[12]);
        bArr[13] = SB4(bArr[13]);
        bArr[14] = SB1(bArr[14]);
        bArr[15] = SB2(bArr[15]);
    }

    protected static byte[][] keySchedule(boolean z, byte[] bArr) {
        int length = bArr.length;
        if (length < 16 || length > 32 || (length & 7) != 0) {
            throw new IllegalArgumentException("Key length not 128/192/256 bits.");
        }
        int i = length >>> 3;
        int i2 = i - 2;
        byte[][] bArr2 = C;
        byte[] bArr3 = bArr2[i2];
        byte[] bArr4 = bArr2[(i - 1) % 3];
        byte[] bArr5 = bArr2[i % 3];
        byte[] bArr6 = new byte[16];
        byte[] bArr7 = new byte[16];
        System.arraycopy(bArr, 0, bArr6, 0, 16);
        System.arraycopy(bArr, 16, bArr7, 0, length - 16);
        byte[] bArr8 = new byte[16];
        byte[] bArr9 = new byte[16];
        byte[] bArr10 = new byte[16];
        byte[] bArr11 = new byte[16];
        System.arraycopy(bArr6, 0, bArr8, 0, 16);
        System.arraycopy(bArr8, 0, bArr9, 0, 16);
        FO(bArr9, bArr3);
        xor(bArr9, bArr7);
        System.arraycopy(bArr9, 0, bArr10, 0, 16);
        FE(bArr10, bArr4);
        xor(bArr10, bArr8);
        System.arraycopy(bArr10, 0, bArr11, 0, 16);
        FO(bArr11, bArr5);
        xor(bArr11, bArr9);
        int i3 = i2 * 2;
        int i4 = i3 + 12;
        byte[][] bArr12 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, i3 + 13, 16);
        keyScheduleRound(bArr12[0], bArr8, bArr9, 19);
        keyScheduleRound(bArr12[1], bArr9, bArr10, 19);
        keyScheduleRound(bArr12[2], bArr10, bArr11, 19);
        keyScheduleRound(bArr12[3], bArr11, bArr8, 19);
        keyScheduleRound(bArr12[4], bArr8, bArr9, 31);
        keyScheduleRound(bArr12[5], bArr9, bArr10, 31);
        keyScheduleRound(bArr12[6], bArr10, bArr11, 31);
        keyScheduleRound(bArr12[7], bArr11, bArr8, 31);
        keyScheduleRound(bArr12[8], bArr8, bArr9, 67);
        keyScheduleRound(bArr12[9], bArr9, bArr10, 67);
        keyScheduleRound(bArr12[10], bArr10, bArr11, 67);
        keyScheduleRound(bArr12[11], bArr11, bArr8, 67);
        keyScheduleRound(bArr12[12], bArr8, bArr9, 97);
        if (i4 > 12) {
            keyScheduleRound(bArr12[13], bArr9, bArr10, 97);
            keyScheduleRound(bArr12[14], bArr10, bArr11, 97);
            if (i4 > 14) {
                keyScheduleRound(bArr12[15], bArr11, bArr8, 97);
                keyScheduleRound(bArr12[16], bArr8, bArr9, 109);
            }
        }
        if (!z) {
            reverseKeys(bArr12);
            for (int i5 = 1; i5 < i4; i5++) {
                A(bArr12[i5]);
            }
        }
        return bArr12;
    }

    protected static void keyScheduleRound(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) {
        int i2 = i >>> 3;
        int i3 = i & 7;
        int i4 = 8 - i3;
        int i5 = bArr3[15 - i2] & 255;
        int i6 = 0;
        while (i6 < 16) {
            int i7 = bArr3[(i6 - i2) & 15] & 255;
            bArr[i6] = (byte) (((i5 << i4) | (i7 >>> i3)) ^ (bArr2[i6] & 255));
            i6++;
            i5 = i7;
        }
    }

    protected static void reverseKeys(byte[][] bArr) {
        int length = bArr.length;
        int i = length / 2;
        int i2 = length - 1;
        for (int i3 = 0; i3 < i; i3++) {
            byte[] bArr2 = bArr[i3];
            int i4 = i2 - i3;
            bArr[i3] = bArr[i4];
            bArr[i4] = bArr2;
        }
    }

    protected static void xor(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < 16; i++) {
            bArr[i] = (byte) (bArr[i] ^ bArr2[i]);
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "ARIA";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("invalid parameter passed to ARIA init - " + cipherParameters.getClass().getName());
        }
        this.roundKeys = keySchedule(z, ((KeyParameter) cipherParameters).getKey());
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws IllegalStateException, DataLengthException {
        if (this.roundKeys == null) {
            throw new IllegalStateException("ARIA engine not initialised");
        }
        if (i > bArr.length - 16) {
            throw new DataLengthException("input buffer too short");
        }
        if (i2 > bArr2.length - 16) {
            throw new OutputLengthException("output buffer too short");
        }
        byte[] bArr3 = new byte[16];
        System.arraycopy(bArr, i, bArr3, 0, 16);
        int length = this.roundKeys.length - 3;
        int i3 = 0;
        while (i3 < length) {
            int i4 = i3 + 1;
            FO(bArr3, this.roundKeys[i3]);
            i3 += 2;
            FE(bArr3, this.roundKeys[i4]);
        }
        FO(bArr3, this.roundKeys[i3]);
        xor(bArr3, this.roundKeys[i3 + 1]);
        SL2(bArr3);
        xor(bArr3, this.roundKeys[i3 + 2]);
        System.arraycopy(bArr3, 0, bArr2, i2, 16);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
