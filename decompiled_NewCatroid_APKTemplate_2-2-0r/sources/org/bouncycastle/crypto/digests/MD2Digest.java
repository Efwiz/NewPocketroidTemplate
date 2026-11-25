package org.bouncycastle.crypto.digests;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import org.billthefarmer.mididriver.MidiConstants;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.util.Memoable;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes13.dex */
public class MD2Digest implements ExtendedDigest, Memoable {
    private static final int DIGEST_LENGTH = 16;
    private static final byte[] S = {41, 46, 67, Opcodes.OPC_jsr_w, Opcodes.OPC_if_icmpge, -40, 124, 1, 61, 54, 84, Opcodes.OPC_if_icmplt, -20, -16, 6, 19, 98, Opcodes.OPC_goto, 5, MidiConstants.SONG_SELECT, -64, Opcodes.OPC_ifnonnull, 115, Opcodes.OPC_f2l, Opcodes.OPC_dcmpg, Opcodes.OPC_i2s, 43, -39, -68, 76, Opcodes.OPC_ixor, -54, 30, Opcodes.OPC_iflt, 87, 60, -3, -44, -32, 22, 103, 66, 111, 24, Opcodes.OPC_l2d, 23, -27, 18, Opcodes.OPC_arraylength, 78, Opcodes.OPC_wide, -42, -38, Opcodes.OPC_ifle, WasmBinary.ARRAY_TYPE, 73, -96, -5, -11, Opcodes.OPC_d2i, -69, 47, -18, 122, Opcodes.OPC_ret, 104, 121, Opcodes.OPC_i2b, 21, Opcodes.OPC_getstatic, 7, 63, Opcodes.OPC_lcmp, Opcodes.OPC_monitorenter, 16, Opcodes.OPC_l2f, 11, 34, 95, 33, -128, 127, 93, Opcodes.OPC_ifne, 90, -112, 50, 39, 53, 62, -52, -25, -65, -9, Opcodes.OPC_dcmpl, 3, -1, 25, 48, Opcodes.OPC_putstatic, 72, Opcodes.OPC_if_acmpeq, Opcodes.OPC_putfield, -47, -41, 94, Opcodes.OPC_i2c, 42, Opcodes.OPC_ireturn, 86, Opcodes.OPC_tableswitch, Opcodes.OPC_ifnull, 79, Opcodes.OPC_invokestatic, 56, -46, Opcodes.OPC_fcmpg, Opcodes.OPC_if_icmple, 125, Opcodes.OPC_invokevirtual, 118, -4, 107, -30, Opcodes.OPC_ifge, 116, 4, MidiConstants.MIDI_TIME_CODE, 69, Opcodes.OPC_ifgt, 112, 89, 100, 113, Opcodes.OPC_i2d, 32, Opcodes.OPC_i2f, 91, WasmBinary.SUB_FINAL_TYPE, 101, -26, 45, Opcodes.OPC_jsr, 2, 27, 96, 37, Opcodes.OPC_lreturn, Opcodes.OPC_freturn, -80, Opcodes.OPC_invokeinterface, -10, 28, 70, 97, 105, 52, 64, 126, 15, 85, 71, Opcodes.OPC_if_icmpgt, 35, -35, 81, Opcodes.OPC_dreturn, 58, Opcodes.OPC_monitorexit, 92, -7, WasmBinary.REC_GROUP, Opcodes.OPC_invokedynamic, Opcodes.OPC_multianewarray, -22, 38, 44, 83, 13, 110, Opcodes.OPC_i2l, 40, Opcodes.OPC_iinc, 9, -45, WasmBinary.STRUCT_TYPE, -51, -12, 65, Opcodes.OPC_lor, 77, 82, 106, -36, 55, Opcodes.OPC_goto_w, 108, Opcodes.OPC_instanceof, Opcodes.OPC_lookupswitch, -6, 36, -31, 123, 8, 12, Opcodes.OPC_anewarray, Opcodes.OPC_return, 74, 120, Opcodes.OPC_l2i, Opcodes.OPC_fcmpl, Opcodes.OPC_f2i, -29, 99, -24, 109, -23, -53, -43, -2, 59, 0, 29, 57, MidiConstants.SONG_POSITION_POBYTEER, ByteSourceJsonBootstrapper.UTF8_BOM_1, Opcodes.OPC_invokespecial, 14, 102, 88, -48, -28, Opcodes.OPC_if_acmpne, 119, 114, -8, -21, 117, 75, 10, 49, 68, 80, Opcodes.OPC_getfield, Opcodes.OPC_d2l, -19, 31, 26, -37, Opcodes.OPC_ifeq, Opcodes.OPC_f2d, 51, Opcodes.OPC_if_icmpeq, 17, Opcodes.OPC_lxor, 20};
    private byte[] C;
    private int COff;
    private byte[] M;
    private byte[] X;
    private int mOff;
    private int xOff;

