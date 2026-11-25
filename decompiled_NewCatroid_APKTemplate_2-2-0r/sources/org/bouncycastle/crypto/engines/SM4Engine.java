package org.bouncycastle.crypto.engines;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
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
public class SM4Engine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;
    private final int[] X = new int[4];
    private int[] rk;
    private static final byte[] Sbox = {-42, -112, -23, -2, -52, -31, 61, Opcodes.OPC_invokespecial, 22, Opcodes.OPC_invokevirtual, 20, Opcodes.OPC_monitorenter, 40, -5, 44, 5, 43, 103, Opcodes.OPC_ifne, 118, 42, Opcodes.OPC_arraylength, 4, Opcodes.OPC_monitorexit, Opcodes.OPC_tableswitch, 68, 19, 38, 73, Opcodes.OPC_i2f, 6, Opcodes.OPC_ifeq, Opcodes.OPC_ifge, 66, 80, -12, Opcodes.OPC_i2b, ByteSourceJsonBootstrapper.UTF8_BOM_1, Opcodes.OPC_dcmpg, 122, 51, 84, 11, 67, -19, WasmBinary.SUB_FINAL_TYPE, Opcodes.OPC_ireturn, 98, -28, Opcodes.OPC_putstatic, 28, Opcodes.OPC_ret, Opcodes.OPC_jsr_w, 8, -24, Opcodes.OPC_fcmpl, -128, WasmBinary.STRUCT_TYPE, Opcodes.OPC_lcmp, -6, 117, Opcodes.OPC_d2l, 63, Opcodes.OPC_if_acmpne, 71, 7, Opcodes.OPC_goto, -4, MidiConstants.SONG_SELECT, 115, 23, Opcodes.OPC_invokedynamic, Opcodes.OPC_lxor, 89, 60, 25, -26, Opcodes.OPC_i2l, 79, Opcodes.OPC_jsr, 104, 107, Opcodes.OPC_lor, Opcodes.OPC_getstatic, 113, 100, -38, Opcodes.OPC_f2i, -8, -21, 15, 75, 112, 86, Opcodes.OPC_ifgt, 53, 30, 36, 14, 94, 99, 88, -47, Opcodes.OPC_if_icmpge, 37, 34, 124, 59, 1, 33, 120, Opcodes.OPC_i2d, -44, 0, 70, 87, Opcodes.OPC_if_icmpeq, -45, 39, 82, 76, 54, 2, -25, -96, Opcodes.OPC_wide, Opcodes.OPC_goto_w, Opcodes.OPC_ifle, -22, -65, Opcodes.OPC_l2d, -46, 64, Opcodes.OPC_ifnonnull, 56, Opcodes.OPC_putfield, Opcodes.OPC_if_icmpgt, -9, MidiConstants.SONG_POSITION_POBYTEER, WasmBinary.REC_GROUP, -7, 97, 21, Opcodes.OPC_if_icmplt, -32, Opcodes.OPC_freturn, 93, Opcodes.OPC_if_icmple, Opcodes.OPC_iflt, 52, 26, 85, Opcodes.OPC_lreturn, Opcodes.OPC_i2s, 50, 48, -11, Opcodes.OPC_f2l, Opcodes.OPC_return, -29, 29, -10, -30, 46, Opcodes.OPC_ixor, 102, -54, 96, -64, 41, 35, Opcodes.OPC_lookupswitch, 13, 83, 78, 111, -43, -37, 55, 69, WasmBinary.ARRAY_TYPE, -3, Opcodes.OPC_d2i, 47, 3, -1, 106, 114, 109, 108, 91, 81, Opcodes.OPC_f2d, 27, Opcodes.OPC_dreturn, Opcodes.OPC_i2c, -69, -35, -68, 127, 17, -39, 92, 65, 31, 16, 90, -40, 10, Opcodes.OPC_instanceof, 49, Opcodes.OPC_l2i, Opcodes.OPC_if_acmpeq, -51, 123, Opcodes.OPC_anewarray, 45, 116, -48, 18, Opcodes.OPC_invokestatic, -27, Opcodes.OPC_getfield, -80, Opcodes.OPC_l2f, 105, Opcodes.OPC_dcmpl, 74, 12, Opcodes.OPC_fcmpg, 119, 126, 101, Opcodes.OPC_invokeinterface, MidiConstants.MIDI_TIME_CODE, 9, Opcodes.OPC_multianewarray, 110, Opcodes.OPC_ifnull, Opcodes.OPC_iinc, 24, -16, 125, -20, 58, -36, 77, 32, 121, -18, 95, 62, -41, -53, 57, 72};
    private static final int[] CK = {462357, 472066609, 943670861, 1415275113, 1886879365, -1936483679, -1464879427, -993275175, -521670923, -66909679, 404694573, 876298825, 1347903077, 1819507329, -2003855715, -1532251463, -1060647211, -589042959, -117504499, 337322537, 808926789, 1280531041, 1752135293, -2071227751, -1599623499, -1128019247, -656414995, -184876535, 269950501, 741554753, 1213159005, 1684763257};
    private static final int[] FK = {-1548633402, 1453994832, 1736282519, -1301273892};

    private int F0(int[] iArr, int i) {
        return T((iArr[3] ^ (iArr[1] ^ iArr[2])) ^ i) ^ iArr[0];
    }

    private int F1(int[] iArr, int i) {
        return T((iArr[0] ^ (iArr[2] ^ iArr[3])) ^ i) ^ iArr[1];
    }

    private int F2(int[] iArr, int i) {
        return T((iArr[1] ^ (iArr[3] ^ iArr[0])) ^ i) ^ iArr[2];
    }

    private int F3(int[] iArr, int i) {
        return T((iArr[2] ^ (iArr[0] ^ iArr[1])) ^ i) ^ iArr[3];
    }

    private int L(int i) {
        return rotateLeft(i, 24) ^ (((rotateLeft(i, 2) ^ i) ^ rotateLeft(i, 10)) ^ rotateLeft(i, 18));
    }

    private int L_ap(int i) {
        return rotateLeft(i, 23) ^ (rotateLeft(i, 13) ^ i);
    }

    private void R(int[] iArr, int i) {
        int i2 = i + 1;
        int i3 = i + 2;
        int i4 = i + 3;
        int i5 = iArr[i] ^ iArr[i4];
        iArr[i] = i5;
        int i6 = i5 ^ iArr[i4];
        iArr[i4] = i6;
        iArr[i] = iArr[i] ^ i6;
        int i7 = iArr[i2] ^ iArr[i3];
        iArr[i2] = i7;
        int i8 = i7 ^ iArr[i3];
        iArr[i3] = i8;
        iArr[i2] = i8 ^ iArr[i2];
    }

    private int T(int i) {
        return L(tau(i));
    }

    private int T_ap(int i) {
        return L_ap(tau(i));
    }

    private int[] expandKey(boolean z, byte[] bArr) {
        int[] iArr = new int[32];
        int[] iArr2 = {Pack.bigEndianToInt(bArr, 0), Pack.bigEndianToInt(bArr, 4), Pack.bigEndianToInt(bArr, 8), Pack.bigEndianToInt(bArr, 12)};
        int i = iArr2[0];
        int[] iArr3 = FK;
        int[] iArr4 = {i ^ iArr3[0], iArr2[1] ^ iArr3[1], iArr2[2] ^ iArr3[2], iArr2[3] ^ iArr3[3]};
        if (z) {
            int i2 = iArr4[0];
            int i3 = (iArr4[1] ^ iArr4[2]) ^ iArr4[3];
            int[] iArr5 = CK;
            int iT_ap = i2 ^ T_ap(i3 ^ iArr5[0]);
            iArr[0] = iT_ap;
            int iT_ap2 = T_ap((iT_ap ^ (iArr4[2] ^ iArr4[3])) ^ iArr5[1]) ^ iArr4[1];
            iArr[1] = iT_ap2;
            int iT_ap3 = T_ap((iT_ap2 ^ (iArr4[3] ^ iArr[0])) ^ iArr5[2]) ^ iArr4[2];
            iArr[2] = iT_ap3;
            iArr[3] = T_ap((iT_ap3 ^ (iArr[0] ^ iArr[1])) ^ iArr5[3]) ^ iArr4[3];
            for (int i4 = 4; i4 < 32; i4++) {
                iArr[i4] = iArr[i4 - 4] ^ T_ap(((iArr[i4 - 3] ^ iArr[i4 - 2]) ^ iArr[i4 - 1]) ^ CK[i4]);
            }
        } else {
            int i5 = iArr4[0];
            int i6 = (iArr4[1] ^ iArr4[2]) ^ iArr4[3];
            int[] iArr6 = CK;
            int iT_ap4 = i5 ^ T_ap(i6 ^ iArr6[0]);
            iArr[31] = iT_ap4;
            int iT_ap5 = T_ap((iT_ap4 ^ (iArr4[2] ^ iArr4[3])) ^ iArr6[1]) ^ iArr4[1];
            iArr[30] = iT_ap5;
            int iT_ap6 = T_ap((iT_ap5 ^ (iArr4[3] ^ iArr[31])) ^ iArr6[2]) ^ iArr4[2];
            iArr[29] = iT_ap6;
            iArr[28] = T_ap((iT_ap6 ^ (iArr[31] ^ iArr[30])) ^ iArr6[3]) ^ iArr4[3];
            for (int i7 = 27; i7 >= 0; i7--) {
                iArr[i7] = iArr[i7 + 4] ^ T_ap(((iArr[i7 + 3] ^ iArr[i7 + 2]) ^ iArr[i7 + 1]) ^ CK[31 - i7]);
            }
        }
        return iArr;
    }

    private int rotateLeft(int i, int i2) {
        return (i >>> (-i2)) | (i << i2);
    }

    private int tau(int i) {
        byte[] bArr = Sbox;
        return (bArr[i & 255] & 255) | ((bArr[(i >> 24) & 255] & 255) << 24) | ((bArr[(i >> 16) & 255] & 255) << 16) | ((bArr[(i >> 8) & 255] & 255) << 8);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "SM4";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("invalid parameter passed to SM4 init - " + cipherParameters.getClass().getName());
        }
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        if (key.length != 16) {
            throw new IllegalArgumentException("SM4 requires a 128 bit key");
        }
        this.rk = expandKey(z, key);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws IllegalStateException, DataLengthException {
        if (this.rk == null) {
            throw new IllegalStateException("SM4 not initialised");
        }
        if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i2 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        this.X[0] = Pack.bigEndianToInt(bArr, i);
        this.X[1] = Pack.bigEndianToInt(bArr, i + 4);
        this.X[2] = Pack.bigEndianToInt(bArr, i + 8);
        this.X[3] = Pack.bigEndianToInt(bArr, i + 12);
        for (int i3 = 0; i3 < 32; i3 += 4) {
            int[] iArr = this.X;
            iArr[0] = F0(iArr, this.rk[i3]);
            int[] iArr2 = this.X;
            iArr2[1] = F1(iArr2, this.rk[i3 + 1]);
            int[] iArr3 = this.X;
            iArr3[2] = F2(iArr3, this.rk[i3 + 2]);
            int[] iArr4 = this.X;
            iArr4[3] = F3(iArr4, this.rk[i3 + 3]);
        }
        R(this.X, 0);
        Pack.intToBigEndian(this.X[0], bArr2, i2);
        Pack.intToBigEndian(this.X[1], bArr2, i2 + 4);
        Pack.intToBigEndian(this.X[2], bArr2, i2 + 8);
        Pack.intToBigEndian(this.X[3], bArr2, i2 + 12);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
