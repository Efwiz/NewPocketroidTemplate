package com.beust.jcommander.converters;

import com.beust.jcommander.ParameterException;
import java.net.MalformedURLException;
import java.net.URL;

/* JADX INFO: loaded from: classes8.dex */
public class URLConverter extends BaseConverter<URL> {
    public URLConverter(String str) {
        super(str);
    }

    @Override // com.beust.jcommander.IStringConverter
    public URL convert(String str) {
        try {
            return new URL(str);
        } catch (MalformedURLException unused) {
            throw new ParameterException(getErrorString(str, "a RFC 2396 and RFC 2732 compliant URL"));
        }
    }
}
