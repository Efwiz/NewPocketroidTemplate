package com.badlogic.gdx.utils.compression.rangecoder;

import java.io.IOException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/compression/rangecoder/BitTreeDecoder.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class BitTreeDecoder {
    short[] Models;
    int NumBitLevels;

    public BitTreeDecoder(int i) {
        this.NumBitLevels = i;
        this.Models = new short[1 << i];
    }

    public void Init() {
        Decoder.InitBitModels(this.Models);
    }

    public int Decode(Decoder decoder) throws IOException {
        int iDecodeBit = 1;
        for (int i = this.NumBitLevels; i != 0; i--) {
            iDecodeBit = decoder.DecodeBit(this.Models, iDecodeBit) + (iDecodeBit << 1);
        }
        return iDecodeBit - (1 << this.NumBitLevels);
    }

    public int ReverseDecode(Decoder decoder) throws IOException {
        int i = 1;
        int i2 = 0;
        for (int i3 = 0; i3 < this.NumBitLevels; i3++) {
            int iDecodeBit = decoder.DecodeBit(this.Models, i);
            i = (i << 1) + iDecodeBit;
            i2 |= iDecodeBit << i3;
        }
        return i2;
    }

    public static int ReverseDecode(short[] sArr, int i, Decoder decoder, int i2) throws IOException {
        int i3 = 1;
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            int iDecodeBit = decoder.DecodeBit(sArr, i + i3);
            i3 = (i3 << 1) + iDecodeBit;
            i4 |= iDecodeBit << i5;
        }
        return i4;
    }
}
