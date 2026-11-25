package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.core.StringCodec;
import javax.xml.bind.DatatypeConverter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/Base64JAXBCodec.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Base64JAXBCodec implements StringCodec {
    @Override // com.thoughtworks.xstream.core.StringCodec
    public byte[] decode(String str) {
        return DatatypeConverter.parseBase64Binary(str);
    }

    @Override // com.thoughtworks.xstream.core.StringCodec
    public String encode(byte[] bArr) {
        return DatatypeConverter.printBase64Binary(bArr);
    }
}
