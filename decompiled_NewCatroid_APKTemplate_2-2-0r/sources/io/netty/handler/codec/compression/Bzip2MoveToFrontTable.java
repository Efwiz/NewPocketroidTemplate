package io.netty.handler.codec.compression;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import org.billthefarmer.mididriver.MidiConstants;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes11.dex */
final class Bzip2MoveToFrontTable {
    private final byte[] mtf = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, -128, Opcodes.OPC_lor, Opcodes.OPC_ixor, Opcodes.OPC_lxor, Opcodes.OPC_iinc, Opcodes.OPC_i2l, Opcodes.OPC_i2f, Opcodes.OPC_i2d, Opcodes.OPC_l2i, Opcodes.OPC_l2f, Opcodes.OPC_l2d, Opcodes.OPC_f2i, Opcodes.OPC_f2l, Opcodes.OPC_f2d, Opcodes.OPC_d2i, Opcodes.OPC_d2l, -112, Opcodes.OPC_i2b, Opcodes.OPC_i2c, Opcodes.OPC_i2s, Opcodes.OPC_lcmp, Opcodes.OPC_fcmpl, Opcodes.OPC_fcmpg, Opcodes.OPC_dcmpl, Opcodes.OPC_dcmpg, Opcodes.OPC_ifeq, Opcodes.OPC_ifne, Opcodes.OPC_iflt, Opcodes.OPC_ifge, Opcodes.OPC_ifgt, Opcodes.OPC_ifle, Opcodes.OPC_if_icmpeq, -96, Opcodes.OPC_if_icmplt, Opcodes.OPC_if_icmpge, Opcodes.OPC_if_icmpgt, Opcodes.OPC_if_icmple, Opcodes.OPC_if_acmpeq, Opcodes.OPC_if_acmpne, Opcodes.OPC_goto, Opcodes.OPC_jsr, Opcodes.OPC_ret, Opcodes.OPC_tableswitch, Opcodes.OPC_lookupswitch, Opcodes.OPC_ireturn, Opcodes.OPC_lreturn, Opcodes.OPC_freturn, Opcodes.OPC_dreturn, -80, Opcodes.OPC_return, Opcodes.OPC_getstatic, Opcodes.OPC_putstatic, Opcodes.OPC_getfield, Opcodes.OPC_putfield, Opcodes.OPC_invokevirtual, Opcodes.OPC_invokespecial, Opcodes.OPC_invokestatic, Opcodes.OPC_invokeinterface, Opcodes.OPC_invokedynamic, -69, -68, Opcodes.OPC_anewarray, Opcodes.OPC_arraylength, -65, -64, Opcodes.OPC_instanceof, Opcodes.OPC_monitorenter, Opcodes.OPC_monitorexit, Opcodes.OPC_wide, Opcodes.OPC_multianewarray, Opcodes.OPC_ifnull, Opcodes.OPC_ifnonnull, Opcodes.OPC_goto_w, Opcodes.OPC_jsr_w, -54, -53, -52, -51, WasmBinary.REC_GROUP, WasmBinary.SUB_FINAL_TYPE, -48, -47, -46, -45, -44, -43, -42, -41, -40, -39, -38, -37, -36, -35, WasmBinary.ARRAY_TYPE, WasmBinary.STRUCT_TYPE, -32, -31, -30, -29, -28, -27, -26, -25, -24, -23, -22, -21, -20, -19, -18, ByteSourceJsonBootstrapper.UTF8_BOM_1, -16, MidiConstants.MIDI_TIME_CODE, MidiConstants.SONG_POSITION_POBYTEER, MidiConstants.SONG_SELECT, -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1};

    Bzip2MoveToFrontTable() {
    }

    int valueToFront(byte b) {
        byte[] bArr = this.mtf;
        int i = 0;
        byte b2 = bArr[0];
        if (b != b2) {
            bArr[0] = b;
            while (b != b2) {
                i++;
                byte[] bArr2 = this.mtf;
                byte b3 = bArr2[i];
                bArr2[i] = b2;
                b2 = b3;
            }
        }
        return i;
    }

    byte indexToFront(int i) {
        byte[] bArr = this.mtf;
        byte b = bArr[i];
        System.arraycopy(bArr, 0, bArr, 1, i);
        this.mtf[0] = b;
        return b;
    }
}
