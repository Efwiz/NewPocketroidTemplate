package org.apache.xerces.util;

import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLParseException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/* JADX INFO: loaded from: classes13.dex */
public class ErrorHandlerWrapper implements XMLErrorHandler {
    protected ErrorHandler fErrorHandler;

    public ErrorHandlerWrapper() {
    }

    public ErrorHandlerWrapper(ErrorHandler errorHandler) {
        setErrorHandler(errorHandler);
    }

    protected static SAXParseException createSAXParseException(XMLParseException xMLParseException) {
        return new SAXParseException(xMLParseException.getMessage(), xMLParseException.getPublicId(), xMLParseException.getExpandedSystemId(), xMLParseException.getLineNumber(), xMLParseException.getColumnNumber(), xMLParseException.getException());
    }

    protected static XMLParseException createXMLParseException(SAXParseException sAXParseException) {
        final String publicId = sAXParseException.getPublicId();
        final String systemId = sAXParseException.getSystemId();
        final int lineNumber = sAXParseException.getLineNumber();
        final int columnNumber = sAXParseException.getColumnNumber();
        return new XMLParseException(new XMLLocator() { // from class: org.apache.xerces.util.ErrorHandlerWrapper.1
            @Override // org.apache.xerces.xni.XMLLocator
            public String getBaseSystemId() {
                return null;
            }

            @Override // org.apache.xerces.xni.XMLLocator
            public int getCharacterOffset() {
                return -1;
            }

            @Override // org.apache.xerces.xni.XMLLocator
            public int getColumnNumber() {
                return columnNumber;
            }

            @Override // org.apache.xerces.xni.XMLLocator
            public String getEncoding() {
                return null;
            }

            @Override // org.apache.xerces.xni.XMLLocator
            public String getExpandedSystemId() {
                return systemId;
            }

            @Override // org.apache.xerces.xni.XMLLocator
            public int getLineNumber() {
                return lineNumber;
            }

            @Override // org.apache.xerces.xni.XMLLocator
            public String getLiteralSystemId() {
                return null;
            }

            @Override // org.apache.xerces.xni.XMLLocator
            public String getPublicId() {
                return publicId;
            }

            @Override // org.apache.xerces.xni.XMLLocator
            public String getXMLVersion() {
                return null;
            }
        }, sAXParseException.getMessage(), sAXParseException);
    }

    protected static XNIException createXNIException(SAXException sAXException) {
        return new XNIException(sAXException.getMessage(), sAXException);
    }

    @Override // org.apache.xerces.xni.parser.XMLErrorHandler
    public void error(String str, String str2, XMLParseException xMLParseException) throws XNIException {
        if (this.fErrorHandler != null) {
            try {
                this.fErrorHandler.error(createSAXParseException(xMLParseException));
            } catch (SAXParseException e) {
                throw createXMLParseException(e);
            } catch (SAXException e2) {
                throw createXNIException(e2);
            }
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLErrorHandler
    public void fatalError(String str, String str2, XMLParseException xMLParseException) throws XNIException {
        if (this.fErrorHandler != null) {
            try {
                this.fErrorHandler.fatalError(createSAXParseException(xMLParseException));
            } catch (SAXParseException e) {
                throw createXMLParseException(e);
            } catch (SAXException e2) {
                throw createXNIException(e2);
            }
        }
    }

    public ErrorHandler getErrorHandler() {
        return this.fErrorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.fErrorHandler = errorHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLErrorHandler
    public void warning(String str, String str2, XMLParseException xMLParseException) throws XNIException {
        if (this.fErrorHandler != null) {
            try {
                this.fErrorHandler.warning(createSAXParseException(xMLParseException));
            } catch (SAXParseException e) {
                throw createXMLParseException(e);
            } catch (SAXException e2) {
                throw createXNIException(e2);
            }
        }
    }
}
