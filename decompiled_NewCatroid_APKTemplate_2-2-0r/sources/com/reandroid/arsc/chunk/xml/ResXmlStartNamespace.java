package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.model.ResourceLibrary;
import com.reandroid.xml.XMLNamespace;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlStartNamespace extends ResXmlNamespaceChunk {
    private final Set<ResXmlAttribute> mReferencedAttributes;
    private final Set<ResXmlStartElement> mReferencedElements;

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

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.common.Namespace
    public /* bridge */ /* synthetic */ String getPrefix() {
        return super.getPrefix();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk
    public /* bridge */ /* synthetic */ int getPrefixReference() {
        return super.getPrefixReference();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ String getUri() {
        return super.getUri();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.ResXmlNamespace
    public /* bridge */ /* synthetic */ int getUriReference() {
        return super.getUriReference();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ void setComment(String str) {
        super.setComment(str);
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ void setCommentReference(int i) {
        super.setCommentReference(i);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ void setLineNumber(int i) {
        super.setLineNumber(i);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.ResXmlNamespace
    public /* bridge */ /* synthetic */ void setPrefix(String str) {
        super.setPrefix(str);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk
    public /* bridge */ /* synthetic */ void setPrefixReference(int i) {
        super.setPrefixReference(i);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.ResXmlNamespace
    public /* bridge */ /* synthetic */ void setUri(String str) {
        super.setUri(str);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.BaseXmlChunk, com.reandroid.arsc.chunk.Chunk
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public ResXmlStartNamespace() {
        super(ChunkType.XML_START_NAMESPACE);
        this.mReferencedAttributes = new HashSet();
        this.mReferencedElements = new HashSet();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk
    void onUriReferenceChanged(int i, int i2) {
        Iterator<ResXmlAttribute> it = this.mReferencedAttributes.iterator();
        while (it.hasNext()) {
            it.next().setUriReference(i2);
        }
        Iterator<ResXmlStartElement> it2 = this.mReferencedElements.iterator();
        while (it2.hasNext()) {
            it2.next().setNamespaceReference(i2);
        }
    }

    ResXmlEndNamespace getEnd() {
        return (ResXmlEndNamespace) getPair();
    }

    void setEnd(ResXmlEndNamespace resXmlEndNamespace) {
        setPair(resXmlEndNamespace);
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    void linkStringReferences() {
        super.linkStringReferences();
        ResXmlEndNamespace end = getEnd();
        if (end != null) {
            end.linkStringReferences();
        }
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    void onRemoved() {
        ResXmlEndNamespace end = getEnd();
        if (end != null) {
            end.onRemoved();
        }
        this.mReferencedAttributes.clear();
        this.mReferencedElements.clear();
    }

    public boolean hasReferences() {
        return this.mReferencedAttributes.size() > 0 || this.mReferencedElements.size() > 0;
    }

    public Iterator<ResXmlAttribute> getReferencedAttributes() {
        return this.mReferencedAttributes.iterator();
    }

    void addAttributeReference(ResXmlAttribute resXmlAttribute) {
        if (resXmlAttribute != null) {
            this.mReferencedAttributes.add(resXmlAttribute);
        }
    }

    void removeAttributeReference(ResXmlAttribute resXmlAttribute) {
        if (resXmlAttribute != null) {
            this.mReferencedAttributes.remove(resXmlAttribute);
        }
    }

    void addElementReference(ResXmlStartElement resXmlStartElement) {
        if (resXmlStartElement != null) {
            this.mReferencedElements.add(resXmlStartElement);
        }
    }

    void removeElementReference(ResXmlStartElement resXmlStartElement) {
        if (resXmlStartElement != null) {
            this.mReferencedElements.remove(resXmlStartElement);
        }
    }

    boolean removeIfNoReference() {
        ResXmlElement parentResXmlElement;
        if (hasReferences() || (parentResXmlElement = getParentResXmlElement()) == null) {
            return false;
        }
        parentResXmlElement.removeNamespace(this);
        return true;
    }

    public XMLNamespace decodeToXml() {
        String uri = getUri();
        String prefix = getPrefix();
        if (isEmpty(uri) || isEmpty(prefix)) {
            return null;
        }
        return new XMLNamespace(uri, prefix);
    }

    boolean fixEmpty() {
        boolean zFixEmptyPrefix = fixEmptyPrefix();
        if (fixEmptyUri()) {
            return true;
        }
        return zFixEmptyPrefix;
    }

    private boolean fixEmptyPrefix() {
        if (!isEmpty(getPrefix())) {
            return false;
        }
        setPrefix("ns" + getIndex());
        return true;
    }

    private boolean fixEmptyUri() {
        if (!isEmpty(getUri())) {
            return false;
        }
        setUri(ResourceLibrary.URI_RES_AUTO);
        return true;
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
