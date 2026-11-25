package org.apache.xerces.impl;

import java.io.IOException;
import kotlin.text.Typography;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.XMLDTDContentModelHandler;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDScanner;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes13.dex */
public class XMLDTDScannerImpl extends XMLScanner implements XMLDTDScanner, XMLComponent, XMLEntityHandler {
    private static final boolean DEBUG_SCANNER_STATE = false;
    protected static final int SCANNER_STATE_END_OF_INPUT = 0;
    protected static final int SCANNER_STATE_MARKUP_DECL = 2;
    protected static final int SCANNER_STATE_TEXT_DECL = 1;
    private int fContentDepth;
    private int[] fContentStack;
    protected XMLDTDContentModelHandler fDTDContentModelHandler;
    protected XMLDTDHandler fDTDHandler;
    private String[] fEnumeration;
    private int fEnumerationCount;
    private int fExtEntityDepth;
    private final XMLStringBuffer fIgnoreConditionalBuffer;
    private int fIncludeSectDepth;
    private final XMLString fLiteral;
    private final XMLString fLiteral2;
    private int fMarkUpDepth;
    private int fPEDepth;
    private boolean[] fPEReport;
    private int[] fPEStack;
    protected int fScannerState;
    protected boolean fSeenExternalDTD;
    protected boolean fSeenPEReferences;
    protected boolean fStandalone;
    private boolean fStartDTDCalled;
    private final XMLString fString;
    private final XMLStringBuffer fStringBuffer;
    private final XMLStringBuffer fStringBuffer2;
    private final String[] fStrings;
    private static final String[] RECOGNIZED_FEATURES = {"http://xml.org/sax/features/validation", "http://apache.org/xml/features/scanner/notify-char-refs"};
    private static final Boolean[] FEATURE_DEFAULTS = {null, Boolean.FALSE};
    private static final String[] RECOGNIZED_PROPERTIES = {"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager"};
    private static final Object[] PROPERTY_DEFAULTS = {null, null, null};

    public XMLDTDScannerImpl() {
        this.fContentStack = new int[5];
        this.fPEStack = new int[5];
        this.fPEReport = new boolean[5];
        this.fStrings = new String[3];
        this.fString = new XMLString();
        this.fStringBuffer = new XMLStringBuffer();
        this.fStringBuffer2 = new XMLStringBuffer();
        this.fLiteral = new XMLString();
        this.fLiteral2 = new XMLString();
        this.fEnumeration = new String[5];
        this.fIgnoreConditionalBuffer = new XMLStringBuffer(128);
    }

    public XMLDTDScannerImpl(SymbolTable symbolTable, XMLErrorReporter xMLErrorReporter, XMLEntityManager xMLEntityManager) {
        this.fContentStack = new int[5];
        this.fPEStack = new int[5];
        this.fPEReport = new boolean[5];
        this.fStrings = new String[3];
        this.fString = new XMLString();
        this.fStringBuffer = new XMLStringBuffer();
        this.fStringBuffer2 = new XMLStringBuffer();
        this.fLiteral = new XMLString();
        this.fLiteral2 = new XMLString();
        this.fEnumeration = new String[5];
        this.fIgnoreConditionalBuffer = new XMLStringBuffer(128);
        this.fSymbolTable = symbolTable;
        this.fErrorReporter = xMLErrorReporter;
        this.fEntityManager = xMLEntityManager;
        xMLEntityManager.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
    }

    private final void ensureEnumerationSize(int i) {
        String[] strArr = this.fEnumeration;
        if (strArr.length == i) {
            String[] strArr2 = new String[i * 2];
            System.arraycopy(strArr, 0, strArr2, 0, i);
            this.fEnumeration = strArr2;
        }
    }

    private static String getScannerStateName(int i) {
        return "??? (" + i + ')';
    }

    private void init() {
        this.fStartDTDCalled = false;
        this.fExtEntityDepth = 0;
        this.fIncludeSectDepth = 0;
        this.fMarkUpDepth = 0;
        this.fPEDepth = 0;
        this.fStandalone = false;
        this.fSeenExternalDTD = false;
        this.fSeenPEReferences = false;
        setScannerState(1);
    }

    private final boolean peekReportEntity() {
        return this.fPEReport[this.fPEDepth - 1];
    }

    private final int popContentStack() {
        int[] iArr = this.fContentStack;
        int i = this.fContentDepth - 1;
        this.fContentDepth = i;
        return iArr[i];
    }

    private final int popPEStack() {
        int[] iArr = this.fPEStack;
        int i = this.fPEDepth - 1;
        this.fPEDepth = i;
        return iArr[i];
    }

    private final void pushContentStack(int i) {
        int[] iArr = this.fContentStack;
        int length = iArr.length;
        int i2 = this.fContentDepth;
        if (length == i2) {
            int[] iArr2 = new int[i2 * 2];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            this.fContentStack = iArr2;
        }
        int[] iArr3 = this.fContentStack;
        int i3 = this.fContentDepth;
        this.fContentDepth = i3 + 1;
        iArr3[i3] = i;
    }

    private final void pushPEStack(int i, boolean z) {
        int[] iArr = this.fPEStack;
        int length = iArr.length;
        int i2 = this.fPEDepth;
        if (length == i2) {
            int[] iArr2 = new int[i2 * 2];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            this.fPEStack = iArr2;
            int i3 = this.fPEDepth;
            boolean[] zArr = new boolean[i3 * 2];
            System.arraycopy(this.fPEReport, 0, zArr, 0, i3);
            this.fPEReport = zArr;
        }
        boolean[] zArr2 = this.fPEReport;
        int i4 = this.fPEDepth;
        zArr2[i4] = z;
        int[] iArr3 = this.fPEStack;
        this.fPEDepth = i4 + 1;
        iArr3[i4] = i;
    }

