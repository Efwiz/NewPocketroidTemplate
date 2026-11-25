package com.reandroid.dex.data;

import com.reandroid.arsc.base.BlockCounter;
import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.base.OffsetSupplier;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.BlockItem;
import com.reandroid.dex.base.DexBlockItem;
import com.reandroid.dex.base.DexException;
import com.reandroid.dex.base.OffsetReceiver;
import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.io.ByteReader;
import com.reandroid.dex.io.StreamUtil;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.StringsUtil;
import java.io.IOException;
import java.io.OutputStream;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes11.dex */
public class StringData extends DataItem implements SmaliFormat, BlockRefresh, OffsetSupplier, OffsetReceiver, Comparable<StringData> {
    private static final String EMPTY_STRING = StringsUtil.EMPTY;
    private String mCache;
    private final StringDataContainer mDataContainer;
    private StringKey mKey;

    public StringData() {
        super(1);
        StringDataContainer stringDataContainer = new StringDataContainer(this);
        this.mDataContainer = stringDataContainer;
        addChild(0, stringDataContainer);
        this.mCache = EMPTY_STRING;
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem
    public void removeSelf() {
        throw new DexException("Remove STRING_ID first before STRING_DATA");
    }

    public void removeSelf(StringId stringId) {
        if (stringId == null || stringId.getParent() == null) {
            return;
        }
        super.removeSelf();
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public StringKey getKey() {
        return this.mKey;
    }

    public void setKey(Key key) {
        StringKey stringKey = (StringKey) key;
        setString(stringKey != null ? stringKey.getString() : null);
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<StringData> getSectionType() {
        return SectionType.STRING_DATA;
    }

    public String getString() {
        return this.mCache;
    }

    public void setString(String str) {
        StringKey stringKey;
        if (!this.mCache.equals(str) || this.mKey == null) {
            if (str == null || str.length() == 0) {
                str = EMPTY_STRING;
            }
            this.mCache = str;
            encodeString(str);
            if (str.length() == 0) {
                stringKey = StringKey.EMPTY;
            } else {
                stringKey = new StringKey(str);
            }
            this.mKey = stringKey;
        }
    }

    public String getQuotedString() {
        return DexUtils.quoteString(getString());
    }

    void onStringBytesChanged() {
        StringKey stringKey;
        String strDecodeString = decodeString();
        if (strDecodeString.length() == 0) {
            stringKey = StringKey.EMPTY;
        } else {
            stringKey = new StringKey(strDecodeString);
        }
        this.mKey = stringKey;
    }

    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public int countBytes() {
        return this.mDataContainer.countBytes();
    }

    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public byte[] getBytes() {
        return this.mDataContainer.getBytes();
    }

    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onCountUpTo(BlockCounter blockCounter) {
        if (blockCounter.FOUND) {
            return;
        }
        blockCounter.setCurrent(this);
        if (blockCounter.END == this) {
            blockCounter.FOUND = true;
        } else {
            blockCounter.addCount(countBytes());
        }
    }

    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        StringKey stringKey;
        if (blockReader.available() < 4) {
            return;
        }
        int position = blockReader.getPosition();
        String strDecodeString = decodeString(StreamUtil.createByteReader(blockReader));
        if (strDecodeString.length() == 0) {
            strDecodeString = EMPTY_STRING;
        }
        int position2 = blockReader.getPosition() - position;
        blockReader.seek(position);
        this.mDataContainer.setLength(position2 + 1);
        blockReader.readFully(this.mDataContainer.getBytesInternal());
        this.mCache = strDecodeString;
        if (strDecodeString.length() == 0) {
            stringKey = StringKey.EMPTY;
        } else {
            stringKey = new StringKey(strDecodeString);
        }
        this.mKey = stringKey;
    }

    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public int onWriteBytes(OutputStream outputStream) throws IOException {
        return this.mDataContainer.onWriteBytes(outputStream);
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append('\"');
        boolean zEncodeString = DexUtils.encodeString(smaliWriter, getString());
        smaliWriter.append('\"');
        if (zEncodeString && smaliWriter.isCommentUnicodeStrings()) {
            DexUtils.appendCommentString(250, smaliWriter.getCommentAppender(), getString());
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(StringData stringData) {
        if (stringData == null) {
            return -1;
        }
        if (stringData == this) {
            return 0;
        }
        return getString().compareTo(stringData.getString());
    }

    public String toString() {
        String string = getString();
        return string != null ? string : "NULL";
    }

    private String decodeString() {
        try {
            return decodeString(StreamUtil.createByteReader(this.mDataContainer.getBytesInternal()));
        } catch (IOException unused) {
            return null;
        }
    }

    private void encodeString(String str) {
        int length = str.length();
        this.mDataContainer.setLength((length * 3) + 4);
        byte[] bytesInternal = this.mDataContainer.getBytesInternal();
        int iWriteUleb128 = DexBlockItem.writeUleb128(bytesInternal, 0, length);
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt != 0 && cCharAt < 128) {
                bytesInternal[iWriteUleb128] = (byte) cCharAt;
                iWriteUleb128++;
            } else if (cCharAt < 2048) {
                int i2 = iWriteUleb128 + 1;
                bytesInternal[iWriteUleb128] = (byte) (((cCharAt >> 6) & 31) | 192);
                iWriteUleb128 += 2;
                bytesInternal[i2] = (byte) ((cCharAt & MangleConstant.Q_MARK) | 128);
            } else {
                bytesInternal[iWriteUleb128] = (byte) (((cCharAt >> '\f') & 15) | 224);
                int i3 = iWriteUleb128 + 2;
                bytesInternal[iWriteUleb128 + 1] = (byte) (((cCharAt >> 6) & 63) | 128);
                iWriteUleb128 += 3;
                bytesInternal[i3] = (byte) ((cCharAt & MangleConstant.Q_MARK) | 128);
            }
        }
        bytesInternal[iWriteUleb128] = 0;
        this.mDataContainer.setLength(iWriteUleb128 + 1);
    }

    private static String decodeString(ByteReader byteReader) throws IOException {
        char c;
        int uleb128 = DexBlockItem.readUleb128(byteReader);
        char[] cArr = new char[uleb128];
        int i = 0;
        int i2 = 0;
        while (uleb128 > 0) {
            int i3 = byteReader.read();
            switch (i3 >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    if (i3 == 0) {
                        return throwBadUtf8(i3, i2);
                    }
                    c = (char) i3;
                    i2++;
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    return throwBadUtf8(i3, i2);
                case 12:
                case 13:
                    int i4 = byteReader.read();
                    int i5 = i4 & 255;
                    if ((i4 & 192) != 128) {
                        return throwBadUtf8(i5, i2 + 1);
                    }
                    int i6 = ((i3 & 31) << 6) | (i4 & 63);
                    if (i6 != 0 && i6 < 128) {
                        return throwBadUtf8(i5, i2 + 1);
                    }
                    c = (char) i6;
                    i2 += 2;
                    break;
                    break;
                case 14:
                    int i7 = byteReader.read();
                    if ((i7 & 192) != 128) {
                        return throwBadUtf8(i7, i2 + 1);
                    }
                    int i8 = byteReader.read();
                    if ((i8 & 192) != 128) {
                        return throwBadUtf8(i8, i2 + 2);
                    }
                    int i9 = ((i3 & 15) << 12) | ((i7 & 63) << 6) | (i8 & 63);
                    if (i9 < 2048) {
                        return throwBadUtf8(i8, i2 + 2);
                    }
                    c = (char) i9;
                    i2 += 3;
                    break;
                    break;
            }
            cArr[i] = c;
            i++;
            uleb128--;
        }
        return new String(cArr, 0, i);
    }

    private static String throwBadUtf8(int i, int i2) throws IOException {
        throw new IOException("bad utf-8 byte " + HexUtil.toHex2("", (byte) i) + " at offset " + i2);
    }

    public static boolean equals(StringData stringData, StringData stringData2) {
        return CompareUtil.compare(stringData, stringData2) == 0;
    }

    static class StringDataContainer extends BlockItem {
        private final StringData stringData;

        StringDataContainer(StringData stringData) {
            super(0);
            this.stringData = stringData;
        }

        void setLength(int i) {
            setBytesLength(i, false);
        }

        @Override // com.reandroid.arsc.item.BlockItem
        public byte[] getBytesInternal() {
            return super.getBytesInternal();
        }

        @Override // com.reandroid.arsc.item.BlockItem
        protected void onBytesChanged() {
            this.stringData.onStringBytesChanged();
        }

        @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
        public int onWriteBytes(OutputStream outputStream) throws IOException {
            return super.onWriteBytes(outputStream);
        }
    }
}
