package com.badlogic.gdx.utils.compression.lz;

import java.io.IOException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/compression/lz/BinTree.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class BinTree extends InWindow {
    private static final int[] CrcTable = new int[256];
    static final int kBT2HashSize = 65536;
    static final int kEmptyHashValue = 0;
    static final int kHash2Size = 1024;
    static final int kHash3Offset = 1024;
    static final int kHash3Size = 65536;
    static final int kMaxValForNormalize = 1073741823;
    static final int kStartMaxLen = 1;
    int _cyclicBufferPos;
    int[] _hash;
    int _hashMask;
    int _matchMaxLen;
    int[] _son;
    int _cyclicBufferSize = 0;
    int _cutValue = 255;
    int _hashSizeSum = 0;
    boolean HASH_ARRAY = true;
    int kNumHashDirectBytes = 0;
    int kMinMatchCheck = 4;
    int kFixHashSize = 66560;

    public void SetType(int i) {
        boolean z = i > 2;
        this.HASH_ARRAY = z;
        if (z) {
            this.kNumHashDirectBytes = 0;
            this.kMinMatchCheck = 4;
            this.kFixHashSize = 66560;
        } else {
            this.kNumHashDirectBytes = 2;
            this.kMinMatchCheck = 3;
            this.kFixHashSize = 0;
        }
    }

    @Override // com.badlogic.gdx.utils.compression.lz.InWindow
    public void Init() throws IOException {
        super.Init();
        for (int i = 0; i < this._hashSizeSum; i++) {
            this._hash[i] = 0;
        }
        this._cyclicBufferPos = 0;
        ReduceOffsets(-1);
    }

    @Override // com.badlogic.gdx.utils.compression.lz.InWindow
    public void MovePos() throws IOException {
        int i = this._cyclicBufferPos + 1;
        this._cyclicBufferPos = i;
        if (i >= this._cyclicBufferSize) {
            this._cyclicBufferPos = 0;
        }
        super.MovePos();
        if (this._pos == 1073741823) {
            Normalize();
        }
    }

    public boolean Create(int i, int i2, int i3, int i4) {
        int i5;
        if (i > 1073741567) {
            return false;
        }
        this._cutValue = (i3 >> 1) + 16;
        int i6 = i2 + i;
        super.Create(i6, i4 + i3, (((i6 + i3) + i4) / 2) + 256);
        this._matchMaxLen = i3;
        int i7 = i + 1;
        if (this._cyclicBufferSize != i7) {
            this._cyclicBufferSize = i7;
            this._son = new int[i7 * 2];
        }
        if (this.HASH_ARRAY) {
            int i8 = i - 1;
            int i9 = i8 | (i8 >> 1);
            int i10 = i9 | (i9 >> 2);
            int i11 = i10 | (i10 >> 4);
            int i12 = ((i11 | (i11 >> 8)) >> 1) | 65535;
            if (i12 > 16777216) {
                i12 >>= 1;
            }
            this._hashMask = i12;
            i5 = i12 + 1 + this.kFixHashSize;
        } else {
            i5 = 65536;
        }
        if (i5 != this._hashSizeSum) {
            this._hashSizeSum = i5;
            this._hash = new int[i5];
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:41:0x0106  */
    /* JADX WARN: Code duplicated, block: B:44:0x0119  */
    /* JADX WARN: Code duplicated, block: B:47:0x011f  */
    /* JADX WARN: Code duplicated, block: B:49:0x0126  */
    /* JADX WARN: Code duplicated, block: B:50:0x0128  */
    /* JADX WARN: Code duplicated, block: B:53:0x0145 A[LOOP:1: B:53:0x0145->B:58:0x015b, LOOP_START, PHI: r10 r16
      0x0145: PHI (r10v13 int) = (r10v4 int), (r10v16 int) binds: [B:52:0x0143, B:58:0x015b] A[DONT_GENERATE, DONT_INLINE]
      0x0145: PHI (r16v2 int) = (r16v0 int), (r16v7 int) binds: [B:52:0x0143, B:58:0x015b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:55:0x014a  */
    /* JADX WARN: Code duplicated, block: B:58:0x015b A[LOOP:1: B:53:0x0145->B:58:0x015b, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:61:0x0164  */
    /* JADX WARN: Code duplicated, block: B:64:0x017d  */
    /* JADX WARN: Code duplicated, block: B:65:0x0182  */
    /* JADX WARN: Code duplicated, block: B:66:0x0186  */
    /* JADX WARN: Code duplicated, block: B:69:0x019b  */
    /* JADX WARN: Code duplicated, block: B:70:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:76:0x01bb A[EDGE_INSN: B:76:0x01bb->B:72:0x01bb BREAK  A[LOOP:0: B:43:0x0117->B:71:0x01b4], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:77:0x0170 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x0160 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x0162 A[EDGE_INSN: B:81:0x0162->B:60:0x0162 BREAK  A[LOOP:1: B:53:0x0145->B:58:0x015b], SYNTHETIC] */
    public int GetMatches(int[] iArr) throws IOException {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int iMin;
        int i18;
        boolean z;
        int i19;
        byte b;
        byte[] bArr;
        int i20;
        if (this._pos + this._matchMaxLen <= this._streamPos) {
            i = this._matchMaxLen;
        } else {
            i = this._streamPos - this._pos;
            if (i < this.kMinMatchCheck) {
                MovePos();
                return 0;
            }
        }
        int i21 = this._pos > this._cyclicBufferSize ? this._pos - this._cyclicBufferSize : 0;
        int i22 = this._bufferOffset + this._pos;
        if (this.HASH_ARRAY) {
            int[] iArr2 = CrcTable;
            int i23 = iArr2[this._bufferBase[i22] & 255] ^ (this._bufferBase[i22 + 1] & 255);
            i3 = i23 & 1023;
            int i24 = i23 ^ ((this._bufferBase[i22 + 2] & 255) << 8);
            i4 = 65535 & i24;
            i2 = ((iArr2[this._bufferBase[i22 + 3] & 255] << 5) ^ i24) & this._hashMask;
        } else {
            i2 = (this._bufferBase[i22] & 255) ^ ((this._bufferBase[i22 + 1] & 255) << 8);
            i3 = 0;
            i4 = 0;
        }
        int[] iArr3 = this._hash;
        int i25 = iArr3[this.kFixHashSize + i2];
        if (this.HASH_ARRAY) {
            int i26 = iArr3[i3];
            int i27 = i4 + 1024;
            int i28 = iArr3[i27];
            iArr3[i3] = this._pos;
            this._hash[i27] = this._pos;
            if (i26 <= i21 || this._bufferBase[this._bufferOffset + i26] != this._bufferBase[i22]) {
                i5 = 0;
                i6 = 1;
            } else {
                iArr[0] = 2;
                iArr[1] = (this._pos - i26) - 1;
                i5 = 2;
                i6 = 2;
            }
            if (i28 > i21 && this._bufferBase[this._bufferOffset + i28] == this._bufferBase[i22]) {
                if (i28 == i26) {
                    i5 -= 2;
                }
                int i29 = i5 + 1;
                iArr[i5] = 3;
                i5 += 2;
                iArr[i29] = (this._pos - i28) - 1;
                i6 = 3;
                i26 = i28;
            }
            if (i5 != 0 && i26 == i25) {
                i5 -= 2;
            }
            this._hash[this.kFixHashSize + i2] = this._pos;
            int i30 = this._cyclicBufferPos;
            i7 = (i30 << 1) + 1;
            i8 = i30 << 1;
            i9 = this.kNumHashDirectBytes;
            if (i9 != 0 && i25 > i21) {
                b = this._bufferBase[this._bufferOffset + i25 + this.kNumHashDirectBytes];
                bArr = this._bufferBase;
                i20 = this.kNumHashDirectBytes;
                if (b != bArr[i22 + i20]) {
                    int i31 = i5 + 1;
                    iArr[i5] = i20;
                    i5 += 2;
                    iArr[i31] = (this._pos - i25) - 1;
                    i6 = i20;
                }
            }
            i10 = this._cutValue;
            i11 = i9;
            while (i25 > i21) {
                i12 = i10 - 1;
                if (i10 == 0) {
                    break;
                }
                i13 = this._pos - i25;
                i14 = this._cyclicBufferPos;
                if (i13 <= i14) {
                    i15 = i14 - i13;
                } else {
                    i15 = (i14 - i13) + this._cyclicBufferSize;
                }
                i16 = i15 << 1;
                i17 = this._bufferOffset + i25;
                iMin = Math.min(i9, i11);
                int i32 = i21;
                if (this._bufferBase[i17 + iMin] == this._bufferBase[i22 + iMin]) {
                    while (true) {
                        i19 = iMin + 1;
                        if (i19 != i) {
                            i18 = i9;
                            break;
                        }
                        i18 = i9;
                        if (this._bufferBase[i17 + i19] != this._bufferBase[i22 + i19]) {
                            break;
                        }
                        iMin = i19;
                        i9 = i18;
                    }
                    if (i6 < i19) {
                        int i33 = i5 + 1;
                        iArr[i5] = i19;
                        i5 += 2;
                        iArr[i33] = i13 - 1;
                        if (i19 == i) {
                            int[] iArr4 = this._son;
                            iArr4[i8] = iArr4[i16];
                            iArr4[i7] = iArr4[i16 + 1];
                        } else {
                            z = true;
                            i6 = i19;
                            iMin = i6;
                        }
                    } else {
                        z = true;
                        iMin = i19;
                    }
                } else {
                    i18 = i9;
                    z = true;
                }
                if ((this._bufferBase[i17 + iMin] & 255) < (this._bufferBase[i22 + iMin] & 255)) {
                    int[] iArr5 = this._son;
                    iArr5[i8] = i25;
                    int i34 = i16 + 1;
                    i25 = iArr5[i34];
                    i11 = iMin;
                    i9 = i18;
                    i8 = i34;
                } else {
                    int[] iArr6 = this._son;
                    iArr6[i7] = i25;
                    i25 = iArr6[i16];
                    i9 = iMin;
                    i7 = i16;
                }
                i10 = i12;
                i21 = i32;
                MovePos();
                return i5;
            }
            int[] iArr7 = this._son;
            iArr7[i8] = 0;
            iArr7[i7] = 0;
            MovePos();
            return i5;
        }
        i5 = 0;
        i6 = 1;
        this._hash[this.kFixHashSize + i2] = this._pos;
        int i35 = this._cyclicBufferPos;
        i7 = (i35 << 1) + 1;
        i8 = i35 << 1;
        i9 = this.kNumHashDirectBytes;
        if (i9 != 0) {
            b = this._bufferBase[this._bufferOffset + i25 + this.kNumHashDirectBytes];
            bArr = this._bufferBase;
            i20 = this.kNumHashDirectBytes;
            if (b != bArr[i22 + i20]) {
                int i36 = i5 + 1;
                iArr[i5] = i20;
                i5 += 2;
                iArr[i36] = (this._pos - i25) - 1;
                i6 = i20;
            }
        }
        i10 = this._cutValue;
        i11 = i9;
        while (i25 > i21) {
            i12 = i10 - 1;
            if (i10 == 0) {
                break;
                break;
            }
            i13 = this._pos - i25;
            i14 = this._cyclicBufferPos;
            if (i13 <= i14) {
                i15 = i14 - i13;
            } else {
                i15 = (i14 - i13) + this._cyclicBufferSize;
            }
            i16 = i15 << 1;
            i17 = this._bufferOffset + i25;
            iMin = Math.min(i9, i11);
            int i37 = i21;
            if (this._bufferBase[i17 + iMin] == this._bufferBase[i22 + iMin]) {
                while (true) {
                    i19 = iMin + 1;
                    if (i19 != i) {
                        i18 = i9;
                        break;
                    }
                    i18 = i9;
                    if (this._bufferBase[i17 + i19] != this._bufferBase[i22 + i19]) {
                        break;
                        break;
                    }
                    iMin = i19;
                    i9 = i18;
                }
                if (i6 < i19) {
                    int i38 = i5 + 1;
                    iArr[i5] = i19;
                    i5 += 2;
                    iArr[i38] = i13 - 1;
                    if (i19 == i) {
                        int[] iArr8 = this._son;
                        iArr8[i8] = iArr8[i16];
                        iArr8[i7] = iArr8[i16 + 1];
                    } else {
                        z = true;
                        i6 = i19;
                        iMin = i6;
                    }
                } else {
                    z = true;
                    iMin = i19;
                }
            } else {
                i18 = i9;
                z = true;
            }
            if ((this._bufferBase[i17 + iMin] & 255) < (this._bufferBase[i22 + iMin] & 255)) {
                int[] iArr9 = this._son;
                iArr9[i8] = i25;
                int i39 = i16 + 1;
                i25 = iArr9[i39];
                i11 = iMin;
                i9 = i18;
                i8 = i39;
            } else {
                int[] iArr10 = this._son;
                iArr10[i7] = i25;
                i25 = iArr10[i16];
                i9 = iMin;
                i7 = i16;
            }
            i10 = i12;
            i21 = i37;
            MovePos();
            return i5;
        }
        int[] iArr11 = this._son;
        iArr11[i8] = 0;
        iArr11[i7] = 0;
        MovePos();
        return i5;
    }

    public void Skip(int i) throws IOException {
        int i2;
        int i3;
        int i4 = i;
        do {
            if (this._pos + this._matchMaxLen <= this._streamPos) {
                i2 = this._matchMaxLen;
            } else {
                i2 = this._streamPos - this._pos;
                if (i2 < this.kMinMatchCheck) {
                    MovePos();
                }
                i4--;
            }
            int i5 = this._pos > this._cyclicBufferSize ? this._pos - this._cyclicBufferSize : 0;
            int i6 = this._bufferOffset + this._pos;
            if (this.HASH_ARRAY) {
                int[] iArr = CrcTable;
                int i7 = iArr[this._bufferBase[i6] & 255] ^ (this._bufferBase[i6 + 1] & 255);
                this._hash[i7 & 1023] = this._pos;
                int i8 = i7 ^ ((this._bufferBase[i6 + 2] & 255) << 8);
                this._hash[(65535 & i8) + 1024] = this._pos;
                i3 = ((iArr[this._bufferBase[i6 + 3] & 255] << 5) ^ i8) & this._hashMask;
            } else {
                i3 = (this._bufferBase[i6] & 255) ^ ((this._bufferBase[i6 + 1] & 255) << 8);
            }
            int[] iArr2 = this._hash;
            int i9 = this.kFixHashSize;
            int i10 = iArr2[i9 + i3];
            iArr2[i9 + i3] = this._pos;
            int i11 = this._cyclicBufferPos;
            int i12 = (i11 << 1) + 1;
            int i13 = i11 << 1;
            int i14 = this.kNumHashDirectBytes;
            int i15 = this._cutValue;
            int i16 = i10;
            int i17 = i14;
            while (true) {
                if (i16 > i5) {
                    int i18 = i15 - 1;
                    if (i15 != 0) {
                        int i19 = this._pos - i16;
                        int i20 = this._cyclicBufferPos;
                        int i21 = (i19 <= i20 ? i20 - i19 : (i20 - i19) + this._cyclicBufferSize) << 1;
                        int i22 = this._bufferOffset + i16;
                        int iMin = Math.min(i14, i17);
                        if (this._bufferBase[i22 + iMin] == this._bufferBase[i6 + iMin]) {
                            do {
                                iMin++;
                                if (iMin == i2) {
                                    break;
                                }
                            } while (this._bufferBase[i22 + iMin] == this._bufferBase[i6 + iMin]);
                            if (iMin == i2) {
                                int[] iArr3 = this._son;
                                iArr3[i13] = iArr3[i21];
                                iArr3[i12] = iArr3[i21 + 1];
                                break;
                            }
                        }
                        if ((this._bufferBase[i22 + iMin] & 255) < (this._bufferBase[i6 + iMin] & 255)) {
                            int[] iArr4 = this._son;
                            iArr4[i13] = i16;
                            int i23 = i21 + 1;
                            i16 = iArr4[i23];
                            i13 = i23;
                            i17 = iMin;
                        } else {
                            int[] iArr5 = this._son;
                            iArr5[i12] = i16;
                            i16 = iArr5[i21];
                            i12 = i21;
                            i14 = iMin;
                        }
                        i15 = i18;
                    }
                }
                int[] iArr6 = this._son;
                iArr6[i13] = 0;
                iArr6[i12] = 0;
                break;
            }
            MovePos();
            i4--;
        } while (i4 != 0);
    }

    void NormalizeLinks(int[] iArr, int i, int i2) {
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = iArr[i3];
            iArr[i3] = i4 <= i2 ? 0 : i4 - i2;
        }
    }

    void Normalize() {
        int i = this._pos;
        int i2 = this._cyclicBufferSize;
        int i3 = i - i2;
        NormalizeLinks(this._son, i2 * 2, i3);
        NormalizeLinks(this._hash, this._hashSizeSum, i3);
        ReduceOffsets(i3);
    }

    public void SetCutValue(int i) {
        this._cutValue = i;
    }

    static {
        for (int i = 0; i < 256; i++) {
            int i2 = i;
            for (int i3 = 0; i3 < 8; i3++) {
                i2 = (i2 & 1) != 0 ? (i2 >>> 1) ^ (-306674912) : i2 >>> 1;
            }
            CrcTable[i] = i2;
        }
    }
}
