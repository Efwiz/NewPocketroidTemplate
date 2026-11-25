package org.apache.xerces.jaxp;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/* JADX INFO: loaded from: classes13.dex */
class DefaultValidationErrorHandler extends DefaultHandler {
    private static int ERROR_COUNT_LIMIT = 10;
    private int errorCount = 0;

    DefaultValidationErrorHandler() {
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ErrorHandler
    public void error(SAXParseException sAXParseException) throws SAXException {
        int i = this.errorCount;
        if (i >= ERROR_COUNT_LIMIT) {
            return;
        }
        if (i == 0) {
            System.err.println("Warning: validation was turned on but an org.xml.sax.ErrorHandler was not");
            System.err.println("set, which is probably not what is desired.  Parser will use a default");
            System.err.println("ErrorHandler to print the first " + ERROR_COUNT_LIMIT + " errors.  Please call");
            System.err.println("the 'setErrorHandler' method to fix this.");
        }
        String systemId = sAXParseException.getSystemId();
        if (systemId == null) {
            systemId = "null";
        }
        System.err.println("Error: URI=" + systemId + " Line=" + sAXParseException.getLineNumber() + ": " + sAXParseException.getMessage());
        this.errorCount++;
    }
}
