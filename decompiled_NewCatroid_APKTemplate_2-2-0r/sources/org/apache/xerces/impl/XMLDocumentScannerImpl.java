package org.apache.xerces.impl;

import java.io.CharConversionException;
import java.io.EOFException;
import java.io.IOException;
import org.apache.xerces.impl.dtd.XMLDTDDescription;
import org.apache.xerces.impl.io.MalformedByteSequenceException;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDScanner;
import org.apache.xerces.xni.parser.XMLInputSource;

/* JADX INFO: loaded from: classes13.dex */
public class XMLDocumentScannerImpl extends XMLDocumentFragmentScannerImpl {
    protected static final int SCANNER_STATE_DTD_EXTERNAL = 18;
    protected static final int SCANNER_STATE_DTD_EXTERNAL_DECLS = 19;
    protected static final int SCANNER_STATE_DTD_INTERNAL_DECLS = 17;
    protected static final int SCANNER_STATE_PROLOG = 5;
    protected static final int SCANNER_STATE_TRAILING_MISC = 12;
    protected static final int SCANNER_STATE_XML_DECL = 0;
    protected XMLDTDScanner fDTDScanner;
    protected String fDoctypeName;
    protected String fDoctypePublicId;
    protected String fDoctypeSystemId;
    protected boolean fScanningDTD;
    protected boolean fSeenDoctypeDecl;
    protected ValidationManager fValidationManager;
    protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
    protected static final String DISALLOW_DOCTYPE_DECL_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String[] RECOGNIZED_FEATURES = {LOAD_EXTERNAL_DTD, DISALLOW_DOCTYPE_DECL_FEATURE};
    private static final Boolean[] FEATURE_DEFAULTS = {Boolean.TRUE, Boolean.FALSE};
    protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
    protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    protected static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
    private static final String[] RECOGNIZED_PROPERTIES = {DTD_SCANNER, VALIDATION_MANAGER, NAMESPACE_CONTEXT};
    private static final Object[] PROPERTY_DEFAULTS = {null, null, null};
    protected NamespaceContext fNamespaceContext = new NamespaceSupport();
    protected boolean fLoadExternalDTD = true;
    protected boolean fDisallowDoctype = false;
    protected final XMLDocumentFragmentScannerImpl.Dispatcher fXMLDeclDispatcher = new XMLDeclDispatcher();
    protected final XMLDocumentFragmentScannerImpl.Dispatcher fPrologDispatcher = new PrologDispatcher();
    protected final XMLDocumentFragmentScannerImpl.Dispatcher fDTDDispatcher = new DTDDispatcher();
    protected final XMLDocumentFragmentScannerImpl.Dispatcher fTrailingMiscDispatcher = new TrailingMiscDispatcher();
    private final String[] fStrings = new String[3];
    private final XMLString fString = new XMLString();
    private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
    private XMLInputSource fExternalSubsetSource = null;
    private final XMLDTDDescription fDTDDescription = new XMLDTDDescription(null, null, null, null, null);

    protected class ContentDispatcher extends XMLDocumentFragmentScannerImpl.FragmentContentDispatcher {
        protected ContentDispatcher() {
            super();
        }

        @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.FragmentContentDispatcher
        protected boolean elementDepthIsZeroHook() throws IOException, XNIException {
            XMLDocumentScannerImpl.this.setScannerState(12);
            XMLDocumentScannerImpl xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
            xMLDocumentScannerImpl.setDispatcher(xMLDocumentScannerImpl.fTrailingMiscDispatcher);
            return true;
        }

        @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.FragmentContentDispatcher
        protected void endOfFileHook(EOFException eOFException) throws IOException, XNIException {
            XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
        }

        protected void resolveExternalSubsetAndRead() throws IOException, XNIException {
            XMLDocumentScannerImpl.this.fDTDDescription.setValues(null, null, XMLDocumentScannerImpl.this.fEntityManager.getCurrentResourceIdentifier().getExpandedSystemId(), null);
            XMLDocumentScannerImpl.this.fDTDDescription.setRootName(XMLDocumentScannerImpl.this.fElementQName.rawname);
            XMLInputSource externalSubset = XMLDocumentScannerImpl.this.fExternalSubsetResolver.getExternalSubset(XMLDocumentScannerImpl.this.fDTDDescription);
            if (externalSubset != null) {
                XMLDocumentScannerImpl xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
                xMLDocumentScannerImpl.fDoctypeName = xMLDocumentScannerImpl.fElementQName.rawname;
                XMLDocumentScannerImpl.this.fDoctypePublicId = externalSubset.getPublicId();
                XMLDocumentScannerImpl.this.fDoctypeSystemId = externalSubset.getSystemId();
                if (XMLDocumentScannerImpl.this.fDocumentHandler != null) {
                    XMLDocumentScannerImpl.this.fDocumentHandler.doctypeDecl(XMLDocumentScannerImpl.this.fDoctypeName, XMLDocumentScannerImpl.this.fDoctypePublicId, XMLDocumentScannerImpl.this.fDoctypeSystemId, null);
                }
                try {
                    if (XMLDocumentScannerImpl.this.fValidationManager == null || !XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD()) {
                        XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(externalSubset);
                        while (XMLDocumentScannerImpl.this.fDTDScanner.scanDTDExternalSubset(true)) {
                        }
                    } else {
                        XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(null);
                    }
                } finally {
                    XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
                }
            }
        }

