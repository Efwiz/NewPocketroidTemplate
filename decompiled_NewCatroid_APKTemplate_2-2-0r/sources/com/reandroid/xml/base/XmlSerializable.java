package com.reandroid.xml.base;

import com.reandroid.xml.XMLFactory;
import com.reandroid.xml.XMLUtil;
import com.reandroid.xml.XmlIndentingSerializer;
import java.io.IOException;
import java.io.StringWriter;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public interface XmlSerializable {
    void serialize(XmlSerializer xmlSerializer) throws IOException;

    static String toXmlString(XmlSerializable xmlSerializable, boolean z) throws IOException {
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XMLFactory.newSerializer(stringWriter);
        if (z) {
            xmlSerializerNewSerializer = XmlIndentingSerializer.create(xmlSerializerNewSerializer);
        }
        xmlSerializable.serialize(xmlSerializerNewSerializer);
        XMLUtil.close(xmlSerializerNewSerializer);
        stringWriter.close();
        return stringWriter.toString();
    }
}
