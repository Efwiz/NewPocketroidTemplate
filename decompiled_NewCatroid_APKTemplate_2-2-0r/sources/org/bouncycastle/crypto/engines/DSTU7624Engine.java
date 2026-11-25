package org.bouncycastle.crypto.engines;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import org.billthefarmer.mididriver.MidiConstants;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes13.dex */
public class DSTU7624Engine implements BlockCipher {
    private static final int ROUNDS_128 = 10;
    private static final int ROUNDS_256 = 14;
    private static final int ROUNDS_512 = 18;
    private static final byte[] S0 = {Opcodes.OPC_jsr, 67, 95, 6, 107, 117, 108, 89, 113, WasmBinary.STRUCT_TYPE, Opcodes.OPC_i2d, Opcodes.OPC_fcmpl, 23, -16, -40, 9, 109, MidiConstants.SONG_SELECT, 29, -53, Opcodes.OPC_jsr_w, 77, 44, Opcodes.OPC_dreturn, 121, -32, Opcodes.OPC_dcmpl, -3, 111, 75, 69, 57, 62, -35, Opcodes.OPC_if_icmpgt, 79, Opcodes.OPC_getfield, Opcodes.OPC_invokevirtual, Opcodes.OPC_ifne, 14, 31, -65, 21, -31, 73, -46, Opcodes.OPC_i2s, Opcodes.OPC_ifnull, Opcodes.OPC_i2c, 114, Opcodes.OPC_ifle, 97, -47, 99, -6, -18, -12, 25, -43, Opcodes.OPC_lreturn, 88, Opcodes.OPC_if_icmple, -69, Opcodes.OPC_if_icmplt, -36, MidiConstants.SONG_POSITION_POBYTEER, Opcodes.OPC_lxor, 55, 66, -28, 122, 50, Opcodes.OPC_ifge, -52, Opcodes.OPC_lookupswitch, 74, Opcodes.OPC_d2l, 110, 4, 39, 46, -25, -30, 90, Opcodes.OPC_fcmpg, 22, 35, 43, Opcodes.OPC_monitorenter, 101, 102, 15, -68, Opcodes.OPC_ret, 71, 65, 52, 72, -4, Opcodes.OPC_invokespecial, 106, Opcodes.OPC_l2i, Opcodes.OPC_if_acmpeq, 83, Opcodes.OPC_i2f, -7, 91, -37, 56, 123, Opcodes.OPC_monitorexit, 30, 34, 51, 36, 40, 54, Opcodes.OPC_ifnonnull, Opcodes.OPC_getstatic, 59, Opcodes.OPC_d2i, 119, Opcodes.OPC_invokedynamic, -11, 20, Opcodes.OPC_if_icmpeq, 8, 85, Opcodes.OPC_iflt, 76, -2, 96, 92, -38, 24, 70, -51, 125, 33, -80, 63, 27, Opcodes.OPC_l2f, -1, -21, Opcodes.OPC_iinc, 105, 58, Opcodes.OPC_ifgt, -41, -45, 112, 103, 64, Opcodes.OPC_putfield, WasmBinary.ARRAY_TYPE, 93, 48, Opcodes.OPC_i2b, Opcodes.OPC_return, 120, 17, 1, -27, 0, 104, Opcodes.OPC_dcmpg, -96, Opcodes.OPC_multianewarray, 2, Opcodes.OPC_if_acmpne, 116, 45, 11, Opcodes.OPC_if_icmpge, 118, Opcodes.OPC_putstatic, Opcodes.OPC_arraylength, WasmBinary.REC_GROUP, Opcodes.OPC_anewarray, Opcodes.OPC_freturn, -23, Opcodes.OPC_l2d, 49, 28, -20, MidiConstants.MIDI_TIME_CODE, Opcodes.OPC_ifeq, Opcodes.OPC_lcmp, Opcodes.OPC_tableswitch, -10, 38, 47, ByteSourceJsonBootstrapper.UTF8_BOM_1, -24, Opcodes.OPC_f2l, 53, 3, -44, 127, -5, 5, Opcodes.OPC_instanceof, 94, -112, 32, 61, Opcodes.OPC_ixor, -9, -22, 10, 13, 126, -8, 80, 26, Opcodes.OPC_wide, 7, 87, Opcodes.OPC_invokestatic, 60, 98, -29, Opcodes.OPC_goto_w, Opcodes.OPC_ireturn, 82, 100, 16, -48, -39, 19, 12, 18, 41, 81, Opcodes.OPC_invokeinterface, WasmBinary.SUB_FINAL_TYPE, -42, 115, Opcodes.OPC_f2d, Opcodes.OPC_lor, 84, -64, -19, 78, 68, Opcodes.OPC_goto, 42, Opcodes.OPC_i2l, 37, -26, -54, 124, Opcodes.OPC_f2i, 86, -128};
    private static final byte[] S1 = {WasmBinary.REC_GROUP, -69, -21, Opcodes.OPC_i2c, -22, -53, 19, Opcodes.OPC_instanceof, -23, 58, -42, Opcodes.OPC_getstatic, -46, -112, 23, -8, 66, 21, 86, Opcodes.OPC_getfield, 101, 28, Opcodes.OPC_l2i, 67, Opcodes.OPC_multianewarray, 92, 54, Opcodes.OPC_invokedynamic, -11, 87, 103, Opcodes.OPC_f2d, 49, -10, 100, 88, Opcodes.OPC_ifle, -12, 34, Opcodes.OPC_tableswitch, 117, 15, 2, Opcodes.OPC_return, WasmBinary.STRUCT_TYPE, 109, 115, 77, 124, 38, 46, -9, 8, 93, 68, 62, Opcodes.OPC_if_icmpeq, 20, Opcodes.OPC_goto_w, Opcodes.OPC_freturn, 84, 16, -40, -68, 26, 107, 105, MidiConstants.SONG_SELECT, Opcodes.OPC_anewarray, 51, Opcodes.OPC_lookupswitch, -6, -47, Opcodes.OPC_iflt, 104, 78, 22, Opcodes.OPC_fcmpl, Opcodes.OPC_i2b, -18, 76, 99, Opcodes.OPC_d2i, 91, -52, 60, 25, Opcodes.OPC_if_icmplt, Opcodes.OPC_lor, 73, 123, -39, 111, 55, 96, -54, -25, 43, 72, -3, Opcodes.OPC_fcmpg, 69, -4, 65, 18, 13, 121, -27, Opcodes.OPC_l2f, Opcodes.OPC_f2l, -29, 32, 48, -36, Opcodes.OPC_invokespecial, 108, 74, Opcodes.OPC_putfield, 63, Opcodes.OPC_dcmpl, -44, 98, 45, 6, Opcodes.OPC_if_icmple, Opcodes.OPC_if_acmpeq, Opcodes.OPC_lxor, 95, 42, -38, Opcodes.OPC_jsr_w, 0, 126, Opcodes.OPC_if_icmpge, 85, -65, 17, -43, Opcodes.OPC_ifge, WasmBinary.SUB_FINAL_TYPE, 14, 10, 61, 81, 125, Opcodes.OPC_i2s, 27, -2, Opcodes.OPC_wide, 71, 9, Opcodes.OPC_i2f, 11, Opcodes.OPC_d2l, Opcodes.OPC_ifgt, 106, 7, Opcodes.OPC_invokeinterface, -80, Opcodes.OPC_dcmpg, 24, 50, 113, 75, ByteSourceJsonBootstrapper.UTF8_BOM_1, 59, 112, -96, -28, 64, -1, Opcodes.OPC_monitorexit, Opcodes.OPC_ret, -26, 120, -7, Opcodes.OPC_f2i, 70, -128, 30, 56, -31, Opcodes.OPC_invokestatic, Opcodes.OPC_jsr, -32, 12, 35, 118, 29, 37, 36, 5, MidiConstants.MIDI_TIME_CODE, 110, Opcodes.OPC_lcmp, 40, Opcodes.OPC_ifne, Opcodes.OPC_iinc, -24, Opcodes.OPC_if_icmpgt, 79, 119, -45, Opcodes.OPC_i2l, -30, 82, MidiConstants.SONG_POSITION_POBYTEER, Opcodes.OPC_ixor, 80, 122, 47, 116, 83, Opcodes.OPC_putstatic, 97, Opcodes.OPC_dreturn, 57, 53, WasmBinary.ARRAY_TYPE, -51, 31, Opcodes.OPC_ifeq, Opcodes.OPC_ireturn, Opcodes.OPC_lreturn, 114, 44, -35, -48, Opcodes.OPC_i2d, Opcodes.OPC_arraylength, 94, Opcodes.OPC_if_acmpne, -20, 4, Opcodes.OPC_ifnull, 3, 52, -5, -37, 89, Opcodes.OPC_invokevirtual, Opcodes.OPC_monitorenter, 1, -16, 90, -19, Opcodes.OPC_goto, 102, 33, 127, Opcodes.OPC_l2d, 39, Opcodes.OPC_ifnonnull, -64, 41, -41};
    private static final byte[] S2 = {Opcodes.OPC_i2s, -39, Opcodes.OPC_ifne, Opcodes.OPC_putfield, Opcodes.OPC_dcmpg, 34, 69, -4, Opcodes.OPC_invokedynamic, 106, WasmBinary.STRUCT_TYPE, 2, Opcodes.OPC_if_icmpeq, -36, 81, 89, 74, 23, 43, Opcodes.OPC_monitorenter, Opcodes.OPC_lcmp, -12, -69, Opcodes.OPC_if_icmpgt, 98, -28, 113, -44, -51, 112, 22, -31, 73, 60, -64, -40, 92, Opcodes.OPC_iflt, Opcodes.OPC_lreturn, Opcodes.OPC_i2l, 83, Opcodes.OPC_if_icmplt, 122, Opcodes.OPC_goto_w, 45, -32, -47, 114, Opcodes.OPC_if_acmpne, 44, Opcodes.OPC_wide, -29, 118, 120, Opcodes.OPC_invokespecial, Opcodes.OPC_getfield, 9, 59, 14, 65, 76, WasmBinary.ARRAY_TYPE, Opcodes.OPC_getstatic, -112, 37, Opcodes.OPC_if_acmpeq, -41, 3, 17, 0, Opcodes.OPC_monitorexit, 46, Opcodes.OPC_i2c, ByteSourceJsonBootstrapper.UTF8_BOM_1, 78, 18, Opcodes.OPC_ifgt, 125, -53, 53, 16, -43, 79, Opcodes.OPC_ifle, 77, Opcodes.OPC_ret, 85, Opcodes.OPC_ifnull, -48, 123, 24, Opcodes.OPC_dcmpl, -45, 54, -26, 72, 86, Opcodes.OPC_lor, Opcodes.OPC_d2l, 119, -52, Opcodes.OPC_ifge, Opcodes.OPC_invokeinterface, -30, Opcodes.OPC_ireturn, Opcodes.OPC_invokestatic, 47, 21, Opcodes.OPC_if_icmple, 124, -38, 56, 30, 11, 5, -42, 20, 110, 108, 126, 102, -3, Opcodes.OPC_return, -27, 96, Opcodes.OPC_dreturn, 94, 51, Opcodes.OPC_i2d, Opcodes.OPC_jsr_w, -16, 93, 109, 63, Opcodes.OPC_l2i, Opcodes.OPC_f2d, Opcodes.OPC_ifnonnull, -9, 29, -23, -20, -19, -128, 41, 39, WasmBinary.SUB_FINAL_TYPE, Opcodes.OPC_ifeq, Opcodes.OPC_jsr, 80, 15, 55, 36, 40, 48, Opcodes.OPC_fcmpl, -46, 62, 91, 64, Opcodes.OPC_lxor, Opcodes.OPC_putstatic, 105, 87, 31, 7, 28, Opcodes.OPC_l2d, -68, 32, -21, WasmBinary.REC_GROUP, Opcodes.OPC_d2i, Opcodes.OPC_lookupswitch, -18, 49, Opcodes.OPC_if_icmpge, 115, -7, -54, 58, 26, -5, 13, Opcodes.OPC_instanceof, -2, -6, MidiConstants.SONG_POSITION_POBYTEER, 111, Opcodes.OPC_anewarray, Opcodes.OPC_fcmpg, -35, 67, 82, Opcodes.OPC_invokevirtual, 8, MidiConstants.SONG_SELECT, Opcodes.OPC_freturn, Opcodes.OPC_arraylength, 25, Opcodes.OPC_l2f, 50, 38, -80, -22, 75, 100, Opcodes.OPC_iinc, Opcodes.OPC_ixor, 107, -11, 121, -65, 1, 95, 117, 99, 27, 35, 61, 104, 42, 101, -24, Opcodes.OPC_i2b, -10, -1, 19, 88, MidiConstants.MIDI_TIME_CODE, 71, 10, 127, Opcodes.OPC_multianewarray, Opcodes.OPC_goto, -25, 97, 90, 6, 70, 68, 66, 4, -96, -37, 57, Opcodes.OPC_i2f, 84, Opcodes.OPC_tableswitch, Opcodes.OPC_f2l, 52, 33, Opcodes.OPC_f2i, -8, 12, 116, 103};
    private static final byte[] S3 = {104, Opcodes.OPC_f2d, -54, 77, 115, 75, 78, 42, -44, 82, 38, Opcodes.OPC_putstatic, 84, 30, 25, 31, 34, 3, 70, 61, 45, 74, 83, Opcodes.OPC_lxor, 19, Opcodes.OPC_l2d, Opcodes.OPC_invokespecial, -43, 37, 121, -11, Opcodes.OPC_anewarray, 88, 47, 13, 2, -19, 81, Opcodes.OPC_ifle, 17, MidiConstants.SONG_POSITION_POBYTEER, 62, 85, 94, -47, 22, 60, 102, 112, 93, MidiConstants.SONG_SELECT, 69, 64, -52, -24, Opcodes.OPC_lcmp, 86, 8, WasmBinary.REC_GROUP, 26, 58, -46, -31, WasmBinary.STRUCT_TYPE, Opcodes.OPC_putfield, 56, 110, 14, -27, -12, -7, Opcodes.OPC_i2f, -23, 79, -42, Opcodes.OPC_i2l, 35, WasmBinary.SUB_FINAL_TYPE, 50, Opcodes.OPC_ifeq, 49, 20, Opcodes.OPC_freturn, -18, Opcodes.OPC_goto_w, 72, -45, 48, Opcodes.OPC_if_icmplt, Opcodes.OPC_i2c, 65, Opcodes.OPC_return, 24, Opcodes.OPC_wide, 44, 113, 114, 68, 21, -3, 55, Opcodes.OPC_arraylength, 95, Opcodes.OPC_tableswitch, Opcodes.OPC_iflt, Opcodes.OPC_l2i, -40, Opcodes.OPC_lookupswitch, Opcodes.OPC_l2f, Opcodes.OPC_ifge, -6, 96, -22, -68, 98, 12, 36, Opcodes.OPC_if_acmpne, Opcodes.OPC_jsr, -20, 103, 32, -37, 124, 40, -35, Opcodes.OPC_ireturn, 91, 52, 126, 16, MidiConstants.MIDI_TIME_CODE, 123, Opcodes.OPC_d2l, 99, -96, 5, Opcodes.OPC_ifne, 67, 119, 33, -65, 39, 9, Opcodes.OPC_monitorexit, Opcodes.OPC_if_icmpeq, Opcodes.OPC_invokevirtual, -41, 41, Opcodes.OPC_monitorenter, -21, -64, Opcodes.OPC_if_icmple, Opcodes.OPC_f2i, Opcodes.OPC_f2l, 29, -5, -1, Opcodes.OPC_instanceof, Opcodes.OPC_getstatic, Opcodes.OPC_dcmpl, 46, -8, 101, -10, 117, 7, 4, 73, 51, -28, -39, Opcodes.OPC_invokeinterface, -48, 66, Opcodes.OPC_ifnonnull, 108, -112, 0, Opcodes.OPC_d2i, 111, 80, 1, Opcodes.OPC_multianewarray, -38, 71, 63, -51, 105, Opcodes.OPC_if_icmpge, -30, 122, Opcodes.OPC_goto, Opcodes.OPC_ifnull, Opcodes.OPC_i2s, 15, 10, 6, -26, 43, Opcodes.OPC_fcmpg, Opcodes.OPC_if_icmpgt, 28, Opcodes.OPC_dreturn, 106, 18, Opcodes.OPC_iinc, 57, -25, -80, Opcodes.OPC_ixor, -9, -2, Opcodes.OPC_ifgt, Opcodes.OPC_i2d, 92, Opcodes.OPC_lor, 53, WasmBinary.ARRAY_TYPE, Opcodes.OPC_getfield, Opcodes.OPC_if_acmpeq, -4, -128, ByteSourceJsonBootstrapper.UTF8_BOM_1, -53, -69, 107, 118, Opcodes.OPC_invokedynamic, 90, 125, 120, 11, Opcodes.OPC_fcmpl, -29, Opcodes.OPC_lreturn, 116, Opcodes.OPC_dcmpg, 59, 54, 100, 109, -36, -16, 89, Opcodes.OPC_ret, 76, 23, 127, Opcodes.OPC_i2b, Opcodes.OPC_invokestatic, Opcodes.OPC_jsr_w, 87, 27, -32, 97};
    private static final byte[] T0 = {Opcodes.OPC_if_icmple, Opcodes.OPC_if_icmpge, Opcodes.OPC_ret, Opcodes.OPC_multianewarray, 78, Opcodes.OPC_jsr_w, 3, -39, 126, 15, -46, Opcodes.OPC_lreturn, -25, -45, 39, 91, -29, Opcodes.OPC_if_icmplt, -24, -26, 124, 42, 85, 12, Opcodes.OPC_i2f, 57, -41, Opcodes.OPC_f2d, Opcodes.OPC_invokestatic, 18, 111, 40, -51, Opcodes.OPC_l2d, 112, 86, 114, -7, -65, 79, 115, -23, -9, 87, 22, Opcodes.OPC_ireturn, 80, -64, Opcodes.OPC_ifgt, Opcodes.OPC_invokespecial, 71, 113, 96, Opcodes.OPC_wide, 116, 67, 108, 31, Opcodes.OPC_i2s, 119, -36, WasmBinary.REC_GROUP, 32, Opcodes.OPC_f2l, Opcodes.OPC_ifeq, 95, 68, 1, -11, 30, Opcodes.OPC_i2d, 94, 97, 44, 75, 29, Opcodes.OPC_lor, 21, -12, 35, -42, -22, -31, 103, MidiConstants.MIDI_TIME_CODE, 127, -2, -38, 60, 7, 83, 106, Opcodes.OPC_iinc, Opcodes.OPC_ifge, -53, 2, Opcodes.OPC_lxor, 51, -35, 53, -30, 89, 90, Opcodes.OPC_dcmpg, Opcodes.OPC_if_acmpeq, Opcodes.OPC_i2c, 100, 4, 6, 16, 77, 28, Opcodes.OPC_dcmpl, 8, 49, -18, Opcodes.OPC_lookupswitch, 5, Opcodes.OPC_dreturn, 121, -96, 24, 70, 109, -4, Opcodes.OPC_l2f, -44, Opcodes.OPC_ifnonnull, -1, -16, WasmBinary.SUB_FINAL_TYPE, 66, Opcodes.OPC_i2b, -8, 104, 10, 101, Opcodes.OPC_d2i, Opcodes.OPC_invokevirtual, -3, Opcodes.OPC_monitorexit, ByteSourceJsonBootstrapper.UTF8_BOM_1, 120, 76, -52, Opcodes.OPC_ifle, 48, 46, -68, 11, 84, 26, Opcodes.OPC_if_acmpne, -69, 38, -128, 72, Opcodes.OPC_lcmp, 50, 125, Opcodes.OPC_goto, 63, Opcodes.OPC_freturn, 34, 61, 102, Opcodes.OPC_tableswitch, -10, 0, 93, Opcodes.OPC_anewarray, 74, -32, 59, Opcodes.OPC_getfield, 23, Opcodes.OPC_f2i, Opcodes.OPC_if_icmpeq, 118, -80, 36, Opcodes.OPC_ifne, 37, 99, -37, -21, 122, 62, 92, Opcodes.OPC_putstatic, Opcodes.OPC_return, 41, MidiConstants.SONG_POSITION_POBYTEER, -54, 88, 110, -40, Opcodes.OPC_jsr, 47, 117, WasmBinary.STRUCT_TYPE, 20, -5, 19, 73, Opcodes.OPC_l2i, Opcodes.OPC_getstatic, -20, -28, 52, 45, Opcodes.OPC_fcmpg, Opcodes.OPC_ifnull, 58, -19, Opcodes.OPC_fcmpl, 14, -27, Opcodes.OPC_i2l, 107, 64, 33, Opcodes.OPC_iflt, 9, 25, 43, 82, WasmBinary.ARRAY_TYPE, 69, Opcodes.OPC_if_icmpgt, -6, 81, Opcodes.OPC_monitorenter, Opcodes.OPC_putfield, -47, -112, Opcodes.OPC_invokeinterface, MidiConstants.SONG_SELECT, 55, Opcodes.OPC_instanceof, 13, Opcodes.OPC_invokedynamic, 65, 17, 56, 123, Opcodes.OPC_arraylength, -48, -43, 105, 54, Opcodes.OPC_goto_w, 98, 27, Opcodes.OPC_ixor, Opcodes.OPC_d2l};
    private static final byte[] T1 = {Opcodes.OPC_lxor, MidiConstants.SONG_POSITION_POBYTEER, 42, -21, -23, -65, 123, Opcodes.OPC_ifge, 52, Opcodes.OPC_fcmpg, Opcodes.OPC_f2d, Opcodes.OPC_dcmpg, Opcodes.OPC_invokeinterface, 105, Opcodes.OPC_f2l, 41, 61, Opcodes.OPC_l2i, 104, 6, 57, 17, 76, 14, -96, 86, 64, Opcodes.OPC_i2c, 21, -68, Opcodes.OPC_putstatic, -36, 111, -8, 38, Opcodes.OPC_invokedynamic, Opcodes.OPC_arraylength, Opcodes.OPC_anewarray, 49, -5, Opcodes.OPC_monitorexit, -2, -128, 97, -31, 122, 50, -46, 112, 32, Opcodes.OPC_if_icmplt, 69, -20, -39, 26, 93, Opcodes.OPC_getfield, -40, 9, Opcodes.OPC_if_acmpeq, 85, Opcodes.OPC_d2i, 55, 118, Opcodes.OPC_ret, 103, 16, 23, 54, 101, Opcodes.OPC_return, Opcodes.OPC_fcmpl, 98, 89, 116, Opcodes.OPC_if_icmpgt, 80, 47, 75, Opcodes.OPC_goto_w, -48, Opcodes.OPC_d2l, -51, -44, 60, Opcodes.OPC_i2f, 18, 29, 35, ByteSourceJsonBootstrapper.UTF8_BOM_1, -12, 83, 25, 53, -26, 127, 94, -42, 121, 81, 34, 20, -9, 30, 74, 66, Opcodes.OPC_iflt, 65, 115, 45, Opcodes.OPC_instanceof, 92, Opcodes.OPC_if_acmpne, Opcodes.OPC_if_icmpge, -32, 46, -45, 40, -69, Opcodes.OPC_jsr_w, Opcodes.OPC_freturn, 106, -47, 90, 48, -112, Opcodes.OPC_iinc, -7, Opcodes.OPC_getstatic, 88, WasmBinary.SUB_FINAL_TYPE, 126, Opcodes.OPC_multianewarray, -53, Opcodes.OPC_dcmpl, -28, 22, 108, -6, -80, 109, 31, 82, Opcodes.OPC_ifeq, 13, 78, 3, Opcodes.OPC_i2b, Opcodes.OPC_monitorenter, 77, 100, 119, Opcodes.OPC_if_icmpeq, -35, Opcodes.OPC_wide, 73, Opcodes.OPC_l2d, Opcodes.OPC_ifne, 36, 56, Opcodes.OPC_goto, 87, Opcodes.OPC_i2l, Opcodes.OPC_ifnonnull, 124, 125, -25, -10, Opcodes.OPC_invokespecial, Opcodes.OPC_ireturn, 39, 70, WasmBinary.ARRAY_TYPE, WasmBinary.STRUCT_TYPE, 59, -41, Opcodes.OPC_ifle, 43, 11, -43, 19, 117, -16, 114, Opcodes.OPC_invokevirtual, Opcodes.OPC_ifgt, 27, 1, 63, 68, -27, Opcodes.OPC_i2d, -3, 7, MidiConstants.MIDI_TIME_CODE, Opcodes.OPC_lookupswitch, Opcodes.OPC_lcmp, 24, -22, -4, 58, Opcodes.OPC_ixor, 95, 5, 84, -37, 0, Opcodes.OPC_f2i, -29, 72, 12, -54, 120, Opcodes.OPC_l2f, 10, -1, 62, 91, Opcodes.OPC_lor, -18, 113, -30, -38, 44, Opcodes.OPC_invokestatic, Opcodes.OPC_putfield, -52, 110, Opcodes.OPC_jsr, 107, Opcodes.OPC_lreturn, 96, Opcodes.OPC_ifnull, 8, 4, 2, -24, -11, 79, Opcodes.OPC_if_icmple, MidiConstants.SONG_SELECT, -64, WasmBinary.REC_GROUP, 67, 37, 28, 33, 51, 15, Opcodes.OPC_dreturn, 71, -19, 102, 99, Opcodes.OPC_i2s, Opcodes.OPC_tableswitch};
    private static final byte[] T2 = {69, -44, 11, 67, MidiConstants.MIDI_TIME_CODE, 114, -19, Opcodes.OPC_if_icmple, Opcodes.OPC_monitorenter, 56, -26, 113, -3, Opcodes.OPC_invokevirtual, 58, Opcodes.OPC_fcmpl, 80, 68, 75, -30, 116, 107, 30, 17, 90, Opcodes.OPC_ifnull, Opcodes.OPC_getfield, -40, Opcodes.OPC_if_acmpeq, Opcodes.OPC_l2d, 112, Opcodes.OPC_if_icmpgt, Opcodes.OPC_jsr, -6, 5, -39, Opcodes.OPC_dcmpl, 64, Opcodes.OPC_jsr_w, -112, Opcodes.OPC_dcmpg, Opcodes.OPC_d2l, -36, 18, 49, 44, 71, 106, Opcodes.OPC_ifeq, Opcodes.OPC_freturn, Opcodes.OPC_goto_w, 127, -7, 79, 93, Opcodes.OPC_fcmpg, 111, -12, Opcodes.OPC_putstatic, 57, 33, -38, Opcodes.OPC_ifge, Opcodes.OPC_i2l, Opcodes.OPC_ifle, 59, -16, -65, ByteSourceJsonBootstrapper.UTF8_BOM_1, 6, -18, -27, 95, 32, 16, -52, 60, 84, 74, 82, Opcodes.OPC_lcmp, 14, -64, 40, -10, 86, 96, Opcodes.OPC_if_icmpge, -29, 15, -20, Opcodes.OPC_ifgt, 36, Opcodes.OPC_lxor, 126, -43, 124, -21, 24, -41, -51, -35, 120, -1, -37, Opcodes.OPC_if_icmplt, 9, -48, 118, Opcodes.OPC_iinc, 117, -69, 29, 26, 47, -80, -2, -42, 52, 99, 53, -46, 42, 89, 109, 77, 119, -25, Opcodes.OPC_d2i, 97, WasmBinary.SUB_FINAL_TYPE, Opcodes.OPC_if_icmpeq, WasmBinary.REC_GROUP, 39, -11, -128, Opcodes.OPC_i2f, Opcodes.OPC_ifnonnull, Opcodes.OPC_if_acmpne, -5, -8, Opcodes.OPC_i2d, Opcodes.OPC_lookupswitch, 98, 63, WasmBinary.STRUCT_TYPE, 72, 0, 20, Opcodes.OPC_ifne, Opcodes.OPC_anewarray, 91, 4, Opcodes.OPC_i2c, 2, 37, 101, 76, 83, 12, MidiConstants.SONG_POSITION_POBYTEER, 41, Opcodes.OPC_dreturn, 23, 108, 65, 48, -23, Opcodes.OPC_i2s, 85, -9, Opcodes.OPC_ireturn, 104, 38, Opcodes.OPC_wide, 125, -54, 122, 62, -96, 55, 3, Opcodes.OPC_instanceof, 54, 105, 102, 8, 22, Opcodes.OPC_goto, -68, Opcodes.OPC_multianewarray, -45, 34, Opcodes.OPC_invokespecial, 19, 70, 50, -24, 87, Opcodes.OPC_l2i, 43, Opcodes.OPC_lor, Opcodes.OPC_getstatic, 78, 100, 28, Opcodes.OPC_tableswitch, Opcodes.OPC_i2b, 88, 46, Opcodes.OPC_iflt, 92, 27, 81, 115, 66, 35, 1, 110, MidiConstants.SONG_SELECT, 13, Opcodes.OPC_arraylength, 61, 10, 45, 31, 103, 51, 25, 123, 94, -22, WasmBinary.ARRAY_TYPE, Opcodes.OPC_f2i, -53, Opcodes.OPC_ret, Opcodes.OPC_f2l, Opcodes.OPC_f2d, Opcodes.OPC_lreturn, 73, Opcodes.OPC_ixor, -28, Opcodes.OPC_invokedynamic, Opcodes.OPC_monitorexit, 21, -47, -32, Opcodes.OPC_l2f, -4, Opcodes.OPC_return, Opcodes.OPC_invokeinterface, Opcodes.OPC_putfield, 7, 121, Opcodes.OPC_invokestatic, -31};
    private static final byte[] T3 = {Opcodes.OPC_getstatic, Opcodes.OPC_invokevirtual, 35, 17, Opcodes.OPC_goto, Opcodes.OPC_l2i, Opcodes.OPC_multianewarray, Opcodes.OPC_if_acmpne, 57, Opcodes.OPC_d2l, Opcodes.OPC_wide, -24, 115, 34, 67, Opcodes.OPC_monitorexit, Opcodes.OPC_ixor, 39, -51, 24, 81, 98, 45, -9, 92, 14, 59, -3, -54, Opcodes.OPC_iflt, 13, 15, 121, Opcodes.OPC_f2l, 16, 76, 116, 28, 10, Opcodes.OPC_d2i, 124, Opcodes.OPC_lcmp, 7, Opcodes.OPC_ifnonnull, 94, 20, Opcodes.OPC_if_icmplt, 33, 87, 80, 78, Opcodes.OPC_ret, -128, -39, ByteSourceJsonBootstrapper.UTF8_BOM_1, 100, 65, WasmBinary.SUB_FINAL_TYPE, 60, -18, 46, 19, 41, Opcodes.OPC_invokedynamic, 52, 90, Opcodes.OPC_freturn, Opcodes.OPC_l2d, 97, 51, 18, Opcodes.OPC_invokeinterface, 85, Opcodes.OPC_jsr, 21, 5, -10, 3, 6, 73, Opcodes.OPC_putfield, 37, 9, 22, 12, 42, 56, -4, 32, -12, -27, 127, -41, 49, 43, 102, 111, -1, 114, Opcodes.OPC_i2f, -16, Opcodes.OPC_if_icmpgt, 47, 120, 0, -68, -52, -30, -80, MidiConstants.MIDI_TIME_CODE, 66, Opcodes.OPC_getfield, 48, 95, 96, 4, -20, Opcodes.OPC_if_acmpeq, -29, Opcodes.OPC_f2i, -25, 29, -65, Opcodes.OPC_iinc, 123, -26, Opcodes.OPC_lor, -8, WasmBinary.ARRAY_TYPE, -40, -46, 23, WasmBinary.REC_GROUP, 75, 71, -42, 105, 108, 25, Opcodes.OPC_ifeq, Opcodes.OPC_ifne, 1, Opcodes.OPC_putstatic, Opcodes.OPC_i2l, Opcodes.OPC_return, -7, 89, Opcodes.OPC_monitorenter, 55, -23, Opcodes.OPC_goto_w, -96, -19, 79, Opcodes.OPC_l2f, 104, 109, -43, 38, Opcodes.OPC_i2b, Opcodes.OPC_i2d, 88, Opcodes.OPC_anewarray, Opcodes.OPC_jsr_w, Opcodes.OPC_dcmpg, -36, 117, -64, 118, -11, 103, 107, 126, -21, 82, -53, -47, 91, Opcodes.OPC_if_icmpeq, 11, -37, 64, Opcodes.OPC_i2c, 26, -6, Opcodes.OPC_ireturn, -28, -31, 113, 31, 101, Opcodes.OPC_f2d, Opcodes.OPC_dcmpl, Opcodes.OPC_ifle, Opcodes.OPC_fcmpl, -112, 93, Opcodes.OPC_invokespecial, Opcodes.OPC_instanceof, Opcodes.OPC_dreturn, 84, -5, 2, -32, 53, -69, 58, 77, Opcodes.OPC_lreturn, 44, 61, 86, 8, 27, 74, Opcodes.OPC_i2s, 106, Opcodes.OPC_lookupswitch, Opcodes.OPC_invokestatic, 122, MidiConstants.SONG_POSITION_POBYTEER, 125, -38, 63, -2, 62, Opcodes.OPC_arraylength, -22, Opcodes.OPC_tableswitch, 68, Opcodes.OPC_ifnull, -48, 54, 72, 112, Opcodes.OPC_fcmpg, 119, 36, 83, WasmBinary.STRUCT_TYPE, MidiConstants.SONG_SELECT, Opcodes.OPC_lxor, 40, 50, 69, 30, Opcodes.OPC_if_icmple, -45, Opcodes.OPC_if_icmpge, 70, 110, Opcodes.OPC_ifge, -35, 99, -44, Opcodes.OPC_ifgt};
    private boolean forEncryption;
    private long[] internalState;
    private long[][] roundKeys;
    private int roundsAmount;
    private int wordsInBlock;
    private int wordsInKey;
    private long[] workingKey;