    public MD2Digest() {
        this.X = new byte[48];
        this.M = new byte[16];
        this.C = new byte[16];
        reset();
    }

    public MD2Digest(MD2Digest mD2Digest) {
        this.X = new byte[48];
        this.M = new byte[16];
        this.C = new byte[16];
        copyIn(mD2Digest);
    }

    private void copyIn(MD2Digest mD2Digest) {
        byte[] bArr = mD2Digest.X;
        System.arraycopy(bArr, 0, this.X, 0, bArr.length);
        this.xOff = mD2Digest.xOff;
        byte[] bArr2 = mD2Digest.M;
        System.arraycopy(bArr2, 0, this.M, 0, bArr2.length);
        this.mOff = mD2Digest.mOff;
        byte[] bArr3 = mD2Digest.C;
        System.arraycopy(bArr3, 0, this.C, 0, bArr3.length);
        this.COff = mD2Digest.COff;
    }

    @Override // org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new MD2Digest(this);
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i) {
        int length = this.M.length;
        int i2 = this.mOff;
        byte b = (byte) (length - i2);
        while (true) {
            byte[] bArr2 = this.M;
            if (i2 >= bArr2.length) {
                processCheckSum(bArr2);
                processBlock(this.M);
                processBlock(this.C);
                System.arraycopy(this.X, this.xOff, bArr, i, 16);
                reset();
                return 16;
            }
            bArr2[i2] = b;
            i2++;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "MD2";
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 16;
    }

    protected void processBlock(byte[] bArr) {
        for (int i = 0; i < 16; i++) {
            byte[] bArr2 = this.X;
            bArr2[i + 16] = bArr[i];
            bArr2[i + 32] = (byte) (bArr[i] ^ bArr2[i]);
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 18; i3++) {
            for (int i4 = 0; i4 < 48; i4++) {
                byte[] bArr3 = this.X;
                byte b = (byte) (S[i2] ^ bArr3[i4]);
                bArr3[i4] = b;
                i2 = b & 255;
            }
            i2 = (i2 + i3) % 256;
        }
    }

    protected void processCheckSum(byte[] bArr) {
        byte b = this.C[15];
        for (int i = 0; i < 16; i++) {
            byte[] bArr2 = this.C;
            b = (byte) (S[(b ^ bArr[i]) & 255] ^ bArr2[i]);
            bArr2[i] = b;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.xOff = 0;
        int i = 0;
        while (true) {
            byte[] bArr = this.X;
            if (i == bArr.length) {
                break;
            }
            bArr[i] = 0;
            i++;
        }
        this.mOff = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr2 = this.M;
            if (i2 == bArr2.length) {
                break;
            }
            bArr2[i2] = 0;
            i2++;
        }
        this.COff = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr3 = this.C;
            if (i3 == bArr3.length) {
                return;
            }
            bArr3[i3] = 0;
            i3++;
        }
    }

    @Override // org.bouncycastle.util.Memoable
    public void reset(Memoable memoable) {
        copyIn((MD2Digest) memoable);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b) {
        byte[] bArr = this.M;
        int i = this.mOff;
        int i2 = i + 1;
        this.mOff = i2;
        bArr[i] = b;
        if (i2 == 16) {
            processCheckSum(bArr);
            processBlock(this.M);
            this.mOff = 0;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i, int i2) {
        while (this.mOff != 0 && i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
        while (i2 > 16) {
            System.arraycopy(bArr, i, this.M, 0, 16);
            processCheckSum(this.M);
            processBlock(this.M);
            i2 -= 16;
            i += 16;
        }
        while (i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
    }
}
