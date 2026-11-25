package org.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.validation.EntityState;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.util.JAXPNamespaceContextWrapper;
import org.apache.xerces.util.StAXLocationWrapper;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLParseException;
import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes13.dex */
final class StAXValidatorHelper implements ValidatorHelper, EntityState {
    private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
    private static final String STRING_INTERNING = "javax.xml.stream.isInterning";
    private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    private final XMLSchemaValidatorComponentManager fComponentManager;
    final ArrayList fDeclaredPrefixes;
    private final XMLErrorReporter fErrorReporter;
    private EventHelper fEventHelper;
    private final JAXPNamespaceContextWrapper fNamespaceContext;
    private final XMLSchemaValidator fSchemaValidator;
    private StAXEventResultBuilder fStAXEventResultBuilder;
    private StAXStreamResultBuilder fStAXStreamResultBuilder;
    private StAXDocumentHandler fStAXValidatorHandler;
    private StreamHelper fStreamHelper;
    final XMLStringBuffer fStringBuffer;
    private final SymbolTable fSymbolTable;
    final XMLString fTempString;
    private final ValidationManager fValidationManager;
    private final StAXLocationWrapper fStAXLocationWrapper = new StAXLocationWrapper();
    private final XMLStreamReaderLocation fXMLStreamReaderLocation = new XMLStreamReaderLocation();
    private HashMap fEntities = null;
    private boolean fStringsInternalized = false;
    private int fDepth = 0;
    private XMLEvent fCurrentEvent = null;
    final QName fElementQName = new QName();
    final QName fAttributeQName = new QName();
    final XMLAttributesImpl fAttributes = new XMLAttributesImpl();

    final class EventHelper {
        private static final int CHUNK_MASK = 1023;
        private static final int CHUNK_SIZE = 1024;
        private final char[] fCharBuffer = new char[1024];

        EventHelper() {
        }

        private void fillDeclaredPrefixes(Iterator it) {
            StAXValidatorHelper.this.fDeclaredPrefixes.clear();
            while (it.hasNext()) {
                String prefix = ((Namespace) it.next()).getPrefix();
                ArrayList arrayList = StAXValidatorHelper.this.fDeclaredPrefixes;
                if (prefix == null) {
                    prefix = "";
                }
                arrayList.add(prefix);
            }
        }

        private void fillDeclaredPrefixes(EndElement endElement) {
            fillDeclaredPrefixes(endElement.getNamespaces());
        }

        private void fillDeclaredPrefixes(StartElement startElement) {
            fillDeclaredPrefixes(startElement.getNamespaces());
        }

        private void fillQName(QName qName, javax.xml.namespace.QName qName2) {
            StAXValidatorHelper.this.fillQName(qName, qName2.getNamespaceURI(), qName2.getLocalPart(), qName2.getPrefix());
        }

        private void fillXMLAttributes(StartElement startElement) {
            StAXValidatorHelper.this.fAttributes.removeAllAttributes();
            Iterator attributes = startElement.getAttributes();
            while (attributes.hasNext()) {
                Attribute attribute = (Attribute) attributes.next();
                fillQName(StAXValidatorHelper.this.fAttributeQName, attribute.getName());
                String dTDType = attribute.getDTDType();
                int length = StAXValidatorHelper.this.fAttributes.getLength();
                XMLAttributesImpl xMLAttributesImpl = StAXValidatorHelper.this.fAttributes;
                QName qName = StAXValidatorHelper.this.fAttributeQName;
                if (dTDType == null) {
                    dTDType = XMLSymbols.fCDATASymbol;
                }
                xMLAttributesImpl.addAttributeNS(qName, dTDType, attribute.getValue());
                StAXValidatorHelper.this.fAttributes.setSpecified(length, attribute.isSpecified());
            }
        }

