package com.thoughtworks.xstream.io.xml;

import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;
import com.thoughtworks.xstream.io.naming.NameCoder;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/WstxDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class WstxDriver extends StaxDriver {
    public WstxDriver() {
    }

    public WstxDriver(QNameMap qNameMap, XmlFriendlyNameCoder xmlFriendlyNameCoder) {
        super(qNameMap, xmlFriendlyNameCoder);
    }

    public WstxDriver(QNameMap qNameMap, NameCoder nameCoder) {
        super(qNameMap, nameCoder);
    }

    public WstxDriver(QNameMap qNameMap) {
        super(qNameMap);
    }

    public WstxDriver(XmlFriendlyNameCoder xmlFriendlyNameCoder) {
        super(xmlFriendlyNameCoder);
    }

    public WstxDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    @Override // com.thoughtworks.xstream.io.xml.StaxDriver
    protected XMLInputFactory createInputFactory() {
        WstxInputFactory wstxInputFactory = new WstxInputFactory();
        wstxInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        return wstxInputFactory;
    }

    @Override // com.thoughtworks.xstream.io.xml.StaxDriver
    protected XMLOutputFactory createOutputFactory() {
        return new WstxOutputFactory();
    }
}
