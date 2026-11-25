package org.bouncycastle.crypto.engines;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import org.billthefarmer.mididriver.MidiConstants;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes13.dex */
public class GOST3412_2015Engine implements BlockCipher {
    protected static final int BLOCK_SIZE = 16;
    private static final byte[] PI = {-4, -18, -35, 17, WasmBinary.SUB_FINAL_TYPE, 110, 49, 22, -5, Opcodes.OPC_wide, -6, -38, 35, Opcodes.OPC_multianewarray, 4, 77, -23, 119, -16, -37, Opcodes.OPC_i2s, 46, Opcodes.OPC_ifeq, Opcodes.OPC_invokedynamic, 23, 54, MidiConstants.MIDI_TIME_CODE, -69, 20, -51, 95, Opcodes.OPC_instanceof, -7, 24, 101, 90, -30, 92, ByteSourceJsonBootstrapper.UTF8_BOM_1, 33, Opcodes.OPC_lor, 28, 60, 66, Opcodes.OPC_f2i, 1, Opcodes.OPC_d2i, 79, 5, Opcodes.OPC_iinc, 2, Opcodes.OPC_freturn, -29, 106, Opcodes.OPC_d2l, -96, 6, 11, -19, Opcodes.OPC_dcmpg, 127, -44, -45, 31, -21, 52, 44, 81, -22, Opcodes.OPC_goto_w, 72, Opcodes.OPC_lookupswitch, MidiConstants.SONG_POSITION_POBYTEER, 42, 104, Opcodes.OPC_if_icmpge, -3, 58, WasmBinary.REC_GROUP, -52, Opcodes.OPC_putfield, 112, 14, 86, 8, 12, 118, 18, -65, 114, 19, 71, Opcodes.OPC_ifge, Opcodes.OPC_invokespecial, 93, Opcodes.OPC_i2d, 21, Opcodes.OPC_if_icmplt, Opcodes.OPC_fcmpg, 41, 16, 123, Opcodes.OPC_ifne, Opcodes.OPC_ifnonnull, MidiConstants.SONG_SELECT, Opcodes.OPC_i2b, 120, 111, Opcodes.OPC_ifgt, Opcodes.OPC_ifle, Opcodes.OPC_getstatic, Opcodes.OPC_return, 50, 117, 25, 61, -1, 53, Opcodes.OPC_l2d, 126, 109, 84, Opcodes.OPC_ifnull, -128, Opcodes.OPC_monitorexit, Opcodes.OPC_anewarray, 13, 87, WasmBinary.STRUCT_TYPE, -11, 36, Opcodes.OPC_ret, 62, Opcodes.OPC_jsr, 67, Opcodes.OPC_jsr_w, -41, 121, -42, -10, 124, 34, Opcodes.OPC_invokeinterface, 3, -32, 15, -20, WasmBinary.ARRAY_TYPE, 122, Opcodes.OPC_lcmp, -80, -68, -36, -24, 40, 80, 78, 51, 10, 74, Opcodes.OPC_goto, Opcodes.OPC_dcmpl, 96, 115, 30, 0, 98, 68, 26, Opcodes.OPC_invokestatic, 56, Opcodes.OPC_ixor, 100, Opcodes.OPC_if_icmpeq, 38, 65, Opcodes.OPC_lreturn, 69, 70, Opcodes.OPC_i2c, 39, 94, 85, 47, Opcodes.OPC_f2l, Opcodes.OPC_if_icmpgt, Opcodes.OPC_if_acmpeq, 125, 105, -43, Opcodes.OPC_fcmpl, 59, 7, 88, Opcodes.OPC_putstatic, 64, Opcodes.OPC_i2f, Opcodes.OPC_ireturn, 29, -9, 48, 55, 107, -28, Opcodes.OPC_l2i, -39, -25, Opcodes.OPC_l2f, -31, 27, Opcodes.OPC_lxor, 73, 76, 63, -8, -2, Opcodes.OPC_f2d, 83, Opcodes.OPC_tableswitch, -112, -54, -40, Opcodes.OPC_i2l, 97, 32, 113, 103, Opcodes.OPC_if_icmple, 45, 43, 9, 91, -53, Opcodes.OPC_iflt, 37, -48, Opcodes.OPC_arraylength, -27, 108, 82, 89, Opcodes.OPC_if_acmpne, 116, -46, -26, -12, Opcodes.OPC_getfield, -64, -47, 102, Opcodes.OPC_dreturn, Opcodes.OPC_monitorenter, 57, 75, 99, Opcodes.OPC_invokevirtual};
    private static final byte[] inversePI = {Opcodes.OPC_if_acmpeq, 45, 50, Opcodes.OPC_d2l, 14, 48, 56, -64, 84, -26, Opcodes.OPC_ifle, 57, 85, 126, 82, Opcodes.OPC_i2b, 100, 3, 87, 90, 28, 96, 7, 24, 33, 114, Opcodes.OPC_jsr, -47, 41, Opcodes.OPC_ifnull, Opcodes.OPC_if_icmple, 63, -32, 39, Opcodes.OPC_f2d, 12, Opcodes.OPC_ixor, -22, Opcodes.OPC_freturn, Opcodes.OPC_getfield, Opcodes.OPC_ifne, 99, 73, -27, 66, -28, 21, Opcodes.OPC_invokespecial, Opcodes.OPC_goto_w, 6, 112, Opcodes.OPC_ifgt, 65, 117, 25, Opcodes.OPC_jsr_w, Opcodes.OPC_tableswitch, -4, 77, -65, 42, 115, Opcodes.OPC_iinc, -43, Opcodes.OPC_monitorexit, Opcodes.OPC_dreturn, 43, Opcodes.OPC_i2f, Opcodes.OPC_goto, Opcodes.OPC_return, Opcodes.OPC_getstatic, 91, 70, -45, Opcodes.OPC_if_icmpeq, -3, -44, 15, Opcodes.OPC_ifge, 47, Opcodes.OPC_iflt, 67, ByteSourceJsonBootstrapper.UTF8_BOM_1, -39, 121, Opcodes.OPC_invokevirtual, 83, 127, Opcodes.OPC_instanceof, -16, 35, -25, 37, 94, Opcodes.OPC_putfield, 30, Opcodes.OPC_if_icmpge, WasmBinary.STRUCT_TYPE, Opcodes.OPC_if_acmpne, -2, Opcodes.OPC_ireturn, 34, -7, -30, 74, -68, 53, -54, -18, 120, 5, 107, 81, -31, 89, Opcodes.OPC_if_icmpgt, MidiConstants.SONG_POSITION_POBYTEER, 113, 86, 17, 106, Opcodes.OPC_l2f, Opcodes.OPC_lcmp, 101, Opcodes.OPC_f2l, -69, 119, 60, 123, 40, Opcodes.OPC_lookupswitch, -46, 49, WasmBinary.ARRAY_TYPE, Opcodes.OPC_wide, 95, -52, WasmBinary.SUB_FINAL_TYPE, 118, 44, Opcodes.OPC_invokestatic, -40, 46, 54, -37, 105, Opcodes.OPC_putstatic, 20, Opcodes.OPC_fcmpl, Opcodes.OPC_arraylength, 98, Opcodes.OPC_if_icmplt, 59, 22, 102, -23, 92, 108, 109, Opcodes.OPC_lreturn, 55, 97, 75, Opcodes.OPC_invokeinterface, -29, Opcodes.OPC_invokedynamic, MidiConstants.MIDI_TIME_CODE, -96, Opcodes.OPC_i2l, Opcodes.OPC_lxor, -38, 71, Opcodes.OPC_multianewarray, -80, 51, -6, Opcodes.OPC_fcmpg, 111, 110, Opcodes.OPC_monitorenter, -10, 80, -1, 93, Opcodes.OPC_ret, Opcodes.OPC_d2i, 23, 27, Opcodes.OPC_dcmpl, 125, -20, 88, -9, 31, -5, 124, 9, 13, 122, 103, 69, Opcodes.OPC_i2d, -36, -24, 79, 29, 78, 4, -21, -8, MidiConstants.SONG_SELECT, 62, 61, Opcodes.OPC_anewarray, Opcodes.OPC_l2d, Opcodes.OPC_l2i, -35, -51, 11, 19, Opcodes.OPC_dcmpg, 2, Opcodes.OPC_i2s, -128, -112, -48, 36, 52, -53, -19, -12, WasmBinary.REC_GROUP, Opcodes.OPC_ifeq, 16, 68, 64, Opcodes.OPC_i2c, 58, 1, 38, 18, 26, 72, 104, -11, Opcodes.OPC_lor, Opcodes.OPC_f2i, Opcodes.OPC_ifnonnull, -42, 32, 10, 8, 0, 76, -41, 116};
    private boolean forEncryption;
    private final byte[] lFactors = {Opcodes.OPC_lcmp, 32, Opcodes.OPC_i2l, 16, Opcodes.OPC_monitorenter, -64, 1, -5, 1, -64, Opcodes.OPC_monitorenter, 16, Opcodes.OPC_i2l, 32, Opcodes.OPC_lcmp, 1};
    private int KEY_LENGTH = 32;
    private int SUB_LENGTH = 32 / 2;
    private byte[][] subKeys = null;
    private byte[][] _gf_mul = init_gf256_mul_table();

