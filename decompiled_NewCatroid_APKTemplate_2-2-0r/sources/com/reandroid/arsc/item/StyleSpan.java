package com.reandroid.arsc.item;

import com.reandroid.arsc.pool.StringPool;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.xml.Span;

/* JADX INFO: loaded from: classes10.dex */
public class StyleSpan extends BlockItem implements Span, JSONConvert<JSONObject> {
    private final IndirectInteger firstChar;
    private final IndirectInteger lastChar;
    private final SpanStringReference stringReference;
    public static final String NAME_tag = ObjectsUtil.of("tag");
    public static final String NAME_first = ObjectsUtil.of("first");
    public static final String NAME_last = ObjectsUtil.of("last");

    public StyleSpan() {
        super(12);
        this.stringReference = new SpanStringReference(this);
        this.firstChar = new IndirectInteger(this, 4);
        this.lastChar = new IndirectInteger(this, 8);
    }

    public String getString() {
        return this.stringReference.getString();
    }

    public void setString(String str) {
        this.stringReference.setString(str);
    }

    @Override // com.reandroid.xml.Span
    public int getFirstChar() {
        return this.firstChar.get();
    }

    public void setFirstChar(int i) {
        this.firstChar.set(i);
    }

    @Override // com.reandroid.xml.Span
    public int getLastChar() {
        return this.lastChar.get();
    }

    @Override // com.reandroid.xml.Span
    public int getSpanOrder() {
        return getIndex();
    }

    public void setLastChar(int i) {
        this.lastChar.set(i);
    }

    @Override // com.reandroid.xml.Span
    public String getTagName() {
        return Span.splitTagName(getString());
    }

    void link() {
        this.stringReference.link();
    }

    void onRemoved() {
        this.stringReference.unlink();
        this.stringReference.set(-1);
    }

    @Override // com.reandroid.xml.Span
    public String getSpanAttributes() {
        return Span.splitAttribute(getString());
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_tag, getString());
        jSONObject.put(NAME_first, getFirstChar());
        jSONObject.put(NAME_last, getLastChar());
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        setString(jSONObject.getString(NAME_tag));
        setFirstChar(jSONObject.getInt(NAME_first));
        setLastChar(jSONObject.getInt(NAME_last));
    }

    public String toString() {
        return this.stringReference + " [" + getFirstChar() + ", " + getLastChar() + "]";
    }

    static class SpanStringReference extends ReferenceBlock<StyleSpan> {
        public SpanStringReference(StyleSpan styleSpan) {
            super(styleSpan, 0);
            set(-1);
        }

        public String getString() {
            StringItem stringItem = getStringItem();
            if (stringItem != null) {
                return stringItem.get();
            }
            return null;
        }

        public void setString(String str) {
            unlink();
            StringItem orCreate = getStringPool().getOrCreate(str);
            set(orCreate.getIndex());
            orCreate.addReference(this);
        }

        public void link() {
            StringItem stringItem = getStringItem();
            if (stringItem != null) {
                stringItem.addReference(this);
            }
        }

        public void unlink() {
            StringItem stringItem = getStringItem();
            if (stringItem != null) {
                stringItem.removeReference(this);
            }
        }

        private StringItem getStringItem() {
            StringPool<?> stringPool = getStringPool();
            if (stringPool != null) {
                return stringPool.get(get());
            }
            return null;
        }

        private StringPool<?> getStringPool() {
            return (StringPool) getBlock().getParentInstance(StringPool.class);
        }

        @Override // com.reandroid.arsc.item.ReferenceBlock
        public String toString() {
            String string = getString();
            return string != null ? string : "NULL{" + get() + "}";
        }
    }
}
