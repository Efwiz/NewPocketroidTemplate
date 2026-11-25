package org.xml.sax.helpers;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.xml.XMLConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/* JADX INFO: loaded from: classes3.dex */
public class ParserAdapter implements XMLReader, DocumentHandler {
    private static final String FEATURES = "http://xml.org/sax/features/";
    private static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
    private static final String XMLNS_URIs = "http://xml.org/sax/features/xmlns-uris";
    private AttributeListAdapter attAdapter;
    private AttributesImpl atts;
    ContentHandler contentHandler;
    DTDHandler dtdHandler;
    EntityResolver entityResolver;
    ErrorHandler errorHandler;
    Locator locator;
    private String[] nameParts;
    private boolean namespaces;
    private NamespaceSupport nsSupport;
    private Parser parser;
    private boolean parsing;
    private boolean prefixes;
    private boolean uris;

    final class AttributeListAdapter implements Attributes {
        private AttributeList qAtts;

        AttributeListAdapter() {
        }

        @Override // org.xml.sax.Attributes
        public int getIndex(String str) {
            int length = ParserAdapter.this.atts.getLength();
            for (int i = 0; i < length; i++) {
                if (this.qAtts.getName(i).equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        @Override // org.xml.sax.Attributes
        public int getIndex(String str, String str2) {
            return -1;
        }

        @Override // org.xml.sax.Attributes
        public int getLength() {
            return this.qAtts.getLength();
        }

        @Override // org.xml.sax.Attributes
        public String getLocalName(int i) {
            return "";
        }

        @Override // org.xml.sax.Attributes
        public String getQName(int i) {
            return this.qAtts.getName(i).intern();
        }

        @Override // org.xml.sax.Attributes
        public String getType(int i) {
            return this.qAtts.getType(i).intern();
        }

        @Override // org.xml.sax.Attributes
        public String getType(String str) {
            return this.qAtts.getType(str).intern();
        }

        @Override // org.xml.sax.Attributes
        public String getType(String str, String str2) {
            return null;
        }

        @Override // org.xml.sax.Attributes
        public String getURI(int i) {
            return "";
        }

        @Override // org.xml.sax.Attributes
        public String getValue(int i) {
            return this.qAtts.getValue(i);
        }

        @Override // org.xml.sax.Attributes
        public String getValue(String str) {
            return this.qAtts.getValue(str);
        }

        @Override // org.xml.sax.Attributes
        public String getValue(String str, String str2) {
            return null;
        }

        void setAttributeList(AttributeList attributeList) {
            this.qAtts = attributeList;
        }
    }

    public ParserAdapter() throws SAXException {
        this.parsing = false;
        this.nameParts = new String[3];
        this.parser = null;
        this.atts = null;
        this.namespaces = true;
        this.prefixes = false;
        this.uris = false;
        this.entityResolver = null;
        this.dtdHandler = null;
        this.contentHandler = null;
        this.errorHandler = null;
        String property = System.getProperty("org.xml.sax.parser");
        try {
            setup(ParserFactory.makeParser());
        } catch (ClassCastException unused) {
            throw new SAXException(new StringBuffer("SAX1 driver class ").append(property).append(" does not implement org.xml.sax.Parser").toString());
        } catch (ClassNotFoundException e) {
            throw new SAXException(new StringBuffer("Cannot find SAX1 driver class ").append(property).toString(), e);
        } catch (IllegalAccessException e2) {
            throw new SAXException(new StringBuffer("SAX1 driver class ").append(property).append(" found but cannot be loaded").toString(), e2);
        } catch (InstantiationException e3) {
            throw new SAXException(new StringBuffer("SAX1 driver class ").append(property).append(" loaded but cannot be instantiated").toString(), e3);
        } catch (NullPointerException unused2) {
            throw new SAXException("System property org.xml.sax.parser not specified");
        }
    }

    public ParserAdapter(Parser parser) {
        this.parsing = false;
        this.nameParts = new String[3];
        this.parser = null;
        this.atts = null;
        this.namespaces = true;
        this.prefixes = false;
        this.uris = false;
        this.entityResolver = null;
        this.dtdHandler = null;
        this.contentHandler = null;
        this.errorHandler = null;
        setup(parser);
    }

    private void checkNotParsing(String str, String str2) throws SAXNotSupportedException {
        if (this.parsing) {
            throw new SAXNotSupportedException(new StringBuffer("Cannot change ").append(str).append(StringUtil.SPACE).append(str2).append(" while parsing").toString());
        }
    }

    private SAXParseException makeException(String str) {
        return this.locator != null ? new SAXParseException(str, this.locator) : new SAXParseException(str, null, null, -1, -1);
    }

    private String[] processName(String str, boolean z, boolean z2) throws SAXException {
        String[] strArrProcessName = this.nsSupport.processName(str, this.nameParts, z);
        if (strArrProcessName != null) {
            return strArrProcessName;
        }
        if (z2) {
            throw makeException(new StringBuffer("Undeclared prefix: ").append(str).toString());
        }
        reportError(new StringBuffer("Undeclared prefix: ").append(str).toString());
        return new String[]{"", "", str.intern()};
    }

    private void setup(Parser parser) {
        if (parser == null) {
            throw new NullPointerException("Parser argument must not be null");
        }
        this.parser = parser;
        this.atts = new AttributesImpl();
        this.nsSupport = new NamespaceSupport();
        this.attAdapter = new AttributeListAdapter();
    }

    private void setupParser() {
        if (!this.prefixes && !this.namespaces) {
            throw new IllegalStateException();
        }
        this.nsSupport.reset();
        if (this.uris) {
            this.nsSupport.setNamespaceDeclUris(true);
        }
        EntityResolver entityResolver = this.entityResolver;
        if (entityResolver != null) {
            this.parser.setEntityResolver(entityResolver);
        }
        DTDHandler dTDHandler = this.dtdHandler;
        if (dTDHandler != null) {
            this.parser.setDTDHandler(dTDHandler);
        }
        ErrorHandler errorHandler = this.errorHandler;
        if (errorHandler != null) {
            this.parser.setErrorHandler(errorHandler);
        }
        this.parser.setDocumentHandler(this);
        this.locator = null;
    }

    @Override // org.xml.sax.DocumentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.characters(cArr, i, i2);
        }
    }

