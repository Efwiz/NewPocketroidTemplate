package org.tukaani.xz.lz;

import org.tukaani.xz.ArrayCache;

/* JADX INFO: loaded from: classes6.dex */
final class BT4 extends LZEncoder {
    private int cyclicPos;
    private final int cyclicSize;
    private final int depthLimit;
    private final Hash234 hash;
    private int lzPos;
    private final Matches matches;
    private final int[] tree;

    BT4(int i, int i2, int i3, int i4, int i5, int i6, ArrayCache arrayCache) {
        super(i, i2, i3, i4, i5, arrayCache);
        this.cyclicPos = -1;
        int i7 = i + 1;
        this.cyclicSize = i7;
        this.lzPos = i7;
        this.hash = new Hash234(i, arrayCache);
        this.tree = arrayCache.getIntArray(i7 * 2, false);
        this.matches = new Matches(i4 - 1);
        this.depthLimit = i6 <= 0 ? (i4 / 2) + 16 : i6;
    }

    static int getMemoryUsage(int i) {
        return Hash234.getMemoryUsage(i) + (i / 128) + 10;
    }

    private int movePos() {
        int iMovePos = movePos(this.niceLen, 4);
        if (iMovePos != 0) {
            int i = this.lzPos + 1;
            this.lzPos = i;
            if (i == Integer.MAX_VALUE) {
                int i2 = Integer.MAX_VALUE - this.cyclicSize;
                this.hash.normalize(i2);
                normalize(this.tree, this.cyclicSize * 2, i2);
                this.lzPos -= i2;
            }
            int i3 = this.cyclicPos + 1;
            this.cyclicPos = i3;
            if (i3 == this.cyclicSize) {
                this.cyclicPos = 0;
            }
        }
        return iMovePos;
    }

