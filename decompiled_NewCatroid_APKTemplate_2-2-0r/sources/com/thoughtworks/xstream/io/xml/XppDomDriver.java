package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/XppDomDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XppDomDriver extends AbstractXppDomDriver {
    public XppDomDriver() {
        super(new XmlFriendlyNameCoder());
    }

    public XppDomDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    public XppDomDriver(XmlFriendlyReplacer xmlFriendlyReplacer) {
        super(xmlFriendlyReplacer);
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractXppDomDriver
    protected synchronized XmlPullParser createParser() throws XmlPullParserException {
        return XppDriver.createDefaultParser();
    }
}
