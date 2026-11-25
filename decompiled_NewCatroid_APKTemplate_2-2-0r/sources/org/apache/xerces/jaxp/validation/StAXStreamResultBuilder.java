package org.apache.xerces.jaxp.validation;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.transform.stax.StAXResult;
import org.apache.xerces.util.JAXPNamespaceContextWrapper;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.catrobat.catroid.BuildConfig;

/* JADX INFO: loaded from: classes13.dex */
final class StAXStreamResultBuilder implements StAXDocumentHandler {
    private final QName fAttrName = new QName();
    private boolean fIgnoreChars;
    private boolean fInCDATA;
    private final JAXPNamespaceContextWrapper fNamespaceContext;
    private XMLStreamWriter fStreamWriter;

    public StAXStreamResultBuilder(JAXPNamespaceContextWrapper jAXPNamespaceContextWrapper) {
        this.fNamespaceContext = jAXPNamespaceContextWrapper;
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void cdata(Characters characters) throws XMLStreamException {
        this.fStreamWriter.writeCData(characters.getData());
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void characters(Characters characters) throws XMLStreamException {
        this.fStreamWriter.writeCharacters(characters.getData());
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fIgnoreChars) {
            return;
        }
        try {
            if (this.fInCDATA) {
                this.fStreamWriter.writeCData(xMLString.toString());
            } else {
                this.fStreamWriter.writeCharacters(xMLString.ch, xMLString.offset, xMLString.length);
            }
        } catch (XMLStreamException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void comment(XMLStreamReader xMLStreamReader) throws XMLStreamException {
        this.fStreamWriter.writeComment(xMLStreamReader.getText());
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void comment(Comment comment) throws XMLStreamException {
        this.fStreamWriter.writeComment(comment.getText());
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void doctypeDecl(DTD dtd) throws XMLStreamException {
        this.fStreamWriter.writeDTD(dtd.getDocumentTypeDeclaration());
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void emptyElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        startElement(qName, xMLAttributes, augmentations);
        endElement(qName, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endCDATA(Augmentations augmentations) throws XNIException {
        this.fInCDATA = false;
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void endDocument(XMLStreamReader xMLStreamReader) throws XMLStreamException {
        this.fStreamWriter.writeEndDocument();
        this.fStreamWriter.flush();
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void endDocument(EndDocument endDocument) throws XMLStreamException {
        this.fStreamWriter.writeEndDocument();
        this.fStreamWriter.flush();
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endDocument(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(QName qName, Augmentations augmentations) throws XNIException {
        try {
            this.fStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endGeneralEntity(String str, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void entityReference(XMLStreamReader xMLStreamReader) throws XMLStreamException {
        this.fStreamWriter.writeEntityRef(xMLStreamReader.getLocalName());
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void entityReference(EntityReference entityReference) throws XMLStreamException {
        this.fStreamWriter.writeEntityRef(entityReference.getName());
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

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void processingInstruction(XMLStreamReader xMLStreamReader) throws XMLStreamException {
        String pIData = xMLStreamReader.getPIData();
        if (pIData == null || pIData.length() <= 0) {
            this.fStreamWriter.writeProcessingInstruction(xMLStreamReader.getPITarget());
        } else {
            this.fStreamWriter.writeProcessingInstruction(xMLStreamReader.getPITarget(), pIData);
        }
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void processingInstruction(ProcessingInstruction processingInstruction) throws XMLStreamException {
        String data = processingInstruction.getData();
        if (data == null || data.length() <= 0) {
            this.fStreamWriter.writeProcessingInstruction(processingInstruction.getTarget());
        } else {
            this.fStreamWriter.writeProcessingInstruction(processingInstruction.getTarget(), data);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void setDocumentSource(XMLDocumentSource xMLDocumentSource) {
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void setIgnoringCharacters(boolean z) {
        this.fIgnoreChars = z;
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void setStAXResult(StAXResult stAXResult) {
        this.fIgnoreChars = false;
        this.fInCDATA = false;
        this.fAttrName.clear();
        this.fStreamWriter = stAXResult != null ? stAXResult.getXMLStreamWriter() : null;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startCDATA(Augmentations augmentations) throws XNIException {
        this.fInCDATA = true;
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void startDocument(XMLStreamReader xMLStreamReader) throws XMLStreamException {
        String version = xMLStreamReader.getVersion();
        String characterEncodingScheme = xMLStreamReader.getCharacterEncodingScheme();
        XMLStreamWriter xMLStreamWriter = this.fStreamWriter;
        if (characterEncodingScheme == null) {
            characterEncodingScheme = "UTF-8";
        }
        if (version == null) {
            version = BuildConfig.VERSION_NAME;
        }
        xMLStreamWriter.writeStartDocument(characterEncodingScheme, version);
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void startDocument(StartDocument startDocument) throws XMLStreamException {
        String version = startDocument.getVersion();
        String characterEncodingScheme = startDocument.getCharacterEncodingScheme();
        XMLStreamWriter xMLStreamWriter = this.fStreamWriter;
        if (characterEncodingScheme == null) {
            characterEncodingScheme = "UTF-8";
        }
        if (version == null) {
            version = BuildConfig.VERSION_NAME;
        }
        xMLStreamWriter.writeStartDocument(characterEncodingScheme, version);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startDocument(XMLLocator xMLLocator, String str, NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        try {
            if (qName.prefix.length() > 0) {
                this.fStreamWriter.writeStartElement(qName.prefix, qName.localpart, qName.uri != null ? qName.uri : "");
            } else if (qName.uri != null) {
                this.fStreamWriter.writeStartElement(qName.uri, qName.localpart);
            } else {
                this.fStreamWriter.writeStartElement(qName.localpart);
            }
            int declaredPrefixCount = this.fNamespaceContext.getDeclaredPrefixCount();
            javax.xml.namespace.NamespaceContext namespaceContext = this.fNamespaceContext.getNamespaceContext();
            for (int i = 0; i < declaredPrefixCount; i++) {
                String declaredPrefixAt = this.fNamespaceContext.getDeclaredPrefixAt(i);
                String namespaceURI = namespaceContext.getNamespaceURI(declaredPrefixAt);
                if (declaredPrefixAt.length() == 0) {
                    XMLStreamWriter xMLStreamWriter = this.fStreamWriter;
                    if (namespaceURI == null) {
                        namespaceURI = "";
                    }
                    xMLStreamWriter.writeDefaultNamespace(namespaceURI);
                } else {
                    XMLStreamWriter xMLStreamWriter2 = this.fStreamWriter;
                    if (namespaceURI == null) {
                        namespaceURI = "";
                    }
                    xMLStreamWriter2.writeNamespace(declaredPrefixAt, namespaceURI);
                }
            }
            int length = xMLAttributes.getLength();
            for (int i2 = 0; i2 < length; i2++) {
                xMLAttributes.getName(i2, this.fAttrName);
                if (this.fAttrName.prefix.length() > 0) {
                    this.fStreamWriter.writeAttribute(this.fAttrName.prefix, this.fAttrName.uri != null ? this.fAttrName.uri : "", this.fAttrName.localpart, xMLAttributes.getValue(i2));
                } else if (this.fAttrName.uri != null) {
                    this.fStreamWriter.writeAttribute(this.fAttrName.uri, this.fAttrName.localpart, xMLAttributes.getValue(i2));
                } else {
                    this.fStreamWriter.writeAttribute(this.fAttrName.localpart, xMLAttributes.getValue(i2));
                }
            }
        } catch (XMLStreamException e) {
            throw new XNIException(e);
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