    public DSTU7624Engine(int i) throws IllegalArgumentException {
        if (i != 128 && i != 256 && i != 512) {
            throw new IllegalArgumentException("unsupported block length: only 128/256/512 are allowed");
        }
        int i2 = i >>> 6;
        this.wordsInBlock = i2;
        this.internalState = new long[i2];
    }

    private void addRoundKey(int i) {
        long[] jArr = this.roundKeys[i];
        for (int i2 = 0; i2 < this.wordsInBlock; i2++) {
            long[] jArr2 = this.internalState;
            jArr2[i2] = jArr2[i2] + jArr[i2];
        }
    }

    private void decryptBlock_128(byte[] bArr, int i, byte[] bArr2, int i2) {
        long jLittleEndianToLong = Pack.littleEndianToLong(bArr, i);
        long jLittleEndianToLong2 = Pack.littleEndianToLong(bArr, i + 8);
        long[][] jArr = this.roundKeys;
        int i3 = this.roundsAmount;
        long[] jArr2 = jArr[i3];
        long j = jLittleEndianToLong - jArr2[0];
        long j2 = jLittleEndianToLong2 - jArr2[1];
        while (true) {
            long jMixColumnInv = mixColumnInv(j);
            long jMixColumnInv2 = mixColumnInv(j2);
            int i4 = (int) jMixColumnInv;
            int i5 = (int) (jMixColumnInv >>> 32);
            int i6 = (int) jMixColumnInv2;
            int i7 = (int) (jMixColumnInv2 >>> 32);
            byte[] bArr3 = T0;
            byte b = bArr3[i4 & 255];
            byte[] bArr4 = T1;
            byte b2 = bArr4[(i4 >>> 8) & 255];
            byte[] bArr5 = T2;
            byte b3 = bArr5[(i4 >>> 16) & 255];
            byte[] bArr6 = T3;
            int i8 = (bArr6[i4 >>> 24] << 24) | ((b3 & 255) << 16) | (b & 255) | ((b2 & 255) << 8);
            long j3 = (((long) ((bArr6[i7 >>> 24] << 24) | (((bArr3[i7 & 255] & 255) | ((bArr4[(i7 >>> 8) & 255] & 255) << 8)) | ((bArr5[(i7 >>> 16) & 255] & 255) << 16)))) << 32) | (((long) i8) & 4294967295L);
            long j4 = (((long) ((bArr6[i6 >>> 24] << 24) | (bArr3[i6 & 255] & 255) | ((bArr4[(i6 >>> 8) & 255] & 255) << 8) | ((bArr5[(i6 >>> 16) & 255] & 255) << 16))) & 4294967295L) | (((long) ((bArr6[i5 >>> 24] << 24) | (((bArr3[i5 & 255] & 255) | ((bArr4[(i5 >>> 8) & 255] & 255) << 8)) | ((bArr5[(i5 >>> 16) & 255] & 255) << 16)))) << 32);
            i3--;
            if (i3 == 0) {
                long[] jArr3 = this.roundKeys[0];
                long j5 = j3 - jArr3[0];
                long j6 = j4 - jArr3[1];
                Pack.longToLittleEndian(j5, bArr2, i2);
                Pack.longToLittleEndian(j6, bArr2, i2 + 8);
                return;
            }
            long[] jArr4 = this.roundKeys[i3];
            long j7 = j3 ^ jArr4[0];
            j2 = j4 ^ jArr4[1];
            j = j7;
        }
    }