        @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.FragmentContentDispatcher
        protected boolean scanForDoctypeHook() throws IOException, XNIException {
            if (!XMLDocumentScannerImpl.this.fEntityScanner.skipString("DOCTYPE")) {
                return false;
            }
            XMLDocumentScannerImpl.this.setScannerState(4);
            return true;
        }

        @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.FragmentContentDispatcher
        protected boolean scanRootElementHook() throws IOException, XNIException {
            if (XMLDocumentScannerImpl.this.fExternalSubsetResolver != null && !XMLDocumentScannerImpl.this.fSeenDoctypeDecl && !XMLDocumentScannerImpl.this.fDisallowDoctype && (XMLDocumentScannerImpl.this.fValidation || XMLDocumentScannerImpl.this.fLoadExternalDTD)) {
                XMLDocumentScannerImpl.this.scanStartElementName();
                resolveExternalSubsetAndRead();
                if (!XMLDocumentScannerImpl.this.scanStartElementAfterName()) {
                    return false;
                }
            } else if (!XMLDocumentScannerImpl.this.scanStartElement()) {
                return false;
            }
            XMLDocumentScannerImpl.this.setScannerState(12);
            XMLDocumentScannerImpl xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
            xMLDocumentScannerImpl.setDispatcher(xMLDocumentScannerImpl.fTrailingMiscDispatcher);
            return true;
        }
    }

    protected final class DTDDispatcher implements XMLDocumentFragmentScannerImpl.Dispatcher {
        protected DTDDispatcher() {
        }

