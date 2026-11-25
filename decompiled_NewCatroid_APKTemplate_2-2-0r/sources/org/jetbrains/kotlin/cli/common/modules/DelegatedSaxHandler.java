package org.jetbrains.kotlin.cli.common.modules;

import java.io.IOException;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DelegatedSaxHandler extends DefaultHandler {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 2:
            case 14:
                objArr[0] = "name";
                break;
            case 3:
            default:
                objArr[0] = "systemId";
                break;
            case 4:
                objArr[0] = "locator";
                break;
            case 5:
            case 6:
                objArr[0] = "uri";
                break;
            case 7:
            case 10:
                objArr[0] = "localName";
                break;
            case 8:
            case 11:
                objArr[0] = "qName";
                break;
            case 9:
                objArr[0] = "attributes";
                break;
            case 12:
                objArr[0] = "target";
                break;
            case 13:
                objArr[0] = "data";
                break;
            case 15:
            case 16:
            case 17:
                objArr[0] = "e";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/cli/common/modules/DelegatedSaxHandler";
        switch (i) {
            case 1:
                objArr[2] = "notationDecl";
                break;
            case 2:
            case 3:
                objArr[2] = "unparsedEntityDecl";
                break;
            case 4:
                objArr[2] = "setDocumentLocator";
                break;
            case 5:
                objArr[2] = "startPrefixMapping";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "startElement";
                break;
            case 10:
            case 11:
                objArr[2] = "endElement";
                break;
            case 12:
            case 13:
                objArr[2] = "processingInstruction";
                break;
            case 14:
                objArr[2] = "skippedEntity";
                break;
            case 15:
                objArr[2] = CompilerOptions.WARNING;
                break;
            case 16:
                objArr[2] = "error";
                break;
            case 17:
                objArr[2] = "fatalError";
                break;
            default:
                objArr[2] = "resolveEntity";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected abstract DefaultHandler getDelegate();

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.EntityResolver
    public InputSource resolveEntity(String str, String str2) throws SAXException, IOException {
        if (str2 == null) {
            $$$reportNull$$$0(0);
        }
        return getDelegate().resolveEntity(str, str2);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.DTDHandler
    public void notationDecl(String str, String str2, String str3) throws SAXException {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        getDelegate().notationDecl(str, str2, str3);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.DTDHandler
    public void unparsedEntityDecl(String str, String str2, String str3, String str4) throws SAXException {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (str3 == null) {
            $$$reportNull$$$0(3);
        }
        getDelegate().unparsedEntityDecl(str, str2, str3, str4);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void setDocumentLocator(Locator locator) {
        if (locator == null) {
            $$$reportNull$$$0(4);
        }
        getDelegate().setDocumentLocator(locator);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startDocument() throws SAXException {
        getDelegate().startDocument();
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endDocument() throws SAXException {
        getDelegate().endDocument();
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startPrefixMapping(String str, String str2) throws SAXException {
        if (str2 == null) {
            $$$reportNull$$$0(5);
        }
        getDelegate().startPrefixMapping(str, str2);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endPrefixMapping(String str) throws SAXException {
        getDelegate().endPrefixMapping(str);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (str2 == null) {
            $$$reportNull$$$0(7);
        }
        if (str3 == null) {
            $$$reportNull$$$0(8);
        }
        if (attributes == null) {
            $$$reportNull$$$0(9);
        }
        getDelegate().startElement(str, str2, str3, attributes);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException {
        if (str2 == null) {
            $$$reportNull$$$0(10);
        }
        if (str3 == null) {
            $$$reportNull$$$0(11);
        }
        getDelegate().endElement(str, str2, str3);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        getDelegate().characters(cArr, i, i2);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        getDelegate().ignorableWhitespace(cArr, i, i2);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void processingInstruction(String str, String str2) throws SAXException {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (str2 == null) {
            $$$reportNull$$$0(13);
        }
        getDelegate().processingInstruction(str, str2);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void skippedEntity(String str) throws SAXException {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        getDelegate().skippedEntity(str);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ErrorHandler
    public void warning(SAXParseException sAXParseException) throws SAXException {
        if (sAXParseException == null) {
            $$$reportNull$$$0(15);
        }
        getDelegate().warning(sAXParseException);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ErrorHandler
    public void error(SAXParseException sAXParseException) throws SAXException {
        if (sAXParseException == null) {
            $$$reportNull$$$0(16);
        }
        getDelegate().error(sAXParseException);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ErrorHandler
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        if (sAXParseException == null) {
            $$$reportNull$$$0(17);
        }
        getDelegate().fatalError(sAXParseException);
    }
}