    private void encryptBlock_128(byte[] bArr, int i, byte[] bArr2, int i2) {
        long jLittleEndianToLong = Pack.littleEndianToLong(bArr, i);
        long jLittleEndianToLong2 = Pack.littleEndianToLong(bArr, i + 8);
        long[] jArr = this.roundKeys[0];
        long j = jLittleEndianToLong + jArr[0];
        long j2 = jLittleEndianToLong2 + jArr[1];
        int i3 = 0;
        while (true) {
            int i4 = (int) j;
            int i5 = (int) (j >>> 32);
            int i6 = (int) j2;
            int i7 = (int) (j2 >>> 32);
            byte[] bArr3 = S0;
            byte b = bArr3[i4 & 255];
            byte[] bArr4 = S1;
            byte b2 = bArr4[(i4 >>> 8) & 255];
            byte[] bArr5 = S2;
            byte b3 = bArr5[(i4 >>> 16) & 255];
            byte[] bArr6 = S3;
            int i8 = ((b3 & 255) << 16) | (b & 255) | ((b2 & 255) << 8) | (bArr6[i4 >>> 24] << 24);
            long j3 = (((long) ((bArr6[i7 >>> 24] << 24) | (((bArr3[i7 & 255] & 255) | ((bArr4[(i7 >>> 8) & 255] & 255) << 8)) | ((bArr5[(i7 >>> 16) & 255] & 255) << 16)))) << 32) | (((long) i8) & 4294967295L);
            int i9 = (bArr6[i6 >>> 24] << 24) | (bArr3[i6 & 255] & 255) | ((bArr4[(i6 >>> 8) & 255] & 255) << 8) | ((bArr5[(i6 >>> 16) & 255] & 255) << 16);
            byte b4 = bArr3[i5 & 255];
            byte b5 = bArr4[(i5 >>> 8) & 255];
            byte b6 = bArr5[(i5 >>> 16) & 255];
            int i10 = bArr6[i5 >>> 24] << 24;
            long jMixColumn = mixColumn(j3);
            long jMixColumn2 = mixColumn((((long) i9) & 4294967295L) | (((long) (i10 | (((b4 & 255) | ((b5 & 255) << 8)) | ((b6 & 255) << 16)))) << 32));
            i3++;
            int i11 = this.roundsAmount;
            if (i3 == i11) {
                long[] jArr2 = this.roundKeys[i11];
                long j4 = jMixColumn + jArr2[0];
                long j5 = jMixColumn2 + jArr2[1];
                Pack.longToLittleEndian(j4, bArr2, i2);
                Pack.longToLittleEndian(j5, bArr2, i2 + 8);
                return;
            }
            long[] jArr3 = this.roundKeys[i3];
            long j6 = jMixColumn ^ jArr3[0];
            j2 = jMixColumn2 ^ jArr3[1];
            j = j6;
        }
    }

