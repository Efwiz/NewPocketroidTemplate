package org.bouncycastle.crypto.engines;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import org.billthefarmer.mididriver.MidiConstants;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.RC2Parameters;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;

/* JADX INFO: loaded from: classes13.dex */
public class RC2Engine implements BlockCipher {
    private static final int BLOCK_SIZE = 8;
    private static byte[] piTable = {-39, 120, -7, Opcodes.OPC_wide, 25, -35, Opcodes.OPC_putfield, -19, 40, -23, -3, 121, 74, -96, -40, Opcodes.OPC_ifgt, Opcodes.OPC_ifnull, 126, 55, Opcodes.OPC_lxor, 43, 118, 83, Opcodes.OPC_d2i, 98, 76, 100, Opcodes.OPC_l2i, 68, Opcodes.OPC_f2i, -5, Opcodes.OPC_if_icmpge, 23, Opcodes.OPC_ifne, 89, -11, Opcodes.OPC_i2d, Opcodes.OPC_putstatic, 79, 19, 97, 69, 109, Opcodes.OPC_f2d, 9, Opcodes.OPC_lor, 125, 50, Opcodes.OPC_anewarray, Opcodes.OPC_d2l, 64, -21, Opcodes.OPC_i2f, Opcodes.OPC_invokespecial, 123, 11, -16, Opcodes.OPC_fcmpl, 33, 34, 92, 107, 78, Opcodes.OPC_ixor, 84, -42, 101, Opcodes.OPC_i2s, WasmBinary.REC_GROUP, 96, Opcodes.OPC_getstatic, 28, 115, 86, -64, 20, Opcodes.OPC_goto, Opcodes.OPC_f2l, MidiConstants.MIDI_TIME_CODE, -36, 18, 117, -54, 31, 59, Opcodes.OPC_arraylength, -28, -47, 66, 61, -44, 48, Opcodes.OPC_if_icmpgt, 60, Opcodes.OPC_invokevirtual, 38, 111, -65, 14, -38, 70, 105, 7, 87, 39, MidiConstants.SONG_POSITION_POBYTEER, 29, Opcodes.OPC_iflt, -68, Opcodes.OPC_lcmp, 67, 3, -8, 17, Opcodes.OPC_ifnonnull, -10, -112, ByteSourceJsonBootstrapper.UTF8_BOM_1, 62, -25, 6, Opcodes.OPC_monitorexit, -43, 47, Opcodes.OPC_goto_w, 102, 30, -41, 8, -24, -22, WasmBinary.ARRAY_TYPE, -128, 82, -18, -9, Opcodes.OPC_iinc, Opcodes.OPC_tableswitch, 114, Opcodes.OPC_ireturn, 53, 77, 106, 42, Opcodes.OPC_fcmpg, 26, -46, 113, 90, 21, 73, 116, 75, Opcodes.OPC_if_icmpeq, -48, 94, 4, 24, Opcodes.OPC_if_icmple, -20, Opcodes.OPC_monitorenter, -32, 65, 110, 15, 81, -53, -52, 36, Opcodes.OPC_i2b, Opcodes.OPC_dreturn, 80, Opcodes.OPC_if_icmplt, -12, 112, 57, Opcodes.OPC_ifeq, 124, 58, Opcodes.OPC_i2l, 35, Opcodes.OPC_invokestatic, Opcodes.OPC_getfield, 122, -4, 2, 54, 91, 37, 85, Opcodes.OPC_dcmpl, 49, 45, 93, -6, Opcodes.OPC_dcmpg, -29, Opcodes.OPC_l2d, Opcodes.OPC_i2c, Opcodes.OPC_freturn, 5, WasmBinary.STRUCT_TYPE, 41, 16, 103, 108, Opcodes.OPC_invokedynamic, Opcodes.OPC_jsr_w, -45, 0, -26, WasmBinary.SUB_FINAL_TYPE, -31, Opcodes.OPC_ifle, Opcodes.OPC_jsr, 44, 99, 22, 1, 63, 88, -30, Opcodes.OPC_l2f, Opcodes.OPC_ret, 13, 56, 52, 27, Opcodes.OPC_lookupswitch, 51, -1, -80, -69, 72, 12, 95, Opcodes.OPC_invokeinterface, Opcodes.OPC_return, -51, 46, Opcodes.OPC_multianewarray, MidiConstants.SONG_SELECT, -37, 71, -27, Opcodes.OPC_if_acmpeq, Opcodes.OPC_ifge, 119, 10, Opcodes.OPC_if_acmpne, 32, 104, -2, 127, Opcodes.OPC_instanceof, Opcodes.OPC_lreturn};
    private boolean encrypting;
    private int[] workingKey;

