package com.badlogic.gdx.utils.compression.lzma;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.compression.ICodeProgress;
import com.badlogic.gdx.utils.compression.lz.BinTree;
import com.badlogic.gdx.utils.compression.rangecoder.BitTreeEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.luaj.vm2.Lua;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/compression/lzma/Encoder.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Encoder {
    public static final int EMatchFinderTypeBT2 = 0;
    public static final int EMatchFinderTypeBT4 = 1;
    static byte[] g_FastPos = null;
    static final int kDefaultDictionaryLogSize = 22;
    static final int kIfinityPrice = 268435455;
    static final int kNumFastBytesDefault = 32;
    public static final int kNumLenSpecSymbols = 16;
    static final int kNumOpts = 4096;
    public static final int kPropSize = 5;
    int _additionalOffset;
    int _alignPriceCount;
    boolean _finished;
    InputStream _inStream;
    int _longestMatchLength;
    boolean _longestMatchWasFound;
    int _matchPriceCount;
    int _numDistancePairs;
    int _optimumCurrentIndex;
    int _optimumEndIndex;
    byte _previousByte;
    int backRes;
    long nowPos64;
    int _state = Base.StateInit();
    int[] _repDistances = new int[4];
    Optimal[] _optimum = new Optimal[4096];
    BinTree _matchFinder = null;
    com.badlogic.gdx.utils.compression.rangecoder.Encoder _rangeEncoder = new com.badlogic.gdx.utils.compression.rangecoder.Encoder();
    short[] _isMatch = new short[192];
    short[] _isRep = new short[12];
    short[] _isRepG0 = new short[12];
    short[] _isRepG1 = new short[12];
    short[] _isRepG2 = new short[12];
    short[] _isRep0Long = new short[192];
    BitTreeEncoder[] _posSlotEncoder = new BitTreeEncoder[4];
    short[] _posEncoders = new short[114];
    BitTreeEncoder _posAlignEncoder = new BitTreeEncoder(4);
    LenPriceTableEncoder _lenEncoder = new LenPriceTableEncoder();
    LenPriceTableEncoder _repMatchLenEncoder = new LenPriceTableEncoder();
    LiteralEncoder _literalEncoder = new LiteralEncoder();
    int[] _matchDistances = new int[548];
    int _numFastBytes = 32;
    int[] _posSlotPrices = new int[256];
    int[] _distancesPrices = new int[512];
    int[] _alignPrices = new int[16];
    int _distTableSize = 44;
    int _posStateBits = 2;
    int _posStateMask = 3;
    int _numLiteralPosStateBits = 0;
    int _numLiteralContextBits = 3;
    int _dictionarySize = 4194304;
    int _dictionarySizePrev = -1;
    int _numFastBytesPrev = -1;
    int _matchFinderType = 1;
    boolean _writeEndMark = false;
    boolean _needReleaseMFStream = false;
    int[] reps = new int[4];
    int[] repLens = new int[4];
    long[] processedInSize = new long[1];
    long[] processedOutSize = new long[1];
    boolean[] finished = new boolean[1];
    byte[] properties = new byte[5];
    int[] tempPrices = new int[128];

    boolean ChangePair(int i, int i2) {
        return i < 33554432 && i2 >= (i << 7);
    }

    public boolean SetAlgorithm(int i) {
        return true;
    }

    static {
        byte[] bArr = new byte[2048];
        g_FastPos = bArr;
        bArr[0] = 0;
        bArr[1] = 1;
        int i = 2;
        for (int i2 = 2; i2 < 22; i2++) {
            int i3 = 1 << ((i2 >> 1) - 1);
            int i4 = 0;
            while (i4 < i3) {
                g_FastPos[i] = (byte) i2;
                i4++;
                i++;
            }
        }
    }

    static int GetPosSlot(int i) {
        if (i < 2048) {
            return g_FastPos[i];
        }
        if (i < 2097152) {
            return g_FastPos[i >> 10] + 20;
        }
        return g_FastPos[i >> 20] + 40;
    }

    static int GetPosSlot2(int i) {
        if (i < 131072) {
            return g_FastPos[i >> 6] + 12;
        }
        if (i < 134217728) {
            return g_FastPos[i >> 16] + 32;
        }
        return g_FastPos[i >> 26] + 52;
    }

    void BaseInit() {
        this._state = Base.StateInit();
        this._previousByte = (byte) 0;
        for (int i = 0; i < 4; i++) {
            this._repDistances[i] = 0;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/compression/lzma/Encoder$LiteralEncoder.class
      classes8.dex
     */
    class LiteralEncoder {
        Encoder2[] m_Coders;
        int m_NumPosBits;
        int m_NumPrevBits;
        int m_PosMask;

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/compression/lzma/Encoder$LiteralEncoder$Encoder2.class
          classes8.dex
         */
        class Encoder2 {
            short[] m_Encoders = new short[768];

            Encoder2() {
            }

            public void Init() {
                com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this.m_Encoders);
            }

            public void Encode(com.badlogic.gdx.utils.compression.rangecoder.Encoder encoder, byte b) throws IOException {
                int i = 1;
                for (int i2 = 7; i2 >= 0; i2--) {
                    int i3 = (b >> i2) & 1;
                    encoder.Encode(this.m_Encoders, i, i3);
                    i = (i << 1) | i3;
                }
            }

            public void EncodeMatched(com.badlogic.gdx.utils.compression.rangecoder.Encoder encoder, byte b, byte b2) throws IOException {
                int i;
                int i2 = 1;
                boolean z = true;
                for (int i3 = 7; i3 >= 0; i3--) {
                    int i4 = (b2 >> i3) & 1;
                    if (z) {
                        int i5 = (b >> i3) & 1;
                        i = ((i5 + 1) << 8) + i2;
                        z = i5 == i4;
                    } else {
                        i = i2;
                    }
                    encoder.Encode(this.m_Encoders, i, i4);
                    i2 = (i2 << 1) | i4;
                }
            }

            /* JADX WARN: Code duplicated, block: B:12:0x0028  */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x0028 -> B:13:0x0036). Please report as a decompilation issue!!! */
            /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
                jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
                	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
                	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
                	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
                */
            public int GetPrice(boolean r8, byte r9, byte r10) {
                /*
                    r7 = this;
                    r0 = 0
                    r1 = 1
                    r2 = 7
                    if (r8 == 0) goto L25
                    r8 = r1
                L6:
                    if (r2 < 0) goto L26
                    int r3 = r9 >> r2
                    r3 = r3 & r1
                    int r4 = r10 >> r2
                    r4 = r4 & r1
                    short[] r5 = r7.m_Encoders
                    int r6 = r3 + 1
                    int r6 = r6 << 8
                    int r6 = r6 + r8
                    short r5 = r5[r6]
                    int r5 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice(r5, r4)
                    int r0 = r0 + r5
                    int r8 = r8 << 1
                    r8 = r8 | r4
                    if (r3 == r4) goto L22
                    goto L36
                L22:
                    int r2 = r2 + (-1)
                    goto L6
                L25:
                    r8 = r1
                L26:
                    if (r2 < 0) goto L39
                    int r9 = r10 >> r2
                    r9 = r9 & r1
                    short[] r3 = r7.m_Encoders
                    short r3 = r3[r8]
                    int r3 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice(r3, r9)
                    int r0 = r0 + r3
                    int r8 = r8 << r1
                    r8 = r8 | r9
                L36:
                    int r2 = r2 + (-1)
                    goto L26
                L39:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.utils.compression.lzma.Encoder.LiteralEncoder.Encoder2.GetPrice(boolean, byte, byte):int");
            }
        }

        LiteralEncoder() {
        }

        public void Create(int i, int i2) {
            if (this.m_Coders != null && this.m_NumPrevBits == i2 && this.m_NumPosBits == i) {
                return;
            }
            this.m_NumPosBits = i;
            this.m_PosMask = (1 << i) - 1;
            this.m_NumPrevBits = i2;
            int i3 = 1 << (i2 + i);
            this.m_Coders = new Encoder2[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                this.m_Coders[i4] = new Encoder2();
            }
        }

        public void Init() {
            int i = 1 << (this.m_NumPrevBits + this.m_NumPosBits);
            for (int i2 = 0; i2 < i; i2++) {
                this.m_Coders[i2].Init();
            }
        }

        public Encoder2 GetSubCoder(int i, byte b) {
            Encoder2[] encoder2Arr = this.m_Coders;
            int i2 = i & this.m_PosMask;
            int i3 = this.m_NumPrevBits;
            return encoder2Arr[(i2 << i3) + ((b & 255) >>> (8 - i3))];
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/compression/lzma/Encoder$LenEncoder.class
      classes8.dex
     */
    class LenEncoder {
        short[] _choice = new short[2];
        BitTreeEncoder[] _lowCoder = new BitTreeEncoder[16];
        BitTreeEncoder[] _midCoder = new BitTreeEncoder[16];
        BitTreeEncoder _highCoder = new BitTreeEncoder(8);

        public LenEncoder() {
            for (int i = 0; i < 16; i++) {
                this._lowCoder[i] = new BitTreeEncoder(3);
                this._midCoder[i] = new BitTreeEncoder(3);
            }
        }

        public void Init(int i) {
            com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._choice);
            for (int i2 = 0; i2 < i; i2++) {
                this._lowCoder[i2].Init();
                this._midCoder[i2].Init();
            }
            this._highCoder.Init();
        }

        public void Encode(com.badlogic.gdx.utils.compression.rangecoder.Encoder encoder, int i, int i2) throws IOException {
            if (i < 8) {
                encoder.Encode(this._choice, 0, 0);
                this._lowCoder[i2].Encode(encoder, i);
                return;
            }
            int i3 = i - 8;
            encoder.Encode(this._choice, 0, 1);
            if (i3 < 8) {
                encoder.Encode(this._choice, 1, 0);
                this._midCoder[i2].Encode(encoder, i3);
            } else {
                encoder.Encode(this._choice, 1, 1);
                this._highCoder.Encode(encoder, i - 16);
            }
        }

        public void SetPrices(int i, int i2, int[] iArr, int i3) {
            int i4 = 0;
            int iGetPrice0 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._choice[0]);
            int iGetPrice1 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._choice[0]);
            int iGetPrice2 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._choice[1]) + iGetPrice1;
            int iGetPrice3 = iGetPrice1 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._choice[1]);
            while (i4 < 8) {
                if (i4 >= i2) {
                    return;
                }
                iArr[i3 + i4] = this._lowCoder[i].GetPrice(i4) + iGetPrice0;
                i4++;
            }
            while (i4 < 16) {
                if (i4 >= i2) {
                    return;
                }
                iArr[i3 + i4] = this._midCoder[i].GetPrice(i4 - 8) + iGetPrice2;
                i4++;
            }
            while (i4 < i2) {
                iArr[i3 + i4] = this._highCoder.GetPrice(i4 - 16) + iGetPrice3;
                i4++;
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/compression/lzma/Encoder$LenPriceTableEncoder.class
      classes8.dex
     */
    class LenPriceTableEncoder extends LenEncoder {
        int[] _counters;
        int[] _prices;
        int _tableSize;

        LenPriceTableEncoder() {
            super();
            this._prices = new int[GL20.GL_DONT_CARE];
            this._counters = new int[16];
        }

        public void SetTableSize(int i) {
            this._tableSize = i;
        }

        public int GetPrice(int i, int i2) {
            return this._prices[(i2 * 272) + i];
        }

        void UpdateTable(int i) {
            SetPrices(i, this._tableSize, this._prices, i * 272);
            this._counters[i] = this._tableSize;
        }

        public void UpdateTables(int i) {
            for (int i2 = 0; i2 < i; i2++) {
                UpdateTable(i2);
            }
        }

        @Override // com.badlogic.gdx.utils.compression.lzma.Encoder.LenEncoder
        public void Encode(com.badlogic.gdx.utils.compression.rangecoder.Encoder encoder, int i, int i2) throws IOException {
            super.Encode(encoder, i, i2);
            int[] iArr = this._counters;
            int i3 = iArr[i2] - 1;
            iArr[i2] = i3;
            if (i3 == 0) {
                UpdateTable(i2);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/compression/lzma/Encoder$Optimal.class
      classes8.dex
     */
    class Optimal {
        public int BackPrev;
        public int BackPrev2;
        public int Backs0;
        public int Backs1;
        public int Backs2;
        public int Backs3;
        public int PosPrev;
        public int PosPrev2;
        public boolean Prev1IsChar;
        public boolean Prev2;
        public int Price;
        public int State;

        Optimal() {
        }

        public void MakeAsChar() {
            this.BackPrev = -1;
            this.Prev1IsChar = false;
        }

        public void MakeAsShortRep() {
            this.BackPrev = 0;
            this.Prev1IsChar = false;
        }

        public boolean IsShortRep() {
            return this.BackPrev == 0;
        }
    }

    void Create() {
        if (this._matchFinder == null) {
            BinTree binTree = new BinTree();
            binTree.SetType(this._matchFinderType == 0 ? 2 : 4);
            this._matchFinder = binTree;
        }
        this._literalEncoder.Create(this._numLiteralPosStateBits, this._numLiteralContextBits);
        int i = this._dictionarySize;
        if (i == this._dictionarySizePrev && this._numFastBytesPrev == this._numFastBytes) {
            return;
        }
        this._matchFinder.Create(i, 4096, this._numFastBytes, 274);
        this._dictionarySizePrev = this._dictionarySize;
        this._numFastBytesPrev = this._numFastBytes;
    }

    public Encoder() {
        for (int i = 0; i < 4096; i++) {
            this._optimum[i] = new Optimal();
        }
        for (int i2 = 0; i2 < 4; i2++) {
            this._posSlotEncoder[i2] = new BitTreeEncoder(6);
        }
    }

    void SetWriteEndMarkerMode(boolean z) {
        this._writeEndMark = z;
    }

    void Init() {
        BaseInit();
        this._rangeEncoder.Init();
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isMatch);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRep0Long);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRep);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRepG0);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRepG1);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRepG2);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._posEncoders);
        this._literalEncoder.Init();
        for (int i = 0; i < 4; i++) {
            this._posSlotEncoder[i].Init();
        }
        this._lenEncoder.Init(1 << this._posStateBits);
        this._repMatchLenEncoder.Init(1 << this._posStateBits);
        this._posAlignEncoder.Init();
        this._longestMatchWasFound = false;
        this._optimumEndIndex = 0;
        this._optimumCurrentIndex = 0;
        this._additionalOffset = 0;
    }

    int ReadMatchDistances() throws IOException {
        int iGetMatchLen;
        int iGetMatches = this._matchFinder.GetMatches(this._matchDistances);
        this._numDistancePairs = iGetMatches;
        if (iGetMatches > 0) {
            int[] iArr = this._matchDistances;
            iGetMatchLen = iArr[iGetMatches - 2];
            if (iGetMatchLen == this._numFastBytes) {
                iGetMatchLen += this._matchFinder.GetMatchLen(iGetMatchLen - 1, iArr[iGetMatches - 1], 273 - iGetMatchLen);
            }
        } else {
            iGetMatchLen = 0;
        }
        this._additionalOffset++;
        return iGetMatchLen;
    }

    void MovePos(int i) throws IOException {
        if (i > 0) {
            this._matchFinder.Skip(i);
            this._additionalOffset += i;
        }
    }

    int GetRepLen1Price(int i, int i2) {
        return com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRepG0[i]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRep0Long[(i << 4) + i2]);
    }

    int GetPureRepPrice(int i, int i2, int i3) {
        int iGetPrice;
        if (i == 0) {
            return com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRepG0[i2]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep0Long[(i2 << 4) + i3]);
        }
        int iGetPrice1 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRepG0[i2]);
        if (i == 1) {
            iGetPrice = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRepG1[i2]);
        } else {
            iGetPrice1 += com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRepG1[i2]);
            iGetPrice = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice(this._isRepG2[i2], i - 2);
        }
        return iGetPrice + iGetPrice1;
    }

    int GetRepPrice(int i, int i2, int i3, int i4) {
        return this._repMatchLenEncoder.GetPrice(i2 - 2, i4) + GetPureRepPrice(i, i3, i4);
    }

    int GetPosLenPrice(int i, int i2, int i3) {
        int i4;
        int iGetLenToPosState = Base.GetLenToPosState(i2);
        if (i < 128) {
            i4 = this._distancesPrices[(iGetLenToPosState * 128) + i];
        } else {
            i4 = this._alignPrices[i & 15] + this._posSlotPrices[(iGetLenToPosState << 6) + GetPosSlot2(i)];
        }
        return i4 + this._lenEncoder.GetPrice(i2 - 2, i3);
    }

    int Backward(int i) {
        this._optimumEndIndex = i;
        int i2 = this._optimum[i].PosPrev;
        int i3 = this._optimum[i].BackPrev;
        while (true) {
            if (this._optimum[i].Prev1IsChar) {
                this._optimum[i2].MakeAsChar();
                int i4 = i2 - 1;
                this._optimum[i2].PosPrev = i4;
                if (this._optimum[i].Prev2) {
                    this._optimum[i4].Prev1IsChar = false;
                    Optimal[] optimalArr = this._optimum;
                    optimalArr[i4].PosPrev = optimalArr[i].PosPrev2;
                    Optimal[] optimalArr2 = this._optimum;
                    optimalArr2[i4].BackPrev = optimalArr2[i].BackPrev2;
                }
            }
            int i5 = this._optimum[i2].BackPrev;
            int i6 = this._optimum[i2].PosPrev;
            this._optimum[i2].BackPrev = i3;
            this._optimum[i2].PosPrev = i;
            if (i2 <= 0) {
                this.backRes = this._optimum[0].BackPrev;
                int i7 = this._optimum[0].PosPrev;
                this._optimumCurrentIndex = i7;
                return i7;
            }
            i = i2;
            i3 = i5;
            i2 = i6;
        }
    }

    /* JADX WARN: Code duplicated, block: B:162:0x048f  */
    /* JADX WARN: Code duplicated, block: B:164:0x049d  */
    /* JADX WARN: Code duplicated, block: B:165:0x04a3  */
    /* JADX WARN: Code duplicated, block: B:171:0x04c2  */
    /* JADX WARN: Code duplicated, block: B:175:0x04d2  */
    /* JADX WARN: Code duplicated, block: B:177:0x04d6  */
    /* JADX WARN: Code duplicated, block: B:179:0x04ec  */
    /* JADX WARN: Code duplicated, block: B:182:0x0563 A[LOOP:10: B:180:0x055f->B:182:0x0563, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:185:0x057e  */
    /* JADX WARN: Code duplicated, block: B:187:0x0592  */
    /* JADX WARN: Code duplicated, block: B:189:0x05a7  */
    /* JADX WARN: Code duplicated, block: B:192:0x05b3  */
    /* JADX WARN: Code duplicated, block: B:195:0x05ba A[LOOP:11: B:193:0x05b4->B:195:0x05ba, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:197:0x05c3  */
    /* JADX WARN: Code duplicated, block: B:199:0x05c8  */
    /* JADX WARN: Code duplicated, block: B:202:0x05d6 A[LOOP:12: B:200:0x05d2->B:202:0x05d6, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:206:0x05e9 A[LOOP:13: B:204:0x05e3->B:206:0x05e9, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:209:0x0601  */
    /* JADX WARN: Code duplicated, block: B:212:0x0612 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:213:0x0614  */
    /* JADX WARN: Code duplicated, block: B:215:0x0626  */
    /* JADX WARN: Code duplicated, block: B:218:0x0693 A[LOOP:15: B:216:0x068f->B:218:0x0693, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:221:0x06b0  */
    /* JADX WARN: Code duplicated, block: B:222:0x06c4  */
    /* JADX WARN: Code duplicated, block: B:223:0x06c6  */
    /* JADX WARN: Code duplicated, block: B:227:0x06dc  */
    /* JADX WARN: Code duplicated, block: B:229:0x06f7  */
    /* JADX WARN: Code duplicated, block: B:255:0x04b5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:256:0x04a8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:257:0x04d0 A[EDGE_INSN: B:257:0x04d0->B:174:0x04d0 BREAK  A[LOOP:9: B:166:0x04a4->B:258:0x04a4], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:261:0x056f A[EDGE_INSN: B:261:0x056f->B:183:0x056f BREAK  A[LOOP:10: B:180:0x055f->B:182:0x0563], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:262:0x05bd A[EDGE_INSN: B:262:0x05bd->B:196:0x05bd BREAK  A[LOOP:11: B:193:0x05b4->B:195:0x05ba], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:265:0x06fc A[EDGE_INSN: B:265:0x06fc->B:230:0x06fc BREAK  A[LOOP:14: B:207:0x05ec->B:228:0x06eb], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:267:0x06eb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:268:0x069f A[EDGE_INSN: B:268:0x069f->B:219:0x069f BREAK  A[LOOP:15: B:216:0x068f->B:218:0x0693], SYNTHETIC] */
    int GetOptimum(int i) throws IOException {
        int iReadMatchDistances;
        int i2;
        int iStateUpdateChar;
        int i3;
        int i4;
        int iStateUpdateMatch;
        int iStateUpdateChar2;
        boolean z;
        boolean z2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        char c;
        int iGetPrice0;
        int i14;
        int i15;
        int iGetPosLenPrice;
        int i16;
        Optimal optimal;
        int i17;
        int i18;
        int i19;
        int i20;
        int iGetMatchLen;
        int i21;
        int i22;
        int iGetRepPrice;
        Optimal optimal2;
        int i23;
        int[] iArr;
        int iGetMatchLen2;
        int i24;
        int i25;
        int iGetRepPrice2;
        Optimal optimal3;
        int iGetMatchLen3;
        int i26;
        int i27;
        int iGetRepPrice3;
        Optimal optimal4;
        int iStateUpdateMatch2;
        int iGetRepLen1Price;
        int i28 = i;
        int i29 = this._optimumEndIndex;
        int i30 = this._optimumCurrentIndex;
        if (i29 != i30) {
            int i31 = this._optimum[i30].PosPrev;
            int i32 = this._optimumCurrentIndex;
            int i33 = i31 - i32;
            this.backRes = this._optimum[i32].BackPrev;
            this._optimumCurrentIndex = this._optimum[this._optimumCurrentIndex].PosPrev;
            return i33;
        }
        char c2 = 0;
        this._optimumEndIndex = 0;
        this._optimumCurrentIndex = 0;
        if (!this._longestMatchWasFound) {
            iReadMatchDistances = ReadMatchDistances();
        } else {
            iReadMatchDistances = this._longestMatchLength;
            this._longestMatchWasFound = false;
        }
        int i34 = this._numDistancePairs;
        int i35 = 2;
        if (this._matchFinder.GetNumAvailableBytes() + 1 < 2) {
            this.backRes = -1;
            return 1;
        }
        int i36 = 0;
        int i37 = 0;
        while (true) {
            if (i36 >= 4) {
                break;
            }
            int[] iArr2 = this.reps;
            int i38 = this._repDistances[i36];
            iArr2[i36] = i38;
            this.repLens[i36] = this._matchFinder.GetMatchLen(-1, i38, 273);
            int[] iArr3 = this.repLens;
            if (iArr3[i36] > iArr3[i37]) {
                i37 = i36;
            }
            i36++;
        }
        int i39 = this.repLens[i37];
        int i40 = this._numFastBytes;
        if (i39 >= i40) {
            this.backRes = i37;
            MovePos(i39 - 1);
            return i39;
        }
        if (iReadMatchDistances >= i40) {
            this.backRes = this._matchDistances[i34 - 1] + 4;
            MovePos(iReadMatchDistances - 1);
            return iReadMatchDistances;
        }
        byte bGetIndexByte = this._matchFinder.GetIndexByte(-1);
        byte bGetIndexByte2 = this._matchFinder.GetIndexByte((-2) - this._repDistances[0]);
        if (iReadMatchDistances < 2 && bGetIndexByte != bGetIndexByte2 && this.repLens[i37] < 2) {
            this.backRes = -1;
            return 1;
        }
        this._optimum[0].State = this._state;
        int i41 = this._posStateMask & i28;
        this._optimum[1].Price = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isMatch[(this._state << 4) + i41]) + this._literalEncoder.GetSubCoder(i28, this._previousByte).GetPrice(!Base.StateIsCharState(this._state), bGetIndexByte2, bGetIndexByte);
        this._optimum[1].MakeAsChar();
        int iGetPrice1 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[(this._state << 4) + i41]);
        int iGetPrice2 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[this._state]) + iGetPrice1;
        if (bGetIndexByte2 == bGetIndexByte && (iGetRepLen1Price = GetRepLen1Price(this._state, i41) + iGetPrice2) < this._optimum[1].Price) {
            this._optimum[1].Price = iGetRepLen1Price;
            this._optimum[1].MakeAsShortRep();
        }
        int i42 = this.repLens[i37];
        if (iReadMatchDistances >= i42) {
            i42 = iReadMatchDistances;
        }
        if (i42 < 2) {
            this.backRes = this._optimum[1].BackPrev;
            return 1;
        }
        this._optimum[1].PosPrev = 0;
        this._optimum[0].Backs0 = this.reps[0];
        this._optimum[0].Backs1 = this.reps[1];
        this._optimum[0].Backs2 = this.reps[2];
        this._optimum[0].Backs3 = this.reps[3];
        int i43 = i42;
        while (true) {
            int i44 = i43 - 1;
            this._optimum[i43].Price = 268435455;
            if (i44 < 2) {
                break;
            }
            i43 = i44;
        }
        int i45 = 0;
        for (i2 = 4; i45 < i2; i2 = 4) {
            int i46 = this.repLens[i45];
            if (i46 >= 2) {
                int iGetPureRepPrice = GetPureRepPrice(i45, this._state, i41) + iGetPrice2;
                do {
                    int iGetPrice = this._repMatchLenEncoder.GetPrice(i46 - 2, i41) + iGetPureRepPrice;
                    Optimal optimal5 = this._optimum[i46];
                    if (iGetPrice < optimal5.Price) {
                        optimal5.Price = iGetPrice;
                        optimal5.PosPrev = 0;
                        optimal5.BackPrev = i45;
                        optimal5.Prev1IsChar = false;
                    }
                    i46--;
                } while (i46 >= 2);
            }
            i45++;
        }
        int iGetPrice3 = iGetPrice1 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRep[this._state]);
        int i47 = this.repLens[0];
        int i48 = i47 >= 2 ? i47 + 1 : 2;
        if (i48 <= iReadMatchDistances) {
            int i49 = 0;
            while (i48 > this._matchDistances[i49]) {
                i49 += 2;
            }
            while (true) {
                int i50 = this._matchDistances[i49 + 1];
                int iGetPosLenPrice2 = GetPosLenPrice(i50, i48, i41) + iGetPrice3;
                Optimal optimal6 = this._optimum[i48];
                if (iGetPosLenPrice2 < optimal6.Price) {
                    optimal6.Price = iGetPosLenPrice2;
                    optimal6.PosPrev = 0;
                    optimal6.BackPrev = i50 + 4;
                    optimal6.Prev1IsChar = false;
                }
                if (i48 == this._matchDistances[i49] && (i49 = i49 + 2) == i34) {
                    break;
                }
                i48++;
            }
        }
        int i51 = 0;
        while (true) {
            i51++;
            if (i51 == i42) {
                return Backward(i51);
            }
            int iReadMatchDistances2 = ReadMatchDistances();
            int i52 = this._numDistancePairs;
            if (iReadMatchDistances2 >= this._numFastBytes) {
                this._longestMatchLength = iReadMatchDistances2;
                this._longestMatchWasFound = true;
                return Backward(i51);
            }
            int i53 = i28 + 1;
            int i54 = this._optimum[i51].PosPrev;
            if (this._optimum[i51].Prev1IsChar) {
                i54--;
                if (this._optimum[i51].Prev2) {
                    Optimal[] optimalArr = this._optimum;
                    int i55 = optimalArr[optimalArr[i51].PosPrev2].State;
                    if (this._optimum[i51].BackPrev2 < 4) {
                        iStateUpdateMatch2 = Base.StateUpdateRep(i55);
                    } else {
                        iStateUpdateMatch2 = Base.StateUpdateMatch(i55);
                    }
                } else {
                    iStateUpdateMatch2 = this._optimum[i54].State;
                }
                iStateUpdateChar = Base.StateUpdateChar(iStateUpdateMatch2);
            } else {
                iStateUpdateChar = this._optimum[i54].State;
            }
            if (i54 == i51) {
                if (this._optimum[i51].IsShortRep()) {
                    iStateUpdateChar2 = Base.StateUpdateShortRep(iStateUpdateChar);
                } else {
                    iStateUpdateChar2 = Base.StateUpdateChar(iStateUpdateChar);
                }
            } else {
                if (this._optimum[i51].Prev1IsChar && this._optimum[i51].Prev2) {
                    i54 = this._optimum[i51].PosPrev2;
                    i3 = this._optimum[i51].BackPrev2;
                    iStateUpdateMatch = Base.StateUpdateRep(iStateUpdateChar);
                    i4 = 4;
                } else {
                    i3 = this._optimum[i51].BackPrev;
                    i4 = 4;
                    if (i3 < 4) {
                        iStateUpdateMatch = Base.StateUpdateRep(iStateUpdateChar);
                    } else {
                        iStateUpdateMatch = Base.StateUpdateMatch(iStateUpdateChar);
                    }
                }
                Optimal optimal7 = this._optimum[i54];
                if (i3 >= i4) {
                    int[] iArr4 = this.reps;
                    iArr4[c2] = i3 - 4;
                    iArr4[1] = optimal7.Backs0;
                    this.reps[i35] = optimal7.Backs1;
                    this.reps[3] = optimal7.Backs2;
                } else if (i3 == 0) {
                    this.reps[c2] = optimal7.Backs0;
                    this.reps[1] = optimal7.Backs1;
                    this.reps[i35] = optimal7.Backs2;
                    this.reps[3] = optimal7.Backs3;
                } else if (i3 == 1) {
                    this.reps[c2] = optimal7.Backs1;
                    this.reps[1] = optimal7.Backs0;
                    this.reps[i35] = optimal7.Backs2;
                    this.reps[3] = optimal7.Backs3;
                } else if (i3 == i35) {
                    this.reps[c2] = optimal7.Backs2;
                    this.reps[1] = optimal7.Backs0;
                    this.reps[i35] = optimal7.Backs1;
                    this.reps[3] = optimal7.Backs3;
                } else {
                    this.reps[c2] = optimal7.Backs3;
                    this.reps[1] = optimal7.Backs0;
                    this.reps[i35] = optimal7.Backs1;
                    this.reps[3] = optimal7.Backs2;
                }
                iStateUpdateChar2 = iStateUpdateMatch;
            }
            this._optimum[i51].State = iStateUpdateChar2;
            this._optimum[i51].Backs0 = this.reps[c2];
            this._optimum[i51].Backs1 = this.reps[1];
            this._optimum[i51].Backs2 = this.reps[i35];
            this._optimum[i51].Backs3 = this.reps[3];
            int i56 = this._optimum[i51].Price;
            byte bGetIndexByte3 = this._matchFinder.GetIndexByte(-1);
            byte bGetIndexByte4 = this._matchFinder.GetIndexByte((-2) - this.reps[c2]);
            int i57 = this._posStateMask & i53;
            int i58 = (iStateUpdateChar2 << 4) + i57;
            int i59 = i42;
            int iGetPrice4 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isMatch[i58]) + i56 + this._literalEncoder.GetSubCoder(i53, this._matchFinder.GetIndexByte(-2)).GetPrice(!Base.StateIsCharState(iStateUpdateChar2), bGetIndexByte4, bGetIndexByte3);
            int i60 = i51 + 2;
            Optimal optimal8 = this._optimum[i60];
            if (iGetPrice4 < optimal8.Price) {
                optimal8.Price = iGetPrice4;
                optimal8.PosPrev = i51;
                optimal8.MakeAsChar();
                z = true;
            } else {
                z = false;
            }
            int iGetPrice5 = i56 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[i58]);
            int iGetPrice6 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[iStateUpdateChar2]) + iGetPrice5;
            if (bGetIndexByte4 != bGetIndexByte3 || (optimal8.PosPrev < i51 && optimal8.BackPrev == 0)) {
                z2 = z;
            } else {
                int iGetRepLen1Price2 = GetRepLen1Price(iStateUpdateChar2, i57) + iGetPrice6;
                z2 = z;
                if (iGetRepLen1Price2 <= optimal8.Price) {
                    optimal8.Price = iGetRepLen1Price2;
                    optimal8.PosPrev = i51;
                    optimal8.MakeAsShortRep();
                    z2 = true;
                }
            }
            int iMin = Math.min(4095 - i51, this._matchFinder.GetNumAvailableBytes() + 1);
            if (iMin < 2) {
                i42 = i59;
                i35 = 2;
                i28 = i53;
                c2 = 0;
            } else {
                int i61 = this._numFastBytes;
                int i62 = iMin > i61 ? i61 : iMin;
                if (z2 || bGetIndexByte4 == bGetIndexByte3) {
                    i5 = iGetPrice5;
                } else {
                    i5 = iGetPrice5;
                    int iGetMatchLen4 = this._matchFinder.GetMatchLen(0, this.reps[0], Math.min(iMin - 1, i61));
                    if (iGetMatchLen4 >= 2) {
                        int iStateUpdateChar3 = Base.StateUpdateChar(iStateUpdateChar2);
                        int i63 = (i28 + 2) & this._posStateMask;
                        int iGetPrice7 = iGetPrice4 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[(iStateUpdateChar3 << 4) + i63]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[iStateUpdateChar3]);
                        int i64 = i60 + iGetMatchLen4;
                        i6 = i59;
                        while (i6 < i64) {
                            int i65 = i6 + 1;
                            this._optimum[i65].Price = 268435455;
                            i6 = i65;
                            iReadMatchDistances2 = iReadMatchDistances2;
                        }
                        iReadMatchDistances2 = iReadMatchDistances2;
                        int iGetRepPrice4 = iGetPrice7 + GetRepPrice(0, iGetMatchLen4, iStateUpdateChar3, i63);
                        Optimal optimal9 = this._optimum[i64];
                        if (iGetRepPrice4 < optimal9.Price) {
                            optimal9.Price = iGetRepPrice4;
                            optimal9.PosPrev = i60;
                            optimal9.BackPrev = 0;
                            optimal9.Prev1IsChar = true;
                            optimal9.Prev2 = false;
                        }
                    }
                    i7 = 0;
                    i9 = 2;
                    for (i8 = 4; i7 < i8; i8 = 4) {
                        iGetMatchLen2 = this._matchFinder.GetMatchLen(-1, this.reps[i7], i62);
                        if (iGetMatchLen2 < 2) {
                            i24 = iGetMatchLen2;
                            while (true) {
                                i25 = i51 + i24;
                                if (i6 < i25) {
                                    i6++;
                                    this._optimum[i6].Price = 268435455;
                                } else {
                                    iGetRepPrice2 = GetRepPrice(i7, i24, iStateUpdateChar2, i57) + iGetPrice6;
                                    optimal3 = this._optimum[i25];
                                    if (iGetRepPrice2 < optimal3.Price) {
                                        optimal3.Price = iGetRepPrice2;
                                        optimal3.PosPrev = i51;
                                        optimal3.BackPrev = i7;
                                        optimal3.Prev1IsChar = false;
                                    }
                                    i24--;
                                    if (i24 < 2) {
                                        break;
                                    }
                                }
                            }
                            if (i7 == 0) {
                                i9 = iGetMatchLen2 + 1;
                            }
                            if (iGetMatchLen2 < iMin) {
                                iGetMatchLen3 = this._matchFinder.GetMatchLen(iGetMatchLen2, this.reps[i7], Math.min((iMin - 1) - iGetMatchLen2, this._numFastBytes));
                                if (iGetMatchLen3 >= 2) {
                                    int iStateUpdateRep = Base.StateUpdateRep(iStateUpdateChar2);
                                    int i66 = i53 + iGetMatchLen2;
                                    int i67 = i9;
                                    int i68 = iGetMatchLen2 - 1;
                                    int iGetRepPrice5 = iGetPrice6 + GetRepPrice(i7, iGetMatchLen2, iStateUpdateChar2, i57) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isMatch[(iStateUpdateRep << 4) + (this._posStateMask & i66)]) + this._literalEncoder.GetSubCoder(i66, this._matchFinder.GetIndexByte(iGetMatchLen2 - 2)).GetPrice(true, this._matchFinder.GetIndexByte(i68 - (this.reps[i7] + 1)), this._matchFinder.GetIndexByte(i68));
                                    int iStateUpdateChar4 = Base.StateUpdateChar(iStateUpdateRep);
                                    int i69 = this._posStateMask & (i66 + 1);
                                    int iGetPrice8 = iGetRepPrice5 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[(iStateUpdateChar4 << 4) + i69]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[iStateUpdateChar4]);
                                    i26 = iGetMatchLen2 + 1 + iGetMatchLen3;
                                    i6 = i6;
                                    while (true) {
                                        i27 = i51 + i26;
                                        if (i6 < i27) {
                                            break;
                                        }
                                        i6++;
                                        this._optimum[i6].Price = 268435455;
                                    }
                                    iGetRepPrice3 = iGetPrice8 + GetRepPrice(0, iGetMatchLen3, iStateUpdateChar4, i69);
                                    optimal4 = this._optimum[i27];
                                    if (iGetRepPrice3 < optimal4.Price) {
                                        optimal4.Price = iGetRepPrice3;
                                        optimal4.PosPrev = iGetMatchLen2 + i51 + 1;
                                        optimal4.BackPrev = 0;
                                        optimal4.Prev1IsChar = true;
                                        optimal4.Prev2 = true;
                                        optimal4.PosPrev2 = i51;
                                        optimal4.BackPrev2 = i7;
                                    }
                                    i9 = i67;
                                } else {
                                    i9 = i9;
                                    i6 = i6;
                                }
                            } else {
                                i9 = i9;
                                i6 = i6;
                            }
                        }
                        i7++;
                        iGetPrice6 = iGetPrice6;
                        i53 = i53;
                    }
                    i10 = i53;
                    i11 = iReadMatchDistances2;
                    if (i11 > i62) {
                        i23 = 0;
                        while (true) {
                            iArr = this._matchDistances;
                            if (i62 > iArr[i23]) {
                                break;
                            }
                            i23 += 2;
                        }
                        iArr[i23] = i62;
                        i13 = i23 + 2;
                        i12 = i62;
                    } else {
                        i12 = i11;
                        i13 = i52;
                    }
                    if (i12 >= i9) {
                        iGetPrice0 = i5 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRep[iStateUpdateChar2]);
                        while (i6 < i51 + i12) {
                            i6++;
                            this._optimum[i6].Price = 268435455;
                        }
                        i14 = 0;
                        while (i9 > this._matchDistances[i14]) {
                            i14 += 2;
                        }
                        while (true) {
                            i15 = this._matchDistances[i14 + 1];
                            iGetPosLenPrice = GetPosLenPrice(i15, i9, i57) + iGetPrice0;
                            i16 = i51 + i9;
                            optimal = this._optimum[i16];
                            if (iGetPosLenPrice < optimal.Price) {
                                optimal.Price = iGetPosLenPrice;
                                optimal.PosPrev = i51;
                                optimal.BackPrev = i15 + 4;
                                optimal.Prev1IsChar = false;
                            }
                            if (i9 == this._matchDistances[i14]) {
                                if (i9 < iMin) {
                                    iGetMatchLen = this._matchFinder.GetMatchLen(i9, i15, Math.min((iMin - 1) - i9, this._numFastBytes));
                                    if (iGetMatchLen >= 2) {
                                        int iStateUpdateMatch3 = Base.StateUpdateMatch(iStateUpdateChar2);
                                        int i70 = i10 + i9;
                                        i17 = iMin;
                                        i18 = iStateUpdateChar2;
                                        i19 = iGetPrice0;
                                        i20 = i57;
                                        int iGetPrice9 = iGetPosLenPrice + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isMatch[(iStateUpdateMatch3 << 4) + (this._posStateMask & i70)]) + this._literalEncoder.GetSubCoder(i70, this._matchFinder.GetIndexByte(i9 - 2)).GetPrice(true, this._matchFinder.GetIndexByte((i9 - (i15 + 1)) - 1), this._matchFinder.GetIndexByte(i9 - 1));
                                        int iStateUpdateChar5 = Base.StateUpdateChar(iStateUpdateMatch3);
                                        int i71 = this._posStateMask & (i70 + 1);
                                        int iGetPrice10 = iGetPrice9 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[(iStateUpdateChar5 << 4) + i71]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[iStateUpdateChar5]);
                                        i21 = i9 + 1 + iGetMatchLen;
                                        i6 = i6;
                                        while (true) {
                                            i22 = i51 + i21;
                                            if (i6 < i22) {
                                                break;
                                            }
                                            i6++;
                                            this._optimum[i6].Price = 268435455;
                                        }
                                        c = 0;
                                        iGetRepPrice = iGetPrice10 + GetRepPrice(0, iGetMatchLen, iStateUpdateChar5, i71);
                                        optimal2 = this._optimum[i22];
                                        if (iGetRepPrice < optimal2.Price) {
                                            optimal2.Price = iGetRepPrice;
                                            optimal2.PosPrev = i16 + 1;
                                            optimal2.BackPrev = 0;
                                            optimal2.Prev1IsChar = true;
                                            optimal2.Prev2 = true;
                                            optimal2.PosPrev2 = i51;
                                            optimal2.BackPrev2 = i15 + 4;
                                        }
                                    } else {
                                        i17 = iMin;
                                        i18 = iStateUpdateChar2;
                                        i19 = iGetPrice0;
                                        i20 = i57;
                                        c = 0;
                                        i6 = i6;
                                    }
                                } else {
                                    i17 = iMin;
                                    i18 = iStateUpdateChar2;
                                    i19 = iGetPrice0;
                                    i20 = i57;
                                    c = 0;
                                    i6 = i6;
                                }
                                i14 += 2;
                                if (i14 == i13) {
                                    break;
                                }
                            } else {
                                i17 = iMin;
                                i18 = iStateUpdateChar2;
                                i19 = iGetPrice0;
                                i20 = i57;
                            }
                            i9++;
                            iMin = i17;
                            iStateUpdateChar2 = i18;
                            iGetPrice0 = i19;
                            i57 = i20;
                        }
                    } else {
                        c = 0;
                    }
                    i42 = i6;
                    c2 = c;
                    i28 = i10;
                    i35 = 2;
                }
                i6 = i59;
                i7 = 0;
                i9 = 2;
                while (i7 < i8) {
                    iGetMatchLen2 = this._matchFinder.GetMatchLen(-1, this.reps[i7], i62);
                    if (iGetMatchLen2 < 2) {
                        i24 = iGetMatchLen2;
                        while (true) {
                            i25 = i51 + i24;
                            if (i6 < i25) {
                                i6++;
                                this._optimum[i6].Price = 268435455;
                            } else {
                                iGetRepPrice2 = GetRepPrice(i7, i24, iStateUpdateChar2, i57) + iGetPrice6;
                                optimal3 = this._optimum[i25];
                                if (iGetRepPrice2 < optimal3.Price) {
                                    optimal3.Price = iGetRepPrice2;
                                    optimal3.PosPrev = i51;
                                    optimal3.BackPrev = i7;
                                    optimal3.Prev1IsChar = false;
                                }
                                i24--;
                                if (i24 < 2) {
                                    break;
                                    break;
                                }
                            }
                        }
                        if (i7 == 0) {
                            i9 = iGetMatchLen2 + 1;
                        }
                        if (iGetMatchLen2 < iMin) {
                            iGetMatchLen3 = this._matchFinder.GetMatchLen(iGetMatchLen2, this.reps[i7], Math.min((iMin - 1) - iGetMatchLen2, this._numFastBytes));
                            if (iGetMatchLen3 >= 2) {
                                int iStateUpdateRep2 = Base.StateUpdateRep(iStateUpdateChar2);
                                int i610 = i53 + iGetMatchLen2;
                                int i611 = i9;
                                int i612 = iGetMatchLen2 - 1;
                                int iGetRepPrice6 = iGetPrice6 + GetRepPrice(i7, iGetMatchLen2, iStateUpdateChar2, i57) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isMatch[(iStateUpdateRep2 << 4) + (this._posStateMask & i610)]) + this._literalEncoder.GetSubCoder(i610, this._matchFinder.GetIndexByte(iGetMatchLen2 - 2)).GetPrice(true, this._matchFinder.GetIndexByte(i612 - (this.reps[i7] + 1)), this._matchFinder.GetIndexByte(i612));
                                int iStateUpdateChar6 = Base.StateUpdateChar(iStateUpdateRep2);
                                int i613 = this._posStateMask & (i610 + 1);
                                int iGetPrice11 = iGetRepPrice6 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[(iStateUpdateChar6 << 4) + i613]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[iStateUpdateChar6]);
                                i26 = iGetMatchLen2 + 1 + iGetMatchLen3;
                                i6 = i6;
                                while (true) {
                                    i27 = i51 + i26;
                                    if (i6 < i27) {
                                        break;
                                        break;
                                    }
                                    i6++;
                                    this._optimum[i6].Price = 268435455;
                                }
                                iGetRepPrice3 = iGetPrice11 + GetRepPrice(0, iGetMatchLen3, iStateUpdateChar6, i613);
                                optimal4 = this._optimum[i27];
                                if (iGetRepPrice3 < optimal4.Price) {
                                    optimal4.Price = iGetRepPrice3;
                                    optimal4.PosPrev = iGetMatchLen2 + i51 + 1;
                                    optimal4.BackPrev = 0;
                                    optimal4.Prev1IsChar = true;
                                    optimal4.Prev2 = true;
                                    optimal4.PosPrev2 = i51;
                                    optimal4.BackPrev2 = i7;
                                }
                                i9 = i611;
                            } else {
                                i9 = i9;
                                i6 = i6;
                            }
                        } else {
                            i9 = i9;
                            i6 = i6;
                        }
                    }
                    i7++;
                    iGetPrice6 = iGetPrice6;
                    i53 = i53;
                }
                i10 = i53;
                i11 = iReadMatchDistances2;
                if (i11 > i62) {
                    i23 = 0;
                    while (true) {
                        iArr = this._matchDistances;
                        if (i62 > iArr[i23]) {
                            break;
                            break;
                        }
                        i23 += 2;
                    }
                    iArr[i23] = i62;
                    i13 = i23 + 2;
                    i12 = i62;
                } else {
                    i12 = i11;
                    i13 = i52;
                }
                if (i12 >= i9) {
                    iGetPrice0 = i5 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRep[iStateUpdateChar2]);
                    while (i6 < i51 + i12) {
                        i6++;
                        this._optimum[i6].Price = 268435455;
                    }
                    i14 = 0;
                    while (i9 > this._matchDistances[i14]) {
                        i14 += 2;
                    }
                    while (true) {
                        i15 = this._matchDistances[i14 + 1];
                        iGetPosLenPrice = GetPosLenPrice(i15, i9, i57) + iGetPrice0;
                        i16 = i51 + i9;
                        optimal = this._optimum[i16];
                        if (iGetPosLenPrice < optimal.Price) {
                            optimal.Price = iGetPosLenPrice;
                            optimal.PosPrev = i51;
                            optimal.BackPrev = i15 + 4;
                            optimal.Prev1IsChar = false;
                        }
                        if (i9 == this._matchDistances[i14]) {
                            if (i9 < iMin) {
                                iGetMatchLen = this._matchFinder.GetMatchLen(i9, i15, Math.min((iMin - 1) - i9, this._numFastBytes));
                                if (iGetMatchLen >= 2) {
                                    int iStateUpdateMatch4 = Base.StateUpdateMatch(iStateUpdateChar2);
                                    int i72 = i10 + i9;
                                    i17 = iMin;
                                    i18 = iStateUpdateChar2;
                                    i19 = iGetPrice0;
                                    i20 = i57;
                                    int iGetPrice12 = iGetPosLenPrice + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isMatch[(iStateUpdateMatch4 << 4) + (this._posStateMask & i72)]) + this._literalEncoder.GetSubCoder(i72, this._matchFinder.GetIndexByte(i9 - 2)).GetPrice(true, this._matchFinder.GetIndexByte((i9 - (i15 + 1)) - 1), this._matchFinder.GetIndexByte(i9 - 1));
                                    int iStateUpdateChar7 = Base.StateUpdateChar(iStateUpdateMatch4);
                                    int i73 = this._posStateMask & (i72 + 1);
                                    int iGetPrice13 = iGetPrice12 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[(iStateUpdateChar7 << 4) + i73]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[iStateUpdateChar7]);
                                    i21 = i9 + 1 + iGetMatchLen;
                                    i6 = i6;
                                    while (true) {
                                        i22 = i51 + i21;
                                        if (i6 < i22) {
                                            break;
                                            break;
                                        }
                                        i6++;
                                        this._optimum[i6].Price = 268435455;
                                    }
                                    c = 0;
                                    iGetRepPrice = iGetPrice13 + GetRepPrice(0, iGetMatchLen, iStateUpdateChar7, i73);
                                    optimal2 = this._optimum[i22];
                                    if (iGetRepPrice < optimal2.Price) {
                                        optimal2.Price = iGetRepPrice;
                                        optimal2.PosPrev = i16 + 1;
                                        optimal2.BackPrev = 0;
                                        optimal2.Prev1IsChar = true;
                                        optimal2.Prev2 = true;
                                        optimal2.PosPrev2 = i51;
                                        optimal2.BackPrev2 = i15 + 4;
                                    }
                                } else {
                                    i17 = iMin;
                                    i18 = iStateUpdateChar2;
                                    i19 = iGetPrice0;
                                    i20 = i57;
                                    c = 0;
                                    i6 = i6;
                                }
                            } else {
                                i17 = iMin;
                                i18 = iStateUpdateChar2;
                                i19 = iGetPrice0;
                                i20 = i57;
                                c = 0;
                                i6 = i6;
                            }
                            i14 += 2;
                            if (i14 == i13) {
                                break;
                                break;
                            }
                        } else {
                            i17 = iMin;
                            i18 = iStateUpdateChar2;
                            i19 = iGetPrice0;
                            i20 = i57;
                        }
                        i9++;
                        iMin = i17;
                        iStateUpdateChar2 = i18;
                        iGetPrice0 = i19;
                        i57 = i20;
                    }
                } else {
                    c = 0;
                }
                i42 = i6;
                c2 = c;
                i28 = i10;
                i35 = 2;
            }
        }
    }

    void WriteEndMarker(int i) throws IOException {
        if (this._writeEndMark) {
            this._rangeEncoder.Encode(this._isMatch, (this._state << 4) + i, 1);
            this._rangeEncoder.Encode(this._isRep, this._state, 0);
            this._state = Base.StateUpdateMatch(this._state);
            this._lenEncoder.Encode(this._rangeEncoder, 0, i);
            this._posSlotEncoder[Base.GetLenToPosState(2)].Encode(this._rangeEncoder, 63);
            this._rangeEncoder.EncodeDirectBits(Lua.MAXARG_Ax, 26);
            this._posAlignEncoder.ReverseEncode(this._rangeEncoder, 15);
        }
    }

    void Flush(int i) throws IOException {
        ReleaseMFStream();
        WriteEndMarker(i & this._posStateMask);
        this._rangeEncoder.FlushData();
        this._rangeEncoder.FlushStream();
    }

    public void CodeOneBlock(long[] jArr, long[] jArr2, boolean[] zArr) throws IOException {
        jArr[0] = 0;
        jArr2[0] = 0;
        zArr[0] = true;
        InputStream inputStream = this._inStream;
        if (inputStream != null) {
            this._matchFinder.SetStream(inputStream);
            this._matchFinder.Init();
            this._needReleaseMFStream = true;
            this._inStream = null;
        }
        if (this._finished) {
            return;
        }
        this._finished = true;
        long j = this.nowPos64;
        if (j == 0) {
            if (this._matchFinder.GetNumAvailableBytes() == 0) {
                Flush((int) this.nowPos64);
                return;
            }
            ReadMatchDistances();
            this._rangeEncoder.Encode(this._isMatch, (this._state << 4) + (((int) this.nowPos64) & this._posStateMask), 0);
            this._state = Base.StateUpdateChar(this._state);
            byte bGetIndexByte = this._matchFinder.GetIndexByte(0 - this._additionalOffset);
            this._literalEncoder.GetSubCoder((int) this.nowPos64, this._previousByte).Encode(this._rangeEncoder, bGetIndexByte);
            this._previousByte = bGetIndexByte;
            this._additionalOffset--;
            this.nowPos64++;
        }
        if (this._matchFinder.GetNumAvailableBytes() == 0) {
            Flush((int) this.nowPos64);
            return;
        }
        while (true) {
            int iGetOptimum = GetOptimum((int) this.nowPos64);
            int i = this.backRes;
            int i2 = ((int) this.nowPos64) & this._posStateMask;
            int i3 = (this._state << 4) + i2;
            if (iGetOptimum == 1 && i == -1) {
                this._rangeEncoder.Encode(this._isMatch, i3, 0);
                byte bGetIndexByte2 = this._matchFinder.GetIndexByte(0 - this._additionalOffset);
                LiteralEncoder.Encoder2 encoder2GetSubCoder = this._literalEncoder.GetSubCoder((int) this.nowPos64, this._previousByte);
                if (!Base.StateIsCharState(this._state)) {
                    encoder2GetSubCoder.EncodeMatched(this._rangeEncoder, this._matchFinder.GetIndexByte(((-1) - this._repDistances[0]) - this._additionalOffset), bGetIndexByte2);
                } else {
                    encoder2GetSubCoder.Encode(this._rangeEncoder, bGetIndexByte2);
                }
                this._previousByte = bGetIndexByte2;
                this._state = Base.StateUpdateChar(this._state);
            } else {
                this._rangeEncoder.Encode(this._isMatch, i3, 1);
                if (i < 4) {
                    this._rangeEncoder.Encode(this._isRep, this._state, 1);
                    if (i == 0) {
                        this._rangeEncoder.Encode(this._isRepG0, this._state, 0);
                        if (iGetOptimum == 1) {
                            this._rangeEncoder.Encode(this._isRep0Long, i3, 0);
                        } else {
                            this._rangeEncoder.Encode(this._isRep0Long, i3, 1);
                        }
                    } else {
                        this._rangeEncoder.Encode(this._isRepG0, this._state, 1);
                        if (i == 1) {
                            this._rangeEncoder.Encode(this._isRepG1, this._state, 0);
                        } else {
                            this._rangeEncoder.Encode(this._isRepG1, this._state, 1);
                            this._rangeEncoder.Encode(this._isRepG2, this._state, i - 2);
                        }
                    }
                    if (iGetOptimum == 1) {
                        this._state = Base.StateUpdateShortRep(this._state);
                    } else {
                        this._repMatchLenEncoder.Encode(this._rangeEncoder, iGetOptimum - 2, i2);
                        this._state = Base.StateUpdateRep(this._state);
                    }
                    int i4 = this._repDistances[i];
                    if (i != 0) {
                        while (i >= 1) {
                            int[] iArr = this._repDistances;
                            iArr[i] = iArr[i - 1];
                            i--;
                        }
                        this._repDistances[0] = i4;
                    }
                } else {
                    this._rangeEncoder.Encode(this._isRep, this._state, 0);
                    this._state = Base.StateUpdateMatch(this._state);
                    this._lenEncoder.Encode(this._rangeEncoder, iGetOptimum - 2, i2);
                    int i5 = i - 4;
                    int iGetPosSlot = GetPosSlot(i5);
                    this._posSlotEncoder[Base.GetLenToPosState(iGetOptimum)].Encode(this._rangeEncoder, iGetPosSlot);
                    if (iGetPosSlot >= 4) {
                        int i6 = iGetPosSlot >> 1;
                        int i7 = i6 - 1;
                        int i8 = ((iGetPosSlot & 1) | 2) << i7;
                        int i9 = i5 - i8;
                        if (iGetPosSlot < 14) {
                            BitTreeEncoder.ReverseEncode(this._posEncoders, (i8 - iGetPosSlot) - 1, this._rangeEncoder, i7, i9);
                        } else {
                            this._rangeEncoder.EncodeDirectBits(i9 >> 4, i6 - 5);
                            this._posAlignEncoder.ReverseEncode(this._rangeEncoder, i9 & 15);
                            this._alignPriceCount++;
                        }
                    }
                    for (int i10 = 3; i10 >= 1; i10--) {
                        int[] iArr2 = this._repDistances;
                        iArr2[i10] = iArr2[i10 - 1];
                    }
                    this._repDistances[0] = i5;
                    this._matchPriceCount++;
                }
                this._previousByte = this._matchFinder.GetIndexByte((iGetOptimum - 1) - this._additionalOffset);
            }
            int i11 = this._additionalOffset - iGetOptimum;
            this._additionalOffset = i11;
            this.nowPos64 += (long) iGetOptimum;
            if (i11 == 0) {
                if (this._matchPriceCount >= 128) {
                    FillDistancesPrices();
                }
                if (this._alignPriceCount >= 16) {
                    FillAlignPrices();
                }
                jArr[0] = this.nowPos64;
                jArr2[0] = this._rangeEncoder.GetProcessedSizeAdd();
                if (this._matchFinder.GetNumAvailableBytes() == 0) {
                    Flush((int) this.nowPos64);
                    return;
                } else if (this.nowPos64 - j >= 4096) {
                    this._finished = false;
                    zArr[0] = false;
                    return;
                }
            }
        }
    }

    void ReleaseMFStream() {
        BinTree binTree = this._matchFinder;
        if (binTree == null || !this._needReleaseMFStream) {
            return;
        }
        binTree.ReleaseStream();
        this._needReleaseMFStream = false;
    }

    void SetOutStream(OutputStream outputStream) {
        this._rangeEncoder.SetStream(outputStream);
    }

    void ReleaseOutStream() {
        this._rangeEncoder.ReleaseStream();
    }

    void ReleaseStreams() {
        ReleaseMFStream();
        ReleaseOutStream();
    }

    void SetStreams(InputStream inputStream, OutputStream outputStream, long j, long j2) {
        this._inStream = inputStream;
        this._finished = false;
        Create();
        SetOutStream(outputStream);
        Init();
        FillDistancesPrices();
        FillAlignPrices();
        this._lenEncoder.SetTableSize(this._numFastBytes - 1);
        this._lenEncoder.UpdateTables(1 << this._posStateBits);
        this._repMatchLenEncoder.SetTableSize(this._numFastBytes - 1);
        this._repMatchLenEncoder.UpdateTables(1 << this._posStateBits);
        this.nowPos64 = 0L;
    }

    public void Code(InputStream inputStream, OutputStream outputStream, long j, long j2, ICodeProgress iCodeProgress) throws IOException {
        this._needReleaseMFStream = false;
        try {
            SetStreams(inputStream, outputStream, j, j2);
            while (true) {
                CodeOneBlock(this.processedInSize, this.processedOutSize, this.finished);
                if (this.finished[0]) {
                    ReleaseStreams();
                    return;
                } else if (iCodeProgress != null) {
                    iCodeProgress.SetProgress(this.processedInSize[0], this.processedOutSize[0]);
                }
            }
        } catch (Throwable th) {
            ReleaseStreams();
            throw th;
        }
    }

    public void WriteCoderProperties(OutputStream outputStream) throws IOException {
        this.properties[0] = (byte) ((((this._posStateBits * 5) + this._numLiteralPosStateBits) * 9) + this._numLiteralContextBits);
        int i = 0;
        while (i < 4) {
            int i2 = i + 1;
            this.properties[i2] = (byte) (this._dictionarySize >> (i * 8));
            i = i2;
        }
        outputStream.write(this.properties, 0, 5);
    }

    void FillDistancesPrices() {
        for (int i = 4; i < 128; i++) {
            int iGetPosSlot = GetPosSlot(i);
            int i2 = (iGetPosSlot >> 1) - 1;
            int i3 = ((iGetPosSlot & 1) | 2) << i2;
            this.tempPrices[i] = BitTreeEncoder.ReverseGetPrice(this._posEncoders, (i3 - iGetPosSlot) - 1, i2, i - i3);
        }
        for (int i4 = 0; i4 < 4; i4++) {
            BitTreeEncoder bitTreeEncoder = this._posSlotEncoder[i4];
            int i5 = i4 << 6;
            for (int i6 = 0; i6 < this._distTableSize; i6++) {
                this._posSlotPrices[i5 + i6] = bitTreeEncoder.GetPrice(i6);
            }
            for (int i7 = 14; i7 < this._distTableSize; i7++) {
                int[] iArr = this._posSlotPrices;
                int i8 = i5 + i7;
                iArr[i8] = iArr[i8] + (((i7 >> 1) - 5) << 6);
            }
            int i9 = i4 * 128;
            int i10 = 0;
            while (i10 < 4) {
                this._distancesPrices[i9 + i10] = this._posSlotPrices[i5 + i10];
                i10++;
            }
            while (i10 < 128) {
                this._distancesPrices[i9 + i10] = this._posSlotPrices[GetPosSlot(i10) + i5] + this.tempPrices[i10];
                i10++;
            }
        }
        this._matchPriceCount = 0;
    }

    void FillAlignPrices() {
        for (int i = 0; i < 16; i++) {
            this._alignPrices[i] = this._posAlignEncoder.ReverseGetPrice(i);
        }
        this._alignPriceCount = 0;
    }

    public boolean SetDictionarySize(int i) {
        int i2 = 0;
        if (i < 1 || i > 536870912) {
            return false;
        }
        this._dictionarySize = i;
        while (i > (1 << i2)) {
            i2++;
        }
        this._distTableSize = i2 * 2;
        return true;
    }

    public boolean SetNumFastBytes(int i) {
        if (i < 5 || i > 273) {
            return false;
        }
        this._numFastBytes = i;
        return true;
    }

    public boolean SetMatchFinder(int i) {
        if (i < 0 || i > 2) {
            return false;
        }
        int i2 = this._matchFinderType;
        this._matchFinderType = i;
        if (this._matchFinder == null || i2 == i) {
            return true;
        }
        this._dictionarySizePrev = -1;
        this._matchFinder = null;
        return true;
    }

    public boolean SetLcLpPb(int i, int i2, int i3) {
        if (i2 < 0 || i2 > 4 || i < 0 || i > 8 || i3 < 0 || i3 > 4) {
            return false;
        }
        this._numLiteralPosStateBits = i2;
        this._numLiteralContextBits = i;
        this._posStateBits = i3;
        this._posStateMask = (1 << i3) - 1;
        return true;
    }

    public void SetEndMarkerMode(boolean z) {
        this._writeEndMark = z;
    }
}
