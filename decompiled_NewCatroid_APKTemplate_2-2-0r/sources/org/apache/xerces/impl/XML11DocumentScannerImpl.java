package org.apache.xerces.impl;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import kotlin.text.Typography;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.catrobat.catroid.BuildConfig;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes13.dex */
public class XML11DocumentScannerImpl extends XMLDocumentScannerImpl {
    private final XMLString fString = new XMLString();
    private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
    private final XMLStringBuffer fStringBuffer2 = new XMLStringBuffer();
    private final XMLStringBuffer fStringBuffer3 = new XMLStringBuffer();

    @Override // org.apache.xerces.impl.XMLScanner
    protected String getVersionNotSupportedKey() {
        return "VersionNotSupported11";
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected boolean isInvalid(int i) {
        return XML11Char.isXML11Invalid(i);
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected boolean isInvalidLiteral(int i) {
        return !XML11Char.isXML11ValidLiteral(i);
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected int isUnchangedByNormalization(XMLString xMLString) {
        int i = xMLString.offset + xMLString.length;
        for (int i2 = xMLString.offset; i2 < i; i2++) {
            if (XMLChar.isSpace(xMLString.ch[i2])) {
                return i2 - xMLString.offset;
            }
        }
        return -1;
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected boolean isValidNCName(int i) {
        return XML11Char.isXML11NCName(i);
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected boolean isValidNameChar(int i) {
        return XML11Char.isXML11Name(i);
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected boolean isValidNameStartChar(int i) {
        return XML11Char.isXML11NameStart(i);
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected boolean isValidNameStartHighSurrogate(int i) {
        return XML11Char.isXML11NameHighSurrogate(i);
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected void normalizeWhitespace(XMLString xMLString) {
        int i = xMLString.offset + xMLString.length;
        for (int i2 = xMLString.offset; i2 < i; i2++) {
            if (XMLChar.isSpace(xMLString.ch[i2])) {
                xMLString.ch[i2] = StringUtil.SPACE;
            }
        }
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected void normalizeWhitespace(XMLString xMLString, int i) {
        int i2 = xMLString.offset + xMLString.length;
        for (int i3 = xMLString.offset + i; i3 < i2; i3++) {
            if (XMLChar.isSpace(xMLString.ch[i3])) {
                xMLString.ch[i3] = StringUtil.SPACE;
            }
        }
    }

    @Override // org.apache.xerces.impl.XMLScanner
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
                } else if (iScanLiteral == 10 || iScanLiteral == 13 || iScanLiteral == 133 || iScanLiteral == 8232) {
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

    @Override // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl
    protected int scanContent() throws IOException, XNIException {
        XMLString xMLString = this.fString;
        int iScanContent = this.fEntityScanner.scanContent(xMLString);
        if (iScanContent == 13 || iScanContent == 133 || iScanContent == 8232) {
            this.fEntityScanner.scanChar();
            this.fStringBuffer.clear();
            this.fStringBuffer.append(this.fString);
            this.fStringBuffer.append((char) iScanContent);
            xMLString = this.fStringBuffer;
            iScanContent = -1;
        }
        if (this.fDocumentHandler != null && xMLString.length > 0) {
            this.fDocumentHandler.characters(xMLString, null);
        }
        if (iScanContent != 93 || this.fString.length != 0) {
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

    @Override // org.apache.xerces.impl.XMLScanner
    protected boolean scanPubidLiteral(XMLString xMLString) throws IOException, XNIException {
        String str;
        int iScanChar = this.fEntityScanner.scanChar();
        if (iScanChar == 39 || iScanChar == 34) {
            this.fStringBuffer.clear();
            boolean z = true;
            boolean z2 = true;
            while (true) {
                int iScanChar2 = this.fEntityScanner.scanChar();
                if (iScanChar2 == 32 || iScanChar2 == 10 || iScanChar2 == 13 || iScanChar2 == 133 || iScanChar2 == 8232) {
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

    @Override // org.apache.xerces.impl.XMLScanner
    protected boolean versionSupported(String str) {
        return str.equals(CompilerOptions.VERSION_1_1) || str.equals(BuildConfig.VERSION_NAME);
    }
}