    private void invShiftRows() {
        int i = this.wordsInBlock;
        if (i == 2) {
            long[] jArr = this.internalState;
            long j = jArr[0];
            long j2 = jArr[1];
            long j3 = (-4294967296L) & (j ^ j2);
            jArr[0] = j ^ j3;
            jArr[1] = j3 ^ j2;
            return;
        }
        if (i == 4) {
            long[] jArr2 = this.internalState;
            long j4 = jArr2[0];
            long j5 = jArr2[1];
            long j6 = jArr2[2];
            long j7 = jArr2[3];
            long j8 = (j4 ^ j5) & (-281470681808896L);
            long j9 = j4 ^ j8;
            long j10 = j5 ^ j8;
            long j11 = (j6 ^ j7) & (-281470681808896L);
            long j12 = j6 ^ j11;
            long j13 = j7 ^ j11;
            long j14 = (j9 ^ j12) & (-4294967296L);
            long j15 = j9 ^ j14;
            long j16 = (j10 ^ j13) & 281474976645120L;
            jArr2[0] = j15;
            jArr2[1] = j10 ^ j16;
            jArr2[2] = j12 ^ j14;
            jArr2[3] = j16 ^ j13;
            return;
        }
        if (i != 8) {
            throw new IllegalStateException("unsupported block length: only 128/256/512 are allowed");
        }
        long[] jArr3 = this.internalState;
        long j17 = jArr3[0];
        long j18 = jArr3[1];
        long j19 = jArr3[2];
        long j20 = jArr3[3];
        long j21 = jArr3[4];
        long j22 = jArr3[5];
        long j23 = jArr3[6];
        long j24 = jArr3[7];
        long j25 = (j17 ^ j18) & (-71777214294589696L);
        long j26 = j17 ^ j25;
        long j27 = j18 ^ j25;
        long j28 = (j19 ^ j20) & (-71777214294589696L);
        long j29 = j19 ^ j28;
        long j30 = j20 ^ j28;
        long j31 = (j21 ^ j22) & (-71777214294589696L);
        long j32 = j21 ^ j31;
        long j33 = j22 ^ j31;
        long j34 = (j23 ^ j24) & (-71777214294589696L);
        long j35 = j23 ^ j34;
        long j36 = j24 ^ j34;
        long j37 = (j26 ^ j29) & (-281470681808896L);
        long j38 = j26 ^ j37;
        long j39 = j29 ^ j37;
        long j40 = (j27 ^ j30) & 72056494543077120L;
        long j41 = j27 ^ j40;
        long j42 = j30 ^ j40;
        long j43 = (j32 ^ j35) & (-281470681808896L);
        long j44 = j32 ^ j43;
        long j45 = j35 ^ j43;
        long j46 = (j33 ^ j36) & 72056494543077120L;
        long j47 = j33 ^ j46;
        long j48 = j36 ^ j46;
        long j49 = (j38 ^ j44) & (-4294967296L);
        long j50 = j38 ^ j49;
        long j51 = j44 ^ j49;
        long j52 = (j41 ^ j47) & 72057594021150720L;
        long j53 = j41 ^ j52;
        long j54 = (j39 ^ j45) & 281474976645120L;
        long j55 = j39 ^ j54;
        long j56 = j54 ^ j45;
        long j57 = (j42 ^ j48) & 1099511627520L;
        jArr3[0] = j50;
        jArr3[1] = j53;
        jArr3[2] = j55;
        jArr3[3] = j42 ^ j57;
        jArr3[4] = j51;
        jArr3[5] = j47 ^ j52;
        jArr3[6] = j56;
        jArr3[7] = j48 ^ j57;
    }

