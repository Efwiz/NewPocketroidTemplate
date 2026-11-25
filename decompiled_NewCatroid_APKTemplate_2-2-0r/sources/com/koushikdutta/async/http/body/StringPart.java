package com.koushikdutta.async.http.body;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes10.dex */
public class StringPart extends StreamPart {
    String value;

    public StringPart(String str, String str2) {
        super(str, str2.getBytes().length, null);
        this.value = str2;
    }

    @Override // com.koushikdutta.async.http.body.StreamPart
    protected InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.value.getBytes());
    }
}
