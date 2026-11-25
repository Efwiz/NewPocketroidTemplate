package org.bouncycastle.crypto.prng;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import org.billthefarmer.mididriver.MidiConstants;
import org.bouncycastle.util.Pack;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes13.dex */
public class VMPCRandomGenerator implements RandomGenerator {
    private byte n = 0;
    private byte[] P = {-69, 44, 98, 127, Opcodes.OPC_putfield, Opcodes.OPC_tableswitch, -44, 13, Opcodes.OPC_lor, -2, Opcodes.OPC_getstatic, Opcodes.OPC_ixor, -53, -96, Opcodes.OPC_if_icmplt, 8, 24, 113, 86, -24, 73, 2, 16, Opcodes.OPC_wide, WasmBinary.ARRAY_TYPE, 53, Opcodes.OPC_if_acmpeq, -20, -128, 18, Opcodes.OPC_invokestatic, 105, -38, 47, 117, -52, Opcodes.OPC_if_icmpge, 9, 54, 3, 97, 45, -3, -32, -35, 5, 67, -112, Opcodes.OPC_lreturn, Opcodes.OPC_goto_w, -31, Opcodes.OPC_dreturn, 87, Opcodes.OPC_iflt, 76, -40, 81, Opcodes.OPC_freturn, 80, Opcodes.OPC_i2l, 60, 10, -28, MidiConstants.SONG_SELECT, Opcodes.OPC_ifge, 38, 35, 83, Opcodes.OPC_jsr_w, Opcodes.OPC_lxor, Opcodes.OPC_dcmpl, 70, Opcodes.OPC_return, Opcodes.OPC_ifeq, 100, 49, 119, -43, 29, -42, 120, Opcodes.OPC_anewarray, 94, -80, Opcodes.OPC_l2d, 34, 56, -8, 104, 43, 42, Opcodes.OPC_multianewarray, -45, -9, -68, 111, WasmBinary.STRUCT_TYPE, 4, -27, Opcodes.OPC_fcmpl, 62, 37, Opcodes.OPC_i2f, Opcodes.OPC_if_acmpne, 11, Opcodes.OPC_d2l, MidiConstants.MIDI_TIME_CODE, 36, 14, -41, 64, Opcodes.OPC_putstatic, WasmBinary.SUB_FINAL_TYPE, 126, 6, 21, Opcodes.OPC_ifne, 77, 28, Opcodes.OPC_if_icmpgt, -37, 50, Opcodes.OPC_i2c, 88, 17, 39, -12, 89, -48, 78, 106, 23, 91, Opcodes.OPC_ireturn, -1, 7, -64, 101, 121, -4, Opcodes.OPC_ifnonnull, -51, 118, 66, 93, -25, 58, 52, 122, 48, 40, 15, 115, 1, -7, -47, -46, 25, -23, Opcodes.OPC_i2b, Opcodes.OPC_invokeinterface, 90, -19, 65, 109, Opcodes.OPC_getfield, Opcodes.OPC_monitorexit, Opcodes.OPC_ifle, -65, 99, -6, 31, 51, 96, 71, Opcodes.OPC_l2f, -16, Opcodes.OPC_fcmpg, 26, 95, Opcodes.OPC_i2s, 61, 55, 75, -39, Opcodes.OPC_jsr, Opcodes.OPC_instanceof, 27, -10, 57, Opcodes.OPC_f2i, Opcodes.OPC_invokespecial, 12, 32, WasmBinary.REC_GROUP, Opcodes.OPC_l2i, 110, Opcodes.OPC_invokevirtual, 116, Opcodes.OPC_d2i, Opcodes.OPC_f2d, 22, 41, MidiConstants.SONG_POSITION_POBYTEER, Opcodes.OPC_i2d, -11, -21, 112, -29, -5, 85, Opcodes.OPC_if_icmpeq, Opcodes.OPC_ifnull, 68, 74, 69, 125, -30, 107, 92, 108, 102, Opcodes.OPC_ret, Opcodes.OPC_f2l, -18, Opcodes.OPC_iinc, 19, Opcodes.OPC_goto, 30, Opcodes.OPC_ifgt, -36, 103, 72, Opcodes.OPC_invokedynamic, 46, -26, Opcodes.OPC_if_icmple, Opcodes.OPC_lookupswitch, 124, Opcodes.OPC_lcmp, 0, 33, ByteSourceJsonBootstrapper.UTF8_BOM_1, -22, Opcodes.OPC_arraylength, -54, 114, 79, 82, Opcodes.OPC_dcmpg, 63, Opcodes.OPC_monitorenter, 20, 123, 59, 84};
    private byte s = Opcodes.OPC_arraylength;

    @Override // org.bouncycastle.crypto.prng.RandomGenerator
    public void addSeedMaterial(long j) {
        addSeedMaterial(Pack.longToBigEndian(j));
    }

    @Override // org.bouncycastle.crypto.prng.RandomGenerator
    public void addSeedMaterial(byte[] bArr) {
        for (byte b : bArr) {
            byte[] bArr2 = this.P;
            byte b2 = this.s;
            byte b3 = this.n;
            byte b4 = bArr2[(b2 + bArr2[b3 & 255] + b) & 255];
            this.s = b4;
            byte b5 = bArr2[b3 & 255];
            bArr2[b3 & 255] = bArr2[b4 & 255];
            bArr2[b4 & 255] = b5;
            this.n = (byte) ((b3 + 1) & 255);
        }
    }

    @Override // org.bouncycastle.crypto.prng.RandomGenerator
    public void nextBytes(byte[] bArr) {
        nextBytes(bArr, 0, bArr.length);
    }

    @Override // org.bouncycastle.crypto.prng.RandomGenerator
    public void nextBytes(byte[] bArr, int i, int i2) {
        synchronized (this.P) {
            int i3 = i2 + i;
            while (i != i3) {
                byte[] bArr2 = this.P;
                byte b = this.s;
                byte b2 = this.n;
                byte b3 = bArr2[(b + bArr2[b2 & 255]) & 255];
                this.s = b3;
                bArr[i] = bArr2[(bArr2[bArr2[b3 & 255] & 255] + 1) & 255];
                byte b4 = bArr2[b2 & 255];
                bArr2[b2 & 255] = bArr2[b3 & 255];
                bArr2[b3 & 255] = b4;
                this.n = (byte) ((b2 + 1) & 255);
                i++;
            }
        }
    }
}
