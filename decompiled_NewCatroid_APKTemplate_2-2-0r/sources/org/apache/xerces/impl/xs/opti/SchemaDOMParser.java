package org.apache.xerces.impl.xs.opti;

import java.io.IOException;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.w3c.dom.Document;

/* JADX INFO: loaded from: classes13.dex */
public class SchemaDOMParser extends DefaultXMLDocumentHandler {
    public static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    public static final String GENERATE_SYNTHETIC_ANNOTATION = "http://apache.org/xml/features/generate-synthetic-annotations";
    XMLParserConfiguration config;
    private ElementImpl fCurrentAnnotationElement;
    XMLErrorReporter fErrorReporter;
    protected XMLLocator fLocator;
    SchemaDOM schemaDOM;
    protected NamespaceContext fNamespaceContext = null;
    private int fAnnotationDepth = -1;
    private int fInnerAnnotationDepth = -1;
    private int fDepth = -1;
    private boolean fGenerateSyntheticAnnotation = false;
    private BooleanStack fHasNonSchemaAttributes = new BooleanStack();
    private BooleanStack fSawAnnotation = new BooleanStack();
    private XMLAttributes fEmptyAttr = new XMLAttributesImpl();

    private static final class BooleanStack {
        private boolean[] fData;
        private int fDepth;

        private void ensureCapacity(int i) {
            boolean[] zArr = this.fData;
            if (zArr == null) {
                this.fData = new boolean[32];
            } else if (zArr.length <= i) {
                boolean[] zArr2 = new boolean[zArr.length * 2];
                System.arraycopy(zArr, 0, zArr2, 0, zArr.length);
                this.fData = zArr2;
            }
        }

        public void clear() {
            this.fDepth = 0;
        }

        public boolean pop() {
            boolean[] zArr = this.fData;
            int i = this.fDepth - 1;
            this.fDepth = i;
            return zArr[i];
        }

        public void push(boolean z) {
            ensureCapacity(this.fDepth + 1);
            boolean[] zArr = this.fData;
            int i = this.fDepth;
            this.fDepth = i + 1;
            zArr[i] = z;
        }

        public int size() {
            return this.fDepth;
        }
    }

    public SchemaDOMParser(XMLParserConfiguration xMLParserConfiguration) {
        this.config = xMLParserConfiguration;
        xMLParserConfiguration.setDocumentHandler(this);
        xMLParserConfiguration.setDTDHandler(this);
        xMLParserConfiguration.setDTDContentModelHandler(this);
    }

