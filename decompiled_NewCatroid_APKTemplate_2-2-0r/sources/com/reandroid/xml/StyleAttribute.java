package com.reandroid.xml;

import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class StyleAttribute extends XMLAttribute {
    public StyleAttribute(XMLAttribute xMLAttribute) {
        super(xMLAttribute.getName(true), xMLAttribute.getValueAsString(false));
    }

    public StyleAttribute(String str, String str2) {
        super(str, str2);
    }

    public StyleAttribute() {
    }

    @Override // com.reandroid.xml.XMLAttribute, com.reandroid.xml.base.XmlSerializable
    public void serialize(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.attribute(null, getName(), getValueAsString());
    }
}