    private void skip(int i, int i2) {
        int i3;
        int i4 = this.depthLimit;
        int i5 = this.cyclicPos;
        int i6 = (i5 << 1) + 1;
        int i7 = i5 << 1;
        int i8 = 0;
        int i9 = 0;
        while (true) {
            int i10 = this.lzPos - i2;
            int i11 = i4 - 1;
            if (i4 == 0 || i10 >= (i3 = this.cyclicSize)) {
                break;
            }
            int i12 = this.cyclicPos;
            int i13 = i12 - i10;
            if (i10 <= i12) {
                i3 = 0;
            }
            int i14 = (i13 + i3) << 1;
            int iMin = Math.min(i8, i9);
            if (this.buf[(this.readPos + iMin) - i10] == this.buf[this.readPos + iMin]) {
                do {
                    iMin++;
                    if (iMin == i) {
                        int[] iArr = this.tree;
                        iArr[i7] = iArr[i14];
                        iArr[i6] = iArr[i14 + 1];
                        return;
                    }
                } while (this.buf[(this.readPos + iMin) - i10] == this.buf[this.readPos + iMin]);
            }
            if ((this.buf[(this.readPos + iMin) - i10] & 255) < (this.buf[this.readPos + iMin] & 255)) {
                int[] iArr2 = this.tree;
                iArr2[i7] = i2;
                int i15 = i14 + 1;
                i2 = iArr2[i15];
                i7 = i15;
                i9 = iMin;
            } else {
                int[] iArr3 = this.tree;
                iArr3[i6] = i2;
                i2 = iArr3[i14];
                i6 = i14;
                i8 = iMin;
            }
            i4 = i11;
        }
        int[] iArr4 = this.tree;
        iArr4[i6] = 0;
        iArr4[i7] = 0;
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00c2 A[DONT_INVERT, PHI: r9
      0x00c2: PHI (r9v3 int) = (r9v2 int), (r9v9 int) binds: [B:24:0x0097, B:30:0x00ba] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:36:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:58:0x0143 A[PHI: r14
      0x0143: PHI (r14v2 int) = (r14v1 int), (r14v5 int) binds: [B:48:0x00fb, B:53:0x0112] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // org.tukaani.xz.lz.LZEncoder
    public Matches getMatches() {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        this.matches.count = 0;
        int i5 = this.matchLenMax;
        int i6 = this.niceLen;
        int iMovePos = movePos();
        if (iMovePos < i5) {
            if (iMovePos == 0) {
                return this.matches;
            }
            i5 = iMovePos;
            if (i6 > iMovePos) {
                i6 = i5;
            }
        }
        this.hash.calcHashes(this.buf, this.readPos);
        int hash2Pos = this.lzPos - this.hash.getHash2Pos();
        int hash3Pos = this.lzPos - this.hash.getHash3Pos();
        int hash4Pos = this.hash.getHash4Pos();
        this.hash.updateTables(this.lzPos);
        if (hash2Pos >= this.cyclicSize || this.buf[this.readPos - hash2Pos] != this.buf[this.readPos]) {
            i = 0;
        } else {
            i = 2;
            this.matches.len[0] = 2;
            this.matches.dist[0] = hash2Pos - 1;
            this.matches.count = 1;
        }
        if (hash2Pos != hash3Pos && hash3Pos < this.cyclicSize && this.buf[this.readPos - hash3Pos] == this.buf[this.readPos]) {
            int[] iArr = this.matches.dist;
            Matches matches = this.matches;
            int i7 = matches.count;
            matches.count = i7 + 1;
            iArr[i7] = hash3Pos - 1;
            hash2Pos = hash3Pos;
            i = 3;
        }
        if (this.matches.count > 0) {
            while (i < i5 && this.buf[(this.readPos + i) - hash2Pos] == this.buf[this.readPos + i]) {
                i++;
            }
            this.matches.len[this.matches.count - 1] = i;
            if (i >= i6) {
                skip(i6, hash4Pos);
            } else {
                int i8 = i >= 3 ? i : 3;
                i2 = this.depthLimit;
                int i9 = this.cyclicPos;
                int i10 = (i9 << 1) + 1;
                int i11 = i9 << 1;
                int i12 = 0;
                int i13 = 0;
                while (true) {
                    int i14 = this.lzPos - hash4Pos;
                    int i15 = i2 - 1;
                    if (i2 == 0 || i14 >= (i3 = this.cyclicSize)) {
                        break;
                    }
                    int i16 = this.cyclicPos;
                    int i17 = i16 - i14;
                    if (i14 <= i16) {
                        i3 = i4;
                    }
                    int i18 = (i17 + i3) << 1;
                    int iMin = Math.min(i12, i13);
                    if (this.buf[(this.readPos + iMin) - i14] == this.buf[this.readPos + iMin]) {
                        do {
                            iMin++;
                            if (iMin >= i5) {
                                break;
                            }
                        } while (this.buf[(this.readPos + iMin) - i14] == this.buf[this.readPos + iMin]);
                        if (iMin > i8) {
                            this.matches.len[this.matches.count] = iMin;
                            this.matches.dist[this.matches.count] = i14 - 1;
                            this.matches.count++;
                            if (iMin >= i6) {
                                int[] iArr2 = this.tree;
                                iArr2[i11] = iArr2[i18];
                                iArr2[i10] = iArr2[i18 + 1];
                            } else {
                                i8 = iMin;
                            }
                        }
                    }
                    if ((this.buf[(this.readPos + iMin) - i14] & 255) < (this.buf[this.readPos + iMin] & 255)) {
                        int[] iArr3 = this.tree;
                        iArr3[i11] = hash4Pos;
                        int i19 = i18 + 1;
                        hash4Pos = iArr3[i19];
                        i11 = i19;
                        i13 = iMin;
                    } else {
                        int[] iArr4 = this.tree;
                        iArr4[i10] = hash4Pos;
                        hash4Pos = iArr4[i18];
                        i10 = i18;
                        i12 = iMin;
                    }
                    i2 = i15;
                    i4 = 0;
                }
                int[] iArr5 = this.tree;
                iArr5[i10] = 0;
                iArr5[i11] = 0;
            }
        } else {
            if (i >= 3) {
            }
            i2 = this.depthLimit;
            int i20 = this.cyclicPos;
            int i110 = (i20 << 1) + 1;
            int i111 = i20 << 1;
            int i112 = 0;
            int i113 = 0;
            while (true) {
                int i114 = this.lzPos - hash4Pos;
                int i115 = i2 - 1;
                if (i2 == 0) {
                    break;
                }
                break;
                break;
                i2 = i115;
                i4 = 0;
            }
            int[] iArr6 = this.tree;
            iArr6[i110] = 0;
            iArr6[i111] = 0;
        }
        return this.matches;
    }

    @Override // org.tukaani.xz.lz.LZEncoder
    public void putArraysToCache(ArrayCache arrayCache) {
        arrayCache.putArray(this.tree);
        this.hash.putArraysToCache(arrayCache);
        super.putArraysToCache(arrayCache);
    }

    @Override // org.tukaani.xz.lz.LZEncoder
    public void skip(int i) {
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            int i3 = this.niceLen;
            int iMovePos = movePos();
            if (iMovePos >= i3) {
                this.hash.calcHashes(this.buf, this.readPos);
                int hash4Pos = this.hash.getHash4Pos();
                this.hash.updateTables(this.lzPos);
                skip(i3, hash4Pos);
            } else if (iMovePos != 0) {
                i3 = iMovePos;
                this.hash.calcHashes(this.buf, this.readPos);
                int hash4Pos2 = this.hash.getHash4Pos();
                this.hash.updateTables(this.lzPos);
                skip(i3, hash4Pos2);
            }
            i = i2;
        }
    }
}
