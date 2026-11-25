package org.apache.xerces.impl;

import java.io.IOException;
import org.apache.xerces.impl.dtd.XMLDTDValidatorFilter;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentSource;

/* JADX INFO: loaded from: classes13.dex */
public class XML11NSDocumentScannerImpl extends XML11DocumentScannerImpl {
    protected boolean fBindNamespaces;
    private XMLDTDValidatorFilter fDTDValidator;
    protected boolean fPerformValidation;
    private boolean fSawSpace;

    protected final class NS11ContentDispatcher extends XMLDocumentScannerImpl.ContentDispatcher {
        protected NS11ContentDispatcher() {
            super();
        }

        private void reconfigurePipeline() {
            if (XML11NSDocumentScannerImpl.this.fDTDValidator == null) {
                XML11NSDocumentScannerImpl.this.fBindNamespaces = true;
                return;
            }
            if (XML11NSDocumentScannerImpl.this.fDTDValidator.hasGrammar()) {
                return;
            }
            XML11NSDocumentScannerImpl.this.fBindNamespaces = true;
            XML11NSDocumentScannerImpl xML11NSDocumentScannerImpl = XML11NSDocumentScannerImpl.this;
            xML11NSDocumentScannerImpl.fPerformValidation = xML11NSDocumentScannerImpl.fDTDValidator.validate();
            XMLDocumentSource documentSource = XML11NSDocumentScannerImpl.this.fDTDValidator.getDocumentSource();
            XMLDocumentHandler documentHandler = XML11NSDocumentScannerImpl.this.fDTDValidator.getDocumentHandler();
            documentSource.setDocumentHandler(documentHandler);
            if (documentHandler != null) {
                documentHandler.setDocumentSource(documentSource);
            }
            XML11NSDocumentScannerImpl.this.fDTDValidator.setDocumentSource(null);
            XML11NSDocumentScannerImpl.this.fDTDValidator.setDocumentHandler(null);
        }

        @Override // org.apache.xerces.impl.XMLDocumentScannerImpl.ContentDispatcher, org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.FragmentContentDispatcher
        protected boolean scanRootElementHook() throws IOException, XNIException {
            if (XML11NSDocumentScannerImpl.this.fExternalSubsetResolver == null || XML11NSDocumentScannerImpl.this.fSeenDoctypeDecl || XML11NSDocumentScannerImpl.this.fDisallowDoctype || !(XML11NSDocumentScannerImpl.this.fValidation || XML11NSDocumentScannerImpl.this.fLoadExternalDTD)) {
                reconfigurePipeline();
                if (!XML11NSDocumentScannerImpl.this.scanStartElement()) {
                    return false;
                }
            } else {
                XML11NSDocumentScannerImpl.this.scanStartElementName();
                resolveExternalSubsetAndRead();
                reconfigurePipeline();
                if (!XML11NSDocumentScannerImpl.this.scanStartElementAfterName()) {
                    return false;
                }
            }
            XML11NSDocumentScannerImpl.this.setScannerState(12);
            XML11NSDocumentScannerImpl xML11NSDocumentScannerImpl = XML11NSDocumentScannerImpl.this;
            xML11NSDocumentScannerImpl.setDispatcher(xML11NSDocumentScannerImpl.fTrailingMiscDispatcher);
            return true;
        }
    }

    @Override // org.apache.xerces.impl.XMLDocumentScannerImpl, org.apache.xerces.impl.XMLDocumentFragmentScannerImpl
    protected XMLDocumentFragmentScannerImpl.Dispatcher createContentDispatcher() {
        return new NS11ContentDispatcher();
    }

    @Override // org.apache.xerces.impl.XMLDocumentScannerImpl, org.apache.xerces.impl.XMLDocumentFragmentScannerImpl, org.apache.xerces.impl.XMLScanner, org.apache.xerces.xni.parser.XMLComponent
    public void reset(XMLComponentManager xMLComponentManager) throws XMLConfigurationException {
        super.reset(xMLComponentManager);
        this.fPerformValidation = false;
        this.fBindNamespaces = false;
    }