        @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.Dispatcher
        public boolean dispatch(boolean z) throws IOException, XNIException {
            XMLEntityManager xMLEntityManager;
            XMLDocumentScannerImpl xMLDocumentScannerImpl;
            XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(null);
            while (true) {
                boolean z2 = false;
                try {
                    try {
                        try {
                            switch (XMLDocumentScannerImpl.this.fScannerState) {
                                case 17:
                                    boolean z3 = (XMLDocumentScannerImpl.this.fValidation || XMLDocumentScannerImpl.this.fLoadExternalDTD) && (XMLDocumentScannerImpl.this.fValidationManager == null || !XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD());
                                    if (!XMLDocumentScannerImpl.this.fDTDScanner.scanDTDInternalSubset(true, XMLDocumentScannerImpl.this.fStandalone, XMLDocumentScannerImpl.this.fHasExternalDTD && z3)) {
                                        if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(93)) {
                                            XMLDocumentScannerImpl.this.reportFatalError("EXPECTED_SQUARE_BRACKET_TO_CLOSE_INTERNAL_SUBSET", null);
                                        }
                                        XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
                                        if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(62)) {
                                            XMLDocumentScannerImpl xMLDocumentScannerImpl2 = XMLDocumentScannerImpl.this;
                                            xMLDocumentScannerImpl2.reportFatalError("DoctypedeclUnterminated", new Object[]{xMLDocumentScannerImpl2.fDoctypeName});
                                        }
                                        XMLDocumentScannerImpl.this.fMarkupDepth--;
                                        if (XMLDocumentScannerImpl.this.fDoctypeSystemId == null) {
                                            if (XMLDocumentScannerImpl.this.fExternalSubsetSource != null) {
                                                XMLDocumentScannerImpl xMLDocumentScannerImpl3 = XMLDocumentScannerImpl.this;
                                                xMLDocumentScannerImpl3.fIsEntityDeclaredVC = !xMLDocumentScannerImpl3.fStandalone;
                                                if (z3) {
                                                    XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(XMLDocumentScannerImpl.this.fExternalSubsetSource);
                                                    XMLDocumentScannerImpl.this.fExternalSubsetSource = null;
                                                    XMLDocumentScannerImpl.this.setScannerState(19);
                                                    break;
                                                }
                                            } else {
                                                XMLDocumentScannerImpl xMLDocumentScannerImpl4 = XMLDocumentScannerImpl.this;
                                                xMLDocumentScannerImpl4.fIsEntityDeclaredVC = xMLDocumentScannerImpl4.fEntityManager.hasPEReferences() && !XMLDocumentScannerImpl.this.fStandalone;
                                            }
                                            XMLDocumentScannerImpl.this.setScannerState(5);
                                            XMLDocumentScannerImpl xMLDocumentScannerImpl5 = XMLDocumentScannerImpl.this;
                                            xMLDocumentScannerImpl5.setDispatcher(xMLDocumentScannerImpl5.fPrologDispatcher);
                                            xMLEntityManager = XMLDocumentScannerImpl.this.fEntityManager;
                                            xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
                                        } else {
                                            XMLDocumentScannerImpl xMLDocumentScannerImpl6 = XMLDocumentScannerImpl.this;
                                            xMLDocumentScannerImpl6.fIsEntityDeclaredVC = !xMLDocumentScannerImpl6.fStandalone;
                                            if (!z3) {
                                                XMLDocumentScannerImpl.this.setScannerState(5);
                                                XMLDocumentScannerImpl xMLDocumentScannerImpl7 = XMLDocumentScannerImpl.this;
                                                xMLDocumentScannerImpl7.setDispatcher(xMLDocumentScannerImpl7.fPrologDispatcher);
                                                xMLEntityManager = XMLDocumentScannerImpl.this.fEntityManager;
                                                xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
                                            } else {
                                                XMLDocumentScannerImpl.this.setScannerState(18);
                                            }
                                        }
                                    }
                                    break;
                                case 18:
                                    XMLDocumentScannerImpl.this.fDTDDescription.setValues(XMLDocumentScannerImpl.this.fDoctypePublicId, XMLDocumentScannerImpl.this.fDoctypeSystemId, null, null);
                                    XMLDocumentScannerImpl.this.fDTDDescription.setRootName(XMLDocumentScannerImpl.this.fDoctypeName);
                                    XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(XMLDocumentScannerImpl.this.fEntityManager.resolveEntity(XMLDocumentScannerImpl.this.fDTDDescription));
                                    XMLDocumentScannerImpl.this.setScannerState(19);
                                    z2 = true;
                                    break;
                                case 19:
                                    if (!XMLDocumentScannerImpl.this.fDTDScanner.scanDTDExternalSubset(true)) {
                                        XMLDocumentScannerImpl.this.setScannerState(5);
                                        XMLDocumentScannerImpl xMLDocumentScannerImpl8 = XMLDocumentScannerImpl.this;
                                        xMLDocumentScannerImpl8.setDispatcher(xMLDocumentScannerImpl8.fPrologDispatcher);
                                        xMLEntityManager = XMLDocumentScannerImpl.this.fEntityManager;
                                        xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
                                    }
                                    break;
                                default:
                                    StringBuilder sbAppend = new StringBuilder().append("DTDDispatcher#dispatch: scanner state=").append(XMLDocumentScannerImpl.this.fScannerState).append(" (");
                                    XMLDocumentScannerImpl xMLDocumentScannerImpl9 = XMLDocumentScannerImpl.this;
                                    throw new XNIException(sbAppend.append(xMLDocumentScannerImpl9.getScannerStateName(xMLDocumentScannerImpl9.fScannerState)).append(')').toString());
                            }
                            if (!z && !z2) {
                                XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
                                return true;
                            }
                        } catch (CharConversionException e) {
                            XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", (Object[]) null, (short) 2, (Exception) e);
                            XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
                            return false;
                        }
                    } catch (EOFException unused) {
                        XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
                        XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
                        return false;
                    } catch (MalformedByteSequenceException e2) {
                        XMLDocumentScannerImpl.this.fErrorReporter.reportError(e2.getDomain(), e2.getKey(), e2.getArguments(), (short) 2, (Exception) e2);
                        XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
                        return false;
                    }
                } catch (Throwable th) {
                    XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
                    throw th;
                }
            }
            xMLEntityManager.setEntityHandler(xMLDocumentScannerImpl);
            XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
            return true;
        }
    }

    protected final class PrologDispatcher implements XMLDocumentFragmentScannerImpl.Dispatcher {
        protected PrologDispatcher() {
        }

        /* JADX WARN: Code duplicated, block: B:105:0x01d5 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:108:0x0000 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
        @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.Dispatcher
        public boolean dispatch(boolean z) throws IOException, XNIException {
            String str;
            XMLDocumentScannerImpl xMLDocumentScannerImpl;
            XMLDocumentScannerImpl xMLDocumentScannerImpl2;
            boolean z2;
            while (true) {
                try {
                    switch (XMLDocumentScannerImpl.this.fScannerState) {
                        case 1:
                            XMLDocumentScannerImpl.this.fMarkupDepth++;
                            str = "MarkupNotRecognizedInProlog";
                            if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(33)) {
                                if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(45)) {
                                    if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(45)) {
                                        XMLDocumentScannerImpl.this.reportFatalError("InvalidCommentStart", null);
                                    }
                                    XMLDocumentScannerImpl.this.setScannerState(2);
                                } else if (XMLDocumentScannerImpl.this.fEntityScanner.skipString("DOCTYPE")) {
                                    XMLDocumentScannerImpl.this.setScannerState(4);
                                } else {
                                    xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
                                    xMLDocumentScannerImpl.reportFatalError(str, null);
                                    z2 = false;
                                }
                                z2 = true;
                            } else {
                                XMLDocumentScannerImpl xMLDocumentScannerImpl3 = XMLDocumentScannerImpl.this;
                                if (xMLDocumentScannerImpl3.isValidNameStartChar(xMLDocumentScannerImpl3.fEntityScanner.peekChar())) {
                                    XMLDocumentScannerImpl.this.setScannerState(6);
                                    XMLDocumentScannerImpl xMLDocumentScannerImpl4 = XMLDocumentScannerImpl.this;
                                    xMLDocumentScannerImpl4.setDispatcher(xMLDocumentScannerImpl4.fContentDispatcher);
                                    return true;
                                }
                                if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(63)) {
                                    XMLDocumentScannerImpl.this.setScannerState(3);
                                    z2 = true;
                                } else {
                                    XMLDocumentScannerImpl xMLDocumentScannerImpl5 = XMLDocumentScannerImpl.this;
                                    if (xMLDocumentScannerImpl5.isValidNameStartHighSurrogate(xMLDocumentScannerImpl5.fEntityScanner.peekChar())) {
                                        XMLDocumentScannerImpl.this.setScannerState(6);
                                        XMLDocumentScannerImpl xMLDocumentScannerImpl6 = XMLDocumentScannerImpl.this;
                                        xMLDocumentScannerImpl6.setDispatcher(xMLDocumentScannerImpl6.fContentDispatcher);
                                        return true;
                                    }
                                    xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
                                    xMLDocumentScannerImpl.reportFatalError(str, null);
                                    z2 = false;
                                }
                            }
                            if (z && !z2) {
                                if (z) {
                                    if (XMLDocumentScannerImpl.this.fEntityScanner.scanChar() != 60) {
                                        XMLDocumentScannerImpl.this.reportFatalError("RootElementRequired", null);
                                    }
                                    XMLDocumentScannerImpl.this.setScannerState(6);
                                    XMLDocumentScannerImpl xMLDocumentScannerImpl7 = XMLDocumentScannerImpl.this;
                                    xMLDocumentScannerImpl7.setDispatcher(xMLDocumentScannerImpl7.fContentDispatcher);
                                }
                                return true;
                            }
                            break;
                            break;
                        case 2:
                            XMLDocumentScannerImpl.this.scanComment();
                            xMLDocumentScannerImpl2 = XMLDocumentScannerImpl.this;
                            xMLDocumentScannerImpl2.setScannerState(5);
                            z2 = false;
                            if (z) {
                            }
                            break;
                        case 3:
                            XMLDocumentScannerImpl.this.scanPI();
                            xMLDocumentScannerImpl2 = XMLDocumentScannerImpl.this;
                            xMLDocumentScannerImpl2.setScannerState(5);
                            z2 = false;
                            if (z) {
                            }
                            break;
                        case 4:
                            if (XMLDocumentScannerImpl.this.fDisallowDoctype) {
                                XMLDocumentScannerImpl.this.reportFatalError("DoctypeNotAllowed", null);
                            }
                            if (XMLDocumentScannerImpl.this.fSeenDoctypeDecl) {
                                XMLDocumentScannerImpl.this.reportFatalError("AlreadySeenDoctype", null);
                            }
                            XMLDocumentScannerImpl.this.fSeenDoctypeDecl = true;
                            if (XMLDocumentScannerImpl.this.scanDoctypeDecl()) {
                                XMLDocumentScannerImpl.this.setScannerState(17);
                                XMLDocumentScannerImpl xMLDocumentScannerImpl8 = XMLDocumentScannerImpl.this;
                                xMLDocumentScannerImpl8.setDispatcher(xMLDocumentScannerImpl8.fDTDDispatcher);
                                return true;
                            }
                            if (XMLDocumentScannerImpl.this.fDoctypeSystemId != null) {
                                XMLDocumentScannerImpl xMLDocumentScannerImpl9 = XMLDocumentScannerImpl.this;
                                xMLDocumentScannerImpl9.fIsEntityDeclaredVC = !xMLDocumentScannerImpl9.fStandalone;
                                if ((XMLDocumentScannerImpl.this.fValidation || XMLDocumentScannerImpl.this.fLoadExternalDTD) && (XMLDocumentScannerImpl.this.fValidationManager == null || !XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD())) {
                                    XMLDocumentScannerImpl.this.setScannerState(18);
                                    XMLDocumentScannerImpl xMLDocumentScannerImpl10 = XMLDocumentScannerImpl.this;
                                    xMLDocumentScannerImpl10.setDispatcher(xMLDocumentScannerImpl10.fDTDDispatcher);
                                    return true;
                                }
                            } else if (XMLDocumentScannerImpl.this.fExternalSubsetSource != null) {
                                XMLDocumentScannerImpl xMLDocumentScannerImpl11 = XMLDocumentScannerImpl.this;
                                xMLDocumentScannerImpl11.fIsEntityDeclaredVC = !xMLDocumentScannerImpl11.fStandalone;
                                if ((XMLDocumentScannerImpl.this.fValidation || XMLDocumentScannerImpl.this.fLoadExternalDTD) && (XMLDocumentScannerImpl.this.fValidationManager == null || !XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD())) {
                                    XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(XMLDocumentScannerImpl.this.fExternalSubsetSource);
                                    XMLDocumentScannerImpl.this.fExternalSubsetSource = null;
                                    XMLDocumentScannerImpl.this.setScannerState(19);
                                    XMLDocumentScannerImpl xMLDocumentScannerImpl12 = XMLDocumentScannerImpl.this;
                                    xMLDocumentScannerImpl12.setDispatcher(xMLDocumentScannerImpl12.fDTDDispatcher);
                                    return true;
                                }
                            }
                            XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(null);
                            xMLDocumentScannerImpl2 = XMLDocumentScannerImpl.this;
                            xMLDocumentScannerImpl2.setScannerState(5);
                            z2 = false;
                            if (z) {
                            }
                            break;
                        case 5:
                            XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
                            if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(60)) {
                                XMLDocumentScannerImpl.this.setScannerState(1);
                            } else if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(38)) {
                                XMLDocumentScannerImpl.this.setScannerState(8);
                            } else {
                                XMLDocumentScannerImpl.this.setScannerState(7);
                            }
                            z2 = true;
                            if (z) {
                            }
                            break;
                        case 6:
                        default:
                            z2 = false;
                            if (z) {
                            }
                            break;
                        case 7:
                            XMLDocumentScannerImpl.this.reportFatalError("ContentIllegalInProlog", null);
                            XMLDocumentScannerImpl.this.fEntityScanner.scanChar();
                        case 8:
                            xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
                            str = "ReferenceIllegalInProlog";
                            xMLDocumentScannerImpl.reportFatalError(str, null);
                            z2 = false;
                            if (z) {
                            }
                            break;
                    }
                } catch (MalformedByteSequenceException e) {
                    XMLDocumentScannerImpl.this.fErrorReporter.reportError(e.getDomain(), e.getKey(), e.getArguments(), (short) 2, (Exception) e);
                    return false;
                } catch (CharConversionException e2) {
                    XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", (Object[]) null, (short) 2, (Exception) e2);
                    return false;
                } catch (EOFException unused) {
                    XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
                    return false;
                }
            }
        }
    }

    protected final class TrailingMiscDispatcher implements XMLDocumentFragmentScannerImpl.Dispatcher {
        protected TrailingMiscDispatcher() {
        }

        @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.Dispatcher
        public boolean dispatch(boolean z) throws IOException, XNIException {
            XMLDocumentScannerImpl xMLDocumentScannerImpl;
            XMLDocumentScannerImpl xMLDocumentScannerImpl2;
            while (true) {
                boolean z2 = false;
                try {
                    int i = XMLDocumentScannerImpl.this.fScannerState;
                    if (i != 1) {
                        if (i == 2) {
                            if (!XMLDocumentScannerImpl.this.fEntityScanner.skipString("--")) {
                                XMLDocumentScannerImpl.this.reportFatalError("InvalidCommentStart", null);
                            }
                            XMLDocumentScannerImpl.this.scanComment();
                            xMLDocumentScannerImpl2 = XMLDocumentScannerImpl.this;
                        } else if (i == 3) {
                            XMLDocumentScannerImpl.this.scanPI();
                            xMLDocumentScannerImpl2 = XMLDocumentScannerImpl.this;
                        } else if (i != 7) {
                            if (i == 8) {
                                XMLDocumentScannerImpl.this.reportFatalError("ReferenceIllegalInTrailingMisc", null);
                                xMLDocumentScannerImpl2 = XMLDocumentScannerImpl.this;
                            } else if (i == 12) {
                                XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
                                if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(60)) {
                                    XMLDocumentScannerImpl.this.setScannerState(1);
                                } else {
                                    XMLDocumentScannerImpl.this.setScannerState(7);
                                }
                                z2 = true;
                            } else if (i == 14) {
                                return false;
                            }
                        } else {
                            if (XMLDocumentScannerImpl.this.fEntityScanner.peekChar() == -1) {
                                XMLDocumentScannerImpl.this.setScannerState(14);
                                return false;
                            }
                            XMLDocumentScannerImpl.this.reportFatalError("ContentIllegalInTrailingMisc", null);
                            XMLDocumentScannerImpl.this.fEntityScanner.scanChar();
                            xMLDocumentScannerImpl2 = XMLDocumentScannerImpl.this;
                        }
                        xMLDocumentScannerImpl2.setScannerState(12);
                    } else {
                        XMLDocumentScannerImpl.this.fMarkupDepth++;
                        if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(63)) {
                            XMLDocumentScannerImpl.this.setScannerState(3);
                        } else if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(33)) {
                            XMLDocumentScannerImpl.this.setScannerState(2);
                        } else if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(47)) {
                            XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", null);
                        } else {
                            XMLDocumentScannerImpl xMLDocumentScannerImpl3 = XMLDocumentScannerImpl.this;
                            if (xMLDocumentScannerImpl3.isValidNameStartChar(xMLDocumentScannerImpl3.fEntityScanner.peekChar())) {
                                XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", null);
                                XMLDocumentScannerImpl.this.scanStartElement();
                                xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
                            } else {
                                XMLDocumentScannerImpl xMLDocumentScannerImpl4 = XMLDocumentScannerImpl.this;
                                if (xMLDocumentScannerImpl4.isValidNameStartHighSurrogate(xMLDocumentScannerImpl4.fEntityScanner.peekChar())) {
                                    XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", null);
                                    XMLDocumentScannerImpl.this.scanStartElement();
                                    xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
                                } else {
                                    XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", null);
                                }
                            }
                            xMLDocumentScannerImpl.setScannerState(7);
                        }
                        z2 = true;
                    }
                    if (!z && !z2) {
                        return true;
                    }
                } catch (EOFException unused) {
                    if (XMLDocumentScannerImpl.this.fMarkupDepth != 0) {
                        XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
                        return false;
                    }
                    XMLDocumentScannerImpl.this.setScannerState(14);
                    return false;
                } catch (MalformedByteSequenceException e) {
                    XMLDocumentScannerImpl.this.fErrorReporter.reportError(e.getDomain(), e.getKey(), e.getArguments(), (short) 2, (Exception) e);
                    return false;
                } catch (CharConversionException e2) {
                    XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", (Object[]) null, (short) 2, (Exception) e2);
                    return false;
                }
            }
        }
    }

    protected final class XMLDeclDispatcher implements XMLDocumentFragmentScannerImpl.Dispatcher {
        protected XMLDeclDispatcher() {
        }

        @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.Dispatcher
        public boolean dispatch(boolean z) throws IOException, XNIException {
            XMLDocumentScannerImpl.this.setScannerState(5);
            XMLDocumentScannerImpl xMLDocumentScannerImpl = XMLDocumentScannerImpl.this;
            xMLDocumentScannerImpl.setDispatcher(xMLDocumentScannerImpl.fPrologDispatcher);
            try {
                if (XMLDocumentScannerImpl.this.fEntityScanner.skipString("<?xml")) {
                    XMLDocumentScannerImpl.this.fMarkupDepth++;
                    if (XMLChar.isName(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                        XMLDocumentScannerImpl.this.fStringBuffer.clear();
                        XMLDocumentScannerImpl.this.fStringBuffer.append("xml");
                        if (XMLDocumentScannerImpl.this.fNamespaces) {
                            while (XMLChar.isNCName(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                                XMLDocumentScannerImpl.this.fStringBuffer.append((char) XMLDocumentScannerImpl.this.fEntityScanner.scanChar());
                            }
                        } else {
                            while (XMLChar.isName(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                                XMLDocumentScannerImpl.this.fStringBuffer.append((char) XMLDocumentScannerImpl.this.fEntityScanner.scanChar());
                            }
                        }
                        String strAddSymbol = XMLDocumentScannerImpl.this.fSymbolTable.addSymbol(XMLDocumentScannerImpl.this.fStringBuffer.ch, XMLDocumentScannerImpl.this.fStringBuffer.offset, XMLDocumentScannerImpl.this.fStringBuffer.length);
                        XMLDocumentScannerImpl xMLDocumentScannerImpl2 = XMLDocumentScannerImpl.this;
                        xMLDocumentScannerImpl2.scanPIData(strAddSymbol, xMLDocumentScannerImpl2.fString);
                    } else {
                        XMLDocumentScannerImpl.this.scanXMLDeclOrTextDecl(false);
                    }
                }
                XMLDocumentScannerImpl.this.fEntityManager.fCurrentEntity.mayReadChunks = true;
                return true;
            } catch (CharConversionException e) {
                XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", (Object[]) null, (short) 2, (Exception) e);
                return false;
            } catch (EOFException unused) {
                XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
                return false;
            } catch (MalformedByteSequenceException e2) {
                XMLDocumentScannerImpl.this.fErrorReporter.reportError(e2.getDomain(), e2.getKey(), e2.getArguments(), (short) 2, (Exception) e2);
                return false;
            }
        }
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl
    protected XMLDocumentFragmentScannerImpl.Dispatcher createContentDispatcher() {
        return new ContentDispatcher();
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl, org.apache.xerces.impl.XMLScanner, org.apache.xerces.impl.XMLEntityHandler
    public void endEntity(String str, Augmentations augmentations) throws XNIException {
        super.endEntity(str, augmentations);
        if (this.fDocumentHandler == null || !str.equals("[xml]")) {
            return;
        }
        this.fDocumentHandler.endDocument(null);
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl, org.apache.xerces.xni.parser.XMLComponent
    public Boolean getFeatureDefault(String str) {
        int i = 0;
        while (true) {
            String[] strArr = RECOGNIZED_FEATURES;
            if (i >= strArr.length) {
                return super.getFeatureDefault(str);
            }
            if (strArr[i].equals(str)) {
                return FEATURE_DEFAULTS[i];
            }
            i++;
        }
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl, org.apache.xerces.xni.parser.XMLComponent
    public Object getPropertyDefault(String str) {
        int i = 0;
        while (true) {
            String[] strArr = RECOGNIZED_PROPERTIES;
            if (i >= strArr.length) {
                return super.getPropertyDefault(str);
            }
            if (strArr[i].equals(str)) {
                return PROPERTY_DEFAULTS[i];
            }
            i++;
        }
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl, org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedFeatures() {
        String[] recognizedFeatures = super.getRecognizedFeatures();
        int length = recognizedFeatures != null ? recognizedFeatures.length : 0;
        String[] strArr = RECOGNIZED_FEATURES;
        String[] strArr2 = new String[strArr.length + length];
        if (recognizedFeatures != null) {
            System.arraycopy(recognizedFeatures, 0, strArr2, 0, recognizedFeatures.length);
        }
        System.arraycopy(strArr, 0, strArr2, length, strArr.length);
        return strArr2;
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl, org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedProperties() {
        String[] recognizedProperties = super.getRecognizedProperties();
        int length = recognizedProperties != null ? recognizedProperties.length : 0;
        String[] strArr = RECOGNIZED_PROPERTIES;
        String[] strArr2 = new String[strArr.length + length];
        if (recognizedProperties != null) {
            System.arraycopy(recognizedProperties, 0, strArr2, 0, recognizedProperties.length);
        }
        System.arraycopy(strArr, 0, strArr2, length, strArr.length);
        return strArr2;
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl
    protected String getScannerStateName(int i) {
        if (i == 0) {
            return "SCANNER_STATE_XML_DECL";
        }
        if (i == 5) {
            return "SCANNER_STATE_PROLOG";
        }
        if (i == 12) {
            return "SCANNER_STATE_TRAILING_MISC";
        }
        switch (i) {
            case 17:
                return "SCANNER_STATE_DTD_INTERNAL_DECLS";
            case 18:
                return "SCANNER_STATE_DTD_EXTERNAL";
            case 19:
                return "SCANNER_STATE_DTD_EXTERNAL_DECLS";
            default:
                return super.getScannerStateName(i);
        }
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl, org.apache.xerces.impl.XMLScanner, org.apache.xerces.xni.parser.XMLComponent
    public void reset(XMLComponentManager xMLComponentManager) throws XMLConfigurationException {
        super.reset(xMLComponentManager);
        this.fDoctypeName = null;
        this.fDoctypePublicId = null;
        this.fDoctypeSystemId = null;
        this.fSeenDoctypeDecl = false;
        this.fScanningDTD = false;
        this.fExternalSubsetSource = null;
        if (this.fParserSettings) {
            try {
                this.fLoadExternalDTD = xMLComponentManager.getFeature(LOAD_EXTERNAL_DTD);
            } catch (XMLConfigurationException unused) {
                this.fLoadExternalDTD = true;
            }
            try {
                this.fDisallowDoctype = xMLComponentManager.getFeature(DISALLOW_DOCTYPE_DECL_FEATURE);
            } catch (XMLConfigurationException unused2) {
                this.fDisallowDoctype = false;
            }
            this.fDTDScanner = (XMLDTDScanner) xMLComponentManager.getProperty(DTD_SCANNER);
            try {
                this.fValidationManager = (ValidationManager) xMLComponentManager.getProperty(VALIDATION_MANAGER);
            } catch (XMLConfigurationException unused3) {
                this.fValidationManager = null;
            }
            try {
                this.fNamespaceContext = (NamespaceContext) xMLComponentManager.getProperty(NAMESPACE_CONTEXT);
            } catch (XMLConfigurationException unused4) {
            }
            if (this.fNamespaceContext == null) {
                this.fNamespaceContext = new NamespaceSupport();
            }
        }
        this.fNamespaceContext.reset();
        setScannerState(0);
        setDispatcher(this.fXMLDeclDispatcher);
    }

    protected boolean scanDoctypeDecl() throws IOException, XNIException {
        XMLDocumentHandler xMLDocumentHandler;
        String str;
        String publicId;
        String systemId;
        if (!this.fEntityScanner.skipSpaces()) {
            reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ROOT_ELEMENT_TYPE_IN_DOCTYPEDECL", null);
        }
        String strScanName = this.fEntityScanner.scanName();
        this.fDoctypeName = strScanName;
        if (strScanName == null) {
            reportFatalError("MSG_ROOT_ELEMENT_TYPE_REQUIRED", null);
        }
        if (this.fEntityScanner.skipSpaces()) {
            scanExternalID(this.fStrings, false);
            String[] strArr = this.fStrings;
            this.fDoctypeSystemId = strArr[0];
            this.fDoctypePublicId = strArr[1];
            this.fEntityScanner.skipSpaces();
        }
        this.fHasExternalDTD = this.fDoctypeSystemId != null;
        if (!this.fHasExternalDTD && this.fExternalSubsetResolver != null) {
            this.fDTDDescription.setValues(null, null, this.fEntityManager.getCurrentResourceIdentifier().getExpandedSystemId(), null);
            this.fDTDDescription.setRootName(this.fDoctypeName);
            XMLInputSource externalSubset = this.fExternalSubsetResolver.getExternalSubset(this.fDTDDescription);
            this.fExternalSubsetSource = externalSubset;
            this.fHasExternalDTD = externalSubset != null;
        }
        if (this.fDocumentHandler != null) {
            if (this.fExternalSubsetSource == null) {
                xMLDocumentHandler = this.fDocumentHandler;
                str = this.fDoctypeName;
                publicId = this.fDoctypePublicId;
                systemId = this.fDoctypeSystemId;
            } else {
                xMLDocumentHandler = this.fDocumentHandler;
                str = this.fDoctypeName;
                publicId = this.fExternalSubsetSource.getPublicId();
                systemId = this.fExternalSubsetSource.getSystemId();
            }
            xMLDocumentHandler.doctypeDecl(str, publicId, systemId, null);
        }
        if (this.fEntityScanner.skipChar(91)) {
            return true;
        }
        this.fEntityScanner.skipSpaces();
        if (!this.fEntityScanner.skipChar(62)) {
            reportFatalError("DoctypedeclUnterminated", new Object[]{this.fDoctypeName});
        }
        this.fMarkupDepth--;
        return false;
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl, org.apache.xerces.impl.XMLScanner, org.apache.xerces.xni.parser.XMLComponent
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
        super.setFeature(str, z);
        if (str.startsWith(Constants.XERCES_FEATURE_PREFIX)) {
            int length = str.length() - Constants.XERCES_FEATURE_PREFIX.length();
            if (length == Constants.LOAD_EXTERNAL_DTD_FEATURE.length() && str.endsWith(Constants.LOAD_EXTERNAL_DTD_FEATURE)) {
                this.fLoadExternalDTD = z;
            } else if (length == Constants.DISALLOW_DOCTYPE_DECL_FEATURE.length() && str.endsWith(Constants.DISALLOW_DOCTYPE_DECL_FEATURE)) {
                this.fDisallowDoctype = z;
            }
        }
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl, org.apache.xerces.xni.parser.XMLDocumentScanner
    public void setInputSource(XMLInputSource xMLInputSource) throws IOException {
        this.fEntityManager.setEntityHandler(this);
        this.fEntityManager.startDocumentEntity(xMLInputSource);
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl, org.apache.xerces.impl.XMLScanner, org.apache.xerces.xni.parser.XMLComponent
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        super.setProperty(str, obj);
        if (str.startsWith(Constants.XERCES_PROPERTY_PREFIX)) {
            int length = str.length() - Constants.XERCES_PROPERTY_PREFIX.length();
            if (length == Constants.DTD_SCANNER_PROPERTY.length() && str.endsWith(Constants.DTD_SCANNER_PROPERTY)) {
                this.fDTDScanner = (XMLDTDScanner) obj;
            }
            if (length == Constants.NAMESPACE_CONTEXT_PROPERTY.length() && str.endsWith(Constants.NAMESPACE_CONTEXT_PROPERTY) && obj != null) {
                this.fNamespaceContext = (NamespaceContext) obj;
            }
        }
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl, org.apache.xerces.impl.XMLScanner, org.apache.xerces.impl.XMLEntityHandler
    public void startEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        super.startEntity(str, xMLResourceIdentifier, str2, augmentations);
        if (!str.equals("[xml]") && this.fEntityScanner.isExternal()) {
            setScannerState(16);
        }
        if (this.fDocumentHandler == null || !str.equals("[xml]")) {
            return;
        }
        this.fDocumentHandler.startDocument(this.fEntityScanner, str2, this.fNamespaceContext, null);
    }
}
