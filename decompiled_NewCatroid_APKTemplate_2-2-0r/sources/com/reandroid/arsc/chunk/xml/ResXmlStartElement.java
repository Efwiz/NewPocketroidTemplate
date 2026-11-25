package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.array.ResXmlAttributeArray;
import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.item.ShortItem;
import com.reandroid.utils.StringsUtil;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlStartElement extends BaseXmlChunk {
    private static final short ATTRIBUTES_DEFAULT_START = 20;
    private static final short ATTRIBUTES_UNIT_SIZE = 20;
    private static final String ATTRIBUTE_NAME_CLASS = "class";
    private static final String ATTRIBUTE_NAME_STYLE = "style";
    private static final int ATTRIBUTE_RESOURCE_ID_id = 16842960;
    private final ResXmlAttributeArray mAttributeArray;
    private final ShortItem mAttributeCount;
    private final ShortItem mAttributeStart;
    private final ShortItem mAttributeUnitSize;
    private final ShortItem mClassAttributePosition;
    private final ShortItem mIdAttributePosition;
    private ResXmlEndElement mResXmlEndElement;
    private final ShortItem mStyleAttributePosition;

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ String getComment() {
        return super.getComment();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ int getCommentReference() {
        return super.getCommentReference();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ int getLineNumber() {
        return super.getLineNumber();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ ResXmlElement getParentResXmlElement() {
        return super.getParentResXmlElement();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ void setComment(String str) {
        super.setComment(str);
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ void setCommentReference(int i) {
        super.setCommentReference(i);
    }

    public ResXmlStartElement() {
        super(ChunkType.XML_START_ELEMENT, 7);
        ShortItem shortItem = new ShortItem((short) 20);
        this.mAttributeStart = shortItem;
        ShortItem shortItem2 = new ShortItem((short) 20);
        this.mAttributeUnitSize = shortItem2;
        ShortItem shortItem3 = new ShortItem();
        this.mAttributeCount = shortItem3;
        ShortItem shortItem4 = new ShortItem();
        this.mIdAttributePosition = shortItem4;
        ShortItem shortItem5 = new ShortItem();
        this.mClassAttributePosition = shortItem5;
        ShortItem shortItem6 = new ShortItem();
        this.mStyleAttributePosition = shortItem6;
        ResXmlAttributeArray resXmlAttributeArray = new ResXmlAttributeArray(getHeaderBlock(), shortItem, shortItem3, shortItem2);
        this.mAttributeArray = resXmlAttributeArray;
        addChild(shortItem);
        addChild(shortItem2);
        addChild(shortItem3);
        addChild(shortItem4);
        addChild(shortItem5);
        addChild(shortItem6);
        addChild(resXmlAttributeArray);
    }

    public int removeUndefinedAttributes() {
        return getResXmlAttributeArray().removeUndefinedAttributes();
    }

    public ResXmlAttribute getIdAttribute() {
        return (ResXmlAttribute) getResXmlAttributeArray().get(this.mIdAttributePosition.unsignedInt() - 1);
    }

    public ResXmlAttribute getClassAttribute() {
        return (ResXmlAttribute) getResXmlAttributeArray().get(this.mClassAttributePosition.unsignedInt() - 1);
    }

    public ResXmlAttribute getStyleAttribute() {
        return (ResXmlAttribute) getResXmlAttributeArray().get(this.mStyleAttributePosition.unsignedInt() - 1);
    }

    void setAttributesUnitSize(int i) {
        this.mAttributeArray.setAttributesUnitSize(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ResXmlAttribute newAttribute() {
        return (ResXmlAttribute) getResXmlAttributeArray().createNext();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    void linkStringReferences() {
        super.linkStringReferences();
        ResXmlEndElement resXmlEndElement = getResXmlEndElement();
        if (resXmlEndElement != null) {
            resXmlEndElement.linkStringReferences();
        }
        linkNamespace();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    void onRemoved() {
        super.onRemoved();
        unlinkNamespace();
        ResXmlEndElement resXmlEndElement = getResXmlEndElement();
        if (resXmlEndElement != null) {
            resXmlEndElement.onRemoved();
        }
        Iterator<ResXmlAttribute> it = iterator();
        while (it.hasNext()) {
            it.next().onRemoved();
        }
    }

    @Override // com.reandroid.arsc.base.BlockContainer
    protected void onPreRefresh() {
        sortAttributes();
    }

    void unlinkNamespace() {
        ResXmlStartNamespace resXmlStartNamespace = getResXmlStartNamespace();
        if (resXmlStartNamespace != null) {
            resXmlStartNamespace.removeElementReference(this);
        }
    }

    void linkNamespace() {
        ResXmlStartNamespace resXmlStartNamespace = getResXmlStartNamespace();
        if (resXmlStartNamespace != null) {
            resXmlStartNamespace.addElementReference(this);
        }
    }

    private void sortAttributes() {
        ResXmlAttributeArray resXmlAttributeArray = getResXmlAttributeArray();
        ResXmlAttribute resXmlAttribute = (ResXmlAttribute) resXmlAttributeArray.get(this.mIdAttributePosition.get() - 1);
        ResXmlAttribute resXmlAttribute2 = (ResXmlAttribute) resXmlAttributeArray.get(this.mClassAttributePosition.get() - 1);
        ResXmlAttribute resXmlAttribute3 = (ResXmlAttribute) resXmlAttributeArray.get(this.mStyleAttributePosition.get() - 1);
        resXmlAttributeArray.sortAttributes();
        if (resXmlAttribute != null) {
            this.mIdAttributePosition.set(resXmlAttribute.getIndex() + 1);
        }
        if (resXmlAttribute2 != null) {
            this.mClassAttributePosition.set(resXmlAttribute2.getIndex() + 1);
            if (!"class".equals(resXmlAttribute2.getName())) {
                resXmlAttribute2.setName("class", 0);
            }
        }
        if (resXmlAttribute3 != null) {
            this.mStyleAttributePosition.set(resXmlAttribute3.getIndex() + 1);
            if (ATTRIBUTE_NAME_STYLE.equals(resXmlAttribute3.getName())) {
                return;
            }
            resXmlAttribute3.setName(ATTRIBUTE_NAME_STYLE, 0);
        }
    }

    void calculatePositions() {
        ResXmlAttribute attribute = getAttribute(16842960);
        ResXmlAttribute noIdAttribute = getNoIdAttribute("class");
        ResXmlAttribute noIdAttribute2 = getNoIdAttribute(ATTRIBUTE_NAME_STYLE);
        if (attribute != null) {
            this.mIdAttributePosition.set(attribute.getIndex() + 1);
        }
        if (noIdAttribute != null) {
            this.mClassAttributePosition.set(noIdAttribute.getIndex() + 1);
        }
        if (noIdAttribute2 != null) {
            this.mStyleAttributePosition.set(noIdAttribute2.getIndex() + 1);
        }
    }

    public ResXmlAttribute getAttribute(int i) {
        for (ResXmlAttribute resXmlAttribute : this) {
            if (i == resXmlAttribute.getNameId()) {
                return resXmlAttribute;
            }
        }
        return null;
    }

    private ResXmlAttribute getNoIdAttribute(String str) {
        for (ResXmlAttribute resXmlAttribute : this) {
            if (resXmlAttribute.getNameId() == 0 && str.equals(resXmlAttribute.getName())) {
                return resXmlAttribute;
            }
        }
        return null;
    }

    public ResXmlAttribute getAttribute(String str, String str2) {
        if (str2 == null) {
            return null;
        }
        for (ResXmlAttribute resXmlAttribute : this) {
            if (resXmlAttribute.equalsName(str2) && (str == null || str.equals(resXmlAttribute.getUri()))) {
                return resXmlAttribute;
            }
        }
        return null;
    }

    public ResXmlAttribute searchAttributeByName(String str) {
        ResXmlAttribute resXmlAttribute = null;
        if (str == null) {
            return null;
        }
        for (ResXmlAttribute resXmlAttribute2 : getResXmlAttributeArray()) {
            if (resXmlAttribute2.equalsName(str)) {
                if (resXmlAttribute2.getNameId() == 0) {
                    return resXmlAttribute2;
                }
                resXmlAttribute = resXmlAttribute2;
            }
        }
        return resXmlAttribute;
    }

    public ResXmlAttribute searchAttributeByResourceId(int i) {
        if (i == 0) {
            return null;
        }
        for (ResXmlAttribute resXmlAttribute : this) {
            if (i == resXmlAttribute.getNameId()) {
                return resXmlAttribute;
            }
        }
        return null;
    }

    public String getTagName() {
        return getTagName(true);
    }

    public String getTagName(boolean z) {
        String prefix;
        String name2 = getName();
        return (!z || (prefix = getPrefix()) == null) ? name2 : prefix + ":" + name2;
    }

    public String getName(boolean z) {
        String prefix;
        String name2 = super.getName();
        return (!z || (prefix = getPrefix()) == null) ? name2 : prefix + ":" + name2;
    }

    public void setName(String str) {
        if (str == null) {
            setStringReference(-1);
        } else {
            setString(str);
        }
        ResXmlEndElement resXmlEndElement = getResXmlEndElement();
        if (resXmlEndElement != null) {
            resXmlEndElement.setString(str);
        }
    }

    public Iterator<ResXmlAttribute> iterator() {
        return getResXmlAttributeArray().iterator();
    }

    public ResXmlAttributeArray getResXmlAttributeArray() {
        return this.mAttributeArray;
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public String getUri() {
        ResXmlStartNamespace resXmlStartNamespace = getResXmlStartNamespace();
        if (resXmlStartNamespace != null) {
            return resXmlStartNamespace.getUri();
        }
        return null;
    }

    public String getPrefix() {
        ResXmlStartNamespace resXmlStartNamespace = getResXmlStartNamespace();
        if (resXmlStartNamespace != null) {
            return resXmlStartNamespace.getPrefix();
        }
        return null;
    }

    public void setTagNamespace(String str, String str2) {
        unlinkNamespace();
        if (str == null || str2 == null) {
            setNamespaceReference(-1);
            return;
        }
        ResXmlElement parentResXmlElement = getParentResXmlElement();
        if (parentResXmlElement == null) {
            return;
        }
        setNamespaceReference(parentResXmlElement.getOrCreateNamespace(str, str2).getUriReference());
        linkNamespace();
    }

    ResXmlStartNamespace getResXmlStartNamespace() {
        ResXmlElement parentResXmlElement;
        int namespaceReference = getNamespaceReference();
        if (namespaceReference >= 0 && (parentResXmlElement = getParentResXmlElement()) != null) {
            return parentResXmlElement.getStartNamespaceByUriRef(namespaceReference);
        }
        return null;
    }

    public void setResXmlEndElement(ResXmlEndElement resXmlEndElement) {
        this.mResXmlEndElement = resXmlEndElement;
    }

    public ResXmlEndElement getResXmlEndElement() {
        return this.mResXmlEndElement;
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk, com.reandroid.arsc.chunk.Chunk
    protected void onChunkRefreshed() {
        refreshAttributeStart();
        refreshAttributeCount();
    }

    private void refreshAttributeStart() {
        this.mAttributeStart.set(countUpTo(this.mAttributeArray) - getHeaderBlock().getHeaderSize());
    }

    private void refreshAttributeCount() {
        this.mAttributeCount.set(this.mAttributeArray.size());
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public void setLineNumber(int i) {
        super.setLineNumber(i);
        ResXmlEndElement resXmlEndElement = getResXmlEndElement();
        if (resXmlEndElement != null) {
            resXmlEndElement.setLineNumber(i);
        }
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk, com.reandroid.arsc.chunk.Chunk
    public String toString() {
        String tagName = getTagName();
        if (tagName == null) {
            return super.toString();
        }
        return tagName + " " + StringsUtil.join(iterator(), Character.valueOf(StringUtil.SPACE));
    }
}
