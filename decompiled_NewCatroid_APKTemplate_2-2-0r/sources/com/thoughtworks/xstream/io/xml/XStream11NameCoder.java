package com.thoughtworks.xstream.io.xml;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/XStream11NameCoder.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XStream11NameCoder extends XmlFriendlyNameCoder {
    @Override // com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder, com.thoughtworks.xstream.io.naming.NameCoder
    public String decodeAttribute(String str) {
        return str;
    }

    @Override // com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder, com.thoughtworks.xstream.io.naming.NameCoder
    public String decodeNode(String str) {
        return str;
    }
}
