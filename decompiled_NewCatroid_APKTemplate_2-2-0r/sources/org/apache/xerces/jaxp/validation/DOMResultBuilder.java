package org.apache.xerces.jaxp.validation;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import javax.xml.transform.dom.DOMResult;
import org.apache.xerces.dom.AttrImpl;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.dom.DocumentTypeImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.ElementNSImpl;
import org.apache.xerces.dom.EntityImpl;
import org.apache.xerces.dom.NotationImpl;
import org.apache.xerces.dom.PSVIAttrNSImpl;
import org.apache.xerces.dom.PSVIDocumentImpl;
import org.apache.xerces.dom.PSVIElementNSImpl;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/* JADX INFO: loaded from: classes13.dex */
final class DOMResultBuilder implements DOMDocumentHandler {
    private static final int[] kidOK = {0, XKeySym.XK_scedilla, 40, 0, 0, XKeySym.XK_scedilla, XKeySym.XK_scedilla, 0, 0, 1410, 0, XKeySym.XK_scedilla, 0};
    private Node fCurrentNode;
    private Document fDocument;
    private CoreDocumentImpl fDocumentImpl;
    private Node fFragmentRoot;
    private boolean fIgnoreChars;
    private Node fNextSibling;
    private boolean fStorePSVI;
    private Node fTarget;
    private final ArrayList fTargetChildren = new ArrayList();
    private final QName fAttributeQName = new QName();

