package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.AbstractReader;
import com.thoughtworks.xstream.io.naming.NameCoder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/AbstractXmlReader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractXmlReader extends AbstractReader {
    protected AbstractXmlReader() {
        this(new XmlFriendlyNameCoder());
    }

    protected AbstractXmlReader(XmlFriendlyReplacer xmlFriendlyReplacer) {
        this((NameCoder) xmlFriendlyReplacer);
    }

    protected AbstractXmlReader(NameCoder nameCoder) {
        super(nameCoder);
    }

    public String unescapeXmlName(String str) {
        return decodeNode(str);
    }

    protected String escapeXmlName(String str) {
        return encodeNode(str);
    }
}
