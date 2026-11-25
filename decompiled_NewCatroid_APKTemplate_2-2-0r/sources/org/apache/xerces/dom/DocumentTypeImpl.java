package org.apache.xerces.dom;

import java.util.Hashtable;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

/* JADX INFO: loaded from: classes13.dex */
public class DocumentTypeImpl extends ParentNode implements DocumentType {
    static final long serialVersionUID = 7751299192316526485L;
    private int doctypeNumber;
    protected NamedNodeMapImpl elements;
    protected NamedNodeMapImpl entities;
    protected String internalSubset;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f466name;
    protected NamedNodeMapImpl notations;
    protected String publicID;
    protected String systemID;
    private Hashtable userData;

    public DocumentTypeImpl(CoreDocumentImpl coreDocumentImpl, String str) {
        super(coreDocumentImpl);
        this.doctypeNumber = 0;
        this.userData = null;
        this.f466name = str;
        this.entities = new NamedNodeMapImpl(this);
        this.notations = new NamedNodeMapImpl(this);
        this.elements = new NamedNodeMapImpl(this);
    }

    public DocumentTypeImpl(CoreDocumentImpl coreDocumentImpl, String str, String str2, String str3) {
        this(coreDocumentImpl, str);
        this.publicID = str2;
        this.systemID = str3;
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.ChildNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node cloneNode(boolean z) {
        DocumentTypeImpl documentTypeImpl = (DocumentTypeImpl) super.cloneNode(z);
        documentTypeImpl.entities = this.entities.cloneMap(documentTypeImpl);
        documentTypeImpl.notations = this.notations.cloneMap(documentTypeImpl);
        documentTypeImpl.elements = this.elements.cloneMap(documentTypeImpl);
        return documentTypeImpl;
    }

    public NamedNodeMap getElements() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this.elements;
    }

    @Override // org.w3c.dom.DocumentType
    public NamedNodeMap getEntities() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this.entities;
    }

    @Override // org.w3c.dom.DocumentType
    public String getInternalSubset() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.internalSubset;
    }

    @Override // org.w3c.dom.DocumentType
    public String getName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.f466name;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getNodeName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.f466name;
    }

    @Override // org.apache.xerces.dom.NodeImpl
    protected int getNodeNumber() {
        if (getOwnerDocument() != null) {
            return super.getNodeNumber();
        }
        if (this.doctypeNumber == 0) {
            this.doctypeNumber = ((CoreDOMImplementationImpl) CoreDOMImplementationImpl.getDOMImplementation()).assignDocTypeNumber();
        }
        return this.doctypeNumber;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 10;
    }

    @Override // org.w3c.dom.DocumentType
    public NamedNodeMap getNotations() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this.notations;
    }

    @Override // org.w3c.dom.DocumentType
    public String getPublicId() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.publicID;
    }

    @Override // org.w3c.dom.DocumentType
    public String getSystemId() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.systemID;
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getTextContent() throws DOMException {
        return null;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Object getUserData(String str) {
        Object obj;
        Hashtable hashtable = this.userData;
        if (hashtable == null || (obj = hashtable.get(str)) == null) {
            return null;
        }
        return ((ParentNode.UserDataRecord) obj).fData;
    }

    @Override // org.apache.xerces.dom.NodeImpl
    protected Hashtable getUserDataRecord() {
        return this.userData;
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public boolean isEqualNode(Node node) {
        if (!super.isEqualNode(node)) {
            return false;
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        DocumentTypeImpl documentTypeImpl = (DocumentTypeImpl) node;
        if ((getPublicId() == null && documentTypeImpl.getPublicId() != null) || ((getPublicId() != null && documentTypeImpl.getPublicId() == null) || ((getSystemId() == null && documentTypeImpl.getSystemId() != null) || ((getSystemId() != null && documentTypeImpl.getSystemId() == null) || ((getInternalSubset() == null && documentTypeImpl.getInternalSubset() != null) || (getInternalSubset() != null && documentTypeImpl.getInternalSubset() == null)))))) {
            return false;
        }
        if (getPublicId() != null && !getPublicId().equals(documentTypeImpl.getPublicId())) {
            return false;
        }
        if (getSystemId() != null && !getSystemId().equals(documentTypeImpl.getSystemId())) {
            return false;
        }
        if (getInternalSubset() != null && !getInternalSubset().equals(documentTypeImpl.getInternalSubset())) {
            return false;
        }
        NamedNodeMapImpl namedNodeMapImpl = documentTypeImpl.entities;
        NamedNodeMapImpl namedNodeMapImpl2 = this.entities;
        if ((namedNodeMapImpl2 == null && namedNodeMapImpl != null) || (namedNodeMapImpl2 != null && namedNodeMapImpl == null)) {
            return false;
        }
        if (namedNodeMapImpl2 != null && namedNodeMapImpl != null) {
            if (namedNodeMapImpl2.getLength() != namedNodeMapImpl.getLength()) {
                return false;
            }
            for (int i = 0; this.entities.item(i) != null; i++) {
                Node nodeItem = this.entities.item(i);
                if (!((NodeImpl) nodeItem).isEqualNode(namedNodeMapImpl.getNamedItem(nodeItem.getNodeName()))) {
                    return false;
                }
            }
        }
        NamedNodeMapImpl namedNodeMapImpl3 = documentTypeImpl.notations;
        NamedNodeMapImpl namedNodeMapImpl4 = this.notations;
        if ((namedNodeMapImpl4 == null && namedNodeMapImpl3 != null) || (namedNodeMapImpl4 != null && namedNodeMapImpl3 == null)) {
            return false;
        }
        if (namedNodeMapImpl4 == null || namedNodeMapImpl3 == null) {
            return true;
        }
        if (namedNodeMapImpl4.getLength() != namedNodeMapImpl3.getLength()) {
            return false;
        }
        for (int i2 = 0; this.notations.item(i2) != null; i2++) {
            Node nodeItem2 = this.notations.item(i2);
            if (!((NodeImpl) nodeItem2).isEqualNode(namedNodeMapImpl3.getNamedItem(nodeItem2.getNodeName()))) {
                return false;
            }
        }
        return true;
    }

    public void setInternalSubset(String str) {
        if (needsSyncData()) {
            synchronizeData();
        }
        this.internalSubset = str;
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl
    protected void setOwnerDocument(CoreDocumentImpl coreDocumentImpl) {
        super.setOwnerDocument(coreDocumentImpl);
        this.entities.setOwnerDocument(coreDocumentImpl);
        this.notations.setOwnerDocument(coreDocumentImpl);
        this.elements.setOwnerDocument(coreDocumentImpl);
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl
    public void setReadOnly(boolean z, boolean z2) {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        super.setReadOnly(z, z2);
        this.elements.setReadOnly(z, true);
        this.entities.setReadOnly(z, true);
        this.notations.setReadOnly(z, true);
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public void setTextContent(String str) throws DOMException {
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Object setUserData(String str, Object obj, UserDataHandler userDataHandler) {
        Object objPut;
        if (this.userData == null) {
            this.userData = new Hashtable();
        }
        if (obj == null) {
            Hashtable hashtable = this.userData;
            if (hashtable == null || (objPut = hashtable.remove(str)) == null) {
                return null;
            }
        } else {
            objPut = this.userData.put(str, new ParentNode.UserDataRecord(obj, userDataHandler));
            if (objPut == null) {
                return null;
            }
        }
        return ((ParentNode.UserDataRecord) objPut).fData;
    }
}
