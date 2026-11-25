package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.StreamException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/SjsxpDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SjsxpDriver extends StaxDriver {
    public SjsxpDriver() {
    }

    public SjsxpDriver(QNameMap qNameMap, XmlFriendlyNameCoder xmlFriendlyNameCoder) {
        super(qNameMap, xmlFriendlyNameCoder);
    }

    public SjsxpDriver(QNameMap qNameMap) {
        super(qNameMap);
    }

    public SjsxpDriver(XmlFriendlyNameCoder xmlFriendlyNameCoder) {
        super(xmlFriendlyNameCoder);
    }

    @Override // com.thoughtworks.xstream.io.xml.StaxDriver
    protected XMLInputFactory createInputFactory() {
        try {
            XMLInputFactory xMLInputFactory = (XMLInputFactory) Class.forName("com.sun.xml.internal.stream.XMLInputFactoryImpl").newInstance();
            xMLInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
            return xMLInputFactory;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new StreamException("Cannot create SJSXP (Sun JDK 6 StAX) XMLInputFaqctory instance.", e);
        }
    }

    @Override // com.thoughtworks.xstream.io.xml.StaxDriver
    protected XMLOutputFactory createOutputFactory() {
        try {
            return (XMLOutputFactory) Class.forName("com.sun.xml.internal.stream.XMLOutputFactoryImpl").newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new StreamException("Cannot create SJSXP (Sun JDK 6 StAX) XMLOutputFaqctory instance.", e);
        }
    }
}