    @Override // org.xml.sax.DocumentHandler
    public void endDocument() throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.endDocument();
        }
    }

    @Override // org.xml.sax.DocumentHandler
    public void endElement(String str) throws SAXException {
        if (!this.namespaces) {
            ContentHandler contentHandler = this.contentHandler;
            if (contentHandler != null) {
                contentHandler.endElement("", "", str.intern());
                return;
            }
            return;
        }
        String[] strArrProcessName = processName(str, false, false);
        ContentHandler contentHandler2 = this.contentHandler;
        if (contentHandler2 != null) {
            contentHandler2.endElement(strArrProcessName[0], strArrProcessName[1], strArrProcessName[2]);
            Enumeration declaredPrefixes = this.nsSupport.getDeclaredPrefixes();
            while (declaredPrefixes.hasMoreElements()) {
                this.contentHandler.endPrefixMapping((String) declaredPrefixes.nextElement());
            }
        }
        this.nsSupport.popContext();
    }

    @Override // org.xml.sax.XMLReader
    public ContentHandler getContentHandler() {
        return this.contentHandler;
    }

    @Override // org.xml.sax.XMLReader
    public DTDHandler getDTDHandler() {
        return this.dtdHandler;
    }

    @Override // org.xml.sax.XMLReader
    public EntityResolver getEntityResolver() {
        return this.entityResolver;
    }

    @Override // org.xml.sax.XMLReader
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    @Override // org.xml.sax.XMLReader
    public boolean getFeature(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str.equals(NAMESPACES)) {
            return this.namespaces;
        }
        if (str.equals(NAMESPACE_PREFIXES)) {
            return this.prefixes;
        }
        if (str.equals(XMLNS_URIs)) {
            return this.uris;
        }
        throw new SAXNotRecognizedException(new StringBuffer("Feature: ").append(str).toString());
    }

    @Override // org.xml.sax.XMLReader
    public Object getProperty(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        throw new SAXNotRecognizedException(new StringBuffer("Property: ").append(str).toString());
    }

    @Override // org.xml.sax.DocumentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.ignorableWhitespace(cArr, i, i2);
        }
    }

    @Override // org.xml.sax.XMLReader
    public void parse(String str) throws SAXException, IOException {
        parse(new InputSource(str));
    }

    @Override // org.xml.sax.XMLReader
    public void parse(InputSource inputSource) throws SAXException, IOException {
        if (this.parsing) {
            throw new SAXException("Parser is already in use");
        }
        setupParser();
        this.parsing = true;
        try {
            this.parser.parse(inputSource);
        } finally {
            this.parsing = false;
        }
    }

    @Override // org.xml.sax.DocumentHandler
    public void processingInstruction(String str, String str2) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.processingInstruction(str, str2);
        }
    }

    void reportError(String str) throws SAXException {
        ErrorHandler errorHandler = this.errorHandler;
        if (errorHandler != null) {
            errorHandler.error(makeException(str));
        }
    }

    @Override // org.xml.sax.XMLReader
    public void setContentHandler(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    @Override // org.xml.sax.XMLReader
    public void setDTDHandler(DTDHandler dTDHandler) {
        this.dtdHandler = dTDHandler;
    }

    @Override // org.xml.sax.DocumentHandler
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.setDocumentLocator(locator);
        }
    }

    @Override // org.xml.sax.XMLReader
    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }

    @Override // org.xml.sax.XMLReader
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override // org.xml.sax.XMLReader
    public void setFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str.equals(NAMESPACES)) {
            checkNotParsing(ConfigConstants.CONFIG_FEATURE_SECTION, str);
            this.namespaces = z;
            if (z || this.prefixes) {
                return;
            }
            this.prefixes = true;
            return;
        }
        if (!str.equals(NAMESPACE_PREFIXES)) {
            if (!str.equals(XMLNS_URIs)) {
                throw new SAXNotRecognizedException(new StringBuffer("Feature: ").append(str).toString());
            }
            checkNotParsing(ConfigConstants.CONFIG_FEATURE_SECTION, str);
            this.uris = z;
            return;
        }
        checkNotParsing(ConfigConstants.CONFIG_FEATURE_SECTION, str);
        this.prefixes = z;
        if (z || this.namespaces) {
            return;
        }
        this.namespaces = true;
    }

    @Override // org.xml.sax.XMLReader
    public void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
        throw new SAXNotRecognizedException(new StringBuffer("Property: ").append(str).toString());
    }

    @Override // org.xml.sax.DocumentHandler
    public void startDocument() throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.startDocument();
        }
    }

    /* JADX WARN: Code duplicated, block: B:69:0x00c7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // org.xml.sax.DocumentHandler
    public void startElement(String str, AttributeList attributeList) throws SAXException {
        int i;
        AttributesImpl attributesImpl;
        String strIntern;
        String str2;
        String strSubstring;
        if (!this.namespaces) {
            if (this.contentHandler != null) {
                this.attAdapter.setAttributeList(attributeList);
                this.contentHandler.startElement("", "", str.intern(), this.attAdapter);
                return;
            }
            return;
        }
        this.nsSupport.pushContext();
        int length = attributeList.getLength();
        int i2 = 0;
        while (true) {
            i = -1;
            if (i2 >= length) {
                break;
            }
            String name2 = attributeList.getName(i2);
            if (name2.startsWith(XMLConstants.XMLNS_ATTRIBUTE)) {
                int iIndexOf = name2.indexOf(58);
                if (iIndexOf == -1 && name2.length() == 5) {
                    strSubstring = "";
                } else if (iIndexOf == 5) {
                    strSubstring = name2.substring(iIndexOf + 1);
                }
                String value = attributeList.getValue(i2);
                if (this.nsSupport.declarePrefix(strSubstring, value)) {
                    ContentHandler contentHandler = this.contentHandler;
                    if (contentHandler != null) {
                        contentHandler.startPrefixMapping(strSubstring, value);
                    }
                } else {
                    reportError(new StringBuffer("Illegal Namespace prefix: ").append(strSubstring).toString());
                }
            }
            i2++;
        }
        this.atts.clear();
        int i3 = 0;
        Vector vector = null;
        while (i3 < length) {
            String name3 = attributeList.getName(i3);
            String type = attributeList.getType(i3);
            String value2 = attributeList.getValue(i3);
            if (name3.startsWith(XMLConstants.XMLNS_ATTRIBUTE)) {
                int iIndexOf2 = name3.indexOf(58);
                String strSubstring2 = (iIndexOf2 == i && name3.length() == 5) ? "" : iIndexOf2 != 5 ? null : name3.substring(6);
                if (strSubstring2 == null) {
                    String[] strArrProcessName = processName(name3, true, true);
                    this.atts.addAttribute(strArrProcessName[0], strArrProcessName[1], strArrProcessName[2], type, value2);
                } else if (this.prefixes) {
                    if (this.uris) {
                        attributesImpl = this.atts;
                        str2 = "http://www.w3.org/XML/1998/namespace";
                        strIntern = name3.intern();
                    } else {
                        attributesImpl = this.atts;
                        strSubstring2 = "";
                        strIntern = name3.intern();
                        str2 = "";
                    }
                    attributesImpl.addAttribute(str2, strSubstring2, strIntern, type, value2);
                }
            } else {
                try {
                    String[] strArrProcessName2 = processName(name3, true, true);
                    this.atts.addAttribute(strArrProcessName2[0], strArrProcessName2[1], strArrProcessName2[2], type, value2);
                } catch (SAXException e) {
                    if (vector == null) {
                        vector = new Vector();
                    }
                    vector.addElement(e);
                    this.atts.addAttribute("", name3, name3, type, value2);
                }
            }
            i3++;
            i = -1;
        }
        if (vector != null && this.errorHandler != null) {
            for (int i4 = 0; i4 < vector.size(); i4++) {
                this.errorHandler.error((SAXParseException) vector.elementAt(i4));
            }
        }
        if (this.contentHandler != null) {
            String[] strArrProcessName3 = processName(str, false, false);
            this.contentHandler.startElement(strArrProcessName3[0], strArrProcessName3[1], strArrProcessName3[2], this.atts);
        }
    }
}
