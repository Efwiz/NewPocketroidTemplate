package org.apache.xerces.dom;

import com.gaurav.avnc.vnc.XKeySym;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.WeakHashMap;
import javax.xml.XMLConstants;
import org.apache.xerces.util.URI;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.NamespaceContext;
import org.catrobat.catroid.BuildConfig;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/* JADX INFO: loaded from: classes13.dex */
public class CoreDocumentImpl extends ParentNode implements Document {
    private static final int[] kidOK = {0, XKeySym.XK_scedilla, 40, 0, 0, XKeySym.XK_scedilla, XKeySym.XK_scedilla, 0, 0, 1410, 0, XKeySym.XK_scedilla, 0};
    static final long serialVersionUID = 0;
    protected String actualEncoding;
    protected boolean allowGrammarAccess;
    protected int changes;
    protected ElementImpl docElement;
    protected DocumentTypeImpl docType;
    private int documentNumber;
    transient DOMNormalizer domNormalizer;
    protected String encoding;
    protected boolean errorChecking;
    transient DOMConfigurationImpl fConfiguration;
    protected String fDocumentURI;
    transient NodeListCache fFreeNLCache;
    transient Object fXPathEvaluator;
    protected Hashtable identifiers;
    private int nodeCounter;
    private Map nodeTable;
    protected boolean standalone;
    protected Map userData;
    protected String version;
    private boolean xml11Version;
    protected boolean xmlVersionChanged;

    public CoreDocumentImpl() {
        this(false);
    }

    public CoreDocumentImpl(DocumentType documentType) {
        this(documentType, false);
    }