    public GOST3412_2015Engine() {
    }

    private void C(byte[] bArr, int i) {
        Arrays.clear(bArr);
        bArr[15] = (byte) i;
        L(bArr);
    }

    private void F(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArrLSX = LSX(bArr, bArr2);
        X(bArrLSX, bArr3);
        System.arraycopy(bArr2, 0, bArr3, 0, this.SUB_LENGTH);
        System.arraycopy(bArrLSX, 0, bArr2, 0, this.SUB_LENGTH);
    }

    private void GOST3412_2015Func(byte[] bArr, int i, byte[] bArr2, int i2) {
        byte[][] bArr3;
        byte[] bArrCopyOf = new byte[16];
        System.arraycopy(bArr, i, bArrCopyOf, 0, 16);
        int i3 = 9;
        if (this.forEncryption) {
            for (int i4 = 0; i4 < 9; i4++) {
                bArrCopyOf = Arrays.copyOf(LSX(this.subKeys[i4], bArrCopyOf), 16);
            }
            X(bArrCopyOf, this.subKeys[9]);
        } else {
            while (true) {
                bArr3 = this.subKeys;
                if (i3 <= 0) {
                    break;
                }
                bArrCopyOf = Arrays.copyOf(XSL(bArr3[i3], bArrCopyOf), 16);
                i3--;
            }
            X(bArrCopyOf, bArr3[0]);
        }
        System.arraycopy(bArrCopyOf, 0, bArr2, i2, 16);
    }