    private void invSubBytes() {
        for (int i = 0; i < this.wordsInBlock; i++) {
            long[] jArr = this.internalState;
            long j = jArr[i];
            int i2 = (int) j;
            int i3 = (int) (j >>> 32);
            byte[] bArr = T0;
            byte b = bArr[i2 & 255];
            byte[] bArr2 = T1;
            byte b2 = bArr2[(i2 >>> 8) & 255];
            byte[] bArr3 = T2;
            byte b3 = bArr3[(i2 >>> 16) & 255];
            byte[] bArr4 = T3;
            int i4 = (bArr4[i2 >>> 24] << 24) | (b & 255) | ((b2 & 255) << 8) | ((b3 & 255) << 16);
            byte b4 = bArr[i3 & 255];
            byte b5 = bArr2[(i3 >>> 8) & 255];
            byte b6 = bArr3[(i3 >>> 16) & 255];
            jArr[i] = (((long) i4) & 4294967295L) | (((long) ((bArr4[i3 >>> 24] << 24) | (((b4 & 255) | ((b5 & 255) << 8)) | ((b6 & 255) << 16)))) << 32);
        }
    }

    private static long mixColumn(long j) {
        long jMulX = mulX(j);
        long jRotate = rotate(8, j) ^ j;
        long jRotate2 = (jRotate ^ rotate(16, jRotate)) ^ rotate(48, j);
        return ((rotate(32, mulX2((j ^ jRotate2) ^ jMulX)) ^ jRotate2) ^ rotate(40, jMulX)) ^ rotate(48, jMulX);
    }