    private final String scanAttType(String str, String str2) throws IOException, XNIException {
        int iScanChar;
        int iScanChar2;
        this.fEnumerationCount = 0;
        if (this.fEntityScanner.skipString("CDATA")) {
            return "CDATA";
        }
        if (this.fEntityScanner.skipString(SchemaSymbols.ATTVAL_IDREFS)) {
            return SchemaSymbols.ATTVAL_IDREFS;
        }
        if (this.fEntityScanner.skipString(SchemaSymbols.ATTVAL_IDREF)) {
            return SchemaSymbols.ATTVAL_IDREF;
        }
        if (this.fEntityScanner.skipString(SchemaSymbols.ATTVAL_ID)) {
            return SchemaSymbols.ATTVAL_ID;
        }
        if (this.fEntityScanner.skipString(SchemaSymbols.ATTVAL_ENTITY)) {
            return SchemaSymbols.ATTVAL_ENTITY;
        }
        if (this.fEntityScanner.skipString(SchemaSymbols.ATTVAL_ENTITIES)) {
            return SchemaSymbols.ATTVAL_ENTITIES;
        }
        if (this.fEntityScanner.skipString(SchemaSymbols.ATTVAL_NMTOKENS)) {
            return SchemaSymbols.ATTVAL_NMTOKENS;
        }
        if (this.fEntityScanner.skipString(SchemaSymbols.ATTVAL_NMTOKEN)) {
            return SchemaSymbols.ATTVAL_NMTOKEN;
        }
        if (!this.fEntityScanner.skipString(SchemaSymbols.ATTVAL_NOTATION)) {
            if (this.fEntityScanner.scanChar() != 40) {
                reportFatalError("AttTypeRequiredInAttDef", new Object[]{str, str2});
            }
            this.fMarkUpDepth++;
            do {
                skipSeparator(false, !scanningInternalSubset());
                String strScanNmtoken = this.fEntityScanner.scanNmtoken();
                if (strScanNmtoken == null) {
                    reportFatalError("MSG_NMTOKEN_REQUIRED_IN_ENUMERATION", new Object[]{str, str2});
                    iScanChar = skipInvalidEnumerationValue();
                    if (iScanChar != 124) {
                        break;
                    }
                } else {
                    ensureEnumerationSize(this.fEnumerationCount + 1);
                    String[] strArr = this.fEnumeration;
                    int i = this.fEnumerationCount;
                    this.fEnumerationCount = i + 1;
                    strArr[i] = strScanNmtoken;
                    skipSeparator(false, !scanningInternalSubset());
                    iScanChar = this.fEntityScanner.scanChar();
                }
            } while (iScanChar == 124);
            if (iScanChar != 41) {
                reportFatalError("EnumerationUnterminated", new Object[]{str, str2});
            }
            this.fMarkUpDepth--;
            return "ENUMERATION";
        }
        if (!skipSeparator(true, !scanningInternalSubset())) {
            reportFatalError("MSG_SPACE_REQUIRED_AFTER_NOTATION_IN_NOTATIONTYPE", new Object[]{str, str2});
        }
        if (this.fEntityScanner.scanChar() != 40) {
            reportFatalError("MSG_OPEN_PAREN_REQUIRED_IN_NOTATIONTYPE", new Object[]{str, str2});
        }
        this.fMarkUpDepth++;
        do {
            skipSeparator(false, !scanningInternalSubset());
            String strScanName = this.fEntityScanner.scanName();
            if (strScanName == null) {
                reportFatalError("MSG_NAME_REQUIRED_IN_NOTATIONTYPE", new Object[]{str, str2});
                iScanChar2 = skipInvalidEnumerationValue();
                if (iScanChar2 != 124) {
                    break;
                }
            } else {
                ensureEnumerationSize(this.fEnumerationCount + 1);
                String[] strArr2 = this.fEnumeration;
                int i2 = this.fEnumerationCount;
                this.fEnumerationCount = i2 + 1;
                strArr2[i2] = strScanName;
                skipSeparator(false, !scanningInternalSubset());
                iScanChar2 = this.fEntityScanner.scanChar();
            }
        } while (iScanChar2 == 124);
        if (iScanChar2 != 41) {
            reportFatalError("NotationTypeUnterminated", new Object[]{str, str2});
        }
        this.fMarkUpDepth--;
        return SchemaSymbols.ATTVAL_NOTATION;
    }

    private final void scanChildren(String str) throws IOException, XNIException {
        short s;
        int iPeekChar;
        this.fContentDepth = 0;
        pushContentStack(0);
        while (true) {
            int i = 0;
            while (!this.fEntityScanner.skipChar(40)) {
                skipSeparator(false, !scanningInternalSubset());
                String strScanName = this.fEntityScanner.scanName();
                if (strScanName == null) {
                    reportFatalError("MSG_OPEN_PAREN_OR_ELEMENT_TYPE_REQUIRED_IN_CHILDREN", new Object[]{str});
                    return;
                }
                XMLDTDContentModelHandler xMLDTDContentModelHandler = this.fDTDContentModelHandler;
                if (xMLDTDContentModelHandler != null) {
                    xMLDTDContentModelHandler.element(strScanName, null);
                }
                this.fStringBuffer.append(strScanName);
                int iPeekChar2 = this.fEntityScanner.peekChar();
                if (iPeekChar2 == 63 || iPeekChar2 == 42 || iPeekChar2 == 43) {
                    XMLDTDContentModelHandler xMLDTDContentModelHandler2 = this.fDTDContentModelHandler;
                    if (xMLDTDContentModelHandler2 != null) {
                        if (iPeekChar2 == 63) {
                            s = 2;
                        } else {
                            s = iPeekChar2 == 42 ? (short) 3 : (short) 4;
                        }
                        xMLDTDContentModelHandler2.occurrence(s, null);
                    }
                    this.fEntityScanner.scanChar();
                    this.fStringBuffer.append((char) iPeekChar2);
                }
                while (true) {
                    skipSeparator(false, !scanningInternalSubset());
                    iPeekChar = this.fEntityScanner.peekChar();
                    if (iPeekChar == 44 && i != 124) {
                        XMLDTDContentModelHandler xMLDTDContentModelHandler3 = this.fDTDContentModelHandler;
                        if (xMLDTDContentModelHandler3 != null) {
                            xMLDTDContentModelHandler3.separator((short) 1, null);
                        }
                        this.fEntityScanner.scanChar();
                        this.fStringBuffer.append(',');
                        break;
                    }
                    if (iPeekChar == 124 && i != 44) {
                        XMLDTDContentModelHandler xMLDTDContentModelHandler4 = this.fDTDContentModelHandler;
                        if (xMLDTDContentModelHandler4 != null) {
                            xMLDTDContentModelHandler4.separator((short) 0, null);
                        }
                        this.fEntityScanner.scanChar();
                        this.fStringBuffer.append('|');
                        break;
                    }
                    if (iPeekChar != 41) {
                        reportFatalError("MSG_CLOSE_PAREN_REQUIRED_IN_CHILDREN", new Object[]{str});
                    }
                    XMLDTDContentModelHandler xMLDTDContentModelHandler5 = this.fDTDContentModelHandler;
                    if (xMLDTDContentModelHandler5 != null) {
                        xMLDTDContentModelHandler5.endGroup(null);
                    }
                    int iPopContentStack = popContentStack();
                    if (this.fEntityScanner.skipString(")?")) {
                        this.fStringBuffer.append(")?");
                        XMLDTDContentModelHandler xMLDTDContentModelHandler6 = this.fDTDContentModelHandler;
                        if (xMLDTDContentModelHandler6 != null) {
                            xMLDTDContentModelHandler6.occurrence((short) 2, null);
                        }
                    } else if (this.fEntityScanner.skipString(")+")) {
                        this.fStringBuffer.append(")+");
                        XMLDTDContentModelHandler xMLDTDContentModelHandler7 = this.fDTDContentModelHandler;
                        if (xMLDTDContentModelHandler7 != null) {
                            xMLDTDContentModelHandler7.occurrence((short) 4, null);
                        }
                    } else if (this.fEntityScanner.skipString(")*")) {
                        this.fStringBuffer.append(")*");
                        XMLDTDContentModelHandler xMLDTDContentModelHandler8 = this.fDTDContentModelHandler;
                        if (xMLDTDContentModelHandler8 != null) {
                            xMLDTDContentModelHandler8.occurrence((short) 3, null);
                        }
                    } else {
                        this.fEntityScanner.scanChar();
                        this.fStringBuffer.append(')');
                    }
                    this.fMarkUpDepth--;
                    if (this.fContentDepth == 0) {
                        return;
                    } else {
                        i = iPopContentStack;
                    }
                }
                skipSeparator(false, !scanningInternalSubset());
                i = iPeekChar;
            }
            this.fMarkUpDepth++;
            this.fStringBuffer.append('(');
            XMLDTDContentModelHandler xMLDTDContentModelHandler9 = this.fDTDContentModelHandler;
            if (xMLDTDContentModelHandler9 != null) {
                xMLDTDContentModelHandler9.startGroup(null);
            }
            pushContentStack(i);
            skipSeparator(false, !scanningInternalSubset());
        }
    }