    private void append(Node node) throws XNIException {
        Node node2 = this.fCurrentNode;
        if (node2 != null) {
            node2.appendChild(node);
        } else {
            if ((kidOK[this.fTarget.getNodeType()] & (1 << node.getNodeType())) == 0) {
                throw new XNIException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null));
            }
            this.fTargetChildren.add(node);
        }
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void cdata(CDATASection cDATASection) throws XNIException {
        append(this.fDocument.createCDATASection(cDATASection.getNodeValue()));
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fIgnoreChars) {
            return;
        }
        append(this.fDocument.createTextNode(xMLString.toString()));
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void characters(Text text) throws XNIException {
        append(this.fDocument.createTextNode(text.getNodeValue()));
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void comment(Comment comment) throws XNIException {
        append(this.fDocument.createComment(comment.getNodeValue()));
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void doctypeDecl(DocumentType documentType) throws XNIException {
        CoreDocumentImpl coreDocumentImpl = this.fDocumentImpl;
        if (coreDocumentImpl != null) {
            DocumentType documentTypeCreateDocumentType = coreDocumentImpl.createDocumentType(documentType.getName(), documentType.getPublicId(), documentType.getSystemId());
            String internalSubset = documentType.getInternalSubset();
            if (internalSubset != null) {
                ((DocumentTypeImpl) documentTypeCreateDocumentType).setInternalSubset(internalSubset);
            }
            NamedNodeMap entities = documentType.getEntities();
            NamedNodeMap entities2 = documentTypeCreateDocumentType.getEntities();
            int length = entities.getLength();
            for (int i = 0; i < length; i++) {
                Entity entity = (Entity) entities.item(i);
                EntityImpl entityImpl = (EntityImpl) this.fDocumentImpl.createEntity(entity.getNodeName());
                entityImpl.setPublicId(entity.getPublicId());
                entityImpl.setSystemId(entity.getSystemId());
                entityImpl.setNotationName(entity.getNotationName());
                entities2.setNamedItem(entityImpl);
            }
            NamedNodeMap notations = documentType.getNotations();
            NamedNodeMap notations2 = documentTypeCreateDocumentType.getNotations();
            int length2 = notations.getLength();
            for (int i2 = 0; i2 < length2; i2++) {
                Notation notation = (Notation) notations.item(i2);
                NotationImpl notationImpl = (NotationImpl) this.fDocumentImpl.createNotation(notation.getNodeName());
                notationImpl.setPublicId(notation.getPublicId());
                notationImpl.setSystemId(notation.getSystemId());
                notations2.setNamedItem(notationImpl);
            }
            append(documentTypeCreateDocumentType);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void emptyElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        startElement(qName, xMLAttributes, augmentations);
        endElement(qName, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endCDATA(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endDocument(Augmentations augmentations) throws XNIException {
        int size = this.fTargetChildren.size();
        int i = 0;
        if (this.fNextSibling == null) {
            while (i < size) {
                this.fTarget.appendChild((Node) this.fTargetChildren.get(i));
                i++;
            }
        } else {
            while (i < size) {
                this.fTarget.insertBefore((Node) this.fTargetChildren.get(i), this.fNextSibling);
                i++;
            }
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(QName qName, Augmentations augmentations) throws XNIException {
        ElementPSVI elementPSVI;
        if (augmentations != null && this.fDocumentImpl != null && (elementPSVI = (ElementPSVI) augmentations.getItem(Constants.ELEMENT_PSVI)) != null) {
            if (this.fStorePSVI) {
                ((PSVIElementNSImpl) this.fCurrentNode).setPSVI(elementPSVI);
            }
            XSTypeDefinition memberTypeDefinition = elementPSVI.getMemberTypeDefinition();
            if (memberTypeDefinition == null) {
                memberTypeDefinition = elementPSVI.getTypeDefinition();
            }
            ((ElementNSImpl) this.fCurrentNode).setType(memberTypeDefinition);
        }
        Node node = this.fCurrentNode;
        if (node != this.fFragmentRoot) {
            this.fCurrentNode = node.getParentNode();
        } else {
            this.fCurrentNode = null;
            this.fFragmentRoot = null;
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endGeneralEntity(String str, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public XMLDocumentSource getDocumentSource() {
        return null;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void ignorableWhitespace(XMLString xMLString, Augmentations augmentations) throws XNIException {
        characters(xMLString, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void processingInstruction(ProcessingInstruction processingInstruction) throws XNIException {
        append(this.fDocument.createProcessingInstruction(processingInstruction.getTarget(), processingInstruction.getData()));
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void setDOMResult(DOMResult dOMResult) {
        this.fCurrentNode = null;
        this.fFragmentRoot = null;
        this.fIgnoreChars = false;
        this.fTargetChildren.clear();
        if (dOMResult == null) {
            this.fTarget = null;
            this.fNextSibling = null;
            this.fDocument = null;
            this.fDocumentImpl = null;
            this.fStorePSVI = false;
            return;
        }
        this.fTarget = dOMResult.getNode();
        this.fNextSibling = dOMResult.getNextSibling();
        Document ownerDocument = this.fTarget.getNodeType() == 9 ? (Document) this.fTarget : this.fTarget.getOwnerDocument();
        this.fDocument = ownerDocument;
        this.fDocumentImpl = ownerDocument instanceof CoreDocumentImpl ? (CoreDocumentImpl) ownerDocument : null;
        this.fStorePSVI = ownerDocument instanceof PSVIDocumentImpl;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void setDocumentSource(XMLDocumentSource xMLDocumentSource) {
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void setIgnoringCharacters(boolean z) {
        this.fIgnoreChars = z;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startCDATA(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startDocument(XMLLocator xMLLocator, String str, NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0097  */
    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        Element elementCreateElementNS;
        int length = xMLAttributes.getLength();
        CoreDocumentImpl coreDocumentImpl = this.fDocumentImpl;
        int i = 0;
        if (coreDocumentImpl == null) {
            elementCreateElementNS = this.fDocument.createElementNS(qName.uri, qName.rawname);
            while (i < length) {
                xMLAttributes.getName(i, this.fAttributeQName);
                elementCreateElementNS.setAttributeNS(this.fAttributeQName.uri, this.fAttributeQName.rawname, xMLAttributes.getValue(i));
                i++;
            }
        } else {
            elementCreateElementNS = coreDocumentImpl.createElementNS(qName.uri, qName.rawname, qName.localpart);
            while (i < length) {
                xMLAttributes.getName(i, this.fAttributeQName);
                AttrImpl attrImpl = (AttrImpl) this.fDocumentImpl.createAttributeNS(this.fAttributeQName.uri, this.fAttributeQName.rawname, this.fAttributeQName.localpart);
                attrImpl.setValue(xMLAttributes.getValue(i));
                elementCreateElementNS.setAttributeNodeNS(attrImpl);
                AttributePSVI attributePSVI = (AttributePSVI) xMLAttributes.getAugmentations(i).getItem(Constants.ATTRIBUTE_PSVI);
                if (attributePSVI != null) {
                    if (this.fStorePSVI) {
                        ((PSVIAttrNSImpl) attrImpl).setPSVI(attributePSVI);
                    }
                    XSSimpleTypeDefinition memberTypeDefinition = attributePSVI.getMemberTypeDefinition();
                    if (memberTypeDefinition == null) {
                        XSTypeDefinition typeDefinition = attributePSVI.getTypeDefinition();
                        if (typeDefinition != null) {
                            attrImpl.setType(typeDefinition);
                            if (((XSSimpleType) typeDefinition).isIDType()) {
                                ((ElementImpl) elementCreateElementNS).setIdAttributeNode(attrImpl, true);
                            }
                        }
                    } else {
                        attrImpl.setType(memberTypeDefinition);
                        if (((XSSimpleType) memberTypeDefinition).isIDType()) {
                            ((ElementImpl) elementCreateElementNS).setIdAttributeNode(attrImpl, true);
                        }
                    }
                }
                attrImpl.setSpecified(xMLAttributes.isSpecified(i));
                i++;
            }
        }
        append(elementCreateElementNS);
        this.fCurrentNode = elementCreateElementNS;
        if (this.fFragmentRoot == null) {
            this.fFragmentRoot = elementCreateElementNS;
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startGeneralEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void textDecl(String str, String str2, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void xmlDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
    }
}