    private static long mixColumnInv(long j) {
        long jRotate = rotate(8, j) ^ j;
        long jRotate2 = (jRotate ^ rotate(32, jRotate)) ^ rotate(48, j);
        long j2 = jRotate2 ^ j;
        long jRotate3 = rotate(48, j);
        long jRotate4 = rotate(56, j);
        long jMulX = mulX(j2 ^ jRotate4) ^ rotate(56, j2);
        long jMulX2 = mulX(rotate(40, mulX(jMulX) ^ j) ^ (rotate(16, j2) ^ j)) ^ (j2 ^ jRotate3);
        return mulX(rotate(40, ((j ^ rotate(32, j2)) ^ jRotate4) ^ mulX(((jRotate3 ^ (rotate(24, j) ^ j2)) ^ jRotate4) ^ mulX(mulX(jMulX2) ^ rotate(16, jRotate2))))) ^ jRotate2;
    }

    private void mixColumns() {
        for (int i = 0; i < this.wordsInBlock; i++) {
            long[] jArr = this.internalState;
            jArr[i] = mixColumn(jArr[i]);
        }
    }

    private void mixColumnsInv() {
        for (int i = 0; i < this.wordsInBlock; i++) {
            long[] jArr = this.internalState;
            jArr[i] = mixColumnInv(jArr[i]);
        }
    }

    private static long mulX(long j) {
        return (((j & (-9187201950435737472L)) >>> 7) * 29) ^ ((9187201950435737471L & j) << 1);
    }

    private static long mulX2(long j) {
        return (((j & 4629771061636907072L) >>> 6) * 29) ^ (((4557430888798830399L & j) << 2) ^ ((((-9187201950435737472L) & j) >>> 6) * 29));
    }

    private static long rotate(int i, long j) {
        return (j << (-i)) | (j >>> i);
    }

