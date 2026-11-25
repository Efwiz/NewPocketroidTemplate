package org.apache.xerces.jaxp.validation;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.transform.stax.StAXResult;
import org.apache.xerces.xni.XMLDocumentHandler;

/* JADX INFO: loaded from: classes13.dex */
interface StAXDocumentHandler extends XMLDocumentHandler {
    void cdata(Characters characters) throws XMLStreamException;

    void characters(Characters characters) throws XMLStreamException;

    void comment(XMLStreamReader xMLStreamReader) throws XMLStreamException;

    void comment(Comment comment) throws XMLStreamException;

    void doctypeDecl(DTD dtd) throws XMLStreamException;

    void endDocument(XMLStreamReader xMLStreamReader) throws XMLStreamException;

    void endDocument(EndDocument endDocument) throws XMLStreamException;

    void entityReference(XMLStreamReader xMLStreamReader) throws XMLStreamException;

    void entityReference(EntityReference entityReference) throws XMLStreamException;

    void processingInstruction(XMLStreamReader xMLStreamReader) throws XMLStreamException;

    void processingInstruction(ProcessingInstruction processingInstruction) throws XMLStreamException;

    void setIgnoringCharacters(boolean z);

    void setStAXResult(StAXResult stAXResult);

    void startDocument(XMLStreamReader xMLStreamReader) throws XMLStreamException;

    void startDocument(StartDocument startDocument) throws XMLStreamException;
}
