package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/Xpp3DomDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Xpp3DomDriver extends AbstractXppDomDriver {
    public Xpp3DomDriver() {
        super(new XmlFriendlyNameCoder());
    }

    public Xpp3DomDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractXppDomDriver
    protected XmlPullParser createParser() {
        return new MXParser();
    }
}
