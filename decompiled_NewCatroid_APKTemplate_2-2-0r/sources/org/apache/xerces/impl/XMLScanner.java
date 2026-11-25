package org.apache.xerces.impl;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import javax.xml.transform.OutputKeys;
import kotlin.text.Typography;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.catrobat.catroid.BuildConfig;

/* JADX INFO: loaded from: classes13.dex */
public abstract class XMLScanner implements XMLComponent {
    protected static final boolean DEBUG_ATTR_NORMALIZATION = false;
    protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
    protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
    protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
    protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    protected static final String VALIDATION = "http://xml.org/sax/features/validation";
    protected int fEntityDepth;
    protected XMLEntityManager fEntityManager;
    protected XMLEntityScanner fEntityScanner;
    protected XMLErrorReporter fErrorReporter;
    protected boolean fNamespaces;
    protected boolean fReportEntity;
    protected boolean fScanningAttribute;
    protected SymbolTable fSymbolTable;
    protected static final String fVersionSymbol = "version".intern();
    protected static final String fEncodingSymbol = "encoding".intern();
    protected static final String fStandaloneSymbol = OutputKeys.STANDALONE.intern();
    protected static final String fAmpSymbol = "amp".intern();
    protected static final String fLtSymbol = "lt".intern();
    protected static final String fGtSymbol = "gt".intern();
    protected static final String fQuotSymbol = "quot".intern();
    protected static final String fAposSymbol = "apos".intern();
    protected boolean fValidation = false;
    protected boolean fNotifyCharRefs = false;
    protected boolean fParserSettings = true;
    protected String fCharRefLiteral = null;
    private final XMLString fString = new XMLString();
    private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
    private final XMLStringBuffer fStringBuffer2 = new XMLStringBuffer();
    private final XMLStringBuffer fStringBuffer3 = new XMLStringBuffer();
    protected final XMLResourceIdentifierImpl fResourceIdentifier = new XMLResourceIdentifierImpl();

    private void init() {
        this.fEntityScanner = null;
        this.fEntityDepth = 0;
        this.fReportEntity = true;
        this.fResourceIdentifier.clear();
    }

    private String scanPseudoAttributeName() throws IOException, XNIException {
        int iPeekChar = this.fEntityScanner.peekChar();
        if (iPeekChar == 101) {
            XMLEntityScanner xMLEntityScanner = this.fEntityScanner;
            String str = fEncodingSymbol;
            if (xMLEntityScanner.skipString(str)) {
                return str;
            }
            return null;
        }
        if (iPeekChar == 115) {
            XMLEntityScanner xMLEntityScanner2 = this.fEntityScanner;
            String str2 = fStandaloneSymbol;
            if (xMLEntityScanner2.skipString(str2)) {
                return str2;
            }
            return null;
        }
        if (iPeekChar != 118) {
            return null;
        }
        XMLEntityScanner xMLEntityScanner3 = this.fEntityScanner;
        String str3 = fVersionSymbol;
        if (xMLEntityScanner3.skipString(str3)) {
            return str3;
        }
        return null;
    }

    public void endEntity(String str, Augmentations augmentations) throws XNIException {
        this.fEntityDepth--;
    }

    public boolean getFeature(String str) throws XMLConfigurationException {
        if (VALIDATION.equals(str)) {
            return this.fValidation;
        }
        if (NOTIFY_CHAR_REFS.equals(str)) {
            return this.fNotifyCharRefs;
        }
        throw new XMLConfigurationException((short) 0, str);
    }

    protected String getVersionNotSupportedKey() {
        return "VersionNotSupported";
    }

    protected boolean isInvalid(int i) {
        return XMLChar.isInvalid(i);
    }

    protected boolean isInvalidLiteral(int i) {
        return XMLChar.isInvalid(i);
    }

    protected int isUnchangedByNormalization(XMLString xMLString) {
        int i = xMLString.offset + xMLString.length;
        for (int i2 = xMLString.offset; i2 < i; i2++) {
            if (xMLString.ch[i2] < ' ') {
                return i2 - xMLString.offset;
            }
        }
        return -1;
    }

    protected boolean isValidNCName(int i) {
        return XMLChar.isNCName(i);
    }