    private void rotateLeft(long[] jArr, long[] jArr2) {
        int i = this.wordsInBlock;
        if (i == 2) {
            long j = jArr[0];
            long j2 = jArr[1];
            jArr2[0] = (j >>> 56) | (j2 << 8);
            jArr2[1] = (j << 8) | (j2 >>> 56);
            return;
        }
        if (i == 4) {
            long j3 = jArr[0];
            long j4 = jArr[1];
            long j5 = jArr[2];
            long j6 = jArr[3];
            jArr2[0] = (j4 >>> 24) | (j5 << 40);
            jArr2[1] = (j5 >>> 24) | (j6 << 40);
            jArr2[2] = (j6 >>> 24) | (j3 << 40);
            jArr2[3] = (j3 >>> 24) | (j4 << 40);
            return;
        }
        if (i != 8) {
            throw new IllegalStateException("unsupported block length: only 128/256/512 are allowed");
        }
        long j7 = jArr[0];
        long j8 = jArr[1];
        long j9 = jArr[2];
        long j10 = jArr[3];
        long j11 = jArr[4];
        long j12 = jArr[5];
        long j13 = jArr[6];
        long j14 = jArr[7];
        jArr2[0] = (j9 >>> 24) | (j10 << 40);
        jArr2[1] = (j10 >>> 24) | (j11 << 40);
        jArr2[2] = (j11 >>> 24) | (j12 << 40);
        jArr2[3] = (j12 >>> 24) | (j13 << 40);
        jArr2[4] = (j13 >>> 24) | (j14 << 40);
        jArr2[5] = (j14 >>> 24) | (j7 << 40);
        jArr2[6] = (j7 >>> 24) | (j8 << 40);
        jArr2[7] = (j8 >>> 24) | (j9 << 40);
    }

    private void shiftRows() {
        int i = this.wordsInBlock;
        if (i == 2) {
            long[] jArr = this.internalState;
            long j = jArr[0];
            long j2 = jArr[1];
            long j3 = (-4294967296L) & (j ^ j2);
            jArr[0] = j ^ j3;
            jArr[1] = j3 ^ j2;
            return;
        }
        if (i == 4) {
            long[] jArr2 = this.internalState;
            long j4 = jArr2[0];
            long j5 = jArr2[1];
            long j6 = jArr2[2];
            long j7 = jArr2[3];
            long j8 = (j4 ^ j6) & (-4294967296L);
            long j9 = j4 ^ j8;
            long j10 = j6 ^ j8;
            long j11 = (j5 ^ j7) & 281474976645120L;
            long j12 = j5 ^ j11;
            long j13 = j7 ^ j11;
            long j14 = (j9 ^ j12) & (-281470681808896L);
            long j15 = (j10 ^ j13) & (-281470681808896L);
            jArr2[0] = j9 ^ j14;
            jArr2[1] = j12 ^ j14;
            jArr2[2] = j10 ^ j15;
            jArr2[3] = j13 ^ j15;
            return;
        }
        if (i != 8) {
            throw new IllegalStateException("unsupported block length: only 128/256/512 are allowed");
        }
        long[] jArr3 = this.internalState;
        long j16 = jArr3[0];
        long j17 = jArr3[1];
        long j18 = jArr3[2];
        long j19 = jArr3[3];
        long j20 = jArr3[4];
        long j21 = jArr3[5];
        long j22 = jArr3[6];
        long j23 = jArr3[7];
        long j24 = (j16 ^ j20) & (-4294967296L);
        long j25 = j16 ^ j24;
        long j26 = j20 ^ j24;
        long j27 = (j17 ^ j21) & 72057594021150720L;
        long j28 = j17 ^ j27;
        long j29 = j21 ^ j27;
        long j30 = (j18 ^ j22) & 281474976645120L;
        long j31 = j18 ^ j30;
        long j32 = j22 ^ j30;
        long j33 = (j19 ^ j23) & 1099511627520L;
        long j34 = j19 ^ j33;
        long j35 = j23 ^ j33;
        long j36 = (j25 ^ j31) & (-281470681808896L);
        long j37 = j25 ^ j36;
        long j38 = j31 ^ j36;
        long j39 = (j28 ^ j34) & 72056494543077120L;
        long j40 = j28 ^ j39;
        long j41 = j34 ^ j39;
        long j42 = (j26 ^ j32) & (-281470681808896L);
        long j43 = j26 ^ j42;
        long j44 = j32 ^ j42;
        long j45 = (j29 ^ j35) & 72056494543077120L;
        long j46 = j29 ^ j45;
        long j47 = j35 ^ j45;
        long j48 = (j37 ^ j40) & (-71777214294589696L);
        long j49 = j37 ^ j48;
        long j50 = j40 ^ j48;
        long j51 = (j38 ^ j41) & (-71777214294589696L);
        long j52 = j38 ^ j51;
        long j53 = j41 ^ j51;
        long j54 = (j43 ^ j46) & (-71777214294589696L);
        long j55 = j43 ^ j54;
        long j56 = j46 ^ j54;
        long j57 = (j44 ^ j47) & (-71777214294589696L);
        jArr3[0] = j49;
        jArr3[1] = j50;
        jArr3[2] = j52;
        jArr3[3] = j53;
        jArr3[4] = j55;
        jArr3[5] = j56;
        jArr3[6] = j44 ^ j57;
        jArr3[7] = j47 ^ j57;
    }

    private void subBytes() {
        for (int i = 0; i < this.wordsInBlock; i++) {
            long[] jArr = this.internalState;
            long j = jArr[i];
            int i2 = (int) j;
            int i3 = (int) (j >>> 32);
            byte[] bArr = S0;
            byte b = bArr[i2 & 255];
            byte[] bArr2 = S1;
            byte b2 = bArr2[(i2 >>> 8) & 255];
            byte[] bArr3 = S2;
            byte b3 = bArr3[(i2 >>> 16) & 255];
            byte[] bArr4 = S3;
            int i4 = (bArr4[i2 >>> 24] << 24) | (b & 255) | ((b2 & 255) << 8) | ((b3 & 255) << 16);
            byte b4 = bArr[i3 & 255];
            byte b5 = bArr2[(i3 >>> 8) & 255];
            byte b6 = bArr3[(i3 >>> 16) & 255];
            jArr[i] = (((long) i4) & 4294967295L) | (((long) ((bArr4[i3 >>> 24] << 24) | (((b4 & 255) | ((b5 & 255) << 8)) | ((b6 & 255) << 16)))) << 32);
        }
    }

    private void subRoundKey(int i) {
        long[] jArr = this.roundKeys[i];
        for (int i2 = 0; i2 < this.wordsInBlock; i2++) {
            long[] jArr2 = this.internalState;
            jArr2[i2] = jArr2[i2] - jArr[i2];
        }
    }

    private void workingKeyExpandEven(long[] jArr, long[] jArr2) {
        int i;
        int i2;
        int i3 = this.wordsInKey;
        long[] jArr3 = new long[i3];
        long[] jArr4 = new long[this.wordsInBlock];
        System.arraycopy(jArr, 0, jArr3, 0, i3);
        long j = 281479271743489L;
        int i4 = 0;
        while (true) {
            for (int i5 = 0; i5 < this.wordsInBlock; i5++) {
                jArr4[i5] = jArr2[i5] + j;
            }
            for (int i6 = 0; i6 < this.wordsInBlock; i6++) {
                this.internalState[i6] = jArr3[i6] + jArr4[i6];
            }
            subBytes();
            shiftRows();
            mixColumns();
            for (int i7 = 0; i7 < this.wordsInBlock; i7++) {
                long[] jArr5 = this.internalState;
                jArr5[i7] = jArr5[i7] ^ jArr4[i7];
            }
            subBytes();
            shiftRows();
            mixColumns();
            int i8 = 0;
            while (true) {
                i = this.wordsInBlock;
                if (i8 >= i) {
                    break;
                }
                long[] jArr6 = this.internalState;
                jArr6[i8] = jArr6[i8] + jArr4[i8];
                i8++;
            }
            System.arraycopy(this.internalState, 0, this.roundKeys[i4], 0, i);
            if (this.roundsAmount == i4) {
                return;
            }
            if (this.wordsInBlock != this.wordsInKey) {
                i4 += 2;
                j <<= 1;
                for (int i9 = 0; i9 < this.wordsInBlock; i9++) {
                    jArr4[i9] = jArr2[i9] + j;
                }
                int i10 = 0;
                while (true) {
                    int i11 = this.wordsInBlock;
                    if (i10 >= i11) {
                        break;
                    }
                    this.internalState[i10] = jArr3[i11 + i10] + jArr4[i10];
                    i10++;
                }
                subBytes();
                shiftRows();
                mixColumns();
                for (int i12 = 0; i12 < this.wordsInBlock; i12++) {
                    long[] jArr7 = this.internalState;
                    jArr7[i12] = jArr7[i12] ^ jArr4[i12];
                }
                subBytes();
                shiftRows();
                mixColumns();
                int i13 = 0;
                while (true) {
                    i2 = this.wordsInBlock;
                    if (i13 >= i2) {
                        break;
                    }
                    long[] jArr8 = this.internalState;
                    jArr8[i13] = jArr8[i13] + jArr4[i13];
                    i13++;
                }
                System.arraycopy(this.internalState, 0, this.roundKeys[i4], 0, i2);
                if (this.roundsAmount == i4) {
                    return;
                }
            }
            i4 += 2;
            j <<= 1;
            long j2 = jArr3[0];
            for (int i14 = 1; i14 < i3; i14++) {
                jArr3[i14 - 1] = jArr3[i14];
            }
            jArr3[i3 - 1] = j2;
        }
    }

