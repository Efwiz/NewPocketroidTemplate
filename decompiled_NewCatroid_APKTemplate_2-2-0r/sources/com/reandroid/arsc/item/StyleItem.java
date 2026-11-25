package com.reandroid.arsc.item;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.container.FixedBlockContainer;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.xml.SpanSet;
import com.reandroid.xml.StyleDocument;
import com.reandroid.xml.StyleElement;
import com.reandroid.xml.StyleSpanEventSet;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class StyleItem extends FixedBlockContainer implements Comparable<StyleItem>, Iterable<StyleSpan>, SpanSet<StyleSpan>, JSONConvert<JSONObject> {
    public static final String NAME_spans = ObjectsUtil.of("spans");
    private final IntegerItem endBlock;
    private StyleIndexReference indexReference;
    private StringItem mStringItem;
    private final BlockList<StyleSpan> spanList;

    public StyleItem() {
        super(2);
        BlockList<StyleSpan> blockList = new BlockList<>();
        this.spanList = blockList;
        IntegerItem integerItem = new IntegerItem();
        this.endBlock = integerItem;
        addChild(0, blockList);
        addChild(1, integerItem);
        integerItem.set(-1);
    }

    public void add(String str, int i, int i2) {
        StyleSpan styleSpanCreateNext = createNext();
        styleSpanCreateNext.setString(str);
        styleSpanCreateNext.setFirstChar(i);
        styleSpanCreateNext.setLastChar(i2);
    }

    public StyleSpan createNext() {
        StyleSpan styleSpan = new StyleSpan();
        this.spanList.add(styleSpan);
        return styleSpan;
    }

    public StyleSpan get(int i) {
        return (StyleSpan) this.spanList.get(i);
    }

    public int size() {
        return this.spanList.size();
    }

    public boolean hasSpans() {
        return this.spanList.size() != 0;
    }

    @Override // java.lang.Iterable
    public Iterator<StyleSpan> iterator() {
        return this.spanList.clonedIterator();
    }

    @Override // com.reandroid.xml.SpanSet
    public Iterator<StyleSpan> getSpans() {
        return iterator();
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        BlockList<StyleSpan> blockList = this.spanList;
        while (blockReader.readInteger() != -1) {
            StyleSpan styleSpan = new StyleSpan();
            blockList.add(styleSpan);
            styleSpan.onReadBytes(blockReader);
        }
        this.endBlock.onReadBytes(blockReader);
    }

    public StyleDocument build(String str) {
        return StyleSpanEventSet.serialize(str, this);
    }

    public void parse(StyleDocument styleDocument) {
        clearSpans();
        Iterator<StyleElement> elements = styleDocument.getElements();
        while (elements.hasNext()) {
            parse(elements.next());
        }
    }

    public void parse(StyleElement styleElement) {
        add(styleElement.getTagString(), styleElement.getFirstChar(), styleElement.getLastChar());
        Iterator<StyleElement> elements = styleElement.getElements();
        while (elements.hasNext()) {
            parse(elements.next());
        }
    }

    protected void clearStyle() {
        StringItem stringItemInternal = getStringItemInternal();
        if (stringItemInternal != null) {
            stringItemInternal.unlinkStyleItemInternal(this);
        }
        clearSpans();
    }

    protected void clearSpans() {
        if (getParent() == null) {
            return;
        }
        Iterator<StyleSpan> it = iterator();
        while (it.hasNext()) {
            it.next().onRemoved();
        }
        this.spanList.clearChildes();
    }

    public void onRemoved() {
        clearStyle();
    }

    public void linkStringsInternal() {
        Iterator<StyleSpan> it = iterator();
        while (it.hasNext()) {
            it.next().link();
        }
    }

    public void setStringItemInternal(StringItem stringItem) {
        if (stringItem == null) {
            StringItem stringItem2 = this.mStringItem;
            this.mStringItem = null;
            unLinkIndexReference(stringItem2);
            return;
        }
        StringItem stringItem3 = this.mStringItem;
        if (stringItem3 != null) {
            if (stringItem != stringItem3) {
                throw new IllegalStateException("Different string item");
            }
        } else {
            this.mStringItem = stringItem;
            StyleIndexReference styleIndexReference = new StyleIndexReference(this);
            stringItem.addReference(styleIndexReference);
            this.indexReference = styleIndexReference;
        }
    }

    public StringItem getStringItemInternal() {
        return this.mStringItem;
    }

    public boolean isEmpty() {
        if (getStringItemInternal() == null) {
            return true;
        }
        return !hasSpans();
    }

    private void unLinkIndexReference(StringItem stringItem) {
        StyleIndexReference styleIndexReference = this.indexReference;
        if (styleIndexReference == null) {
            return;
        }
        this.indexReference = null;
        if (stringItem == null) {
            return;
        }
        stringItem.removeReference(styleIndexReference);
    }

    public String applyStyle(String str, boolean z, boolean z2) {
        if (str == null) {
            return null;
        }
        StyleDocument styleDocumentBuild = build(str);
        return styleDocumentBuild == null ? str : styleDocumentBuild.getText(z, z2);
    }

    @Override // com.reandroid.arsc.base.Block
    public void setNull(boolean z) {
        if (z) {
            clearStyle();
        }
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        if (isNull()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        int i = 0;
        for (StyleSpan styleSpan : this) {
            if (styleSpan != null) {
                jSONArray.put(i, styleSpan.toJson());
                i++;
            }
        }
        if (i == 0) {
            return null;
        }
        jSONObject.put(NAME_spans, jSONArray);
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        clearSpans();
        if (jSONObject == null) {
            clearStyle();
            return;
        }
        JSONArray jSONArray = jSONObject.getJSONArray(NAME_spans);
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            createNext().fromJson(jSONArray.getJSONObject(i));
        }
    }

    public void merge(StyleItem styleItem) {
        if (styleItem == null || styleItem == this) {
            return;
        }
        for (StyleSpan styleSpan : styleItem) {
            add(styleSpan.getString(), styleSpan.getFirstChar(), styleSpan.getLastChar());
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(StyleItem styleItem) {
        if (styleItem == this) {
            return 0;
        }
        if (styleItem == null) {
            return -1;
        }
        int iCompare = CompareUtil.compare(hasSpans(), styleItem.hasSpans()) * (-1);
        if (iCompare != 0) {
            return iCompare;
        }
        StringItem stringItemInternal = getStringItemInternal();
        StringItem stringItemInternal2 = styleItem.getStringItemInternal();
        int iCompare2 = CompareUtil.compare(stringItemInternal == null, stringItemInternal2 == null);
        return (iCompare2 != 0 || stringItemInternal == null || stringItemInternal2 == null) ? iCompare2 : CompareUtil.compareUnsigned(stringItemInternal.getIndex(), stringItemInternal2.getIndex());
    }

    public String toString() {
        return "Spans count = " + size();
    }

    static final class StyleIndexReference implements WeakStringReference {
        private int index;
        private final StyleItem styleItem;

        StyleIndexReference(StyleItem styleItem) {
            this.styleItem = styleItem;
            this.index = styleItem.getIndex();
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public void set(int i) {
            this.index = i;
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public int get() {
            return this.index;
        }

        @Override // com.reandroid.arsc.item.ReferenceItem
        public <T1 extends Block> T1 getReferredParent(Class<T1> cls) {
            if (cls.isInstance(this.styleItem)) {
                return this.styleItem;
            }
            return null;
        }
    }
}
