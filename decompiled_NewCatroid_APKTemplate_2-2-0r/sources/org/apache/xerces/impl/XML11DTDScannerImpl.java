package org.apache.xerces.impl;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.catrobat.catroid.BuildConfig;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes13.dex */
public class XML11DTDScannerImpl extends XMLDTDScannerImpl {
    private final XMLStringBuffer fStringBuffer;

    public XML11DTDScannerImpl() {
        this.fStringBuffer = new XMLStringBuffer();
    }

    public XML11DTDScannerImpl(SymbolTable symbolTable, XMLErrorReporter xMLErrorReporter, XMLEntityManager xMLEntityManager) {
        super(symbolTable, xMLErrorReporter, xMLEntityManager);
        this.fStringBuffer = new XMLStringBuffer();
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected String getVersionNotSupportedKey() {
        return "VersionNotSupported11";
    }

    @Override // org.apache.xerces.impl.XMLScanner
    protected boolean isInvalid(int i) {
        return !XML11Char.isXML11Valid(i);
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