    protected boolean isValidNameChar(int i) {
        return XMLChar.isName(i);
    }

    protected boolean isValidNameStartChar(int i) {
        return XMLChar.isNameStart(i);
    }

    protected boolean isValidNameStartHighSurrogate(int i) {
        return false;
    }

    protected void normalizeWhitespace(XMLString xMLString) {
        int i = xMLString.offset + xMLString.length;
        for (int i2 = xMLString.offset; i2 < i; i2++) {
            if (xMLString.ch[i2] < ' ') {
                xMLString.ch[i2] = StringUtil.SPACE;
            }
        }
    }

    protected void normalizeWhitespace(XMLString xMLString, int i) {
        int i2 = xMLString.offset + xMLString.length;
        for (int i3 = xMLString.offset + i; i3 < i2; i3++) {
            if (xMLString.ch[i3] < ' ') {
                xMLString.ch[i3] = StringUtil.SPACE;
            }
        }
    }

    protected void reportFatalError(String str, Object[] objArr) throws XNIException {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", str, objArr, (short) 2);
    }

    protected void reset() {
        init();
        this.fValidation = true;
        this.fNotifyCharRefs = false;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void reset(XMLComponentManager xMLComponentManager) throws XMLConfigurationException {
        try {
            this.fParserSettings = xMLComponentManager.getFeature(PARSER_SETTINGS);
        } catch (XMLConfigurationException unused) {
            this.fParserSettings = true;
        }
        if (!this.fParserSettings) {
            init();
            return;
        }
        this.fSymbolTable = (SymbolTable) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
        this.fErrorReporter = (XMLErrorReporter) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
        this.fEntityManager = (XMLEntityManager) xMLComponentManager.getProperty(ENTITY_MANAGER);
        try {
            this.fValidation = xMLComponentManager.getFeature(VALIDATION);
        } catch (XMLConfigurationException unused2) {
            this.fValidation = false;
        }
        try {
            this.fNamespaces = xMLComponentManager.getFeature(NAMESPACES);
        } catch (XMLConfigurationException unused3) {
            this.fNamespaces = true;
        }
        try {
            this.fNotifyCharRefs = xMLComponentManager.getFeature(NOTIFY_CHAR_REFS);
        } catch (XMLConfigurationException unused4) {
            this.fNotifyCharRefs = false;
        }
        init();
    }

    protected boolean scanAttributeValue(XMLString xMLString, XMLString xMLString2, String str, boolean z, String str2) throws IOException, XNIException {
        int iIsUnchangedByNormalization;
        char c;
        XMLStringBuffer xMLStringBuffer;
        int iPeekChar = this.fEntityScanner.peekChar();
        if (iPeekChar != 39 && iPeekChar != 34) {
            reportFatalError("OpenQuoteExpected", new Object[]{str2, str});
        }
        this.fEntityScanner.scanChar();
        int i = this.fEntityDepth;
        int iScanLiteral = this.fEntityScanner.scanLiteral(iPeekChar, xMLString);
        if (iScanLiteral == iPeekChar) {
            iIsUnchangedByNormalization = isUnchangedByNormalization(xMLString);
            if (iIsUnchangedByNormalization == -1) {
                xMLString2.setValues(xMLString);
                if (this.fEntityScanner.scanChar() != iPeekChar) {
                    reportFatalError("CloseQuoteExpected", new Object[]{str2, str});
                }
                return true;
            }
        } else {
            iIsUnchangedByNormalization = 0;
        }
        this.fStringBuffer2.clear();
        this.fStringBuffer2.append(xMLString);
        normalizeWhitespace(xMLString, iIsUnchangedByNormalization);
        if (iScanLiteral != iPeekChar) {
            this.fScanningAttribute = true;
            this.fStringBuffer.clear();
            while (true) {
                this.fStringBuffer.append(xMLString);
                if (iScanLiteral == 38) {
                    this.fEntityScanner.skipChar(38);
                    if (i == this.fEntityDepth) {
                        this.fStringBuffer2.append(Typography.amp);
                    }
                    if (this.fEntityScanner.skipChar(35)) {
                        if (i == this.fEntityDepth) {
                            this.fStringBuffer2.append('#');
                        }
                        scanCharReferenceValue(this.fStringBuffer, this.fStringBuffer2);
                    } else {
                        String strScanName = this.fEntityScanner.scanName();
                        if (strScanName == null) {
                            reportFatalError("NameRequiredInReference", null);
                        } else if (i == this.fEntityDepth) {
                            this.fStringBuffer2.append(strScanName);
                        }
                        if (!this.fEntityScanner.skipChar(59)) {
                            reportFatalError("SemicolonRequiredInReference", new Object[]{strScanName});
                        } else if (i == this.fEntityDepth) {
                            this.fStringBuffer2.append(';');
                        }
                        if (strScanName == fAmpSymbol) {
                            this.fStringBuffer.append(Typography.amp);
                        } else if (strScanName == fAposSymbol) {
                            this.fStringBuffer.append('\'');
                        } else if (strScanName == fLtSymbol) {
                            this.fStringBuffer.append('<');
                        } else if (strScanName == fGtSymbol) {
                            this.fStringBuffer.append('>');
                        } else if (strScanName == fQuotSymbol) {
                            this.fStringBuffer.append('\"');
                        } else if (this.fEntityManager.isExternalEntity(strScanName)) {
                            reportFatalError("ReferenceToExternalEntity", new Object[]{strScanName});
                        } else {
                            if (!this.fEntityManager.isDeclaredEntity(strScanName)) {
                                if (!z) {
                                    reportFatalError("EntityNotDeclared", new Object[]{strScanName});
                                } else if (this.fValidation) {
                                    this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityNotDeclared", new Object[]{strScanName}, (short) 1);
                                }
                            }
                            this.fEntityManager.startEntity(strScanName, true);
                        }
                    }
                } else if (iScanLiteral == 60) {
                    reportFatalError("LessthanInAttValue", new Object[]{str2, str});
                    this.fEntityScanner.scanChar();
                    if (i == this.fEntityDepth) {
                        this.fStringBuffer2.append((char) iScanLiteral);
                    }
                } else if (iScanLiteral == 37 || iScanLiteral == 93) {
                    this.fEntityScanner.scanChar();
                    c = (char) iScanLiteral;
                    this.fStringBuffer.append(c);
                    if (i == this.fEntityDepth) {
                        xMLStringBuffer = this.fStringBuffer2;
                        xMLStringBuffer.append(c);
                    }
                } else if (iScanLiteral == 10 || iScanLiteral == 13) {
                    this.fEntityScanner.scanChar();
                    this.fStringBuffer.append(StringUtil.SPACE);
                    if (i == this.fEntityDepth) {
                        this.fStringBuffer2.append('\n');
                    }
                } else {
                    int i2 = -1;
                    if (iScanLiteral != -1) {
                        if (XMLChar.isHighSurrogate(iScanLiteral)) {
                            this.fStringBuffer3.clear();
                            if (scanSurrogates(this.fStringBuffer3)) {
                                this.fStringBuffer.append(this.fStringBuffer3);
                                if (i == this.fEntityDepth) {
                                    this.fStringBuffer2.append(this.fStringBuffer3);
                                }
                            }
                        } else {
                            i2 = -1;
                        }
                    }
                    if (iScanLiteral != i2 && isInvalidLiteral(iScanLiteral)) {
                        reportFatalError("InvalidCharInAttValue", new Object[]{str2, str, Integer.toString(iScanLiteral, 16)});
                        this.fEntityScanner.scanChar();
                        if (i == this.fEntityDepth) {
                            xMLStringBuffer = this.fStringBuffer2;
                            c = (char) iScanLiteral;
                            xMLStringBuffer.append(c);
                        }
                    }
                }
                iScanLiteral = this.fEntityScanner.scanLiteral(iPeekChar, xMLString);
                if (i == this.fEntityDepth) {
                    this.fStringBuffer2.append(xMLString);
                }
                normalizeWhitespace(xMLString);
                if (iScanLiteral == iPeekChar && i == this.fEntityDepth) {
                    break;
                }
            }
            this.fStringBuffer.append(xMLString);
            xMLString.setValues(this.fStringBuffer);
            this.fScanningAttribute = false;
        }
        xMLString2.setValues(this.fStringBuffer2);
        if (this.fEntityScanner.scanChar() != iPeekChar) {
            reportFatalError("CloseQuoteExpected", new Object[]{str2, str});
        }
        return xMLString2.equals(xMLString.ch, xMLString.offset, xMLString.length);
    }

    protected int scanCharReferenceValue(XMLStringBuffer xMLStringBuffer, XMLStringBuffer xMLStringBuffer2) throws IOException, XNIException {
        boolean z;
        boolean z2;
        int i;
        char cLowSurrogate;
        boolean z3;
        if (this.fEntityScanner.skipChar(120)) {
            if (xMLStringBuffer2 != null) {
                xMLStringBuffer2.append('x');
            }
            this.fStringBuffer3.clear();
            int iPeekChar = this.fEntityScanner.peekChar();
            if ((iPeekChar < 48 || iPeekChar > 57) && ((iPeekChar < 97 || iPeekChar > 102) && (iPeekChar < 65 || iPeekChar > 70))) {
                reportFatalError("HexdigitRequiredInCharRef", null);
            } else {
                if (xMLStringBuffer2 != null) {
                    xMLStringBuffer2.append((char) iPeekChar);
                }
                this.fEntityScanner.scanChar();
                this.fStringBuffer3.append((char) iPeekChar);
                do {
                    int iPeekChar2 = this.fEntityScanner.peekChar();
                    z3 = (iPeekChar2 >= 48 && iPeekChar2 <= 57) || (iPeekChar2 >= 97 && iPeekChar2 <= 102) || (iPeekChar2 >= 65 && iPeekChar2 <= 70);
                    if (z3) {
                        if (xMLStringBuffer2 != null) {
                            xMLStringBuffer2.append((char) iPeekChar2);
                        }
                        this.fEntityScanner.scanChar();
                        this.fStringBuffer3.append((char) iPeekChar2);
                    }
                } while (z3);
            }
            z = true;
        } else {
            this.fStringBuffer3.clear();
            int iPeekChar3 = this.fEntityScanner.peekChar();
            if (iPeekChar3 < 48 || iPeekChar3 > 57) {
                reportFatalError("DigitRequiredInCharRef", null);
            } else {
                if (xMLStringBuffer2 != null) {
                    xMLStringBuffer2.append((char) iPeekChar3);
                }
                this.fEntityScanner.scanChar();
                this.fStringBuffer3.append((char) iPeekChar3);
                do {
                    int iPeekChar4 = this.fEntityScanner.peekChar();
                    z2 = iPeekChar4 >= 48 && iPeekChar4 <= 57;
                    if (z2) {
                        if (xMLStringBuffer2 != null) {
                            xMLStringBuffer2.append((char) iPeekChar4);
                        }
                        this.fEntityScanner.scanChar();
                        this.fStringBuffer3.append((char) iPeekChar4);
                    }
                } while (z2);
            }
            z = false;
        }
        if (!this.fEntityScanner.skipChar(59)) {
            reportFatalError("SemicolonRequiredInCharRef", null);
        }
        if (xMLStringBuffer2 != null) {
            xMLStringBuffer2.append(';');
        }
        try {
            i = Integer.parseInt(this.fStringBuffer3.toString(), z ? 16 : 10);
            try {
                if (isInvalid(i)) {
                    StringBuffer stringBuffer = new StringBuffer(this.fStringBuffer3.length + 1);
                    if (z) {
                        stringBuffer.append('x');
                    }
                    stringBuffer.append(this.fStringBuffer3.ch, this.fStringBuffer3.offset, this.fStringBuffer3.length);
                    reportFatalError("InvalidCharRef", new Object[]{stringBuffer.toString()});
                }
            } catch (NumberFormatException unused) {
                StringBuffer stringBuffer2 = new StringBuffer(this.fStringBuffer3.length + 1);
                if (z) {
                    stringBuffer2.append('x');
                }
                stringBuffer2.append(this.fStringBuffer3.ch, this.fStringBuffer3.offset, this.fStringBuffer3.length);
                reportFatalError("InvalidCharRef", new Object[]{stringBuffer2.toString()});
            }
        } catch (NumberFormatException unused2) {
            i = -1;
        }
        if (XMLChar.isSupplemental(i)) {
            xMLStringBuffer.append(XMLChar.highSurrogate(i));
            cLowSurrogate = XMLChar.lowSurrogate(i);
        } else {
            cLowSurrogate = (char) i;
        }
        xMLStringBuffer.append(cLowSurrogate);
        if (this.fNotifyCharRefs && i != -1) {
            String str = "#" + (z ? "x" : "") + this.fStringBuffer3.toString();
            if (!this.fScanningAttribute) {
                this.fCharRefLiteral = str;
            }
        }
        return i;
    }

    protected void scanComment(XMLStringBuffer xMLStringBuffer) throws IOException, XNIException {
        xMLStringBuffer.clear();
        while (this.fEntityScanner.scanData("--", xMLStringBuffer)) {
            int iPeekChar = this.fEntityScanner.peekChar();
            if (iPeekChar != -1) {
                if (XMLChar.isHighSurrogate(iPeekChar)) {
                    scanSurrogates(xMLStringBuffer);
                } else if (isInvalidLiteral(iPeekChar)) {
                    reportFatalError("InvalidCharInComment", new Object[]{Integer.toHexString(iPeekChar)});
                    this.fEntityScanner.scanChar();
                }
            }
        }
        if (this.fEntityScanner.skipChar(62)) {
            return;
        }
        reportFatalError("DashDashInComment", null);
    }

    protected void scanExternalID(String[] strArr, boolean z) throws IOException, XNIException {
        String string;
        String str = null;
        if (this.fEntityScanner.skipString("PUBLIC")) {
            if (!this.fEntityScanner.skipSpaces()) {
                reportFatalError("SpaceRequiredAfterPUBLIC", null);
            }
            scanPubidLiteral(this.fString);
            string = this.fString.toString();
            if (!this.fEntityScanner.skipSpaces() && !z) {
                reportFatalError("SpaceRequiredBetweenPublicAndSystem", null);
            }
        } else {
            string = null;
        }
        if (string != null || this.fEntityScanner.skipString("SYSTEM")) {
            if (string == null && !this.fEntityScanner.skipSpaces()) {
                reportFatalError("SpaceRequiredAfterSYSTEM", null);
            }
            int iPeekChar = this.fEntityScanner.peekChar();
            if (iPeekChar != 39 && iPeekChar != 34) {
                if (string != null && z) {
                    strArr[0] = null;
                    strArr[1] = string;
                    return;
                }
                reportFatalError("QuoteRequiredInSystemID", null);
            }
            this.fEntityScanner.scanChar();
            XMLString xMLString = this.fString;
            if (this.fEntityScanner.scanLiteral(iPeekChar, xMLString) != iPeekChar) {
                this.fStringBuffer.clear();
                do {
                    this.fStringBuffer.append(xMLString);
                    int iPeekChar2 = this.fEntityScanner.peekChar();
                    if (XMLChar.isMarkup(iPeekChar2) || iPeekChar2 == 93) {
                        this.fStringBuffer.append((char) this.fEntityScanner.scanChar());
                    } else if (XMLChar.isHighSurrogate(iPeekChar2)) {
                        scanSurrogates(this.fStringBuffer);
                    } else if (isInvalidLiteral(iPeekChar2)) {
                        reportFatalError("InvalidCharInSystemID", new Object[]{Integer.toHexString(iPeekChar2)});
                        this.fEntityScanner.scanChar();
                    }
                } while (this.fEntityScanner.scanLiteral(iPeekChar, xMLString) != iPeekChar);
                this.fStringBuffer.append(xMLString);
                xMLString = this.fStringBuffer;
            }
            String string2 = xMLString.toString();
            if (!this.fEntityScanner.skipChar(iPeekChar)) {
                reportFatalError("SystemIDUnterminated", null);
            }
            str = string2;
        }
        strArr[0] = str;
        strArr[1] = string;
    }

    protected void scanPI() throws IOException, XNIException {
        this.fReportEntity = false;
        String strScanNCName = this.fNamespaces ? this.fEntityScanner.scanNCName() : this.fEntityScanner.scanName();
        if (strScanNCName == null) {
            reportFatalError("PITargetRequired", null);
        }
        scanPIData(strScanNCName, this.fString);
        this.fReportEntity = true;
    }

    protected void scanPIData(String str, XMLString xMLString) throws IOException, XNIException {
        if (str.length() == 3) {
            char lowerCase = Character.toLowerCase(str.charAt(0));
            char lowerCase2 = Character.toLowerCase(str.charAt(1));
            char lowerCase3 = Character.toLowerCase(str.charAt(2));
            if (lowerCase == 'x' && lowerCase2 == 'm' && lowerCase3 == 'l') {
                reportFatalError("ReservedPITarget", null);
            }
        }
        if (!this.fEntityScanner.skipSpaces()) {
            if (this.fEntityScanner.skipString("?>")) {
                xMLString.clear();
                return;
            }
            if (this.fNamespaces && this.fEntityScanner.peekChar() == 58) {
                this.fEntityScanner.scanChar();
                XMLStringBuffer xMLStringBuffer = new XMLStringBuffer(str);
                xMLStringBuffer.append(':');
                String strScanName = this.fEntityScanner.scanName();
                if (strScanName != null) {
                    xMLStringBuffer.append(strScanName);
                }
                reportFatalError("ColonNotLegalWithNS", new Object[]{xMLStringBuffer.toString()});
                this.fEntityScanner.skipSpaces();
            } else {
                reportFatalError("SpaceRequiredInPI", null);
            }
        }
        this.fStringBuffer.clear();
        if (this.fEntityScanner.scanData("?>", this.fStringBuffer)) {
            do {
                int iPeekChar = this.fEntityScanner.peekChar();
                if (iPeekChar != -1) {
                    if (XMLChar.isHighSurrogate(iPeekChar)) {
                        scanSurrogates(this.fStringBuffer);
                    } else if (isInvalidLiteral(iPeekChar)) {
                        reportFatalError("InvalidCharInPI", new Object[]{Integer.toHexString(iPeekChar)});
                        this.fEntityScanner.scanChar();
                    }
                }
            } while (this.fEntityScanner.scanData("?>", this.fStringBuffer));
        }
        xMLString.setValues(this.fStringBuffer);
    }

    public String scanPseudoAttribute(boolean z, XMLString xMLString) throws IOException, XNIException {
        String strScanPseudoAttributeName = scanPseudoAttributeName();
        XMLEntityManager.print(this.fEntityManager.getCurrentEntity());
        if (strScanPseudoAttributeName == null) {
            reportFatalError("PseudoAttrNameExpected", null);
        }
        this.fEntityScanner.skipDeclSpaces();
        if (!this.fEntityScanner.skipChar(61)) {
            reportFatalError(z ? "EqRequiredInTextDecl" : "EqRequiredInXMLDecl", new Object[]{strScanPseudoAttributeName});
        }
        this.fEntityScanner.skipDeclSpaces();
        int iPeekChar = this.fEntityScanner.peekChar();
        if (iPeekChar != 39 && iPeekChar != 34) {
            reportFatalError(z ? "QuoteRequiredInTextDecl" : "QuoteRequiredInXMLDecl", new Object[]{strScanPseudoAttributeName});
        }
        this.fEntityScanner.scanChar();
        int iScanLiteral = this.fEntityScanner.scanLiteral(iPeekChar, xMLString);
        if (iScanLiteral != iPeekChar) {
            this.fStringBuffer2.clear();
            do {
                this.fStringBuffer2.append(xMLString);
                if (iScanLiteral != -1) {
                    if (iScanLiteral == 38 || iScanLiteral == 37 || iScanLiteral == 60 || iScanLiteral == 93) {
                        this.fStringBuffer2.append((char) this.fEntityScanner.scanChar());
                    } else if (XMLChar.isHighSurrogate(iScanLiteral)) {
                        scanSurrogates(this.fStringBuffer2);
                    } else if (isInvalidLiteral(iScanLiteral)) {
                        reportFatalError(z ? "InvalidCharInTextDecl" : "InvalidCharInXMLDecl", new Object[]{Integer.toString(iScanLiteral, 16)});
                        this.fEntityScanner.scanChar();
                    }
                }
                iScanLiteral = this.fEntityScanner.scanLiteral(iPeekChar, xMLString);
            } while (iScanLiteral != iPeekChar);
            this.fStringBuffer2.append(xMLString);
            xMLString.setValues(this.fStringBuffer2);
        }
        if (!this.fEntityScanner.skipChar(iPeekChar)) {
            reportFatalError(z ? "CloseQuoteMissingInTextDecl" : "CloseQuoteMissingInXMLDecl", new Object[]{strScanPseudoAttributeName});
        }
        return strScanPseudoAttributeName;
    }

    protected boolean scanPubidLiteral(XMLString xMLString) throws IOException, XNIException {
        String str;
        int iScanChar = this.fEntityScanner.scanChar();
        if (iScanChar == 39 || iScanChar == 34) {
            this.fStringBuffer.clear();
            boolean z = true;
            boolean z2 = true;
            while (true) {
                int iScanChar2 = this.fEntityScanner.scanChar();
                if (iScanChar2 == 32 || iScanChar2 == 10 || iScanChar2 == 13) {
                    if (!z) {
                        this.fStringBuffer.append(StringUtil.SPACE);
                        z = true;
                    }
                } else {
                    if (iScanChar2 == iScanChar) {
                        if (z) {
                            this.fStringBuffer.length--;
                        }
                        xMLString.setValues(this.fStringBuffer);
                        return z2;
                    }
                    if (XMLChar.isPubid(iScanChar2)) {
                        this.fStringBuffer.append((char) iScanChar2);
                        z = false;
                    } else if (iScanChar2 == -1) {
                        str = "PublicIDUnterminated";
                    } else {
                        reportFatalError("InvalidCharInPublicID", new Object[]{Integer.toHexString(iScanChar2)});
                        z2 = false;
                    }
                }
            }
        } else {
            str = "QuoteRequiredInPublicID";
        }
        reportFatalError(str, null);
        return false;
    }

    protected boolean scanSurrogates(XMLStringBuffer xMLStringBuffer) throws IOException, XNIException {
        int iScanChar = this.fEntityScanner.scanChar();
        int iPeekChar = this.fEntityScanner.peekChar();
        if (!XMLChar.isLowSurrogate(iPeekChar)) {
            reportFatalError("InvalidCharInContent", new Object[]{Integer.toString(iScanChar, 16)});
            return false;
        }
        this.fEntityScanner.scanChar();
        char c = (char) iScanChar;
        char c2 = (char) iPeekChar;
        int iSupplemental = XMLChar.supplemental(c, c2);
        if (isInvalid(iSupplemental)) {
            reportFatalError("InvalidCharInContent", new Object[]{Integer.toString(iSupplemental, 16)});
            return false;
        }
        xMLStringBuffer.append(c);
        xMLStringBuffer.append(c2);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0094 A[PHI: r10
      0x0094: PHI (r10v3 java.lang.String) = (r10v2 java.lang.String), (r10v6 java.lang.String) binds: [B:68:0x010c, B:31:0x0091] A[DONT_GENERATE, DONT_INLINE]] */
    protected void scanXMLDeclOrTextDecl(boolean z, String[] strArr) throws IOException, XNIException {
        Object[] objArr;
        Object[] objArr2;
        boolean zSkipDeclSpaces = this.fEntityScanner.skipDeclSpaces();
        XMLEntityManager.ScannedEntity currentEntity = this.fEntityManager.getCurrentEntity();
        boolean z2 = currentEntity.literal;
        currentEntity.literal = false;
        char c = 0;
        boolean z3 = false;
        String string = null;
        String string2 = null;
        String string3 = null;
        while (this.fEntityScanner.peekChar() != 63) {
            String strScanPseudoAttribute = scanPseudoAttribute(z, this.fString);
            if (c != 0) {
                if (c != 1) {
                    if (c != 2) {
                        reportFatalError("NoMorePseudoAttributes", null);
                    } else if (strScanPseudoAttribute == fStandaloneSymbol) {
                        if (!zSkipDeclSpaces) {
                            reportFatalError("SpaceRequiredBeforeStandalone", null);
                        }
                        string3 = this.fString.toString();
                        if (!string3.equals("yes") && !string3.equals("no")) {
                            reportFatalError("SDDeclInvalid", new Object[]{string3});
                        }
                    } else {
                        reportFatalError("EncodingDeclRequired", null);
                    }
                } else if (strScanPseudoAttribute == fEncodingSymbol) {
                    if (!zSkipDeclSpaces) {
                        reportFatalError(z ? "SpaceRequiredBeforeEncodingInTextDecl" : "SpaceRequiredBeforeEncodingInXMLDecl", null);
                    }
                    string2 = this.fString.toString();
                    if (!z) {
                        c = 2;
                    }
                } else if (z || strScanPseudoAttribute != fStandaloneSymbol) {
                    objArr2 = null;
                    reportFatalError("EncodingDeclRequired", objArr2);
                } else {
                    if (!zSkipDeclSpaces) {
                        reportFatalError("SpaceRequiredBeforeStandalone", null);
                    }
                    string3 = this.fString.toString();
                    if (!string3.equals("yes") && !string3.equals("no")) {
                        reportFatalError("SDDeclInvalid", new Object[]{string3});
                    }
                }
                c = 3;
            } else if (strScanPseudoAttribute == fVersionSymbol) {
                if (!zSkipDeclSpaces) {
                    reportFatalError(z ? "SpaceRequiredBeforeVersionInTextDecl" : "SpaceRequiredBeforeVersionInXMLDecl", null);
                }
                string = this.fString.toString();
                if (!versionSupported(string)) {
                    reportFatalError(getVersionNotSupportedKey(), new Object[]{string});
                }
                c = 1;
            } else if (strScanPseudoAttribute == fEncodingSymbol) {
                if (!z) {
                    reportFatalError("VersionInfoRequired", null);
                }
                if (!zSkipDeclSpaces) {
                    reportFatalError(z ? "SpaceRequiredBeforeEncodingInTextDecl" : "SpaceRequiredBeforeEncodingInXMLDecl", null);
                }
                string2 = this.fString.toString();
                if (z) {
                    c = 3;
                } else {
                    c = 2;
                }
            } else {
                objArr2 = null;
                if (z) {
                    reportFatalError("EncodingDeclRequired", objArr2);
                } else {
                    reportFatalError("VersionInfoRequired", null);
                }
            }
            zSkipDeclSpaces = this.fEntityScanner.skipDeclSpaces();
            z3 = true;
        }
        if (z2) {
            currentEntity.literal = true;
        }
        if (!z || c == 3) {
            objArr = null;
        } else {
            objArr = null;
            reportFatalError("MorePseudoAttributes", null);
        }
        if (z) {
            if (!z3 && string2 == null) {
                reportFatalError("EncodingDeclRequired", objArr);
            }
        } else if (!z3 && string == null) {
            reportFatalError("VersionInfoRequired", objArr);
        }
        if (!this.fEntityScanner.skipChar(63)) {
            reportFatalError("XMLDeclUnterminated", objArr);
        }
        if (!this.fEntityScanner.skipChar(62)) {
            reportFatalError("XMLDeclUnterminated", objArr);
        }
        strArr[0] = string;
        strArr[1] = string2;
        strArr[2] = string3;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
        if (VALIDATION.equals(str)) {
            this.fValidation = z;
        } else if (NOTIFY_CHAR_REFS.equals(str)) {
            this.fNotifyCharRefs = z;
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        if (str.startsWith(Constants.XERCES_PROPERTY_PREFIX)) {
            int length = str.length() - Constants.XERCES_PROPERTY_PREFIX.length();
            if (length == Constants.SYMBOL_TABLE_PROPERTY.length() && str.endsWith(Constants.SYMBOL_TABLE_PROPERTY)) {
                this.fSymbolTable = (SymbolTable) obj;
                return;
            }
            if (length == Constants.ERROR_REPORTER_PROPERTY.length() && str.endsWith(Constants.ERROR_REPORTER_PROPERTY)) {
                this.fErrorReporter = (XMLErrorReporter) obj;
            } else if (length == Constants.ENTITY_MANAGER_PROPERTY.length() && str.endsWith(Constants.ENTITY_MANAGER_PROPERTY)) {
                this.fEntityManager = (XMLEntityManager) obj;
            }
        }
    }

    public void startEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        this.fEntityDepth++;
        this.fEntityScanner = this.fEntityManager.getEntityScanner();
    }

    protected boolean versionSupported(String str) {
        return str.equals(BuildConfig.VERSION_NAME);
    }
}
