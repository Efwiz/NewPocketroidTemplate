package com.reandroid.arsc.coder.xml;

import com.reandroid.arsc.item.StringItem;
import com.reandroid.xml.XMLUtil;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class RawXmlStringDecoder implements XmlStringDecoder {
    @Override // com.reandroid.arsc.coder.xml.XmlStringDecoder
    public void serializeText(StringItem stringItem, XmlSerializer xmlSerializer) throws IOException {
        stringItem.serializeText(xmlSerializer, true);
    }

    @Override // com.reandroid.arsc.coder.xml.XmlStringDecoder
    public String decodeAttributeValue(StringItem stringItem) {
        return XMLUtil.escapeXmlChars(stringItem.getXml());
    }
}