    private void L(byte[] bArr) {
        for (int i = 0; i < 16; i++) {
            R(bArr);
        }
    }

    private byte[] LSX(byte[] bArr, byte[] bArr2) {
        byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
        X(bArrCopyOf, bArr2);
        S(bArrCopyOf);
        L(bArrCopyOf);
        return bArrCopyOf;
    }

    private void R(byte[] bArr) {
        byte bL = l(bArr);
        System.arraycopy(bArr, 0, bArr, 1, 15);
        bArr[0] = bL;
    }

    private void S(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = PI[unsignedByte(bArr[i])];
        }
    }

    private void X(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) (bArr[i] ^ bArr2[i]);
        }
    }

    private byte[] XSL(byte[] bArr, byte[] bArr2) {
        byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
        X(bArrCopyOf, bArr2);
        inverseL(bArrCopyOf);
        inverseS(bArrCopyOf);
        return bArrCopyOf;
    }

    private void generateSubKeys(byte[] bArr) {
        int i;
        if (bArr.length != this.KEY_LENGTH) {
            throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
        }
        this.subKeys = new byte[10][];
        for (int i2 = 0; i2 < 10; i2++) {
            this.subKeys[i2] = new byte[this.SUB_LENGTH];
        }
        int i3 = this.SUB_LENGTH;
        byte[] bArr2 = new byte[i3];
        byte[] bArr3 = new byte[i3];
        int i4 = 0;
        while (true) {
            i = this.SUB_LENGTH;
            if (i4 >= i) {
                break;
            }
            byte[][] bArr4 = this.subKeys;
            byte[] bArr5 = bArr4[0];
            byte b = bArr[i4];
            bArr2[i4] = b;
            bArr5[i4] = b;
            byte[] bArr6 = bArr4[1];
            byte b2 = bArr[i + i4];
            bArr3[i4] = b2;
            bArr6[i4] = b2;
            i4++;
        }
        byte[] bArr7 = new byte[i];
        for (int i5 = 1; i5 < 5; i5++) {
            for (int i6 = 1; i6 <= 8; i6++) {
                C(bArr7, ((i5 - 1) * 8) + i6);
                F(bArr7, bArr2, bArr3);
            }
            int i7 = i5 * 2;
            System.arraycopy(bArr2, 0, this.subKeys[i7], 0, this.SUB_LENGTH);
            System.arraycopy(bArr3, 0, this.subKeys[i7 + 1], 0, this.SUB_LENGTH);
        }
    }

    private static byte[][] init_gf256_mul_table() {
        byte[][] bArr = new byte[256][];
        for (int i = 0; i < 256; i++) {
            bArr[i] = new byte[256];
            for (int i2 = 0; i2 < 256; i2++) {
                bArr[i][i2] = kuz_mul_gf256_slow((byte) i, (byte) i2);
            }
        }
        return bArr;
    }

    private void inverseL(byte[] bArr) {
        for (int i = 0; i < 16; i++) {
            inverseR(bArr);
        }
    }

    private void inverseR(byte[] bArr) {
        byte[] bArr2 = new byte[16];
        System.arraycopy(bArr, 1, bArr2, 0, 15);
        bArr2[15] = bArr[0];
        byte bL = l(bArr2);
        System.arraycopy(bArr, 1, bArr, 0, 15);
        bArr[15] = bL;
    }

    private void inverseS(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = inversePI[unsignedByte(bArr[i])];
        }
    }

    private static byte kuz_mul_gf256_slow(byte b, byte b2) {
        byte b3 = 0;
        for (byte b4 = 0; b4 < 8 && b != 0 && b2 != 0; b4 = (byte) (b4 + 1)) {
            if ((b2 & 1) != 0) {
                b3 = (byte) (b3 ^ b);
            }
            byte b5 = (byte) (b & 128);
            b = (byte) (b << 1);
            if (b5 != 0) {
                b = (byte) (b ^ Opcodes.OPC_monitorexit);
            }
            b2 = (byte) (b2 >> 1);
        }
        return b3;
    }

    private byte l(byte[] bArr) {
        byte b = bArr[15];
        for (int i = 14; i >= 0; i--) {
            b = (byte) (b ^ this._gf_mul[unsignedByte(bArr[i])][unsignedByte(this.lFactors[i])]);
        }
        return b;
    }

    private int unsignedByte(byte b) {
        return b & 255;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "GOST3412_2015";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        if (cipherParameters instanceof KeyParameter) {
            this.forEncryption = z;
            generateSubKeys(((KeyParameter) cipherParameters).getKey());
        } else if (cipherParameters != null) {
            throw new IllegalArgumentException("invalid parameter passed to GOST3412_2015 init - " + cipherParameters.getClass().getName());
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws IllegalStateException, DataLengthException {
        if (this.subKeys == null) {
            throw new IllegalStateException("GOST3412_2015 engine not initialised");
        }
        if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i2 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        GOST3412_2015Func(bArr, i, bArr2, i2);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
