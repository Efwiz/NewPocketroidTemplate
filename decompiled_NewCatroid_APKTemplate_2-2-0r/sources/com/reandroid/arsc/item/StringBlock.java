package com.reandroid.arsc.item;

import com.reandroid.utils.StringsUtil;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes10.dex */
public abstract class StringBlock extends BlockItem implements StringReference {
    public static final CharsetDecoder UTF8_DECODER = StandardCharsets.UTF_8.newDecoder();
    private String mCache;

    protected abstract String decodeString(byte[] bArr);

    protected abstract byte[] encodeString(String str);

    protected void onStringChanged(String str, String str2) {
    }

    public StringBlock() {
        super(0);
        this.mCache = StringsUtil.EMPTY;
    }

    @Override // com.reandroid.arsc.item.StringReference
    public String get() {
        return this.mCache;
    }

    @Override // com.reandroid.arsc.item.StringReference
    public void set(String str) {
        set(str, true);
    }

    public void set(String str, boolean z) {
        if (str == null || str.length() == 0) {
            str = StringsUtil.EMPTY;
        }
        String str2 = this.mCache;
        if (countBytes() == 0) {
            str2 = null;
        } else if (str.equals(str2)) {
            return;
        }
        this.mCache = str;
        setBytesInternal(encodeString(str), false);
        if (z) {
            onStringChanged(str2, str);
        }
    }

    @Override // com.reandroid.arsc.item.BlockItem
    protected void onBytesChanged() {
        this.mCache = decodeString(getBytesInternal());
    }

    public int compareTo(StringBlock stringBlock) {
        if (stringBlock == null) {
            return -1;
        }
        return StringsUtil.compareStrings(get(), stringBlock.get());
    }

    public String toString() {
        return get();
    }
}
