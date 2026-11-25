package org.apache.xerces.jaxp.validation;

import javax.xml.transform.dom.DOMResult;
import org.apache.xerces.dom.AttrImpl;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.ElementNSImpl;
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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/* JADX INFO: loaded from: classes13.dex */
final class DOMResultAugmentor implements DOMDocumentHandler {
    private final QName fAttributeQName = new QName();
    private final DOMValidatorHelper fDOMValidatorHelper;
    private Document fDocument;
    private CoreDocumentImpl fDocumentImpl;
    private boolean fIgnoreChars;
    private boolean fStorePSVI;

    public DOMResultAugmentor(DOMValidatorHelper dOMValidatorHelper) {
        this.fDOMValidatorHelper = dOMValidatorHelper;
    }

    private boolean processAttributePSVI(AttrImpl attrImpl, AttributePSVI attributePSVI) {
        if (this.fStorePSVI) {
            ((PSVIAttrNSImpl) attrImpl).setPSVI(attributePSVI);
        }
        XSSimpleTypeDefinition memberTypeDefinition = attributePSVI.getMemberTypeDefinition();
        if (memberTypeDefinition != null) {
            attrImpl.setType(memberTypeDefinition);
            return ((XSSimpleType) memberTypeDefinition).isIDType();
        }
        XSTypeDefinition typeDefinition = attributePSVI.getTypeDefinition();
        if (typeDefinition == null) {
            return false;
        }
        attrImpl.setType(typeDefinition);
        return ((XSSimpleType) typeDefinition).isIDType();
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void cdata(CDATASection cDATASection) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fIgnoreChars) {
            return;
        }
        ((Element) this.fDOMValidatorHelper.getCurrentElement()).appendChild(this.fDocument.createTextNode(xMLString.toString()));
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void characters(Text text) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void comment(Comment comment) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void doctypeDecl(DocumentType documentType) throws XNIException {
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
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(QName qName, Augmentations augmentations) throws XNIException {
        ElementPSVI elementPSVI;
        Node currentElement = this.fDOMValidatorHelper.getCurrentElement();
        if (augmentations == null || this.fDocumentImpl == null || (elementPSVI = (ElementPSVI) augmentations.getItem(Constants.ELEMENT_PSVI)) == null) {
            return;
        }
        if (this.fStorePSVI) {
            ((PSVIElementNSImpl) currentElement).setPSVI(elementPSVI);
        }
        XSTypeDefinition memberTypeDefinition = elementPSVI.getMemberTypeDefinition();
        if (memberTypeDefinition == null) {
            memberTypeDefinition = elementPSVI.getTypeDefinition();
        }
        ((ElementNSImpl) currentElement).setType(memberTypeDefinition);
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
    }

    @Override // org.apache.xerces.jaxp.validation.DOMDocumentHandler
    public void setDOMResult(DOMResult dOMResult) {
        this.fIgnoreChars = false;
        if (dOMResult == null) {
            this.fDocument = null;
            this.fDocumentImpl = null;
            this.fStorePSVI = false;
        } else {
            Node node = dOMResult.getNode();
            Document ownerDocument = node.getNodeType() == 9 ? (Document) node : node.getOwnerDocument();
            this.fDocument = ownerDocument;
            this.fDocumentImpl = ownerDocument instanceof CoreDocumentImpl ? (CoreDocumentImpl) ownerDocument : null;
            this.fStorePSVI = ownerDocument instanceof PSVIDocumentImpl;
        }
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

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        Element element = (Element) this.fDOMValidatorHelper.getCurrentElement();
        NamedNodeMap attributes = element.getAttributes();
        int length = attributes.getLength();
        if (this.fDocumentImpl != null) {
            for (int i = 0; i < length; i++) {
                AttrImpl attrImpl = (AttrImpl) attributes.item(i);
                AttributePSVI attributePSVI = (AttributePSVI) xMLAttributes.getAugmentations(i).getItem(Constants.ATTRIBUTE_PSVI);
                if (attributePSVI != null && processAttributePSVI(attrImpl, attributePSVI)) {
                    ((ElementImpl) element).setIdAttributeNode(attrImpl, true);
                }
            }
        }
        int length2 = xMLAttributes.getLength();
        if (length2 > length) {
            if (this.fDocumentImpl == null) {
                while (length < length2) {
                    xMLAttributes.getName(length, this.fAttributeQName);
                    element.setAttributeNS(this.fAttributeQName.uri, this.fAttributeQName.rawname, xMLAttributes.getValue(length));
                    length++;
                }
                return;
            }
            while (length < length2) {
                xMLAttributes.getName(length, this.fAttributeQName);
                AttrImpl attrImpl2 = (AttrImpl) this.fDocumentImpl.createAttributeNS(this.fAttributeQName.uri, this.fAttributeQName.rawname, this.fAttributeQName.localpart);
                attrImpl2.setValue(xMLAttributes.getValue(length));
                element.setAttributeNodeNS(attrImpl2);
                AttributePSVI attributePSVI2 = (AttributePSVI) xMLAttributes.getAugmentations(length).getItem(Constants.ATTRIBUTE_PSVI);
                if (attributePSVI2 != null && processAttributePSVI(attrImpl2, attributePSVI2)) {
                    ((ElementImpl) element).setIdAttributeNode(attrImpl2, true);
                }
                attrImpl2.setSpecified(false);
                length++;
            }
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