    public CoreDocumentImpl(DocumentType documentType, boolean z) {
        this(z);
        if (documentType != null) {
            try {
                ((DocumentTypeImpl) documentType).ownerDocument = this;
                appendChild(documentType);
            } catch (ClassCastException unused) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
    }

    public CoreDocumentImpl(boolean z) {
        super(null);
        this.domNormalizer = null;
        this.fConfiguration = null;
        this.fXPathEvaluator = null;
        this.changes = 0;
        this.errorChecking = true;
        this.xmlVersionChanged = false;
        this.documentNumber = 0;
        this.nodeCounter = 0;
        this.xml11Version = false;
        this.ownerDocument = this;
        this.allowGrammarAccess = z;
    }

    /* JADX WARN: Code duplicated, block: B:48:0x0162  */
    /* JADX WARN: Code duplicated, block: B:79:0x01dc  */
    /* JADX WARN: Code duplicated, block: B:81:0x01e2  */
    /* JADX WARN: Code duplicated, block: B:83:0x01e8 A[LOOP:0: B:82:0x01e6->B:83:0x01e8, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:86:0x01fb  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [org.apache.xerces.dom.CoreDocumentImpl] */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v14, types: [org.apache.xerces.dom.EntityImpl] */
    /* JADX WARN: Type inference failed for: r2v16, types: [org.w3c.dom.ProcessingInstruction] */
    /* JADX WARN: Type inference failed for: r2v17, types: [org.w3c.dom.Comment] */
    /* JADX WARN: Type inference failed for: r2v20, types: [org.apache.xerces.dom.DocumentTypeImpl] */
    /* JADX WARN: Type inference failed for: r2v21, types: [org.w3c.dom.DocumentFragment] */
    /* JADX WARN: Type inference failed for: r2v22, types: [org.w3c.dom.Node] */
    /* JADX WARN: Type inference failed for: r2v25, types: [org.apache.xerces.dom.NotationImpl] */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r2v29 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.Object, org.w3c.dom.Element] */
    /* JADX WARN: Type inference failed for: r2v30 */
    /* JADX WARN: Type inference failed for: r2v7, types: [org.w3c.dom.Node] */
    /* JADX WARN: Type inference failed for: r2v8, types: [org.w3c.dom.Text] */
    /* JADX WARN: Type inference failed for: r2v9, types: [org.w3c.dom.CDATASection] */
    private Node importNode(Node node, boolean z, boolean z2, HashMap map) throws DOMException {
        ?? CreateElement;
        Object obj;
        Node firstChild;
        Hashtable userDataRecord = node instanceof NodeImpl ? ((NodeImpl) node).getUserDataRecord() : null;
        int i = 0;
        switch (node.getNodeType()) {
            case 1:
                boolean zHasFeature = node.getOwnerDocument().getImplementation().hasFeature("XML", "2.0");
                CreateElement = (!zHasFeature || node.getLocalName() == null) ? createElement(node.getNodeName()) : createElementNS(node.getNamespaceURI(), node.getNodeName());
                NamedNodeMap attributes = node.getAttributes();
                if (attributes != null) {
                    int length = attributes.getLength();
                    while (i < length) {
                        Attr attr = (Attr) attributes.item(i);
                        if (attr.getSpecified() || z2) {
                            Attr attr2 = (Attr) importNode(attr, true, z2, map);
                            if (!zHasFeature || attr.getLocalName() == null) {
                                CreateElement.setAttributeNode(attr2);
                            } else {
                                CreateElement.setAttributeNodeNS(attr2);
                            }
                        }
                        i++;
                    }
                }
                if (map != null && (obj = map.get(node)) != null) {
                    if (this.identifiers == null) {
                        this.identifiers = new Hashtable();
                    }
                    this.identifiers.put(obj, CreateElement);
                }
                if (userDataRecord != null) {
                    callUserDataHandlers(node, CreateElement, (short) 2, userDataRecord);
                }
                if (z) {
                    for (firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                        CreateElement.appendChild(importNode(firstChild, true, z2, map));
                    }
                }
                if (CreateElement.getNodeType() == 6) {
                    ((NodeImpl) CreateElement).setReadOnly(true, true);
                }
                return CreateElement;
            case 2:
                CreateElement = (!node.getOwnerDocument().getImplementation().hasFeature("XML", "2.0") || node.getLocalName() == null) ? createAttribute(node.getNodeName()) : createAttributeNS(node.getNamespaceURI(), node.getNodeName());
                if (node instanceof AttrImpl) {
                    AttrImpl attrImpl = (AttrImpl) node;
                    if (attrImpl.hasStringValue()) {
                        ((AttrImpl) CreateElement).setValue(attrImpl.getValue());
                        CreateElement = CreateElement;
                        z = false;
                    } else {
                        z = true;
                    }
                } else if (node.getFirstChild() == null) {
                    CreateElement.setNodeValue(node.getNodeValue());
                    CreateElement = CreateElement;
                    z = false;
                } else {
                    z = true;
                }
                if (userDataRecord != null) {
                    callUserDataHandlers(node, CreateElement, (short) 2, userDataRecord);
                }
                if (z) {
                    while (firstChild != null) {
                        CreateElement.appendChild(importNode(firstChild, true, z2, map));
                    }
                }
                if (CreateElement.getNodeType() == 6) {
                    ((NodeImpl) CreateElement).setReadOnly(true, true);
                }
                return CreateElement;
            case 3:
                CreateElement = createTextNode(node.getNodeValue());
                if (userDataRecord != null) {
                    callUserDataHandlers(node, CreateElement, (short) 2, userDataRecord);
                }
                if (z) {
                    while (firstChild != null) {
                        CreateElement.appendChild(importNode(firstChild, true, z2, map));
                    }
                }
                if (CreateElement.getNodeType() == 6) {
                    ((NodeImpl) CreateElement).setReadOnly(true, true);
                }
                return CreateElement;
            case 4:
                CreateElement = createCDATASection(node.getNodeValue());
                if (userDataRecord != null) {
                    callUserDataHandlers(node, CreateElement, (short) 2, userDataRecord);
                }
                if (z) {
                    while (firstChild != null) {
                        CreateElement.appendChild(importNode(firstChild, true, z2, map));
                    }
                }
                if (CreateElement.getNodeType() == 6) {
                    ((NodeImpl) CreateElement).setReadOnly(true, true);
                }
                return CreateElement;
            case 5:
                CreateElement = createEntityReference(node.getNodeName());
                z = false;
                if (userDataRecord != null) {
                    callUserDataHandlers(node, CreateElement, (short) 2, userDataRecord);
                }
                if (z) {
                    while (firstChild != null) {
                        CreateElement.appendChild(importNode(firstChild, true, z2, map));
                    }
                }
                if (CreateElement.getNodeType() == 6) {
                    ((NodeImpl) CreateElement).setReadOnly(true, true);
                }
                return CreateElement;
            case 6:
                Entity entity = (Entity) node;
                CreateElement = (EntityImpl) createEntity(node.getNodeName());
                CreateElement.setPublicId(entity.getPublicId());
                CreateElement.setSystemId(entity.getSystemId());
                CreateElement.setNotationName(entity.getNotationName());
                CreateElement.isReadOnly(false);
                if (userDataRecord != null) {
                    callUserDataHandlers(node, CreateElement, (short) 2, userDataRecord);
                }
                if (z) {
                    while (firstChild != null) {
                        CreateElement.appendChild(importNode(firstChild, true, z2, map));
                    }
                }
                if (CreateElement.getNodeType() == 6) {
                    ((NodeImpl) CreateElement).setReadOnly(true, true);
                }
                return CreateElement;
            case 7:
                CreateElement = createProcessingInstruction(node.getNodeName(), node.getNodeValue());
                if (userDataRecord != null) {
                    callUserDataHandlers(node, CreateElement, (short) 2, userDataRecord);
                }
                if (z) {
                    while (firstChild != null) {
                        CreateElement.appendChild(importNode(firstChild, true, z2, map));
                    }
                }
                if (CreateElement.getNodeType() == 6) {
                    ((NodeImpl) CreateElement).setReadOnly(true, true);
                }
                return CreateElement;
            case 8:
                CreateElement = createComment(node.getNodeValue());
                if (userDataRecord != null) {
                    callUserDataHandlers(node, CreateElement, (short) 2, userDataRecord);
                }
                if (z) {
                    while (firstChild != null) {
                        CreateElement.appendChild(importNode(firstChild, true, z2, map));
                    }
                }
                if (CreateElement.getNodeType() == 6) {
                    ((NodeImpl) CreateElement).setReadOnly(true, true);
                }
                return CreateElement;
            case 9:
            default:
                throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null));
            case 10:
                if (!z2) {
                    throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null));
                }
                DocumentType documentType = (DocumentType) node;
                CreateElement = (DocumentTypeImpl) createDocumentType(documentType.getNodeName(), documentType.getPublicId(), documentType.getSystemId());
                CreateElement.setInternalSubset(documentType.getInternalSubset());
                NamedNodeMap entities = documentType.getEntities();
                NamedNodeMap entities2 = CreateElement.getEntities();
                if (entities != null) {
                    for (int i2 = 0; i2 < entities.getLength(); i2++) {
                        entities2.setNamedItem(importNode(entities.item(i2), true, true, map));
                    }
                }
                NamedNodeMap notations = documentType.getNotations();
                NamedNodeMap notations2 = CreateElement.getNotations();
                if (notations != null) {
                    while (i < notations.getLength()) {
                        notations2.setNamedItem(importNode(notations.item(i), true, true, map));
                        i++;
                    }
                }
                if (userDataRecord != null) {
                    callUserDataHandlers(node, CreateElement, (short) 2, userDataRecord);
                }
                if (z) {
                    while (firstChild != null) {
                        CreateElement.appendChild(importNode(firstChild, true, z2, map));
                    }
                }
                if (CreateElement.getNodeType() == 6) {
                    ((NodeImpl) CreateElement).setReadOnly(true, true);
                }
                return CreateElement;
            case 11:
                CreateElement = createDocumentFragment();
                if (userDataRecord != null) {
                    callUserDataHandlers(node, CreateElement, (short) 2, userDataRecord);
                }
                if (z) {
                    while (firstChild != null) {
                        CreateElement.appendChild(importNode(firstChild, true, z2, map));
                    }
                }
                if (CreateElement.getNodeType() == 6) {
                    ((NodeImpl) CreateElement).setReadOnly(true, true);
                }
                return CreateElement;
            case 12:
                Notation notation = (Notation) node;
                CreateElement = (NotationImpl) createNotation(node.getNodeName());
                CreateElement.setPublicId(notation.getPublicId());
                CreateElement.setSystemId(notation.getSystemId());
                if (userDataRecord != null) {
                    callUserDataHandlers(node, CreateElement, (short) 2, userDataRecord);
                }
                if (z) {
                    while (firstChild != null) {
                        CreateElement.appendChild(importNode(firstChild, true, z2, map));
                    }
                }
                if (CreateElement.getNodeType() == 6) {
                    ((NodeImpl) CreateElement).setReadOnly(true, true);
                }
                return CreateElement;
        }
    }

    public static final boolean isValidQName(String str, String str2, boolean z) {
        if (str2 == null) {
            return false;
        }
        if (z) {
            if ((str != null && !XML11Char.isXML11ValidNCName(str)) || !XML11Char.isXML11ValidNCName(str2)) {
                return false;
            }
        } else if ((str != null && !XMLChar.isValidNCName(str)) || !XMLChar.isValidNCName(str2)) {
            return false;
        }
        return true;
    }

    public static final boolean isXMLName(String str, boolean z) {
        if (str == null) {
            return false;
        }
        return !z ? XMLChar.isValidName(str) : XML11Char.isXML11ValidName(str);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        if (this.userData != null) {
            this.userData = new WeakHashMap(this.userData);
        }
        if (this.nodeTable != null) {
            this.nodeTable = new WeakHashMap(this.nodeTable);
        }
    }

    private ElementImpl replaceRenameElement(ElementImpl elementImpl, String str, String str2) {
        ElementNSImpl elementNSImpl = (ElementNSImpl) createElementNS(str, str2);
        copyEventListeners(elementImpl, elementNSImpl);
        Hashtable hashtableRemoveUserDataTable = removeUserDataTable(elementImpl);
        Node parentNode = elementImpl.getParentNode();
        Node nextSibling = elementImpl.getNextSibling();
        if (parentNode != null) {
            parentNode.removeChild(elementImpl);
        }
        while (true) {
            Node firstChild = elementImpl.getFirstChild();
            if (firstChild == null) {
                break;
            }
            elementImpl.removeChild(firstChild);
            elementNSImpl.appendChild(firstChild);
        }
        elementNSImpl.moveSpecifiedAttributes(elementImpl);
        setUserDataTable(elementNSImpl, hashtableRemoveUserDataTable);
        callUserDataHandlers(elementImpl, elementNSImpl, (short) 4);
        if (parentNode != null) {
            parentNode.insertBefore(elementNSImpl, nextSibling);
        }
        return elementNSImpl;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        Map map = this.userData;
        Map map2 = this.nodeTable;
        if (map != null) {
            try {
                this.userData = new Hashtable(map);
            } finally {
                this.userData = map;
                this.nodeTable = map2;
            }
        }
        if (map2 != null) {
            this.nodeTable = new Hashtable(map2);
        }
        objectOutputStream.defaultWriteObject();
    }

    public void abort() {
    }

    protected void addEventListener(NodeImpl nodeImpl, String str, EventListener eventListener, boolean z) {
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0034  */
    @Override // org.w3c.dom.Document
    public Node adoptNode(Node node) {
        Hashtable userDataRecord;
        Node namedItem;
        try {
            NodeImpl nodeImpl = (NodeImpl) node;
            if (node == null) {
                return null;
            }
            if (node != null && node.getOwnerDocument() != null) {
                DOMImplementation implementation = getImplementation();
                DOMImplementation implementation2 = node.getOwnerDocument().getImplementation();
                if (implementation != implementation2) {
                    if ((implementation instanceof DOMImplementationImpl) && (implementation2 instanceof DeferredDOMImplementationImpl)) {
                        undeferChildren(nodeImpl);
                    } else if (!(implementation instanceof DeferredDOMImplementationImpl) || !(implementation2 instanceof DOMImplementationImpl)) {
                        return null;
                    }
                } else if (implementation2 instanceof DeferredDOMImplementationImpl) {
                    undeferChildren(nodeImpl);
                }
            }
            short nodeType = nodeImpl.getNodeType();
            if (nodeType == 1) {
                userDataRecord = nodeImpl.getUserDataRecord();
                Node parentNode = nodeImpl.getParentNode();
                if (parentNode != null) {
                    parentNode.removeChild(node);
                }
                nodeImpl.setOwnerDocument(this);
                if (userDataRecord != null) {
                    setUserDataTable(nodeImpl, userDataRecord);
                }
                ((ElementImpl) nodeImpl).reconcileDefaultAttributes();
            } else if (nodeType == 2) {
                AttrImpl attrImpl = (AttrImpl) nodeImpl;
                if (attrImpl.getOwnerElement() != null) {
                    attrImpl.getOwnerElement().removeAttributeNode(attrImpl);
                }
                attrImpl.isSpecified(true);
                Hashtable userDataRecord2 = nodeImpl.getUserDataRecord();
                attrImpl.setOwnerDocument(this);
                if (userDataRecord2 != null) {
                    setUserDataTable(nodeImpl, userDataRecord2);
                }
                userDataRecord = userDataRecord2;
            } else {
                if (nodeType != 5) {
                    if (nodeType != 6) {
                        if (nodeType == 9 || nodeType == 10) {
                            throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null));
                        }
                        if (nodeType != 12) {
                            userDataRecord = nodeImpl.getUserDataRecord();
                            Node parentNode2 = nodeImpl.getParentNode();
                            if (parentNode2 != null) {
                                parentNode2.removeChild(node);
                            }
                            nodeImpl.setOwnerDocument(this);
                            if (userDataRecord != null) {
                                setUserDataTable(nodeImpl, userDataRecord);
                            }
                        }
                    }
                    throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
                }
                userDataRecord = nodeImpl.getUserDataRecord();
                Node parentNode3 = nodeImpl.getParentNode();
                if (parentNode3 != null) {
                    parentNode3.removeChild(node);
                }
                while (true) {
                    Node firstChild = nodeImpl.getFirstChild();
                    if (firstChild == null) {
                        break;
                    }
                    nodeImpl.removeChild(firstChild);
                }
                nodeImpl.setOwnerDocument(this);
                if (userDataRecord != null) {
                    setUserDataTable(nodeImpl, userDataRecord);
                }
                DocumentTypeImpl documentTypeImpl = this.docType;
                if (documentTypeImpl != null && (namedItem = documentTypeImpl.getEntities().getNamedItem(nodeImpl.getNodeName())) != null) {
                    for (Node firstChild2 = namedItem.getFirstChild(); firstChild2 != null; firstChild2 = firstChild2.getNextSibling()) {
                        nodeImpl.appendChild(firstChild2.cloneNode(true));
                    }
                }
            }
            if (userDataRecord != null) {
                callUserDataHandlers(node, null, (short) 5, userDataRecord);
            }
            return nodeImpl;
        } catch (ClassCastException unused) {
            return null;
        }
    }

    protected void callUserDataHandlers(Node node, Node node2, short s) {
        Hashtable userDataRecord;
        if (this.userData == null || !(node instanceof NodeImpl) || (userDataRecord = ((NodeImpl) node).getUserDataRecord()) == null || userDataRecord.isEmpty()) {
            return;
        }
        callUserDataHandlers(node, node2, s, userDataRecord);
    }

    void callUserDataHandlers(Node node, Node node2, short s, Hashtable hashtable) {
        if (hashtable == null || hashtable.isEmpty()) {
            return;
        }
        for (Map.Entry entry : hashtable.entrySet()) {
            String str = (String) entry.getKey();
            ParentNode.UserDataRecord userDataRecord = (ParentNode.UserDataRecord) entry.getValue();
            if (userDataRecord.fHandler != null) {
                userDataRecord.fHandler.handle(s, str, userDataRecord.fData, node, node2);
            }
        }
    }

    protected boolean canRenameElements(String str, String str2, ElementImpl elementImpl) {
        return true;
    }

    @Override // org.apache.xerces.dom.NodeImpl
    protected void changed() {
        this.changes++;
    }

    @Override // org.apache.xerces.dom.NodeImpl
    protected int changes() {
        return this.changes;
    }

    protected final void checkDOMNSErr(String str, String str2) {
        if (this.errorChecking) {
            if (str2 == null) {
                throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
            }
            if (str.equals("xml") && !str2.equals(NamespaceContext.XML_URI)) {
                throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
            }
            if ((str.equals(XMLConstants.XMLNS_ATTRIBUTE) && !str2.equals(NamespaceContext.XMLNS_URI)) || (!str.equals(XMLConstants.XMLNS_ATTRIBUTE) && str2.equals(NamespaceContext.XMLNS_URI))) {
                throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
            }
        }
    }

    protected final void checkNamespaceWF(String str, int i, int i2) {
        if (this.errorChecking) {
            if (i == 0 || i == str.length() - 1 || i2 != i) {
                throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
            }
        }
    }

    protected final void checkQName(String str, String str2) {
        if (this.errorChecking) {
            if (this.xml11Version) {
                if ((str == null || XML11Char.isXML11ValidNCName(str)) && XML11Char.isXML11ValidNCName(str2)) {
                    return;
                }
            } else if ((str == null || XMLChar.isValidNCName(str)) && XMLChar.isValidNCName(str2)) {
                return;
            }
            throw new DOMException((short) 5, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null));
        }
    }

    protected final void clearIdentifiers() {
        Hashtable hashtable = this.identifiers;
        if (hashtable != null) {
            hashtable.clear();
        }
    }

    public Object clone() throws CloneNotSupportedException {
        CoreDocumentImpl coreDocumentImpl = (CoreDocumentImpl) super.clone();
        coreDocumentImpl.docType = null;
        coreDocumentImpl.docElement = null;
        return coreDocumentImpl;
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.ChildNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node cloneNode(boolean z) {
        CoreDocumentImpl coreDocumentImpl = new CoreDocumentImpl();
        callUserDataHandlers(this, coreDocumentImpl, (short) 1);
        cloneNode(coreDocumentImpl, z);
        return coreDocumentImpl;
    }

    protected void cloneNode(CoreDocumentImpl coreDocumentImpl, boolean z) {
        HashMap map;
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        if (z) {
            if (this.identifiers != null) {
                map = new HashMap();
                for (Map.Entry entry : this.identifiers.entrySet()) {
                    map.put(entry.getValue(), entry.getKey());
                }
            } else {
                map = null;
            }
            for (ChildNode childNode = this.firstChild; childNode != null; childNode = childNode.nextSibling) {
                coreDocumentImpl.appendChild(coreDocumentImpl.importNode(childNode, true, true, map));
            }
        }
        coreDocumentImpl.allowGrammarAccess = this.allowGrammarAccess;
        coreDocumentImpl.errorChecking = this.errorChecking;
    }

    protected void copyEventListeners(NodeImpl nodeImpl, NodeImpl nodeImpl2) {
    }

    public Attr createAttribute(String str) throws DOMException {
        if (!this.errorChecking || isXMLName(str, this.xml11Version)) {
            return new AttrImpl(this, str);
        }
        throw new DOMException((short) 5, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null));
    }

    @Override // org.w3c.dom.Document
    public Attr createAttributeNS(String str, String str2) throws DOMException {
        return new AttrNSImpl(this, str, str2);
    }

    public Attr createAttributeNS(String str, String str2, String str3) throws DOMException {
        return new AttrNSImpl(this, str, str2, str3);
    }

    @Override // org.w3c.dom.Document
    public CDATASection createCDATASection(String str) throws DOMException {
        return new CDATASectionImpl(this, str);
    }

    @Override // org.w3c.dom.Document
    public Comment createComment(String str) {
        return new CommentImpl(this, str);
    }

    @Override // org.w3c.dom.Document
    public DocumentFragment createDocumentFragment() {
        return new DocumentFragmentImpl(this);
    }

    public DocumentType createDocumentType(String str, String str2, String str3) throws DOMException {
        return new DocumentTypeImpl(this, str, str2, str3);
    }

    public Element createElement(String str) throws DOMException {
        if (!this.errorChecking || isXMLName(str, this.xml11Version)) {
            return new ElementImpl(this, str);
        }
        throw new DOMException((short) 5, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null));
    }

    public ElementDefinitionImpl createElementDefinition(String str) throws DOMException {
        if (!this.errorChecking || isXMLName(str, this.xml11Version)) {
            return new ElementDefinitionImpl(this, str);
        }
        throw new DOMException((short) 5, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null));
    }

    public Element createElementNS(String str, String str2) throws DOMException {
        return new ElementNSImpl(this, str, str2);
    }

    public Element createElementNS(String str, String str2, String str3) throws DOMException {
        return new ElementNSImpl(this, str, str2, str3);
    }

    public Entity createEntity(String str) throws DOMException {
        if (!this.errorChecking || isXMLName(str, this.xml11Version)) {
            return new EntityImpl(this, str);
        }
        throw new DOMException((short) 5, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null));
    }

    @Override // org.w3c.dom.Document
    public EntityReference createEntityReference(String str) throws DOMException {
        if (!this.errorChecking || isXMLName(str, this.xml11Version)) {
            return new EntityReferenceImpl(this, str);
        }
        throw new DOMException((short) 5, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null));
    }

    public Notation createNotation(String str) throws DOMException {
        if (!this.errorChecking || isXMLName(str, this.xml11Version)) {
            return new NotationImpl(this, str);
        }
        throw new DOMException((short) 5, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null));
    }

    @Override // org.w3c.dom.Document
    public ProcessingInstruction createProcessingInstruction(String str, String str2) throws DOMException {
        if (!this.errorChecking || isXMLName(str, this.xml11Version)) {
            return new ProcessingInstructionImpl(this, str, str2);
        }
        throw new DOMException((short) 5, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null));
    }

    @Override // org.w3c.dom.Document
    public Text createTextNode(String str) {
        return new TextImpl(this, str);
    }

    void deletedText(CharacterDataImpl characterDataImpl, int i, int i2) {
    }

    protected boolean dispatchEvent(NodeImpl nodeImpl, Event event) {
        return false;
    }

    void freeNodeListCache(NodeListCache nodeListCache) {
        nodeListCache.next = this.fFreeNLCache;
        this.fFreeNLCache = nodeListCache;
    }

    public boolean getAsync() {
        return false;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getBaseURI() {
        String str = this.fDocumentURI;
        if (str == null || str.length() == 0) {
            return this.fDocumentURI;
        }
        try {
            return new URI(this.fDocumentURI).toString();
        } catch (URI.MalformedURIException unused) {
            return null;
        }
    }

    @Override // org.w3c.dom.Document
    public DocumentType getDoctype() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this.docType;
    }

    public Element getDocumentElement() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this.docElement;
    }

    @Override // org.w3c.dom.Document
    public String getDocumentURI() {
        return this.fDocumentURI;
    }

    @Override // org.w3c.dom.Document
    public DOMConfiguration getDomConfig() {
        if (this.fConfiguration == null) {
            this.fConfiguration = new DOMConfigurationImpl();
        }
        return this.fConfiguration;
    }

    public Element getElementById(String str) {
        return getIdentifier(str);
    }

    public NodeList getElementsByTagName(String str) {
        return new DeepNodeListImpl(this, str);
    }

    public NodeList getElementsByTagNameNS(String str, String str2) {
        return new DeepNodeListImpl(this, str, str2);
    }

    public String getEncoding() {
        return getXmlEncoding();
    }

    public boolean getErrorChecking() {
        return this.errorChecking;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Object getFeature(String str, String str2) {
        boolean z = str2 == null || str2.length() == 0;
        if (!str.equalsIgnoreCase("+XPath") || (!z && !str2.equals("3.0"))) {
            return super.getFeature(str, str2);
        }
        Object obj = this.fXPathEvaluator;
        if (obj != null) {
            return obj;
        }
        try {
            Class clsFindProviderClass = ObjectFactory.findProviderClass("org.apache.xpath.domapi.XPathEvaluatorImpl", ObjectFactory.findClassLoader(), true);
            Constructor constructor = clsFindProviderClass.getConstructor(Document.class);
            for (Class<?> cls : clsFindProviderClass.getInterfaces()) {
                if (cls.getName().equals("org.w3c.dom.xpath.XPathEvaluator")) {
                    Object objNewInstance = constructor.newInstance(this);
                    this.fXPathEvaluator = objNewInstance;
                    return objNewInstance;
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public Element getIdentifier(String str) {
        Element element;
        if (needsSyncData()) {
            synchronizeData();
        }
        Hashtable hashtable = this.identifiers;
        if (hashtable != null && (element = (Element) hashtable.get(str)) != null) {
            for (Node parentNode = element.getParentNode(); parentNode != null; parentNode = parentNode.getParentNode()) {
                if (parentNode == this) {
                    return element;
                }
            }
        }
        return null;
    }

    public Enumeration getIdentifiers() {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (this.identifiers == null) {
            this.identifiers = new Hashtable();
        }
        return this.identifiers.keys();
    }

    @Override // org.w3c.dom.Document
    public DOMImplementation getImplementation() {
        return CoreDOMImplementationImpl.getDOMImplementation();
    }

    @Override // org.w3c.dom.Document
    public String getInputEncoding() {
        return this.actualEncoding;
    }

    boolean getMutationEvents() {
        return false;
    }

    NodeListCache getNodeListCache(ParentNode parentNode) {
        NodeListCache nodeListCache = this.fFreeNLCache;
        if (nodeListCache == null) {
            return new NodeListCache(parentNode);
        }
        this.fFreeNLCache = nodeListCache.next;
        nodeListCache.fChild = null;
        nodeListCache.fChildIndex = -1;
        nodeListCache.fLength = -1;
        if (nodeListCache.fOwner != null) {
            nodeListCache.fOwner.fNodeListCache = null;
        }
        nodeListCache.fOwner = parentNode;
        return nodeListCache;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getNodeName() {
        return "#document";
    }

    @Override // org.apache.xerces.dom.NodeImpl
    protected int getNodeNumber() {
        if (this.documentNumber == 0) {
            this.documentNumber = ((CoreDOMImplementationImpl) CoreDOMImplementationImpl.getDOMImplementation()).assignDocumentNumber();
        }
        return this.documentNumber;
    }

    protected int getNodeNumber(Node node) {
        int i;
        Map weakHashMap;
        Integer num;
        Map map = this.nodeTable;
        if (map == null) {
            weakHashMap = new WeakHashMap();
            this.nodeTable = weakHashMap;
            i = this.nodeCounter - 1;
            this.nodeCounter = i;
            num = new Integer(i);
        } else {
            Integer num2 = (Integer) map.get(node);
            if (num2 != null) {
                return num2.intValue();
            }
            i = this.nodeCounter - 1;
            this.nodeCounter = i;
            weakHashMap = this.nodeTable;
            num = new Integer(i);
        }
        weakHashMap.put(node, num);
        return i;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 9;
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public final Document getOwnerDocument() {
        return null;
    }

    public boolean getStandalone() {
        return getXmlStandalone();
    }

    @Override // org.w3c.dom.Document
    public boolean getStrictErrorChecking() {
        return this.errorChecking;
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getTextContent() throws DOMException {
        return null;
    }

    protected Object getUserData(NodeImpl nodeImpl) {
        return getUserData(nodeImpl, "XERCES1DOMUSERDATA");
    }

    public Object getUserData(Node node, String str) {
        Hashtable hashtable;
        Object obj;
        Map map = this.userData;
        if (map == null || (hashtable = (Hashtable) map.get(node)) == null || (obj = hashtable.get(str)) == null) {
            return null;
        }
        return ((ParentNode.UserDataRecord) obj).fData;
    }

    protected Hashtable getUserDataRecord(Node node) {
        Hashtable hashtable;
        Map map = this.userData;
        if (map == null || (hashtable = (Hashtable) map.get(node)) == null) {
            return null;
        }
        return hashtable;
    }

    public String getVersion() {
        return getXmlVersion();
    }

    @Override // org.w3c.dom.Document
    public String getXmlEncoding() {
        return this.encoding;
    }

    @Override // org.w3c.dom.Document
    public boolean getXmlStandalone() {
        return this.standalone;
    }

    @Override // org.w3c.dom.Document
    public String getXmlVersion() {
        String str = this.version;
        return str == null ? BuildConfig.VERSION_NAME : str;
    }

    @Override // org.w3c.dom.Document
    public Node importNode(Node node, boolean z) throws DOMException {
        return importNode(node, z, false, null);
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node insertBefore(Node node, Node node2) throws DOMException {
        short nodeType = node.getNodeType();
        if (this.errorChecking) {
            if (needsSyncChildren()) {
                synchronizeChildren();
            }
            if ((nodeType == 1 && this.docElement != null) || (nodeType == 10 && this.docType != null)) {
                throw new DOMException((short) 3, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null));
            }
        }
        if (node.getOwnerDocument() == null && (node instanceof DocumentTypeImpl)) {
            ((DocumentTypeImpl) node).ownerDocument = this;
        }
        super.insertBefore(node, node2);
        if (nodeType == 1) {
            this.docElement = (ElementImpl) node;
        } else if (nodeType == 10) {
            this.docType = (DocumentTypeImpl) node;
        }
        return node;
    }

    void insertedNode(NodeImpl nodeImpl, NodeImpl nodeImpl2, boolean z) {
    }

    void insertedText(CharacterDataImpl characterDataImpl, int i, int i2) {
    }

    void insertingNode(NodeImpl nodeImpl, boolean z) {
    }

    protected boolean isKidOK(Node node, Node node2) {
        if (this.allowGrammarAccess && node.getNodeType() == 10) {
            return node2.getNodeType() == 1;
        }
        return (kidOK[node.getNodeType()] & (1 << node2.getNodeType())) != 0;
    }

    boolean isNormalizeDocRequired() {
        return true;
    }

    boolean isXML11Version() {
        return this.xml11Version;
    }

    boolean isXMLVersionChanged() {
        return this.xmlVersionChanged;
    }

    public boolean load(String str) {
        return false;
    }

    public boolean loadXML(String str) {
        return false;
    }

    void modifiedAttrValue(AttrImpl attrImpl, String str) {
    }

    void modifiedCharacterData(NodeImpl nodeImpl, String str, String str2, boolean z) {
    }

    void modifyingCharacterData(NodeImpl nodeImpl, boolean z) {
    }

    @Override // org.w3c.dom.Document
    public void normalizeDocument() {
        if (!isNormalized() || isNormalizeDocRequired()) {
            if (needsSyncChildren()) {
                synchronizeChildren();
            }
            if (this.domNormalizer == null) {
                this.domNormalizer = new DOMNormalizer();
            }
            DOMConfigurationImpl dOMConfigurationImpl = this.fConfiguration;
            if (dOMConfigurationImpl == null) {
                this.fConfiguration = new DOMConfigurationImpl();
            } else {
                dOMConfigurationImpl.reset();
            }
            this.domNormalizer.normalizeDocument(this, this.fConfiguration);
            isNormalized(true);
            this.xmlVersionChanged = false;
        }
    }

    public void putIdentifier(String str, Element element) {
        if (element == null) {
            removeIdentifier(str);
            return;
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        if (this.identifiers == null) {
            this.identifiers = new Hashtable();
        }
        this.identifiers.put(str, element);
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node removeChild(Node node) throws DOMException {
        super.removeChild(node);
        short nodeType = node.getNodeType();
        if (nodeType == 1) {
            this.docElement = null;
        } else if (nodeType == 10) {
            this.docType = null;
        }
        return node;
    }

    protected void removeEventListener(NodeImpl nodeImpl, String str, EventListener eventListener, boolean z) {
    }

    public void removeIdentifier(String str) {
        if (needsSyncData()) {
            synchronizeData();
        }
        Hashtable hashtable = this.identifiers;
        if (hashtable == null) {
            return;
        }
        hashtable.remove(str);
    }

    Hashtable removeUserDataTable(Node node) {
        Map map = this.userData;
        if (map == null) {
            return null;
        }
        return (Hashtable) map.get(node);
    }

    void removedAttrNode(AttrImpl attrImpl, NodeImpl nodeImpl, String str) {
    }

    void removedNode(NodeImpl nodeImpl, boolean z) {
    }

    void removingNode(NodeImpl nodeImpl, NodeImpl nodeImpl2, boolean z) {
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00ae  */
    @Override // org.w3c.dom.Document
    public Node renameNode(Node node, String str, String str2) throws DOMException {
        if (this.errorChecking && node.getOwnerDocument() != this && node != this) {
            throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
        }
        short nodeType = node.getNodeType();
        if (nodeType == 1) {
            ElementImpl elementImplReplaceRenameElement = (ElementImpl) node;
            if (elementImplReplaceRenameElement instanceof ElementNSImpl) {
                if (canRenameElements(str, str2, elementImplReplaceRenameElement)) {
                    ((ElementNSImpl) elementImplReplaceRenameElement).rename(str, str2);
                    callUserDataHandlers(elementImplReplaceRenameElement, null, (short) 4);
                } else {
                    elementImplReplaceRenameElement = replaceRenameElement(elementImplReplaceRenameElement, str, str2);
                }
            } else if (str == null && canRenameElements(null, str2, elementImplReplaceRenameElement)) {
                elementImplReplaceRenameElement.rename(str2);
                callUserDataHandlers(elementImplReplaceRenameElement, null, (short) 4);
            } else {
                elementImplReplaceRenameElement = replaceRenameElement(elementImplReplaceRenameElement, str, str2);
            }
            renamedElement((Element) node, elementImplReplaceRenameElement);
            return elementImplReplaceRenameElement;
        }
        if (nodeType != 2) {
            throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null));
        }
        AttrImpl attrImpl = (AttrImpl) node;
        Element ownerElement = attrImpl.getOwnerElement();
        if (ownerElement != null) {
            ownerElement.removeAttributeNode(attrImpl);
        }
        if (!(node instanceof AttrNSImpl)) {
            if (str == null) {
                attrImpl.rename(str2);
                if (ownerElement != null) {
                    ownerElement.setAttributeNode(attrImpl);
                }
            } else {
                AttrNSImpl attrNSImpl = (AttrNSImpl) createAttributeNS(str, str2);
                copyEventListeners(attrImpl, attrNSImpl);
                Hashtable hashtableRemoveUserDataTable = removeUserDataTable(attrImpl);
                while (true) {
                    Node firstChild = attrImpl.getFirstChild();
                    if (firstChild == null) {
                        break;
                    }
                    attrImpl.removeChild(firstChild);
                    attrNSImpl.appendChild(firstChild);
                }
                setUserDataTable(attrNSImpl, hashtableRemoveUserDataTable);
                callUserDataHandlers(attrImpl, attrNSImpl, (short) 4);
                if (ownerElement != null) {
                    ownerElement.setAttributeNode(attrNSImpl);
                }
                attrImpl = attrNSImpl;
            }
            renamedAttrNode((Attr) node, attrImpl);
            return attrImpl;
        }
        ((AttrNSImpl) attrImpl).rename(str, str2);
        if (ownerElement != null) {
            ownerElement.setAttributeNodeNS(attrImpl);
        }
        callUserDataHandlers(attrImpl, null, (short) 4);
        renamedAttrNode((Attr) node, attrImpl);
        return attrImpl;
    }

    void renamedAttrNode(Attr attr, Attr attr2) {
    }

    void renamedElement(Element element, Element element2) {
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node replaceChild(Node node, Node node2) throws DOMException {
        if (node.getOwnerDocument() == null && (node instanceof DocumentTypeImpl)) {
            ((DocumentTypeImpl) node).ownerDocument = this;
        }
        if (this.errorChecking && ((this.docType != null && node2.getNodeType() != 10 && node.getNodeType() == 10) || (this.docElement != null && node2.getNodeType() != 1 && node.getNodeType() == 1))) {
            throw new DOMException((short) 3, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null));
        }
        super.replaceChild(node, node2);
        short nodeType = node2.getNodeType();
        if (nodeType == 1) {
            this.docElement = (ElementImpl) node;
        } else if (nodeType == 10) {
            this.docType = (DocumentTypeImpl) node;
        }
        return node2;
    }

    void replacedCharacterData(NodeImpl nodeImpl, String str, String str2) {
    }

    void replacedNode(NodeImpl nodeImpl) {
    }

    void replacedText(CharacterDataImpl characterDataImpl) {
    }

    void replacingData(NodeImpl nodeImpl) {
    }

    void replacingNode(NodeImpl nodeImpl) {
    }

    public String saveXML(Node node) throws DOMException {
        if (this.errorChecking && node != null && this != node.getOwnerDocument()) {
            throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
        }
        LSSerializer lSSerializerCreateLSSerializer = ((DOMImplementationLS) DOMImplementationImpl.getDOMImplementation()).createLSSerializer();
        if (node == null) {
            node = this;
        }
        return lSSerializerCreateLSSerializer.writeToString(node);
    }

    public void setAsync(boolean z) {
        if (z) {
            throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null));
        }
    }

    void setAttrNode(AttrImpl attrImpl, AttrImpl attrImpl2) {
    }

    @Override // org.w3c.dom.Document
    public void setDocumentURI(String str) {
        this.fDocumentURI = str;
    }

    public void setEncoding(String str) {
        setXmlEncoding(str);
    }

    public void setErrorChecking(boolean z) {
        this.errorChecking = z;
    }

    public void setInputEncoding(String str) {
        this.actualEncoding = str;
    }

    void setMutationEvents(boolean z) {
    }

    public void setStandalone(boolean z) {
        setXmlStandalone(z);
    }

    @Override // org.w3c.dom.Document
    public void setStrictErrorChecking(boolean z) {
        this.errorChecking = z;
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public void setTextContent(String str) throws DOMException {
    }

    /* JADX WARN: Code duplicated, block: B:23:0x004b A[RETURN] */
    public Object setUserData(Node node, String str, Object obj, UserDataHandler userDataHandler) {
        Hashtable hashtable;
        Object objPut;
        Hashtable hashtable2;
        if (obj == null) {
            Map map = this.userData;
            if (map == null || (hashtable2 = (Hashtable) map.get(node)) == null || (objPut = hashtable2.remove(str)) == null) {
                return null;
            }
        } else {
            Map map2 = this.userData;
            if (map2 == null) {
                this.userData = new WeakHashMap();
                hashtable = new Hashtable();
            } else {
                hashtable = (Hashtable) map2.get(node);
                if (hashtable == null) {
                    hashtable = new Hashtable();
                }
                objPut = hashtable.put(str, new ParentNode.UserDataRecord(obj, userDataHandler));
                if (objPut == null) {
                    return null;
                }
            }
            this.userData.put(node, hashtable);
            objPut = hashtable.put(str, new ParentNode.UserDataRecord(obj, userDataHandler));
            if (objPut == null) {
                return null;
            }
        }
        return ((ParentNode.UserDataRecord) objPut).fData;
    }

    protected void setUserData(NodeImpl nodeImpl, Object obj) {
        setUserData(nodeImpl, "XERCES1DOMUSERDATA", obj, null);
    }

    void setUserDataTable(Node node, Hashtable hashtable) {
        if (this.userData == null) {
            this.userData = new WeakHashMap();
        }
        if (hashtable != null) {
            this.userData.put(node, hashtable);
        }
    }

    public void setVersion(String str) {
        setXmlVersion(str);
    }

    public void setXmlEncoding(String str) {
        this.encoding = str;
    }

    @Override // org.w3c.dom.Document
    public void setXmlStandalone(boolean z) throws DOMException {
        this.standalone = z;
    }

    @Override // org.w3c.dom.Document
    public void setXmlVersion(String str) {
        if (!str.equals(BuildConfig.VERSION_NAME) && !str.equals(CompilerOptions.VERSION_1_1)) {
            throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null));
        }
        if (!getXmlVersion().equals(str)) {
            this.xmlVersionChanged = true;
            isNormalized(false);
            this.version = str;
        }
        if (getXmlVersion().equals(CompilerOptions.VERSION_1_1)) {
            this.xml11Version = true;
        } else {
            this.xml11Version = false;
        }
    }

    protected void undeferChildren(Node node) {
        Node parentNode = node;
        while (parentNode != null) {
            NodeImpl nodeImpl = (NodeImpl) parentNode;
            if (nodeImpl.needsSyncData()) {
                nodeImpl.synchronizeData();
            }
            NamedNodeMap attributes = parentNode.getAttributes();
            if (attributes != null) {
                int length = attributes.getLength();
                for (int i = 0; i < length; i++) {
                    undeferChildren(attributes.item(i));
                }
            }
            Node firstChild = parentNode.getFirstChild();
            while (true) {
                if (firstChild != null || node.equals(parentNode)) {
                    parentNode = firstChild;
                } else {
                    firstChild = parentNode.getNextSibling();
                    if (firstChild == null && ((parentNode = parentNode.getParentNode()) == null || node.equals(parentNode))) {
                        parentNode = null;
                    }
                }
            }
        }
    }
}
