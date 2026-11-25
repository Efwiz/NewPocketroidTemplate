package org.apache.xerces.impl;

import java.io.CharConversionException;
import java.io.EOFException;
import java.io.IOException;
import kotlin.text.Typography;
import org.apache.xerces.impl.io.MalformedByteSequenceException;
import org.apache.xerces.util.AugmentationsImpl;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentScanner;
import org.apache.xerces.xni.parser.XMLInputSource;

/* JADX INFO: loaded from: classes13.dex */
public class XMLDocumentFragmentScannerImpl extends XMLScanner implements XMLDocumentScanner, XMLComponent, XMLEntityHandler {
    protected static final boolean DEBUG_CONTENT_SCANNING = false;
    private static final boolean DEBUG_DISPATCHER = false;
    private static final boolean DEBUG_SCANNER_STATE = false;
    protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    protected static final int SCANNER_STATE_CDATA = 15;
    protected static final int SCANNER_STATE_COMMENT = 2;
    protected static final int SCANNER_STATE_CONTENT = 7;
    protected static final int SCANNER_STATE_DOCTYPE = 4;
    protected static final int SCANNER_STATE_END_OF_INPUT = 13;
    protected static final int SCANNER_STATE_PI = 3;
    protected static final int SCANNER_STATE_REFERENCE = 8;
    protected static final int SCANNER_STATE_ROOT_ELEMENT = 6;
    protected static final int SCANNER_STATE_START_OF_MARKUP = 1;
    protected static final int SCANNER_STATE_TERMINATED = 14;
    protected static final int SCANNER_STATE_TEXT_DECL = 16;
    protected QName fCurrentElement;
    protected Dispatcher fDispatcher;
    protected XMLDocumentHandler fDocumentHandler;
    protected ExternalSubsetResolver fExternalSubsetResolver;
    protected boolean fHasExternalDTD;
    protected boolean fIsEntityDeclaredVC;
    protected int fMarkupDepth;
    private boolean fSawSpace;
    protected int fScannerState;
    protected boolean fStandalone;
    protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    protected static final String NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
    private static final String[] RECOGNIZED_FEATURES = {NAMESPACES, "http://xml.org/sax/features/validation", NOTIFY_BUILTIN_REFS, "http://apache.org/xml/features/scanner/notify-char-refs"};
    private static final Boolean[] FEATURE_DEFAULTS = {null, null, Boolean.FALSE, Boolean.FALSE};
    private static final String[] RECOGNIZED_PROPERTIES = {"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager", "http://apache.org/xml/properties/internal/entity-resolver"};
    private static final Object[] PROPERTY_DEFAULTS = {null, null, null, null};
    protected int[] fEntityStack = new int[4];
    protected boolean fInScanContent = false;
    protected final ElementStack fElementStack = new ElementStack();
    protected boolean fNotifyBuiltInRefs = false;
    protected final Dispatcher fContentDispatcher = createContentDispatcher();
    protected final QName fElementQName = new QName();
    protected final QName fAttributeQName = new QName();
    protected final XMLAttributesImpl fAttributes = new XMLAttributesImpl();
    protected final XMLString fTempString = new XMLString();
    protected final XMLString fTempString2 = new XMLString();
    private final String[] fStrings = new String[3];
    private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
    private final XMLStringBuffer fStringBuffer2 = new XMLStringBuffer();
    private final QName fQName = new QName();
    private final char[] fSingleChar = new char[1];
    private Augmentations fTempAugmentations = null;

    protected interface Dispatcher {
        boolean dispatch(boolean z) throws IOException, XNIException;
    }

    protected static class ElementStack {
        protected QName[] fElements = new QName[10];
        protected int fSize;

        public ElementStack() {
            int i = 0;
            while (true) {
                QName[] qNameArr = this.fElements;
                if (i >= qNameArr.length) {
                    return;
                }
                qNameArr[i] = new QName();
                i++;
            }
        }

        public void clear() {
            this.fSize = 0;
        }

        public void popElement(QName qName) {
            QName[] qNameArr = this.fElements;
            int i = this.fSize - 1;
            this.fSize = i;
            qName.setValues(qNameArr[i]);
        }

        public QName pushElement(QName qName) {
            int i = this.fSize;
            QName[] qNameArr = this.fElements;
            if (i == qNameArr.length) {
                QName[] qNameArr2 = new QName[qNameArr.length * 2];
                System.arraycopy(qNameArr, 0, qNameArr2, 0, i);
                this.fElements = qNameArr2;
                int i2 = this.fSize;
                while (true) {
                    QName[] qNameArr3 = this.fElements;
                    if (i2 >= qNameArr3.length) {
                        break;
                    }
                    qNameArr3[i2] = new QName();
                    i2++;
                }
            }
            this.fElements[this.fSize].setValues(qName);
            QName[] qNameArr4 = this.fElements;
            int i3 = this.fSize;
            this.fSize = i3 + 1;
            return qNameArr4[i3];
        }
    }

    protected class FragmentContentDispatcher implements Dispatcher {
        protected FragmentContentDispatcher() {
        }