        private void sendCharactersToValidator(String str) {
            if (str != null) {
                int length = str.length();
                int i = length & CHUNK_MASK;
                if (i > 0) {
                    str.getChars(0, i, this.fCharBuffer, 0);
                    StAXValidatorHelper.this.fTempString.setValues(this.fCharBuffer, 0, i);
                    StAXValidatorHelper.this.fSchemaValidator.characters(StAXValidatorHelper.this.fTempString, null);
                }
                while (i < length) {
                    int i2 = i + 1024;
                    str.getChars(i, i2, this.fCharBuffer, 0);
                    StAXValidatorHelper.this.fTempString.setValues(this.fCharBuffer, 0, 1024);
                    StAXValidatorHelper.this.fSchemaValidator.characters(StAXValidatorHelper.this.fTempString, null);
                    i = i2;
                }
            }
        }

        final void validate(XMLEventReader xMLEventReader, StAXResult stAXResult) throws SAXException, XMLStreamException {
            StAXValidatorHelper.this.fCurrentEvent = xMLEventReader.peek();
            if (StAXValidatorHelper.this.fCurrentEvent != null) {
                int eventType = StAXValidatorHelper.this.fCurrentEvent.getEventType();
                if (eventType != 7 && eventType != 1) {
                    throw new SAXException(JAXPValidationMessageFormatter.formatMessage(StAXValidatorHelper.this.fComponentManager.getLocale(), "StAXIllegalInitialState", null));
                }
                StAXValidatorHelper.this.setup(null, stAXResult, false);
                StAXValidatorHelper.this.fSchemaValidator.startDocument(StAXValidatorHelper.this.fStAXLocationWrapper, null, StAXValidatorHelper.this.fNamespaceContext, null);
                while (xMLEventReader.hasNext()) {
                    StAXValidatorHelper.this.fCurrentEvent = xMLEventReader.nextEvent();
                    switch (StAXValidatorHelper.this.fCurrentEvent.getEventType()) {
                        case 1:
                            StAXValidatorHelper.access$504(StAXValidatorHelper.this);
                            StartElement startElementAsStartElement = StAXValidatorHelper.this.fCurrentEvent.asStartElement();
                            fillQName(StAXValidatorHelper.this.fElementQName, startElementAsStartElement.getName());
                            fillXMLAttributes(startElementAsStartElement);
                            fillDeclaredPrefixes(startElementAsStartElement);
                            StAXValidatorHelper.this.fNamespaceContext.setNamespaceContext(startElementAsStartElement.getNamespaceContext());
                            StAXValidatorHelper.this.fStAXLocationWrapper.setLocation(startElementAsStartElement.getLocation());
                            StAXValidatorHelper.this.fSchemaValidator.startElement(StAXValidatorHelper.this.fElementQName, StAXValidatorHelper.this.fAttributes, null);
                            break;
                        case 2:
                            EndElement endElementAsEndElement = StAXValidatorHelper.this.fCurrentEvent.asEndElement();
                            fillQName(StAXValidatorHelper.this.fElementQName, endElementAsEndElement.getName());
                            fillDeclaredPrefixes(endElementAsEndElement);
                            StAXValidatorHelper.this.fStAXLocationWrapper.setLocation(endElementAsEndElement.getLocation());
                            StAXValidatorHelper.this.fSchemaValidator.endElement(StAXValidatorHelper.this.fElementQName, null);
                            if (StAXValidatorHelper.access$506(StAXValidatorHelper.this) <= 0) {
                                StAXValidatorHelper.this.fSchemaValidator.endDocument(null);
                            }
                            break;
                        case 3:
                            if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                                StAXValidatorHelper.this.fStAXValidatorHandler.processingInstruction((ProcessingInstruction) StAXValidatorHelper.this.fCurrentEvent);
                            }
                            break;
                        case 4:
                        case 6:
                            if (StAXValidatorHelper.this.fStAXValidatorHandler == null) {
                                sendCharactersToValidator(StAXValidatorHelper.this.fCurrentEvent.asCharacters().getData());
                            } else {
                                Characters charactersAsCharacters = StAXValidatorHelper.this.fCurrentEvent.asCharacters();
                                StAXValidatorHelper.this.fStAXValidatorHandler.setIgnoringCharacters(true);
                                sendCharactersToValidator(charactersAsCharacters.getData());
                                StAXValidatorHelper.this.fStAXValidatorHandler.setIgnoringCharacters(false);
                                StAXValidatorHelper.this.fStAXValidatorHandler.characters(charactersAsCharacters);
                            }
                            break;
                        case 5:
                            if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                                StAXValidatorHelper.this.fStAXValidatorHandler.comment((Comment) StAXValidatorHelper.this.fCurrentEvent);
                            }
                            break;
                        case 7:
                            StAXValidatorHelper.access$504(StAXValidatorHelper.this);
                            if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                                StAXValidatorHelper.this.fStAXValidatorHandler.startDocument((StartDocument) StAXValidatorHelper.this.fCurrentEvent);
                            }
                            break;
                        case 8:
                            if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                                StAXValidatorHelper.this.fStAXValidatorHandler.endDocument((EndDocument) StAXValidatorHelper.this.fCurrentEvent);
                            }
                            break;
                        case 9:
                            if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                                StAXValidatorHelper.this.fStAXValidatorHandler.entityReference((EntityReference) StAXValidatorHelper.this.fCurrentEvent);
                            }
                            break;
                        case 11:
                            DTD dtd = (DTD) StAXValidatorHelper.this.fCurrentEvent;
                            StAXValidatorHelper.this.processEntityDeclarations(dtd.getEntities());
                            if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                                StAXValidatorHelper.this.fStAXValidatorHandler.doctypeDecl(dtd);
                            }
                            break;
                        case 12:
                            if (StAXValidatorHelper.this.fStAXValidatorHandler == null) {
                                StAXValidatorHelper.this.fSchemaValidator.startCDATA(null);
                                sendCharactersToValidator(StAXValidatorHelper.this.fCurrentEvent.asCharacters().getData());
                                StAXValidatorHelper.this.fSchemaValidator.endCDATA(null);
                            } else {
                                Characters charactersAsCharacters2 = StAXValidatorHelper.this.fCurrentEvent.asCharacters();
                                StAXValidatorHelper.this.fStAXValidatorHandler.setIgnoringCharacters(true);
                                StAXValidatorHelper.this.fSchemaValidator.startCDATA(null);
                                sendCharactersToValidator(StAXValidatorHelper.this.fCurrentEvent.asCharacters().getData());
                                StAXValidatorHelper.this.fSchemaValidator.endCDATA(null);
                                StAXValidatorHelper.this.fStAXValidatorHandler.setIgnoringCharacters(false);
                                StAXValidatorHelper.this.fStAXValidatorHandler.cdata(charactersAsCharacters2);
                            }
                            break;
                    }
                }
                StAXValidatorHelper.this.fSchemaValidator.endDocument(null);
            }
        }
    }

    final class StreamHelper {
        StreamHelper() {
        }

        private void fillDeclaredPrefixes(XMLStreamReader xMLStreamReader) {
            StAXValidatorHelper.this.fDeclaredPrefixes.clear();
            int namespaceCount = xMLStreamReader.getNamespaceCount();
            for (int i = 0; i < namespaceCount; i++) {
                String namespacePrefix = xMLStreamReader.getNamespacePrefix(i);
                ArrayList arrayList = StAXValidatorHelper.this.fDeclaredPrefixes;
                if (namespacePrefix == null) {
                    namespacePrefix = "";
                }
                arrayList.add(namespacePrefix);
            }
        }

        private void fillXMLAttributes(XMLStreamReader xMLStreamReader) {
            StAXValidatorHelper.this.fAttributes.removeAllAttributes();
            int attributeCount = xMLStreamReader.getAttributeCount();
            for (int i = 0; i < attributeCount; i++) {
                StAXValidatorHelper stAXValidatorHelper = StAXValidatorHelper.this;
                stAXValidatorHelper.fillQName(stAXValidatorHelper.fAttributeQName, xMLStreamReader.getAttributeNamespace(i), xMLStreamReader.getAttributeLocalName(i), xMLStreamReader.getAttributePrefix(i));
                String attributeType = xMLStreamReader.getAttributeType(i);
                XMLAttributesImpl xMLAttributesImpl = StAXValidatorHelper.this.fAttributes;
                QName qName = StAXValidatorHelper.this.fAttributeQName;
                if (attributeType == null) {
                    attributeType = XMLSymbols.fCDATASymbol;
                }
                xMLAttributesImpl.addAttributeNS(qName, attributeType, xMLStreamReader.getAttributeValue(i));
                StAXValidatorHelper.this.fAttributes.setSpecified(i, xMLStreamReader.isAttributeSpecified(i));
            }
        }

        final void validate(XMLStreamReader xMLStreamReader, StAXResult stAXResult) throws SAXException, XMLStreamException {
            if (xMLStreamReader.hasNext()) {
                int eventType = xMLStreamReader.getEventType();
                if (eventType != 7 && eventType != 1) {
                    throw new SAXException(JAXPValidationMessageFormatter.formatMessage(StAXValidatorHelper.this.fComponentManager.getLocale(), "StAXIllegalInitialState", null));
                }
                StAXValidatorHelper.this.fXMLStreamReaderLocation.setXMLStreamReader(xMLStreamReader);
                Object property = Boolean.FALSE;
                try {
                    property = xMLStreamReader.getProperty(StAXValidatorHelper.STRING_INTERNING);
                } catch (Exception unused) {
                }
                StAXValidatorHelper stAXValidatorHelper = StAXValidatorHelper.this;
                stAXValidatorHelper.setup(stAXValidatorHelper.fXMLStreamReaderLocation, stAXResult, Boolean.TRUE.equals(property));
                StAXValidatorHelper.this.fSchemaValidator.startDocument(StAXValidatorHelper.this.fStAXLocationWrapper, null, StAXValidatorHelper.this.fNamespaceContext, null);
                do {
                    switch (eventType) {
                        case 1:
                            StAXValidatorHelper.access$504(StAXValidatorHelper.this);
                            StAXValidatorHelper stAXValidatorHelper2 = StAXValidatorHelper.this;
                            stAXValidatorHelper2.fillQName(stAXValidatorHelper2.fElementQName, xMLStreamReader.getNamespaceURI(), xMLStreamReader.getLocalName(), xMLStreamReader.getPrefix());
                            fillXMLAttributes(xMLStreamReader);
                            fillDeclaredPrefixes(xMLStreamReader);
                            StAXValidatorHelper.this.fNamespaceContext.setNamespaceContext(xMLStreamReader.getNamespaceContext());
                            StAXValidatorHelper.this.fSchemaValidator.startElement(StAXValidatorHelper.this.fElementQName, StAXValidatorHelper.this.fAttributes, null);
                            break;
                        case 2:
                            StAXValidatorHelper stAXValidatorHelper3 = StAXValidatorHelper.this;
                            stAXValidatorHelper3.fillQName(stAXValidatorHelper3.fElementQName, xMLStreamReader.getNamespaceURI(), xMLStreamReader.getLocalName(), xMLStreamReader.getPrefix());
                            fillDeclaredPrefixes(xMLStreamReader);
                            StAXValidatorHelper.this.fNamespaceContext.setNamespaceContext(xMLStreamReader.getNamespaceContext());
                            StAXValidatorHelper.this.fSchemaValidator.endElement(StAXValidatorHelper.this.fElementQName, null);
                            StAXValidatorHelper.access$506(StAXValidatorHelper.this);
                            break;
                        case 3:
                            if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                                StAXValidatorHelper.this.fStAXValidatorHandler.processingInstruction(xMLStreamReader);
                            }
                            break;
                        case 4:
                        case 6:
                            StAXValidatorHelper.this.fTempString.setValues(xMLStreamReader.getTextCharacters(), xMLStreamReader.getTextStart(), xMLStreamReader.getTextLength());
                            StAXValidatorHelper.this.fSchemaValidator.characters(StAXValidatorHelper.this.fTempString, null);
                            break;
                        case 5:
                            if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                                StAXValidatorHelper.this.fStAXValidatorHandler.comment(xMLStreamReader);
                            }
                            break;
                        case 7:
                            StAXValidatorHelper.access$504(StAXValidatorHelper.this);
                            if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                                StAXValidatorHelper.this.fStAXValidatorHandler.startDocument(xMLStreamReader);
                            }
                            break;
                        case 9:
                            if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                                StAXValidatorHelper.this.fStAXValidatorHandler.entityReference(xMLStreamReader);
                            }
                            break;
                        case 11:
                            StAXValidatorHelper.this.processEntityDeclarations((List) xMLStreamReader.getProperty("javax.xml.stream.entities"));
                            break;
                        case 12:
                            StAXValidatorHelper.this.fSchemaValidator.startCDATA(null);
                            StAXValidatorHelper.this.fTempString.setValues(xMLStreamReader.getTextCharacters(), xMLStreamReader.getTextStart(), xMLStreamReader.getTextLength());
                            StAXValidatorHelper.this.fSchemaValidator.characters(StAXValidatorHelper.this.fTempString, null);
                            StAXValidatorHelper.this.fSchemaValidator.endCDATA(null);
                            break;
                    }
                    eventType = xMLStreamReader.next();
                    if (xMLStreamReader.hasNext()) {
                    }
                    StAXValidatorHelper.this.fSchemaValidator.endDocument(null);
                    if (eventType == 8 || StAXValidatorHelper.this.fStAXValidatorHandler == null) {
                    }
                    StAXValidatorHelper.this.fStAXValidatorHandler.endDocument(xMLStreamReader);
                    return;
                } while (StAXValidatorHelper.this.fDepth > 0);
                StAXValidatorHelper.this.fSchemaValidator.endDocument(null);
                if (eventType == 8) {
                }
            }
        }
    }

    static final class XMLStreamReaderLocation implements Location {
        private XMLStreamReader reader;

        private Location getLocation() {
            XMLStreamReader xMLStreamReader = this.reader;
            if (xMLStreamReader != null) {
                return xMLStreamReader.getLocation();
            }
            return null;
        }

        @Override // javax.xml.stream.Location
        public int getCharacterOffset() {
            Location location = getLocation();
            if (location != null) {
                return location.getCharacterOffset();
            }
            return -1;
        }

        @Override // javax.xml.stream.Location
        public int getColumnNumber() {
            Location location = getLocation();
            if (location != null) {
                return location.getColumnNumber();
            }
            return -1;
        }

        @Override // javax.xml.stream.Location
        public int getLineNumber() {
            Location location = getLocation();
            if (location != null) {
                return location.getLineNumber();
            }
            return -1;
        }

        @Override // javax.xml.stream.Location
        public String getPublicId() {
            Location location = getLocation();
            if (location != null) {
                return location.getPublicId();
            }
            return null;
        }

        @Override // javax.xml.stream.Location
        public String getSystemId() {
            Location location = getLocation();
            if (location != null) {
                return location.getSystemId();
            }
            return null;
        }

        public void setXMLStreamReader(XMLStreamReader xMLStreamReader) {
            this.reader = xMLStreamReader;
        }
    }

    public StAXValidatorHelper(XMLSchemaValidatorComponentManager xMLSchemaValidatorComponentManager) {
        ArrayList arrayList = new ArrayList();
        this.fDeclaredPrefixes = arrayList;
        this.fTempString = new XMLString();
        this.fStringBuffer = new XMLStringBuffer();
        this.fComponentManager = xMLSchemaValidatorComponentManager;
        this.fErrorReporter = (XMLErrorReporter) xMLSchemaValidatorComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
        this.fSchemaValidator = (XMLSchemaValidator) xMLSchemaValidatorComponentManager.getProperty(SCHEMA_VALIDATOR);
        SymbolTable symbolTable = (SymbolTable) xMLSchemaValidatorComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
        this.fSymbolTable = symbolTable;
        this.fValidationManager = (ValidationManager) xMLSchemaValidatorComponentManager.getProperty(VALIDATION_MANAGER);
        JAXPNamespaceContextWrapper jAXPNamespaceContextWrapper = new JAXPNamespaceContextWrapper(symbolTable);
        this.fNamespaceContext = jAXPNamespaceContextWrapper;
        jAXPNamespaceContextWrapper.setDeclaredPrefixes(arrayList);
    }

    static /* synthetic */ int access$504(StAXValidatorHelper stAXValidatorHelper) {
        int i = stAXValidatorHelper.fDepth + 1;
        stAXValidatorHelper.fDepth = i;
        return i;
    }

    static /* synthetic */ int access$506(StAXValidatorHelper stAXValidatorHelper) {
        int i = stAXValidatorHelper.fDepth - 1;
        stAXValidatorHelper.fDepth = i;
        return i;
    }

    private void setupStAXResultHandler(StAXResult stAXResult) {
        if (stAXResult == null) {
            this.fStAXValidatorHandler = null;
            this.fSchemaValidator.setDocumentHandler(null);
            return;
        }
        if (stAXResult.getXMLStreamWriter() != null) {
            if (this.fStAXStreamResultBuilder == null) {
                this.fStAXStreamResultBuilder = new StAXStreamResultBuilder(this.fNamespaceContext);
            }
            StAXStreamResultBuilder stAXStreamResultBuilder = this.fStAXStreamResultBuilder;
            this.fStAXValidatorHandler = stAXStreamResultBuilder;
            stAXStreamResultBuilder.setStAXResult(stAXResult);
        } else {
            if (this.fStAXEventResultBuilder == null) {
                this.fStAXEventResultBuilder = new StAXEventResultBuilder(this, this.fNamespaceContext);
            }
            StAXEventResultBuilder stAXEventResultBuilder = this.fStAXEventResultBuilder;
            this.fStAXValidatorHandler = stAXEventResultBuilder;
            stAXEventResultBuilder.setStAXResult(stAXResult);
        }
        this.fSchemaValidator.setDocumentHandler(this.fStAXValidatorHandler);
    }

    final void fillQName(QName qName, String str, String str2, String str3) {
        String strAddSymbol;
        String strAddSymbol2;
        String strAddSymbol3 = null;
        if (this.fStringsInternalized) {
            if (str != null && str.length() == 0) {
                str = null;
            }
            if (str2 == null) {
                str2 = XMLSymbols.EMPTY_STRING;
            }
            if (str3 == null) {
                str3 = XMLSymbols.EMPTY_STRING;
            }
            strAddSymbol3 = str;
            strAddSymbol = str2;
        } else {
            if (str != null && str.length() > 0) {
                strAddSymbol3 = this.fSymbolTable.addSymbol(str);
            }
            strAddSymbol = str2 != null ? this.fSymbolTable.addSymbol(str2) : XMLSymbols.EMPTY_STRING;
            str3 = (str3 == null || str3.length() <= 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(str3);
        }
        if (str3 != XMLSymbols.EMPTY_STRING) {
            this.fStringBuffer.clear();
            this.fStringBuffer.append(str3);
            this.fStringBuffer.append(':');
            this.fStringBuffer.append(strAddSymbol);
            strAddSymbol2 = this.fSymbolTable.addSymbol(this.fStringBuffer.ch, this.fStringBuffer.offset, this.fStringBuffer.length);
        } else {
            strAddSymbol2 = strAddSymbol;
        }
        qName.setValues(str3, strAddSymbol, strAddSymbol2, strAddSymbol3);
    }

    final XMLEvent getCurrentEvent() {
        return this.fCurrentEvent;
    }

    final EntityDeclaration getEntityDeclaration(String str) {
        HashMap map = this.fEntities;
        if (map != null) {
            return (EntityDeclaration) map.get(str);
        }
        return null;
    }

    @Override // org.apache.xerces.impl.validation.EntityState
    public boolean isEntityDeclared(String str) {
        HashMap map = this.fEntities;
        if (map != null) {
            return map.containsKey(str);
        }
        return false;
    }

    @Override // org.apache.xerces.impl.validation.EntityState
    public boolean isEntityUnparsed(String str) {
        EntityDeclaration entityDeclaration;
        HashMap map = this.fEntities;
        return (map == null || (entityDeclaration = (EntityDeclaration) map.get(str)) == null || entityDeclaration.getNotationName() == null) ? false : true;
    }

    final void processEntityDeclarations(List list) {
        int size = list != null ? list.size() : 0;
        if (size > 0) {
            if (this.fEntities == null) {
                this.fEntities = new HashMap();
            }
            for (int i = 0; i < size; i++) {
                EntityDeclaration entityDeclaration = (EntityDeclaration) list.get(i);
                this.fEntities.put(entityDeclaration.getName(), entityDeclaration);
            }
        }
    }

    final void setup(Location location, StAXResult stAXResult, boolean z) {
        this.fDepth = 0;
        this.fComponentManager.reset();
        setupStAXResultHandler(stAXResult);
        this.fValidationManager.setEntityState(this);
        HashMap map = this.fEntities;
        if (map != null && !map.isEmpty()) {
            this.fEntities.clear();
        }
        this.fStAXLocationWrapper.setLocation(location);
        this.fErrorReporter.setDocumentLocator(this.fStAXLocationWrapper);
        this.fStringsInternalized = z;
    }

    @Override // org.apache.xerces.jaxp.validation.ValidatorHelper
    public void validate(Source source, Result result) throws SAXException, IOException {
        if (!(result instanceof StAXResult) && result != null) {
            throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceResultMismatch", new Object[]{source.getClass().getName(), result.getClass().getName()}));
        }
        StAXSource stAXSource = (StAXSource) source;
        StAXResult stAXResult = (StAXResult) result;
        try {
            try {
                try {
                    XMLStreamReader xMLStreamReader = stAXSource.getXMLStreamReader();
                    if (xMLStreamReader != null) {
                        if (this.fStreamHelper == null) {
                            this.fStreamHelper = new StreamHelper();
                        }
                        this.fStreamHelper.validate(xMLStreamReader, stAXResult);
                    } else {
                        if (this.fEventHelper == null) {
                            this.fEventHelper = new EventHelper();
                        }
                        this.fEventHelper.validate(stAXSource.getXMLEventReader(), stAXResult);
                    }
                    this.fCurrentEvent = null;
                    this.fStAXLocationWrapper.setLocation(null);
                    this.fXMLStreamReaderLocation.setXMLStreamReader(null);
                    StAXDocumentHandler stAXDocumentHandler = this.fStAXValidatorHandler;
                    if (stAXDocumentHandler != null) {
                        stAXDocumentHandler.setStAXResult(null);
                    }
                } catch (XMLStreamException e) {
                    throw new SAXException(e);
                }
            } catch (XMLParseException e2) {
                throw Util.toSAXParseException(e2);
            } catch (XNIException e3) {
                throw Util.toSAXException(e3);
            }
        } catch (Throwable th) {
            this.fCurrentEvent = null;
            this.fStAXLocationWrapper.setLocation(null);
            this.fXMLStreamReaderLocation.setXMLStreamReader(null);
            StAXDocumentHandler stAXDocumentHandler2 = this.fStAXValidatorHandler;
            if (stAXDocumentHandler2 != null) {
                stAXDocumentHandler2.setStAXResult(null);
            }
            throw th;
        }
    }
}
