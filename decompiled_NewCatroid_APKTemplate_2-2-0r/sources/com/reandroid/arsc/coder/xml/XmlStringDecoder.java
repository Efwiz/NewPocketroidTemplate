package com.reandroid.arsc.coder.xml;

import com.reandroid.arsc.item.StringItem;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public interface XmlStringDecoder {
    String decodeAttributeValue(StringItem stringItem);

    void serializeText(StringItem stringItem, XmlSerializer xmlSerializer) throws IOException;
}