        @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.Dispatcher
        public boolean dispatch(boolean z) throws IOException, XNIException {
            Exception exc;
            Object[] arguments;
            short s;
            String domain;
            String key;
            XMLErrorReporter xMLErrorReporter;
            XMLDocumentFragmentScannerImpl xMLDocumentFragmentScannerImpl;
            XMLDocumentFragmentScannerImpl xMLDocumentFragmentScannerImpl2;
            while (true) {
                boolean z2 = false;
                try {
                    int i = XMLDocumentFragmentScannerImpl.this.fScannerState;
                    int i2 = 2;
                    if (i != 1) {
                        if (i == 2) {
                            XMLDocumentFragmentScannerImpl.this.scanComment();
                            xMLDocumentFragmentScannerImpl = XMLDocumentFragmentScannerImpl.this;
                        } else if (i == 3) {
                            XMLDocumentFragmentScannerImpl.this.scanPI();
                            xMLDocumentFragmentScannerImpl = XMLDocumentFragmentScannerImpl.this;
                        } else if (i == 4) {
                            XMLDocumentFragmentScannerImpl.this.reportFatalError("DoctypeIllegalInContent", null);
                            xMLDocumentFragmentScannerImpl = XMLDocumentFragmentScannerImpl.this;
                        } else if (i != 6) {
                            i2 = 8;
                            if (i != 7) {
                                if (i == 8) {
                                    XMLDocumentFragmentScannerImpl.this.fMarkupDepth++;
                                    XMLDocumentFragmentScannerImpl.this.setScannerState(7);
                                    if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(35)) {
                                        XMLDocumentFragmentScannerImpl.this.scanCharReference();
                                    } else {
                                        XMLDocumentFragmentScannerImpl.this.scanEntityReference();
                                    }
                                } else if (i == 15) {
                                    XMLDocumentFragmentScannerImpl.this.scanCDATASection(z);
                                    xMLDocumentFragmentScannerImpl = XMLDocumentFragmentScannerImpl.this;
                                } else if (i == 16) {
                                    if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipString("<?xml")) {
                                        XMLDocumentFragmentScannerImpl.this.fMarkupDepth++;
                                        XMLDocumentFragmentScannerImpl xMLDocumentFragmentScannerImpl3 = XMLDocumentFragmentScannerImpl.this;
                                        if (xMLDocumentFragmentScannerImpl3.isValidNameChar(xMLDocumentFragmentScannerImpl3.fEntityScanner.peekChar())) {
                                            XMLDocumentFragmentScannerImpl.this.fStringBuffer.clear();
                                            XMLDocumentFragmentScannerImpl.this.fStringBuffer.append("xml");
                                            if (!XMLDocumentFragmentScannerImpl.this.fNamespaces) {
                                                while (true) {
                                                    XMLDocumentFragmentScannerImpl xMLDocumentFragmentScannerImpl4 = XMLDocumentFragmentScannerImpl.this;
                                                    if (!xMLDocumentFragmentScannerImpl4.isValidNameChar(xMLDocumentFragmentScannerImpl4.fEntityScanner.peekChar())) {
                                                        break;
                                                    }
                                                    XMLDocumentFragmentScannerImpl.this.fStringBuffer.append((char) XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar());
                                                }
                                            } else {
                                                while (true) {
                                                    XMLDocumentFragmentScannerImpl xMLDocumentFragmentScannerImpl5 = XMLDocumentFragmentScannerImpl.this;
                                                    if (!xMLDocumentFragmentScannerImpl5.isValidNCName(xMLDocumentFragmentScannerImpl5.fEntityScanner.peekChar())) {
                                                        break;
                                                    }
                                                    XMLDocumentFragmentScannerImpl.this.fStringBuffer.append((char) XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar());
                                                }
                                            }
                                            String strAddSymbol = XMLDocumentFragmentScannerImpl.this.fSymbolTable.addSymbol(XMLDocumentFragmentScannerImpl.this.fStringBuffer.ch, XMLDocumentFragmentScannerImpl.this.fStringBuffer.offset, XMLDocumentFragmentScannerImpl.this.fStringBuffer.length);
                                            XMLDocumentFragmentScannerImpl xMLDocumentFragmentScannerImpl6 = XMLDocumentFragmentScannerImpl.this;
                                            xMLDocumentFragmentScannerImpl6.scanPIData(strAddSymbol, xMLDocumentFragmentScannerImpl6.fTempString);
                                        } else {
                                            XMLDocumentFragmentScannerImpl.this.scanXMLDeclOrTextDecl(true);
                                        }
                                    }
                                    XMLDocumentFragmentScannerImpl.this.fEntityManager.fCurrentEntity.mayReadChunks = true;
                                    xMLDocumentFragmentScannerImpl = XMLDocumentFragmentScannerImpl.this;
                                }
                            } else if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(60)) {
                                XMLDocumentFragmentScannerImpl.this.setScannerState(1);
                                z2 = true;
                            } else if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(38)) {
                                xMLDocumentFragmentScannerImpl2 = XMLDocumentFragmentScannerImpl.this;
                                xMLDocumentFragmentScannerImpl2.setScannerState(i2);
                                z2 = true;
                            } else {
                                do {
                                    int iScanContent = XMLDocumentFragmentScannerImpl.this.scanContent();
                                    if (iScanContent == 60) {
                                        XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar();
                                        XMLDocumentFragmentScannerImpl.this.setScannerState(1);
                                        break;
                                    }
                                    if (iScanContent == 38) {
                                        XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar();
                                        XMLDocumentFragmentScannerImpl.this.setScannerState(8);
                                        break;
                                    }
                                    if (iScanContent != -1 && XMLDocumentFragmentScannerImpl.this.isInvalidLiteral(iScanContent)) {
                                        if (XMLChar.isHighSurrogate(iScanContent)) {
                                            XMLDocumentFragmentScannerImpl.this.fStringBuffer.clear();
                                            XMLDocumentFragmentScannerImpl xMLDocumentFragmentScannerImpl7 = XMLDocumentFragmentScannerImpl.this;
                                            if (xMLDocumentFragmentScannerImpl7.scanSurrogates(xMLDocumentFragmentScannerImpl7.fStringBuffer) && XMLDocumentFragmentScannerImpl.this.fDocumentHandler != null) {
                                                XMLDocumentFragmentScannerImpl.this.fDocumentHandler.characters(XMLDocumentFragmentScannerImpl.this.fStringBuffer, null);
                                            }
                                        } else {
                                            XMLDocumentFragmentScannerImpl.this.reportFatalError("InvalidCharInContent", new Object[]{Integer.toString(iScanContent, 16)});
                                            XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar();
                                        }
                                    }
                                } while (z);
                            }
                        } else {
                            if (scanRootElementHook()) {
                                return true;
                            }
                            xMLDocumentFragmentScannerImpl = XMLDocumentFragmentScannerImpl.this;
                        }
                        xMLDocumentFragmentScannerImpl.setScannerState(7);
                    } else {
                        XMLDocumentFragmentScannerImpl.this.fMarkupDepth++;
                        if (!XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(47)) {
                            XMLDocumentFragmentScannerImpl xMLDocumentFragmentScannerImpl8 = XMLDocumentFragmentScannerImpl.this;
                            if (xMLDocumentFragmentScannerImpl8.isValidNameStartChar(xMLDocumentFragmentScannerImpl8.fEntityScanner.peekChar())) {
                                XMLDocumentFragmentScannerImpl.this.scanStartElement();
                                xMLDocumentFragmentScannerImpl = XMLDocumentFragmentScannerImpl.this;
                            } else {
                                if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(33)) {
                                    if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(45)) {
                                        if (!XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(45)) {
                                            XMLDocumentFragmentScannerImpl.this.reportFatalError("InvalidCommentStart", null);
                                        }
                                        xMLDocumentFragmentScannerImpl2 = XMLDocumentFragmentScannerImpl.this;
                                        xMLDocumentFragmentScannerImpl2.setScannerState(i2);
                                    } else if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipString("[CDATA[")) {
                                        XMLDocumentFragmentScannerImpl.this.setScannerState(15);
                                    } else if (!scanForDoctypeHook()) {
                                        XMLDocumentFragmentScannerImpl.this.reportFatalError("MarkupNotRecognizedInContent", null);
                                    }
                                } else if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(63)) {
                                    XMLDocumentFragmentScannerImpl.this.setScannerState(3);
                                } else {
                                    XMLDocumentFragmentScannerImpl xMLDocumentFragmentScannerImpl9 = XMLDocumentFragmentScannerImpl.this;
                                    if (xMLDocumentFragmentScannerImpl9.isValidNameStartHighSurrogate(xMLDocumentFragmentScannerImpl9.fEntityScanner.peekChar())) {
                                        XMLDocumentFragmentScannerImpl.this.scanStartElement();
                                        xMLDocumentFragmentScannerImpl = XMLDocumentFragmentScannerImpl.this;
                                    } else {
                                        XMLDocumentFragmentScannerImpl.this.reportFatalError("MarkupNotRecognizedInContent", null);
                                        xMLDocumentFragmentScannerImpl = XMLDocumentFragmentScannerImpl.this;
                                    }
                                }
                                z2 = true;
                            }
                        } else {
                            if (XMLDocumentFragmentScannerImpl.this.scanEndElement() == 0 && elementDepthIsZeroHook()) {
                                return true;
                            }
                            xMLDocumentFragmentScannerImpl = XMLDocumentFragmentScannerImpl.this;
                        }
                        xMLDocumentFragmentScannerImpl.setScannerState(7);
                    }
                    if (!z && !z2) {
                        return true;
                    }
                } catch (MalformedByteSequenceException e) {
                    MalformedByteSequenceException malformedByteSequenceException = e;
                    XMLErrorReporter xMLErrorReporter2 = XMLDocumentFragmentScannerImpl.this.fErrorReporter;
                    domain = malformedByteSequenceException.getDomain();
                    key = malformedByteSequenceException.getKey();
                    arguments = malformedByteSequenceException.getArguments();
                    s = 2;
                    xMLErrorReporter = xMLErrorReporter2;
                    exc = malformedByteSequenceException;
                    xMLErrorReporter.reportError(domain, key, arguments, s, exc);
                    return false;
                } catch (CharConversionException e2) {
                    exc = e2;
                    arguments = null;
                    s = 2;
                    domain = "http://www.w3.org/TR/1998/REC-xml-19980210";
                    key = "CharConversionFailure";
                    xMLErrorReporter = XMLDocumentFragmentScannerImpl.this.fErrorReporter;
                    xMLErrorReporter.reportError(domain, key, arguments, s, exc);
                    return false;
                } catch (EOFException e3) {
                    endOfFileHook(e3);
                    return false;
                }
            }
        }

        protected boolean elementDepthIsZeroHook() throws IOException, XNIException {
            return false;
        }

        protected void endOfFileHook(EOFException eOFException) throws IOException, XNIException {
            if (XMLDocumentFragmentScannerImpl.this.fMarkupDepth != 0) {
                XMLDocumentFragmentScannerImpl.this.reportFatalError("PrematureEOF", null);
            }
        }

        protected boolean scanForDoctypeHook() throws IOException, XNIException {
            return false;
        }

        protected boolean scanRootElementHook() throws IOException, XNIException {
            return false;
        }
    }

    private void handleCharacter(char c, String str) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            if (this.fNotifyBuiltInRefs) {
                xMLDocumentHandler.startGeneralEntity(str, null, null, null);
            }
            char[] cArr = this.fSingleChar;
            cArr[0] = c;
            this.fTempString.setValues(cArr, 0, 1);
            this.fDocumentHandler.characters(this.fTempString, null);
            if (this.fNotifyBuiltInRefs) {
                this.fDocumentHandler.endGeneralEntity(str, null);
            }
        }
    }

    protected Dispatcher createContentDispatcher() {
        return new FragmentContentDispatcher();
    }

    @Override // org.apache.xerces.impl.XMLScanner, org.apache.xerces.impl.XMLEntityHandler
    public void endEntity(String str, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler;
        if (this.fInScanContent && this.fStringBuffer.length != 0 && (xMLDocumentHandler = this.fDocumentHandler) != null) {
            xMLDocumentHandler.characters(this.fStringBuffer, null);
            this.fStringBuffer.length = 0;
        }
        super.endEntity(str, augmentations);
        if (this.fMarkupDepth != this.fEntityStack[this.fEntityDepth]) {
            reportFatalError("MarkupEntityMismatch", null);
        }
        if (this.fDocumentHandler == null || this.fScanningAttribute || str.equals("[xml]")) {
            return;
        }
        this.fDocumentHandler.endGeneralEntity(str, augmentations);
    }

    public String getDispatcherName(Dispatcher dispatcher) {
        return "null";
    }

    @Override // org.apache.xerces.xni.parser.XMLDocumentSource
    public XMLDocumentHandler getDocumentHandler() {
        return this.fDocumentHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public Boolean getFeatureDefault(String str) {
        int i = 0;
        while (true) {
            String[] strArr = RECOGNIZED_FEATURES;
            if (i >= strArr.length) {
                return null;
            }
            if (strArr[i].equals(str)) {
                return FEATURE_DEFAULTS[i];
            }
            i++;
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public Object getPropertyDefault(String str) {
        int i = 0;
        while (true) {
            String[] strArr = RECOGNIZED_PROPERTIES;
            if (i >= strArr.length) {
                return null;
            }
            if (strArr[i].equals(str)) {
                return PROPERTY_DEFAULTS[i];
            }
            i++;
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedFeatures() {
        return (String[]) RECOGNIZED_FEATURES.clone();
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedProperties() {
        return (String[]) RECOGNIZED_PROPERTIES.clone();
    }

    protected String getScannerStateName(int i) {
        if (i == 1) {
            return "SCANNER_STATE_START_OF_MARKUP";
        }
        if (i == 2) {
            return "SCANNER_STATE_COMMENT";
        }
        if (i == 3) {
            return "SCANNER_STATE_PI";
        }
        if (i == 4) {
            return "SCANNER_STATE_DOCTYPE";
        }
        if (i == 6) {
            return "SCANNER_STATE_ROOT_ELEMENT";
        }
        if (i == 7) {
            return "SCANNER_STATE_CONTENT";
        }
        if (i == 8) {
            return "SCANNER_STATE_REFERENCE";
        }
        switch (i) {
            case 13:
                return "SCANNER_STATE_END_OF_INPUT";
            case 14:
                return "SCANNER_STATE_TERMINATED";
            case 15:
                return "SCANNER_STATE_CDATA";
            case 16:
                return "SCANNER_STATE_TEXT_DECL";
            default:
                return "??? (" + i + ')';
        }
    }

    protected int handleEndElement(QName qName, boolean z) throws XNIException {
        int i = this.fMarkupDepth - 1;
        this.fMarkupDepth = i;
        if (i < this.fEntityStack[this.fEntityDepth - 1]) {
            reportFatalError("ElementEntityMismatch", new Object[]{this.fCurrentElement.rawname});
        }
        QName qName2 = this.fQName;
        this.fElementStack.popElement(qName2);
        if (qName.rawname != qName2.rawname) {
            reportFatalError("ETagRequired", new Object[]{qName2.rawname});
        }
        if (this.fNamespaces) {
            qName.uri = qName2.uri;
        }
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null && !z) {
            xMLDocumentHandler.endElement(qName, null);
        }
        return this.fMarkupDepth;
    }

    @Override // org.apache.xerces.impl.XMLScanner, org.apache.xerces.xni.parser.XMLComponent
    public void reset(XMLComponentManager xMLComponentManager) throws XMLConfigurationException {
        super.reset(xMLComponentManager);
        this.fAttributes.setNamespaces(this.fNamespaces);
        this.fMarkupDepth = 0;
        this.fCurrentElement = null;
        this.fElementStack.clear();
        this.fHasExternalDTD = false;
        this.fStandalone = false;
        this.fIsEntityDeclaredVC = false;
        this.fInScanContent = false;
        setScannerState(7);
        setDispatcher(this.fContentDispatcher);
        if (this.fParserSettings) {
            try {
                this.fNotifyBuiltInRefs = xMLComponentManager.getFeature(NOTIFY_BUILTIN_REFS);
            } catch (XMLConfigurationException unused) {
                this.fNotifyBuiltInRefs = false;
            }
            try {
                Object property = xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
                this.fExternalSubsetResolver = property instanceof ExternalSubsetResolver ? (ExternalSubsetResolver) property : null;
            } catch (XMLConfigurationException unused2) {
                this.fExternalSubsetResolver = null;
            }
        }
    }

    protected void scanAttribute(XMLAttributes xMLAttributes) throws IOException, XNIException {
        if (this.fNamespaces) {
            this.fEntityScanner.scanQName(this.fAttributeQName);
        } else {
            String strScanName = this.fEntityScanner.scanName();
            this.fAttributeQName.setValues(null, strScanName, strScanName, null);
        }
        this.fEntityScanner.skipSpaces();
        if (!this.fEntityScanner.skipChar(61)) {
            reportFatalError("EqRequiredInAttribute", new Object[]{this.fCurrentElement.rawname, this.fAttributeQName.rawname});
        }
        this.fEntityScanner.skipSpaces();
        int length = xMLAttributes.getLength();
        int iAddAttribute = xMLAttributes.addAttribute(this.fAttributeQName, XMLSymbols.fCDATASymbol, null);
        if (length == xMLAttributes.getLength()) {
            reportFatalError("AttributeNotUnique", new Object[]{this.fCurrentElement.rawname, this.fAttributeQName.rawname});
        }
        boolean zScanAttributeValue = scanAttributeValue(this.fTempString, this.fTempString2, this.fAttributeQName.rawname, this.fIsEntityDeclaredVC, this.fCurrentElement.rawname);
        xMLAttributes.setValue(iAddAttribute, this.fTempString.toString());
        if (!zScanAttributeValue) {
            xMLAttributes.setNonNormalizedValue(iAddAttribute, this.fTempString2.toString());
        }
        xMLAttributes.setSpecified(iAddAttribute, true);
    }

    protected boolean scanCDATASection(boolean z) throws IOException, XNIException {
        XMLDocumentHandler xMLDocumentHandler;
        XMLDocumentHandler xMLDocumentHandler2 = this.fDocumentHandler;
        if (xMLDocumentHandler2 != null) {
            xMLDocumentHandler2.startCDATA(null);
        }
        while (true) {
            this.fStringBuffer.clear();
            if (this.fEntityScanner.scanData("]]", this.fStringBuffer)) {
                XMLDocumentHandler xMLDocumentHandler3 = this.fDocumentHandler;
                if (xMLDocumentHandler3 != null) {
                    xMLDocumentHandler3.characters(this.fStringBuffer, null);
                }
                int iPeekChar = this.fEntityScanner.peekChar();
                if (iPeekChar != -1 && isInvalidLiteral(iPeekChar)) {
                    if (XMLChar.isHighSurrogate(iPeekChar)) {
                        this.fStringBuffer.clear();
                        scanSurrogates(this.fStringBuffer);
                        xMLDocumentHandler = this.fDocumentHandler;
                        if (xMLDocumentHandler != null) {
                            xMLDocumentHandler.characters(this.fStringBuffer, null);
                        }
                    } else {
                        reportFatalError("InvalidCharInCDSect", new Object[]{Integer.toString(iPeekChar, 16)});
                        this.fEntityScanner.scanChar();
                    }
                }
            } else {
                if (this.fDocumentHandler != null && this.fStringBuffer.length > 0) {
                    this.fDocumentHandler.characters(this.fStringBuffer, null);
                }
                int i = 0;
                int i2 = 0;
                while (this.fEntityScanner.skipChar(93)) {
                    i2++;
                }
                if (this.fDocumentHandler != null && i2 > 0) {
                    this.fStringBuffer.clear();
                    if (i2 > 2048) {
                        int i3 = i2 / 2048;
                        int i4 = i2 % 2048;
                        for (int i5 = 0; i5 < 2048; i5++) {
                            this.fStringBuffer.append(']');
                        }
                        while (i < i3) {
                            this.fDocumentHandler.characters(this.fStringBuffer, null);
                            i++;
                        }
                        if (i4 != 0) {
                            this.fStringBuffer.length = i4;
                        }
                    } else {
                        while (i < i2) {
                            this.fStringBuffer.append(']');
                            i++;
                        }
                    }
                    this.fDocumentHandler.characters(this.fStringBuffer, null);
                }
                if (this.fEntityScanner.skipChar(62)) {
                    break;
                }
                if (this.fDocumentHandler != null) {
                    this.fStringBuffer.clear();
                    this.fStringBuffer.append("]]");
                    xMLDocumentHandler = this.fDocumentHandler;
                    xMLDocumentHandler.characters(this.fStringBuffer, null);
                }
            }
        }
        this.fMarkupDepth--;
        XMLDocumentHandler xMLDocumentHandler4 = this.fDocumentHandler;
        if (xMLDocumentHandler4 != null) {
            xMLDocumentHandler4.endCDATA(null);
        }
        return true;
    }

    protected void scanCharReference() throws IOException, XNIException {
        Augmentations augmentations;
        this.fStringBuffer2.clear();
        int iScanCharReferenceValue = scanCharReferenceValue(this.fStringBuffer2, null);
        this.fMarkupDepth--;
        if (iScanCharReferenceValue == -1 || this.fDocumentHandler == null) {
            return;
        }
        if (this.fNotifyCharRefs) {
            this.fDocumentHandler.startGeneralEntity(this.fCharRefLiteral, null, null, null);
        }
        if (!this.fValidation || iScanCharReferenceValue > 32) {
            augmentations = null;
        } else {
            Augmentations augmentations2 = this.fTempAugmentations;
            if (augmentations2 != null) {
                augmentations2.removeAllItems();
            } else {
                this.fTempAugmentations = new AugmentationsImpl();
            }
            augmentations = this.fTempAugmentations;
            augmentations.putItem(Constants.CHAR_REF_PROBABLE_WS, Boolean.TRUE);
        }
        this.fDocumentHandler.characters(this.fStringBuffer2, augmentations);
        if (this.fNotifyCharRefs) {
            this.fDocumentHandler.endGeneralEntity(this.fCharRefLiteral, null);
        }
    }

    protected void scanComment() throws IOException, XNIException {
        scanComment(this.fStringBuffer);
        this.fMarkupDepth--;
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.comment(this.fStringBuffer, null);
        }
    }

    protected int scanContent() throws IOException, XNIException {
        XMLString xMLString = this.fTempString;
        int iScanContent = this.fEntityScanner.scanContent(xMLString);
        if (iScanContent == 13) {
            this.fEntityScanner.scanChar();
            this.fStringBuffer.clear();
            this.fStringBuffer.append(this.fTempString);
            this.fStringBuffer.append((char) iScanContent);
            xMLString = this.fStringBuffer;
            iScanContent = -1;
        }
        if (this.fDocumentHandler != null && xMLString.length > 0) {
            this.fDocumentHandler.characters(xMLString, null);
        }
        if (iScanContent != 93 || this.fTempString.length != 0) {
            return iScanContent;
        }
        this.fStringBuffer.clear();
        this.fStringBuffer.append((char) this.fEntityScanner.scanChar());
        this.fInScanContent = true;
        if (this.fEntityScanner.skipChar(93)) {
            do {
                this.fStringBuffer.append(']');
            } while (this.fEntityScanner.skipChar(93));
            if (this.fEntityScanner.skipChar(62)) {
                reportFatalError("CDEndInContent", null);
            }
        }
        if (this.fDocumentHandler != null && this.fStringBuffer.length != 0) {
            this.fDocumentHandler.characters(this.fStringBuffer, null);
        }
        this.fInScanContent = false;
        return -1;
    }

    @Override // org.apache.xerces.xni.parser.XMLDocumentScanner
    public boolean scanDocument(boolean z) throws IOException, XNIException {
        this.fEntityScanner = this.fEntityManager.getEntityScanner();
        this.fEntityManager.setEntityHandler(this);
        while (this.fDispatcher.dispatch(z)) {
            if (!z) {
                return true;
            }
        }
        return false;
    }

    protected int scanEndElement() throws IOException, XNIException {
        this.fElementStack.popElement(this.fElementQName);
        if (!this.fEntityScanner.skipString(this.fElementQName.rawname)) {
            reportFatalError("ETagRequired", new Object[]{this.fElementQName.rawname});
        }
        this.fEntityScanner.skipSpaces();
        if (!this.fEntityScanner.skipChar(62)) {
            reportFatalError("ETagUnterminated", new Object[]{this.fElementQName.rawname});
        }
        int i = this.fMarkupDepth - 2;
        this.fMarkupDepth = i;
        if (i < this.fEntityStack[this.fEntityDepth - 1]) {
            reportFatalError("ElementEntityMismatch", new Object[]{this.fCurrentElement.rawname});
        }
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.endElement(this.fElementQName, null);
        }
        return this.fMarkupDepth;
    }

    protected void scanEntityReference() throws IOException, XNIException {
        char c;
        String str;
        String strScanName = this.fEntityScanner.scanName();
        if (strScanName == null) {
            reportFatalError("NameRequiredInReference", null);
            return;
        }
        if (!this.fEntityScanner.skipChar(59)) {
            reportFatalError("SemicolonRequiredInReference", new Object[]{strScanName});
        }
        this.fMarkupDepth--;
        if (strScanName == fAmpSymbol) {
            c = Typography.amp;
            str = fAmpSymbol;
        } else if (strScanName == fLtSymbol) {
            c = '<';
            str = fLtSymbol;
        } else if (strScanName == fGtSymbol) {
            c = '>';
            str = fGtSymbol;
        } else if (strScanName == fQuotSymbol) {
            c = '\"';
            str = fQuotSymbol;
        } else {
            if (strScanName != fAposSymbol) {
                if (this.fEntityManager.isUnparsedEntity(strScanName)) {
                    reportFatalError("ReferenceToUnparsedEntity", new Object[]{strScanName});
                    return;
                }
                if (!this.fEntityManager.isDeclaredEntity(strScanName)) {
                    if (!this.fIsEntityDeclaredVC) {
                        reportFatalError("EntityNotDeclared", new Object[]{strScanName});
                    } else if (this.fValidation) {
                        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityNotDeclared", new Object[]{strScanName}, (short) 1);
                    }
                }
                this.fEntityManager.startEntity(strScanName, false);
                return;
            }
            c = '\'';
            str = fAposSymbol;
        }
        handleCharacter(c, str);
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected void scanPIData(String str, XMLString xMLString) throws IOException, XNIException {
        super.scanPIData(str, xMLString);
        this.fMarkupDepth--;
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.processingInstruction(str, xMLString, null);
        }
    }

    protected boolean scanStartElement() throws IOException, XNIException {
        boolean z;
        if (this.fNamespaces) {
            this.fEntityScanner.scanQName(this.fElementQName);
        } else {
            String strScanName = this.fEntityScanner.scanName();
            this.fElementQName.setValues(null, strScanName, strScanName, null);
        }
        String str = this.fElementQName.rawname;
        this.fCurrentElement = this.fElementStack.pushElement(this.fElementQName);
        this.fAttributes.removeAllAttributes();
        while (true) {
            boolean zSkipSpaces = this.fEntityScanner.skipSpaces();
            int iPeekChar = this.fEntityScanner.peekChar();
            if (iPeekChar == 62) {
                this.fEntityScanner.scanChar();
                z = false;
                break;
            }
            if (iPeekChar == 47) {
                this.fEntityScanner.scanChar();
                if (!this.fEntityScanner.skipChar(62)) {
                    reportFatalError("ElementUnterminated", new Object[]{str});
                }
                z = true;
                break;
            }
            if ((!isValidNameStartChar(iPeekChar) || !zSkipSpaces) && (!isValidNameStartHighSurrogate(iPeekChar) || !zSkipSpaces)) {
                reportFatalError("ElementUnterminated", new Object[]{str});
            }
            scanAttribute(this.fAttributes);
        }
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            if (z) {
                int i = this.fMarkupDepth - 1;
                this.fMarkupDepth = i;
                if (i < this.fEntityStack[this.fEntityDepth - 1]) {
                    reportFatalError("ElementEntityMismatch", new Object[]{this.fCurrentElement.rawname});
                }
                this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, null);
                this.fElementStack.popElement(this.fElementQName);
            } else {
                xMLDocumentHandler.startElement(this.fElementQName, this.fAttributes, null);
            }
        }
        return z;
    }

    protected boolean scanStartElementAfterName() throws IOException, XNIException {
        boolean z;
        String str = this.fElementQName.rawname;
        this.fCurrentElement = this.fElementStack.pushElement(this.fElementQName);
        this.fAttributes.removeAllAttributes();
        while (true) {
            int iPeekChar = this.fEntityScanner.peekChar();
            if (iPeekChar == 62) {
                this.fEntityScanner.scanChar();
                z = false;
                break;
            }
            if (iPeekChar == 47) {
                this.fEntityScanner.scanChar();
                if (!this.fEntityScanner.skipChar(62)) {
                    reportFatalError("ElementUnterminated", new Object[]{str});
                }
                z = true;
                break;
            }
            if ((!isValidNameStartChar(iPeekChar) || !this.fSawSpace) && (!isValidNameStartHighSurrogate(iPeekChar) || !this.fSawSpace)) {
                reportFatalError("ElementUnterminated", new Object[]{str});
            }
            scanAttribute(this.fAttributes);
            this.fSawSpace = this.fEntityScanner.skipSpaces();
        }
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            if (z) {
                int i = this.fMarkupDepth - 1;
                this.fMarkupDepth = i;
                if (i < this.fEntityStack[this.fEntityDepth - 1]) {
                    reportFatalError("ElementEntityMismatch", new Object[]{this.fCurrentElement.rawname});
                }
                this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, null);
                this.fElementStack.popElement(this.fElementQName);
            } else {
                xMLDocumentHandler.startElement(this.fElementQName, this.fAttributes, null);
            }
        }
        return z;
    }

    protected void scanStartElementName() throws IOException, XNIException {
        if (this.fNamespaces) {
            this.fEntityScanner.scanQName(this.fElementQName);
        } else {
            String strScanName = this.fEntityScanner.scanName();
            this.fElementQName.setValues(null, strScanName, strScanName, null);
        }
        this.fSawSpace = this.fEntityScanner.skipSpaces();
    }

    protected void scanXMLDeclOrTextDecl(boolean z) throws IOException, XNIException {
        super.scanXMLDeclOrTextDecl(z, this.fStrings);
        this.fMarkupDepth--;
        String[] strArr = this.fStrings;
        String str = strArr[0];
        String str2 = strArr[1];
        String str3 = strArr[2];
        this.fStandalone = str3 != null && str3.equals("yes");
        this.fEntityManager.setStandalone(this.fStandalone);
        this.fEntityScanner.setXMLVersion(str);
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            if (z) {
                xMLDocumentHandler.textDecl(str, str2, null);
            } else {
                xMLDocumentHandler.xmlDecl(str, str2, str3, null);
            }
        }
        if (str2 == null || this.fEntityScanner.fCurrentEntity.isEncodingExternallySpecified()) {
            return;
        }
        this.fEntityScanner.setEncoding(str2);
    }

    protected final void setDispatcher(Dispatcher dispatcher) {
        this.fDispatcher = dispatcher;
    }

    @Override // org.apache.xerces.xni.parser.XMLDocumentSource
    public void setDocumentHandler(XMLDocumentHandler xMLDocumentHandler) {
        this.fDocumentHandler = xMLDocumentHandler;
    }

    @Override // org.apache.xerces.impl.XMLScanner, org.apache.xerces.xni.parser.XMLComponent
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
        super.setFeature(str, z);
        if (str.startsWith(Constants.XERCES_FEATURE_PREFIX) && str.length() - Constants.XERCES_FEATURE_PREFIX.length() == Constants.NOTIFY_BUILTIN_REFS_FEATURE.length() && str.endsWith(Constants.NOTIFY_BUILTIN_REFS_FEATURE)) {
            this.fNotifyBuiltInRefs = z;
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLDocumentScanner
    public void setInputSource(XMLInputSource xMLInputSource) throws IOException {
        this.fEntityManager.setEntityHandler(this);
        this.fEntityManager.startEntity("$fragment$", xMLInputSource, false, true);
    }

    @Override // org.apache.xerces.impl.XMLScanner, org.apache.xerces.xni.parser.XMLComponent
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        super.setProperty(str, obj);
        if (str.startsWith(Constants.XERCES_PROPERTY_PREFIX)) {
            int length = str.length() - Constants.XERCES_PROPERTY_PREFIX.length();
            if (length == Constants.ENTITY_MANAGER_PROPERTY.length() && str.endsWith(Constants.ENTITY_MANAGER_PROPERTY)) {
                this.fEntityManager = (XMLEntityManager) obj;
            } else if (length == Constants.ENTITY_RESOLVER_PROPERTY.length() && str.endsWith(Constants.ENTITY_RESOLVER_PROPERTY)) {
                this.fExternalSubsetResolver = obj instanceof ExternalSubsetResolver ? (ExternalSubsetResolver) obj : null;
            }
        }
    }

    protected final void setScannerState(int i) {
        this.fScannerState = i;
    }

    @Override // org.apache.xerces.impl.XMLScanner, org.apache.xerces.impl.XMLEntityHandler
    public void startEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        int i = this.fEntityDepth;
        int[] iArr = this.fEntityStack;
        if (i == iArr.length) {
            int[] iArr2 = new int[iArr.length * 2];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.fEntityStack = iArr2;
        }
        this.fEntityStack[this.fEntityDepth] = this.fMarkupDepth;
        super.startEntity(str, xMLResourceIdentifier, str2, augmentations);
        if (this.fStandalone && this.fEntityManager.isEntityDeclInExternalSubset(str)) {
            reportFatalError("MSG_REFERENCE_TO_EXTERNALLY_DECLARED_ENTITY_WHEN_STANDALONE", new Object[]{str});
        }
        if (this.fDocumentHandler == null || this.fScanningAttribute || str.equals("[xml]")) {
            return;
        }
        this.fDocumentHandler.startGeneralEntity(str, xMLResourceIdentifier, str2, augmentations);
    }
}
