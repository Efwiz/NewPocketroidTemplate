package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/StandardStaxDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class StandardStaxDriver extends StaxDriver {
    public StandardStaxDriver() {
    }

    public StandardStaxDriver(QNameMap qNameMap, XmlFriendlyNameCoder xmlFriendlyNameCoder) {
        super(qNameMap, xmlFriendlyNameCoder);
    }

    public StandardStaxDriver(QNameMap qNameMap, NameCoder nameCoder) {
        super(qNameMap, nameCoder);
    }

    public StandardStaxDriver(QNameMap qNameMap) {
        super(qNameMap);
    }

    public StandardStaxDriver(XmlFriendlyNameCoder xmlFriendlyNameCoder) {
        super(xmlFriendlyNameCoder);
    }

    public StandardStaxDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    @Override // com.thoughtworks.xstream.io.xml.StaxDriver
    protected XMLInputFactory createInputFactory() {
        try {
            Class staxInputFactory = JVM.getStaxInputFactory();
            if (staxInputFactory != null) {
                XMLInputFactory xMLInputFactory = (XMLInputFactory) staxInputFactory.newInstance();
                xMLInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
                return xMLInputFactory;
            }
            throw new StreamException("Java runtime has no standard XMLInputFactory implementation.", null);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new StreamException("Cannot create standard XMLInputFactory instance of Java runtime.", e);
        }
    }

    @Override // com.thoughtworks.xstream.io.xml.StaxDriver
    protected XMLOutputFactory createOutputFactory() {
        try {
            Class staxOutputFactory = JVM.getStaxOutputFactory();
            if (staxOutputFactory != null) {
                return (XMLOutputFactory) staxOutputFactory.newInstance();
            }
            throw new StreamException("Java runtime has no standard XMLOutputFactory implementation.", null);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new StreamException("Cannot create standard XMLOutputFactory instance of Java runtime.", e);
        }
    }
}
