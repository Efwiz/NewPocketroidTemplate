package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/KXml2DomDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class KXml2DomDriver extends AbstractXppDomDriver {
    public KXml2DomDriver() {
        super(new XmlFriendlyNameCoder());
    }

    public KXml2DomDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractXppDomDriver
    protected XmlPullParser createParser() {
        return new KXmlParser();
    }
}
