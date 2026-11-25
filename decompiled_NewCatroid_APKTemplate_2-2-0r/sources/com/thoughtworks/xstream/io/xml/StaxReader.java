package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.converters.ErrorWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/StaxReader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class StaxReader extends AbstractPullReader {
    private final XMLStreamReader in;
    private final QNameMap qnameMap;

    public StaxReader(QNameMap qNameMap, XMLStreamReader xMLStreamReader) {
        this(qNameMap, xMLStreamReader, new XmlFriendlyNameCoder());
    }

    public StaxReader(QNameMap qNameMap, XMLStreamReader xMLStreamReader, NameCoder nameCoder) {
        super(nameCoder);
        this.qnameMap = qNameMap;
        this.in = xMLStreamReader;
        moveDown();
    }

    public StaxReader(QNameMap qNameMap, XMLStreamReader xMLStreamReader, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(qNameMap, xMLStreamReader, (NameCoder) xmlFriendlyReplacer);
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractPullReader
    protected int pullNextEvent() {
        try {
            int next = this.in.next();
            if (next != 1) {
                if (next != 2) {
                    if (next == 4) {
                        return 3;
                    }
                    if (next == 5) {
                        return 4;
                    }
                    if (next != 7) {
                        if (next != 8) {
                            return 0;
                        }
                    }
                }
                return 2;
            }
            return 1;
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractPullReader
    protected String pullElementName() {
        return this.qnameMap.getJavaClassName(this.in.getName());
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractPullReader
    protected String pullText() {
        return this.in.getText();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttribute(String str) {
        return this.in.getAttributeValue(null, encodeAttribute(str));
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttribute(int i) {
        return this.in.getAttributeValue(i);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public int getAttributeCount() {
        return this.in.getAttributeCount();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttributeName(int i) {
        return decodeAttribute(this.in.getAttributeLocalName(i));
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader, com.thoughtworks.xstream.converters.ErrorReporter
    public void appendErrors(ErrorWriter errorWriter) {
        errorWriter.add("line number", String.valueOf(this.in.getLocation().getLineNumber()));
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public void close() {
        try {
            this.in.close();
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }
}