    private void decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int iRotateWordLeft = ((bArr[i + 7] & 255) << 8) + (bArr[i + 6] & 255);
        int iRotateWordLeft2 = ((bArr[i + 5] & 255) << 8) + (bArr[i + 4] & 255);
        int iRotateWordLeft3 = ((bArr[i + 3] & 255) << 8) + (bArr[i + 2] & 255);
        int iRotateWordLeft4 = ((bArr[i + 1] & 255) << 8) + (bArr[i] & 255);
        for (int i3 = 60; i3 >= 44; i3 -= 4) {
            iRotateWordLeft = rotateWordLeft(iRotateWordLeft, 11) - ((((~iRotateWordLeft2) & iRotateWordLeft4) + (iRotateWordLeft3 & iRotateWordLeft2)) + this.workingKey[i3 + 3]);
            iRotateWordLeft2 = rotateWordLeft(iRotateWordLeft2, 13) - ((((~iRotateWordLeft3) & iRotateWordLeft) + (iRotateWordLeft4 & iRotateWordLeft3)) + this.workingKey[i3 + 2]);
            iRotateWordLeft3 = rotateWordLeft(iRotateWordLeft3, 14) - ((((~iRotateWordLeft4) & iRotateWordLeft2) + (iRotateWordLeft & iRotateWordLeft4)) + this.workingKey[i3 + 1]);
            iRotateWordLeft4 = rotateWordLeft(iRotateWordLeft4, 15) - ((((~iRotateWordLeft) & iRotateWordLeft3) + (iRotateWordLeft2 & iRotateWordLeft)) + this.workingKey[i3]);
        }
        int[] iArr = this.workingKey;
        int iRotateWordLeft5 = iRotateWordLeft - iArr[iRotateWordLeft2 & 63];
        int iRotateWordLeft6 = iRotateWordLeft2 - iArr[iRotateWordLeft3 & 63];
        int iRotateWordLeft7 = iRotateWordLeft3 - iArr[iRotateWordLeft4 & 63];
        int iRotateWordLeft8 = iRotateWordLeft4 - iArr[iRotateWordLeft5 & 63];
        for (int i4 = 40; i4 >= 20; i4 -= 4) {
            iRotateWordLeft5 = rotateWordLeft(iRotateWordLeft5, 11) - ((((~iRotateWordLeft6) & iRotateWordLeft8) + (iRotateWordLeft7 & iRotateWordLeft6)) + this.workingKey[i4 + 3]);
            iRotateWordLeft6 = rotateWordLeft(iRotateWordLeft6, 13) - ((((~iRotateWordLeft7) & iRotateWordLeft5) + (iRotateWordLeft8 & iRotateWordLeft7)) + this.workingKey[i4 + 2]);
            iRotateWordLeft7 = rotateWordLeft(iRotateWordLeft7, 14) - ((((~iRotateWordLeft8) & iRotateWordLeft6) + (iRotateWordLeft5 & iRotateWordLeft8)) + this.workingKey[i4 + 1]);
            iRotateWordLeft8 = rotateWordLeft(iRotateWordLeft8, 15) - ((((~iRotateWordLeft5) & iRotateWordLeft7) + (iRotateWordLeft6 & iRotateWordLeft5)) + this.workingKey[i4]);
        }
        int[] iArr2 = this.workingKey;
        int iRotateWordLeft9 = iRotateWordLeft5 - iArr2[iRotateWordLeft6 & 63];
        int iRotateWordLeft10 = iRotateWordLeft6 - iArr2[iRotateWordLeft7 & 63];
        int iRotateWordLeft11 = iRotateWordLeft7 - iArr2[iRotateWordLeft8 & 63];
        int iRotateWordLeft12 = iRotateWordLeft8 - iArr2[iRotateWordLeft9 & 63];
        for (int i5 = 16; i5 >= 0; i5 -= 4) {
            iRotateWordLeft9 = rotateWordLeft(iRotateWordLeft9, 11) - ((((~iRotateWordLeft10) & iRotateWordLeft12) + (iRotateWordLeft11 & iRotateWordLeft10)) + this.workingKey[i5 + 3]);
            iRotateWordLeft10 = rotateWordLeft(iRotateWordLeft10, 13) - ((((~iRotateWordLeft11) & iRotateWordLeft9) + (iRotateWordLeft12 & iRotateWordLeft11)) + this.workingKey[i5 + 2]);
            iRotateWordLeft11 = rotateWordLeft(iRotateWordLeft11, 14) - ((((~iRotateWordLeft12) & iRotateWordLeft10) + (iRotateWordLeft9 & iRotateWordLeft12)) + this.workingKey[i5 + 1]);
            iRotateWordLeft12 = rotateWordLeft(iRotateWordLeft12, 15) - ((((~iRotateWordLeft9) & iRotateWordLeft11) + (iRotateWordLeft10 & iRotateWordLeft9)) + this.workingKey[i5]);
        }
        bArr2[i2] = (byte) iRotateWordLeft12;
        bArr2[i2 + 1] = (byte) (iRotateWordLeft12 >> 8);
        bArr2[i2 + 2] = (byte) iRotateWordLeft11;
        bArr2[i2 + 3] = (byte) (iRotateWordLeft11 >> 8);
        bArr2[i2 + 4] = (byte) iRotateWordLeft10;
        bArr2[i2 + 5] = (byte) (iRotateWordLeft10 >> 8);
        bArr2[i2 + 6] = (byte) iRotateWordLeft9;
        bArr2[i2 + 7] = (byte) (iRotateWordLeft9 >> 8);
    }

    private void encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int iRotateWordLeft = ((bArr[i + 7] & 255) << 8) + (bArr[i + 6] & 255);
        int iRotateWordLeft2 = ((bArr[i + 5] & 255) << 8) + (bArr[i + 4] & 255);
        int iRotateWordLeft3 = ((bArr[i + 3] & 255) << 8) + (bArr[i + 2] & 255);
        int iRotateWordLeft4 = ((bArr[i + 1] & 255) << 8) + (bArr[i] & 255);
        for (int i3 = 0; i3 <= 16; i3 += 4) {
            iRotateWordLeft4 = rotateWordLeft(iRotateWordLeft4 + ((~iRotateWordLeft) & iRotateWordLeft3) + (iRotateWordLeft2 & iRotateWordLeft) + this.workingKey[i3], 1);
            iRotateWordLeft3 = rotateWordLeft(iRotateWordLeft3 + ((~iRotateWordLeft4) & iRotateWordLeft2) + (iRotateWordLeft & iRotateWordLeft4) + this.workingKey[i3 + 1], 2);
            iRotateWordLeft2 = rotateWordLeft(iRotateWordLeft2 + ((~iRotateWordLeft3) & iRotateWordLeft) + (iRotateWordLeft4 & iRotateWordLeft3) + this.workingKey[i3 + 2], 3);
            iRotateWordLeft = rotateWordLeft(iRotateWordLeft + ((~iRotateWordLeft2) & iRotateWordLeft4) + (iRotateWordLeft3 & iRotateWordLeft2) + this.workingKey[i3 + 3], 5);
        }
        int[] iArr = this.workingKey;
        int iRotateWordLeft5 = iRotateWordLeft4 + iArr[iRotateWordLeft & 63];
        int iRotateWordLeft6 = iRotateWordLeft3 + iArr[iRotateWordLeft5 & 63];
        int iRotateWordLeft7 = iRotateWordLeft2 + iArr[iRotateWordLeft6 & 63];
        int iRotateWordLeft8 = iRotateWordLeft + iArr[iRotateWordLeft7 & 63];
        for (int i4 = 20; i4 <= 40; i4 += 4) {
            iRotateWordLeft5 = rotateWordLeft(iRotateWordLeft5 + ((~iRotateWordLeft8) & iRotateWordLeft6) + (iRotateWordLeft7 & iRotateWordLeft8) + this.workingKey[i4], 1);
            iRotateWordLeft6 = rotateWordLeft(iRotateWordLeft6 + ((~iRotateWordLeft5) & iRotateWordLeft7) + (iRotateWordLeft8 & iRotateWordLeft5) + this.workingKey[i4 + 1], 2);
            iRotateWordLeft7 = rotateWordLeft(iRotateWordLeft7 + ((~iRotateWordLeft6) & iRotateWordLeft8) + (iRotateWordLeft5 & iRotateWordLeft6) + this.workingKey[i4 + 2], 3);
            iRotateWordLeft8 = rotateWordLeft(iRotateWordLeft8 + ((~iRotateWordLeft7) & iRotateWordLeft5) + (iRotateWordLeft6 & iRotateWordLeft7) + this.workingKey[i4 + 3], 5);
        }
        int[] iArr2 = this.workingKey;
        int iRotateWordLeft9 = iRotateWordLeft5 + iArr2[iRotateWordLeft8 & 63];
        int iRotateWordLeft10 = iRotateWordLeft6 + iArr2[iRotateWordLeft9 & 63];
        int iRotateWordLeft11 = iRotateWordLeft7 + iArr2[iRotateWordLeft10 & 63];
        int iRotateWordLeft12 = iRotateWordLeft8 + iArr2[iRotateWordLeft11 & 63];
        for (int i5 = 44; i5 < 64; i5 += 4) {
            iRotateWordLeft9 = rotateWordLeft(iRotateWordLeft9 + ((~iRotateWordLeft12) & iRotateWordLeft10) + (iRotateWordLeft11 & iRotateWordLeft12) + this.workingKey[i5], 1);
            iRotateWordLeft10 = rotateWordLeft(iRotateWordLeft10 + ((~iRotateWordLeft9) & iRotateWordLeft11) + (iRotateWordLeft12 & iRotateWordLeft9) + this.workingKey[i5 + 1], 2);
            iRotateWordLeft11 = rotateWordLeft(iRotateWordLeft11 + ((~iRotateWordLeft10) & iRotateWordLeft12) + (iRotateWordLeft9 & iRotateWordLeft10) + this.workingKey[i5 + 2], 3);
            iRotateWordLeft12 = rotateWordLeft(iRotateWordLeft12 + ((~iRotateWordLeft11) & iRotateWordLeft9) + (iRotateWordLeft10 & iRotateWordLeft11) + this.workingKey[i5 + 3], 5);
        }
        bArr2[i2] = (byte) iRotateWordLeft9;
        bArr2[i2 + 1] = (byte) (iRotateWordLeft9 >> 8);
        bArr2[i2 + 2] = (byte) iRotateWordLeft10;
        bArr2[i2 + 3] = (byte) (iRotateWordLeft10 >> 8);
        bArr2[i2 + 4] = (byte) iRotateWordLeft11;
        bArr2[i2 + 5] = (byte) (iRotateWordLeft11 >> 8);
        bArr2[i2 + 6] = (byte) iRotateWordLeft12;
        bArr2[i2 + 7] = (byte) (iRotateWordLeft12 >> 8);
    }

    private int[] generateWorkingKey(byte[] bArr, int i) {
        int[] iArr = new int[128];
        for (int i2 = 0; i2 != bArr.length; i2++) {
            iArr[i2] = bArr[i2] & 255;
        }
        int length = bArr.length;
        if (length < 128) {
            int i3 = iArr[length - 1];
            int i4 = 0;
            while (true) {
                int i5 = i4 + 1;
                i3 = piTable[(i3 + iArr[i4]) & 255] & 255;
                int i6 = length + 1;
                iArr[length] = i3;
                if (i6 >= 128) {
                    break;
                }
                length = i6;
                i4 = i5;
            }
        }
        int i7 = (i + 7) >> 3;
        int i8 = 128 - i7;
        int i9 = piTable[(255 >> ((-i) & 7)) & iArr[i8]] & 255;
        iArr[i8] = i9;
        for (int i10 = 127 - i7; i10 >= 0; i10--) {
            i9 = piTable[i9 ^ iArr[i10 + i7]] & 255;
            iArr[i10] = i9;
        }
        int[] iArr2 = new int[64];
        for (int i11 = 0; i11 != 64; i11++) {
            int i12 = i11 * 2;
            iArr2[i11] = iArr[i12] + (iArr[i12 + 1] << 8);
        }
        return iArr2;
    }

    private int rotateWordLeft(int i, int i2) {
        int i3 = i & 65535;
        return (i3 >> (16 - i2)) | (i3 << i2);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "RC2";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) {
        this.encrypting = z;
        if (cipherParameters instanceof RC2Parameters) {
            RC2Parameters rC2Parameters = (RC2Parameters) cipherParameters;
            this.workingKey = generateWorkingKey(rC2Parameters.getKey(), rC2Parameters.getEffectiveKeyBits());
        } else {
            if (!(cipherParameters instanceof KeyParameter)) {
                throw new IllegalArgumentException("invalid parameter passed to RC2 init - " + cipherParameters.getClass().getName());
            }
            byte[] key = ((KeyParameter) cipherParameters).getKey();
            this.workingKey = generateWorkingKey(key, key.length * 8);
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public final int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey == null) {
            throw new IllegalStateException("RC2 engine not initialised");
        }
        if (i + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i2 + 8 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        if (this.encrypting) {
            encryptBlock(bArr, i, bArr2, i2);
            return 8;
        }
        decryptBlock(bArr, i, bArr2, i2);
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
