package com.reandroid.arsc.value;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.coder.EncodeResult;
import com.reandroid.arsc.coder.ValueCoder;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.HexUtil;
import com.reandroid.xml.XMLAttribute;
import com.reandroid.xml.XMLElement;
import com.reandroid.xml.XMLUtil;

/* JADX INFO: loaded from: classes10.dex */
public class ResValueMap extends AttributeValue implements Comparable<ResValueMap> {
    public static final String NAME_name = "name";
    private static final int OFFSET_NAME = 0;
    private static final int OFFSET_SIZE = 4;

    @Override // com.reandroid.arsc.value.AttributeValue
    boolean allowNullPrefixEncode() {
        return true;
    }

    public ResValueMap() {
        super(12, 4);
    }

    public void setArrayIndex() {
        setArrayIndex(getIndex() + 1);
    }

    public void setArrayIndex(int i) {
        setNameId(i | 16777216);
    }

    public int getArrayIndex() {
        int nameId = getNameId();
        int i = (-65536) & nameId;
        if (i == 16777216 || i == 33554432) {
            return nameId & 65535;
        }
        return -1;
    }

    public EncodeResult encodeStyle(XMLElement xMLElement) {
        return encodeStyle(false, xMLElement);
    }

    public EncodeResult encodeStyle(boolean z, XMLElement xMLElement) {
        XMLAttribute attribute = xMLElement.getAttribute("name");
        if (attribute == null) {
            return new EncodeResult("Missing attribute name");
        }
        return encodeStyle(z, attribute.getPrefix(), attribute.getValueAsString(), xMLElement.getTextContent());
    }

    public EncodeResult encodeStyle(boolean z, String str, String str2) {
        return encodeStyle(z, XMLUtil.splitPrefix(str), XMLUtil.splitName(str), str2);
    }

    public EncodeResult encodeStyle(String str, String str2) {
        return encodeStyle(false, XMLUtil.splitPrefix(str), XMLUtil.splitName(str), str2);
    }

    public EncodeResult encodeStyle(String str, String str2, String str3) {
        return encodeStyle(false, str, str2, str3);
    }

    public EncodeResult encodeStyle(boolean z, String str, String str2, String str3) {
        ResourceEntry resourceEntryEncodeAttrName = super.encodeAttrName(str, str2);
        if (resourceEntryEncodeAttrName == null) {
            return new EncodeResult("Unknown attribute name");
        }
        return super.encodeStyleValue(z, resourceEntryEncodeAttrName, str3);
    }

    @Override // com.reandroid.arsc.value.AttributeValue
    public String decodeName(boolean z) {
        String packageName;
        int nameId = getNameId();
        if (!PackageBlock.isResourceId(nameId)) {
            if (nameId == 0 || getAttributeType() != null) {
                return null;
            }
            return ValueCoder.decodeUnknownNameId(nameId);
        }
        ResourceEntry resourceEntryResolve = resolve(nameId);
        if (resourceEntryResolve == null || !resourceEntryResolve.isDeclared()) {
            return ValueCoder.decodeUnknownNameId(nameId);
        }
        String name2 = resourceEntryResolve.getName();
        return (!z || resourceEntryResolve.getPackageBlock() == getPackageBlock() || (packageName = resourceEntryResolve.getPackageName()) == null) ? name2 : packageName + ":" + name2;
    }

    @Override // com.reandroid.arsc.value.AttributeValue
    String decodeDataAsAttrFormats() {
        if (getAttributeType() != AttributeType.FORMATS) {
            return null;
        }
        int data = getData() & 255;
        if (data == 0) {
            return "";
        }
        return AttributeDataFormat.toString(AttributeDataFormat.decodeValueTypes(data));
    }

    @Override // com.reandroid.arsc.value.AttributeValue
    public String decodePrefix() {
        ResourceEntry resourceEntryResolveName = resolveName();
        if (resourceEntryResolveName == null || getPackageBlock() == resourceEntryResolveName.getPackageBlock()) {
            return null;
        }
        return resourceEntryResolveName.getPackageName();
    }

