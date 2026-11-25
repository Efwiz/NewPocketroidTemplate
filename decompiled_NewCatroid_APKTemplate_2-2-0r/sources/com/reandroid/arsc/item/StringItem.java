package com.reandroid.arsc.item;

import com.reandroid.arsc.array.StringArray;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.coder.ThreeByteCharsetDecoder;
import com.reandroid.arsc.coder.XmlSanitizer;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.pool.StringPool;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.xml.StyleDocument;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class StringItem extends StringBlock implements JSONConvert<JSONObject>, Comparable<StringItem> {
    private final Set<ReferenceItem> mReferencedList = new HashSet();
    private StyleItem mStyleItem;
    private boolean mUtf8;
    private static final CharsetDecoder UTF16LE_DECODER = StandardCharsets.UTF_16LE.newDecoder();
    private static final CharsetDecoder DECODER_3B = ThreeByteCharsetDecoder.INSTANCE;
    public static final String NAME_string = ObjectsUtil.of("string");
    public static final String NAME_style = ObjectsUtil.of("style");

    public StringItem(boolean z) {
        this.mUtf8 = z;
    }

    public StyleDocument getStyleDocument() {
        if (hasStyle()) {
            return getStyle().build(get());
        }
        return null;
    }

    public <T extends Block> Iterator<T> getUsers(Class<T> cls) {
        return getUsers(cls, null);
    }

    public <T extends Block> Iterator<T> getUsers(final Class<T> cls, final Predicate<T> predicate) {
        Collection<ReferenceItem> referencedList = getReferencedList();
        if (referencedList.size() == 0) {
            return EmptyIterator.of();
        }
        return new ComputeIterator(referencedList.iterator(), new Function() { // from class: com.reandroid.arsc.item.StringItem$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return StringItem.lambda$getUsers$0(cls, predicate, (ReferenceItem) obj);
            }
        });
    }

    static /* synthetic */ Block lambda$getUsers$0(Class cls, Predicate predicate, ReferenceItem referenceItem) {
        Block referredParent = referenceItem.getReferredParent(cls);
        if (predicate == null || predicate.test(referredParent)) {
            return referredParent;
        }
        return null;
    }

    public boolean removeReference(ReferenceItem referenceItem) {
        return this.mReferencedList.remove(referenceItem);
    }

    public void removeAllReference() {
        this.mReferencedList.clear();
    }

    public boolean hasReference() {
        ensureStringLinkUnlocked();
        if (this.mReferencedList.size() == 0) {
            return false;
        }
        return FilterIterator.of(this.mReferencedList.iterator(), new Predicate() { // from class: com.reandroid.arsc.item.StringItem$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return StringItem.lambda$hasReference$1((ReferenceItem) obj);
            }
        }).hasNext();
    }

    static /* synthetic */ boolean lambda$hasReference$1(ReferenceItem referenceItem) {
        return !(referenceItem instanceof StyleItem.StyleIndexReference);
    }

    public Collection<ReferenceItem> getReferencedList() {
        ensureStringLinkUnlocked();
        return this.mReferencedList;
    }

    void ensureStringLinkUnlocked() {
        StringPool stringPool = (StringPool) getParentInstance(StringPool.class);
        if (stringPool != null) {
            stringPool.ensureStringLinkUnlockedInternal();
        }
    }

    public void addReference(ReferenceItem referenceItem) {
        if (referenceItem != null) {
            this.mReferencedList.add(referenceItem);
        }
    }

    public void addReferenceIfAbsent(ReferenceItem referenceItem) {
        if (referenceItem != null) {
            this.mReferencedList.add(referenceItem);
        }
    }

    public void addReference(Collection<ReferenceItem> collection) {
        if (collection == null) {
            return;
        }
        for (ReferenceItem referenceItem : collection) {
            if (referenceItem != null) {
                this.mReferencedList.add(referenceItem);
            }
        }
    }

    private void reUpdateReferences(int i) {
        for (ReferenceItem referenceItem : (ReferenceItem[]) this.mReferencedList.toArray(new ReferenceItem[0])) {
            referenceItem.set(i);
        }
    }

    public void onRemoved() {
        clearStyle();
        setParent(null);
    }

    @Override // com.reandroid.arsc.base.Block
    public void onIndexChanged(int i, int i2) {
        reUpdateReferences(i2);
    }

    @Override // com.reandroid.arsc.item.StringBlock
    protected void onStringChanged(String str, String str2) {
        super.onStringChanged(str, str2);
        StringPool stringPool = (StringPool) getParentInstance(StringPool.class);
        if (stringPool != null) {
            stringPool.onStringChanged(str, this);
        }
    }

    public void serializeText(XmlSerializer xmlSerializer) throws IOException {
        serializeText(xmlSerializer, false);
    }

    public void serializeText(XmlSerializer xmlSerializer, boolean z) throws IOException {
        String strEscapeSpecialCharacter;
        String str = get();
        if (str == null) {
            return;
        }
        if (z) {
            strEscapeSpecialCharacter = XmlSanitizer.escapeDecodedValue(str);
        } else {
            strEscapeSpecialCharacter = XmlSanitizer.escapeSpecialCharacter(str);
        }
        xmlSerializer.text(strEscapeSpecialCharacter);
    }

    public void serializeAttribute(XmlSerializer xmlSerializer, String str, String str2) throws IOException {
        String str3 = get();
        if (str3 == null) {
            str3 = "";
        }
        xmlSerializer.attribute(str, str2, XmlSanitizer.escapeSpecialCharacter(str3));
    }

    public String getHtml() {
        String str = get();
        if (str == null) {
            return null;
        }
        StyleItem style = getStyle();
        return style == null ? str : style.applyStyle(str, false, false);
    }

    public String getXml() {
        return getXml(false);
    }

    public String getXml(boolean z) {
        String str = get();
        if (str == null) {
            return null;
        }
        StyleItem style = getStyle();
        return style == null ? str : style.applyStyle(str, true, z);
    }

    @Override // com.reandroid.arsc.item.StringBlock, com.reandroid.arsc.item.StringReference
    public void set(String str) {
        StyleItem style;
        if (str == null && (style = getStyle()) != null) {
            style.clearStyle();
        }
        super.set(str);
    }

    public void set(StyleDocument styleDocument) {
        String xml = getXml();
        if (countBytes() == 0) {
            xml = null;
        }
        clearStyle();
        set(styleDocument.getStyledString(), false);
        if (styleDocument.hasElements()) {
            getOrCreateStyle().parse(styleDocument);
        }
        onStringChanged(xml, getXml());
    }

    public void set(JSONObject jSONObject) {
        String xml = getXml();
        if (countBytes() == 0) {
            xml = null;
        }
        clearStyle();
        set(jSONObject.getString(NAME_string), false);
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(NAME_style);
        if (jSONObjectOptJSONObject != null) {
            getOrCreateStyle().fromJson(jSONObjectOptJSONObject);
        }
        onStringChanged(xml, getXml());
    }

    public boolean isUtf8() {
        return this.mUtf8;
    }

    public void setUtf8(boolean z) {
        if (z == this.mUtf8) {
            return;
        }
        this.mUtf8 = z;
        onBytesChanged();
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        if (blockReader.available() < 4) {
            return;
        }
        setBytesLength(calculateReadLength(blockReader), false);
        blockReader.readFully(getBytesInternal());
        onBytesChanged();
    }

    int calculateReadLength(BlockReader blockReader) throws IOException {
        int[] iArrDecodeUtf16StringByteLength;
        if (blockReader.available() < 4) {
            return blockReader.available();
        }
        byte[] bArr = new byte[4];
        blockReader.readFully(bArr);
        blockReader.offset(-4);
        if (isUtf8()) {
            iArrDecodeUtf16StringByteLength = decodeUtf8StringByteLength(bArr);
        } else {
            iArrDecodeUtf16StringByteLength = decodeUtf16StringByteLength(bArr);
        }
        return iArrDecodeUtf16StringByteLength[0] + iArrDecodeUtf16StringByteLength[1] + (isUtf8() ? 1 : 2);
    }

    @Override // com.reandroid.arsc.item.StringBlock
    protected String decodeString(byte[] bArr) {
        return decodeString(bArr, this.mUtf8);
    }

    @Override // com.reandroid.arsc.item.StringBlock
    protected byte[] encodeString(String str) {
        if (this.mUtf8) {
            return encodeUtf8ToBytes(str);
        }
        return encodeUtf16ToBytes(str);
    }

    private String decodeString(byte[] bArr, boolean z) {
        int[] iArrDecodeUtf16StringByteLength;
        CharsetDecoder charsetDecoder;
        if (isNullBytes(bArr)) {
            if (bArr == null || bArr.length == 0) {
                return null;
            }
            return "";
        }
        if (z) {
            iArrDecodeUtf16StringByteLength = decodeUtf8StringByteLength(bArr);
        } else {
            iArrDecodeUtf16StringByteLength = decodeUtf16StringByteLength(bArr);
        }
        if (z) {
            charsetDecoder = UTF8_DECODER;
        } else {
            charsetDecoder = UTF16LE_DECODER;
        }
        try {
            return charsetDecoder.decode(ByteBuffer.wrap(bArr, iArrDecodeUtf16StringByteLength[0], iArrDecodeUtf16StringByteLength[1])).toString();
        } catch (CharacterCodingException unused) {
            if (z) {
                return tryThreeByteDecoder(bArr, iArrDecodeUtf16StringByteLength[0], iArrDecodeUtf16StringByteLength[1]);
            }
            return new String(bArr, iArrDecodeUtf16StringByteLength[0], iArrDecodeUtf16StringByteLength[1], StandardCharsets.UTF_16LE);
        }
    }

    private String tryThreeByteDecoder(byte[] bArr, int i, int i2) {
        try {
            return DECODER_3B.decode(ByteBuffer.wrap(bArr, i, i2)).toString();
        } catch (CharacterCodingException unused) {
            return new String(bArr, i, i2, StandardCharsets.UTF_8);
        }
    }

    public boolean hasStyle() {
        StyleItem style = getStyle();
        return style != null && style.size() > 0;
    }

    public StyleItem getStyle() {
        return this.mStyleItem;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public StyleItem getOrCreateStyle() {
        StyleItem style = getStyle();
        if (style != null) {
            return style;
        }
        linkStyleItemInternal((StyleItem) ((StringPool) getParentInstance(StringPool.class)).getStyleArray().createNext());
        return getStyle();
    }

    public void linkStyleItemInternal(StyleItem styleItem) {
        if (styleItem == null) {
            throw new NullPointerException("Can not link null style item");
        }
        StyleItem styleItem2 = this.mStyleItem;
        if (styleItem2 == styleItem) {
            return;
        }
        if (styleItem2 != null) {
            throw new IllegalStateException("Style item is already linked");
        }
        this.mStyleItem = styleItem;
        styleItem.setStringItemInternal(this);
    }

    public void unlinkStyleItemInternal(StyleItem styleItem) {
        StyleItem styleItem2 = this.mStyleItem;
        if (styleItem2 == null) {
            return;
        }
        if (styleItem != styleItem2) {
            throw new IllegalStateException("Wrong style item");
        }
        this.mStyleItem = null;
        styleItem.setStringItemInternal(null);
    }

    private void clearStyle() {
        StyleItem style = getStyle();
        if (style != null) {
            style.clearStyle();
        }
    }

    public void transferReferences(StringItem stringItem) {
        int index;
        if (stringItem == this || stringItem == null || getParent() != stringItem.getParent() || (index = getIndex()) < 0 || stringItem.getIndex() < 0) {
            return;
        }
        for (ReferenceItem referenceItem : (ReferenceItem[]) stringItem.getReferencedList().toArray(new ReferenceItem[0])) {
            if (isTransferable(referenceItem)) {
                stringItem.removeReference(referenceItem);
                referenceItem.set(index);
                addReference(referenceItem);
            }
        }
    }

    private boolean isTransferable(ReferenceItem referenceItem) {
        return !(referenceItem instanceof WeakStringReference);
    }

    public boolean merge(StringItem stringItem) {
        if (!canMerge(stringItem)) {
            return false;
        }
        clearStyle();
        set(stringItem.get(), false);
        StyleItem style = stringItem.getStyle();
        if (style != null && style.hasSpans()) {
            getOrCreateStyle().merge(style);
        }
        onStringChanged(null, getXml());
        return true;
    }

    boolean canMerge(StringItem stringItem) {
        if (stringItem == null || stringItem == this) {
            return false;
        }
        Block block = (Block) getParentInstance(StringArray.class);
        Block block2 = (Block) stringItem.getParentInstance(StringArray.class);
        return (block == null || block2 == null || block == block2) ? false : true;
    }

    @Override // java.lang.Comparable
    public int compareTo(StringItem stringItem) {
        if (stringItem == null) {
            return -1;
        }
        if (stringItem == this) {
            return 0;
        }
        int iCompare = CompareUtil.compare(hasStyle(), stringItem.hasStyle()) * (-1);
        return iCompare != 0 ? iCompare : CompareUtil.compare(getXml(), stringItem.getXml());
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_string, get());
        if (hasStyle()) {
            jSONObject.put(NAME_style, getStyle().toJson());
        }
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        set(jSONObject);
    }

    @Override // com.reandroid.arsc.item.StringBlock
    public String toString() {
        String xml = getXml();
        if (xml == null) {
            return getIndex() + ": NULL";
        }
        StringPool stringPool = (StringPool) getParentInstance(StringPool.class);
        if (stringPool != null && !stringPool.isStringLinkLocked()) {
            return getIndex() + ": USED BY=" + this.mReferencedList.size() + "{" + xml + "}";
        }
        return getIndex() + ":" + xml;
    }

    private static int[] decodeUtf8StringByteLength(byte[] bArr) {
        int i = (bArr[0] & 128) != 0 ? 2 : 1;
        int i2 = bArr[i];
        int i3 = i + 1;
        if ((i2 & 128) != 0) {
            i2 = ((i2 & 127) << 8) + (bArr[i3] & 255);
            i3 = i + 2;
        }
        return new int[]{i3, i2};
    }

    private static int[] decodeUtf16StringByteLength(byte[] bArr) {
        int i = ((bArr[1] & 255) << 8) | (bArr[0] & 255);
        if ((32768 & i) != 0) {
            return new int[]{4, (((i & 32767) << 16) + ((bArr[3] & 255) << 8) + (bArr[2] & 255)) * 2};
        }
        return new int[]{2, i * 2};
    }

    static boolean isNullBytes(byte[] bArr) {
        if (bArr == null) {
            return true;
        }
        int length = bArr.length;
        if (length < 2) {
            return true;
        }
        for (int i = 2; i < length; i++) {
            if (bArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    private static byte[] encodeUtf8ToBytes(String str) {
        byte[] bArr;
        byte[] bytesInternal;
        byte[] bArr2 = new byte[2];
        if (str != null) {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            int length = bytes.length;
            if ((65408 & length) != 0) {
                int i = length & 255;
                byte[] bArr3 = {(byte) (i | 128), (byte) i, (byte) (((length - i) >> 8) | 128), (byte) i};
                int length2 = str.length();
                int i2 = length2 & 255;
                int i3 = (length2 - i2) >> 8;
                bytesInternal = bArr3;
            } else {
                bytesInternal = new ShortItem((short) length).getBytesInternal();
                bytesInternal[1] = bytesInternal[0];
                bytesInternal[0] = (byte) str.length();
            }
            bArr = bytes;
            bArr2 = bytesInternal;
        } else {
            bArr = new byte[0];
        }
        return addBytes(bArr2, bArr, new byte[1]);
    }

    private static byte[] encodeUtf16ToBytes(String str) {
        byte[] bytesInternal;
        if (str == null) {
            return null;
        }
        byte[] utf16Bytes = getUtf16Bytes(str);
        int length = utf16Bytes.length / 2;
        if ((length & (-32768)) != 0) {
            int i = length & 255;
            int i2 = length - i;
            int i3 = i2 & 65280;
            int i4 = i2 - i3;
            bytesInternal = new byte[]{(byte) i, (byte) (((i4 & 65280) >> 8) | 128), (byte) i, (byte) (i3 >> 8)};
            int i5 = i4 & 255;
        } else {
            bytesInternal = new ShortItem((short) length).getBytesInternal();
        }
        return addBytes(bytesInternal, utf16Bytes, new byte[2]);
    }

    static byte[] getUtf16Bytes(String str) {
        return str.getBytes(StandardCharsets.UTF_16LE);
    }

    private static byte[] addBytes(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int length;
        if (bArr == null && bArr2 == null && bArr3 == null) {
            return null;
        }
        int length2 = bArr != null ? bArr.length : 0;
        if (bArr2 != null) {
            length2 += bArr2.length;
        }
        if (bArr3 != null) {
            length2 += bArr3.length;
        }
        byte[] bArr4 = new byte[length2];
        if (bArr != null) {
            length = bArr.length;
            System.arraycopy(bArr, 0, bArr4, 0, length);
        } else {
            length = 0;
        }
        if (bArr2 != null) {
            System.arraycopy(bArr2, 0, bArr4, length, bArr2.length);
            length += bArr2.length;
        }
        if (bArr3 != null) {
            System.arraycopy(bArr3, 0, bArr4, length, bArr3.length);
        }
        return bArr4;
    }
}
