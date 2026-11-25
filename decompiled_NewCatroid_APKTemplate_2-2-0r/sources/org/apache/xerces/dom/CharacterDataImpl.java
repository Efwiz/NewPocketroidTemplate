package org.apache.xerces.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes13.dex */
public abstract class CharacterDataImpl extends ChildNode {
    static final long serialVersionUID = 7931170150428474230L;
    private static final transient NodeList singletonNodeList = new NodeList() { // from class: org.apache.xerces.dom.CharacterDataImpl.1
        @Override // org.w3c.dom.NodeList, org.w3c.dom.html.HTMLFormElement
        public int getLength() {
            return 0;
        }

        @Override // org.w3c.dom.NodeList
        public Node item(int i) {
            return null;
        }
    };
    protected String data;

    public CharacterDataImpl() {
    }

    protected CharacterDataImpl(CoreDocumentImpl coreDocumentImpl, String str) {
        super(coreDocumentImpl);
        this.data = str;
    }

    public void appendData(String str) {
        if (isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (str == null) {
            return;
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        setNodeValue(this.data + str);
    }

    public void deleteData(int i, int i2) throws DOMException {
        internalDeleteData(i, i2, false);
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public NodeList getChildNodes() {
        return singletonNodeList;
    }

    public String getData() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.data;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.NodeList, org.w3c.dom.html.HTMLFormElement
    public int getLength() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.data.length();
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getNodeValue() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.data;
    }

    public void insertData(int i, String str) throws DOMException {
        internalInsertData(i, str, false);
    }

    void internalDeleteData(int i, int i2, boolean z) throws DOMException {
        String strSubstring;
        CoreDocumentImpl coreDocumentImplOwnerDocument = ownerDocument();
        if (coreDocumentImplOwnerDocument.errorChecking) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (i2 < 0) {
                throw new DOMException((short) 1, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null));
            }
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        int iMax = Math.max((this.data.length() - i2) - i, 0);
        try {
            StringBuilder sbAppend = new StringBuilder().append(this.data.substring(0, i));
            if (iMax > 0) {
                int i3 = i + i2;
                strSubstring = this.data.substring(i3, iMax + i3);
            } else {
                strSubstring = "";
            }
            setNodeValueInternal(sbAppend.append(strSubstring).toString(), z);
            coreDocumentImplOwnerDocument.deletedText(this, i, i2);
        } catch (StringIndexOutOfBoundsException unused) {
            throw new DOMException((short) 1, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null));
        }
    }

    void internalInsertData(int i, String str, boolean z) throws DOMException {
        CoreDocumentImpl coreDocumentImplOwnerDocument = ownerDocument();
        if (coreDocumentImplOwnerDocument.errorChecking && isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        try {
            setNodeValueInternal(new StringBuffer(this.data).insert(i, str).toString(), z);
            coreDocumentImplOwnerDocument.insertedText(this, i, str.length());
        } catch (StringIndexOutOfBoundsException unused) {
            throw new DOMException((short) 1, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null));
        }
    }

    public void replaceData(int i, int i2, String str) throws DOMException {
        CoreDocumentImpl coreDocumentImplOwnerDocument = ownerDocument();
        if (coreDocumentImplOwnerDocument.errorChecking && isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        coreDocumentImplOwnerDocument.replacingData(this);
        String str2 = this.data;
        internalDeleteData(i, i2, true);
        internalInsertData(i, str, true);
        coreDocumentImplOwnerDocument.replacedCharacterData(this, str2, this.data);
    }

    public void setData(String str) throws DOMException {
        setNodeValue(str);
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public void setNodeValue(String str) {
        setNodeValueInternal(str);
        ownerDocument().replacedText(this);
    }

    protected void setNodeValueInternal(String str) {
        setNodeValueInternal(str, false);
    }

    protected void setNodeValueInternal(String str, boolean z) {
        CoreDocumentImpl coreDocumentImplOwnerDocument = ownerDocument();
        if (coreDocumentImplOwnerDocument.errorChecking && isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        String str2 = this.data;
        coreDocumentImplOwnerDocument.modifyingCharacterData(this, z);
        this.data = str;
        coreDocumentImplOwnerDocument.modifiedCharacterData(this, str2, str, z);
    }

    public String substringData(int i, int i2) throws DOMException {
        if (needsSyncData()) {
            synchronizeData();
        }
        int length = this.data.length();
        if (i2 < 0 || i < 0 || i > length - 1) {
            throw new DOMException((short) 1, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null));
        }
        return this.data.substring(i, Math.min(i2 + i, length));
    }
}
