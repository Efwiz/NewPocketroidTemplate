package org.apache.xerces.jaxp.validation;

import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stax.StAXResult;
import org.apache.xerces.util.JAXPNamespaceContextWrapper;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.catrobat.catroid.BuildConfig;

/* JADX INFO: loaded from: classes13.dex */
final class StAXEventResultBuilder implements StAXDocumentHandler {
    private static final Iterator EMPTY_COLLECTION_ITERATOR = new Iterator() { // from class: org.apache.xerces.jaxp.validation.StAXEventResultBuilder.1
        @Override // java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        @Override // java.util.Iterator
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    };
    private final QName fAttrName = new QName();
    private final XMLEventFactory fEventFactory = XMLEventFactory.newInstance();
    private XMLEventWriter fEventWriter;
    private boolean fIgnoreChars;
    private boolean fInCDATA;
    private final JAXPNamespaceContextWrapper fNamespaceContext;
    private final StAXValidatorHelper fStAXValidatorHelper;

    final class AttributeIterator implements Iterator {
        XMLAttributes fAttributes;
        int fEnd;
        int fIndex = 0;

        AttributeIterator(XMLAttributes xMLAttributes, int i) {
            this.fAttributes = xMLAttributes;
            this.fEnd = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.fIndex < this.fEnd) {
                return true;
            }
            this.fAttributes = null;
            return false;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.fAttributes.getName(this.fIndex, StAXEventResultBuilder.this.fAttrName);
            XMLEventFactory xMLEventFactory = StAXEventResultBuilder.this.fEventFactory;
            String str = StAXEventResultBuilder.this.fAttrName.prefix;
            String str2 = StAXEventResultBuilder.this.fAttrName.uri != null ? StAXEventResultBuilder.this.fAttrName.uri : "";
            String str3 = StAXEventResultBuilder.this.fAttrName.localpart;
            XMLAttributes xMLAttributes = this.fAttributes;
            int i = this.fIndex;
            this.fIndex = i + 1;
            return xMLEventFactory.createAttribute(str, str2, str3, xMLAttributes.getValue(i));
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    final class NamespaceIterator implements Iterator {
        int fEnd;
        int fIndex = 0;
        NamespaceContext fNC;

        NamespaceIterator(int i) {
            this.fNC = StAXEventResultBuilder.this.fNamespaceContext.getNamespaceContext();
            this.fEnd = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.fIndex < this.fEnd) {
                return true;
            }
            this.fNC = null;
            return false;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            JAXPNamespaceContextWrapper jAXPNamespaceContextWrapper = StAXEventResultBuilder.this.fNamespaceContext;
            int i = this.fIndex;
            this.fIndex = i + 1;
            String declaredPrefixAt = jAXPNamespaceContextWrapper.getDeclaredPrefixAt(i);
            String namespaceURI = this.fNC.getNamespaceURI(declaredPrefixAt);
            if (declaredPrefixAt.length() == 0) {
                XMLEventFactory xMLEventFactory = StAXEventResultBuilder.this.fEventFactory;
                if (namespaceURI == null) {
                    namespaceURI = "";
                }
                return xMLEventFactory.createNamespace(namespaceURI);
            }
            XMLEventFactory xMLEventFactory2 = StAXEventResultBuilder.this.fEventFactory;
            if (namespaceURI == null) {
                namespaceURI = "";
            }
            return xMLEventFactory2.createNamespace(declaredPrefixAt, namespaceURI);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public StAXEventResultBuilder(StAXValidatorHelper stAXValidatorHelper, JAXPNamespaceContextWrapper jAXPNamespaceContextWrapper) {
        this.fStAXValidatorHelper = stAXValidatorHelper;
        this.fNamespaceContext = jAXPNamespaceContextWrapper;
    }

    private Iterator getAttributeIterator(XMLAttributes xMLAttributes, int i) {
        return i > 0 ? new AttributeIterator(xMLAttributes, i) : EMPTY_COLLECTION_ITERATOR;
    }

    private Iterator getNamespaceIterator() {
        int declaredPrefixCount = this.fNamespaceContext.getDeclaredPrefixCount();
        return declaredPrefixCount > 0 ? new NamespaceIterator(declaredPrefixCount) : EMPTY_COLLECTION_ITERATOR;
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void cdata(Characters characters) throws XMLStreamException {
        this.fEventWriter.add(characters);
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void characters(Characters characters) throws XMLStreamException {
        this.fEventWriter.add(characters);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        XMLEventWriter xMLEventWriter;
        Characters charactersCreateCData;
        if (this.fIgnoreChars) {
            return;
        }
        try {
            if (this.fInCDATA) {
                xMLEventWriter = this.fEventWriter;
                charactersCreateCData = this.fEventFactory.createCData(xMLString.toString());
            } else {
                xMLEventWriter = this.fEventWriter;
                charactersCreateCData = this.fEventFactory.createCharacters(xMLString.toString());
            }
            xMLEventWriter.add(charactersCreateCData);
        } catch (XMLStreamException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void comment(XMLStreamReader xMLStreamReader) throws XMLStreamException {
        this.fEventWriter.add(this.fEventFactory.createComment(xMLStreamReader.getText()));
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void comment(Comment comment) throws XMLStreamException {
        this.fEventWriter.add(comment);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void doctypeDecl(DTD dtd) throws XMLStreamException {
        this.fEventWriter.add(dtd);
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
        this.fEventWriter.add(this.fEventFactory.createEndDocument());
        this.fEventWriter.flush();
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void endDocument(EndDocument endDocument) throws XMLStreamException {
        this.fEventWriter.add(endDocument);
        this.fEventWriter.flush();
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endDocument(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(QName qName, Augmentations augmentations) throws XNIException {
        try {
            XMLEvent currentEvent = this.fStAXValidatorHelper.getCurrentEvent();
            if (currentEvent != null) {
                this.fEventWriter.add(currentEvent);
            } else {
                this.fEventWriter.add(this.fEventFactory.createEndElement(qName.prefix, qName.uri, qName.localpart, getNamespaceIterator()));
            }
        } catch (XMLStreamException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endGeneralEntity(String str, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void entityReference(XMLStreamReader xMLStreamReader) throws XMLStreamException {
        String localName = xMLStreamReader.getLocalName();
        this.fEventWriter.add(this.fEventFactory.createEntityReference(localName, this.fStAXValidatorHelper.getEntityDeclaration(localName)));
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void entityReference(EntityReference entityReference) throws XMLStreamException {
        this.fEventWriter.add(entityReference);
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
        XMLEventWriter xMLEventWriter = this.fEventWriter;
        XMLEventFactory xMLEventFactory = this.fEventFactory;
        String pITarget = xMLStreamReader.getPITarget();
        if (pIData == null) {
            pIData = "";
        }
        xMLEventWriter.add(xMLEventFactory.createProcessingInstruction(pITarget, pIData));
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void processingInstruction(ProcessingInstruction processingInstruction) throws XMLStreamException {
        this.fEventWriter.add(processingInstruction);
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
        this.fEventWriter = stAXResult != null ? stAXResult.getXMLEventWriter() : null;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startCDATA(Augmentations augmentations) throws XNIException {
        this.fInCDATA = true;
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void startDocument(XMLStreamReader xMLStreamReader) throws XMLStreamException {
        String version = xMLStreamReader.getVersion();
        String characterEncodingScheme = xMLStreamReader.getCharacterEncodingScheme();
        boolean zStandaloneSet = xMLStreamReader.standaloneSet();
        XMLEventWriter xMLEventWriter = this.fEventWriter;
        XMLEventFactory xMLEventFactory = this.fEventFactory;
        if (characterEncodingScheme == null) {
            characterEncodingScheme = "UTF-8";
        }
        if (version == null) {
            version = BuildConfig.VERSION_NAME;
        }
        xMLEventWriter.add(xMLEventFactory.createStartDocument(characterEncodingScheme, version, zStandaloneSet));
    }

    @Override // org.apache.xerces.jaxp.validation.StAXDocumentHandler
    public void startDocument(StartDocument startDocument) throws XMLStreamException {
        this.fEventWriter.add(startDocument);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startDocument(XMLLocator xMLLocator, String str, org.apache.xerces.xni.NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        XMLEvent currentEvent;
        try {
            int length = xMLAttributes.getLength();
            if (length != 0 || (currentEvent = this.fStAXValidatorHelper.getCurrentEvent()) == null) {
                this.fEventWriter.add(this.fEventFactory.createStartElement(qName.prefix, qName.uri != null ? qName.uri : "", qName.localpart, getAttributeIterator(xMLAttributes, length), getNamespaceIterator(), this.fNamespaceContext.getNamespaceContext()));
            } else {
                this.fEventWriter.add(currentEvent);
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
