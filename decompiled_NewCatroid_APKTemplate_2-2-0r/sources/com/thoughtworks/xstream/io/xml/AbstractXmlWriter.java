package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.AbstractWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/AbstractXmlWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractXmlWriter extends AbstractWriter implements XmlFriendlyWriter {
    protected AbstractXmlWriter() {
        this(new XmlFriendlyNameCoder());
    }

    protected AbstractXmlWriter(XmlFriendlyReplacer xmlFriendlyReplacer) {
        this((NameCoder) xmlFriendlyReplacer);
    }

    protected AbstractXmlWriter(NameCoder nameCoder) {
        super(nameCoder);
    }

    @Override // com.thoughtworks.xstream.io.xml.XmlFriendlyWriter
    public String escapeXmlName(String str) {
        return super.encodeNode(str);
    }
}