    private final void scanConditionalSect(int i) throws IOException, XNIException {
        this.fReportEntity = false;
        skipSeparator(false, !scanningInternalSubset());
        if (this.fEntityScanner.skipString("INCLUDE")) {
            skipSeparator(false, !scanningInternalSubset());
            if (i != this.fPEDepth && this.fValidation) {
                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "INVALID_PE_IN_CONDITIONAL", new Object[]{this.fEntityManager.fCurrentEntity.f472name}, (short) 1);
            }
            if (!this.fEntityScanner.skipChar(91)) {
                reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
            }
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.startConditional((short) 0, null);
            }
            this.fIncludeSectDepth++;
            this.fReportEntity = true;
            return;
        }
        if (!this.fEntityScanner.skipString("IGNORE")) {
            reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
            return;
        }
        skipSeparator(false, !scanningInternalSubset());
        if (i != this.fPEDepth && this.fValidation) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "INVALID_PE_IN_CONDITIONAL", new Object[]{this.fEntityManager.fCurrentEntity.f472name}, (short) 1);
        }
        XMLDTDHandler xMLDTDHandler2 = this.fDTDHandler;
        if (xMLDTDHandler2 != null) {
            xMLDTDHandler2.startConditional((short) 1, null);
        }
        if (!this.fEntityScanner.skipChar(91)) {
            reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
        }
        this.fReportEntity = true;
        int i2 = this.fIncludeSectDepth + 1;
        this.fIncludeSectDepth = i2;
        if (this.fDTDHandler != null) {
            this.fIgnoreConditionalBuffer.clear();
        }
        while (true) {
            if (this.fEntityScanner.skipChar(60)) {
                if (this.fDTDHandler != null) {
                    this.fIgnoreConditionalBuffer.append('<');
                }
                if (this.fEntityScanner.skipChar(33)) {
                    if (this.fEntityScanner.skipChar(91)) {
                        if (this.fDTDHandler != null) {
                            this.fIgnoreConditionalBuffer.append("![");
                        }
                        this.fIncludeSectDepth++;
                    } else if (this.fDTDHandler != null) {
                        this.fIgnoreConditionalBuffer.append("!");
                    }
                }
            } else if (this.fEntityScanner.skipChar(93)) {
                if (this.fDTDHandler != null) {
                    this.fIgnoreConditionalBuffer.append(']');
                }
                if (this.fEntityScanner.skipChar(93)) {
                    if (this.fDTDHandler != null) {
                        this.fIgnoreConditionalBuffer.append(']');
                    }
                    while (this.fEntityScanner.skipChar(93)) {
                        if (this.fDTDHandler != null) {
                            this.fIgnoreConditionalBuffer.append(']');
                        }
                    }
                    if (this.fEntityScanner.skipChar(62)) {
                        int i3 = this.fIncludeSectDepth;
                        this.fIncludeSectDepth = i3 - 1;
                        if (i3 == i2) {
                            this.fMarkUpDepth--;
                            if (this.fDTDHandler != null) {
                                this.fLiteral.setValues(this.fIgnoreConditionalBuffer.ch, 0, this.fIgnoreConditionalBuffer.length - 2);
                                this.fDTDHandler.ignoredCharacters(this.fLiteral, null);
                                this.fDTDHandler.endConditional(null);
                                return;
                            }
                            return;
                        }
                        if (this.fDTDHandler != null) {
                            this.fIgnoreConditionalBuffer.append('>');
                        }
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            } else {
                int iScanChar = this.fEntityScanner.scanChar();
                if (this.fScannerState == 0) {
                    reportFatalError("IgnoreSectUnterminated", null);
                    return;
                } else if (this.fDTDHandler != null) {
                    this.fIgnoreConditionalBuffer.append((char) iScanChar);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x005d  */
    private final void scanEntityDecl() throws IOException, XNIException {
        String str;
        boolean z;
        boolean zSkipChar;
        String str2;
        int iScanEntityValue;
        this.fReportEntity = false;
        if (!this.fEntityScanner.skipSpaces()) {
            if (scanningInternalSubset() || !this.fEntityScanner.skipChar(37)) {
                str = "MSG_SPACE_REQUIRED_BEFORE_ENTITY_NAME_IN_ENTITYDECL";
            } else if (this.fEntityScanner.skipSpaces()) {
                str = "MSG_SPACE_REQUIRED_BEFORE_PERCENT_IN_PEDECL";
            } else {
                zSkipChar = false;
                z = true;
            }
            reportFatalError(str, null);
            z = false;
            zSkipChar = false;
        } else if (this.fEntityScanner.skipChar(37)) {
            if (!skipSeparator(true, !scanningInternalSubset())) {
                if (scanningInternalSubset()) {
                    reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ENTITY_NAME_IN_PEDECL", null);
                } else if (this.fEntityScanner.peekChar() == 37) {
                    skipSeparator(false, !scanningInternalSubset());
                } else {
                    zSkipChar = false;
                    z = true;
                }
            }
            z = false;
            zSkipChar = true;
        } else {
            z = false;
            zSkipChar = false;
        }
        if (z) {
            while (true) {
                String strScanName = this.fEntityScanner.scanName();
                if (strScanName == null) {
                    reportFatalError("NameRequiredInPEReference", null);
                } else if (this.fEntityScanner.skipChar(59)) {
                    startPE(strScanName, false);
                } else {
                    reportFatalError("SemicolonRequiredInPEReference", new Object[]{strScanName});
                }
                this.fEntityScanner.skipSpaces();
                if (!this.fEntityScanner.skipChar(37)) {
                    break;
                }
                if (!zSkipChar) {
                    if (skipSeparator(true, !scanningInternalSubset())) {
                        zSkipChar = true;
                        break;
                    }
                    zSkipChar = this.fEntityScanner.skipChar(37);
                }
            }
        }
        String strScanNCName = this.fNamespaces ? this.fEntityScanner.scanNCName() : this.fEntityScanner.scanName();
        if (strScanNCName == null) {
            reportFatalError("MSG_ENTITY_NAME_REQUIRED_IN_ENTITYDECL", null);
        }
        if (!skipSeparator(true, !scanningInternalSubset())) {
            if (this.fNamespaces && this.fEntityScanner.peekChar() == 58) {
                this.fEntityScanner.scanChar();
                XMLStringBuffer xMLStringBuffer = new XMLStringBuffer(strScanNCName);
                xMLStringBuffer.append(':');
                String strScanName2 = this.fEntityScanner.scanName();
                if (strScanName2 != null) {
                    xMLStringBuffer.append(strScanName2);
                }
                reportFatalError("ColonNotLegalWithNS", new Object[]{xMLStringBuffer.toString()});
                if (!skipSeparator(true, !scanningInternalSubset())) {
                    reportFatalError("MSG_SPACE_REQUIRED_AFTER_ENTITY_NAME_IN_ENTITYDECL", new Object[]{strScanNCName});
                }
            } else {
                reportFatalError("MSG_SPACE_REQUIRED_AFTER_ENTITY_NAME_IN_ENTITYDECL", new Object[]{strScanNCName});
            }
        }
        scanExternalID(this.fStrings, false);
        String[] strArr = this.fStrings;
        String str3 = strArr[0];
        String str4 = strArr[1];
        boolean zSkipSeparator = skipSeparator(true, !scanningInternalSubset());
        if (zSkipChar || !this.fEntityScanner.skipString("NDATA")) {
            str2 = null;
        } else {
            if (!zSkipSeparator) {
                reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NDATA_IN_UNPARSED_ENTITYDECL", new Object[]{strScanNCName});
            }
            if (!skipSeparator(true, !scanningInternalSubset())) {
                reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NOTATION_NAME_IN_UNPARSED_ENTITYDECL", new Object[]{strScanNCName});
            }
            String strScanName3 = this.fEntityScanner.scanName();
            if (strScanName3 == null) {
                reportFatalError("MSG_NOTATION_NAME_REQUIRED_FOR_UNPARSED_ENTITYDECL", new Object[]{strScanNCName});
            }
            str2 = strScanName3;
        }
        if (str3 == null) {
            iScanEntityValue = scanEntityValue(this.fLiteral, this.fLiteral2);
            this.fStringBuffer.clear();
            this.fStringBuffer2.clear();
            this.fStringBuffer.append(this.fLiteral.ch, this.fLiteral.offset, this.fLiteral.length);
            this.fStringBuffer2.append(this.fLiteral2.ch, this.fLiteral2.offset, this.fLiteral2.length);
        } else {
            iScanEntityValue = 0;
        }
        skipSeparator(false, !scanningInternalSubset());
        if (!this.fEntityScanner.skipChar(62)) {
            reportFatalError("EntityDeclUnterminated", new Object[]{strScanNCName});
        }
        this.fMarkUpDepth--;
        if (zSkipChar) {
            strScanNCName = "%" + strScanNCName;
        }
        if (str3 != null) {
            String baseSystemId = this.fEntityScanner.getBaseSystemId();
            XMLEntityManager xMLEntityManager = this.fEntityManager;
            if (str2 != null) {
                xMLEntityManager.addUnparsedEntity(strScanNCName, str4, str3, baseSystemId, str2);
            } else {
                xMLEntityManager.addExternalEntity(strScanNCName, str4, str3, baseSystemId);
            }
            if (this.fDTDHandler != null) {
                this.fResourceIdentifier.setValues(str4, str3, baseSystemId, XMLEntityManager.expandSystemId(str3, baseSystemId, false));
                XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
                XMLResourceIdentifierImpl xMLResourceIdentifierImpl = this.fResourceIdentifier;
                if (str2 != null) {
                    xMLDTDHandler.unparsedEntityDecl(strScanNCName, xMLResourceIdentifierImpl, str2, null);
                } else {
                    xMLDTDHandler.externalEntityDecl(strScanNCName, xMLResourceIdentifierImpl, null);
                }
            }
        } else {
            this.fEntityManager.addInternalEntity(strScanNCName, this.fStringBuffer.toString(), iScanEntityValue);
            XMLDTDHandler xMLDTDHandler2 = this.fDTDHandler;
            if (xMLDTDHandler2 != null) {
                xMLDTDHandler2.internalEntityDecl(strScanNCName, this.fStringBuffer, this.fStringBuffer2, null);
            }
        }
        this.fReportEntity = true;
    }

    private final void scanMixed(String str) throws IOException, XNIException {
        this.fStringBuffer.append("#PCDATA");
        XMLDTDContentModelHandler xMLDTDContentModelHandler = this.fDTDContentModelHandler;
        if (xMLDTDContentModelHandler != null) {
            xMLDTDContentModelHandler.pcdata(null);
        }
        skipSeparator(false, !scanningInternalSubset());
        String strScanName = null;
        while (this.fEntityScanner.skipChar(124)) {
            this.fStringBuffer.append('|');
            XMLDTDContentModelHandler xMLDTDContentModelHandler2 = this.fDTDContentModelHandler;
            if (xMLDTDContentModelHandler2 != null) {
                xMLDTDContentModelHandler2.separator((short) 0, null);
            }
            skipSeparator(false, !scanningInternalSubset());
            strScanName = this.fEntityScanner.scanName();
            if (strScanName == null) {
                reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_MIXED_CONTENT", new Object[]{str});
            }
            this.fStringBuffer.append(strScanName);
            XMLDTDContentModelHandler xMLDTDContentModelHandler3 = this.fDTDContentModelHandler;
            if (xMLDTDContentModelHandler3 != null) {
                xMLDTDContentModelHandler3.element(strScanName, null);
            }
            skipSeparator(false, !scanningInternalSubset());
        }
        if (this.fEntityScanner.skipString(")*")) {
            this.fStringBuffer.append(")*");
            XMLDTDContentModelHandler xMLDTDContentModelHandler4 = this.fDTDContentModelHandler;
            if (xMLDTDContentModelHandler4 != null) {
                xMLDTDContentModelHandler4.endGroup(null);
                this.fDTDContentModelHandler.occurrence((short) 3, null);
            }
        } else if (strScanName != null) {
            reportFatalError("MixedContentUnterminated", new Object[]{str});
        } else if (this.fEntityScanner.skipChar(41)) {
            this.fStringBuffer.append(')');
            XMLDTDContentModelHandler xMLDTDContentModelHandler5 = this.fDTDContentModelHandler;
            if (xMLDTDContentModelHandler5 != null) {
                xMLDTDContentModelHandler5.endGroup(null);
            }
        } else {
            reportFatalError("MSG_CLOSE_PAREN_REQUIRED_IN_CHILDREN", new Object[]{str});
        }
        this.fMarkUpDepth--;
    }

    private final void scanNotationDecl() throws IOException, XNIException {
        this.fReportEntity = false;
        if (!skipSeparator(true, !scanningInternalSubset())) {
            reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NOTATION_NAME_IN_NOTATIONDECL", null);
        }
        String strScanNCName = this.fNamespaces ? this.fEntityScanner.scanNCName() : this.fEntityScanner.scanName();
        if (strScanNCName == null) {
            reportFatalError("MSG_NOTATION_NAME_REQUIRED_IN_NOTATIONDECL", null);
        }
        if (!skipSeparator(true, !scanningInternalSubset())) {
            if (this.fNamespaces && this.fEntityScanner.peekChar() == 58) {
                this.fEntityScanner.scanChar();
                XMLStringBuffer xMLStringBuffer = new XMLStringBuffer(strScanNCName);
                xMLStringBuffer.append(':');
                xMLStringBuffer.append(this.fEntityScanner.scanName());
                reportFatalError("ColonNotLegalWithNS", new Object[]{xMLStringBuffer.toString()});
                skipSeparator(true, !scanningInternalSubset());
            } else {
                reportFatalError("MSG_SPACE_REQUIRED_AFTER_NOTATION_NAME_IN_NOTATIONDECL", new Object[]{strScanNCName});
            }
        }
        scanExternalID(this.fStrings, true);
        String[] strArr = this.fStrings;
        String str = strArr[0];
        String str2 = strArr[1];
        String baseSystemId = this.fEntityScanner.getBaseSystemId();
        if (str == null && str2 == null) {
            reportFatalError("ExternalIDorPublicIDRequired", new Object[]{strScanNCName});
        }
        skipSeparator(false, !scanningInternalSubset());
        if (!this.fEntityScanner.skipChar(62)) {
            reportFatalError("NotationDeclUnterminated", new Object[]{strScanNCName});
        }
        this.fMarkUpDepth--;
        if (this.fDTDHandler != null) {
            this.fResourceIdentifier.setValues(str2, str, baseSystemId, XMLEntityManager.expandSystemId(str, baseSystemId, false));
            this.fDTDHandler.notationDecl(strScanNCName, this.fResourceIdentifier, null);
        }
        this.fReportEntity = true;
    }

    private int skipInvalidEnumerationValue() throws IOException {
        int iScanChar;
        do {
            iScanChar = this.fEntityScanner.scanChar();
            if (iScanChar == 124) {
                break;
            }
        } while (iScanChar != 41);
        ensureEnumerationSize(this.fEnumerationCount + 1);
        String[] strArr = this.fEnumeration;
        int i = this.fEnumerationCount;
        this.fEnumerationCount = i + 1;
        strArr[i] = XMLSymbols.EMPTY_STRING;
        return iScanChar;
    }

    private boolean skipSeparator(boolean z, boolean z2) throws IOException, XNIException {
        int i = this.fPEDepth;
        boolean zSkipSpaces = this.fEntityScanner.skipSpaces();
        if (!z2 || !this.fEntityScanner.skipChar(37)) {
            return (z && !zSkipSpaces && i == this.fPEDepth) ? false : true;
        }
        do {
            String strScanName = this.fEntityScanner.scanName();
            if (strScanName == null) {
                reportFatalError("NameRequiredInPEReference", null);
            } else if (!this.fEntityScanner.skipChar(59)) {
                reportFatalError("SemicolonRequiredInPEReference", new Object[]{strScanName});
            }
            startPE(strScanName, false);
            this.fEntityScanner.skipSpaces();
        } while (this.fEntityScanner.skipChar(37));
        return true;
    }

    @Override // org.apache.xerces.impl.XMLScanner, org.apache.xerces.impl.XMLEntityHandler
    public void endEntity(String str, Augmentations augmentations) throws XNIException {
        super.endEntity(str, augmentations);
        if (this.fScannerState == 0) {
            return;
        }
        boolean z = this.fReportEntity;
        boolean z2 = false;
        if (!str.startsWith("%")) {
            if (str.equals("[dtd]")) {
                if (this.fIncludeSectDepth != 0) {
                    reportFatalError("IncludeSectUnterminated", null);
                }
                this.fScannerState = 0;
                this.fEntityManager.endExternalSubset();
                XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
                if (xMLDTDHandler != null) {
                    xMLDTDHandler.endExternalSubset(null);
                    this.fDTDHandler.endDTD(null);
                }
                this.fExtEntityDepth--;
                return;
            }
            return;
        }
        boolean zPeekReportEntity = peekReportEntity();
        int iPopPEStack = popPEStack();
        if (iPopPEStack == 0 && iPopPEStack < this.fMarkUpDepth) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "ILL_FORMED_PARAMETER_ENTITY_WHEN_USED_IN_DECL", new Object[]{this.fEntityManager.fCurrentEntity.f472name}, (short) 2);
        }
        if (iPopPEStack == this.fMarkUpDepth) {
            z2 = zPeekReportEntity;
        } else if (this.fValidation) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "ImproperDeclarationNesting", new Object[]{str}, (short) 1);
        }
        if (this.fEntityScanner.isExternal()) {
            this.fExtEntityDepth--;
        }
        XMLDTDHandler xMLDTDHandler2 = this.fDTDHandler;
        if (xMLDTDHandler2 == null || !z2) {
            return;
        }
        xMLDTDHandler2.endParameterEntity(str, augmentations);
    }

    @Override // org.apache.xerces.xni.parser.XMLDTDContentModelSource
    public XMLDTDContentModelHandler getDTDContentModelHandler() {
        return this.fDTDContentModelHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLDTDSource
    public XMLDTDHandler getDTDHandler() {
        return this.fDTDHandler;
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

    @Override // org.apache.xerces.impl.XMLScanner
    public void reset() {
        super.reset();
        init();
    }

    @Override // org.apache.xerces.impl.XMLScanner, org.apache.xerces.xni.parser.XMLComponent
    public void reset(XMLComponentManager xMLComponentManager) throws XMLConfigurationException {
        super.reset(xMLComponentManager);
        init();
    }

    protected final String scanAttDefaultDecl(String str, String str2, String str3, XMLString xMLString, XMLString xMLString2) throws IOException, XNIException {
        this.fString.clear();
        xMLString.clear();
        String str4 = "#REQUIRED";
        if (!this.fEntityScanner.skipString("#REQUIRED")) {
            str4 = "#IMPLIED";
            if (!this.fEntityScanner.skipString("#IMPLIED")) {
                str4 = "#FIXED";
                boolean z = true;
                if (!this.fEntityScanner.skipString("#FIXED")) {
                    str4 = null;
                } else if (!skipSeparator(true, !scanningInternalSubset())) {
                    reportFatalError("MSG_SPACE_REQUIRED_AFTER_FIXED_IN_DEFAULTDECL", new Object[]{str, str2});
                }
                if (this.fStandalone || (!this.fSeenExternalDTD && !this.fSeenPEReferences)) {
                    z = false;
                }
                scanAttributeValue(xMLString, xMLString2, str2, z, str);
            }
        }
        return str4;
    }

    protected final void scanAttlistDecl() throws IOException, XNIException {
        String str;
        String[] strArr;
        this.fReportEntity = false;
        if (!skipSeparator(true, !scanningInternalSubset())) {
            reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ELEMENT_TYPE_IN_ATTLISTDECL", null);
        }
        String strScanName = this.fEntityScanner.scanName();
        if (strScanName == null) {
            reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_ATTLISTDECL", null);
        }
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.startAttlist(strScanName, null);
        }
        int i = 62;
        if (!skipSeparator(true, !scanningInternalSubset())) {
            if (this.fEntityScanner.skipChar(62)) {
                XMLDTDHandler xMLDTDHandler2 = this.fDTDHandler;
                if (xMLDTDHandler2 != null) {
                    xMLDTDHandler2.endAttlist(null);
                }
                this.fMarkUpDepth--;
                return;
            }
            reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ATTRIBUTE_NAME_IN_ATTDEF", new Object[]{strScanName});
        }
        while (!this.fEntityScanner.skipChar(i)) {
            String strScanName2 = this.fEntityScanner.scanName();
            if (strScanName2 == null) {
                reportFatalError("AttNameRequiredInAttDef", new Object[]{strScanName});
            }
            if (!skipSeparator(true, !scanningInternalSubset())) {
                reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ATTTYPE_IN_ATTDEF", new Object[]{strScanName, strScanName2});
            }
            String strScanAttType = scanAttType(strScanName, strScanName2);
            if (!skipSeparator(true, !scanningInternalSubset())) {
                reportFatalError("MSG_SPACE_REQUIRED_BEFORE_DEFAULTDECL_IN_ATTDEF", new Object[]{strScanName, strScanName2});
            }
            String strScanAttDefaultDecl = scanAttDefaultDecl(strScanName, strScanName2, strScanAttType, this.fLiteral, this.fLiteral2);
            if (this.fDTDHandler != null) {
                int i2 = this.fEnumerationCount;
                if (i2 != 0) {
                    strArr = new String[i2];
                    System.arraycopy(this.fEnumeration, 0, strArr, 0, i2);
                } else {
                    strArr = null;
                }
                if (strScanAttDefaultDecl == null || !(strScanAttDefaultDecl.equals("#REQUIRED") || strScanAttDefaultDecl.equals("#IMPLIED"))) {
                    str = strScanName;
                    this.fDTDHandler.attributeDecl(str, strScanName2, strScanAttType, strArr, strScanAttDefaultDecl, this.fLiteral, this.fLiteral2, null);
                } else {
                    str = strScanName;
                    this.fDTDHandler.attributeDecl(strScanName, strScanName2, strScanAttType, strArr, strScanAttDefaultDecl, null, null, null);
                }
            } else {
                str = strScanName;
            }
            skipSeparator(false, !scanningInternalSubset());
            i = i;
            strScanName = str;
        }
        XMLDTDHandler xMLDTDHandler3 = this.fDTDHandler;
        if (xMLDTDHandler3 != null) {
            xMLDTDHandler3.endAttlist(null);
        }
        this.fMarkUpDepth--;
        this.fReportEntity = true;
    }

    protected final void scanComment() throws IOException, XNIException {
        this.fReportEntity = false;
        scanComment(this.fStringBuffer);
        this.fMarkUpDepth--;
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.comment(this.fStringBuffer, null);
        }
        this.fReportEntity = true;
    }

    @Override // org.apache.xerces.xni.parser.XMLDTDScanner
    public boolean scanDTDExternalSubset(boolean z) throws IOException, XNIException {
        this.fEntityManager.setEntityHandler(this);
        if (this.fScannerState == 1) {
            this.fSeenExternalDTD = true;
            boolean zScanTextDecl = scanTextDecl();
            if (this.fScannerState == 0) {
                return false;
            }
            setScannerState(2);
            if (zScanTextDecl && !z) {
                return true;
            }
        }
        while (scanDecls(z)) {
            if (!z) {
                return true;
            }
        }
        return false;
    }

    @Override // org.apache.xerces.xni.parser.XMLDTDScanner
    public boolean scanDTDInternalSubset(boolean z, boolean z2, boolean z3) throws IOException, XNIException {
        this.fEntityScanner = this.fEntityManager.getEntityScanner();
        this.fEntityManager.setEntityHandler(this);
        this.fStandalone = z2;
        if (this.fScannerState == 1) {
            XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
            if (xMLDTDHandler != null) {
                xMLDTDHandler.startDTD(this.fEntityScanner, null);
                this.fStartDTDCalled = true;
            }
            setScannerState(2);
        }
        while (scanDecls(z)) {
            if (!z) {
                return true;
            }
        }
        XMLDTDHandler xMLDTDHandler2 = this.fDTDHandler;
        if (xMLDTDHandler2 != null && !z3) {
            xMLDTDHandler2.endDTD(null);
        }
        setScannerState(1);
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00a3  */
    protected final boolean scanDecls(boolean z) throws IOException, XNIException {
        int iPeekChar;
        skipSeparator(false, true);
        boolean z2 = true;
        while (z2 && this.fScannerState == 2) {
            if (this.fEntityScanner.skipChar(60)) {
                this.fMarkUpDepth++;
                if (this.fEntityScanner.skipChar(63)) {
                    scanPI();
                } else if (!this.fEntityScanner.skipChar(33)) {
                    this.fMarkUpDepth--;
                    reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
                } else if (this.fEntityScanner.skipChar(45)) {
                    if (this.fEntityScanner.skipChar(45)) {
                        scanComment();
                    } else {
                        reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
                    }
                } else if (this.fEntityScanner.skipString("ELEMENT")) {
                    scanElementDecl();
                } else if (this.fEntityScanner.skipString("ATTLIST")) {
                    scanAttlistDecl();
                } else if (this.fEntityScanner.skipString(SchemaSymbols.ATTVAL_ENTITY)) {
                    scanEntityDecl();
                } else if (this.fEntityScanner.skipString(SchemaSymbols.ATTVAL_NOTATION)) {
                    scanNotationDecl();
                } else if (!this.fEntityScanner.skipChar(91) || scanningInternalSubset()) {
                    this.fMarkUpDepth--;
                    reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
                } else {
                    scanConditionalSect(this.fPEDepth);
                }
            } else if (this.fIncludeSectDepth > 0 && this.fEntityScanner.skipChar(93)) {
                if (!this.fEntityScanner.skipChar(93) || !this.fEntityScanner.skipChar(62)) {
                    reportFatalError("IncludeSectUnterminated", null);
                }
                XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
                if (xMLDTDHandler != null) {
                    xMLDTDHandler.endConditional(null);
                }
                this.fIncludeSectDepth--;
                this.fMarkUpDepth--;
            } else {
                if (scanningInternalSubset() && this.fEntityScanner.peekChar() == 93) {
                    return false;
                }
                if (!this.fEntityScanner.skipSpaces()) {
                    reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
                    do {
                        this.fEntityScanner.scanChar();
                        skipSeparator(false, true);
                        iPeekChar = this.fEntityScanner.peekChar();
                        if (iPeekChar == 60 || iPeekChar == 93) {
                            break;
                        }
                    } while (!XMLChar.isSpace(iPeekChar));
                }
            }
            skipSeparator(false, true);
            z2 = z;
        }
        return this.fScannerState != 0;
    }

    protected final void scanElementDecl() throws IOException, XNIException {
        this.fReportEntity = false;
        if (!skipSeparator(true, !scanningInternalSubset())) {
            reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ELEMENT_TYPE_IN_ELEMENTDECL", null);
        }
        String strScanName = this.fEntityScanner.scanName();
        if (strScanName == null) {
            reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_ELEMENTDECL", null);
        }
        if (!skipSeparator(true, !scanningInternalSubset())) {
            reportFatalError("MSG_SPACE_REQUIRED_BEFORE_CONTENTSPEC_IN_ELEMENTDECL", new Object[]{strScanName});
        }
        XMLDTDContentModelHandler xMLDTDContentModelHandler = this.fDTDContentModelHandler;
        if (xMLDTDContentModelHandler != null) {
            xMLDTDContentModelHandler.startContentModel(strScanName, null);
        }
        this.fReportEntity = true;
        String string = "EMPTY";
        if (this.fEntityScanner.skipString("EMPTY")) {
            XMLDTDContentModelHandler xMLDTDContentModelHandler2 = this.fDTDContentModelHandler;
            if (xMLDTDContentModelHandler2 != null) {
                xMLDTDContentModelHandler2.empty(null);
            }
        } else {
            string = "ANY";
            if (this.fEntityScanner.skipString("ANY")) {
                XMLDTDContentModelHandler xMLDTDContentModelHandler3 = this.fDTDContentModelHandler;
                if (xMLDTDContentModelHandler3 != null) {
                    xMLDTDContentModelHandler3.any(null);
                }
            } else {
                if (!this.fEntityScanner.skipChar(40)) {
                    reportFatalError("MSG_OPEN_PAREN_OR_ELEMENT_TYPE_REQUIRED_IN_CHILDREN", new Object[]{strScanName});
                }
                XMLDTDContentModelHandler xMLDTDContentModelHandler4 = this.fDTDContentModelHandler;
                if (xMLDTDContentModelHandler4 != null) {
                    xMLDTDContentModelHandler4.startGroup(null);
                }
                this.fStringBuffer.clear();
                this.fStringBuffer.append('(');
                this.fMarkUpDepth++;
                skipSeparator(false, !scanningInternalSubset());
                if (this.fEntityScanner.skipString("#PCDATA")) {
                    scanMixed(strScanName);
                } else {
                    scanChildren(strScanName);
                }
                string = this.fStringBuffer.toString();
            }
        }
        XMLDTDContentModelHandler xMLDTDContentModelHandler5 = this.fDTDContentModelHandler;
        if (xMLDTDContentModelHandler5 != null) {
            xMLDTDContentModelHandler5.endContentModel(null);
        }
        this.fReportEntity = false;
        skipSeparator(false, !scanningInternalSubset());
        if (!this.fEntityScanner.skipChar(62)) {
            reportFatalError("ElementDeclUnterminated", new Object[]{strScanName});
        }
        this.fReportEntity = true;
        this.fMarkUpDepth--;
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.elementDecl(strScanName, string, null);
        }
    }

    protected final int scanEntityValue(XMLString xMLString, XMLString xMLString2) throws IOException, XNIException {
        XMLString xMLString3;
        int iScanChar = this.fEntityScanner.scanChar();
        if (iScanChar != 39 && iScanChar != 34) {
            reportFatalError("OpenQuoteMissingInDecl", null);
        }
        int i = this.fEntityDepth;
        XMLString xMLString4 = this.fString;
        int paramEntityRefCount = 0;
        if (this.fEntityScanner.scanLiteral(iScanChar, this.fString) != iScanChar) {
            this.fStringBuffer.clear();
            this.fStringBuffer2.clear();
            do {
                this.fStringBuffer.append(this.fString);
                this.fStringBuffer2.append(this.fString);
                if (this.fEntityScanner.skipChar(38)) {
                    if (this.fEntityScanner.skipChar(35)) {
                        this.fStringBuffer2.append("&#");
                        scanCharReferenceValue(this.fStringBuffer, this.fStringBuffer2);
                    } else {
                        this.fStringBuffer.append(Typography.amp);
                        this.fStringBuffer2.append(Typography.amp);
                        String strScanName = this.fEntityScanner.scanName();
                        if (strScanName == null) {
                            reportFatalError("NameRequiredInReference", null);
                        } else {
                            this.fStringBuffer.append(strScanName);
                            this.fStringBuffer2.append(strScanName);
                        }
                        if (this.fEntityScanner.skipChar(59)) {
                            this.fStringBuffer.append(';');
                            this.fStringBuffer2.append(';');
                        } else {
                            reportFatalError("SemicolonRequiredInReference", new Object[]{strScanName});
                        }
                    }
                } else if (this.fEntityScanner.skipChar(37)) {
                    do {
                        this.fStringBuffer2.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                        String strScanName2 = this.fEntityScanner.scanName();
                        if (strScanName2 == null) {
                            reportFatalError("NameRequiredInPEReference", null);
                        } else if (this.fEntityScanner.skipChar(59)) {
                            if (scanningInternalSubset()) {
                                reportFatalError("PEReferenceWithinMarkup", new Object[]{strScanName2});
                            }
                            this.fStringBuffer2.append(strScanName2);
                            this.fStringBuffer2.append(';');
                        } else {
                            reportFatalError("SemicolonRequiredInPEReference", new Object[]{strScanName2});
                        }
                        paramEntityRefCount += this.fEntityManager.getParamEntityRefCount(startPE(strScanName2, true)) + 1;
                        this.fEntityScanner.skipSpaces();
                    } while (this.fEntityScanner.skipChar(37));
                } else {
                    int iPeekChar = this.fEntityScanner.peekChar();
                    if (XMLChar.isHighSurrogate(iPeekChar)) {
                        scanSurrogates(this.fStringBuffer2);
                    } else {
                        if (isInvalidLiteral(iPeekChar)) {
                            reportFatalError("InvalidCharInLiteral", new Object[]{Integer.toHexString(iPeekChar)});
                        } else if (iPeekChar != iScanChar || i != this.fEntityDepth) {
                            char c = (char) iPeekChar;
                            this.fStringBuffer.append(c);
                            this.fStringBuffer2.append(c);
                        }
                        this.fEntityScanner.scanChar();
                    }
                }
            } while (this.fEntityScanner.scanLiteral(iScanChar, this.fString) != iScanChar);
            this.fStringBuffer.append(this.fString);
            this.fStringBuffer2.append(this.fString);
            xMLString4 = this.fStringBuffer;
            xMLString3 = this.fStringBuffer2;
        } else {
            xMLString3 = xMLString4;
        }
        xMLString.setValues(xMLString4);
        xMLString2.setValues(xMLString3);
        if (!this.fEntityScanner.skipChar(iScanChar)) {
            reportFatalError("CloseQuoteMissingInDecl", null);
        }
        return paramEntityRefCount;
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected final void scanPIData(String str, XMLString xMLString) throws IOException, XNIException {
        super.scanPIData(str, xMLString);
        this.fMarkUpDepth--;
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.processingInstruction(str, xMLString, null);
        }
    }

    protected final boolean scanTextDecl() throws IOException, XNIException {
        boolean z = false;
        if (this.fEntityScanner.skipString("<?xml")) {
            this.fMarkUpDepth++;
            if (isValidNameChar(this.fEntityScanner.peekChar())) {
                this.fStringBuffer.clear();
                this.fStringBuffer.append("xml");
                if (this.fNamespaces) {
                    while (isValidNCName(this.fEntityScanner.peekChar())) {
                        this.fStringBuffer.append((char) this.fEntityScanner.scanChar());
                    }
                } else {
                    while (isValidNameChar(this.fEntityScanner.peekChar())) {
                        this.fStringBuffer.append((char) this.fEntityScanner.scanChar());
                    }
                }
                scanPIData(this.fSymbolTable.addSymbol(this.fStringBuffer.ch, this.fStringBuffer.offset, this.fStringBuffer.length), this.fString);
            } else {
                scanXMLDeclOrTextDecl(true, this.fStrings);
                this.fMarkUpDepth--;
                String[] strArr = this.fStrings;
                String str = strArr[0];
                String str2 = strArr[1];
                this.fEntityScanner.setXMLVersion(str);
                if (!this.fEntityScanner.fCurrentEntity.isEncodingExternallySpecified()) {
                    this.fEntityScanner.setEncoding(str2);
                }
                XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
                if (xMLDTDHandler != null) {
                    xMLDTDHandler.textDecl(str, str2, null);
                }
                z = true;
            }
        }
        this.fEntityManager.fCurrentEntity.mayReadChunks = true;
        return z;
    }

    protected final boolean scanningInternalSubset() {
        return this.fExtEntityDepth == 0;
    }

    @Override // org.apache.xerces.xni.parser.XMLDTDContentModelSource
    public void setDTDContentModelHandler(XMLDTDContentModelHandler xMLDTDContentModelHandler) {
        this.fDTDContentModelHandler = xMLDTDContentModelHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLDTDSource
    public void setDTDHandler(XMLDTDHandler xMLDTDHandler) {
        this.fDTDHandler = xMLDTDHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLDTDScanner
    public void setInputSource(XMLInputSource xMLInputSource) throws IOException {
        if (xMLInputSource != null) {
            this.fEntityManager.setEntityHandler(this);
            this.fEntityManager.startDTDEntity(xMLInputSource);
            return;
        }
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.startDTD(null, null);
            this.fDTDHandler.endDTD(null);
        }
    }

    protected final void setScannerState(int i) {
        this.fScannerState = i;
    }

    @Override // org.apache.xerces.impl.XMLScanner, org.apache.xerces.impl.XMLEntityHandler
    public void startEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        super.startEntity(str, xMLResourceIdentifier, str2, augmentations);
        boolean zEquals = str.equals("[dtd]");
        if (!zEquals) {
            if (str.charAt(0) == '%') {
                pushPEStack(this.fMarkUpDepth, this.fReportEntity);
                if (this.fEntityScanner.isExternal()) {
                }
            }
            if (this.fDTDHandler == null && !zEquals && this.fReportEntity) {
                this.fDTDHandler.startParameterEntity(str, xMLResourceIdentifier, str2, augmentations);
                return;
            }
            return;
        }
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null && !this.fStartDTDCalled) {
            xMLDTDHandler.startDTD(this.fEntityScanner, null);
        }
        XMLDTDHandler xMLDTDHandler2 = this.fDTDHandler;
        if (xMLDTDHandler2 != null) {
            xMLDTDHandler2.startExternalSubset(xMLResourceIdentifier, null);
        }
        this.fEntityManager.startExternalSubset();
        this.fExtEntityDepth++;
        if (this.fDTDHandler == null) {
        }
    }

    protected String startPE(String str, boolean z) throws IOException, XNIException {
        int i = this.fPEDepth;
        String str2 = "%" + str;
        if (!this.fSeenPEReferences) {
            this.fSeenPEReferences = true;
            this.fEntityManager.notifyHasPEReferences();
        }
        if (this.fValidation && !this.fEntityManager.isDeclaredEntity(str2)) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityNotDeclared", new Object[]{str}, (short) 1);
        }
        this.fEntityManager.startEntity(this.fSymbolTable.addSymbol(str2), z);
        if (i != this.fPEDepth && this.fEntityScanner.isExternal()) {
            scanTextDecl();
        }
        return str2;
    }
}