    protected void scanAttribute(XMLAttributesImpl xMLAttributesImpl) throws IOException, XNIException {
        int length;
        NamespaceContext namespaceContext;
        String str;
        this.fEntityScanner.scanQName(this.fAttributeQName);
        this.fEntityScanner.skipSpaces();
        if (!this.fEntityScanner.skipChar(61)) {
            reportFatalError("EqRequiredInAttribute", new Object[]{this.fCurrentElement.rawname, this.fAttributeQName.rawname});
        }
        this.fEntityScanner.skipSpaces();
        if (this.fBindNamespaces) {
            length = xMLAttributesImpl.getLength();
            xMLAttributesImpl.addAttributeNS(this.fAttributeQName, XMLSymbols.fCDATASymbol, null);
        } else {
            int length2 = xMLAttributesImpl.getLength();
            int iAddAttribute = xMLAttributesImpl.addAttribute(this.fAttributeQName, XMLSymbols.fCDATASymbol, null);
            if (length2 == xMLAttributesImpl.getLength()) {
                reportFatalError("AttributeNotUnique", new Object[]{this.fCurrentElement.rawname, this.fAttributeQName.rawname});
            }
            length = iAddAttribute;
        }
        boolean zScanAttributeValue = scanAttributeValue(this.fTempString, this.fTempString2, this.fAttributeQName.rawname, this.fIsEntityDeclaredVC, this.fCurrentElement.rawname);
        String string = this.fTempString.toString();
        xMLAttributesImpl.setValue(length, string);
        if (!zScanAttributeValue) {
            xMLAttributesImpl.setNonNormalizedValue(length, this.fTempString2.toString());
        }
        xMLAttributesImpl.setSpecified(length, true);
        if (this.fBindNamespaces) {
            String str2 = this.fAttributeQName.localpart;
            String str3 = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
            if (str3 == XMLSymbols.PREFIX_XMLNS || (str3 == XMLSymbols.EMPTY_STRING && str2 == XMLSymbols.PREFIX_XMLNS)) {
                String strAddSymbol = this.fSymbolTable.addSymbol(string);
                if (str3 == XMLSymbols.PREFIX_XMLNS && str2 == XMLSymbols.PREFIX_XMLNS) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXMLNS", new Object[]{this.fAttributeQName}, (short) 2);
                }
                if (strAddSymbol == NamespaceContext.XMLNS_URI) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXMLNS", new Object[]{this.fAttributeQName}, (short) 2);
                }
                if (str2 == XMLSymbols.PREFIX_XML) {
                    if (strAddSymbol != NamespaceContext.XML_URI) {
                        this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXML", new Object[]{this.fAttributeQName}, (short) 2);
                    }
                } else if (strAddSymbol == NamespaceContext.XML_URI) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXML", new Object[]{this.fAttributeQName}, (short) 2);
                }
                if (str2 == XMLSymbols.PREFIX_XMLNS) {
                    str2 = XMLSymbols.EMPTY_STRING;
                }
                this.fNamespaceContext.declarePrefix(str2, strAddSymbol.length() != 0 ? strAddSymbol : null);
                namespaceContext = this.fNamespaceContext;
                str = XMLSymbols.PREFIX_XMLNS;
            } else {
                if (this.fAttributeQName.prefix == null) {
                    return;
                }
                namespaceContext = this.fNamespaceContext;
                str = this.fAttributeQName.prefix;
            }
            xMLAttributesImpl.setURI(length, namespaceContext.getURI(str));
        }
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl
    protected int scanEndElement() throws IOException, XNIException {
        this.fElementStack.popElement(this.fElementQName);
        if (!this.fEntityScanner.skipString(this.fElementQName.rawname)) {
            reportFatalError("ETagRequired", new Object[]{this.fElementQName.rawname});
        }
        this.fEntityScanner.skipSpaces();
        if (!this.fEntityScanner.skipChar(62)) {
            reportFatalError("ETagUnterminated", new Object[]{this.fElementQName.rawname});
        }
        this.fMarkupDepth--;
        this.fMarkupDepth--;
        if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
            reportFatalError("ElementEntityMismatch", new Object[]{this.fCurrentElement.rawname});
        }
        if (this.fDocumentHandler != null) {
            this.fDocumentHandler.endElement(this.fElementQName, null);
            if (this.fBindNamespaces) {
                this.fNamespaceContext.popContext();
            }
        }
        return this.fMarkupDepth;
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl
    protected boolean scanStartElement() throws IOException, XNIException {
        int i;
        boolean z;
        QName qNameCheckDuplicatesNS;
        this.fEntityScanner.scanQName(this.fElementQName);
        String str = this.fElementQName.rawname;
        if (this.fBindNamespaces) {
            this.fNamespaceContext.pushContext();
            if (this.fScannerState == 6 && this.fPerformValidation) {
                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_GRAMMAR_NOT_FOUND", new Object[]{str}, (short) 1);
                if (this.fDoctypeName == null || !this.fDoctypeName.equals(str)) {
                    this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RootElementTypeMustMatchDoctypedecl", new Object[]{this.fDoctypeName, str}, (short) 1);
                }
            }
        }
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
        if (this.fBindNamespaces) {
            if (this.fElementQName.prefix == XMLSymbols.PREFIX_XMLNS) {
                this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "ElementXMLNSPrefix", new Object[]{this.fElementQName.rawname}, (short) 2);
            }
            this.fElementQName.uri = this.fNamespaceContext.getURI(this.fElementQName.prefix != null ? this.fElementQName.prefix : XMLSymbols.EMPTY_STRING);
            this.fCurrentElement.uri = this.fElementQName.uri;
            if (this.fElementQName.prefix == null && this.fElementQName.uri != null) {
                this.fElementQName.prefix = XMLSymbols.EMPTY_STRING;
                this.fCurrentElement.prefix = XMLSymbols.EMPTY_STRING;
            }
            if (this.fElementQName.prefix != null && this.fElementQName.uri == null) {
                this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "ElementPrefixUnbound", new Object[]{this.fElementQName.prefix, this.fElementQName.rawname}, (short) 2);
            }
            int length = this.fAttributes.getLength();
            for (i = 0; i < length; i++) {
                this.fAttributes.getName(i, this.fAttributeQName);
                String str2 = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
                String uri = this.fNamespaceContext.getURI(str2);
                if ((this.fAttributeQName.uri == null || this.fAttributeQName.uri != uri) && str2 != XMLSymbols.EMPTY_STRING) {
                    this.fAttributeQName.uri = uri;
                    if (uri == null) {
                        this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "AttributePrefixUnbound", new Object[]{this.fElementQName.rawname, this.fAttributeQName.rawname, str2}, (short) 2);
                    }
                    this.fAttributes.setURI(i, uri);
                }
            }
            if (length > 1 && (qNameCheckDuplicatesNS = this.fAttributes.checkDuplicatesNS()) != null) {
                if (qNameCheckDuplicatesNS.uri != null) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "AttributeNSNotUnique", new Object[]{this.fElementQName.rawname, qNameCheckDuplicatesNS.localpart, qNameCheckDuplicatesNS.uri}, (short) 2);
                } else {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "AttributeNotUnique", new Object[]{this.fElementQName.rawname, qNameCheckDuplicatesNS.rawname}, (short) 2);
                }
            }
        }
        if (this.fDocumentHandler != null) {
            if (z) {
                this.fMarkupDepth--;
                if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
                    reportFatalError("ElementEntityMismatch", new Object[]{this.fCurrentElement.rawname});
                }
                this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, null);
                if (this.fBindNamespaces) {
                    this.fNamespaceContext.popContext();
                }
                this.fElementStack.popElement(this.fElementQName);
            } else {
                this.fDocumentHandler.startElement(this.fElementQName, this.fAttributes, null);
            }
        }
        return z;
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl
    protected boolean scanStartElementAfterName() throws IOException, XNIException {
        int i;
        boolean z;
        QName qNameCheckDuplicatesNS;
        String str = this.fElementQName.rawname;
        if (this.fBindNamespaces) {
            this.fNamespaceContext.pushContext();
            if (this.fScannerState == 6 && this.fPerformValidation) {
                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_GRAMMAR_NOT_FOUND", new Object[]{str}, (short) 1);
                if (this.fDoctypeName == null || !this.fDoctypeName.equals(str)) {
                    this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RootElementTypeMustMatchDoctypedecl", new Object[]{this.fDoctypeName, str}, (short) 1);
                }
            }
        }
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
        if (this.fBindNamespaces) {
            if (this.fElementQName.prefix == XMLSymbols.PREFIX_XMLNS) {
                this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "ElementXMLNSPrefix", new Object[]{this.fElementQName.rawname}, (short) 2);
            }
            this.fElementQName.uri = this.fNamespaceContext.getURI(this.fElementQName.prefix != null ? this.fElementQName.prefix : XMLSymbols.EMPTY_STRING);
            this.fCurrentElement.uri = this.fElementQName.uri;
            if (this.fElementQName.prefix == null && this.fElementQName.uri != null) {
                this.fElementQName.prefix = XMLSymbols.EMPTY_STRING;
                this.fCurrentElement.prefix = XMLSymbols.EMPTY_STRING;
            }
            if (this.fElementQName.prefix != null && this.fElementQName.uri == null) {
                this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "ElementPrefixUnbound", new Object[]{this.fElementQName.prefix, this.fElementQName.rawname}, (short) 2);
            }
            int length = this.fAttributes.getLength();
            for (i = 0; i < length; i++) {
                this.fAttributes.getName(i, this.fAttributeQName);
                String str2 = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
                String uri = this.fNamespaceContext.getURI(str2);
                if ((this.fAttributeQName.uri == null || this.fAttributeQName.uri != uri) && str2 != XMLSymbols.EMPTY_STRING) {
                    this.fAttributeQName.uri = uri;
                    if (uri == null) {
                        this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "AttributePrefixUnbound", new Object[]{this.fElementQName.rawname, this.fAttributeQName.rawname, str2}, (short) 2);
                    }
                    this.fAttributes.setURI(i, uri);
                }
            }
            if (length > 1 && (qNameCheckDuplicatesNS = this.fAttributes.checkDuplicatesNS()) != null) {
                if (qNameCheckDuplicatesNS.uri != null) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "AttributeNSNotUnique", new Object[]{this.fElementQName.rawname, qNameCheckDuplicatesNS.localpart, qNameCheckDuplicatesNS.uri}, (short) 2);
                } else {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "AttributeNotUnique", new Object[]{this.fElementQName.rawname, qNameCheckDuplicatesNS.rawname}, (short) 2);
                }
            }
        }
        if (this.fDocumentHandler != null) {
            if (z) {
                this.fMarkupDepth--;
                if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
                    reportFatalError("ElementEntityMismatch", new Object[]{this.fCurrentElement.rawname});
                }
                this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, null);
                if (this.fBindNamespaces) {
                    this.fNamespaceContext.popContext();
                }
                this.fElementStack.popElement(this.fElementQName);
            } else {
                this.fDocumentHandler.startElement(this.fElementQName, this.fAttributes, null);
            }
        }
        return z;
    }

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl
    protected void scanStartElementName() throws IOException, XNIException {
        this.fEntityScanner.scanQName(this.fElementQName);
        this.fSawSpace = this.fEntityScanner.skipSpaces();
    }

    public void setDTDValidator(XMLDTDValidatorFilter xMLDTDValidatorFilter) {
        this.fDTDValidator = xMLDTDValidatorFilter;
    }
}
