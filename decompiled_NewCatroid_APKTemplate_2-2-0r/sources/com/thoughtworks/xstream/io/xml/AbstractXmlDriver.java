package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.AbstractDriver;
import com.thoughtworks.xstream.io.naming.NameCoder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/AbstractXmlDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractXmlDriver extends AbstractDriver {
    public AbstractXmlDriver() {
        this(new XmlFriendlyNameCoder());
    }

    public AbstractXmlDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    public AbstractXmlDriver(XmlFriendlyReplacer xmlFriendlyReplacer) {
        this((NameCoder) xmlFriendlyReplacer);
    }

    protected XmlFriendlyReplacer xmlFriendlyReplacer() {
        NameCoder nameCoder = getNameCoder();
        if (nameCoder instanceof XmlFriendlyReplacer) {
            return (XmlFriendlyReplacer) nameCoder;
        }
        return null;
    }
}