    public AttributeType getAttributeType() {
        return AttributeType.valueOf(getNameId());
    }

    public void setAttributeType(AttributeType attributeType) {
        setNameId(attributeType.getId());
        if (attributeType == AttributeType.FORMATS && getValueType() == ValueType.NULL) {
            setValueType(ValueType.DEC);
        }
    }

    public AttributeDataFormat[] getAttributeTypeFormats() {
        if (getAttributeType() != AttributeType.FORMATS) {
            return null;
        }
        return AttributeDataFormat.decodeValueTypes(getData());
    }

    public void addAttributeTypeFormats(AttributeDataFormat... attributeDataFormatArr) {
        if (attributeDataFormatArr == null) {
            return;
        }
        setData(AttributeDataFormat.sum(attributeDataFormatArr) | getData());
        if (getValueType() == ValueType.NULL) {
            setValueType(ValueType.DEC);
        }
    }

    public void addAttributeTypeFormat(AttributeDataFormat attributeDataFormat) {
        if (attributeDataFormat == null) {
            return;
        }
        setData(attributeDataFormat.getMask() | getData());
    }

    public Entry getEntry() {
        return (Entry) getParent(Entry.class);
    }

    @Override // com.reandroid.arsc.value.Value
    public PackageBlock getParentChunk() {
        Entry entry = getEntry();
        if (entry != null) {
            return entry.getPackageBlock();
        }
        return null;
    }

    public ResTableMapEntry getParentMapEntry() {
        return (ResTableMapEntry) getParentInstance(ResTableMapEntry.class);
    }

    public Entry getParentEntry() {
        return (Entry) getParentInstance(Entry.class);
    }

    @Override // com.reandroid.arsc.value.AttributeValue
    public int getNameId() {
        return getInteger(getBytesInternal(), 0);
    }

    @Override // com.reandroid.arsc.value.AttributeValue
    public void setNameId(int i) {
        putInteger(getBytesInternal(), 0, i);
    }

    @Override // com.reandroid.arsc.value.ValueItem, com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        if (json == null) {
            return null;
        }
        json.put("name", getNameId());
        return json;
    }

    @Override // com.reandroid.arsc.value.ValueItem, com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        super.fromJson(jSONObject);
        setNameId(jSONObject.getInt("name"));
    }

    public void setNameHigh(short s) {
        setNameId(((s & 65535) << 16) | (getNameId() & 65535));
    }

    public void setNameLow(short s) {
        setNameId((s & 65535) | (getNameId() & (-65536)));
    }

    public void setDataHigh(short s) {
        setData(((s & 65535) << 16) | (getData() & 65535));
    }

    public void setDataLow(short s) {
        setData((s & 65535) | (getData() & (-65536)));
    }

    @Override // com.reandroid.arsc.value.ValueItem
    public void merge(ValueItem valueItem) {
        if (valueItem == this || !(valueItem instanceof ResValueMap)) {
            return;
        }
        ResValueMap resValueMap = (ResValueMap) valueItem;
        super.merge(resValueMap);
        setNameId(resValueMap.getNameId());
    }

    @Override // java.lang.Comparable
    public int compareTo(ResValueMap resValueMap) {
        int nameId;
        int nameId2;
        if (resValueMap == null) {
            return -1;
        }
        if (resValueMap == this || (nameId = getNameId()) == (nameId2 = resValueMap.getNameId())) {
            return 0;
        }
        if (nameId == 0) {
            return 1;
        }
        if (nameId2 == 0) {
            return -1;
        }
        return Integer.compare(nameId, nameId2);
    }

    @Override // com.reandroid.arsc.value.ValueItem
    public String toString() {
        String strDecodeName = decodeName();
        String strDecodeValue = decodeValue();
        if (strDecodeName != null && strDecodeValue != null) {
            return strDecodeName + "=\"" + strDecodeValue + "\"";
        }
        return "name=" + HexUtil.toHex8(getNameId()) + ", " + super.toString();
    }
}
