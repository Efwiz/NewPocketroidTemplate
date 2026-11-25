package com.reandroid.arsc.value;

import com.reandroid.arsc.ApkFile;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.chunk.MainChunk;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.ParentChunk;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.coder.CoderUnknownStringRef;
import com.reandroid.arsc.coder.EncodeResult;
import com.reandroid.arsc.coder.ValueCoder;
import com.reandroid.arsc.coder.XmlSanitizer;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.BlockItem;
import com.reandroid.arsc.item.ReferenceItem;
import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.pool.StringPool;
import com.reandroid.arsc.pool.TableStringPool;
import com.reandroid.arsc.refactor.ResourceMergeOption;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.xml.StyleDocument;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ValueItem extends BlockItem implements Value, JSONConvert<JSONObject> {
    public static final String NAME_data = "data";
    public static final String NAME_value_type = "value_type";
    private static final int OFFSET_DATA = 4;
    private static final int OFFSET_RES0 = 2;
    private static final int OFFSET_SIZE = 0;
    private static final int OFFSET_TYPE = 3;
    private ReferenceItem mStringReference;
    private final int sizeOffset;

    protected void onDataChanged() {
    }

    public ValueItem(int i, int i2) {
        super(i);
        this.sizeOffset = i2;
        writeSize();
    }

    public boolean isUndefined() {
        return getValueType() == ValueType.NULL && getData() == 0;
    }

    public ResourceEntry resolve(int i) {
        TableBlock tableBlock;
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock == null || (tableBlock = packageBlock.getTableBlock()) == null) {
            return null;
        }
        return tableBlock.getResource(packageBlock, i);
    }

    @Override // com.reandroid.arsc.value.Value
    public PackageBlock getPackageBlock() {
        ParentChunk parentChunk = getParentChunk();
        if (parentChunk != null) {
            return parentChunk.getPackageBlock();
        }
        return null;
    }

    void linkTableStrings(TableStringPool tableStringPool) {
        if (getValueType() == ValueType.STRING) {
            linkStringReference(tableStringPool);
        }
    }

    public void onRemoved() {
        unLinkStringReference();
    }

    public void refresh() {
        updateSize();
    }

    byte getRes0() {
        return getBytesInternal()[this.sizeOffset + 2];
    }

    void setRes0(byte b) {
        getBytesInternal()[this.sizeOffset + 2] = b;
    }

    public byte getType() {
        return getBytesInternal()[this.sizeOffset + 3];
    }

    public void setType(byte b) {
        if (b == getType()) {
            return;
        }
        byte[] bytesInternal = getBytesInternal();
        int i = this.sizeOffset + 3;
        byte b2 = bytesInternal[i];
        bytesInternal[i] = b;
        onTypeChanged(b2, b);
        onDataChanged();
    }

    public int getSize() {
        return getShort(getBytesInternal(), this.sizeOffset) & 65535;
    }

    public void setSize(int i) {
        setBytesLength(this.sizeOffset + i, false);
        writeSize();
    }

    void updateSize() {
        writeSize();
    }

    private void writeSize() {
        int i = this.sizeOffset;
        putShort(getBytesInternal(), i, (short) (countBytes() - i));
    }

    protected void onDataLoaded() {
        if (getValueType() == ValueType.STRING) {
            linkStringReference();
        } else {
            unLinkStringReference();
        }
    }

    @Override // com.reandroid.arsc.value.Value
    public ValueType getValueType() {
        return ValueType.valueOf(getType());
    }

    @Override // com.reandroid.arsc.value.Value
    public void setValueType(ValueType valueType) {
        setType(valueType != null ? valueType.getByte() : (byte) 0);
    }

    public int getData() {
        return getInteger(getBytesInternal(), this.sizeOffset + 4);
    }

    @Override // com.reandroid.arsc.value.Value
    public void setData(int i) {
        if (getData() == i) {
            return;
        }
        unLinkStringReference();
        writeData(i);
        if (ValueType.STRING == getValueType()) {
            linkStringReference();
        }
        onDataChanged();
    }

    void writeData(int i) {
        putInteger(getBytesInternal(), this.sizeOffset + 4, i);
    }

    public StringItem getDataAsPoolString() {
        StringPool<?> stringPool;
        if (getValueType() == ValueType.STRING && (stringPool = getStringPool()) != null) {
            return stringPool.get(getData());
        }
        return null;
    }

    private void onTypeChanged(byte b, byte b2) {
        byte b3 = ValueType.STRING.getByte();
        if (b == b3) {
            unLinkStringReference();
        } else if (b2 == b3) {
            linkStringReference();
        }
    }

    private void linkStringReference() {
        StringPool<?> stringPool = getStringPool();
        if (stringPool == null || stringPool.isStringLinkLocked()) {
            return;
        }
        linkStringReference(stringPool);
    }

    private void linkStringReference(StringPool<?> stringPool) {
        StringItem stringItem = stringPool.get(getData());
        if (stringItem == null) {
            unLinkStringReference();
            return;
        }
        if (this.mStringReference != null) {
            unLinkStringReference();
        }
        ValueStringReference valueStringReference = new ValueStringReference(this);
        this.mStringReference = valueStringReference;
        stringItem.addReference(valueStringReference);
    }

    private void unLinkStringReference() {
        ReferenceItem referenceItem = this.mStringReference;
        if (referenceItem == null) {
            return;
        }
        this.mStringReference = null;
        onUnlinkDataString(referenceItem);
    }

    protected void onUnlinkDataString(ReferenceItem referenceItem) {
        StringPool<?> stringPool = getStringPool();
        if (stringPool == null) {
            return;
        }
        stringPool.removeReference(referenceItem);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public StringPool<?> getStringPool() {
        for (Block parent = getParent(); parent != 0; parent = parent.getParent()) {
            if (parent instanceof MainChunk) {
                return ((MainChunk) parent).getStringPool();
            }
        }
        return null;
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        int iInitializeBytes = initializeBytes(blockReader);
        super.onReadBytes(blockReader);
        if (iInitializeBytes < 8) {
            setBytesLength(this.sizeOffset + 8, false);
            writeSize();
        }
    }

    private int initializeBytes(BlockReader blockReader) throws IOException {
        int position = blockReader.getPosition();
        int i = this.sizeOffset;
        blockReader.offset(i);
        int unsignedShort = blockReader.readUnsignedShort();
        int i2 = (unsignedShort >= 8 || blockReader.available() < 8) ? unsignedShort : 8;
        blockReader.seek(position);
        setBytesLength(i + i2, false);
        return unsignedShort;
    }

    @Override // com.reandroid.arsc.value.Value
    public String getValueAsString() {
        StringItem dataAsPoolString = getDataAsPoolString();
        if (dataAsPoolString == null) {
            return null;
        }
        String xml = dataAsPoolString.getXml();
        return xml == null ? "" : xml;
    }

    public StyleDocument getValueAsStyleDocument() {
        StringItem dataAsPoolString = getDataAsPoolString();
        if (dataAsPoolString != null) {
            return dataAsPoolString.getStyleDocument();
        }
        return null;
    }

    public void setValueAsString(StyleDocument styleDocument) {
        if (styleDocument == null) {
            setValueAsString("");
            return;
        }
        StringPool<?> stringPool = getStringPool();
        if (!styleDocument.hasElements()) {
            setValueAsString(XmlSanitizer.unEscapeUnQuote(styleDocument.getXml(false)));
        } else {
            setData(stringPool.getOrCreate(styleDocument).getIndex());
            setValueType(ValueType.STRING);
        }
    }

    @Override // com.reandroid.arsc.value.Value
    public void setValueAsString(String str) {
        if (getValueType() == ValueType.STRING && Objects.equals(str, getValueAsString())) {
            return;
        }
        if (str == null) {
            str = "";
        }
        setData(getStringPool().getOrCreate(str).getIndex());
        setValueType(ValueType.STRING);
    }

    public void serializeText(XmlSerializer xmlSerializer) throws IOException {
        serializeText(xmlSerializer, false);
    }

    public void serializeText(XmlSerializer xmlSerializer, boolean z) throws IOException {
        if (getValueType() == ValueType.STRING) {
            StringItem dataAsPoolString = getDataAsPoolString();
            if (dataAsPoolString != null) {
                dataAsPoolString.serializeText(xmlSerializer, z);
                return;
            } else {
                xmlSerializer.text(CoderUnknownStringRef.INS.decode(getData()));
                return;
            }
        }
        String strDecodeValue = decodeValue();
        if (strDecodeValue == null) {
            strDecodeValue = "";
        }
        xmlSerializer.text(strDecodeValue);
    }

    public void serializeAttribute(XmlSerializer xmlSerializer, String str, boolean z) throws IOException {
        serializeAttribute(xmlSerializer, null, str, z);
    }

    public void serializeAttribute(XmlSerializer xmlSerializer, String str, String str2, boolean z) throws IOException {
        if (getValueType() == ValueType.STRING) {
            StringItem dataAsPoolString = getDataAsPoolString();
            if (dataAsPoolString != null) {
                dataAsPoolString.serializeAttribute(xmlSerializer, str, str2);
                return;
            } else {
                xmlSerializer.attribute(str, str2, CoderUnknownStringRef.INS.decode(getData()));
                return;
            }
        }
        String strDecodeValue = decodeValue();
        if (z && StringsUtil.isEmpty(strDecodeValue)) {
            return;
        }
        if (strDecodeValue == null) {
            strDecodeValue = "";
        }
        xmlSerializer.attribute(str, str2, strDecodeValue);
    }

    public boolean getValueAsBoolean() {
        return getData() != 0;
    }

    public void setValueAsBoolean(boolean z) {
        setValueType(ValueType.BOOLEAN);
        setData(z ? -1 : 0);
    }

    @Override // com.reandroid.arsc.value.Value
    public void setValue(EncodeResult encodeResult) {
        encodeResult.getClass();
        if (encodeResult.isError()) {
            throw new IllegalArgumentException("Can not set error value: " + encodeResult.getError());
        }
        setTypeAndData(encodeResult.valueType, encodeResult.value);
    }

    public void merge(ValueItem valueItem) {
        if (valueItem == null || valueItem == this) {
            return;
        }
        if (valueItem.getSize() != 0) {
            setSize(valueItem.getSize());
        }
        ValueType valueType = valueItem.getValueType();
        if (valueType == ValueType.STRING) {
            StringItem dataAsPoolString = valueItem.getDataAsPoolString();
            if (dataAsPoolString != null) {
                StyleDocument styleDocument = dataAsPoolString.getStyleDocument();
                if (styleDocument != null) {
                    setValueAsString(styleDocument);
                    return;
                } else {
                    setValueAsString(dataAsPoolString.get());
                    return;
                }
            }
            return;
        }
        setTypeAndData(valueType, valueItem.getData());
    }

    public void mergeWithName(ResourceMergeOption resourceMergeOption, ValueItem valueItem) {
        int data;
        ResourceEntry resourceEntryResolveUndeclared;
        if (valueItem == null || valueItem == this) {
            return;
        }
        if (valueItem.getSize() != 0) {
            setSize(valueItem.getSize());
        }
        ValueType valueType = valueItem.getValueType();
        if (valueType == ValueType.STRING) {
            StyleDocument valueAsStyleDocument = valueItem.getValueAsStyleDocument();
            if (valueAsStyleDocument != null) {
                setValueAsString(valueAsStyleDocument);
                return;
            }
            ApkFile apkFile = getPackageBlock().getTableBlock().getApkFile();
            ApkFile apkFile2 = valueItem.getPackageBlock().getTableBlock().getApkFile();
            String valueAsString = valueItem.getValueAsString();
            setValueAsString(valueAsString);
            if (apkFile == null || apkFile2 == null) {
                return;
            }
            apkFile.mergeWithName(resourceMergeOption, apkFile2, valueAsString);
            return;
        }
        if (valueType.isReference()) {
            ResourceEntry valueAsReference = valueItem.getValueAsReference();
            if (valueAsReference != null && valueAsReference.isContext(valueItem.getPackageBlock())) {
                if (valueAsReference.isDeclared()) {
                    resourceEntryResolveUndeclared = getPackageBlock().mergeWithName(resourceMergeOption, valueAsReference);
                } else {
                    resourceEntryResolveUndeclared = resourceMergeOption.resolveUndeclared(getPackageBlock(), valueAsReference);
                }
                data = resourceEntryResolveUndeclared != null ? resourceEntryResolveUndeclared.getResourceId() : 0;
            } else {
                data = valueItem.getData();
            }
            setTypeAndData(valueType, data);
            return;
        }
        setTypeAndData(valueType, valueItem.getData());
    }

    public String decodeValue() {
        return decodeValue(true);
    }

    public String decodeValue(boolean z) {
        ValueType valueType = getValueType();
        if (valueType == null) {
            return null;
        }
        if (valueType.isReference()) {
            return decodeAsReferenceString(valueType, z);
        }
        if (valueType == ValueType.STRING) {
            return getValueAsString();
        }
        return ValueCoder.decode(valueType, getData());
    }

    private String decodeAsReferenceString(ValueType valueType, boolean z) {
        int data = getData();
        if (data == 0) {
            return ValueCoder.decodeReference(null, valueType, data);
        }
        ResourceEntry valueAsReference = getValueAsReference();
        if (z && valueAsReference == null && getPackageBlock() == null) {
            throw new NullPointerException("Parent package block is null");
        }
        if (valueAsReference == null || !valueAsReference.isDeclared()) {
            return ValueCoder.decodeUnknownResourceId(valueType == ValueType.REFERENCE, data);
        }
        return valueAsReference.buildReference(getPackageBlock(), valueType);
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        if (isNull()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        ValueType valueType = getValueType();
        jSONObject.put("value_type", valueType.name());
        if (valueType == ValueType.STRING) {
            StringItem dataAsPoolString = getDataAsPoolString();
            if (dataAsPoolString.hasStyle()) {
                jSONObject.put("data", getDataAsPoolString().toJson());
            } else {
                jSONObject.put("data", dataAsPoolString.get());
            }
        } else if (valueType == ValueType.BOOLEAN) {
            jSONObject.put("data", getValueAsBoolean());
        } else {
            jSONObject.put("data", getData());
        }
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        StringItem orCreate;
        if (jSONObject != null) {
            ValueType valueTypeFromName = ValueType.fromName(jSONObject.getString("value_type"));
            if (valueTypeFromName == ValueType.STRING) {
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                StringPool<?> stringPool = getStringPool();
                if (jSONObjectOptJSONObject != null) {
                    orCreate = stringPool.getOrCreate(jSONObjectOptJSONObject);
                } else {
                    orCreate = stringPool.getOrCreate(jSONObject.getString("data"));
                }
                setTypeAndData(valueTypeFromName, orCreate.getIndex());
                return;
            }
            if (valueTypeFromName == ValueType.BOOLEAN) {
                setValueAsBoolean(jSONObject.getBoolean("data"));
            } else {
                setValueType(valueTypeFromName);
                setData(jSONObject.getInt("data"));
            }
        }
    }

    public String toString() {
        StringItem dataAsPoolString;
        if (getPackageBlock() != null) {
            return getValueType() + ":" + HexUtil.toHex8(getData()) + " " + decodeValue();
        }
        StringBuilder sb = new StringBuilder();
        if (getSize() != 8) {
            sb.append("size=").append(getSize());
            sb.append(", ");
        }
        sb.append("type=");
        ValueType valueType = getValueType();
        if (valueType != null) {
            sb.append(valueType);
        } else {
            sb.append(HexUtil.toHex2(getType()));
        }
        sb.append(", data=");
        int data = getData();
        if (valueType == ValueType.STRING && (dataAsPoolString = getDataAsPoolString()) != null) {
            sb.append(dataAsPoolString.getHtml());
        } else {
            sb.append(HexUtil.toHex8(data));
        }
        return sb.toString();
    }

    static class ValueStringReference implements ReferenceItem {
        private final ValueItem valueItem;

        ValueStringReference(ValueItem valueItem) {
            this.valueItem = valueItem;
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public int get() {
            return this.valueItem.getData();
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public void set(int i) {
            this.valueItem.writeData(i);
        }

        @Override // com.reandroid.arsc.item.ReferenceItem
        public <T1 extends Block> T1 getReferredParent(Class<T1> cls) {
            ValueItem valueItem = this.valueItem;
            return cls.isInstance(valueItem) ? valueItem : (T1) valueItem.getParentInstance(cls);
        }
    }
}
