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
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes13.dex */
public class RijndaelEngine implements BlockCipher {
    private static final int MAXKC = 64;
    private static final int MAXROUNDS = 14;
    private long A0;
    private long A1;
    private long A2;
    private long A3;
    private int BC;
    private long BC_MASK;
    private int ROUNDS;
    private int blockBits;
    private boolean forEncryption;
    private byte[] shifts0SC;
    private byte[] shifts1SC;
    private long[][] workingKey;
    private static final byte[] logtable = {0, 0, 25, 1, 50, 2, 26, Opcodes.OPC_ifnull, 75, Opcodes.OPC_ifnonnull, 27, 104, 51, -18, WasmBinary.STRUCT_TYPE, 3, 100, 4, -32, 14, 52, Opcodes.OPC_f2d, Opcodes.OPC_lor, ByteSourceJsonBootstrapper.UTF8_BOM_1, 76, 113, 8, Opcodes.OPC_goto_w, -8, 105, 28, Opcodes.OPC_instanceof, 125, Opcodes.OPC_monitorenter, 29, Opcodes.OPC_putfield, -7, Opcodes.OPC_invokeinterface, 39, 106, 77, -28, Opcodes.OPC_if_acmpne, 114, Opcodes.OPC_ifne, Opcodes.OPC_jsr_w, 9, 120, 101, 47, Opcodes.OPC_l2d, 5, 33, 15, -31, 36, 18, -16, Opcodes.OPC_ixor, 69, 53, Opcodes.OPC_i2s, -38, Opcodes.OPC_d2i, Opcodes.OPC_fcmpg, Opcodes.OPC_d2l, -37, Opcodes.OPC_anewarray, 54, -48, WasmBinary.REC_GROUP, Opcodes.OPC_lcmp, 19, 92, -46, MidiConstants.MIDI_TIME_CODE, 64, 70, Opcodes.OPC_lxor, 56, 102, -35, -3, 48, -65, 6, Opcodes.OPC_f2i, 98, Opcodes.OPC_putstatic, 37, -30, Opcodes.OPC_dcmpg, 34, Opcodes.OPC_l2i, Opcodes.OPC_i2b, 16, 126, 110, 72, Opcodes.OPC_monitorexit, Opcodes.OPC_if_icmpgt, Opcodes.OPC_invokevirtual, 30, 66, 58, 107, 40, 84, -6, Opcodes.OPC_i2l, 61, Opcodes.OPC_invokedynamic, 43, 121, 10, 21, Opcodes.OPC_iflt, Opcodes.OPC_if_icmpeq, 94, -54, 78, -44, Opcodes.OPC_ireturn, -27, MidiConstants.SONG_SELECT, 115, Opcodes.OPC_goto, 87, Opcodes.OPC_dreturn, 88, Opcodes.OPC_jsr, 80, -12, -22, -42, 116, 79, Opcodes.OPC_freturn, -23, -43, -25, -26, Opcodes.OPC_lreturn, -24, 44, -41, 117, 122, -21, 22, 11, -11, 89, -53, 95, -80, Opcodes.OPC_ifge, Opcodes.OPC_ret, 81, -96, 127, 12, -10, 111, 23, Opcodes.OPC_wide, 73, -20, -40, 67, 31, 45, Opcodes.OPC_if_icmple, 118, 123, Opcodes.OPC_invokespecial, -52, -69, 62, 90, -5, 96, Opcodes.OPC_return, Opcodes.OPC_i2f, 59, 82, Opcodes.OPC_if_icmplt, 108, Opcodes.OPC_tableswitch, 85, 41, Opcodes.OPC_ifgt, Opcodes.OPC_dcmpl, Opcodes.OPC_getstatic, Opcodes.OPC_i2d, -112, 97, Opcodes.OPC_arraylength, -36, -4, -68, Opcodes.OPC_fcmpl, WasmBinary.SUB_FINAL_TYPE, -51, 55, 63, 91, -47, 83, 57, Opcodes.OPC_iinc, 60, 65, Opcodes.OPC_if_icmpge, 109, 71, 20, 42, Opcodes.OPC_ifle, 93, 86, MidiConstants.SONG_POSITION_POBYTEER, -45, Opcodes.OPC_lookupswitch, 68, 17, Opcodes.OPC_i2c, -39, 35, 32, 46, Opcodes.OPC_l2f, Opcodes.OPC_getfield, 124, Opcodes.OPC_invokestatic, 38, 119, Opcodes.OPC_ifeq, -29, Opcodes.OPC_if_acmpeq, 103, 74, -19, WasmBinary.ARRAY_TYPE, Opcodes.OPC_multianewarray, 49, -2, 24, 13, 99, Opcodes.OPC_f2l, -128, -64, -9, 112, 7};
    private static final byte[] aLogtable = {0, 3, 5, 15, 17, 51, 85, -1, 26, 46, 114, Opcodes.OPC_fcmpg, Opcodes.OPC_if_icmplt, -8, 19, 53, 95, -31, 56, 72, -40, 115, Opcodes.OPC_fcmpl, Opcodes.OPC_if_icmple, -9, 2, 6, 10, 30, 34, 102, Opcodes.OPC_tableswitch, -27, 52, 92, -28, 55, 89, -21, 38, 106, Opcodes.OPC_arraylength, -39, 112, -112, Opcodes.OPC_lookupswitch, -26, 49, 83, -11, 4, 12, 20, 60, 68, -52, 79, -47, 104, Opcodes.OPC_invokestatic, -45, 110, Opcodes.OPC_getstatic, -51, 76, -44, 103, Opcodes.OPC_ret, -32, 59, 77, -41, 98, Opcodes.OPC_if_acmpne, MidiConstants.MIDI_TIME_CODE, 8, 24, 40, 120, Opcodes.OPC_l2i, Opcodes.OPC_lxor, Opcodes.OPC_ifle, Opcodes.OPC_invokeinterface, -48, 107, Opcodes.OPC_anewarray, -36, 127, Opcodes.OPC_lor, Opcodes.OPC_dcmpg, Opcodes.OPC_putstatic, WasmBinary.REC_GROUP, 73, -37, 118, Opcodes.OPC_ifne, Opcodes.OPC_putfield, Opcodes.OPC_wide, 87, -7, 16, 48, 80, -16, 11, 29, 39, 105, -69, -42, 97, Opcodes.OPC_if_icmpgt, -2, 25, 43, 125, Opcodes.OPC_i2d, Opcodes.OPC_i2c, Opcodes.OPC_lreturn, -20, 47, 113, Opcodes.OPC_i2s, Opcodes.OPC_freturn, -23, 32, 96, -96, -5, 22, 58, 78, -46, 109, Opcodes.OPC_invokespecial, Opcodes.OPC_monitorenter, 93, -25, 50, 86, -6, 21, 63, 65, Opcodes.OPC_monitorexit, 94, -30, 61, 71, Opcodes.OPC_jsr_w, 64, -64, 91, -19, 44, 116, Opcodes.OPC_ifge, -65, -38, 117, Opcodes.OPC_if_icmpeq, Opcodes.OPC_invokedynamic, -43, 100, Opcodes.OPC_ireturn, ByteSourceJsonBootstrapper.UTF8_BOM_1, 42, 126, Opcodes.OPC_ixor, Opcodes.OPC_ifgt, -68, WasmBinary.STRUCT_TYPE, 122, Opcodes.OPC_d2i, Opcodes.OPC_l2f, -128, Opcodes.OPC_iflt, Opcodes.OPC_invokevirtual, Opcodes.OPC_instanceof, 88, -24, 35, 101, Opcodes.OPC_dreturn, -22, 37, 111, Opcodes.OPC_return, Opcodes.OPC_goto_w, 67, Opcodes.OPC_multianewarray, 84, -4, 31, 33, 99, Opcodes.OPC_if_acmpeq, -12, 7, 9, 27, 45, 119, Opcodes.OPC_ifeq, -80, -53, 70, -54, 69, WasmBinary.SUB_FINAL_TYPE, 74, WasmBinary.ARRAY_TYPE, 121, Opcodes.OPC_f2i, Opcodes.OPC_i2f, Opcodes.OPC_i2b, Opcodes.OPC_jsr, -29, 62, 66, Opcodes.OPC_ifnull, 81, MidiConstants.SONG_SELECT, 14, 18, 54, 90, -18, 41, 123, Opcodes.OPC_f2d, Opcodes.OPC_f2l, Opcodes.OPC_d2l, Opcodes.OPC_l2d, Opcodes.OPC_i2l, Opcodes.OPC_lcmp, Opcodes.OPC_goto, MidiConstants.SONG_POSITION_POBYTEER, 13, 23, 57, 75, -35, 124, Opcodes.OPC_iinc, Opcodes.OPC_dcmpl, Opcodes.OPC_if_icmpge, -3, 28, 36, 108, Opcodes.OPC_getfield, Opcodes.OPC_ifnonnull, 82, -10, 1, 3, 5, 15, 17, 51, 85, -1, 26, 46, 114, Opcodes.OPC_fcmpg, Opcodes.OPC_if_icmplt, -8, 19, 53, 95, -31, 56, 72, -40, 115, Opcodes.OPC_fcmpl, Opcodes.OPC_if_icmple, -9, 2, 6, 10, 30, 34, 102, Opcodes.OPC_tableswitch, -27, 52, 92, -28, 55, 89, -21, 38, 106, Opcodes.OPC_arraylength, -39, 112, -112, Opcodes.OPC_lookupswitch, -26, 49, 83, -11, 4, 12, 20, 60, 68, -52, 79, -47, 104, Opcodes.OPC_invokestatic, -45, 110, Opcodes.OPC_getstatic, -51, 76, -44, 103, Opcodes.OPC_ret, -32, 59, 77, -41, 98, Opcodes.OPC_if_acmpne, MidiConstants.MIDI_TIME_CODE, 8, 24, 40, 120, Opcodes.OPC_l2i, Opcodes.OPC_lxor, Opcodes.OPC_ifle, Opcodes.OPC_invokeinterface, -48, 107, Opcodes.OPC_anewarray, -36, 127, Opcodes.OPC_lor, Opcodes.OPC_dcmpg, Opcodes.OPC_putstatic, WasmBinary.REC_GROUP, 73, -37, 118, Opcodes.OPC_ifne, Opcodes.OPC_putfield, Opcodes.OPC_wide, 87, -7, 16, 48, 80, -16, 11, 29, 39, 105, -69, -42, 97, Opcodes.OPC_if_icmpgt, -2, 25, 43, 125, Opcodes.OPC_i2d, Opcodes.OPC_i2c, Opcodes.OPC_lreturn, -20, 47, 113, Opcodes.OPC_i2s, Opcodes.OPC_freturn, -23, 32, 96, -96, -5, 22, 58, 78, -46, 109, Opcodes.OPC_invokespecial, Opcodes.OPC_monitorenter, 93, -25, 50, 86, -6, 21, 63, 65, Opcodes.OPC_monitorexit, 94, -30, 61, 71, Opcodes.OPC_jsr_w, 64, -64, 91, -19, 44, 116, Opcodes.OPC_ifge, -65, -38, 117, Opcodes.OPC_if_icmpeq, Opcodes.OPC_invokedynamic, -43, 100, Opcodes.OPC_ireturn, ByteSourceJsonBootstrapper.UTF8_BOM_1, 42, 126, Opcodes.OPC_ixor, Opcodes.OPC_ifgt, -68, WasmBinary.STRUCT_TYPE, 122, Opcodes.OPC_d2i, Opcodes.OPC_l2f, -128, Opcodes.OPC_iflt, Opcodes.OPC_invokevirtual, Opcodes.OPC_instanceof, 88, -24, 35, 101, Opcodes.OPC_dreturn, -22, 37, 111, Opcodes.OPC_return, Opcodes.OPC_goto_w, 67, Opcodes.OPC_multianewarray, 84, -4, 31, 33, 99, Opcodes.OPC_if_acmpeq, -12, 7, 9, 27, 45, 119, Opcodes.OPC_ifeq, -80, -53, 70, -54, 69, WasmBinary.SUB_FINAL_TYPE, 74, WasmBinary.ARRAY_TYPE, 121, Opcodes.OPC_f2i, Opcodes.OPC_i2f, Opcodes.OPC_i2b, Opcodes.OPC_jsr, -29, 62, 66, Opcodes.OPC_ifnull, 81, MidiConstants.SONG_SELECT, 14, 18, 54, 90, -18, 41, 123, Opcodes.OPC_f2d, Opcodes.OPC_f2l, Opcodes.OPC_d2l, Opcodes.OPC_l2d, Opcodes.OPC_i2l, Opcodes.OPC_lcmp, Opcodes.OPC_goto, MidiConstants.SONG_POSITION_POBYTEER, 13, 23, 57, 75, -35, 124, Opcodes.OPC_iinc, Opcodes.OPC_dcmpl, Opcodes.OPC_if_icmpge, -3, 28, 36, 108, Opcodes.OPC_getfield, Opcodes.OPC_ifnonnull, 82, -10, 1};
    private static final byte[] S = {99, 124, 119, 123, MidiConstants.SONG_POSITION_POBYTEER, 107, 111, Opcodes.OPC_multianewarray, 48, 1, 103, 43, -2, -41, Opcodes.OPC_lookupswitch, 118, -54, Opcodes.OPC_ixor, Opcodes.OPC_jsr_w, 125, -6, 89, 71, -16, Opcodes.OPC_lreturn, -44, Opcodes.OPC_if_icmpge, Opcodes.OPC_dreturn, Opcodes.OPC_ifge, Opcodes.OPC_if_icmple, 114, -64, Opcodes.OPC_invokespecial, -3, Opcodes.OPC_i2s, 38, 54, 63, -9, -52, 52, Opcodes.OPC_if_acmpeq, -27, MidiConstants.MIDI_TIME_CODE, 113, -40, 49, 21, 4, Opcodes.OPC_ifnonnull, 35, Opcodes.OPC_monitorexit, 24, Opcodes.OPC_fcmpg, 5, Opcodes.OPC_ifne, 7, 18, -128, -30, -21, 39, Opcodes.OPC_getstatic, 117, 9, Opcodes.OPC_lxor, 44, 26, 27, 110, 90, -96, 82, 59, -42, Opcodes.OPC_putstatic, 41, -29, 47, Opcodes.OPC_iinc, 83, -47, 0, -19, 32, -4, Opcodes.OPC_return, 91, 106, -53, Opcodes.OPC_arraylength, 57, 74, 76, 88, WasmBinary.SUB_FINAL_TYPE, -48, ByteSourceJsonBootstrapper.UTF8_BOM_1, Opcodes.OPC_tableswitch, -5, 67, 77, 51, Opcodes.OPC_i2l, 69, -7, 2, 127, 80, 60, Opcodes.OPC_if_icmpeq, Opcodes.OPC_jsr, 81, Opcodes.OPC_if_icmpgt, 64, Opcodes.OPC_d2l, Opcodes.OPC_i2c, Opcodes.OPC_ifgt, 56, -11, -68, Opcodes.OPC_invokevirtual, -38, 33, 16, -1, MidiConstants.SONG_SELECT, -46, -51, 12, 19, -20, 95, Opcodes.OPC_dcmpl, 68, 23, Opcodes.OPC_wide, Opcodes.OPC_goto, 126, 61, 100, 93, 25, 115, 96, Opcodes.OPC_lor, 79, -36, 34, 42, -112, Opcodes.OPC_l2i, 70, -18, Opcodes.OPC_invokestatic, 20, WasmBinary.ARRAY_TYPE, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, Opcodes.OPC_monitorenter, -45, Opcodes.OPC_ireturn, 98, Opcodes.OPC_i2b, Opcodes.OPC_fcmpl, -28, 121, -25, Opcodes.OPC_goto_w, 55, 109, Opcodes.OPC_f2d, -43, 78, Opcodes.OPC_ret, 108, 86, -12, -22, 101, 122, Opcodes.OPC_freturn, 8, Opcodes.OPC_invokedynamic, 120, 37, 46, 28, Opcodes.OPC_if_acmpne, Opcodes.OPC_getfield, Opcodes.OPC_ifnull, -24, -35, 116, 31, 75, Opcodes.OPC_anewarray, Opcodes.OPC_f2i, Opcodes.OPC_l2d, 112, 62, Opcodes.OPC_putfield, 102, 72, 3, -10, 14, 97, 53, 87, Opcodes.OPC_invokeinterface, Opcodes.OPC_i2f, Opcodes.OPC_instanceof, 29, Opcodes.OPC_ifle, -31, -8, Opcodes.OPC_dcmpg, 17, 105, -39, Opcodes.OPC_d2i, Opcodes.OPC_lcmp, Opcodes.OPC_iflt, 30, Opcodes.OPC_i2d, -23, WasmBinary.REC_GROUP, 85, 40, WasmBinary.STRUCT_TYPE, Opcodes.OPC_f2l, Opcodes.OPC_if_icmplt, Opcodes.OPC_l2f, 13, -65, -26, 66, 104, 65, Opcodes.OPC_ifeq, 45, 15, -80, 84, -69, 22};
    private static final byte[] Si = {82, 9, 106, -43, 48, 54, Opcodes.OPC_if_acmpeq, 56, -65, 64, Opcodes.OPC_if_icmpgt, Opcodes.OPC_ifle, Opcodes.OPC_lor, MidiConstants.SONG_SELECT, -41, -5, 124, -29, 57, Opcodes.OPC_ixor, Opcodes.OPC_iflt, 47, -1, Opcodes.OPC_i2d, 52, Opcodes.OPC_d2i, 67, 68, Opcodes.OPC_wide, WasmBinary.ARRAY_TYPE, -23, -53, 84, 123, Opcodes.OPC_lcmp, 50, Opcodes.OPC_if_acmpne, Opcodes.OPC_monitorenter, 35, 61, -18, 76, Opcodes.OPC_fcmpl, 11, 66, -6, Opcodes.OPC_monitorexit, 78, 8, 46, Opcodes.OPC_if_icmplt, 102, 40, -39, 36, Opcodes.OPC_getstatic, 118, 91, Opcodes.OPC_if_icmpge, 73, 109, Opcodes.OPC_f2i, -47, 37, 114, -8, -10, 100, Opcodes.OPC_i2f, 104, Opcodes.OPC_dcmpg, 22, -44, Opcodes.OPC_if_icmple, 92, -52, 93, 101, Opcodes.OPC_invokevirtual, Opcodes.OPC_i2c, 108, 112, 72, 80, -3, -19, Opcodes.OPC_invokeinterface, -38, 94, 21, 70, 87, Opcodes.OPC_goto, Opcodes.OPC_f2d, Opcodes.OPC_ifgt, Opcodes.OPC_iinc, -112, -40, Opcodes.OPC_lookupswitch, 0, Opcodes.OPC_f2l, -68, -45, 10, -9, -28, 88, 5, Opcodes.OPC_invokestatic, Opcodes.OPC_putstatic, 69, 6, -48, 44, 30, Opcodes.OPC_d2l, -54, 63, 15, 2, Opcodes.OPC_instanceof, Opcodes.OPC_dreturn, Opcodes.OPC_anewarray, 3, 1, 19, Opcodes.OPC_l2d, 107, 58, Opcodes.OPC_i2b, 17, 65, 79, 103, -36, -22, Opcodes.OPC_dcmpl, MidiConstants.SONG_POSITION_POBYTEER, WasmBinary.SUB_FINAL_TYPE, WasmBinary.REC_GROUP, -16, Opcodes.OPC_getfield, -26, 115, Opcodes.OPC_fcmpg, Opcodes.OPC_ireturn, 116, 34, -25, Opcodes.OPC_lreturn, 53, Opcodes.OPC_i2l, -30, -7, 55, -24, 28, 117, WasmBinary.STRUCT_TYPE, 110, 71, MidiConstants.MIDI_TIME_CODE, 26, 113, 29, 41, Opcodes.OPC_multianewarray, Opcodes.OPC_l2f, 111, Opcodes.OPC_invokespecial, 98, 14, Opcodes.OPC_tableswitch, 24, Opcodes.OPC_arraylength, 27, -4, 86, 62, 75, Opcodes.OPC_ifnull, -46, 121, 32, Opcodes.OPC_ifne, -37, -64, -2, 120, -51, 90, -12, 31, -35, Opcodes.OPC_jsr, 51, Opcodes.OPC_l2i, 7, Opcodes.OPC_ifnonnull, 49, Opcodes.OPC_return, 18, 16, 89, 39, -128, -20, 95, 96, 81, 127, Opcodes.OPC_ret, 25, Opcodes.OPC_putfield, 74, 13, 45, -27, 122, Opcodes.OPC_if_icmpeq, Opcodes.OPC_i2s, Opcodes.OPC_jsr_w, Opcodes.OPC_ifge, ByteSourceJsonBootstrapper.UTF8_BOM_1, -96, -32, 59, 77, Opcodes.OPC_freturn, 42, -11, -80, Opcodes.OPC_goto_w, -21, -69, 60, Opcodes.OPC_lxor, 83, Opcodes.OPC_ifeq, 97, 23, 43, 4, 126, Opcodes.OPC_invokedynamic, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125};
    private static final int[] rcon = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, XKeySym.XK_idiaeresis, 197, 145};
    static byte[][] shifts0 = {new byte[]{0, 8, 16, 24}, new byte[]{0, 8, 16, 24}, new byte[]{0, 8, 16, 24}, new byte[]{0, 8, 16, 32}, new byte[]{0, 8, 24, 32}};
    static byte[][] shifts1 = {new byte[]{0, 24, 16, 8}, new byte[]{0, 32, 24, 16}, new byte[]{0, 40, 32, 24}, new byte[]{0, 48, 40, 24}, new byte[]{0, 56, 40, 32}};

    public RijndaelEngine() {
        this(128);
    }

    public RijndaelEngine(int i) {
        if (i == 128) {
            this.BC = 32;
            this.BC_MASK = 4294967295L;
            this.shifts0SC = shifts0[0];
            this.shifts1SC = shifts1[0];
        } else if (i == 160) {
            this.BC = 40;
            this.BC_MASK = 1099511627775L;
            this.shifts0SC = shifts0[1];
            this.shifts1SC = shifts1[1];
        } else if (i == 192) {
            this.BC = 48;
            this.BC_MASK = 281474976710655L;
            this.shifts0SC = shifts0[2];
            this.shifts1SC = shifts1[2];
        } else if (i == 224) {
            this.BC = 56;
            this.BC_MASK = 72057594037927935L;
            this.shifts0SC = shifts0[3];
            this.shifts1SC = shifts1[3];
        } else {
            if (i != 256) {
                throw new IllegalArgumentException("unknown blocksize to Rijndael");
            }
            this.BC = 64;
            this.BC_MASK = -1L;
            this.shifts0SC = shifts0[4];
            this.shifts1SC = shifts1[4];
        }
        this.blockBits = i;
    }

    private void InvMixColumn() {
        long jMul0xe = 0;
        long jMul0xe2 = 0;
        long jMul0xe3 = 0;
        long jMul0xe4 = 0;
        for (int i = 0; i < this.BC; i += 8) {
            int i2 = (int) ((this.A0 >> i) & 255);
            int i3 = (int) ((this.A1 >> i) & 255);
            int i4 = (int) ((this.A2 >> i) & 255);
            long j = jMul0xe3;
            int i5 = (int) ((this.A3 >> i) & 255);
            int i6 = -1;
            int i7 = i2 != 0 ? logtable[i2 & 255] & 255 : -1;
            int i8 = i3 != 0 ? logtable[i3 & 255] & 255 : -1;
            int i9 = i4 != 0 ? logtable[i4 & 255] & 255 : -1;
            if (i5 != 0) {
                i6 = logtable[i5 & 255] & 255;
            }
            jMul0xe |= ((long) ((((mul0xe(i7) ^ mul0xb(i8)) ^ mul0xd(i9)) ^ mul0x9(i6)) & 255)) << i;
            jMul0xe4 |= ((long) ((((mul0xe(i8) ^ mul0xb(i9)) ^ mul0xd(i6)) ^ mul0x9(i7)) & 255)) << i;
            jMul0xe2 |= ((long) ((((mul0xe(i9) ^ mul0xb(i6)) ^ mul0xd(i7)) ^ mul0x9(i8)) & 255)) << i;
            jMul0xe3 = (((long) ((((mul0xe(i6) ^ mul0xb(i7)) ^ mul0xd(i8)) ^ mul0x9(i9)) & 255)) << i) | j;
        }
        this.A0 = jMul0xe;
        this.A1 = jMul0xe4;
        this.A2 = jMul0xe2;
        this.A3 = jMul0xe3;
    }

    private void KeyAddition(long[] jArr) {
        this.A0 ^= jArr[0];
        this.A1 ^= jArr[1];
        this.A2 ^= jArr[2];
        this.A3 ^= jArr[3];
    }

    private void MixColumn() {
        long jMul0x2 = 0;
        long jMul0x3 = 0;
        long jMul0x4 = 0;
        long jMul0x5 = 0;
        for (int i = 0; i < this.BC; i += 8) {
            int i2 = (int) ((this.A0 >> i) & 255);
            int i3 = (int) ((this.A1 >> i) & 255);
            int i4 = (int) ((this.A2 >> i) & 255);
            long j = jMul0x4;
            int i5 = (int) ((this.A3 >> i) & 255);
            jMul0x2 |= ((long) ((((mul0x2(i2) ^ mul0x3(i3)) ^ i4) ^ i5) & 255)) << i;
            jMul0x5 |= ((long) ((((mul0x2(i3) ^ mul0x3(i4)) ^ i5) ^ i2) & 255)) << i;
            jMul0x3 |= ((long) ((((mul0x2(i4) ^ mul0x3(i5)) ^ i2) ^ i3) & 255)) << i;
            jMul0x4 = (((long) ((((mul0x2(i5) ^ mul0x3(i2)) ^ i3) ^ i4) & 255)) << i) | j;
        }
        this.A0 = jMul0x2;
        this.A1 = jMul0x5;
        this.A2 = jMul0x3;
        this.A3 = jMul0x4;
    }

    private void ShiftRow(byte[] bArr) {
        this.A1 = shift(this.A1, bArr[1]);
        this.A2 = shift(this.A2, bArr[2]);
        this.A3 = shift(this.A3, bArr[3]);
    }

    private void Substitution(byte[] bArr) {
        this.A0 = applyS(this.A0, bArr);
        this.A1 = applyS(this.A1, bArr);
        this.A2 = applyS(this.A2, bArr);
        this.A3 = applyS(this.A3, bArr);
    }

    private long applyS(long j, byte[] bArr) {
        long j2 = 0;
        for (int i = 0; i < this.BC; i += 8) {
            j2 |= ((long) (bArr[(int) ((j >> i) & 255)] & 255)) << i;
        }
        return j2;
    }

    private void decryptBlock(long[][] jArr) {
        KeyAddition(jArr[this.ROUNDS]);
        Substitution(Si);
        ShiftRow(this.shifts1SC);
        for (int i = this.ROUNDS - 1; i > 0; i--) {
            KeyAddition(jArr[i]);
            InvMixColumn();
            Substitution(Si);
            ShiftRow(this.shifts1SC);
        }
        KeyAddition(jArr[0]);
    }

    private void encryptBlock(long[][] jArr) {
        KeyAddition(jArr[0]);
        for (int i = 1; i < this.ROUNDS; i++) {
            Substitution(S);
            ShiftRow(this.shifts0SC);
            MixColumn();
            KeyAddition(jArr[i]);
        }
        Substitution(S);
        ShiftRow(this.shifts0SC);
        KeyAddition(jArr[this.ROUNDS]);
    }

    private long[][] generateWorkingKey(byte[] bArr) {
        int i;
        int i2 = 8;
        int length = bArr.length * 8;
        int i3 = 1;
        int i4 = 0;
        int i5 = 4;
        byte[][] bArr2 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 4, 64);
        long[][] jArr = (long[][]) Array.newInstance((Class<?>) Long.TYPE, 15, 4);
        if (length == 128) {
            i = 4;
        } else if (length == 160) {
            i = 5;
        } else if (length == 192) {
            i = 6;
        } else if (length == 224) {
            i = 7;
        } else {
            if (length != 256) {
                throw new IllegalArgumentException("Key length not 128/160/192/224/256 bits.");
            }
            i = 8;
        }
        this.ROUNDS = length >= this.blockBits ? i + 6 : (this.BC / 8) + 6;
        int i6 = 0;
        int i7 = 0;
        while (i6 < bArr.length) {
            bArr2[i6 % 4][i6 / 4] = bArr[i7];
            i6++;
            i7++;
        }
        int i8 = 0;
        int i9 = 0;
        while (i8 < i && i9 < (this.ROUNDS + 1) * (this.BC / 8)) {
            int i10 = i4;
            while (i10 < i5) {
                int i11 = this.BC;
                long[] jArr2 = jArr[i9 / (i11 / 8)];
                jArr2[i10] = (((long) (bArr2[i10][i8] & 255)) << ((i9 * 8) % i11)) | jArr2[i10];
                i10++;
                i5 = 4;
            }
            i8++;
            i9++;
            i4 = 0;
            i5 = 4;
        }
        int i12 = 0;
        while (i9 < (this.ROUNDS + i3) * (this.BC / i2)) {
            int i13 = 0;
            while (i13 < 4) {
                byte[] bArr3 = bArr2[i13];
                i13++;
                bArr3[0] = (byte) (S[bArr2[i13 % 4][i - 1] & 255] ^ bArr3[0]);
            }
            byte[] bArr4 = bArr2[0];
            int i14 = i12 + 1;
            bArr4[0] = (byte) (rcon[i12] ^ bArr4[0]);
            int i15 = i3;
            if (i <= 6) {
                while (i15 < i) {
                    for (int i16 = 0; i16 < 4; i16++) {
                        byte[] bArr5 = bArr2[i16];
                        bArr5[i15] = (byte) (bArr5[i15] ^ bArr5[i15 - 1]);
                    }
                    i15++;
                }
            } else {
                while (true) {
                    if (i15 >= 4) {
                        break;
                    }
                    int i17 = 0;
                    for (int i18 = 4; i17 < i18; i18 = 4) {
                        byte[] bArr6 = bArr2[i17];
                        bArr6[i15] = (byte) (bArr6[i15] ^ bArr6[i15 - 1]);
                        i17++;
                    }
                    i15++;
                }
                int i19 = 4;
                for (int i20 = 0; i20 < 4; i20++) {
                    byte[] bArr7 = bArr2[i20];
                    bArr7[4] = (byte) (bArr7[4] ^ S[bArr7[3] & 255]);
                }
                int i21 = 5;
                while (i21 < i) {
                    int i22 = 0;
                    while (i22 < i19) {
                        byte[] bArr8 = bArr2[i22];
                        bArr8[i21] = (byte) (bArr8[i21] ^ bArr8[i21 - 1]);
                        i22++;
                        i19 = 4;
                    }
                    i21++;
                    i19 = 4;
                }
            }
            int i23 = 0;
            while (i23 < i && i9 < (this.ROUNDS + i3) * (this.BC / i2)) {
                for (int i24 = 0; i24 < 4; i24++) {
                    int i25 = this.BC;
                    long[] jArr3 = jArr[i9 / (i25 / 8)];
                    jArr3[i24] = (((long) (bArr2[i24][i23] & 255)) << ((i9 * 8) % i25)) | jArr3[i24];
                }
                i23++;
                i9++;
                i2 = 8;
                i3 = 1;
            }
            i12 = i14;
            i2 = 8;
            i3 = 1;
        }
        return jArr;
    }

    private byte mul0x2(int i) {
        if (i != 0) {
            return aLogtable[(logtable[i] & 255) + 25];
        }
        return (byte) 0;
    }

    private byte mul0x3(int i) {
        if (i != 0) {
            return aLogtable[(logtable[i] & 255) + 1];
        }
        return (byte) 0;
    }

    private byte mul0x9(int i) {
        if (i >= 0) {
            return aLogtable[i + 199];
        }
        return (byte) 0;
    }

    private byte mul0xb(int i) {
        if (i >= 0) {
            return aLogtable[i + 104];
        }
        return (byte) 0;
    }

    private byte mul0xd(int i) {
        if (i >= 0) {
            return aLogtable[i + XKeySym.XK_icircumflex];
        }
        return (byte) 0;
    }

    private byte mul0xe(int i) {
        if (i >= 0) {
            return aLogtable[i + 223];
        }
        return (byte) 0;
    }

    private void packBlock(byte[] bArr, int i) {
        for (int i2 = 0; i2 != this.BC; i2 += 8) {
            bArr[i] = (byte) (this.A0 >> i2);
            bArr[i + 1] = (byte) (this.A1 >> i2);
            int i3 = i + 3;
            bArr[i + 2] = (byte) (this.A2 >> i2);
            i += 4;
            bArr[i3] = (byte) (this.A3 >> i2);
        }
    }

    private long shift(long j, int i) {
        return ((j << (this.BC - i)) | (j >>> i)) & this.BC_MASK;
    }

    private void unpackBlock(byte[] bArr, int i) {
        this.A0 = bArr[i] & 255;
        this.A1 = bArr[i + 1] & 255;
        int i2 = i + 3;
        this.A2 = bArr[i + 2] & 255;
        int i3 = i + 4;
        this.A3 = bArr[i2] & 255;
        for (int i4 = 8; i4 != this.BC; i4 += 8) {
            this.A0 |= ((long) (bArr[i3] & 255)) << i4;
            this.A1 |= ((long) (bArr[i3 + 1] & 255)) << i4;
            int i5 = i3 + 3;
            this.A2 |= ((long) (bArr[i3 + 2] & 255)) << i4;
            i3 += 4;
            this.A3 |= ((long) (bArr[i5] & 255)) << i4;
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "Rijndael";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.BC / 2;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("invalid parameter passed to Rijndael init - " + cipherParameters.getClass().getName());
        }
        this.workingKey = generateWorkingKey(((KeyParameter) cipherParameters).getKey());
        this.forEncryption = z;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey == null) {
            throw new IllegalStateException("Rijndael engine not initialised");
        }
        int i3 = this.BC;
        if ((i3 / 2) + i > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if ((i3 / 2) + i2 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        boolean z = this.forEncryption;
        unpackBlock(bArr, i);
        long[][] jArr = this.workingKey;
        if (z) {
            encryptBlock(jArr);
        } else {
            decryptBlock(jArr);
        }
        packBlock(bArr2, i2);
        return this.BC / 2;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