    private boolean hasNonSchemaAttributes(QName qName, XMLAttributes xMLAttributes) {
        int length = xMLAttributes.getLength();
        for (int i = 0; i < length; i++) {
            String uri = xMLAttributes.getURI(i);
            if (uri != null && uri != SchemaSymbols.URI_SCHEMAFORSCHEMA && uri != NamespaceContext.XMLNS_URI && (uri != NamespaceContext.XML_URI || xMLAttributes.getQName(i) != SchemaSymbols.ATT_XML_LANG || qName.localpart != SchemaSymbols.ELT_SCHEMA)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler, org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fInnerAnnotationDepth != -1) {
            this.schemaDOM.characters(xMLString);
            return;
        }
        for (int i = xMLString.offset; i < xMLString.offset + xMLString.length; i++) {
            if (!XMLChar.isSpace(xMLString.ch[i])) {
                this.fErrorReporter.reportError(this.fLocator, XSMessageFormatter.SCHEMA_DOMAIN, "s4s-elt-character", new Object[]{new String(xMLString.ch, i, (xMLString.length + xMLString.offset) - i)}, (short) 1);
                return;
            }
        }
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler, org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fAnnotationDepth > -1) {
            this.schemaDOM.comment(xMLString);
        }
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler, org.apache.xerces.xni.XMLDocumentHandler
    public void emptyElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        if (this.fGenerateSyntheticAnnotation && this.fAnnotationDepth == -1 && qName.uri == SchemaSymbols.URI_SCHEMAFORSCHEMA && qName.localpart != SchemaSymbols.ELT_ANNOTATION && hasNonSchemaAttributes(qName, xMLAttributes)) {
            this.schemaDOM.startElement(qName, xMLAttributes, this.fLocator.getLineNumber(), this.fLocator.getColumnNumber(), this.fLocator.getCharacterOffset());
            xMLAttributes.removeAllAttributes();
            String prefix = this.fNamespaceContext.getPrefix(SchemaSymbols.URI_SCHEMAFORSCHEMA);
            String str = prefix.length() == 0 ? SchemaSymbols.ELT_ANNOTATION : prefix + ':' + SchemaSymbols.ELT_ANNOTATION;
            this.schemaDOM.startAnnotation(str, xMLAttributes, this.fNamespaceContext);
            String str2 = prefix.length() == 0 ? SchemaSymbols.ELT_DOCUMENTATION : prefix + ':' + SchemaSymbols.ELT_DOCUMENTATION;
            this.schemaDOM.startAnnotationElement(str2, xMLAttributes);
            this.schemaDOM.charactersRaw("SYNTHETIC_ANNOTATION");
            this.schemaDOM.endSyntheticAnnotationElement(str2, false);
            this.schemaDOM.endSyntheticAnnotationElement(str, true);
            this.schemaDOM.endElement();
            return;
        }
        if (this.fAnnotationDepth != -1) {
            this.schemaDOM.startAnnotationElement(qName, xMLAttributes);
        } else if (qName.uri == SchemaSymbols.URI_SCHEMAFORSCHEMA && qName.localpart == SchemaSymbols.ELT_ANNOTATION) {
            this.schemaDOM.startAnnotation(qName, xMLAttributes, this.fNamespaceContext);
        }
        ElementImpl elementImplEmptyElement = this.schemaDOM.emptyElement(qName, xMLAttributes, this.fLocator.getLineNumber(), this.fLocator.getColumnNumber(), this.fLocator.getCharacterOffset());
        if (this.fAnnotationDepth != -1) {
            this.schemaDOM.endAnnotationElement(qName);
        } else if (qName.uri == SchemaSymbols.URI_SCHEMAFORSCHEMA && qName.localpart == SchemaSymbols.ELT_ANNOTATION) {
            this.schemaDOM.endAnnotation(qName, elementImplEmptyElement);
        }
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler, org.apache.xerces.xni.XMLDocumentHandler
    public void endCDATA(Augmentations augmentations) throws XNIException {
        if (this.fAnnotationDepth != -1) {
            this.schemaDOM.endAnnotationCDATA();
        }
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler, org.apache.xerces.xni.XMLDocumentHandler
    public void endDocument(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler, org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(QName qName, Augmentations augmentations) throws XNIException {
        int i = this.fAnnotationDepth;
        if (i > -1) {
            int i2 = this.fInnerAnnotationDepth;
            int i3 = this.fDepth;
            if (i2 != i3) {
                if (i == i3) {
                    this.fAnnotationDepth = -1;
                    this.schemaDOM.endAnnotation(qName, this.fCurrentAnnotationElement);
                } else {
                    this.schemaDOM.endAnnotationElement(qName);
                }
                this.fDepth--;
            }
            this.fInnerAnnotationDepth = -1;
            this.schemaDOM.endAnnotationElement(qName);
        } else if (qName.uri == SchemaSymbols.URI_SCHEMAFORSCHEMA && this.fGenerateSyntheticAnnotation) {
            boolean zPop = this.fHasNonSchemaAttributes.pop();
            boolean zPop2 = this.fSawAnnotation.pop();
            if (zPop && !zPop2) {
                String prefix = this.fNamespaceContext.getPrefix(SchemaSymbols.URI_SCHEMAFORSCHEMA);
                String str = prefix.length() == 0 ? SchemaSymbols.ELT_ANNOTATION : prefix + ':' + SchemaSymbols.ELT_ANNOTATION;
                this.schemaDOM.startAnnotation(str, this.fEmptyAttr, this.fNamespaceContext);
                String str2 = prefix.length() == 0 ? SchemaSymbols.ELT_DOCUMENTATION : prefix + ':' + SchemaSymbols.ELT_DOCUMENTATION;
                this.schemaDOM.startAnnotationElement(str2, this.fEmptyAttr);
                this.schemaDOM.charactersRaw("SYNTHETIC_ANNOTATION");
                this.schemaDOM.endSyntheticAnnotationElement(str2, false);
                this.schemaDOM.endSyntheticAnnotationElement(str, true);
            }
        }
        this.schemaDOM.endElement();
        this.fDepth--;
    }

    public Document getDocument() {
        return this.schemaDOM;
    }

    public boolean getFeature(String str) {
        return this.config.getFeature(str);
    }

    public Object getProperty(String str) {
        return this.config.getProperty(str);
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler, org.apache.xerces.xni.XMLDocumentHandler
    public void ignorableWhitespace(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fAnnotationDepth != -1) {
            this.schemaDOM.characters(xMLString);
        }
    }

    public void parse(XMLInputSource xMLInputSource) throws IOException {
        this.config.parse(xMLInputSource);
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler, org.apache.xerces.xni.XMLDocumentHandler
    public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fAnnotationDepth > -1) {
            this.schemaDOM.processingInstruction(str, xMLString);
        }
    }

    public void reset() {
        ((SchemaParsingConfig) this.config).reset();
    }

    public void resetNodePool() {
        ((SchemaParsingConfig) this.config).resetNodePool();
    }

    public void setEntityResolver(XMLEntityResolver xMLEntityResolver) {
        this.config.setEntityResolver(xMLEntityResolver);
    }

    public void setFeature(String str, boolean z) {
        this.config.setFeature(str, z);
    }

    public void setProperty(String str, Object obj) {
        this.config.setProperty(str, obj);
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler, org.apache.xerces.xni.XMLDocumentHandler
    public void startCDATA(Augmentations augmentations) throws XNIException {
        if (this.fAnnotationDepth != -1) {
            this.schemaDOM.startAnnotationCDATA();
        }
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler, org.apache.xerces.xni.XMLDocumentHandler
    public void startDocument(XMLLocator xMLLocator, String str, NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
        this.fErrorReporter = (XMLErrorReporter) this.config.getProperty("http://apache.org/xml/properties/internal/error-reporter");
        this.fGenerateSyntheticAnnotation = this.config.getFeature(GENERATE_SYNTHETIC_ANNOTATION);
        this.fHasNonSchemaAttributes.clear();
        this.fSawAnnotation.clear();
        SchemaDOM schemaDOM = new SchemaDOM();
        this.schemaDOM = schemaDOM;
        this.fCurrentAnnotationElement = null;
        this.fAnnotationDepth = -1;
        this.fInnerAnnotationDepth = -1;
        this.fDepth = -1;
        this.fLocator = xMLLocator;
        this.fNamespaceContext = namespaceContext;
        schemaDOM.setDocumentURI(xMLLocator.getExpandedSystemId());
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler, org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        int i = this.fDepth + 1;
        this.fDepth = i;
        int i2 = this.fAnnotationDepth;
        if (i2 == -1) {
            if (qName.uri == SchemaSymbols.URI_SCHEMAFORSCHEMA && qName.localpart == SchemaSymbols.ELT_ANNOTATION) {
                if (this.fGenerateSyntheticAnnotation) {
                    if (this.fSawAnnotation.size() > 0) {
                        this.fSawAnnotation.pop();
                    }
                    this.fSawAnnotation.push(true);
                }
                this.fAnnotationDepth = this.fDepth;
                this.schemaDOM.startAnnotation(qName, xMLAttributes, this.fNamespaceContext);
                this.fCurrentAnnotationElement = this.schemaDOM.startElement(qName, xMLAttributes, this.fLocator.getLineNumber(), this.fLocator.getColumnNumber(), this.fLocator.getCharacterOffset());
                return;
            }
            if (qName.uri == SchemaSymbols.URI_SCHEMAFORSCHEMA && this.fGenerateSyntheticAnnotation) {
                this.fSawAnnotation.push(false);
                this.fHasNonSchemaAttributes.push(hasNonSchemaAttributes(qName, xMLAttributes));
            }
        } else if (i != i2 + 1) {
            this.schemaDOM.startAnnotationElement(qName, xMLAttributes);
            return;
        } else {
            this.fInnerAnnotationDepth = i;
            this.schemaDOM.startAnnotationElement(qName, xMLAttributes);
        }
        this.schemaDOM.startElement(qName, xMLAttributes, this.fLocator.getLineNumber(), this.fLocator.getColumnNumber(), this.fLocator.getCharacterOffset());
    }
}
