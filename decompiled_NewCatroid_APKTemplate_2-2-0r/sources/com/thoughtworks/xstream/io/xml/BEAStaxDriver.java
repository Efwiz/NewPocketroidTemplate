package com.thoughtworks.xstream.io.xml;

import com.bea.xml.stream.MXParserFactory;
import com.bea.xml.stream.XMLOutputFactoryBase;
import com.thoughtworks.xstream.io.naming.NameCoder;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/BEAStaxDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class BEAStaxDriver extends StaxDriver {
    public BEAStaxDriver() {
    }

    public BEAStaxDriver(QNameMap qNameMap, XmlFriendlyNameCoder xmlFriendlyNameCoder) {
        super(qNameMap, xmlFriendlyNameCoder);
    }

    public BEAStaxDriver(QNameMap qNameMap, NameCoder nameCoder) {
        super(qNameMap, nameCoder);
    }

    public BEAStaxDriver(QNameMap qNameMap) {
        super(qNameMap);
    }

    public BEAStaxDriver(XmlFriendlyNameCoder xmlFriendlyNameCoder) {
        super(xmlFriendlyNameCoder);
    }

    public BEAStaxDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    @Override // com.thoughtworks.xstream.io.xml.StaxDriver
    protected XMLInputFactory createInputFactory() {
        MXParserFactory mXParserFactory = new MXParserFactory();
        mXParserFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        return mXParserFactory;
    }

    @Override // com.thoughtworks.xstream.io.xml.StaxDriver
    protected XMLOutputFactory createOutputFactory() {
        return new XMLOutputFactoryBase();
    }
}
