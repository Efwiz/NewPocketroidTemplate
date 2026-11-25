package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/XppDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XppDriver extends AbstractXppDriver {
    private static XmlPullParserFactory factory;

    public XppDriver() {
        super(new XmlFriendlyNameCoder());
    }

    public XppDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    public XppDriver(XmlFriendlyReplacer xmlFriendlyReplacer) {
        this((NameCoder) xmlFriendlyReplacer);
    }

    public static synchronized XmlPullParser createDefaultParser() throws XmlPullParserException {
        if (factory == null) {
            factory = XmlPullParserFactory.newInstance();
        }
        return factory.newPullParser();
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractXppDriver
    protected XmlPullParser createParser() throws XmlPullParserException {
        return createDefaultParser();
    }
}