    private void workingKeyExpandKT(long[] jArr, long[] jArr2) {
        int i = this.wordsInBlock;
        long[] jArr3 = new long[i];
        long[] jArr4 = new long[i];
        long[] jArr5 = new long[i];
        this.internalState = jArr5;
        long j = jArr5[0];
        int i2 = this.wordsInKey;
        jArr5[0] = j + ((long) (i + i2 + 1));
        System.arraycopy(jArr, 0, jArr3, 0, i);
        if (i == i2) {
            System.arraycopy(jArr, 0, jArr4, 0, i);
        } else {
            int i3 = this.wordsInBlock;
            System.arraycopy(jArr, i3, jArr4, 0, i3);
        }
        int i4 = 0;
        while (true) {
            long[] jArr6 = this.internalState;
            if (i4 >= jArr6.length) {
                break;
            }
            jArr6[i4] = jArr6[i4] + jArr3[i4];
            i4++;
        }
        subBytes();
        shiftRows();
        mixColumns();
        int i5 = 0;
        while (true) {
            long[] jArr7 = this.internalState;
            if (i5 >= jArr7.length) {
                break;
            }
            jArr7[i5] = jArr7[i5] ^ jArr4[i5];
            i5++;
        }
        subBytes();
        shiftRows();
        mixColumns();
        int i6 = 0;
        while (true) {
            long[] jArr8 = this.internalState;
            if (i6 >= jArr8.length) {
                subBytes();
                shiftRows();
                mixColumns();
                System.arraycopy(this.internalState, 0, jArr2, 0, this.wordsInBlock);
                return;
            }
            jArr8[i6] = jArr8[i6] + jArr3[i6];
            i6++;
        }
    }

    private void workingKeyExpandOdd() {
        for (int i = 1; i < this.roundsAmount; i += 2) {
            long[][] jArr = this.roundKeys;
            rotateLeft(jArr[i - 1], jArr[i]);
        }
    }

    private void xorRoundKey(int i) {
        long[] jArr = this.roundKeys[i];
        for (int i2 = 0; i2 < this.wordsInBlock; i2++) {
            long[] jArr2 = this.internalState;
            jArr2[i2] = jArr2[i2] ^ jArr[i2];
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "DSTU7624";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.wordsInBlock << 3;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x005b A[LOOP:0: B:26:0x0056->B:28:0x005b, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:31:0x006f  */
    /* JADX WARN: Code duplicated, block: B:33:0x0084  */
    /* JADX WARN: Code duplicated, block: B:37:0x0064 A[EDGE_INSN: B:37:0x0064->B:29:0x0064 BREAK  A[LOOP:0: B:26:0x0056->B:28:0x005b], SYNTHETIC] */
    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        int i;
        int i2;
        long[][] jArr;
        long[] jArr2;
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("Invalid parameter passed to DSTU7624Engine init");
        }
        this.forEncryption = z;
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        int length = key.length << 3;
        int i3 = this.wordsInBlock << 6;
        if (length != 128 && length != 256 && length != 512) {
            throw new IllegalArgumentException("unsupported key length: only 128/256/512 are allowed");
        }
        if (length != i3 && length != i3 * 2) {
            throw new IllegalArgumentException("Unsupported key length");
        }
        if (length == 128) {
            i = 10;
        } else {
            if (length != 256) {
                if (length == 512) {
                    i = 18;
                }
                this.wordsInKey = length >>> 6;
                this.roundKeys = new long[this.roundsAmount + 1][];
                i2 = 0;
                while (true) {
                    jArr = this.roundKeys;
                    if (i2 < jArr.length) {
                        break;
                    }
                    jArr[i2] = new long[this.wordsInBlock];
                    i2++;
                }
                jArr2 = new long[this.wordsInKey];
                this.workingKey = jArr2;
                if (key.length == (length >>> 3)) {
                    throw new IllegalArgumentException("Invalid key parameter passed to DSTU7624Engine init");
                }
                Pack.littleEndianToLong(key, 0, jArr2);
                long[] jArr3 = new long[this.wordsInBlock];
                workingKeyExpandKT(this.workingKey, jArr3);
                workingKeyExpandEven(this.workingKey, jArr3);
                workingKeyExpandOdd();
            }
            i = 14;
        }
        this.roundsAmount = i;
        this.wordsInKey = length >>> 6;
        this.roundKeys = new long[this.roundsAmount + 1][];
        i2 = 0;
        while (true) {
            jArr = this.roundKeys;
            if (i2 < jArr.length) {
                break;
                break;
            } else {
                jArr[i2] = new long[this.wordsInBlock];
                i2++;
            }
        }
        jArr2 = new long[this.wordsInKey];
        this.workingKey = jArr2;
        if (key.length == (length >>> 3)) {
            throw new IllegalArgumentException("Invalid key parameter passed to DSTU7624Engine init");
        }
        Pack.littleEndianToLong(key, 0, jArr2);
        long[] jArr4 = new long[this.wordsInBlock];
        workingKeyExpandKT(this.workingKey, jArr4);
        workingKeyExpandEven(this.workingKey, jArr4);
        workingKeyExpandOdd();
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws IllegalStateException, DataLengthException {
        int i3;
        if (this.workingKey == null) {
            throw new IllegalStateException("DSTU7624 engine not initialised");
        }
        if (getBlockSize() + i > bArr.length) {
            throw new DataLengthException("Input buffer too short");
        }
        if (getBlockSize() + i2 > bArr2.length) {
            throw new OutputLengthException("Output buffer too short");
        }
        int i4 = 0;
        if (this.forEncryption) {
            if (this.wordsInBlock != 2) {
                Pack.littleEndianToLong(bArr, i, this.internalState);
                addRoundKey(0);
                while (true) {
                    subBytes();
                    shiftRows();
                    mixColumns();
                    i4++;
                    i3 = this.roundsAmount;
                    if (i4 == i3) {
                        break;
                    }
                    xorRoundKey(i4);
                }
                addRoundKey(i3);
                Pack.longToLittleEndian(this.internalState, bArr2, i2);
            } else {
                encryptBlock_128(bArr, i, bArr2, i2);
            }
        } else if (this.wordsInBlock != 2) {
            Pack.littleEndianToLong(bArr, i, this.internalState);
            subRoundKey(this.roundsAmount);
            int i5 = this.roundsAmount;
            while (true) {
                mixColumnsInv();
                invShiftRows();
                invSubBytes();
                i5--;
                if (i5 == 0) {
                    break;
                }
                xorRoundKey(i5);
            }
            subRoundKey(0);
            Pack.longToLittleEndian(this.internalState, bArr2, i2);
        } else {
            decryptBlock_128(bArr, i, bArr2, i2);
        }
        return getBlockSize();
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        Arrays.fill(this.internalState, 0L);
    }
}
