package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.core.StringCodec;
import java.util.Base64;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/Base64JavaUtilCodec.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Base64JavaUtilCodec implements StringCodec {
    private final Base64.Decoder decoder;
    private final Base64.Encoder encoder;

    public Base64JavaUtilCodec() {
        this(Base64.getEncoder(), Base64.getMimeDecoder());
    }

    public Base64JavaUtilCodec(Base64.Encoder encoder, Base64.Decoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override // com.thoughtworks.xstream.core.StringCodec
    public byte[] decode(String str) {
        return this.decoder.decode(str);
    }

    @Override // com.thoughtworks.xstream.core.StringCodec
    public String encode(byte[] bArr) {
        return this.encoder.encodeToString(bArr);
    }
}
