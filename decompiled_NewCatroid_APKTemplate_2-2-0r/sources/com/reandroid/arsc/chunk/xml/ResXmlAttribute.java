package com.reandroid.arsc.chunk.xml;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.coder.EncodeResult;
import com.reandroid.arsc.coder.ValueCoder;
import com.reandroid.arsc.coder.XmlSanitizer;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.ReferenceBlock;
import com.reandroid.arsc.item.ReferenceItem;
import com.reandroid.arsc.item.ResXmlID;
import com.reandroid.arsc.item.ResXmlString;
import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.pool.ResXmlStringPool;
import com.reandroid.arsc.pool.StringPool;
import com.reandroid.arsc.refactor.ResourceMergeOption;
import com.reandroid.arsc.value.AttributeValue;
import com.reandroid.arsc.value.ValueItem;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.arsc.value.attribute.AttributeBag;
import com.reandroid.common.Namespace;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.xml.XMLAttribute;
import com.reandroid.xml.XMLUtil;
import java.io.IOException;
import java.util.Objects;
import name.antonsmirnov.firmata.FormatHelper;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlAttribute extends AttributeValue implements Comparable<ResXmlAttribute> {
    public static final String NAME_id = "id";
    public static final String NAME_name = "name";
    public static final String NAME_namespace_uri = "namespace_uri";
    private static final int OFFSET_NAME = 4;
    private static final int OFFSET_NS = 0;
    private static final int OFFSET_SIZE = 12;
    private static final int OFFSET_STRING = 8;
    private ResXmlStartNamespace mLinkedNamespace;
    private ReferenceItem mNSReference;
    private ReferenceItem mNameIdReference;
    private ReferenceItem mNameReference;
    private ReferenceItem mValueStringReference;

    public ResXmlAttribute(int i) {
        super(i, 12);
        byte[] bytesInternal = getBytesInternal();
        putInteger(bytesInternal, 0, -1);
        putInteger(bytesInternal, 4, -1);
        putInteger(bytesInternal, 8, -1);
    }

    public ResXmlAttribute() {
        this(20);
    }

    public boolean autoSetNamespace() {
        return autoSetNamespace(true);
    }

    public boolean autoSetNamespace(boolean z) {
        if (getNameId() != 0) {
            return autoSetNamespace(resolveName());
        }
        if (!z) {
            return false;
        }
        String uri = getUri();
        if (Namespace.isExternalUri(uri) && Namespace.isValidUri(uri) && Namespace.isValidPrefix(getPrefix())) {
            return false;
        }
        return setNamespace(null, null);
    }

    public boolean autoSetName() {
        return autoSetName(true);
    }

    public boolean autoSetName(boolean z) {
        String name2;
        int nameId = getNameId();
        if (nameId == 0) {
            if (z) {
                String uri = getUri();
                if (!Namespace.isExternalUri(uri) || !Namespace.isValidUri(uri) || !Namespace.isValidPrefix(getPrefix())) {
                    return setNamespace(null, null);
                }
            }
            return false;
        }
        ResourceEntry resourceEntryResolveName = resolveName();
        if (resourceEntryResolveName == null || resourceEntryResolveName.isEmpty() || (name2 = resourceEntryResolveName.getName()) == null) {
            return false;
        }
        boolean zAutoSetNamespace = autoSetNamespace(resourceEntryResolveName);
        String name3 = getName();
        setName(name2, nameId);
        return zAutoSetNamespace || Objects.equals(name3, name2);
    }

    private boolean autoSetNamespace(ResourceEntry resourceEntry) {
        if (resourceEntry == null) {
            return false;
        }
        PackageBlock packageBlock = resourceEntry.getPackageBlock();
        String prefix = getPrefix();
        String uri = getUri();
        String name2 = packageBlock.getName();
        if (!packageBlock.isMultiPackage() && Namespace.isValidPrefix(prefix, name2) && Namespace.isValidUri(uri, name2)) {
            return false;
        }
        return setNamespace(packageBlock.getUri(), packageBlock.getPrefix());
    }

    @Override // com.reandroid.arsc.value.ValueItem
    public boolean isUndefined() {
        return getNameReference() < 0;
    }

    public String getUri() {
        return getString(getNamespaceReference());
    }

    public boolean equalsName(String str) {
        if (str == null) {
            return getName() == null;
        }
        String strSplitPrefix = XMLUtil.splitPrefix(str);
        if (strSplitPrefix == null || strSplitPrefix.equals(getPrefix())) {
            return str.equals(getName());
        }
        return false;
    }

    public String getName(boolean z) {
        String prefix;
        String name2 = getName();
        return (name2 == null || !z || (prefix = getPrefix()) == null) ? name2 : prefix + ":" + name2;
    }

    public String getName() {
        return getString(getNameReference());
    }

    @Override // com.reandroid.arsc.value.AttributeValue
    public String decodePrefix() {
        int nameId = getNameId();
        String prefix = getPrefix();
        if (nameId == 0) {
            if (Namespace.isValidPrefix(prefix) && Namespace.isExternalUri(getUri())) {
                return prefix;
            }
            return null;
        }
        ResourceEntry resourceEntryResolveName = resolveName();
        if (resourceEntryResolveName != null) {
            PackageBlock packageBlock = resourceEntryResolveName.getPackageBlock();
            return (packageBlock.isMultiPackage() || !Namespace.isValidPrefix(prefix, packageBlock.getName())) ? packageBlock.getPrefix() : prefix;
        }
        return Namespace.prefixForResourceId(nameId);
    }

    public String decodeUri() {
        String uri = getUri();
        int nameId = getNameId();
        if (nameId == 0) {
            if (Namespace.isExternalUri(uri)) {
                return uri;
            }
            return null;
        }
        ResourceEntry resourceEntryResolveName = resolveName();
        if (Namespace.isValidUri(uri, nameId)) {
            return uri;
        }
        if (resourceEntryResolveName == null) {
            return Namespace.uriForResourceId(nameId);
        }
        return resourceEntryResolveName.getPackageBlock().getUri();
    }

    @Override // com.reandroid.arsc.value.AttributeValue
    public String decodeName(boolean z) {
        String strDecodePrefix;
        String strDecodePrefix2;
        String strDecodePrefix3;
        int nameId = getNameId();
        if (nameId == 0) {
            String name2 = getName(false);
            return (z && (strDecodePrefix3 = decodePrefix()) != null) ? strDecodePrefix3 + ":" + name2 : name2;
        }
        ResourceEntry resourceEntryResolveName = resolveName();
        if (resourceEntryResolveName == null || !resourceEntryResolveName.isDeclared()) {
            String strDecodeUnknownNameId = ValueCoder.decodeUnknownNameId(nameId);
            return (!z || (strDecodePrefix = decodePrefix()) == null) ? strDecodeUnknownNameId : strDecodePrefix + ":" + strDecodeUnknownNameId;
        }
        String name3 = resourceEntryResolveName.getName();
        return (!z || name3 == null || (strDecodePrefix2 = decodePrefix()) == null) ? name3 : strDecodePrefix2 + ":" + name3;
    }

    public String getPrefix() {
        ResXmlStartNamespace startNamespace = getStartNamespace();
        if (startNamespace != null) {
            return startNamespace.getPrefix();
        }
        return null;
    }

    public ResXmlNamespace getNamespace() {
        return getStartNamespace();
    }

    public void setNamespace(Namespace namespace) {
        if (namespace != null) {
            setNamespace(namespace.getUri(), namespace.getPrefix());
        } else {
            setNamespace(null, null);
        }
    }

    public String getValueString() {
        return getString(getValueStringReference());
    }

    @Override // com.reandroid.arsc.value.AttributeValue
    public int getNameId() {
        ResXmlID resXmlID = getResXmlID();
        if (resXmlID != null) {
            return resXmlID.get();
        }
        return 0;
    }

    @Override // com.reandroid.arsc.value.AttributeValue
    public void setNameId(int i) {
        ResXmlIDMap resXmlIDMap = getResXmlIDMap();
        if (resXmlIDMap == null) {
            return;
        }
        setNameReference(resXmlIDMap.getOrCreate(i).getIndex());
    }

    @Override // com.reandroid.arsc.value.AttributeValue
    public void setName(String str, int i) {
        if (ObjectsUtil.equals(str, getName()) && i == getNameId()) {
            return;
        }
        unlink(this.mNameReference);
        unLinkNameId(getResXmlID());
        ResXmlString orCreateAttributeName = getOrCreateAttributeName(str, i);
        if (orCreateAttributeName == null) {
            return;
        }
        setNameReference(orCreateAttributeName.getIndex());
        this.mNameReference = link(4);
        linkNameId();
    }

    private void linkStartNameSpace() {
        unLinkStartNameSpace();
        ResXmlStartNamespace startNamespace = getStartNamespace();
        if (startNamespace == null) {
            return;
        }
        this.mLinkedNamespace = startNamespace;
        startNamespace.addAttributeReference(this);
    }

    private void unLinkStartNameSpace() {
        ResXmlStartNamespace resXmlStartNamespace = this.mLinkedNamespace;
        if (resXmlStartNamespace == null) {
            return;
        }
        this.mLinkedNamespace = null;
        resXmlStartNamespace.removeAttributeReference(this);
    }

    private ResXmlStartNamespace getStartNamespace() {
        int namespaceReference = getNamespaceReference();
        if (namespaceReference < 0) {
            return null;
        }
        ResXmlStartNamespace resXmlStartNamespace = this.mLinkedNamespace;
        if (resXmlStartNamespace != null && resXmlStartNamespace.getUriReference() == namespaceReference) {
            return resXmlStartNamespace;
        }
        ResXmlElement parentElement = getParentElement();
        if (parentElement != null) {
            return parentElement.getStartNamespaceByUriRef(namespaceReference);
        }
        return null;
    }

    private ResXmlString getOrCreateAttributeName(String str, int i) {
        ResXmlStringPool stringPool = getStringPool();
        if (stringPool == null) {
            return null;
        }
        return stringPool.getOrCreate(i, str);
    }

    public boolean removeSelf() {
        ResXmlElement parentElement = getParentElement();
        if (parentElement != null) {
            return parentElement.removeAttribute(this);
        }
        return false;
    }

    public ResXmlElement getParentElement() {
        return (ResXmlElement) getParent(ResXmlElement.class);
    }

    public int getAttributesUnitSize() {
        return super.getSize() + 12;
    }

    public void setAttributesUnitSize(int i) {
        super.setSize(i - 12);
    }

    private String getString(int i) {
        StringItem stringItem;
        if (getStringPool() == null || (stringItem = getStringItem(i)) == null) {
            return null;
        }
        return stringItem.getHtml();
    }

    private StringItem getStringItem(int i) {
        ResXmlStringPool stringPool;
        if (i >= 0 && (stringPool = getStringPool()) != null) {
            return stringPool.get(i);
        }
        return null;
    }

    private ResXmlID getResXmlID() {
        ResXmlString resXmlString = (ResXmlString) getStringItem(getNameReference());
        if (resXmlString != null) {
            return resXmlString.getResXmlID();
        }
        return null;
    }

    private ResXmlIDMap getResXmlIDMap() {
        ResXmlElement parentElement = getParentElement();
        if (parentElement != null) {
            return parentElement.getResXmlIDMap();
        }
        return null;
    }

    int getNamespaceReference() {
        return getInteger(getBytesInternal(), 0);
    }

    public boolean setNamespace(String str, String str2) {
        ResXmlNamespace namespaceByPrefix;
        String strEmptyToNull = StringsUtil.emptyToNull(str);
        String strEmptyToNull2 = StringsUtil.emptyToNull(str2);
        if (strEmptyToNull == null && strEmptyToNull2 == null) {
            return setNamespaceReference(-1);
        }
        ResXmlElement parentElement = getParentElement();
        if (parentElement == null) {
            return false;
        }
        if (strEmptyToNull != null && strEmptyToNull2 != null) {
            namespaceByPrefix = parentElement.getOrCreateNamespace(strEmptyToNull, strEmptyToNull2);
        } else if (strEmptyToNull != null) {
            namespaceByPrefix = parentElement.getStartNamespaceByUri(strEmptyToNull);
        } else {
            namespaceByPrefix = parentElement.getNamespaceByPrefix(strEmptyToNull2);
        }
        if (namespaceByPrefix == null) {
            return false;
        }
        return setNamespaceReference(namespaceByPrefix.getUriReference());
    }

    public boolean setNamespaceReference(int i) {
        if (i == getNamespaceReference()) {
            return false;
        }
        setUriReference(i);
        linkStartNameSpace();
        return true;
    }

    void setUriReference(int i) {
        StringItem stringItem = getStringItem(getNamespaceReference());
        putInteger(getBytesInternal(), 0, i);
        if (stringItem != null) {
            stringItem.removeReference(this.mNSReference);
        }
        this.mNSReference = link(0);
    }

    int getNameReference() {
        return getInteger(getBytesInternal(), 4);
    }

    void setNameReference(int i) {
        if (i == getNameReference()) {
            return;
        }
        unLinkNameId(getResXmlID());
        unlink(this.mNameReference);
        putInteger(getBytesInternal(), 4, i);
        this.mNameReference = link(4);
        linkNameId();
    }

    int getValueStringReference() {
        return getInteger(getBytesInternal(), 8);
    }

    void setValueStringReference(int i) {
        ResXmlStringPool stringPool;
        ReferenceBlock referenceBlock;
        if ((i != getValueStringReference() || this.mValueStringReference == null) && (stringPool = getStringPool()) != null) {
            ResXmlString resXmlString = stringPool.get(i);
            unlink(this.mValueStringReference);
            if (resXmlString != null) {
                i = resXmlString.getIndex();
            }
            putInteger(getBytesInternal(), 8, i);
            if (resXmlString != null) {
                referenceBlock = new ReferenceBlock(this, 8);
                resXmlString.addReference(referenceBlock);
            } else {
                referenceBlock = null;
            }
            this.mValueStringReference = referenceBlock;
        }
    }

    @Override // com.reandroid.arsc.value.ValueItem, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        super.onReadBytes(blockReader);
        super.onDataLoaded();
        linkAll();
        linkStartNameSpace();
    }

    @Override // com.reandroid.arsc.value.ValueItem
    public void onRemoved() {
        super.onRemoved();
        unLinkStartNameSpace();
        unlinkAll();
    }

    @Override // com.reandroid.arsc.value.ValueItem
    protected void onUnlinkDataString(ReferenceItem referenceItem) {
        unlink(referenceItem);
    }

    @Override // com.reandroid.arsc.value.ValueItem
    protected void onDataChanged() {
        if (getValueType() == ValueType.STRING) {
            setValueStringReference(getData());
        } else {
            setValueStringReference(-1);
        }
    }

    @Override // com.reandroid.arsc.value.Value
    public ResXmlDocument getParentChunk() {
        ResXmlElement parentElement = getParentElement();
        if (parentElement != null) {
            return parentElement.getParentDocument();
        }
        return null;
    }

    private void linkNameId() {
        ResXmlID resXmlID = getResXmlID();
        if (resXmlID == null) {
            return;
        }
        if (this.mNameIdReference == null || !resXmlID.hasReference(this)) {
            unLinkNameId(resXmlID);
            ReferenceBlock referenceBlock = new ReferenceBlock(this, 4);
            resXmlID.addReference(referenceBlock);
            this.mNameIdReference = referenceBlock;
        }
    }

    private void unLinkNameId(ResXmlID resXmlID) {
        ResXmlIDMap resXmlIDMap;
        ReferenceItem referenceItem = this.mNameIdReference;
        if (referenceItem == null || resXmlID == null) {
            return;
        }
        resXmlID.removeReference(referenceItem);
        this.mNameIdReference = null;
        if (resXmlID.hasReference() || (resXmlIDMap = getResXmlIDMap()) == null) {
            return;
        }
        resXmlIDMap.removeSafely(resXmlID);
    }

    private void linkAll() {
        unlink(this.mNSReference);
        this.mNSReference = link(0);
        unlink(this.mNameReference);
        this.mNameReference = link(4);
        unlink(this.mValueStringReference);
        this.mValueStringReference = link(8);
        linkNameId();
    }

    private void unlinkAll() {
        unlink(this.mNSReference);
        unlink(this.mNameReference);
        unlink(this.mValueStringReference);
        this.mNSReference = null;
        this.mNameReference = null;
        this.mValueStringReference = null;
        unLinkNameId(getResXmlID());
    }

    private ReferenceItem link(int i) {
        ResXmlStringPool stringPool;
        ResXmlString resXmlString;
        if (i < 0 || (stringPool = getStringPool()) == null || (resXmlString = stringPool.get(getInteger(getBytesInternal(), i))) == null) {
            return null;
        }
        ReferenceBlock referenceBlock = new ReferenceBlock(this, i);
        resXmlString.addReference(referenceBlock);
        return referenceBlock;
    }

    private void unlink(ReferenceItem referenceItem) {
        ResXmlStringPool stringPool;
        if (referenceItem == null || (stringPool = getStringPool()) == null) {
            return;
        }
        stringPool.removeReference(referenceItem);
    }

    @Override // com.reandroid.arsc.value.ValueItem
    public ResXmlStringPool getStringPool() {
        StringPool<?> stringPool = super.getStringPool();
        if (stringPool instanceof ResXmlStringPool) {
            return (ResXmlStringPool) stringPool;
        }
        return null;
    }

    @Override // com.reandroid.arsc.value.AttributeValue, com.reandroid.arsc.value.ValueItem
    public void mergeWithName(ResourceMergeOption resourceMergeOption, ValueItem valueItem) {
        super.mergeWithName(resourceMergeOption, valueItem);
        setNamespace(((ResXmlAttribute) valueItem).getNamespace());
    }

    @Override // java.lang.Comparable
    public int compareTo(ResXmlAttribute resXmlAttribute) {
        int nameId = getNameId();
        int nameId2 = resXmlAttribute.getNameId();
        if (nameId == 0 && nameId2 != 0) {
            return 1;
        }
        if (nameId2 == 0 && nameId != 0) {
            return -1;
        }
        if (nameId != 0) {
            return Integer.compare(nameId, nameId2);
        }
        String name2 = getName();
        if (name2 == null) {
            name2 = "";
        }
        String name3 = resXmlAttribute.getName();
        return name2.compareTo(name3 != null ? name3 : "");
    }

    public void serialize(XmlSerializer xmlSerializer) throws IOException {
        serialize(xmlSerializer, true);
    }

    public void serialize(XmlSerializer xmlSerializer, boolean z) throws IOException {
        String strDecodeValue;
        String name2;
        String uri;
        if (getValueType() == ValueType.STRING) {
            String valueAsString = getValueAsString();
            if (valueAsString == null) {
                return;
            }
            strDecodeValue = XmlSanitizer.escapeSpecialCharacter(valueAsString);
            if (getNameId() == 0 || resolveName() == null) {
                strDecodeValue = XmlSanitizer.escapeDecodedValue(strDecodeValue);
            }
        } else {
            strDecodeValue = decodeValue(z);
        }
        if (z) {
            name2 = decodeName(false);
        } else {
            name2 = getName(false);
        }
        if (z) {
            uri = decodeUri();
        } else {
            uri = getUri();
        }
        xmlSerializer.attribute(uri, name2, strDecodeValue);
    }

    public ResourceEntry encodeAttributeName(String str, String str2, String str3) throws IOException {
        setNamespace(str, str2);
        if (!Namespace.isValidUri(str) || Namespace.isExternalUri(str)) {
            setName(str3, 0);
            return null;
        }
        ResourceEntry resourceEntryEncodeAttrName = super.encodeAttrName(str2, str3);
        if (resourceEntryEncodeAttrName != null) {
            return resourceEntryEncodeAttrName;
        }
        String strEmptyToNull = StringsUtil.emptyToNull(str2);
        if (strEmptyToNull == null) {
            setName(str3, 0);
            return null;
        }
        throw new IOException("Unknown attribute name '" + strEmptyToNull + ":" + str3 + FormatHelper.QUOTE);
    }

    public void encode(boolean z, String str, String str2, String str3, String str4) throws IOException {
        EncodeResult encodeResultEncodeStyleValue = super.encodeStyleValue(z, encodeAttributeName(str, str2, str3), str4);
        if (encodeResultEncodeStyleValue.isError()) {
            throw new IOException(buildErrorMessage(encodeResultEncodeStyleValue.getError(), str4));
        }
    }

    public void encode(String str, String str2, String str3, String str4, boolean z) throws IOException {
        ResourceEntry resourceEntryEncodeAttributeName = encodeAttributeName(str, str2, str3);
        EncodeResult encodeResultEncodeReference = ValueCoder.encodeReference(getPackageBlock(), str4);
        if (encodeResultEncodeReference != null) {
            if (encodeResultEncodeReference.isError()) {
                throw new IOException(buildErrorMessage(encodeResultEncodeReference.getError(), str4));
            }
            setValue(encodeResultEncodeReference);
            return;
        }
        if (resourceEntryEncodeAttributeName != null) {
            resourceEntryEncodeAttributeName = resourceEntryEncodeAttributeName.resolveReference();
        }
        if (resourceEntryEncodeAttributeName == null || resourceEntryEncodeAttributeName.isEmpty()) {
            EncodeResult encodeResultEncode = ValueCoder.encode(str4);
            if (encodeResultEncode != null) {
                setValue(encodeResultEncode);
                return;
            } else {
                setValueAsString(XmlSanitizer.unEscapeSpecialCharacter(str4));
                return;
            }
        }
        EncodeResult encodeResultEncode2 = AttributeBag.create(resourceEntryEncodeAttributeName.get()).encode(str4);
        if (encodeResultEncode2 != null) {
            if (encodeResultEncode2.valueType == ValueType.STRING) {
                setValueAsString(XmlSanitizer.unEscapeSpecialCharacter(str4));
                return;
            } else if (!encodeResultEncode2.isError()) {
                setValue(encodeResultEncode2);
                return;
            } else {
                if (z) {
                    throw new IOException(buildErrorMessage(encodeResultEncode2.getError(), str4));
                }
                logEncodeError(encodeResultEncode2, str4);
            }
        }
        EncodeResult encodeResultEncode3 = ValueCoder.encode(str4);
        if (encodeResultEncode3 != null) {
            setValue(encodeResultEncode3);
        } else {
            setValueAsString(XmlSanitizer.unEscapeSpecialCharacter(str4));
        }
    }

    private void logEncodeError(EncodeResult encodeResult, String str) {
        System.out.println(buildErrorMessage(encodeResult.getError(), str));
    }

    private String buildErrorMessage(String str, String str2) {
        ResXmlElement parentElement = getParentElement();
        return str + ", at line = " + parentElement.getStartLineNumber() + ", <" + parentElement.getName(true) + " " + getName(true) + "=\"" + str2 + "\"";
    }

    @Override // com.reandroid.arsc.value.ValueItem, com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("name", getName());
        json.put("id", getNameId());
        json.put(NAME_namespace_uri, getUri());
        return json;
    }

    @Override // com.reandroid.arsc.value.ValueItem, com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        setName(jSONObject.optString("name", ""), jSONObject.optInt("id", 0));
        String strOptString = jSONObject.optString(NAME_namespace_uri, null);
        if (strOptString != null) {
            ResXmlNamespace startNamespaceByUri = getParentElement().getStartNamespaceByUri(strOptString);
            if (startNamespaceByUri == null) {
                startNamespaceByUri = getParentElement().getRootElement().getOrCreateNamespace(strOptString, "");
            }
            setNamespaceReference(startNamespaceByUri.getUriReference());
        }
        super.fromJson(jSONObject);
    }

    public XMLAttribute decodeToXml() {
        return toXml(true);
    }

    public XMLAttribute toXml() {
        return toXml(false);
    }

    public XMLAttribute toXml(boolean z) {
        if (z) {
            return new XMLAttribute(decodeName(false), decodeValue());
        }
        return new XMLAttribute(getName(false), decodeValue(false));
    }

    @Override // com.reandroid.arsc.value.ValueItem
    public String toString() {
        String strValueOf;
        String name2 = getName(true);
        if (name2 != null) {
            int nameId = getNameId();
            if (nameId != 0) {
                name2 = name2 + "(@" + HexUtil.toHex8(nameId) + Constant.AFTER_QUTO;
            }
            ValueType valueType = getValueType();
            if (valueType == ValueType.STRING) {
                strValueOf = getValueAsString();
            } else if (valueType == ValueType.BOOLEAN) {
                strValueOf = String.valueOf(getValueAsBoolean());
            } else if (valueType == ValueType.DEC) {
                strValueOf = String.valueOf(getData());
            } else {
                strValueOf = "[" + valueType + "] " + HexUtil.toHex8(getData());
            }
            return strValueOf != null ? name2 + "=\"" + strValueOf + "\"" : name2 + "[" + valueType + "]=\"" + getData() + "\"";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(": ");
        sb.append(getIndex());
        sb.append("{NamespaceReference=").append(getNamespaceReference());
        sb.append(", NameReference=").append(getNameReference());
        sb.append(", ValueStringReference=").append(getValueStringReference());
        sb.append(", ValueSize=").append(getSize());
        sb.append(", ValueTypeByte=").append(getType() & 255);
        sb.append(", Data=").append(getData());
        sb.append("}");
        return sb.toString();
    }
}
